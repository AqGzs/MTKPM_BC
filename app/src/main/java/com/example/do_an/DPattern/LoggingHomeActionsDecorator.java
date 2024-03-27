package com.example.do_an.DPattern;

import android.content.Intent;
import android.util.Log;

public class LoggingHomeActionsDecorator extends HomeActionsDecorator {

    public LoggingHomeActionsDecorator(HomeActions decoratedActions) {
        super(decoratedActions);
    }

    @Override
    public void navigateToActivity(Intent intent) {
        logAction(intent);
        super.navigateToActivity(intent);
    }

    private void logAction(Intent intent) {
        Log.d("HomeActionsDecorator", "Navigating to " + intent.getComponent().getClassName());
    }
}

