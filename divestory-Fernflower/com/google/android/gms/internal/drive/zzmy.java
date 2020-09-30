package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.util.Arrays;

public final class zzmy {
   private static final zzmy zzvr = new zzmy(0, new int[0], new Object[0], false);
   private int count;
   private boolean zznh;
   private int zzrr;
   private Object[] zzue;
   private int[] zzvs;

   private zzmy() {
      this(0, new int[8], new Object[8], true);
   }

   private zzmy(int var1, int[] var2, Object[] var3, boolean var4) {
      this.zzrr = -1;
      this.count = var1;
      this.zzvs = var2;
      this.zzue = var3;
      this.zznh = var4;
   }

   static zzmy zza(zzmy var0, zzmy var1) {
      int var2 = var0.count + var1.count;
      int[] var3 = Arrays.copyOf(var0.zzvs, var2);
      System.arraycopy(var1.zzvs, 0, var3, var0.count, var1.count);
      Object[] var4 = Arrays.copyOf(var0.zzue, var2);
      System.arraycopy(var1.zzue, 0, var4, var0.count, var1.count);
      return new zzmy(var2, var3, var4, true);
   }

   private static void zzb(int var0, Object var1, zzns var2) throws IOException {
      int var3 = var0 >>> 3;
      var0 &= 7;
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 != 2) {
               if (var0 != 3) {
                  if (var0 == 5) {
                     var2.zzf(var3, (Integer)var1);
                  } else {
                     throw new RuntimeException(zzkq.zzdl());
                  }
               } else if (var2.zzcd() == zzkk.zze.zzsi) {
                  var2.zzak(var3);
                  ((zzmy)var1).zzb(var2);
                  var2.zzal(var3);
               } else {
                  var2.zzal(var3);
                  ((zzmy)var1).zzb(var2);
                  var2.zzak(var3);
               }
            } else {
               var2.zza(var3, (zzjc)var1);
            }
         } else {
            var2.zzc(var3, (Long)var1);
         }
      } else {
         var2.zzi(var3, (Long)var1);
      }
   }

   public static zzmy zzfa() {
      return zzvr;
   }

   static zzmy zzfb() {
      return new zzmy();
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof zzmy)) {
         return false;
      } else {
         zzmy var6 = (zzmy)var1;
         int var2 = this.count;
         if (var2 == var6.count) {
            int[] var3 = this.zzvs;
            int[] var4 = var6.zzvs;
            int var5 = 0;

            boolean var9;
            while(true) {
               if (var5 >= var2) {
                  var9 = true;
                  break;
               }

               if (var3[var5] != var4[var5]) {
                  var9 = false;
                  break;
               }

               ++var5;
            }

            if (var9) {
               Object[] var8 = this.zzue;
               Object[] var7 = var6.zzue;
               var2 = this.count;
               var5 = 0;

               while(true) {
                  if (var5 >= var2) {
                     var9 = true;
                     break;
                  }

                  if (!var8[var5].equals(var7[var5])) {
                     var9 = false;
                     break;
                  }

                  ++var5;
               }

               if (var9) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public final int hashCode() {
      int var1 = this.count;
      int[] var2 = this.zzvs;
      byte var3 = 0;
      int var4 = 17;
      int var5 = 0;

      int var6;
      for(var6 = 17; var5 < var1; ++var5) {
         var6 = var6 * 31 + var2[var5];
      }

      Object[] var8 = this.zzue;
      int var7 = this.count;

      for(var5 = var3; var5 < var7; ++var5) {
         var4 = var4 * 31 + var8[var5].hashCode();
      }

      return ((var1 + 527) * 31 + var6) * 31 + var4;
   }

   final void zza(zzns var1) throws IOException {
      int var2;
      if (var1.zzcd() == zzkk.zze.zzsj) {
         for(var2 = this.count - 1; var2 >= 0; --var2) {
            var1.zza(this.zzvs[var2] >>> 3, this.zzue[var2]);
         }

      } else {
         for(var2 = 0; var2 < this.count; ++var2) {
            var1.zza(this.zzvs[var2] >>> 3, this.zzue[var2]);
         }

      }
   }

   final void zza(StringBuilder var1, int var2) {
      for(int var3 = 0; var3 < this.count; ++var3) {
         zzlt.zza(var1, var2, String.valueOf(this.zzvs[var3] >>> 3), this.zzue[var3]);
      }

   }

   final void zzb(int var1, Object var2) {
      if (this.zznh) {
         int var3 = this.count;
         if (var3 == this.zzvs.length) {
            if (var3 < 4) {
               var3 = 8;
            } else {
               var3 >>= 1;
            }

            var3 += this.count;
            this.zzvs = Arrays.copyOf(this.zzvs, var3);
            this.zzue = Arrays.copyOf(this.zzue, var3);
         }

         int[] var4 = this.zzvs;
         var3 = this.count;
         var4[var3] = var1;
         this.zzue[var3] = var2;
         this.count = var3 + 1;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public final void zzb(zzns var1) throws IOException {
      if (this.count != 0) {
         int var2;
         if (var1.zzcd() == zzkk.zze.zzsi) {
            for(var2 = 0; var2 < this.count; ++var2) {
               zzb(this.zzvs[var2], this.zzue[var2], var1);
            }

         } else {
            for(var2 = this.count - 1; var2 >= 0; --var2) {
               zzb(this.zzvs[var2], this.zzue[var2], var1);
            }

         }
      }
   }

   public final void zzbp() {
      this.zznh = false;
   }

   public final int zzcx() {
      int var1 = this.zzrr;
      if (var1 != -1) {
         return var1;
      } else {
         int var2 = 0;

         int var3;
         for(var3 = 0; var2 < this.count; ++var2) {
            int var4 = this.zzvs[var2];
            var1 = var4 >>> 3;
            var4 &= 7;
            if (var4 != 0) {
               if (var4 != 1) {
                  if (var4 != 2) {
                     if (var4 != 3) {
                        if (var4 != 5) {
                           throw new IllegalStateException(zzkq.zzdl());
                        }

                        var1 = zzjr.zzj(var1, (Integer)this.zzue[var2]);
                     } else {
                        var1 = (zzjr.zzab(var1) << 1) + ((zzmy)this.zzue[var2]).zzcx();
                     }
                  } else {
                     var1 = zzjr.zzc(var1, (zzjc)this.zzue[var2]);
                  }
               } else {
                  var1 = zzjr.zzg(var1, (Long)this.zzue[var2]);
               }
            } else {
               var1 = zzjr.zze(var1, (Long)this.zzue[var2]);
            }

            var3 += var1;
         }

         this.zzrr = var3;
         return var3;
      }
   }

   public final int zzfc() {
      int var1 = this.zzrr;
      if (var1 != -1) {
         return var1;
      } else {
         var1 = 0;

         int var2;
         for(var2 = 0; var1 < this.count; ++var1) {
            var2 += zzjr.zzd(this.zzvs[var1] >>> 3, (zzjc)this.zzue[var1]);
         }

         this.zzrr = var2;
         return var2;
      }
   }
}
