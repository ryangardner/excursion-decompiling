/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Functions {
    private Functions() {
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> function, Function<A, ? extends B> function2) {
        return new FunctionComposition<A, B, C>(function, function2);
    }

    public static <E> Function<Object, E> constant(@NullableDecl E e) {
        return new ConstantFunction<E>(e);
    }

    public static <K, V> Function<K, V> forMap(Map<K, V> map) {
        return new FunctionForMapNoDefault<K, V>(map);
    }

    public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, @NullableDecl V v) {
        return new ForMapWithDefault<K, V>(map, (V)v);
    }

    public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
        return new PredicateFunction(predicate);
    }

    public static <T> Function<Object, T> forSupplier(Supplier<T> supplier) {
        return new SupplierFunction(supplier);
    }

    public static <E> Function<E, E> identity() {
        return IdentityFunction.INSTANCE;
    }

    public static Function<Object, String> toStringFunction() {
        return ToStringFunction.INSTANCE;
    }

    private static class ConstantFunction<E>
    implements Function<Object, E>,
    Serializable {
        private static final long serialVersionUID = 0L;
        @NullableDecl
        private final E value;

        public ConstantFunction(@NullableDecl E e) {
            this.value = e;
        }

        @Override
        public E apply(@NullableDecl Object object) {
            return this.value;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof ConstantFunction)) return false;
            object = (ConstantFunction)object;
            return Objects.equal(this.value, ((ConstantFunction)object).value);
        }

        public int hashCode() {
            E e = this.value;
            if (e != null) return e.hashCode();
            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Functions.constant(");
            stringBuilder.append(this.value);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class ForMapWithDefault<K, V>
    implements Function<K, V>,
    Serializable {
        private static final long serialVersionUID = 0L;
        @NullableDecl
        final V defaultValue;
        final Map<K, ? extends V> map;

        ForMapWithDefault(Map<K, ? extends V> map, @NullableDecl V v) {
            this.map = Preconditions.checkNotNull(map);
            this.defaultValue = v;
        }

        @Override
        public V apply(@NullableDecl K k) {
            V v;
            V v2 = v = this.map.get(k);
            if (v != null) return v2;
            if (this.map.containsKey(k)) {
                v2 = v;
                return v2;
            }
            v2 = this.defaultValue;
            return v2;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof ForMapWithDefault;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (ForMapWithDefault)object;
            bl3 = bl;
            if (!this.map.equals(((ForMapWithDefault)object).map)) return bl3;
            bl3 = bl;
            if (!Objects.equal(this.defaultValue, ((ForMapWithDefault)object).defaultValue)) return bl3;
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.map, this.defaultValue);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Functions.forMap(");
            stringBuilder.append(this.map);
            stringBuilder.append(", defaultValue=");
            stringBuilder.append(this.defaultValue);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class FunctionComposition<A, B, C>
    implements Function<A, C>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final Function<A, ? extends B> f;
        private final Function<B, C> g;

        public FunctionComposition(Function<B, C> function, Function<A, ? extends B> function2) {
            this.g = Preconditions.checkNotNull(function);
            this.f = Preconditions.checkNotNull(function2);
        }

        @Override
        public C apply(@NullableDecl A a) {
            return this.g.apply(this.f.apply(a));
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof FunctionComposition;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (FunctionComposition)object;
            bl3 = bl;
            if (!this.f.equals(((FunctionComposition)object).f)) return bl3;
            bl3 = bl;
            if (!this.g.equals(((FunctionComposition)object).g)) return bl3;
            return true;
        }

        public int hashCode() {
            return this.f.hashCode() ^ this.g.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.g);
            stringBuilder.append("(");
            stringBuilder.append(this.f);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class FunctionForMapNoDefault<K, V>
    implements Function<K, V>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final Map<K, V> map;

        FunctionForMapNoDefault(Map<K, V> map) {
            this.map = Preconditions.checkNotNull(map);
        }

        @Override
        public V apply(@NullableDecl K k) {
            V v = this.map.get(k);
            boolean bl = v != null || this.map.containsKey(k);
            Preconditions.checkArgument(bl, "Key '%s' not present in map", k);
            return v;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof FunctionForMapNoDefault)) return false;
            object = (FunctionForMapNoDefault)object;
            return this.map.equals(((FunctionForMapNoDefault)object).map);
        }

        public int hashCode() {
            return this.map.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Functions.forMap(");
            stringBuilder.append(this.map);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class IdentityFunction
    extends Enum<IdentityFunction>
    implements Function<Object, Object> {
        private static final /* synthetic */ IdentityFunction[] $VALUES;
        public static final /* enum */ IdentityFunction INSTANCE;

        static {
            IdentityFunction identityFunction;
            INSTANCE = identityFunction = new IdentityFunction();
            $VALUES = new IdentityFunction[]{identityFunction};
        }

        public static IdentityFunction valueOf(String string2) {
            return Enum.valueOf(IdentityFunction.class, string2);
        }

        public static IdentityFunction[] values() {
            return (IdentityFunction[])$VALUES.clone();
        }

        @NullableDecl
        @Override
        public Object apply(@NullableDecl Object object) {
            return object;
        }

        public String toString() {
            return "Functions.identity()";
        }
    }

    private static class PredicateFunction<T>
    implements Function<T, Boolean>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final Predicate<T> predicate;

        private PredicateFunction(Predicate<T> predicate) {
            this.predicate = Preconditions.checkNotNull(predicate);
        }

        @Override
        public Boolean apply(@NullableDecl T t) {
            return this.predicate.apply(t);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof PredicateFunction)) return false;
            object = (PredicateFunction)object;
            return this.predicate.equals(((PredicateFunction)object).predicate);
        }

        public int hashCode() {
            return this.predicate.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Functions.forPredicate(");
            stringBuilder.append(this.predicate);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class SupplierFunction<T>
    implements Function<Object, T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final Supplier<T> supplier;

        private SupplierFunction(Supplier<T> supplier) {
            this.supplier = Preconditions.checkNotNull(supplier);
        }

        @Override
        public T apply(@NullableDecl Object object) {
            return this.supplier.get();
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof SupplierFunction)) return false;
            object = (SupplierFunction)object;
            return this.supplier.equals(((SupplierFunction)object).supplier);
        }

        public int hashCode() {
            return this.supplier.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Functions.forSupplier(");
            stringBuilder.append(this.supplier);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class ToStringFunction
    extends Enum<ToStringFunction>
    implements Function<Object, String> {
        private static final /* synthetic */ ToStringFunction[] $VALUES;
        public static final /* enum */ ToStringFunction INSTANCE;

        static {
            ToStringFunction toStringFunction;
            INSTANCE = toStringFunction = new ToStringFunction();
            $VALUES = new ToStringFunction[]{toStringFunction};
        }

        public static ToStringFunction valueOf(String string2) {
            return Enum.valueOf(ToStringFunction.class, string2);
        }

        public static ToStringFunction[] values() {
            return (ToStringFunction[])$VALUES.clone();
        }

        @Override
        public String apply(Object object) {
            Preconditions.checkNotNull(object);
            return object.toString();
        }

        public String toString() {
            return "Functions.toStringFunction()";
        }
    }

}

