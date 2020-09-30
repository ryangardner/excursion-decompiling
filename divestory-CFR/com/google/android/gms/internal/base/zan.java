/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.base;

import com.google.android.gms.internal.base.zam;
import com.google.android.gms.internal.base.zao;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

final class zan
implements zam {
    private zan() {
    }

    /* synthetic */ zan(zao zao2) {
        this();
    }

    @Override
    public final ExecutorService zaa(int n, int n2) {
        return this.zaa(4, Executors.defaultThreadFactory(), n2);
    }

    @Override
    public final ExecutorService zaa(int n, ThreadFactory object, int n2) {
        object = new ThreadPoolExecutor(n, n, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), (ThreadFactory)object);
        ((ThreadPoolExecutor)object).allowCoreThreadTimeOut(true);
        return Executors.unconfigurableExecutorService((ExecutorService)object);
    }

    @Override
    public final ExecutorService zaa(ThreadFactory threadFactory2, int n) {
        return this.zaa(1, threadFactory2, n);
    }
}

