package com.guggiemedia.fibermetric.lib.db;

/**
 * Define legal thing categories
 */
public enum ThingTypeEnum {
    UNKNOWN("Unknown"),
    PART("Part"),
    TOOL("Tool"),
    VEHICLE("Vehicle");

    private final String _name;

    ThingTypeEnum(String arg) {
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
    public static ThingTypeEnum discoverMatchingEnum(String arg) {
        ThingTypeEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (ThingTypeEnum token: ThingTypeEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
