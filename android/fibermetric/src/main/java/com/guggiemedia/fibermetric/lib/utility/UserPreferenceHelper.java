package com.guggiemedia.fibermetric.lib.utility;

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
    public static final String PERSONA = "persona";
    public static final String USER = "user";
    public static final String INVENTORY_TAB_SELECTION = "inventoryTabSelection";

    public static final Long NO_CURRENT_USER = -1L;
    public static final Long NO_TAB_SELECTION = -1L;

    // set from the Diagnostics Activity
    public static final String TEST_USER_LAT = "testUserLat";
    public static final String TEST_USER_LNG = "testUserLng";

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
        editor.putString(PERSONA, PersonaEnum.UNKNOWN.toString());
        editor.putLong(USER, NO_CURRENT_USER);
        editor.putLong(INVENTORY_TAB_SELECTION, NO_TAB_SELECTION);

        editor.putFloat(TEST_USER_LAT, 0);
        editor.putFloat(TEST_USER_LNG, 0);

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
     * determine if chromecast video enabled
     *
     * @param context
     * @return true, chromecast video is enabled
     */
    public boolean isChromeCast(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(CHROME_CAST, false);
    }

    /**
     * enable/disable chromecast video
     *
     * @param context
     * @param flag true, chromecast video is enabled
     */
    public void setChromeCast(Context context, boolean flag) {
        setBoolean(context, CHROME_CAST, flag);
    }

    /**
     * determine if display transition audio cues are enabled
     *
     * @param context
     * @return true, display transition audio cue is enabled
     */
    public boolean isDisplayTransitionAudioCue(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(DISPLAY_TRANSITION_AUDIO_CUE, true);
    }

    /**
     * enable/disable display transition audio cues
     *
     * @param context
     * @param flag true, display transition audio cue is enabled
     */
    public void setDisplayTransitionAudioCue(Context context, boolean flag) {
        setBoolean(context, DISPLAY_TRANSITION_AUDIO_CUE, flag);
    }

    /**
     * return current user
     *
     * @param context
     * @return row id in Person table of current user
     */
    public long getCurrentUser(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(USER, NO_CURRENT_USER);
    }

    /**
     * define current user
     *
     * @param context
     * @param arg     row id in Person table of current user
     */
    public void setCurrentUser(Context context, long arg) {
        setLong(context, USER, arg);
    }

    /**
     * determine if google analytics enabled
     *
     * @param context
     * @return true, google analytics is enabled
     */
    public boolean isGoogleAnalytics(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(GOOGLE_ANALYTICS, false);
    }

    /**
     * enable/disable google analytics
     *
     * @param context
     * @param flag    true, google analytics is enabled
     */
    public void setGoogleAnalytics(Context context, boolean flag) {
        setBoolean(context, GOOGLE_ANALYTICS, flag);
    }

    /**
     * determine if display transition audio cues are enabled
     *
     * @param context
     * @return true, display transition audio cue is enabled
     */
    public boolean isLearnedNavDrawer(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(LEARNED_NAV_DRAWER, false);
    }

    /**
     * enable/disable display transition audio cues
     *
     * @param context
     * @param flag    true, display transition audio cue is enabled
     */
    public void setLearnedNavDrawer(Context context, boolean flag) {
        setBoolean(context, LEARNED_NAV_DRAWER, flag);
    }

    /**
     * @param context
     * @return
     */
    public PersonaEnum getPersona(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return PersonaEnum.discoverMatchingEnum(sp.getString(PERSONA, "bogus"));
    }

    /**
     * @param context
     * @param arg
     */
    public void setPersona(Context context, PersonaEnum arg) {
        setString(context, PERSONA, arg.toString());
    }

    /**
     * @param context
     * @param inventoryTabSelection
     */
    public void setInventoryTabSelection(Context context, Long inventoryTabSelection) {
        setLong(context, INVENTORY_TAB_SELECTION, inventoryTabSelection);
    }

    /**
     * @param context
     * @return
     */
    public Long getInventoryTabSelection(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(INVENTORY_TAB_SELECTION, NO_TAB_SELECTION);
    }

    /**
     *
     * @param context
     * @return
     */
    public float getTestUserLatitude(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getFloat(TEST_USER_LAT, 0);
    }

    public void setTestUserLatitude(Context context, Float testUserLatitude) {
        setFloat(context, TEST_USER_LAT, testUserLatitude);
    }

    public float getTestUserLongitude(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getFloat(TEST_USER_LNG, 0);
    }

    public void setTestUserLongitude(Context context, Float testUserLongitude) {
        setFloat(context, TEST_USER_LNG, testUserLongitude);
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
