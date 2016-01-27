package com.guggiemedia.fibermetric.lib.db;

import android.location.Location;


/**
 * Created by donal on 11/10/15.
 */
public class ChartModel {
    public static class MarkerData {
        public String title;
        public Location location;
        public Long rowId;

        public MarkerImageData markerImageData;
    }

    public static class MarkerImageData {
        public Integer mainImageId;
        public JobTaskPriorityEnum taskPriority;
        public JobStateEnum jobState;
    }

    public enum ChartType {
        part,
        jobSites,
        jobTask
    }

}
