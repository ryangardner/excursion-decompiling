/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common;

import com.google.android.gms.common.zzl;
import com.google.android.gms.common.zzo;
import java.util.concurrent.Callable;

final class zzn
extends zzl {
    private final Callable<String> zzb;

    private zzn(Callable<String> callable) {
        super(false, null, null);
        this.zzb = callable;
    }

    /* synthetic */ zzn(Callable callable, zzo zzo2) {
        this(callable);
    }

    @Override
    final String zzb() {
        try {
            return this.zzb.call();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}

