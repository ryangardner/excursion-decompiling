package com.fasterxml.jackson.core.sym;

import java.util.Arrays;

public final class NameN extends Name {
   private final int[] q;
   private final int q1;
   private final int q2;
   private final int q3;
   private final int q4;
   private final int qlen;

   NameN(String var1, int var2, int var3, int var4, int var5, int var6, int[] var7, int var8) {
      super(var1, var2);
      this.q1 = var3;
      this.q2 = var4;
      this.q3 = var5;
      this.q4 = var6;
      this.q = var7;
      this.qlen = var8;
   }

   private final boolean _equals2(int[] var1) {
      int var2 = this.qlen;

      for(int var3 = 0; var3 < var2 - 4; ++var3) {
         if (var1[var3 + 4] != this.q[var3]) {
            return false;
         }
      }

      return true;
   }

   public static NameN construct(String var0, int var1, int[] var2, int var3) {
      if (var3 >= 4) {
         int var4 = var2[0];
         int var5 = var2[1];
         int var6 = var2[2];
         int var7 = var2[3];
         if (var3 - 4 > 0) {
            var2 = Arrays.copyOfRange(var2, 4, var3);
         } else {
            var2 = null;
         }

         return new NameN(var0, var1, var4, var5, var6, var7, var2, var3);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public boolean equals(int var1) {
      return false;
   }

   public boolean equals(int var1, int var2) {
      return false;
   }

   public boolean equals(int var1, int var2, int var3) {
      return false;
   }

   public boolean equals(int[] var1, int var2) {
      if (var2 != this.qlen) {
         return false;
      } else if (var1[0] != this.q1) {
         return false;
      } else if (var1[1] != this.q2) {
         return false;
      } else if (var1[2] != this.q3) {
         return false;
      } else if (var1[3] != this.q4) {
         return false;
      } else {
         switch(var2) {
         case 8:
            if (var1[7] != this.q[3]) {
               return false;
            }
         case 7:
            if (var1[6] != this.q[2]) {
               return false;
            }
         case 6:
            if (var1[5] != this.q[1]) {
               return false;
            }
         case 5:
            if (var1[4] != this.q[0]) {
               return false;
            }
         case 4:
            return true;
         default:
            return this._equals2(var1);
         }
      }
   }
}
