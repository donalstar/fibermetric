package com.guggiemedia.fibermetric.lib.chain;

import android.util.Log;

import com.guggiemedia.fibermetric.lib.Personality;
import com.guggiemedia.fibermetric.lib.db.PersonModel;
import com.guggiemedia.fibermetric.lib.utility.UserPreferenceHelper;

/**
 * authentication test command
 */
public class AuthenticationTestCmd extends AbstractCmd {
    public static final String LOG_TAG = AuthenticationTestCmd.class.getName();

    private final AuthenticationHelper _helper = new AuthenticationHelper();

    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final AuthenticationTestCtx ctx = (AuthenticationTestCtx) context;

        long personRowId = _helper.getCurrentUser(ctx.getAndroidContext());
        if (personRowId == UserPreferenceHelper.NO_CURRENT_USER) {
            ctx.setHappyFlag(false);
            Log.i(LOG_TAG, "auth test w/no active user");
        } else {
            PersonModel model = _helper.selectUser(personRowId, ctx.getAndroidContext());
            if (model.getId() > 0) {
                ctx.setHappyFlag(true);

                if (Personality.personSelf == null) {
                    // personSelf can be null if application is just starting up, but using previous sign in
                    Personality.personSelf = model;
                }
            }
        }

        ctx.writeEvent("authtest");

        return returnToSender(ctx, ResultEnum.OK);
    }
}
