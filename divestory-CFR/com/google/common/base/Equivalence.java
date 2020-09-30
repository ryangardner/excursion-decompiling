/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Function;
import com.google.common.base.FunctionalEquivalence;
import com.google.common.base.Objects;
import com.google.common.base.PairwiseEquivalence;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class Equivalence<T> {
    protected Equivalence() {
    }

    public static Equivalence<Object> equals() {
        return Equals.INSTANCE;
    }

    public static Equivalence<Object> identity() {
        return Identity.INSTANCE;
    }

    protected abstract boolean doEquivalent(T var1, T var2);

    protected abstract int doHash(T var1);

    public final boolean equivalent(@NullableDecl T t, @NullableDecl T t2) {
        if (t == t2) {
            return true;
        }
        if (t == null) return false;
        if (t2 != null) return this.doEquivalent(t, t2);
        return false;
    }

    public final Predicate<T> equivalentTo(@NullableDecl T t) {
        return new EquivalentToPredicate<T>(this, t);
    }

    public final int hash(@NullableDecl T t) {
        if (t != null) return this.doHash(t);
        return 0;
    }

    public final <F> Equivalence<F> onResultOf(Function<F, ? extends T> function) {
        return new FunctionalEquivalence<F, T>(function, this);
    }

    public final <S extends T> Equivalence<Iterable<S>> pairwise() {
        return new PairwiseEquivalence<T>(this);
    }

    public final <S extends T> Wrapper<S> wrap(@NullableDecl S s) {
        return new Wrapper<T>(this, s);
    }

    static final class Equals
    extends Equivalence<Object>
    implements Serializable {
        static final Equals INSTANCE = new Equals();
        private static final long serialVersionUID = 1L;

        Equals() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        protected boolean doEquivalent(Object object, Object object2) {
            return object.equals(object2);
        }

        @Override
        protected int doHash(Object object) {
            return object.hashCode();
        }
    }

    private static final class EquivalentToPredicate<T>
    implements Predicate<T>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final Equivalence<T> equivalence;
        @NullableDecl
        private final T target;

        EquivalentToPredicate(Equivalence<T> equivalence, @NullableDecl T t) {
            this.equivalence = Preconditions.checkNotNull(equivalence);
            this.target = t;
        }

        @Override
        public boolean apply(@NullableDecl T t) {
            return this.equivalence.equivalent(t, this.target);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof EquivalentToPredicate)) return false;
            object = (EquivalentToPredicate)object;
            if (!this.equivalence.equals(((EquivalentToPredicate)object).equivalence)) return false;
            if (!Objects.equal(this.target, ((EquivalentToPredicate)object).target)) return false;
            return bl;
        }

        public int hashCode() {
            return Objects.hashCode(this.equivalence, this.target);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.equivalence);
            stringBuilder.append(".equivalentTo(");
            stringBuilder.append(this.target);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static final class Identity
    extends Equivalence<Object>
    implements Serializable {
        static final Identity INSTANCE = new Identity();
        private static final long serialVersionUID = 1L;

        Identity() {
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        protected boolean doEquivalent(Object object, Object object2) {
            return false;
        }

        @Override
        protected int doHash(Object object) {
            return System.identityHashCode(object);
        }
    }

    public static final class Wrapper<T>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final Equivalence<? super T> equivalence;
        @NullableDecl
        private final T reference;

        private Wrapper(Equivalence<? super T> equivalence, @NullableDecl T t) {
            this.equivalence = Preconditions.checkNotNull(equivalence);
            this.reference = t;
        }

        public boolean equals(@NullableDecl Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof Wrapper)) return false;
            object = (Wrapper)object;
            if (!this.equivalence.equals(((Wrapper)object).equivalence)) return false;
            return this.equivalence.equivalent(this.reference, ((Wrapper)object).reference);
        }

        @NullableDecl
        public T get() {
            return this.reference;
        }

        public int hashCode() {
            return this.equivalence.hash(this.reference);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.equivalence);
            stringBuilder.append(".wrap(");
            stringBuilder.append(this.reference);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

}

