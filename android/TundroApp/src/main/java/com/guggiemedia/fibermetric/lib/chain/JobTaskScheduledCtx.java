package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;


/**
 * start a job
 */
public class JobTaskScheduledCtx extends AbstractCmdCtx {
    private long _id;

    public JobTaskScheduledCtx(Context androidContext) {
        super(CommandEnum.JOBTASK_SCHEDULED, androidContext);
    }

    public Long getId() {
        return _id;
    }
    public void setId(Long arg) {
        _id = arg;
    }
}