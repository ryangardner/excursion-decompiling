package com.google.zxing;

public final class Dimension {
   private final int height;
   private final int width;

   public Dimension(int var1, int var2) {
      if (var1 >= 0 && var2 >= 0) {
         this.width = var1;
         this.height = var2;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof Dimension;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Dimension var5 = (Dimension)var1;
         var4 = var3;
         if (this.width == var5.width) {
            var4 = var3;
            if (this.height == var5.height) {
               var4 = true;
            }
         }
      }

      return var4;
   }

   public int getHeight() {
      return this.height;
   }

   public int getWidth() {
      return this.width;
   }

   public int hashCode() {
      return this.width * 32713 + this.height;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.width);
      var1.append("x");
      var1.append(this.height);
      return var1.toString();
   }
}
