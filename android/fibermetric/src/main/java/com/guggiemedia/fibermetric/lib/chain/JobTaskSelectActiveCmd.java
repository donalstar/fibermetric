package com.guggiemedia.fibermetric.lib.chain;

import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;
import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;

/**
 * select the active job (return either zero or one job)
 */
public class JobTaskSelectActiveCmd extends AbstractCmd {
    public static final String LOG_TAG = JobTaskSelectActiveCmd.class.getName();

    private DataBaseFacade _dbf;

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final JobTaskSelectActiveCtx ctx = (JobTaskSelectActiveCtx) context;

        _dbf = new DataBaseFacade(ctx.getAndroidContext());

        JobTaskModelList results = new JobTaskModelList();

        JobTaskModelList candidates = _dbf.selectJobs(false);
        for (JobTaskModel current : candidates) {
            if (current.getState() == JobStateEnum.ACTIVE) {
                results.add(current);
            }
        }

        ctx.setSelected(results);

        return returnToSender(ctx, ResultEnum.OK);
    }
}