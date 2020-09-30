package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class zzc {
   private static final ClassLoader zzd = zzc.class.getClassLoader();

   private zzc() {
   }

   public static void writeBoolean(Parcel var0, boolean var1) {
      var0.writeInt(var1);
   }

   public static <T extends Parcelable> T zza(Parcel var0, Creator<T> var1) {
      return var0.readInt() == 0 ? null : (Parcelable)var1.createFromParcel(var0);
   }

   public static void zza(Parcel var0, IInterface var1) {
      if (var1 == null) {
         var0.writeStrongBinder((IBinder)null);
      } else {
         var0.writeStrongBinder(var1.asBinder());
      }
   }

   public static void zza(Parcel var0, Parcelable var1) {
      if (var1 == null) {
         var0.writeInt(0);
      } else {
         var0.writeInt(1);
         var1.writeToParcel(var0, 0);
      }
   }

   public static boolean zza(Parcel var0) {
      return var0.readInt() != 0;
   }
}
