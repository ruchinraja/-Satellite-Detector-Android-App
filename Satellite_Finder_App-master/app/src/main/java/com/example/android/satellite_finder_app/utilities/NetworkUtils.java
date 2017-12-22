package com.example.android.satellite_finder_app.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import info.guardianproject.netcipher.NetCipher;

/**
 * Created by dbzabhiram on 7/8/17.
 * A final class to create get the TLE strings from celestrak.com.
 * Thanks to people at celestrak.com for maintaining the repository of TLEs.
 */

public final class NetworkUtils {
    private static final String SATELLITE_BASE_URL = "https://www.celestrak.com/NORAD/elements/";

    /**
     * Build the URL based on the type of satellite.
     * @param satelliteType
     * @return
     */
    public static URL buildUrl(com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType satelliteType){
        Uri builtUri = Uri.parse(SATELLITE_BASE_URL).buildUpon()
                .appendPath(com.example.android.satellite_finder_app.utilities.SatelliteUtils.getURLString(satelliteType))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Fetch data from the generated URL and return data as a String.
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {


        HttpURLConnection urlConnection = null;

        try {
            urlConnection = NetCipher.getHttpsURLConnection(url);

            urlConnection.connect();

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}