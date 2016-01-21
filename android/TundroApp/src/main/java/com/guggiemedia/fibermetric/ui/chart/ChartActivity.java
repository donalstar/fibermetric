package com.guggiemedia.fibermetric.ui.chart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import net.go_factory.tundro.R;
import com.guggiemedia.fibermetric.ui.main.MainActivityDialogEnum;
import com.guggiemedia.fibermetric.ui.main.MainActivityEnum;
import com.guggiemedia.fibermetric.ui.main.MainActivityFragmentEnum;
import com.guggiemedia.fibermetric.ui.main.MainActivityListener;
import com.guggiemedia.fibermetric.ui.utility.PageViewHelper;

/**
 *
 */
public class ChartActivity extends AppCompatActivity implements MainActivityListener {
    public static final String LOG_TAG = ChartActivity.class.getName();
    public static final String ACTIVITY_TAG = "ACTIVITY_CHART";

    public static final String ARG_DRAWER_SELECTION = "DRAWER_SELECTION";
    public static final String ARG_FRAGMENT_SELECTION = "FRAGMENT_SELECTION";
    public static final String ARG_FRAGMENT_POP = "FRAGMENT_POP";
    public static final String ARG_PARAMS = "PARAMS";
    public static final String ARG_OLD_FRAGMENT = "OLD_FRAGMENT";
    public static final String ARG_NEW_FRAGMENT = "NEW_FRAGMENT";
    public static final String ARG_OLD_BUNDLE = "OLD_BUNDLE";
    public static final String ARG_NEW_BUNDLE = "NEW_BUNDLE";
    public static final String ARG_RETURN_TYPE = "RETURN_TYPE";

    private Toolbar _toolBar;
    private Bundle _bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle newBundle = null;
        Bundle oldBundle = null;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            _bundle = extras.getBundle(ARG_PARAMS);

            newBundle = _bundle.getBundle(ARG_NEW_BUNDLE);
            oldBundle = _bundle.getBundle(ARG_OLD_BUNDLE);
        }

        setContentView(R.layout.activity_main);

        // every Activity should invoke
        PageViewHelper.activityTransition(ACTIVITY_TAG, this);

        _toolBar = (Toolbar) findViewById(R.id.navToolBar);

        setSupportActionBar(_toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.leftNavDrawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                navDrawerDispatch(menuItem.getItemId());
                return true;
            }
        });

        fragmentPush(MainActivityFragmentEnum.PART_VIEW, oldBundle,
                MainActivityFragmentEnum.CHART_VIEW, newBundle);
    }

    private void navDrawerDispatch(int arg) {
        Intent intent = this.getIntent();

        intent.putExtra(ARG_RETURN_TYPE, ReturnType.drawerItemSelect);
        intent.putExtra(ARG_DRAWER_SELECTION, arg);
        this.setResult(RESULT_OK, intent);
        finish();
    }

    // MainActivityListener


    @Override
    public void fragmentSelect(MainActivityFragmentEnum selected, Bundle args) {
        Log.i(LOG_TAG, "fragmentSelect " + selected);

        Intent intent = this.getIntent();
        intent.putExtra(ARG_RETURN_TYPE, ReturnType.fragmentSelect);

        intent.putExtra(ARG_FRAGMENT_SELECTION, selected);
        intent.putExtra(ARG_PARAMS, args);
        this.setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        if (item.getTitle().equals(getString(R.string.menuJob))) {
            fragmentSelect(MainActivityFragmentEnum.JOB_TODAY_LIST, new Bundle());
        } else {
            Intent intent = this.getIntent();
            intent.putExtra(ARG_RETURN_TYPE, ReturnType.drawerOpen);
            intent.putExtra(ARG_DRAWER_SELECTION, -1);

            this.setResult(RESULT_OK, intent);
            finish();
        }

        return false;
    }

    @Override
    public void requireLogIn() {
        Log.i(LOG_TAG, "requireLogIn");
    }

    @Override
    public void activitySelect(MainActivityEnum selected, Bundle args) {
        Log.i(LOG_TAG, "activitySelect");
    }

    @Override
    public void dialogSelect(MainActivityDialogEnum selected, Bundle args) {
        Log.i(LOG_TAG, "dialogSelect");
    }

    @Override
    public void fragmentPop() {
        Intent intent = this.getIntent();
        intent.putExtra(ARG_RETURN_TYPE, ReturnType.fragmentPop);
        intent.putExtra(ARG_FRAGMENT_POP, _bundle.getBundle(ARG_OLD_BUNDLE));
        this.setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void fragmentPush(MainActivityFragmentEnum oldFragment, Bundle oldArgs, MainActivityFragmentEnum newFragment, Bundle newArgs) {
        Log.i(LOG_TAG, "fragmentPush");

        Intent intent = this.getIntent();

        intent.putExtra(ARG_RETURN_TYPE, ReturnType.fragmentPush);

        intent.putExtra(ARG_OLD_FRAGMENT, oldFragment);
        intent.putExtra(ARG_NEW_FRAGMENT, newFragment);

        intent.putExtra(ARG_OLD_BUNDLE, oldArgs);
        intent.putExtra(ARG_NEW_BUNDLE, newArgs);

        this.setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void navDrawerOpen(boolean arg) {
        Log.i(LOG_TAG, "navDrawerOpen");
    }

    public enum ReturnType {
        fragmentSelect,
        fragmentPush,
        fragmentPop,
        drawerOpen,
        drawerItemSelect
    }
}
