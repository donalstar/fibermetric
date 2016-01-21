package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;


import com.guggiemedia.fibermetric.lib.db.ChartModel;
import com.guggiemedia.fibermetric.lib.db.JobSiteModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.SiteModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by donal on 9/29/15.
 */
public class ChartGetMarkerDataCmd extends AbstractCmd {
    public static final String LOG_TAG = ChartGetMarkerDataCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final ChartGetMarkerDataCtx ctx = (ChartGetMarkerDataCtx) context;

        List<ChartModel.MarkerData> markerData = getChartMarkerData(ctx.getRowId(),
                ctx.getChartType(), ctx.getIsToday(), ctx.getAndroidContext());

        ctx.setMarkerData(markerData);

        return result;
    }


    public List<ChartModel.MarkerData> getChartMarkerData(Long id, ChartModel.ChartType chartType,
                                                          Boolean isToday, Context androidContext) {
        List<ChartModel.MarkerData> list = null;

        if (chartType != null) {
            switch (chartType) {
                case part:
                    list = getPartData(id, androidContext);
                    break;
                case jobSites:
                    list = getJobSiteData(isToday, androidContext);
                    break;
                case jobTask:
                    list = getJobData(id, androidContext);
                    break;
            }
        }

        return list;
    }

    private  List<ChartModel.MarkerData> getJobSiteData(Boolean isToday, Context androidContext) {
        List<ChartModel.MarkerData> list = new ArrayList<>();

        List<JobSiteModel> jobSites = (isToday)
                ? CommandFacade.jobSitesSelectByDeadlineDate(new Date(), androidContext)
                : CommandFacade.jobSitesSelectAll(androidContext);

        for (JobSiteModel jobSiteModel : jobSites) {
            SiteModel siteModel = jobSiteModel.getSite();

            if (siteModel != null) {
                ChartModel.MarkerData markerData = new ChartModel.MarkerData();

                JobTaskModel jobTask = jobSiteModel.getJobTask();

                markerData.location = siteModel.getLocation();
                markerData.title = jobTask.getName();

                markerData.rowId = jobTask.getId();

                markerData.markerImageData = new ChartModel.MarkerImageData();

                markerData.markerImageData.taskPriority = jobTask.getJobPriority();
                markerData.markerImageData.jobState = jobTask.getState();

                list.add(markerData);
            }
        }

        return list;
    }

    private  List<ChartModel.MarkerData> getPartData(Long id, Context androidContext) {
        List<ChartModel.MarkerData> list = new ArrayList<>();

        PartModel model = CommandFacade.partSelectByRowId(id, androidContext);
//        SiteModel siteModel = CommandFacade.siteSelectByRowId(model.getSiteId(), androidContext);

        ChartModel.MarkerData markerData = new ChartModel.MarkerData();

        markerData.location = model.getLocation();
        markerData.title = model.getName();

        markerData.rowId = id;

        markerData.markerImageData = new ChartModel.MarkerImageData();

        list.add(markerData);

        return list;
    }

    private  List<ChartModel.MarkerData> getJobData(Long id, Context androidContext) {
        List<ChartModel.MarkerData> list = new ArrayList<>();

        JobTaskModel model = CommandFacade.jobTaskSelectByRowId(id, androidContext);
        SiteModel siteModel = CommandFacade.siteSelectByRowId(model.getSite(), androidContext);

        ChartModel.MarkerData markerData = new ChartModel.MarkerData();

        if (siteModel != null) {
            markerData.location = siteModel.getLocation();
        }

        markerData.title = model.getName();
        markerData.rowId = id;

        markerData.markerImageData = new ChartModel.MarkerImageData();

        list.add(markerData);

        return list;
    }
}
