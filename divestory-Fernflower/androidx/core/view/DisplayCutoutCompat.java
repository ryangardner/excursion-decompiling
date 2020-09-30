package androidx.core.view;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.DisplayCutout;
import java.util.List;

public final class DisplayCutoutCompat {
   private final Object mDisplayCutout;

   public DisplayCutoutCompat(Rect var1, List<Rect> var2) {
      DisplayCutout var3;
      if (VERSION.SDK_INT >= 28) {
         var3 = new DisplayCutout(var1, var2);
      } else {
         var3 = null;
      }

      this(var3);
   }

   private DisplayCutoutCompat(Object var1) {
      this.mDisplayCutout = var1;
   }

   static DisplayCutoutCompat wrap(Object var0) {
      DisplayCutoutCompat var1;
      if (var0 == null) {
         var1 = null;
      } else {
         var1 = new DisplayCutoutCompat(var0);
      }

      return var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         DisplayCutoutCompat var3 = (DisplayCutoutCompat)var1;
         var1 = this.mDisplayCutout;
         if (var1 == null) {
            if (var3.mDisplayCutout != null) {
               var2 = false;
            }
         } else {
            var2 = var1.equals(var3.mDisplayCutout);
         }

         return var2;
      } else {
         return false;
      }
   }

   public List<Rect> getBoundingRects() {
      return VERSION.SDK_INT >= 28 ? ((DisplayCutout)this.mDisplayCutout).getBoundingRects() : null;
   }

   public int getSafeInsetBottom() {
      return VERSION.SDK_INT >= 28 ? ((DisplayCutout)this.mDisplayCutout).getSafeInsetBottom() : 0;
   }

   public int getSafeInsetLeft() {
      return VERSION.SDK_INT >= 28 ? ((DisplayCutout)this.mDisplayCutout).getSafeInsetLeft() : 0;
   }

   public int getSafeInsetRight() {
      return VERSION.SDK_INT >= 28 ? ((DisplayCutout)this.mDisplayCutout).getSafeInsetRight() : 0;
   }

   public int getSafeInsetTop() {
      return VERSION.SDK_INT >= 28 ? ((DisplayCutout)this.mDisplayCutout).getSafeInsetTop() : 0;
   }

   public int hashCode() {
      Object var1 = this.mDisplayCutout;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.hashCode();
      }

      return var2;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("DisplayCutoutCompat{");
      var1.append(this.mDisplayCutout);
      var1.append("}");
      return var1.toString();
   }

   DisplayCutout unwrap() {
      return (DisplayCutout)this.mDisplayCutout;
   }
}
