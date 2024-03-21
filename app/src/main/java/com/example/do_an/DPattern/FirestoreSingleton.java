package com.example.do_an.DPattern;

import com.google.firebase.firestore.FirebaseFirestore;
public class FirestoreSingleton {
    private static FirebaseFirestore INSTANCE = null;

    private FirestoreSingleton() {
    }

    public static synchronized FirebaseFirestore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = FirebaseFirestore.getInstance();
        }
        return INSTANCE;
    }
}
