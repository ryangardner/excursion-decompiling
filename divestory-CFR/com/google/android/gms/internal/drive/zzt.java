/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.IntentSender
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.content.Context;
import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzu;

@Deprecated
public final class zzt {
    private String zzba;
    private DriveId zzbd;
    private Integer zzdk;
    private final int zzdl;
    private MetadataChangeSet zzdm;

    public zzt(int n) {
        this.zzdl = 0;
    }

    public final IntentSender build(GoogleApiClient object) {
        Preconditions.checkState(((GoogleApiClient)object).isConnected(), "Client must be connected");
        this.zzg();
        object = ((GoogleApiClient)object).getClient(Drive.CLIENT_KEY);
        this.zzdm.zzq().zza(((BaseGmsClient)object).getContext());
        try {
            object = (zzeo)((BaseGmsClient)object).getService();
            zzu zzu2 = new zzu(this.zzdm.zzq(), this.zzdk, this.zzba, this.zzbd, 0);
            return object.zza(zzu2);
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Unable to connect Drive Play Service", remoteException);
        }
    }

    public final int getRequestId() {
        return this.zzdk;
    }

    public final void zza(DriveId driveId) {
        this.zzbd = Preconditions.checkNotNull(driveId);
    }

    public final void zza(MetadataChangeSet metadataChangeSet) {
        this.zzdm = Preconditions.checkNotNull(metadataChangeSet);
    }

    public final MetadataChangeSet zzc() {
        return this.zzdm;
    }

    public final void zzc(String string2) {
        this.zzba = Preconditions.checkNotNull(string2);
    }

    public final DriveId zzd() {
        return this.zzbd;
    }

    public final void zzd(int n) {
        this.zzdk = n;
    }

    public final String zze() {
        return this.zzba;
    }

    public final void zzg() {
        Preconditions.checkNotNull(this.zzdm, "Must provide initial metadata via setInitialMetadata.");
        Integer n = this.zzdk;
        int n2 = n == null ? 0 : n;
        this.zzdk = n2;
    }
}

