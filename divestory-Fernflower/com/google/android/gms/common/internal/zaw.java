package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.view.View;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.RemoteCreator;

public final class zaw extends RemoteCreator<zam> {
   private static final zaw zaa = new zaw();

   private zaw() {
      super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
   }

   public static View zaa(Context var0, int var1, int var2) throws RemoteCreator.RemoteCreatorException {
      return zaa.zab(var0, var1, var2);
   }

   private final View zab(Context var1, int var2, int var3) throws RemoteCreator.RemoteCreatorException {
      try {
         zau var4 = new zau(var2, var3, (Scope[])null);
         IObjectWrapper var5 = ObjectWrapper.wrap(var1);
         View var8 = (View)ObjectWrapper.unwrap(((zam)this.getRemoteCreatorInstance(var1)).zaa(var5, var4));
         return var8;
      } catch (Exception var6) {
         StringBuilder var7 = new StringBuilder(64);
         var7.append("Could not get button with size ");
         var7.append(var2);
         var7.append(" and color ");
         var7.append(var3);
         throw new RemoteCreator.RemoteCreatorException(var7.toString(), var6);
      }
   }

   // $FF: synthetic method
   public final Object getRemoteCreator(IBinder var1) {
      if (var1 == null) {
         return null;
      } else {
         IInterface var2 = var1.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
         return var2 instanceof zam ? (zam)var2 : new zal(var1);
      }
   }
}
