package com.guggiemedia.fibermetric.lib.chain;



/**
 * logout command
 */
public class AuthenticationSignOutCmd extends AbstractCmd {
    public static final String LOG_TAG = AuthenticationSignOutCmd.class.getName();

    private final AuthenticationHelper _helper = new AuthenticationHelper();

    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final AuthenticationSignOutCtx ctx = (AuthenticationSignOutCtx) context;

        _helper.signOut(ctx.getAndroidContext());

        ctx.writeEvent("logout");

        return returnToSender(ctx, ResultEnum.OK);
    }
}
