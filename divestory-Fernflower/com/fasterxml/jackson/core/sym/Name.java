package com.fasterxml.jackson.core.sym;

public abstract class Name {
   protected final int _hashCode;
   protected final String _name;

   protected Name(String var1, int var2) {
      this._name = var1;
      this._hashCode = var2;
   }

   public abstract boolean equals(int var1);

   public abstract boolean equals(int var1, int var2);

   public abstract boolean equals(int var1, int var2, int var3);

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 == this) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public abstract boolean equals(int[] var1, int var2);

   public String getName() {
      return this._name;
   }

   public final int hashCode() {
      return this._hashCode;
   }

   public String toString() {
      return this._name;
   }
}
