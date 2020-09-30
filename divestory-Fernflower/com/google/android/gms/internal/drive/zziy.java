package com.google.android.gms.internal.drive;

import java.io.IOException;

final class zziy {
   static int zza(int var0, byte[] var1, int var2, int var3, zziz var4) throws zzkq {
      if (var0 >>> 3 == 0) {
         throw zzkq.zzdk();
      } else {
         int var5 = var0 & 7;
         if (var5 == 0) {
            return zzb(var1, var2, var4);
         } else if (var5 == 1) {
            return var2 + 8;
         } else if (var5 == 2) {
            return zza(var1, var2, var4) + var4.zznk;
         } else if (var5 != 3) {
            if (var5 == 5) {
               return var2 + 4;
            } else {
               throw zzkq.zzdk();
            }
         } else {
            int var6 = var0 & -8 | 4;
            var0 = 0;

            int var7;
            while(true) {
               var7 = var0;
               var5 = var2;
               if (var2 >= var3) {
                  break;
               }

               var2 = zza(var1, var2, var4);
               var0 = var4.zznk;
               var7 = var0;
               var5 = var2;
               if (var0 == var6) {
                  break;
               }

               var2 = zza(var0, var1, var2, var3, var4);
            }

            if (var5 <= var3 && var7 == var6) {
               return var5;
            } else {
               throw zzkq.zzdm();
            }
         }
      }
   }

   static int zza(int var0, byte[] var1, int var2, int var3, zzkp<?> var4, zziz var5) {
      zzkl var7 = (zzkl)var4;
      var2 = zza(var1, var2, var5);
      var7.zzam(var5.zznk);

      while(var2 < var3) {
         int var6 = zza(var1, var2, var5);
         if (var0 != var5.zznk) {
            break;
         }

         var2 = zza(var1, var6, var5);
         var7.zzam(var5.zznk);
      }

      return var2;
   }

   static int zza(int var0, byte[] var1, int var2, int var3, zzmy var4, zziz var5) throws zzkq {
      if (var0 >>> 3 == 0) {
         throw zzkq.zzdk();
      } else {
         int var6 = var0 & 7;
         if (var6 == 0) {
            var2 = zzb(var1, var2, var5);
            var4.zzb(var0, var5.zznl);
            return var2;
         } else if (var6 == 1) {
            var4.zzb(var0, zzb(var1, var2));
            return var2 + 8;
         } else if (var6 == 2) {
            var2 = zza(var1, var2, var5);
            var3 = var5.zznk;
            if (var3 >= 0) {
               if (var3 <= var1.length - var2) {
                  if (var3 == 0) {
                     var4.zzb(var0, zzjc.zznq);
                  } else {
                     var4.zzb(var0, zzjc.zzb(var1, var2, var3));
                  }

                  return var2 + var3;
               } else {
                  throw zzkq.zzdi();
               }
            } else {
               throw zzkq.zzdj();
            }
         } else if (var6 != 3) {
            if (var6 == 5) {
               var4.zzb(var0, zza(var1, var2));
               return var2 + 4;
            } else {
               throw zzkq.zzdk();
            }
         } else {
            zzmy var7 = zzmy.zzfb();
            int var8 = var0 & -8 | 4;
            var6 = 0;

            int var9;
            while(true) {
               var9 = var6;
               var6 = var2;
               if (var2 >= var3) {
                  break;
               }

               var9 = zza(var1, var2, var5);
               var6 = var5.zznk;
               var2 = var6;
               if (var6 == var8) {
                  var6 = var9;
                  var9 = var2;
                  break;
               }

               var2 = zza(var6, var1, var9, var3, var7, var5);
            }

            if (var6 <= var3 && var9 == var8) {
               var4.zzb(var0, var7);
               return var6;
            } else {
               throw zzkq.zzdm();
            }
         }
      }
   }

   static int zza(int var0, byte[] var1, int var2, zziz var3) {
      int var4 = var0 & 127;
      var0 = var2 + 1;
      byte var7 = var1[var2];
      if (var7 >= 0) {
         var3.zznk = var4 | var7 << 7;
         return var0;
      } else {
         var2 = var4 | (var7 & 127) << 7;
         var4 = var0 + 1;
         byte var6 = var1[var0];
         if (var6 >= 0) {
            var3.zznk = var2 | var6 << 14;
            return var4;
         } else {
            var0 = var2 | (var6 & 127) << 14;
            var2 = var4 + 1;
            byte var8 = var1[var4];
            if (var8 >= 0) {
               var3.zznk = var0 | var8 << 21;
               return var2;
            } else {
               var4 = var0 | (var8 & 127) << 21;
               var0 = var2 + 1;
               byte var5 = var1[var2];
               if (var5 >= 0) {
                  var3.zznk = var4 | var5 << 28;
                  return var0;
               } else {
                  while(true) {
                     var2 = var0 + 1;
                     if (var1[var0] >= 0) {
                        var3.zznk = var4 | (var5 & 127) << 28;
                        return var2;
                     }

                     var0 = var2;
                  }
               }
            }
         }
      }
   }

   static int zza(zzmf<?> var0, int var1, byte[] var2, int var3, int var4, zzkp<?> var5, zziz var6) throws IOException {
      var3 = zza(var0, var2, var3, var4, var6);
      var5.add(var6.zznm);

      while(var3 < var4) {
         int var7 = zza(var2, var3, var6);
         if (var1 != var6.zznk) {
            break;
         }

         var3 = zza(var0, var2, var7, var4, var6);
         var5.add(var6.zznm);
      }

      return var3;
   }

   static int zza(zzmf var0, byte[] var1, int var2, int var3, int var4, zziz var5) throws IOException {
      zzlu var7 = (zzlu)var0;
      Object var6 = var7.newInstance();
      var2 = var7.zza(var6, var1, var2, var3, var4, var5);
      var7.zzd(var6);
      var5.zznm = var6;
      return var2;
   }

   static int zza(zzmf var0, byte[] var1, int var2, int var3, zziz var4) throws IOException {
      int var5 = var2 + 1;
      byte var6 = var1[var2];
      int var7 = var5;
      var2 = var6;
      if (var6 < 0) {
         var7 = zza(var6, var1, var5, var4);
         var2 = var4.zznk;
      }

      if (var2 >= 0 && var2 <= var3 - var7) {
         Object var8 = var0.newInstance();
         var2 += var7;
         var0.zza(var8, var1, var7, var2, var4);
         var0.zzd(var8);
         var4.zznm = var8;
         return var2;
      } else {
         throw zzkq.zzdi();
      }
   }

   static int zza(byte[] var0, int var1) {
      byte var2 = var0[var1];
      byte var3 = var0[var1 + 1];
      byte var4 = var0[var1 + 2];
      return (var0[var1 + 3] & 255) << 24 | var2 & 255 | (var3 & 255) << 8 | (var4 & 255) << 16;
   }

   static int zza(byte[] var0, int var1, zziz var2) {
      int var3 = var1 + 1;
      byte var4 = var0[var1];
      if (var4 >= 0) {
         var2.zznk = var4;
         return var3;
      } else {
         return zza(var4, var0, var3, var2);
      }
   }

   static int zza(byte[] var0, int var1, zzkp<?> var2, zziz var3) throws IOException {
      zzkl var5 = (zzkl)var2;
      var1 = zza(var0, var1, var3);
      int var4 = var3.zznk + var1;

      while(var1 < var4) {
         var1 = zza(var0, var1, var3);
         var5.zzam(var3.zznk);
      }

      if (var1 == var4) {
         return var1;
      } else {
         throw zzkq.zzdi();
      }
   }

   static int zzb(byte[] var0, int var1, zziz var2) {
      int var3 = var1 + 1;
      long var4 = (long)var0[var1];
      if (var4 >= 0L) {
         var2.zznl = var4;
         return var3;
      } else {
         var1 = var3 + 1;
         byte var6 = var0[var3];
         var4 = var4 & 127L | (long)(var6 & 127) << 7;

         for(var3 = 7; var6 < 0; ++var1) {
            var6 = var0[var1];
            var3 += 7;
            var4 |= (long)(var6 & 127) << var3;
         }

         var2.zznl = var4;
         return var1;
      }
   }

   static long zzb(byte[] var0, int var1) {
      long var2 = (long)var0[var1];
      long var4 = (long)var0[var1 + 1];
      long var6 = (long)var0[var1 + 2];
      long var8 = (long)var0[var1 + 3];
      long var10 = (long)var0[var1 + 4];
      long var12 = (long)var0[var1 + 5];
      long var14 = (long)var0[var1 + 6];
      return ((long)var0[var1 + 7] & 255L) << 56 | var2 & 255L | (var4 & 255L) << 8 | (var6 & 255L) << 16 | (var8 & 255L) << 24 | (var10 & 255L) << 32 | (var12 & 255L) << 40 | (var14 & 255L) << 48;
   }

   static double zzc(byte[] var0, int var1) {
      return Double.longBitsToDouble(zzb(var0, var1));
   }

   static int zzc(byte[] var0, int var1, zziz var2) throws zzkq {
      int var3 = zza(var0, var1, var2);
      var1 = var2.zznk;
      if (var1 >= 0) {
         if (var1 == 0) {
            var2.zznm = "";
            return var3;
         } else {
            var2.zznm = new String(var0, var3, var1, zzkm.UTF_8);
            return var3 + var1;
         }
      } else {
         throw zzkq.zzdj();
      }
   }

   static float zzd(byte[] var0, int var1) {
      return Float.intBitsToFloat(zza(var0, var1));
   }

   static int zzd(byte[] var0, int var1, zziz var2) throws zzkq {
      var1 = zza(var0, var1, var2);
      int var3 = var2.zznk;
      if (var3 >= 0) {
         if (var3 == 0) {
            var2.zznm = "";
            return var1;
         } else {
            var2.zznm = zznf.zzg(var0, var1, var3);
            return var1 + var3;
         }
      } else {
         throw zzkq.zzdj();
      }
   }

   static int zze(byte[] var0, int var1, zziz var2) throws zzkq {
      int var3 = zza(var0, var1, var2);
      var1 = var2.zznk;
      if (var1 >= 0) {
         if (var1 <= var0.length - var3) {
            if (var1 == 0) {
               var2.zznm = zzjc.zznq;
               return var3;
            } else {
               var2.zznm = zzjc.zzb(var0, var3, var1);
               return var3 + var1;
            }
         } else {
            throw zzkq.zzdi();
         }
      } else {
         throw zzkq.zzdj();
      }
   }
}
