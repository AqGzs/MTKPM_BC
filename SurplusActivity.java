package com.example.do_an.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.do_an.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SurplusActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    TextView soDuTextView;
    private ImageButton bck_sdv_admin;
    private String accountId;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_du_vi);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = this.getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");

        bck_sdv_admin = findViewById(R.id.bck_sdv_admin);
        bck_sdv_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        soDuTextView = findViewById(R.id.chitietsodu_admin);

        fetchSoDuFromFirestore(phoneNumber);
    }
    private void fetchSoDuFromFirestore(String phoneNumber) {
        db.collection("Users").document(phoneNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                long soDu = document.getLong("soDuVi");
                                if(soDu >= 1000)
                                    soDuTextView.setText("Số dư ví: " + String.format("%,d", soDu) + "đ");
                                else
                                    soDuTextView.setText("Số dư ví: " + String.format("%,d", soDu) + "đ");
                            } else {
                                Log.d("SurplusActivity", "TaikhoanID: " + phoneNumber);
                                // Xử lý khi tài liệu không tồn tại
                                Log.d("SurplusActivity", "Document does not exist");
                            }
                        } else {
                            Log.d("SurplusActivity", "Error getting documents: ", task.getException());
                            // Xử lý khi không thể lấy dữ liệu từ Firestore
                        }
                    }
                });
    }
}