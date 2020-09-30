/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

final class ListenerCallQueue<L> {
    private static final Logger logger = Logger.getLogger(ListenerCallQueue.class.getName());
    private final List<PerListenerQueue<L>> listeners = Collections.synchronizedList(new ArrayList());

    ListenerCallQueue() {
    }

    private void enqueueHelper(Event<L> event, Object object) {
        Preconditions.checkNotNull(event, "event");
        Preconditions.checkNotNull(object, "label");
        List<PerListenerQueue<L>> list = this.listeners;
        synchronized (list) {
            Iterator<PerListenerQueue<L>> iterator2 = this.listeners.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().add(event, object);
            }
            return;
        }
    }

    public void addListener(L l, Executor executor) {
        Preconditions.checkNotNull(l, "listener");
        Preconditions.checkNotNull(executor, "executor");
        this.listeners.add(new PerListenerQueue<L>(l, executor));
    }

    public void dispatch() {
        int n = 0;
        while (n < this.listeners.size()) {
            this.listeners.get(n).dispatch();
            ++n;
        }
    }

    public void enqueue(Event<L> event) {
        this.enqueueHelper(event, event);
    }

    public void enqueue(Event<L> event, String string2) {
        this.enqueueHelper(event, string2);
    }

    static interface Event<L> {
        public void call(L var1);
    }

    private static final class PerListenerQueue<L>
    implements Runnable {
        final Executor executor;
        boolean isThreadScheduled;
        final Queue<Object> labelQueue = Queues.newArrayDeque();
        final L listener;
        final Queue<Event<L>> waitQueue = Queues.newArrayDeque();

        PerListenerQueue(L l, Executor executor) {
            this.listener = Preconditions.checkNotNull(l);
            this.executor = Preconditions.checkNotNull(executor);
        }

        void add(Event<L> event, Object object) {
            synchronized (this) {
                this.waitQueue.add(event);
                this.labelQueue.add(object);
                return;
            }
        }

        /*
         * Enabled unnecessary exception pruning
         */
        void dispatch() {
            boolean bl;
            synchronized (this) {
                boolean bl2 = this.isThreadScheduled;
                bl = true;
                if (!bl2) {
                    this.isThreadScheduled = true;
                } else {
                    bl = false;
                }
            }
            if (!bl) return;
            try {
                this.executor.execute(this);
                return;
            }
            catch (RuntimeException runtimeException) {
                synchronized (this) {
                    this.isThreadScheduled = false;
                }
                Logger logger = logger;
                Level level = Level.SEVERE;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception while running callbacks for ");
                stringBuilder.append(this.listener);
                stringBuilder.append(" on ");
                stringBuilder.append(this.executor);
                logger.log(level, stringBuilder.toString(), runtimeException);
                throw runtimeException;
            }
        }

        /*
         * Loose catch block
         * WARNING - void declaration
         * Enabled unnecessary exception pruning
         * Converted monitor instructions to comments
         */
        @Override
        public void run() {
            void var3_9;
            boolean bl;
            block16 : {
                Object object2;
                Object object;
                do {
                    bl = true;
                    Preconditions.checkState(this.isThreadScheduled);
                    object = this.waitQueue.poll();
                    object2 = this.labelQueue.poll();
                    if (object == null) {
                        this.isThreadScheduled = false;
                        // MONITOREXIT : this
                        return;
                    }
                    // MONITOREXIT : this
                    break;
                } while (true);
                catch (Throwable throwable) {
                    bl = true;
                    try {
                        void var3_5;
                        throw var3_5;
                    }
                    catch (Throwable throwable2) {
                        break block16;
                    }
                }
                {
                    try {
                        object.call(this.listener);
                    }
                    catch (RuntimeException runtimeException) {
                        object = logger;
                        Level level = Level.SEVERE;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Exception while executing callback: ");
                        stringBuilder.append(this.listener);
                        stringBuilder.append(" ");
                        stringBuilder.append(object2);
                        ((Logger)object).log(level, stringBuilder.toString(), runtimeException);
                    }
                    continue;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            if (!bl) throw var3_9;
            // MONITORENTER : this
            this.isThreadScheduled = false;
            // MONITOREXIT : this
            throw var3_9;
        }
    }

}

