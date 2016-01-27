package com.guggiemedia.fibermetric.ui.utility;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.service.BleScanService;
import com.guggiemedia.fibermetric.ui.main.MainActivity;
import com.guggiemedia.fibermetric.ui.main.MainActivityFragmentEnum;
import com.guggiemedia.fibermetric.ui.main.PartFragment;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by donal on 1/13/16.
 */
public class NotificationManager extends BroadcastReceiver {
    private Context _context;

    public static String PART_BUNDLE = "part_bundle";

    private static int mNotificationId;

    NotificationCompat.Builder mBuilder;
    android.app.NotificationManager mNotifyMgr;

    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("d MMM HH:mm:ss z");

    public NotificationManager(Context context) {
        _context = context;


        mBuilder = new NotificationCompat.Builder(context);

        mNotifyMgr =
                (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Long partId = intent.getLongExtra(BleScanService.ARG_PART_ID, -1L);

        Long scanStartTimeMillis = intent.getLongExtra("scan_time", -1L);

        PartModel model = CommandFacade.partSelectByRowId(partId, _context);

        displayPartRangeNotification(model, new Date(scanStartTimeMillis));
    }

    /**
     *
     */
    protected void displayPartRangeNotification(PartModel partModel, Date scanStartTime) {
        mNotificationId += 1;

        boolean inRange = (partModel.getBleInRange() == 1);

        int messageId = (inRange) ? R.string.part_in_range2 : R.string.part_out_of_range2;

        String message = String.format(
                _context.getResources().getString(messageId),
                partModel.getName());

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        String[] events = new String[6];
        events[0] = partModel.getName();
        events[1] = "MAC Address: " + partModel.getBleAddress();

        String scanDate = DATE_FORMATTER.format(scanStartTime);

        events[2] = "Time: " + scanDate;

        int bigTitleId = (inRange) ? R.string.part_in_range : R.string.part_out_of_range;

        String bigTitle = _context.getResources().getString(bigTitleId);

        inboxStyle.setBigContentTitle(bigTitle);

        for (String event : events) {
            inboxStyle.addLine(event);
        }

        int beaconImageResourceId = (inRange)
                ? R.drawable.ic_beacon_blue : R.drawable.ic_beacon_red;

        Bundle bundle = getPartBundle(partModel);

        displayNotification(beaconImageResourceId, bigTitle, message, message, events, bundle);
    }

    private Bundle getPartBundle(PartModel model) {
        Bundle bundle = new Bundle();

        bundle.putLong(PartFragment.ARG_PARAM_ROW_ID, model.getId());

        bundle.putString(PartFragment.ARG_PARAM_ITEM_NAME, model.getName());

        MainActivityFragmentEnum parent = MainActivityFragmentEnum.TODAYS_INVENTORY_VIEW;

        bundle.putSerializable(PartFragment.ARG_PARAM_PARENT, parent);

        return bundle;
    }

    /**
     * @param contentTitle
     * @param contentText
     * @param ticker
     * @param messageDetails
     */
    public void displayNotification(int iconResourceId, String contentTitle, String contentText,
                                    String ticker, String messageDetails[], Bundle bundle) {

        mNotificationId += 1;

        mBuilder.setContentTitle(contentTitle);
        mBuilder.setContentText(contentText);
        mBuilder.setTicker(ticker);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle(contentTitle);

        for (String detail : messageDetails) {
            inboxStyle.addLine(detail);
        }

        mBuilder.setStyle(inboxStyle);

        mBuilder.setSmallIcon(iconResourceId);

        Intent intent = new Intent(_context, MainActivity.class);

        intent.putExtra(PART_BUNDLE, bundle);

        intent.setAction(Long.toString(System.currentTimeMillis()));
        PendingIntent pendingIntent = PendingIntent.getActivity(_context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);

        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}

