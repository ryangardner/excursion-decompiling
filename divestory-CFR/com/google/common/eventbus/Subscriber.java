/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.eventbus;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class Subscriber {
    private EventBus bus;
    private final Executor executor;
    private final Method method;
    final Object target;

    private Subscriber(EventBus eventBus, Object object, Method method) {
        this.bus = eventBus;
        this.target = Preconditions.checkNotNull(object);
        this.method = method;
        method.setAccessible(true);
        this.executor = eventBus.executor();
    }

    private SubscriberExceptionContext context(Object object) {
        return new SubscriberExceptionContext(this.bus, object, this.target, this.method);
    }

    static Subscriber create(EventBus object, Object object2, Method method) {
        if (!Subscriber.isDeclaredThreadSafe(method)) return new SynchronizedSubscriber((EventBus)object, object2, method);
        return new Subscriber((EventBus)object, object2, method);
    }

    private static boolean isDeclaredThreadSafe(Method method) {
        if (method.getAnnotation(AllowConcurrentEvents.class) == null) return false;
        return true;
    }

    final void dispatchEvent(final Object object) {
        this.executor.execute(new Runnable(){

            @Override
            public void run() {
                try {
                    Subscriber.this.invokeSubscriberMethod(object);
                    return;
                }
                catch (InvocationTargetException invocationTargetException) {
                    Subscriber.this.bus.handleSubscriberException(invocationTargetException.getCause(), Subscriber.this.context(object));
                }
            }
        });
    }

    public final boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof Subscriber;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (Subscriber)object;
        bl3 = bl;
        if (this.target != ((Subscriber)object).target) return bl3;
        bl3 = bl;
        if (!this.method.equals(((Subscriber)object).method)) return bl3;
        return true;
    }

    public final int hashCode() {
        return (this.method.hashCode() + 31) * 31 + System.identityHashCode(this.target);
    }

    void invokeSubscriberMethod(Object object) throws InvocationTargetException {
        try {
            this.method.invoke(this.target, Preconditions.checkNotNull(object));
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            if (!(invocationTargetException.getCause() instanceof Error)) throw invocationTargetException;
            throw (Error)invocationTargetException.getCause();
        }
        catch (IllegalAccessException illegalAccessException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Method became inaccessible: ");
            stringBuilder.append(object);
            throw new Error(stringBuilder.toString(), illegalAccessException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Method rejected target/argument: ");
            stringBuilder.append(object);
            throw new Error(stringBuilder.toString(), illegalArgumentException);
        }
    }

    static final class SynchronizedSubscriber
    extends Subscriber {
        private SynchronizedSubscriber(EventBus eventBus, Object object, Method method) {
            super(eventBus, object, method);
        }

        @Override
        void invokeSubscriberMethod(Object object) throws InvocationTargetException {
            synchronized (this) {
                super.invokeSubscriberMethod(object);
                return;
            }
        }
    }

}

