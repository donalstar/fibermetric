package com.guggiemedia.fibermetric.chain;

import com.guggiemedia.fibermetric.db.ContentFacade;
import com.guggiemedia.fibermetric.db.DailyRecordModel;

/**
 * Created by donal on 10/5/15.
 */
public class DailyRecordUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = DailyRecordUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final DailyRecordUpdateCtx ctx = (DailyRecordUpdateCtx) context;
        final DailyRecordModel model = ctx.getModel();

        _contentFacade.updateDailyRecord(model, ctx.getAndroidContext());

        return result;
    }
}
