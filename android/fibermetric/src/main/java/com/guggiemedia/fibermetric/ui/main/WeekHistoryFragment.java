package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.db.ContentFacade;
import com.guggiemedia.fibermetric.db.HistoryModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WeekHistoryFragment extends Fragment implements FragmentContext {
    public static final String FRAGMENT_TAG = "FRAGMENT_HISTORY";

    private static final String TITLE = "Weekly History";

    public static final String LOG_TAG = WeekHistoryFragment.class.getName();

    private MainActivityListener _listener;

    private final ContentFacade _contentFacade = new ContentFacade();

    private Typeface tf;

    public static WeekHistoryFragment newInstance() {
        return new WeekHistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_week_history, container, false);

        TextView titleView = (TextView) view.findViewById(R.id.title);

        List<HistoryModel> historyModels = getData();

        HistoryModel firstDayModel = historyModels.get(0);
        HistoryModel lastDayModel = historyModels.get(historyModels.size() - 1);

        SimpleDateFormat format = new SimpleDateFormat("EEE MM/dd");

        String firstDay = format.format(firstDayModel.getDate());
        String lastDay = format.format(lastDayModel.getDate());

        titleView.setText("Daily fiber intake - " + firstDay + " to " + lastDay);

        BarChart chart = (BarChart) view.findViewById(R.id.chart);

        configureChart(chart, historyModels);

        return view;
    }

    /**
     *
     * @return
     */
    private List<HistoryModel> getData() {
        Date now = new Date();

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, -8);

        return _contentFacade.selectHistoryByDate(getActivity(), calendar.getTime(), now);
    }

    /**
     * @param chart
     * @param historyModels
     */
    private void configureChart(BarChart chart, List<HistoryModel> historyModels) {
        chart.setDescription("");

        chart.setDrawBarShadow(false);

        chart.setDrawValueAboveBar(true);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);

        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        XAxis xl = chart.getXAxis();

        ChartUtility.configureXAxis(xl, tf);

        YAxis yl = chart.getAxisLeft();

        ChartUtility.configureYAxis(yl);

        YAxis yAxisRight = chart.getAxisRight();

        ChartUtility.configureYAxisRight(yAxisRight, tf);

        ChartUtility.addDailyLimitLine(yAxisRight, tf);

        BarData data = getChartData(historyModels);

        chart.setData(data);

        chart.animateY(1000);

        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
    }

    private BarData getChartData(List<HistoryModel> historyModels) {

        SimpleDateFormat format = new SimpleDateFormat("EEE");

        List<String> xValues = new ArrayList<>();

        for (HistoryModel model : historyModels) {
            String dateLabel = format.format(model.getDate());


            xValues.add(dateLabel);
        }

        List<BarEntry> yValues = new ArrayList<>();

        for (int i = 0; i < historyModels.size(); i++) {
            HistoryModel model = historyModels.get(i);

            float values[] = {model.getFruit().floatValue(),
                    model.getVeg().floatValue(),
                    model.getGrain().floatValue()};

            yValues.add(new BarEntry(values, i));
        }

        BarDataSet set1 = new BarDataSet(yValues, "grams (daily)");
        set1.setColors(getColors());
        set1.setStackLabels(new String[]{"Fruit", "Veg", "Grains"});

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xValues, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(tf);

        return data;
    }

    private int[] getColors() {

        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < stacksize; i++) {
            colors[i] = ColorTemplate.VORDIPLOM_COLORS[i];
        }

        return colors;
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
