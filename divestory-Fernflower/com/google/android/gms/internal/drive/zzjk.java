package com.google.android.gms.internal.drive;

final class zzjk {
   private final byte[] buffer;
   private final zzjr zznx;

   private zzjk(int var1) {
      byte[] var2 = new byte[var1];
      this.buffer = var2;
      this.zznx = zzjr.zzb(var2);
   }

   // $FF: synthetic method
   zzjk(int var1, zzjd var2) {
      this(var1);
   }

   public final zzjc zzbx() {
      this.zznx.zzcb();
      return new zzjm(this.buffer);
   }

   public final zzjr zzby() {
      return this.zznx;
   }
}
