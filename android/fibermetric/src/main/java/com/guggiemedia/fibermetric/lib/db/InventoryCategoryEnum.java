package com.guggiemedia.fibermetric.lib.db;

/**
 * Created by donal on 9/29/15.
 */
public enum InventoryCategoryEnum {
    unknown("Unknown"),
    parts("parts"),
    tools("tools"),
    vehicles("vehicles");

    private final String _name;

    InventoryCategoryEnum(String arg) {
        _name = arg;
    }

    @Override
    public String toString() {
        return _name;
    }

    /**
     * string to enum conversion
     *
     * @param arg
     * @return
     */
    public static InventoryCategoryEnum discoverMatchingEnum(String arg) {
        InventoryCategoryEnum result = unknown;

        if (arg == null) {
            return result;
        }

        for (InventoryCategoryEnum token : InventoryCategoryEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}