package com.google.android.gms.internal.drive;

public abstract class zzjo {
   private int zznz;
   private int zzoa;
   private boolean zzob;

   private zzjo() {
      this.zznz = 100;
      this.zzoa = Integer.MAX_VALUE;
      this.zzob = false;
   }

   // $FF: synthetic method
   zzjo(zzjp var1) {
      this();
   }

   static zzjo zza(byte[] var0, int var1, int var2, boolean var3) {
      zzjq var5 = new zzjq(var0, 0, var2, false, (zzjp)null);

      try {
         var5.zzv(var2);
         return var5;
      } catch (zzkq var4) {
         throw new IllegalArgumentException(var4);
      }
   }

   public static long zzk(long var0) {
      return -(var0 & 1L) ^ var0 >>> 1;
   }

   public static int zzw(int var0) {
      return -(var0 & 1) ^ var0 >>> 1;
   }

   public abstract int zzbz();

   public abstract int zzv(int var1) throws zzkq;
}
