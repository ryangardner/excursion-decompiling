/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadFactoryBuilder {
    private ThreadFactory backingThreadFactory = null;
    private Boolean daemon = null;
    private String nameFormat = null;
    private Integer priority = null;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;

    private static ThreadFactory doBuild(ThreadFactoryBuilder object) {
        AtomicLong atomicLong;
        String string2 = ((ThreadFactoryBuilder)object).nameFormat;
        Boolean bl = ((ThreadFactoryBuilder)object).daemon;
        Integer n = ((ThreadFactoryBuilder)object).priority;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = ((ThreadFactoryBuilder)object).uncaughtExceptionHandler;
        object = ((ThreadFactoryBuilder)object).backingThreadFactory;
        if (object == null) {
            object = Executors.defaultThreadFactory();
        }
        if (string2 != null) {
            atomicLong = new AtomicLong(0L);
            return new ThreadFactory((ThreadFactory)object, string2, atomicLong, bl, n, uncaughtExceptionHandler){
                final /* synthetic */ ThreadFactory val$backingThreadFactory;
                final /* synthetic */ AtomicLong val$count;
                final /* synthetic */ Boolean val$daemon;
                final /* synthetic */ String val$nameFormat;
                final /* synthetic */ Integer val$priority;
                final /* synthetic */ Thread.UncaughtExceptionHandler val$uncaughtExceptionHandler;
                {
                    this.val$backingThreadFactory = threadFactory2;
                    this.val$nameFormat = string2;
                    this.val$count = atomicLong;
                    this.val$daemon = bl;
                    this.val$priority = n;
                    this.val$uncaughtExceptionHandler = uncaughtExceptionHandler;
                }

                @Override
                public Thread newThread(Runnable runnable2) {
                    runnable2 = this.val$backingThreadFactory.newThread(runnable2);
                    Object object = this.val$nameFormat;
                    if (object != null) {
                        ((Thread)runnable2).setName(ThreadFactoryBuilder.format((String)object, new Object[]{this.val$count.getAndIncrement()}));
                    }
                    if ((object = this.val$daemon) != null) {
                        ((Thread)runnable2).setDaemon((Boolean)object);
                    }
                    if ((object = this.val$priority) != null) {
                        ((Thread)runnable2).setPriority((Integer)object);
                    }
                    if ((object = this.val$uncaughtExceptionHandler) == null) return runnable2;
                    ((Thread)runnable2).setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)object);
                    return runnable2;
                }
            };
        }
        atomicLong = null;
        return new /* invalid duplicate definition of identical inner class */;
    }

    private static String format(String string2, Object ... arrobject) {
        return String.format(Locale.ROOT, string2, arrobject);
    }

    @CheckReturnValue
    public ThreadFactory build() {
        return ThreadFactoryBuilder.doBuild(this);
    }

    public ThreadFactoryBuilder setDaemon(boolean bl) {
        this.daemon = bl;
        return this;
    }

    public ThreadFactoryBuilder setNameFormat(String string2) {
        ThreadFactoryBuilder.format(string2, 0);
        this.nameFormat = string2;
        return this;
    }

    public ThreadFactoryBuilder setPriority(int n) {
        boolean bl = false;
        boolean bl2 = n >= 1;
        Preconditions.checkArgument(bl2, "Thread priority (%s) must be >= %s", n, 1);
        bl2 = bl;
        if (n <= 10) {
            bl2 = true;
        }
        Preconditions.checkArgument(bl2, "Thread priority (%s) must be <= %s", n, 10);
        this.priority = n;
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory threadFactory2) {
        this.backingThreadFactory = Preconditions.checkNotNull(threadFactory2);
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = Preconditions.checkNotNull(uncaughtExceptionHandler);
        return this;
    }

}

