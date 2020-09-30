/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive;

import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.zzo;
import com.google.android.gms.drive.zzp;

@Deprecated
public final class zzn
extends ExecutionOptions {
    private boolean zzat;

    private zzn(String string2, boolean bl, int n, boolean bl2) {
        super(string2, bl, n);
        this.zzat = bl2;
    }

    /* synthetic */ zzn(String string2, boolean bl, int n, boolean bl2, zzo zzo2) {
        this(string2, bl, n, bl2);
    }

    public static zzn zza(ExecutionOptions object) {
        zzp zzp2 = new zzp();
        if (object == null) return (zzn)((ExecutionOptions.Builder)zzp2).build();
        ((ExecutionOptions.Builder)zzp2).setConflictStrategy(((ExecutionOptions)object).zzn());
        ((ExecutionOptions.Builder)zzp2).setNotifyOnCompletion(((ExecutionOptions)object).zzm());
        object = ((ExecutionOptions)object).zzl();
        if (object == null) return (zzn)((ExecutionOptions.Builder)zzp2).build();
        ((ExecutionOptions.Builder)zzp2).setTrackingTag((String)object);
        return (zzn)((ExecutionOptions.Builder)zzp2).build();
    }

    public final boolean zzp() {
        return this.zzat;
    }
}

