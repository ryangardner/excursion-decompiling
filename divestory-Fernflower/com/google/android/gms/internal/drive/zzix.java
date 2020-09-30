package com.google.android.gms.internal.drive;

final class zzix {
   private static final Class<?> zzni = zzj("libcore.io.Memory");
   private static final boolean zznj;

   static {
      boolean var0;
      if (zzj("org.robolectric.Robolectric") != null) {
         var0 = true;
      } else {
         var0 = false;
      }

      zznj = var0;
   }

   static boolean zzbr() {
      return zzni != null && !zznj;
   }

   static Class<?> zzbs() {
      return zzni;
   }

   private static <T> Class<T> zzj(String var0) {
      try {
         Class var3 = Class.forName(var0);
         return var3;
      } finally {
         ;
      }
   }
}
