package com.guggiemedia.fibermetric.lib.utility;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

/**
 *
 */
public class IpHelper {

    public static String discoverIpAddress(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }
}
