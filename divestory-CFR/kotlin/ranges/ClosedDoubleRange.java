/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.ranges;

import kotlin.Metadata;
import kotlin.ranges.ClosedFloatingPointRange;

@Metadata(bv={1, 0, 3}, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0002H\u0096\u0002J\u0013\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\fH\u0016J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0002H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0019"}, d2={"Lkotlin/ranges/ClosedDoubleRange;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "start", "endInclusive", "(DD)V", "_endInclusive", "_start", "getEndInclusive", "()Ljava/lang/Double;", "getStart", "contains", "", "value", "equals", "other", "", "hashCode", "", "isEmpty", "lessThanOrEquals", "a", "b", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
final class ClosedDoubleRange
implements ClosedFloatingPointRange<Double> {
    private final double _endInclusive;
    private final double _start;

    public ClosedDoubleRange(double d, double d2) {
        this._start = d;
        this._endInclusive = d2;
    }

    @Override
    public boolean contains(double d) {
        if (!(d >= this._start)) return false;
        if (!(d <= this._endInclusive)) return false;
        return true;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ClosedDoubleRange)) return false;
        if (this.isEmpty()) {
            if (((ClosedDoubleRange)object).isEmpty()) return true;
        }
        double d = this._start;
        object = (ClosedDoubleRange)object;
        if (d != ((ClosedDoubleRange)object)._start) return false;
        if (this._endInclusive != ((ClosedDoubleRange)object)._endInclusive) return false;
        return true;
    }

    @Override
    public Double getEndInclusive() {
        return this._endInclusive;
    }

    @Override
    public Double getStart() {
        return this._start;
    }

    public int hashCode() {
        if (!this.isEmpty()) return ((Object)this._start).hashCode() * 31 + ((Object)this._endInclusive).hashCode();
        return -1;
    }

    @Override
    public boolean isEmpty() {
        if (this._start <= this._endInclusive) return false;
        return true;
    }

    @Override
    public boolean lessThanOrEquals(double d, double d2) {
        if (!(d <= d2)) return false;
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._start);
        stringBuilder.append("..");
        stringBuilder.append(this._endInclusive);
        return stringBuilder.toString();
    }
}

