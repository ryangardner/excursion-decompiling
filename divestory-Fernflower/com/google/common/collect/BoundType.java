package com.google.common.collect;

public enum BoundType {
   CLOSED,
   OPEN(false);

   final boolean inclusive;

   static {
      BoundType var0 = new BoundType("CLOSED", 1, true);
      CLOSED = var0;
   }

   private BoundType(boolean var3) {
      this.inclusive = var3;
   }

   static BoundType forBoolean(boolean var0) {
      BoundType var1;
      if (var0) {
         var1 = CLOSED;
      } else {
         var1 = OPEN;
      }

      return var1;
   }

   BoundType flip() {
      return forBoolean(this.inclusive ^ true);
   }
}
