package com.google.android.gms.internal.drive;

import java.lang.reflect.Type;

public enum zzke {
   zzoy(0, zzkg.zzrg, zzks.zzsv),
   zzoz(1, zzkg.zzrg, zzks.zzsu),
   zzpa(2, zzkg.zzrg, zzks.zzst),
   zzpb(3, zzkg.zzrg, zzks.zzst),
   zzpc(4, zzkg.zzrg, zzks.zzss),
   zzpd(5, zzkg.zzrg, zzks.zzst),
   zzpe(6, zzkg.zzrg, zzks.zzss),
   zzpf(7, zzkg.zzrg, zzks.zzsw),
   zzpg(8, zzkg.zzrg, zzks.zzsx),
   zzph(9, zzkg.zzrg, zzks.zzta),
   zzpi(10, zzkg.zzrg, zzks.zzsy),
   zzpj(11, zzkg.zzrg, zzks.zzss),
   zzpk(12, zzkg.zzrg, zzks.zzsz),
   zzpl(13, zzkg.zzrg, zzks.zzss),
   zzpm(14, zzkg.zzrg, zzks.zzst),
   zzpn(15, zzkg.zzrg, zzks.zzss),
   zzpo(16, zzkg.zzrg, zzks.zzst),
   zzpp(17, zzkg.zzrg, zzks.zzta),
   zzpq(18, zzkg.zzrh, zzks.zzsv),
   zzpr(19, zzkg.zzrh, zzks.zzsu),
   zzps(20, zzkg.zzrh, zzks.zzst),
   zzpt(21, zzkg.zzrh, zzks.zzst),
   zzpu(22, zzkg.zzrh, zzks.zzss),
   zzpv(23, zzkg.zzrh, zzks.zzst),
   zzpw(24, zzkg.zzrh, zzks.zzss),
   zzpx(25, zzkg.zzrh, zzks.zzsw),
   zzpy(26, zzkg.zzrh, zzks.zzsx),
   zzpz(27, zzkg.zzrh, zzks.zzta),
   zzqa(28, zzkg.zzrh, zzks.zzsy),
   zzqb(29, zzkg.zzrh, zzks.zzss),
   zzqc(30, zzkg.zzrh, zzks.zzsz),
   zzqd(31, zzkg.zzrh, zzks.zzss),
   zzqe(32, zzkg.zzrh, zzks.zzst),
   zzqf(33, zzkg.zzrh, zzks.zzss),
   zzqg(34, zzkg.zzrh, zzks.zzst),
   zzqh(35, zzkg.zzri, zzks.zzsv),
   zzqi(36, zzkg.zzri, zzks.zzsu),
   zzqj(37, zzkg.zzri, zzks.zzst),
   zzqk(38, zzkg.zzri, zzks.zzst),
   zzql(39, zzkg.zzri, zzks.zzss),
   zzqm(40, zzkg.zzri, zzks.zzst),
   zzqn(41, zzkg.zzri, zzks.zzss),
   zzqo(42, zzkg.zzri, zzks.zzsw),
   zzqp(43, zzkg.zzri, zzks.zzss),
   zzqq(44, zzkg.zzri, zzks.zzsz),
   zzqr(45, zzkg.zzri, zzks.zzss),
   zzqs(46, zzkg.zzri, zzks.zzst),
   zzqt(47, zzkg.zzri, zzks.zzss),
   zzqu(48, zzkg.zzri, zzks.zzst),
   zzqv(49, zzkg.zzrh, zzks.zzta),
   zzqw;

   private static final zzke[] zzrb;
   private static final Type[] zzrc;
   private final int id;
   private final zzks zzqx;
   private final zzkg zzqy;
   private final Class<?> zzqz;
   private final boolean zzra;

   static {
      zzke var0 = new zzke("MAP", 50, 50, zzkg.zzrj, zzks.zzsr);
      zzqw = var0;
      zzke var1 = zzoy;
      int var2 = 0;
      zzrc = new Type[0];
      zzke[] var4 = values();
      zzrb = new zzke[var4.length];

      for(int var3 = var4.length; var2 < var3; ++var2) {
         var1 = var4[var2];
         zzrb[var1.id] = var1;
      }

   }

   private zzke(int var3, zzkg var4, zzks var5) {
      this.id = var3;
      this.zzqy = var4;
      this.zzqx = var5;
      var2 = zzkf.zzre[var4.ordinal()];
      boolean var6 = true;
      if (var2 != 1) {
         if (var2 != 2) {
            this.zzqz = null;
         } else {
            this.zzqz = var5.zzdo();
         }
      } else {
         this.zzqz = var5.zzdo();
      }

      label23: {
         if (var4 == zzkg.zzrg) {
            var2 = zzkf.zzrf[var5.ordinal()];
            if (var2 != 1 && var2 != 2 && var2 != 3) {
               break label23;
            }
         }

         var6 = false;
      }

      this.zzra = var6;
   }

   public final int id() {
      return this.id;
   }
}
