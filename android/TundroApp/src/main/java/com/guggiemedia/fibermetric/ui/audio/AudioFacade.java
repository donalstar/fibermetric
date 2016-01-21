package com.guggiemedia.fibermetric.ui.audio;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.utility.UserPreferenceHelper;

/**
 * audio facade
 */
public class AudioFacade {
    public static final String LOG_TAG = AudioFacade.class.getName();

    private static final AudioHelper audioHelper = new AudioHelper();
    private static final UserPreferenceHelper uph = new UserPreferenceHelper();

    /**
     * audio cue for page transitions
     * @param context
     */
    public static void playTransitionCue(Context context) {
        if (uph.isDisplayTransitionAudioCue(context)) {
            audioHelper.playStreak(context);
        }
    }

    public static void playBeep(Context context) {
        if (uph.isDisplayTransitionAudioCue(context)) {
            audioHelper.playBeep(context);
        }
    }
}
