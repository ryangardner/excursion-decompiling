package com.google.android.gms.internal.location;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;

public class zzk extends GmsClient<zzao> {
   private final String zzca;
   protected final zzbj<zzao> zzcb = new zzl(this);

   public zzk(Context var1, Looper var2, GoogleApiClient.ConnectionCallbacks var3, GoogleApiClient.OnConnectionFailedListener var4, String var5, ClientSettings var6) {
      super(var1, var2, 23, var6, (GoogleApiClient.ConnectionCallbacks)var3, (GoogleApiClient.OnConnectionFailedListener)var4);
      this.zzca = var5;
   }

   // $FF: synthetic method
   static void zza(zzk var0) {
      var0.checkConnected();
   }

   // $FF: synthetic method
   protected IInterface createServiceInterface(IBinder var1) {
      if (var1 == null) {
         return null;
      } else {
         IInterface var2 = var1.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
         return (IInterface)(var2 instanceof zzao ? (zzao)var2 : new zzap(var1));
      }
   }

   protected Bundle getGetServiceRequestExtraArgs() {
      Bundle var1 = new Bundle();
      var1.putString("client_name", this.zzca);
      return var1;
   }

   public int getMinApkVersion() {
      return 11925000;
   }

   protected String getServiceDescriptor() {
      return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
   }

   protected String getStartServiceAction() {
      return "com.google.android.location.internal.GoogleLocationManagerService.START";
   }
}
