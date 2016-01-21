package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.KeyList;


/**
 * select tasks by job ID command context
 */
public class JobTaskSelectByJobIdCtx extends AbstractCmdCtx {
    private long _rowId;
    private KeyList _selected = new KeyList();

    public JobTaskSelectByJobIdCtx(Context androidContext) {
        super(CommandEnum.JOBTASK_SELECT_BY_JOB_ID, androidContext);
    }

    public Long getJobId() {
        return _rowId;
    }

    public void setJobId(Long arg) {
        _rowId = arg;
    }

    public KeyList getSelected() {
        return _selected;
    }

    public void setSelected(KeyList arg) {
        _selected = arg;
    }
}
