/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.TimeLimiter;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.UncheckedTimeoutException;
import com.google.common.util.concurrent.Uninterruptibles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class SimpleTimeLimiter
implements TimeLimiter {
    private final ExecutorService executor;

    private SimpleTimeLimiter(ExecutorService executorService) {
        this.executor = Preconditions.checkNotNull(executorService);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private <T> T callWithTimeout(Callable<T> var1_1, long var2_3, TimeUnit var4_4, boolean var5_7) throws Exception {
        Preconditions.checkNotNull(var1_1);
        Preconditions.checkNotNull(var4_4 /* !! */ );
        SimpleTimeLimiter.checkPositiveTimeout(var2_3);
        var1_1 = this.executor.submit(var1_1);
        if (var5_9 == false) ** GOTO lbl16
        try {
            try {
                var4_5 = var1_1.get(var2_3, var4_4 /* !! */ );
            }
            catch (InterruptedException var4_6) {
                var1_1.cancel(true);
                throw var4_6;
            }
            return var4_5;
lbl16: // 1 sources:
            var4_7 = Uninterruptibles.getUninterruptibly(var1_1, var2_3, var4_4 /* !! */ );
        }
        catch (TimeoutException var4_8) {
            var1_1.cancel(true);
            throw new UncheckedTimeoutException(var4_8);
        }
        catch (ExecutionException var1_2) {
            throw SimpleTimeLimiter.throwCause(var1_2, true);
        }
        return var4_7;
    }

    private static void checkPositiveTimeout(long l) {
        boolean bl = l > 0L;
        Preconditions.checkArgument(bl, "timeout must be positive: %s", l);
    }

    public static SimpleTimeLimiter create(ExecutorService executorService) {
        return new SimpleTimeLimiter(executorService);
    }

    private static boolean declaresInterruptedEx(Method arrclass) {
        arrclass = arrclass.getExceptionTypes();
        int n = arrclass.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrclass[n2] == InterruptedException.class) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private static Set<Method> findInterruptibleMethods(Class<?> arrmethod) {
        HashSet<Method> hashSet = Sets.newHashSet();
        arrmethod = arrmethod.getMethods();
        int n = arrmethod.length;
        int n2 = 0;
        while (n2 < n) {
            Method method = arrmethod[n2];
            if (SimpleTimeLimiter.declaresInterruptedEx(method)) {
                hashSet.add(method);
            }
            ++n2;
        }
        return hashSet;
    }

    private static <T> T newProxy(Class<T> class_, InvocationHandler invocationHandler) {
        return class_.cast(Proxy.newProxyInstance(class_.getClassLoader(), new Class[]{class_}, invocationHandler));
    }

    private static Exception throwCause(Exception exception, boolean bl) throws Exception {
        Throwable throwable = exception.getCause();
        if (throwable == null) throw exception;
        if (bl) {
            throwable.setStackTrace(ObjectArrays.concat(throwable.getStackTrace(), exception.getStackTrace(), StackTraceElement.class));
        }
        if (throwable instanceof Exception) throw (Exception)throwable;
        if (!(throwable instanceof Error)) throw exception;
        throw (Error)throwable;
    }

    private void wrapAndThrowExecutionExceptionOrError(Throwable throwable) throws ExecutionException {
        if (throwable instanceof Error) throw new ExecutionError((Error)throwable);
        if (!(throwable instanceof RuntimeException)) throw new ExecutionException(throwable);
        throw new UncheckedExecutionException(throwable);
    }

    private void wrapAndThrowRuntimeExecutionExceptionOrError(Throwable throwable) {
        if (!(throwable instanceof Error)) throw new UncheckedExecutionException(throwable);
        throw new ExecutionError((Error)throwable);
    }

    @Override
    public <T> T callUninterruptiblyWithTimeout(Callable<T> object, long l, TimeUnit timeUnit) throws TimeoutException, ExecutionException {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(timeUnit);
        SimpleTimeLimiter.checkPositiveTimeout(l);
        object = this.executor.submit(object);
        try {
            timeUnit = Uninterruptibles.getUninterruptibly(object, l, timeUnit);
        }
        catch (ExecutionException executionException) {
            this.wrapAndThrowExecutionExceptionOrError(executionException.getCause());
            throw new AssertionError();
        }
        catch (TimeoutException timeoutException) {
            object.cancel(true);
            throw timeoutException;
        }
        return (T)((Object)timeUnit);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <T> T callWithTimeout(Callable<T> callable, long l, TimeUnit timeUnit) throws TimeoutException, InterruptedException, ExecutionException {
        void var1_5;
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeUnit);
        SimpleTimeLimiter.checkPositiveTimeout(l);
        Future<T> future = this.executor.submit(callable);
        try {
            callable = future.get(l, timeUnit);
        }
        catch (ExecutionException executionException) {
            this.wrapAndThrowExecutionExceptionOrError(executionException.getCause());
            throw new AssertionError();
        }
        catch (TimeoutException timeoutException) {
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        return (T)callable;
        future.cancel(true);
        throw var1_5;
    }

    @Override
    public <T> T newProxy(final T t, Class<T> class_, final long l, final TimeUnit timeUnit) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(class_);
        Preconditions.checkNotNull(timeUnit);
        SimpleTimeLimiter.checkPositiveTimeout(l);
        Preconditions.checkArgument(class_.isInterface(), "interfaceType must be an interface type");
        return SimpleTimeLimiter.newProxy(class_, new InvocationHandler(SimpleTimeLimiter.findInterruptibleMethods(class_)){
            final /* synthetic */ Set val$interruptibleMethods;
            {
                this.val$interruptibleMethods = set;
            }

            @Override
            public Object invoke(Object object, final Method method, final Object[] arrobject) throws Throwable {
                object = new Callable<Object>(){

                    @Override
                    public Object call() throws Exception {
                        try {
                            return method.invoke(t, arrobject);
                        }
                        catch (InvocationTargetException invocationTargetException) {
                            throw SimpleTimeLimiter.throwCause(invocationTargetException, false);
                        }
                    }
                };
                return SimpleTimeLimiter.this.callWithTimeout((Callable)object, l, timeUnit, this.val$interruptibleMethods.contains(method));
            }

        });
    }

    @Override
    public void runUninterruptiblyWithTimeout(Runnable object, long l, TimeUnit timeUnit) throws TimeoutException {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(timeUnit);
        SimpleTimeLimiter.checkPositiveTimeout(l);
        object = this.executor.submit((Runnable)object);
        try {
            Uninterruptibles.getUninterruptibly(object, l, timeUnit);
            return;
        }
        catch (ExecutionException executionException) {
            this.wrapAndThrowRuntimeExecutionExceptionOrError(executionException.getCause());
            throw new AssertionError();
        }
        catch (TimeoutException timeoutException) {
            object.cancel(true);
            throw timeoutException;
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void runWithTimeout(Runnable runnable2, long l, TimeUnit timeUnit) throws TimeoutException, InterruptedException {
        void var1_5;
        Preconditions.checkNotNull(runnable2);
        Preconditions.checkNotNull(timeUnit);
        SimpleTimeLimiter.checkPositiveTimeout(l);
        Future<?> future = this.executor.submit(runnable2);
        try {
            future.get(l, timeUnit);
            return;
        }
        catch (ExecutionException executionException) {
            this.wrapAndThrowRuntimeExecutionExceptionOrError(executionException.getCause());
            throw new AssertionError();
        }
        catch (TimeoutException timeoutException) {
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        future.cancel(true);
        throw var1_5;
    }

}

