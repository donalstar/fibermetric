package com.guggiemedia.fibermetric.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.KeyList;


/**
 *
 */
public class TaskPagerAdapter extends FragmentStatePagerAdapter {
    public static final String LOG_TAG = TaskPagerAdapter.class.getName();

    private final KeyList _keyList;

    private final Context _context;


    public TaskPagerAdapter(KeyList keyList, FragmentActivity activity) {
        super(activity.getSupportFragmentManager());
        _keyList = keyList;
        _context = activity;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(LOG_TAG, "getItem:" + position);

        Bundle bundle = new Bundle();
        bundle.putLong(TaskViewFragment.ARG_PARAM_TASK_ID, _keyList.get(position));

        return TaskViewFragment.newInstance(bundle);
    }

    @Override
    public int getCount() {
        return _keyList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        JobTaskModel model = CommandFacade.jobTaskSelectByRowId(_keyList.get(position), _context);

        String arg = model.getName();
        if (arg.length() > 25) {
            arg = model.getName().substring(0, 25) + "...";
        }

        return arg;
    }

    @Override
    public int getItemPosition(Object object) {
        // force a refresh of the fragments
        return POSITION_NONE;
    }
}
