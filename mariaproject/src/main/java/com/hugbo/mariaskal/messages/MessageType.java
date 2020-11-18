package com.hugbo.mariaskal.messages;

public enum MessageType {
    START_GAME("START_GAME"), JOIN_GAME("JOIN_GAME"), ADD_WORD("ADD_WORD"), SET_USERNAME("SET_USERNAME"), PING("PING"),
    PONG("PONG"), USERNAME_SET("USERNAME_SET");

    private String name;

    MessageType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}