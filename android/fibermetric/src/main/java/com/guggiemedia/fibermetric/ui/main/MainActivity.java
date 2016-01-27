package com.guggiemedia.fibermetric.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.utility.AnalyticHelper;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements MainActivityListener {
    public static final String LOG_TAG = MainActivity.class.getName();

    private Stack<StackElement> _fragmentStack = new Stack();
    private Toolbar _toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbars change w/fragment
        _toolBar = (Toolbar) findViewById(R.id.navToolBar);

        fragmentSelect(MainActivityFragmentEnum.JOB_TODAY_LIST, new Bundle());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void requireLogIn() {

    }

    @Override
    public void activitySelect(MainActivityEnum selected, Bundle args) {

    }

    // MainActivityListener
    @Override
    public void fragmentSelect(MainActivityFragmentEnum selected, Bundle args) {
        String tag = "bogus";
        String title = "bogus";
        Fragment fragment = null;

        Log.d(LOG_TAG, "fragmentSelect:" + selected);

        int homeId = R.drawable.ic_menu_white;

        switch (selected) {
            case ADD_BEACON_VIEW:
                homeId = R.drawable.ic_arrow_back_white;
                tag = AddByBeaconFragment.FRAGMENT_TAG;
                title = getString(R.string.menuAddByBeacon);
                fragment = AddByBeaconFragment.newInstance();
                break;
            case JOB_VIEW: // Job Detail (args contain JobTask row id)
                tag = JobViewFragment.FRAGMENT_TAG;
                title = getString(R.string.menuJob);
                fragment = JobViewFragment.newInstance(args);
                homeId = R.drawable.ic_arrow_back_white;
                break;
            case JOB_TODAY_LIST: // Job Today List
                _fragmentStack.clear();
                tag = StatusFragment.FRAGMENT_TAG;
                title = getString(R.string.menuStatus);
                args.putBoolean(StatusFragment.ARG_PARAM_TODAY, true);
                fragment = StatusFragment.newInstance(args);
                break;
            case MY_INVENTORY_VIEW: // Inventory
                tag = InventoryPagerFragment.FRAGMENT_TAG;
                title = getString(R.string.menuMyInventory);
                args.putString(InventoryPagerFragment.VIEW_TYPE,
                        InventoryPagerFragment.ViewType.myInventory.toString());

                fragment = InventoryPagerFragment.newInstance(args);
                break;
            case PART_VIEW: // Part Detail
                tag = PartFragment.FRAGMENT_TAG;
                title = args.getString(PartFragment.ARG_PARAM_ITEM_NAME);
                homeId = R.drawable.ic_arrow_back_white;
                fragment = PartFragment.newInstance();
                break;
            case REPLACE_BEACON_VIEW:
                homeId = R.drawable.ic_arrow_back_white;
                tag = ReplaceBeaconFragment.FRAGMENT_TAG;
                title = getString(R.string.menuReplaceBeacon);
                fragment = ReplaceBeaconFragment.newInstance();
                break;
            case REQUIRED_INVENTORY_VIEW: // Job Inventory
                tag = InventoryPagerFragment.FRAGMENT_TAG;
                title = getString(R.string.menuRequiredInventory);
                homeId = R.drawable.ic_arrow_back_white;

                args.putString(InventoryPagerFragment.VIEW_TYPE,
                        InventoryPagerFragment.ViewType.requiredInventory.toString());

                fragment = InventoryPagerFragment.newInstance(args);
                break;

            case TODAYS_INVENTORY_VIEW: // Inventory
                tag = InventoryPagerFragment.FRAGMENT_TAG;
                title = getString(R.string.menuTodaysInventory);
                homeId = R.drawable.ic_arrow_back_white;

                args.putString(InventoryPagerFragment.VIEW_TYPE,
                        InventoryPagerFragment.ViewType.todaysInventory.toString());

                fragment = InventoryPagerFragment.newInstance(args);
                break;
        }

        if (fragment == null) {
            throw new IllegalArgumentException("missing fragment:" + selected);
        } else {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setHomeAsUpIndicator(homeId);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            fragment.setArguments(args);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentLayout, fragment, tag);

            fragmentTransaction.commit();

            AnalyticHelper analyticHelper = AnalyticHelper.getInstance(this);
            analyticHelper.fragmentViewLog(tag);
        }
    }

    @Override
    public void fragmentPop() {

    }

    @Override
    public void fragmentPush(MainActivityFragmentEnum oldFragment, Bundle oldArgs, MainActivityFragmentEnum newFragment, Bundle newArgs) {

    }

    @Override
    public void navDrawerOpen(boolean arg) {

    }

}
