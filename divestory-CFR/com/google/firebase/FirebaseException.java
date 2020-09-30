/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.firebase;

import com.google.android.gms.common.internal.Preconditions;

public class FirebaseException
extends Exception {
    @Deprecated
    protected FirebaseException() {
    }

    public FirebaseException(String string2) {
        super(Preconditions.checkNotEmpty(string2, "Detail message must not be empty"));
    }

    public FirebaseException(String string2, Throwable throwable) {
        super(Preconditions.checkNotEmpty(string2, "Detail message must not be empty"), throwable);
    }
}

