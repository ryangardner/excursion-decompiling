/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.internal.zzb;
import java.util.Collection;

final class zzht
extends zzb {
    zzht(String string2, Collection collection, Collection collection2, int n) {
        super(string2, collection, collection2, 7000000);
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return ((zzb)this).zze(dataHolder, n, n2);
    }

    @Override
    protected final Boolean zze(DataHolder dataHolder, int n, int n2) {
        boolean bl;
        if (dataHolder.getInteger("trashed", n, n2) == 2) {
            bl = true;
            return bl;
        }
        bl = false;
        return bl;
    }
}

