package com.guggiemedia.fibermetric.lib.utility;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donal on 10/21/15.
 */
public class MapUtils {

    public static List<String> getStepsListFromJson(String jsonResult) throws JSONException {
        List<String> instructionSet = new ArrayList<>();

        JSONObject firstLeg = getFirstLeg(jsonResult);

        JSONArray steps = firstLeg.getJSONArray("steps");

        for (int i = 0; i < steps.length(); i++) {
            JSONObject step = steps.getJSONObject(i);

            Object htmlInstructions = step.get("html_instructions");

            instructionSet.add(htmlInstructions.toString());
        }

        return instructionSet;
    }

    public static String getDistanceFromJson(String jsonResult) throws JSONException {
        JSONObject firstLeg = getFirstLeg(jsonResult);

        JSONObject duration = (JSONObject) firstLeg.get("distance");

        return (String) duration.get("text");
    }

    public static String getDurationFromJson(String jsonResult) throws JSONException {
        JSONObject firstLeg = getFirstLeg(jsonResult);

        JSONObject duration = (JSONObject) firstLeg.get("duration");

        return (String) duration.get("text");
    }


    private static JSONObject getFirstLeg(String jsonResult) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonResult);

        JSONArray routes = jsonObject.getJSONArray("routes");

        JSONObject firstRoute = routes.getJSONObject(0);

        JSONArray legs = firstRoute.getJSONArray("legs");

        return legs.getJSONObject(0);
    }

    /**
     * @param jsonResult
     * @return
     * @throws JSONException
     */
    public static List<LatLng> getPolylinePointsFromJson(String jsonResult) throws JSONException {
        final JSONObject json = new JSONObject(jsonResult);
        JSONArray routeArray = json.getJSONArray("routes");
        JSONObject routes = routeArray.getJSONObject(0);
        JSONObject overviewPolylines = routes
                .getJSONObject("overview_polyline");
        String encodedString = overviewPolylines.getString("points");

        return MapUtils.decodePolyline(encodedString);
    }

    /**
     * @param encoded
     * @return
     */
    public static List<LatLng> decodePolyline(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
