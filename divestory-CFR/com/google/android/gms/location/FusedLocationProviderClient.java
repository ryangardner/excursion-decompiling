/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.location.Location
 *  android.os.Looper
 */
package com.google.android.gms.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.location.zzad;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzak;
import com.google.android.gms.internal.location.zzbd;
import com.google.android.gms.internal.location.zzbm;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.zzl;
import com.google.android.gms.location.zzm;
import com.google.android.gms.location.zzn;
import com.google.android.gms.location.zzo;
import com.google.android.gms.location.zzp;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class FusedLocationProviderClient
extends GoogleApi<Api.ApiOptions.NoOptions> {
    public static final String KEY_VERTICAL_ACCURACY = "verticalAccuracy";

    public FusedLocationProviderClient(Activity activity) {
        super(activity, LocationServices.API, null, (StatusExceptionMapper)new ApiExceptionMapper());
    }

    public FusedLocationProviderClient(Context context) {
        super(context, LocationServices.API, null, (StatusExceptionMapper)new ApiExceptionMapper());
    }

    static /* synthetic */ zzaj zza(FusedLocationProviderClient fusedLocationProviderClient, TaskCompletionSource taskCompletionSource) {
        return fusedLocationProviderClient.zza(taskCompletionSource);
    }

    private final zzaj zza(TaskCompletionSource<Boolean> taskCompletionSource) {
        return new zzp(this, taskCompletionSource);
    }

    public Task<Void> flushLocations() {
        return PendingResultUtil.toVoidTask(LocationServices.FusedLocationApi.flushLocations(this.asGoogleApiClient()));
    }

    public Task<Location> getLastLocation() {
        return this.doRead(new zzl(this));
    }

    public Task<LocationAvailability> getLocationAvailability() {
        return this.doRead(new zzm(this));
    }

    public Task<Void> removeLocationUpdates(PendingIntent pendingIntent) {
        return PendingResultUtil.toVoidTask(LocationServices.FusedLocationApi.removeLocationUpdates(this.asGoogleApiClient(), pendingIntent));
    }

    public Task<Void> removeLocationUpdates(LocationCallback locationCallback) {
        return TaskUtil.toVoidTaskThatFailsOnFalse(this.doUnregisterEventListener(ListenerHolders.createListenerKey(locationCallback, LocationCallback.class.getSimpleName())));
    }

    public Task<Void> requestLocationUpdates(LocationRequest locationRequest, PendingIntent pendingIntent) {
        return PendingResultUtil.toVoidTask(LocationServices.FusedLocationApi.requestLocationUpdates(this.asGoogleApiClient(), locationRequest, pendingIntent));
    }

    public Task<Void> requestLocationUpdates(LocationRequest abstractSafeParcelable, LocationCallback object, Looper looper) {
        abstractSafeParcelable = zzbd.zza(abstractSafeParcelable);
        object = ListenerHolders.createListenerHolder(object, zzbm.zza(looper), LocationCallback.class.getSimpleName());
        return this.doRegisterEventListener(new zzn(this, (ListenerHolder)object, (zzbd)abstractSafeParcelable, (ListenerHolder)object), new zzo(this, ((ListenerHolder)object).getListenerKey()));
    }

    public Task<Void> setMockLocation(Location location) {
        return PendingResultUtil.toVoidTask(LocationServices.FusedLocationApi.setMockLocation(this.asGoogleApiClient(), location));
    }

    public Task<Void> setMockMode(boolean bl) {
        return PendingResultUtil.toVoidTask(LocationServices.FusedLocationApi.setMockMode(this.asGoogleApiClient(), bl));
    }

    private static final class zza
    extends zzak {
        private final TaskCompletionSource<Void> zzac;

        public zza(TaskCompletionSource<Void> taskCompletionSource) {
            this.zzac = taskCompletionSource;
        }

        @Override
        public final void zza(zzad zzad2) {
            TaskUtil.setResultOrApiException(zzad2.getStatus(), this.zzac);
        }
    }

}

