package com.guggiemedia.fibermetric.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 *
 */
public class InventoryPagerAdapter extends FragmentStatePagerAdapter {
    public static final String LOG_TAG = InventoryPagerAdapter.class.getName();

    private InventoryPagerFragment.ViewType _viewType;
    private MainActivityFragmentEnum _parent = MainActivityFragmentEnum.UNKNOWN;
    private Long _jobId;
    private Context _context;

    public InventoryPagerAdapter(FragmentManager fragmentManager,
                                 MainActivityFragmentEnum parent, InventoryPagerFragment.ViewType viewType,
                                 Long jobId,
                                 Context context) {
        super(fragmentManager);

        _parent = parent;
        _context = context;
        _jobId = jobId;
        _viewType = viewType;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";

        switch (position) {
            case 0:
                title = "Parts";
                break;
            case 1:
                title = "Tools";
                break;
            case 2:
                title = "Vehicles";
                break;
            default:
                break;
        }

        return title;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putLong(InventoryViewFragment.ARG_PARAM_TAB_ID, (long) position);
        bundle.putString(InventoryViewFragment.ARG_PARAM_VIEW_TYPE, _viewType.toString());
        bundle.putSerializable(InventoryViewFragment.ARG_PARAM_PARENT, _parent);
        bundle.putLong(JobViewFragment.ARG_PARAM_JOB_ID, _jobId);

        return InventoryViewFragment.newInstance(bundle);
    }
}
