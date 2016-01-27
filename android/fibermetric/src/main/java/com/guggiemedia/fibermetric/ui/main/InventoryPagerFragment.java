package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.utility.UserPreferenceHelper;
import com.guggiemedia.fibermetric.ui.component.FloatingActionMenu;
import com.guggiemedia.fibermetric.ui.utility.FloatingActionButtonHelper;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;



/**
 *
 */
public class InventoryPagerFragment extends Fragment
        implements FloatingActionButtonHelper.ButtonClickListener {

    public static final String FRAGMENT_TAG = "FRAGMENT_INVENTORY_PAGER";

    public static final String LOG_TAG = InventoryPagerFragment.class.getName();

    public static final String VIEW_TYPE = "VIEW_TYPE";

    public static final String ARG_ADD_TO_CUSTODY = "ADD_TO_CUSTODY";

    public enum ViewType {
        myInventory,
        requiredInventory,
        todaysInventory;

        static ViewType getForValue(String value) {
            ViewType match = null;

            for (ViewType type : ViewType.values()) {
                if (type.toString().equals(value)) {
                    match = type;
                    break;
                }
            }

            return match;
        }
    }

    private MainActivityListener _listener;

    private TabLayout _tabLayout;
    private ViewPager _viewPager;

    private InventoryPagerAdapter _adapter;

    private ViewType _viewType;

    private final UserPreferenceHelper _uph = new UserPreferenceHelper();

    private android.support.design.widget.FloatingActionButton _fab;
    private FloatingActionMenu _fabMenu;

    /**
     * Use this factory method to create a new fragment instance
     *
     * @param args
     * @return new instance of TaskPagerFragment
     */
    public static InventoryPagerFragment newInstance(Bundle args) {
        InventoryPagerFragment fragment = new InventoryPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String addPartToCustody = getArguments().getString(ARG_ADD_TO_CUSTODY);

        if (addPartToCustody != null) {
            Log.i(LOG_TAG, "Add to Custody?? " + addPartToCustody);

            ToastHelper.show("Added to Custody: \n" + addPartToCustody, getActivity());
        }


        super.onCreate(savedInstanceState);

        _viewType = ViewType.getForValue(
                getArguments().getString(InventoryPagerFragment.VIEW_TYPE));

        setHasOptionsMenu(true);
    }

    private void createFabMenu(ViewGroup view) {
        int fabMenuButtonImages[] = {R.drawable.ic_barcode, R.drawable.ic_beacon};
        int fabMenuButtonLabels[] =
                {R.string.inventory_add_by_barcode, R.string.inventory_add_by_beacon};

        FloatingActionButtonHelper floatingActionButtonHelper = new FloatingActionButtonHelper();

        _fabMenu = floatingActionButtonHelper.createFloatingActionButtonMenu(
                fabMenuButtonImages,
                fabMenuButtonLabels, getActivity(),
                view, _fab);

        floatingActionButtonHelper.addOnButtonClickListener(new FloatingActionButtonHelper.ButtonClickListener() {
            @Override
            public void handleButtonClick(int imageId) {

                switch (imageId) {
                    case R.drawable.ic_beacon: {
                        Bundle bundle = new Bundle();

                        MainActivityFragmentEnum parent
                                = _viewType.equals(ViewType.myInventory)
                                ? MainActivityFragmentEnum.MY_INVENTORY_VIEW
                                : MainActivityFragmentEnum.TODAYS_INVENTORY_VIEW;

                        bundle.putSerializable(PartFragment.ARG_PARAM_PARENT, parent);

                        _listener.fragmentSelect(MainActivityFragmentEnum.ADD_BEACON_VIEW, bundle);

                        break;
                    }
                    case R.drawable.ic_barcode:
                        _fabMenu.close(false);

                        Bundle bundle = new Bundle();

                        ((MainActivity) getActivity()).activitySelect(
                                MainActivityEnum.DISCOVER_THING_BARCODE, bundle);

                        break;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_inventory_pager, container, false);

        MainActivityFragmentEnum parent
                = _viewType.equals(ViewType.myInventory)
                ? MainActivityFragmentEnum.MY_INVENTORY_VIEW
                : MainActivityFragmentEnum.TODAYS_INVENTORY_VIEW;


        _adapter = new InventoryPagerAdapter(
                getChildFragmentManager(), parent, _viewType,
                getArguments().getLong(JobViewFragment.ARG_PARAM_JOB_ID), getActivity());

        _viewPager = (ViewPager) view.findViewById(R.id.inventoryTabPager);
        _tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        _viewPager.setAdapter(_adapter);

        Long savedTabItem = _uph.getInventoryTabSelection(getActivity());

        if (savedTabItem != null) {
            _viewPager.setCurrentItem(savedTabItem.intValue());
        }

        _viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_tabLayout));


        _viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                _uph.setInventoryTabSelection(getActivity(), (long) i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        _tabLayout.post(new Runnable() {
            @Override
            public void run() {
                _tabLayout.setTabsFromPagerAdapter(_adapter);

                _tabLayout.setupWithViewPager(_viewPager);
            }
        });

        FrameLayout fabFrame = (FrameLayout) view.findViewById(R.id.fabFrame);

        _fab = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fab);

        createFabMenu(fabFrame);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.inventory_view_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        switch (item.getItemId()) {
            case android.R.id.home:
                if (_viewType.equals(ViewType.myInventory)) {
                    _listener.navDrawerOpen(true);
                } else {
                    _listener.fragmentSelect(MainActivityFragmentEnum.JOB_TODAY_LIST, new Bundle());
                }

                break;
            case R.id.actionSearch:
                break;
            case R.id.actionMore:
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }

    public void handleButtonClick(int imageId) {

        switch (imageId) {
            case R.drawable.ic_beacon: {
                ToastHelper.show("WHAT SHOULD HAPPEN HERE??", getActivity());
//
//                Bundle bundle = new Bundle();
//
//                bundle.putSerializable(PartFragment.ARG_PARAM_PARENT, _viewType);
//
//       //         bundle.putSerializable(PartFragment.ARG_PARAM_PART, _model);
//
//                _listener.fragmentSelect(MainActivityFragmentEnum.ADD_BEACON_VIEW, bundle);

                break;
            }
            case R.drawable.ic_barcode:
                Bundle bundle = new Bundle();

                ((MainActivity) getActivity()).activitySelect(
                        MainActivityEnum.DISCOVER_THING_BARCODE, bundle);

                break;
        }
    }
}




