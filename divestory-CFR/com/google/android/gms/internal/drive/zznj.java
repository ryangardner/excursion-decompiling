/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

final class zznj
extends IllegalArgumentException {
    zznj(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder(54);
        stringBuilder.append("Unpaired surrogate at index ");
        stringBuilder.append(n);
        stringBuilder.append(" of ");
        stringBuilder.append(n2);
        super(stringBuilder.toString());
    }
}

