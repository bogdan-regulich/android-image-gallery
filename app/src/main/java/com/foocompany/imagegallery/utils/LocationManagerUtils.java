package com.foocompany.imagegallery.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Bogdan on 20-Jul-14.
 */
public class LocationManagerUtils {

    private LocationManagerUtils() { }

    public static Location getLastKnownLocationFromBestAvailableProvider(Context mContext) {
        LocationManager locationManager =
                (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager == null)
            return null;

        Location fromNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location fromGps     = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return fromGps != null ? fromGps : fromNetwork;
    }
}
