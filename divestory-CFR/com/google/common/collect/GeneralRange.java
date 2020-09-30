/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import java.io.Serializable;
import java.util.Comparator;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class GeneralRange<T>
implements Serializable {
    private final Comparator<? super T> comparator;
    private final boolean hasLowerBound;
    private final boolean hasUpperBound;
    private final BoundType lowerBoundType;
    @NullableDecl
    private final T lowerEndpoint;
    @MonotonicNonNullDecl
    private transient GeneralRange<T> reverse;
    private final BoundType upperBoundType;
    @NullableDecl
    private final T upperEndpoint;

    private GeneralRange(Comparator<? super T> comparator, boolean bl, @NullableDecl T t, BoundType boundType, boolean bl2, @NullableDecl T t2, BoundType boundType2) {
        this.comparator = Preconditions.checkNotNull(comparator);
        this.hasLowerBound = bl;
        this.hasUpperBound = bl2;
        this.lowerEndpoint = t;
        this.lowerBoundType = Preconditions.checkNotNull(boundType);
        this.upperEndpoint = t2;
        this.upperBoundType = Preconditions.checkNotNull(boundType2);
        if (bl) {
            comparator.compare(t, t);
        }
        if (bl2) {
            comparator.compare(t2, t2);
        }
        if (!bl) return;
        if (!bl2) return;
        int n = comparator.compare(t, t2);
        int n2 = 1;
        bl = n <= 0;
        Preconditions.checkArgument(bl, "lowerEndpoint (%s) > upperEndpoint (%s)", t, t2);
        if (n != 0) return;
        n = boundType != BoundType.OPEN ? 1 : 0;
        if (boundType2 == BoundType.OPEN) {
            n2 = 0;
        }
        Preconditions.checkArgument((n | n2) != 0);
    }

    static <T> GeneralRange<T> all(Comparator<? super T> comparator) {
        return new GeneralRange<Object>(comparator, false, null, BoundType.OPEN, false, null, BoundType.OPEN);
    }

    static <T> GeneralRange<T> downTo(Comparator<? super T> comparator, @NullableDecl T t, BoundType boundType) {
        return new GeneralRange<Object>((Comparator<Object>)comparator, true, t, boundType, false, null, BoundType.OPEN);
    }

    static <T extends Comparable> GeneralRange<T> from(Range<T> range) {
        BoundType boundType;
        boolean bl = range.hasLowerBound();
        T t = null;
        T t2 = bl ? (T)range.lowerEndpoint() : null;
        BoundType boundType2 = range.hasLowerBound() ? range.lowerBoundType() : BoundType.OPEN;
        if (range.hasUpperBound()) {
            t = range.upperEndpoint();
        }
        if (range.hasUpperBound()) {
            boundType = range.upperBoundType();
            return new GeneralRange<Object>(Ordering.natural(), range.hasLowerBound(), t2, boundType2, range.hasUpperBound(), t, boundType);
        }
        boundType = BoundType.OPEN;
        return new GeneralRange<Object>(Ordering.natural(), range.hasLowerBound(), t2, boundType2, range.hasUpperBound(), t, boundType);
    }

    static <T> GeneralRange<T> range(Comparator<? super T> comparator, @NullableDecl T t, BoundType boundType, @NullableDecl T t2, BoundType boundType2) {
        return new GeneralRange<T>(comparator, true, (T)t, boundType, true, (T)t2, boundType2);
    }

    static <T> GeneralRange<T> upTo(Comparator<? super T> comparator, @NullableDecl T t, BoundType boundType) {
        return new GeneralRange<Object>((Comparator<Object>)comparator, false, null, BoundType.OPEN, true, t, boundType);
    }

    Comparator<? super T> comparator() {
        return this.comparator;
    }

    boolean contains(@NullableDecl T t) {
        if (this.tooLow(t)) return false;
        if (this.tooHigh(t)) return false;
        return true;
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof GeneralRange;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (GeneralRange)object;
        bl3 = bl;
        if (!this.comparator.equals(((GeneralRange)object).comparator)) return bl3;
        bl3 = bl;
        if (this.hasLowerBound != ((GeneralRange)object).hasLowerBound) return bl3;
        bl3 = bl;
        if (this.hasUpperBound != ((GeneralRange)object).hasUpperBound) return bl3;
        bl3 = bl;
        if (!this.getLowerBoundType().equals((Object)((GeneralRange)object).getLowerBoundType())) return bl3;
        bl3 = bl;
        if (!this.getUpperBoundType().equals((Object)((GeneralRange)object).getUpperBoundType())) return bl3;
        bl3 = bl;
        if (!Objects.equal(this.getLowerEndpoint(), ((GeneralRange)object).getLowerEndpoint())) return bl3;
        bl3 = bl;
        if (!Objects.equal(this.getUpperEndpoint(), ((GeneralRange)object).getUpperEndpoint())) return bl3;
        return true;
    }

    BoundType getLowerBoundType() {
        return this.lowerBoundType;
    }

    T getLowerEndpoint() {
        return this.lowerEndpoint;
    }

    BoundType getUpperBoundType() {
        return this.upperBoundType;
    }

    T getUpperEndpoint() {
        return this.upperEndpoint;
    }

    boolean hasLowerBound() {
        return this.hasLowerBound;
    }

    boolean hasUpperBound() {
        return this.hasUpperBound;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.comparator, this.getLowerEndpoint(), this.getLowerBoundType(), this.getUpperEndpoint(), this.getUpperBoundType()});
    }

    GeneralRange<T> intersect(GeneralRange<T> object) {
        T t;
        BoundType boundType;
        T t2;
        BoundType boundType2;
        boolean bl;
        boolean bl2;
        int n;
        block11 : {
            boolean bl3;
            block12 : {
                T t3;
                BoundType boundType3;
                block10 : {
                    block8 : {
                        block9 : {
                            block7 : {
                                Preconditions.checkNotNull(object);
                                Preconditions.checkArgument(this.comparator.equals(((GeneralRange)object).comparator));
                                bl = this.hasLowerBound;
                                t2 = this.getLowerEndpoint();
                                boundType = this.getLowerBoundType();
                                if (this.hasLowerBound()) break block7;
                                bl2 = ((GeneralRange)object).hasLowerBound;
                                t = ((GeneralRange)object).getLowerEndpoint();
                                boundType2 = ((GeneralRange)object).getLowerBoundType();
                                break block8;
                            }
                            bl2 = bl;
                            t = t2;
                            boundType2 = boundType;
                            if (!((GeneralRange)object).hasLowerBound()) break block8;
                            n = this.comparator.compare(this.getLowerEndpoint(), ((GeneralRange)object).getLowerEndpoint());
                            if (n < 0) break block9;
                            bl2 = bl;
                            t = t2;
                            boundType2 = boundType;
                            if (n != 0) break block8;
                            bl2 = bl;
                            t = t2;
                            boundType2 = boundType;
                            if (((GeneralRange)object).getLowerBoundType() != BoundType.OPEN) break block8;
                        }
                        t = ((GeneralRange)object).getLowerEndpoint();
                        boundType2 = ((GeneralRange)object).getLowerBoundType();
                        bl2 = bl;
                    }
                    bl3 = this.hasUpperBound;
                    t3 = this.getUpperEndpoint();
                    boundType3 = this.getUpperBoundType();
                    if (this.hasUpperBound()) break block10;
                    bl = ((GeneralRange)object).hasUpperBound;
                    t2 = ((GeneralRange)object).getUpperEndpoint();
                    boundType = ((GeneralRange)object).getUpperBoundType();
                    break block11;
                }
                bl = bl3;
                t2 = t3;
                boundType = boundType3;
                if (!((GeneralRange)object).hasUpperBound()) break block11;
                n = this.comparator.compare(this.getUpperEndpoint(), ((GeneralRange)object).getUpperEndpoint());
                if (n > 0) break block12;
                bl = bl3;
                t2 = t3;
                boundType = boundType3;
                if (n != 0) break block11;
                bl = bl3;
                t2 = t3;
                boundType = boundType3;
                if (((GeneralRange)object).getUpperBoundType() != BoundType.OPEN) break block11;
            }
            t2 = ((GeneralRange)object).getUpperEndpoint();
            boundType = ((GeneralRange)object).getUpperBoundType();
            bl = bl3;
        }
        if (bl2 && bl && ((n = this.comparator.compare(t, t2)) > 0 || n == 0 && boundType2 == BoundType.OPEN && boundType == BoundType.OPEN)) {
            boundType2 = BoundType.OPEN;
            object = BoundType.CLOSED;
            t = t2;
            return new GeneralRange<T>(this.comparator, bl2, (T)t, boundType2, bl, (T)t2, (BoundType)((Object)object));
        }
        object = boundType;
        return new GeneralRange<T>(this.comparator, bl2, (T)t, boundType2, bl, (T)t2, (BoundType)((Object)object));
    }

    boolean isEmpty() {
        if (this.hasUpperBound()) {
            if (this.tooLow(this.getUpperEndpoint())) return true;
        }
        if (!this.hasLowerBound()) return false;
        if (!this.tooHigh(this.getLowerEndpoint())) return false;
        return true;
    }

    GeneralRange<T> reverse() {
        GeneralRange<T> generalRange;
        GeneralRange<Object> generalRange2 = generalRange = this.reverse;
        if (generalRange != null) return generalRange2;
        generalRange2 = new GeneralRange(Ordering.from(this.comparator).reverse(), this.hasUpperBound, this.getUpperEndpoint(), this.getUpperBoundType(), this.hasLowerBound, this.getLowerEndpoint(), this.getLowerBoundType());
        generalRange2.reverse = this;
        this.reverse = generalRange2;
        return generalRange2;
    }

    public String toString() {
        char c;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.comparator);
        stringBuilder.append(":");
        char c2 = this.lowerBoundType == BoundType.CLOSED ? (c = '[') : (c = '(');
        stringBuilder.append(c2);
        String string2 = this.hasLowerBound ? this.lowerEndpoint : "-\u221e";
        stringBuilder.append((Object)string2);
        stringBuilder.append(',');
        string2 = this.hasUpperBound ? this.upperEndpoint : "\u221e";
        stringBuilder.append((Object)string2);
        c2 = this.upperBoundType == BoundType.CLOSED ? (c = ']') : (c = ')');
        stringBuilder.append(c2);
        return stringBuilder.toString();
    }

    boolean tooHigh(@NullableDecl T t) {
        boolean bl = this.hasUpperBound();
        int n = 0;
        if (!bl) {
            return false;
        }
        T t2 = this.getUpperEndpoint();
        int n2 = this.comparator.compare(t, t2);
        int n3 = n2 > 0 ? 1 : 0;
        n2 = n2 == 0 ? 1 : 0;
        if (this.getUpperBoundType() != BoundType.OPEN) return (n2 & n | n3) != 0;
        n = 1;
        return (n2 & n | n3) != 0;
    }

    boolean tooLow(@NullableDecl T t) {
        boolean bl = this.hasLowerBound();
        int n = 0;
        if (!bl) {
            return false;
        }
        T t2 = this.getLowerEndpoint();
        int n2 = this.comparator.compare(t, t2);
        int n3 = n2 < 0 ? 1 : 0;
        n2 = n2 == 0 ? 1 : 0;
        if (this.getLowerBoundType() != BoundType.OPEN) return (n2 & n | n3) != 0;
        n = 1;
        return (n2 & n | n3) != 0;
    }
}

