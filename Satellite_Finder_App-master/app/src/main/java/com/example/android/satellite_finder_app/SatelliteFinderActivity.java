package com.example.android.satellite_finder_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.satellite_finder_app.utilities.DeviceSensorUtils;
import com.example.android.satellite_finder_app.utilities.SatelliteUtils;
import com.google.gson.Gson;

import java.util.Date;

import uk.me.g4dpz.satellite.SatPos;
import uk.me.g4dpz.satellite.Satellite;
import uk.me.g4dpz.satellite.SatelliteFactory;
import uk.me.g4dpz.satellite.TLE;

/**
 * Implements LocationListener as well as SensorEventListener
 * Get the location first and then get the device orientation
 * Also creates a Satellite object and GroundPosition object to get the satellite's position.
 */
public class SatelliteFinderActivity extends AppCompatActivity implements
        LocationListener, SensorEventListener{

    // Display on screen messages
    ImageView mImageView;
    ProgressBar mProgressBar;
    TextView mTextView1, mTextView2, mTextView3;

    // Device location
    Location mLocation;

    // Satellite Object
    Satellite mSatellite;

    // The location manager for handling location events
    LocationManager locationManager;

    // Sensor manager to do the same for sensor events
    SensorManager sensorManager;

    // To hold the device senors
    Sensor rotVector;

    // Hold accelerometer and magnetic sensor attributes
    float[] mRotationMatrix;
    float[] mOutRotationMatrix;

    // A code for storing the permissions while asking for permissions
    final static int M_LOCATION_PERMISSION_CODE = 1234;

    /**
     * Performs initial setup
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satellite_finder);

        // Initialize the display parameters
        mImageView = (ImageView) findViewById(R.id.tv_satellite_direction);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator_location);
        mTextView1 = (TextView) findViewById(R.id.text_instructions_one);
        mTextView2 = (TextView) findViewById(R.id.text_instructions_two);

        //Animation for the TextView
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        mTextView1.startAnimation(animation1);
        mTextView2.startAnimation(animation1);
        mTextView1.postDelayed(new Runnable() {
            public void run() {
                mTextView1.setVisibility(View.INVISIBLE);
            }
        }, 4000);
        mTextView2.postDelayed(new Runnable() {
            public void run() {
                mTextView2.setVisibility(View.INVISIBLE);
            }
        }, 4000);

        //Setting title in titlebar
        String satelliteName = getIntent().getExtras().getString("sat_name");
        setTitle(satelliteName);

        // Get the selected satellite
        Intent intent = getIntent();
        String passedGSON = intent.getStringExtra(SatelliteUtils.SATELLITE);
        TLE passedTLE = new Gson().fromJson(passedGSON, TLE.class);
        mSatellite = SatelliteFactory.createSatellite(passedTLE);

        // Initialize the managers
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Initialize the sensors
        rotVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        // Asking for permissions if not granted already and start by getting the location
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // Request permissions
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, M_LOCATION_PERMISSION_CODE);

            }
            else {
                getLocation();
            }
        }
        else {
            getLocation();
        }
    }

    /**
     * Once permissions are granted/denied
     * @param requestCode The request code
     * @param permissions the permissions
     * @param grantResults the result for these permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case M_LOCATION_PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }
                else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    showErrorMessage();
                }
        }
    }

    /**
     * Request for location from GPS PROVIDER
     */
    private void getLocation() {
        showLoader();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
    }

    /**
     * Request for device orientation by registering sensors
     */
    private void getOrientation() {
        sensorManager.registerListener(this, rotVector, SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Once the sensor responds, display appropriate message
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        // Making sure it is the sensor we want
        switch (sensorEvent.sensor.getType()) {

            case Sensor.TYPE_ROTATION_VECTOR:
                mRotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(mRotationMatrix, sensorEvent.values);
                break;
            default:
                return;
        }

        if(mRotationMatrix != null) {
            float orientation[] = new float[3];
            mOutRotationMatrix = new float[16];

            // Remapping for landscape mode
            SensorManager.remapCoordinateSystem(mRotationMatrix, SensorManager.AXIS_X,
                    SensorManager.AXIS_Z, mOutRotationMatrix);   // Remap coordinate System to compensate for the landscape position of device
            SensorManager.getOrientation(mOutRotationMatrix, orientation);

            // Adjustment for True north
            GeomagneticField geomagneticField = new GeomagneticField((float) mLocation.getLatitude(),
                    (float) mLocation.getLongitude(), (float) mLocation.getAltitude(),
                    new Date().getTime());

            // The values
            double azimuth = (float) (Math.toDegrees(orientation[0]) + geomagneticField.getDeclination()); //Azimuth; (Degrees);
            double pitch = (float) Math.toDegrees(orientation[1]) * -1; //Pitch; (Degrees); down is 90 , up is -90.

            Log.d("TAG", "Azimuth: " + azimuth + " Pitch: " + pitch);
            // Get the direction to point the device so it match satellite position
            DeviceSensorUtils.Direction dir = DeviceSensorUtils.getDirection(this, azimuth, pitch, mSatellite, mLocation);
            String dirString = DeviceSensorUtils.getDirectionString(dir);

            // Show the appropriate direction
            showDirection(dirString);
        }
    }

    /**
     * Shows the direction
     * @param dirString direction as string
     */
    private void showDirection(String dirString) {
        mProgressBar.setVisibility(View.INVISIBLE);
        mImageView.setVisibility(View.VISIBLE);
//        int id = getResources().getIdentifier(dirString,"drawable",getPackageName());
        if(dirString != null) {
            mImageView.setImageResource(getResources().getIdentifier(dirString,"drawable",getPackageName()));
        }
        else{
            mImageView.setImageResource(R.drawable.rotate_device);
        }
    }

    /**
     * Shows the error message if permission is not granted
     */
    private void showErrorMessage(){
        mProgressBar.setVisibility(View.INVISIBLE);
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setImageResource(R.drawable.permission_not_granted);
    }

    /**
     * Show the loader
     */
    private void showLoader(){
        mImageView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Not implemented
     * @param sensor
     * @param i
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * Once location is updated, this function is called
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        // Set the location object
        mLocation = location;
        //Remove the updates as we don't need the location often
        locationManager.removeUpdates(this);

        // Now check the orientation
        getOrientation();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    /**
     * In case we do not have the GPS enabled
     * @param s
     */
    @Override
    public void onProviderDisabled(String s) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    /**
     * Making sure to shut down the listeners to save battery on screen off
     */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
        sensorManager.unregisterListener(this);
    }

    /**
     * Start the process again if we resume the app
     */
    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }
}