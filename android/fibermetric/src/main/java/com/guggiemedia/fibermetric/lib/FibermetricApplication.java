package com.guggiemedia.fibermetric.lib;

import android.app.Application;
import android.util.Log;

import com.guggiemedia.fibermetric.lib.utility.UserPreferenceHelper;

/**
 *
 */
public class FibermetricApplication extends Application {
    public static final String LOG_TAG = FibermetricApplication.class.getName();

    @Override
    public void onCreate() {

        UserPreferenceHelper uph = new UserPreferenceHelper();
        if (uph.isEmptyPreferences(this)) {
            Log.i(LOG_TAG, "Preferences are empty");
            uph.writeDefaults(this);

            initializeDatabase();
        }
    }

    private void initializeDatabase() {
        DataBaseScenario scenario = new DataBaseScenario();

        scenario.loadItems(this);
    }
}