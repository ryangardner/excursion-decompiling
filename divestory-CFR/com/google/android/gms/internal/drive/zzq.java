/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public class zzq {
    public final String zzba;
    public final DriveId zzbd;
    public final MetadataBundle zzde;
    public final Integer zzdk;
    public final int zzdl;

    protected zzq(MetadataBundle metadataBundle, Integer n, String string2, DriveId driveId, int n2) {
        this.zzde = metadataBundle;
        this.zzdk = n;
        this.zzba = string2;
        this.zzbd = driveId;
        this.zzdl = n2;
    }
}

