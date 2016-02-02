package com.guggiemedia.fibermetric.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.utility.ToastHelper;

public class MainActivity extends AppCompatActivity implements MainActivityListener {
    public static final String LOG_TAG = MainActivity.class.getName();

    private DrawerLayout _drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _drawerLayout = (DrawerLayout) findViewById(R.id.navDrawerLayout);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                Fragment fragment = fragmentManager.findFragmentById(R.id.contentLayout);

                if (fragment != null) {
                    FragmentContext fragmentContext = (FragmentContext) fragment;

                    String title = fragmentContext.getName();

                    getSupportActionBar().setTitle(title);

                    getSupportActionBar().setHomeAsUpIndicator(fragmentContext.getHomeIndicator());
                }

                int stackHeight = getSupportFragmentManager().getBackStackEntryCount();

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                    getSupportActionBar().setHomeButtonEnabled(true);
                } else {
                    getSupportActionBar().setHomeButtonEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }

        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.leftNavDrawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                navDrawerDispatch(menuItem.getItemId());
                return true;
            }
        });


        fragmentSelect(Fragments.STATUS_VIEW, new Bundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void fragmentSelect(Fragments selected, Bundle args) {
        Fragment fragment = null;

        switch (selected) {
            case FOOD_SELECTOR_VIEW:
                fragment = FoodSelectorFragment.newInstance();
                break;
            case HISTORY_VIEW:
                fragment = HistoryFragment.newInstance();
                break;
            case WEEK_HISTORY_VIEW:
                fragment = WeekHistoryFragment.newInstance();
                break;
            case STATUS_VIEW:
                args.putBoolean(HomeFragment.ARG_PARAM_TODAY, true);
                fragment = HomeFragment.newInstance(args);
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (selected.equals(Fragments.FOOD_SELECTOR_VIEW)) {
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit);
            }

            fragmentTransaction.replace(R.id.contentLayout, fragment, fragment.getTag());

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void fragmentPop() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void navDrawerOpen(boolean arg) {

    }

    private void navDrawerDispatch(int arg) {
        _drawerLayout.closeDrawers();

        Bundle bundle = new Bundle();

        switch (arg) {
            case R.id.navStatus:
                fragmentSelect(Fragments.STATUS_VIEW, bundle);
                break;
            case R.id.navHistory:
                fragmentSelect(Fragments.HISTORY_VIEW, bundle);
                break;
            case R.id.navWeekHistory:
                fragmentSelect(Fragments.WEEK_HISTORY_VIEW, bundle);
                break;
            case R.id.navHelp:
                break;
            case R.id.navSetting:
                break;
            case R.id.navSignOut:
                break;
            default:
                throw new IllegalArgumentException("unknown nav drawer selection");
        }
    }
}

