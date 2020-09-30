/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 */
package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.internal.Preconditions;

public abstract class RemoteCreator<T> {
    private final String zza;
    private T zzb;

    protected RemoteCreator(String string2) {
        this.zza = string2;
    }

    protected abstract T getRemoteCreator(IBinder var1);

    protected final T getRemoteCreatorInstance(Context object) throws RemoteCreatorException {
        if (this.zzb != null) return this.zzb;
        Preconditions.checkNotNull(object);
        object = GooglePlayServicesUtilLight.getRemoteContext((Context)object);
        if (object == null) throw new RemoteCreatorException("Could not get remote context.");
        object = object.getClassLoader();
        try {
            this.zzb = this.getRemoteCreator((IBinder)((ClassLoader)object).loadClass(this.zza).newInstance());
            return this.zzb;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RemoteCreatorException("Could not access creator.", illegalAccessException);
        }
        catch (InstantiationException instantiationException) {
            throw new RemoteCreatorException("Could not instantiate creator.", instantiationException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new RemoteCreatorException("Could not load creator class.", classNotFoundException);
        }
    }

    public static class RemoteCreatorException
    extends Exception {
        public RemoteCreatorException(String string2) {
            super(string2);
        }

        public RemoteCreatorException(String string2, Throwable throwable) {
            super(string2, throwable);
        }
    }

}

