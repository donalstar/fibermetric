package com.guggiemedia.fibermetric.lib;

import android.location.Location;

import com.guggiemedia.fibermetric.lib.db.PersonModel;
import com.guggiemedia.fibermetric.lib.utility.PersonaEnum;


/**
 *
 */
public class Personality {
    // percent charged
    public static int batteryPower;

    // false = network unavail
    public static Boolean networkActive;

    // device token
    public static String deviceToken;

    // most recent location update
    public static Location currentLocation;

    public static PersonaEnum personaEnum;

    public static PersonModel personSelf;
}
