package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.AlertEnum;


/**
 * create or update alert
 */
public class AlertBumpCounterCtx extends AbstractCmdCtx {
    private long _thingRowId;
    private String _thingName = "Unknown";
    private AlertEnum _alertType;

    public AlertBumpCounterCtx(Context androidContext) {
        super(CommandEnum.ALERT_BUMP_COUNTER, androidContext);
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

    public String getThingName() {
        return _thingName;
    }

    public void setThingName(String arg) {
        _thingName = arg;
    }
}