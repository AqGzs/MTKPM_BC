package com.example.do_an.model;

import java.util.ArrayList;
import java.util.List;

public class ChatPayload {
    String model;
    List<Message> messages;

    // Constructor that matches the provided arguments
    public ChatPayload(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    // Getters and setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
