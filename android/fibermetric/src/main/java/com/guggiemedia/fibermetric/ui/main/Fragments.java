package com.guggiemedia.fibermetric.ui.main;

public enum Fragments {
    UNKNOWN("Unknown"),
    FOOD_SELECTOR_VIEW("FoodSelectorView"),
    STATUS_VIEW("StatusView");

    private final String _name;

    Fragments(String arg) {
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
    public static Fragments discoverMatchingEnum(String arg) {
        Fragments result = UNKNOWN;

        if (arg == null) {
            return result;
        }

        for (Fragments token: Fragments.values()) {
            if (token._name.equals(arg)) {
                result = token;
            }
        }

        return result;
    }
}
