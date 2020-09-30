/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.util.concurrent;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.concurrent.zza;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NumberedThreadFactory
implements ThreadFactory {
    private final String zza;
    private final int zzb;
    private final AtomicInteger zzc = new AtomicInteger();
    private final ThreadFactory zzd = Executors.defaultThreadFactory();

    public NumberedThreadFactory(String string2) {
        this(string2, 0);
    }

    private NumberedThreadFactory(String string2, int n) {
        this.zza = Preconditions.checkNotNull(string2, "Name must not be null");
        this.zzb = 0;
    }

    @Override
    public Thread newThread(Runnable runnable2) {
        runnable2 = this.zzd.newThread(new zza(runnable2, 0));
        String string2 = this.zza;
        int n = this.zzc.getAndIncrement();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 13);
        stringBuilder.append(string2);
        stringBuilder.append("[");
        stringBuilder.append(n);
        stringBuilder.append("]");
        ((Thread)runnable2).setName(stringBuilder.toString());
        return runnable2;
    }
}

