package androidx.core.view;

import android.app.UiModeManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.Display;
import android.view.Display.Mode;
import androidx.core.util.Preconditions;
import java.util.ArrayList;

public final class DisplayCompat {
   private static final int DISPLAY_SIZE_4K_HEIGHT = 2160;
   private static final int DISPLAY_SIZE_4K_WIDTH = 3840;

   private DisplayCompat() {
   }

   private static Point getPhysicalDisplaySize(Context var0, Display var1) {
      Point var2;
      if (VERSION.SDK_INT < 28) {
         var2 = parsePhysicalDisplaySizeFromSystemProperties("sys.display-size", var1);
      } else {
         var2 = parsePhysicalDisplaySizeFromSystemProperties("vendor.display-size", var1);
      }

      if (var2 != null) {
         return var2;
      } else if (isSonyBravia4kTv(var0)) {
         return new Point(3840, 2160);
      } else {
         Point var3 = new Point();
         if (VERSION.SDK_INT >= 23) {
            Mode var4 = var1.getMode();
            var3.x = var4.getPhysicalWidth();
            var3.y = var4.getPhysicalHeight();
         } else if (VERSION.SDK_INT >= 17) {
            var1.getRealSize(var3);
         } else {
            var1.getSize(var3);
         }

         return var3;
      }
   }

   public static DisplayCompat.ModeCompat[] getSupportedModes(Context var0, Display var1) {
      Point var5 = getPhysicalDisplaySize(var0, var1);
      if (VERSION.SDK_INT >= 23) {
         Mode[] var6 = var1.getSupportedModes();
         ArrayList var2 = new ArrayList(var6.length);
         int var3 = 0;

         boolean var4;
         for(var4 = false; var3 < var6.length; ++var3) {
            if (physicalSizeEquals(var6[var3], var5)) {
               var2.add(var3, new DisplayCompat.ModeCompat(var6[var3], true));
               var4 = true;
            } else {
               var2.add(var3, new DisplayCompat.ModeCompat(var6[var3], false));
            }
         }

         if (!var4) {
            var2.add(new DisplayCompat.ModeCompat(var5));
         }

         return (DisplayCompat.ModeCompat[])var2.toArray(new DisplayCompat.ModeCompat[0]);
      } else {
         return new DisplayCompat.ModeCompat[]{new DisplayCompat.ModeCompat(var5)};
      }
   }

   private static String getSystemProperty(String var0) {
      try {
         Class var1 = Class.forName("android.os.SystemProperties");
         var0 = (String)var1.getMethod("get", String.class).invoke(var1, var0);
         return var0;
      } catch (Exception var2) {
         return null;
      }
   }

   private static boolean isSonyBravia4kTv(Context var0) {
      boolean var1;
      if (isTv(var0) && "Sony".equals(Build.MANUFACTURER) && Build.MODEL.startsWith("BRAVIA") && var0.getPackageManager().hasSystemFeature("com.sony.dtv.hardware.panel.qfhd")) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static boolean isTv(Context var0) {
      UiModeManager var2 = (UiModeManager)var0.getSystemService("uimode");
      boolean var1;
      if (var2 != null && var2.getCurrentModeType() == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static Point parseDisplaySize(String var0) throws NumberFormatException {
      String[] var3 = var0.trim().split("x", -1);
      if (var3.length == 2) {
         int var1 = Integer.parseInt(var3[0]);
         int var2 = Integer.parseInt(var3[1]);
         if (var1 > 0 && var2 > 0) {
            return new Point(var1, var2);
         }
      }

      throw new NumberFormatException();
   }

   private static Point parsePhysicalDisplaySizeFromSystemProperties(String var0, Display var1) {
      if (var1.getDisplayId() == 0) {
         var0 = getSystemProperty(var0);
         if (!TextUtils.isEmpty(var0)) {
            try {
               Point var3 = parseDisplaySize(var0);
               return var3;
            } catch (NumberFormatException var2) {
            }
         }
      }

      return null;
   }

   private static boolean physicalSizeEquals(Mode var0, Point var1) {
      boolean var2;
      if ((var0.getPhysicalWidth() != var1.x || var0.getPhysicalHeight() != var1.y) && (var0.getPhysicalWidth() != var1.y || var0.getPhysicalHeight() != var1.x)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public static final class ModeCompat {
      private final boolean mIsNative;
      private final Mode mMode;
      private final Point mPhysicalDisplaySize;

      ModeCompat(Point var1) {
         Preconditions.checkNotNull(var1, "physicalDisplaySize == null");
         this.mIsNative = true;
         this.mPhysicalDisplaySize = var1;
         this.mMode = null;
      }

      ModeCompat(Mode var1, boolean var2) {
         Preconditions.checkNotNull(var1, "Display.Mode == null, can't wrap a null reference");
         this.mIsNative = var2;
         this.mPhysicalDisplaySize = new Point(var1.getPhysicalWidth(), var1.getPhysicalHeight());
         this.mMode = var1;
      }

      public int getPhysicalHeight() {
         return this.mPhysicalDisplaySize.y;
      }

      public int getPhysicalWidth() {
         return this.mPhysicalDisplaySize.x;
      }

      public boolean isNative() {
         return this.mIsNative;
      }

      public Mode toMode() {
         return this.mMode;
      }
   }
}
