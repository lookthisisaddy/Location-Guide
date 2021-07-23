package com.locationtracker.app.HelperClasses.APIData;

import android.os.AsyncTask;

import com.locationtracker.app.HelperClasses.DownloadURL;

public class GetNearbyPlace extends AsyncTask<String, Integer, String> {

    public static final String TAG = "GetNearbyPlace";

    @Override
    protected String doInBackground(String... urls) {

        String placesData = null;

        DownloadURL downloadURL = new DownloadURL();
        try {
            placesData = downloadURL.readUrl(urls[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return placesData;
    }

}
