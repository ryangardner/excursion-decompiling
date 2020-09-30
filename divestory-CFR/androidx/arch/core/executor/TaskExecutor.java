/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.arch.core.executor;

public abstract class TaskExecutor {
    public abstract void executeOnDiskIO(Runnable var1);

    public void executeOnMainThread(Runnable runnable2) {
        if (this.isMainThread()) {
            runnable2.run();
            return;
        }
        this.postToMainThread(runnable2);
    }

    public abstract boolean isMainThread();

    public abstract void postToMainThread(Runnable var1);
}

