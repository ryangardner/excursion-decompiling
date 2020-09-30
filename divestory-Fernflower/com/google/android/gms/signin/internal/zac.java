package com.google.android.gms.signin.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

public interface zac extends IInterface {
   void zaa(ConnectionResult var1, zab var2) throws RemoteException;

   void zaa(Status var1) throws RemoteException;

   void zaa(Status var1, GoogleSignInAccount var2) throws RemoteException;

   void zaa(zag var1) throws RemoteException;

   void zaa(zam var1) throws RemoteException;

   void zab(Status var1) throws RemoteException;
}
