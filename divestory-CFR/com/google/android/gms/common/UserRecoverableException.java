/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.common;

import android.content.Intent;

public class UserRecoverableException
extends Exception {
    private final Intent zza;

    public UserRecoverableException(String string2, Intent intent) {
        super(string2);
        this.zza = intent;
    }

    public Intent getIntent() {
        return new Intent(this.zza);
    }
}

