/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.location.Address
 *  android.location.Criteria
 *  android.location.Geocoder
 *  android.location.Location
 *  android.location.LocationListener
 *  android.location.LocationManager
 *  android.location.OnNmeaMessageListener
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.provider.Settings$SettingNotFoundException
 *  android.util.Log
 */
package com.syntak.library;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.syntak.library.StringOp;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapOp {
    public static final int NEW_LOCATION_TIME_THRESHOLD = 60000;
    public static final int PROVIDER_COARSE = 1;
    public static final int PROVIDER_FINE = 2;
    public static final int PROVIDER_GPS = 12;
    public static final int PROVIDER_NETWORK = 11;
    public static final int PROVIDER_PASSIVE = 13;

    public static void enableLocation(Context context) {
        context.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    public static Address getAddressFromString(Context object, String object2) {
        Geocoder geocoder = new Geocoder(object);
        object = null;
        try {
            object2 = geocoder.getFromLocationName((String)object2, 5);
            if (object2 == null) {
                return null;
            }
            object2 = (Address)object2.get(0);
            return object2;
        }
        catch (IOException iOException) {
            return object;
        }
    }

    public static String getAddressString(Context object, double d, double d2) {
        Object object2 = new Geocoder(object, Locale.getDefault());
        Object object3 = object = "";
        try {
            object2 = object2.getFromLocation(d, d2, 1);
            int n = 0;
            object3 = object;
            Address address = (Address)object2.get(0);
            do {
                object3 = object;
                object2 = object;
                if (n > address.getMaxAddressLineIndex()) return object2;
                object3 = object;
                object3 = object;
                object2 = new StringBuilder();
                object3 = object;
                ((StringBuilder)object2).append((String)object);
                object3 = object;
                ((StringBuilder)object2).append(address.getAddressLine(n));
                object3 = object;
                object = ((StringBuilder)object2).toString();
                ++n;
            } while (true);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return object3;
        }
    }

    public static String getCountryCode(Context object, double d, double d2) {
        object = new Geocoder(object, Locale.getDefault());
        try {
            return ((Address)object.getFromLocation(d, d2, 1).get(0)).getCountryCode();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return null;
        }
    }

    public static Location getLastLocation(Context object) {
        LocationManager locationManager = (LocationManager)object.getSystemService("location");
        Object object2 = new Criteria();
        object2.setAccuracy(2);
        object = locationManager.getBestProvider(object2, true);
        Location location = null;
        object = object != null ? locationManager.getLastKnownLocation((String)object) : null;
        object2.setAccuracy(1);
        object2 = locationManager.getBestProvider(object2, true);
        if (object2 != null) {
            location = locationManager.getLastKnownLocation((String)object2);
        }
        if (object != null && location != null) {
            if (!MapOp.isBetterLocation((Location)object, location)) return object;
            return location;
        }
        if (location == null) return object;
        return location;
    }

    public static boolean isBetterLocation(Location location, Location location2) {
        boolean bl = true;
        if (location == null) {
            return true;
        }
        boolean bl2 = location2.getTime() > location.getTime();
        boolean bl3 = location2.getAccuracy() < location.getAccuracy();
        if (bl3 && bl2) {
            return true;
        }
        if (!bl3) return false;
        if (bl2) return false;
        if (location.getTime() - location2.getTime() <= 60000L) return false;
        return bl;
    }

    public static boolean isCoarseEnabled(Context context) {
        context = (LocationManager)context.getSystemService("location");
        Object object = new Criteria();
        object.setAccuracy(2);
        object = context.getBestProvider(object, true);
        if (StringOp.strlen((String)object) <= 0) return false;
        return context.isProviderEnabled((String)object);
    }

    public static boolean isFineEnabled(Context context) {
        context = (LocationManager)context.getSystemService("location");
        Object object = new Criteria();
        object.setAccuracy(1);
        object = context.getBestProvider(object, true);
        if (StringOp.strlen((String)object) <= 0) return false;
        return context.isProviderEnabled((String)object);
    }

    public static boolean isLocationEnabled(Context context) {
        int n = Build.VERSION.SDK_INT;
        boolean bl = true;
        boolean bl2 = true;
        if (n >= 19) {
            try {
                n = Settings.Secure.getInt((ContentResolver)context.getContentResolver(), (String)"location_mode");
            }
            catch (Settings.SettingNotFoundException settingNotFoundException) {
                settingNotFoundException.printStackTrace();
                return false;
            }
            if (n == 0) return false;
            return bl2;
        }
        if (StringOp.strlen(Settings.Secure.getString((ContentResolver)context.getContentResolver(), (String)"location_providers_allowed")) <= 0) return false;
        return bl;
    }

    public static boolean navigate_by_GoogleMap(Context context, double d, double d2, TRAVEL_MODE object) {
        int n = 1.$SwitchMap$com$syntak$library$MapOp$TRAVEL_MODE[object.ordinal()];
        CharSequence charSequence = "d";
        object = charSequence;
        if (n != 1) {
            object = n != 2 ? (n != 3 ? (n != 4 ? charSequence : "w") : "l") : "b";
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("google.navigation:q=");
        ((StringBuilder)charSequence).append(d);
        ((StringBuilder)charSequence).append(",");
        ((StringBuilder)charSequence).append(d2);
        ((StringBuilder)charSequence).append("&mode=");
        ((StringBuilder)charSequence).append((String)object);
        object = new Intent("android.intent.action.VIEW", Uri.parse((String)((StringBuilder)charSequence).toString()));
        object.setPackage("com.google.android.apps.maps");
        if (object.resolveActivity(context.getPackageManager()) == null) return false;
        context.startActivity((Intent)object);
        return true;
    }

    public static void restoreMockLocationSettings(Context context, int n) {
        try {
            Settings.Secure.putInt((ContentResolver)context.getContentResolver(), (String)"mock_location", (int)n);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public static int setMockLocationSettings(Context context) {
        void var0_3;
        int n;
        block4 : {
            int n2 = 1;
            n = Settings.Secure.getInt((ContentResolver)context.getContentResolver(), (String)"mock_location");
            try {
                Settings.Secure.putInt((ContentResolver)context.getContentResolver(), (String)"mock_location", (int)1);
                return n;
            }
            catch (Exception exception) {}
            break block4;
            catch (Exception exception) {
                n = n2;
            }
        }
        var0_3.printStackTrace();
        return n;
    }

    public static void show_Route_on_GoogleMap(Context context, Location object, Location object2, Location[] object3, String string2) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("&origin=");
        charSequence.append(object.getLatitude());
        charSequence.append(",");
        charSequence.append(object.getLongitude());
        charSequence = charSequence.toString();
        object = new StringBuilder();
        ((StringBuilder)object).append("&destination=");
        ((StringBuilder)object).append(object2.getLatitude());
        ((StringBuilder)object).append(",");
        ((StringBuilder)object).append(object2.getLongitude());
        object2 = ((StringBuilder)object).toString();
        object = "";
        if (object3 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < ((Object)object3).length; ++i) {
                object = stringBuilder.toString().equals("") ? "" : "%7C";
                stringBuilder.append((String)object);
                stringBuilder.append(object3[i].getLatitude());
                stringBuilder.append(",");
                stringBuilder.append(object3[i].getLongitude());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("&waypoints=");
            ((StringBuilder)object).append(stringBuilder.toString());
            object = ((StringBuilder)object).toString();
        }
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("https://www.google.com/maps/dir/?api=1&travelmode=");
        ((StringBuilder)object3).append(string2);
        ((StringBuilder)object3).append((String)charSequence);
        ((StringBuilder)object3).append((String)object2);
        ((StringBuilder)object3).append((String)object);
        object = new Intent("android.intent.action.VIEW", Uri.parse((String)((StringBuilder)object3).toString()));
        object.setFlags(268435456);
        object.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        context.startActivity((Intent)object);
    }

    public boolean show_Location_on_GoogleMap(Context context, double d, double d2, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("geo:");
        stringBuilder.append(d);
        stringBuilder.append(",");
        stringBuilder.append(d2);
        stringBuilder.append("?q=");
        stringBuilder.append(d);
        stringBuilder.append(",");
        stringBuilder.append(d2);
        stringBuilder.append("(");
        stringBuilder.append(string2);
        stringBuilder.append(")");
        string2 = new Intent("android.intent.action.VIEW", Uri.parse((String)stringBuilder.toString()));
        string2.setPackage("com.google.android.apps.maps");
        if (string2.resolveActivity(context.getPackageManager()) == null) return false;
        context.startActivity((Intent)string2);
        return true;
    }

    public static class GetNemaLocation {
        Context context;
        LocationListener locationListener;
        LocationManager locationManager;
        OnNmeaMessageListener nmeaListener;

        public GetNemaLocation(Context context) {
            this.context = context;
        }

        public void onLocationReceived(double d, double d2) {
        }

        public void start() {
            this.locationManager = (LocationManager)this.context.getSystemService("location");
            this.locationListener = new LocationListener(){

                public void onLocationChanged(Location location) {
                    location.getLatitude();
                    location.getLongitude();
                }

                public void onProviderDisabled(String string2) {
                }

                public void onProviderEnabled(String string2) {
                }

                public void onStatusChanged(String string2, int n, Bundle bundle) {
                }
            };
            this.nmeaListener = new OnNmeaMessageListener(){

                public void onNmeaMessage(String arrstring, long l) {
                    double d;
                    double d2;
                    Log.d((String)"GPS-NMEA", (String)arrstring);
                    arrstring = arrstring.split(",");
                    if (!arrstring[0].equalsIgnoreCase("$GPGGA")) return;
                    double d3 = d = Double.parseDouble(arrstring[2].substring(0, 1)) + Double.parseDouble(arrstring[2].substring(2)) / 60.0;
                    if ("S".equalsIgnoreCase(arrstring[3])) {
                        d3 = d;
                        if (d > 0.0) {
                            d3 = -d;
                        }
                    }
                    d = d2 = Double.parseDouble(arrstring[4].substring(0, 2)) + Double.parseDouble(arrstring[4].substring(3)) / 60.0;
                    if ("W".equalsIgnoreCase(arrstring[5])) {
                        d = d2;
                        if (d2 > 0.0) {
                            d = -d2;
                        }
                    }
                    GetNemaLocation.this.onLocationReceived(d3, d);
                }
            };
            this.locationManager.requestLocationUpdates("gps", 2000L, 0.0f, this.locationListener);
            this.locationManager.addNmeaListener(this.nmeaListener);
        }

        public void stop() {
            this.locationManager.removeUpdates(this.locationListener);
            this.locationManager.removeNmeaListener(this.nmeaListener);
        }

    }

    public static class GoogleLocationService {
        Context context;
        boolean flag_updating_location = true;
        GoogleApiClient googleApiClient = null;
        com.google.android.gms.location.LocationListener locationListener = null;
        int priority = 100;

        public GoogleLocationService(Context context) {
            this.context = context;
            GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks(){

                @Override
                public void onConnected(Bundle bundle) {
                    GoogleLocationService.this.OnGoogleLocationServiceConnected(bundle);
                }

                @Override
                public void onConnectionSuspended(int n) {
                    GoogleLocationService.this.OnGoogleLocationServiceSuspended(n);
                }
            };
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener(){

                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    GoogleLocationService.this.OnGoogleLocationServiceFailed(connectionResult);
                }
            };
            this.googleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(connectionCallbacks).addOnConnectionFailedListener(onConnectionFailedListener).addApi(LocationServices.API).build();
        }

        public void OnGoogleLocationServiceConnected(Bundle bundle) {
        }

        public void OnGoogleLocationServiceFailed(ConnectionResult connectionResult) {
        }

        public void OnGoogleLocationServiceSuspended(int n) {
        }

        public void OnLocationChanged(Location location) {
        }

        public void connect() {
            GoogleApiClient googleApiClient = this.googleApiClient;
            if (googleApiClient == null) return;
            googleApiClient.connect();
        }

        public void disconnect() {
            GoogleApiClient googleApiClient = this.googleApiClient;
            if (googleApiClient == null) return;
            googleApiClient.disconnect();
        }

        public Location getLastLocation() {
            return LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
        }

        public void setPriority(int n) {
            this.priority = n;
        }

        public void startLocationUpdating(int n, int n2) {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(n);
            locationRequest.setFastestInterval(n2);
            locationRequest.setPriority(this.priority);
            this.locationListener = new com.google.android.gms.location.LocationListener(){

                @Override
                public void onLocationChanged(Location location) {
                    GoogleLocationService.this.OnLocationChanged(location);
                }
            };
            LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, locationRequest, this.locationListener);
        }

        public void stop() {
            this.stopLocationUpdating();
            this.disconnect();
        }

        public void stopLocationUpdating() {
            if (this.googleApiClient == null) return;
            if (this.locationListener == null) return;
            LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, this.locationListener);
        }

    }

    public static class LocationService {
        Context context;
        boolean isOnce = true;
        LocationListener listener = null;
        LocationListener listenerCoarse = null;
        LocationListener listenerFine = null;
        LocationListener listenerGps = null;
        LocationListener listenerNetwork = null;
        LocationListener listenerPassive = null;
        LocationManager mLocationManager;
        float minDistance = 0.0f;
        long minTime = 0L;
        int provider_type;

        public LocationService(Context context, boolean bl) {
            this.context = context;
            this.isOnce = bl;
            this.mLocationManager = (LocationManager)context.getSystemService("location");
        }

        public LocationService(Context context, boolean bl, long l, float f) {
            this.minTime = l;
            this.minDistance = f;
            this.context = context;
            this.isOnce = bl;
            this.mLocationManager = (LocationManager)context.getSystemService("location");
        }

        private LocationListener listerner_init(final int n) {
            return new LocationListener(){

                public void onLocationChanged(Location location) {
                    if (LocationService.this.isOnce) {
                        LocationService.this.mLocationManager.removeUpdates((LocationListener)this);
                    }
                    LocationService.this.OnLocationRefreshed(location, n);
                }

                public void onProviderDisabled(String string2) {
                }

                public void onProviderEnabled(String string2) {
                }

                public void onStatusChanged(String string2, int n2, Bundle bundle) {
                }
            };
        }

        public void OnLocationRefreshed(Location location, int n) {
        }

        /*
         * Unable to fully structure code
         */
        public void start(int var1_1) {
            this.provider_type = var1_1;
            this.listener = this.listerner_init(var1_1);
            var2_2 = new Criteria();
            var3_10 = "network";
            if (var1_1 != 1) {
                if (var1_1 != 2) {
                    var2_3 = var3_10;
                    switch (var1_1) {
                        default: {
                            var2_4 = var3_10;
                            break;
                        }
                        case 13: {
                            var2_6 = "passive";
                            ** break;
                        }
                        case 12: {
                            var2_7 = "gps";
                            ** break;
                        }
lbl18: // 3 sources:
                        case 11: 
                    }
                } else {
                    var2_2.setAccuracy(1);
                    var2_2.setSpeedRequired(true);
                    var2_2.setAltitudeRequired(false);
                    var2_2.setBearingRequired(false);
                    var2_2.setCostAllowed(false);
                    var2_8 = this.mLocationManager.getBestProvider(var2_2, true);
                }
            } else {
                var2_2.setAccuracy(2);
                var2_9 = this.mLocationManager.getBestProvider(var2_2, true);
            }
            this.mLocationManager.requestLocationUpdates((String)var2_5, this.minTime, this.minDistance, this.listener);
        }

        public void stop() {
            LocationListener locationListener = this.listenerCoarse;
            if (locationListener != null) {
                this.mLocationManager.removeUpdates(locationListener);
            }
            if ((locationListener = this.listenerFine) != null) {
                this.mLocationManager.removeUpdates(locationListener);
            }
            if ((locationListener = this.listenerGps) != null) {
                this.mLocationManager.removeUpdates(locationListener);
            }
            if ((locationListener = this.listenerNetwork) != null) {
                this.mLocationManager.removeUpdates(locationListener);
            }
            if ((locationListener = this.listenerPassive) == null) return;
            this.mLocationManager.removeUpdates(locationListener);
        }

    }

    public static class MockLocation {
        Context context;
        LocationManager locationManager;
        String locationProvider;

        public MockLocation(Context context) {
            this.context = context;
        }

        public void refresh(Location location) {
            Location location2 = new Location(this.locationProvider);
            location2.setLatitude(location.getLatitude());
            location2.setLongitude(location.getLongitude());
            location2.setAltitude(location.getAltitude());
            location2.setTime(System.currentTimeMillis());
            int n = MapOp.setMockLocationSettings(this.context);
            this.locationManager.setTestProviderLocation(this.locationProvider, location2);
            MapOp.restoreMockLocationSettings(this.context, n);
        }

        public void start() {
            LocationManager locationManager;
            this.locationProvider = "gps";
            this.locationManager = locationManager = (LocationManager)this.context.getSystemService("location");
            locationManager.addTestProvider(this.locationProvider, false, false, false, false, true, true, true, 0, 5);
            this.locationManager.setTestProviderEnabled(this.locationProvider, true);
        }

        public void stop() {
            this.locationManager.setTestProviderEnabled(this.locationProvider, false);
        }
    }

    public static final class TRAVEL_MODE
    extends Enum<TRAVEL_MODE> {
        private static final /* synthetic */ TRAVEL_MODE[] $VALUES;
        public static final /* enum */ TRAVEL_MODE BICYCLE;
        public static final /* enum */ TRAVEL_MODE DRIVE;
        public static final /* enum */ TRAVEL_MODE TWO_WHEELER;
        public static final /* enum */ TRAVEL_MODE WALK;

        static {
            TRAVEL_MODE tRAVEL_MODE;
            DRIVE = new TRAVEL_MODE();
            BICYCLE = new TRAVEL_MODE();
            TWO_WHEELER = new TRAVEL_MODE();
            WALK = tRAVEL_MODE = new TRAVEL_MODE();
            $VALUES = new TRAVEL_MODE[]{DRIVE, BICYCLE, TWO_WHEELER, tRAVEL_MODE};
        }

        public static TRAVEL_MODE valueOf(String string2) {
            return Enum.valueOf(TRAVEL_MODE.class, string2);
        }

        public static TRAVEL_MODE[] values() {
            return (TRAVEL_MODE[])$VALUES.clone();
        }
    }

}

