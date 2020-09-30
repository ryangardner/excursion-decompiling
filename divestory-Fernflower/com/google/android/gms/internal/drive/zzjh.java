package com.google.android.gms.internal.drive;

final class zzjh extends zzjm {
   private final int zznv;
   private final int zznw;

   zzjh(byte[] var1, int var2, int var3) {
      super(var1);
      zzb(var2, var2 + var3, var1.length);
      this.zznv = var2;
      this.zznw = var3;
   }

   public final int size() {
      return this.zznw;
   }

   protected final int zzbw() {
      return this.zznv;
   }

   public final byte zzs(int var1) {
      int var2 = this.size();
      if ((var2 - (var1 + 1) | var1) < 0) {
         StringBuilder var3;
         if (var1 < 0) {
            var3 = new StringBuilder(22);
            var3.append("Index < 0: ");
            var3.append(var1);
            throw new ArrayIndexOutOfBoundsException(var3.toString());
         } else {
            var3 = new StringBuilder(40);
            var3.append("Index > length: ");
            var3.append(var1);
            var3.append(", ");
            var3.append(var2);
            throw new ArrayIndexOutOfBoundsException(var3.toString());
         }
      } else {
         return this.zzny[this.zznv + var1];
      }
   }

   final byte zzt(int var1) {
      return this.zzny[this.zznv + var1];
   }
}
