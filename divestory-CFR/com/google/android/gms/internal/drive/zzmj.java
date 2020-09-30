/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkd;
import com.google.android.gms.internal.drive.zzmi;
import java.util.Collections;
import java.util.List;
import java.util.Map;

final class zzmj
extends zzmi<FieldDescriptorType, Object> {
    zzmj(int n) {
        super(n, null);
    }

    @Override
    public final void zzbp() {
        if (!this.isImmutable()) {
            for (int i = 0; i < this.zzer(); ++i) {
                Map.Entry entry = this.zzaw(i);
                if (!((zzkd)entry.getKey()).zzcs()) continue;
                entry.setValue(Collections.unmodifiableList((List)entry.getValue()));
            }
            for (Map.Entry entry : this.zzes()) {
                if (!((zzkd)entry.getKey()).zzcs()) continue;
                entry.setValue(Collections.unmodifiableList((List)entry.getValue()));
            }
        }
        super.zzbp();
    }
}

