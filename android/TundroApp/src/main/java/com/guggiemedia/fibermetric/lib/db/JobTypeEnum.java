package com.guggiemedia.fibermetric.lib.db;

/**
 * job types
 */
public enum JobTypeEnum {
    UNKNOWN("Unknown"),
    MAINTENANCE("Maintenance"),
    REPAIR("Repair");

    private final String _name;

    JobTypeEnum(String arg) {
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
    public static JobTypeEnum discoverMatchingEnum(String arg) {
        JobTypeEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (JobTypeEnum token: JobTypeEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
