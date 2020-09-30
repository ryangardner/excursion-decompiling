/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.internal.zzm;
import java.util.Collection;

final class zzhu
extends zzm<BitmapTeleporter> {
    zzhu(String string2, Collection collection, Collection collection2, int n) {
        super(string2, collection, collection2, 4400000);
    }

    @Override
    protected final /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        throw new IllegalStateException("Thumbnail field is write only");
    }
}

