package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;


/**
 * logout command context, issue 18
 *
 * logout current user
 */
public class AuthenticationSignOutCtx extends AbstractCmdCtx {

    public AuthenticationSignOutCtx(Context androidContext) {
        super(CommandEnum.AUTHENTICATION_SIGN_OUT, androidContext);
    }
}