package com.google.android.gms.common;

import java.lang.ref.WeakReference;

abstract class zzf extends zzd {
   private static final WeakReference<byte[]> zzb = new WeakReference((Object)null);
   private WeakReference<byte[]> zza;

   zzf(byte[] var1) {
      super(var1);
      this.zza = zzb;
   }

   final byte[] zza() {
      synchronized(this){}

      Throwable var10000;
      boolean var10001;
      label176: {
         byte[] var1;
         try {
            var1 = (byte[])this.zza.get();
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label176;
         }

         byte[] var2 = var1;
         if (var1 == null) {
            try {
               var2 = this.zzd();
               WeakReference var23 = new WeakReference(var2);
               this.zza = var23;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label176;
            }
         }

         label165:
         try {
            return var2;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            break label165;
         }
      }

      while(true) {
         Throwable var24 = var10000;

         try {
            throw var24;
         } catch (Throwable var19) {
            var10000 = var19;
            var10001 = false;
            continue;
         }
      }
   }

   protected abstract byte[] zzd();
}
