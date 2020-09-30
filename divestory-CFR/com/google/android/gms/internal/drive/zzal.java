/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;

final class zzal
implements Releasable,
DriveApi.DriveContentsResult {
    private final Status zzdy;
    private final DriveContents zzo;

    public zzal(Status status, DriveContents driveContents) {
        this.zzdy = status;
        this.zzo = driveContents;
    }

    @Override
    public final DriveContents getDriveContents() {
        return this.zzo;
    }

    @Override
    public final Status getStatus() {
        return this.zzdy;
    }

    @Override
    public final void release() {
        DriveContents driveContents = this.zzo;
        if (driveContents == null) return;
        driveContents.zzj();
    }
}

