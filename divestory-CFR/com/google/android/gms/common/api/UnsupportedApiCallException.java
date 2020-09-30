/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.Feature;

public final class UnsupportedApiCallException
extends UnsupportedOperationException {
    private final Feature zza;

    public UnsupportedApiCallException(Feature feature) {
        this.zza = feature;
    }

    @Override
    public final String getMessage() {
        String string2 = String.valueOf(this.zza);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 8);
        stringBuilder.append("Missing ");
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }
}

