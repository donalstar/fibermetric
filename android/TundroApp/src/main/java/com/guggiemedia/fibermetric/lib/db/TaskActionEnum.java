package com.guggiemedia.fibermetric.lib.db;

/**
 * task action types
 */
public enum TaskActionEnum {
    UNKNOWN("Unknown"),
    AUDIO_RECORD("AudioRecord"),
    CAMERA("Camera"),
    CAPTURE_INTEGER("CaptureInteger"),
    CAPTURE_NOTE("CaptureNote"),
    SCAN_BARCODE("ScanBarcode");

    private final String _name;

    TaskActionEnum(String arg) {
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
    public static TaskActionEnum discoverMatchingEnum(String arg) {
        TaskActionEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (TaskActionEnum token : TaskActionEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
