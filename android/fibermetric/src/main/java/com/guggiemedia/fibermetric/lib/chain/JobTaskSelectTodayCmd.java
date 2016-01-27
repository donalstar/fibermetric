package com.guggiemedia.fibermetric.lib.chain;



import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;
import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;

/**
 * select jobs for today
 */
public class JobTaskSelectTodayCmd extends AbstractCmd {
    public static final String LOG_TAG = JobTaskSelectTodayCmd.class.getName();

    private DataBaseFacade _dbf;

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final JobTaskSelectTodayCtx ctx = (JobTaskSelectTodayCtx) context;

        _dbf = new DataBaseFacade(ctx.getAndroidContext());

        JobTaskModelList candidates = _dbf.selectJobs(true);

        // note that results contains "header" rows along w/rows from database
        JobTaskModelList results = new JobTaskModelList();

        results = filterRows(results, candidates, JobStateEnum.ACTIVE);
        results = filterRows(results, candidates, JobStateEnum.SUSPEND);
        results = filterRows(results, candidates, JobStateEnum.SCHEDULE);
        results = filterRows(results, candidates, JobStateEnum.COMPLETE);

        ctx.setSelected(results);

        return returnToSender(ctx, ResultEnum.OK);
    }

    /**
     * JobListFragment needs a magic row between actual jobs as a header
     * @param freshState
     * @return
     */
    private JobTaskModel getStuntRow(JobStateEnum freshState) {
        JobTaskModel model = new JobTaskModel();
        model.setDefault();
        model.setState(freshState);
        model.setStuntFlag(true);
        return model;
    }

    private JobTaskModelList filterRows(JobTaskModelList results, JobTaskModelList candidates, JobStateEnum target) {
        JobTaskModelList temp = new JobTaskModelList();

        for (JobTaskModel current : candidates) {
            if (current.getState() == target) {
                temp.add(current);
            }
        }

        if (temp.size() > 0) {
            results.add(getStuntRow(target));
            results.addAll(temp);
        }

        return results;
    }
}