package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;


/**
 * select jobs for today
 */
public class JobTaskSelectTodayCtx extends AbstractCmdCtx {
    private JobTaskModelList _selected = new JobTaskModelList();

    public JobTaskSelectTodayCtx(Context androidContext) {
        super(CommandEnum.JOBTASK_SELECT_TODAY, androidContext);
    }

    public JobTaskModelList getSelected() {
        return _selected;
    }
    public void setSelected(JobTaskModelList arg) {
        _selected = arg;
    }
}
