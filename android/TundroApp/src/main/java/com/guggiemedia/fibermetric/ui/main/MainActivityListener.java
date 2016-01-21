package com.guggiemedia.fibermetric.ui.main;

import android.os.Bundle;

/**
 * MainActivity listener
 */
public interface MainActivityListener {

    /**
     * force user authentication
     */
    void requireLogIn();

    /**
     * display selected activity
     *
     * @param selected
     * @param optional args, null OK
     */
    void activitySelect(MainActivityEnum selected, Bundle args);

    /**
     * display selected dialog
     *
     * @param selected
     * @param optional args, null OK
     */
    void dialogSelect(MainActivityDialogEnum selected, Bundle args);

    /**
     * display selected fragment (within MainActivity)
     *
     * @param selected
     * @param optional args, null OK
     */
    void fragmentSelect(MainActivityFragmentEnum selected, Bundle args);

    /**
     * pop to previous fragment (within MainActivity)
     */
    void fragmentPop();

    /**
     * save a fragment and navigate to new fragment
     *
     * @param oldFragment
     * @param oldArgs
     * @param newFragment
     * @param newArgs
     */
    void fragmentPush(MainActivityFragmentEnum oldFragment, Bundle oldArgs, MainActivityFragmentEnum newFragment, Bundle newArgs);

    /**
     * open/close navigation drawer
     *
     * @param arg true, open navigation drawer
     */
    void navDrawerOpen(boolean arg);
}
