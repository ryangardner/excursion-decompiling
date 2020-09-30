package com.fasterxml.jackson.core.sym;

public final class Name2 extends Name {
   private final int q1;
   private final int q2;

   Name2(String var1, int var2, int var3, int var4) {
      super(var1, var2);
      this.q1 = var3;
      this.q2 = var4;
   }

   public boolean equals(int var1) {
      return false;
   }

   public boolean equals(int var1, int var2) {
      boolean var3;
      if (var1 == this.q1 && var2 == this.q2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean equals(int var1, int var2, int var3) {
      return false;
   }

   public boolean equals(int[] var1, int var2) {
      boolean var3 = true;
      if (var2 != 2 || var1[0] != this.q1 || var1[1] != this.q2) {
         var3 = false;
      }

      return var3;
   }
}
