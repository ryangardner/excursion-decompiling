package com.google.android.gms.internal.drive;

public final class zzfd extends zzkk<zzfd, zzfd.zza> implements zzls {
   private static volatile zzmb<zzfd> zzhk;
   private static final zzfd zzhq;
   private int zzhd;
   private long zzhg = -1L;
   private byte zzhi = (byte)2;
   private long zzhn = -1L;

   static {
      zzfd var0 = new zzfd();
      zzhq = var0;
      zzkk.zza(zzfd.class, var0);
   }

   private zzfd() {
   }

   private final void zza(long var1) {
      this.zzhd |= 2;
      this.zzhg = var1;
   }

   public static zzfd.zza zzap() {
      return (zzfd.zza)zzhq.zzcw();
   }

   private final void zzf(long var1) {
      this.zzhd |= 1;
      this.zzhn = var1;
   }

   protected final Object zza(int var1, Object var2, Object var3) {
      int[] var26 = zzfe.zzhl;
      byte var4 = 1;
      switch(var26[var1 - 1]) {
      case 1:
         return new zzfd();
      case 2:
         return new zzfd.zza((zzfe)null);
      case 3:
         return zza(zzhq, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001Ԑ\u0000\u0002Ԑ\u0001", new Object[]{"zzhd", "zzhn", "zzhg"});
      case 4:
         return zzhq;
      case 5:
         zzmb var28 = zzhk;
         var2 = var28;
         if (var28 == null) {
            synchronized(zzfd.class){}

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
                     var2 = new zzkk.zzb(zzhq);
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

   public static final class zza extends zzkk.zza<zzfd, zzfd.zza> implements zzls {
      private zza() {
         super(zzfd.zzhq);
      }

      // $FF: synthetic method
      zza(zzfe var1) {
         this();
      }

      public final zzfd.zza zzi(long var1) {
         this.zzdb();
         ((zzfd)this.zzru).zzf(var1);
         return this;
      }

      public final zzfd.zza zzj(long var1) {
         this.zzdb();
         ((zzfd)this.zzru).zza(var1);
         return this;
      }
   }
}
