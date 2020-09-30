/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.IntentSender
 */
package com.google.android.gms.internal.drive;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.internal.drive.zzbc;
import com.google.android.gms.internal.drive.zzbd;
import com.google.android.gms.internal.drive.zzbe;
import com.google.android.gms.internal.drive.zzbf;
import com.google.android.gms.internal.drive.zzbg;
import com.google.android.gms.internal.drive.zzbh;
import com.google.android.gms.tasks.Task;

public final class zzbb
extends DriveClient {
    public zzbb(Activity activity, Drive.zza zza2) {
        super(activity, zza2);
    }

    public zzbb(Context context, Drive.zza zza2) {
        super(context, zza2);
    }

    @Override
    public final Task<DriveId> getDriveId(String string2) {
        Preconditions.checkNotNull(string2, "resourceId must not be null");
        return this.doRead(new zzbc(this, string2));
    }

    @Override
    public final Task<TransferPreferences> getUploadPreferences() {
        return this.doRead(new zzbd(this));
    }

    @Override
    public final Task<IntentSender> newCreateFileActivityIntentSender(CreateFileActivityOptions createFileActivityOptions) {
        return this.doRead(new zzbg(this, createFileActivityOptions));
    }

    @Override
    public final Task<IntentSender> newOpenFileActivityIntentSender(OpenFileActivityOptions openFileActivityOptions) {
        return this.doRead(new zzbf(this, openFileActivityOptions));
    }

    @Override
    public final Task<Void> requestSync() {
        return this.doWrite(new zzbh(this));
    }

    @Override
    public final Task<Void> setUploadPreferences(TransferPreferences transferPreferences) {
        Preconditions.checkNotNull(transferPreferences, "transferPreferences cannot be null.");
        return this.doWrite(new zzbe(this, transferPreferences));
    }
}

