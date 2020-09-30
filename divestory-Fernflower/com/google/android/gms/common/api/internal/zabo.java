package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.PendingIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

public class zabo extends zak {
   private TaskCompletionSource<Void> zad = new TaskCompletionSource();

   private zabo(LifecycleFragment var1) {
      super(var1);
      this.mLifecycleFragment.addCallback("GmsAvailabilityHelper", this);
   }

   public static zabo zaa(Activity var0) {
      LifecycleFragment var1 = getFragment(var0);
      zabo var2 = (zabo)var1.getCallbackOrNull("GmsAvailabilityHelper", zabo.class);
      if (var2 != null) {
         if (var2.zad.getTask().isComplete()) {
            var2.zad = new TaskCompletionSource();
         }

         return var2;
      } else {
         return new zabo(var1);
      }
   }

   public void onDestroy() {
      super.onDestroy();
      this.zad.trySetException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
   }

   protected final void zaa() {
      Activity var1 = this.mLifecycleFragment.getLifecycleActivity();
      if (var1 == null) {
         this.zad.trySetException(new ApiException(new Status(8)));
      } else {
         int var2 = this.zac.isGooglePlayServicesAvailable(var1);
         if (var2 == 0) {
            this.zad.trySetResult((Object)null);
         } else {
            if (!this.zad.getTask().isComplete()) {
               this.zab(new ConnectionResult(var2, (PendingIntent)null), 0);
            }

         }
      }
   }

   protected final void zaa(ConnectionResult var1, int var2) {
      String var3 = var1.getErrorMessage();
      String var4 = var3;
      if (var3 == null) {
         var4 = "Error connecting to Google Play services";
      }

      this.zad.setException(new ApiException(new Status(var1, var4, var1.getErrorCode())));
   }

   public final Task<Void> zac() {
      return this.zad.getTask();
   }
}
