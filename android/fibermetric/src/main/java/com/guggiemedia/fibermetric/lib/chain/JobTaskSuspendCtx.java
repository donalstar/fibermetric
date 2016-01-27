package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;


/**
 * suspend a job
 */
public class JobTaskSuspendCtx extends AbstractCmdCtx {
    private long _id;

    public JobTaskSuspendCtx(Context androidContext) {
        super(CommandEnum.JOBTASK_SUSPEND, androidContext);
    }

    public Long getId() {
        return _id;
    }
    public void setId(Long arg) {
        _id = arg;
    }
}