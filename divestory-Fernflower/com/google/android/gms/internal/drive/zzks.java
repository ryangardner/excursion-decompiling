package com.google.android.gms.internal.drive;

public enum zzks {
   zzsr(Void.class, Void.class, (Object)null),
   zzss(Integer.TYPE, Integer.class, 0),
   zzst(Long.TYPE, Long.class, 0L),
   zzsu(Float.TYPE, Float.class, 0.0F),
   zzsv(Double.TYPE, Double.class, 0.0D),
   zzsw(Boolean.TYPE, Boolean.class, false),
   zzsx(String.class, String.class, ""),
   zzsy(zzjc.class, zzjc.class, zzjc.zznq),
   zzsz(Integer.TYPE, Integer.class, (Object)null),
   zzta;

   private final Class<?> zztb;
   private final Class<?> zztc;
   private final Object zztd;

   static {
      zzks var0 = new zzks("MESSAGE", 9, Object.class, Object.class, (Object)null);
      zzta = var0;
   }

   private zzks(Class<?> var3, Class<?> var4, Object var5) {
      this.zztb = var3;
      this.zztc = var4;
      this.zztd = var5;
   }

   public final Class<?> zzdo() {
      return this.zztc;
   }
}
