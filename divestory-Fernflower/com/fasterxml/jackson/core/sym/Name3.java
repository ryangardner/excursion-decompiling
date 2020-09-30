package com.fasterxml.jackson.core.sym;

public final class Name3 extends Name {
   private final int q1;
   private final int q2;
   private final int q3;

   Name3(String var1, int var2, int var3, int var4, int var5) {
      super(var1, var2);
      this.q1 = var3;
      this.q2 = var4;
      this.q3 = var5;
   }

   public boolean equals(int var1) {
      return false;
   }

   public boolean equals(int var1, int var2) {
      return false;
   }

   public boolean equals(int var1, int var2, int var3) {
      boolean var4;
      if (this.q1 == var1 && this.q2 == var2 && this.q3 == var3) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean equals(int[] var1, int var2) {
      boolean var3 = true;
      if (var2 != 3 || var1[0] != this.q1 || var1[1] != this.q2 || var1[2] != this.q3) {
         var3 = false;
      }

      return var3;
   }
}
