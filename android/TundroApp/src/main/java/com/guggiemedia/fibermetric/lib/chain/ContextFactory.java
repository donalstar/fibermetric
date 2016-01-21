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
            case ALERT_BUMP_COUNTER:
                result = new AlertBumpCounterCtx(androidContext);
                break;
            case ALERT_STOP:
                result = new AlertStopCtx(androidContext);
                break;
            case AUTHENTICATION_SIGN_IN:
                result = new AuthenticationSignInCtx(androidContext);
                break;
            case AUTHENTICATION_SIGN_OUT:
                result = new AuthenticationSignOutCtx(androidContext);
                break;
            case AUTHENTICATION_TEST:
                result = new AuthenticationTestCtx(androidContext);
                break;
            case BATTERY_UPDATE:
                result = new BatteryUpdateCtx(androidContext);
                break;
            case CHAIN_OF_CUSTODY_UPDATE:
                result = new ChainOfCustodyUpdateCtx(androidContext);
                break;
            case CHART_GET_MARKER_DATA:
                result = new ChartGetMarkerDataCtx(androidContext);
                break;
            case CHAT_UPDATE:
                result = new ChatUpdateCtx(androidContext);
                break;
            case CHAT_MSG_UPDATE:
                result = new ChatMessageUpdateCtx(androidContext);
                break;
            case CONNECTION_CHANGE:
                result = new ConnectionChangeCtx(androidContext);
                break;
            case CUSTODY_ASSERT:
                result = new CustodyAssertCtx(androidContext);
                break;
            case EVENT_LOG:
                result = new EventLogCtx(androidContext);
                break;
            case GEOGRAPHIC_UPDATE:
                result = new GeographicUpdateCtx(androidContext);
                break;
            case HEART_BEAT:
                result = new HeartBeatCtx(androidContext);
                break;
            case IMAGE_FRESH:
                result = new ImageFreshCtx(androidContext);
                break;
            case IMAGE_GET:
                result = new ImageGetCtx(androidContext);
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
            case JOBTASK_UPDATE:
                result = new JobTaskUpdateCtx(androidContext);
                break;
            case JOB_PART_UPDATE:
                result = new JobPartUpdateCtx(androidContext);
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
            case PART_UPDATE_BY_BARCODE:
                result = new PartUpdateByBarcodeCtx(androidContext);
                break;
            case PERSON_PART_UPDATE:
                result = new PersonPartUpdateCtx(androidContext);
                break;
            case PERSON_SELECT_ALL:
                result = new PersonSelectAllCtx(androidContext);
                break;
            case PERSON_SELECT_BY_USER_NAME:
                result = new PersonSelectByUserNameCtx(androidContext);
                break;
            case PERSON_SELECT_SELF:
                result = new PersonSelectSelfCtx(androidContext);
                break;
            case PERSON_UPDATE:
                result = new PersonUpdateCtx(androidContext);
                break;
            case SELECT_BY_ROW_ID:
                result = new SelectByRowIdCtx(androidContext);
                break;
            case SITE_UPDATE:
                result = new SiteUpdateCtx(androidContext);
                break;
            case TASK_ACTION_SELECT_BY_PARENT:
                result = new TaskActionSelectByParentCtx(androidContext);
                break;
            case TASK_ACTION_UPDATE:
                result = new TaskActionUpdateCtx(androidContext);
                break;
            case TASK_DETAIL_SELECT_BY_PARENT:
                result = new TaskDetailSelectByParentCtx(androidContext);
                break;
            case THING_SELECT_BY_BARCODE:
                result = new ThingSelectByBarCodeCtx(androidContext);
                break;
            case THING_SELECT_BY_BLE_ADDRESS:
                result = new ThingSelectByBleAddressCtx(androidContext);
                break;
            case THING_UPDATE:
                result = new ThingUpdateCtx(androidContext);
                break;
            case TURN_BY_TURN_DIRECTIONS:
                result = new TurnByTurnDirectionsCtx(androidContext);
                break;
            default:
                throw new IllegalArgumentException("unknown command:" + command);
        }

        return result;
    }
}
