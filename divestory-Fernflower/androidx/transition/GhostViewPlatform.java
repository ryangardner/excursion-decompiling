package androidx.transition;

import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class GhostViewPlatform implements GhostView {
   private static final String TAG = "GhostViewApi21";
   private static Method sAddGhostMethod;
   private static boolean sAddGhostMethodFetched;
   private static Class<?> sGhostViewClass;
   private static boolean sGhostViewClassFetched;
   private static Method sRemoveGhostMethod;
   private static boolean sRemoveGhostMethodFetched;
   private final View mGhostView;

   private GhostViewPlatform(View var1) {
      this.mGhostView = var1;
   }

   static GhostView addGhost(View var0, ViewGroup var1, Matrix var2) {
      fetchAddGhostMethod();
      if (sAddGhostMethod != null) {
         try {
            GhostViewPlatform var5 = new GhostViewPlatform((View)sAddGhostMethod.invoke((Object)null, var0, var1, var2));
            return var5;
         } catch (IllegalAccessException var3) {
         } catch (InvocationTargetException var4) {
            throw new RuntimeException(var4.getCause());
         }
      }

      return null;
   }

   private static void fetchAddGhostMethod() {
      if (!sAddGhostMethodFetched) {
         try {
            fetchGhostViewClass();
            Method var0 = sGhostViewClass.getDeclaredMethod("addGhost", View.class, ViewGroup.class, Matrix.class);
            sAddGhostMethod = var0;
            var0.setAccessible(true);
         } catch (NoSuchMethodException var1) {
            Log.i("GhostViewApi21", "Failed to retrieve addGhost method", var1);
         }

         sAddGhostMethodFetched = true;
      }

   }

   private static void fetchGhostViewClass() {
      if (!sGhostViewClassFetched) {
         try {
            sGhostViewClass = Class.forName("android.view.GhostView");
         } catch (ClassNotFoundException var1) {
            Log.i("GhostViewApi21", "Failed to retrieve GhostView class", var1);
         }

         sGhostViewClassFetched = true;
      }

   }

   private static void fetchRemoveGhostMethod() {
      if (!sRemoveGhostMethodFetched) {
         try {
            fetchGhostViewClass();
            Method var0 = sGhostViewClass.getDeclaredMethod("removeGhost", View.class);
            sRemoveGhostMethod = var0;
            var0.setAccessible(true);
         } catch (NoSuchMethodException var1) {
            Log.i("GhostViewApi21", "Failed to retrieve removeGhost method", var1);
         }

         sRemoveGhostMethodFetched = true;
      }

   }

   static void removeGhost(View var0) {
      fetchRemoveGhostMethod();
      Method var1 = sRemoveGhostMethod;
      if (var1 != null) {
         try {
            var1.invoke((Object)null, var0);
         } catch (IllegalAccessException var2) {
         } catch (InvocationTargetException var3) {
            throw new RuntimeException(var3.getCause());
         }
      }

   }

   public void reserveEndViewTransition(ViewGroup var1, View var2) {
   }

   public void setVisibility(int var1) {
      this.mGhostView.setVisibility(var1);
   }
}
