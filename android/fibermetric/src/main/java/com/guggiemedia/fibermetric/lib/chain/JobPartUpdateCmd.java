package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.JobPartModel;


/**
 * Created by donal on 10/5/15.
 */
public class JobPartUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = JobPartUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final JobPartUpdateCtx ctx = (JobPartUpdateCtx) context;
        final JobPartModel model = ctx.getModel();

        _contentFacade.updateJobPart(model, ctx.getAndroidContext());

        return result;
    }
}
