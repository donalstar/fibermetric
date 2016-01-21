package com.guggiemedia.fibermetric.lib.db;

/**
 * Created by donal on 9/29/15.
 */
public enum ChatMessageTypeEnum {
    UNKNOWN("Unknown"),
    inbound("inbound"),
    outbound("outbound");

    private final String _name;

    ChatMessageTypeEnum(String arg) {
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
    public static ChatMessageTypeEnum discoverMatchingEnum(String arg) {
        ChatMessageTypeEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (ChatMessageTypeEnum token : ChatMessageTypeEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}