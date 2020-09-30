package com.google.android.gms.internal.drive;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.OnChangeListener;

final class zzdi {
   private OnChangeListener zzgg;
   private zzee zzgh;
   private DriveId zzk;

   zzdi(zzch var1, OnChangeListener var2, DriveId var3) {
      Preconditions.checkState(com.google.android.gms.drive.events.zzj.zza(1, var3));
      this.zzgg = var2;
      this.zzk = var3;
      Looper var6 = var1.getLooper();
      Context var4 = var1.getApplicationContext();
      var2.getClass();
      zzee var5 = new zzee(var6, var4, 1, zzdj.zza(var2));
      this.zzgh = var5;
      var5.zzf(1);
   }

   // $FF: synthetic method
   static zzee zza(zzdi var0) {
      return var0.zzgh;
   }
}
