/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.IntentSender
 *  android.os.RemoteException
 */
package com.google.android.gms.drive;

import android.content.IntentSender;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.drive.query.internal.zzk;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzgm;

@Deprecated
public class OpenFileActivityBuilder {
    public static final String EXTRA_RESPONSE_DRIVE_ID = "response_drive_id";
    private String zzba;
    private String[] zzbb;
    private Filter zzbc;
    private DriveId zzbd;

    public IntentSender build(GoogleApiClient object) {
        Preconditions.checkState(((GoogleApiClient)object).isConnected(), "Client must be connected");
        this.zzg();
        FilterHolder filterHolder = this.zzbc == null ? null : new FilterHolder(this.zzbc);
        try {
            object = (zzeo)((GoogleApiClient)object).getClient(Drive.CLIENT_KEY).getService();
            zzgm zzgm2 = new zzgm(this.zzba, this.zzbb, this.zzbd, filterHolder);
            return object.zza(zzgm2);
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Unable to connect Drive Play Service", remoteException);
        }
    }

    final String getTitle() {
        return this.zzba;
    }

    public OpenFileActivityBuilder setActivityStartFolder(DriveId driveId) {
        this.zzbd = Preconditions.checkNotNull(driveId);
        return this;
    }

    public OpenFileActivityBuilder setActivityTitle(String string2) {
        this.zzba = Preconditions.checkNotNull(string2);
        return this;
    }

    public OpenFileActivityBuilder setMimeType(String[] arrstring) {
        boolean bl = arrstring != null;
        Preconditions.checkArgument(bl, "mimeTypes may not be null");
        this.zzbb = arrstring;
        return this;
    }

    public OpenFileActivityBuilder setSelectionFilter(Filter filter) {
        boolean bl = filter != null;
        Preconditions.checkArgument(bl, "filter may not be null");
        Preconditions.checkArgument(true ^ zzk.zza(filter), "FullTextSearchFilter cannot be used as a selection filter");
        this.zzbc = filter;
        return this;
    }

    final void zzg() {
        if (this.zzbb == null) {
            this.zzbb = new String[0];
        }
        if (this.zzbb.length <= 0) return;
        if (this.zzbc != null) throw new IllegalStateException("Cannot use a selection filter and set mimetypes simultaneously");
    }

    final String[] zzs() {
        return this.zzbb;
    }

    final Filter zzt() {
        return this.zzbc;
    }

    final DriveId zzu() {
        return this.zzbd;
    }
}

