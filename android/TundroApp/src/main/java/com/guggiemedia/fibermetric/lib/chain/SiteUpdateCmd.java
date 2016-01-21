package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.EventModel;


/**
 * insert/update a site
 */
public class SiteUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = SiteUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final SiteUpdateCtx ctx = (SiteUpdateCtx) context;

        _contentFacade.updateSite(ctx.getModel(), ctx.getAndroidContext());

        updateLog(ctx.getModel().getId(), ctx.getAndroidContext());

        return returnToSender(ctx, ResultEnum.OK);
    }

    private void updateLog(long rowId, Context context) {
        EventModel model = new EventModel();
        model.setDefault();
        model.setNote("update site:" + rowId);

        _contentFacade.insertEvent(model, context);
    }
}
