package androidx.transition;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import java.util.ArrayList;

class GhostViewHolder extends FrameLayout {
   private boolean mAttached;
   private ViewGroup mParent;

   GhostViewHolder(ViewGroup var1) {
      super(var1.getContext());
      this.setClipChildren(false);
      this.mParent = var1;
      var1.setTag(R.id.ghost_view_holder, this);
      ViewGroupUtils.getOverlay(this.mParent).add(this);
      this.mAttached = true;
   }

   static GhostViewHolder getHolder(ViewGroup var0) {
      return (GhostViewHolder)var0.getTag(R.id.ghost_view_holder);
   }

   private int getInsertIndex(ArrayList<View> var1) {
      ArrayList var2 = new ArrayList();
      int var3 = this.getChildCount() - 1;

      int var4;
      for(var4 = 0; var4 <= var3; var2.clear()) {
         int var5 = (var4 + var3) / 2;
         getParents(((GhostViewPort)this.getChildAt(var5)).mView, var2);
         if (isOnTop(var1, var2)) {
            var4 = var5 + 1;
         } else {
            var3 = var5 - 1;
         }
      }

      return var4;
   }

   private static void getParents(View var0, ArrayList<View> var1) {
      ViewParent var2 = var0.getParent();
      if (var2 instanceof ViewGroup) {
         getParents((View)var2, var1);
      }

      var1.add(var0);
   }

   private static boolean isOnTop(View var0, View var1) {
      ViewGroup var2 = (ViewGroup)var0.getParent();
      int var3 = var2.getChildCount();
      int var4 = VERSION.SDK_INT;
      boolean var5 = false;
      boolean var6 = false;
      if (var4 >= 21 && var0.getZ() != var1.getZ()) {
         if (var0.getZ() > var1.getZ()) {
            var6 = true;
         }

         return var6;
      } else {
         var4 = 0;

         while(true) {
            if (var4 < var3) {
               View var7 = var2.getChildAt(ViewGroupUtils.getChildDrawingOrder(var2, var4));
               if (var7 == var0) {
                  var6 = var5;
                  break;
               }

               if (var7 != var1) {
                  ++var4;
                  continue;
               }
            }

            var6 = true;
            break;
         }

         return var6;
      }
   }

   private static boolean isOnTop(ArrayList<View> var0, ArrayList<View> var1) {
      boolean var2 = var0.isEmpty();
      boolean var3 = true;
      boolean var4 = var3;
      if (!var2) {
         var4 = var3;
         if (!var1.isEmpty()) {
            if (var0.get(0) != var1.get(0)) {
               var4 = var3;
            } else {
               int var5 = Math.min(var0.size(), var1.size());

               for(int var6 = 1; var6 < var5; ++var6) {
                  View var7 = (View)var0.get(var6);
                  View var8 = (View)var1.get(var6);
                  if (var7 != var8) {
                     return isOnTop(var7, var8);
                  }
               }

               if (var1.size() == var5) {
                  var4 = var3;
               } else {
                  var4 = false;
               }
            }
         }
      }

      return var4;
   }

   void addGhostView(GhostViewPort var1) {
      ArrayList var2 = new ArrayList();
      getParents(var1.mView, var2);
      int var3 = this.getInsertIndex(var2);
      if (var3 >= 0 && var3 < this.getChildCount()) {
         this.addView(var1, var3);
      } else {
         this.addView(var1);
      }

   }

   public void onViewAdded(View var1) {
      if (this.mAttached) {
         super.onViewAdded(var1);
      } else {
         throw new IllegalStateException("This GhostViewHolder is detached!");
      }
   }

   public void onViewRemoved(View var1) {
      super.onViewRemoved(var1);
      if (this.getChildCount() == 1 && this.getChildAt(0) == var1 || this.getChildCount() == 0) {
         this.mParent.setTag(R.id.ghost_view_holder, (Object)null);
         ViewGroupUtils.getOverlay(this.mParent).remove(this);
         this.mAttached = false;
      }

   }

   void popToOverlayTop() {
      if (this.mAttached) {
         ViewGroupUtils.getOverlay(this.mParent).remove(this);
         ViewGroupUtils.getOverlay(this.mParent).add(this);
      } else {
         throw new IllegalStateException("This GhostViewHolder is detached!");
      }
   }
}
