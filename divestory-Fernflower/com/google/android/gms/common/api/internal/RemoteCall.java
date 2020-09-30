package com.google.android.gms.common.api.internal;

import android.os.RemoteException;

public interface RemoteCall<T, U> {
   void accept(T var1, U var2) throws RemoteException;
}
