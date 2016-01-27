package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;


/**
 * service heartbeat from AlarmManager
 */
public class HeartBeatCtx extends AbstractCmdCtx {
    public HeartBeatCtx(Context androidContext) {
        super(CommandEnum.HEART_BEAT, androidContext);
    }
}