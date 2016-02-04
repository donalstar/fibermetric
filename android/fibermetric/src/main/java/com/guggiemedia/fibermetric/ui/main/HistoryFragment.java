package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.db.ContentFacade;
import com.guggiemedia.fibermetric.db.HistoryModel;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment implements FragmentContext {
    public static final String FRAGMENT_TAG = "FRAGMENT_HISTORY";

    private static final String TITLE = "History";

    public static final String LOG_TAG = HistoryFragment.class.getName();

    private MainActivityListener _listener;

    private final ContentFacade _contentFacade = new ContentFacade();
    
    private LineChart mChart;


    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        List<HistoryModel> historyModels = _contentFacade.selectHistoryAll(getActivity());

        mChart = (LineChart) view.findViewById(R.id.chart1);


        mChart.setDrawGridBackground(false);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);


        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        LimitLine ll1 = new LimitLine(40f, "Daily Recommended");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setTypeface(tf);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);

        leftAxis.setAxisMaxValue(60f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setStartAtZero(false);

        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        // add data
        setData(historyModels);

        mChart.animateX(1000, Easing.EasingOption.EaseInOutQuart);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        l.setForm(Legend.LegendForm.LINE);


        return view;
    }

    private void setData(List<HistoryModel> historyModels) {

        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < historyModels.size(); i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<>();


        for (int i = 0; i < historyModels.size(); i++) {
            HistoryModel model = historyModels.get(i);

            yVals.add(new Entry(model.getTotal().floatValue(), i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "Daily");


        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);

        set1.setFillColor(Color.rgb(135, 211, 124));

        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);

        set1.setDrawFilled(true);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_history, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

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

    @Override
    public String getName() {
        return TITLE;
    }

    @Override
    public int getHomeIndicator() {
        return R.drawable.ic_menu_white;
    }
}
