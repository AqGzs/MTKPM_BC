package com.example.do_an.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.do_an.DPattern.TransactionRepository;
import com.example.do_an.R;
import com.example.do_an.adapter.TransHisAdapter;
import com.example.do_an.model.NotificationModel;
import com.example.do_an.ui.StatisticalActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransHisFragment extends Fragment {
    private static final String TAG = "TransactionHistoryFragment";
    private RecyclerView recyclerView;
    private List<NotificationModel> transactionList;
    private TextView report;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trans_his, container, false);

        report = view.findViewById(R.id.report);
        recyclerView = view.findViewById(R.id.recycle_transhis);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        transactionList = new ArrayList<>();

        fetchDataFromRepository();

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StatisticalActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void fetchDataFromRepository() {
        TransactionRepository transactionRepository = new TransactionRepository();
        transactionRepository.getTransactions(transactions -> {
            transactionList.clear();
            transactionList.addAll(transactions);

            Collections.sort(transactionList, (o1, o2) -> {
                int dateComparison = o2.getDate().compareTo(o1.getDate());
                if (dateComparison == 0) {
                    return o2.getHour().compareTo(o1.getHour());
                }
                return dateComparison;
            });

            TransHisAdapter adapter = new TransHisAdapter(transactionList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
    }
}
