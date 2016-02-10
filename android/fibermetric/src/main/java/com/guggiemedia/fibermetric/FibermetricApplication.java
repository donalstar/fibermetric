package com.guggiemedia.fibermetric;

import android.app.Application;
import android.util.Log;

import com.guggiemedia.fibermetric.db.DailyRecordModel;
import com.guggiemedia.fibermetric.db.ItemModel;
import com.guggiemedia.fibermetric.utility.UserPreferenceHelper;

import java.util.List;

/**
 *
 */
public class FibermetricApplication extends Application {
    public static final String LOG_TAG = FibermetricApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();

        UserPreferenceHelper uph = new UserPreferenceHelper();
        if (uph.isEmptyPreferences(this)) {
            Log.i(LOG_TAG, "Preferences are empty");
            uph.writeDefaults(this);

            initializeDatabase();
        }
    }

    private void initializeDatabase() {
        DataBaseScenario scenario = new DataBaseScenario();

        List<DailyRecordModel> historyRecords = scenario.loadHistory(this);

        List<ItemModel> itemModels = scenario.loadItems(this);

        scenario.loadAddedItems(this, historyRecords, itemModels);
    }
}