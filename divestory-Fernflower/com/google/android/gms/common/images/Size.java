package com.google.android.gms.common.images;

public final class Size {
   private final int zaa;
   private final int zab;

   public Size(int var1, int var2) {
      this.zaa = var1;
      this.zab = var2;
   }

   public static Size parseSize(String var0) throws NumberFormatException {
      if (var0 != null) {
         int var1 = var0.indexOf(42);
         int var2 = var1;
         if (var1 < 0) {
            var2 = var0.indexOf(120);
         }

         if (var2 >= 0) {
            try {
               Size var3 = new Size(Integer.parseInt(var0.substring(0, var2)), Integer.parseInt(var0.substring(var2 + 1)));
               return var3;
            } catch (NumberFormatException var4) {
               throw zaa(var0);
            }
         } else {
            throw zaa(var0);
         }
      } else {
         throw new IllegalArgumentException("string must not be null");
      }
   }

   private static NumberFormatException zaa(String var0) {
      StringBuilder var1 = new StringBuilder(String.valueOf(var0).length() + 16);
      var1.append("Invalid Size: \"");
      var1.append(var0);
      var1.append("\"");
      throw new NumberFormatException(var1.toString());
   }

   public final boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (this == var1) {
         return true;
      } else {
         if (var1 instanceof Size) {
            Size var2 = (Size)var1;
            if (this.zaa == var2.zaa && this.zab == var2.zab) {
               return true;
            }
         }

         return false;
      }
   }

   public final int getHeight() {
      return this.zab;
   }

   public final int getWidth() {
      return this.zaa;
   }

   public final int hashCode() {
      int var1 = this.zab;
      int var2 = this.zaa;
      return var1 ^ (var2 >>> 16 | var2 << 16);
   }

   public final String toString() {
      int var1 = this.zaa;
      int var2 = this.zab;
      StringBuilder var3 = new StringBuilder(23);
      var3.append(var1);
      var3.append("x");
      var3.append(var2);
      return var3.toString();
   }
}
