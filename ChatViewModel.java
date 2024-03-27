package com.example.do_an.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.do_an.DPattern.ChatRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {
    private ChatRepository chatRepository = new ChatRepository();
    private MutableLiveData<List<Message>> messages = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public ChatViewModel() {
        this.chatRepository = new ChatRepository();
    }
    public void sendMessage(String userInput) {
        List<Message> currentMessages = messages.getValue();
        if (currentMessages == null) {
            currentMessages = new ArrayList<>();
        }
        // Assuming "user" role for messages sent by the user
        currentMessages.add(new Message("user", userInput, true));
        messages.postValue(currentMessages);

        chatRepository.getResponseFromGPT(userInput, new ChatRepository.ResponseCallback() {
            @Override
            public void onResponse(String response) {
                List<Message> updatedMessages = messages.getValue();
                if (updatedMessages == null) {
                    updatedMessages = new ArrayList<>();
                }
                updatedMessages.add(new Message("system", response, false));
                messages.postValue(updatedMessages);
            }

            @Override
            public void onFailure(String error) {
                // Handle the failure case
                Log.e("ChatViewModel", "API call failed: " + error);
                // Optionally, update the UI or notify the user of the failure
            }
        });
    }
}
