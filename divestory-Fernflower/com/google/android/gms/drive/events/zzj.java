package com.google.android.gms.drive.events;

import com.google.android.gms.drive.DriveId;

public final class zzj {
   public static boolean zza(int var0, DriveId var1) {
      if (var0 != 1) {
         if (var0 == 4 || var0 == 7) {
            return var1 == null;
         }

         if (var0 != 8) {
            return false;
         }
      }

      if (var1 != null) {
         return true;
      } else {
         return false;
      }
   }
}
