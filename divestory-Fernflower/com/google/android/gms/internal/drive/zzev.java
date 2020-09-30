package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.IInterface;

public abstract class zzev extends zzb implements zzeu {
   public static zzeu zza(IBinder var0) {
      if (var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.drive.internal.IEventReleaseCallback");
         return (zzeu)(var1 instanceof zzeu ? (zzeu)var1 : new zzew(var0));
      }
   }
}
