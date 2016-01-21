package com.guggiemedia.fibermetric.lib.service;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class MultiService extends IntentService {
    public static final String LOG_TAG = MultiService.class.getName();
    public static final String ACTION_BLE_SCAN = "ACTION_BLE_SCAN";
    public static final Long SCAN_DURATION = 3333L;

    private Handler _handler = new Handler();

    public static void startActionBleScan(Context context) {
        Intent intent = new Intent(context, MultiService.class);
        intent.setAction(ACTION_BLE_SCAN);
        context.startService(intent);
    }

    public MultiService() {
        super("MultiService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate entry");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy entry");
    }

    private void setNextAlarm() {
        /*
        UserPreferenceHelper uph = new UserPreferenceHelper();
        int delay = uph.getPollInterval(this);
        Log.d(LOG_TAG, "current delay:" + delay);

        Time timeNow = Utility.timeNow();
        Time timeAlarm = new Time();
        timeAlarm.set(timeNow.toMillis(Constant.IGNORE_DST) + (delay * 60 * 1000L));
        Log.d(LOG_TAG, "next alarm:" + timeAlarm);

        Intent ii = new Intent(this, CollectionService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, ii, 0);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC, timeAlarm.toMillis(Constant.IGNORE_DST), pi);
        */
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "onHandleIntent");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_BLE_SCAN.equals(action)) {
                handleActionBleScan();
            }
        }
    }


    private BluetoothAdapter.LeScanCallback _scanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            Log.d(LOG_TAG, "device:" + device.getAddress());
            /*
            final BleObservation observation = new BleObservation(device, rssi);
            if (_listAdapter.isDuplicate(observation)) {
                Log.d(LOG_TAG, "skipping duplicate device:" + device.getName() + ":" + device.getAddress());
                return;
            }

            ThingModelList modelList = selectThingByAddress(device.getAddress());
            */
        }
    };

    private void handleActionBleScan() {
        Log.d(LOG_TAG, "BLE scan");

        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothAdapter btAdapter = manager.getAdapter();

        if (btAdapter == null || !btAdapter.isEnabled()) {
            Log.i(LOG_TAG, "bluetooth disabled");
        } else {
            btAdapter.startLeScan(_scanCallback);

            _handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (btAdapter != null) {
                        btAdapter.stopLeScan(_scanCallback);
                    }
                }
            }, SCAN_DURATION);
        }
    }
}
