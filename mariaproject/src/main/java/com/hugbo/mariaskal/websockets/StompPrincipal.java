package com.hugbo.mariaskal.websockets;

import java.security.Principal;
import java.util.UUID;

public class StompPrincipal implements Principal {
    private UUID id;
    private String username;

    public StompPrincipal(UUID id) {
        this.id = id;
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
}
