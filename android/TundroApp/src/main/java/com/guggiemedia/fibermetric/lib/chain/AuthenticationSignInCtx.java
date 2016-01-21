package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;



/**
 * login command context, issue 44
 *
 * given a user name and password, attempt to authenticate user
 * successful validation returns happy flag true
 */
public class AuthenticationSignInCtx extends AbstractCmdCtx {
    private boolean _happyFlag = false;
    private String _userName;
    private String _passWord;

    public AuthenticationSignInCtx(Context androidContext) {
        super(CommandEnum.AUTHENTICATION_SIGN_IN, androidContext);
    }

    public String getUserName() {
        return _userName;
    }

    public void setUserName(String userName) {
        _userName = userName;
    }

    public String getPassWord() {
        return _passWord;
    }

    public void setPassWord(String passWord) {
        _passWord = passWord;
    }

    public boolean isHappyFlag() {
        return _happyFlag;
    }

    public void setHappyFlag(boolean arg) {
        _happyFlag = arg;
    }
}