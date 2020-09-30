/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class FuturesGetChecked {
    private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>(){

        @Override
        public Boolean apply(Constructor<?> constructor) {
            return Arrays.asList(constructor.getParameterTypes()).contains(String.class);
        }
    }).reverse();

    private FuturesGetChecked() {
    }

    private static GetCheckedTypeValidator bestGetCheckedTypeValidator() {
        return GetCheckedTypeValidatorHolder.BEST_VALIDATOR;
    }

    static void checkExceptionClassValidity(Class<? extends Exception> class_) {
        Preconditions.checkArgument(FuturesGetChecked.isCheckedException(class_), "Futures.getChecked exception type (%s) must not be a RuntimeException", class_);
        Preconditions.checkArgument(FuturesGetChecked.hasConstructorUsableByGetChecked(class_), "Futures.getChecked exception type (%s) must be an accessible class with an accessible constructor whose parameters (if any) must be of type String and/or Throwable", class_);
    }

    static GetCheckedTypeValidator classValueValidator() {
        return GetCheckedTypeValidatorHolder.ClassValueValidator.INSTANCE;
    }

    static <V, X extends Exception> V getChecked(GetCheckedTypeValidator getCheckedTypeValidator, Future<V> future, Class<X> class_) throws Exception {
        getCheckedTypeValidator.validateClass(class_);
        try {
            getCheckedTypeValidator = future.get();
        }
        catch (ExecutionException executionException) {
            FuturesGetChecked.wrapAndThrowExceptionOrError(executionException.getCause(), class_);
            throw new AssertionError();
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw FuturesGetChecked.newWithCause(class_, interruptedException);
        }
        return (V)getCheckedTypeValidator;
    }

    static <V, X extends Exception> V getChecked(Future<V> future, Class<X> class_) throws Exception {
        return FuturesGetChecked.getChecked(FuturesGetChecked.bestGetCheckedTypeValidator(), future, class_);
    }

    static <V, X extends Exception> V getChecked(Future<V> future, Class<X> class_, long l, TimeUnit timeUnit) throws Exception {
        FuturesGetChecked.bestGetCheckedTypeValidator().validateClass(class_);
        try {
            future = future.get(l, timeUnit);
        }
        catch (ExecutionException executionException) {
            FuturesGetChecked.wrapAndThrowExceptionOrError(executionException.getCause(), class_);
            throw new AssertionError();
        }
        catch (TimeoutException timeoutException) {
            throw FuturesGetChecked.newWithCause(class_, timeoutException);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw FuturesGetChecked.newWithCause(class_, interruptedException);
        }
        return (V)future;
    }

    private static boolean hasConstructorUsableByGetChecked(Class<? extends Exception> class_) {
        try {
            Exception exception = new Exception();
            FuturesGetChecked.newWithCause(class_, exception);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    static boolean isCheckedException(Class<? extends Exception> class_) {
        return RuntimeException.class.isAssignableFrom(class_) ^ true;
    }

    @NullableDecl
    private static <X> X newFromConstructor(Constructor<X> constructor, Throwable throwable) {
        Class<?>[] arrclass = constructor.getParameterTypes();
        Object[] arrobject = new Object[arrclass.length];
        for (int i = 0; i < arrclass.length; ++i) {
            Class<?> class_ = arrclass[i];
            if (class_.equals(String.class)) {
                arrobject[i] = throwable.toString();
                continue;
            }
            if (!class_.equals(Throwable.class)) return null;
            arrobject[i] = throwable;
        }
        try {
            constructor = constructor.newInstance(arrobject);
        }
        catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException exception) {
            return null;
        }
        return (X)constructor;
    }

    private static <X extends Exception> X newWithCause(Class<X> class_, Throwable throwable) {
        Exception exception;
        Object object = FuturesGetChecked.preferringStrings(Arrays.asList(class_.getConstructors())).iterator();
        do {
            if (object.hasNext()) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("No appropriate constructor for exception of type ");
            ((StringBuilder)object).append(class_);
            ((StringBuilder)object).append(" in response to chained exception");
            throw new IllegalArgumentException(((StringBuilder)object).toString(), throwable);
        } while ((exception = (Exception)FuturesGetChecked.newFromConstructor(object.next(), throwable)) == null);
        if (exception.getCause() != null) return (X)exception;
        exception.initCause(throwable);
        return (X)exception;
    }

    private static <X extends Exception> List<Constructor<X>> preferringStrings(List<Constructor<X>> list) {
        return WITH_STRING_PARAM_FIRST.sortedCopy(list);
    }

    static GetCheckedTypeValidator weakSetValidator() {
        return GetCheckedTypeValidatorHolder.WeakSetValidator.INSTANCE;
    }

    private static <X extends Exception> void wrapAndThrowExceptionOrError(Throwable throwable, Class<X> class_) throws Exception {
        if (throwable instanceof Error) throw new ExecutionError((Error)throwable);
        if (!(throwable instanceof RuntimeException)) throw FuturesGetChecked.newWithCause(class_, throwable);
        throw new UncheckedExecutionException(throwable);
    }

    static interface GetCheckedTypeValidator {
        public void validateClass(Class<? extends Exception> var1);
    }

    static class GetCheckedTypeValidatorHolder {
        static final GetCheckedTypeValidator BEST_VALIDATOR;
        static final String CLASS_VALUE_VALIDATOR_NAME;

        static {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(GetCheckedTypeValidatorHolder.class.getName());
            stringBuilder.append("$ClassValueValidator");
            CLASS_VALUE_VALIDATOR_NAME = stringBuilder.toString();
            BEST_VALIDATOR = GetCheckedTypeValidatorHolder.getBestValidator();
        }

        GetCheckedTypeValidatorHolder() {
        }

        static GetCheckedTypeValidator getBestValidator() {
            try {
                return (GetCheckedTypeValidator)Class.forName(CLASS_VALUE_VALIDATOR_NAME).getEnumConstants()[0];
            }
            catch (Throwable throwable) {
                return FuturesGetChecked.weakSetValidator();
            }
        }

        static final class ClassValueValidator
        extends Enum<ClassValueValidator>
        implements GetCheckedTypeValidator {
            private static final /* synthetic */ ClassValueValidator[] $VALUES;
            public static final /* enum */ ClassValueValidator INSTANCE;
            private static final ClassValue<Boolean> isValidClass;

            static {
                ClassValueValidator classValueValidator;
                INSTANCE = classValueValidator = new ClassValueValidator();
                $VALUES = new ClassValueValidator[]{classValueValidator};
                isValidClass = new ClassValue<Boolean>(){

                    @Override
                    protected Boolean computeValue(Class<?> class_) {
                        FuturesGetChecked.checkExceptionClassValidity(class_.asSubclass(Exception.class));
                        return true;
                    }
                };
            }

            public static ClassValueValidator valueOf(String string2) {
                return Enum.valueOf(ClassValueValidator.class, string2);
            }

            public static ClassValueValidator[] values() {
                return (ClassValueValidator[])$VALUES.clone();
            }

            @Override
            public void validateClass(Class<? extends Exception> class_) {
                isValidClass.get(class_);
            }

        }

        static final class WeakSetValidator
        extends Enum<WeakSetValidator>
        implements GetCheckedTypeValidator {
            private static final /* synthetic */ WeakSetValidator[] $VALUES;
            public static final /* enum */ WeakSetValidator INSTANCE;
            private static final Set<WeakReference<Class<? extends Exception>>> validClasses;

            static {
                WeakSetValidator weakSetValidator;
                INSTANCE = weakSetValidator = new WeakSetValidator();
                $VALUES = new WeakSetValidator[]{weakSetValidator};
                validClasses = new CopyOnWriteArraySet<WeakReference<Class<? extends Exception>>>();
            }

            public static WeakSetValidator valueOf(String string2) {
                return Enum.valueOf(WeakSetValidator.class, string2);
            }

            public static WeakSetValidator[] values() {
                return (WeakSetValidator[])$VALUES.clone();
            }

            @Override
            public void validateClass(Class<? extends Exception> class_) {
                Iterator<WeakReference<Class<? extends Exception>>> iterator2 = validClasses.iterator();
                while (iterator2.hasNext()) {
                    if (!class_.equals(iterator2.next().get())) continue;
                    return;
                }
                FuturesGetChecked.checkExceptionClassValidity(class_);
                if (validClasses.size() > 1000) {
                    validClasses.clear();
                }
                validClasses.add(new WeakReference<Class<? extends Exception>>(class_));
            }
        }

    }

}

