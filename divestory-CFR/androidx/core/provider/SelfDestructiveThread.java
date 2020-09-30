/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 */
package androidx.core.provider;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SelfDestructiveThread {
    private static final int MSG_DESTRUCTION = 0;
    private static final int MSG_INVOKE_RUNNABLE = 1;
    private Handler.Callback mCallback = new Handler.Callback(){

        public boolean handleMessage(Message message) {
            int n = message.what;
            if (n == 0) {
                SelfDestructiveThread.this.onDestruction();
                return true;
            }
            if (n != 1) {
                return true;
            }
            SelfDestructiveThread.this.onInvokeRunnable((Runnable)message.obj);
            return true;
        }
    };
    private final int mDestructAfterMillisec;
    private int mGeneration;
    private Handler mHandler;
    private final Object mLock = new Object();
    private final int mPriority;
    private HandlerThread mThread;
    private final String mThreadName;

    public SelfDestructiveThread(String string2, int n, int n2) {
        this.mThreadName = string2;
        this.mPriority = n;
        this.mDestructAfterMillisec = n2;
        this.mGeneration = 0;
    }

    private void post(Runnable runnable2) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mThread == null) {
                HandlerThread handlerThread;
                this.mThread = handlerThread = new HandlerThread(this.mThreadName, this.mPriority);
                handlerThread.start();
                handlerThread = new Handler(this.mThread.getLooper(), this.mCallback);
                this.mHandler = handlerThread;
                ++this.mGeneration;
            }
            this.mHandler.removeMessages(0);
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, (Object)runnable2));
            return;
        }
    }

    public int getGeneration() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mGeneration;
        }
    }

    public boolean isRunning() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mThread == null) return false;
            return true;
        }
    }

    void onDestruction() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mHandler.hasMessages(1)) {
                return;
            }
            this.mThread.quit();
            this.mThread = null;
            this.mHandler = null;
            return;
        }
    }

    void onInvokeRunnable(Runnable object) {
        object.run();
        object = this.mLock;
        synchronized (object) {
            this.mHandler.removeMessages(0);
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0), (long)this.mDestructAfterMillisec);
            return;
        }
    }

    public <T> void postAndReply(final Callable<T> callable, ReplyCallback<T> replyCallback) {
        this.post(new Runnable(new Handler(), replyCallback){
            final /* synthetic */ Handler val$callingHandler;
            final /* synthetic */ ReplyCallback val$reply;
            {
                this.val$callingHandler = handler;
                this.val$reply = replyCallback;
            }

            @Override
            public void run() {
                Object v;
                try {
                    v = callable.call();
                }
                catch (Exception exception) {
                    v = null;
                }
                this.val$callingHandler.post(new Runnable(){

                    @Override
                    public void run() {
                        val$reply.onReply(v);
                    }
                });
            }

        });
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public <T> T postAndWait(Callable<T> var1_1, int var2_4) throws InterruptedException {
        block8 : {
            var3_5 = new ReentrantLock();
            var4_6 = var3_5.newCondition();
            var5_7 = new AtomicReference<V>();
            var6_8 = new AtomicBoolean(true);
            this.post(new Runnable((Callable)var1_1, var3_5, var6_8, var4_6){
                final /* synthetic */ Callable val$callable;
                final /* synthetic */ Condition val$cond;
                final /* synthetic */ ReentrantLock val$lock;
                final /* synthetic */ AtomicBoolean val$running;
                {
                    this.val$callable = callable;
                    this.val$lock = reentrantLock;
                    this.val$running = atomicBoolean;
                    this.val$cond = condition;
                }

                /*
                 * Unable to fully structure code
                 * Enabled unnecessary exception pruning
                 */
                @Override
                public void run() {
                    try {
                        var5_7.set(this.val$callable.call());
lbl3: // 2 sources:
                        do {
                            this.val$lock.lock();
                            break;
                        } while (true);
                    }
                    catch (Exception var1_1) {
                        ** continue;
                    }
                    {
                        try {
                            this.val$running.set(false);
                            this.val$cond.signal();
                            return;
                        }
                        finally {
                            this.val$lock.unlock();
                        }
                    }
                }
            });
            var3_5.lock();
            if (var6_8.get()) break block8;
            var1_1 = var5_7.get();
            var3_5.unlock();
            return (T)var1_1;
        }
        try {
            var7_9 = TimeUnit.MILLISECONDS.toNanos(var2_4);
            try {
                block6 : do {
                    var7_9 = var9_10 = var4_6.awaitNanos(var7_9);
lbl18: // 2 sources:
                    do {
                        if (var6_8.get()) continue block6;
                        var1_1 = var5_7.get();
                        var3_5.unlock();
                        return (T)var1_1;
                        if (var7_9 > 0L) continue block6;
                        break;
                    } while (true);
                    break;
                } while (true);
            }
            catch (InterruptedException var1_3) {
                ** continue;
            }
            {
                var1_1 = new InterruptedException("timeout");
                throw var1_1;
            }
        }
        catch (Throwable var1_2) {
            var3_5.unlock();
            throw var1_2;
        }
    }

    public static interface ReplyCallback<T> {
        public void onReply(T var1);
    }

}

