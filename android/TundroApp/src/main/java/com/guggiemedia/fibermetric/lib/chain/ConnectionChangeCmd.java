package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * service connection change
 */
public class ConnectionChangeCmd extends AbstractCmd {
    public static final String LOG_TAG = ConnectionChangeCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final ConnectionChangeCtx ctx = (ConnectionChangeCtx) context;

        ConnectivityManager cm = (ConnectivityManager) ctx.getAndroidContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        boolean flag = network != null && network.isConnectedOrConnecting();
        Log.d(LOG_TAG, "network status:" + flag);

        return returnToSender(ctx, ResultEnum.OK);
    }
}
