package com.guggiemedia.fibermetric.chain;

import com.guggiemedia.fibermetric.db.ContentFacade;
import com.guggiemedia.fibermetric.db.HistoryModel;
import com.guggiemedia.fibermetric.db.ItemModel;

/**
 * Created by donal on 10/5/15.
 */
public class HistoryUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = HistoryUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final HistoryUpdateCtx ctx = (HistoryUpdateCtx) context;
        final HistoryModel model = ctx.getModel();

        _contentFacade.updateHistory(model, ctx.getAndroidContext());

        return result;
    }
}
