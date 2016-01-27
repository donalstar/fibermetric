package com.guggiemedia.fibermetric.ui.utility;

import android.content.Context;
import android.os.AsyncTask;


import com.guggiemedia.fibermetric.lib.db.TurnByTurnDirectionsModel;


/**
 * Created by donal on 12/9/15.
 */
public class DirectionsLookup extends AsyncTask<Double[], Void, TurnByTurnDirectionsModel> {
    DirectionsLookupListener directionsLookupListener;
    Context context;

    public DirectionsLookup(Context context, DirectionsLookupListener directionsLookupListener) {
        this.context = context;

        this.directionsLookupListener = directionsLookupListener;
    }

    @Override
    protected TurnByTurnDirectionsModel doInBackground(Double[]... location) {
        return null;
    }

    @Override
    protected void onPostExecute(TurnByTurnDirectionsModel result) {
        directionsLookupListener.setResult(result);
    }

    public interface DirectionsLookupListener {
        void setResult(TurnByTurnDirectionsModel result);
    }
}
