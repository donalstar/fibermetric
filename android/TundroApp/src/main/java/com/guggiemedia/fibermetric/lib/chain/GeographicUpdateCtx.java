package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;
import android.location.Location;

/**
 * fresh geographic update
 */
public class GeographicUpdateCtx extends AbstractCmdCtx {
    private boolean _testFlag;
    private Location _location;

    public GeographicUpdateCtx(Context androidContext) {
        super(CommandEnum.GEOGRAPHIC_UPDATE, androidContext);
    }

    public boolean isTestFlag() {
        return _testFlag;
    }

    public void setTestFlag(boolean testFlag) {
        _testFlag = testFlag;
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location location) {
        _location = location;
    }
}