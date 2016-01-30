package com.guggiemedia.fibermetric.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;

/**
 * SharedPreference wrapper.
 */
public class UserPreferenceHelper {
    public static final String CHROME_CAST = "chromeCast";
    public static final String DISPLAY_TRANSITION_AUDIO_CUE = "displayTransitionCue";

    public static final String GOOGLE_ANALYTICS = "googleAnalytics";
    public static final String LEARNED_NAV_DRAWER = "learnedNavDrawer";

    public static final String USER = "user";


    public static final Long NO_CURRENT_USER = -1L;


    /**
     * @param context
     */
    public void writeDefaults(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean(CHROME_CAST, false);
        editor.putBoolean(DISPLAY_TRANSITION_AUDIO_CUE, true);
        editor.putBoolean(GOOGLE_ANALYTICS, true);
        editor.putBoolean(LEARNED_NAV_DRAWER, false);

        editor.putLong(USER, NO_CURRENT_USER);


        editor.commit();
    }

    /**
     * Could only be true on a fresh install
     *
     * @param context
     * @return true if user preferences are empty
     */
    public boolean isEmptyPreferences(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, ?> map = sp.getAll();
        return map.isEmpty();
    }


    /**
     * Update user preferences file
     *
     * @param context
     * @param key
     * @param flag
     */
    private void setBoolean(Context context, String key, boolean flag) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, flag);
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param flag
     */
    private void setLong(Context context, String key, long arg) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, arg);
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param arg
     */
    private void setString(Context context, String key, String arg) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, arg);
        editor.commit();
    }

    /**
     *
     * @param context
     * @param key
     * @param arg
     */
    private void setFloat(Context context, String key, float arg) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, arg);
        editor.commit();
    }
}
