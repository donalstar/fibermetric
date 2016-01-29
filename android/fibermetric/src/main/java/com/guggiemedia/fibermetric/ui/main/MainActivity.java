package com.guggiemedia.fibermetric.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.guggiemedia.fibermetric.R;

public class MainActivity extends AppCompatActivity implements MainActivityListener {
    public static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        fragmentSelect(MainActivityFragmentEnum.STATUS_VIEW, new Bundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void fragmentSelect(MainActivityFragmentEnum selected, Bundle args) {
        Fragment fragment = null;

        switch (selected) {
            case FOOD_SELECTOR_VIEW:
                fragment = FoodSelectorFragment.newInstance();
                break;
            case STATUS_VIEW:
                args.putBoolean(HomeFragment.ARG_PARAM_TODAY, true);
                fragment = HomeFragment.newInstance(args);
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

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

}
