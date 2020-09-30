package com.google.android.gms.internal.drive;

final class zznk extends zznh {
   private static int zza(byte[] var0, int var1, long var2, int var4) {
      if (var4 != 0) {
         if (var4 != 1) {
            if (var4 == 2) {
               return zznf.zzd(var1, zznd.zza(var0, var2), zznd.zza(var0, var2 + 1L));
            } else {
               throw new AssertionError();
            }
         } else {
            return zznf.zzs(var1, zznd.zza(var0, var2));
         }
      } else {
         return zznf.zzaz(var1);
      }
   }

   final int zzb(int var1, byte[] var2, int var3, int var4) {
      if ((var3 | var4 | var2.length - var4) < 0) {
         throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", var2.length, var3, var4));
      } else {
         long var5 = (long)var3;
         var3 = (int)((long)var4 - var5);
         long var7;
         if (var3 < 16) {
            var1 = 0;
         } else {
            var7 = var5;
            var1 = 0;

            while(true) {
               if (var1 >= var3) {
                  var1 = var3;
                  break;
               }

               if (zznd.zza(var2, var7) < 0) {
                  break;
               }

               ++var1;
               ++var7;
            }
         }

         var3 -= var1;
         var7 = var5 + (long)var1;
         var1 = var3;

         while(true) {
            byte var12 = 0;
            var3 = var1;
            byte var11 = var12;

            while(true) {
               var5 = var7;
               if (var3 <= 0) {
                  break;
               }

               var5 = var7 + 1L;
               var11 = zznd.zza(var2, var7);
               if (var11 < 0) {
                  break;
               }

               --var3;
               var7 = var5;
            }

            if (var3 == 0) {
               return 0;
            }

            --var3;
            if (var11 < -32) {
               if (var3 == 0) {
                  return var11;
               }

               --var3;
               if (var11 >= -62) {
                  var7 = var5 + 1L;
                  var1 = var3;
                  if (zznd.zza(var2, var5) <= -65) {
                     continue;
                  }
               }

               return -1;
            } else {
               byte var13;
               if (var11 < -16) {
                  if (var3 < 2) {
                     return zza(var2, var11, var5, var3);
                  }

                  var3 -= 2;
                  long var9 = var5 + 1L;
                  var13 = zznd.zza(var2, var5);
                  if (var13 <= -65 && (var11 != -32 || var13 >= -96) && (var11 != -19 || var13 < -96)) {
                     var7 = var9 + 1L;
                     var1 = var3;
                     if (zznd.zza(var2, var9) <= -65) {
                        continue;
                     }
                  }

                  return -1;
               } else {
                  if (var3 < 3) {
                     return zza(var2, var11, var5, var3);
                  }

                  var3 -= 3;
                  var7 = var5 + 1L;
                  var13 = zznd.zza(var2, var5);
                  if (var13 <= -65 && (var11 << 28) + var13 + 112 >> 30 == 0) {
                     var5 = var7 + 1L;
                     if (zznd.zza(var2, var7) <= -65) {
                        var7 = var5 + 1L;
                        var1 = var3;
                        if (zznd.zza(var2, var5) <= -65) {
                           continue;
                        }
                     }
                  }

                  return -1;
               }
            }
         }
      }
   }

   final int zzb(CharSequence var1, byte[] var2, int var3, int var4) {
      long var5 = (long)var3;
      long var7 = (long)var4 + var5;
      int var9 = var1.length();
      char var17;
      StringBuilder var18;
      if (var9 <= var4 && var2.length - var4 >= var3) {
         var4 = 0;

         long var10;
         while(true) {
            var10 = 1L;
            if (var4 >= var9) {
               break;
            }

            char var19 = var1.charAt(var4);
            if (var19 >= 128) {
               break;
            }

            zznd.zza(var2, var5, (byte)var19);
            ++var4;
            ++var5;
         }

         var3 = var4;
         long var12 = var5;
         if (var4 == var9) {
            return (int)var5;
         } else {
            while(true) {
               if (var3 >= var9) {
                  return (int)var12;
               }

               char var14 = var1.charAt(var3);
               if (var14 < 128 && var12 < var7) {
                  zznd.zza(var2, var12, (byte)var14);
                  var5 = var12 + var10;
                  var10 = var10;
               } else if (var14 < 2048 && var12 <= var7 - 2L) {
                  var5 = var12 + var10;
                  zznd.zza(var2, var12, (byte)(var14 >>> 6 | 960));
                  zznd.zza(var2, var5, (byte)(var14 & 63 | 128));
                  var5 += var10;
               } else if ((var14 < '\ud800' || '\udfff' < var14) && var12 <= var7 - 3L) {
                  var5 = var12 + var10;
                  zznd.zza(var2, var12, (byte)(var14 >>> 12 | 480));
                  var10 += var5;
                  zznd.zza(var2, var5, (byte)(var14 >>> 6 & 63 | 128));
                  zznd.zza(var2, var10, (byte)(var14 & 63 | 128));
                  var5 = var10 + 1L;
                  var10 = 1L;
               } else {
                  if (var12 > var7 - 4L) {
                     if ('\ud800' <= var14 && var14 <= '\udfff') {
                        var4 = var3 + 1;
                        if (var4 == var9 || !Character.isSurrogatePair(var14, var1.charAt(var4))) {
                           throw new zznj(var3, var9);
                        }
                     }

                     var18 = new StringBuilder(46);
                     var18.append("Failed writing ");
                     var18.append(var14);
                     var18.append(" at index ");
                     var18.append(var12);
                     throw new ArrayIndexOutOfBoundsException(var18.toString());
                  }

                  var4 = var3 + 1;
                  if (var4 == var9) {
                     break;
                  }

                  var17 = var1.charAt(var4);
                  if (!Character.isSurrogatePair(var14, var17)) {
                     var3 = var4;
                     break;
                  }

                  var3 = Character.toCodePoint(var14, var17);
                  var10 = var12 + 1L;
                  zznd.zza(var2, var12, (byte)(var3 >>> 18 | 240));
                  var5 = var10 + 1L;
                  zznd.zza(var2, var10, (byte)(var3 >>> 12 & 63 | 128));
                  var12 = var5 + 1L;
                  zznd.zza(var2, var5, (byte)(var3 >>> 6 & 63 | 128));
                  var10 = 1L;
                  var5 = var12 + 1L;
                  zznd.zza(var2, var12, (byte)(var3 & 63 | 128));
                  var3 = var4;
               }

               ++var3;
               var12 = var5;
            }

            throw new zznj(var3 - 1, var9);
         }
      } else {
         var17 = var1.charAt(var9 - 1);
         var18 = new StringBuilder(37);
         var18.append("Failed writing ");
         var18.append(var17);
         var18.append(" at index ");
         var18.append(var3 + var4);
         throw new ArrayIndexOutOfBoundsException(var18.toString());
      }
   }

   final String zzg(byte[] var1, int var2, int var3) throws zzkq {
      if ((var2 | var3 | var1.length - var2 - var3) < 0) {
         throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", var1.length, var2, var3));
      } else {
         int var4 = var2 + var3;
         char[] var5 = new char[var3];

         byte var6;
         for(var3 = 0; var2 < var4; ++var3) {
            var6 = zznd.zza(var1, (long)var2);
            if (!zzng.zzh(var6)) {
               break;
            }

            ++var2;
            zzng.zzb(var6, var5, var3);
         }

         int var7 = var3;
         var3 = var2;
         var2 = var7;

         while(true) {
            while(var3 < var4) {
               var7 = var3 + 1;
               var6 = zznd.zza(var1, (long)var3);
               if (zzng.zzh(var6)) {
                  var3 = var2 + 1;
                  zzng.zzb(var6, var5, var2);
                  var2 = var3;

                  for(var3 = var7; var3 < var4; ++var2) {
                     var6 = zznd.zza(var1, (long)var3);
                     if (!zzng.zzh(var6)) {
                        break;
                     }

                     ++var3;
                     zzng.zzb(var6, var5, var2);
                  }
               } else if (zzng.zzi(var6)) {
                  if (var7 >= var4) {
                     throw zzkq.zzdn();
                  }

                  zzng.zzb(var6, zznd.zza(var1, (long)var7), var5, var2);
                  var3 = var7 + 1;
                  ++var2;
               } else if (zzng.zzj(var6)) {
                  if (var7 >= var4 - 1) {
                     throw zzkq.zzdn();
                  }

                  var3 = var7 + 1;
                  zzng.zzb(var6, zznd.zza(var1, (long)var7), zznd.zza(var1, (long)var3), var5, var2);
                  ++var3;
                  ++var2;
               } else {
                  if (var7 >= var4 - 2) {
                     throw zzkq.zzdn();
                  }

                  var3 = var7 + 1;
                  byte var8 = zznd.zza(var1, (long)var7);
                  var7 = var3 + 1;
                  zzng.zzb(var6, var8, zznd.zza(var1, (long)var3), zznd.zza(var1, (long)var7), var5, var2);
                  var3 = var7 + 1;
                  var2 = var2 + 1 + 1;
               }
            }

            return new String(var5, 0, var2);
         }
      }
   }
}
