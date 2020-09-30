/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.arch.core.executor;

import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;
import java.util.concurrent.Executor;

public class ArchTaskExecutor
extends TaskExecutor {
    private static final Executor sIOThreadExecutor;
    private static volatile ArchTaskExecutor sInstance;
    private static final Executor sMainThreadExecutor;
    private TaskExecutor mDefaultTaskExecutor;
    private TaskExecutor mDelegate;

    static {
        sMainThreadExecutor = new Executor(){

            @Override
            public void execute(Runnable runnable2) {
                ArchTaskExecutor.getInstance().postToMainThread(runnable2);
            }
        };
        sIOThreadExecutor = new Executor(){

            @Override
            public void execute(Runnable runnable2) {
                ArchTaskExecutor.getInstance().executeOnDiskIO(runnable2);
            }
        };
    }

    private ArchTaskExecutor() {
        DefaultTaskExecutor defaultTaskExecutor = new DefaultTaskExecutor();
        this.mDefaultTaskExecutor = defaultTaskExecutor;
        this.mDelegate = defaultTaskExecutor;
    }

    public static Executor getIOThreadExecutor() {
        return sIOThreadExecutor;
    }

    public static ArchTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (ArchTaskExecutor.class) {
            ArchTaskExecutor archTaskExecutor;
            if (sInstance != null) return sInstance;
            sInstance = archTaskExecutor = new ArchTaskExecutor();
            return sInstance;
        }
    }

    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    @Override
    public void executeOnDiskIO(Runnable runnable2) {
        this.mDelegate.executeOnDiskIO(runnable2);
    }

    @Override
    public boolean isMainThread() {
        return this.mDelegate.isMainThread();
    }

    @Override
    public void postToMainThread(Runnable runnable2) {
        this.mDelegate.postToMainThread(runnable2);
    }

    public void setDelegate(TaskExecutor taskExecutor) {
        TaskExecutor taskExecutor2 = taskExecutor;
        if (taskExecutor == null) {
            taskExecutor2 = this.mDefaultTaskExecutor;
        }
        this.mDelegate = taskExecutor2;
    }

}

