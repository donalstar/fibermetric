package com.guggiemedia.fibermetric.lib.chain;

import android.util.Log;



import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;
import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;
import com.guggiemedia.fibermetric.lib.db.JobTaskTable;

import java.util.Date;

/**
 * complete a task
 */
public class JobTaskCompleteCmd extends AbstractCmd {
    public static final String LOG_TAG = JobTaskCompleteCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final JobTaskCompleteCtx ctx = (JobTaskCompleteCtx) context;

        final DataBaseFacade dbf = new DataBaseFacade(ctx.getAndroidContext());

        JobTaskModel model = (JobTaskModel) dbf.selectModel(ctx.getId(), new JobTaskTable());
        model.setState(JobStateEnum.COMPLETE);
        model.setUpdateTime(new Date());

        dbf.updateModel(model);

        if (!model.isJob()) {
            taskStunt(model.getParent(), dbf);
        }

        return returnToSender(ctx, ResultEnum.OK);
    }

    /**
     * if a task was just completed, identify the next task (if any) and mark it "active"
     * this is purely to make the icons change
     * @param model
     */
    private void taskStunt(long parentId, DataBaseFacade dbf) {
        JobTaskModelList modelList = dbf.selectJobTaskByParent(parentId);
        for (JobTaskModel model:modelList) {
            if (model.getState() != JobStateEnum.COMPLETE) {
                model.setState(JobStateEnum.ACTIVE);
                Log.d(LOG_TAG, "xoxoxoxoxoxoxoxoxoxoxxoxoxoxo" + parentId + ":" + model.getId());
                model.setUpdateTime(new Date());
                dbf.updateModel(model);
                return;
            }
        }
    }
}