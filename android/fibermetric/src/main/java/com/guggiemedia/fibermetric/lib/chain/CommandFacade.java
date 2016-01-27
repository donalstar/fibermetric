package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.guggiemedia.fibermetric.lib.Personality;
import com.guggiemedia.fibermetric.lib.db.ChainOfCustodyModel;
import com.guggiemedia.fibermetric.lib.db.ChartModel;
import com.guggiemedia.fibermetric.lib.db.ChatMessageModel;
import com.guggiemedia.fibermetric.lib.db.ImageModel;
import com.guggiemedia.fibermetric.lib.db.InventoryCategoryEnum;
import com.guggiemedia.fibermetric.lib.db.JobSiteModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;
import com.guggiemedia.fibermetric.lib.db.JobTaskTable;
import com.guggiemedia.fibermetric.lib.db.KeyList;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.PartTable;
import com.guggiemedia.fibermetric.lib.db.PersonModel;
import com.guggiemedia.fibermetric.lib.db.SiteModel;
import com.guggiemedia.fibermetric.lib.db.SiteTable;
import com.guggiemedia.fibermetric.lib.db.TaskActionModelList;
import com.guggiemedia.fibermetric.lib.db.TaskActionStateEnum;
import com.guggiemedia.fibermetric.lib.db.TaskDetailModelList;
import com.guggiemedia.fibermetric.lib.db.ThingModel;
import com.guggiemedia.fibermetric.lib.db.ThingTable;
import com.guggiemedia.fibermetric.lib.db.TurnByTurnDirectionsModel;
import com.guggiemedia.fibermetric.lib.utility.ImageTypeEnum;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class CommandFacade {

    /**
     * Validate a login attempt.  Issue 44
     *
     * @param userName
     * @param passWord
     * @param androidContext
     * @return true, success else failed login attempt
     */
    public static boolean authenticationSignIn(String userName, String passWord, Context androidContext) {
        AuthenticationSignInCtx ctx = (AuthenticationSignInCtx) ContextFactory.factory(CommandEnum.AUTHENTICATION_SIGN_IN, androidContext);
        ctx.setUserName(userName);
        ctx.setPassWord(passWord);
        CommandFactory.execute(ctx);
        return ctx.isHappyFlag();
    }

    /**
     * Logout current user.  Issue 18
     *
     * @param androidContext
     */
    public static void authenticationSignOut(Context androidContext) {
        AuthenticationSignOutCtx ctx = (AuthenticationSignOutCtx) ContextFactory.factory(CommandEnum.AUTHENTICATION_SIGN_OUT, androidContext);
        CommandFactory.execute(ctx);
    }

    /**
     * Validate current user.  Can be revoked at any time.  Issue 45
     *
     * @param androidContext
     * @return true, legal happy user else failure.
     */
    public static boolean authenticationTest(Context androidContext) {
        AuthenticationTestCtx ctx = (AuthenticationTestCtx) ContextFactory.factory(CommandEnum.AUTHENTICATION_TEST, androidContext);
        CommandFactory.execute(ctx);
        return ctx.isHappyFlag();
    }

    /**
     * Power battery charge level report
     *
     * @param chargeLevel    as percentage of battery
     * @param androidContext
     */
    public static void batteryReport(int chargeLevel, Context androidContext) {
        BatteryUpdateCtx ctx = (BatteryUpdateCtx) ContextFactory.factory(CommandEnum.BATTERY_UPDATE, androidContext);
        ctx.setChargeLevel(chargeLevel);
        CommandFactory.execute(ctx);
    }

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
     * Process a fresh image
     *
     * @param model
     * @param androidContext
     * @return populated ImageModel
     */
    public static ImageModel imageFresh(ImageModel model, Context androidContext) {
        ImageFreshCtx ctx = (ImageFreshCtx) ContextFactory.factory(CommandEnum.IMAGE_FRESH, androidContext);
        ctx.setImageModel(model);
        CommandFactory.execute(ctx);
        return ctx.getImageModel();
    }

    /**
     * Retrieve specified bitmap
     *
     * @param imageFile
     * @param imageType
     * @param androidContext
     * @return
     */
    public static Bitmap imageGet(File imageFile, ImageTypeEnum imageType, Context androidContext) {
        ImageGetCtx ctx = (ImageGetCtx) ContextFactory.factory(CommandEnum.IMAGE_GET, androidContext);
        ctx.setOriginalFile(imageFile);
        ctx.setImageType(imageType);
        CommandFactory.execute(ctx);
        return ctx.getBitmap();
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

    /**
     * Return jobs for today
     *
     * @param androidContext
     */
    public static JobTaskModelList jobTaskSelectToday(Context androidContext) {
        JobTaskSelectTodayCtx ctx = (JobTaskSelectTodayCtx) ContextFactory.factory(CommandEnum.JOBTASK_SELECT_TODAY, androidContext);
        CommandFactory.execute(ctx);
        return ctx.getSelected();
    }

    /**
     * Update or create a job
     *
     * @param jobTask
     * @param detailList
     * @param androidContext
     */
    public static void jobTaskUpdate(JobTaskModel jobTask, Context androidContext) {
        JobTaskUpdateCtx ctx = (JobTaskUpdateCtx) ContextFactory.factory(CommandEnum.JOBTASK_UPDATE, androidContext);
        ctx.setJobTask(jobTask);
        CommandFactory.execute(ctx);
    }

    /**
     * Update or create task details
     *
     * @param jobTask        task
     * @param actionList     task action
     * @param androidContext
     */
    public static void jobTaskActionUpdate(JobTaskModel jobTask, TaskActionModelList actionList, Context androidContext) {
        JobTaskUpdateCtx ctx = (JobTaskUpdateCtx) ContextFactory.factory(CommandEnum.JOBTASK_UPDATE, androidContext);
        ctx.setJobTask(jobTask);
        ctx.setActionList(actionList);
        CommandFactory.execute(ctx);
    }

    /**
     * Update or create task details
     *
     * @param jobTask        task
     * @param taskList       task detaiæl
     * @param androidContext
     */
    public static void jobTaskDetailUpdate(JobTaskModel jobTask, TaskDetailModelList detailList, Context androidContext) {
        JobTaskUpdateCtx ctx = (JobTaskUpdateCtx) ContextFactory.factory(CommandEnum.JOBTASK_UPDATE, androidContext);
        ctx.setJobTask(jobTask);
        ctx.setDetailList(detailList);
        CommandFactory.execute(ctx);
    }

    /**
     * Selext tasks ID by parent job id
     *
     * @param jobId
     * @param androidContext
     * @return
     */
    public static KeyList jobTaskSelectByJobId(long jobId, Context androidContext) {
        JobTaskSelectByJobIdCtx ctx = (JobTaskSelectByJobIdCtx) ContextFactory.factory(CommandEnum.JOBTASK_SELECT_BY_JOB_ID, androidContext);
        ctx.setJobId(jobId);
        CommandFactory.execute(ctx);
        return ctx.getSelected();
    }

    public static PersonModel personSelectByUserName(String userName, Context androidContext) {
        PersonSelectByUserNameCtx ctx = (PersonSelectByUserNameCtx) ContextFactory.factory(CommandEnum.PERSON_SELECT_BY_USER_NAME, androidContext);
        ctx.setUserName(userName);
        CommandFactory.execute(ctx);
        return ctx.getSelected();
    }

    public static void personUpdate(PersonModel model, Context androidContext) {
        PersonUpdateCtx ctx = (PersonUpdateCtx) ContextFactory.factory(CommandEnum.PERSON_UPDATE, androidContext);
        ctx.setModel(model);
        CommandFactory.execute(ctx);
    }

    public static void chatMessageUpdate(ChatMessageModel model, Context androidContext) {
        ChatMessageUpdateCtx ctx = (ChatMessageUpdateCtx) ContextFactory.factory(CommandEnum.CHAT_MSG_UPDATE, androidContext);
        ctx.setModel(model);
        CommandFactory.execute(ctx);
    }

    public static SiteModel siteSelectByRowId(long rowId, Context androidContext) {
        SelectByRowIdCtx ctx = (SelectByRowIdCtx) ContextFactory.factory(CommandEnum.SELECT_BY_ROW_ID, androidContext);
        ctx.setRowId(rowId);
        ctx.setTable(new SiteTable());
        CommandFactory.execute(ctx);
        return (SiteModel) ctx.getSelected();
    }


    /**
     * Return the task actions
     *
     * @param parentId       task ID
     * @param androidContext
     * @return
     */
    public static TaskActionModelList taskActionSelectByParent(long parentId, Context androidContext) {
        TaskActionSelectByParentCtx ctx = (TaskActionSelectByParentCtx) ContextFactory.factory(CommandEnum.TASK_ACTION_SELECT_BY_PARENT, androidContext);
        ctx.setParentId(parentId);
        CommandFactory.execute(ctx);
        return ctx.getSelected();
    }

    /**
     * update task action
     *
     * @param freshState
     * @param actionId
     * @param androidContext
     */
    public static void taskActionUpdate(TaskActionStateEnum freshState, long actionId, Context androidContext) {
        TaskActionUpdateCtx ctx = (TaskActionUpdateCtx) ContextFactory.factory(CommandEnum.TASK_ACTION_UPDATE, androidContext);
        ctx.setActionId(actionId);
        ctx.setState(freshState);
        CommandFactory.execute(ctx);
    }

    /**
     * Return the task detail (steps)
     *
     * @param parentId       task ID
     * @param androidContext
     * @return
     */
    public static TaskDetailModelList taskDetailSelectByParent(long parentId, Context androidContext) {
        TaskDetailSelectByParentCtx ctx = (TaskDetailSelectByParentCtx) ContextFactory.factory(CommandEnum.TASK_DETAIL_SELECT_BY_PARENT, androidContext);
        ctx.setParentId(parentId);
        CommandFactory.execute(ctx);
        return ctx.getSelected();
    }

    public static ThingModel thingSelectByRowId(long rowId, Context androidContext) {
        SelectByRowIdCtx ctx = (SelectByRowIdCtx) ContextFactory.factory(CommandEnum.SELECT_BY_ROW_ID, androidContext);
        ctx.setRowId(rowId);
        ctx.setTable(new ThingTable());
        CommandFactory.execute(ctx);
        return (ThingModel) ctx.getSelected();
    }

    public static PartModel updatePartStatusByBarcode(String barcode, PersonModel personModel, Context androidContext) {
        PartUpdateByBarcodeCtx ctx = (PartUpdateByBarcodeCtx) ContextFactory.factory(CommandEnum.PART_UPDATE_BY_BARCODE, androidContext);

        ctx.setBarcode(barcode);
        ctx.setPersonModel(personModel);

        CommandFactory.execute(ctx);

        PartModel partModel = ctx.getPartModel();

        partStatusUpdate(partModel, personModel.getName(), 0L, androidContext);

        return partModel;
    }

    public static PartModel updateBarcodeForPart(long partId, String barcode, Context androidContext) {
        PartReplaceBarcodeCtx ctx = (PartReplaceBarcodeCtx) ContextFactory.factory(CommandEnum.PART_REPLACE_BARCODE, androidContext);

        ctx.setPartId(partId);
        ctx.setBarcode(barcode);
        CommandFactory.execute(ctx);

        return ctx.getPartModel();
    }

    /**
     * @param model
     */
    public static void partStatusUpdate(PartModel model, String newCustodian, Long pickupSiteId,
                                        Context androidContext) {
        PartUpdateCtx ctx = (PartUpdateCtx) ContextFactory.factory(CommandEnum.PART_UPDATE, androidContext);

        Location location = Personality.currentLocation;

        model.setLocation(location);
        model.setSiteId(pickupSiteId);

        ctx.setModel(model);

        CommandFactory.execute(ctx);

        // add a Chain of Custody entry
        ChainOfCustodyModel chainOfCustodyModel = new ChainOfCustodyModel();
        chainOfCustodyModel.setDefault();

        chainOfCustodyModel.setPartId(model.getId());
        chainOfCustodyModel.setCustodian(newCustodian);
        chainOfCustodyModel.setPickupDate(new Date());

        chainOfCustodyModel.setLocation(location);
        chainOfCustodyModel.setPickupSiteId(pickupSiteId);

        chainOfCustodyUpdate(chainOfCustodyModel, androidContext);
    }

    /**
     * @param model
     * @param androidContext
     */
    public static void chainOfCustodyUpdate(ChainOfCustodyModel model, Context androidContext) {
        ChainOfCustodyUpdateCtx ctx
                = (ChainOfCustodyUpdateCtx) ContextFactory.factory(CommandEnum.CHAIN_OF_CUSTODY_UPDATE, androidContext);

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