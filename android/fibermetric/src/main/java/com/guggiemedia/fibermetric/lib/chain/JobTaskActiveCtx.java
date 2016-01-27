package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;


/**
 * start a job
 */
public class JobTaskActiveCtx extends AbstractCmdCtx {
    private long _id;

    public JobTaskActiveCtx(Context androidContext) {
        super(CommandEnum.JOBTASK_ACTIVE, androidContext);
    }

    public Long getId() {
        return _id;
    }
    public void setId(Long arg) {
        _id = arg;
    }
}