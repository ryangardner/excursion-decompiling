/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Suppliers {
    private Suppliers() {
    }

    public static <F, T> Supplier<T> compose(Function<? super F, T> function, Supplier<F> supplier) {
        return new SupplierComposition<F, T>(function, supplier);
    }

    public static <T> Supplier<T> memoize(Supplier<T> supplier) {
        if (supplier instanceof NonSerializableMemoizingSupplier) return supplier;
        if (supplier instanceof MemoizingSupplier) {
            return supplier;
        }
        if (!(supplier instanceof Serializable)) return new NonSerializableMemoizingSupplier<T>(supplier);
        return new MemoizingSupplier<T>(supplier);
    }

    public static <T> Supplier<T> memoizeWithExpiration(Supplier<T> supplier, long l, TimeUnit timeUnit) {
        return new ExpiringMemoizingSupplier<T>(supplier, l, timeUnit);
    }

    public static <T> Supplier<T> ofInstance(@NullableDecl T t) {
        return new SupplierOfInstance<T>(t);
    }

    public static <T> Function<Supplier<T>, T> supplierFunction() {
        return SupplierFunctionImpl.INSTANCE;
    }

    public static <T> Supplier<T> synchronizedSupplier(Supplier<T> supplier) {
        return new ThreadSafeSupplier<T>(supplier);
    }

    static class ExpiringMemoizingSupplier<T>
    implements Supplier<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final Supplier<T> delegate;
        final long durationNanos;
        volatile transient long expirationNanos;
        @NullableDecl
        volatile transient T value;

        ExpiringMemoizingSupplier(Supplier<T> supplier, long l, TimeUnit timeUnit) {
            this.delegate = Preconditions.checkNotNull(supplier);
            this.durationNanos = timeUnit.toNanos(l);
            boolean bl = l > 0L;
            Preconditions.checkArgument(bl, "duration (%s %s) must be > 0", l, (Object)timeUnit);
        }

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public T get() {
            long l = this.expirationNanos;
            long l2 = Platform.systemNanoTime();
            if (l != 0L) {
                if (l2 - l < 0L) return this.value;
            }
            synchronized (this) {
                if (l != this.expirationNanos) return this.value;
                T t = this.delegate.get();
                this.value = t;
                l2 = l = l2 + this.durationNanos;
                if (l == 0L) {
                    l2 = 1L;
                }
                this.expirationNanos = l2;
                return t;
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Suppliers.memoizeWithExpiration(");
            stringBuilder.append(this.delegate);
            stringBuilder.append(", ");
            stringBuilder.append(this.durationNanos);
            stringBuilder.append(", NANOS)");
            return stringBuilder.toString();
        }
    }

    static class MemoizingSupplier<T>
    implements Supplier<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final Supplier<T> delegate;
        volatile transient boolean initialized;
        @NullableDecl
        transient T value;

        MemoizingSupplier(Supplier<T> supplier) {
            this.delegate = Preconditions.checkNotNull(supplier);
        }

        @Override
        public T get() {
            if (this.initialized) return this.value;
            synchronized (this) {
                if (this.initialized) return this.value;
                T t = this.delegate.get();
                this.value = t;
                this.initialized = true;
                return t;
            }
        }

        public String toString() {
            Object object;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Suppliers.memoize(");
            if (this.initialized) {
                object = new StringBuilder();
                ((StringBuilder)object).append("<supplier that returned ");
                ((StringBuilder)object).append(this.value);
                ((StringBuilder)object).append(">");
                object = ((StringBuilder)object).toString();
            } else {
                object = this.delegate;
            }
            stringBuilder.append(object);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static class NonSerializableMemoizingSupplier<T>
    implements Supplier<T> {
        volatile Supplier<T> delegate;
        volatile boolean initialized;
        @NullableDecl
        T value;

        NonSerializableMemoizingSupplier(Supplier<T> supplier) {
            this.delegate = Preconditions.checkNotNull(supplier);
        }

        @Override
        public T get() {
            if (this.initialized) return this.value;
            synchronized (this) {
                if (this.initialized) return this.value;
                T t = this.delegate.get();
                this.value = t;
                this.initialized = true;
                this.delegate = null;
                return t;
            }
        }

        public String toString() {
            Supplier<T> supplier = this.delegate;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Suppliers.memoize(");
            Object object = supplier;
            if (supplier == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("<supplier that returned ");
                ((StringBuilder)object).append(this.value);
                ((StringBuilder)object).append(">");
                object = ((StringBuilder)object).toString();
            }
            stringBuilder.append(object);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class SupplierComposition<F, T>
    implements Supplier<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final Function<? super F, T> function;
        final Supplier<F> supplier;

        SupplierComposition(Function<? super F, T> function, Supplier<F> supplier) {
            this.function = Preconditions.checkNotNull(function);
            this.supplier = Preconditions.checkNotNull(supplier);
        }

        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof SupplierComposition;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (SupplierComposition)object;
            bl3 = bl;
            if (!this.function.equals(((SupplierComposition)object).function)) return bl3;
            bl3 = bl;
            if (!this.supplier.equals(((SupplierComposition)object).supplier)) return bl3;
            return true;
        }

        @Override
        public T get() {
            return this.function.apply(this.supplier.get());
        }

        public int hashCode() {
            return Objects.hashCode(this.function, this.supplier);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Suppliers.compose(");
            stringBuilder.append(this.function);
            stringBuilder.append(", ");
            stringBuilder.append(this.supplier);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static interface SupplierFunction<T>
    extends Function<Supplier<T>, T> {
    }

    private static final class SupplierFunctionImpl
    extends Enum<SupplierFunctionImpl>
    implements SupplierFunction<Object> {
        private static final /* synthetic */ SupplierFunctionImpl[] $VALUES;
        public static final /* enum */ SupplierFunctionImpl INSTANCE;

        static {
            SupplierFunctionImpl supplierFunctionImpl;
            INSTANCE = supplierFunctionImpl = new SupplierFunctionImpl();
            $VALUES = new SupplierFunctionImpl[]{supplierFunctionImpl};
        }

        public static SupplierFunctionImpl valueOf(String string2) {
            return Enum.valueOf(SupplierFunctionImpl.class, string2);
        }

        public static SupplierFunctionImpl[] values() {
            return (SupplierFunctionImpl[])$VALUES.clone();
        }

        @Override
        public Object apply(Supplier<Object> supplier) {
            return supplier.get();
        }

        public String toString() {
            return "Suppliers.supplierFunction()";
        }
    }

    private static class SupplierOfInstance<T>
    implements Supplier<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        @NullableDecl
        final T instance;

        SupplierOfInstance(@NullableDecl T t) {
            this.instance = t;
        }

        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof SupplierOfInstance)) return false;
            object = (SupplierOfInstance)object;
            return Objects.equal(this.instance, ((SupplierOfInstance)object).instance);
        }

        @Override
        public T get() {
            return this.instance;
        }

        public int hashCode() {
            return Objects.hashCode(this.instance);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Suppliers.ofInstance(");
            stringBuilder.append(this.instance);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class ThreadSafeSupplier<T>
    implements Supplier<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final Supplier<T> delegate;

        ThreadSafeSupplier(Supplier<T> supplier) {
            this.delegate = Preconditions.checkNotNull(supplier);
        }

        @Override
        public T get() {
            Supplier<T> supplier = this.delegate;
            synchronized (supplier) {
                T t = this.delegate.get();
                return t;
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Suppliers.synchronizedSupplier(");
            stringBuilder.append(this.delegate);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

}

