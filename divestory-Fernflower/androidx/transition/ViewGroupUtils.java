package androidx.transition;

import android.os.Build.VERSION;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewGroupUtils {
   private static Method sGetChildDrawingOrderMethod;
   private static boolean sGetChildDrawingOrderMethodFetched;
   private static boolean sTryHiddenSuppressLayout;

   private ViewGroupUtils() {
   }

   static int getChildDrawingOrder(ViewGroup var0, int var1) {
      if (VERSION.SDK_INT >= 29) {
         return var0.getChildDrawingOrder(var1);
      } else {
         Method var2;
         if (!sGetChildDrawingOrderMethodFetched) {
            try {
               var2 = ViewGroup.class.getDeclaredMethod("getChildDrawingOrder", Integer.TYPE, Integer.TYPE);
               sGetChildDrawingOrderMethod = var2;
               var2.setAccessible(true);
            } catch (NoSuchMethodException var4) {
            }

            sGetChildDrawingOrderMethodFetched = true;
         }

         var2 = sGetChildDrawingOrderMethod;
         if (var2 != null) {
            try {
               int var3 = (Integer)var2.invoke(var0, var0.getChildCount(), var1);
               return var3;
            } catch (InvocationTargetException | IllegalAccessException var5) {
            }
         }

         return var1;
      }
   }

   static ViewGroupOverlayImpl getOverlay(ViewGroup var0) {
      return (ViewGroupOverlayImpl)(VERSION.SDK_INT >= 18 ? new ViewGroupOverlayApi18(var0) : ViewGroupOverlayApi14.createFrom(var0));
   }

   private static void hiddenSuppressLayout(ViewGroup var0, boolean var1) {
      if (sTryHiddenSuppressLayout) {
         try {
            var0.suppressLayout(var1);
         } catch (NoSuchMethodError var2) {
            sTryHiddenSuppressLayout = false;
         }
      }

   }

   static void suppressLayout(ViewGroup var0, boolean var1) {
      if (VERSION.SDK_INT >= 29) {
         var0.suppressLayout(var1);
      } else if (VERSION.SDK_INT >= 18) {
         hiddenSuppressLayout(var0, var1);
      } else {
         ViewGroupUtilsApi14.suppressLayout(var0, var1);
      }

   }
}
