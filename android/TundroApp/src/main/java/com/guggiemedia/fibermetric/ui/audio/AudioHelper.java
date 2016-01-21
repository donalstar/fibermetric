package com.guggiemedia.fibermetric.ui.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import net.go_factory.tundro.R;


public class AudioHelper implements AudioManager.OnAudioFocusChangeListener {
    public static final String LOG_TAG = AudioHelper.class.getName();

    // OnAudioFocusChangeListener
    public void onAudioFocusChange(int focusChange) {
        Log.d(LOG_TAG, "onAudioFocusChange:" + focusChange);

        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                Log.d(LOG_TAG, "audiofocus gain");
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.d(LOG_TAG, "audiofocus loss");
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.d(LOG_TAG, "audiofocus loss transient");
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                Log.d(LOG_TAG, "audiofocus loss transient can duck");
                break;
            default:
                Log.d(LOG_TAG, "unknown focus change");
                break;
        }
    }

    public void playStreak(Context context) {
        playSound(context, R.raw.streak);
    }

    public void playBeep(Context context) {
        playSound(context, R.raw.beep);
    }


    private void playSound(Context context, int soundResourceId) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        int result = am.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        switch (result) {
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                Log.d(LOG_TAG, "audiofocus request failure");
                return;
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                Log.d(LOG_TAG, "audiofocus request granted");
                break;
            default:
                Log.d(LOG_TAG, "unknown focus request response:" + result);
                break;
        }

        int volumeNdx = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, volumeNdx, 0);

        AudioPlayerListener apl = new AudioPlayerListener();
        MediaPlayer mp = MediaPlayer.create(context, soundResourceId);
        mp.setOnCompletionListener(apl);
        mp.setOnErrorListener(apl);
        mp.setOnInfoListener(apl);
//    mp.setVolume(0.7f, 0.7f);
        mp.setLooping(false);
        mp.start();

        am.abandonAudioFocus(this);
    }

    private class AudioPlayerListener implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener {
        public void onCompletion(MediaPlayer mp) {
            Log.d(LOG_TAG, "onCompletion");
            mp.release();
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            Log.d(LOG_TAG, "onError");

            switch (what) {
                case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                    Log.d(LOG_TAG, "media error not valid");
                    break;
                case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                    Log.d(LOG_TAG, "media error server died");
                    break;
                case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                    Log.d(LOG_TAG, "media error unknown");
                    break;
                default:
                    Log.d(LOG_TAG, "media error default");
            }

            return (false);
        }

        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_UNKNOWN:
                    Log.d(LOG_TAG, "media info unknown");
                    break;
                case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                    Log.d(LOG_TAG, "media info video track lag");
                    break;
                case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                    Log.d(LOG_TAG, "media info bad interleave");
                    break;
                case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                    Log.d(LOG_TAG, "media info metadata update");
                    break;
                case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                    Log.d(LOG_TAG, "media info not seekable");
                    break;
                default:
                    Log.d(LOG_TAG, "media info default");
            }

            return (false);
        }
    }
}
