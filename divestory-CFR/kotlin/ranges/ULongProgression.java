/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.ranges;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.collections.ULongIterator;
import kotlin.internal.UProgressionUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.ULongProgressionIterator;

@Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0017\u0018\u0000 \u001a2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001aB\"\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0010H\u0016J\t\u0010\u0016\u001a\u00020\u0017H\u0096\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0016\u0010\b\u001a\u00020\u0002\u00f8\u0001\u0000\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0016\u0010\f\u001a\u00020\u0002\u00f8\u0001\u0000\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001b"}, d2={"Lkotlin/ranges/ULongProgression;", "", "Lkotlin/ULong;", "start", "endInclusive", "step", "", "(JJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "first", "getFirst", "()J", "J", "last", "getLast", "getStep", "equals", "", "other", "", "hashCode", "", "isEmpty", "iterator", "Lkotlin/collections/ULongIterator;", "toString", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public class ULongProgression
implements Iterable<ULong>,
KMappedMarker {
    public static final Companion Companion = new Companion(null);
    private final long first;
    private final long last;
    private final long step;

    private ULongProgression(long l, long l2, long l3) {
        if (l3 == 0L) throw (Throwable)new IllegalArgumentException("Step must be non-zero.");
        if (l3 == Long.MIN_VALUE) throw (Throwable)new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation.");
        this.first = l;
        this.last = UProgressionUtilKt.getProgressionLastElement-7ftBX0g(l, l2, l3);
        this.step = l3;
    }

    public /* synthetic */ ULongProgression(long l, long l2, long l3, DefaultConstructorMarker defaultConstructorMarker) {
        this(l, l2, l3);
    }

    public boolean equals(Object object) {
        if (!(object instanceof ULongProgression)) return false;
        if (this.isEmpty()) {
            if (((ULongProgression)object).isEmpty()) return true;
        }
        long l = this.first;
        object = (ULongProgression)object;
        if (l != ((ULongProgression)object).first) return false;
        if (this.last != ((ULongProgression)object).last) return false;
        if (this.step != ((ULongProgression)object).step) return false;
        return true;
    }

    public final long getFirst() {
        return this.first;
    }

    public final long getLast() {
        return this.last;
    }

    public final long getStep() {
        return this.step;
    }

    public int hashCode() {
        if (this.isEmpty()) {
            return -1;
        }
        long l = this.first;
        int n = (int)ULong.constructor-impl(l ^ ULong.constructor-impl(l >>> 32));
        l = this.last;
        int n2 = (int)ULong.constructor-impl(l ^ ULong.constructor-impl(l >>> 32));
        l = this.step;
        return (int)(l ^ l >>> 32) + (n * 31 + n2) * 31;
    }

    public boolean isEmpty() {
        long l = this.step;
        boolean bl = true;
        int n = UnsignedKt.ulongCompare(this.first, this.last);
        if (l > 0L) {
            if (n <= 0) return false;
            return bl;
        }
        if (n >= 0) return false;
        return bl;
    }

    public ULongIterator iterator() {
        return new ULongProgressionIterator(this.first, this.last, this.step, null);
    }

    public String toString() {
        StringBuilder stringBuilder;
        long l = this.step;
        if (l > 0L) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(ULong.toString-impl(this.first));
            stringBuilder.append("..");
            stringBuilder.append(ULong.toString-impl(this.last));
            stringBuilder.append(" step ");
            l = this.step;
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(ULong.toString-impl(this.first));
            stringBuilder.append(" downTo ");
            stringBuilder.append(ULong.toString-impl(this.last));
            stringBuilder.append(" step ");
            l = -this.step;
        }
        stringBuilder.append(l);
        return stringBuilder.toString();
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(bv={1, 0, 3}, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2={"Lkotlin/ranges/ULongProgression$Companion;", "", "()V", "fromClosedRange", "Lkotlin/ranges/ULongProgression;", "rangeStart", "Lkotlin/ULong;", "rangeEnd", "step", "", "fromClosedRange-7ftBX0g", "(JJJ)Lkotlin/ranges/ULongProgression;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ULongProgression fromClosedRange-7ftBX0g(long l, long l2, long l3) {
            return new ULongProgression(l, l2, l3, null);
        }
    }

}

