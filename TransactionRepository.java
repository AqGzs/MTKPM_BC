package com.example.do_an.DPattern;

import android.util.Log;

import com.example.do_an.model.NotificationModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    private FirebaseFirestore db;
    private List<NotificationModel> transactionList = new ArrayList<>();

    public TransactionRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public interface DataCallback {
        void onDataLoaded(List<NotificationModel> transactions);
    }

    public void getTransactions(final DataCallback callback) {
        db.collection("TransactionInfo").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                transactionList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    NotificationModel transaction = new NotificationModel(
                            document.getString("iddata"),
                            document.getString("pricetran"),
                            document.getString("date"),
                            document.getString("hour")
                    );
                    transactionList.add(transaction);
                }
                callback.onDataLoaded(transactionList);
            } else {
                Log.d("TransactionRepository", "Error getting documents: ", task.getException());
            }
        });
    }
}
