package com.locationtracker.app.HelperClasses.APIData;

import android.os.AsyncTask;

public class GetNearbyPlace extends AsyncTask<String, Integer, String> {

    public static final String TAG = "GetNearbyPlace";

    @Override
    protected String doInBackground(String... urls) {

        String placesData = null;

        DownloadUrl downloadURL = new DownloadUrl();
        try {
            placesData = downloadURL.readUrl(urls[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return placesData;
    }

}
