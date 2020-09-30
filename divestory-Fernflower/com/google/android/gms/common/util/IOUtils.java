package com.google.android.gms.common.util;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.internal.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.Nullable;

@Deprecated
public final class IOUtils {
   private IOUtils() {
   }

   public static void closeQuietly(@Nullable ParcelFileDescriptor var0) {
      if (var0 != null) {
         try {
            var0.close();
         } catch (IOException var1) {
         }
      }

   }

   public static void closeQuietly(@Nullable Closeable var0) {
      if (var0 != null) {
         try {
            var0.close();
         } catch (IOException var1) {
         }
      }

   }

   @Deprecated
   public static long copyStream(InputStream var0, OutputStream var1) throws IOException {
      return zza(var0, var1, false);
   }

   @Deprecated
   public static long copyStream(InputStream var0, OutputStream var1, boolean var2, int var3) throws IOException {
      byte[] var4 = new byte[var3];
      long var5 = 0L;

      while(true) {
         Throwable var10000;
         label120: {
            boolean var10001;
            int var7;
            try {
               var7 = var0.read(var4, 0, var3);
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label120;
            }

            if (var7 == -1) {
               if (var2) {
                  closeQuietly((Closeable)var0);
                  closeQuietly((Closeable)var1);
               }

               return var5;
            }

            var5 += (long)var7;

            label108:
            try {
               var1.write(var4, 0, var7);
               continue;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break label108;
            }
         }

         Throwable var14 = var10000;
         if (var2) {
            closeQuietly((Closeable)var0);
            closeQuietly((Closeable)var1);
         }

         throw var14;
      }
   }

   public static boolean isGzipByteBuffer(byte[] var0) {
      if (var0.length > 1) {
         byte var1 = var0[0];
         if (((var0[1] & 255) << 8 | var1 & 255) == 35615) {
            return true;
         }
      }

      return false;
   }

   @Deprecated
   public static byte[] readInputStreamFully(InputStream var0) throws IOException {
      return readInputStreamFully(var0, true);
   }

   @Deprecated
   public static byte[] readInputStreamFully(InputStream var0, boolean var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      zza(var0, var2, var1);
      return var2.toByteArray();
   }

   @Deprecated
   public static byte[] toByteArray(InputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      byte[] var2 = new byte[4096];

      while(true) {
         int var3 = var0.read(var2);
         if (var3 == -1) {
            return var1.toByteArray();
         }

         var1.write(var2, 0, var3);
      }
   }

   @Deprecated
   private static long zza(InputStream var0, OutputStream var1, boolean var2) throws IOException {
      return copyStream(var0, var1, var2, 1024);
   }
}
