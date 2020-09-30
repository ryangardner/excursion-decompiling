/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.Process
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.ChangeListener;
import com.google.android.gms.drive.events.zzd;
import com.google.android.gms.drive.events.zzi;
import com.google.android.gms.drive.events.zzl;
import com.google.android.gms.internal.drive.zzad;
import com.google.android.gms.internal.drive.zzat;
import com.google.android.gms.internal.drive.zzax;
import com.google.android.gms.internal.drive.zzay;
import com.google.android.gms.internal.drive.zzee;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzep;
import com.google.android.gms.internal.drive.zzgs;
import com.google.android.gms.internal.drive.zzj;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzaw
extends GmsClient<zzeo> {
    private final String zzeb;
    protected final boolean zzec;
    private volatile DriveId zzed;
    private volatile DriveId zzee;
    private volatile boolean zzef = false;
    private final Map<DriveId, Map<ChangeListener, zzee>> zzeg = new HashMap<DriveId, Map<ChangeListener, zzee>>();
    private final Map<zzd, zzee> zzeh = new HashMap<zzd, zzee>();
    private final Map<DriveId, Map<zzl, zzee>> zzei = new HashMap<DriveId, Map<zzl, zzee>>();
    private final Map<DriveId, Map<zzl, zzee>> zzej = new HashMap<DriveId, Map<zzl, zzee>>();
    private final Bundle zzz;

    public zzaw(Context object, Looper object2, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, Bundle bundle) {
        super((Context)object, (Looper)object2, 11, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzeb = clientSettings.getRealClientPackageName();
        this.zzz = bundle;
        object2 = new Intent("com.google.android.gms.drive.events.HANDLE_EVENT");
        object2.setPackage(object.getPackageName());
        object = object.getPackageManager().queryIntentServices((Intent)object2, 0);
        int n = object.size();
        if (n == 0) {
            this.zzec = false;
            return;
        }
        if (n != 1) {
            object = object2.getAction();
            object2 = new StringBuilder(String.valueOf(object).length() + 72);
            ((StringBuilder)object2).append("AndroidManifest.xml can only define one service that handles the ");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(" action");
            throw new IllegalStateException(((StringBuilder)object2).toString());
        }
        object = ((ResolveInfo)object.get((int)0)).serviceInfo;
        if (((ServiceInfo)object).exported) {
            this.zzec = true;
            return;
        }
        object = ((ServiceInfo)object).name;
        object2 = new StringBuilder(String.valueOf(object).length() + 60);
        ((StringBuilder)object2).append("Drive event service ");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(" must be exported in AndroidManifest.xml");
        throw new IllegalStateException(((StringBuilder)object2).toString());
    }

    @Override
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.drive.internal.IDriveService");
        if (!(iInterface instanceof zzeo)) return new zzep(iBinder);
        return (zzeo)iInterface;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public final void disconnect() {
        Map<DriveId, Map<zzl, zzee>> map;
        Map<DriveId, Map<zzl, zzee>> map2;
        if (this.isConnected()) {
            try {
                map2 = (zzeo)this.getService();
                map = new Map<DriveId, Map<zzl, zzee>>();
                map2.zza((zzad)((Object)map));
            }
            catch (RemoteException remoteException) {}
        }
        super.disconnect();
        map2 = this.zzeg;
        synchronized (map2) {
            this.zzeg.clear();
        }
        map = this.zzeh;
        synchronized (map) {
            this.zzeh.clear();
        }
        map2 = this.zzei;
        synchronized (map2) {
            this.zzei.clear();
        }
        map = this.zzej;
        synchronized (map) {
            this.zzej.clear();
            return;
        }
    }

    @Override
    protected final Bundle getGetServiceRequestExtraArgs() {
        String string2 = this.getContext().getPackageName();
        Preconditions.checkNotNull(string2);
        Preconditions.checkState(this.getClientSettings().getAllRequestedScopes().isEmpty() ^ true);
        Bundle bundle = new Bundle();
        if (!string2.equals(this.zzeb)) {
            bundle.putString("proxy_package_name", this.zzeb);
        }
        bundle.putAll(this.zzz);
        return bundle;
    }

    @Override
    public final int getMinApkVersion() {
        return 12451000;
    }

    @Override
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.drive.internal.IDriveService";
    }

    @Override
    protected final String getStartServiceAction() {
        return "com.google.android.gms.drive.ApiService.START";
    }

    @Override
    protected final void onPostInitHandler(int n, IBinder iBinder, Bundle bundle, int n2) {
        if (bundle != null) {
            bundle.setClassLoader(this.getClass().getClassLoader());
            this.zzed = (DriveId)bundle.getParcelable("com.google.android.gms.drive.root_id");
            this.zzee = (DriveId)bundle.getParcelable("com.google.android.gms.drive.appdata_id");
            this.zzef = true;
        }
        super.onPostInitHandler(n, iBinder, bundle, n2);
    }

    @Override
    public final boolean requiresAccount() {
        return true;
    }

    @Override
    public final boolean requiresSignIn() {
        if (!this.getContext().getPackageName().equals(this.zzeb)) return true;
        if (UidVerifier.isGooglePlayServicesUid(this.getContext(), Process.myUid())) return false;
        return true;
    }

    final PendingResult<Status> zza(GoogleApiClient object, DriveId object2, ChangeListener object3) {
        Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(1, (DriveId)object2));
        Preconditions.checkNotNull(object3, "listener");
        Preconditions.checkState(this.isConnected(), "Client must be connected");
        Map<DriveId, Map<ChangeListener, zzee>> map = this.zzeg;
        synchronized (map) {
            Object object4;
            Object object5 = object4 = this.zzeg.get(object2);
            if (object4 == null) {
                object5 = new HashMap();
                this.zzeg.put((DriveId)object2, (Map<ChangeListener, zzee>)object5);
            }
            if ((object4 = (zzee)object5.get(object3)) == null) {
                object4 = new zzee(this.getLooper(), this.getContext(), 1, (zzi)object3);
                object5.put(object3, object4);
                object3 = object4;
            } else {
                object3 = object4;
                if (((zzee)object4).zzg(1)) {
                    return new zzat((GoogleApiClient)object, Status.RESULT_SUCCESS);
                }
            }
            ((zzee)object3).zzf(1);
            object5 = new zzj(1, (DriveId)object2);
            object2 = new zzax(this, (GoogleApiClient)object, (zzj)object5, (zzee)object3);
            return ((GoogleApiClient)object).execute(object2);
        }
    }

    public final DriveId zzae() {
        return this.zzed;
    }

    public final DriveId zzaf() {
        return this.zzee;
    }

    public final boolean zzag() {
        return this.zzef;
    }

    public final boolean zzah() {
        return this.zzec;
    }

    final PendingResult<Status> zzb(GoogleApiClient object, DriveId object2, ChangeListener object3) {
        Preconditions.checkArgument(com.google.android.gms.drive.events.zzj.zza(1, (DriveId)object2));
        Preconditions.checkState(this.isConnected(), "Client must be connected");
        Preconditions.checkNotNull(object3, "listener");
        Map<DriveId, Map<ChangeListener, zzee>> map = this.zzeg;
        synchronized (map) {
            Object object4 = this.zzeg.get(object2);
            if (object4 == null) {
                return new zzat((GoogleApiClient)object, Status.RESULT_SUCCESS);
            }
            if ((object3 = object4.remove(object3)) == null) {
                return new zzat((GoogleApiClient)object, Status.RESULT_SUCCESS);
            }
            if (object4.isEmpty()) {
                this.zzeg.remove(object2);
            }
            object4 = new zzgs((DriveId)object2, 1);
            object2 = new zzay(this, (GoogleApiClient)object, (zzgs)object4, (zzee)object3);
            return ((GoogleApiClient)object).execute(object2);
        }
    }
}

