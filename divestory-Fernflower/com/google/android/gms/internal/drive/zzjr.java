package com.google.android.gms.internal.drive;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class zzjr extends zzjb {
   private static final Logger logger = Logger.getLogger(zzjr.class.getName());
   private static final boolean zzog = zznd.zzfd();
   zzjt zzoh;

   private zzjr() {
   }

   // $FF: synthetic method
   zzjr(zzjs var1) {
      this();
   }

   public static int zza(int var0, zzkx var1) {
      var0 = zzab(var0);
      int var2 = var1.zzcx();
      return var0 + zzad(var2) + var2;
   }

   public static int zza(zzkx var0) {
      int var1 = var0.zzcx();
      return zzad(var1) + var1;
   }

   static int zza(zzlq var0, zzmf var1) {
      zzit var4 = (zzit)var0;
      int var2 = var4.zzbm();
      int var3 = var2;
      if (var2 == -1) {
         var3 = var1.zzn(var4);
         var4.zzo(var3);
      }

      return zzad(var3) + var3;
   }

   public static int zzab(int var0) {
      return zzad(var0 << 3);
   }

   public static int zzac(int var0) {
      return var0 >= 0 ? zzad(var0) : 10;
   }

   public static int zzad(int var0) {
      if ((var0 & -128) == 0) {
         return 1;
      } else if ((var0 & -16384) == 0) {
         return 2;
      } else if ((-2097152 & var0) == 0) {
         return 3;
      } else {
         return (var0 & -268435456) == 0 ? 4 : 5;
      }
   }

   public static int zzae(int var0) {
      return zzad(zzai(var0));
   }

   public static int zzaf(int var0) {
      return 4;
   }

   public static int zzag(int var0) {
      return 4;
   }

   public static int zzah(int var0) {
      return zzac(var0);
   }

   private static int zzai(int var0) {
      return var0 >> 31 ^ var0 << 1;
   }

   @Deprecated
   public static int zzaj(int var0) {
      return zzad(var0);
   }

   public static int zzb(double var0) {
      return 8;
   }

   public static int zzb(float var0) {
      return 4;
   }

   public static int zzb(int var0, double var1) {
      return zzab(var0) + 8;
   }

   public static int zzb(int var0, float var1) {
      return zzab(var0) + 4;
   }

   public static int zzb(int var0, zzkx var1) {
      return (zzab(1) << 1) + zzh(2, var0) + zza(3, (zzkx)var1);
   }

   public static int zzb(int var0, zzlq var1) {
      return (zzab(1) << 1) + zzh(2, var0) + zzab(3) + zzc(var1);
   }

   static int zzb(int var0, zzlq var1, zzmf var2) {
      return zzab(var0) + zza(var1, var2);
   }

   public static int zzb(int var0, String var1) {
      return zzab(var0) + zzm(var1);
   }

   public static int zzb(zzjc var0) {
      int var1 = var0.size();
      return zzad(var1) + var1;
   }

   public static zzjr zzb(byte[] var0) {
      return new zzjr.zza(var0, 0, var0.length);
   }

   public static int zzc(int var0, zzjc var1) {
      int var2 = zzab(var0);
      var0 = var1.size();
      return var2 + zzad(var0) + var0;
   }

   @Deprecated
   static int zzc(int var0, zzlq var1, zzmf var2) {
      int var3 = zzab(var0);
      zzit var5 = (zzit)var1;
      int var4 = var5.zzbm();
      var0 = var4;
      if (var4 == -1) {
         var0 = var2.zzn(var5);
         var5.zzo(var0);
      }

      return (var3 << 1) + var0;
   }

   public static int zzc(int var0, boolean var1) {
      return zzab(var0) + 1;
   }

   public static int zzc(zzlq var0) {
      int var1 = var0.zzcx();
      return zzad(var1) + var1;
   }

   public static int zzc(byte[] var0) {
      int var1 = var0.length;
      return zzad(var1) + var1;
   }

   public static int zzd(int var0, long var1) {
      return zzab(var0) + zzp(var1);
   }

   public static int zzd(int var0, zzjc var1) {
      return (zzab(1) << 1) + zzh(2, var0) + zzc(3, var1);
   }

   @Deprecated
   public static int zzd(zzlq var0) {
      return var0.zzcx();
   }

   public static int zzd(boolean var0) {
      return 1;
   }

   public static int zze(int var0, long var1) {
      return zzab(var0) + zzp(var1);
   }

   public static int zzf(int var0, long var1) {
      return zzab(var0) + zzp(zzt(var1));
   }

   public static int zzg(int var0, int var1) {
      return zzab(var0) + zzac(var1);
   }

   public static int zzg(int var0, long var1) {
      return zzab(var0) + 8;
   }

   public static int zzh(int var0, int var1) {
      return zzab(var0) + zzad(var1);
   }

   public static int zzh(int var0, long var1) {
      return zzab(var0) + 8;
   }

   public static int zzi(int var0, int var1) {
      return zzab(var0) + zzad(zzai(var1));
   }

   public static int zzj(int var0, int var1) {
      return zzab(var0) + 4;
   }

   public static int zzk(int var0, int var1) {
      return zzab(var0) + 4;
   }

   public static int zzl(int var0, int var1) {
      return zzab(var0) + zzac(var1);
   }

   public static int zzm(String var0) {
      int var1;
      try {
         var1 = zznf.zza(var0);
      } catch (zznj var3) {
         var1 = var0.getBytes(zzkm.UTF_8).length;
      }

      return zzad(var1) + var1;
   }

   public static int zzo(long var0) {
      return zzp(var0);
   }

   public static int zzp(long var0) {
      if ((-128L & var0) == 0L) {
         return 1;
      } else if (var0 < 0L) {
         return 10;
      } else {
         byte var2;
         if ((-34359738368L & var0) != 0L) {
            var2 = 6;
            var0 >>>= 28;
         } else {
            var2 = 2;
         }

         int var3 = var2;
         long var4 = var0;
         if ((-2097152L & var0) != 0L) {
            var3 = var2 + 2;
            var4 = var0 >>> 14;
         }

         int var6 = var3;
         if ((var4 & -16384L) != 0L) {
            var6 = var3 + 1;
         }

         return var6;
      }
   }

   public static int zzq(long var0) {
      return zzp(zzt(var0));
   }

   public static int zzr(long var0) {
      return 8;
   }

   public static int zzs(long var0) {
      return 8;
   }

   private static long zzt(long var0) {
      return var0 >> 63 ^ var0 << 1;
   }

   public final void zza(double var1) throws IOException {
      this.zzn(Double.doubleToRawLongBits(var1));
   }

   public final void zza(float var1) throws IOException {
      this.zzaa(Float.floatToRawIntBits(var1));
   }

   public final void zza(int var1, double var2) throws IOException {
      this.zzc(var1, Double.doubleToRawLongBits(var2));
   }

   public final void zza(int var1, float var2) throws IOException {
      this.zzf(var1, Float.floatToRawIntBits(var2));
   }

   public abstract void zza(int var1, long var2) throws IOException;

   public abstract void zza(int var1, zzjc var2) throws IOException;

   public abstract void zza(int var1, zzlq var2) throws IOException;

   abstract void zza(int var1, zzlq var2, zzmf var3) throws IOException;

   public abstract void zza(int var1, String var2) throws IOException;

   public abstract void zza(zzjc var1) throws IOException;

   final void zza(String var1, zznj var2) throws IOException {
      logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", var2);
      byte[] var5 = var1.getBytes(zzkm.UTF_8);

      try {
         this.zzy(var5.length);
         this.zza(var5, 0, var5.length);
      } catch (IndexOutOfBoundsException var3) {
         throw new zzjr.zzb(var3);
      } catch (zzjr.zzb var4) {
         throw var4;
      }
   }

   public abstract void zzaa(int var1) throws IOException;

   public abstract void zzb(int var1, int var2) throws IOException;

   public final void zzb(int var1, long var2) throws IOException {
      this.zza(var1, zzt(var2));
   }

   public abstract void zzb(int var1, zzjc var2) throws IOException;

   public abstract void zzb(int var1, boolean var2) throws IOException;

   public abstract void zzb(zzlq var1) throws IOException;

   public abstract void zzc(byte var1) throws IOException;

   public abstract void zzc(int var1, int var2) throws IOException;

   public abstract void zzc(int var1, long var2) throws IOException;

   public final void zzc(boolean var1) throws IOException {
      this.zzc((byte)var1);
   }

   public abstract int zzca();

   public final void zzcb() {
      if (this.zzca() != 0) {
         throw new IllegalStateException("Did not write as much data as expected.");
      }
   }

   public abstract void zzd(int var1, int var2) throws IOException;

   abstract void zzd(byte[] var1, int var2, int var3) throws IOException;

   public final void zze(int var1, int var2) throws IOException {
      this.zzd(var1, zzai(var2));
   }

   public abstract void zzf(int var1, int var2) throws IOException;

   public abstract void zzl(long var1) throws IOException;

   public abstract void zzl(String var1) throws IOException;

   public final void zzm(long var1) throws IOException {
      this.zzl(zzt(var1));
   }

   public abstract void zzn(long var1) throws IOException;

   public abstract void zzx(int var1) throws IOException;

   public abstract void zzy(int var1) throws IOException;

   public final void zzz(int var1) throws IOException {
      this.zzy(zzai(var1));
   }

   static final class zza extends zzjr {
      private final byte[] buffer;
      private final int limit;
      private final int offset;
      private int position;

      zza(byte[] var1, int var2, int var3) {
         super((zzjs)null);
         if (var1 != null) {
            var2 = var1.length;
            int var4 = var3 + 0;
            if ((var3 | 0 | var2 - var4) >= 0) {
               this.buffer = var1;
               this.offset = 0;
               this.position = 0;
               this.limit = var4;
            } else {
               throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", var1.length, 0, var3));
            }
         } else {
            throw new NullPointerException("buffer");
         }
      }

      private final void write(byte[] var1, int var2, int var3) throws IOException {
         try {
            System.arraycopy(var1, var2, this.buffer, this.position, var3);
            this.position += var3;
         } catch (IndexOutOfBoundsException var4) {
            throw new zzjr.zzb(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, var3), var4);
         }
      }

      public final void zza(int var1, long var2) throws IOException {
         this.zzb(var1, 0);
         this.zzl(var2);
      }

      public final void zza(int var1, zzjc var2) throws IOException {
         this.zzb(var1, 2);
         this.zza(var2);
      }

      public final void zza(int var1, zzlq var2) throws IOException {
         this.zzb(1, 3);
         this.zzd(2, var1);
         this.zzb(3, 2);
         this.zzb(var2);
         this.zzb(1, 4);
      }

      final void zza(int var1, zzlq var2, zzmf var3) throws IOException {
         this.zzb(var1, 2);
         zzit var4 = (zzit)var2;
         int var5 = var4.zzbm();
         var1 = var5;
         if (var5 == -1) {
            var1 = var3.zzn(var4);
            var4.zzo(var1);
         }

         this.zzy(var1);
         var3.zza(var2, this.zzoh);
      }

      public final void zza(int var1, String var2) throws IOException {
         this.zzb(var1, 2);
         this.zzl(var2);
      }

      public final void zza(zzjc var1) throws IOException {
         this.zzy(var1.size());
         var1.zza((zzjb)this);
      }

      public final void zza(byte[] var1, int var2, int var3) throws IOException {
         this.write(var1, var2, var3);
      }

      public final void zzaa(int var1) throws IOException {
         byte[] var2;
         int var4;
         label61: {
            IndexOutOfBoundsException var10000;
            label63: {
               int var3;
               boolean var10001;
               try {
                  var2 = this.buffer;
                  var3 = this.position;
               } catch (IndexOutOfBoundsException var11) {
                  var10000 = var11;
                  var10001 = false;
                  break label63;
               }

               var4 = var3 + 1;

               try {
                  this.position = var4;
               } catch (IndexOutOfBoundsException var10) {
                  var10000 = var10;
                  var10001 = false;
                  break label63;
               }

               var2[var3] = (byte)((byte)var1);

               try {
                  var2 = this.buffer;
               } catch (IndexOutOfBoundsException var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label63;
               }

               var3 = var4 + 1;

               try {
                  this.position = var3;
               } catch (IndexOutOfBoundsException var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label63;
               }

               var2[var4] = (byte)((byte)(var1 >> 8));

               try {
                  var2 = this.buffer;
               } catch (IndexOutOfBoundsException var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label63;
               }

               var4 = var3 + 1;

               try {
                  this.position = var4;
               } catch (IndexOutOfBoundsException var6) {
                  var10000 = var6;
                  var10001 = false;
                  break label63;
               }

               var2[var3] = (byte)((byte)(var1 >> 16));

               try {
                  var2 = this.buffer;
                  this.position = var4 + 1;
                  break label61;
               } catch (IndexOutOfBoundsException var5) {
                  var10000 = var5;
                  var10001 = false;
               }
            }

            IndexOutOfBoundsException var12 = var10000;
            throw new zzjr.zzb(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), var12);
         }

         var2[var4] = (byte)((byte)(var1 >>> 24));
      }

      public final void zzb(int var1, int var2) throws IOException {
         this.zzy(var1 << 3 | var2);
      }

      public final void zzb(int var1, zzjc var2) throws IOException {
         this.zzb(1, 3);
         this.zzd(2, var1);
         this.zza(3, (zzjc)var2);
         this.zzb(1, 4);
      }

      public final void zzb(int var1, boolean var2) throws IOException {
         this.zzb(var1, 0);
         this.zzc((byte)var2);
      }

      public final void zzb(zzlq var1) throws IOException {
         this.zzy(var1.zzcx());
         var1.zzb(this);
      }

      public final void zzc(byte var1) throws IOException {
         byte[] var2;
         int var3;
         try {
            var2 = this.buffer;
            var3 = this.position++;
         } catch (IndexOutOfBoundsException var4) {
            throw new zzjr.zzb(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), var4);
         }

         var2[var3] = (byte)var1;
      }

      public final void zzc(int var1, int var2) throws IOException {
         this.zzb(var1, 0);
         this.zzx(var2);
      }

      public final void zzc(int var1, long var2) throws IOException {
         this.zzb(var1, 1);
         this.zzn(var2);
      }

      public final int zzca() {
         return this.limit - this.position;
      }

      public final void zzd(int var1, int var2) throws IOException {
         this.zzb(var1, 0);
         this.zzy(var2);
      }

      public final void zzd(byte[] var1, int var2, int var3) throws IOException {
         this.zzy(var3);
         this.write(var1, 0, var3);
      }

      public final void zzf(int var1, int var2) throws IOException {
         this.zzb(var1, 5);
         this.zzaa(var2);
      }

      public final void zzl(long var1) throws IOException {
         long var3 = var1;
         byte[] var5;
         int var6;
         if (zzjr.zzog) {
            var3 = var1;
            if (this.zzca() >= 10) {
               while((var1 & -128L) != 0L) {
                  var5 = this.buffer;
                  var6 = this.position++;
                  zznd.zza(var5, (long)var6, (byte)((int)var1 & 127 | 128));
                  var1 >>>= 7;
               }

               var5 = this.buffer;
               var6 = this.position++;
               zznd.zza(var5, (long)var6, (byte)((int)var1));
               return;
            }
         }

         while(true) {
            IndexOutOfBoundsException var10000;
            boolean var10001;
            if ((var3 & -128L) == 0L) {
               try {
                  var5 = this.buffer;
                  var6 = this.position++;
                  break;
               } catch (IndexOutOfBoundsException var7) {
                  var10000 = var7;
                  var10001 = false;
               }
            } else {
               label32: {
                  try {
                     var5 = this.buffer;
                     var6 = this.position++;
                  } catch (IndexOutOfBoundsException var8) {
                     var10000 = var8;
                     var10001 = false;
                     break label32;
                  }

                  var5[var6] = (byte)((byte)((int)var3 & 127 | 128));
                  var3 >>>= 7;
                  continue;
               }
            }

            IndexOutOfBoundsException var9 = var10000;
            throw new zzjr.zzb(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), var9);
         }

         var5[var6] = (byte)((byte)((int)var3));
      }

      public final void zzl(String var1) throws IOException {
         int var2 = this.position;

         zznj var13;
         label38: {
            IndexOutOfBoundsException var10000;
            label37: {
               int var3;
               int var4;
               boolean var10001;
               try {
                  var3 = zzad(var1.length() * 3);
                  var4 = zzad(var1.length());
               } catch (zznj var10) {
                  var13 = var10;
                  var10001 = false;
                  break label38;
               } catch (IndexOutOfBoundsException var11) {
                  var10000 = var11;
                  var10001 = false;
                  break label37;
               }

               if (var4 == var3) {
                  var3 = var2 + var4;

                  try {
                     this.position = var3;
                     var3 = zznf.zza(var1, this.buffer, var3, this.zzca());
                     this.position = var2;
                     this.zzy(var3 - var2 - var4);
                     this.position = var3;
                     return;
                  } catch (zznj var6) {
                     var13 = var6;
                     var10001 = false;
                     break label38;
                  } catch (IndexOutOfBoundsException var7) {
                     var10000 = var7;
                     var10001 = false;
                  }
               } else {
                  try {
                     this.zzy(zznf.zza(var1));
                     this.position = zznf.zza(var1, this.buffer, this.position, this.zzca());
                     return;
                  } catch (zznj var8) {
                     var13 = var8;
                     var10001 = false;
                     break label38;
                  } catch (IndexOutOfBoundsException var9) {
                     var10000 = var9;
                     var10001 = false;
                  }
               }
            }

            IndexOutOfBoundsException var12 = var10000;
            throw new zzjr.zzb(var12);
         }

         zznj var5 = var13;
         this.position = var2;
         this.zza(var1, var5);
      }

      public final void zzn(long var1) throws IOException {
         byte[] var3;
         int var5;
         label125: {
            IndexOutOfBoundsException var10000;
            label127: {
               int var4;
               boolean var10001;
               try {
                  var3 = this.buffer;
                  var4 = this.position;
               } catch (IndexOutOfBoundsException var20) {
                  var10000 = var20;
                  var10001 = false;
                  break label127;
               }

               var5 = var4 + 1;

               try {
                  this.position = var5;
               } catch (IndexOutOfBoundsException var19) {
                  var10000 = var19;
                  var10001 = false;
                  break label127;
               }

               var3[var4] = (byte)((byte)((int)var1));

               try {
                  var3 = this.buffer;
               } catch (IndexOutOfBoundsException var18) {
                  var10000 = var18;
                  var10001 = false;
                  break label127;
               }

               var4 = var5 + 1;

               try {
                  this.position = var4;
               } catch (IndexOutOfBoundsException var17) {
                  var10000 = var17;
                  var10001 = false;
                  break label127;
               }

               var3[var5] = (byte)((byte)((int)(var1 >> 8)));

               try {
                  var3 = this.buffer;
               } catch (IndexOutOfBoundsException var16) {
                  var10000 = var16;
                  var10001 = false;
                  break label127;
               }

               var5 = var4 + 1;

               try {
                  this.position = var5;
               } catch (IndexOutOfBoundsException var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label127;
               }

               var3[var4] = (byte)((byte)((int)(var1 >> 16)));

               try {
                  var3 = this.buffer;
               } catch (IndexOutOfBoundsException var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label127;
               }

               var4 = var5 + 1;

               try {
                  this.position = var4;
               } catch (IndexOutOfBoundsException var13) {
                  var10000 = var13;
                  var10001 = false;
                  break label127;
               }

               var3[var5] = (byte)((byte)((int)(var1 >> 24)));

               try {
                  var3 = this.buffer;
               } catch (IndexOutOfBoundsException var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label127;
               }

               var5 = var4 + 1;

               try {
                  this.position = var5;
               } catch (IndexOutOfBoundsException var11) {
                  var10000 = var11;
                  var10001 = false;
                  break label127;
               }

               var3[var4] = (byte)((byte)((int)(var1 >> 32)));

               try {
                  var3 = this.buffer;
               } catch (IndexOutOfBoundsException var10) {
                  var10000 = var10;
                  var10001 = false;
                  break label127;
               }

               var4 = var5 + 1;

               try {
                  this.position = var4;
               } catch (IndexOutOfBoundsException var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label127;
               }

               var3[var5] = (byte)((byte)((int)(var1 >> 40)));

               try {
                  var3 = this.buffer;
               } catch (IndexOutOfBoundsException var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label127;
               }

               var5 = var4 + 1;

               try {
                  this.position = var5;
               } catch (IndexOutOfBoundsException var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label127;
               }

               var3[var4] = (byte)((byte)((int)(var1 >> 48)));

               try {
                  var3 = this.buffer;
                  this.position = var5 + 1;
                  break label125;
               } catch (IndexOutOfBoundsException var6) {
                  var10000 = var6;
                  var10001 = false;
               }
            }

            IndexOutOfBoundsException var21 = var10000;
            throw new zzjr.zzb(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), var21);
         }

         var3[var5] = (byte)((byte)((int)(var1 >> 56)));
      }

      public final void zzx(int var1) throws IOException {
         if (var1 >= 0) {
            this.zzy(var1);
         } else {
            this.zzl((long)var1);
         }
      }

      public final void zzy(int var1) throws IOException {
         int var2 = var1;
         byte[] var3;
         if (zzjr.zzog) {
            var2 = var1;
            if (!zzix.zzbr()) {
               var2 = var1;
               if (this.zzca() >= 5) {
                  if ((var1 & -128) == 0) {
                     var3 = this.buffer;
                     var2 = this.position++;
                     zznd.zza(var3, (long)var2, (byte)var1);
                     return;
                  }

                  var3 = this.buffer;
                  var2 = this.position++;
                  zznd.zza(var3, (long)var2, (byte)(var1 | 128));
                  var1 >>>= 7;
                  if ((var1 & -128) == 0) {
                     var3 = this.buffer;
                     var2 = this.position++;
                     zznd.zza(var3, (long)var2, (byte)var1);
                     return;
                  }

                  var3 = this.buffer;
                  var2 = this.position++;
                  zznd.zza(var3, (long)var2, (byte)(var1 | 128));
                  var1 >>>= 7;
                  if ((var1 & -128) == 0) {
                     var3 = this.buffer;
                     var2 = this.position++;
                     zznd.zza(var3, (long)var2, (byte)var1);
                     return;
                  }

                  var3 = this.buffer;
                  var2 = this.position++;
                  zznd.zza(var3, (long)var2, (byte)(var1 | 128));
                  var1 >>>= 7;
                  if ((var1 & -128) == 0) {
                     var3 = this.buffer;
                     var2 = this.position++;
                     zznd.zza(var3, (long)var2, (byte)var1);
                     return;
                  }

                  var3 = this.buffer;
                  var2 = this.position++;
                  zznd.zza(var3, (long)var2, (byte)(var1 | 128));
                  var3 = this.buffer;
                  var2 = this.position++;
                  zznd.zza(var3, (long)var2, (byte)(var1 >>> 7));
                  return;
               }
            }
         }

         while(true) {
            IndexOutOfBoundsException var10000;
            boolean var10001;
            if ((var2 & -128) == 0) {
               try {
                  var3 = this.buffer;
                  var1 = this.position++;
                  break;
               } catch (IndexOutOfBoundsException var4) {
                  var10000 = var4;
                  var10001 = false;
               }
            } else {
               label43: {
                  try {
                     var3 = this.buffer;
                     var1 = this.position++;
                  } catch (IndexOutOfBoundsException var5) {
                     var10000 = var5;
                     var10001 = false;
                     break label43;
                  }

                  var3[var1] = (byte)((byte)(var2 & 127 | 128));
                  var2 >>>= 7;
                  continue;
               }
            }

            IndexOutOfBoundsException var6 = var10000;
            throw new zzjr.zzb(String.format("Pos: %d, limit: %d, len: %d", this.position, this.limit, 1), var6);
         }

         var3[var1] = (byte)((byte)var2);
      }
   }

   public static final class zzb extends IOException {
      zzb() {
         super("CodedOutputStream was writing to a flat byte array and ran out of space.");
      }

      zzb(String var1, Throwable var2) {
         var1 = String.valueOf(var1);
         if (var1.length() != 0) {
            var1 = "CodedOutputStream was writing to a flat byte array and ran out of space.: ".concat(var1);
         } else {
            var1 = new String("CodedOutputStream was writing to a flat byte array and ran out of space.: ");
         }

         super(var1, var2);
      }

      zzb(Throwable var1) {
         super("CodedOutputStream was writing to a flat byte array and ran out of space.", var1);
      }
   }
}
