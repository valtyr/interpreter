package com.hugbo.mariaskal.messages;

public class Message {
    public final MessageType type;
    public final Object payload;

    public Message(MessageType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
}
