/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.google.android.gms.auth.GoogleAuthException
 *  com.google.android.gms.auth.GooglePlayServicesAvailabilityException
 *  com.google.android.gms.auth.UserRecoverableAuthException
 */
package com.google.api.client.googleapis.extensions.android.gms.auth;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

public class GooglePlayServicesAvailabilityIOException
extends UserRecoverableAuthIOException {
    private static final long serialVersionUID = 1L;

    public GooglePlayServicesAvailabilityIOException(GooglePlayServicesAvailabilityException googlePlayServicesAvailabilityException) {
        super((UserRecoverableAuthException)((Object)googlePlayServicesAvailabilityException));
    }

    @Override
    public GooglePlayServicesAvailabilityException getCause() {
        return (GooglePlayServicesAvailabilityException)((Object)super.getCause());
    }

    public final int getConnectionStatusCode() {
        return this.getCause().getConnectionStatusCode();
    }
}

