package com.guggiemedia.fibermetric.lib.db;

/**
 * state types
 */
public enum TaskActionStateEnum {
    UNKNOWN("Unknown"),
    ACTIVE("Active"),
    CANCELED("Canceled"),
    COMPLETE("Complete");

    private final String _name;

    TaskActionStateEnum(String arg) {
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
    public static TaskActionStateEnum discoverMatchingEnum(String arg) {
        TaskActionStateEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (TaskActionStateEnum token: TaskActionStateEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
