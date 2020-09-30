/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.signin.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.signin.internal.zab;
import com.google.android.gms.signin.internal.zag;
import com.google.android.gms.signin.internal.zam;

public interface zac
extends IInterface {
    public void zaa(ConnectionResult var1, zab var2) throws RemoteException;

    public void zaa(Status var1) throws RemoteException;

    public void zaa(Status var1, GoogleSignInAccount var2) throws RemoteException;

    public void zaa(zag var1) throws RemoteException;

    public void zaa(zam var1) throws RemoteException;

    public void zab(Status var1) throws RemoteException;
}

