/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.google.android.gms.auth.GoogleAuthException
 */
package com.google.api.client.googleapis.extensions.android.gms.auth;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class GoogleAuthIOException
extends IOException {
    private static final long serialVersionUID = 1L;

    public GoogleAuthIOException(GoogleAuthException googleAuthException) {
        this.initCause((Throwable)Preconditions.checkNotNull(googleAuthException));
    }

    public GoogleAuthException getCause() {
        return (GoogleAuthException)super.getCause();
    }
}

