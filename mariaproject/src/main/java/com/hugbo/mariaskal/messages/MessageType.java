package com.hugbo.mariaskal.messages;

public enum MessageType {
    CREATE_GAME("CREATE_GAME"), GAME_CREATED("GAME_CREATED"), GAME_UPDATED("GAME_UPDATED"), START_GAME("START_GAME"),
    JOIN_GAME("JOIN_GAME"), GAME_JOINED("GAME_JOINED"), ADD_WORD("ADD_WORD"), DELETE_WORD("DELETE_WORD"),
    SET_USERNAME("SET_USERNAME"), PING("PING"), PONG("PONG"), USERNAME_SET("USERNAME_SET"),
    ERROR_RECEIVED("ERROR_RECEIVED");

    private String name;

    MessageType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}