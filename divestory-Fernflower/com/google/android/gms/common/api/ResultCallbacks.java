package com.google.android.gms.common.api;

import android.util.Log;

public abstract class ResultCallbacks<R extends Result> implements ResultCallback<R> {
   public abstract void onFailure(Status var1);

   public final void onResult(R var1) {
      Status var2 = var1.getStatus();
      if (var2.isSuccess()) {
         this.onSuccess(var1);
      } else {
         this.onFailure(var2);
         if (var1 instanceof Releasable) {
            try {
               ((Releasable)var1).release();
               return;
            } catch (RuntimeException var4) {
               String var3 = String.valueOf(var1);
               StringBuilder var5 = new StringBuilder(String.valueOf(var3).length() + 18);
               var5.append("Unable to release ");
               var5.append(var3);
               Log.w("ResultCallbacks", var5.toString(), var4);
            }
         }

      }
   }

   public abstract void onSuccess(R var1);
}
