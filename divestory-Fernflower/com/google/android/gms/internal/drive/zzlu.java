package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import sun.misc.Unsafe;

final class zzlu<T> implements zzmf<T> {
   private static final int[] zzub = new int[0];
   private static final Unsafe zzuc = zznd.zzff();
   private final int[] zzud;
   private final Object[] zzue;
   private final int zzuf;
   private final int zzug;
   private final zzlq zzuh;
   private final boolean zzui;
   private final boolean zzuj;
   private final boolean zzuk;
   private final boolean zzul;
   private final int[] zzum;
   private final int zzun;
   private final int zzuo;
   private final zzly zzup;
   private final zzla zzuq;
   private final zzmx<?, ?> zzur;
   private final zzjy<?> zzus;
   private final zzll zzut;

   private zzlu(int[] var1, Object[] var2, int var3, int var4, zzlq var5, boolean var6, boolean var7, int[] var8, int var9, int var10, zzly var11, zzla var12, zzmx<?, ?> var13, zzjy<?> var14, zzll var15) {
      this.zzud = var1;
      this.zzue = var2;
      this.zzuf = var3;
      this.zzug = var4;
      this.zzuj = var5 instanceof zzkk;
      this.zzuk = var6;
      if (var14 != null && var14.zze(var5)) {
         var6 = true;
      } else {
         var6 = false;
      }

      this.zzui = var6;
      this.zzul = false;
      this.zzum = var8;
      this.zzun = var9;
      this.zzuo = var10;
      this.zzup = var11;
      this.zzuq = var12;
      this.zzur = var13;
      this.zzus = var14;
      this.zzuh = var5;
      this.zzut = var15;
   }

   private static <UT, UB> int zza(zzmx<UT, UB> var0, T var1) {
      return var0.zzn(var0.zzr(var1));
   }

   private final int zza(T var1, byte[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, zziz var13) throws IOException {
      Unsafe var14;
      long var15;
      label131: {
         label130: {
            var14 = zzuc;
            var15 = (long)(this.zzud[var12 + 2] & 1048575);
            Object var18;
            switch(var9) {
            case 51:
               if (var7 != 1) {
                  return var3;
               }

               var14.putObject(var1, var10, zziy.zzc(var2, var3));
               break;
            case 52:
               if (var7 != 5) {
                  return var3;
               }

               var14.putObject(var1, var10, zziy.zzd(var2, var3));
               break label130;
            case 53:
            case 54:
               if (var7 != 0) {
                  return var3;
               }

               var3 = zziy.zzb(var2, var3, var13);
               var14.putObject(var1, var10, var13.zznl);
               break label131;
            case 55:
            case 62:
               if (var7 != 0) {
                  return var3;
               }

               var3 = zziy.zza(var2, var3, var13);
               var14.putObject(var1, var10, var13.zznk);
               break label131;
            case 56:
            case 65:
               if (var7 != 1) {
                  return var3;
               }

               var14.putObject(var1, var10, zziy.zzb(var2, var3));
               break;
            case 57:
            case 64:
               if (var7 != 5) {
                  return var3;
               }

               var14.putObject(var1, var10, zziy.zza(var2, var3));
               break label130;
            case 58:
               if (var7 != 0) {
                  return var3;
               }

               var3 = zziy.zzb(var2, var3, var13);
               boolean var17;
               if (var13.zznl != 0L) {
                  var17 = true;
               } else {
                  var17 = false;
               }

               var14.putObject(var1, var10, var17);
               break label131;
            case 59:
               if (var7 == 2) {
                  var3 = zziy.zza(var2, var3, var13);
                  var4 = var13.zznk;
                  if (var4 == 0) {
                     var14.putObject(var1, var10, "");
                  } else {
                     if ((var8 & 536870912) != 0 && !zznf.zze(var2, var3, var3 + var4)) {
                        throw zzkq.zzdn();
                     }

                     var14.putObject(var1, var10, new String(var2, var3, var4, zzkm.UTF_8));
                     var3 += var4;
                  }

                  var14.putInt(var1, var15, var6);
               }

               return var3;
            case 60:
               if (var7 == 2) {
                  var3 = zziy.zza(this.zzap(var12), var2, var3, var4, var13);
                  if (var14.getInt(var1, var15) == var6) {
                     var18 = var14.getObject(var1, var10);
                  } else {
                     var18 = null;
                  }

                  if (var18 == null) {
                     var14.putObject(var1, var10, var13.zznm);
                  } else {
                     var14.putObject(var1, var10, zzkm.zza(var18, var13.zznm));
                  }

                  var14.putInt(var1, var15, var6);
               }

               return var3;
            case 61:
               if (var7 != 2) {
                  return var3;
               }

               var3 = zziy.zze(var2, var3, var13);
               var14.putObject(var1, var10, var13.zznm);
               break label131;
            case 63:
               if (var7 != 0) {
                  return var3;
               }

               var3 = zziy.zza(var2, var3, var13);
               var4 = var13.zznk;
               zzko var19 = this.zzar(var12);
               if (var19 != null && !var19.zzan(var4)) {
                  zzo(var1).zzb(var5, (long)var4);
                  return var3;
               }

               var14.putObject(var1, var10, var4);
               break label131;
            case 66:
               if (var7 != 0) {
                  return var3;
               }

               var3 = zziy.zza(var2, var3, var13);
               var14.putObject(var1, var10, zzjo.zzw(var13.zznk));
               break label131;
            case 67:
               if (var7 != 0) {
                  return var3;
               }

               var3 = zziy.zzb(var2, var3, var13);
               var14.putObject(var1, var10, zzjo.zzk(var13.zznl));
               break label131;
            case 68:
               if (var7 == 3) {
                  var3 = zziy.zza(this.zzap(var12), var2, var3, var4, var5 & -8 | 4, var13);
                  if (var14.getInt(var1, var15) == var6) {
                     var18 = var14.getObject(var1, var10);
                  } else {
                     var18 = null;
                  }

                  if (var18 == null) {
                     var14.putObject(var1, var10, var13.zznm);
                  } else {
                     var14.putObject(var1, var10, zzkm.zza(var18, var13.zznm));
                  }
                  break label131;
               }

               return var3;
            default:
               return var3;
            }

            var3 += 8;
            break label131;
         }

         var3 += 4;
      }

      var14.putInt(var1, var15, var6);
      return var3;
   }

   private final int zza(T var1, byte[] var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9, int var11, long var12, zziz var14) throws IOException {
      int var15 = var3;
      zzkp var16 = (zzkp)zzuc.getObject(var1, var12);
      zzkp var17 = var16;
      if (!var16.zzbo()) {
         int var18 = var16.size();
         if (var18 == 0) {
            var18 = 10;
         } else {
            var18 <<= 1;
         }

         var17 = var16.zzr(var18);
         zzuc.putObject(var1, var12, var17);
      }

      label513: {
         zzle var21;
         zzkl var23;
         switch(var11) {
         case 18:
         case 35:
            zzju var27;
            if (var7 == 2) {
               var27 = (zzju)var17;
               var3 = zziy.zza(var2, var3, var14);

               for(var4 = var14.zznk + var3; var3 < var4; var3 += 8) {
                  var27.zzc(zziy.zzc(var2, var3));
               }

               if (var3 != var4) {
                  throw zzkq.zzdi();
               }

               return var3;
            }

            var11 = var3;
            if (var7 == 1) {
               var27 = (zzju)var17;
               var27.zzc(zziy.zzc(var2, var3));

               while(true) {
                  var6 = var15 + 8;
                  var3 = var6;
                  if (var6 >= var4) {
                     return var3;
                  }

                  var15 = zziy.zza(var2, var6, var14);
                  var3 = var6;
                  if (var5 != var14.zznk) {
                     return var3;
                  }

                  var27.zzc(zziy.zzc(var2, var15));
               }
            }
            break label513;
         case 19:
         case 36:
            zzkh var26;
            if (var7 == 2) {
               var26 = (zzkh)var17;
               var3 = zziy.zza(var2, var3, var14);

               for(var4 = var14.zznk + var3; var3 < var4; var3 += 4) {
                  var26.zzc(zziy.zzd(var2, var3));
               }

               if (var3 != var4) {
                  throw zzkq.zzdi();
               }

               return var3;
            }

            var11 = var3;
            if (var7 == 5) {
               var26 = (zzkh)var17;
               var26.zzc(zziy.zzd(var2, var3));

               while(true) {
                  var6 = var15 + 4;
                  var3 = var6;
                  if (var6 >= var4) {
                     return var3;
                  }

                  var15 = zziy.zza(var2, var6, var14);
                  var3 = var6;
                  if (var5 != var14.zznk) {
                     return var3;
                  }

                  var26.zzc(zziy.zzd(var2, var15));
               }
            }
            break label513;
         case 20:
         case 21:
         case 37:
         case 38:
            if (var7 == 2) {
               var21 = (zzle)var17;
               var3 = zziy.zza(var2, var3, var14);
               var4 = var14.zznk + var3;

               while(var3 < var4) {
                  var3 = zziy.zzb(var2, var3, var14);
                  var21.zzv(var14.zznl);
               }

               if (var3 != var4) {
                  throw zzkq.zzdi();
               }

               return var3;
            }

            var11 = var3;
            if (var7 == 0) {
               var21 = (zzle)var17;
               var6 = zziy.zzb(var2, var3, var14);
               var21.zzv(var14.zznl);

               while(true) {
                  var3 = var6;
                  if (var6 >= var4) {
                     return var3;
                  }

                  var7 = zziy.zza(var2, var6, var14);
                  var3 = var6;
                  if (var5 != var14.zznk) {
                     return var3;
                  }

                  var6 = zziy.zzb(var2, var7, var14);
                  var21.zzv(var14.zznl);
               }
            }
            break label513;
         case 22:
         case 29:
         case 39:
         case 43:
            if (var7 == 2) {
               var3 = zziy.zza(var2, var3, var17, var14);
               return var3;
            }

            var11 = var3;
            if (var7 == 0) {
               var3 = zziy.zza(var5, var2, var3, var4, var17, var14);
               return var3;
            }
            break label513;
         case 23:
         case 32:
         case 40:
         case 46:
            if (var7 == 2) {
               var21 = (zzle)var17;
               var3 = zziy.zza(var2, var3, var14);

               for(var4 = var14.zznk + var3; var3 < var4; var3 += 8) {
                  var21.zzv(zziy.zzb(var2, var3));
               }

               if (var3 != var4) {
                  throw zzkq.zzdi();
               }

               return var3;
            }

            var11 = var3;
            if (var7 == 1) {
               var21 = (zzle)var17;
               var21.zzv(zziy.zzb(var2, var3));

               while(true) {
                  var6 = var15 + 8;
                  var3 = var6;
                  if (var6 >= var4) {
                     return var3;
                  }

                  var15 = zziy.zza(var2, var6, var14);
                  var3 = var6;
                  if (var5 != var14.zznk) {
                     return var3;
                  }

                  var21.zzv(zziy.zzb(var2, var15));
               }
            }
            break label513;
         case 24:
         case 31:
         case 41:
         case 45:
            if (var7 == 2) {
               var23 = (zzkl)var17;
               var3 = zziy.zza(var2, var3, var14);

               for(var4 = var14.zznk + var3; var3 < var4; var3 += 4) {
                  var23.zzam(zziy.zza(var2, var3));
               }

               if (var3 != var4) {
                  throw zzkq.zzdi();
               }

               return var3;
            }

            var11 = var3;
            if (var7 == 5) {
               var23 = (zzkl)var17;
               var23.zzam(zziy.zza(var2, var3));

               while(true) {
                  var6 = var15 + 4;
                  var3 = var6;
                  if (var6 >= var4) {
                     return var3;
                  }

                  var15 = zziy.zza(var2, var6, var14);
                  var3 = var6;
                  if (var5 != var14.zznk) {
                     return var3;
                  }

                  var23.zzam(zziy.zza(var2, var15));
               }
            }
            break label513;
         case 25:
         case 42:
            boolean var19;
            zzja var25;
            if (var7 != 2) {
               var11 = var3;
               if (var7 == 0) {
                  var25 = (zzja)var17;
                  var3 = zziy.zzb(var2, var3, var14);
                  if (var14.zznl != 0L) {
                     var19 = true;
                  } else {
                     var19 = false;
                  }

                  var25.addBoolean(var19);

                  while(true) {
                     var11 = var3;
                     if (var3 >= var4) {
                        break label513;
                     }

                     var6 = zziy.zza(var2, var3, var14);
                     var11 = var3;
                     if (var5 != var14.zznk) {
                        break label513;
                     }

                     var3 = zziy.zzb(var2, var6, var14);
                     if (var14.zznl != 0L) {
                        var19 = true;
                     } else {
                        var19 = false;
                     }

                     var25.addBoolean(var19);
                  }
               }
               break label513;
            }

            var25 = (zzja)var17;
            var3 = zziy.zza(var2, var3, var14);

            for(var4 = var14.zznk + var3; var3 < var4; var25.addBoolean(var19)) {
               var3 = zziy.zzb(var2, var3, var14);
               if (var14.zznl != 0L) {
                  var19 = true;
               } else {
                  var19 = false;
               }
            }

            if (var3 != var4) {
               throw zzkq.zzdi();
            }

            var4 = var3;
            break;
         case 26:
            var11 = var3;
            if (var7 == 2) {
               if ((var9 & 536870912L) == 0L) {
                  var3 = zziy.zza(var2, var3, var14);
                  var6 = var14.zznk;
                  if (var6 < 0) {
                     throw zzkq.zzdj();
                  }

                  if (var6 == 0) {
                     var17.add("");
                  } else {
                     var17.add(new String(var2, var3, var6, zzkm.UTF_8));
                     var3 += var6;
                  }

                  while(true) {
                     var11 = var3;
                     if (var3 >= var4) {
                        break label513;
                     }

                     var6 = zziy.zza(var2, var3, var14);
                     var11 = var3;
                     if (var5 != var14.zznk) {
                        break label513;
                     }

                     var3 = zziy.zza(var2, var6, var14);
                     var6 = var14.zznk;
                     if (var6 < 0) {
                        throw zzkq.zzdj();
                     }

                     if (var6 == 0) {
                        var17.add("");
                     } else {
                        var17.add(new String(var2, var3, var6, zzkm.UTF_8));
                        var3 += var6;
                     }
                  }
               } else {
                  var6 = zziy.zza(var2, var3, var14);
                  var7 = var14.zznk;
                  if (var7 < 0) {
                     throw zzkq.zzdj();
                  }

                  if (var7 == 0) {
                     var17.add("");
                     var3 = var6;
                  } else {
                     var3 = var6 + var7;
                     if (!zznf.zze(var2, var6, var3)) {
                        throw zzkq.zzdn();
                     }

                     var17.add(new String(var2, var6, var7, zzkm.UTF_8));
                  }

                  while(true) {
                     var11 = var3;
                     if (var3 >= var4) {
                        break label513;
                     }

                     var6 = zziy.zza(var2, var3, var14);
                     var11 = var3;
                     if (var5 != var14.zznk) {
                        break label513;
                     }

                     var6 = zziy.zza(var2, var6, var14);
                     var7 = var14.zznk;
                     if (var7 < 0) {
                        throw zzkq.zzdj();
                     }

                     if (var7 == 0) {
                        var17.add("");
                        var3 = var6;
                     } else {
                        var3 = var6 + var7;
                        if (!zznf.zze(var2, var6, var3)) {
                           throw zzkq.zzdn();
                        }

                        var17.add(new String(var2, var6, var7, zzkm.UTF_8));
                     }
                  }
               }
            }
            break label513;
         case 27:
            var11 = var3;
            if (var7 == 2) {
               var3 = zziy.zza(this.zzap(var8), var5, var2, var3, var4, var17, var14);
               return var3;
            }
            break label513;
         case 28:
            var11 = var3;
            if (var7 == 2) {
               var6 = zziy.zza(var2, var3, var14);
               var3 = var14.zznk;
               if (var3 >= 0) {
                  if (var3 > var2.length - var6) {
                     throw zzkq.zzdi();
                  }

                  if (var3 == 0) {
                     var17.add(zzjc.zznq);
                  } else {
                     var17.add(zzjc.zzb(var2, var6, var3));
                     var6 += var3;
                  }

                  while(true) {
                     var3 = var6;
                     if (var6 >= var4) {
                        return var3;
                     }

                     var7 = zziy.zza(var2, var6, var14);
                     var3 = var6;
                     if (var5 != var14.zznk) {
                        return var3;
                     }

                     var6 = zziy.zza(var2, var7, var14);
                     var3 = var14.zznk;
                     if (var3 < 0) {
                        throw zzkq.zzdj();
                     }

                     if (var3 > var2.length - var6) {
                        throw zzkq.zzdi();
                     }

                     if (var3 == 0) {
                        var17.add(zzjc.zznq);
                     } else {
                        var17.add(zzjc.zzb(var2, var6, var3));
                        var6 += var3;
                     }
                  }
               }

               throw zzkq.zzdj();
            }
            break label513;
         case 30:
         case 44:
            if (var7 == 2) {
               var3 = zziy.zza(var2, var3, var17, var14);
            } else {
               var11 = var3;
               if (var7 != 0) {
                  break label513;
               }

               var3 = zziy.zza(var5, var2, var3, var4, var17, var14);
            }

            zzkk var28 = (zzkk)var1;
            zzmy var22 = var28.zzrq;
            zzmy var24 = var22;
            if (var22 == zzmy.zzfa()) {
               var24 = null;
            }

            var24 = (zzmy)zzmh.zza(var6, var17, this.zzar(var8), var24, this.zzur);
            var4 = var3;
            if (var24 != null) {
               var28.zzrq = var24;
               var4 = var3;
            }
            break;
         case 33:
         case 47:
            if (var7 == 2) {
               var23 = (zzkl)var17;
               var3 = zziy.zza(var2, var3, var14);
               var4 = var14.zznk + var3;

               while(var3 < var4) {
                  var3 = zziy.zza(var2, var3, var14);
                  var23.zzam(zzjo.zzw(var14.zznk));
               }

               if (var3 != var4) {
                  throw zzkq.zzdi();
               }

               return var3;
            }

            var11 = var3;
            if (var7 == 0) {
               var23 = (zzkl)var17;
               var6 = zziy.zza(var2, var3, var14);
               var23.zzam(zzjo.zzw(var14.zznk));

               while(true) {
                  var3 = var6;
                  if (var6 >= var4) {
                     return var3;
                  }

                  var7 = zziy.zza(var2, var6, var14);
                  var3 = var6;
                  if (var5 != var14.zznk) {
                     return var3;
                  }

                  var6 = zziy.zza(var2, var7, var14);
                  var23.zzam(zzjo.zzw(var14.zznk));
               }
            }
            break label513;
         case 34:
         case 48:
            if (var7 == 2) {
               var21 = (zzle)var17;
               var3 = zziy.zza(var2, var3, var14);
               var4 = var14.zznk + var3;

               while(var3 < var4) {
                  var3 = zziy.zzb(var2, var3, var14);
                  var21.zzv(zzjo.zzk(var14.zznl));
               }

               if (var3 != var4) {
                  throw zzkq.zzdi();
               }

               return var3;
            }

            var11 = var3;
            if (var7 == 0) {
               var21 = (zzle)var17;
               var6 = zziy.zzb(var2, var3, var14);
               var21.zzv(zzjo.zzk(var14.zznl));

               while(true) {
                  var3 = var6;
                  if (var6 >= var4) {
                     return var3;
                  }

                  var7 = zziy.zza(var2, var6, var14);
                  var3 = var6;
                  if (var5 != var14.zznk) {
                     return var3;
                  }

                  var6 = zziy.zzb(var2, var7, var14);
                  var21.zzv(zzjo.zzk(var14.zznl));
               }
            }
            break label513;
         case 49:
            var11 = var3;
            if (var7 == 3) {
               zzmf var20 = this.zzap(var8);
               var6 = var5 & -8 | 4;
               var3 = zziy.zza(var20, var2, var3, var4, var6, var14);
               var17.add(var14.zznm);

               while(true) {
                  var11 = var3;
                  if (var3 >= var4) {
                     break label513;
                  }

                  var7 = zziy.zza(var2, var3, var14);
                  var11 = var3;
                  if (var5 != var14.zznk) {
                     break label513;
                  }

                  var3 = zziy.zza(var20, var2, var7, var4, var6, var14);
                  var17.add(var14.zznm);
               }
            }
            break label513;
         default:
            var11 = var3;
            break label513;
         }

         var3 = var4;
         return var3;
      }

      var3 = var11;
      return var3;
   }

   private final <K, V> int zza(T var1, byte[] var2, int var3, int var4, int var5, long var6, zziz var8) throws IOException {
      Unsafe var9 = zzuc;
      Object var10 = this.zzaq(var5);
      Object var11 = var9.getObject(var1, var6);
      Object var12 = var11;
      if (this.zzut.zzj(var11)) {
         var12 = this.zzut.zzl(var10);
         this.zzut.zzb(var12, var11);
         var9.putObject(var1, var6, var12);
      }

      zzlj var17 = this.zzut.zzm(var10);
      Map var16 = this.zzut.zzh(var12);
      var3 = zziy.zza(var2, var3, var8);
      var5 = var8.zznk;
      if (var5 >= 0 && var5 <= var4 - var3) {
         int var13 = var5 + var3;
         var1 = var17.zztv;
         var12 = var17.zztx;

         while(true) {
            while(var3 < var13) {
               int var14 = var3 + 1;
               byte var15 = var2[var3];
               var5 = var14;
               var3 = var15;
               if (var15 < 0) {
                  var5 = zziy.zza(var15, var2, var14, var8);
                  var3 = var8.zznk;
               }

               int var18 = var3 >>> 3;
               var14 = var3 & 7;
               if (var18 != 1) {
                  if (var18 == 2 && var14 == var17.zztw.zzfk()) {
                     var3 = zza(var2, var5, var4, var17.zztw, var17.zztx.getClass(), var8);
                     var12 = var8.zznm;
                     continue;
                  }
               } else if (var14 == var17.zztu.zzfk()) {
                  var3 = zza(var2, var5, var4, var17.zztu, (Class)null, var8);
                  var1 = var8.zznm;
                  continue;
               }

               var3 = zziy.zza(var3, var2, var5, var4, var8);
            }

            if (var3 == var13) {
               var16.put(var1, var12);
               return var13;
            }

            throw zzkq.zzdm();
         }
      } else {
         throw zzkq.zzdi();
      }
   }

   private static int zza(byte[] var0, int var1, int var2, zznm var3, Class<?> var4, zziz var5) throws IOException {
      // $FF: Couldn't be decompiled
   }

   static <T> zzlu<T> zza(Class<T> var0, zzlo var1, zzly var2, zzla var3, zzmx<?, ?> var4, zzjy<?> var5, zzll var6) {
      int var9;
      if (var1 instanceof zzme) {
         zzme var7 = (zzme)var1;
         int var8 = var7.zzec();
         var9 = zzkk.zze.zzsg;
         byte var10 = 0;
         boolean var11;
         if (var8 == var9) {
            var11 = true;
         } else {
            var11 = false;
         }

         String var41 = var7.zzek();
         int var12 = var41.length();
         int var13 = var41.charAt(0);
         int var14;
         int var15;
         char var46;
         if (var13 >= 55296) {
            var14 = var13 & 8191;
            var15 = 1;
            var8 = 13;

            while(true) {
               var9 = var15 + 1;
               var46 = var41.charAt(var15);
               if (var46 < '\ud800') {
                  var13 = var14 | var46 << var8;
                  var8 = var9;
                  break;
               }

               var14 |= (var46 & 8191) << var8;
               var8 += 13;
               var15 = var9;
            }
         } else {
            var8 = 1;
         }

         var9 = var8 + 1;
         var46 = var41.charAt(var8);
         var8 = var9;
         var14 = var46;
         if (var46 >= '\ud800') {
            var14 = var46 & 8191;
            var8 = 13;
            var15 = var9;

            while(true) {
               var9 = var15 + 1;
               var46 = var41.charAt(var15);
               if (var46 < '\ud800') {
                  var14 |= var46 << var8;
                  var8 = var9;
                  break;
               }

               var14 |= (var46 & 8191) << var8;
               var8 += 13;
               var15 = var9;
            }
         }

         int var16;
         int var18;
         int var19;
         int var20;
         int var22;
         int[] var42;
         int var43;
         char var44;
         int var48;
         char var50;
         char var51;
         int var54;
         if (var14 == 0) {
            var42 = zzub;
            var16 = 0;
            byte var17 = 0;
            var18 = 0;
            var14 = 0;
            var19 = 0;
            var9 = 0;
            var15 = var10;
            var43 = var17;
         } else {
            var14 = var8 + 1;
            char var45 = var41.charAt(var8);
            var9 = var45;
            var15 = var14;
            if (var45 >= '\ud800') {
               var8 = var45 & 8191;
               var9 = 13;
               var15 = var14;
               var14 = var8;

               while(true) {
                  var8 = var15 + 1;
                  var46 = var41.charAt(var15);
                  if (var46 < '\ud800') {
                     var9 = var14 | var46 << var9;
                     var15 = var8;
                     break;
                  }

                  var14 |= (var46 & 8191) << var9;
                  var9 += 13;
                  var15 = var8;
               }
            }

            var8 = var15 + 1;
            char var49 = var41.charAt(var15);
            var19 = var49;
            var15 = var8;
            if (var49 >= '\ud800') {
               var15 = var49 & 8191;
               var14 = 13;
               var43 = var8;

               while(true) {
                  var8 = var43 + 1;
                  var44 = var41.charAt(var43);
                  if (var44 < '\ud800') {
                     var19 = var15 | var44 << var14;
                     var15 = var8;
                     break;
                  }

                  var15 |= (var44 & 8191) << var14;
                  var14 += 13;
                  var43 = var8;
               }
            }

            var14 = var15 + 1;
            var44 = var41.charAt(var15);
            var8 = var44;
            var15 = var14;
            if (var44 >= '\ud800') {
               var15 = var44 & 8191;
               var8 = 13;
               var43 = var14;

               while(true) {
                  var14 = var43 + 1;
                  var44 = var41.charAt(var43);
                  if (var44 < '\ud800') {
                     var8 = var15 | var44 << var8;
                     var15 = var14;
                     break;
                  }

                  var15 |= (var44 & 8191) << var8;
                  var8 += 13;
                  var43 = var14;
               }
            }

            var43 = var15 + 1;
            var51 = var41.charAt(var15);
            var14 = var51;
            var15 = var43;
            if (var51 >= '\ud800') {
               var14 = var51 & 8191;
               var15 = 13;
               var18 = var43;
               var43 = var14;

               while(true) {
                  var14 = var18 + 1;
                  var51 = var41.charAt(var18);
                  if (var51 < '\ud800') {
                     var43 |= var51 << var15;
                     var15 = var14;
                     var14 = var43;
                     break;
                  }

                  var43 |= (var51 & 8191) << var15;
                  var15 += 13;
                  var18 = var14;
               }
            }

            var43 = var15 + 1;
            var51 = var41.charAt(var15);
            var15 = var51;
            var48 = var43;
            if (var51 >= '\ud800') {
               var18 = var51 & 8191;
               var15 = 13;
               var48 = var43;

               while(true) {
                  var43 = var48 + 1;
                  var50 = var41.charAt(var48);
                  if (var50 < '\ud800') {
                     var15 = var18 | var50 << var15;
                     var48 = var43;
                     break;
                  }

                  var18 |= (var50 & 8191) << var15;
                  var15 += 13;
                  var48 = var43;
               }
            }

            var18 = var48 + 1;
            char var47 = var41.charAt(var48);
            var43 = var47;
            var48 = var18;
            if (var47 >= '\ud800') {
               var48 = var47 & 8191;
               var43 = 13;
               var16 = var18;

               while(true) {
                  var18 = var16 + 1;
                  var47 = var41.charAt(var16);
                  if (var47 < '\ud800') {
                     var43 = var48 | var47 << var43;
                     var48 = var18;
                     break;
                  }

                  var48 |= (var47 & 8191) << var43;
                  var43 += 13;
                  var16 = var18;
               }
            }

            var20 = var48 + 1;
            var16 = var41.charAt(var48);
            if (var16 < 55296) {
               var18 = var20;
            } else {
               var16 &= 8191;
               var48 = 13;

               while(true) {
                  var18 = var20 + 1;
                  char var52 = var41.charAt(var20);
                  if (var52 < '\ud800') {
                     var16 |= var52 << var48;
                     break;
                  }

                  var16 |= (var52 & 8191) << var48;
                  var48 += 13;
                  var20 = var18;
               }
            }

            var20 = var18 + 1;
            char var21 = var41.charAt(var18);
            var48 = var21;
            var18 = var20;
            if (var21 >= '\ud800') {
               var48 = var21 & 8191;
               var18 = 13;
               var54 = var20;
               var20 = var48;

               while(true) {
                  var48 = var54 + 1;
                  var21 = var41.charAt(var54);
                  if (var21 < '\ud800') {
                     var20 |= var21 << var18;
                     var18 = var48;
                     var48 = var20;
                     break;
                  }

                  var20 |= (var21 & 8191) << var18;
                  var18 += 13;
                  var54 = var48;
               }
            }

            var42 = new int[var48 + var43 + var16];
            var16 = var14;
            var22 = (var9 << 1) + var19;
            var14 = var48;
            var48 = var9;
            var20 = var18;
            var54 = var43;
            var9 = var14;
            var19 = var15;
            var14 = var22;
            var18 = var8;
            var43 = var16;
            var8 = var20;
            var16 = var54;
            var15 = var48;
         }

         Unsafe var23 = zzuc;
         Object[] var24 = var7.zzel();
         Class var25 = var7.zzee().getClass();
         int[] var26 = new int[var19 * 3];
         Object[] var27 = new Object[var19 << 1];
         int var28 = var9 + var16;
         var20 = var8;
         var22 = var28;
         byte var53 = 0;
         int var29 = 0;
         var8 = var9;
         var48 = var53;
         var16 = var43;
         var43 = var20;
         var20 = var15;

         int var38;
         for(var54 = var12; var43 < var54; var22 = var38) {
            var15 = var43 + 1;
            var12 = var41.charAt(var43);
            char var30;
            if (var12 < 55296) {
               var43 = var15;
               var15 = var9;
            } else {
               var12 &= 8191;
               var43 = 13;

               while(true) {
                  var18 = var15 + 1;
                  var30 = var41.charAt(var15);
                  var15 = var9;
                  if (var30 < '\ud800') {
                     var12 |= var30 << var43;
                     var43 = var18;
                     break;
                  }

                  var12 |= (var30 & 8191) << var43;
                  var43 += 13;
                  var9 = var9;
                  var15 = var18;
               }
            }

            var9 = var43 + 1;
            int var31 = var41.charAt(var43);
            int var55;
            if (var31 < 55296) {
               var43 = var9;
            } else {
               var18 = var31 & 8191;
               var43 = 13;
               var55 = var9;

               while(true) {
                  var9 = var55 + 1;
                  var30 = var41.charAt(var55);
                  if (var30 < '\ud800') {
                     var31 = var18 | var30 << var43;
                     var43 = var9;
                     break;
                  }

                  var18 |= (var30 & 8191) << var43;
                  var43 += 13;
                  var55 = var9;
               }
            }

            int var32 = var31 & 255;
            var55 = var48;
            if ((var31 & 1024) != 0) {
               var42[var48] = var29;
               var55 = var48 + 1;
            }

            Field var33;
            int var34;
            int var35;
            int var36;
            int var37;
            Object var57;
            if (var32 >= 51) {
               var48 = var43 + 1;
               var44 = var41.charAt(var43);
               var9 = var48;
               var18 = var44;
               if (var44 >= '\ud800') {
                  var18 = var44 & 8191;
                  var9 = 13;

                  while(true) {
                     var43 = var48 + 1;
                     var50 = var41.charAt(var48);
                     if (var50 < '\ud800') {
                        var18 |= var50 << var9;
                        var9 = var43;
                        break;
                     }

                     var18 |= (var50 & 8191) << var9;
                     var9 += 13;
                     var48 = var43;
                  }
               }

               var48 = var32 - 51;
               if (var48 != 9 && var48 != 17) {
                  var43 = var14;
                  if (var48 == 12) {
                     var43 = var14;
                     if ((var13 & 1) == 1) {
                        var27[(var29 / 3 << 1) + 1] = var24[var14];
                        var43 = var14 + 1;
                     }
                  }

                  var14 = var43;
               } else {
                  var27[(var29 / 3 << 1) + 1] = var24[var14];
                  ++var14;
               }

               var43 = var18 << 1;
               var57 = var24[var43];
               if (var57 instanceof Field) {
                  var33 = (Field)var57;
               } else {
                  var33 = zza(var25, (String)var57);
                  var24[var43] = var33;
               }

               var34 = (int)var23.objectFieldOffset(var33);
               ++var43;
               var57 = var24[var43];
               if (var57 instanceof Field) {
                  var33 = (Field)var57;
               } else {
                  var33 = zza(var25, (String)var57);
                  var24[var43] = var33;
               }

               var35 = (int)var23.objectFieldOffset(var33);
               var36 = 0;
               var43 = var9;
               var37 = var8;
               var38 = var22;
               var9 = var14;
            } else {
               var18 = var14 + 1;
               var33 = zza(var25, (String)var24[var14]);
               if (var32 != 9 && var32 != 17) {
                  label376: {
                     if (var32 != 27 && var32 != 49) {
                        if (var32 != 12 && var32 != 30 && var32 != 44) {
                           var9 = var18;
                           var14 = var8;
                           if (var32 == 50) {
                              var9 = var8 + 1;
                              var42[var8] = var29;
                              var48 = var29 / 3 << 1;
                              var14 = var18 + 1;
                              var27[var48] = var24[var18];
                              if ((var31 & 2048) != 0) {
                                 var8 = var14 + 1;
                                 var27[var48 + 1] = var24[var14];
                                 var14 = var9;
                                 var9 = var8;
                              } else {
                                 var8 = var14;
                                 var14 = var9;
                                 var9 = var8;
                              }
                           }
                           break label376;
                        }

                        var9 = var18;
                        var14 = var8;
                        if ((var13 & 1) != 1) {
                           break label376;
                        }

                        var14 = var29 / 3;
                        var9 = var18 + 1;
                        var27[(var14 << 1) + 1] = var24[var18];
                     } else {
                        var14 = var29 / 3;
                        var9 = var18 + 1;
                        var27[(var14 << 1) + 1] = var24[var18];
                     }

                     var14 = var8;
                  }
               } else {
                  var27[(var29 / 3 << 1) + 1] = var33.getType();
                  var14 = var8;
                  var9 = var18;
               }

               int var39 = (int)var23.objectFieldOffset(var33);
               if ((var13 & 1) == 1 && var32 <= 17) {
                  var18 = var43 + 1;
                  String var56 = var41;
                  var50 = var41.charAt(var43);
                  var8 = var18;
                  var43 = var50;
                  if (var50 >= '\ud800') {
                     var48 = var50 & 8191;
                     var8 = 13;

                     while(true) {
                        var43 = var18 + 1;
                        var51 = var56.charAt(var18);
                        if (var51 < '\ud800') {
                           var18 = var48 | var51 << var8;
                           var8 = var43;
                           var43 = var18;
                           break;
                        }

                        var48 |= (var51 & 8191) << var8;
                        var8 += 13;
                        var18 = var43;
                     }
                  }

                  var18 = (var20 << 1) + var43 / 32;
                  var57 = var24[var18];
                  if (var57 instanceof Field) {
                     var33 = (Field)var57;
                  } else {
                     var33 = zza(var25, (String)var57);
                     var24[var18] = var33;
                  }

                  var18 = (int)var23.objectFieldOffset(var33);
                  var48 = var43 % 32;
               } else {
                  var18 = 0;
                  var48 = 0;
                  var8 = var43;
               }

               int var40 = var9;
               var34 = var39;
               var35 = var18;
               var36 = var48;
               var43 = var8;
               var37 = var14;
               var38 = var22;
               var9 = var9;
               if (var32 >= 18) {
                  var34 = var39;
                  var35 = var18;
                  var36 = var48;
                  var43 = var8;
                  var37 = var14;
                  var38 = var22;
                  var9 = var40;
                  if (var32 <= 49) {
                     var42[var22] = var39;
                     var38 = var22 + 1;
                     var9 = var40;
                     var37 = var14;
                     var43 = var8;
                     var36 = var48;
                     var35 = var18;
                     var34 = var39;
                  }
               }
            }

            var18 = var29 + 1;
            var26[var29] = var12;
            var48 = var18 + 1;
            if ((var31 & 512) != 0) {
               var8 = 536870912;
            } else {
               var8 = 0;
            }

            if ((var31 & 256) != 0) {
               var14 = 268435456;
            } else {
               var14 = 0;
            }

            var26[var18] = var34 | var14 | var8 | var32 << 20;
            var29 = var48 + 1;
            var26[var48] = var36 << 20 | var35;
            var14 = var9;
            var9 = var15;
            var48 = var55;
            var8 = var37;
         }

         return new zzlu(var26, var27, var18, var16, var7.zzee(), var11, false, var42, var9, var28, var2, var3, var4, var5, var6);
      } else {
         ((zzms)var1).zzec();
         var9 = zzkk.zze.zzsg;
         throw new NoSuchMethodError();
      }
   }

   private final <K, V, UT, UB> UB zza(int var1, int var2, Map<K, V> var3, zzko var4, UB var5, zzmx<UT, UB> var6) {
      zzlj var7 = this.zzut.zzm(this.zzaq(var1));
      Iterator var8 = var3.entrySet().iterator();

      while(var8.hasNext()) {
         Entry var9 = (Entry)var8.next();
         if (!var4.zzan((Integer)var9.getValue())) {
            Object var12 = var5;
            if (var5 == null) {
               var12 = var6.zzez();
            }

            zzjk var10 = zzjc.zzu(zzli.zza(var7, var9.getKey(), var9.getValue()));
            zzjr var13 = var10.zzby();

            try {
               zzli.zza(var13, var7, var9.getKey(), var9.getValue());
            } catch (IOException var11) {
               throw new RuntimeException(var11);
            }

            var6.zza(var12, var2, var10.zzbx());
            var8.remove();
            var5 = var12;
         }
      }

      return var5;
   }

   private static Field zza(Class<?> var0, String var1) {
      try {
         Field var9 = var0.getDeclaredField(var1);
         return var9;
      } catch (NoSuchFieldException var6) {
         Field[] var2 = var0.getDeclaredFields();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Field var5 = var2[var4];
            if (var1.equals(var5.getName())) {
               return var5;
            }
         }

         String var7 = var0.getName();
         String var10 = Arrays.toString(var2);
         StringBuilder var8 = new StringBuilder(String.valueOf(var1).length() + 40 + String.valueOf(var7).length() + String.valueOf(var10).length());
         var8.append("Field ");
         var8.append(var1);
         var8.append(" for ");
         var8.append(var7);
         var8.append(" not found. Known fields are ");
         var8.append(var10);
         throw new RuntimeException(var8.toString());
      }
   }

   private static void zza(int var0, Object var1, zzns var2) throws IOException {
      if (var1 instanceof String) {
         var2.zza(var0, (String)var1);
      } else {
         var2.zza(var0, (zzjc)var1);
      }
   }

   private static <UT, UB> void zza(zzmx<UT, UB> var0, T var1, zzns var2) throws IOException {
      var0.zza(var0.zzr(var1), var2);
   }

   private final <K, V> void zza(zzns var1, int var2, Object var3, int var4) throws IOException {
      if (var3 != null) {
         var1.zza(var2, this.zzut.zzm(this.zzaq(var4)), this.zzut.zzi(var3));
      }

   }

   private final void zza(T var1, T var2, int var3) {
      long var4 = (long)(this.zzas(var3) & 1048575);
      if (this.zza(var2, var3)) {
         Object var6 = zznd.zzo(var1, var4);
         var2 = zznd.zzo(var2, var4);
         if (var6 != null && var2 != null) {
            zznd.zza(var1, var4, zzkm.zza(var6, var2));
            this.zzb(var1, var3);
         } else {
            if (var2 != null) {
               zznd.zza(var1, var4, var2);
               this.zzb(var1, var3);
            }

         }
      }
   }

   private final boolean zza(T var1, int var2) {
      if (this.zzuk) {
         var2 = this.zzas(var2);
         long var3 = (long)(var2 & 1048575);
         switch((var2 & 267386880) >>> 20) {
         case 0:
            if (zznd.zzn(var1, var3) != 0.0D) {
               return true;
            }

            return false;
         case 1:
            if (zznd.zzm(var1, var3) != 0.0F) {
               return true;
            }

            return false;
         case 2:
            if (zznd.zzk(var1, var3) != 0L) {
               return true;
            }

            return false;
         case 3:
            if (zznd.zzk(var1, var3) != 0L) {
               return true;
            }

            return false;
         case 4:
            if (zznd.zzj(var1, var3) != 0) {
               return true;
            }

            return false;
         case 5:
            if (zznd.zzk(var1, var3) != 0L) {
               return true;
            }

            return false;
         case 6:
            if (zznd.zzj(var1, var3) != 0) {
               return true;
            }

            return false;
         case 7:
            return zznd.zzl(var1, var3);
         case 8:
            var1 = zznd.zzo(var1, var3);
            if (var1 instanceof String) {
               if (!((String)var1).isEmpty()) {
                  return true;
               }

               return false;
            } else {
               if (var1 instanceof zzjc) {
                  if (!zzjc.zznq.equals(var1)) {
                     return true;
                  }

                  return false;
               }

               throw new IllegalArgumentException();
            }
         case 9:
            if (zznd.zzo(var1, var3) != null) {
               return true;
            }

            return false;
         case 10:
            if (!zzjc.zznq.equals(zznd.zzo(var1, var3))) {
               return true;
            }

            return false;
         case 11:
            if (zznd.zzj(var1, var3) != 0) {
               return true;
            }

            return false;
         case 12:
            if (zznd.zzj(var1, var3) != 0) {
               return true;
            }

            return false;
         case 13:
            if (zznd.zzj(var1, var3) != 0) {
               return true;
            }

            return false;
         case 14:
            if (zznd.zzk(var1, var3) != 0L) {
               return true;
            }

            return false;
         case 15:
            if (zznd.zzj(var1, var3) != 0) {
               return true;
            }

            return false;
         case 16:
            if (zznd.zzk(var1, var3) != 0L) {
               return true;
            }

            return false;
         case 17:
            if (zznd.zzo(var1, var3) != null) {
               return true;
            }

            return false;
         default:
            throw new IllegalArgumentException();
         }
      } else {
         var2 = this.zzat(var2);
         return (zznd.zzj(var1, (long)(var2 & 1048575)) & 1 << (var2 >>> 20)) != 0;
      }
   }

   private final boolean zza(T var1, int var2, int var3) {
      return zznd.zzj(var1, (long)(this.zzat(var3) & 1048575)) == var2;
   }

   private final boolean zza(T var1, int var2, int var3, int var4) {
      if (this.zzuk) {
         return this.zza(var1, var2);
      } else {
         return (var3 & var4) != 0;
      }
   }

   private static boolean zza(Object var0, int var1, zzmf var2) {
      return var2.zzp(zznd.zzo(var0, (long)(var1 & 1048575)));
   }

   private final zzmf zzap(int var1) {
      var1 = var1 / 3 << 1;
      zzmf var2 = (zzmf)this.zzue[var1];
      if (var2 != null) {
         return var2;
      } else {
         var2 = zzmd.zzej().zzf((Class)this.zzue[var1 + 1]);
         this.zzue[var1] = var2;
         return var2;
      }
   }

   private final Object zzaq(int var1) {
      return this.zzue[var1 / 3 << 1];
   }

   private final zzko zzar(int var1) {
      return (zzko)this.zzue[(var1 / 3 << 1) + 1];
   }

   private final int zzas(int var1) {
      return this.zzud[var1 + 1];
   }

   private final int zzat(int var1) {
      return this.zzud[var1 + 2];
   }

   private final int zzau(int var1) {
      return var1 >= this.zzuf && var1 <= this.zzug ? this.zzq(var1, 0) : -1;
   }

   private final void zzb(T var1, int var2) {
      if (!this.zzuk) {
         var2 = this.zzat(var2);
         long var3 = (long)(var2 & 1048575);
         zznd.zza(var1, var3, zznd.zzj(var1, var3) | 1 << (var2 >>> 20));
      }
   }

   private final void zzb(T var1, int var2, int var3) {
      zznd.zza(var1, (long)(this.zzat(var3) & 1048575), var2);
   }

   private final void zzb(T var1, zzns var2) throws IOException {
      Iterator var4;
      Entry var19;
      label200: {
         if (this.zzui) {
            zzkb var3 = this.zzus.zzb(var1);
            if (!var3.zzos.isEmpty()) {
               var4 = var3.iterator();
               var19 = (Entry)var4.next();
               break label200;
            }
         }

         var4 = null;
         var19 = null;
      }

      int var5 = -1;
      int var6 = this.zzud.length;
      Unsafe var7 = zzuc;
      int var8 = 0;
      int var9 = 0;

      while(true) {
         Entry var10 = var19;
         if (var8 >= var6) {
            while(var10 != null) {
               this.zzus.zza(var2, var10);
               if (var4.hasNext()) {
                  var10 = (Entry)var4.next();
               } else {
                  var10 = null;
               }
            }

            zza(this.zzur, var1, var2);
            return;
         }

         int var11 = this.zzas(var8);
         int[] var20 = this.zzud;
         int var12 = var20[var8];
         int var13 = (267386880 & var11) >>> 20;
         int var15;
         if (!this.zzuk && var13 <= 17) {
            int var14 = var20[var8 + 2];
            var15 = var14 & 1048575;
            int var16 = var5;
            if (var15 != var5) {
               var9 = var7.getInt(var1, (long)var15);
               var16 = var15;
            }

            var15 = 1 << (var14 >>> 20);
            var5 = var16;
         } else {
            var15 = 0;
         }

         while(var19 != null && this.zzus.zza(var19) <= var12) {
            this.zzus.zza(var2, var19);
            if (var4.hasNext()) {
               var19 = (Entry)var4.next();
            } else {
               var19 = null;
            }
         }

         long var17 = (long)(var11 & 1048575);
         switch(var13) {
         case 0:
            if ((var15 & var9) != 0) {
               var2.zza(var12, zznd.zzn(var1, var17));
            }
            break;
         case 1:
            if ((var15 & var9) != 0) {
               var2.zza(var12, zznd.zzm(var1, var17));
            }
            break;
         case 2:
            if ((var15 & var9) != 0) {
               var2.zzi(var12, var7.getLong(var1, var17));
            }
            break;
         case 3:
            if ((var15 & var9) != 0) {
               var2.zza(var12, var7.getLong(var1, var17));
            }
            break;
         case 4:
            if ((var15 & var9) != 0) {
               var2.zzc(var12, var7.getInt(var1, var17));
            }
            break;
         case 5:
            if ((var15 & var9) != 0) {
               var2.zzc(var12, var7.getLong(var1, var17));
            }
            break;
         case 6:
            if ((var15 & var9) != 0) {
               var2.zzf(var12, var7.getInt(var1, var17));
            }
            break;
         case 7:
            if ((var15 & var9) != 0) {
               var2.zzb(var12, zznd.zzl(var1, var17));
            }
            break;
         case 8:
            if ((var15 & var9) != 0) {
               zza(var12, var7.getObject(var1, var17), var2);
            }
            break;
         case 9:
            if ((var15 & var9) != 0) {
               var2.zza(var12, var7.getObject(var1, var17), this.zzap(var8));
            }
            break;
         case 10:
            if ((var15 & var9) != 0) {
               var2.zza(var12, (zzjc)var7.getObject(var1, var17));
            }
            break;
         case 11:
            if ((var15 & var9) != 0) {
               var2.zzd(var12, var7.getInt(var1, var17));
            }
            break;
         case 12:
            if ((var15 & var9) != 0) {
               var2.zzn(var12, var7.getInt(var1, var17));
            }
            break;
         case 13:
            if ((var15 & var9) != 0) {
               var2.zzm(var12, var7.getInt(var1, var17));
            }
            break;
         case 14:
            if ((var15 & var9) != 0) {
               var2.zzj(var12, var7.getLong(var1, var17));
            }
            break;
         case 15:
            if ((var15 & var9) != 0) {
               var2.zze(var12, var7.getInt(var1, var17));
            }
            break;
         case 16:
            if ((var15 & var9) != 0) {
               var2.zzb(var12, var7.getLong(var1, var17));
            }
            break;
         case 17:
            if ((var15 & var9) != 0) {
               var2.zzb(var12, var7.getObject(var1, var17), this.zzap(var8));
            }
            break;
         case 18:
            zzmh.zza(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 19:
            zzmh.zzb(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 20:
            zzmh.zzc(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 21:
            zzmh.zzd(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 22:
            zzmh.zzh(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 23:
            zzmh.zzf(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 24:
            zzmh.zzk(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 25:
            zzmh.zzn(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 26:
            zzmh.zza(this.zzud[var8], (List)var7.getObject(var1, var17), var2);
            break;
         case 27:
            zzmh.zza(this.zzud[var8], (List)var7.getObject(var1, var17), var2, this.zzap(var8));
            break;
         case 28:
            zzmh.zzb(this.zzud[var8], (List)var7.getObject(var1, var17), var2);
            break;
         case 29:
            zzmh.zzi(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 30:
            zzmh.zzm(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 31:
            zzmh.zzl(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 32:
            zzmh.zzg(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 33:
            zzmh.zzj(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 34:
            zzmh.zze(this.zzud[var8], (List)var7.getObject(var1, var17), var2, false);
            break;
         case 35:
            zzmh.zza(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 36:
            zzmh.zzb(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 37:
            zzmh.zzc(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 38:
            zzmh.zzd(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 39:
            zzmh.zzh(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 40:
            zzmh.zzf(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 41:
            zzmh.zzk(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 42:
            zzmh.zzn(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 43:
            zzmh.zzi(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 44:
            zzmh.zzm(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 45:
            zzmh.zzl(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 46:
            zzmh.zzg(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 47:
            zzmh.zzj(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 48:
            zzmh.zze(this.zzud[var8], (List)var7.getObject(var1, var17), var2, true);
            break;
         case 49:
            zzmh.zzb(this.zzud[var8], (List)var7.getObject(var1, var17), var2, this.zzap(var8));
            break;
         case 50:
            this.zza(var2, var12, var7.getObject(var1, var17), var8);
            break;
         case 51:
            if (this.zza(var1, var12, var8)) {
               var2.zza(var12, zze(var1, var17));
            }
            break;
         case 52:
            if (this.zza(var1, var12, var8)) {
               var2.zza(var12, zzf(var1, var17));
            }
            break;
         case 53:
            if (this.zza(var1, var12, var8)) {
               var2.zzi(var12, zzh(var1, var17));
            }
            break;
         case 54:
            if (this.zza(var1, var12, var8)) {
               var2.zza(var12, zzh(var1, var17));
            }
            break;
         case 55:
            if (this.zza(var1, var12, var8)) {
               var2.zzc(var12, zzg(var1, var17));
            }
            break;
         case 56:
            if (this.zza(var1, var12, var8)) {
               var2.zzc(var12, zzh(var1, var17));
            }
            break;
         case 57:
            if (this.zza(var1, var12, var8)) {
               var2.zzf(var12, zzg(var1, var17));
            }
            break;
         case 58:
            if (this.zza(var1, var12, var8)) {
               var2.zzb(var12, zzi(var1, var17));
            }
            break;
         case 59:
            if (this.zza(var1, var12, var8)) {
               zza(var12, var7.getObject(var1, var17), var2);
            }
            break;
         case 60:
            if (this.zza(var1, var12, var8)) {
               var2.zza(var12, var7.getObject(var1, var17), this.zzap(var8));
            }
            break;
         case 61:
            if (this.zza(var1, var12, var8)) {
               var2.zza(var12, (zzjc)var7.getObject(var1, var17));
            }
            break;
         case 62:
            if (this.zza(var1, var12, var8)) {
               var2.zzd(var12, zzg(var1, var17));
            }
            break;
         case 63:
            if (this.zza(var1, var12, var8)) {
               var2.zzn(var12, zzg(var1, var17));
            }
            break;
         case 64:
            if (this.zza(var1, var12, var8)) {
               var2.zzm(var12, zzg(var1, var17));
            }
            break;
         case 65:
            if (this.zza(var1, var12, var8)) {
               var2.zzj(var12, zzh(var1, var17));
            }
            break;
         case 66:
            if (this.zza(var1, var12, var8)) {
               var2.zze(var12, zzg(var1, var17));
            }
            break;
         case 67:
            if (this.zza(var1, var12, var8)) {
               var2.zzb(var12, zzh(var1, var17));
            }
            break;
         case 68:
            if (this.zza(var1, var12, var8)) {
               var2.zzb(var12, var7.getObject(var1, var17), this.zzap(var8));
            }
         }

         var8 += 3;
      }
   }

   private final void zzb(T var1, T var2, int var3) {
      int var4 = this.zzas(var3);
      int var5 = this.zzud[var3];
      long var6 = (long)(var4 & 1048575);
      if (this.zza(var2, var5, var3)) {
         Object var8 = zznd.zzo(var1, var6);
         var2 = zznd.zzo(var2, var6);
         if (var8 != null && var2 != null) {
            zznd.zza(var1, var6, zzkm.zza(var8, var2));
            this.zzb(var1, var5, var3);
         } else {
            if (var2 != null) {
               zznd.zza(var1, var6, var2);
               this.zzb(var1, var5, var3);
            }

         }
      }
   }

   private final boolean zzc(T var1, T var2, int var3) {
      return this.zza(var1, var3) == this.zza(var2, var3);
   }

   private static <E> List<E> zzd(Object var0, long var1) {
      return (List)zznd.zzo(var0, var1);
   }

   private static <T> double zze(T var0, long var1) {
      return (Double)zznd.zzo(var0, var1);
   }

   private static <T> float zzf(T var0, long var1) {
      return (Float)zznd.zzo(var0, var1);
   }

   private static <T> int zzg(T var0, long var1) {
      return (Integer)zznd.zzo(var0, var1);
   }

   private static <T> long zzh(T var0, long var1) {
      return (Long)zznd.zzo(var0, var1);
   }

   private static <T> boolean zzi(T var0, long var1) {
      return (Boolean)zznd.zzo(var0, var1);
   }

   private static zzmy zzo(Object var0) {
      zzkk var1 = (zzkk)var0;
      zzmy var2 = var1.zzrq;
      zzmy var3 = var2;
      if (var2 == zzmy.zzfa()) {
         var3 = zzmy.zzfb();
         var1.zzrq = var3;
      }

      return var3;
   }

   private final int zzp(int var1, int var2) {
      return var1 >= this.zzuf && var1 <= this.zzug ? this.zzq(var1, var2) : -1;
   }

   private final int zzq(int var1, int var2) {
      int var3 = this.zzud.length / 3 - 1;

      while(var2 <= var3) {
         int var4 = var3 + var2 >>> 1;
         int var5 = var4 * 3;
         int var6 = this.zzud[var5];
         if (var1 == var6) {
            return var5;
         }

         if (var1 < var6) {
            var3 = var4 - 1;
         } else {
            var2 = var4 + 1;
         }
      }

      return -1;
   }

   public final boolean equals(T var1, T var2) {
      int var3 = this.zzud.length;
      int var4 = 0;

      while(true) {
         boolean var5 = true;
         if (var4 >= var3) {
            if (!this.zzur.zzr(var1).equals(this.zzur.zzr(var2))) {
               return false;
            }

            if (this.zzui) {
               return this.zzus.zzb(var1).equals(this.zzus.zzb(var2));
            }

            return true;
         }

         label124: {
            int var6 = this.zzas(var4);
            long var7 = (long)(var6 & 1048575);
            switch((var6 & 267386880) >>> 20) {
            case 0:
               if (this.zzc(var1, var2, var4) && Double.doubleToLongBits(zznd.zzn(var1, var7)) == Double.doubleToLongBits(zznd.zzn(var2, var7))) {
                  break label124;
               }
               break;
            case 1:
               if (this.zzc(var1, var2, var4) && Float.floatToIntBits(zznd.zzm(var1, var7)) == Float.floatToIntBits(zznd.zzm(var2, var7))) {
                  break label124;
               }
               break;
            case 2:
               if (this.zzc(var1, var2, var4) && zznd.zzk(var1, var7) == zznd.zzk(var2, var7)) {
                  break label124;
               }
               break;
            case 3:
               if (this.zzc(var1, var2, var4) && zznd.zzk(var1, var7) == zznd.zzk(var2, var7)) {
                  break label124;
               }
               break;
            case 4:
               if (this.zzc(var1, var2, var4) && zznd.zzj(var1, var7) == zznd.zzj(var2, var7)) {
                  break label124;
               }
               break;
            case 5:
               if (this.zzc(var1, var2, var4) && zznd.zzk(var1, var7) == zznd.zzk(var2, var7)) {
                  break label124;
               }
               break;
            case 6:
               if (this.zzc(var1, var2, var4) && zznd.zzj(var1, var7) == zznd.zzj(var2, var7)) {
                  break label124;
               }
               break;
            case 7:
               if (this.zzc(var1, var2, var4) && zznd.zzl(var1, var7) == zznd.zzl(var2, var7)) {
                  break label124;
               }
               break;
            case 8:
               if (this.zzc(var1, var2, var4) && zzmh.zzd(zznd.zzo(var1, var7), zznd.zzo(var2, var7))) {
                  break label124;
               }
               break;
            case 9:
               if (this.zzc(var1, var2, var4) && zzmh.zzd(zznd.zzo(var1, var7), zznd.zzo(var2, var7))) {
                  break label124;
               }
               break;
            case 10:
               if (this.zzc(var1, var2, var4) && zzmh.zzd(zznd.zzo(var1, var7), zznd.zzo(var2, var7))) {
                  break label124;
               }
               break;
            case 11:
               if (this.zzc(var1, var2, var4) && zznd.zzj(var1, var7) == zznd.zzj(var2, var7)) {
                  break label124;
               }
               break;
            case 12:
               if (this.zzc(var1, var2, var4) && zznd.zzj(var1, var7) == zznd.zzj(var2, var7)) {
                  break label124;
               }
               break;
            case 13:
               if (this.zzc(var1, var2, var4) && zznd.zzj(var1, var7) == zznd.zzj(var2, var7)) {
                  break label124;
               }
               break;
            case 14:
               if (this.zzc(var1, var2, var4) && zznd.zzk(var1, var7) == zznd.zzk(var2, var7)) {
                  break label124;
               }
               break;
            case 15:
               if (this.zzc(var1, var2, var4) && zznd.zzj(var1, var7) == zznd.zzj(var2, var7)) {
                  break label124;
               }
               break;
            case 16:
               if (this.zzc(var1, var2, var4) && zznd.zzk(var1, var7) == zznd.zzk(var2, var7)) {
                  break label124;
               }
               break;
            case 17:
               if (this.zzc(var1, var2, var4) && zzmh.zzd(zznd.zzo(var1, var7), zznd.zzo(var2, var7))) {
                  break label124;
               }
               break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
               var5 = zzmh.zzd(zznd.zzo(var1, var7), zznd.zzo(var2, var7));
               break label124;
            case 50:
               var5 = zzmh.zzd(zznd.zzo(var1, var7), zznd.zzo(var2, var7));
               break label124;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
               long var9 = (long)(this.zzat(var4) & 1048575);
               if (zznd.zzj(var1, var9) == zznd.zzj(var2, var9) && zzmh.zzd(zznd.zzo(var1, var7), zznd.zzo(var2, var7))) {
                  break label124;
               }
               break;
            default:
               break label124;
            }

            var5 = false;
         }

         if (!var5) {
            return false;
         }

         var4 += 3;
      }
   }

   public final int hashCode(T var1) {
      int var2 = this.zzud.length;
      int var3 = 0;

      int var4;
      int var9;
      for(var4 = 0; var3 < var2; var4 = var9) {
         label118: {
            label117: {
               int var5 = this.zzas(var3);
               int var6 = this.zzud[var3];
               long var7 = (long)(1048575 & var5);
               var9 = 37;
               Object var10;
               switch((var5 & 267386880) >>> 20) {
               case 0:
                  var9 = var4 * 53;
                  var4 = zzkm.zzu(Double.doubleToLongBits(zznd.zzn(var1, var7)));
                  break label117;
               case 1:
                  var9 = var4 * 53;
                  var4 = Float.floatToIntBits(zznd.zzm(var1, var7));
                  break label117;
               case 2:
                  var9 = var4 * 53;
                  var4 = zzkm.zzu(zznd.zzk(var1, var7));
                  break label117;
               case 3:
                  var9 = var4 * 53;
                  var4 = zzkm.zzu(zznd.zzk(var1, var7));
                  break label117;
               case 4:
                  var9 = var4 * 53;
                  var4 = zznd.zzj(var1, var7);
                  break label117;
               case 5:
                  var9 = var4 * 53;
                  var4 = zzkm.zzu(zznd.zzk(var1, var7));
                  break label117;
               case 6:
                  var9 = var4 * 53;
                  var4 = zznd.zzj(var1, var7);
                  break label117;
               case 7:
                  var9 = var4 * 53;
                  var4 = zzkm.zze(zznd.zzl(var1, var7));
                  break label117;
               case 8:
                  var9 = var4 * 53;
                  var4 = ((String)zznd.zzo(var1, var7)).hashCode();
                  break label117;
               case 9:
                  var10 = zznd.zzo(var1, var7);
                  if (var10 != null) {
                     var9 = var10.hashCode();
                  }
                  break;
               case 10:
                  var9 = var4 * 53;
                  var4 = zznd.zzo(var1, var7).hashCode();
                  break label117;
               case 11:
                  var9 = var4 * 53;
                  var4 = zznd.zzj(var1, var7);
                  break label117;
               case 12:
                  var9 = var4 * 53;
                  var4 = zznd.zzj(var1, var7);
                  break label117;
               case 13:
                  var9 = var4 * 53;
                  var4 = zznd.zzj(var1, var7);
                  break label117;
               case 14:
                  var9 = var4 * 53;
                  var4 = zzkm.zzu(zznd.zzk(var1, var7));
                  break label117;
               case 15:
                  var9 = var4 * 53;
                  var4 = zznd.zzj(var1, var7);
                  break label117;
               case 16:
                  var9 = var4 * 53;
                  var4 = zzkm.zzu(zznd.zzk(var1, var7));
                  break label117;
               case 17:
                  var10 = zznd.zzo(var1, var7);
                  if (var10 != null) {
                     var9 = var10.hashCode();
                  }
                  break;
               case 18:
               case 19:
               case 20:
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
               case 26:
               case 27:
               case 28:
               case 29:
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 35:
               case 36:
               case 37:
               case 38:
               case 39:
               case 40:
               case 41:
               case 42:
               case 43:
               case 44:
               case 45:
               case 46:
               case 47:
               case 48:
               case 49:
                  var9 = var4 * 53;
                  var4 = zznd.zzo(var1, var7).hashCode();
                  break label117;
               case 50:
                  var9 = var4 * 53;
                  var4 = zznd.zzo(var1, var7).hashCode();
                  break label117;
               case 51:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzkm.zzu(Double.doubleToLongBits(zze(var1, var7)));
                  break label117;
               case 52:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = Float.floatToIntBits(zzf(var1, var7));
                  break label117;
               case 53:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzkm.zzu(zzh(var1, var7));
                  break label117;
               case 54:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzkm.zzu(zzh(var1, var7));
                  break label117;
               case 55:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzg(var1, var7);
                  break label117;
               case 56:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzkm.zzu(zzh(var1, var7));
                  break label117;
               case 57:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzg(var1, var7);
                  break label117;
               case 58:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzkm.zze(zzi(var1, var7));
                  break label117;
               case 59:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = ((String)zznd.zzo(var1, var7)).hashCode();
                  break label117;
               case 60:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var10 = zznd.zzo(var1, var7);
                  var9 = var4 * 53;
                  var4 = var10.hashCode();
                  break label117;
               case 61:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zznd.zzo(var1, var7).hashCode();
                  break label117;
               case 62:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzg(var1, var7);
                  break label117;
               case 63:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzg(var1, var7);
                  break label117;
               case 64:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzg(var1, var7);
                  break label117;
               case 65:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzkm.zzu(zzh(var1, var7));
                  break label117;
               case 66:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzg(var1, var7);
                  break label117;
               case 67:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var9 = var4 * 53;
                  var4 = zzkm.zzu(zzh(var1, var7));
                  break label117;
               case 68:
                  var9 = var4;
                  if (!this.zza(var1, var6, var3)) {
                     break label118;
                  }

                  var10 = zznd.zzo(var1, var7);
                  var9 = var4 * 53;
                  var4 = var10.hashCode();
                  break label117;
               default:
                  var9 = var4;
                  break label118;
               }

               var9 += var4 * 53;
               break label118;
            }

            var9 += var4;
         }

         var3 += 3;
      }

      var4 = var4 * 53 + this.zzur.zzr(var1).hashCode();
      var9 = var4;
      if (this.zzui) {
         var9 = var4 * 53 + this.zzus.zzb(var1).hashCode();
      }

      return var9;
   }

   public final T newInstance() {
      return this.zzup.newInstance(this.zzuh);
   }

   final int zza(T var1, byte[] var2, int var3, int var4, int var5, zziz var6) throws IOException {
      Object var7 = var1;
      int var8 = var4;
      int var9 = var5;
      zziz var10 = var6;
      Object var11 = zzuc;
      int var12 = -1;
      int var13 = 0;
      int var14 = 0;
      int var15 = 0;
      int var16 = -1;

      Object var32;
      zzlu var33;
      while(true) {
         if (var3 >= var8) {
            var32 = var7;
            var33 = this;
            var5 = var3;
            var3 = var14;
            var14 = var9;
            var8 = var15;
            break;
         }

         int var19 = var3 + 1;
         var3 = var2[var3];
         if (var3 < 0) {
            var19 = zziy.zza(var3, var2, var19, var10);
            var3 = var10.zznk;
         }

         var8 = var3 >>> 3;
         int var20 = var3 & 7;
         if (var8 > var12) {
            var14 = this.zzp(var8, var13 / 3);
         } else {
            var14 = this.zzau(var8);
         }

         label255: {
            label279: {
               if (var14 == -1) {
                  var12 = var8;
                  var14 = var19;
                  var13 = var3;
                  var3 = var9;
                  var9 = 0;
                  var8 = var15;
                  var15 = var13;
               } else {
                  int[] var18 = this.zzud;
                  int var21 = var18[var14 + 1];
                  int var22 = (var21 & 267386880) >>> 20;
                  long var23 = (long)(var21 & 1048575);
                  int var25;
                  if (var22 <= 17) {
                     label268: {
                        var9 = var18[var14 + 2];
                        var25 = 1 << (var9 >>> 20);
                        var13 = var9 & 1048575;
                        var12 = var15;
                        var9 = var16;
                        if (var13 != var16) {
                           if (var16 != -1) {
                              ((Unsafe)var11).putInt(var7, (long)var16, var15);
                           }

                           var12 = ((Unsafe)var11).getInt(var7, (long)var13);
                           var9 = var13;
                        }

                        label239: {
                           label281: {
                              label236: {
                                 label235: {
                                    label234: {
                                       label233: {
                                          switch(var22) {
                                          case 0:
                                             if (var20 != 1) {
                                                break label281;
                                             }

                                             zznd.zza(var7, var23, zziy.zzc(var2, var19));
                                             break;
                                          case 1:
                                             if (var20 != 5) {
                                                break label281;
                                             }

                                             zznd.zza(var7, var23, zziy.zzd(var2, var19));
                                             var15 = var19 + 4;
                                             break label235;
                                          case 2:
                                          case 3:
                                             if (var20 == 0) {
                                                var16 = zziy.zzb(var2, var19, var6);
                                                ((Unsafe)var11).putLong(var1, var23, var6.zznl);
                                                var19 = var12 | var25;
                                                var15 = var3;
                                                var12 = var8;
                                                var10 = var6;
                                                var8 = var9;
                                                var3 = var16;
                                                var13 = var14;
                                                var14 = var15;
                                                var16 = var19;
                                                break label279;
                                             }
                                             break label281;
                                          case 4:
                                          case 11:
                                             if (var20 != 0) {
                                                break label281;
                                             }

                                             var15 = zziy.zza(var2, var19, var6);
                                             ((Unsafe)var11).putInt(var7, var23, var6.zznk);
                                             break label235;
                                          case 5:
                                          case 14:
                                             if (var20 != 1) {
                                                break label281;
                                             }

                                             ((Unsafe)var11).putLong(var1, var23, zziy.zzb(var2, var19));
                                             break;
                                          case 6:
                                          case 13:
                                             if (var20 != 5) {
                                                break label281;
                                             }

                                             ((Unsafe)var11).putInt(var7, var23, zziy.zza(var2, var19));
                                             var15 = var19 + 4;
                                             break label233;
                                          case 7:
                                             if (var20 != 0) {
                                                break label281;
                                             }

                                             var15 = zziy.zzb(var2, var19, var6);
                                             boolean var26;
                                             if (var6.zznl != 0L) {
                                                var26 = true;
                                             } else {
                                                var26 = false;
                                             }

                                             zznd.zza(var7, var23, var26);
                                             var16 = var12 | var25;
                                             break label236;
                                          case 8:
                                             if (var20 != 2) {
                                                break label281;
                                             }

                                             if ((var21 & 536870912) == 0) {
                                                var15 = zziy.zzc(var2, var19, var6);
                                             } else {
                                                var15 = zziy.zzd(var2, var19, var6);
                                             }

                                             ((Unsafe)var11).putObject(var7, var23, var6.zznm);
                                             break label233;
                                          case 9:
                                             if (var20 != 2) {
                                                break label281;
                                             }

                                             var15 = zziy.zza(this.zzap(var14), var2, var19, var4, var6);
                                             if ((var12 & var25) == 0) {
                                                ((Unsafe)var11).putObject(var7, var23, var6.zznm);
                                             } else {
                                                ((Unsafe)var11).putObject(var7, var23, zzkm.zza(((Unsafe)var11).getObject(var7, var23), var6.zznm));
                                             }
                                             break label233;
                                          case 10:
                                             if (var20 != 2) {
                                                break label281;
                                             }

                                             var15 = zziy.zze(var2, var19, var6);
                                             ((Unsafe)var11).putObject(var7, var23, var6.zznm);
                                             break label234;
                                          case 12:
                                             if (var20 != 0) {
                                                break label281;
                                             }

                                             var15 = zziy.zza(var2, var19, var6);
                                             var16 = var6.zznk;
                                             zzko var35 = this.zzar(var14);
                                             if (var35 != null && !var35.zzan(var16)) {
                                                zzo(var1).zzb(var3, (long)var16);
                                                var16 = var12;
                                                break label239;
                                             }

                                             ((Unsafe)var11).putInt(var7, var23, var16);
                                             break label234;
                                          case 15:
                                             if (var20 != 0) {
                                                break label281;
                                             }

                                             var15 = zziy.zza(var2, var19, var6);
                                             ((Unsafe)var11).putInt(var7, var23, zzjo.zzw(var6.zznk));
                                             break label234;
                                          case 16:
                                             if (var20 == 0) {
                                                var15 = zziy.zzb(var2, var19, var6);
                                                ((Unsafe)var11).putLong(var1, var23, zzjo.zzk(var6.zznl));
                                                var16 = var12 | var25;
                                                break label239;
                                             }
                                             break label281;
                                          case 17:
                                             if (var20 == 3) {
                                                var13 = zziy.zza(this.zzap(var14), var2, var19, var4, var8 << 3 | 4, var6);
                                                if ((var12 & var25) == 0) {
                                                   ((Unsafe)var11).putObject(var7, var23, var6.zznm);
                                                } else {
                                                   ((Unsafe)var11).putObject(var7, var23, zzkm.zza(((Unsafe)var11).getObject(var7, var23), var6.zznm));
                                                }

                                                var16 = var12 | var25;
                                                var15 = var3;
                                                var12 = var8;
                                                var10 = var6;
                                                var3 = var13;
                                                var13 = var14;
                                                var14 = var15;
                                                var15 = var16;
                                                var16 = var9;
                                                var9 = var5;
                                                var8 = var4;
                                                continue;
                                             }
                                          default:
                                             break label281;
                                          }

                                          var15 = var19 + 8;
                                          break label235;
                                       }

                                       var16 = var12 | var25;
                                       break label236;
                                    }

                                    var16 = var12 | var25;
                                    break label239;
                                 }

                                 var16 = var12 | var25;
                                 break label239;
                              }

                              var25 = var3;
                              var12 = var8;
                              var10 = var6;
                              var8 = var4;
                              var3 = var15;
                              var13 = var14;
                              var14 = var25;
                              var15 = var16;
                              var16 = var9;
                              var9 = var5;
                              continue;
                           }

                           var16 = var19;
                           var19 = var14;
                           var13 = var8;
                           var15 = var3;
                           var3 = var5;
                           var14 = var16;
                           var8 = var12;
                           var16 = var9;
                           var9 = var19;
                           var12 = var13;
                           break label268;
                        }

                        var13 = var14;
                        var12 = var8;
                        var10 = var6;
                        var8 = var9;
                        var14 = var3;
                        var3 = var15;
                        break label279;
                     }
                  } else {
                     label251: {
                        if (var22 == 27) {
                           if (var20 == 2) {
                              zzkp var27 = (zzkp)((Unsafe)var11).getObject(var7, var23);
                              zzkp var37 = var27;
                              if (!var27.zzbo()) {
                                 var9 = var27.size();
                                 if (var9 == 0) {
                                    var9 = 10;
                                 } else {
                                    var9 <<= 1;
                                 }

                                 var37 = var27.zzr(var9);
                                 ((Unsafe)var11).putObject(var7, var23, var37);
                              }

                              var13 = zziy.zza(this.zzap(var14), var3, var2, var19, var4, var37, var6);
                              var9 = var5;
                              var12 = var8;
                              var19 = var3;
                              var8 = var4;
                              var3 = var13;
                              var13 = var14;
                              var14 = var19;
                              continue;
                           }
                        } else {
                           if (var22 <= 49) {
                              var13 = this.zza(var1, var2, var19, var4, var3, var8, var20, var14, (long)var21, var22, var23, var6);
                              var12 = var13;
                              if (var13 != var19) {
                                 break label255;
                              }

                              var9 = var13;
                              break label251;
                           }

                           var25 = var8;
                           var12 = var14;
                           if (var22 != 50) {
                              var13 = this.zza(var1, var2, var19, var4, var3, var8, var20, var21, var22, var23, var14, var6);
                              if (var13 != var19) {
                                 var7 = var1;
                                 var8 = var4;
                                 var14 = var3;
                                 int var29 = var16;
                                 var16 = var12;
                                 var15 = var15;
                                 var10 = var6;
                                 var3 = var13;
                                 var12 = var25;
                                 var13 = var16;
                                 var16 = var29;
                                 var9 = var5;
                                 continue;
                              }

                              var9 = var13;
                              break label251;
                           }

                           if (var20 == 2) {
                              var13 = this.zza(var1, var2, var19, var4, var14, var23, var6);
                              var12 = var13;
                              if (var13 != var19) {
                                 break label255;
                              }

                              var9 = var13;
                              break label251;
                           }
                        }

                        var9 = var19;
                     }

                     var12 = var8;
                     var13 = var14;
                     var8 = var15;
                     var15 = var3;
                     var14 = var9;
                     var3 = var5;
                     var9 = var13;
                  }
               }

               if (var15 == var3 && var3 != 0) {
                  var33 = this;
                  var5 = var14;
                  var14 = var3;
                  var3 = var15;
                  var32 = var1;
                  break;
               }

               label181: {
                  if (this.zzui) {
                     var10 = var6;
                     if (var6.zznn != zzjx.zzci()) {
                        var11 = this.zzuh;
                        if (var6.zznn.zza((zzlq)var11, var12) != null) {
                           zzkk.zzc var30 = (zzkk.zzc)var1;
                           var30.zzdg();
                           zzkb var31 = var30.zzrw;
                           throw new NoSuchMethodError();
                        }

                        var14 = zziy.zza(var15, var2, var14, var4, zzo(var1), var6);
                        var7 = var1;
                        break label181;
                     }
                  }

                  var14 = zziy.zza(var15, var2, var14, var4, zzo(var1), var6);
                  var10 = var6;
                  var7 = var1;
               }

               var11 = var11;
               var13 = var9;
               var9 = var3;
               var3 = var14;
               var14 = var15;
               var15 = var8;
               var8 = var4;
               continue;
            }

            var9 = var5;
            var15 = var16;
            var16 = var8;
            var8 = var4;
            continue;
         }

         var7 = var1;
         var10 = var6;
         var15 = var15;
         var9 = var8;
         var8 = var3;
         var3 = var12;
         var12 = var9;
         var13 = var14;
         var14 = var8;
         var9 = var5;
         var8 = var4;
      }

      if (var16 != -1) {
         ((Unsafe)var11).putInt(var32, (long)var16, var8);
      }

      var15 = var33.zzun;

      for(var1 = null; var15 < var33.zzuo; ++var15) {
         var8 = var33.zzum[var15];
         zzmx var36 = var33.zzur;
         var9 = var33.zzud[var8];
         var11 = zznd.zzo(var32, (long)(var33.zzas(var8) & 1048575));
         if (var11 != null) {
            zzko var34 = var33.zzar(var8);
            if (var34 != null) {
               var1 = this.zza(var8, var9, var33.zzut.zzh(var11), var34, var1, var36);
            }
         }

         var1 = (zzmy)var1;
      }

      if (var1 != null) {
         var33.zzur.zzf(var32, var1);
      }

      if (var14 == 0) {
         if (var5 != var4) {
            throw zzkq.zzdm();
         }
      } else if (var5 > var4 || var3 != var14) {
         throw zzkq.zzdm();
      }

      return var5;
   }

   public final void zza(T var1, zzns var2) throws IOException {
      zzkb var3;
      Iterator var4;
      Object var5;
      int var6;
      int var7;
      int var8;
      Object var10;
      if (var2.zzcd() == zzkk.zze.zzsj) {
         label365: {
            zza(this.zzur, var1, var2);
            if (this.zzui) {
               var3 = this.zzus.zzb(var1);
               if (!var3.zzos.isEmpty()) {
                  var4 = var3.descendingIterator();
                  var5 = (Entry)var4.next();
                  break label365;
               }
            }

            var4 = null;
            var5 = var4;
         }

         var6 = this.zzud.length - 3;

         while(true) {
            var10 = var5;
            if (var6 < 0) {
               while(var10 != null) {
                  this.zzus.zza(var2, (Entry)var10);
                  if (var4.hasNext()) {
                     var10 = (Entry)var4.next();
                  } else {
                     var10 = null;
                  }
               }

               return;
            }

            var7 = this.zzas(var6);
            var8 = this.zzud[var6];

            while(var5 != null && this.zzus.zza((Entry)var5) > var8) {
               this.zzus.zza(var2, (Entry)var5);
               if (var4.hasNext()) {
                  var5 = (Entry)var4.next();
               } else {
                  var5 = null;
               }
            }

            switch((var7 & 267386880) >>> 20) {
            case 0:
               if (this.zza(var1, var6)) {
                  var2.zza(var8, zznd.zzn(var1, (long)(var7 & 1048575)));
               }
               break;
            case 1:
               if (this.zza(var1, var6)) {
                  var2.zza(var8, zznd.zzm(var1, (long)(var7 & 1048575)));
               }
               break;
            case 2:
               if (this.zza(var1, var6)) {
                  var2.zzi(var8, zznd.zzk(var1, (long)(var7 & 1048575)));
               }
               break;
            case 3:
               if (this.zza(var1, var6)) {
                  var2.zza(var8, zznd.zzk(var1, (long)(var7 & 1048575)));
               }
               break;
            case 4:
               if (this.zza(var1, var6)) {
                  var2.zzc(var8, zznd.zzj(var1, (long)(var7 & 1048575)));
               }
               break;
            case 5:
               if (this.zza(var1, var6)) {
                  var2.zzc(var8, zznd.zzk(var1, (long)(var7 & 1048575)));
               }
               break;
            case 6:
               if (this.zza(var1, var6)) {
                  var2.zzf(var8, zznd.zzj(var1, (long)(var7 & 1048575)));
               }
               break;
            case 7:
               if (this.zza(var1, var6)) {
                  var2.zzb(var8, zznd.zzl(var1, (long)(var7 & 1048575)));
               }
               break;
            case 8:
               if (this.zza(var1, var6)) {
                  zza(var8, zznd.zzo(var1, (long)(var7 & 1048575)), var2);
               }
               break;
            case 9:
               if (this.zza(var1, var6)) {
                  var2.zza(var8, zznd.zzo(var1, (long)(var7 & 1048575)), this.zzap(var6));
               }
               break;
            case 10:
               if (this.zza(var1, var6)) {
                  var2.zza(var8, (zzjc)zznd.zzo(var1, (long)(var7 & 1048575)));
               }
               break;
            case 11:
               if (this.zza(var1, var6)) {
                  var2.zzd(var8, zznd.zzj(var1, (long)(var7 & 1048575)));
               }
               break;
            case 12:
               if (this.zza(var1, var6)) {
                  var2.zzn(var8, zznd.zzj(var1, (long)(var7 & 1048575)));
               }
               break;
            case 13:
               if (this.zza(var1, var6)) {
                  var2.zzm(var8, zznd.zzj(var1, (long)(var7 & 1048575)));
               }
               break;
            case 14:
               if (this.zza(var1, var6)) {
                  var2.zzj(var8, zznd.zzk(var1, (long)(var7 & 1048575)));
               }
               break;
            case 15:
               if (this.zza(var1, var6)) {
                  var2.zze(var8, zznd.zzj(var1, (long)(var7 & 1048575)));
               }
               break;
            case 16:
               if (this.zza(var1, var6)) {
                  var2.zzb(var8, zznd.zzk(var1, (long)(var7 & 1048575)));
               }
               break;
            case 17:
               if (this.zza(var1, var6)) {
                  var2.zzb(var8, zznd.zzo(var1, (long)(var7 & 1048575)), this.zzap(var6));
               }
               break;
            case 18:
               zzmh.zza(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 19:
               zzmh.zzb(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 20:
               zzmh.zzc(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 21:
               zzmh.zzd(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 22:
               zzmh.zzh(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 23:
               zzmh.zzf(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 24:
               zzmh.zzk(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 25:
               zzmh.zzn(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 26:
               zzmh.zza(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2);
               break;
            case 27:
               zzmh.zza(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, this.zzap(var6));
               break;
            case 28:
               zzmh.zzb(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2);
               break;
            case 29:
               zzmh.zzi(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 30:
               zzmh.zzm(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 31:
               zzmh.zzl(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 32:
               zzmh.zzg(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 33:
               zzmh.zzj(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 34:
               zzmh.zze(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, false);
               break;
            case 35:
               zzmh.zza(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 36:
               zzmh.zzb(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 37:
               zzmh.zzc(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 38:
               zzmh.zzd(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 39:
               zzmh.zzh(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 40:
               zzmh.zzf(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 41:
               zzmh.zzk(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 42:
               zzmh.zzn(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 43:
               zzmh.zzi(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 44:
               zzmh.zzm(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 45:
               zzmh.zzl(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 46:
               zzmh.zzg(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 47:
               zzmh.zzj(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 48:
               zzmh.zze(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, true);
               break;
            case 49:
               zzmh.zzb(this.zzud[var6], (List)zznd.zzo(var1, (long)(var7 & 1048575)), var2, this.zzap(var6));
               break;
            case 50:
               this.zza(var2, var8, zznd.zzo(var1, (long)(var7 & 1048575)), var6);
               break;
            case 51:
               if (this.zza(var1, var8, var6)) {
                  var2.zza(var8, zze(var1, (long)(var7 & 1048575)));
               }
               break;
            case 52:
               if (this.zza(var1, var8, var6)) {
                  var2.zza(var8, zzf(var1, (long)(var7 & 1048575)));
               }
               break;
            case 53:
               if (this.zza(var1, var8, var6)) {
                  var2.zzi(var8, zzh(var1, (long)(var7 & 1048575)));
               }
               break;
            case 54:
               if (this.zza(var1, var8, var6)) {
                  var2.zza(var8, zzh(var1, (long)(var7 & 1048575)));
               }
               break;
            case 55:
               if (this.zza(var1, var8, var6)) {
                  var2.zzc(var8, zzg(var1, (long)(var7 & 1048575)));
               }
               break;
            case 56:
               if (this.zza(var1, var8, var6)) {
                  var2.zzc(var8, zzh(var1, (long)(var7 & 1048575)));
               }
               break;
            case 57:
               if (this.zza(var1, var8, var6)) {
                  var2.zzf(var8, zzg(var1, (long)(var7 & 1048575)));
               }
               break;
            case 58:
               if (this.zza(var1, var8, var6)) {
                  var2.zzb(var8, zzi(var1, (long)(var7 & 1048575)));
               }
               break;
            case 59:
               if (this.zza(var1, var8, var6)) {
                  zza(var8, zznd.zzo(var1, (long)(var7 & 1048575)), var2);
               }
               break;
            case 60:
               if (this.zza(var1, var8, var6)) {
                  var2.zza(var8, zznd.zzo(var1, (long)(var7 & 1048575)), this.zzap(var6));
               }
               break;
            case 61:
               if (this.zza(var1, var8, var6)) {
                  var2.zza(var8, (zzjc)zznd.zzo(var1, (long)(var7 & 1048575)));
               }
               break;
            case 62:
               if (this.zza(var1, var8, var6)) {
                  var2.zzd(var8, zzg(var1, (long)(var7 & 1048575)));
               }
               break;
            case 63:
               if (this.zza(var1, var8, var6)) {
                  var2.zzn(var8, zzg(var1, (long)(var7 & 1048575)));
               }
               break;
            case 64:
               if (this.zza(var1, var8, var6)) {
                  var2.zzm(var8, zzg(var1, (long)(var7 & 1048575)));
               }
               break;
            case 65:
               if (this.zza(var1, var8, var6)) {
                  var2.zzj(var8, zzh(var1, (long)(var7 & 1048575)));
               }
               break;
            case 66:
               if (this.zza(var1, var8, var6)) {
                  var2.zze(var8, zzg(var1, (long)(var7 & 1048575)));
               }
               break;
            case 67:
               if (this.zza(var1, var8, var6)) {
                  var2.zzb(var8, zzh(var1, (long)(var7 & 1048575)));
               }
               break;
            case 68:
               if (this.zza(var1, var8, var6)) {
                  var2.zzb(var8, zznd.zzo(var1, (long)(var7 & 1048575)), this.zzap(var6));
               }
            }

            var6 -= 3;
         }
      } else if (!this.zzuk) {
         this.zzb(var1, var2);
      } else {
         label380: {
            if (this.zzui) {
               var3 = this.zzus.zzb(var1);
               if (!var3.zzos.isEmpty()) {
                  var4 = var3.iterator();
                  var10 = (Entry)var4.next();
                  break label380;
               }
            }

            var4 = null;
            var10 = var4;
         }

         var7 = this.zzud.length;
         var6 = 0;

         while(true) {
            var5 = var10;
            if (var6 >= var7) {
               while(var5 != null) {
                  this.zzus.zza(var2, (Entry)var5);
                  if (var4.hasNext()) {
                     var5 = (Entry)var4.next();
                  } else {
                     var5 = null;
                  }
               }

               zza(this.zzur, var1, var2);
               return;
            }

            int var9 = this.zzas(var6);
            var8 = this.zzud[var6];

            while(var10 != null && this.zzus.zza((Entry)var10) <= var8) {
               this.zzus.zza(var2, (Entry)var10);
               if (var4.hasNext()) {
                  var10 = (Entry)var4.next();
               } else {
                  var10 = null;
               }
            }

            switch((var9 & 267386880) >>> 20) {
            case 0:
               if (this.zza(var1, var6)) {
                  var2.zza(var8, zznd.zzn(var1, (long)(var9 & 1048575)));
               }
               break;
            case 1:
               if (this.zza(var1, var6)) {
                  var2.zza(var8, zznd.zzm(var1, (long)(var9 & 1048575)));
               }
               break;
            case 2:
               if (this.zza(var1, var6)) {
                  var2.zzi(var8, zznd.zzk(var1, (long)(var9 & 1048575)));
               }
               break;
            case 3:
               if (this.zza(var1, var6)) {
                  var2.zza(var8, zznd.zzk(var1, (long)(var9 & 1048575)));
               }
               break;
            case 4:
               if (this.zza(var1, var6)) {
                  var2.zzc(var8, zznd.zzj(var1, (long)(var9 & 1048575)));
               }
               break;
            case 5:
               if (this.zza(var1, var6)) {
                  var2.zzc(var8, zznd.zzk(var1, (long)(var9 & 1048575)));
               }
               break;
            case 6:
               if (this.zza(var1, var6)) {
                  var2.zzf(var8, zznd.zzj(var1, (long)(var9 & 1048575)));
               }
               break;
            case 7:
               if (this.zza(var1, var6)) {
                  var2.zzb(var8, zznd.zzl(var1, (long)(var9 & 1048575)));
               }
               break;
            case 8:
               if (this.zza(var1, var6)) {
                  zza(var8, zznd.zzo(var1, (long)(var9 & 1048575)), var2);
               }
               break;
            case 9:
               if (this.zza(var1, var6)) {
                  var2.zza(var8, zznd.zzo(var1, (long)(var9 & 1048575)), this.zzap(var6));
               }
               break;
            case 10:
               if (this.zza(var1, var6)) {
                  var2.zza(var8, (zzjc)zznd.zzo(var1, (long)(var9 & 1048575)));
               }
               break;
            case 11:
               if (this.zza(var1, var6)) {
                  var2.zzd(var8, zznd.zzj(var1, (long)(var9 & 1048575)));
               }
               break;
            case 12:
               if (this.zza(var1, var6)) {
                  var2.zzn(var8, zznd.zzj(var1, (long)(var9 & 1048575)));
               }
               break;
            case 13:
               if (this.zza(var1, var6)) {
                  var2.zzm(var8, zznd.zzj(var1, (long)(var9 & 1048575)));
               }
               break;
            case 14:
               if (this.zza(var1, var6)) {
                  var2.zzj(var8, zznd.zzk(var1, (long)(var9 & 1048575)));
               }
               break;
            case 15:
               if (this.zza(var1, var6)) {
                  var2.zze(var8, zznd.zzj(var1, (long)(var9 & 1048575)));
               }
               break;
            case 16:
               if (this.zza(var1, var6)) {
                  var2.zzb(var8, zznd.zzk(var1, (long)(var9 & 1048575)));
               }
               break;
            case 17:
               if (this.zza(var1, var6)) {
                  var2.zzb(var8, zznd.zzo(var1, (long)(var9 & 1048575)), this.zzap(var6));
               }
               break;
            case 18:
               zzmh.zza(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 19:
               zzmh.zzb(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 20:
               zzmh.zzc(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 21:
               zzmh.zzd(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 22:
               zzmh.zzh(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 23:
               zzmh.zzf(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 24:
               zzmh.zzk(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 25:
               zzmh.zzn(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 26:
               zzmh.zza(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2);
               break;
            case 27:
               zzmh.zza(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, this.zzap(var6));
               break;
            case 28:
               zzmh.zzb(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2);
               break;
            case 29:
               zzmh.zzi(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 30:
               zzmh.zzm(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 31:
               zzmh.zzl(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 32:
               zzmh.zzg(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 33:
               zzmh.zzj(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 34:
               zzmh.zze(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, false);
               break;
            case 35:
               zzmh.zza(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 36:
               zzmh.zzb(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 37:
               zzmh.zzc(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 38:
               zzmh.zzd(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 39:
               zzmh.zzh(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 40:
               zzmh.zzf(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 41:
               zzmh.zzk(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 42:
               zzmh.zzn(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 43:
               zzmh.zzi(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 44:
               zzmh.zzm(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 45:
               zzmh.zzl(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 46:
               zzmh.zzg(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 47:
               zzmh.zzj(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 48:
               zzmh.zze(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, true);
               break;
            case 49:
               zzmh.zzb(this.zzud[var6], (List)zznd.zzo(var1, (long)(var9 & 1048575)), var2, this.zzap(var6));
               break;
            case 50:
               this.zza(var2, var8, zznd.zzo(var1, (long)(var9 & 1048575)), var6);
               break;
            case 51:
               if (this.zza(var1, var8, var6)) {
                  var2.zza(var8, zze(var1, (long)(var9 & 1048575)));
               }
               break;
            case 52:
               if (this.zza(var1, var8, var6)) {
                  var2.zza(var8, zzf(var1, (long)(var9 & 1048575)));
               }
               break;
            case 53:
               if (this.zza(var1, var8, var6)) {
                  var2.zzi(var8, zzh(var1, (long)(var9 & 1048575)));
               }
               break;
            case 54:
               if (this.zza(var1, var8, var6)) {
                  var2.zza(var8, zzh(var1, (long)(var9 & 1048575)));
               }
               break;
            case 55:
               if (this.zza(var1, var8, var6)) {
                  var2.zzc(var8, zzg(var1, (long)(var9 & 1048575)));
               }
               break;
            case 56:
               if (this.zza(var1, var8, var6)) {
                  var2.zzc(var8, zzh(var1, (long)(var9 & 1048575)));
               }
               break;
            case 57:
               if (this.zza(var1, var8, var6)) {
                  var2.zzf(var8, zzg(var1, (long)(var9 & 1048575)));
               }
               break;
            case 58:
               if (this.zza(var1, var8, var6)) {
                  var2.zzb(var8, zzi(var1, (long)(var9 & 1048575)));
               }
               break;
            case 59:
               if (this.zza(var1, var8, var6)) {
                  zza(var8, zznd.zzo(var1, (long)(var9 & 1048575)), var2);
               }
               break;
            case 60:
               if (this.zza(var1, var8, var6)) {
                  var2.zza(var8, zznd.zzo(var1, (long)(var9 & 1048575)), this.zzap(var6));
               }
               break;
            case 61:
               if (this.zza(var1, var8, var6)) {
                  var2.zza(var8, (zzjc)zznd.zzo(var1, (long)(var9 & 1048575)));
               }
               break;
            case 62:
               if (this.zza(var1, var8, var6)) {
                  var2.zzd(var8, zzg(var1, (long)(var9 & 1048575)));
               }
               break;
            case 63:
               if (this.zza(var1, var8, var6)) {
                  var2.zzn(var8, zzg(var1, (long)(var9 & 1048575)));
               }
               break;
            case 64:
               if (this.zza(var1, var8, var6)) {
                  var2.zzm(var8, zzg(var1, (long)(var9 & 1048575)));
               }
               break;
            case 65:
               if (this.zza(var1, var8, var6)) {
                  var2.zzj(var8, zzh(var1, (long)(var9 & 1048575)));
               }
               break;
            case 66:
               if (this.zza(var1, var8, var6)) {
                  var2.zze(var8, zzg(var1, (long)(var9 & 1048575)));
               }
               break;
            case 67:
               if (this.zza(var1, var8, var6)) {
                  var2.zzb(var8, zzh(var1, (long)(var9 & 1048575)));
               }
               break;
            case 68:
               if (this.zza(var1, var8, var6)) {
                  var2.zzb(var8, zznd.zzo(var1, (long)(var9 & 1048575)), this.zzap(var6));
               }
            }

            var6 += 3;
         }
      }
   }

   public final void zza(T var1, byte[] var2, int var3, int var4, zziz var5) throws IOException {
      zzlu var6 = this;
      Object var7 = var1;
      byte[] var8 = var2;
      int var9 = var4;
      zziz var10 = var5;
      if (!this.zzuk) {
         this.zza(var1, var2, var3, var4, 0, var5);
      } else {
         Unsafe var11 = zzuc;
         int var12 = var3;
         int var13 = -1;

         int var15;
         for(var3 = 0; var12 < var9; var3 = var15) {
            int var14 = var12 + 1;
            var12 = var8[var12];
            if (var12 < 0) {
               var14 = zziy.zza(var12, var8, var14, var10);
               var12 = var10.zznk;
            }

            var15 = var12 >>> 3;
            int var16 = var12 & 7;
            if (var15 > var13) {
               var3 = var6.zzp(var15, var3 / 3);
            } else {
               var3 = var6.zzau(var15);
            }

            label149: {
               label148: {
                  label147: {
                     label146: {
                        label163: {
                           if (var3 == -1) {
                              var9 = 0;
                           } else {
                              label164: {
                                 var13 = var6.zzud[var3 + 1];
                                 int var17 = (267386880 & var13) >>> 20;
                                 long var18 = (long)(1048575 & var13);
                                 if (var17 <= 17) {
                                    boolean var20 = true;
                                    switch(var17) {
                                    case 0:
                                       if (var16 == 1) {
                                          zznd.zza(var7, var18, zziy.zzc(var8, var14));
                                          break label163;
                                       }
                                       break;
                                    case 1:
                                       if (var16 == 5) {
                                          zznd.zza(var7, var18, zziy.zzd(var8, var14));
                                          var14 += 4;
                                          break label148;
                                       }
                                       break;
                                    case 2:
                                    case 3:
                                       if (var16 == 0) {
                                          var14 = zziy.zzb(var8, var14, var10);
                                          var11.putLong(var1, var18, var10.zznl);
                                          break label148;
                                       }
                                       break;
                                    case 4:
                                    case 11:
                                       if (var16 == 0) {
                                          var14 = zziy.zza(var8, var14, var10);
                                          var11.putInt(var7, var18, var10.zznk);
                                          break label148;
                                       }
                                       break;
                                    case 5:
                                    case 14:
                                       if (var16 == 1) {
                                          var11.putLong(var1, var18, zziy.zzb(var8, var14));
                                          break label163;
                                       }
                                       break;
                                    case 6:
                                    case 13:
                                       if (var16 == 5) {
                                          var11.putInt(var7, var18, zziy.zza(var8, var14));
                                          var14 += 4;
                                          break label147;
                                       }
                                       break;
                                    case 7:
                                       if (var16 == 0) {
                                          var14 = zziy.zzb(var8, var14, var10);
                                          if (var10.zznl == 0L) {
                                             var20 = false;
                                          }

                                          zznd.zza(var7, var18, var20);
                                          break label147;
                                       }
                                       break;
                                    case 8:
                                       if (var16 == 2) {
                                          if ((536870912 & var13) == 0) {
                                             var14 = zziy.zzc(var8, var14, var10);
                                          } else {
                                             var14 = zziy.zzd(var8, var14, var10);
                                          }

                                          var11.putObject(var7, var18, var10.zznm);
                                          break label147;
                                       }
                                       break;
                                    case 9:
                                       if (var16 == 2) {
                                          var14 = zziy.zza(var6.zzap(var3), var8, var14, var9, var10);
                                          Object var24 = var11.getObject(var7, var18);
                                          if (var24 == null) {
                                             var11.putObject(var7, var18, var10.zznm);
                                          } else {
                                             var11.putObject(var7, var18, zzkm.zza(var24, var10.zznm));
                                          }
                                          break label147;
                                       }
                                       break;
                                    case 10:
                                       if (var16 == 2) {
                                          var14 = zziy.zze(var8, var14, var10);
                                          var11.putObject(var7, var18, var10.zznm);
                                          break label147;
                                       }
                                       break;
                                    case 12:
                                       if (var16 == 0) {
                                          var14 = zziy.zza(var8, var14, var10);
                                          var11.putInt(var7, var18, var10.zznk);
                                          break label148;
                                       }
                                       break;
                                    case 15:
                                       if (var16 == 0) {
                                          var14 = zziy.zza(var8, var14, var10);
                                          var11.putInt(var7, var18, zzjo.zzw(var10.zznk));
                                          break label148;
                                       }
                                       break;
                                    case 16:
                                       if (var16 == 0) {
                                          var14 = zziy.zzb(var8, var14, var10);
                                          var11.putLong(var1, var18, zzjo.zzk(var10.zznl));
                                          break label148;
                                       }
                                    }
                                 } else if (var17 == 27) {
                                    if (var16 == 2) {
                                       zzkp var22 = (zzkp)var11.getObject(var7, var18);
                                       zzkp var21 = var22;
                                       if (!var22.zzbo()) {
                                          var13 = var22.size();
                                          if (var13 == 0) {
                                             var13 = 10;
                                          } else {
                                             var13 <<= 1;
                                          }

                                          var21 = var22.zzr(var13);
                                          var11.putObject(var7, var18, var21);
                                       }

                                       var13 = zziy.zza(var6.zzap(var3), var12, var2, var14, var4, var21, var5);
                                       var14 = var15;
                                       var15 = var3;
                                       var3 = var13;
                                       break label149;
                                    }
                                 } else {
                                    label162: {
                                       var9 = var3;
                                       int var23;
                                       if (var17 <= 49) {
                                          var23 = this.zza(var1, var2, var14, var4, var12, var15, var16, var3, (long)var13, var17, var18, var5);
                                          var3 = var23;
                                          var13 = var9;
                                          if (var23 != var14) {
                                             break label146;
                                          }

                                          var3 = var23;
                                       } else {
                                          var23 = var14;
                                          if (var17 == 50) {
                                             var3 = var3;
                                             if (var16 != 2) {
                                                break label162;
                                             }

                                             var14 = this.zza(var1, var2, var14, var4, var9, var18, var5);
                                             var3 = var14;
                                             var13 = var9;
                                             if (var14 != var23) {
                                                break label146;
                                             }

                                             var3 = var14;
                                          } else {
                                             var14 = this.zza(var1, var2, var14, var4, var12, var15, var16, var13, var17, var18, var3, var5);
                                             var3 = var14;
                                             var13 = var9;
                                             if (var14 != var23) {
                                                break label146;
                                             }

                                             var3 = var14;
                                          }
                                       }

                                       var14 = var3;
                                       break label164;
                                    }
                                 }

                                 var9 = var3;
                              }
                           }

                           var3 = zziy.zza(var12, var2, var14, var4, zzo(var1), var5);
                           var13 = var9;
                           break label146;
                        }

                        var14 += 8;
                        break label148;
                     }

                     var6 = this;
                     var7 = var1;
                     var8 = var2;
                     var9 = var4;
                     var10 = var5;
                     var14 = var15;
                     var15 = var13;
                     break label149;
                  }

                  var13 = var3;
                  var3 = var14;
                  var14 = var15;
                  var15 = var13;
                  break label149;
               }

               var13 = var3;
               var3 = var14;
               var14 = var15;
               var15 = var13;
            }

            var12 = var3;
            var13 = var14;
         }

         if (var12 != var9) {
            throw zzkq.zzdm();
         }
      }
   }

   public final void zzc(T var1, T var2) {
      if (var2 != null) {
         for(int var3 = 0; var3 < this.zzud.length; var3 += 3) {
            int var4 = this.zzas(var3);
            long var5 = (long)(1048575 & var4);
            int var7 = this.zzud[var3];
            switch((var4 & 267386880) >>> 20) {
            case 0:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzn(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 1:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzm(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 2:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzk(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 3:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzk(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 4:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzj(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 5:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzk(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 6:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzj(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 7:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzl(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 8:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzo(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 9:
               this.zza(var1, var2, var3);
               break;
            case 10:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzo(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 11:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzj(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 12:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzj(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 13:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzj(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 14:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzk(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 15:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzj(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 16:
               if (this.zza(var2, var3)) {
                  zznd.zza(var1, var5, zznd.zzk(var2, var5));
                  this.zzb(var1, var3);
               }
               break;
            case 17:
               this.zza(var1, var2, var3);
               break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
               this.zzuq.zza(var1, var2, var5);
               break;
            case 50:
               zzmh.zza(this.zzut, var1, var2, var5);
               break;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
               if (this.zza(var2, var7, var3)) {
                  zznd.zza(var1, var5, zznd.zzo(var2, var5));
                  this.zzb(var1, var7, var3);
               }
               break;
            case 60:
               this.zzb(var1, var2, var3);
               break;
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
               if (this.zza(var2, var7, var3)) {
                  zznd.zza(var1, var5, zznd.zzo(var2, var5));
                  this.zzb(var1, var7, var3);
               }
               break;
            case 68:
               this.zzb(var1, var2, var3);
            }
         }

         if (!this.zzuk) {
            zzmh.zza(this.zzur, var1, var2);
            if (this.zzui) {
               zzmh.zza(this.zzus, var1, var2);
            }
         }

      } else {
         throw null;
      }
   }

   public final void zzd(T var1) {
      int var2 = this.zzun;

      while(true) {
         int var3 = this.zzuo;
         if (var2 >= var3) {
            int var7 = this.zzum.length;

            for(var2 = var3; var2 < var7; ++var2) {
               this.zzuq.zza(var1, (long)this.zzum[var2]);
            }

            this.zzur.zzd(var1);
            if (this.zzui) {
               this.zzus.zzd(var1);
            }

            return;
         }

         long var4 = (long)(this.zzas(this.zzum[var2]) & 1048575);
         Object var6 = zznd.zzo(var1, var4);
         if (var6 != null) {
            zznd.zza(var1, var4, this.zzut.zzk(var6));
         }

         ++var2;
      }
   }

   public final int zzn(T var1) {
      Unsafe var2;
      int var3;
      int var4;
      int var5;
      int var6;
      int var7;
      long var8;
      int var11;
      int var12;
      Object var21;
      if (this.zzuk) {
         var2 = zzuc;
         var3 = 0;

         for(var4 = 0; var3 < this.zzud.length; var4 = var6) {
            var5 = this.zzas(var3);
            var6 = (var5 & 267386880) >>> 20;
            var7 = this.zzud[var3];
            var8 = (long)(var5 & 1048575);
            if (var6 >= zzke.zzqh.id() && var6 <= zzke.zzqu.id()) {
               var5 = this.zzud[var3 + 2] & 1048575;
            } else {
               var5 = 0;
            }

            label531: {
               label530: {
                  switch(var6) {
                  case 0:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzb(var7, 0.0D);
                     break label530;
                  case 1:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzb(var7, 0.0F);
                     break label530;
                  case 2:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzd(var7, zznd.zzk(var1, var8));
                     break label530;
                  case 3:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zze(var7, zznd.zzk(var1, var8));
                     break label530;
                  case 4:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzg(var7, zznd.zzj(var1, var8));
                     break label530;
                  case 5:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzg(var7, 0L);
                     break label530;
                  case 6:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzj(var7, 0);
                     break label530;
                  case 7:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzc(var7, true);
                     break label530;
                  case 8:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var21 = zznd.zzo(var1, var8);
                     if (var21 instanceof zzjc) {
                        var5 = zzjr.zzc(var7, (zzjc)var21);
                     } else {
                        var5 = zzjr.zzb(var7, (String)var21);
                     }
                     break label530;
                  case 9:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzmh.zzc(var7, zznd.zzo(var1, var8), this.zzap(var3));
                     break label530;
                  case 10:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzc(var7, (zzjc)zznd.zzo(var1, var8));
                     break label530;
                  case 11:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzh(var7, zznd.zzj(var1, var8));
                     break label530;
                  case 12:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzl(var7, zznd.zzj(var1, var8));
                     break label530;
                  case 13:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzk(var7, 0);
                     break label530;
                  case 14:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzh(var7, 0L);
                     break label530;
                  case 15:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzi(var7, zznd.zzj(var1, var8));
                     break label530;
                  case 16:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzf(var7, zznd.zzk(var1, var8));
                     break label530;
                  case 17:
                     var6 = var4;
                     if (!this.zza(var1, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzc(var7, (zzlq)zznd.zzo(var1, var8), this.zzap(var3));
                     break label530;
                  case 18:
                     var5 = zzmh.zzw(var7, zzd(var1, var8), false);
                     break label530;
                  case 19:
                     var5 = zzmh.zzv(var7, zzd(var1, var8), false);
                     break label530;
                  case 20:
                     var5 = zzmh.zzo(var7, zzd(var1, var8), false);
                     break label530;
                  case 21:
                     var5 = zzmh.zzp(var7, zzd(var1, var8), false);
                     break label530;
                  case 22:
                     var5 = zzmh.zzs(var7, zzd(var1, var8), false);
                     break label530;
                  case 23:
                     var5 = zzmh.zzw(var7, zzd(var1, var8), false);
                     break label530;
                  case 24:
                     var5 = zzmh.zzv(var7, zzd(var1, var8), false);
                     break label530;
                  case 25:
                     var5 = zzmh.zzx(var7, zzd(var1, var8), false);
                     break label530;
                  case 26:
                     var5 = zzmh.zzc(var7, zzd(var1, var8));
                     break label530;
                  case 27:
                     var5 = zzmh.zzc(var7, zzd(var1, var8), this.zzap(var3));
                     break label530;
                  case 28:
                     var5 = zzmh.zzd(var7, zzd(var1, var8));
                     break label530;
                  case 29:
                     var5 = zzmh.zzt(var7, zzd(var1, var8), false);
                     break label530;
                  case 30:
                     var5 = zzmh.zzr(var7, zzd(var1, var8), false);
                     break label530;
                  case 31:
                     var5 = zzmh.zzv(var7, zzd(var1, var8), false);
                     break label530;
                  case 32:
                     var5 = zzmh.zzw(var7, zzd(var1, var8), false);
                     break label530;
                  case 33:
                     var5 = zzmh.zzu(var7, zzd(var1, var8), false);
                     break label530;
                  case 34:
                     var5 = zzmh.zzq(var7, zzd(var1, var8), false);
                     break label530;
                  case 35:
                     var12 = zzmh.zzi((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var12 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var12);
                     }

                     var5 = zzjr.zzab(var7);
                     var11 = zzjr.zzad(var12);
                     var6 = var12;
                     break;
                  case 36:
                     var11 = zzmh.zzh((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 37:
                     var11 = zzmh.zza((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 38:
                     var11 = zzmh.zzb((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 39:
                     var11 = zzmh.zze((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 40:
                     var11 = zzmh.zzi((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 41:
                     var11 = zzmh.zzh((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 42:
                     var11 = zzmh.zzj((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 43:
                     var11 = zzmh.zzf((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 44:
                     var11 = zzmh.zzd((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 45:
                     var11 = zzmh.zzh((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 46:
                     var11 = zzmh.zzi((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 47:
                     var11 = zzmh.zzg((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 48:
                     var11 = zzmh.zzc((List)var2.getObject(var1, var8));
                     var6 = var4;
                     if (var11 <= 0) {
                        break label531;
                     }

                     if (this.zzul) {
                        var2.putInt(var1, (long)var5, var11);
                     }

                     var5 = zzjr.zzab(var7);
                     var12 = zzjr.zzad(var11);
                     var6 = var11;
                     var11 = var12;
                     break;
                  case 49:
                     var5 = zzmh.zzd(var7, zzd(var1, var8), this.zzap(var3));
                     break label530;
                  case 50:
                     var5 = this.zzut.zzb(var7, zznd.zzo(var1, var8), this.zzaq(var3));
                     break label530;
                  case 51:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzb(var7, 0.0D);
                     break label530;
                  case 52:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzb(var7, 0.0F);
                     break label530;
                  case 53:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzd(var7, zzh(var1, var8));
                     break label530;
                  case 54:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zze(var7, zzh(var1, var8));
                     break label530;
                  case 55:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzg(var7, zzg(var1, var8));
                     break label530;
                  case 56:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzg(var7, 0L);
                     break label530;
                  case 57:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzj(var7, 0);
                     break label530;
                  case 58:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzc(var7, true);
                     break label530;
                  case 59:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var21 = zznd.zzo(var1, var8);
                     if (var21 instanceof zzjc) {
                        var5 = zzjr.zzc(var7, (zzjc)var21);
                     } else {
                        var5 = zzjr.zzb(var7, (String)var21);
                     }
                     break label530;
                  case 60:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzmh.zzc(var7, zznd.zzo(var1, var8), this.zzap(var3));
                     break label530;
                  case 61:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzc(var7, (zzjc)zznd.zzo(var1, var8));
                     break label530;
                  case 62:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzh(var7, zzg(var1, var8));
                     break label530;
                  case 63:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzl(var7, zzg(var1, var8));
                     break label530;
                  case 64:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzk(var7, 0);
                     break label530;
                  case 65:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzh(var7, 0L);
                     break label530;
                  case 66:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzi(var7, zzg(var1, var8));
                     break label530;
                  case 67:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzf(var7, zzh(var1, var8));
                     break label530;
                  case 68:
                     var6 = var4;
                     if (!this.zza(var1, var7, var3)) {
                        break label531;
                     }

                     var5 = zzjr.zzc(var7, (zzlq)zznd.zzo(var1, var8), this.zzap(var3));
                     break label530;
                  default:
                     var6 = var4;
                     break label531;
                  }

                  var5 = var5 + var11 + var6;
               }

               var6 = var4 + var5;
            }

            var3 += 3;
         }

         return var4 + zza(this.zzur, var1);
      } else {
         var2 = zzuc;
         var6 = -1;
         var3 = 0;
         var4 = 0;

         for(var5 = 0; var3 < this.zzud.length; var5 = var12) {
            int var13 = this.zzas(var3);
            int[] var10 = this.zzud;
            int var14 = var10[var3];
            int var15 = (var13 & 267386880) >>> 20;
            int var16;
            if (var15 <= 17) {
               var12 = var10[var3 + 2];
               var7 = var12 & 1048575;
               var16 = 1 << (var12 >>> 20);
               var11 = var6;
               if (var7 != var6) {
                  var5 = var2.getInt(var1, (long)var7);
                  var11 = var7;
               }

               var7 = var11;
               var11 = var12;
               var12 = var5;
            } else {
               if (this.zzul && var15 >= zzke.zzqh.id() && var15 <= zzke.zzqu.id()) {
                  var11 = this.zzud[var3 + 2] & 1048575;
               } else {
                  var11 = 0;
               }

               var16 = 0;
               var12 = var5;
               var7 = var6;
            }

            label732: {
               label675: {
                  label674: {
                     label673: {
                        label672: {
                           var8 = (long)(var13 & 1048575);
                           switch(var15) {
                           case 0:
                              var5 = var4;
                              if ((var12 & var16) != 0) {
                                 var5 = var4 + zzjr.zzb(var14, 0.0D);
                              }
                              break label732;
                           case 1:
                              var5 = var4;
                              if ((var12 & var16) != 0) {
                                 var5 = var4 + zzjr.zzb(var14, 0.0F);
                              }
                              break label732;
                           case 2:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zzd(var14, var2.getLong(var1, var8));
                              break label672;
                           case 3:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zze(var14, var2.getLong(var1, var8));
                              break label672;
                           case 4:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zzg(var14, var2.getInt(var1, var8));
                              break label672;
                           case 5:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zzg(var14, 0L);
                              break label672;
                           case 6:
                              var5 = var4;
                              if ((var12 & var16) != 0) {
                                 var5 = var4 + zzjr.zzj(var14, 0);
                              }
                              break label732;
                           case 7:
                              var5 = var4;
                              if ((var12 & var16) != 0) {
                                 var5 = var4 + zzjr.zzc(var14, true);
                              }
                              break label732;
                           case 8:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var21 = var2.getObject(var1, var8);
                              if (var21 instanceof zzjc) {
                                 var5 = zzjr.zzc(var14, (zzjc)var21);
                              } else {
                                 var5 = zzjr.zzb(var14, (String)var21);
                              }
                              break label673;
                           case 9:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzmh.zzc(var14, var2.getObject(var1, var8), this.zzap(var3));
                              break label673;
                           case 10:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zzc(var14, (zzjc)var2.getObject(var1, var8));
                              break label673;
                           case 11:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zzh(var14, var2.getInt(var1, var8));
                              break label673;
                           case 12:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zzl(var14, var2.getInt(var1, var8));
                              break label673;
                           case 13:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zzk(var14, 0);
                              break label675;
                           case 14:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zzh(var14, 0L);
                              break label673;
                           case 15:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zzi(var14, var2.getInt(var1, var8));
                              break label673;
                           case 16:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zzf(var14, var2.getLong(var1, var8));
                              break label673;
                           case 17:
                              var5 = var4;
                              if ((var12 & var16) == 0) {
                                 break label732;
                              }

                              var5 = zzjr.zzc(var14, (zzlq)var2.getObject(var1, var8), this.zzap(var3));
                              break label673;
                           case 18:
                              var5 = zzmh.zzw(var14, (List)var2.getObject(var1, var8), false);
                              break label673;
                           case 19:
                              var5 = zzmh.zzv(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 20:
                              var5 = zzmh.zzo(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 21:
                              var5 = zzmh.zzp(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 22:
                              var5 = zzmh.zzs(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 23:
                              var5 = zzmh.zzw(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 24:
                              var5 = zzmh.zzv(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 25:
                              var5 = zzmh.zzx(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 26:
                              var5 = zzmh.zzc(var14, (List)var2.getObject(var1, var8));
                              break label673;
                           case 27:
                              var5 = zzmh.zzc(var14, (List)var2.getObject(var1, var8), this.zzap(var3));
                              break label673;
                           case 28:
                              var5 = zzmh.zzd(var14, (List)var2.getObject(var1, var8));
                              break label673;
                           case 29:
                              var5 = zzmh.zzt(var14, (List)var2.getObject(var1, var8), false);
                              break label673;
                           case 30:
                              var5 = zzmh.zzr(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 31:
                              var5 = zzmh.zzv(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 32:
                              var5 = zzmh.zzw(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 33:
                              var5 = zzmh.zzu(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 34:
                              var5 = zzmh.zzq(var14, (List)var2.getObject(var1, var8), false);
                              break label674;
                           case 35:
                              var16 = zzmh.zzi((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var16 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var16);
                              }

                              var6 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var16);
                              var5 = var16;
                              break;
                           case 36:
                              var6 = zzmh.zzh((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 37:
                              var6 = zzmh.zza((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 38:
                              var6 = zzmh.zzb((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 39:
                              var6 = zzmh.zze((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 40:
                              var6 = zzmh.zzi((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 41:
                              var6 = zzmh.zzh((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 42:
                              var6 = zzmh.zzj((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 43:
                              var6 = zzmh.zzf((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 44:
                              var6 = zzmh.zzd((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 45:
                              var6 = zzmh.zzh((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 46:
                              var6 = zzmh.zzi((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 47:
                              var6 = zzmh.zzg((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 48:
                              var6 = zzmh.zzc((List)var2.getObject(var1, var8));
                              var5 = var4;
                              if (var6 <= 0) {
                                 break label732;
                              }

                              if (this.zzul) {
                                 var2.putInt(var1, (long)var11, var6);
                              }

                              var16 = zzjr.zzab(var14);
                              var11 = zzjr.zzad(var6);
                              var5 = var6;
                              var6 = var16;
                              break;
                           case 49:
                              var5 = zzmh.zzd(var14, (List)var2.getObject(var1, var8), this.zzap(var3));
                              break label673;
                           case 50:
                              var5 = this.zzut.zzb(var14, var2.getObject(var1, var8), this.zzaq(var3));
                              break label673;
                           case 51:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzb(var14, 0.0D);
                              break label673;
                           case 52:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzb(var14, 0.0F);
                              break label675;
                           case 53:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzd(var14, zzh(var1, var8));
                              break label673;
                           case 54:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zze(var14, zzh(var1, var8));
                              break label673;
                           case 55:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzg(var14, zzg(var1, var8));
                              break label673;
                           case 56:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzg(var14, 0L);
                              break label673;
                           case 57:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzj(var14, 0);
                              break label675;
                           case 58:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzc(var14, true);
                              break label675;
                           case 59:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var21 = var2.getObject(var1, var8);
                              if (var21 instanceof zzjc) {
                                 var5 = zzjr.zzc(var14, (zzjc)var21);
                              } else {
                                 var5 = zzjr.zzb(var14, (String)var21);
                              }
                              break label673;
                           case 60:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzmh.zzc(var14, var2.getObject(var1, var8), this.zzap(var3));
                              break label673;
                           case 61:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzc(var14, (zzjc)var2.getObject(var1, var8));
                              break label673;
                           case 62:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzh(var14, zzg(var1, var8));
                              break label673;
                           case 63:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzl(var14, zzg(var1, var8));
                              break label673;
                           case 64:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzk(var14, 0);
                              break label675;
                           case 65:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzh(var14, 0L);
                              break label673;
                           case 66:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzi(var14, zzg(var1, var8));
                              break label673;
                           case 67:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzf(var14, zzh(var1, var8));
                              break label673;
                           case 68:
                              var5 = var4;
                              if (!this.zza(var1, var14, var3)) {
                                 break label732;
                              }

                              var5 = zzjr.zzc(var14, (zzlq)var2.getObject(var1, var8), this.zzap(var3));
                              break label673;
                           default:
                              var5 = var4;
                              break label732;
                           }

                           var5 += var6 + var11;
                           break label675;
                        }

                        var5 += var4;
                        break label732;
                     }

                     var5 += var4;
                     break label732;
                  }

                  var5 += var4;
                  break label732;
               }

               var5 += var4;
            }

            var3 += 3;
            var6 = var7;
            var4 = var5;
         }

         byte var20 = 0;
         var11 = var4 + zza(this.zzur, var1);
         var5 = var11;
         if (this.zzui) {
            zzkb var17 = this.zzus.zzb(var1);
            var6 = 0;

            Entry var19;
            for(var5 = var20; var6 < var17.zzos.zzer(); ++var6) {
               var19 = var17.zzos.zzaw(var6);
               var5 += zzkb.zzb((zzkd)var19.getKey(), var19.getValue());
            }

            for(Iterator var18 = var17.zzos.zzes().iterator(); var18.hasNext(); var5 += zzkb.zzb((zzkd)var19.getKey(), var19.getValue())) {
               var19 = (Entry)var18.next();
            }

            var5 += var11;
         }

         return var5;
      }
   }

   public final boolean zzp(T var1) {
      int var2 = -1;
      int var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = this.zzun;
         boolean var6 = true;
         boolean var7 = true;
         if (var3 >= var5) {
            if (this.zzui && !this.zzus.zzb(var1).isInitialized()) {
               return false;
            }

            return true;
         }

         int var8 = this.zzum[var3];
         int var9 = this.zzud[var8];
         int var10 = this.zzas(var8);
         int var13;
         if (!this.zzuk) {
            var5 = this.zzud[var8 + 2];
            int var11 = var5 & 1048575;
            int var12 = 1 << (var5 >>> 20);
            var5 = var2;
            var13 = var12;
            if (var11 != var2) {
               var4 = zzuc.getInt(var1, (long)var11);
               var5 = var11;
               var13 = var12;
            }
         } else {
            var13 = 0;
            var5 = var2;
         }

         boolean var18;
         if ((268435456 & var10) != 0) {
            var18 = true;
         } else {
            var18 = false;
         }

         if (var18 && !this.zza(var1, var8, var4, var13)) {
            return false;
         }

         var2 = (267386880 & var10) >>> 20;
         if (var2 != 9 && var2 != 17) {
            label119: {
               zzmf var14;
               boolean var20;
               if (var2 != 27) {
                  if (var2 == 60 || var2 == 68) {
                     if (this.zza(var1, var9, var8) && !zza(var1, var10, this.zzap(var8))) {
                        return false;
                     }
                     break label119;
                  }

                  if (var2 != 49) {
                     if (var2 != 50) {
                        break label119;
                     }

                     Map var19 = this.zzut.zzi(zznd.zzo(var1, (long)(var10 & 1048575)));
                     var20 = var7;
                     if (!var19.isEmpty()) {
                        Object var21 = this.zzaq(var8);
                        var20 = var7;
                        if (this.zzut.zzm(var21).zztw.zzfj() == zznr.zzxx) {
                           zzmf var22 = null;
                           Iterator var16 = var19.values().iterator();

                           while(true) {
                              var20 = var7;
                              if (!var16.hasNext()) {
                                 break;
                              }

                              Object var17 = var16.next();
                              var14 = var22;
                              if (var22 == null) {
                                 var14 = zzmd.zzej().zzf(var17.getClass());
                              }

                              var22 = var14;
                              if (!var14.zzp(var17)) {
                                 var20 = false;
                                 break;
                              }
                           }
                        }
                     }

                     if (!var20) {
                        return false;
                     }
                     break label119;
                  }
               }

               List var15 = (List)zznd.zzo(var1, (long)(var10 & 1048575));
               var20 = var6;
               if (!var15.isEmpty()) {
                  var14 = this.zzap(var8);
                  var2 = 0;

                  while(true) {
                     var20 = var6;
                     if (var2 >= var15.size()) {
                        break;
                     }

                     if (!var14.zzp(var15.get(var2))) {
                        var20 = false;
                        break;
                     }

                     ++var2;
                  }
               }

               if (!var20) {
                  return false;
               }
            }
         } else if (this.zza(var1, var8, var4, var13) && !zza(var1, var10, this.zzap(var8))) {
            return false;
         }

         ++var3;
         var2 = var5;
      }
   }
}
