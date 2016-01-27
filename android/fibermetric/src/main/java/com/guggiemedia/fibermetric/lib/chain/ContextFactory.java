package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;


/**
 *
 */
public class ContextFactory {
    public static final String LOG_TAG = ContextFactory.class.getName();

    public static AbstractCmdCtx factory(CommandEnum command, Context androidContext) {
        AbstractCmdCtx result = null;

        switch(command) {


            case CHART_GET_MARKER_DATA:
                result = new ChartGetMarkerDataCtx(androidContext);
                break;

            case CONNECTION_CHANGE:
                result = new ConnectionChangeCtx(androidContext);
                break;
            case EVENT_LOG:
                result = new EventLogCtx(androidContext);
                break;

            case HEART_BEAT:
                result = new HeartBeatCtx(androidContext);
                break;

            case PART_SELECT_BY_CATEGORY:
                result = new PartSelectByCategoryCtx(androidContext);
                break;
            case JOBSITES_SELECT:
                result = new JobSitesSelectCtx(androidContext);
                break;
            case JOBTASK_ACTIVE:
                result = new JobTaskActiveCtx(androidContext);
                break;
            case JOBTASK_COMPLETE:
                result = new JobTaskCompleteCtx(androidContext);
                break;
            case JOBTASK_DURATION:
                result = new JobTaskDurationCtx(androidContext);
                break;
            case JOBTASK_NEXT:
                result = new JobTaskNextCtx(androidContext);
                break;
            case JOBTASK_SCHEDULED:
                result = new JobTaskScheduledCtx(androidContext);
                break;
            case JOBTASK_SELECT_ACTIVE:
                result = new JobTaskSelectActiveCtx(androidContext);
                break;
            case JOBTASK_SELECT_BY_JOB_ID:
                result = new JobTaskSelectByJobIdCtx(androidContext);
                break;
            case JOBTASK_SELECT_BY_PARENT:
                result = new JobTaskSelectByParentCtx(androidContext);
                break;
            case JOBTASK_SELECT_TODAY:
                result = new JobTaskSelectTodayCtx(androidContext);
                break;
            case JOBTASK_SUSPEND:
                result = new JobTaskSuspendCtx(androidContext);
                break;


            case MESSAGE_SEND:
                result = new MessageSendCtx(androidContext);
                break;
            case PART_REPLACE_BARCODE:
                result = new PartReplaceBarcodeCtx(androidContext);
                break;
            case PART_UPDATE:
                result = new PartUpdateCtx(androidContext);
                break;

            case SELECT_BY_ROW_ID:
                result = new SelectByRowIdCtx(androidContext);
                break;

            default:
                throw new IllegalArgumentException("unknown command:" + command);
        }

        return result;
    }
}
