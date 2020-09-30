/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.os.AsyncTask
 *  android.util.Log
 */
package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CrashUtils;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.security.zza;
import java.lang.reflect.Method;

public class ProviderInstaller {
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    private static final GoogleApiAvailabilityLight zza = GoogleApiAvailabilityLight.getInstance();
    private static final Object zzb = new Object();
    private static Method zzc = null;

    public static void installIfNeeded(Context object) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        Object object2;
        Preconditions.checkNotNull(object, "Context must not be null");
        zza.verifyGooglePlayServicesIsAvailable((Context)object, 11925000);
        Context context = object2 = ProviderInstaller.zza((Context)object);
        if (object2 == null) {
            context = ProviderInstaller.zzb((Context)object);
        }
        if (context == null) {
            Log.e((String)"ProviderInstaller", (String)"Failed to get remote context");
            throw new GooglePlayServicesNotAvailableException(8);
        }
        object2 = zzb;
        synchronized (object2) {
            try {
                try {
                    if (zzc == null) {
                        zzc = context.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", Context.class);
                    }
                    zzc.invoke(null, new Object[]{context});
                    return;
                }
                catch (Exception exception) {
                    object = exception.getCause();
                    if (Log.isLoggable((String)"ProviderInstaller", (int)6)) {
                        object = object == null ? exception.getMessage() : ((Throwable)object).getMessage();
                        object = String.valueOf(object);
                        object = ((String)object).length() != 0 ? "Failed to install provider: ".concat((String)object) : new String("Failed to install provider: ");
                        Log.e((String)"ProviderInstaller", (String)object);
                    }
                    object = new GooglePlayServicesNotAvailableException(8);
                    throw object;
                }
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public static void installIfNeededAsync(Context context, ProviderInstallListener providerInstallListener) {
        Preconditions.checkNotNull(context, "Context must not be null");
        Preconditions.checkNotNull(providerInstallListener, "Listener must not be null");
        Preconditions.checkMainThread("Must be called on the UI thread");
        new zza(context, providerInstallListener).execute((Object[])new Void[0]);
    }

    private static Context zza(Context context) {
        try {
            return DynamiteModule.load(context, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, "com.google.android.gms.providerinstaller").getModuleContext();
        }
        catch (DynamiteModule.LoadingException loadingException) {
            String string2 = String.valueOf(loadingException.getMessage());
            string2 = string2.length() != 0 ? "Failed to load providerinstaller module: ".concat(string2) : new String("Failed to load providerinstaller module: ");
            Log.w((String)"ProviderInstaller", (String)string2);
            return null;
        }
    }

    static /* synthetic */ GoogleApiAvailabilityLight zza() {
        return zza;
    }

    private static Context zzb(Context context) {
        try {
            return GooglePlayServicesUtilLight.getRemoteContext(context);
        }
        catch (Resources.NotFoundException notFoundException) {
            String string2 = String.valueOf(notFoundException.getMessage());
            string2 = string2.length() != 0 ? "Failed to load GMS Core context for providerinstaller: ".concat(string2) : new String("Failed to load GMS Core context for providerinstaller: ");
            Log.w((String)"ProviderInstaller", (String)string2);
            CrashUtils.addDynamiteErrorToDropBox(context, notFoundException);
            return null;
        }
    }

    public static interface ProviderInstallListener {
        public void onProviderInstallFailed(int var1, Intent var2);

        public void onProviderInstalled();
    }

}

