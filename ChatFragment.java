package com.example.do_an.fragment;

import android.os.Bundle;

import com.example.do_an.R;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.do_an.model.ChatViewModel;
import com.example.do_an.model.Message;
import com.example.do_an.adapter.MessagesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private ChatViewModel chatViewModel;
    List<Message> messageList;
    private MessagesAdapter adapter;
    private EditText editTextMessage;
    private Button buttonSend;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView recyclerViewMessages = view.findViewById(R.id.recyclerViewMessages);
        MessagesAdapter messagesAdapter = new MessagesAdapter(new ArrayList<>());
        recyclerViewMessages.setAdapter(messagesAdapter);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(getContext()));


        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        // Initialize editTextMessage and buttonSend
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);

        // Setup buttonSend click listener to send messages
        buttonSend.setOnClickListener(v -> {
            String messageText = editTextMessage.getText().toString().trim();
            if (!messageText.isEmpty()) {
                chatViewModel.sendMessage(messageText); // Send message via ViewModel
                editTextMessage.setText(""); // Clear the input field
            }
        });

        // Observe LiveData from ViewModel and update the UI accordingly
        chatViewModel.getMessages().observe(getViewLifecycleOwner(), messages -> {
            messagesAdapter.setMessages(messages);
            messagesAdapter.notifyDataSetChanged();
            if (messages != null && !messages.isEmpty()) {
                // Scroll to the bottom of the list to show the newest message
                recyclerViewMessages.smoothScrollToPosition(messages.size() - 1);
            }
        });
        return view;
    }
}