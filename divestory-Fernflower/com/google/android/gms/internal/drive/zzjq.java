package com.google.android.gms.internal.drive;

final class zzjq extends zzjo {
   private final byte[] buffer;
   private int limit;
   private int pos;
   private final boolean zzoc;
   private int zzod;
   private int zzoe;
   private int zzof;

   private zzjq(byte[] var1, int var2, int var3, boolean var4) {
      super((zzjp)null);
      this.zzof = Integer.MAX_VALUE;
      this.buffer = var1;
      this.limit = var3 + var2;
      this.pos = var2;
      this.zzoe = var2;
      this.zzoc = var4;
   }

   // $FF: synthetic method
   zzjq(byte[] var1, int var2, int var3, boolean var4, zzjp var5) {
      this(var1, var2, var3, var4);
   }

   public final int zzbz() {
      return this.pos - this.zzoe;
   }

   public final int zzv(int var1) throws zzkq {
      if (var1 >= 0) {
         int var2 = var1 + this.zzbz();
         int var3 = this.zzof;
         if (var2 <= var3) {
            this.zzof = var2;
            var1 = this.limit + this.zzod;
            this.limit = var1;
            int var4 = var1 - this.zzoe;
            if (var4 > var2) {
               var2 = var4 - var2;
               this.zzod = var2;
               this.limit = var1 - var2;
            } else {
               this.zzod = 0;
            }

            return var3;
         } else {
            throw zzkq.zzdi();
         }
      } else {
         throw zzkq.zzdj();
      }
   }
}
