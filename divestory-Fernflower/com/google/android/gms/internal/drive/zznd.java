package com.google.android.gms.internal.drive;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

final class zznd {
   private static final Logger logger = Logger.getLogger(zznd.class.getName());
   private static final Class<?> zzni = zzix.zzbs();
   private static final boolean zzog;
   private static final Unsafe zzuc = zzff();
   private static final boolean zzvy;
   private static final boolean zzvz;
   private static final zznd.zzd zzwa;
   private static final boolean zzwb;
   private static final long zzwc;
   private static final long zzwd;
   private static final long zzwe;
   private static final long zzwf;
   private static final long zzwg;
   private static final long zzwh;
   private static final long zzwi;
   private static final long zzwj;
   private static final long zzwk;
   private static final long zzwl;
   private static final long zzwm;
   private static final long zzwn;
   private static final long zzwo;
   private static final long zzwp;
   private static final int zzwq;
   static final boolean zzwr;

   static {
      zzvy = zzk(Long.TYPE);
      zzvz = zzk(Integer.TYPE);
      Unsafe var0 = zzuc;
      Object var1 = null;
      if (var0 != null) {
         if (zzix.zzbr()) {
            if (zzvy) {
               var1 = new zznd.zzb(zzuc);
            } else if (zzvz) {
               var1 = new zznd.zza(zzuc);
            }
         } else {
            var1 = new zznd.zzc(zzuc);
         }
      }

      long var2;
      label30: {
         zzwa = (zznd.zzd)var1;
         zzwb = zzfh();
         zzog = zzfg();
         zzwc = (long)zzi(byte[].class);
         zzwd = (long)zzi(boolean[].class);
         zzwe = (long)zzj(boolean[].class);
         zzwf = (long)zzi(int[].class);
         zzwg = (long)zzj(int[].class);
         zzwh = (long)zzi(long[].class);
         zzwi = (long)zzj(long[].class);
         zzwj = (long)zzi(float[].class);
         zzwk = (long)zzj(float[].class);
         zzwl = (long)zzi(double[].class);
         zzwm = (long)zzj(double[].class);
         zzwn = (long)zzi(Object[].class);
         zzwo = (long)zzj(Object[].class);
         Field var5 = zzfi();
         if (var5 != null) {
            zznd.zzd var6 = zzwa;
            if (var6 != null) {
               var2 = var6.zzws.objectFieldOffset(var5);
               break label30;
            }
         }

         var2 = -1L;
      }

      zzwp = var2;
      zzwq = (int)(zzwc & 7L);
      boolean var4;
      if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
         var4 = true;
      } else {
         var4 = false;
      }

      zzwr = var4;
   }

   private zznd() {
   }

   static byte zza(byte[] var0, long var1) {
      return zzwa.zzx(var0, zzwc + var1);
   }

   private static void zza(Object var0, long var1, byte var3) {
      long var4 = -4L & var1;
      int var6 = zzj(var0, var4);
      int var7 = ((int)var1 & 3) << 3;
      zza(var0, var4, (255 & var3) << var7 | var6 & 255 << var7);
   }

   static void zza(Object var0, long var1, double var3) {
      zzwa.zza(var0, var1, var3);
   }

   static void zza(Object var0, long var1, float var3) {
      zzwa.zza(var0, var1, var3);
   }

   static void zza(Object var0, long var1, int var3) {
      zzwa.zza(var0, var1, var3);
   }

   static void zza(Object var0, long var1, long var3) {
      zzwa.zza(var0, var1, var3);
   }

   static void zza(Object var0, long var1, Object var3) {
      zzwa.zzws.putObject(var0, var1, var3);
   }

   static void zza(Object var0, long var1, boolean var3) {
      zzwa.zza(var0, var1, var3);
   }

   static void zza(byte[] var0, long var1, byte var3) {
      zzwa.zze(var0, zzwc + var1, var3);
   }

   private static Field zzb(Class<?> var0, String var1) {
      Field var4;
      try {
         var4 = var0.getDeclaredField(var1);
      } finally {
         ;
      }

      return var4;
   }

   private static void zzb(Object var0, long var1, byte var3) {
      long var4 = -4L & var1;
      int var6 = zzj(var0, var4);
      int var7 = ((int)var1 & 3) << 3;
      zza(var0, var4, (255 & var3) << var7 | var6 & 255 << var7);
   }

   private static void zzb(Object var0, long var1, boolean var3) {
      zza(var0, var1, (byte)var3);
   }

   private static void zzc(Object var0, long var1, boolean var3) {
      zzb(var0, var1, (byte)var3);
   }

   static boolean zzfd() {
      return zzog;
   }

   static boolean zzfe() {
      return zzwb;
   }

   static Unsafe zzff() {
      Unsafe var3;
      try {
         zzne var0 = new zzne();
         var3 = (Unsafe)AccessController.doPrivileged(var0);
      } finally {
         ;
      }

      return var3;
   }

   private static boolean zzfg() {
      Unsafe var0 = zzuc;
      if (var0 == null) {
         return false;
      } else {
         try {
            Class var7 = var0.getClass();
            var7.getMethod("objectFieldOffset", Field.class);
            var7.getMethod("arrayBaseOffset", Class.class);
            var7.getMethod("arrayIndexScale", Class.class);
            var7.getMethod("getInt", Object.class, Long.TYPE);
            var7.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            var7.getMethod("getLong", Object.class, Long.TYPE);
            var7.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            var7.getMethod("getObject", Object.class, Long.TYPE);
            var7.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            if (zzix.zzbr()) {
               return true;
            } else {
               var7.getMethod("getByte", Object.class, Long.TYPE);
               var7.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
               var7.getMethod("getBoolean", Object.class, Long.TYPE);
               var7.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
               var7.getMethod("getFloat", Object.class, Long.TYPE);
               var7.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
               var7.getMethod("getDouble", Object.class, Long.TYPE);
               var7.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
               return true;
            }
         } catch (Throwable var5) {
            Logger var2 = logger;
            Level var6 = Level.WARNING;
            String var3 = String.valueOf(var5);
            StringBuilder var1 = new StringBuilder(String.valueOf(var3).length() + 71);
            var1.append("platform method missing - proto runtime falling back to safer methods: ");
            var1.append(var3);
            var2.logp(var6, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", var1.toString());
            return false;
         }
      }
   }

   private static boolean zzfh() {
      Unsafe var0 = zzuc;
      if (var0 == null) {
         return false;
      } else {
         try {
            Class var7 = var0.getClass();
            var7.getMethod("objectFieldOffset", Field.class);
            var7.getMethod("getLong", Object.class, Long.TYPE);
            if (zzfi() == null) {
               return false;
            } else if (zzix.zzbr()) {
               return true;
            } else {
               var7.getMethod("getByte", Long.TYPE);
               var7.getMethod("putByte", Long.TYPE, Byte.TYPE);
               var7.getMethod("getInt", Long.TYPE);
               var7.getMethod("putInt", Long.TYPE, Integer.TYPE);
               var7.getMethod("getLong", Long.TYPE);
               var7.getMethod("putLong", Long.TYPE, Long.TYPE);
               var7.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
               var7.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
               return true;
            }
         } catch (Throwable var5) {
            Logger var6 = logger;
            Level var2 = Level.WARNING;
            String var3 = String.valueOf(var5);
            StringBuilder var1 = new StringBuilder(String.valueOf(var3).length() + 71);
            var1.append("platform method missing - proto runtime falling back to safer methods: ");
            var1.append(var3);
            var6.logp(var2, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", var1.toString());
            return false;
         }
      }
   }

   private static Field zzfi() {
      Field var0;
      if (zzix.zzbr()) {
         var0 = zzb(Buffer.class, "effectiveDirectAddress");
         if (var0 != null) {
            return var0;
         }
      }

      var0 = zzb(Buffer.class, "address");
      return var0 != null && var0.getType() == Long.TYPE ? var0 : null;
   }

   static <T> T zzh(Class<T> var0) {
      try {
         Object var2 = zzuc.allocateInstance(var0);
         return var2;
      } catch (InstantiationException var1) {
         throw new IllegalStateException(var1);
      }
   }

   private static int zzi(Class<?> var0) {
      return zzog ? zzwa.zzws.arrayBaseOffset(var0) : -1;
   }

   private static int zzj(Class<?> var0) {
      return zzog ? zzwa.zzws.arrayIndexScale(var0) : -1;
   }

   static int zzj(Object var0, long var1) {
      return zzwa.zzj(var0, var1);
   }

   static long zzk(Object var0, long var1) {
      return zzwa.zzk(var0, var1);
   }

   private static boolean zzk(Class<?> var0) {
      if (!zzix.zzbr()) {
         return false;
      } else {
         try {
            Class var1 = zzni;
            var1.getMethod("peekLong", var0, Boolean.TYPE);
            var1.getMethod("pokeLong", var0, Long.TYPE, Boolean.TYPE);
            var1.getMethod("pokeInt", var0, Integer.TYPE, Boolean.TYPE);
            var1.getMethod("peekInt", var0, Boolean.TYPE);
            var1.getMethod("pokeByte", var0, Byte.TYPE);
            var1.getMethod("peekByte", var0);
            var1.getMethod("pokeByteArray", var0, byte[].class, Integer.TYPE, Integer.TYPE);
            var1.getMethod("peekByteArray", var0, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
         } finally {
            ;
         }
      }
   }

   static boolean zzl(Object var0, long var1) {
      return zzwa.zzl(var0, var1);
   }

   static float zzm(Object var0, long var1) {
      return zzwa.zzm(var0, var1);
   }

   static double zzn(Object var0, long var1) {
      return zzwa.zzn(var0, var1);
   }

   static Object zzo(Object var0, long var1) {
      return zzwa.zzws.getObject(var0, var1);
   }

   private static byte zzp(Object var0, long var1) {
      return (byte)(zzj(var0, -4L & var1) >>> (int)((var1 & 3L) << 3));
   }

   private static byte zzq(Object var0, long var1) {
      return (byte)(zzj(var0, -4L & var1) >>> (int)((var1 & 3L) << 3));
   }

   private static boolean zzr(Object var0, long var1) {
      return zzp(var0, var1) != 0;
   }

   private static boolean zzs(Object var0, long var1) {
      return zzq(var0, var1) != 0;
   }

   static final class zza extends zznd.zzd {
      zza(Unsafe var1) {
         super(var1);
      }

      public final void zza(Object var1, long var2, double var4) {
         this.zza(var1, var2, Double.doubleToLongBits(var4));
      }

      public final void zza(Object var1, long var2, float var4) {
         this.zza(var1, var2, Float.floatToIntBits(var4));
      }

      public final void zza(Object var1, long var2, boolean var4) {
         if (zznd.zzwr) {
            zznd.zzb(var1, var2, var4);
         } else {
            zznd.zzc(var1, var2, var4);
         }
      }

      public final void zze(Object var1, long var2, byte var4) {
         if (zznd.zzwr) {
            zznd.zza(var1, var2, var4);
         } else {
            zznd.zzb(var1, var2, var4);
         }
      }

      public final boolean zzl(Object var1, long var2) {
         return zznd.zzwr ? zznd.zzr(var1, var2) : zznd.zzs(var1, var2);
      }

      public final float zzm(Object var1, long var2) {
         return Float.intBitsToFloat(this.zzj(var1, var2));
      }

      public final double zzn(Object var1, long var2) {
         return Double.longBitsToDouble(this.zzk(var1, var2));
      }

      public final byte zzx(Object var1, long var2) {
         return zznd.zzwr ? zznd.zzp(var1, var2) : zznd.zzq(var1, var2);
      }
   }

   static final class zzb extends zznd.zzd {
      zzb(Unsafe var1) {
         super(var1);
      }

      public final void zza(Object var1, long var2, double var4) {
         this.zza(var1, var2, Double.doubleToLongBits(var4));
      }

      public final void zza(Object var1, long var2, float var4) {
         this.zza(var1, var2, Float.floatToIntBits(var4));
      }

      public final void zza(Object var1, long var2, boolean var4) {
         if (zznd.zzwr) {
            zznd.zzb(var1, var2, var4);
         } else {
            zznd.zzc(var1, var2, var4);
         }
      }

      public final void zze(Object var1, long var2, byte var4) {
         if (zznd.zzwr) {
            zznd.zza(var1, var2, var4);
         } else {
            zznd.zzb(var1, var2, var4);
         }
      }

      public final boolean zzl(Object var1, long var2) {
         return zznd.zzwr ? zznd.zzr(var1, var2) : zznd.zzs(var1, var2);
      }

      public final float zzm(Object var1, long var2) {
         return Float.intBitsToFloat(this.zzj(var1, var2));
      }

      public final double zzn(Object var1, long var2) {
         return Double.longBitsToDouble(this.zzk(var1, var2));
      }

      public final byte zzx(Object var1, long var2) {
         return zznd.zzwr ? zznd.zzp(var1, var2) : zznd.zzq(var1, var2);
      }
   }

   static final class zzc extends zznd.zzd {
      zzc(Unsafe var1) {
         super(var1);
      }

      public final void zza(Object var1, long var2, double var4) {
         this.zzws.putDouble(var1, var2, var4);
      }

      public final void zza(Object var1, long var2, float var4) {
         this.zzws.putFloat(var1, var2, var4);
      }

      public final void zza(Object var1, long var2, boolean var4) {
         this.zzws.putBoolean(var1, var2, var4);
      }

      public final void zze(Object var1, long var2, byte var4) {
         this.zzws.putByte(var1, var2, var4);
      }

      public final boolean zzl(Object var1, long var2) {
         return this.zzws.getBoolean(var1, var2);
      }

      public final float zzm(Object var1, long var2) {
         return this.zzws.getFloat(var1, var2);
      }

      public final double zzn(Object var1, long var2) {
         return this.zzws.getDouble(var1, var2);
      }

      public final byte zzx(Object var1, long var2) {
         return this.zzws.getByte(var1, var2);
      }
   }

   abstract static class zzd {
      Unsafe zzws;

      zzd(Unsafe var1) {
         this.zzws = var1;
      }

      public abstract void zza(Object var1, long var2, double var4);

      public abstract void zza(Object var1, long var2, float var4);

      public final void zza(Object var1, long var2, int var4) {
         this.zzws.putInt(var1, var2, var4);
      }

      public final void zza(Object var1, long var2, long var4) {
         this.zzws.putLong(var1, var2, var4);
      }

      public abstract void zza(Object var1, long var2, boolean var4);

      public abstract void zze(Object var1, long var2, byte var4);

      public final int zzj(Object var1, long var2) {
         return this.zzws.getInt(var1, var2);
      }

      public final long zzk(Object var1, long var2) {
         return this.zzws.getLong(var1, var2);
      }

      public abstract boolean zzl(Object var1, long var2);

      public abstract float zzm(Object var1, long var2);

      public abstract double zzn(Object var1, long var2);

      public abstract byte zzx(Object var1, long var2);
   }
}
