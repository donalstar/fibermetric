package com.guggiemedia.fibermetric.lib.chain;



/**
 *
 */
public class CommandFactory {
    public static final String LOG_TAG = CommandFactory.class.getName();

    /**
     *
     * @param context
     * @return
     */
    public static Boolean execute(AbstractCmdCtx context) {
        AbstractCmd command = null;

        switch(context.getCommand()) {
            case ALERT_BUMP_COUNTER:
                command = new AlertBumpCounterCmd();
                break;
            case ALERT_STOP:
                command = new AlertStopCmd();
                break;
            case AUTHENTICATION_SIGN_IN:
                command = new AuthenticationSignInCmd();
                break;
            case AUTHENTICATION_SIGN_OUT:
                command = new AuthenticationSignOutCmd();
                break;
            case AUTHENTICATION_TEST:
                command = new AuthenticationTestCmd();
                break;
            case BATTERY_UPDATE:
                command = new BatteryUpdateCmd();
                break;
            case CHAIN_OF_CUSTODY_UPDATE:
                command = new ChainOfCustodyUpdateCmd();
                break;
            case CHART_GET_MARKER_DATA:
                command = new ChartGetMarkerDataCmd();
                break;
            case CHAT_UPDATE:
                command = new ChatUpdateCmd();
                break;
            case CHAT_MSG_UPDATE:
                command = new ChatMessageUpdateCmd();
                break;
            case CONNECTION_CHANGE:
                command = new ConnectionChangeCmd();
                break;
            case CUSTODY_ASSERT:
                command = new CustodyAssertCmd();
                break;
            case EVENT_LOG:
                command = new EventLogCmd();
                break;
            case GEOGRAPHIC_UPDATE:
                command = new GeographicUpdateCmd();
                break;
            case HEART_BEAT:
                command = new HeartBeatCmd();
                break;
            case IMAGE_FRESH:
                command = new ImageFreshCmd();
                break;
            case IMAGE_GET:
                command = new ImageGetCmd();
                break;
            case PART_SELECT_BY_CATEGORY:
                command = new PartSelectByCategoryCmd();
                break;
            case JOBSITES_SELECT:
                command = new JobSitesSelectCmd();
                break;
            case JOBTASK_ACTIVE:
                command = new JobTaskActiveCmd();
                break;
            case JOBTASK_COMPLETE:
                command = new JobTaskCompleteCmd();
                break;
            case JOBTASK_DURATION:
                command = new JobTaskDurationCmd();
                break;
            case JOBTASK_NEXT:
                command = new JobTaskNextCmd();
                break;
            case JOBTASK_SCHEDULED:
                command = new JobTaskScheduledCmd();
                break;
            case JOBTASK_SELECT_ACTIVE:
                command = new JobTaskSelectActiveCmd();
                break;
            case JOBTASK_SELECT_BY_JOB_ID:
                command = new JobTaskSelectByJobIdCmd();
                break;
            case JOBTASK_SELECT_BY_PARENT:
                command = new JobTaskSelectByParentCmd();
                break;
            case JOBTASK_SELECT_TODAY:
                command = new JobTaskSelectTodayCmd();
                break;
            case JOBTASK_SUSPEND:
                command = new JobTaskSuspendCmd();
                break;
            case JOBTASK_UPDATE:
                command = new JobTaskUpdateCmd();
                break;
            case JOB_PART_UPDATE:
                command = new JobPartUpdateCmd();
                break;
            case MESSAGE_SEND:
                command = new MessageSendCmd();
                break;
            case PART_REPLACE_BARCODE:
                command = new PartReplaceBarcodeCmd();
                break;
            case PART_UPDATE:
                command = new PartUpdateCmd();
                break;
            case PART_UPDATE_BY_BARCODE:
                command = new PartUpdateByBarcodeCmd();
                break;
            case PERSON_PART_UPDATE:
                command = new PersonPartUpdateCmd();
                break;
            case PERSON_SELECT_ALL:
                command = new PersonSelectAllCmd();
                break;
            case PERSON_SELECT_BY_USER_NAME:
                command = new PersonSelectByUserNameCmd();
                break;
            case PERSON_SELECT_SELF:
                command = new PersonSelectSelfCmd();
                break;
            case PERSON_UPDATE:
                command = new PersonUpdateCmd();
                break;
            case SELECT_BY_ROW_ID:
                command = new SelectByRowIdCmd();
                break;
            case SITE_UPDATE:
                command = new SiteUpdateCmd();
                break;
            case TASK_ACTION_SELECT_BY_PARENT:
                command = new TaskActionSelectByParentCmd();
                break;
            case TASK_ACTION_UPDATE:
                command = new TaskActionUpdateCmd();
                break;
            case TASK_DETAIL_SELECT_BY_PARENT:
                command = new TaskDetailSelectByParentCmd();
                break;
            case THING_SELECT_BY_BARCODE:
                command = new ThingSelectByBarCodeCmd();
                break;
            case THING_SELECT_BY_BLE_ADDRESS:
                command = new ThingSelectByBleAddressCmd();
                break;
            case THING_UPDATE:
                command = new ThingUpdateCmd();
                break;
            case TURN_BY_TURN_DIRECTIONS:
                command = new TurnByTurnDirectionsCmd();
                break;
            default:
                throw new IllegalArgumentException("unknown command:" + command);
        }

        try {
            command.execute(context);

            if (context.isSuccess()) {
                return AbstractCmdCtx.CONTINUE_PROCESSING;
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }

        return AbstractCmdCtx.PROCESSING_COMPLETE;
    }

    /**
     *
     * @param contextList
     */
    public static void execute(ContextList contextList) {
        for (AbstractCmdCtx context:contextList) {
            Boolean flag = execute(context);
            if (flag == AbstractCmdCtx.PROCESSING_COMPLETE) {
                return;
            }
        }
    }
}
