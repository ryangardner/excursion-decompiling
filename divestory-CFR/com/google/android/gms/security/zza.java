/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 */
package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

final class zza
extends AsyncTask<Void, Void, Integer> {
    private final /* synthetic */ Context zza;
    private final /* synthetic */ ProviderInstaller.ProviderInstallListener zzb;

    zza(Context context, ProviderInstaller.ProviderInstallListener providerInstallListener) {
        this.zza = context;
        this.zzb = providerInstallListener;
    }

    private final Integer zza(Void ... arrvoid) {
        try {
            ProviderInstaller.installIfNeeded(this.zza);
        }
        catch (GooglePlayServicesNotAvailableException googlePlayServicesNotAvailableException) {
            return googlePlayServicesNotAvailableException.errorCode;
        }
        catch (GooglePlayServicesRepairableException googlePlayServicesRepairableException) {
            return googlePlayServicesRepairableException.getConnectionStatusCode();
        }
        return 0;
    }

    protected final /* synthetic */ Object doInBackground(Object[] arrobject) {
        return this.zza((Void[])arrobject);
    }

    protected final /* synthetic */ void onPostExecute(Object object) {
        if ((Integer)(object = (Integer)object) == 0) {
            this.zzb.onProviderInstalled();
            return;
        }
        Intent intent = ProviderInstaller.zza().getErrorResolutionIntent(this.zza, (Integer)object, "pi");
        this.zzb.onProviderInstallFailed((Integer)object, intent);
    }
}

