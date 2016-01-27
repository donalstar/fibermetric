package com.guggiemedia.fibermetric.lib.chain;

/**
 * legal result options
 */
public enum ResultEnum {
    UNKNOWN("Unknown"),
    FAIL("Fail"),
    OK("Ok");

    private final String _name;

    ResultEnum(String arg) {
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
    public static ResultEnum discoverMatchingEnum(String arg) {
        ResultEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (ResultEnum token: ResultEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
