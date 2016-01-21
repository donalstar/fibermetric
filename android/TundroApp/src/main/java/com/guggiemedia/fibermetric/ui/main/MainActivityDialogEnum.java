package com.guggiemedia.fibermetric.ui.main;

/**
 *
 */
public enum MainActivityDialogEnum {
    CAPTURE_INTEGER("CaptureInteger"),
    CAPTURE_NOTE("CaptureNote"),
    SUSPEND_JOB1("SuspendJob1"), // operator selects job suspension
    SUSPEND_JOB2("SuspendJob2"), // operator selects another job, enforce "there can only be one"
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
     * @param arg
     * @return
     */
    public static MainActivityDialogEnum discoverMatchingEnum(String arg) {
        MainActivityDialogEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (MainActivityDialogEnum token: MainActivityDialogEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
