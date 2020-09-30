package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.nio.charset.Charset;

class zzjm extends zzjl {
   protected final byte[] zzny;

   zzjm(byte[] var1) {
      if (var1 != null) {
         this.zzny = var1;
      } else {
         throw null;
      }
   }

   public final boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof zzjc)) {
         return false;
      } else if (this.size() != ((zzjc)var1).size()) {
         return false;
      } else if (this.size() == 0) {
         return true;
      } else if (var1 instanceof zzjm) {
         zzjm var4 = (zzjm)var1;
         int var2 = this.zzbv();
         int var3 = var4.zzbv();
         return var2 != 0 && var3 != 0 && var2 != var3 ? false : this.zza(var4, 0, this.size());
      } else {
         return var1.equals(this);
      }
   }

   public int size() {
      return this.zzny.length;
   }

   protected final int zza(int var1, int var2, int var3) {
      return zzkm.zza(var1, this.zzny, this.zzbw(), var3);
   }

   public final zzjc zza(int var1, int var2) {
      var1 = zzb(0, var2, this.size());
      return (zzjc)(var1 == 0 ? zzjc.zznq : new zzjh(this.zzny, this.zzbw(), var1));
   }

   protected final String zza(Charset var1) {
      return new String(this.zzny, this.zzbw(), this.size(), var1);
   }

   final void zza(zzjb var1) throws IOException {
      var1.zza(this.zzny, this.zzbw(), this.size());
   }

   final boolean zza(zzjc var1, int var2, int var3) {
      StringBuilder var8;
      if (var3 <= var1.size()) {
         if (var3 <= var1.size()) {
            if (var1 instanceof zzjm) {
               zzjm var9 = (zzjm)var1;
               byte[] var4 = this.zzny;
               byte[] var5 = var9.zzny;
               int var6 = this.zzbw();
               int var7 = this.zzbw();

               for(var2 = var9.zzbw(); var7 < var6 + var3; ++var2) {
                  if (var4[var7] != var5[var2]) {
                     return false;
                  }

                  ++var7;
               }

               return true;
            } else {
               return var1.zza(0, var3).equals(this.zza(0, var3));
            }
         } else {
            var2 = var1.size();
            var8 = new StringBuilder(59);
            var8.append("Ran off end of other: 0, ");
            var8.append(var3);
            var8.append(", ");
            var8.append(var2);
            throw new IllegalArgumentException(var8.toString());
         }
      } else {
         var2 = this.size();
         var8 = new StringBuilder(40);
         var8.append("Length too large: ");
         var8.append(var3);
         var8.append(var2);
         throw new IllegalArgumentException(var8.toString());
      }
   }

   public final boolean zzbu() {
      int var1 = this.zzbw();
      return zznf.zze(this.zzny, var1, this.size() + var1);
   }

   protected int zzbw() {
      return 0;
   }

   public byte zzs(int var1) {
      return this.zzny[var1];
   }

   byte zzt(int var1) {
      return this.zzny[var1];
   }
}
