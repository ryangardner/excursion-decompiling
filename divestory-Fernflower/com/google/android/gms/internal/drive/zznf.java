package com.google.android.gms.internal.drive;

final class zznf {
   private static final zznh zzwt;

   static {
      boolean var0;
      if (zznd.zzfd() && zznd.zzfe()) {
         var0 = true;
      } else {
         var0 = false;
      }

      Object var1;
      if (var0 && !zzix.zzbr()) {
         var1 = new zznk();
      } else {
         var1 = new zzni();
      }

      zzwt = (zznh)var1;
   }

   static int zza(CharSequence var0) {
      int var1 = var0.length();
      byte var2 = 0;

      int var3;
      for(var3 = 0; var3 < var1 && var0.charAt(var3) < 128; ++var3) {
      }

      int var4 = var1;

      int var5;
      while(true) {
         var5 = var4;
         if (var3 >= var1) {
            break;
         }

         char var13 = var0.charAt(var3);
         if (var13 >= 2048) {
            int var6 = var0.length();

            int var12;
            for(var5 = var2; var3 < var6; var3 = var12 + 1) {
               char var7 = var0.charAt(var3);
               if (var7 < 2048) {
                  var5 += 127 - var7 >>> 31;
                  var12 = var3;
               } else {
                  int var8 = var5 + 2;
                  var5 = var8;
                  var12 = var3;
                  if ('\ud800' <= var7) {
                     var5 = var8;
                     var12 = var3;
                     if (var7 <= '\udfff') {
                        if (Character.codePointAt(var0, var3) < 65536) {
                           throw new zznj(var3, var6);
                        }

                        var12 = var3 + 1;
                        var5 = var8;
                     }
                  }
               }
            }

            var5 += var4;
            break;
         }

         var4 += 127 - var13 >>> 31;
         ++var3;
      }

      if (var5 >= var1) {
         return var5;
      } else {
         long var9 = (long)var5;
         StringBuilder var11 = new StringBuilder(54);
         var11.append("UTF-8 length does not fit in int: ");
         var11.append(var9 + 4294967296L);
         throw new IllegalArgumentException(var11.toString());
      }
   }

   static int zza(CharSequence var0, byte[] var1, int var2, int var3) {
      return zzwt.zzb(var0, var1, var2, var3);
   }

   private static int zzay(int var0) {
      int var1 = var0;
      if (var0 > -12) {
         var1 = -1;
      }

      return var1;
   }

   // $FF: synthetic method
   static int zzaz(int var0) {
      return zzay(var0);
   }

   private static int zzc(int var0, int var1, int var2) {
      return var0 <= -12 && var1 <= -65 && var2 <= -65 ? var0 ^ var1 << 8 ^ var2 << 16 : -1;
   }

   // $FF: synthetic method
   static int zzd(int var0, int var1, int var2) {
      return zzc(var0, var1, var2);
   }

   public static boolean zzd(byte[] var0) {
      return zzwt.zze(var0, 0, var0.length);
   }

   public static boolean zze(byte[] var0, int var1, int var2) {
      return zzwt.zze(var0, var1, var2);
   }

   private static int zzf(byte[] var0, int var1, int var2) {
      byte var3 = var0[var1 - 1];
      var2 -= var1;
      if (var2 != 0) {
         if (var2 != 1) {
            if (var2 == 2) {
               return zzc(var3, var0[var1], var0[var1 + 1]);
            } else {
               throw new AssertionError();
            }
         } else {
            return zzr(var3, var0[var1]);
         }
      } else {
         return zzay(var3);
      }
   }

   static String zzg(byte[] var0, int var1, int var2) throws zzkq {
      return zzwt.zzg(var0, var1, var2);
   }

   // $FF: synthetic method
   static int zzh(byte[] var0, int var1, int var2) {
      return zzf(var0, var1, var2);
   }

   private static int zzr(int var0, int var1) {
      return var0 <= -12 && var1 <= -65 ? var0 ^ var1 << 8 : -1;
   }

   // $FF: synthetic method
   static int zzs(int var0, int var1) {
      return zzr(var0, var1);
   }
}
