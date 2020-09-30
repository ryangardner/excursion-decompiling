package com.google.android.gms.location;

import android.os.IInterface;
import android.os.RemoteException;

public interface zzu extends IInterface {
   void onLocationAvailability(LocationAvailability var1) throws RemoteException;

   void onLocationResult(LocationResult var1) throws RemoteException;
}
