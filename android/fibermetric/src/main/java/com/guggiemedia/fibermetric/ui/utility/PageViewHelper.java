package com.guggiemedia.fibermetric.ui.utility;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.utility.AnalyticHelper;
import com.guggiemedia.fibermetric.ui.login.LoginActivity;


/**
 *
 */
public class PageViewHelper {
    public static final String LOG_TAG = PageViewHelper.class.getName();

    public static Boolean activityTransition(String activityTag, Context context) {
        if (LoginActivity.ACTIVITY_TAG.equals(activityTag)) {
 //           AudioFacade.playTransitionCue(context);

            AnalyticHelper analyticHelper = AnalyticHelper.getInstance(context);
            analyticHelper.fragmentViewLog(activityTag);

            return true;
        }

        if (CommandFacade.authenticationTest(context)) {
  //          AudioFacade.playTransitionCue(context);

            AnalyticHelper analyticHelper = AnalyticHelper.getInstance(context);
            analyticHelper.fragmentViewLog(activityTag);

            return true;
        }

        return false;
    }

    public static Boolean fragmentTransition(String fragmentTag, Context context) {
        if (CommandFacade.authenticationTest(context)) {
    //        AudioFacade.playTransitionCue(context);

            AnalyticHelper analyticHelper = AnalyticHelper.getInstance(context);
            analyticHelper.fragmentViewLog(fragmentTag);

            return true;
        }

        return false;
    }
}
