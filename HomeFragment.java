package com.example.do_an.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.do_an.DPattern.HomeActions;
import com.example.do_an.DPattern.HomeFragmentActions;
import com.example.do_an.DPattern.LoggingHomeActionsDecorator;
import com.example.do_an.R;
import com.example.do_an.ui.ChuyenTienActivity;
import com.example.do_an.ui.DataActivity;
import com.example.do_an.ui.NotificationActivity;
import com.example.do_an.ui.RechargeActivity;
import com.example.do_an.ui.PersonalPageActivity;
import com.example.do_an.ui.WithdrawMoneyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.example.do_an.ui.InfoUserActivity;



public class HomeFragment extends Fragment {
    ImageButton goUsers, thongbao;
    TextView search, soduvi;
    LinearLayout naptien, rutTien, chuyenTien, napdata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupViews(view);
        setupActions();
        getInfo(phoneNumber);
        return view;
    }

    private void setupViews(View view) {
        goUsers = view.findViewById(R.id.goUsers);
        thongbao = view.findViewById(R.id.thongbao);
        search = view.findViewById(R.id.search); // Chỉ cần đề cập, không sử dụng trong mã mẫu này
        soduvi = view.findViewById(R.id.soduvi);
        naptien = view.findViewById(R.id.naptien);
        rutTien = view.findViewById(R.id.rutTien);
        chuyenTien = view.findViewById(R.id.chuyenTien);
        napdata = view.findViewById(R.id.napdata);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        // Có thể thêm mã để hiển thị thông tin người dùng hoặc tải dữ liệu cần thiết tại đây
    }

    private void setupActions() {
        HomeActions actions = new LoggingHomeActionsDecorator(new HomeFragmentActions(this));

        naptien.setOnClickListener(view -> actions.navigateToActivity(new Intent(getActivity(), RechargeActivity.class)));
        rutTien.setOnClickListener(view -> actions.navigateToActivity(new Intent(getActivity(), WithdrawMoneyActivity.class)));
        chuyenTien.setOnClickListener(view -> actions.navigateToActivity(new Intent(getActivity(), ChuyenTienActivity.class)));
        thongbao.setOnClickListener(view -> actions.navigateToActivity(new Intent(getActivity(), NotificationActivity.class)));
        napdata.setOnClickListener(view -> actions.navigateToActivity(new Intent(getActivity(), DataActivity.class)));
        goUsers.setOnClickListener(view -> actions.navigateToActivity(new Intent(getActivity(), PersonalPageActivity.class)));
    }
    void getInfo(String phoneNumber){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("Users").document(phoneNumber).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                long sodu = document.getLong("soDuVi");
                                if(sodu >= 1000)
                                    soduvi.setText("Số dư ví: " + String.format("%,d", sodu) + "đ"); // Định dạng số dư thành chuỗi có dấu chấm làm dấu phân cách hàng nghìn
                                else
                                    soduvi.setText(String.valueOf(sodu)); // Định dạng số dư thành chuỗi có dấu chấm làm dấu phân cách hàng nghìn

                            }
                        }
                    }
                });
    }
}