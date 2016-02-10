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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.db.ContentFacade;
import com.guggiemedia.fibermetric.db.DailyRecordModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment implements FragmentContext {
    public static final String FRAGMENT_TAG = "FRAGMENT_HISTORY";

    private static final String TITLE = "History";

    public static final String LOG_TAG = HistoryFragment.class.getName();

    private MainActivityListener _listener;

    private final ContentFacade _contentFacade = new ContentFacade();

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

        List<DailyRecordModel> dailyRecordModels = _contentFacade.selectDailyRecordAll(getActivity());

        LineChart chart = (LineChart) view.findViewById(R.id.chart);

        configureChart(chart, dailyRecordModels);

        return view;
    }

    /**
     * @param chart
     * @param dailyRecordModels
     */
    private void configureChart(LineChart chart, List<DailyRecordModel> dailyRecordModels) {
        chart.setDrawGridBackground(false);

        // no description text
        chart.setDescription("");
        chart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        XAxis xl = chart.getXAxis();

        ChartUtility.configureXAxis(xl, tf);

        YAxis yAxis = chart.getAxisLeft();

        ChartUtility.configureYAxis(yAxis);

        YAxis yAxisRight = chart.getAxisRight();

        ChartUtility.configureYAxisRight(yAxisRight, tf);

        ChartUtility.addDailyLimitLine(yAxis, tf);

        chart.getAxisRight().setEnabled(false);

        // add data
        LineData data = getChartData(dailyRecordModels);

        // set data
        chart.setData(data);

        chart.animateX(1000, Easing.EasingOption.EaseInOutQuart);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        l.setForm(Legend.LegendForm.LINE);
    }

    private LineData getChartData(List<DailyRecordModel> dailyRecordModels) {


        SimpleDateFormat format = new SimpleDateFormat("MM/dd");

        List<String> xValues = new ArrayList<>();

        for (DailyRecordModel model : dailyRecordModels) {
            String dateLabel = format.format(model.getDate());


            xValues.add(dateLabel);
        }

//        List<String> xValues = new ArrayList<>();
//        for (int i = 0; i < dailyRecordModels.size(); i++) {
//            xValues.add((i) + "x");
//        }

        List<Entry> totals = new ArrayList<>();

        for (int i = 0; i < dailyRecordModels.size(); i++) {
            DailyRecordModel model = dailyRecordModels.get(i);

            totals.add(new Entry(model.getTotal().floatValue(), i));
        }

        List<Entry> fruit = new ArrayList<>();

        for (int i = 0; i < dailyRecordModels.size(); i++) {
            DailyRecordModel model = dailyRecordModels.get(i);

            fruit.add(new Entry(model.getFruit().floatValue() + model.getVeg().floatValue(), i));
        }

        List<Entry> veg = new ArrayList<>();

        for (int i = 0; i < dailyRecordModels.size(); i++) {
            DailyRecordModel model = dailyRecordModels.get(i);

            veg.add(new Entry(model.getVeg().floatValue(), i));
        }

        int greenColor = Color.rgb(135, 211, 124);


        LineDataSet fruitSet = new LineDataSet(fruit, "fruit");

        // set the line to be drawn like this "- - - - - -"
        fruitSet.enableDashedLine(10f, 5f, 0f);
        fruitSet.enableDashedHighlightLine(10f, 5f, 0f);
        fruitSet.setColor(greenColor);
        fruitSet.setLineWidth(1f);

        fruitSet.setFillColor(Color.rgb(135, 211, 124));

        fruitSet.setDrawCubic(true);
        fruitSet.setDrawCircles(false);
        fruitSet.setDrawValues(false);

        fruitSet.setFillColor(greenColor);
        fruitSet.setDrawFilled(true);

        int yellowColor = Color.rgb(245, 215, 110);

        LineDataSet vegSet = new LineDataSet(veg, "veg");

        // set the line to be drawn like this "- - - - - -"
        vegSet.enableDashedLine(10f, 5f, 0f);
        vegSet.enableDashedHighlightLine(10f, 5f, 0f);
        vegSet.setColor(yellowColor);
        vegSet.setLineWidth(1f);

        vegSet.setFillColor(yellowColor);

        vegSet.setDrawCubic(true);
        vegSet.setDrawCircles(false);
        vegSet.setDrawValues(false);

        vegSet.setFillColor(yellowColor);
        vegSet.setDrawFilled(true);

        int orangeColor = Color.rgb(244, 179, 80);

        LineDataSet totalSet = new LineDataSet(totals, "grain");

        totalSet.enableDashedLine(10f, 5f, 0f);
        totalSet.enableDashedHighlightLine(10f, 5f, 0f);
        totalSet.setColor(orangeColor);
        totalSet.setLineWidth(1.5f);
        totalSet.setFillColor(orangeColor);
        totalSet.setDrawFilled(true);
        totalSet.setDrawCubic(true);
        totalSet.setDrawCircles(false);
        totalSet.setDrawValues(false);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(totalSet);

        dataSets.add(fruitSet);

        dataSets.add(vegSet);

        return new LineData(xValues, dataSets);
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
