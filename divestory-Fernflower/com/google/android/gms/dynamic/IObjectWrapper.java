package com.google.android.gms.dynamic;

import android.os.IBinder;
import android.os.IInterface;
import com.google.android.gms.internal.common.zzb;

public interface IObjectWrapper extends IInterface {
   public static class Stub extends com.google.android.gms.internal.common.zza implements IObjectWrapper {
      public Stub() {
         super("com.google.android.gms.dynamic.IObjectWrapper");
      }

      public static IObjectWrapper asInterface(IBinder var0) {
         if (var0 == null) {
            return null;
         } else {
            IInterface var1 = var0.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
            return (IObjectWrapper)(var1 instanceof IObjectWrapper ? (IObjectWrapper)var1 : new IObjectWrapper.Stub.zza(var0));
         }
      }

      public static final class zza extends zzb implements IObjectWrapper {
         zza(IBinder var1) {
            super(var1, "com.google.android.gms.dynamic.IObjectWrapper");
         }
      }
   }
}
