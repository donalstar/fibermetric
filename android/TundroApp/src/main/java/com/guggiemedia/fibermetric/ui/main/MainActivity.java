package com.guggiemedia.fibermetric.ui.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.Personality;
import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.ImageModel;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.TaskActionStateEnum;
import com.guggiemedia.fibermetric.lib.service.BleScanService;
import com.guggiemedia.fibermetric.lib.utility.AnalyticHelper;
import com.guggiemedia.fibermetric.lib.utility.FileHelper;
import com.guggiemedia.fibermetric.lib.utility.ImageTypeEnum;
import com.guggiemedia.fibermetric.ui.barcode.BarcodeCaptureActivity;
import com.guggiemedia.fibermetric.ui.chart.ChartActivity;
import com.guggiemedia.fibermetric.ui.login.LoginActivity;
import com.guggiemedia.fibermetric.ui.utility.NotificationManager;
import com.guggiemedia.fibermetric.ui.utility.PageViewHelper;
import com.guggiemedia.fibermetric.ui.utility.StringUtils;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


import java.io.File;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements MainActivityListener {

    public static final String LOG_TAG = MainActivity.class.getName();
    public static final String ACTIVITY_TAG = "ACTIVITY_MAIN";

    public static final int REQUEST_BARCODE_SCAN_ADD = 27;
    public static final int REQUEST_BARCODE_SCAN_DISCOVER = 28;
    public static final int REQUEST_BARCODE_SCAN_REPLACE = 271;
    public static final int REQUEST_CAPTURE_IMAGE = 2718;
    public static final int REQUEST_CHART = 27182;

    private DrawerLayout _drawerLayout;
    private Toolbar _toolBar;

    private Bundle _activityBundle;
    private Bundle _fragmentState;

    private ImageView _navDrawerThumbNail;
    private TextView _navDrawerEmail;

    private Stack<StackElement> _fragmentStack = new Stack();

    private ImageModel _currentPhoto;

    private NotificationManager nReceiver;

    // MainActivityListener
    @Override
    public void requireLogIn() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    // MainActivityListener
    @Override
    public void activitySelect(MainActivityEnum selected, Bundle args) {
        Intent intent;

        _activityBundle = args;

        switch (selected) {
            case CHART_ACTIVITY:
                intent = new Intent(getBaseContext(), ChartActivity.class);

                intent.putExtra(ChartActivity.ARG_PARAMS, args);

                startActivityForResult(intent, REQUEST_CHART);
                break;

            case IMAGE_ACTIVITY:
                _currentPhoto = new ImageModel();
                _currentPhoto.setDefault();

                File photoFile = FileHelper.getOutputPhotoFile(_currentPhoto.getCreateTime(), ImageTypeEnum.ORIGINAL, this);
                _currentPhoto.setFileName(photoFile.getName());

                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);
                break;

            case LOGIN_ACTIVITY:
                intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();  // inhibit back stack navigation
                break;

            case ADD_BARCODE:
                intent = new Intent(getBaseContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, REQUEST_BARCODE_SCAN_ADD);
                break;

            case DISCOVER_THING_BARCODE:
                intent = new Intent(getBaseContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, REQUEST_BARCODE_SCAN_DISCOVER);
                break;

            case REPLACE_THING_BARCODE:
                _fragmentState = args;

                intent = new Intent(getBaseContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, REQUEST_BARCODE_SCAN_REPLACE);
                break;
            case PHONE_CALL_ACTIVITY:
                String number = args.getString(ChatFragment.ARG_PHONE_NUMBER);

                Log.d(LOG_TAG, "make phone call - to " + number);

                if (number != null) {
                    String formattedNumber = StringUtils.getFormattedPhoneNumber(number);

                    ToastHelper.show("Calling " + " " + formattedNumber + " ...", this);

                    try {
                        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                        phoneIntent.setData(Uri.parse("tel:" + number));
                        startActivity(phoneIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        ToastHelper.show("Call failed, please try again later!", this);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("unknown activity option:" + selected);
        }
    }

    // MainActivityListener
    @Override
    public void dialogSelect(MainActivityDialogEnum selected, Bundle args) {
        String tag = "bogus";
        DialogFragment fragment = null;

        switch (selected) {
            case CAPTURE_INTEGER:
                tag = CaptureDialog.FRAGMENT_TAG;
                fragment = CaptureDialog.newInstance(args);
                break;
            case CAPTURE_NOTE:
                tag = CaptureDialog.FRAGMENT_TAG;
                fragment = CaptureDialog.newInstance(args);
                break;
            case SUSPEND_JOB1:
                tag = SuspendDialog1.FRAGMENT_TAG;
                fragment = SuspendDialog1.newInstance(args);
                break;
            case SUSPEND_JOB2:
                tag = SuspendDialog2.FRAGMENT_TAG;
                fragment = SuspendDialog2.newInstance(args);
                break;
            default:
                throw new IllegalArgumentException("unknown dialog option:" + selected);
        }

        if (fragment == null) {
            throw new IllegalArgumentException("missing dialog:" + selected);
        } else {
            if (PageViewHelper.fragmentTransition(tag, getBaseContext())) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment oldFragment = fragmentManager.findFragmentByTag(tag);
                if (oldFragment != null) {
                    fragmentTransaction.remove(oldFragment);
                }
                fragment.show(fragmentTransaction, tag);
            } else {
                requireLogIn();
            }
        }
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
            case CALENDAR_VIEW: // Weekly Job Calendar
                tag = CalendarFragment.FRAGMENT_TAG;
                title = getString(R.string.menuCalendar);
                fragment = CalendarFragment.newInstance();
                break;
            case CHART_VIEW: // Google Map View
                tag = ChartFragment.FRAGMENT_TAG;
                title = "";
                fragment = ChartFragment.newInstance();
                break;
            case CHAT_LIST: // Chat Catalog
                tag = ChatFragment.FRAGMENT_TAG;
                title = getString(R.string.menuChat);
                fragment = ChatListFragment.newInstance();
                break;
            case CHAT_VIEW: // Chat
                tag = ChatFragment.FRAGMENT_TAG;
                title = getString(R.string.menuChat);
                fragment = ChatFragment.newInstance(args);

                homeId = R.drawable.ic_arrow_back_white;
                break;
            case ESCALATION_FORM: // Escalation
                _fragmentStack.clear(); //remove me after implementation
                tag = StubFragment.FRAGMENT_TAG;
                title = getString(R.string.menuEscalation);
                fragment = StubFragment.newInstance();
                break;
            case FEEDBACK_FORM: // Submit feedback
                _fragmentStack.clear(); //remove me after implementation
                tag = StubFragment.FRAGMENT_TAG;
                title = getString(R.string.menuFeedBack);
                fragment = StubFragment.newInstance();
                break;
            case HELP_VIEW: // Help Text
                _fragmentStack.clear(); //remove me after implementation
                tag = StubFragment.FRAGMENT_TAG;
                title = getString(R.string.menuHelp);
                fragment = StubFragment.newInstance();
                break;
            case JOB_VIEW: // Job Detail (args contain JobTask row id)
                tag = JobViewFragment.FRAGMENT_TAG;
                title = getString(R.string.menuJob);
                fragment = JobViewFragment.newInstance(args);
                homeId = R.drawable.ic_arrow_back_white;
                break;
            case JOB_TODAY_LIST: // Job Today List
                _fragmentStack.clear();
                tag = JobListFragment.FRAGMENT_TAG;
                title = getString(R.string.menuJobToday);
                args.putBoolean(JobListFragment.ARG_PARAM_TODAY, true);
                fragment = JobListFragment.newInstance(args);
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
            case REPORT_VIEW: // Report a Problem
                homeId = R.drawable.ic_close_white;
                tag = ReportFragment.FRAGMENT_TAG;
                title = getString(R.string.menuReport);
                fragment = ReportFragment.newInstance();
                break;
            case REQUIRED_INVENTORY_VIEW: // Job Inventory
                tag = InventoryPagerFragment.FRAGMENT_TAG;
                title = getString(R.string.menuRequiredInventory);
                homeId = R.drawable.ic_arrow_back_white;

                args.putString(InventoryPagerFragment.VIEW_TYPE,
                        InventoryPagerFragment.ViewType.requiredInventory.toString());

                fragment = InventoryPagerFragment.newInstance(args);
                break;
            case SETTING_FORM: // User Preferences
                _fragmentStack.clear(); //remove me after implementation
                tag = StubFragment.FRAGMENT_TAG;
                title = getString(R.string.menuSetting);
                fragment = StubFragment.newInstance();
                break;
            case STUB_VIEW: // Place Holder
                _fragmentStack.clear(); //remove me after implementation
                tag = StubFragment.FRAGMENT_TAG;
                title = getString(R.string.menuStub);
                fragment = StubFragment.newInstance();
                break;
            case TASK_END: // Task End
                homeId = R.drawable.ic_arrow_back_white;
                tag = TaskEndFragment.FRAGMENT_TAG;
                title = getString(R.string.menuTaskEnd);
                fragment = TaskEndFragment.newInstance(args);
                break;
            case TASK_PAGER: // Pager
                homeId = R.drawable.ic_arrow_back_white;
                tag = TaskPagerFragment.FRAGMENT_TAG;
                title = getString(R.string.menuTask);
                fragment = TaskPagerFragment.newInstance(args);
                break;
            case TASK_VIEW: // Task Detail
                tag = TaskViewFragment.FRAGMENT_TAG;
                title = getString(R.string.menuTask);
                fragment = TaskViewFragment.newInstance(args);
                break;
            case TODAYS_INVENTORY_VIEW: // Inventory
                tag = InventoryPagerFragment.FRAGMENT_TAG;
                title = getString(R.string.menuTodaysInventory);
                homeId = R.drawable.ic_arrow_back_white;

                args.putString(InventoryPagerFragment.VIEW_TYPE,
                        InventoryPagerFragment.ViewType.todaysInventory.toString());

                fragment = InventoryPagerFragment.newInstance(args);
                break;
            case TURN_BY_TURN_LIST:
                tag = TurnByTurnListFragment.FRAGMENT_TAG;
                title = getString(R.string.menuChart);
                fragment = TurnByTurnListFragment.newInstance();
                homeId = R.drawable.ic_arrow_back_white;
                break;
            case TURN_BY_TURN_VIEW:
                tag = ChartFragment.FRAGMENT_TAG;
                title = getString(R.string.menuChart);
                fragment = TurnByTurnViewFragment.newInstance();
                homeId = R.drawable.ic_nav_arrow_big;
                break;
            case NAVIGATION_PREVIEW:
                tag = ChartFragment.FRAGMENT_TAG;
                title = getString(R.string.menuChart);
                fragment = NavigationPreviewFragment.newInstance();
                homeId = R.drawable.ic_arrow_back_white;
                break;
        }

        if (fragment == null) {
            throw new IllegalArgumentException("missing fragment:" + selected);
        } else {
            setSupportActionBar(_toolBar); // force menu reload
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

    // MainActivityListener
    public void fragmentPop() {
        if (_fragmentStack.empty()) {
            throw new IllegalArgumentException("fragment pop w/empty stack");
        }

        StackElement element = _fragmentStack.pop();
        Log.d(LOG_TAG, "pop:" + element.getFragment());
        fragmentSelect(element.getFragment(), element.getArgs());
    }

    // MainActivityListener
    public void fragmentPush(MainActivityFragmentEnum oldFragment, Bundle oldArgs, MainActivityFragmentEnum newFragment, Bundle newArgs) {
        Log.d(LOG_TAG, "push:" + _fragmentStack.size() + ":" + oldFragment + ":" + newFragment);
        _fragmentStack.push(new StackElement(oldFragment, oldArgs));
        fragmentSelect(newFragment, newArgs);
    }

    // MainActivityListener
    public void navDrawerOpen(boolean arg) {
        if (arg) {
            _drawerLayout.openDrawer(GravityCompat.START);
        } else {
            _drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean initialLogin = this.getIntent().getBooleanExtra(LoginActivity.ARG_INITIAL_LOGIN, false);

        if (initialLogin) {
            ToastHelper.show(R.string.toastSignInSuccess, this);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // every Activity should invoke
        PageViewHelper.activityTransition(ACTIVITY_TAG, this);

        // toolbars change w/fragment
        _toolBar = (Toolbar) findViewById(R.id.navToolBar);

        setSupportActionBar(_toolBar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
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

        _drawerLayout = (DrawerLayout) findViewById(R.id.navDrawerLayout);

        _navDrawerEmail = (TextView) findViewById(R.id.headerTextView);
        _navDrawerThumbNail = (ImageView) findViewById(R.id.headerImageView);

        fragmentSelect(MainActivityFragmentEnum.JOB_TODAY_LIST, new Bundle());

        listenForNotifications();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getBundleExtra(NotificationManager.PART_BUNDLE);

        if (bundle != null) {
            // TODO: bug, we're creating multiple MainActivities
            this.fragmentSelect(MainActivityFragmentEnum.PART_VIEW, bundle);
        } else {
            _navDrawerEmail.setText(Personality.personSelf.getEmail());
            //TODO when server starts to deliver thumbnails _navDrawerThumbNail;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void navDrawerDispatch(int arg) {
        _drawerLayout.closeDrawers();

        Bundle bundle = new Bundle();

        switch (arg) {
            case R.id.navToday:
                fragmentSelect(MainActivityFragmentEnum.JOB_TODAY_LIST, bundle);
                break;
            case R.id.navWeek:
                fragmentSelect(MainActivityFragmentEnum.CALENDAR_VIEW, bundle);
                break;
            case R.id.navChat:
                fragmentSelect(MainActivityFragmentEnum.CHAT_LIST, bundle);
                break;
            case R.id.navHelp:
                fragmentSelect(MainActivityFragmentEnum.HELP_VIEW, bundle);
                break;
            case R.id.navInventory:
                fragmentSelect(MainActivityFragmentEnum.MY_INVENTORY_VIEW, bundle);
                break;
            case R.id.navSetting:
                fragmentSelect(MainActivityFragmentEnum.SETTING_FORM, bundle);
                //activitySelect(MainActivityEnum.IMAGE_ACTIVITY, new Bundle());
                break;
            case R.id.navSignOut:
                CommandFacade.authenticationSignOut(this);
                finish();
                break;
            default:
                throw new IllegalArgumentException("unknown nav drawer selection");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d(LOG_TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + intent);

        switch (requestCode) {
            case REQUEST_BARCODE_SCAN_ADD:
                if (resultCode == CommonStatusCodes.SUCCESS) {
                    boolean gotBarcode = false;

                    if (intent != null) {
                        Barcode barcode = intent.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                        Log.d(LOG_TAG, "Scanned barcode " + barcode.displayValue);

                        gotBarcode = true;
                    }

                    TaskActionStateEnum taskActionState = (gotBarcode)
                            ? TaskActionStateEnum.COMPLETE : TaskActionStateEnum.CANCELED;

                    CommandFacade.taskActionUpdate(taskActionState, _activityBundle.getLong(TaskPagerFragment.ARG_PARAM_ACTION_ID), this);
                }

                if (_activityBundle.containsKey(TaskPagerFragment.ARG_PARAM_ACTION_ID)) {
                    fragmentSelect(MainActivityFragmentEnum.TASK_PAGER, _activityBundle);
                }

                break;

            case REQUEST_BARCODE_SCAN_DISCOVER:
                if (resultCode == CommonStatusCodes.SUCCESS) {
                    if (intent != null) {
                        Barcode barcode = intent.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                        Log.d(LOG_TAG, "Scanned barcode " + barcode);

                        PartModel partModel = CommandFacade.updatePartStatusByBarcode(
                                barcode.displayValue, Personality.personSelf, this);

                        String message = (partModel != null) ? String.format(
                                this.getResources().getString(R.string.barcode_added_message),
                                partModel.getName()) : "barcode was not recognized!";

                        ToastHelper.show(message, this);
                    }
                }

                break;
            case REQUEST_BARCODE_SCAN_REPLACE:
                if (resultCode == CommonStatusCodes.SUCCESS) {
                    if (intent != null) {
                        Barcode barcode = intent.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);

                        Log.d(LOG_TAG, "Scanned barcode " + barcode.displayValue);

                        long partId = _fragmentState.getLong(PartFragment.ARG_PARAM_ROW_ID);

                        PartModel partModel = CommandFacade.updateBarcodeForPart(partId, barcode.displayValue, this);

                        String message = String.format(
                                this.getResources().getString(R.string.barcode_replace_message),
                                partModel.getName());

                        ToastHelper.show(message, this);
                    }
                }

                break;
            case REQUEST_CHART:
                ChartActivity.ReturnType returnType
                        = (ChartActivity.ReturnType) intent.getExtras().get(ChartActivity.ARG_RETURN_TYPE);

                if (returnType != null) {
                    switch (returnType) {
                        case fragmentSelect:
                            MainActivityFragmentEnum fragment
                                    = (MainActivityFragmentEnum) intent.getExtras().get(ChartActivity.ARG_FRAGMENT_SELECTION);

                            Bundle args = intent.getExtras().getBundle(ChartActivity.ARG_PARAMS);

                            fragmentSelect(fragment, args);
                            break;
                        case fragmentPop:
                            fragmentPop();
                            break;
                        case fragmentPush:


                            MainActivityFragmentEnum oldFragment
                                    = (MainActivityFragmentEnum) intent.getExtras().get(ChartActivity.ARG_OLD_FRAGMENT);

                            MainActivityFragmentEnum newFragment
                                    = (MainActivityFragmentEnum) intent.getExtras().get(ChartActivity.ARG_NEW_FRAGMENT);

                            Bundle oldArgs = intent.getExtras().getBundle(ChartActivity.ARG_OLD_BUNDLE);
                            Bundle newArgs = intent.getExtras().getBundle(ChartActivity.ARG_NEW_BUNDLE);

                            fragmentPush(oldFragment, oldArgs, newFragment, newArgs);
                            break;
                        case drawerItemSelect:
                            int arg = intent.getExtras().getInt(ChartActivity.ARG_DRAWER_SELECTION);
                            navDrawerDispatch(arg);
                            break;
                        case drawerOpen:
                            navDrawerOpen(true);
                            break;
                    }
                }

                break;
            case REQUEST_CAPTURE_IMAGE:
                if ((resultCode == RESULT_OK) || (resultCode == RESULT_CANCELED)) {
                    CommandFacade.imageFresh(_currentPhoto, this);

                    TaskActionStateEnum taskActionState = (resultCode == RESULT_OK)
                            ? TaskActionStateEnum.COMPLETE : TaskActionStateEnum.CANCELED;

                    CommandFacade.taskActionUpdate(taskActionState, _activityBundle.getLong(TaskPagerFragment.ARG_PARAM_ACTION_ID), this);
                }

                if (_activityBundle.containsKey(TaskPagerFragment.ARG_PARAM_ACTION_ID)) {
                    fragmentSelect(MainActivityFragmentEnum.TASK_PAGER, _activityBundle);
                }

                break;
        }
    }

    private void listenForNotifications() {
        nReceiver = new NotificationManager(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BleScanService.ACTION_BLE_SCAN);
        registerReceiver(nReceiver, filter);
    }
}
