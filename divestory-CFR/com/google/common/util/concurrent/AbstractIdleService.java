/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Supplier;
import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractIdleService
implements Service {
    private final Service delegate = new DelegateService();
    private final Supplier<String> threadNameSupplier = new ThreadNameSupplier();

    protected AbstractIdleService() {
    }

    @Override
    public final void addListener(Service.Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    @Override
    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    @Override
    public final void awaitRunning(long l, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitRunning(l, timeUnit);
    }

    @Override
    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    @Override
    public final void awaitTerminated(long l, TimeUnit timeUnit) throws TimeoutException {
        this.delegate.awaitTerminated(l, timeUnit);
    }

    protected Executor executor() {
        return new Executor(){

            @Override
            public void execute(Runnable runnable2) {
                MoreExecutors.newThread((String)AbstractIdleService.this.threadNameSupplier.get(), runnable2).start();
            }
        };
    }

    @Override
    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    @Override
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    protected String serviceName() {
        return this.getClass().getSimpleName();
    }

    protected abstract void shutDown() throws Exception;

    @Override
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    protected abstract void startUp() throws Exception;

    @Override
    public final Service.State state() {
        return this.delegate.state();
    }

    @Override
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.serviceName());
        stringBuilder.append(" [");
        stringBuilder.append((Object)((Object)this.state()));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private final class DelegateService
    extends AbstractService {
        private DelegateService() {
        }

        @Override
        protected final void doStart() {
            MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), (Supplier<String>)AbstractIdleService.this.threadNameSupplier).execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        AbstractIdleService.this.startUp();
                        DelegateService.this.notifyStarted();
                        return;
                    }
                    catch (Throwable throwable) {
                        DelegateService.this.notifyFailed(throwable);
                    }
                }
            });
        }

        @Override
        protected final void doStop() {
            MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), (Supplier<String>)AbstractIdleService.this.threadNameSupplier).execute(new Runnable(){

                @Override
                public void run() {
                    try {
                        AbstractIdleService.this.shutDown();
                        DelegateService.this.notifyStopped();
                        return;
                    }
                    catch (Throwable throwable) {
                        DelegateService.this.notifyFailed(throwable);
                    }
                }
            });
        }

        @Override
        public String toString() {
            return AbstractIdleService.this.toString();
        }

    }

    private final class ThreadNameSupplier
    implements Supplier<String> {
        private ThreadNameSupplier() {
        }

        @Override
        public String get() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(AbstractIdleService.this.serviceName());
            stringBuilder.append(" ");
            stringBuilder.append((Object)AbstractIdleService.this.state());
            return stringBuilder.toString();
        }
    }

}

