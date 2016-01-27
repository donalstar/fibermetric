package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.JobTaskModel;

/**
 * return next uncompleted task
 */
public class JobTaskNextCtx extends AbstractCmdCtx {
    private long _jobId;
    private JobTaskModel _nextTask;

    public JobTaskNextCtx(Context androidContext) {
        super(CommandEnum.JOBTASK_NEXT, androidContext);
    }

    public Long getJobId() {
        return _jobId;
    }
    public void setJobId(Long arg) {
        _jobId = arg;
    }

    public JobTaskModel getNextTask() {
        return _nextTask;
    }
    public void setNextTask(JobTaskModel arg) {
        _nextTask = arg;
    }
}