/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive;

import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.zzn;
import com.google.android.gms.drive.zzo;

public final class zzp
extends ExecutionOptions.Builder {
    private boolean zzat = true;

    @Override
    public final /* synthetic */ ExecutionOptions build() {
        this.zzo();
        return new zzn(this.zzaq, this.zzar, this.zzas, this.zzat, null);
    }

    @Override
    public final /* synthetic */ ExecutionOptions.Builder setConflictStrategy(int n) {
        super.setConflictStrategy(n);
        return this;
    }

    @Override
    public final /* synthetic */ ExecutionOptions.Builder setNotifyOnCompletion(boolean bl) {
        super.setNotifyOnCompletion(bl);
        return this;
    }

    @Override
    public final /* synthetic */ ExecutionOptions.Builder setTrackingTag(String string2) {
        super.setTrackingTag(string2);
        return this;
    }
}

