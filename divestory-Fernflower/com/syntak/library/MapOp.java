package com.syntak.library;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

   public static void enableLocation(Context var0) {
      var0.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
   }

   public static Address getAddressFromString(Context var0, String var1) {
      Geocoder var2 = new Geocoder(var0);
      Address var5 = null;

      boolean var10001;
      List var6;
      try {
         var6 = var2.getFromLocationName(var1, 5);
      } catch (IOException var4) {
         var10001 = false;
         return var5;
      }

      if (var6 == null) {
         return null;
      } else {
         Address var7;
         try {
            var7 = (Address)var6.get(0);
         } catch (IOException var3) {
            var10001 = false;
            return var5;
         }

         var5 = var7;
         return var5;
      }
   }

   public static String getAddressString(Context var0, double var1, double var3) {
      Geocoder var5 = new Geocoder(var0, Locale.getDefault());
      String var17 = "";
      String var6 = var17;

      IOException var10000;
      String var20;
      label73: {
         boolean var10001;
         List var19;
         try {
            var19 = var5.getFromLocation(var1, var3, 1);
         } catch (IOException var16) {
            var10000 = var16;
            var10001 = false;
            break label73;
         }

         int var7 = 0;
         var6 = var17;

         Address var8;
         try {
            var8 = (Address)var19.get(0);
         } catch (IOException var15) {
            var10000 = var15;
            var10001 = false;
            break label73;
         }

         while(true) {
            var6 = var17;
            var20 = var17;

            try {
               if (var7 > var8.getMaxAddressLineIndex()) {
                  return var20;
               }
            } catch (IOException var14) {
               var10000 = var14;
               var10001 = false;
               break;
            }

            var6 = var17;

            StringBuilder var21;
            try {
               var21 = new StringBuilder;
            } catch (IOException var13) {
               var10000 = var13;
               var10001 = false;
               break;
            }

            var6 = var17;

            try {
               var21.<init>();
            } catch (IOException var12) {
               var10000 = var12;
               var10001 = false;
               break;
            }

            var6 = var17;

            try {
               var21.append(var17);
            } catch (IOException var11) {
               var10000 = var11;
               var10001 = false;
               break;
            }

            var6 = var17;

            try {
               var21.append(var8.getAddressLine(var7));
            } catch (IOException var10) {
               var10000 = var10;
               var10001 = false;
               break;
            }

            var6 = var17;

            try {
               var17 = var21.toString();
            } catch (IOException var9) {
               var10000 = var9;
               var10001 = false;
               break;
            }

            ++var7;
         }
      }

      IOException var18 = var10000;
      var18.printStackTrace();
      var20 = var6;
      return var20;
   }

   public static String getCountryCode(Context var0, double var1, double var3) {
      Geocoder var6 = new Geocoder(var0, Locale.getDefault());

      String var7;
      try {
         var7 = ((Address)var6.getFromLocation(var1, var3, 1).get(0)).getCountryCode();
      } catch (IOException var5) {
         var5.printStackTrace();
         var7 = null;
      }

      return var7;
   }

   public static Location getLastLocation(Context var0) {
      LocationManager var1 = (LocationManager)var0.getSystemService("location");
      Criteria var2 = new Criteria();
      var2.setAccuracy(2);
      String var4 = var1.getBestProvider(var2, true);
      Location var3 = null;
      Location var5;
      if (var4 != null) {
         var5 = var1.getLastKnownLocation(var4);
      } else {
         var5 = null;
      }

      var2.setAccuracy(1);
      String var6 = var1.getBestProvider(var2, true);
      if (var6 != null) {
         var3 = var1.getLastKnownLocation(var6);
      }

      if (var5 != null && var3 != null) {
         return isBetterLocation(var5, var3) ? var3 : var5;
      } else {
         return var3 != null ? var3 : var5;
      }
   }

   public static boolean isBetterLocation(Location var0, Location var1) {
      boolean var2 = true;
      if (var0 == null) {
         return true;
      } else {
         boolean var3;
         if (var1.getTime() > var0.getTime()) {
            var3 = true;
         } else {
            var3 = false;
         }

         boolean var4;
         if (var1.getAccuracy() < var0.getAccuracy()) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4 && var3) {
            return true;
         } else if (var4 && !var3) {
            if (var0.getTime() - var1.getTime() <= 60000L) {
               var2 = false;
            }

            return var2;
         } else {
            return false;
         }
      }
   }

   public static boolean isCoarseEnabled(Context var0) {
      LocationManager var3 = (LocationManager)var0.getSystemService("location");
      Criteria var1 = new Criteria();
      var1.setAccuracy(2);
      String var4 = var3.getBestProvider(var1, true);
      boolean var2;
      if (StringOp.strlen(var4) > 0) {
         var2 = var3.isProviderEnabled(var4);
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean isFineEnabled(Context var0) {
      LocationManager var3 = (LocationManager)var0.getSystemService("location");
      Criteria var1 = new Criteria();
      var1.setAccuracy(1);
      String var4 = var3.getBestProvider(var1, true);
      boolean var2;
      if (StringOp.strlen(var4) > 0) {
         var2 = var3.isProviderEnabled(var4);
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean isLocationEnabled(Context var0) {
      int var1 = VERSION.SDK_INT;
      boolean var2 = true;
      boolean var3 = true;
      if (var1 >= 19) {
         try {
            var1 = Secure.getInt(var0.getContentResolver(), "location_mode");
         } catch (SettingNotFoundException var4) {
            var4.printStackTrace();
            var1 = 0;
         }

         if (var1 == 0) {
            var3 = false;
         }

         return var3;
      } else {
         if (StringOp.strlen(Secure.getString(var0.getContentResolver(), "location_providers_allowed")) > 0) {
            var3 = var2;
         } else {
            var3 = false;
         }

         return var3;
      }
   }

   public static boolean navigate_by_GoogleMap(Context var0, double var1, double var3, MapOp.TRAVEL_MODE var5) {
      int var6 = null.$SwitchMap$com$syntak$library$MapOp$TRAVEL_MODE[var5.ordinal()];
      String var7 = "d";
      String var8 = var7;
      if (var6 != 1) {
         if (var6 != 2) {
            if (var6 != 3) {
               if (var6 != 4) {
                  var8 = var7;
               } else {
                  var8 = "w";
               }
            } else {
               var8 = "l";
            }
         } else {
            var8 = "b";
         }
      }

      StringBuilder var9 = new StringBuilder();
      var9.append("google.navigation:q=");
      var9.append(var1);
      var9.append(",");
      var9.append(var3);
      var9.append("&mode=");
      var9.append(var8);
      Intent var10 = new Intent("android.intent.action.VIEW", Uri.parse(var9.toString()));
      var10.setPackage("com.google.android.apps.maps");
      if (var10.resolveActivity(var0.getPackageManager()) != null) {
         var0.startActivity(var10);
         return true;
      } else {
         return false;
      }
   }

   public static void restoreMockLocationSettings(Context var0, int var1) {
      try {
         Secure.putInt(var0.getContentResolver(), "mock_location", var1);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public static int setMockLocationSettings(Context var0) {
      byte var1 = 1;

      int var2;
      Exception var5;
      label20: {
         try {
            var2 = Secure.getInt(var0.getContentResolver(), "mock_location");
         } catch (Exception var4) {
            var5 = var4;
            var2 = var1;
            break label20;
         }

         try {
            Secure.putInt(var0.getContentResolver(), "mock_location", 1);
            return var2;
         } catch (Exception var3) {
            var5 = var3;
         }
      }

      var5.printStackTrace();
      return var2;
   }

   public static void show_Route_on_GoogleMap(Context var0, Location var1, Location var2, Location[] var3, String var4) {
      StringBuilder var5 = new StringBuilder();
      var5.append("&origin=");
      var5.append(var1.getLatitude());
      var5.append(",");
      var5.append(var1.getLongitude());
      String var13 = var5.toString();
      StringBuilder var8 = new StringBuilder();
      var8.append("&destination=");
      var8.append(var2.getLatitude());
      var8.append(",");
      var8.append(var2.getLongitude());
      String var10 = var8.toString();
      String var9 = "";
      if (var3 != null) {
         StringBuilder var6 = new StringBuilder();

         for(int var7 = 0; var7 < var3.length; ++var7) {
            if (var6.toString().equals("")) {
               var9 = "";
            } else {
               var9 = "%7C";
            }

            var6.append(var9);
            var6.append(var3[var7].getLatitude());
            var6.append(",");
            var6.append(var3[var7].getLongitude());
         }

         var8 = new StringBuilder();
         var8.append("&waypoints=");
         var8.append(var6.toString());
         var9 = var8.toString();
      }

      StringBuilder var11 = new StringBuilder();
      var11.append("https://www.google.com/maps/dir/?api=1&travelmode=");
      var11.append(var4);
      var11.append(var13);
      var11.append(var10);
      var11.append(var9);
      Intent var12 = new Intent("android.intent.action.VIEW", Uri.parse(var11.toString()));
      var12.setFlags(268435456);
      var12.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
      var0.startActivity(var12);
   }

   public boolean show_Location_on_GoogleMap(Context var1, double var2, double var4, String var6) {
      StringBuilder var7 = new StringBuilder();
      var7.append("geo:");
      var7.append(var2);
      var7.append(",");
      var7.append(var4);
      var7.append("?q=");
      var7.append(var2);
      var7.append(",");
      var7.append(var4);
      var7.append("(");
      var7.append(var6);
      var7.append(")");
      Intent var8 = new Intent("android.intent.action.VIEW", Uri.parse(var7.toString()));
      var8.setPackage("com.google.android.apps.maps");
      if (var8.resolveActivity(var1.getPackageManager()) != null) {
         var1.startActivity(var8);
         return true;
      } else {
         return false;
      }
   }

   public static class GetNemaLocation {
      Context context;
      LocationListener locationListener;
      LocationManager locationManager;
      OnNmeaMessageListener nmeaListener;

      public GetNemaLocation(Context var1) {
         this.context = var1;
      }

      public void onLocationReceived(double var1, double var3) {
      }

      public void start() {
         this.locationManager = (LocationManager)this.context.getSystemService("location");
         this.locationListener = new LocationListener() {
            public void onLocationChanged(Location var1) {
               var1.getLatitude();
               var1.getLongitude();
            }

            public void onProviderDisabled(String var1) {
            }

            public void onProviderEnabled(String var1) {
            }

            public void onStatusChanged(String var1, int var2, Bundle var3) {
            }
         };
         this.nmeaListener = new OnNmeaMessageListener() {
            public void onNmeaMessage(String var1, long var2) {
               Log.d("GPS-NMEA", var1);
               String[] var10 = var1.split(",");
               if (var10[0].equalsIgnoreCase("$GPGGA")) {
                  double var4 = Double.parseDouble(var10[2].substring(0, 1)) + Double.parseDouble(var10[2].substring(2)) / 60.0D;
                  double var6 = var4;
                  if ("S".equalsIgnoreCase(var10[3])) {
                     var6 = var4;
                     if (var4 > 0.0D) {
                        var6 = -var4;
                     }
                  }

                  double var8 = Double.parseDouble(var10[4].substring(0, 2)) + Double.parseDouble(var10[4].substring(3)) / 60.0D;
                  var4 = var8;
                  if ("W".equalsIgnoreCase(var10[5])) {
                     var4 = var8;
                     if (var8 > 0.0D) {
                        var4 = -var8;
                     }
                  }

                  GetNemaLocation.this.onLocationReceived(var6, var4);
               }

            }
         };
         this.locationManager.requestLocationUpdates("gps", 2000L, 0.0F, this.locationListener);
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

      public GoogleLocationService(Context var1) {
         this.context = var1;
         GoogleApiClient.ConnectionCallbacks var2 = new GoogleApiClient.ConnectionCallbacks() {
            public void onConnected(Bundle var1) {
               GoogleLocationService.this.OnGoogleLocationServiceConnected(var1);
            }

            public void onConnectionSuspended(int var1) {
               GoogleLocationService.this.OnGoogleLocationServiceSuspended(var1);
            }
         };
         GoogleApiClient.OnConnectionFailedListener var3 = new GoogleApiClient.OnConnectionFailedListener() {
            public void onConnectionFailed(ConnectionResult var1) {
               GoogleLocationService.this.OnGoogleLocationServiceFailed(var1);
            }
         };
         this.googleApiClient = (new GoogleApiClient.Builder(var1)).addConnectionCallbacks(var2).addOnConnectionFailedListener(var3).addApi(LocationServices.API).build();
      }

      public void OnGoogleLocationServiceConnected(Bundle var1) {
      }

      public void OnGoogleLocationServiceFailed(ConnectionResult var1) {
      }

      public void OnGoogleLocationServiceSuspended(int var1) {
      }

      public void OnLocationChanged(Location var1) {
      }

      public void connect() {
         GoogleApiClient var1 = this.googleApiClient;
         if (var1 != null) {
            var1.connect();
         }

      }

      public void disconnect() {
         GoogleApiClient var1 = this.googleApiClient;
         if (var1 != null) {
            var1.disconnect();
         }

      }

      public Location getLastLocation() {
         return LocationServices.FusedLocationApi.getLastLocation(this.googleApiClient);
      }

      public void setPriority(int var1) {
         this.priority = var1;
      }

      public void startLocationUpdating(int var1, int var2) {
         LocationRequest var3 = new LocationRequest();
         var3.setInterval((long)var1);
         var3.setFastestInterval((long)var2);
         var3.setPriority(this.priority);
         this.locationListener = new com.google.android.gms.location.LocationListener() {
            public void onLocationChanged(Location var1) {
               GoogleLocationService.this.OnLocationChanged(var1);
            }
         };
         LocationServices.FusedLocationApi.requestLocationUpdates(this.googleApiClient, var3, this.locationListener);
      }

      public void stop() {
         this.stopLocationUpdating();
         this.disconnect();
      }

      public void stopLocationUpdating() {
         if (this.googleApiClient != null && this.locationListener != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, this.locationListener);
         }

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
      float minDistance = 0.0F;
      long minTime = 0L;
      int provider_type;

      public LocationService(Context var1, boolean var2) {
         this.context = var1;
         this.isOnce = var2;
         this.mLocationManager = (LocationManager)var1.getSystemService("location");
      }

      public LocationService(Context var1, boolean var2, long var3, float var5) {
         this.minTime = var3;
         this.minDistance = var5;
         this.context = var1;
         this.isOnce = var2;
         this.mLocationManager = (LocationManager)var1.getSystemService("location");
      }

      private LocationListener listerner_init(final int var1) {
         return new LocationListener() {
            public void onLocationChanged(Location var1x) {
               if (LocationService.this.isOnce) {
                  LocationService.this.mLocationManager.removeUpdates(this);
               }

               LocationService.this.OnLocationRefreshed(var1x, var1);
            }

            public void onProviderDisabled(String var1x) {
            }

            public void onProviderEnabled(String var1x) {
            }

            public void onStatusChanged(String var1x, int var2, Bundle var3) {
            }
         };
      }

      public void OnLocationRefreshed(Location var1, int var2) {
      }

      public void start(int var1) {
         this.provider_type = var1;
         this.listener = this.listerner_init(var1);
         Criteria var2 = new Criteria();
         String var3 = "network";
         String var4;
         if (var1 != 1) {
            if (var1 != 2) {
               var4 = var3;
               switch(var1) {
               case 11:
                  break;
               case 12:
                  var4 = "gps";
                  break;
               case 13:
                  var4 = "passive";
                  break;
               default:
                  var4 = var3;
               }
            } else {
               var2.setAccuracy(1);
               var2.setSpeedRequired(true);
               var2.setAltitudeRequired(false);
               var2.setBearingRequired(false);
               var2.setCostAllowed(false);
               var4 = this.mLocationManager.getBestProvider(var2, true);
            }
         } else {
            var2.setAccuracy(2);
            var4 = this.mLocationManager.getBestProvider(var2, true);
         }

         this.mLocationManager.requestLocationUpdates(var4, this.minTime, this.minDistance, this.listener);
      }

      public void stop() {
         LocationListener var1 = this.listenerCoarse;
         if (var1 != null) {
            this.mLocationManager.removeUpdates(var1);
         }

         var1 = this.listenerFine;
         if (var1 != null) {
            this.mLocationManager.removeUpdates(var1);
         }

         var1 = this.listenerGps;
         if (var1 != null) {
            this.mLocationManager.removeUpdates(var1);
         }

         var1 = this.listenerNetwork;
         if (var1 != null) {
            this.mLocationManager.removeUpdates(var1);
         }

         var1 = this.listenerPassive;
         if (var1 != null) {
            this.mLocationManager.removeUpdates(var1);
         }

      }
   }

   public static class MockLocation {
      Context context;
      LocationManager locationManager;
      String locationProvider;

      public MockLocation(Context var1) {
         this.context = var1;
      }

      public void refresh(Location var1) {
         Location var2 = new Location(this.locationProvider);
         var2.setLatitude(var1.getLatitude());
         var2.setLongitude(var1.getLongitude());
         var2.setAltitude(var1.getAltitude());
         var2.setTime(System.currentTimeMillis());
         int var3 = MapOp.setMockLocationSettings(this.context);
         this.locationManager.setTestProviderLocation(this.locationProvider, var2);
         MapOp.restoreMockLocationSettings(this.context, var3);
      }

      public void start() {
         this.locationProvider = "gps";
         LocationManager var1 = (LocationManager)this.context.getSystemService("location");
         this.locationManager = var1;
         var1.addTestProvider(this.locationProvider, false, false, false, false, true, true, true, 0, 5);
         this.locationManager.setTestProviderEnabled(this.locationProvider, true);
      }

      public void stop() {
         this.locationManager.setTestProviderEnabled(this.locationProvider, false);
      }
   }

   public static enum TRAVEL_MODE {
      BICYCLE,
      DRIVE,
      TWO_WHEELER,
      WALK;

      static {
         MapOp.TRAVEL_MODE var0 = new MapOp.TRAVEL_MODE("WALK", 3);
         WALK = var0;
      }
   }
}
