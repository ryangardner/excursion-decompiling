/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.internal.zzb;

public final class zzic
extends zzb
implements SearchableMetadataField<Boolean> {
    public zzic(String string2, int n) {
        super(string2, 4100000);
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return ((zzb)this).zze(dataHolder, n, n2);
    }

    @Override
    protected final Boolean zze(DataHolder dataHolder, int n, int n2) {
        boolean bl;
        if (dataHolder.getInteger(this.getName(), n, n2) != 0) {
            bl = true;
            return bl;
        }
        bl = false;
        return bl;
    }
}

