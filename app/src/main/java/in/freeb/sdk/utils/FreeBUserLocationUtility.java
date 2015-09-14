package in.freeb.sdk.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * FreeBUserLocationUtility is a wrapper around fetching the current device's location. It looks for the GPS
 * and Network LocationProviders by default (printStackTrace()'ing if, for example, the app doesn't
 * have the correct permissions in its AndroidManifest.xml). This class is intended to be used for a
 * <i>single</i> location update.
 * When testing, if a fakeLocation is provided (via setFakeLocation()), we don't wait for the
 * LocationManager to fire or for the timer to run out; instead, we build a local LocationListener,
 * then call the onLocationChanged() method manually.
 */

public class FreeBUserLocationUtility extends Service implements LocationListener {
    private final Context mContext;
    /**
     * @author CH-E00953/A.Sharma
     */
    // flag for GPS Status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;
    double accuracy;

    // Declaring a Location Manager
    protected LocationManager locationManager;
    private String TAG = FreeBUserLocationUtility.class.getSimpleName();

    public FreeBUserLocationUtility(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;

                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        updateGPSCoordinates();
                    }
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            updateGPSCoordinates();
                        }
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
            /*Log.e("Error : Location",
                    "Impossible to connect to LocationManager", e);*/
        }

        return location;
    }

    public void updateGPSCoordinates() {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     */

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(FreeBUserLocationUtility.this);
        }
    }

    /**
     * Function to get latitude
     *
     * @return Latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getAccuracy() {
        if (location != null) {
            accuracy = location.getAccuracy();
            //Log.i(TAG  ,"acuracy = "+accuracy);
        }
        return accuracy;
    }

    /**
     * Function to get longitude
     *
     * @return Longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return True if GPS/wifi enabled else false
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Get list of address by latitude and longitude
     *
     * @param context The context used to request location updates.
     * @return null or address
     */
    public List<Address> getGeocoderAddress(Context context) {
        if (location != null) {
            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                return addresses;
            } catch (IOException e) {
                // e.printStackTrace();
				/*Log.e("Error : Geocoder", "Impossible to connect to Geocoder",
						e);*/
            }
        }

        return null;
    }

    /**
     * Try to get AddressLine
     *
     * @param context The context used to request location updates.
     * @return null or addressLine
     */
    public String getAddressLine(Context context) {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);

            return addressLine;
        } else {
            return null;
        }
    }

    /**
     * Try to get Locality
     *
     * @param context The context used to request location updates.
     * @return null or locality
     */
    public String getLocality(Context context) {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String locality = address.getLocality();

            return locality;
        } else {
            return null;
        }
    }

    /**
     * Try to get Postal Code
     *
     * @param context The context used to request location updates.
     * @return null or postalCode
     */
    public String getPostalCode(Context context) {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String postalCode = address.getPostalCode();

            return postalCode;
        } else {
            return null;
        }
    }

    /**
     * Try to get CountryName
     *
     * @param context The context used to request location updates.
     * @return null or postalCode
     */
    public String getCountryName(Context context) {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String countryName = address.getCountryName();

            return countryName;
        } else {
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}