package com.guggiemedia.fibermetric.lib;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.PersonModel;
import com.guggiemedia.fibermetric.lib.db.SiteModel;
import com.guggiemedia.fibermetric.lib.service.BleScanService;
import com.guggiemedia.fibermetric.lib.service.LocationLookupService;
import com.guggiemedia.fibermetric.lib.utility.AlarmManagerHelper;
import com.guggiemedia.fibermetric.lib.utility.AnalyticHelper;
import com.guggiemedia.fibermetric.lib.utility.FileHelper;
import com.guggiemedia.fibermetric.lib.utility.PersonaEnum;
import com.guggiemedia.fibermetric.lib.utility.UserPreferenceHelper;

import java.util.List;

/**
 *
 */
public class FibermetricApplication extends Application {
    public static final String LOG_TAG = FibermetricApplication.class.getName();

    private AnalyticHelper _analyticHelper;

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            CommandFacade.batteryReport(level, context);
        }
    };

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        Log.i(LOG_TAG, "xoxoxoxoxoxoxoxoxoxoxoxxoxoxoxoxoxo");
        Log.i(LOG_TAG, "xo start start start start start xo");
        Log.i(LOG_TAG, "xoxoxoxoxoxoxoxoxoxoxoxxoxoxoxoxoxo");


        UserPreferenceHelper uph = new UserPreferenceHelper();
        if (uph.isEmptyPreferences(this)) {
            uph.writeDefaults(this);

            AlarmManagerHelper helper = new AlarmManagerHelper();
            helper.startHeartBeat(this);
        }

        if (uph.isGoogleAnalytics(this)) {
            _analyticHelper = AnalyticHelper.getInstance(this);
            _analyticHelper.eventLogger("APPLICATION", "onCreate");
        }

        CommandFacade.eventLog("application start", this);

        // discover current network connectivity
        CommandFacade.connectionChange(this);

        // register for battery statistics - must register here (will not work from manifest)
        IntentFilter batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, batteryFilter);

        BleScanService.startActionBleScan(this);

        //setupWiFiReceiver();

        uph.setPersona(this, PersonaEnum.DEMO_NO_SERVER);
        Personality.personaEnum = PersonaEnum.DEMO_NO_SERVER;
        if (Personality.personaEnum == PersonaEnum.DEMO_NO_SERVER) {
            setupDemoDataBase();
        }

        FileHelper.writeNoMedia(this);

        Personality.deviceToken = FileHelper.getDeviceToken(this);

        startService(new Intent(this, LocationLookupService.class));

        /*
        Personality2.gleepAndroidFacade = new GleepAndroidFacade();
        Personality2.gleepAndroidFacade.start(this);

        ChatDriver2 chatDriver = new ChatDriver2(this);
        chatDriver.initialize();
        chatDriver.initSession();
        chatDriver.joinChatChannel();
        */
    }

    @Override
    public void onLowMemory() {

        _analyticHelper.eventLogger("APPLICATION", "lowMemory");
    }

    @Override
    public void onTerminate() {
        Log.i(LOG_TAG, "terminate");

        _analyticHelper.eventLogger("APPLICATION", "terminate");
    }

    @Override
    public void onTrimMemory(int arg) {
        Log.i(LOG_TAG, "trim memory:" + arg);

        _analyticHelper.eventLogger("APPLICATION", "trimMemory");
    }

    private void setupDemoDataBase() {
        DataBaseScenario scenario = new DataBaseScenario();

        PersonModel personModel = CommandFacade.personSelectByUserName("allan", this);
        if (personModel.getId() < 1) {
            List<PersonModel> personModels = scenario.loadPerson(this);

            personModel = CommandFacade.personSelectByUserName("allan", this);

            List<SiteModel> sites = scenario.loadSite(this);

            List<JobTaskModel> jobTasks = scenario.loadJobTasks(this, sites);

            List<PartModel> parts = scenario.loadParts(this, sites);

            scenario.loadPartsForJobs(this, jobTasks, parts);

            scenario.loadChainOfCustodyItems(this, parts, sites, personModel);
            scenario.loadPartsForPerson(this, parts, personModel);

            scenario.loadChats(this, personModels);
        }
    }
}