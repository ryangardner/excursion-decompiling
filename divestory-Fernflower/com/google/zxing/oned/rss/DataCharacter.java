package com.google.zxing.oned.rss;

public class DataCharacter {
   private final int checksumPortion;
   private final int value;

   public DataCharacter(int var1, int var2) {
      this.value = var1;
      this.checksumPortion = var2;
   }

   public final boolean equals(Object var1) {
      boolean var2 = var1 instanceof DataCharacter;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         DataCharacter var4 = (DataCharacter)var1;
         var2 = var3;
         if (this.value == var4.value) {
            var2 = var3;
            if (this.checksumPortion == var4.checksumPortion) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   public final int getChecksumPortion() {
      return this.checksumPortion;
   }

   public final int getValue() {
      return this.value;
   }

   public final int hashCode() {
      return this.value ^ this.checksumPortion;
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.value);
      var1.append("(");
      var1.append(this.checksumPortion);
      var1.append(')');
      return var1.toString();
   }
}
