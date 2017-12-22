package com.example.android.satellite_finder_app.utilities;

import android.content.res.Resources;

import java.util.Collection;

import uk.me.g4dpz.satellite.Satellite;
import uk.me.g4dpz.satellite.SatelliteFactory;
import uk.me.g4dpz.satellite.TLE;

import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.AMATEUR_RADIO;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.ARGOS_DATA_COLLECTION_SYSTEM;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.BEIDOU;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.CUBESATS;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.DISASTER_MONITORING;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.EARTH_RESOURCES;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.EXPERIMENTAL;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.GALILEO;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.GEODETIC;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.GEOSTATIONARY;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.GLOBALSTAR;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.GLONASS_OPERATIONAL;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.GORIZONT;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.GPS_OPERATIONAL;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.INTELSAT;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.IRIDIUM;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.IRIDIUM_NEXT;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.LAST_30_DAYS_LAUNCHES;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.MISCELLANEOUS_MILITARY;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.MOLNIYA;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.NAVY_NAVIGATION_SATELLITE_SYSTEM;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.ORBCOMM;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.OTHER_COMMUNICATION;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.RADAR_CALIBRATION;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.RADUGA;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.RUSSIAN_LEO_NAVIGATION;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.SATELLITE_BASED_AUGMENTATION_SYSTEM;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.SEARCH_AND_RESCUE;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.SES;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.SPACE_AND_EARTH_SCIENCE;
import static com.example.android.satellite_finder_app.utilities.SatelliteUtils.SatelliteType.TRACKING_AND_DATA_RELAY_SATELLITE_SYSTEM;

/**
 * Created by dbzabhiram on 7/8/17.
 * This class uses predict4java library to create the Satellite objects. Also provides some other
 * utilities.
 */

public final class SatelliteUtils {

    /**
     * A temporary solution to hold the satellite tyeps.
     * TODO: Move this to a database
     */
    public enum SatelliteType{
        AMATEUR_RADIO,
        ARGOS_DATA_COLLECTION_SYSTEM,
        BEIDOU,
        CUBESATS,
        DISASTER_MONITORING,
        EARTH_RESOURCES,
        EDUCATION,
        ENGINEERING,
        EXPERIMENTAL,
        GEOSTATIONARY,
        GLOBALSTAR,
        GOES,
        GORIZONT,
        INTELSAT,
        IRIDIUM,
        IRIDIUM_NEXT,
        GPS_OPERATIONAL,
        GLONASS_OPERATIONAL,
        GALILEO,
        GEODETIC,
        LAST_30_DAYS_LAUNCHES,
        MISCELLANEOUS_MILITARY,
        MOLNIYA,
        NAVY_NAVIGATION_SATELLITE_SYSTEM,
        NOAA,
        ORBCOMM,
        OTHER,
        OTHER_COMMUNICATION,
        RADAR_CALIBRATION,
        RADUGA,
        RUSSIAN_LEO_NAVIGATION,
        SATELLITE_BASED_AUGMENTATION_SYSTEM,
        SEARCH_AND_RESCUE,
        SES,
        SPACE_AND_EARTH_SCIENCE,
        SPACE_STATIONS,
        TRACKING_AND_DATA_RELAY_SATELLITE_SYSTEM,
        WEATHER
    };

    // One for the bundle
    public static String TYPE = "SatelliteType";

    public static String SATELLITE = "Satellite";

    public static String SATELLITE_BUNDLE ="SatelliteBundle";

    /**
     * Get the full name of the satellite type. Again a temporary solution.
     * TODO: Move this to a database oriented solution
     * @param satelliteType
     * @return
     */
    public static String getFullTypeName(SatelliteType satelliteType) {
        String satelliteString;

        switch (satelliteType){
            case LAST_30_DAYS_LAUNCHES:
                satelliteString = "Last 30 Days Launches";
                break;
            case SPACE_STATIONS:
                satelliteString = "Space Stations";
                break;
            case WEATHER:
                satelliteString = "Weather";
                break;
            case NOAA:
                satelliteString = "NOAA";
                break;
            case GOES:
                satelliteString = "GOES";
                break;
            case EARTH_RESOURCES:
                satelliteString = "Earth Resources";
                break;
            case SEARCH_AND_RESCUE:
                satelliteString = "Search and Rescue";
                break;
            case DISASTER_MONITORING:
                satelliteString = "Disaster Monitoring";
                break;
            case TRACKING_AND_DATA_RELAY_SATELLITE_SYSTEM:
                satelliteString = "Tracking and Data Relay Satellite System";
                break;
            case ARGOS_DATA_COLLECTION_SYSTEM:
                satelliteString = "ARGOS Data Collection System";
                break;
            case GEOSTATIONARY:
                satelliteString = "Geostationary";
                break;
            case INTELSAT:
                satelliteString = "Intelsat";
                break;
            case SES:
                satelliteString = "SES";
                break;
            case IRIDIUM:
                satelliteString = "Iridium";
                break;
            case IRIDIUM_NEXT:
                satelliteString = "Iridium NEXT";
                break;
            case ORBCOMM:
                satelliteString = "Orbcomm";
                break;
            case GLOBALSTAR:
                satelliteString = "Globalstar";
                break;
            case AMATEUR_RADIO:
                satelliteString = "Amateur Radio";
                break;
            case EXPERIMENTAL:
                satelliteString = "Experimental";
                break;
            case OTHER_COMMUNICATION:
                satelliteString = "Other Communication";
                break;
            case GORIZONT:
                satelliteString = "Gorizont";
                break;
            case RADUGA:
                satelliteString = "Raduga";
                break;
            case MOLNIYA:
                satelliteString = "Molniya";
                break;
            case GPS_OPERATIONAL:
                satelliteString = "GPS Operational";
                break;
            case GLONASS_OPERATIONAL:
                satelliteString = "Glonass Operational";
                break;
            case GALILEO:
                satelliteString = "Galileo";
                break;
            case BEIDOU:
                satelliteString = "Beidou";
                break;
            case SATELLITE_BASED_AUGMENTATION_SYSTEM:
                satelliteString = "Satellite-Based Augmentation System";
                break;
            case NAVY_NAVIGATION_SATELLITE_SYSTEM:
                satelliteString = "Navy Navigation Satellite System";
                break;
            case RUSSIAN_LEO_NAVIGATION:
                satelliteString = "Russian LEO Navigation";
                break;
            case SPACE_AND_EARTH_SCIENCE:
                satelliteString = "Space & Earth Science";
                break;
            case GEODETIC:
                satelliteString = "Geodetic";
                break;
            case ENGINEERING:
                satelliteString = "Engineering";
                break;
            case EDUCATION:
                satelliteString = "Education";
                break;
            case MISCELLANEOUS_MILITARY:
                satelliteString = "Miscellaneous Military";
                break;
            case RADAR_CALIBRATION:
                satelliteString = "Radar Calibration";
                break;
            case CUBESATS:
                satelliteString = "CubeSats";
                break;
            case OTHER:
                satelliteString = "Other";
                break;
            default:
                satelliteString = null;
        }

        return satelliteString;
    }

    /**
     * Get the type of satellite enum from the satelliteTypeString.
     * @param satelliteTypeString
     * @return
     */
    public static SatelliteType getType(String satelliteTypeString){
        SatelliteType satelliteType;

        switch (satelliteTypeString){
            case "Last 30 Days Launches":
                satelliteType = SatelliteType.LAST_30_DAYS_LAUNCHES;
                break;
            case "Space Stations":
                satelliteType = SatelliteType.SPACE_STATIONS;
                break;
            case "Weather":
                satelliteType = SatelliteType.WEATHER;
                break;
            case "NOAA":
                satelliteType = SatelliteType.NOAA;
                break;
            case "GOES":
                satelliteType = SatelliteType.GOES;
                break;
            case "Earth Resources":
                satelliteType = SatelliteType.EARTH_RESOURCES;
                break;
            case "Search and Rescue":
                satelliteType = SatelliteType.SEARCH_AND_RESCUE;
                break;
            case "Disaster Monitoring":
                satelliteType = SatelliteType.DISASTER_MONITORING;
                break;
            case "Tracking and Data Relay Satellite System":
                satelliteType = SatelliteType.TRACKING_AND_DATA_RELAY_SATELLITE_SYSTEM;
                break;
            case "ARGOS Data Collection System":
                satelliteType = SatelliteType.ARGOS_DATA_COLLECTION_SYSTEM;
                break;
            case "Geostationary":
                satelliteType = SatelliteType.GEOSTATIONARY;
                break;
            case "Intelsat":
                satelliteType = SatelliteType.INTELSAT;
                break;
            case "SES":
                satelliteType = SatelliteType.SES;
                break;
            case "Iridium":
                satelliteType = SatelliteType.IRIDIUM;
                break;
            case "Iridium NEXT":
                satelliteType = SatelliteType.IRIDIUM_NEXT;
                break;
            case "Orbcomm":
                satelliteType = SatelliteType.ORBCOMM;
                break;
            case "Globalstar":
                satelliteType = SatelliteType.GLOBALSTAR;
                break;
            case "Amateur Radio":
                satelliteType = SatelliteType.AMATEUR_RADIO;
                break;
            case "Experimental":
                satelliteType = SatelliteType.EXPERIMENTAL;
                break;
            case "Other Comm":
                satelliteType = SatelliteType.OTHER_COMMUNICATION;
                break;
            case "Gorizont":
                satelliteType = SatelliteType.GORIZONT;
                break;
            case "Raduga":
                satelliteType = SatelliteType.RADUGA;
                break;
            case "Molniya":
                satelliteType = SatelliteType.MOLNIYA;
                break;
            case "GPS Operational":
                satelliteType = SatelliteType.GPS_OPERATIONAL;
                break;
            case "Glonass Operational":
                satelliteType = SatelliteType.GLONASS_OPERATIONAL;
                break;
            case "Galileo":
                satelliteType = SatelliteType.GALILEO;
                break;
            case "Beidou":
                satelliteType = SatelliteType.BEIDOU;
                break;
            case "Satellite-Based Augmentation System":
                satelliteType = SatelliteType.SATELLITE_BASED_AUGMENTATION_SYSTEM;
                break;
            case "Navy Navigation Satellite System":
                satelliteType = SatelliteType.NAVY_NAVIGATION_SATELLITE_SYSTEM;
                break;
            case "Russian LEO Navigation":
                satelliteType = SatelliteType.RUSSIAN_LEO_NAVIGATION;
                break;
            case "Space & Earth Science":
                satelliteType = SatelliteType.SPACE_AND_EARTH_SCIENCE;
                break;
            case "Geodetic":
                satelliteType = SatelliteType.GEODETIC;
                break;
            case "Engineering":
                satelliteType = SatelliteType.ENGINEERING;
                break;
            case "Education":
                satelliteType = SatelliteType.EDUCATION;
                break;
            case "Miscellaneous Military":
                satelliteType = SatelliteType.MISCELLANEOUS_MILITARY;
                break;
            case "Radar Calibration":
                satelliteType = SatelliteType.RADAR_CALIBRATION;
                break;
            case "CubeSats":
                satelliteType = SatelliteType.CUBESATS;
                break;
            case "Other":
                satelliteType = SatelliteType.OTHER;
                break;
            default:
                satelliteType = null;
        }

        return satelliteType;
    }

    /**
     * Get the URL extension of the satellite type
     * @param satelliteType
     * @return
     */
    public static String getURLString(SatelliteType satelliteType) {
        String URLString;

        switch (satelliteType){
            case LAST_30_DAYS_LAUNCHES:
                URLString = "tle-new.txt";
                break;
            case SPACE_STATIONS:
                URLString = "stations.txt";
                break;
            case WEATHER:
                URLString = "weather.txt";
                break;
            case NOAA:
                URLString = "noaa.txt";
                break;
            case GOES:
                URLString = "goes.txt";
                break;
            case EARTH_RESOURCES:
                URLString = "resource.txt";
                break;
            case SEARCH_AND_RESCUE:
                URLString = "sarsat.txt";
                break;
            case DISASTER_MONITORING:
                URLString = "dmc.txt";
                break;
            case TRACKING_AND_DATA_RELAY_SATELLITE_SYSTEM:
                URLString = "tdrss.txt";
                break;
            case ARGOS_DATA_COLLECTION_SYSTEM:
                URLString = "argos.txt";
                break;
            case GEOSTATIONARY:
                URLString = "geo.txt";
                break;
            case INTELSAT:
                URLString = "intelsat.txt";
                break;
            case SES:
                URLString = "ses.txt";
                break;
            case IRIDIUM:
                URLString = "iridium.txt";
                break;
            case IRIDIUM_NEXT:
                URLString = "iridium-NEXT.txt";
                break;
            case ORBCOMM:
                URLString = "orbcomm.txt";
                break;
            case GLOBALSTAR:
                URLString = "globalstar.txt";
                break;
            case AMATEUR_RADIO:
                URLString = "amateur.txt";
                break;
            case EXPERIMENTAL:
                URLString = "x-comm.txt";
                break;
            case OTHER_COMMUNICATION:
                URLString = "other-comm.txt";
                break;
            case GORIZONT:
                URLString = "gorizont.txt";
                break;
            case RADUGA:
                URLString = "raduga.txt";
                break;
            case MOLNIYA:
                URLString = "molniya.txt";
                break;
            case GPS_OPERATIONAL:
                URLString = "gps-ops.txt";
                break;
            case GLONASS_OPERATIONAL:
                URLString = "glo-ops.txt";
                break;
            case GALILEO:
                URLString = "galileo.txt";
                break;
            case BEIDOU:
                URLString = "beidou.txt";
                break;
            case SATELLITE_BASED_AUGMENTATION_SYSTEM:
                URLString = "sbas.txt";
                break;
            case NAVY_NAVIGATION_SATELLITE_SYSTEM:
                URLString = "nnss.txt";
                break;
            case RUSSIAN_LEO_NAVIGATION:
                URLString = "musson.txt";
                break;
            case SPACE_AND_EARTH_SCIENCE:
                URLString = "science.txt";
                break;
            case GEODETIC:
                URLString = "geodetic.txt";
                break;
            case ENGINEERING:
                URLString = "engineering.txt";
                break;
            case EDUCATION:
                URLString = "education.txt";
                break;
            case MISCELLANEOUS_MILITARY:
                URLString = "military.txt";
                break;
            case RADAR_CALIBRATION:
                URLString = "radar.txt";
                break;
            case CUBESATS:
                URLString = "cubesat.txt";
                break;
            case OTHER:
                URLString = "other.txt";
                break;
            default:
                URLString = null;
        }

        return URLString;
    }

    /**
     * Get all the satellite types. Used this to fill the screen with all types.
     * @return
     */
    public static String[] getAllSatelliteTypeStrings(){
        String [] allSatelliteStrings = new String[SatelliteType.values().length];

        int i = 0;
        for (SatelliteType satelliteType: SatelliteType.values()){
            allSatelliteStrings[i++] = getFullTypeName(satelliteType);
        }

        return allSatelliteStrings;
    }

    /**
     * Create an object of type satellite. Accepts the tle strings.
     * @param tleStrings
     * @return
     */
    public static TLE createTLE(String[] tleStrings){
        return new TLE(tleStrings);
    }
}
