package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;


/**
 * select the active job (return either zero or one job)
 */
public class JobTaskSelectActiveCtx extends AbstractCmdCtx {
    private JobTaskModelList _selected = new JobTaskModelList();

    public JobTaskSelectActiveCtx(Context androidContext) {
        super(CommandEnum.JOBTASK_SELECT_ACTIVE, androidContext);
    }

    public JobTaskModelList getSelected() {
        return _selected;
    }
    public void setSelected(JobTaskModelList arg) {
        _selected = arg;
    }
}
