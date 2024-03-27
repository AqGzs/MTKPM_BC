package com.example.do_an.DPattern;

import android.content.Intent;

import androidx.fragment.app.Fragment;

public class HomeFragmentActions implements HomeActions {
    private Fragment fragment;

    public HomeFragmentActions(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void navigateToActivity(Intent intent) {
        fragment.startActivity(intent);
    }
}
