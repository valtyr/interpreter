package com.hugbo.mariaskal.messages.payloads;

public class ErrorReceivedPayload {
    public String title;
    public String message;

    public ErrorReceivedPayload(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
