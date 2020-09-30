/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.util.concurrent;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.concurrent.zza;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory
implements ThreadFactory {
    private final String zza;
    private final int zzb;
    private final ThreadFactory zzc = Executors.defaultThreadFactory();

    public NamedThreadFactory(String string2) {
        this(string2, 0);
    }

    private NamedThreadFactory(String string2, int n) {
        this.zza = Preconditions.checkNotNull(string2, "Name must not be null");
        this.zzb = 0;
    }

    @Override
    public Thread newThread(Runnable runnable2) {
        runnable2 = this.zzc.newThread(new zza(runnable2, 0));
        ((Thread)runnable2).setName(this.zza);
        return runnable2;
    }
}

