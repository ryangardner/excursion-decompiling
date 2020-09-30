package com.google.android.gms.internal.drive;

public final class zzez extends zzkk<zzez, zzez.zza> implements zzls {
   private static final zzez zzhj;
   private static volatile zzmb<zzez> zzhk;
   private int zzhd;
   private int zzhe = 1;
   private long zzhf = -1L;
   private long zzhg = -1L;
   private long zzhh = -1L;
   private byte zzhi = (byte)2;

   static {
      zzez var0 = new zzez();
      zzhj = var0;
      zzkk.zza(zzez.class, var0);
   }

   private zzez() {
   }

   private final void setSequenceNumber(long var1) {
      this.zzhd |= 2;
      this.zzhf = var1;
   }

   private final void zza(long var1) {
      this.zzhd |= 4;
      this.zzhg = var1;
   }

   // $FF: synthetic method
   static void zza(zzez var0, int var1) {
      var0.zzj(1);
   }

   public static zzez.zza zzaj() {
      return (zzez.zza)zzhj.zzcw();
   }

   private final void zzb(long var1) {
      this.zzhd |= 8;
      this.zzhh = var1;
   }

   private final void zzj(int var1) {
      this.zzhd |= 1;
      this.zzhe = var1;
   }

   protected final Object zza(int var1, Object var2, Object var3) {
      int[] var26 = zzfa.zzhl;
      byte var4 = 1;
      switch(var26[var1 - 1]) {
      case 1:
         return new zzez();
      case 2:
         return new zzez.zza((zzfa)null);
      case 3:
         return zza(zzhj, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0004\u0001Ԅ\u0000\u0002Ԑ\u0001\u0003Ԑ\u0002\u0004Ԑ\u0003", new Object[]{"zzhd", "zzhe", "zzhf", "zzhg", "zzhh"});
      case 4:
         return zzhj;
      case 5:
         zzmb var28 = zzhk;
         var2 = var28;
         if (var28 == null) {
            synchronized(zzez.class){}

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
                     var2 = new zzkk.zzb(zzhj);
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

   public static final class zza extends zzkk.zza<zzez, zzez.zza> implements zzls {
      private zza() {
         super(zzez.zzhj);
      }

      // $FF: synthetic method
      zza(zzfa var1) {
         this();
      }

      public final zzez.zza zzc(long var1) {
         this.zzdb();
         ((zzez)this.zzru).setSequenceNumber(var1);
         return this;
      }

      public final zzez.zza zzd(long var1) {
         this.zzdb();
         ((zzez)this.zzru).zza(var1);
         return this;
      }

      public final zzez.zza zze(long var1) {
         this.zzdb();
         ((zzez)this.zzru).zzb(var1);
         return this;
      }

      public final zzez.zza zzk(int var1) {
         this.zzdb();
         zzez.zza((zzez)this.zzru, 1);
         return this;
      }
   }
}
