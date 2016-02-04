package com.guggiemedia.fibermetric.db;

public enum ItemTypeEnum {
    UNKNOWN("Unknown"),
    vegetable("vegetable"),
    grain("grain"),
    fruit("fruit");

    private final String _name;

    ItemTypeEnum(String arg) {
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
    public static ItemTypeEnum discoverMatchingEnum(String arg) {
        ItemTypeEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (ItemTypeEnum token : ItemTypeEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}