package com.guggiemedia.fibermetric.lib.chain;



import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;
import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;

/**
 * calculate job duration
 */
public class JobTaskDurationCmd extends AbstractCmd {
    public static final String LOG_TAG = JobTaskDurationCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final JobTaskDurationCtx ctx = (JobTaskDurationCtx) context;

        ctx.setCompleteMinutes(0);
        ctx.setTotalMinutes(0);

        DataBaseFacade dbf = new DataBaseFacade(ctx.getAndroidContext());

        JobTaskModelList modelList = dbf.selectJobs(ctx.isTodayOnly());
        for (JobTaskModel model:modelList) {
            if (model.getState() == JobStateEnum.COMPLETE) {
                ctx.setCompleteMinutes(model.getDuration() + ctx.getCompleteMinutes());
            }

            ctx.setTotalMinutes(model.getDuration() + ctx.getTotalMinutes());
        }

        return returnToSender(ctx, ResultEnum.OK);
    }
}