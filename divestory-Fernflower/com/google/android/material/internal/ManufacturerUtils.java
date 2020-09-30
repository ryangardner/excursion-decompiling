package com.google.android.material.internal;

import android.os.Build;
import java.util.Locale;

public class ManufacturerUtils {
   private static final String LGE = "lge";
   private static final String MEIZU = "meizu";
   private static final String SAMSUNG = "samsung";

   private ManufacturerUtils() {
   }

   public static boolean isDateInputKeyboardMissingSeparatorCharacters() {
      boolean var0;
      if (!isLGEDevice() && !isSamsungDevice()) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   public static boolean isLGEDevice() {
      return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).equals("lge");
   }

   public static boolean isMeizuDevice() {
      return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).equals("meizu");
   }

   public static boolean isSamsungDevice() {
      return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).equals("samsung");
   }
}
