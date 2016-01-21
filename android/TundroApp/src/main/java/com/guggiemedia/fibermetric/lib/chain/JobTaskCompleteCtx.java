package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;



/**
 * complete a task
 */
public class JobTaskCompleteCtx extends AbstractCmdCtx {
    private long _id;

    public JobTaskCompleteCtx(Context androidContext) {
        super(CommandEnum.JOBTASK_COMPLETE, androidContext);
    }

    public Long getId() {
        return _id;
    }
    public void setId(Long arg) {
        _id = arg;
    }
}