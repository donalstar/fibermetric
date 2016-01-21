package com.guggiemedia.fibermetric.lib.utility;

/**
 * legal command options
 */
public enum PersonaEnum {
    UNKNOWN("Unknown"),
    DEMO_NO_SERVER("DemoNoServer"),
    DEMO_WITH_SERVER("DemoWithServer");

    private final String _name;

    PersonaEnum(String arg) {
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
    public static PersonaEnum discoverMatchingEnum(String arg) {
        PersonaEnum result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (PersonaEnum token: PersonaEnum.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
