/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.os.IInterface
 */
package com.google.android.gms.internal.location;

import android.os.DeadObjectException;
import android.os.IInterface;

public interface zzbj<T extends IInterface> {
    public void checkConnected();

    public T getService() throws DeadObjectException;
}

