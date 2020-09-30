/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Process
 *  android.os.WorkSource
 *  android.util.Log
 */
package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.os.WorkSource;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.common.wrappers.Wrappers;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WorkSourceUtil {
    private static final int zza = Process.myUid();
    private static final Method zzb = WorkSourceUtil.zza();
    private static final Method zzc = WorkSourceUtil.zzb();
    private static final Method zzd = WorkSourceUtil.zzc();
    private static final Method zze = WorkSourceUtil.zzd();
    private static final Method zzf = WorkSourceUtil.zze();
    private static final Method zzg = WorkSourceUtil.zzf();
    private static final Method zzh = WorkSourceUtil.zzg();

    private WorkSourceUtil() {
    }

    public static WorkSource fromPackage(Context object, String string2) {
        if (object == null) return null;
        if (object.getPackageManager() == null) return null;
        if (string2 == null) {
            return null;
        }
        try {
            object = Wrappers.packageManager((Context)object).getApplicationInfo(string2, 0);
            if (object != null) return WorkSourceUtil.zza(((ApplicationInfo)object).uid, string2);
            object = String.valueOf(string2);
            object = ((String)object).length() != 0 ? "Could not get applicationInfo from package: ".concat((String)object) : new String("Could not get applicationInfo from package: ");
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            String string3 = String.valueOf(string2);
            string3 = string3.length() != 0 ? "Could not find package: ".concat(string3) : new String("Could not find package: ");
            Log.e((String)"WorkSourceUtil", (String)string3);
        }
        Log.e((String)"WorkSourceUtil", (String)object);
        return null;
    }

    public static WorkSource fromPackageAndModuleExperimentalPi(Context context, String string2, String string3) {
        if (context != null && context.getPackageManager() != null && string3 != null && string2 != null) {
            int n = WorkSourceUtil.zza(context, string2);
            if (n < 0) {
                return null;
            }
            context = new WorkSource();
            Object object = zzg;
            if (object != null && zzh != null) {
                try {
                    object = ((Method)object).invoke((Object)context, new Object[0]);
                    if (n != zza) {
                        zzh.invoke(object, n, string2);
                    }
                    zzh.invoke(object, zza, string3);
                    return context;
                }
                catch (Exception exception) {
                    Log.w((String)"WorkSourceUtil", (String)"Unable to assign chained blame through WorkSource", (Throwable)exception);
                    return context;
                }
            }
            WorkSourceUtil.zza((WorkSource)context, n, string2);
            return context;
        }
        Log.w((String)"WorkSourceUtil", (String)"Unexpected null arguments");
        return null;
    }

    public static List<String> getNames(WorkSource workSource) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n = 0;
        int n2 = workSource == null ? 0 : WorkSourceUtil.zza(workSource);
        if (n2 == 0) {
            return arrayList;
        }
        while (n < n2) {
            String string2 = WorkSourceUtil.zza(workSource, n);
            if (!Strings.isEmptyOrWhitespace(string2)) {
                arrayList.add(Preconditions.checkNotNull(string2));
            }
            ++n;
        }
        return arrayList;
    }

    public static boolean hasWorkSourcePermission(Context context) {
        if (context == null) {
            return false;
        }
        if (context.getPackageManager() == null) {
            return false;
        }
        if (Wrappers.packageManager(context).checkPermission("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) != 0) return false;
        return true;
    }

    private static int zza(Context object, String string2) {
        try {
            object = Wrappers.packageManager((Context)object).getApplicationInfo(string2, 0);
            if (object != null) return ((ApplicationInfo)object).uid;
            object = String.valueOf(string2);
            object = ((String)object).length() != 0 ? "Could not get applicationInfo from package: ".concat((String)object) : new String("Could not get applicationInfo from package: ");
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            String string3 = String.valueOf(string2);
            string3 = string3.length() != 0 ? "Could not find package: ".concat(string3) : new String("Could not find package: ");
            Log.e((String)"WorkSourceUtil", (String)string3);
            return -1;
        }
        Log.e((String)"WorkSourceUtil", (String)object);
        return -1;
    }

    private static int zza(WorkSource workSource) {
        Method method = zzd;
        if (method == null) return 0;
        try {
            return (Integer)Preconditions.checkNotNull(method.invoke((Object)workSource, new Object[0]));
        }
        catch (Exception exception) {
            Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
        }
        return 0;
    }

    private static WorkSource zza(int n, String string2) {
        WorkSource workSource = new WorkSource();
        WorkSourceUtil.zza(workSource, n, string2);
        return workSource;
    }

    private static String zza(WorkSource object, int n) {
        Method method = zzf;
        if (method == null) return null;
        try {
            return (String)method.invoke(object, n);
        }
        catch (Exception exception) {
            Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
        }
        return null;
    }

    private static Method zza() {
        try {
            return WorkSource.class.getMethod("add", Integer.TYPE);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static void zza(WorkSource workSource, int n, String object) {
        if (zzc != null) {
            String string2 = object;
            if (object == null) {
                string2 = "";
            }
            try {
                zzc.invoke((Object)workSource, n, string2);
                return;
            }
            catch (Exception exception) {
                Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
                return;
            }
        }
        object = zzb;
        if (object == null) return;
        try {
            ((Method)object).invoke((Object)workSource, n);
            return;
        }
        catch (Exception exception) {
            Log.wtf((String)"WorkSourceUtil", (String)"Unable to assign blame through WorkSource", (Throwable)exception);
        }
    }

    private static Method zzb() {
        if (!PlatformVersion.isAtLeastJellyBeanMR2()) return null;
        try {
            return WorkSource.class.getMethod("add", Integer.TYPE, String.class);
        }
        catch (Exception exception) {}
        return null;
    }

    private static Method zzc() {
        try {
            return WorkSource.class.getMethod("size", new Class[0]);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static Method zzd() {
        try {
            return WorkSource.class.getMethod("get", Integer.TYPE);
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static Method zze() {
        if (!PlatformVersion.isAtLeastJellyBeanMR2()) return null;
        try {
            return WorkSource.class.getMethod("getName", Integer.TYPE);
        }
        catch (Exception exception) {}
        return null;
    }

    private static final Method zzf() {
        if (!PlatformVersion.isAtLeastP()) return null;
        try {
            return WorkSource.class.getMethod("createWorkChain", new Class[0]);
        }
        catch (Exception exception) {
            Log.w((String)"WorkSourceUtil", (String)"Missing WorkChain API createWorkChain", (Throwable)exception);
        }
        return null;
    }

    private static final Method zzg() {
        if (!PlatformVersion.isAtLeastP()) return null;
        try {
            return Class.forName("android.os.WorkSource$WorkChain").getMethod("addNode", Integer.TYPE, String.class);
        }
        catch (Exception exception) {
            Log.w((String)"WorkSourceUtil", (String)"Missing WorkChain class", (Throwable)exception);
        }
        return null;
    }
}

