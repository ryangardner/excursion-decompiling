package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

abstract class zzd extends com.google.android.gms.common.internal.zzo {
   private int zza;

   protected zzd(byte[] var1) {
      boolean var2;
      if (var1.length == 25) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      this.zza = Arrays.hashCode(var1);
   }

   protected static byte[] zza(String var0) {
      try {
         byte[] var2 = var0.getBytes("ISO-8859-1");
         return var2;
      } catch (UnsupportedEncodingException var1) {
         throw new AssertionError(var1);
      }
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof com.google.android.gms.common.internal.zzm) {
         RemoteException var10000;
         label45: {
            boolean var10001;
            com.google.android.gms.common.internal.zzm var6;
            try {
               var6 = (com.google.android.gms.common.internal.zzm)var1;
               if (var6.zzc() != this.hashCode()) {
                  return false;
               }
            } catch (RemoteException var5) {
               var10000 = var5;
               var10001 = false;
               break label45;
            }

            IObjectWrapper var7;
            try {
               var7 = var6.zzb();
            } catch (RemoteException var4) {
               var10000 = var4;
               var10001 = false;
               break label45;
            }

            if (var7 == null) {
               return false;
            }

            try {
               byte[] var9 = (byte[])ObjectWrapper.unwrap(var7);
               boolean var2 = Arrays.equals(this.zza(), var9);
               return var2;
            } catch (RemoteException var3) {
               var10000 = var3;
               var10001 = false;
            }
         }

         RemoteException var8 = var10000;
         Log.e("GoogleCertificates", "Failed to get Google certificates from remote", var8);
      }

      return false;
   }

   public int hashCode() {
      return this.zza;
   }

   abstract byte[] zza();

   public final IObjectWrapper zzb() {
      return ObjectWrapper.wrap(this.zza());
   }

   public final int zzc() {
      return this.hashCode();
   }
}
