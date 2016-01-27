package com.guggiemedia.fibermetric.lib.db;

/**
 * Created by donal on 9/29/15.
 */
public enum InventoryStatusEnum {
    unknown("Unknown"),
    pickUp("pickUp"),
    inCustody("inCustody");

    private final String _name;

    InventoryStatusEnum(String arg) {
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
    public static InventoryStatusEnum discoverMatchingEnum(String arg) {
        InventoryStatusEnum result = unknown;

        if (arg == null) {
            return result;
        }

        for (InventoryStatusEnum token : InventoryStatusEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}