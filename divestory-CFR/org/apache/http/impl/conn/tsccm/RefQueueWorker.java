/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn.tsccm;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import org.apache.http.impl.conn.tsccm.RefQueueHandler;

@Deprecated
public class RefQueueWorker
implements Runnable {
    protected final RefQueueHandler refHandler;
    protected final ReferenceQueue<?> refQueue;
    protected volatile Thread workerThread;

    public RefQueueWorker(ReferenceQueue<?> referenceQueue, RefQueueHandler refQueueHandler) {
        if (referenceQueue == null) throw new IllegalArgumentException("Queue must not be null.");
        if (refQueueHandler == null) throw new IllegalArgumentException("Handler must not be null.");
        this.refQueue = referenceQueue;
        this.refHandler = refQueueHandler;
    }

    @Override
    public void run() {
        if (this.workerThread == null) {
            this.workerThread = Thread.currentThread();
        }
        while (this.workerThread == Thread.currentThread()) {
            try {
                Reference<?> reference = this.refQueue.remove();
                this.refHandler.handleReference(reference);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    public void shutdown() {
        Thread thread2 = this.workerThread;
        if (thread2 == null) return;
        this.workerThread = null;
        thread2.interrupt();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RefQueueWorker::");
        stringBuilder.append(this.workerThread);
        return stringBuilder.toString();
    }
}

