package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

public abstract class zaf extends com.google.android.gms.internal.base.zaa implements zac {
   public zaf() {
      super("com.google.android.gms.signin.internal.ISignInCallbacks");
   }

   protected final boolean zaa(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      switch(var1) {
      case 3:
         this.zaa((ConnectionResult)com.google.android.gms.internal.base.zad.zaa(var2, ConnectionResult.CREATOR), (zab)com.google.android.gms.internal.base.zad.zaa(var2, zab.CREATOR));
         break;
      case 4:
         this.zaa((Status)com.google.android.gms.internal.base.zad.zaa(var2, Status.CREATOR));
         break;
      case 5:
      default:
         return false;
      case 6:
         this.zab((Status)com.google.android.gms.internal.base.zad.zaa(var2, Status.CREATOR));
         break;
      case 7:
         this.zaa((Status)com.google.android.gms.internal.base.zad.zaa(var2, Status.CREATOR), (GoogleSignInAccount)com.google.android.gms.internal.base.zad.zaa(var2, GoogleSignInAccount.CREATOR));
         break;
      case 8:
         this.zaa((zam)com.google.android.gms.internal.base.zad.zaa(var2, zam.CREATOR));
         break;
      case 9:
         this.zaa((zag)com.google.android.gms.internal.base.zad.zaa(var2, zag.CREATOR));
      }

      var3.writeNoException();
      return true;
   }
}
