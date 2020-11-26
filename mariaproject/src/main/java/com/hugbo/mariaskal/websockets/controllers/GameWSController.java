package com.hugbo.mariaskal.websockets.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;
import java.util.Random;

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

import com.hugbo.mariaskal.helpers.ISOTimestamp;
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

  private void turnOver(Game game) {
    int sequenceSize = game.getPlayerSequence().size();
    int idx = (game.getCurrentPlayerIdx() + 1) % sequenceSize;
    game.setCurrentPlayerIdx(idx);
    long playerId = game.getPlayerSequence().get(idx);
    long guesserId = game.getPlayerSequence().get((idx + 1) % sequenceSize);
    game.setCurrentPlayer(playerService.findById(playerId));
    game.setCurrentGuesser(playerService.findById(guesserId));

    game.setTurnInProgress(false);

    if (game.getCardSequence().size() == 0) {
      int round = game.getCurrentRound();
      if (round == 4) {
        game.setGameOver(true);

        // Player winner = game.getPlayerList().stream().mapToInt(p ->
        // p.getScore()).max()
        // .orElseThrow(NoSuchElementException::new);
      }
      game.setCurrentRound(round + 1);
    }
  }

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
      case START_GAME -> {
        Game game = gameService.findById(principal.getGameId().getAsLong());
        game.setGameStartedTime(ISOTimestamp.getISOTimestamp());

        game.setCurrentRound(1);

        gameService.save(game);

        GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);
        simpMessagingTemplate.convertAndSend("/topic/messages",
            new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));
        // simpMessagingTemplate.convertAndSend("/topic/messages",
        // new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));
      }

      case START_ROUND -> {
        Game game = gameService.findById(principal.getGameId().getAsLong());
        // betra að búa til annan lista sem er copy svo röðin breytist ekki?
        // ja holup

        ArrayList<Long> sequence = new ArrayList<Long>(
            game.getPlayerList().stream().map(p -> p.getId()).collect(Collectors.toList()));

        logger.info(sequence.toString());

        Collections.shuffle(sequence, new Random());
        game.setPlayerSequence(sequence);

        // Veljum fyrsta leikmann
        int currentPlayerIdx = 0;
        int currentGuesserIdx = (currentPlayerIdx + 1) % sequence.size();
        game.setCurrentPlayerIdx(currentPlayerIdx);

        long playerId = sequence.get(currentPlayerIdx);
        long guesserId = sequence.get(currentGuesserIdx);
        game.setCurrentPlayer(playerService.findById(playerId));
        game.setCurrentGuesser(playerService.findById(guesserId));

        // Stokkum spilum
        ArrayList<Long> cardSequence = new ArrayList<Long>(
            game.getCardGroup().getCards().stream().map(c -> c.getId()).collect(Collectors.toList()));
        game.setCardSequence(cardSequence);

        gameService.save(game);

        GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);
        simpMessagingTemplate.convertAndSend("/topic/messages",
            new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));
      }
      case START_TURN -> {
        // Vista tíma sem turn byrjar
        Game game = gameService.findById(principal.getGameId().getAsLong());

        game.setTurnStartedTime(ISOTimestamp.getISOTimestamp());
        game.setTurnInProgress(true);

        ArrayList<Long> cardSequence = game.getCardSequence();
        Collections.shuffle(cardSequence, new Random());
        game.setCardSequence(cardSequence);

        game.setCurrentCardIdx(0);

        long currentCardIdx = cardSequence.get(0);
        Card currentCard = cardService.findById(currentCardIdx);
        game.setCurrentCard(currentCard);

        gameService.save(game);

        GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);
        simpMessagingTemplate.convertAndSend("/topic/messages",
            new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));
      }
      case END_TURN -> {
        Game game = gameService.findById(principal.getGameId().getAsLong());
        turnOver(game);
        gameService.save(game);

        GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);
        simpMessagingTemplate.convertAndSend("/topic/messages",
            new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));
      }
      case PUBLISH_CARDGROUP -> {
        LinkedHashMap<String, Object> payload = (LinkedHashMap<String, Object>) m.payload;

        Game game = gameService.findById(principal.getGameId().getAsLong());
        game.getCardGroup().setPublished(true);

        CardGroup cardGroup = game.getCardGroup();
        cardGroup.setName((String) payload.get("name"));
        cardGroup.setTags(new LinkedHashSet<String>((List<String>) payload.get("tags")));
        cardGroup.setCreationDate(ISOTimestamp.getISOTimestamp());

        UUID userId = UUID.fromString(principal.getName());
        Player player = playerService.findByConnectionId(userId);

        cardGroup.setCreator(player);
        cardGroup.incrementTimesUsed();

        gameService.save(game);

        GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);
        simpMessagingTemplate.convertAndSend("/topic/messages",
            new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));
      }
      case CORRECT -> {
        Game game = gameService.findById(principal.getGameId().getAsLong());

        game.getCurrentPlayer().addPoints(2);
        game.getCurrentGuesser().addPoints(3);

        int idx = game.getCurrentCardIdx();
        game.getCardSequence().remove(idx);

        if (idx >= game.getCardSequence().size()) {
          turnOver(game);
          gameService.save(game);

          GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);
          simpMessagingTemplate.convertAndSend("/topic/messages",
              new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));

          return;
        }

        long id = game.getCardSequence().get(idx);
        Card currentCard = cardService.findById(id);
        game.setCurrentCard(currentCard);

        gameService.save(game);

        GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);
        simpMessagingTemplate.convertAndSend("/topic/messages",
            new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));

      }
      case SKIP -> {
        Game game = gameService.findById(principal.getGameId().getAsLong());

        game.getCurrentPlayer().addPoints(-1);

        int idx = game.getCurrentCardIdx() + 1;
        if (idx >= game.getCardSequence().size()) {
          turnOver(game);

          gameService.save(game);

          GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);
          simpMessagingTemplate.convertAndSend("/topic/messages",
              new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));

          return;
        }

        game.setCurrentCardIdx(idx);

        long currentCardIdx = game.getCardSequence().get(idx);
        Card currentCard = cardService.findById(currentCardIdx);
        game.setCurrentCard(currentCard);

        gameService.save(game);

        GameUpdatedPayload gameUpdatedPayload = new GameUpdatedPayload(game);
        simpMessagingTemplate.convertAndSend("/topic/messages",
            new Message(MessageType.GAME_UPDATED, gameUpdatedPayload));
      }

      default -> {
        logger.warn("Unhandled message: " + m.type.toString());
      }
    }
  }
}