package com.example.do_an.DPattern;

import android.content.Intent;

public abstract class HomeActionsDecorator implements HomeActions {
    protected HomeActions decoratedActions;

    public HomeActionsDecorator(HomeActions decoratedActions) {
        this.decoratedActions = decoratedActions;
    }

    public void navigateToActivity(Intent intent) {
        decoratedActions.navigateToActivity(intent);
    }
}
