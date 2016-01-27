package com.guggiemedia.fibermetric.lib.db;

/**
 * state types
 */
public enum JobStateEnum {
    UNKNOWN("Unknown"),
    ACTIVE("Active"),
    COMPLETE("Complete"),
    SCHEDULE("Scheduled"),
    SUSPEND("Suspend");

    private final String _name;

    JobStateEnum(String arg) {
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
    public static JobStateEnum discoverMatchingEnum(String arg) {
        JobStateEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (JobStateEnum token: JobStateEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
