/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common;

public final class GooglePlayServicesNotAvailableException
extends Exception {
    public final int errorCode;

    public GooglePlayServicesNotAvailableException(int n) {
        this.errorCode = n;
    }
}

