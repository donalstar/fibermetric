package com.guggiemedia.fibermetric.lib.chain;

import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.EventModel;

/**
 * event log command
 */
public class EventLogCmd extends AbstractCmd {
    public static final String LOG_TAG = EventLogCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final EventLogCtx ctx = (EventLogCtx) context;

        EventModel model = new EventModel();
        model.setDefault();
        model.setTimeStamp(ctx.getTimeStamp());
        model.setNote(ctx.getEvent());

        _contentFacade.insertEvent(model, ctx.getAndroidContext());

        return returnToSender(ctx, ResultEnum.OK);
    }
}
