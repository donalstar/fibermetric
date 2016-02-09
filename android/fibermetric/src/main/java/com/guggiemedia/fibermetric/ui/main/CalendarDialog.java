package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.guggiemedia.fibermetric.R;


public class CalendarDialog extends DialogFragment {
    public static final String LOG_TAG = CalendarDialog.class.getName();

    public static final String FRAGMENT_TAG = "FRAGMENT_CALENDAR";

    CalendarView calendarView;

    public static CalendarDialog newInstance(Bundle bundle) {
        CalendarDialog fragment = new CalendarDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_calendar, container, false);

        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        calendarView.setSelectedWeekBackgroundColor(getResources().getColor(R.color.transparent));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                Toast.makeText(getActivity(), "Selected Date:\n" + "Day = " + i2 + "\n" + "Month = " + i1 + "\n" + "Year = " + i, Toast.LENGTH_LONG).show();

                dismiss();
            }
        });

        getDialog().setTitle("Choose a Date");


        return view;
    }
}