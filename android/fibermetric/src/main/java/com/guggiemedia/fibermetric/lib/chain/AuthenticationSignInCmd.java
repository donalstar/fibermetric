package com.guggiemedia.fibermetric.lib.chain;

import android.util.Log;

import com.guggiemedia.fibermetric.lib.db.PersonModel;
import com.guggiemedia.fibermetric.lib.utility.UserPreferenceHelper;


/**
 * login command
 */
public class AuthenticationSignInCmd extends AbstractCmd {
    public static final String LOG_TAG = AuthenticationSignInCmd.class.getName();

    private final AuthenticationHelper _helper = new AuthenticationHelper();

    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final AuthenticationSignInCtx ctx = (AuthenticationSignInCtx) context;

        long personRowId = _helper.getCurrentUser(ctx.getAndroidContext());
        if (personRowId != UserPreferenceHelper.NO_CURRENT_USER) {
            Log.i(LOG_TAG, "sign in w/active user noted");
        }

        String logMessage = "person select failure:" + ctx.getUserName();
        PersonModel model = _helper.selectUser(ctx.getUserName().toLowerCase(), ctx.getAndroidContext());
        if (model.getId() > 0) {
            //TODO password test
            boolean passWordTest = true;
            if (passWordTest) {
                logMessage = "login success for " + ctx.getUserName();
                _helper.signIn(model, ctx.getAndroidContext());
                ctx.setHappyFlag(true);
            } else {
                logMessage = "login failure for " + ctx.getUserName();
                ctx.setHappyFlag(false);
            }
        } else {
            Log.i(LOG_TAG, logMessage);
        }

        ctx.writeEvent(logMessage);

        return returnToSender(ctx, ResultEnum.OK);
    }
}
