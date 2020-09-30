/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.StrictMode;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.zzd;
import com.google.android.gms.common.zze;
import com.google.android.gms.common.zzj;
import com.google.android.gms.common.zzl;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
final class zzc {
    private static volatile zzr zza;
    private static final Object zzb;
    private static Context zzc;

    static {
        zzb = new Object();
    }

    static zzl zza(String object, zzd zzd2, boolean bl, boolean bl2) {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.allowThreadDiskReads();
        try {
            object = zzc.zzb((String)object, zzd2, bl, bl2);
            return object;
        }
        finally {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)threadPolicy);
        }
    }

    static final /* synthetic */ String zza(boolean bl, String string2, zzd zzd2) throws Exception {
        boolean bl2 = true;
        if (!bl && zzc.zzb((String)string2, (zzd)zzd2, (boolean)true, (boolean)false).zza) {
            return zzl.zza(string2, zzd2, bl, bl2);
        }
        bl2 = false;
        return zzl.zza(string2, zzd2, bl, bl2);
    }

    static void zza(Context context) {
        synchronized (zzc.class) {
            if (zzc == null) {
                if (context == null) return;
                zzc = context.getApplicationContext();
                return;
            }
            Log.w((String)"GoogleCertificates", (String)"GoogleCertificates has been initialized already");
            return;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    private static zzl zzb(String string2, zzd object3, boolean bl, boolean bl2) {
        void var1_2;
        Object object2;
        boolean bl3;
        void var2_4;
        block8 : {
            try {
                if (zza != null) break block8;
                Preconditions.checkNotNull(zzc);
                object2 = zzb;
                synchronized (object2) {
                    if (zza != null) break block8;
                }
            }
            catch (DynamiteModule.LoadingException object3) {
                Log.e((String)"GoogleCertificates", (String)"Failed to get Google certificates from remote", (Throwable)object3);
                string2 = String.valueOf(((Throwable)object3).getMessage());
                if (string2.length() != 0) {
                    string2 = "module init: ".concat(string2);
                    return zzl.zza(string2, (Throwable)object3);
                }
                string2 = new String("module init: ");
                return zzl.zza(string2, (Throwable)object3);
            }
            {
                zza = zzq.zza(DynamiteModule.load(zzc, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, "com.google.android.gms.googlecertificates").instantiate("com.google.android.gms.common.GoogleCertificatesImpl"));
            }
        }
        Preconditions.checkNotNull(zzc);
        object2 = new zzj(string2, (zzd)var1_2, (boolean)var2_4, bl3);
        try {
            bl3 = zza.zza((zzj)object2, ObjectWrapper.wrap(zzc.getPackageManager()));
            if (!bl3) return zzl.zza(new zze((boolean)var2_4, string2, (zzd)var1_2));
        }
        catch (RemoteException remoteException) {
            Log.e((String)"GoogleCertificates", (String)"Failed to get Google certificates from remote", (Throwable)remoteException);
            return zzl.zza("module call", remoteException);
        }
        return zzl.zza();
    }
}

