/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import java.util.concurrent.Executor;

final class DirectExecutor
extends Enum<DirectExecutor>
implements Executor {
    private static final /* synthetic */ DirectExecutor[] $VALUES;
    public static final /* enum */ DirectExecutor INSTANCE;

    static {
        DirectExecutor directExecutor;
        INSTANCE = directExecutor = new DirectExecutor();
        $VALUES = new DirectExecutor[]{directExecutor};
    }

    public static DirectExecutor valueOf(String string2) {
        return Enum.valueOf(DirectExecutor.class, string2);
    }

    public static DirectExecutor[] values() {
        return (DirectExecutor[])$VALUES.clone();
    }

    @Override
    public void execute(Runnable runnable2) {
        runnable2.run();
    }

    public String toString() {
        return "MoreExecutors.directExecutor()";
    }
}

