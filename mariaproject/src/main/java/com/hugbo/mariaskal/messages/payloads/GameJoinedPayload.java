package com.hugbo.mariaskal.messages.payloads;

import com.hugbo.mariaskal.model.Game;

public class GameJoinedPayload {
    public Game gameState;

    public GameJoinedPayload(Game gameState) {
        this.gameState = gameState;
    }
}
