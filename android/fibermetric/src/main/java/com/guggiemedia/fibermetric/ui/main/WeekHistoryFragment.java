package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.db.ContentFacade;
import com.guggiemedia.fibermetric.db.HistoryModel;

import java.util.ArrayList;
import java.util.List;


public class WeekHistoryFragment extends Fragment implements FragmentContext {
    public static final String FRAGMENT_TAG = "FRAGMENT_HISTORY";

    private static final String TITLE = "Weekly History";

    public static final String LOG_TAG = WeekHistoryFragment.class.getName();

    private MainActivityListener _listener;

    private final ContentFacade _contentFacade = new ContentFacade();


    protected BarChart mChart;

    private Typeface tf;

    protected String[] _dayLabels = new String[]{
            "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"
    };


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

        List<HistoryModel> historyModels = _contentFacade.selectHistoryAll(getActivity());

        mChart = (BarChart) view.findViewById(R.id.chart1);

        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);

        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);

        YAxis yl = mChart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.3f);


        YAxis yr = mChart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);

        LimitLine limitLine = new LimitLine(35f, "Daily Recommended");
        limitLine.setLineWidth(4f);
        limitLine.enableDashedLine(10f, 10f, 0f);
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        limitLine.setTextSize(10f);
        limitLine.setTypeface(tf);

        setData(historyModels);

        yr.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        yr.addLimitLine(limitLine);

        mChart.animateY(1000);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

        return view;
    }


    private void setData(List<HistoryModel> historyModels) {

        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < historyModels.size(); i++) {
            xVals.add(_dayLabels[i % _dayLabels.length]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < historyModels.size(); i++) {
            HistoryModel model = historyModels.get(i);

            float values[] = {model.getFruit().floatValue(),
                    model.getVeg().floatValue(),
                    model.getGrain().floatValue()};

            yVals1.add(new BarEntry(values, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "grams (daily)");
        set1.setColors(getColors());
        set1.setStackLabels(new String[]{"Fruit", "Veg", "Grains"});

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);
        data.setValueTypeface(tf);


        mChart.setData(data);
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
