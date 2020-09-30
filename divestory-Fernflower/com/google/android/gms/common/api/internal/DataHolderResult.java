package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

public class DataHolderResult implements Releasable, Result {
   protected final DataHolder mDataHolder;
   protected final Status mStatus;

   protected DataHolderResult(DataHolder var1) {
      this(var1, new Status(var1.getStatusCode()));
   }

   protected DataHolderResult(DataHolder var1, Status var2) {
      this.mStatus = var2;
      this.mDataHolder = var1;
   }

   public Status getStatus() {
      return this.mStatus;
   }

   public void release() {
      DataHolder var1 = this.mDataHolder;
      if (var1 != null) {
         var1.close();
      }

   }
}
