/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.Immutable;
import java.util.Comparator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
public final class ElementOrder<T> {
    @NullableDecl
    private final Comparator<T> comparator;
    private final Type type;

    private ElementOrder(Type type, @NullableDecl Comparator<T> comparator) {
        this.type = Preconditions.checkNotNull(type);
        this.comparator = comparator;
        Type type2 = Type.SORTED;
        boolean bl = true;
        boolean bl2 = type == type2;
        boolean bl3 = comparator != null;
        if (bl2 != bl3) {
            bl = false;
        }
        Preconditions.checkState(bl);
    }

    public static <S> ElementOrder<S> insertion() {
        return new ElementOrder<T>(Type.INSERTION, null);
    }

    public static <S extends Comparable<? super S>> ElementOrder<S> natural() {
        return new ElementOrder(Type.SORTED, Ordering.natural());
    }

    public static <S> ElementOrder<S> sorted(Comparator<S> comparator) {
        return new ElementOrder<S>(Type.SORTED, comparator);
    }

    static <S> ElementOrder<S> stable() {
        return new ElementOrder<T>(Type.STABLE, null);
    }

    public static <S> ElementOrder<S> unordered() {
        return new ElementOrder<T>(Type.UNORDERED, null);
    }

    <T1 extends T> ElementOrder<T1> cast() {
        return this;
    }

    public Comparator<T> comparator() {
        Comparator<T> comparator = this.comparator;
        if (comparator == null) throw new UnsupportedOperationException("This ordering does not define a comparator.");
        return comparator;
    }

    <K extends T, V> Map<K, V> createMap(int n) {
        int n2 = 1.$SwitchMap$com$google$common$graph$ElementOrder$Type[this.type.ordinal()];
        if (n2 == 1) return Maps.newHashMapWithExpectedSize(n);
        if (n2 == 2) return Maps.newLinkedHashMapWithExpectedSize(n);
        if (n2 == 3) return Maps.newLinkedHashMapWithExpectedSize(n);
        if (n2 != 4) throw new AssertionError();
        return Maps.newTreeMap(this.comparator());
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof ElementOrder)) {
            return false;
        }
        object = (ElementOrder)object;
        if (this.type != ((ElementOrder)object).type) return false;
        if (!Objects.equal(this.comparator, ((ElementOrder)object).comparator)) return false;
        return bl;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.type, this.comparator});
    }

    public String toString() {
        MoreObjects.ToStringHelper toStringHelper = MoreObjects.toStringHelper(this).add("type", (Object)this.type);
        Comparator<T> comparator = this.comparator;
        if (comparator == null) return toStringHelper.toString();
        toStringHelper.add("comparator", comparator);
        return toStringHelper.toString();
    }

    public Type type() {
        return this.type;
    }

    public static final class Type
    extends Enum<Type> {
        private static final /* synthetic */ Type[] $VALUES;
        public static final /* enum */ Type INSERTION;
        public static final /* enum */ Type SORTED;
        public static final /* enum */ Type STABLE;
        public static final /* enum */ Type UNORDERED;

        static {
            Type type;
            UNORDERED = new Type();
            STABLE = new Type();
            INSERTION = new Type();
            SORTED = type = new Type();
            $VALUES = new Type[]{UNORDERED, STABLE, INSERTION, type};
        }

        public static Type valueOf(String string2) {
            return Enum.valueOf(Type.class, string2);
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }
    }

}

