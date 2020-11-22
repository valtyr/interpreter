package com.hugbo.mariaskal.websockets.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import com.hugbo.mariaskal.messages.Message;
import com.hugbo.mariaskal.messages.MessageType;
import com.hugbo.mariaskal.model.Game;
import com.hugbo.mariaskal.websockets.StompPrincipal;

@Controller
public class GameWSController {
  private final SimpMessagingTemplate simpMessagingTemplate;

  private ArrayList<Game> activeGames;

  Logger logger = LoggerFactory.getLogger(GameWSController.class);

  @Autowired
  SimpUserRegistry simpUserRegistry;

  public GameWSController(SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.activeGames = new ArrayList<>();
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
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/messages",
            new Message(MessageType.USERNAME_SET, username));

      }
      default -> {
        logger.warn("Unhandled message: " + m.type.toString());
      }
    }
  }

  @MessageMapping("/ping")
  @SendTo("/topic/pong")
  public String ping() throws Exception {
    return "Pong";
  }

  @MessageMapping("/setusername")
  @SendTo("/topic/usernames")
  public String setUsername(String username, StompPrincipal principal) {
    principal.setUsername(username);
    simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/username", username);
    return "User with id " + principal.getName() + " chose username " + username;
  }

  @MessageMapping("/creategame")
  @SendToUser("/topic/game")
  public String createGame() {
    Game newGame = new Game();
    this.activeGames.add(newGame);
    return String.valueOf(newGame.getId());
  }

  @MessageMapping("/listgames")
  @SendToUser("/topic/games")
  public List<String> listGames() {
    Game newGame = new Game();
    this.activeGames.add(newGame);
    return this.activeGames.stream().map(game -> game.getId()).map(String::valueOf).collect(Collectors.toList());
  }

}