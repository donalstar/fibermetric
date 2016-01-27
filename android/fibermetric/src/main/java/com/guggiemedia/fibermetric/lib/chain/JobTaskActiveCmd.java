package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;
import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskTable;

import java.util.Date;

/**
 * start a job
 */
public class JobTaskActiveCmd extends AbstractCmd {
    public static final String LOG_TAG = JobTaskActiveCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final JobTaskActiveCtx ctx = (JobTaskActiveCtx) context;

        final DataBaseFacade dbf = new DataBaseFacade(ctx.getAndroidContext());

        JobTaskModel parent = (JobTaskModel) dbf.selectModel(ctx.getId(), new JobTaskTable());
        parent.setState(JobStateEnum.ACTIVE);
        parent.setUpdateTime(new Date());

        dbf.updateModel(parent);

        return returnToSender(ctx, ResultEnum.OK);
    }
}