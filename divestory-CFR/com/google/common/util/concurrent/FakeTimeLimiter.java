/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.TimeLimiter;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public final class FakeTimeLimiter
implements TimeLimiter {
    @Override
    public <T> T callUninterruptiblyWithTimeout(Callable<T> callable, long l, TimeUnit timeUnit) throws ExecutionException {
        return this.callWithTimeout(callable, l, timeUnit);
    }

    @Override
    public <T> T callWithTimeout(Callable<T> callable, long l, TimeUnit timeUnit) throws ExecutionException {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeUnit);
        try {
            callable = callable.call();
        }
        catch (Throwable throwable) {
            throw new ExecutionException(throwable);
        }
        catch (Error error) {
            throw new ExecutionError(error);
        }
        catch (Exception exception) {
            throw new ExecutionException(exception);
        }
        catch (RuntimeException runtimeException) {
            throw new UncheckedExecutionException(runtimeException);
        }
        return (T)callable;
    }

    @Override
    public <T> T newProxy(T t, Class<T> class_, long l, TimeUnit timeUnit) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(class_);
        Preconditions.checkNotNull(timeUnit);
        return t;
    }

    @Override
    public void runUninterruptiblyWithTimeout(Runnable runnable2, long l, TimeUnit timeUnit) {
        this.runWithTimeout(runnable2, l, timeUnit);
    }

    @Override
    public void runWithTimeout(Runnable runnable2, long l, TimeUnit timeUnit) {
        Preconditions.checkNotNull(runnable2);
        Preconditions.checkNotNull(timeUnit);
        try {
            runnable2.run();
            return;
        }
        catch (Throwable throwable) {
            throw new UncheckedExecutionException(throwable);
        }
        catch (Error error) {
            throw new ExecutionError(error);
        }
        catch (RuntimeException runtimeException) {
            throw new UncheckedExecutionException(runtimeException);
        }
    }
}

