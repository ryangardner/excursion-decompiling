/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  com.google.android.gms.auth.GoogleAuthException
 *  com.google.android.gms.auth.UserRecoverableAuthException
 */
package com.google.api.client.googleapis.extensions.android.gms.auth;

import android.content.Intent;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException;

public class UserRecoverableAuthIOException
extends GoogleAuthIOException {
    private static final long serialVersionUID = 1L;

    public UserRecoverableAuthIOException(UserRecoverableAuthException userRecoverableAuthException) {
        super((GoogleAuthException)((Object)userRecoverableAuthException));
    }

    @Override
    public UserRecoverableAuthException getCause() {
        return (UserRecoverableAuthException)((Object)super.getCause());
    }

    public final Intent getIntent() {
        return this.getCause().getIntent();
    }
}

