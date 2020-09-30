package androidx.viewpager2.widget;

import android.animation.LayoutTransition;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Arrays;
import java.util.Comparator;

final class AnimateLayoutChangeDetector {
   private static final MarginLayoutParams ZERO_MARGIN_LAYOUT_PARAMS;
   private LinearLayoutManager mLayoutManager;

   static {
      MarginLayoutParams var0 = new MarginLayoutParams(-1, -1);
      ZERO_MARGIN_LAYOUT_PARAMS = var0;
      var0.setMargins(0, 0, 0, 0);
   }

   AnimateLayoutChangeDetector(LinearLayoutManager var1) {
      this.mLayoutManager = var1;
   }

   private boolean arePagesLaidOutContiguously() {
      int var1 = this.mLayoutManager.getChildCount();
      if (var1 == 0) {
         return true;
      } else {
         boolean var2;
         if (this.mLayoutManager.getOrientation() == 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         int[][] var3 = new int[var1][2];

         int var4;
         for(var4 = 0; var4 < var1; ++var4) {
            View var5 = this.mLayoutManager.getChildAt(var4);
            if (var5 == null) {
               throw new IllegalStateException("null view contained in the view hierarchy");
            }

            LayoutParams var6 = var5.getLayoutParams();
            MarginLayoutParams var11;
            if (var6 instanceof MarginLayoutParams) {
               var11 = (MarginLayoutParams)var6;
            } else {
               var11 = ZERO_MARGIN_LAYOUT_PARAMS;
            }

            int[] var7 = var3[var4];
            int var8;
            int var9;
            if (var2) {
               var8 = var5.getLeft();
               var9 = var11.leftMargin;
            } else {
               var8 = var5.getTop();
               var9 = var11.topMargin;
            }

            var7[0] = var8 - var9;
            var7 = var3[var4];
            if (var2) {
               var9 = var5.getRight();
               var8 = var11.rightMargin;
            } else {
               var9 = var5.getBottom();
               var8 = var11.bottomMargin;
            }

            var7[1] = var9 + var8;
         }

         Arrays.sort(var3, new Comparator<int[]>() {
            public int compare(int[] var1, int[] var2) {
               return var1[0] - var2[0];
            }
         });

         int var10;
         for(var10 = 1; var10 < var1; ++var10) {
            if (var3[var10 - 1][1] != var3[var10][0]) {
               return false;
            }
         }

         var4 = var3[0][1];
         var10 = var3[0][0];
         if (var3[0][0] <= 0 && var3[var1 - 1][1] >= var4 - var10) {
            return true;
         } else {
            return false;
         }
      }
   }

   private boolean hasRunningChangingLayoutTransition() {
      int var1 = this.mLayoutManager.getChildCount();

      for(int var2 = 0; var2 < var1; ++var2) {
         if (hasRunningChangingLayoutTransition(this.mLayoutManager.getChildAt(var2))) {
            return true;
         }
      }

      return false;
   }

   private static boolean hasRunningChangingLayoutTransition(View var0) {
      if (var0 instanceof ViewGroup) {
         ViewGroup var1 = (ViewGroup)var0;
         LayoutTransition var4 = var1.getLayoutTransition();
         if (var4 != null && var4.isChangingLayout()) {
            return true;
         }

         int var2 = var1.getChildCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            if (hasRunningChangingLayoutTransition(var1.getChildAt(var3))) {
               return true;
            }
         }
      }

      return false;
   }

   boolean mayHaveInterferingAnimations() {
      boolean var1 = this.arePagesLaidOutContiguously();
      boolean var2 = true;
      if (var1 && this.mLayoutManager.getChildCount() > 1 || !this.hasRunningChangingLayoutTransition()) {
         var2 = false;
      }

      return var2;
   }
}
