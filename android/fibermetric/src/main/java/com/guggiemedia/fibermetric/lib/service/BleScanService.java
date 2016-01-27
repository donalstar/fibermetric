package com.guggiemedia.fibermetric.lib.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.guggiemedia.fibermetric.lib.chain.CommandEnum;
import com.guggiemedia.fibermetric.lib.chain.CommandFactory;
import com.guggiemedia.fibermetric.lib.chain.ContextFactory;
import com.guggiemedia.fibermetric.lib.db.AlertEnum;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.InventoryStatusEnum;
import com.guggiemedia.fibermetric.lib.db.PartModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * monitor BLE beacons which are "in custody" and alarm is any are unheard
 */
public class BleScanService extends IntentService {
    public static final String LOG_TAG = BleScanService.class.getName();

    public static final String ACTION_BLE_SCAN = "ACTION_BLE_SCAN";
    public static final String ARG_PART_ID = "part_id";

    public static final Long SCAN_DURATION = 3333L;
    public static final Long SLEEP_DURATION = SCAN_DURATION + 1234L;
    public static final Long NEXT_ALARM = 33000L;

    private Handler _handler = new Handler();
    private ArrayList<String> _scanList = new ArrayList<>();

    private final ContentFacade _contentFacade = new ContentFacade();

    public static void startActionBleScan(Context context) {
        Intent intent = new Intent(context, BleScanService.class);
        intent.setAction(ACTION_BLE_SCAN);
        context.startService(intent);
    }

    public BleScanService() {
        super("BleScanService");
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


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "onHandleIntent");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_BLE_SCAN.equals(action)) {
                handleActionBleScan();
            }
        }

        setNextAlarm();
    }

    private void handleActionBleScan() {
        Log.d(LOG_TAG, "BLE scan");

        List<PartModel> partModels = _contentFacade.selectPartsWithBleBeacons(this);

        if (partModels.isEmpty()) {
            Log.d(LOG_TAG, "empty parts list skips BLE collection");
            return;
        }


        BluetoothManager manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothAdapter btAdapter = manager.getAdapter();

        if (btAdapter == null || !btAdapter.isEnabled()) {

        } else {


            Date scanStartTime = new Date();

            btAdapter.startLeScan(_scanCallback);

            _handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (btAdapter != null) {
                        Log.i(LOG_TAG, "Stop BLE scan");
                        btAdapter.stopLeScan(_scanCallback);
                    }
                }
            }, SCAN_DURATION);

            try {
                //wait for scan to complete
                Thread.sleep(SLEEP_DURATION);
            } catch (InterruptedException exception) {
                // empty
            }

            for (PartModel partModel : partModels) {
                // update part status

                if (partModel.getStatus().equals(InventoryStatusEnum.inCustody)) {
                    int bleInRange = (_scanList.contains(partModel.getBleAddress())) ? 1 : 0;

                    if (bleInRange != partModel.getBleInRange()) {
                        partModel.setBleInRange(bleInRange);

                        _contentFacade.updatePart(partModel, this);

                        displayNotification(partModel, scanStartTime);
                    }
                }
            }
        }
    }

    private void displayNotification(PartModel partModel, Date scanStartTime) {
        Intent i = new Intent(ACTION_BLE_SCAN);

        i.putExtra(ARG_PART_ID, partModel.getId());
        i.putExtra("scan_time", scanStartTime.getTime());

        sendBroadcast(i);
    }

    private BluetoothAdapter.LeScanCallback _scanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            if (!_scanList.contains(device.getAddress())) {
                _scanList.add(device.getAddress());
            }
        }
    };


    private void setNextAlarm() {
        Date date = new Date();
        long nextAlarm = date.getTime() + NEXT_ALARM;

        Intent intent = new Intent(this, BleScanService.class);
        intent.setAction(ACTION_BLE_SCAN);
        PendingIntent pi = PendingIntent.getService(this, 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC, nextAlarm, pi);
    }

}
