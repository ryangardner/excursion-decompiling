/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.eventbus;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Dispatcher;
import com.google.common.eventbus.Subscriber;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.google.common.eventbus.SubscriberRegistry;
import com.google.common.util.concurrent.MoreExecutors;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventBus {
    private static final Logger logger = Logger.getLogger(EventBus.class.getName());
    private final Dispatcher dispatcher;
    private final SubscriberExceptionHandler exceptionHandler;
    private final Executor executor;
    private final String identifier;
    private final SubscriberRegistry subscribers = new SubscriberRegistry(this);

    public EventBus() {
        this("default");
    }

    public EventBus(SubscriberExceptionHandler subscriberExceptionHandler) {
        this("default", MoreExecutors.directExecutor(), Dispatcher.perThreadDispatchQueue(), subscriberExceptionHandler);
    }

    public EventBus(String string2) {
        this(string2, MoreExecutors.directExecutor(), Dispatcher.perThreadDispatchQueue(), LoggingHandler.INSTANCE);
    }

    EventBus(String string2, Executor executor, Dispatcher dispatcher, SubscriberExceptionHandler subscriberExceptionHandler) {
        this.identifier = Preconditions.checkNotNull(string2);
        this.executor = Preconditions.checkNotNull(executor);
        this.dispatcher = Preconditions.checkNotNull(dispatcher);
        this.exceptionHandler = Preconditions.checkNotNull(subscriberExceptionHandler);
    }

    final Executor executor() {
        return this.executor;
    }

    void handleSubscriberException(Throwable throwable, SubscriberExceptionContext subscriberExceptionContext) {
        Preconditions.checkNotNull(throwable);
        Preconditions.checkNotNull(subscriberExceptionContext);
        try {
            this.exceptionHandler.handleException(throwable, subscriberExceptionContext);
            return;
        }
        catch (Throwable throwable2) {
            logger.log(Level.SEVERE, String.format(Locale.ROOT, "Exception %s thrown while handling exception: %s", throwable2, throwable), throwable2);
        }
    }

    public final String identifier() {
        return this.identifier;
    }

    public void post(Object object) {
        Iterator<Subscriber> iterator2 = this.subscribers.getSubscribers(object);
        if (iterator2.hasNext()) {
            this.dispatcher.dispatch(object, iterator2);
            return;
        }
        if (object instanceof DeadEvent) return;
        this.post(new DeadEvent(this, object));
    }

    public void register(Object object) {
        this.subscribers.register(object);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(this.identifier).toString();
    }

    public void unregister(Object object) {
        this.subscribers.unregister(object);
    }

    static final class LoggingHandler
    implements SubscriberExceptionHandler {
        static final LoggingHandler INSTANCE = new LoggingHandler();

        LoggingHandler() {
        }

        private static Logger logger(SubscriberExceptionContext subscriberExceptionContext) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(EventBus.class.getName());
            stringBuilder.append(".");
            stringBuilder.append(subscriberExceptionContext.getEventBus().identifier());
            return Logger.getLogger(stringBuilder.toString());
        }

        private static String message(SubscriberExceptionContext subscriberExceptionContext) {
            Method method = subscriberExceptionContext.getSubscriberMethod();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception thrown by subscriber method ");
            stringBuilder.append(method.getName());
            stringBuilder.append('(');
            stringBuilder.append(method.getParameterTypes()[0].getName());
            stringBuilder.append(')');
            stringBuilder.append(" on subscriber ");
            stringBuilder.append(subscriberExceptionContext.getSubscriber());
            stringBuilder.append(" when dispatching event: ");
            stringBuilder.append(subscriberExceptionContext.getEvent());
            return stringBuilder.toString();
        }

        @Override
        public void handleException(Throwable throwable, SubscriberExceptionContext subscriberExceptionContext) {
            Logger logger = LoggingHandler.logger(subscriberExceptionContext);
            if (!logger.isLoggable(Level.SEVERE)) return;
            logger.log(Level.SEVERE, LoggingHandler.message(subscriberExceptionContext), throwable);
        }
    }

}

