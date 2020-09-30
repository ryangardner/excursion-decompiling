/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 */
package androidx.arch.core.executor;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.arch.core.executor.TaskExecutor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultTaskExecutor
extends TaskExecutor {
    private final ExecutorService mDiskIO = Executors.newFixedThreadPool(4, new ThreadFactory(){
        private static final String THREAD_NAME_STEM = "arch_disk_io_%d";
        private final AtomicInteger mThreadId = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable2) {
            runnable2 = new Thread(runnable2);
            ((Thread)runnable2).setName(String.format(THREAD_NAME_STEM, this.mThreadId.getAndIncrement()));
            return runnable2;
        }
    });
    private final Object mLock = new Object();
    private volatile Handler mMainHandler;

    private static Handler createAsync(Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Handler.createAsync((Looper)looper);
        }
        if (Build.VERSION.SDK_INT < 16) return new Handler(looper);
        try {
            return (Handler)Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(new Object[]{looper, null, true});
        }
        catch (InvocationTargetException invocationTargetException) {
            return new Handler(looper);
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException reflectiveOperationException) {
            return new Handler(looper);
        }
    }

    @Override
    public void executeOnDiskIO(Runnable runnable2) {
        this.mDiskIO.execute(runnable2);
    }

    @Override
    public boolean isMainThread() {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) return false;
        return true;
    }

    @Override
    public void postToMainThread(Runnable runnable2) {
        if (this.mMainHandler == null) {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mMainHandler == null) {
                    this.mMainHandler = DefaultTaskExecutor.createAsync(Looper.getMainLooper());
                }
            }
        }
        this.mMainHandler.post(runnable2);
    }

}

