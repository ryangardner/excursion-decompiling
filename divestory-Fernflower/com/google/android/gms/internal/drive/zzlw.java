package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

final class zzlw<T> implements zzmf<T> {
   private final zzlq zzuh;
   private final boolean zzui;
   private final zzmx<?, ?> zzur;
   private final zzjy<?> zzus;

   private zzlw(zzmx<?, ?> var1, zzjy<?> var2, zzlq var3) {
      this.zzur = var1;
      this.zzui = var2.zze(var3);
      this.zzus = var2;
      this.zzuh = var3;
   }

   static <T> zzlw<T> zza(zzmx<?, ?> var0, zzjy<?> var1, zzlq var2) {
      return new zzlw(var0, var1, var2);
   }

   public final boolean equals(T var1, T var2) {
      if (!this.zzur.zzr(var1).equals(this.zzur.zzr(var2))) {
         return false;
      } else {
         return this.zzui ? this.zzus.zzb(var1).equals(this.zzus.zzb(var2)) : true;
      }
   }

   public final int hashCode(T var1) {
      int var2 = this.zzur.zzr(var1).hashCode();
      int var3 = var2;
      if (this.zzui) {
         var3 = var2 * 53 + this.zzus.zzb(var1).hashCode();
      }

      return var3;
   }

   public final T newInstance() {
      return this.zzuh.zzcz().zzde();
   }

   public final void zza(T var1, zzns var2) throws IOException {
      Iterator var3 = this.zzus.zzb(var1).iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         zzkd var5 = (zzkd)var4.getKey();
         if (var5.zzcr() != zznr.zzxx || var5.zzcs() || var5.zzct()) {
            throw new IllegalStateException("Found invalid MessageSet item.");
         }

         if (var4 instanceof zzkv) {
            var2.zza(var5.zzcp(), (Object)((zzkv)var4).zzdq().zzbl());
         } else {
            var2.zza(var5.zzcp(), var4.getValue());
         }
      }

      zzmx var6 = this.zzur;
      var6.zzc(var6.zzr(var1), var2);
   }

   public final void zza(T var1, byte[] var2, int var3, int var4, zziz var5) throws IOException {
      zzkk var6 = (zzkk)var1;
      zzmy var7 = var6.zzrq;
      zzmy var8 = var7;
      if (var7 == zzmy.zzfa()) {
         var8 = zzmy.zzfb();
         var6.zzrq = var8;
      }

      ((zzkk.zzc)var1).zzdg();
      zzkk.zzd var13 = null;

      while(true) {
         while(var3 < var4) {
            var3 = zziy.zza(var2, var3, var5);
            int var9 = var5.zznk;
            if (var9 != 11) {
               if ((var9 & 7) == 2) {
                  var13 = (zzkk.zzd)this.zzus.zza(var5.zznn, this.zzuh, var9 >>> 3);
                  if (var13 != null) {
                     zzmd.zzej();
                     throw new NoSuchMethodError();
                  }

                  var3 = zziy.zza(var9, var2, var3, var4, var8, var5);
               } else {
                  var3 = zziy.zza(var9, var2, var3, var4, var5);
               }
            } else {
               int var10 = 0;
               zzjc var14 = null;

               while(true) {
                  var9 = var3;
                  if (var3 >= var4) {
                     break;
                  }

                  var3 = zziy.zza(var2, var3, var5);
                  int var11 = var5.zznk;
                  int var12 = var11 >>> 3;
                  var9 = var11 & 7;
                  if (var12 != 2) {
                     if (var12 == 3) {
                        if (var13 != null) {
                           zzmd.zzej();
                           throw new NoSuchMethodError();
                        }

                        if (var9 == 2) {
                           var3 = zziy.zze(var2, var3, var5);
                           var14 = (zzjc)var5.zznm;
                           continue;
                        }
                     }
                  } else if (var9 == 0) {
                     var3 = zziy.zza(var2, var3, var5);
                     var10 = var5.zznk;
                     var13 = (zzkk.zzd)this.zzus.zza(var5.zznn, this.zzuh, var10);
                     continue;
                  }

                  var9 = var3;
                  if (var11 == 12) {
                     break;
                  }

                  var3 = zziy.zza(var11, var2, var3, var4, var5);
               }

               if (var14 != null) {
                  var8.zzb(var10 << 3 | 2, var14);
               }

               var3 = var9;
            }
         }

         if (var3 == var4) {
            return;
         }

         throw zzkq.zzdm();
      }
   }

   public final void zzc(T var1, T var2) {
      zzmh.zza(this.zzur, var1, var2);
      if (this.zzui) {
         zzmh.zza(this.zzus, var1, var2);
      }

   }

   public final void zzd(T var1) {
      this.zzur.zzd(var1);
      this.zzus.zzd(var1);
   }

   public final int zzn(T var1) {
      zzmx var2 = this.zzur;
      int var3 = var2.zzs(var2.zzr(var1)) + 0;
      int var4 = var3;
      if (this.zzui) {
         var4 = var3 + this.zzus.zzb(var1).zzco();
      }

      return var4;
   }

   public final boolean zzp(T var1) {
      return this.zzus.zzb(var1).isInitialized();
   }
}
