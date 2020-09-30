/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;

public final class zzaa
extends Metadata {
    private final MetadataBundle zzdt;

    public zzaa(MetadataBundle metadataBundle) {
        this.zzdt = metadataBundle;
    }

    @Override
    public final /* synthetic */ Object freeze() {
        return new zzaa(this.zzdt.zzbf());
    }

    @Override
    public final boolean isDataValid() {
        if (this.zzdt == null) return false;
        return true;
    }

    public final String toString() {
        String string2 = String.valueOf(this.zzdt);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 17);
        stringBuilder.append("Metadata [mImpl=");
        stringBuilder.append(string2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public final <T> T zza(MetadataField<T> metadataField) {
        return this.zzdt.zza(metadataField);
    }
}

