package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityWindowInfo;

public class AccessibilityWindowInfoCompat {
   public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
   public static final int TYPE_APPLICATION = 1;
   public static final int TYPE_INPUT_METHOD = 2;
   public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
   public static final int TYPE_SYSTEM = 3;
   private static final int UNDEFINED = -1;
   private Object mInfo;

   private AccessibilityWindowInfoCompat(Object var1) {
      this.mInfo = var1;
   }

   public static AccessibilityWindowInfoCompat obtain() {
      return VERSION.SDK_INT >= 21 ? wrapNonNullInstance(AccessibilityWindowInfo.obtain()) : null;
   }

   public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat var0) {
      int var1 = VERSION.SDK_INT;
      Object var2 = null;
      AccessibilityWindowInfoCompat var3 = (AccessibilityWindowInfoCompat)var2;
      if (var1 >= 21) {
         if (var0 == null) {
            var3 = (AccessibilityWindowInfoCompat)var2;
         } else {
            var3 = wrapNonNullInstance(AccessibilityWindowInfo.obtain((AccessibilityWindowInfo)var0.mInfo));
         }
      }

      return var3;
   }

   private static String typeToString(int var0) {
      if (var0 != 1) {
         if (var0 != 2) {
            if (var0 != 3) {
               return var0 != 4 ? "<UNKNOWN>" : "TYPE_ACCESSIBILITY_OVERLAY";
            } else {
               return "TYPE_SYSTEM";
            }
         } else {
            return "TYPE_INPUT_METHOD";
         }
      } else {
         return "TYPE_APPLICATION";
      }
   }

   static AccessibilityWindowInfoCompat wrapNonNullInstance(Object var0) {
      return var0 != null ? new AccessibilityWindowInfoCompat(var0) : null;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof AccessibilityWindowInfoCompat)) {
         return false;
      } else {
         AccessibilityWindowInfoCompat var2 = (AccessibilityWindowInfoCompat)var1;
         var1 = this.mInfo;
         if (var1 == null) {
            if (var2.mInfo != null) {
               return false;
            }
         } else if (!var1.equals(var2.mInfo)) {
            return false;
         }

         return true;
      }
   }

   public AccessibilityNodeInfoCompat getAnchor() {
      return VERSION.SDK_INT >= 24 ? AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getAnchor()) : null;
   }

   public void getBoundsInScreen(Rect var1) {
      if (VERSION.SDK_INT >= 21) {
         ((AccessibilityWindowInfo)this.mInfo).getBoundsInScreen(var1);
      }

   }

   public AccessibilityWindowInfoCompat getChild(int var1) {
      return VERSION.SDK_INT >= 21 ? wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getChild(var1)) : null;
   }

   public int getChildCount() {
      return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo)this.mInfo).getChildCount() : 0;
   }

   public int getId() {
      return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo)this.mInfo).getId() : -1;
   }

   public int getLayer() {
      return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo)this.mInfo).getLayer() : -1;
   }

   public AccessibilityWindowInfoCompat getParent() {
      return VERSION.SDK_INT >= 21 ? wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getParent()) : null;
   }

   public AccessibilityNodeInfoCompat getRoot() {
      return VERSION.SDK_INT >= 21 ? AccessibilityNodeInfoCompat.wrapNonNullInstance(((AccessibilityWindowInfo)this.mInfo).getRoot()) : null;
   }

   public CharSequence getTitle() {
      return VERSION.SDK_INT >= 24 ? ((AccessibilityWindowInfo)this.mInfo).getTitle() : null;
   }

   public int getType() {
      return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo)this.mInfo).getType() : -1;
   }

   public int hashCode() {
      Object var1 = this.mInfo;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.hashCode();
      }

      return var2;
   }

   public boolean isAccessibilityFocused() {
      return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo)this.mInfo).isAccessibilityFocused() : true;
   }

   public boolean isActive() {
      return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo)this.mInfo).isActive() : true;
   }

   public boolean isFocused() {
      return VERSION.SDK_INT >= 21 ? ((AccessibilityWindowInfo)this.mInfo).isFocused() : true;
   }

   public void recycle() {
      if (VERSION.SDK_INT >= 21) {
         ((AccessibilityWindowInfo)this.mInfo).recycle();
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      Rect var2 = new Rect();
      this.getBoundsInScreen(var2);
      var1.append("AccessibilityWindowInfo[");
      var1.append("id=");
      var1.append(this.getId());
      var1.append(", type=");
      var1.append(typeToString(this.getType()));
      var1.append(", layer=");
      var1.append(this.getLayer());
      var1.append(", bounds=");
      var1.append(var2);
      var1.append(", focused=");
      var1.append(this.isFocused());
      var1.append(", active=");
      var1.append(this.isActive());
      var1.append(", hasParent=");
      AccessibilityWindowInfoCompat var5 = this.getParent();
      boolean var3 = true;
      boolean var4;
      if (var5 != null) {
         var4 = true;
      } else {
         var4 = false;
      }

      var1.append(var4);
      var1.append(", hasChildren=");
      if (this.getChildCount() > 0) {
         var4 = var3;
      } else {
         var4 = false;
      }

      var1.append(var4);
      var1.append(']');
      return var1.toString();
   }
}
