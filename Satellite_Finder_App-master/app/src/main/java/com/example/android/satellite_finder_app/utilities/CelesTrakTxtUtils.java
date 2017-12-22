package com.example.android.satellite_finder_app.utilities;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.me.g4dpz.satellite.Satellite;
import uk.me.g4dpz.satellite.TLE;

/**
 * Created by dbzabhiram on 7/9/17.
 */

public final class CelesTrakTxtUtils {

    static class TleComparator implements Comparator<TLE>
    {
        public int compare(TLE t1, TLE t2)
        {
            return t1.getName().compareTo(t2.getName());
        }
    }

    /**
     * Converts a string of TLEs to Satellite object array. There's no explicit error check right now.
     * Exception given is IllegalArgumentException of 'predict4java' is thrown if an error occurs.
     * @param satelliteTypeTLEString the type of Satellite
     * @return array of TLEs
     */
    public static TLE[] getTlesFromTxt(String satelliteTypeTLEString, String filterString){
        String[] lines = satelliteTypeTLEString.split(System.getProperty("line.separator"));

        // As the full form of TLE is Three Line element. The number of TLEs hence
        int numTLEs = (lines.length/3);
        List<TLE> tlesList= new ArrayList<TLE>();

        for(int i = 0; i < numTLEs; ++i){
            int stringNum = i * 3;
            String[] tleStrings = new String[3];

            // Trust 'celestrak.com' to provide the correct file.
            tleStrings[0] = lines[stringNum];
            tleStrings[1] = lines[stringNum + 1];
            tleStrings[2] = lines[stringNum + 2];

            // Create the Satellite object.
            TLE tempTle = com.example.android.satellite_finder_app.utilities.SatelliteUtils.createTLE(tleStrings);

            if (tempTle.getName().toLowerCase().contains(filterString.toLowerCase())) {
                tlesList.add(tempTle);
            }
        }

        Collections.sort(tlesList, new TleComparator());

        TLE[] tles = tlesList.toArray(new TLE[tlesList.size()]);

        return tles;
    }
}
