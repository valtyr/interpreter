package com.hugbo.mariaskal.messages.payloads;

import com.hugbo.mariaskal.model.Game;

public class GameUpdatedPayload {
    public Game gameState;

    public GameUpdatedPayload(Game game) {
        this.gameState = game;
    }
}
