package com.guggiemedia.fibermetric.lib.db;

/**
 * state types
 */
public enum JobTaskPriorityEnum {
    UNKNOWN("Unknown"),
    NORMAL("Normal"),
    CRITICAL("Critical");

    private final String _name;

    JobTaskPriorityEnum(String arg) {
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
    public static JobTaskPriorityEnum discoverMatchingEnum(String arg) {
        JobTaskPriorityEnum result = NORMAL;

        if (arg == null) {
            return result;
        }

        for (JobTaskPriorityEnum token: JobTaskPriorityEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
