package com.example.do_an.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRequest {
    private String model;
    private List<Message> messages;

    // Updated constructor
    public ChatRequest(String model, List<Message> messages) {
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
