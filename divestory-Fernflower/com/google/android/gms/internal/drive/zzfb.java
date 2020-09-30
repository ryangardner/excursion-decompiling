package com.google.android.gms.internal.drive;

public final class zzfb extends zzkk<zzfb, zzfb.zza> implements zzls {
   private static volatile zzmb<zzfb> zzhk;
   private static final zzfb zzhp;
   private int zzhd;
   private int zzhe = 1;
   private long zzhg = -1L;
   private byte zzhi = (byte)2;
   private String zzhm = "";
   private long zzhn = -1L;
   private int zzho = -1;

   static {
      zzfb var0 = new zzfb();
      zzhp = var0;
      zzkk.zza(zzfb.class, var0);
   }

   private zzfb() {
   }

   public static zzfb zza(byte[] var0, zzjx var1) throws zzkq {
      return (zzfb)zzkk.zza((zzkk)zzhp, (byte[])var0, (zzjx)var1);
   }

   private final void zza(long var1) {
      this.zzhd |= 8;
      this.zzhg = var1;
   }

   // $FF: synthetic method
   static void zza(zzfb var0, int var1) {
      var0.zzj(1);
   }

   public static zzfb.zza zzan() {
      return (zzfb.zza)zzhp.zzcw();
   }

   private final void zzd(String var1) {
      if (var1 != null) {
         this.zzhd |= 2;
         this.zzhm = var1;
      } else {
         throw null;
      }
   }

   private final void zzf(long var1) {
      this.zzhd |= 4;
      this.zzhn = var1;
   }

   private final void zzj(int var1) {
      this.zzhd |= 1;
      this.zzhe = var1;
   }

   private final void zzl(int var1) {
      this.zzhd |= 16;
      this.zzho = var1;
   }

   public final String getResourceId() {
      return this.zzhm;
   }

   public final int getResourceType() {
      return this.zzho;
   }

   protected final Object zza(int var1, Object var2, Object var3) {
      int[] var26 = zzfc.zzhl;
      byte var4 = 1;
      switch(var26[var1 - 1]) {
      case 1:
         return new zzfb();
      case 2:
         return new zzfb.zza((zzfc)null);
      case 3:
         return zza(zzhp, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0004\u0001Ԅ\u0000\u0002Ԉ\u0001\u0003Ԑ\u0002\u0004Ԑ\u0003\u0005\u0004\u0004", new Object[]{"zzhd", "zzhe", "zzhm", "zzhn", "zzhg", "zzho"});
      case 4:
         return zzhp;
      case 5:
         zzmb var28 = zzhk;
         var2 = var28;
         if (var28 == null) {
            synchronized(zzfb.class){}

            Throwable var10000;
            boolean var10001;
            label291: {
               try {
                  var28 = zzhk;
               } catch (Throwable var24) {
                  var10000 = var24;
                  var10001 = false;
                  break label291;
               }

               var2 = var28;
               if (var28 == null) {
                  try {
                     var2 = new zzkk.zzb(zzhp);
                     zzhk = (zzmb)var2;
                  } catch (Throwable var23) {
                     var10000 = var23;
                     var10001 = false;
                     break label291;
                  }
               }

               label276:
               try {
                  return var2;
               } catch (Throwable var22) {
                  var10000 = var22;
                  var10001 = false;
                  break label276;
               }
            }

            while(true) {
               Throwable var27 = var10000;

               try {
                  throw var27;
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  continue;
               }
            }
         }

         return var2;
      case 6:
         return this.zzhi;
      case 7:
         byte var25 = var4;
         if (var2 == null) {
            var25 = 0;
         }

         this.zzhi = (byte)((byte)var25);
         return null;
      default:
         throw new UnsupportedOperationException();
      }
   }

   public final long zzal() {
      return this.zzhn;
   }

   public final long zzam() {
      return this.zzhg;
   }

   public static final class zza extends zzkk.zza<zzfb, zzfb.zza> implements zzls {
      private zza() {
         super(zzfb.zzhp);
      }

      // $FF: synthetic method
      zza(zzfc var1) {
         this();
      }

      public final zzfb.zza zze(String var1) {
         this.zzdb();
         ((zzfb)this.zzru).zzd(var1);
         return this;
      }

      public final zzfb.zza zzg(long var1) {
         this.zzdb();
         ((zzfb)this.zzru).zzf(var1);
         return this;
      }

      public final zzfb.zza zzh(long var1) {
         this.zzdb();
         ((zzfb)this.zzru).zza(var1);
         return this;
      }

      public final zzfb.zza zzm(int var1) {
         this.zzdb();
         zzfb.zza((zzfb)this.zzru, 1);
         return this;
      }

      public final zzfb.zza zzn(int var1) {
         this.zzdb();
         ((zzfb)this.zzru).zzl(var1);
         return this;
      }
   }
}
