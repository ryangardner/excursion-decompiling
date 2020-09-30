/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.primitives.Booleans;
import java.io.Serializable;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class Cut<C extends Comparable>
implements Comparable<Cut<C>>,
Serializable {
    private static final long serialVersionUID = 0L;
    @NullableDecl
    final C endpoint;

    Cut(@NullableDecl C c) {
        this.endpoint = c;
    }

    static <C extends Comparable> Cut<C> aboveAll() {
        return AboveAll.INSTANCE;
    }

    static <C extends Comparable> Cut<C> aboveValue(C c) {
        return new AboveValue<C>(c);
    }

    static <C extends Comparable> Cut<C> belowAll() {
        return BelowAll.INSTANCE;
    }

    static <C extends Comparable> Cut<C> belowValue(C c) {
        return new BelowValue<C>(c);
    }

    Cut<C> canonical(DiscreteDomain<C> discreteDomain) {
        return this;
    }

    @Override
    public int compareTo(Cut<C> cut) {
        if (cut == Cut.belowAll()) {
            return 1;
        }
        if (cut == Cut.aboveAll()) {
            return -1;
        }
        int n = Range.compareOrThrow(this.endpoint, cut.endpoint);
        if (n == 0) return Booleans.compare(this instanceof AboveValue, cut instanceof AboveValue);
        return n;
    }

    abstract void describeAsLowerBound(StringBuilder var1);

    abstract void describeAsUpperBound(StringBuilder var1);

    C endpoint() {
        return this.endpoint;
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = object instanceof Cut;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (Cut)object;
        try {
            int n = this.compareTo((Cut<C>)object);
            bl3 = bl;
            if (n != 0) return bl3;
            return true;
        }
        catch (ClassCastException classCastException) {
            return bl;
        }
    }

    abstract C greatestValueBelow(DiscreteDomain<C> var1);

    public abstract int hashCode();

    abstract boolean isLessThan(C var1);

    abstract C leastValueAbove(DiscreteDomain<C> var1);

    abstract BoundType typeAsLowerBound();

    abstract BoundType typeAsUpperBound();

    abstract Cut<C> withLowerBoundType(BoundType var1, DiscreteDomain<C> var2);

    abstract Cut<C> withUpperBoundType(BoundType var1, DiscreteDomain<C> var2);

    private static final class AboveAll
    extends Cut<Comparable<?>> {
        private static final AboveAll INSTANCE = new AboveAll();
        private static final long serialVersionUID = 0L;

        private AboveAll() {
            super(null);
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        public int compareTo(Cut<Comparable<?>> cut) {
            if (cut != this) return 1;
            return 0;
        }

        @Override
        void describeAsLowerBound(StringBuilder stringBuilder) {
            throw new AssertionError();
        }

        @Override
        void describeAsUpperBound(StringBuilder stringBuilder) {
            stringBuilder.append("+\u221e)");
        }

        @Override
        Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        @Override
        Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.maxValue();
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this);
        }

        @Override
        boolean isLessThan(Comparable<?> comparable) {
            return false;
        }

        @Override
        Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        public String toString() {
            return "+\u221e";
        }

        @Override
        BoundType typeAsLowerBound() {
            throw new AssertionError((Object)"this statement should be unreachable");
        }

        @Override
        BoundType typeAsUpperBound() {
            throw new IllegalStateException();
        }

        @Override
        Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError((Object)"this statement should be unreachable");
        }

        @Override
        Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }
    }

    private static final class AboveValue<C extends Comparable>
    extends Cut<C> {
        private static final long serialVersionUID = 0L;

        AboveValue(C c) {
            super((Comparable)Preconditions.checkNotNull(c));
        }

        @Override
        Cut<C> canonical(DiscreteDomain<C> cut) {
            if ((cut = this.leastValueAbove((DiscreteDomain<C>)((Object)cut))) == null) return Cut.aboveAll();
            return AboveValue.belowValue(cut);
        }

        @Override
        void describeAsLowerBound(StringBuilder stringBuilder) {
            stringBuilder.append('(');
            stringBuilder.append(this.endpoint);
        }

        @Override
        void describeAsUpperBound(StringBuilder stringBuilder) {
            stringBuilder.append(this.endpoint);
            stringBuilder.append(']');
        }

        @Override
        C greatestValueBelow(DiscreteDomain<C> discreteDomain) {
            return (C)this.endpoint;
        }

        @Override
        public int hashCode() {
            return this.endpoint.hashCode();
        }

        @Override
        boolean isLessThan(C c) {
            if (Range.compareOrThrow(this.endpoint, c) >= 0) return false;
            return true;
        }

        @Override
        C leastValueAbove(DiscreteDomain<C> discreteDomain) {
            return (C)discreteDomain.next(this.endpoint);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("/");
            stringBuilder.append(this.endpoint);
            stringBuilder.append("\\");
            return stringBuilder.toString();
        }

        @Override
        BoundType typeAsLowerBound() {
            return BoundType.OPEN;
        }

        @Override
        BoundType typeAsUpperBound() {
            return BoundType.CLOSED;
        }

        @Override
        Cut<C> withLowerBoundType(BoundType object, DiscreteDomain<C> discreteDomain) {
            int n = 1.$SwitchMap$com$google$common$collect$BoundType[object.ordinal()];
            if (n != 1) {
                if (n != 2) throw new AssertionError();
                return this;
            }
            object = discreteDomain.next(this.endpoint);
            if (object != null) return AboveValue.belowValue(object);
            return Cut.belowAll();
        }

        @Override
        Cut<C> withUpperBoundType(BoundType object, DiscreteDomain<C> discreteDomain) {
            int n = 1.$SwitchMap$com$google$common$collect$BoundType[object.ordinal()];
            if (n == 1) return this;
            if (n != 2) throw new AssertionError();
            object = discreteDomain.next(this.endpoint);
            if (object != null) return AboveValue.belowValue(object);
            return Cut.aboveAll();
        }
    }

    private static final class BelowAll
    extends Cut<Comparable<?>> {
        private static final BelowAll INSTANCE = new BelowAll();
        private static final long serialVersionUID = 0L;

        private BelowAll() {
            super(null);
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        Cut<Comparable<?>> canonical(DiscreteDomain<Comparable<?>> object) {
            try {
                return Cut.belowValue(((DiscreteDomain)object).minValue());
            }
            catch (NoSuchElementException noSuchElementException) {
                return this;
            }
        }

        @Override
        public int compareTo(Cut<Comparable<?>> cut) {
            if (cut != this) return -1;
            return 0;
        }

        @Override
        void describeAsLowerBound(StringBuilder stringBuilder) {
            stringBuilder.append("(-\u221e");
        }

        @Override
        void describeAsUpperBound(StringBuilder stringBuilder) {
            throw new AssertionError();
        }

        @Override
        Comparable<?> endpoint() {
            throw new IllegalStateException("range unbounded on this side");
        }

        @Override
        Comparable<?> greatestValueBelow(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this);
        }

        @Override
        boolean isLessThan(Comparable<?> comparable) {
            return true;
        }

        @Override
        Comparable<?> leastValueAbove(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.minValue();
        }

        public String toString() {
            return "-\u221e";
        }

        @Override
        BoundType typeAsLowerBound() {
            throw new IllegalStateException();
        }

        @Override
        BoundType typeAsUpperBound() {
            throw new AssertionError((Object)"this statement should be unreachable");
        }

        @Override
        Cut<Comparable<?>> withLowerBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        @Override
        Cut<Comparable<?>> withUpperBoundType(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError((Object)"this statement should be unreachable");
        }
    }

    private static final class BelowValue<C extends Comparable>
    extends Cut<C> {
        private static final long serialVersionUID = 0L;

        BelowValue(C c) {
            super((Comparable)Preconditions.checkNotNull(c));
        }

        @Override
        void describeAsLowerBound(StringBuilder stringBuilder) {
            stringBuilder.append('[');
            stringBuilder.append(this.endpoint);
        }

        @Override
        void describeAsUpperBound(StringBuilder stringBuilder) {
            stringBuilder.append(this.endpoint);
            stringBuilder.append(')');
        }

        @Override
        C greatestValueBelow(DiscreteDomain<C> discreteDomain) {
            return (C)discreteDomain.previous(this.endpoint);
        }

        @Override
        public int hashCode() {
            return this.endpoint.hashCode();
        }

        @Override
        boolean isLessThan(C c) {
            if (Range.compareOrThrow(this.endpoint, c) > 0) return false;
            return true;
        }

        @Override
        C leastValueAbove(DiscreteDomain<C> discreteDomain) {
            return (C)this.endpoint;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\\");
            stringBuilder.append(this.endpoint);
            stringBuilder.append("/");
            return stringBuilder.toString();
        }

        @Override
        BoundType typeAsLowerBound() {
            return BoundType.CLOSED;
        }

        @Override
        BoundType typeAsUpperBound() {
            return BoundType.OPEN;
        }

        @Override
        Cut<C> withLowerBoundType(BoundType object, DiscreteDomain<C> discreteDomain) {
            int n = 1.$SwitchMap$com$google$common$collect$BoundType[object.ordinal()];
            if (n == 1) return this;
            if (n != 2) throw new AssertionError();
            object = discreteDomain.previous(this.endpoint);
            if (object != null) return new AboveValue<BoundType>((BoundType)((Object)object));
            return Cut.belowAll();
        }

        @Override
        Cut<C> withUpperBoundType(BoundType object, DiscreteDomain<C> discreteDomain) {
            int n = 1.$SwitchMap$com$google$common$collect$BoundType[object.ordinal()];
            if (n != 1) {
                if (n != 2) throw new AssertionError();
                return this;
            }
            object = discreteDomain.previous(this.endpoint);
            if (object != null) return new AboveValue<BoundType>((BoundType)((Object)object));
            return Cut.aboveAll();
        }
    }

}

