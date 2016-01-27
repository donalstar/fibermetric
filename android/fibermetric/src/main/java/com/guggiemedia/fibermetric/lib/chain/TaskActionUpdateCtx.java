package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.TaskActionStateEnum;


/**
 * update task action context
 */
public class TaskActionUpdateCtx extends AbstractCmdCtx {
    private long _actionId;
    private TaskActionStateEnum _state = TaskActionStateEnum.UNKNOWN;

    public TaskActionUpdateCtx(Context androidContext) {
        super(CommandEnum.TASK_ACTION_UPDATE, androidContext);
    }

    public Long getActionId() {
        return _actionId;
    }
    public void setActionId(Long arg) {
        _actionId = arg;
    }

    public TaskActionStateEnum getState() {
        return _state;
    }
    public void setState(TaskActionStateEnum arg) {
        _state = arg;
    }
}