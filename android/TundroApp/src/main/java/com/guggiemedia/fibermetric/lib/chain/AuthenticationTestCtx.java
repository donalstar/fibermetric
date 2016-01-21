package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;



/**
 * authentication test command context, issue 45
 *
 * ensure current user remains legal (can be revoked by server at any time)
 */
public class AuthenticationTestCtx extends AbstractCmdCtx {
    private boolean _happyFlag = false;

    public AuthenticationTestCtx(Context androidContext) {
        super(CommandEnum.AUTHENTICATION_TEST, androidContext);
    }

    public boolean isHappyFlag() {
        return _happyFlag;
    }

    public void setHappyFlag(boolean arg) {
        _happyFlag = arg;
    }
}