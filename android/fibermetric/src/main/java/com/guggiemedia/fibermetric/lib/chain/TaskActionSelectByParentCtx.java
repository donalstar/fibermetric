package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.TaskActionModelList;


/**
 * select task action by parent ID command context
 */
public class TaskActionSelectByParentCtx extends AbstractCmdCtx {
    private long _parentId;
    private TaskActionModelList _selected = new TaskActionModelList();

    public TaskActionSelectByParentCtx(Context androidContext) {
        super(CommandEnum.TASK_ACTION_SELECT_BY_PARENT, androidContext);
    }

    public Long getParentId() {
        return _parentId;
    }
    public void setParentId(Long arg) {
        _parentId = arg;
    }

    public TaskActionModelList getSelected() {
        return _selected;
    }
    public void setSelected(TaskActionModelList arg) {
        _selected = arg;
    }
}