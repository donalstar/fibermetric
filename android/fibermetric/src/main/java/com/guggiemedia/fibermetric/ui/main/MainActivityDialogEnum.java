package com.guggiemedia.fibermetric.ui.main;

/**
 *
 */
public enum MainActivityDialogEnum {
    CALENDAR("Calendar"),
    UNKNOWN("Unknown");

    private final String _name;

    MainActivityDialogEnum(String arg) {
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
    public static MainActivityDialogEnum discoverMatchingEnum(String arg) {
        MainActivityDialogEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (MainActivityDialogEnum token : MainActivityDialogEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
