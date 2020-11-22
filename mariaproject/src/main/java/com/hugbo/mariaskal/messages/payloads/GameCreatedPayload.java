package com.hugbo.mariaskal.messages.payloads;

import com.hugbo.mariaskal.model.Game;

public class GameCreatedPayload {
    public String id;
    public Game gameState;

    public GameCreatedPayload(String id, Game gameState) {
        this.id = id;
        this.gameState = gameState;
    }
}
