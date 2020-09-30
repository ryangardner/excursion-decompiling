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
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractExecutionThreadService
implements Service {
    private static final Logger logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
    private final Service delegate = new AbstractService(){

        @Override
        protected final void doStart() {
            MoreExecutors.renamingDecorator(AbstractExecutionThreadService.this.executor(), new Supplier<String>(){

                @Override
                public String get() {
                    return AbstractExecutionThreadService.this.serviceName();
                }
            }).execute(new Runnable(){

                /*
                 * Unable to fully structure code
                 * Enabled unnecessary exception pruning
                 */
                @Override
                public void run() {
                    AbstractExecutionThreadService.this.startUp();
                    this.notifyStarted();
                    var1_1 = this.isRunning();
                    if (!var1_1) ** GOTO lbl18
                    try {
                        AbstractExecutionThreadService.this.run();
                        ** GOTO lbl18
                    }
                    catch (Throwable var2_2) {
                        try {
                            try {
                                AbstractExecutionThreadService.this.shutDown();
                            }
                            catch (Exception var3_3) {
                                AbstractExecutionThreadService.access$000().log(Level.WARNING, "Error while attempting to shut down the service after failure.", var3_3);
                            }
                            this.notifyFailed(var2_2);
                            return;
lbl18: // 2 sources:
                            AbstractExecutionThreadService.this.shutDown();
                            this.notifyStopped();
                            return;
                        }
                        catch (Throwable var3_4) {
                            this.notifyFailed(var3_4);
                        }
                    }
                }
            });
        }

        @Override
        protected void doStop() {
            AbstractExecutionThreadService.this.triggerShutdown();
        }

        @Override
        public String toString() {
            return AbstractExecutionThreadService.this.toString();
        }

    };

    protected AbstractExecutionThreadService() {
    }

    static /* synthetic */ Logger access$000() {
        return logger;
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
                MoreExecutors.newThread(AbstractExecutionThreadService.this.serviceName(), runnable2).start();
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

    protected abstract void run() throws Exception;

    protected String serviceName() {
        return this.getClass().getSimpleName();
    }

    protected void shutDown() throws Exception {
    }

    @Override
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    protected void startUp() throws Exception {
    }

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

    protected void triggerShutdown() {
    }

}

