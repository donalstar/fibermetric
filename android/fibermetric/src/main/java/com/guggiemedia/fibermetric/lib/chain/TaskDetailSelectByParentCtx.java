package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.TaskDetailModelList;


/**
 * select task detail by parent ID command context
 */
public class TaskDetailSelectByParentCtx extends AbstractCmdCtx {
    private long _parentId;
    private TaskDetailModelList _selected = new TaskDetailModelList();

    public TaskDetailSelectByParentCtx(Context androidContext) {
        super(CommandEnum.TASK_DETAIL_SELECT_BY_PARENT, androidContext);
    }

    public Long getParentId() {
        return _parentId;
    }
    public void setParentId(Long arg) {
        _parentId = arg;
    }

    public TaskDetailModelList getSelected() {
        return _selected;
    }
    public void setSelected(TaskDetailModelList arg) {
        _selected = arg;
    }
}