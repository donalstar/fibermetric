/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.guggiemedia.fibermetric.ui.barcode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.guggiemedia.fibermetric.lib.utility.AnalyticHelper;

import net.go_factory.tundro.R;


/**
 * Activity for the multi-tracker app.  This app detects barcodes and displays the value with the
 * rear facing camera. During detection overlay graphics are drawn to indicate the position,
 * size, and ID of each barcode.
 */
public final class BarcodeCaptureActivity extends AppCompatActivity {
    public static final String LOG_TAG = BarcodeCaptureActivity.class.getName();

    public static final String BarcodeObject = "Barcode";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_barcode_capture);

        fragmentSelect();
    }

    public void fragmentSelect() {
        String tag = BarcodeCaptureFragment.FRAGMENT_TAG;
        Fragment fragment = BarcodeCaptureFragment.newInstance();

        fragment.setArguments(new Bundle());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentLayout, fragment, tag);

        fragmentTransaction.commit();

        AnalyticHelper analyticHelper = AnalyticHelper.getInstance(this);
        analyticHelper.fragmentViewLog(tag);
    }
}