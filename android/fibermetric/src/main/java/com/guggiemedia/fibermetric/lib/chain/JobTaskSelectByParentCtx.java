package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;


/**
 * select jobtask by parent ID command context
 */
public class JobTaskSelectByParentCtx extends AbstractCmdCtx {
    private long _parentId;
    private JobTaskModelList _selected = new JobTaskModelList();

    public JobTaskSelectByParentCtx(Context androidContext) {
        super(CommandEnum.JOBTASK_SELECT_BY_PARENT, androidContext);
    }

    public Long getParentId() {
        return _parentId;
    }
    public void setParentId(Long arg) {
        _parentId = arg;
    }

    public JobTaskModelList getSelected() {
        return _selected;
    }
    public void setSelected(JobTaskModelList arg) {
        _selected = arg;
    }
}