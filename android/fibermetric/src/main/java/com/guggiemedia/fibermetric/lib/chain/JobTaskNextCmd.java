package com.guggiemedia.fibermetric.lib.chain;



import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;
import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;
import com.guggiemedia.fibermetric.lib.db.JobTaskTable;

import java.util.Date;

/**
 * return next uncompleted task
 */
public class JobTaskNextCmd extends AbstractCmd {
    public static final String LOG_TAG = JobTaskNextCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final JobTaskNextCtx ctx = (JobTaskNextCtx) context;

        DataBaseFacade dbf = new DataBaseFacade(ctx.getAndroidContext());
        JobTaskModelList modelList = dbf.selectJobTaskByParent(ctx.getJobId());
        for (JobTaskModel model:modelList) {
            if (model.getState() != JobStateEnum.COMPLETE) {
                model.setState(JobStateEnum.ACTIVE);
                model.setUpdateTime(new Date());
                dbf.updateModel(model);

                ctx.setNextTask(model);
                return returnToSender(ctx, ResultEnum.OK);
            }
        }

        // task list exhausted, mark job as complete
        JobTaskModel model = (JobTaskModel) dbf.selectModel(ctx.getJobId(), new JobTaskTable());
        model.setState(JobStateEnum.COMPLETE);
        model.setUpdateTime(new Date());

        dbf.updateModel(model);

        // return default model to signal job complete
        model = new JobTaskModel();
        model.setDefault();

        ctx.setNextTask(model);
        return returnToSender(ctx, ResultEnum.OK);
    }
}