package com.guggiemedia.fibermetric.lib;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.guggiemedia.fibermetric.lib.utility.AlarmManagerHelper;
import com.guggiemedia.fibermetric.lib.utility.FileHelper;
import com.guggiemedia.fibermetric.lib.utility.UserPreferenceHelper;

/**
 *
 */
public class FibermetricApplication extends Application {
    public static final String LOG_TAG = FibermetricApplication.class.getName();


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {

        UserPreferenceHelper uph = new UserPreferenceHelper();
        if (uph.isEmptyPreferences(this)) {
            uph.writeDefaults(this);

            AlarmManagerHelper helper = new AlarmManagerHelper();
            helper.startHeartBeat(this);
        }


        setupDemoDataBase();

        FileHelper.writeNoMedia(this);
    }

    private void setupDemoDataBase() {
        DataBaseScenario scenario = new DataBaseScenario();

        scenario.loadParts(this);
    }
}