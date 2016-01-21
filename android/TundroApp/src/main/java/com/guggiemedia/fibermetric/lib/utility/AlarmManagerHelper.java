package com.guggiemedia.fibermetric.lib.utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.guggiemedia.fibermetric.lib.service.HeartBeatReceiver;


/**
 *
 */
public class AlarmManagerHelper {

    public PendingIntent startHeartBeat(Context context) {
        Intent intent = new Intent(context, HeartBeatReceiver.class);
        intent.setAction(HeartBeatReceiver.HEART_BEAT_ACTION);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 61 * 1000, pendingIntent);

        return pendingIntent;
    }

    public void stopAlarm(PendingIntent pendingIntent, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}