package com.guggiemedia.fibermetric.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import net.go_factory.tundro.R;
import com.guggiemedia.fibermetric.ui.login.LoginActivity;
import com.guggiemedia.fibermetric.ui.utility.PageViewHelper;


/**
 * Display splash graphic
 */
public class SplashActivity extends Activity {
    public static final String LOG_TAG = SplashActivity.class.getName();
    public static final String ACTIVITY_TAG = "ACTIVITY_SPLASH";

    public static final Long SLEEP_DURATION = 2000L;

    private boolean _diagnosticFlag = false;
    private GestureDetector _gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PageViewHelper.activityTransition(ACTIVITY_TAG, this);

        _gestureDetector = new GestureDetector(this, new DoubleTapGestureDetector());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Class nextActivityClass = (_diagnosticFlag) ? DiagnosticActivity.class : LoginActivity.class;

                startActivity(new Intent(getBaseContext(), nextActivityClass));

                finish();  // inhibit navigation back to splash
            }
        }, SLEEP_DURATION);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return _gestureDetector.onTouchEvent(motionEvent);
    }

    class DoubleTapGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(LOG_TAG, "entering diagnostic mode");
            _diagnosticFlag = true;
            return true;
        }
    }
}
