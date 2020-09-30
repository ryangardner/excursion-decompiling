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
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.CreateFileActivityOptions;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbb;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzu;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzbg
extends TaskApiCall<zzaw, IntentSender> {
    private final /* synthetic */ CreateFileActivityOptions zzer;

    zzbg(zzbb zzbb2, CreateFileActivityOptions createFileActivityOptions) {
        this.zzer = createFileActivityOptions;
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient object, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzaw zzaw2 = (zzaw)object;
        object = (zzeo)zzaw2.getService();
        this.zzer.zzde.zza(zzaw2.getContext());
        taskCompletionSource.setResult(object.zza(new zzu(this.zzer.zzde, this.zzer.zzdk, this.zzer.zzba, this.zzer.zzbd, this.zzer.zzdl)));
    }
}

