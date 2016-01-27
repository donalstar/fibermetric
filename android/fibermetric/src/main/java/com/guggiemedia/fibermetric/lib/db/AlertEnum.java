package com.guggiemedia.fibermetric.lib.db;

/**
 * alert types
 */
public enum AlertEnum {
    UNKNOWN("Unknown"),
    BLE_DISABLED("BleDisabled"),
    BLE_MISSING("BleMissing");

    private final String _name;

    AlertEnum(String arg) {
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
    public static AlertEnum discoverMatchingEnum(String arg) {
        AlertEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (AlertEnum token: AlertEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
