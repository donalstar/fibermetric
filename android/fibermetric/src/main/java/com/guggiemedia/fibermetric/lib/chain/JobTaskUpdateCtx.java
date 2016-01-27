package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.TaskActionModelList;
import com.guggiemedia.fibermetric.lib.db.TaskDetailModelList;


/**
 * insert/update jobtask command context
 */
public class JobTaskUpdateCtx extends AbstractCmdCtx {
    private JobTaskModel _jobTask = new JobTaskModel();
    private TaskActionModelList _actionList = new TaskActionModelList();
    private TaskDetailModelList _detailList = new TaskDetailModelList();

    public JobTaskUpdateCtx(Context androidContext) {
        super(CommandEnum.JOBTASK_UPDATE, androidContext);
    }

    public JobTaskModel getJobTask() {
        return _jobTask;
    }
    public void setJobTask(JobTaskModel arg) {
        _jobTask = arg;
    }

    public TaskActionModelList getActionList() {
        return _actionList;
    }
    public void setActionList(TaskActionModelList arg) {
        _actionList = arg;
    }

    public TaskDetailModelList getDetailList() {
        return _detailList;
    }
    public void setDetailList(TaskDetailModelList arg) {
        _detailList = arg;
    }
}