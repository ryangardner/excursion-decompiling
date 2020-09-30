/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.firebase;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseException;

public class FirebaseExceptionMapper
implements StatusExceptionMapper {
    @Override
    public Exception getException(Status status) {
        if (status.getStatusCode() != 8) return new FirebaseApiNotAvailableException(status.zza());
        return new FirebaseException(status.zza());
    }
}

