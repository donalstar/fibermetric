package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarFragment extends Fragment {
    public static final String FRAGMENT_TAG = "FRAGMENT_CALENDAR_VIEW";

    public static final String LOG_TAG = CalendarFragment.class.getName();

    private MainActivityListener _listener;
    private final ContentFacade _contentFacade = new ContentFacade();

    private RelativeLayout[] _dayColumnLayouts;

    private List<CalendarEntry> _calendarEntries;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;

        _calendarEntries = loadCalendarEntries();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        LinearLayout dayColumns = (LinearLayout) view.findViewById(R.id.dayColumns);

        _dayColumnLayouts = new RelativeLayout[7];

        for (int i = 0; i < _dayColumnLayouts.length; i++) {
            _dayColumnLayouts[i] = createDayColumn();
            dayColumns.addView(_dayColumnLayouts[i]);
        }

        RelativeLayout grid = (RelativeLayout) view.findViewById(R.id.grid);

        createGrid(grid);

        RelativeLayout leftColumn = (RelativeLayout) view.findViewById(R.id.leftColumn);

        TextView currentHourView = createLeftColumn(leftColumn);

        LinearLayout columnHeaders = (LinearLayout) view.findViewById(R.id.columnHeaders);

        LinearLayout columnHeadersRow2 = (LinearLayout) view.findViewById(R.id.columnHeadersRow2);

        String weekDates[] = getWeekDates();

        String weekDays[] = getWeekDays();

        for (int i = 0; i < weekDates.length; i++) {
            TextView dateNumber = addHeaderCell(weekDates[i]);

            columnHeaders.addView(dateNumber);

            TextView dayName = addHeaderCell2(weekDays[i]);

            columnHeadersRow2.addView(dayName);
        }

        addCalendarEntries();

        ActionBar actionBar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();

        actionBar.setTitle(getMonth());

        ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView);

        scrollToView(scrollView, currentHourView);

        return view;
    }

    private void scrollToView(final ScrollView scrollView, final TextView currentHourView) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, currentHourView.getBottom());
            }
        });
    }

    private void addCalendarEntries() {
        for (CalendarEntry calendarEntry : _calendarEntries) {
            int day = calendarEntry.dayIndex;

            int height = Integer.parseInt(getHightOfButton(calendarEntry.startHour, calendarEntry.endHour));

            RelativeLayout columnLayout = _dayColumnLayouts[day];

            if (columnLayout != null) {
                int topMargin = getCalendarEntryTopOffset(calendarEntry.startHour);

                TextView entry = createCalendarEntry(height, topMargin, calendarEntry.name);

                columnLayout.addView(entry);
            }

        }
    }

    private void createGrid(RelativeLayout grid) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());

        for (int i = 0; i < 25; i++) {
            int topMargin = (int) px * i;

            View delimiterView = addDelimiterView(topMargin, "#777777");

            grid.addView(delimiterView);
        }

        for (int i = 0; i < 24; i++) {
            int topMargin = (int) px * i + (int) (px / 2);

            View delimiterView = addDelimiterView(topMargin, "#aaaaaa");

            grid.addView(delimiterView);
        }
    }

    private TextView createLeftColumn(RelativeLayout leftColumn) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());

        int currentHour = getHour();
        TextView currentHourView = null;

        for (int i = 0; i < 24; i++) {
            int topMargin = (int) px * i;

            TextView textView = new TextView(getActivity());

            final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(0, topMargin, 0, 0);

            textView.setLayoutParams(params);

            textView.setGravity(Gravity.CENTER);

            if (i == currentHour - 2) {
                currentHourView = textView;
            }

            int hour = (i % 12) + 1;
            String suffix = (i > 11) ? "pm" : "am";

            textView.setText(hour + suffix);

            leftColumn.addView(textView);
        }

        return currentHourView;
    }

    private RelativeLayout createDayColumn() {

        RelativeLayout relativeLayout = new RelativeLayout(getActivity());

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, 0.2f);

        relativeLayout.setLayoutParams(params);

        View delimiterView = addDelimiterView2("#aaaaaa");

        relativeLayout.addView(delimiterView);

        return relativeLayout;
    }

    /**
     * @param topMargin
     * @param color
     * @return
     */
    private View addDelimiterView(int topMargin, String color) {
        View delimiterView = new View(getActivity());

        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT, 1);

        params.setMargins(0, topMargin, 0, 0);

        delimiterView.setLayoutParams(params);

        delimiterView.setBackgroundColor(Color.parseColor(color));

        return delimiterView;
    }

    private View addDelimiterView2(String color) {
        View delimiterView = new View(getActivity());

        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                1, RelativeLayout.LayoutParams.FILL_PARENT);

        delimiterView.setLayoutParams(params);

        delimiterView.setBackgroundColor(Color.parseColor(color));

        return delimiterView;
    }

    private TextView addHeaderCell(String value, boolean topHeader) {
        TextView textView = new TextView(getActivity());

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.3f);

        textView.setLayoutParams(params);

        textView.setGravity(Gravity.CENTER);

        textView.setText(value);

        int style = (topHeader) ? R.style.HeaderCell1 : R.style.HeaderCell2;

        textView.setTextAppearance(getActivity(), style);

        return textView;
    }

    private TextView addHeaderCell(String value) {
        return addHeaderCell(value, true);
    }

    private TextView addHeaderCell2(String value) {
        return addHeaderCell(value, false);
    }

    public String[] getWeekDays() {
        return getWeekDayForPattern("EEE");
    }

    public String[] getWeekDayForPattern(String pattern) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String[] days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1;
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }

        return days;
    }

    private String[] getWeekDates() {
        return getWeekDayForPattern("d");
    }

    private Date getEndOfWeekDate() {
        Calendar now = Calendar.getInstance();

        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 8;

        now.add(Calendar.DAY_OF_MONTH, delta);

        return now.getTime();
    }

    private int getDayOfWeek(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);

        int index = now.get(Calendar.DAY_OF_WEEK);

        return index-1;
    }

    /**
     * @return
     */
    private String getMonth() {
        Calendar now = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("MMMM");

        return format.format(now.getTime());
    }

    private int getHour() {
        Calendar now = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("H");

        return Integer.parseInt(format.format(now.getTime()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.calendar_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.navDrawerOpen(true);
                break;
            case R.id.actionSearch:
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }


    public int getCalendarEntryTopOffset(int hour) {
        return hour * 60;
    }

    public String getHightOfButton(int startTime, int endTime) {
        String hight = "0";
        try {
            int subHigth = endTime - startTime;

            hight = String.valueOf(subHigth * 60);

        } catch (Exception e) {
            Log.getStackTraceString(e);
        }

        return hight;

    }

    public TextView createCalendarEntry(int entryHeight, int marginTop,
                                        String text) {


        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT, entryHeight);

        TextView calendarEntry = new TextView(getActivity());
        calendarEntry.setLayoutParams(params);

        calendarEntry.setTextAppearance(getActivity(), R.style.CalendarCell);

        calendarEntry.setBackgroundColor(Color.parseColor("#ffbf00"));
        calendarEntry.setText(text);


        calendarEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastHelper.show("Selected job!!", getActivity());
            }
        });

        calendarEntry.setTextSize(9);

        params.setMargins(0, marginTop, 0, 0);

        return calendarEntry;
    }

    private List<CalendarEntry> loadCalendarEntries() {

        Date endOfWeek = getEndOfWeekDate();

        List<JobTaskModel> jobTasks = _contentFacade.selectJobTaskByDeadlineDate(endOfWeek, getActivity());

        List<CalendarEntry> entries = new ArrayList<>();

        for (JobTaskModel jobTask : jobTasks) {
            CalendarEntry entry = new CalendarEntry();

            entry.name = jobTask.getName();

            // hard-coding to 9:00 am
            entry.startHour = 9;

            entry.endHour = entry.startHour + jobTask.getDuration()/60;

            entry.dayIndex = getDayOfWeek(jobTask.getDeadLine());

            entries.add(entry);
        }

        return entries;
    }

    private class CalendarEntry {
        public int dayIndex;
        public int startHour;
        public int endHour;
        public String name;

    }
}