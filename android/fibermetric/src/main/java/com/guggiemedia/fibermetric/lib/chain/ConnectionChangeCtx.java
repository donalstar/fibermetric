package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

/**
 * service connection change
 */
public class ConnectionChangeCtx extends AbstractCmdCtx {
    public ConnectionChangeCtx(Context androidContext) {
        super(CommandEnum.CONNECTION_CHANGE, androidContext);
    }
}