package com.example.do_an.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.R;
import com.example.do_an.model.NotificationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<NotificationModel> notificationList;

    public NotificationAdapter(List<NotificationModel> notificationList) {
        this.notificationList = notificationList;
    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_thongbao, parent, false);
        return new NotificationViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel item = notificationList.get(position);
        holder.bind(item);
    }
    @Override
    public int getItemCount() {
        return notificationList.size();
    }
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView titleNotification, transactionAmount, transactionDate, transactionTime;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleNotification = itemView.findViewById(R.id.title_thongbao);
            transactionAmount = itemView.findViewById(R.id.sotiengiaodich);
            transactionDate = itemView.findViewById(R.id.ngaygiaodich);
            transactionTime = itemView.findViewById(R.id.giogiaodich);
        }

        public void bind(NotificationModel item){
            titleNotification.setText(item.getTitle() + " successful");
            transactionAmount.setText(item.getPrice());
            transactionDate.setText(item.getDate());
            transactionTime.setText(item.getHour());
        }
    }
}
