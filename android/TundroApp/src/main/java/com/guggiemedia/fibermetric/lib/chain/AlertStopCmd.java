package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import android.content.Intent;


import com.guggiemedia.fibermetric.lib.Constant;
import com.guggiemedia.fibermetric.lib.db.AlertEnum;
import com.guggiemedia.fibermetric.lib.db.AlertModel;
import com.guggiemedia.fibermetric.lib.db.AlertModelList;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.EventModel;
import com.guggiemedia.fibermetric.lib.db.ThingModel;


import java.util.Date;

/**
 * stop alert
 */
public class AlertStopCmd extends AbstractCmd {
    public static final String LOG_TAG = AlertStopCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final AlertStopCtx ctx = (AlertStopCtx) context;

        AlertModel alertModel = _contentFacade.selectActiveAlert(ctx.getAlertType(), ctx.getThingRowId(), ctx.getAndroidContext());
        if (alertModel != null) {
            if (alertModel.getThingId() > 0L) {
                stopThing(alertModel.getThingId(), ctx.getAndroidContext());
            }

            stopAlert(alertModel, ctx.getAndroidContext());
        }

        missingBleHandler(ctx.getAndroidContext());

        return returnToSender(ctx, ResultEnum.OK);
    }

    private void stopAlert(AlertModel model, Context context) {
        model.setActiveFlag(false);
        model.setEndTime(new Date());

        _contentFacade.updateAlert(model, context);

        EventModel eventModel = new EventModel();
        eventModel.setDefault();
        eventModel.setNote("stop alert:" + model.getId());

        _contentFacade.insertEvent(eventModel, context);
    }

    private void stopThing(long thingRowId, Context context) {
        ThingModel model = _contentFacade.selectThingByRowId(thingRowId, context);
        model.setAlertFlag(false);
        _contentFacade.updateThing(model, context);


    }

    private void missingBleHandler(Context context) {
        AlertModelList modelList = _contentFacade.selectActiveAlertByType(AlertEnum.BLE_MISSING, context);
        if (modelList.size() < 1) {
            context.sendBroadcast(new Intent(Constant.HAPPY_BEACON));
        }
    }
}
