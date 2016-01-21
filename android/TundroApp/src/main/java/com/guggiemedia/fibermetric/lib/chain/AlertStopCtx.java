package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.AlertEnum;


/**
 * stop alert
 */
public class AlertStopCtx extends AbstractCmdCtx {
    private long _thingRowId;
    private AlertEnum _alertType;

    public AlertStopCtx(Context androidContext) {
        super(CommandEnum.ALERT_STOP, androidContext);
    }

    public AlertEnum getAlertType() {
        return _alertType;
    }

    public void setAlertType(AlertEnum arg) {
        _alertType = arg;
    }

    public Long getThingRowId() {
        return _thingRowId;
    }

    public void setThingRowId(long arg) {
        _thingRowId = arg;
    }
}