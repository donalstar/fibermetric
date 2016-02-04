package com.guggiemedia.fibermetric.chain;

import com.guggiemedia.fibermetric.db.ContentFacade;
import com.guggiemedia.fibermetric.db.HistoryModel;

import java.util.List;

/**
 * Created by donal on 10/5/15.
 */
public class HistorySelectCmd extends AbstractCmd {
    public static final String LOG_TAG = HistorySelectCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final HistorySelectCtx ctx = (HistorySelectCtx) context;

        List<HistoryModel> models = _contentFacade.selectHistoryAll(ctx.getAndroidContext());

        ctx.setHistoryModels(models);

        return returnToSender(ctx, ResultEnum.OK);
    }
}
