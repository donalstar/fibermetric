package com.guggiemedia.fibermetric.lib.chain;

import android.util.Log;


import com.guggiemedia.fibermetric.lib.Personality;
import com.guggiemedia.fibermetric.lib.db.TurnByTurnDirectionsModel;
import com.guggiemedia.fibermetric.lib.utility.MapUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by donal on 9/22/15.
 */
public class TurnByTurnDirectionsCmd extends AbstractCmd {
    public static final String LOG_TAG = TurnByTurnDirectionsCmd.class.getName();

    private static final String USER_AGENT = "Mozilla/5.0";

    private static String GOOGLE_DIRECTIONS_API_KEY = "AIzaSyCtGEuOgX11iCEnQSS7DaxF0npBN-uDnbU";
    private static String GOOGLEAPI_URL_BASE = "https://maps.googleapis.com/maps/api/directions/json";

    /**
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        Log.i(LOG_TAG, "execute");

        final TurnByTurnDirectionsCtx ctx = (TurnByTurnDirectionsCtx) context;

        Double[] location = ctx.getJobSiteLocation();

        TurnByTurnDirectionsModel result = getDirectionsSet(location);

        ctx.setInstructionSet(result);

        return true;
    }

    private TurnByTurnDirectionsModel getDirectionsSet(Double[] location) throws Exception {

        String origin = "origin=" + Personality.currentLocation.getLatitude()
                + "," + Personality.currentLocation.getLongitude();

        String destination = "destination=" + location[0] + "," + location[1];

        String url = GOOGLEAPI_URL_BASE + "?" + origin + "&"
                + destination + "&sensor=false&key=" + GOOGLE_DIRECTIONS_API_KEY;

        HttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url);

        httpGet.addHeader("User-Agent", USER_AGENT);

        HttpResponse httpResponse = httpClient.execute(httpGet);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        return parseJsonResult(response.toString());
    }

    private TurnByTurnDirectionsModel parseJsonResult(String result) throws Exception {
        Log.i(LOG_TAG, "RES: " + result);

        String distanceValue = MapUtils.getDistanceFromJson(result);

        String durationValue = MapUtils.getDurationFromJson(result);

        List<String> instructionSet = MapUtils.getStepsListFromJson(result);

        TurnByTurnDirectionsModel model = new TurnByTurnDirectionsModel();

        model.setRaw(result);

        model.setPolyline(MapUtils.getPolylinePointsFromJson(result));

        model.setSteps(instructionSet);
        model.setDistance(distanceValue);
        model.setDuration(durationValue);

        return model;
    }
}





