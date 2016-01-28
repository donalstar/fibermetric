package com.guggiemedia.fibermetric.ui.main;

public enum MainActivityFragmentEnum {
    UNKNOWN("Unknown"),
    ADD_BEACON_VIEW("AddBeaconView"),
    CALENDAR_VIEW("CalendarView"),
    CHART_VIEW("ChartView"),
    CHAT_LIST("ChatList"),
    CHAT_VIEW("ChatView"),
    ESCALATION_FORM("EscalationForm"),
    FEEDBACK_FORM("FeedbackForm"),
    HELP_VIEW("HelpView"),
    MY_INVENTORY_VIEW("MyInventoryView"),
    TODAYS_INVENTORY_VIEW("TodaysInventoryView"),
    REQUIRED_INVENTORY_VIEW("RequiredInventoryView"),
    JOB_TODAY_LIST("JobTodayList"),
    JOB_VIEW("JobView"),
    STATUS_VIEW("StatusView");

    private final String _name;

    MainActivityFragmentEnum(String arg) {
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
    public static MainActivityFragmentEnum discoverMatchingEnum(String arg) {
        MainActivityFragmentEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (MainActivityFragmentEnum token:MainActivityFragmentEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
