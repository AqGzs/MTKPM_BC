package com.example.do_an.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.do_an.DPattern.UserDataObserver;
import com.example.do_an.MainActivity;
import com.example.do_an.R;
import com.example.do_an.model.UserManager;
import com.example.do_an.ui.EnterSdtActivity;
import com.example.do_an.ui.LoginActivity;
import com.example.do_an.ui.LoginAndSecurityActivity;
import com.example.do_an.ui.PersonalPageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;

public class SettingFragment extends Fragment implements UserDataObserver{
    Button btlogout;
    TextView nameUser, phoneNumber1;
    LinearLayout userPage, loginSecurity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate (tạo) view từ layout XML
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Cài đặt các view và sự kiện
        setupViews(view);

        // Đăng ký là một observer với UserManager để nhận cập nhật về dữ liệu người dùng
        UserManager.getInstance().addObserver(this);

        // Thực hiện lấy tên người dùng
        fetchUserName();

        return view;
    }

    private void setupViews(View view) {
        // Ánh xạ các view từ XML
        nameUser = view.findViewById(R.id.nameUser);
        phoneNumber1 = view.findViewById(R.id.phoneNumber1);
        btlogout = view.findViewById(R.id.out);
        userPage = view.findViewById(R.id.userPage);
        loginSecurity = view.findViewById(R.id.loginSecurity);

        // Đọc số điện thoại từ SharedPreferences và hiển thị lên giao diện
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        phoneNumber1.setText(phoneNumber);

        // Cài đặt các sự kiện click cho các nút
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        // Xử lý khi nhấn vào từng phần tử trên giao diện
        userPage.setOnClickListener(view -> {
            // Mở PersonalPageActivity
            Intent intent = new Intent(getActivity(), PersonalPageActivity.class);
            startActivity(intent);
        });

        loginSecurity.setOnClickListener(view -> {
            // Mở LoginAndSecurityActivity
            Intent intent = new Intent(getActivity(), LoginAndSecurityActivity.class);
            startActivity(intent);
        });

        btlogout.setOnClickListener(view -> showDialog());
    }

    private void fetchUserName() {
        // Lấy số điện thoại từ SharedPreferences và yêu cầu UserManager cập nhật tên người dùng
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_phone", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        UserManager.getInstance().fetchUserName(phoneNumber);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy đăng ký observer khi Fragment không còn hiển thị
        UserManager.getInstance().removeObserver( this);
    }

    @Override
    public void onUserDataChanged(String hoTen) {
        // Khi dữ liệu người dùng thay đổi, cập nhật UI trên main thread
        getActivity().runOnUiThread(() -> {
            if (!hoTen.equals("")) {
                nameUser.setText(hoTen);
            }
        });
    }

    private void showDialog() {
        // Hiển thị dialog xác nhận đăng xuất
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất khỏi ứng dụng?");
        builder.setPositiveButton("Có", (dialog, which) -> {
            // Đăng xuất và chuyển về EnterSdtActivity
            Intent intent = new Intent(requireContext(), EnterSdtActivity.class);
            startActivity(intent);
            Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            requireActivity().finishAffinity(); // kết thúc tất cả các activity và xóa khỏi stack
        });
        builder.setNegativeButton("Không", (dialog, which) -> {
            // Hủy dialog, không làm gì
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}