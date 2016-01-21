package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.EventModel;
import com.guggiemedia.fibermetric.lib.db.ThingModel;


/**
 * assert custody for a thing
 */
public class CustodyAssertCmd extends AbstractCmd {
    public static final String LOG_TAG = CustodyAssertCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final CustodyAssertCtx ctx = (CustodyAssertCtx) context;

        ThingModel model = ctx.getModel();
        if (model == null) {
            long rowId = ctx.getRowId();
            model = _contentFacade.selectThingByRowId(ctx.getRowId(), ctx.getAndroidContext());
        }

        model.setCustodyFlag(true);
        _contentFacade.updateThing(model, ctx.getAndroidContext());
        updateLog(model.getId(), model.getName(), ctx.getAndroidContext());

        return returnToSender(ctx, ResultEnum.OK);
    }

    private void updateLog(long thingRowId, String thingName, Context context) {
        EventModel model = new EventModel();
        model.setDefault();
        model.setNote("assert custody:" + thingRowId + ":" + thingName);

        _contentFacade.insertEvent(model, context);
    }
}
