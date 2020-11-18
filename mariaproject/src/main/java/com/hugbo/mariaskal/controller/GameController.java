package com.hugbo.mariaskal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hugbo.mariaskal.model.Game;
import com.hugbo.mariaskal.service.GameService;

import java.util.UUID;

@RestController
@RequestMapping("api/")
public class GameController {
    @Autowired
    private GameService gameService;

    // @Autowired
    // private Game game;

    @GetMapping("games")
    public List<Game> getGame() {
        return this.gameService.findAll();
    }

    public void startNewRound() {

    }

    /*
     * public void addCard(String CardName, UUID playerUUID, UUID gameUUID) { //
     * finnum leikinn með gameUUID í gagnagrunni // Bætum orðinu við á leikmanni með
     * playerUUID // vistum það í gagnagrunni // Broadköstum nýju state-i á leiknum
     * // }
     */

    public String createGame() {
        String joinId = String.valueOf(Math.random() * 100000);
        // this.game = blalbabla
        return joinId;
    }

    /*
     * public UUID joinGame(String joinID, String username, UUID playerUUID) {
     * Player newPlayer = new Player(username, playerUUID); // Game joinedGame =
     * gameService.findByJoinID(joinID); return joinedGame.getUUID(); }
     */

}