package com.google.android.gms.internal.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class zzc {
   private static final ClassLoader zzd = zzc.class.getClassLoader();

   private zzc() {
   }

   public static <T extends Parcelable> T zza(Parcel var0, Creator<T> var1) {
      return var0.readInt() == 0 ? null : (Parcelable)var1.createFromParcel(var0);
   }

   public static void zza(Parcel var0, IInterface var1) {
      IBinder var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.asBinder();
      }

      var0.writeStrongBinder(var2);
   }

   public static void zza(Parcel var0, Parcelable var1) {
      if (var1 == null) {
         var0.writeInt(0);
      } else {
         var0.writeInt(1);
         var1.writeToParcel(var0, 0);
      }
   }

   public static void zza(Parcel var0, boolean var1) {
      var0.writeInt(var1);
   }
}
