package com.guggiemedia.fibermetric.lib.chain;

/**
 * legal command options
 */
public enum CommandEnum {
    UNKNOWN("Unknown"),
    ALERT_BUMP_COUNTER("AlertBumpCounter"),
    ALERT_STOP("AlertStop"),
    AUTHENTICATION_SIGN_IN("AuthenticationSignIn"),
    AUTHENTICATION_SIGN_OUT("AuthenticationSignOut"),
    AUTHENTICATION_TEST("AuthenticationTest"),
    BATTERY_UPDATE("BatteryUpdate"),
    CHAIN_OF_CUSTODY_UPDATE("ChainOfCustodyUpdate"),
    CHART_GET_MARKER_DATA("ChartGetMarkerData"),
    CHAT_MSG_RETRIEVE("ChatMessageRetrieve"),
    CHAT_MSG_UPDATE("ChatMessageUpdate"),
    CHAT_UPDATE("ChatUpdate"),
    CONNECTION_CHANGE("ConnectionChange"),
    CUSTODY_ASSERT("CustodyAssert"),
    EVENT_LOG("EventLog"),
    GEOGRAPHIC_UPDATE("GeographicUpdate"),
    HEART_BEAT("HeartBeat"),
    IMAGE_FRESH("ImageFresh"),
    IMAGE_GET("ImageGet"),
    JOBSITES_SELECT("JobSitesSelect"),
    JOBTASK_ACTIVE("JobTaskActive"),
    JOBTASK_COMPLETE("JobTaskComplete"),
    JOBTASK_DURATION("JobTaskDuration"),
    JOBTASK_NEXT("JobTaskNext"),
    JOBTASK_SCHEDULED("JobTaskScheduled"),
    JOBTASK_SELECT_ACTIVE("JobTaskSelectActive"),
    JOBTASK_SELECT_BY_JOB_ID("JobTaskSelectByJobId"),
    JOBTASK_SELECT_BY_PARENT("JobTaskSelectByParent"),
    JOBTASK_SELECT_TODAY("JobTaskSelectToday"),
    JOBTASK_SUSPEND("JobTaskSuspend"),
    JOBTASK_UPDATE("JobTaskUpdate"),
    JOB_PART_UPDATE("JobPartUpdate"),
    MESSAGE_SEND("MessageSend"),
    PART_SELECT_BY_CATEGORY("PartSelectByCategory"),
    PART_REPLACE_BARCODE("PartReplaceBarcode"),
    PART_UPDATE("PartUpdate"),
    PART_UPDATE_BY_BARCODE("PartUpdateByBarcode"),
    PERSON_PART_UPDATE("PersonPartUpdate"),
    PERSON_SELECT_ALL("PersonSelectAll"),
    PERSON_SELECT_BY_USER_NAME("PersonSelectByUserName"),
    PERSON_SELECT_SELF("PersonSelectSelf"),
    PERSON_UPDATE("PersonUpdate"),
    SELECT_BY_ROW_ID("SelectByRowId"),
    SITE_UPDATE("SiteUpdate"),
    TASK_ACTION_SELECT_BY_PARENT("TaskActionSelectByParent"),
    TASK_ACTION_UPDATE("TaskActionUpdate"),
    TASK_DETAIL_SELECT_BY_PARENT("TaskDetailSelectByParent"),
    THING_SELECT_BY_BARCODE("ThingSelectByBarCode"),
    THING_SELECT_BY_BLE_ADDRESS("ThingSelectByBleAddress"),
    THING_UPDATE("ThingUpdate"),
    TURN_BY_TURN_DIRECTIONS("TurnByTurnDirections");

    private final String _name;

    CommandEnum(String arg) {
        _name = arg;
    }

    @Override
    public String toString() {
        return _name;
    }

    /**
     * string to enum conversion
     * @param arg
     * @return
     */
    public static CommandEnum discoverMatchingEnum(String arg) {
        CommandEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (CommandEnum token: CommandEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
