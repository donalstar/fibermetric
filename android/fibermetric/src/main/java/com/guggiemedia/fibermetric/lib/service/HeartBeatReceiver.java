package com.guggiemedia.fibermetric.lib.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class HeartBeatReceiver extends BroadcastReceiver {

    public static final String LOG_TAG = HeartBeatReceiver.class.getName();

    public static final String HEART_BEAT_ACTION = "HEART_BEAT_ACTION";

    public HeartBeatReceiver() {
        //empty
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.d(LOG_TAG, "HeartBeatReceiver:null intent");
        } else {
            Log.d(LOG_TAG, "HeartBeatReceiver:" + intent.getAction());

            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                com.guggiemedia.fibermetric.lib.utility.AlarmManagerHelper helper = new com.guggiemedia.fibermetric.lib.utility.AlarmManagerHelper();
                helper.startHeartBeat(context);
            } else if (intent.getAction().equals(HEART_BEAT_ACTION)) {
                com.guggiemedia.fibermetric.lib.chain.CommandFactory.execute((com.guggiemedia.fibermetric.lib.chain.HeartBeatCtx) com.guggiemedia.fibermetric.lib.chain.ContextFactory.factory(com.guggiemedia.fibermetric.lib.chain.CommandEnum.HEART_BEAT, context));
            } else {
                Log.i(LOG_TAG, "unknown action:" + intent.getAction());
            }
        }
    }
}
