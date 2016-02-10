package com.guggiemedia.fibermetric.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.utility.ToastHelper;
import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;

import java.util.Calendar;
import java.util.Date;


public class CalendarDialog extends DialogFragment {
    public static final String LOG_TAG = CalendarDialog.class.getName();

    public static final String FRAGMENT_TAG = "FRAGMENT_CALENDAR";

    public static final String DATE_SELECT_FILTER = "DateSelect";

    CustomCalendarView _calendarView;

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

        _calendarView = (CustomCalendarView) view.findViewById(R.id.calendar_view);

        _calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                Log.i(LOG_TAG, "onDateSelected " + date);

                _selectedDate = date;

                long currentDateTime = (new Date()).getTime();

                if (_selectedDate != null && _selectedDate.getTime() < currentDateTime) {
                    dismiss();
                } else {
                    ToastHelper.show("Choose a date in the past", getActivity());
                }
            }

            @Override
            public void onMonthChanged(Date date) {

            }
        });

        _calendarView.refreshCalendar(Calendar.getInstance());

        _calendarView.markDayAsSelectedDay(new Date());
        _calendarView.markDayAsCurrentDay(Calendar.getInstance());

        final Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
        if (null != typeface) {
            _calendarView.setCustomTypeface(typeface);
            _calendarView.refreshCalendar(Calendar.getInstance());
        }

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