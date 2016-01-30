package com.guggiemedia.fibermetric.ui.main;

import android.os.Bundle;

/**
 * MainActivity listener
 */
public interface MainActivityListener {

    /**
     * display selected fragment (within MainActivity)
     *
     * @param selected
     * @param args
     */
    void fragmentSelect(Fragments selected, Bundle args);


    /**
     * pop to previous fragment (within MainActivity)
     */
    void fragmentPop();

    /**
     * open/close navigation drawer
     *
     * @param arg true, open navigation drawer
     */
    void navDrawerOpen(boolean arg);
}
