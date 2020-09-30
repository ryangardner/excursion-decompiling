package com.google.android.gms.internal.drive;

public enum zznm {
   zzwu(zznr.zzxs, 1),
   zzwv(zznr.zzxr, 5),
   zzww(zznr.zzxq, 0),
   zzwx(zznr.zzxq, 0),
   zzwy(zznr.zzxp, 0),
   zzwz(zznr.zzxq, 1),
   zzxa(zznr.zzxp, 5),
   zzxb(zznr.zzxt, 0),
   zzxc(zznr.zzxu, 2),
   zzxd(zznr.zzxx, 3),
   zzxe(zznr.zzxx, 2),
   zzxf(zznr.zzxv, 2),
   zzxg(zznr.zzxp, 0),
   zzxh(zznr.zzxw, 0),
   zzxi(zznr.zzxp, 5),
   zzxj(zznr.zzxq, 1),
   zzxk(zznr.zzxp, 0),
   zzxl;

   private final zznr zzxm;
   private final int zzxn;

   static {
      zznm var0 = new zznm("SINT64", 17, zznr.zzxq, 0);
      zzxl = var0;
   }

   private zznm(zznr var3, int var4) {
      this.zzxm = var3;
      this.zzxn = var4;
   }

   // $FF: synthetic method
   zznm(zznr var3, int var4, zznl var5) {
      this(var3, var4);
   }

   public final zznr zzfj() {
      return this.zzxm;
   }

   public final int zzfk() {
      return this.zzxn;
   }
}
