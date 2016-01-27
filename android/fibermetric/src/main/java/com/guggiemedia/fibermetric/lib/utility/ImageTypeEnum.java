package com.guggiemedia.fibermetric.lib.utility;

/**
 *
 */
public enum ImageTypeEnum {
    UNKNOWN("Unknown"),
    ORIGINAL("original"),
    SUBSAMPLE("subSample"),
    THUMBNAIL("thumbNail");

    private final String _name;

    ImageTypeEnum(String arg) {
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
    public static ImageTypeEnum discoverMatchingEnum(String arg) {
        ImageTypeEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (ImageTypeEnum token : ImageTypeEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}