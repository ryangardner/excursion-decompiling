/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataBuffer;

public final class zzaq
implements DriveApi.MetadataBufferResult {
    private final Status zzdy;
    private final MetadataBuffer zzdz;
    private final boolean zzea;

    public zzaq(Status status, MetadataBuffer metadataBuffer, boolean bl) {
        this.zzdy = status;
        this.zzdz = metadataBuffer;
        this.zzea = bl;
    }

    @Override
    public final MetadataBuffer getMetadataBuffer() {
        return this.zzdz;
    }

    @Override
    public final Status getStatus() {
        return this.zzdy;
    }

    @Override
    public final void release() {
        MetadataBuffer metadataBuffer = this.zzdz;
        if (metadataBuffer == null) return;
        metadataBuffer.release();
    }
}

