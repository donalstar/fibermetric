package com.guggiemedia.fibermetric.ui.main;

import android.os.Bundle;

/**
 *
 */
public class StackElement {
    private MainActivityFragmentEnum _fragment;
    private Bundle _args;

    public StackElement(MainActivityFragmentEnum fragment, Bundle args) {
        _fragment = fragment;
        _args = args;
    }

    public MainActivityFragmentEnum getFragment() {
        return _fragment;
    }

    public Bundle getArgs() {
        return _args;
    }
}
