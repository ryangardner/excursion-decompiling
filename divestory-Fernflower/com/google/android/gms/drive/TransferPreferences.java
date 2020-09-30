package com.google.android.gms.drive;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface TransferPreferences {
   int BATTERY_USAGE_CHARGING_ONLY = 257;
   int BATTERY_USAGE_UNKNOWN = 0;
   int BATTERY_USAGE_UNRESTRICTED = 256;
   int NETWORK_TYPE_ANY = 1;
   int NETWORK_TYPE_UNKNOWN = 0;
   int NETWORK_TYPE_WIFI_ONLY = 2;

   int getBatteryUsagePreference();

   int getNetworkPreference();

   boolean isRoamingAllowed();

   @Retention(RetentionPolicy.SOURCE)
   public @interface BatteryUsage {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface NetworkType {
   }
}
