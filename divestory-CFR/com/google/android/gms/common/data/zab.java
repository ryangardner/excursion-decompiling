/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 */
package com.google.android.gms.common.data;

import android.content.ContentValues;
import com.google.android.gms.common.data.DataHolder;
import java.util.HashMap;

final class zab
extends DataHolder.Builder {
    zab(String[] arrstring, String string2) {
        super(arrstring, null, null);
    }

    @Override
    public final DataHolder.Builder withRow(ContentValues contentValues) {
        throw new UnsupportedOperationException("Cannot add data to empty builder");
    }

    @Override
    public final DataHolder.Builder zaa(HashMap<String, Object> hashMap) {
        throw new UnsupportedOperationException("Cannot add data to empty builder");
    }
}

