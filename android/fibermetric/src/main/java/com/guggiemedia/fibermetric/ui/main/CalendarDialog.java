package com.guggiemedia.fibermetric.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.utility.ToastHelper;

import java.util.Calendar;
import java.util.Date;


public class CalendarDialog extends DialogFragment {
    public static final String LOG_TAG = CalendarDialog.class.getName();

    public static final String FRAGMENT_TAG = "FRAGMENT_CALENDAR";

    public static final String DATE_SELECT_FILTER = "DateSelect";

    CalendarView calendarView;

    Date _selectedDate;

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
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);

                _selectedDate = calendar.getTime();

                long currentDateTime = (new Date()).getTime();

                if (_selectedDate != null && _selectedDate.getTime() < currentDateTime) {
                    dismiss();
                } else {
                    ToastHelper.show("Choose a date in the past", getActivity());
                }

            }
        });

        Button cancelButton = (Button) view.findViewById(R.id.cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getDialog().setTitle("Choose a Date");

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        long currentDateTime = (new Date()).getTime();

        if (_selectedDate != null && _selectedDate.getTime() < currentDateTime) {
            Intent intent = new Intent(DATE_SELECT_FILTER);

            intent.putExtra("date", _selectedDate.getTime());

            getActivity().sendBroadcast(intent);
        }
    }
}