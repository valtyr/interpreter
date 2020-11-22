package com.hugbo.mariaskal.messages.payloads;

import com.hugbo.mariaskal.model.Player;

public class UsernameSetPayload {
    public String username;
    public Player player;

    public UsernameSetPayload(String username, Player player) {
        this.username = username;
        this.player = player;
    }
}
