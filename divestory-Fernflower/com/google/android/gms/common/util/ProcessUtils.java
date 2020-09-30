package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import com.google.android.gms.common.internal.Preconditions;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;

public class ProcessUtils {
   @Nullable
   private static String zza;
   private static int zzb;

   private ProcessUtils() {
   }

   public static String getMyProcessName() {
      if (zza == null) {
         if (zzb == 0) {
            zzb = Process.myPid();
         }

         zza = zza(zzb);
      }

      return zza;
   }

   private static BufferedReader zza(String var0) throws IOException {
      ThreadPolicy var1 = StrictMode.allowThreadDiskReads();

      BufferedReader var5;
      try {
         FileReader var2 = new FileReader(var0);
         var5 = new BufferedReader(var2);
      } finally {
         StrictMode.setThreadPolicy(var1);
      }

      return var5;
   }

   @Nullable
   private static String zza(int var0) {
      Object var1 = null;
      if (var0 <= 0) {
         return null;
      } else {
         label119: {
            StringBuilder var2;
            label107: {
               BufferedReader var16;
               label106: {
                  try {
                     var2 = new StringBuilder(25);
                     var2.append("/proc/");
                     var2.append(var0);
                     var2.append("/cmdline");
                     var16 = zza(var2.toString());
                     break label106;
                  } catch (IOException var14) {
                  } finally {
                     ;
                  }

                  var2 = null;
                  break label107;
               }

               try {
                  String var3 = ((String)Preconditions.checkNotNull(var16.readLine())).trim();
                  break label119;
               } catch (IOException var12) {
               } finally {
                  IOUtils.closeQuietly((Closeable)var16);
                  throw var1;
               }
            }

            IOUtils.closeQuietly((Closeable)var2);
            return (String)var1;
         }

         return (String)var1;
      }
   }
}
