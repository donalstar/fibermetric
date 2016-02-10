package com.guggiemedia.fibermetric.chain;

import com.guggiemedia.fibermetric.db.ContentFacade;
import com.guggiemedia.fibermetric.db.DailyRecordModel;

import java.util.List;

/**
 * Created by donal on 10/5/15.
 */
public class DailyRecordSelectCmd extends AbstractCmd {
    public static final String LOG_TAG = DailyRecordSelectCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final DailyRecordSelectCtx ctx = (DailyRecordSelectCtx) context;

        List<DailyRecordModel> models = _contentFacade.selectDailyRecordAll(ctx.getAndroidContext());

        ctx.setHistoryModels(models);

        return returnToSender(ctx, ResultEnum.OK);
    }
}
