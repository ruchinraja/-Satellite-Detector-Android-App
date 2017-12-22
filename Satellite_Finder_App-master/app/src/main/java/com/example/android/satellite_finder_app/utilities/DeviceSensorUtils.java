package com.example.android.satellite_finder_app.utilities;

import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.example.android.satellite_finder_app.R;

import java.util.Date;

import uk.me.g4dpz.satellite.GroundStationPosition;
import uk.me.g4dpz.satellite.SatPos;
import uk.me.g4dpz.satellite.Satellite;

/**
 * Created by dbzabhiram on 7/10/17.
 */

public final class DeviceSensorUtils {

    // The enum for direction
    public enum Direction{
        UP,
        DOWN,
        RIGHT,
        LEFT,
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT,
        CENTER
    }

    // The solid view angle within which we accept as a permissible limit
    private final static int VIEW_ANGLE = 5;

    /**
     * Calculates direction of satellite wrt device
     * @param context Current context
     * @param deviceAzimuth Azimuth of the device
     * @param devicePitch Pitch of the device
     * @param mSatellite The satellite
     * @param location location of the device
     * @return the direction to be shown on device
     */
    public static Direction getDirection(Context context, double deviceAzimuth, double devicePitch, Satellite mSatellite, Location location){
        Direction direction = null;

        double deviceElevation = devicePitch;
        GroundStationPosition mDevicePosition = new GroundStationPosition(location.getLatitude(),
                location.getLongitude(),
                location.getAltitude());
        Date now = new Date();

        SatPos satellitePosition = mSatellite.getPosition(mDevicePosition, now);

        double satAzimuth = Math.toDegrees(satellitePosition.getAzimuth());

        // Bringing the azimuth to a [-180,180]
        if(satAzimuth > 180) {
            satAzimuth -= 360;
        }

        double satElevation = Math.toDegrees(satellitePosition.getElevation());

        // Calculating the difference between the satellite look angle
        // and device look angle
        double diffAzimuth = satAzimuth - deviceAzimuth;
        if(diffAzimuth > 180){
            diffAzimuth -= 360;
        }
        else if(diffAzimuth <-180){
            diffAzimuth += 360;
        }

        double diffElevation = satElevation - deviceElevation;

        // Finally getting the direction
        direction = Direction.CENTER;
        if(  diffElevation < -1 * VIEW_ANGLE) {
            direction = Direction.DOWN;
            if( diffAzimuth < -1 * VIEW_ANGLE){
                direction = Direction.DOWN_LEFT;
            }
            else if (diffAzimuth > VIEW_ANGLE) {
                direction = Direction.DOWN_RIGHT;
            }
        }
        else if(diffElevation > VIEW_ANGLE){
            direction = Direction.UP;
            if( diffAzimuth < -1 * VIEW_ANGLE){
                direction = Direction.UP_LEFT;
            }
            else if (diffAzimuth > VIEW_ANGLE) {
                direction = Direction.UP_RIGHT;
            }
        }
        else {
            if (diffAzimuth < -1 * VIEW_ANGLE) {
                direction = Direction.LEFT;
            } else if (diffAzimuth > VIEW_ANGLE) {
                direction = Direction.RIGHT;
            }
        }

        Log.d("TAG", "Azimuth: " + deviceAzimuth + " "  + satAzimuth + " Pitch: " + deviceElevation  + " " + satElevation);
        return direction;
    }

    /**
     * Returns the string to be displayed
     * @param direction The direction Enum
     * @return direction as a string
     */
    public static String getDirectionString(Direction direction){
        if(direction == null){
            return null;
        }

        switch (direction){
            case UP:
                return "up";
            case DOWN:
                return "down";
            case RIGHT:
                return "right";
            case LEFT:
                return "left";
            case UP_LEFT:
                return "up_left";
            case UP_RIGHT:
                return "up_right";
            case DOWN_LEFT:
                return "down_left";
            case DOWN_RIGHT:
                return "down_right";
            case CENTER:
                return "found";
            default:
                return null;
        }
    }
}
