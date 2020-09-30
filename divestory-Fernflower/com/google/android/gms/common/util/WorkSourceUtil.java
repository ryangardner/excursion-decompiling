package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Process;
import android.os.WorkSource;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WorkSourceUtil {
   private static final int zza = Process.myUid();
   private static final Method zzb = zza();
   private static final Method zzc = zzb();
   private static final Method zzd = zzc();
   private static final Method zze = zzd();
   private static final Method zzf = zze();
   private static final Method zzg = zzf();
   private static final Method zzh = zzg();

   private WorkSourceUtil() {
   }

   public static WorkSource fromPackage(Context var0, String var1) {
      if (var0 != null && var0.getPackageManager() != null && var1 != null) {
         String var3;
         ApplicationInfo var4;
         try {
            var4 = Wrappers.packageManager(var0).getApplicationInfo(var1, 0);
         } catch (NameNotFoundException var2) {
            var3 = String.valueOf(var1);
            if (var3.length() != 0) {
               var3 = "Could not find package: ".concat(var3);
            } else {
               var3 = new String("Could not find package: ");
            }

            Log.e("WorkSourceUtil", var3);
            return null;
         }

         if (var4 == null) {
            var3 = String.valueOf(var1);
            if (var3.length() != 0) {
               var3 = "Could not get applicationInfo from package: ".concat(var3);
            } else {
               var3 = new String("Could not get applicationInfo from package: ");
            }

            Log.e("WorkSourceUtil", var3);
            return null;
         } else {
            return zza(var4.uid, var1);
         }
      } else {
         return null;
      }
   }

   public static WorkSource fromPackageAndModuleExperimentalPi(Context var0, String var1, String var2) {
      if (var0 != null && var0.getPackageManager() != null && var2 != null && var1 != null) {
         int var3 = zza(var0, var1);
         if (var3 < 0) {
            return null;
         } else {
            WorkSource var6 = new WorkSource();
            Method var4 = zzg;
            if (var4 != null && zzh != null) {
               try {
                  Object var7 = var4.invoke(var6);
                  if (var3 != zza) {
                     zzh.invoke(var7, var3, var1);
                  }

                  zzh.invoke(var7, zza, var2);
               } catch (Exception var5) {
                  Log.w("WorkSourceUtil", "Unable to assign chained blame through WorkSource", var5);
               }
            } else {
               zza(var6, var3, var1);
            }

            return var6;
         }
      } else {
         Log.w("WorkSourceUtil", "Unexpected null arguments");
         return null;
      }
   }

   public static List<String> getNames(WorkSource var0) {
      ArrayList var1 = new ArrayList();
      int var2 = 0;
      int var3;
      if (var0 == null) {
         var3 = 0;
      } else {
         var3 = zza(var0);
      }

      if (var3 == 0) {
         return var1;
      } else {
         for(; var2 < var3; ++var2) {
            String var4 = zza(var0, var2);
            if (!Strings.isEmptyOrWhitespace(var4)) {
               var1.add((String)Preconditions.checkNotNull(var4));
            }
         }

         return var1;
      }
   }

   public static boolean hasWorkSourcePermission(Context var0) {
      if (var0 == null) {
         return false;
      } else if (var0.getPackageManager() == null) {
         return false;
      } else {
         return Wrappers.packageManager(var0).checkPermission("android.permission.UPDATE_DEVICE_STATS", var0.getPackageName()) == 0;
      }
   }

   private static int zza(Context var0, String var1) {
      String var3;
      ApplicationInfo var4;
      try {
         var4 = Wrappers.packageManager(var0).getApplicationInfo(var1, 0);
      } catch (NameNotFoundException var2) {
         var3 = String.valueOf(var1);
         if (var3.length() != 0) {
            var3 = "Could not find package: ".concat(var3);
         } else {
            var3 = new String("Could not find package: ");
         }

         Log.e("WorkSourceUtil", var3);
         return -1;
      }

      if (var4 == null) {
         var3 = String.valueOf(var1);
         if (var3.length() != 0) {
            var3 = "Could not get applicationInfo from package: ".concat(var3);
         } else {
            var3 = new String("Could not get applicationInfo from package: ");
         }

         Log.e("WorkSourceUtil", var3);
         return -1;
      } else {
         return var4.uid;
      }
   }

   private static int zza(WorkSource var0) {
      Method var1 = zzd;
      if (var1 != null) {
         try {
            int var2 = (Integer)Preconditions.checkNotNull(var1.invoke(var0));
            return var2;
         } catch (Exception var3) {
            Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", var3);
         }
      }

      return 0;
   }

   private static WorkSource zza(int var0, String var1) {
      WorkSource var2 = new WorkSource();
      zza(var2, var0, var1);
      return var2;
   }

   private static String zza(WorkSource var0, int var1) {
      Method var2 = zzf;
      if (var2 != null) {
         try {
            String var4 = (String)var2.invoke(var0, var1);
            return var4;
         } catch (Exception var3) {
            Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", var3);
         }
      }

      return null;
   }

   private static Method zza() {
      Method var0;
      try {
         var0 = WorkSource.class.getMethod("add", Integer.TYPE);
      } catch (Exception var1) {
         var0 = null;
      }

      return var0;
   }

   private static void zza(WorkSource var0, int var1, String var2) {
      if (zzc != null) {
         String var3 = var2;
         if (var2 == null) {
            var3 = "";
         }

         try {
            zzc.invoke(var0, var1, var3);
         } catch (Exception var4) {
            Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", var4);
         }
      } else {
         Method var6 = zzb;
         if (var6 != null) {
            try {
               var6.invoke(var0, var1);
               return;
            } catch (Exception var5) {
               Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", var5);
            }
         }

      }
   }

   private static Method zzb() {
      Method var0;
      if (PlatformVersion.isAtLeastJellyBeanMR2()) {
         try {
            var0 = WorkSource.class.getMethod("add", Integer.TYPE, String.class);
            return var0;
         } catch (Exception var1) {
         }
      }

      var0 = null;
      return var0;
   }

   private static Method zzc() {
      Method var0;
      try {
         var0 = WorkSource.class.getMethod("size");
      } catch (Exception var1) {
         var0 = null;
      }

      return var0;
   }

   private static Method zzd() {
      Method var0;
      try {
         var0 = WorkSource.class.getMethod("get", Integer.TYPE);
      } catch (Exception var1) {
         var0 = null;
      }

      return var0;
   }

   private static Method zze() {
      Method var0;
      if (PlatformVersion.isAtLeastJellyBeanMR2()) {
         try {
            var0 = WorkSource.class.getMethod("getName", Integer.TYPE);
            return var0;
         } catch (Exception var1) {
         }
      }

      var0 = null;
      return var0;
   }

   private static final Method zzf() {
      Method var0;
      if (PlatformVersion.isAtLeastP()) {
         try {
            var0 = WorkSource.class.getMethod("createWorkChain");
            return var0;
         } catch (Exception var1) {
            Log.w("WorkSourceUtil", "Missing WorkChain API createWorkChain", var1);
         }
      }

      var0 = null;
      return var0;
   }

   private static final Method zzg() {
      Method var0;
      if (PlatformVersion.isAtLeastP()) {
         try {
            var0 = Class.forName("android.os.WorkSource$WorkChain").getMethod("addNode", Integer.TYPE, String.class);
            return var0;
         } catch (Exception var1) {
            Log.w("WorkSourceUtil", "Missing WorkChain class", var1);
         }
      }

      var0 = null;
      return var0;
   }
}
