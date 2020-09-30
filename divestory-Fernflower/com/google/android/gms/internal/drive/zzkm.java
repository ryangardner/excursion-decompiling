package com.google.android.gms.internal.drive;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class zzkm {
   private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
   static final Charset UTF_8 = Charset.forName("UTF-8");
   public static final byte[] zzsn;
   private static final ByteBuffer zzso;
   private static final zzjo zzsp;

   static {
      byte[] var0 = new byte[0];
      zzsn = var0;
      zzso = ByteBuffer.wrap(var0);
      var0 = zzsn;
      zzsp = zzjo.zza(var0, 0, var0.length, false);
   }

   static <T> T checkNotNull(T var0) {
      if (var0 != null) {
         return var0;
      } else {
         throw null;
      }
   }

   public static int hashCode(byte[] var0) {
      int var1 = var0.length;
      int var2 = zza(var1, var0, 0, var1);
      var1 = var2;
      if (var2 == 0) {
         var1 = 1;
      }

      return var1;
   }

   static int zza(int var0, byte[] var1, int var2, int var3) {
      int var5 = var0;

      for(var0 = var2; var0 < var2 + var3; ++var0) {
         var5 = var5 * 31 + var1[var0];
      }

      return var5;
   }

   static Object zza(Object var0, Object var1) {
      return ((zzlq)var0).zzcy().zza((zzlq)var1).zzde();
   }

   static <T> T zza(T var0, String var1) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(var1);
      }
   }

   public static boolean zzd(byte[] var0) {
      return zznf.zzd(var0);
   }

   public static int zze(boolean var0) {
      return var0 ? 1231 : 1237;
   }

   public static String zze(byte[] var0) {
      return new String(var0, UTF_8);
   }

   static boolean zzf(zzlq var0) {
      return false;
   }

   public static int zzu(long var0) {
      return (int)(var0 ^ var0 >>> 32);
   }
}
