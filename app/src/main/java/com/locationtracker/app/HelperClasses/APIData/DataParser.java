package com.locationtracker.app.HelperClasses.APIData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private static final String TAG = "DataParser";
    //PLACES API

    //return hashMap for each place
    private HashMap<String, String> getPlace(JSONObject googlePlaceJSON) {

        HashMap<String, String> placeMap = new HashMap<>();

        String placeID;
        String latitude;
        String longitude;
        String name;
        String rating;
        String usersRatingTotal;
        String address;
        String open;

        try {

            if (!googlePlaceJSON.isNull("place_id")) {

                placeID = googlePlaceJSON.getString("place_id");
                placeMap.put("place_id", placeID);
            }

            if (!googlePlaceJSON.getJSONObject("geometry").getJSONObject("location")
                    .getString("lat").isEmpty()) {

                latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location")
                        .getString("lat");
                placeMap.put("lat", latitude);
            }

            if (!googlePlaceJSON.getJSONObject("geometry").getJSONObject("location")
                    .getString("lng").isEmpty()) {

                longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location")
                        .getString("lng");
                placeMap.put("lng", longitude);
            }

            if (!googlePlaceJSON.isNull("name")) {
                name = googlePlaceJSON.getString("name");
                placeMap.put("name", name);
            }

            if (!googlePlaceJSON.isNull("vicinity")) {
                address = googlePlaceJSON.getString("vicinity");
                placeMap.put("vicinity", address);
            }

            if (!googlePlaceJSON.isNull("rating")) {
                rating = googlePlaceJSON.getString("rating");
                placeMap.put("rating", rating);
            }

            if (!googlePlaceJSON.isNull("user_ratings_total")) {
                usersRatingTotal = googlePlaceJSON.getString("user_ratings_total");
                placeMap.put("user_ratings_total", usersRatingTotal);
            }

            if (!googlePlaceJSON.isNull("opening_hours")) {
                open = googlePlaceJSON.getJSONObject("opening_hours").getString("open_now");
                placeMap.put("open_now", open);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return placeMap;

    }

    //Returns the list of all hashMaps stored
    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {

        List<HashMap<String, String>> placesList = new ArrayList<>();

        HashMap<String, String> placeMap;
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return placesList;
    }

    //'results' is JSONArray which has JSONObjects i.e places
    public List<HashMap<String, String>> parsePlaces(String jsonData) {

        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray != null) {
            return getPlaces(jsonArray);
        } else {
            return null;
        }
    }


    //DIRECTION API
    public String destDuration(String jsonData) {

        String destDuration = null;

        try {

            JSONObject jsonObject = new JSONObject(jsonData);

            JSONObject legs = jsonObject.getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0);

            destDuration = legs.getJSONObject("duration").getString("text");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return destDuration;
    }

    public String polyline(String jsonData){

        String overviewPolyline = null;

        try {

            JSONObject jsonObject = new JSONObject(jsonData);

            overviewPolyline = jsonObject.getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONObject("overview_polyline").getString("points");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return overviewPolyline;
    }

    public String[] parseDirections(String jsonData) {

        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getJSONArray("steps");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray != null) {
            return getPaths(jsonArray);
        } else {
            return null;
        }

    }

    public String[] getPaths(JSONArray stepsJson) {

        int count = stepsJson.length();
        String[] polyLines = new String[count];

        for (int i = 0; i < count; i++) {
            try {
                polyLines[i] = getPath(stepsJson.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return polyLines;
    }

    public String getPath(JSONObject pathJson) {

        String polyLine = null;
        try {
            polyLine = pathJson.getJSONObject("polyline").getString("points");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return polyLine;
    }


}
