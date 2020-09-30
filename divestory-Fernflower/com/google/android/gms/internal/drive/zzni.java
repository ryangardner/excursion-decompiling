package com.google.android.gms.internal.drive;

final class zzni extends zznh {
   final int zzb(int var1, byte[] var2, int var3, int var4) {
      while(var3 < var4 && var2[var3] >= 0) {
         ++var3;
      }

      var1 = var3;
      if (var3 >= var4) {
         return 0;
      } else {
         while(true) {
            byte var5;
            do {
               if (var1 >= var4) {
                  return 0;
               }

               var3 = var1 + 1;
               var5 = var2[var1];
               var1 = var3;
            } while(var5 >= 0);

            if (var5 < -32) {
               if (var3 >= var4) {
                  return var5;
               }

               if (var5 >= -62) {
                  var1 = var3 + 1;
                  if (var2[var3] <= -65) {
                     continue;
                  }
               }

               return -1;
            } else if (var5 < -16) {
               if (var3 >= var4 - 1) {
                  return zznf.zzh(var2, var3, var4);
               }

               int var6 = var3 + 1;
               byte var7 = var2[var3];
               if (var7 <= -65 && (var5 != -32 || var7 >= -96) && (var5 != -19 || var7 < -96)) {
                  var1 = var6 + 1;
                  if (var2[var6] <= -65) {
                     continue;
                  }
               }

               return -1;
            } else {
               if (var3 >= var4 - 2) {
                  return zznf.zzh(var2, var3, var4);
               }

               var1 = var3 + 1;
               byte var8 = var2[var3];
               if (var8 <= -65 && (var5 << 28) + var8 + 112 >> 30 == 0) {
                  var3 = var1 + 1;
                  if (var2[var1] <= -65) {
                     var1 = var3 + 1;
                     if (var2[var3] <= -65) {
                        continue;
                     }
                  }
               }

               return -1;
            }
         }
      }
   }

   final int zzb(CharSequence var1, byte[] var2, int var3, int var4) {
      int var5 = var1.length();
      int var6 = var4 + var3;

      int var7;
      for(var4 = 0; var4 < var5; ++var4) {
         var7 = var4 + var3;
         if (var7 >= var6) {
            break;
         }

         char var8 = var1.charAt(var4);
         if (var8 >= 128) {
            break;
         }

         var2[var7] = (byte)((byte)var8);
      }

      if (var4 == var5) {
         return var3 + var5;
      } else {
         int var12 = var3 + var4;
         var3 = var4;

         while(true) {
            if (var3 >= var5) {
               return var12;
            }

            char var9 = var1.charAt(var3);
            if (var9 < 128 && var12 < var6) {
               var4 = var12 + 1;
               var2[var12] = (byte)((byte)var9);
            } else if (var9 < 2048 && var12 <= var6 - 2) {
               var7 = var12 + 1;
               var2[var12] = (byte)((byte)(var9 >>> 6 | 960));
               var4 = var7 + 1;
               var2[var7] = (byte)((byte)(var9 & 63 | 128));
            } else if ((var9 < '\ud800' || '\udfff' < var9) && var12 <= var6 - 3) {
               var4 = var12 + 1;
               var2[var12] = (byte)((byte)(var9 >>> 12 | 480));
               var12 = var4 + 1;
               var2[var4] = (byte)((byte)(var9 >>> 6 & 63 | 128));
               var4 = var12 + 1;
               var2[var12] = (byte)((byte)(var9 & 63 | 128));
            } else {
               if (var12 > var6 - 4) {
                  if ('\ud800' <= var9 && var9 <= '\udfff') {
                     var4 = var3 + 1;
                     if (var4 == var1.length() || !Character.isSurrogatePair(var9, var1.charAt(var4))) {
                        throw new zznj(var3, var5);
                     }
                  }

                  StringBuilder var11 = new StringBuilder(37);
                  var11.append("Failed writing ");
                  var11.append(var9);
                  var11.append(" at index ");
                  var11.append(var12);
                  throw new ArrayIndexOutOfBoundsException(var11.toString());
               }

               var4 = var3 + 1;
               if (var4 == var1.length()) {
                  break;
               }

               char var10 = var1.charAt(var4);
               if (!Character.isSurrogatePair(var9, var10)) {
                  var3 = var4;
                  break;
               }

               var3 = Character.toCodePoint(var9, var10);
               var7 = var12 + 1;
               var2[var12] = (byte)((byte)(var3 >>> 18 | 240));
               var12 = var7 + 1;
               var2[var7] = (byte)((byte)(var3 >>> 12 & 63 | 128));
               var7 = var12 + 1;
               var2[var12] = (byte)((byte)(var3 >>> 6 & 63 | 128));
               var12 = var7 + 1;
               var2[var7] = (byte)((byte)(var3 & 63 | 128));
               var3 = var4;
               var4 = var12;
            }

            ++var3;
            var12 = var4;
         }

         throw new zznj(var3 - 1, var5);
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
            var6 = var1[var2];
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
               byte var8 = var1[var3];
               if (zzng.zzh(var8)) {
                  var3 = var2 + 1;
                  zzng.zzb(var8, var5, var2);
                  var2 = var3;

                  for(var3 = var7; var3 < var4; ++var2) {
                     var6 = var1[var3];
                     if (!zzng.zzh(var6)) {
                        break;
                     }

                     ++var3;
                     zzng.zzb(var6, var5, var2);
                  }
               } else if (zzng.zzi(var8)) {
                  if (var7 >= var4) {
                     throw zzkq.zzdn();
                  }

                  zzng.zzb(var8, var1[var7], var5, var2);
                  var3 = var7 + 1;
                  ++var2;
               } else if (zzng.zzj(var8)) {
                  if (var7 >= var4 - 1) {
                     throw zzkq.zzdn();
                  }

                  var3 = var7 + 1;
                  zzng.zzb(var8, var1[var7], var1[var3], var5, var2);
                  ++var3;
                  ++var2;
               } else {
                  if (var7 >= var4 - 2) {
                     throw zzkq.zzdn();
                  }

                  var3 = var7 + 1;
                  var6 = var1[var7];
                  var7 = var3 + 1;
                  zzng.zzb(var8, var6, var1[var3], var1[var7], var5, var2);
                  var3 = var7 + 1;
                  var2 = var2 + 1 + 1;
               }
            }

            return new String(var5, 0, var2);
         }
      }
   }
}
