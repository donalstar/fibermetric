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


            case CHART_GET_MARKER_DATA:
                command = new ChartGetMarkerDataCmd();
                break;

            case CONNECTION_CHANGE:
                command = new ConnectionChangeCmd();
                break;

            case EVENT_LOG:
                command = new EventLogCmd();
                break;

            case HEART_BEAT:
                command = new HeartBeatCmd();
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


            case MESSAGE_SEND:
                command = new MessageSendCmd();
                break;
            case PART_REPLACE_BARCODE:
                command = new PartReplaceBarcodeCmd();
                break;
            case PART_UPDATE:
                command = new PartUpdateCmd();
                break;


            case SELECT_BY_ROW_ID:
                command = new SelectByRowIdCmd();
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
