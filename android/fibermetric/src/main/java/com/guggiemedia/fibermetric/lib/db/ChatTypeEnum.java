package com.guggiemedia.fibermetric.lib.db;

/**
 * Created by donal on 9/29/15.
 */

public enum ChatTypeEnum {
    UNKNOWN("Unknown"),
    oneOnOne("oneOnOne"),
    group("group");

    private final String _name;

    ChatTypeEnum(String arg) {
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
    public static ChatTypeEnum discoverMatchingEnum(String arg) {
        ChatTypeEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (ChatTypeEnum token : ChatTypeEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
