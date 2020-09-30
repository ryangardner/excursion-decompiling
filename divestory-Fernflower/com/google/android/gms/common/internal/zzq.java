package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;

public abstract class zzq extends com.google.android.gms.internal.common.zza implements zzr {
   public static zzr zza(IBinder var0) {
      if (var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.common.internal.IGoogleCertificatesApi");
         return (zzr)(var1 instanceof zzr ? (zzr)var1 : new zzs(var0));
      }
   }
}
