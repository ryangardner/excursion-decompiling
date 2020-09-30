package com.google.android.material.badge;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.internal.ParcelableSparseArray;

public class BadgeUtils {
   public static final boolean USE_COMPAT_PARENT;

   static {
      boolean var0;
      if (VERSION.SDK_INT < 18) {
         var0 = true;
      } else {
         var0 = false;
      }

      USE_COMPAT_PARENT = var0;
   }

   private BadgeUtils() {
   }

   public static void attachBadgeDrawable(BadgeDrawable var0, View var1, FrameLayout var2) {
      setBadgeDrawableBounds(var0, var1, var2);
      if (USE_COMPAT_PARENT) {
         var2.setForeground(var0);
      } else {
         var1.getOverlay().add(var0);
      }

   }

   public static SparseArray<BadgeDrawable> createBadgeDrawablesFromSavedStates(Context var0, ParcelableSparseArray var1) {
      SparseArray var2 = new SparseArray(var1.size());

      for(int var3 = 0; var3 < var1.size(); ++var3) {
         int var4 = var1.keyAt(var3);
         BadgeDrawable.SavedState var5 = (BadgeDrawable.SavedState)var1.valueAt(var3);
         if (var5 == null) {
            throw new IllegalArgumentException("BadgeDrawable's savedState cannot be null");
         }

         var2.put(var4, BadgeDrawable.createFromSavedState(var0, var5));
      }

      return var2;
   }

   public static ParcelableSparseArray createParcelableBadgeStates(SparseArray<BadgeDrawable> var0) {
      ParcelableSparseArray var1 = new ParcelableSparseArray();

      for(int var2 = 0; var2 < var0.size(); ++var2) {
         int var3 = var0.keyAt(var2);
         BadgeDrawable var4 = (BadgeDrawable)var0.valueAt(var2);
         if (var4 == null) {
            throw new IllegalArgumentException("badgeDrawable cannot be null");
         }

         var1.put(var3, var4.getSavedState());
      }

      return var1;
   }

   public static void detachBadgeDrawable(BadgeDrawable var0, View var1, FrameLayout var2) {
      if (var0 != null) {
         if (USE_COMPAT_PARENT) {
            var2.setForeground((Drawable)null);
         } else {
            var1.getOverlay().remove(var0);
         }

      }
   }

   public static void setBadgeDrawableBounds(BadgeDrawable var0, View var1, FrameLayout var2) {
      Rect var3 = new Rect();
      Object var4;
      if (USE_COMPAT_PARENT) {
         var4 = var2;
      } else {
         var4 = var1;
      }

      ((View)var4).getDrawingRect(var3);
      var0.setBounds(var3);
      var0.updateBadgeCoordinates(var1, var2);
   }

   public static void updateBadgeBounds(Rect var0, float var1, float var2, float var3, float var4) {
      var0.set((int)(var1 - var3), (int)(var2 - var4), (int)(var1 + var3), (int)(var2 + var4));
   }
}
