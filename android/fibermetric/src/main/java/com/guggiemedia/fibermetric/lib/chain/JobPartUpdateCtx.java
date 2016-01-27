package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.JobPartModel;


/**
 * Created by donal on 9/29/15.
 */
public class JobPartUpdateCtx extends AbstractCmdCtx {
    private JobPartModel _model = new JobPartModel();

    public JobPartUpdateCtx(Context androidContext) {
        super(CommandEnum.JOB_PART_UPDATE, androidContext);
    }

    public JobPartModel getModel() {
        return _model;
    }

    public void setModel(JobPartModel arg) {
        _model = arg;
    }
}
