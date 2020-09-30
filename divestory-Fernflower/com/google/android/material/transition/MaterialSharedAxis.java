package com.google.android.material.transition;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class MaterialSharedAxis extends MaterialVisibility<VisibilityAnimatorProvider> {
   public static final int X = 0;
   public static final int Y = 1;
   public static final int Z = 2;
   private final int axis;
   private final boolean forward;

   public MaterialSharedAxis(int var1, boolean var2) {
      super(createPrimaryAnimatorProvider(var1, var2), createSecondaryAnimatorProvider());
      this.axis = var1;
      this.forward = var2;
   }

   private static VisibilityAnimatorProvider createPrimaryAnimatorProvider(int var0, boolean var1) {
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 == 2) {
               return new ScaleProvider(var1);
            } else {
               StringBuilder var2 = new StringBuilder();
               var2.append("Invalid axis: ");
               var2.append(var0);
               throw new IllegalArgumentException(var2.toString());
            }
         } else {
            byte var3;
            if (var1) {
               var3 = 80;
            } else {
               var3 = 48;
            }

            return new SlideDistanceProvider(var3);
         }
      } else {
         if (var1) {
            var0 = 8388613;
         } else {
            var0 = 8388611;
         }

         return new SlideDistanceProvider(var0);
      }
   }

   private static VisibilityAnimatorProvider createSecondaryAnimatorProvider() {
      return new FadeThroughProvider();
   }

   public int getAxis() {
      return this.axis;
   }

   public boolean isForward() {
      return this.forward;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface Axis {
   }
}
