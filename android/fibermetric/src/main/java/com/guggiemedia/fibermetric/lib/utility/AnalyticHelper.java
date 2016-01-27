package com.guggiemedia.fibermetric.lib.utility;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/**
 * Google Analytics wrapper.  Implemented as Singleton.
 */
public class AnalyticHelper {
    public static final String LOG_TAG = AnalyticHelper.class.getName();

    public static final String PROPERTY_ID = "UA-64086500-1";

    private static Tracker _TRACKER;
    private static AnalyticHelper _INSTANCE;
    private static GoogleAnalytics _ANALYTIC;

    private AnalyticHelper(Context context) {
        _ANALYTIC = GoogleAnalytics.getInstance(context);
        _ANALYTIC.setAppOptOut(false);
        _ANALYTIC.setDryRun(false);
        _ANALYTIC.getLogger().setLogLevel(Logger.LogLevel.INFO);

        _ANALYTIC.setLocalDispatchPeriod(1800);
        _ANALYTIC.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);

        _TRACKER = _ANALYTIC.newTracker(PROPERTY_ID);
        _TRACKER.enableExceptionReporting(true);
        _TRACKER.enableAdvertisingIdCollection(false);
        _TRACKER.enableAutoActivityTracking(false);
    }

    public static AnalyticHelper getInstance(Context context) {
        if (_INSTANCE == null) {
            _INSTANCE = new AnalyticHelper(context);
        }

        return _INSTANCE;
    }

    /*
    public void thingInsert(ThingCaptureEnum action) {
        _TRACKER.send(new HitBuilders.EventBuilder("THING_INSERT", action.toString()).build());
    }

    public void thingUpdate(ThingCaptureEnum action) {
        _TRACKER.send(new HitBuilders.EventBuilder("THING_UPDATE", action.toString()).build());
    }
    */

    public void eventLogger(String category, String action) {
        _TRACKER.send(new HitBuilders.EventBuilder(category, action).build());
    }

    public void fragmentViewLog(String fragmentTag) {
        _TRACKER.setScreenName(fragmentTag);
        _TRACKER.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void visitGoFactory() {
        _TRACKER.setScreenName("VISIT_GO_FACTORY");
        _TRACKER.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void sendFeedBack() {
        //TODO
    }
}
