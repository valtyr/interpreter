package com.hugbo.mariaskal.websockets.controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.hugbo.mariaskal.messages.Message;
import com.hugbo.mariaskal.messages.MessageType;
import com.hugbo.mariaskal.messages.payloads.ErrorReceivedPayload;
import com.hugbo.mariaskal.messages.payloads.GameCreatedPayload;
import com.hugbo.mariaskal.messages.payloads.GameJoinedPayload;
import com.hugbo.mariaskal.messages.payloads.GameUpdatedPayload;
import com.hugbo.mariaskal.messages.payloads.UsernameSetPayload;
import com.hugbo.mariaskal.model.Game;
import com.hugbo.mariaskal.model.Player;
import com.hugbo.mariaskal.model.Card;
import com.hugbo.mariaskal.model.CardGroup;
import com.hugbo.mariaskal.service.GameService;
import com.hugbo.mariaskal.service.CardGroupService;
import com.hugbo.mariaskal.service.CardService;
import com.hugbo.mariaskal.service.PlayerService;
import com.hugbo.mariaskal.websockets.StompPrincipal;

@Controller
@Transactional
public class GameWSController {
  private final SimpMessagingTemplate simpMessagingTemplate;

  private ArrayList<Game> activeGames;

  @Autowired
  private PlayerService playerService;

  @Autowired
  private GameService gameService;

  @Autowired
  private CardService cardService;

  @Autowired
  private CardGroupService cardGroupService;

  Logger logger = LoggerFactory.getLogger(GameWSController.class);

  @Autowired
  SimpUserRegistry simpUserRegistry;

  public GameWSController(SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.activeGames = new ArrayList<>();
  }

  private Player getPlayerFromUUID(String uuid) {
    UUID userId = UUID.fromString(uuid);
    return playerService.findByConnectionId(userId);
  }

  // private Game getGame

  @MessageMapping("/message")
  public void message(Message m, StompPrincipal principal) throws Exception {
    logger.info("Received message of type: " + m.type.toString());

    switch (m.type) {
      case PING -> {
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/messages",
            new Message(MessageType.PONG, null));
      }
      case SET_USERNAME -> {
        String username = (String) m.payload;
        principal.setUsername(username);

        UUID connectionId = UUID.fromString(principal.getName());

        // Skrifa þetta
        Player newPlayer = playerService.createPlayerFromConnectionId(connectionId, username);
        playerService.save(newPlayer);

        UsernameSetPayload payload = new UsernameSetPayload(username, newPlayer);

        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/messages",
            new Message(MessageType.USERNAME_SET, payload));
      }
      case CREATE_GAME -> {
        UUID userId = UUID.fromString(principal.getName());

        Player creator = playerService.findByConnectionId(userId);
        Game game = new Game(creator);
        game = gameService.save(game);

        principal.setGameId(game.getId());

        GameCreatedPayload payload = new GameCreatedPayload(game.getShareId(), game);
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/messages",
            new Message(MessageType.GAME_CREATED, payload));
      }
      case JOIN_GAME -> {
        String gameId = (String) m.payload;

        Game game = gameService.findByShareId(gameId);

        if (game == null) {
          simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/messages", new Message(
              MessageType.ERROR_RECEIVED,
              new ErrorReceivedPayload("Game not found", "Couldn't join game " + m.payload + ". Game ID not found.")));
          return;
        }

        principal.setGameId(game.getId());

        UUID userId = UUID.fromString(principal.getName());
        Player player = playerService.findByConnectionId(userId);
        game.addPlayer(player);
        game = gameService.save(game);

        GameJoinedPayload gameJoinedPayload = new GameJoinedPayload(game);
        GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);

        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/messages",
            new Message(MessageType.GAME_JOINED, gameJoinedPayload));
        simpMessagingTemplate.convertAndSend("/topic/messages",
            new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));

        // m.payload -> ID Leiks
        // principal.getName() -> UUID Notanda (í augum spring)
        // principal.getUsername () -> Notandanafn notanda

        // Finna leik með serviceinu
        // Ef leikur er ekki til senda error
        // Annars bætum við notanda við leik
        // Sendum message á ALLA í leik með uppfæru leika-state meðal annars notanda sem
        // var að joina

        // simpMessagingTemplate.convertAndSendToUser(principal.getName(),
        // "/topic/messages", new Message(
        // MessageType.ERROR_RECEIVED,
        // new ErrorReceivedPayload("Game not found", "Couldn't join game " + m.payload
        // + ". Game ID not found.")));
      }
      case ADD_WORD -> {
        UUID userId = UUID.fromString(principal.getName());
        Player player = playerService.findByConnectionId(userId);
        // Player player = getPlayerFromUUID(principal.getName());
        String word = (String) m.payload;

        if (principal.getGameId().isEmpty()) {
          simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/messages",
              new Message(MessageType.ERROR_RECEIVED,
                  new ErrorReceivedPayload("Game not found", "You can't create a card if you haven't joined a game.")));
          return;
        }

        Card card = new Card(word, player);
        card = cardService.save(card);

        Game game = gameService.findById(principal.getGameId().getAsLong());

        CardGroup cardGroup = game.getCardGroup();
        cardGroup.addCard(card);
        cardGroup = cardGroupService.save(cardGroup);

        game.setCardGroup(cardGroup);
        game = gameService.save(game);

        GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);

        simpMessagingTemplate.convertAndSend("/topic/messages",
            new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));
      }
      case DELETE_WORD -> {
        LinkedHashMap<String, Integer> payload = (LinkedHashMap<String, Integer>) m.payload;
        Long id = Long.valueOf(payload.get("id"));

        UUID userId = UUID.fromString(principal.getName());
        Player player = playerService.findByConnectionId(userId);

        Game game = gameService.findById(principal.getGameId().getAsLong());
        Card card = cardService.findById(id);

        if (player.getConnectionId() != card.getCreatorId()) {
          simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/messages",
              new Message(MessageType.ERROR_RECEIVED,
                  new ErrorReceivedPayload("Not allowed", "You can only delete your own cards.")));
          return;
        }

        CardGroup cardGroup = game.getCardGroup();
        cardGroup.removeCard(card);
        cardGroupService.save(cardGroup);

        cardService.delete(card);

        gameService.save(game);

        GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);

        simpMessagingTemplate.convertAndSend("/topic/messages",
            new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));
      }
      // case START_GAME -> { }

      default -> {
        logger.warn("Unhandled message: " + m.type.toString());
      }
    }
  }
}