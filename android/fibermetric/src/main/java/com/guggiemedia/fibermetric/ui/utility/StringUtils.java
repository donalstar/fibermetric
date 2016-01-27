package com.guggiemedia.fibermetric.ui.utility;

/**
 * Created by donal on 10/23/15.
 */
public class StringUtils {

    /**
     * @param name
     * @return
     */
    public static String getAsInitials(String name) {
        String pieces[] = name.split(" ");

        StringBuffer initials = new StringBuffer();

        for (String piece : pieces) {
            initials.append(piece.substring(0, 1));
        }

        return initials.toString();
    }

    /**
     * Takes as input a numver in the format 4155556666 & returns
     * as follows: (415)555-6666
     *
     * @param number
     * @return
     */
    public static String getFormattedPhoneNumber(String number) {
        return number.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
    }
}
