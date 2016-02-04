package com.guggiemedia.fibermetric.db;

public enum PortionTypeEnum {
    UNKNOWN("Unknown"),
    unit("unit"),
    cup("cup"),
    ounces("ounces");

    private final String _name;

    PortionTypeEnum(String arg) {
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
    public static PortionTypeEnum discoverMatchingEnum(String arg) {
        PortionTypeEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (PortionTypeEnum token : PortionTypeEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}