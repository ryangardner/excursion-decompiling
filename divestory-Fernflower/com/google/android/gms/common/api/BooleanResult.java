package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.Preconditions;

public class BooleanResult implements Result {
   private final Status zaa;
   private final boolean zab;

   public BooleanResult(Status var1, boolean var2) {
      this.zaa = (Status)Preconditions.checkNotNull(var1, "Status must not be null");
      this.zab = var2;
   }

   public final boolean equals(Object var1) {
      if (var1 == null) {
         return false;
      } else if (var1 == this) {
         return true;
      } else if (!(var1 instanceof BooleanResult)) {
         return false;
      } else {
         BooleanResult var2 = (BooleanResult)var1;
         return this.zaa.equals(var2.zaa) && this.zab == var2.zab;
      }
   }

   public Status getStatus() {
      return this.zaa;
   }

   public boolean getValue() {
      return this.zab;
   }

   public final int hashCode() {
      return (this.zaa.hashCode() + 527) * 31 + this.zab;
   }
}
