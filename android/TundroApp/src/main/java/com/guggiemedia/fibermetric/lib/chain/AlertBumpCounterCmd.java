package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.guggiemedia.fibermetric.lib.Constant;

import java.util.Date;

/**
 * create or update alert
 */
public class AlertBumpCounterCmd extends AbstractCmd {
    public static final String LOG_TAG = AlertBumpCounterCmd.class.getName();

    private final com.guggiemedia.fibermetric.lib.db.ContentFacade _contentFacade = new com.guggiemedia.fibermetric.lib.db.ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(com.guggiemedia.fibermetric.lib.chain.AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final com.guggiemedia.fibermetric.lib.chain.AlertBumpCounterCtx ctx = (com.guggiemedia.fibermetric.lib.chain.AlertBumpCounterCtx) context;

        com.guggiemedia.fibermetric.lib.db.AlertModel alertModel = _contentFacade.selectActiveAlert(ctx.getAlertType(), ctx.getThingRowId(), ctx.getAndroidContext());
        if (alertModel == null) {
            createFresh(ctx.getAlertType(), ctx.getThingRowId(), ctx.getThingName(), ctx.getAndroidContext());
            broadcastFresh(ctx.getAlertType(), ctx.getAndroidContext());
        } else {
            updateAlert(alertModel, ctx.getAndroidContext());
            broadcastUpdate(ctx.getAlertType(), ctx.getAndroidContext());
        }

        return returnToSender(ctx, com.guggiemedia.fibermetric.lib.chain.ResultEnum.OK);
    }

    private void updateAlert(com.guggiemedia.fibermetric.lib.db.AlertModel model, Context context) {
        Log.d(LOG_TAG, "update existing alert:" + model.getId() + ":" + model.getAlertType());

        model.setCounter(1 + model.getCounter());
        model.setEndTime(new Date());

        _contentFacade.updateAlert(model, context);
    }

    private void createFresh(com.guggiemedia.fibermetric.lib.db.AlertEnum alertType, Long thingRowId, String thingName, Context context) {
        Log.d(LOG_TAG, "create fresh alert:" + alertType + ":" + thingRowId + ":" + thingName);

        com.guggiemedia.fibermetric.lib.db.AlertModel model = new com.guggiemedia.fibermetric.lib.db.AlertModel();
        model.setDefault();
        model.setActiveFlag(true);
        model.setCounter(1L);
        model.setAlertType(alertType);
        model.setThingId(thingRowId);
        model.setThingName(thingName);

        _contentFacade.updateAlert(model, context);

        com.guggiemedia.fibermetric.lib.db.EventModel eventModel = new com.guggiemedia.fibermetric.lib.db.EventModel();
        eventModel.setDefault();
        eventModel.setNote("start alert:" + model.getId() + ":" + thingName);

        _contentFacade.insertEvent(eventModel, context);
    }

    private void broadcastFresh(com.guggiemedia.fibermetric.lib.db.AlertEnum alertType, Context context) {
        Log.d(LOG_TAG, "broadcast alert:" + alertType);

        Intent intent = null;

        switch (alertType) {
            case BLE_DISABLED:
                intent = new Intent(Constant.ENABLE_BT);
                break;
            case BLE_MISSING:
                intent = new Intent(Constant.MISSING_BEACON);
                break;
            default:
                throw new IllegalArgumentException("unsupported alert:" + alertType);
        }

        if (intent != null) {
            context.sendBroadcast(intent);
        }
    }

    private void broadcastUpdate(com.guggiemedia.fibermetric.lib.db.AlertEnum alertType, Context context) {
        Log.d(LOG_TAG, "broadcast alert:" + alertType);

        Intent intent = null;

        switch (alertType) {
            case BLE_DISABLED:
                intent = new Intent(Constant.ENABLE_BT);
                break;
            case BLE_MISSING:
//                intent = new Intent(Constant.MISSING_BEACON);
                break;
            default:
                throw new IllegalArgumentException("unsupported alert:" + alertType);
        }

        if (intent != null) {
            context.sendBroadcast(intent);
        }
    }
}
