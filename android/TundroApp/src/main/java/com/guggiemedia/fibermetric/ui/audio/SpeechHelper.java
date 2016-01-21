package com.guggiemedia.fibermetric.ui.audio;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * demonstrate text to speech
 */
public class SpeechHelper implements TextToSpeech.OnInitListener {
  public static final String LOG_TAG = SpeechHelper.class.getName();

  private String textArg;
  private TextToSpeech tts;


  /**
   *
   * @param context
   */
  public SpeechHelper(Context context, String arg) {
    textArg = arg;
    tts = new TextToSpeech(context, this);
  }

  //OnInitListener
  public void onInit(int status) {
    Log.d(LOG_TAG, "onInit:" + status);

    if (status == TextToSpeech.SUCCESS) {
      int result = tts.setLanguage(Locale.US);
      if ((result == TextToSpeech.LANG_MISSING_DATA) || (result == TextToSpeech.LANG_NOT_SUPPORTED)) {
        Log.d(LOG_TAG, "unsupported language");
      } else {
        tts.speak(textArg,  TextToSpeech.QUEUE_FLUSH, null);
      }
    }
  }

  protected void finalize() throws Throwable {
    if (tts != null) {
      tts.stop();
      tts.shutdown();
    }
  }
}
