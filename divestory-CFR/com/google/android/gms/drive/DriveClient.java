/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.IntentSender
 */
package com.google.android.gms.drive;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.tasks.Task;

@Deprecated
public abstract class DriveClient
extends GoogleApi<Drive.zza> {
    public DriveClient(Activity activity, Drive.zza zza2) {
        super(activity, Drive.zzw, zza2, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    public DriveClient(Context context, Drive.zza zza2) {
        super(context, Drive.zzw, zza2, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    @Deprecated
    public abstract Task<DriveId> getDriveId(String var1);

    @Deprecated
    public abstract Task<TransferPreferences> getUploadPreferences();

    @Deprecated
    public abstract Task<IntentSender> newCreateFileActivityIntentSender(CreateFileActivityOptions var1);

    @Deprecated
    public abstract Task<IntentSender> newOpenFileActivityIntentSender(OpenFileActivityOptions var1);

    @Deprecated
    public abstract Task<Void> requestSync();

    @Deprecated
    public abstract Task<Void> setUploadPreferences(TransferPreferences var1);
}

