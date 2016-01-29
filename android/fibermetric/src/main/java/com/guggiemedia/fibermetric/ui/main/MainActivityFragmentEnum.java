package com.guggiemedia.fibermetric.ui.main;

public enum MainActivityFragmentEnum {
    UNKNOWN("Unknown"),
    FOOD_SELECTOR_VIEW("FoodSelectorView"),
    STATUS_VIEW("StatusView");

    private final String _name;

    MainActivityFragmentEnum(String arg) {
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
    public static MainActivityFragmentEnum discoverMatchingEnum(String arg) {
        MainActivityFragmentEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (MainActivityFragmentEnum token:MainActivityFragmentEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
