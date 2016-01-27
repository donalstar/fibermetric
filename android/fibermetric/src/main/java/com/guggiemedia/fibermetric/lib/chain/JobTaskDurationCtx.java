package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

/**
 * calculate job duration
 */
public class JobTaskDurationCtx extends AbstractCmdCtx {
    private boolean _todayOnly = false;
    private int _completeMinutes;
    private int _totalMinutes;

    public JobTaskDurationCtx(Context androidContext) {
        super(CommandEnum.JOBTASK_DURATION, androidContext);
    }

    public boolean isTodayOnly() {
        return _todayOnly;
    }

    public void setTodayOnly(boolean todayOnly) {
        _todayOnly = todayOnly;
    }

    public int getCompleteMinutes() {
        return _completeMinutes;
    }

    public void setCompleteMinutes(int minutes) {
        _completeMinutes = minutes;
    }

    public int getTotalMinutes() {
        return _totalMinutes;
    }

    public void setTotalMinutes(int minutes) {
        _totalMinutes = minutes;
    }
}