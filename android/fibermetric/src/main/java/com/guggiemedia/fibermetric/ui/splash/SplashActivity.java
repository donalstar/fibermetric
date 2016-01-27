package com.guggiemedia.fibermetric.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.ui.main.MainActivity;


/**
 * Display splash graphic
 */
public class SplashActivity extends Activity {
    public static final String LOG_TAG = SplashActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startActivity(new Intent(getBaseContext(), MainActivity.class));
    }
}
