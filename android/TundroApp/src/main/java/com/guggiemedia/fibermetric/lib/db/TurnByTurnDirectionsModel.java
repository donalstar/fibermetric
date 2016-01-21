package com.guggiemedia.fibermetric.lib.db;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

/**
 * Created by donal on 9/28/15.
 */
public class TurnByTurnDirectionsModel implements Serializable {

    private List<String> _steps;
    private String _duration;
    private String _distance;
    private String raw;
    private List<LatLng> _polyline;

    public List<String> getSteps() {
        return _steps;
    }

    public void setSteps(List<String> steps) {
        this._steps = steps;
    }

    public String getDuration() {
        return _duration;
    }

    public void setDuration(String duration) {
        this._duration = duration;
    }

    public String getDistance() {
        return _distance;
    }

    public void setDistance(String distance) {
        this._distance = distance;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public List<LatLng> getPolyline() {
        return _polyline;
    }

    public void setPolyline(List<LatLng> polyline) {
        this._polyline = polyline;
    }
}
