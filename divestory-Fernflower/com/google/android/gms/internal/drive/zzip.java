package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.IInterface;

public final class zzip extends zzb implements zzio {
   public static zzio zzb(IBinder var0) {
      if (var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.drive.realtime.internal.IRealtimeService");
         return (zzio)(var1 instanceof zzio ? (zzio)var1 : new zziq(var0));
      }
   }
}
