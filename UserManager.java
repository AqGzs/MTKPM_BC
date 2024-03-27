package com.example.do_an.model;

import com.example.do_an.DPattern.UserDataObserver;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private FirebaseFirestore db;
    private List<UserDataObserver> observers = new ArrayList<>();

    private UserManager() {
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void addObserver(UserDataObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(UserDataObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String hoTen) {
        for (UserDataObserver observer : observers) {
            observer.onUserDataChanged(hoTen);
        }
    }

    public void fetchUserName(String phoneNumber) {
        db.collection("UsersInfo").document("CN" + phoneNumber).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String hoTen = document.getString("HoTen");
                            notifyObservers(hoTen);
                        } else {
                            notifyObservers(""); // Document not found or HoTen is empty
                        }
                    } else {
                        notifyObservers(""); // Error occurred
                    }
                });
    }
}

