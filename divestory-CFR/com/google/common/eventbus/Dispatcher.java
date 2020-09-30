/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.eventbus;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import com.google.common.eventbus.Subscriber;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

abstract class Dispatcher {
    Dispatcher() {
    }

    static Dispatcher immediate() {
        return ImmediateDispatcher.INSTANCE;
    }

    static Dispatcher legacyAsync() {
        return new LegacyAsyncDispatcher();
    }

    static Dispatcher perThreadDispatchQueue() {
        return new PerThreadQueuedDispatcher();
    }

    abstract void dispatch(Object var1, Iterator<Subscriber> var2);

    private static final class ImmediateDispatcher
    extends Dispatcher {
        private static final ImmediateDispatcher INSTANCE = new ImmediateDispatcher();

        private ImmediateDispatcher() {
        }

        @Override
        void dispatch(Object object, Iterator<Subscriber> iterator2) {
            Preconditions.checkNotNull(object);
            while (iterator2.hasNext()) {
                iterator2.next().dispatchEvent(object);
            }
        }
    }

    private static final class LegacyAsyncDispatcher
    extends Dispatcher {
        private final ConcurrentLinkedQueue<EventWithSubscriber> queue = Queues.newConcurrentLinkedQueue();

        private LegacyAsyncDispatcher() {
        }

        @Override
        void dispatch(Object object, Iterator<Subscriber> iterator2) {
            Preconditions.checkNotNull(object);
            do {
                if (!iterator2.hasNext()) {
                    while ((object = this.queue.poll()) != null) {
                        ((EventWithSubscriber)object).subscriber.dispatchEvent(((EventWithSubscriber)object).event);
                    }
                    return;
                }
                this.queue.add(new EventWithSubscriber(object, iterator2.next()));
            } while (true);
        }

        private static final class EventWithSubscriber {
            private final Object event;
            private final Subscriber subscriber;

            private EventWithSubscriber(Object object, Subscriber subscriber) {
                this.event = object;
                this.subscriber = subscriber;
            }
        }

    }

    private static final class PerThreadQueuedDispatcher
    extends Dispatcher {
        private final ThreadLocal<Boolean> dispatching = new ThreadLocal<Boolean>(){

            @Override
            protected Boolean initialValue() {
                return false;
            }
        };
        private final ThreadLocal<Queue<Event>> queue = new ThreadLocal<Queue<Event>>(){

            @Override
            protected Queue<Event> initialValue() {
                return Queues.newArrayDeque();
            }
        };

        private PerThreadQueuedDispatcher() {
        }

        @Override
        void dispatch(Object object, Iterator<Subscriber> iterator2) {
            Preconditions.checkNotNull(object);
            Preconditions.checkNotNull(iterator2);
            Queue<Event> queue = this.queue.get();
            queue.offer(new Event(object, iterator2));
            if (this.dispatching.get() != false) return;
            this.dispatching.set(true);
            try {
                block3 : do {
                    if ((object = queue.poll()) == null) return;
                    do {
                        if (!((Event)object).subscribers.hasNext()) continue block3;
                        ((Subscriber)((Event)object).subscribers.next()).dispatchEvent(((Event)object).event);
                    } while (true);
                    break;
                } while (true);
            }
            finally {
                this.dispatching.remove();
                this.queue.remove();
            }
        }

        private static final class Event {
            private final Object event;
            private final Iterator<Subscriber> subscribers;

            private Event(Object object, Iterator<Subscriber> iterator2) {
                this.event = object;
                this.subscribers = iterator2;
            }
        }

    }

}

