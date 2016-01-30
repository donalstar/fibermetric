package com.guggiemedia.fibermetric.lib.chain;

/**
 * legal command options
 */
public enum CommandEnum {
    UNKNOWN("Unknown"),
    ITEM_UPDATE("ItemUpdate"),
    SELECT_BY_ROW_ID("SelectByRowId");

    private final String _name;

    CommandEnum(String arg) {
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
    public static CommandEnum discoverMatchingEnum(String arg) {
        CommandEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (CommandEnum token: CommandEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}