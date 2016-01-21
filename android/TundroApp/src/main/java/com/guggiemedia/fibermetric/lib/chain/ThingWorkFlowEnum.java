package com.guggiemedia.fibermetric.lib.chain;

/**
 * Define legal responses
 */
public enum ThingWorkFlowEnum {
    UNKNOWN("Unknown"),
    SUCCESS("Success"),
    FAIL_BAD_BARCODE("FailBadBarCode"),
    FAIL_BAD_BLE_ADDRESS("FailBadBleAddress"),
    FAIL_BAD_DESCRIPTION("FailBadDescription"),
    FAIL_BAD_NAME("FailBadName"),
    FAIL_BAD_NOTE("FailBadNote"),
    FAIL_BAD_SERIAL("FailBadSerial"),
    FAIL_BAD_UUID("FailBadUuid"),
    FAIL_DUPLICATE_BARCODE("FailDuplicateBarCode"),
    FAIL_DUPLICATE_BLE_ADDRESS("FailDuplicateBleAddress"),
    FAIL_NOT_CUSTODY("FailNotCustody");

    private final String _name;

    ThingWorkFlowEnum(String arg) {
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
    public static ThingWorkFlowEnum discoverMatchingEnum(String arg) {
        ThingWorkFlowEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (ThingWorkFlowEnum token: ThingWorkFlowEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
