package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.guggiemedia.fibermetric.lib.db.ChainOfCustodyModel;
import com.guggiemedia.fibermetric.lib.db.ChartModel;
import com.guggiemedia.fibermetric.lib.db.ImageModel;
import com.guggiemedia.fibermetric.lib.db.InventoryCategoryEnum;
import com.guggiemedia.fibermetric.lib.db.JobSiteModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;
import com.guggiemedia.fibermetric.lib.db.JobTaskTable;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.PartTable;
import com.guggiemedia.fibermetric.lib.db.SiteModel;
import com.guggiemedia.fibermetric.lib.db.SiteTable;
import com.guggiemedia.fibermetric.lib.utility.ImageTypeEnum;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class CommandFacade {



    /**
     * Manage network connectivity
     *
     * @param androidContext
     */
    public static void connectionChange(Context androidContext) {
        CommandFactory.execute((ConnectionChangeCtx) ContextFactory.factory(CommandEnum.CONNECTION_CHANGE, androidContext));
    }

    public static void eventLog(String message, Context androidContext) {
        EventLogCtx ctx = (EventLogCtx) ContextFactory.factory(CommandEnum.EVENT_LOG, androidContext);
        ctx.setEvent(message);
        CommandFactory.execute(ctx);
    }

    public static List<PartModel> partSelectByCategory(InventoryCategoryEnum category, Context androidContext) {
        PartSelectByCategoryCtx ctx = (PartSelectByCategoryCtx) ContextFactory.factory(CommandEnum.PART_SELECT_BY_CATEGORY, androidContext);
        ctx.setCategory(category);

        CommandFactory.execute(ctx);
        return ctx.getSelected();
    }

    /**
     * Select a part by row ID
     *
     * @param rowId
     * @param androidContext
     * @return
     */
    public static PartModel partSelectByRowId(long rowId, Context androidContext) {
        SelectByRowIdCtx ctx = (SelectByRowIdCtx) ContextFactory.factory(CommandEnum.SELECT_BY_ROW_ID, androidContext);
        ctx.setRowId(rowId);
        ctx.setTable(new PartTable());
        CommandFactory.execute(ctx);
        return (PartModel) ctx.getSelected();
    }



    /**
     * @param androidContext
     * @return
     */
    public static List<JobSiteModel> jobSitesSelectAll(Context androidContext) {
        JobSitesSelectCtx ctx = (JobSitesSelectCtx) ContextFactory.factory(CommandEnum.JOBSITES_SELECT, androidContext);

        CommandFactory.execute(ctx);

        return ctx.getJobSites();
    }

    /**
     * @param date
     * @param androidContext
     * @return
     */
    public static List<JobSiteModel> jobSitesSelectByDeadlineDate(Date date, Context androidContext) {
        Log.i("CommandFacade", "jobSitesSelectByDeadlineDate");

        JobSitesSelectCtx ctx = (JobSitesSelectCtx) ContextFactory.factory(CommandEnum.JOBSITES_SELECT, androidContext);

        ctx.setDeadlineDate(date);

        CommandFactory.execute(ctx);

        return ctx.getJobSites();
    }

    /**
     *
     * @param rowId
     * @param androidContext
     */
    public static void jobTaskScheduled(long rowId, Context androidContext) {
        JobTaskScheduledCtx ctx = (JobTaskScheduledCtx) ContextFactory.factory(CommandEnum.JOBTASK_SCHEDULED, androidContext);
        ctx.setId(rowId);
        CommandFactory.execute(ctx);
    }

    /**
     * Given a job, mark it as active
     *
     * @param rowId          task row id
     * @param androidContext
     */
    public static void jobTaskActive(long rowId, Context androidContext) {
        JobTaskActiveCtx ctx = (JobTaskActiveCtx) ContextFactory.factory(CommandEnum.JOBTASK_ACTIVE, androidContext);
        ctx.setId(rowId);
        CommandFactory.execute(ctx);
    }

    /**
     * Given a task, mark it as complete
     *
     * @param rowId          task row id
     * @param androidContext
     */
    public static void jobTaskComplete(long rowId, Context androidContext) {
        JobTaskCompleteCtx ctx = (JobTaskCompleteCtx) ContextFactory.factory(CommandEnum.JOBTASK_COMPLETE, androidContext);
        ctx.setId(rowId);
        CommandFactory.execute(ctx);
    }


    /**
     * Given a Job, mark it as suspended
     *
     * @param rowId
     * @param androidContext
     */
    public static void jobTaskSuspend(long rowId, Context androidContext) {
        JobTaskSuspendCtx ctx = (JobTaskSuspendCtx) ContextFactory.factory(CommandEnum.JOBTASK_SUSPEND, androidContext);
        ctx.setId(rowId);
        CommandFactory.execute(ctx);
    }

    /**
     * Calculate total job duration
     *
     * @param todayOnly      true, return duration for today else all jobtask
     * @param androidContext
     * @return duration in minutes
     */
    public static JobTaskDurationCtx jobTaskDuration(boolean todayOnly, Context androidContext) {
        JobTaskDurationCtx ctx = (JobTaskDurationCtx) ContextFactory.factory(CommandEnum.JOBTASK_DURATION, androidContext);
        ctx.setTodayOnly(todayOnly);
        CommandFactory.execute(ctx);
        return ctx;
    }

    /**
     * Given a job, return the next task to be performed
     *
     * @param rowId          parent job row id
     * @param androidContext
     * @return next task
     */
    public static JobTaskModel jobTaskNext(long rowId, Context androidContext) {
        JobTaskNextCtx ctx = (JobTaskNextCtx) ContextFactory.factory(CommandEnum.JOBTASK_NEXT, androidContext);
        ctx.setJobId(rowId);
        CommandFactory.execute(ctx);
        return ctx.getNextTask();
    }

    /**
     * Return active jobs, should be one or zero
     *
     * @param androidContext
     */
    public static JobTaskModelList jobTaskSelectActive(Context androidContext) {
        JobTaskSelectActiveCtx ctx = (JobTaskSelectActiveCtx) ContextFactory.factory(CommandEnum.JOBTASK_SELECT_ACTIVE, androidContext);
        CommandFactory.execute(ctx);
        return ctx.getSelected();
    }

    /**
     * Select a job/task by row ID
     *
     * @param rowId
     * @param androidContext
     * @return
     */
    public static JobTaskModel jobTaskSelectByRowId(long rowId, Context androidContext) {
        SelectByRowIdCtx ctx = (SelectByRowIdCtx) ContextFactory.factory(CommandEnum.SELECT_BY_ROW_ID, androidContext);
        ctx.setRowId(rowId);
        ctx.setTable(new JobTaskTable());
        CommandFactory.execute(ctx);
        return (JobTaskModel) ctx.getSelected();
    }

    /**
     * Select a task by parent ID
     *
     * @param parentId
     * @param androidContext
     * @return
     */
    public static JobTaskModelList jobTaskSelectByParent(long parentId, Context androidContext) {
        JobTaskSelectByParentCtx ctx = (JobTaskSelectByParentCtx) ContextFactory.factory(CommandEnum.JOBTASK_SELECT_BY_PARENT, androidContext);
        ctx.setParentId(parentId);
        CommandFactory.execute(ctx);
        return ctx.getSelected();
    }


    public static SiteModel siteSelectByRowId(long rowId, Context androidContext) {
        SelectByRowIdCtx ctx = (SelectByRowIdCtx) ContextFactory.factory(CommandEnum.SELECT_BY_ROW_ID, androidContext);
        ctx.setRowId(rowId);
        ctx.setTable(new SiteTable());
        CommandFactory.execute(ctx);
        return (SiteModel) ctx.getSelected();
    }


    /**
     * @param model
     */
    public static void partStatusUpdate(PartModel model, String newCustodian, Long pickupSiteId,
                                        Context androidContext) {
        PartUpdateCtx ctx = (PartUpdateCtx) ContextFactory.factory(CommandEnum.PART_UPDATE, androidContext);


        model.setSiteId(pickupSiteId);

        ctx.setModel(model);

        CommandFactory.execute(ctx);


    }


    public static List<ChartModel.MarkerData> getChartMarkerData(Long id,
                                                                 ChartModel.ChartType chartType,
                                                                 Boolean isToday,
                                                                 Context androidContext) {
        ChartGetMarkerDataCtx ctx = (ChartGetMarkerDataCtx) ContextFactory.factory(CommandEnum.CHART_GET_MARKER_DATA, androidContext);

        ctx.setChartType(chartType);
        ctx.setIsToday(isToday);
        ctx.setRowId(id);

        CommandFactory.execute(ctx);

        return ctx.getMarkerData();
    }
}