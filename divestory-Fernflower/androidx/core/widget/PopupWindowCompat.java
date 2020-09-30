package androidx.core.widget;

import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class PopupWindowCompat {
   private static final String TAG = "PopupWindowCompatApi21";
   private static Method sGetWindowLayoutTypeMethod;
   private static boolean sGetWindowLayoutTypeMethodAttempted;
   private static Field sOverlapAnchorField;
   private static boolean sOverlapAnchorFieldAttempted;
   private static Method sSetWindowLayoutTypeMethod;
   private static boolean sSetWindowLayoutTypeMethodAttempted;

   private PopupWindowCompat() {
   }

   public static boolean getOverlapAnchor(PopupWindow var0) {
      if (VERSION.SDK_INT >= 23) {
         return var0.getOverlapAnchor();
      } else {
         if (VERSION.SDK_INT >= 21) {
            Field var1;
            if (!sOverlapAnchorFieldAttempted) {
               try {
                  var1 = PopupWindow.class.getDeclaredField("mOverlapAnchor");
                  sOverlapAnchorField = var1;
                  var1.setAccessible(true);
               } catch (NoSuchFieldException var3) {
                  Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", var3);
               }

               sOverlapAnchorFieldAttempted = true;
            }

            var1 = sOverlapAnchorField;
            if (var1 != null) {
               try {
                  boolean var2 = (Boolean)var1.get(var0);
                  return var2;
               } catch (IllegalAccessException var4) {
                  Log.i("PopupWindowCompatApi21", "Could not get overlap anchor field in PopupWindow", var4);
               }
            }
         }

         return false;
      }
   }

   public static int getWindowLayoutType(PopupWindow var0) {
      if (VERSION.SDK_INT >= 23) {
         return var0.getWindowLayoutType();
      } else {
         Method var1;
         if (!sGetWindowLayoutTypeMethodAttempted) {
            try {
               var1 = PopupWindow.class.getDeclaredMethod("getWindowLayoutType");
               sGetWindowLayoutTypeMethod = var1;
               var1.setAccessible(true);
            } catch (Exception var3) {
            }

            sGetWindowLayoutTypeMethodAttempted = true;
         }

         var1 = sGetWindowLayoutTypeMethod;
         if (var1 != null) {
            try {
               int var2 = (Integer)var1.invoke(var0);
               return var2;
            } catch (Exception var4) {
            }
         }

         return 0;
      }
   }

   public static void setOverlapAnchor(PopupWindow var0, boolean var1) {
      if (VERSION.SDK_INT >= 23) {
         var0.setOverlapAnchor(var1);
      } else if (VERSION.SDK_INT >= 21) {
         Field var2;
         if (!sOverlapAnchorFieldAttempted) {
            try {
               var2 = PopupWindow.class.getDeclaredField("mOverlapAnchor");
               sOverlapAnchorField = var2;
               var2.setAccessible(true);
            } catch (NoSuchFieldException var4) {
               Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", var4);
            }

            sOverlapAnchorFieldAttempted = true;
         }

         var2 = sOverlapAnchorField;
         if (var2 != null) {
            try {
               var2.set(var0, var1);
            } catch (IllegalAccessException var3) {
               Log.i("PopupWindowCompatApi21", "Could not set overlap anchor field in PopupWindow", var3);
            }
         }
      }

   }

   public static void setWindowLayoutType(PopupWindow var0, int var1) {
      if (VERSION.SDK_INT >= 23) {
         var0.setWindowLayoutType(var1);
      } else {
         Method var2;
         if (!sSetWindowLayoutTypeMethodAttempted) {
            try {
               var2 = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", Integer.TYPE);
               sSetWindowLayoutTypeMethod = var2;
               var2.setAccessible(true);
            } catch (Exception var4) {
            }

            sSetWindowLayoutTypeMethodAttempted = true;
         }

         var2 = sSetWindowLayoutTypeMethod;
         if (var2 != null) {
            try {
               var2.invoke(var0, var1);
            } catch (Exception var3) {
            }
         }

      }
   }

   public static void showAsDropDown(PopupWindow var0, View var1, int var2, int var3, int var4) {
      if (VERSION.SDK_INT >= 19) {
         var0.showAsDropDown(var1, var2, var3, var4);
      } else {
         int var5 = var2;
         if ((GravityCompat.getAbsoluteGravity(var4, ViewCompat.getLayoutDirection(var1)) & 7) == 5) {
            var5 = var2 - (var0.getWidth() - var1.getWidth());
         }

         var0.showAsDropDown(var1, var5, var3);
      }

   }
}
