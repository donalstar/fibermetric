package com.guggiemedia.fibermetric.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.guggiemedia.fibermetric.lib.utility.UserPreferenceHelper;

import net.go_factory.tundro.R;



public class DiagnosticActivity extends Activity {
    public static final String LOG_TAG = DiagnosticActivity.class.getName();
    public static final String ACTIVITY_TAG = "ACTIVITY_DIAGNOSTIC";
    public static final int REQUEST_CODE = 1234;

    private final UserPreferenceHelper _uph = new UserPreferenceHelper();

    private void killMe() {
        setTestLocation();

        dismissKeyboard();

        startActivity(new Intent(getBaseContext(), SplashActivity.class));
        finish();  // inhibit navigation back to diagnostic
    }

    private void discoverDisplaySize() {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        Log.i(LOG_TAG, "display width:" + metrics.widthPixels + ":height:" + metrics.heightPixels);

        int displaySize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        String sizeCategory = "size unknown";

        switch (displaySize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                sizeCategory = "xlarge display";
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                sizeCategory = "large display";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                sizeCategory = "normal display";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                sizeCategory = "small display";
                break;
        }

        Log.i(LOG_TAG, sizeCategory);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);

        final Button finishedButton = (Button) findViewById(R.id.button_finished);
        finishedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                killMe();
            }
        });


//        final Button zxingButton = (Button) findViewById(R.id.button_test_zxing);
//        zxingButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), CaptureActivity.class);
//                intent.setAction(Intents.Scan.ACTION);
//                startActivityForResult(intent, REQUEST_CODE);
//            }
//        });
//
//        final Button wiFiDiscoveryButton = (Button) findViewById(R.id.button_test_discovery);
//        wiFiDiscoveryButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                //empty
//            }
//        });

        discoverDisplaySize();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d(LOG_TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + intent);

        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_CANCELED) {
                    Log.d(LOG_TAG, "skipping result canceled");
                } else {
                    System.out.println("action:" + intent.getAction());
                    Bundle bundle = intent.getExtras();
                    System.out.println("extras:" + bundle.size());

                    for (String key : bundle.keySet()) {
                        System.out.println(key + ":" + bundle.get(key));
                    }
                }

                break;
        }
    }

    /**
     * Set the test location in UserPreferenceHelper
     */
    private void setTestLocation() {
        final TextView userLatitudeField = (TextView) findViewById(R.id.userLatitude);
        final TextView userLongitudeField = (TextView) findViewById(R.id.userLongitude);

        String latitude = String.valueOf(userLatitudeField.getText());
        String longitude = String.valueOf(userLongitudeField.getText());

        _uph.setTestUserLatitude(this, Float.valueOf(latitude));
        _uph.setTestUserLongitude(this, Float.valueOf(longitude));
    }

    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}