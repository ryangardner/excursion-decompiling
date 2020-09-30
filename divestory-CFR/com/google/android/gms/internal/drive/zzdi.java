/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.internal.drive;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.OnChangeListener;
import com.google.android.gms.drive.events.zzi;
import com.google.android.gms.drive.events.zzj;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzdj;
import com.google.android.gms.internal.drive.zzee;

final class zzdi {
    private OnChangeListener zzgg;
    private zzee zzgh;
    private DriveId zzk;

    zzdi(zzch object, OnChangeListener onChangeListener, DriveId driveId) {
        Preconditions.checkState(zzj.zza(1, driveId));
        this.zzgg = onChangeListener;
        this.zzk = driveId;
        driveId = ((GoogleApi)object).getLooper();
        object = ((GoogleApi)object).getApplicationContext();
        onChangeListener.getClass();
        this.zzgh = object = new zzee((Looper)driveId, (Context)object, 1, zzdj.zza(onChangeListener));
        ((zzee)object).zzf(1);
    }

    static /* synthetic */ zzee zza(zzdi zzdi2) {
        return zzdi2.zzgh;
    }
}

