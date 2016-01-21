package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;
import android.content.Intent;

import com.guggiemedia.fibermetric.lib.Constant;
import com.guggiemedia.fibermetric.lib.Personality;
import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;
import com.guggiemedia.fibermetric.lib.db.PersonModel;
import com.guggiemedia.fibermetric.lib.db.PersonTable;
import com.guggiemedia.fibermetric.lib.utility.UserPreferenceHelper;


/**
 * Utility methods supporting Authentication
 */
public class AuthenticationHelper {
    private final UserPreferenceHelper _uph = new UserPreferenceHelper();

    public long getCurrentUser(Context context) {
        return  _uph.getCurrentUser(context);
    }

    public PersonModel selectUser(long rowId, Context context) {
        DataBaseFacade dataBaseFacade = new DataBaseFacade(context);
        return (PersonModel) dataBaseFacade.selectModel(rowId, new PersonTable());
    }

    public PersonModel selectUser(String userName, Context context) {
        DataBaseFacade dbf = new DataBaseFacade(context);
        return dbf.selectUser(userName);
    }

    public void signIn(PersonModel model, Context context) {
        _uph.setCurrentUser(context, model.getId());
        Personality.personSelf = model;
    }

    public void signOut(Context context) {
        _uph.setCurrentUser(context, UserPreferenceHelper.NO_CURRENT_USER);
        Personality.personSelf = null;
    }

    public void failureBroadcast(Context context) {
        context.sendBroadcast(new Intent(Constant.AUTHORIZATION_FAIL));
    }
}