package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.StatusCallback;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import javax.annotation.Nullable;

public final class zzaz extends zzk {
   private final zzas zzde;

   public zzaz(Context var1, Looper var2, GoogleApiClient.ConnectionCallbacks var3, GoogleApiClient.OnConnectionFailedListener var4, String var5) {
      this(var1, var2, var3, var4, var5, ClientSettings.createDefault(var1));
   }

   public zzaz(Context var1, Looper var2, GoogleApiClient.ConnectionCallbacks var3, GoogleApiClient.OnConnectionFailedListener var4, String var5, @Nullable ClientSettings var6) {
      super(var1, var2, var3, var4, var5, var6);
      this.zzde = new zzas(var1, this.zzcb);
   }

   public final void disconnect() {
      // $FF: Couldn't be decompiled
   }

   public final Location getLastLocation() throws RemoteException {
      return this.zzde.getLastLocation();
   }

   public final LocationAvailability zza() throws RemoteException {
      return this.zzde.zza();
   }

   public final void zza(long var1, PendingIntent var3) throws RemoteException {
      this.checkConnected();
      Preconditions.checkNotNull(var3);
      boolean var4;
      if (var1 >= 0L) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "detectionIntervalMillis must be >= 0");
      ((zzao)this.getService()).zza(var1, true, var3);
   }

   public final void zza(PendingIntent var1, BaseImplementation.ResultHolder<Status> var2) throws RemoteException {
      this.checkConnected();
      Preconditions.checkNotNull(var2, "ResultHolder not provided.");
      StatusCallback var3 = new StatusCallback(var2);
      ((zzao)this.getService()).zza((PendingIntent)var1, (IStatusCallback)var3);
   }

   public final void zza(PendingIntent var1, zzaj var2) throws RemoteException {
      this.zzde.zza(var1, var2);
   }

   public final void zza(Location var1) throws RemoteException {
      this.zzde.zza(var1);
   }

   public final void zza(ListenerHolder.ListenerKey<LocationListener> var1, zzaj var2) throws RemoteException {
      this.zzde.zza(var1, var2);
   }

   public final void zza(zzaj var1) throws RemoteException {
      this.zzde.zza(var1);
   }

   public final void zza(zzbd param1, ListenerHolder<LocationCallback> param2, zzaj param3) throws RemoteException {
      // $FF: Couldn't be decompiled
   }

   public final void zza(ActivityTransitionRequest var1, PendingIntent var2, BaseImplementation.ResultHolder<Status> var3) throws RemoteException {
      this.checkConnected();
      Preconditions.checkNotNull(var3, "ResultHolder not provided.");
      StatusCallback var4 = new StatusCallback(var3);
      ((zzao)this.getService()).zza((ActivityTransitionRequest)var1, (PendingIntent)var2, (IStatusCallback)var4);
   }

   public final void zza(GeofencingRequest var1, PendingIntent var2, BaseImplementation.ResultHolder<Status> var3) throws RemoteException {
      this.checkConnected();
      Preconditions.checkNotNull(var1, "geofencingRequest can't be null.");
      Preconditions.checkNotNull(var2, "PendingIntent must be specified.");
      Preconditions.checkNotNull(var3, "ResultHolder not provided.");
      zzba var4 = new zzba(var3);
      ((zzao)this.getService()).zza((GeofencingRequest)var1, (PendingIntent)var2, (zzam)var4);
   }

   public final void zza(LocationRequest var1, PendingIntent var2, zzaj var3) throws RemoteException {
      this.zzde.zza(var1, var2, var3);
   }

   public final void zza(LocationRequest param1, ListenerHolder<LocationListener> param2, zzaj param3) throws RemoteException {
      // $FF: Couldn't be decompiled
   }

   public final void zza(LocationSettingsRequest var1, BaseImplementation.ResultHolder<LocationSettingsResult> var2, @Nullable String var3) throws RemoteException {
      this.checkConnected();
      boolean var4 = true;
      boolean var5;
      if (var1 != null) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5, "locationSettingsRequest can't be null nor empty.");
      if (var2 != null) {
         var5 = var4;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5, "listener can't be null.");
      zzbc var6 = new zzbc(var2);
      ((zzao)this.getService()).zza((LocationSettingsRequest)var1, (zzaq)var6, (String)var3);
   }

   public final void zza(com.google.android.gms.location.zzal var1, BaseImplementation.ResultHolder<Status> var2) throws RemoteException {
      this.checkConnected();
      Preconditions.checkNotNull(var1, "removeGeofencingRequest can't be null.");
      Preconditions.checkNotNull(var2, "ResultHolder not provided.");
      zzbb var3 = new zzbb(var2);
      ((zzao)this.getService()).zza((com.google.android.gms.location.zzal)var1, (zzam)var3);
   }

   public final void zza(boolean var1) throws RemoteException {
      this.zzde.zza(var1);
   }

   public final void zzb(PendingIntent var1) throws RemoteException {
      this.checkConnected();
      Preconditions.checkNotNull(var1);
      ((zzao)this.getService()).zzb(var1);
   }

   public final void zzb(ListenerHolder.ListenerKey<LocationCallback> var1, zzaj var2) throws RemoteException {
      this.zzde.zzb(var1, var2);
   }
}
