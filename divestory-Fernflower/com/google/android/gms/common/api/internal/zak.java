package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zak extends LifecycleCallback implements OnCancelListener {
   protected volatile boolean zaa;
   protected final AtomicReference<zam> zab;
   protected final GoogleApiAvailability zac;
   private final Handler zad;

   protected zak(LifecycleFragment var1) {
      this(var1, GoogleApiAvailability.getInstance());
   }

   zak(LifecycleFragment var1, GoogleApiAvailability var2) {
      super(var1);
      this.zab = new AtomicReference((Object)null);
      this.zad = new com.google.android.gms.internal.base.zap(Looper.getMainLooper());
      this.zac = var2;
   }

   private static int zaa(zam var0) {
      return var0 == null ? -1 : var0.zaa();
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      zam var7;
      boolean var9;
      label50: {
         zam var4 = (zam)this.zab.get();
         boolean var5 = true;
         boolean var6 = true;
         if (var1 != 1) {
            if (var1 == 2) {
               int var10 = this.zac.isGooglePlayServicesAvailable(this.getActivity());
               boolean var8;
               if (var10 == 0) {
                  var8 = var6;
               } else {
                  var8 = false;
               }

               if (var4 == null) {
                  return;
               }

               var7 = var4;
               var9 = var8;
               if (var4.zab().getErrorCode() == 18) {
                  var7 = var4;
                  var9 = var8;
                  if (var10 == 18) {
                     return;
                  }
               }
               break label50;
            }

            var7 = var4;
         } else {
            if (var2 == -1) {
               var7 = var4;
               var9 = var5;
               break label50;
            }

            var7 = var4;
            if (var2 == 0) {
               if (var4 == null) {
                  return;
               }

               var1 = 13;
               if (var3 != null) {
                  var1 = var3.getIntExtra("<<ResolutionFailureErrorDetail>>", 13);
               }

               var7 = new zam(new ConnectionResult(var1, (PendingIntent)null, var4.zab().toString()), zaa(var4));
               this.zab.set(var7);
            }
         }

         var9 = false;
      }

      if (var9) {
         this.zab();
      } else {
         if (var7 != null) {
            this.zaa(var7.zab(), var7.zaa());
         }

      }
   }

   public void onCancel(DialogInterface var1) {
      this.zaa(new ConnectionResult(13, (PendingIntent)null), zaa((zam)this.zab.get()));
      this.zab();
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      if (var1 != null) {
         AtomicReference var2 = this.zab;
         zam var3;
         if (var1.getBoolean("resolving_error", false)) {
            var3 = new zam(new ConnectionResult(var1.getInt("failed_status"), (PendingIntent)var1.getParcelable("failed_resolution")), var1.getInt("failed_client_id", -1));
         } else {
            var3 = null;
         }

         var2.set(var3);
      }

   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      zam var2 = (zam)this.zab.get();
      if (var2 != null) {
         var1.putBoolean("resolving_error", true);
         var1.putInt("failed_client_id", var2.zaa());
         var1.putInt("failed_status", var2.zab().getErrorCode());
         var1.putParcelable("failed_resolution", var2.zab().getResolution());
      }

   }

   public void onStart() {
      super.onStart();
      this.zaa = true;
   }

   public void onStop() {
      super.onStop();
      this.zaa = false;
   }

   protected abstract void zaa();

   protected abstract void zaa(ConnectionResult var1, int var2);

   protected final void zab() {
      this.zab.set((Object)null);
      this.zaa();
   }

   public final void zab(ConnectionResult var1, int var2) {
      zam var3 = new zam(var1, var2);
      if (this.zab.compareAndSet((Object)null, var3)) {
         this.zad.post(new zal(this, var3));
      }

   }
}
