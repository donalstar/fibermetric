package com.guggiemedia.fibermetric.lib.chain;



import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;
import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskTable;

import java.util.Date;

/**
 * suspend a job
 */
public class JobTaskSuspendCmd extends AbstractCmd {
    public static final String LOG_TAG = JobTaskSuspendCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final JobTaskSuspendCtx ctx = (JobTaskSuspendCtx) context;

        final DataBaseFacade dbf = new DataBaseFacade(ctx.getAndroidContext());

        JobTaskModel model = (JobTaskModel) dbf.selectModel(ctx.getId(), new JobTaskTable());
        model.setState(JobStateEnum.SUSPEND);
        model.setUpdateTime(new Date());

        dbf.updateModel(model);

        return returnToSender(ctx, ResultEnum.OK);
    }
}