package com.hugbo.mariaskal.websockets;

import java.security.Principal;
import java.util.OptionalLong;
import java.util.UUID;

public class StompPrincipal implements Principal {
    private UUID id;
    private String username;
    private OptionalLong gameId;

    public StompPrincipal(UUID id) {
        this.id = id;
        this.gameId = OptionalLong.empty();
    }

    @Override
    public String getName() {
        return id.toString();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setGameId(long gameId) {
        this.gameId = OptionalLong.of(gameId);
    }

    public OptionalLong getGameId() {
        return gameId;
    }
}
