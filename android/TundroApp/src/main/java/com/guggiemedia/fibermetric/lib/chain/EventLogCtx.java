package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;


import java.util.Date;

/**
 * event log
 */
public class EventLogCtx extends AbstractCmdCtx {
    private final Date _date = new Date();
    private String _event = "Unknown";

    public EventLogCtx(Context androidContext) {
        super(CommandEnum.EVENT_LOG, androidContext);
    }

    public Date getTimeStamp() {
        return _date;
    }

    public String getEvent() {
        return _event;
    }

    public void setEvent(String arg) {
        _event = arg;
    }
}
