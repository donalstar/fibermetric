package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ChartModel;

import java.util.List;

/**
 * get data used to display map markers for jobs/sites/parts
 */
public class ChartGetMarkerDataCtx extends AbstractCmdCtx {
    private Long _rowId;
    private ChartModel.ChartType _chartType;
    private List<ChartModel.MarkerData> _markerData;
    private Boolean _isToday;

    public ChartGetMarkerDataCtx(Context androidContext) {
        super(CommandEnum.CHART_GET_MARKER_DATA, androidContext);
    }

    public Long getRowId() {
        return _rowId;
    }

    public void setRowId(Long rowId) {
        this._rowId = rowId;
    }

    public ChartModel.ChartType getChartType() {
        return _chartType;
    }

    public void setChartType(ChartModel.ChartType chartType) {
        this._chartType = chartType;
    }

    public void setIsToday(Boolean isToday) {
        _isToday = isToday;
    }

    public Boolean getIsToday() {
        return _isToday;
    }

    public List<ChartModel.MarkerData> getMarkerData() {
        return _markerData;
    }

    public void setMarkerData(List<ChartModel.MarkerData> markerData) {
        this._markerData = markerData;
    }
}
