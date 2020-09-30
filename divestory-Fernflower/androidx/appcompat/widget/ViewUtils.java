package androidx.appcompat.widget;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import androidx.core.view.ViewCompat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {
   private static final String TAG = "ViewUtils";
   private static Method sComputeFitSystemWindowsMethod;

   static {
      if (VERSION.SDK_INT >= 18) {
         try {
            Method var0 = View.class.getDeclaredMethod("computeFitSystemWindows", Rect.class, Rect.class);
            sComputeFitSystemWindowsMethod = var0;
            if (!var0.isAccessible()) {
               sComputeFitSystemWindowsMethod.setAccessible(true);
            }
         } catch (NoSuchMethodException var1) {
            Log.d("ViewUtils", "Could not find method computeFitSystemWindows. Oh well.");
         }
      }

   }

   private ViewUtils() {
   }

   public static void computeFitSystemWindows(View var0, Rect var1, Rect var2) {
      Method var3 = sComputeFitSystemWindowsMethod;
      if (var3 != null) {
         try {
            var3.invoke(var0, var1, var2);
         } catch (Exception var4) {
            Log.d("ViewUtils", "Could not invoke computeFitSystemWindows", var4);
         }
      }

   }

   public static boolean isLayoutRtl(View var0) {
      int var1 = ViewCompat.getLayoutDirection(var0);
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   public static void makeOptionalFitsSystemWindows(View var0) {
      if (VERSION.SDK_INT >= 16) {
         try {
            Method var1 = var0.getClass().getMethod("makeOptionalFitsSystemWindows");
            if (!var1.isAccessible()) {
               var1.setAccessible(true);
            }

            var1.invoke(var0);
         } catch (NoSuchMethodException var2) {
            Log.d("ViewUtils", "Could not find method makeOptionalFitsSystemWindows. Oh well...");
         } catch (InvocationTargetException var3) {
            Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", var3);
         } catch (IllegalAccessException var4) {
            Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", var4);
         }
      }

   }
}
