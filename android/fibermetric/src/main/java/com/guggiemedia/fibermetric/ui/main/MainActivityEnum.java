package com.guggiemedia.fibermetric.ui.main;

/**
 *
 */
public enum MainActivityEnum {
    ADD_BARCODE("AddBarcode"),
    UNKNOWN("Unknown"),
    CHART_ACTIVITY("ChartActivity"),
    IMAGE_ACTIVITY("ImageActivity"),
    DISCOVER_THING_BARCODE("DiscoverThingBarCode"),
    PHONE_CALL_ACTIVITY("PhoneCallActivity"),
    REPLACE_THING_BARCODE("ReplaceThingBarCode"),
    LOGIN_ACTIVITY("LoginActivity");

    private final String _name;

    MainActivityEnum(String arg) {
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
    public static MainActivityEnum discoverMatchingEnum(String arg) {
        MainActivityEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (MainActivityEnum token : MainActivityEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
