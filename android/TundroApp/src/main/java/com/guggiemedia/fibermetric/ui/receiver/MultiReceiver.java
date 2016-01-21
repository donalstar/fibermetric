package com.guggiemedia.fibermetric.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.util.Log;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;

public class MultiReceiver extends BroadcastReceiver {
    public static final String LOG_TAG = MultiReceiver.class.getName();

    //private final NotificationHelper _notificationHelper = new NotificationHelper();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.d(LOG_TAG, "MultiReceiver:null intent");
        } else {
            Log.d(LOG_TAG, "MultiReceiver:" + intent.getAction());

            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                CommandFacade.connectionChange(context);
            }

            /*
            if (intent.getAction().equals(Constant.AUTHORIZATION_FAIL)) {
                // empty
            } else if (intent.getAction().equals(Constant.ENABLE_BT)) {
                _notificationHelper.enableMissingBleRadio(context);
            } else if (intent.getAction().equals(Constant.HAPPY_BEACON)) {
                _notificationHelper.disableMissingBeacon(context);
            } else if (intent.getAction().equals(Constant.MISSING_BEACON)) {
                _notificationHelper.enableMissingBeacon(context);
            }
            */
        }
    }
}