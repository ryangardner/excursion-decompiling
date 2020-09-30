/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive.query.internal;

import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.zzb;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.internal.zzj;
import com.google.android.gms.drive.query.internal.zzx;
import java.util.List;

public final class zzk
implements zzj<Boolean> {
    private Boolean zzmm = false;

    private zzk() {
    }

    public static boolean zza(Filter filter) {
        if (filter != null) return filter.zza(new zzk());
        return false;
    }

    @Override
    public final /* synthetic */ Object zza(zzb zzb2, Object object) {
        return this.zzmm;
    }

    @Override
    public final /* synthetic */ Object zza(zzx zzx2, MetadataField metadataField, Object object) {
        return this.zzmm;
    }

    @Override
    public final /* synthetic */ Object zza(zzx zzx2, List list) {
        return this.zzmm;
    }

    @Override
    public final /* synthetic */ Object zza(Object object) {
        return this.zzmm;
    }

    @Override
    public final /* synthetic */ Object zzbj() {
        return this.zzmm;
    }

    @Override
    public final /* synthetic */ Object zzbk() {
        return this.zzmm;
    }

    @Override
    public final /* synthetic */ Object zzc(MetadataField metadataField, Object object) {
        return this.zzmm;
    }

    @Override
    public final /* synthetic */ Object zze(MetadataField metadataField) {
        return this.zzmm;
    }

    @Override
    public final /* synthetic */ Object zzi(String string2) {
        if (string2.isEmpty()) return this.zzmm;
        this.zzmm = true;
        return this.zzmm;
    }
}

