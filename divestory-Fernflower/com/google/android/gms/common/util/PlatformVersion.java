package com.google.android.gms.common.util;

import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;

public final class PlatformVersion {
   private static Boolean zza;

   private PlatformVersion() {
   }

   public static boolean isAtLeastHoneycomb() {
      return true;
   }

   public static boolean isAtLeastHoneycombMR1() {
      return true;
   }

   public static boolean isAtLeastIceCreamSandwich() {
      return true;
   }

   public static boolean isAtLeastIceCreamSandwichMR1() {
      return VERSION.SDK_INT >= 15;
   }

   public static boolean isAtLeastJellyBean() {
      return VERSION.SDK_INT >= 16;
   }

   public static boolean isAtLeastJellyBeanMR1() {
      return VERSION.SDK_INT >= 17;
   }

   public static boolean isAtLeastJellyBeanMR2() {
      return VERSION.SDK_INT >= 18;
   }

   public static boolean isAtLeastKitKat() {
      return VERSION.SDK_INT >= 19;
   }

   public static boolean isAtLeastKitKatWatch() {
      return VERSION.SDK_INT >= 20;
   }

   public static boolean isAtLeastLollipop() {
      return VERSION.SDK_INT >= 21;
   }

   public static boolean isAtLeastLollipopMR1() {
      return VERSION.SDK_INT >= 22;
   }

   public static boolean isAtLeastM() {
      return VERSION.SDK_INT >= 23;
   }

   public static boolean isAtLeastN() {
      return VERSION.SDK_INT >= 24;
   }

   public static boolean isAtLeastO() {
      return VERSION.SDK_INT >= 26;
   }

   public static boolean isAtLeastP() {
      return VERSION.SDK_INT >= 28;
   }

   public static boolean isAtLeastQ() {
      return VERSION.SDK_INT >= 29;
   }

   public static boolean isAtLeastR() {
      boolean var0 = isAtLeastQ();
      boolean var1 = false;
      if (!var0) {
         return false;
      } else if (VERSION.SDK_INT >= 30 && VERSION.CODENAME.equals("REL")) {
         return true;
      } else {
         boolean var2;
         if (VERSION.CODENAME.length() == 1 && VERSION.CODENAME.charAt(0) >= 'R' && VERSION.CODENAME.charAt(0) <= 'Z') {
            var2 = true;
         } else {
            var2 = false;
         }

         if (!var2) {
            return false;
         } else {
            Boolean var3 = zza;
            if (var3 != null) {
               return var3;
            } else {
               var0 = var1;

               label70: {
                  label69: {
                     boolean var10001;
                     label86: {
                        try {
                           if (!"google".equals(Build.BRAND)) {
                              break label86;
                           }
                        } catch (NumberFormatException var8) {
                           var10001 = false;
                           break label69;
                        }

                        var0 = var1;

                        try {
                           if (Build.ID.startsWith("RPP1")) {
                              break label86;
                           }
                        } catch (NumberFormatException var7) {
                           var10001 = false;
                           break label69;
                        }

                        var0 = var1;

                        try {
                           if (Build.ID.startsWith("RPP2")) {
                              break label86;
                           }
                        } catch (NumberFormatException var6) {
                           var10001 = false;
                           break label69;
                        }

                        var0 = var1;

                        try {
                           if (Integer.parseInt(VERSION.INCREMENTAL) < 6301457) {
                              break label86;
                           }
                        } catch (NumberFormatException var5) {
                           var10001 = false;
                           break label69;
                        }

                        var0 = true;
                     }

                     try {
                        zza = var0;
                        break label70;
                     } catch (NumberFormatException var4) {
                        var10001 = false;
                     }
                  }

                  zza = true;
               }

               if (!zza) {
                  Log.w("PlatformVersion", "Build version must be at least 6301457 to support R in gmscore");
               }

               return zza;
            }
         }
      }
   }
}
