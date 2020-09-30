package com.google.android.gms.common.api;

import android.app.Activity;
import android.content.IntentSender.SendIntentException;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;

public abstract class ResolvingResultCallbacks<R extends Result> extends ResultCallbacks<R> {
   private final Activity zza;
   private final int zzb;

   protected ResolvingResultCallbacks(Activity var1, int var2) {
      this.zza = (Activity)Preconditions.checkNotNull(var1, "Activity must not be null");
      this.zzb = var2;
   }

   public final void onFailure(Status var1) {
      if (var1.hasResolution()) {
         try {
            var1.startResolutionForResult(this.zza, this.zzb);
         } catch (SendIntentException var2) {
            Log.e("ResolvingResultCallback", "Failed to start resolution", var2);
            this.onUnresolvableFailure(new Status(8));
         }
      } else {
         this.onUnresolvableFailure(var1);
      }
   }

   public abstract void onSuccess(R var1);

   public abstract void onUnresolvableFailure(Status var1);
}
