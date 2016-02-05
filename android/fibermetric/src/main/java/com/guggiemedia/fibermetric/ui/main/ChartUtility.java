package com.guggiemedia.fibermetric.ui.main;

import android.graphics.Typeface;

import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

/**
 * Created by donal on 2/4/16.
 */
public class ChartUtility {

    /**
     *
     * @param xAxis
     * @param tf
     */
    public static void configureXAxis(XAxis xAxis, Typeface tf) {
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setGridLineWidth(0.3f);
    }

    /**
     * @param yAxis
     */
    public static  void configureYAxis(YAxis yAxis) {
        yAxis.setAxisMaxValue(60f);
        yAxis.setAxisMinValue(0f);
        yAxis.setStartAtZero(false);

        yAxis.enableGridDashedLine(10f, 10f, 0f);
    }

    /**
     *
     * @param yAxis
     * @param tf
     */
    public static  void configureYAxisRight(YAxis yAxis, Typeface tf) {
        yAxis.setTypeface(tf);
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawGridLines(false);
    }

    /**
     * @param yAxis
     * @param tf
     */
    public static void addDailyLimitLine(YAxis yAxis, Typeface tf) {
        LimitLine limitLine = new LimitLine(40f, "Daily Recommended");
        limitLine.setLineWidth(4f);
        limitLine.enableDashedLine(10f, 10f, 0f);
        limitLine.setLineWidth(1f);
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        limitLine.setTextSize(10f);
        limitLine.setTypeface(tf);

        yAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        yAxis.addLimitLine(limitLine);
    }
}
