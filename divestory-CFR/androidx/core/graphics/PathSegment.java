/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.PointF
 */
package androidx.core.graphics;

import android.graphics.PointF;
import androidx.core.util.Preconditions;

public final class PathSegment {
    private final PointF mEnd;
    private final float mEndFraction;
    private final PointF mStart;
    private final float mStartFraction;

    public PathSegment(PointF pointF, float f, PointF pointF2, float f2) {
        this.mStart = Preconditions.checkNotNull(pointF, "start == null");
        this.mStartFraction = f;
        this.mEnd = Preconditions.checkNotNull(pointF2, "end == null");
        this.mEndFraction = f2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof PathSegment)) {
            return false;
        }
        object = (PathSegment)object;
        if (Float.compare(this.mStartFraction, ((PathSegment)object).mStartFraction) != 0) return false;
        if (Float.compare(this.mEndFraction, ((PathSegment)object).mEndFraction) != 0) return false;
        if (!this.mStart.equals((Object)((PathSegment)object).mStart)) return false;
        if (!this.mEnd.equals((Object)((PathSegment)object).mEnd)) return false;
        return bl;
    }

    public PointF getEnd() {
        return this.mEnd;
    }

    public float getEndFraction() {
        return this.mEndFraction;
    }

    public PointF getStart() {
        return this.mStart;
    }

    public float getStartFraction() {
        return this.mStartFraction;
    }

    public int hashCode() {
        int n = this.mStart.hashCode();
        float f = this.mStartFraction;
        int n2 = 0;
        int n3 = f != 0.0f ? Float.floatToIntBits(f) : 0;
        int n4 = this.mEnd.hashCode();
        f = this.mEndFraction;
        if (f == 0.0f) return ((n * 31 + n3) * 31 + n4) * 31 + n2;
        n2 = Float.floatToIntBits(f);
        return ((n * 31 + n3) * 31 + n4) * 31 + n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PathSegment{start=");
        stringBuilder.append((Object)this.mStart);
        stringBuilder.append(", startFraction=");
        stringBuilder.append(this.mStartFraction);
        stringBuilder.append(", end=");
        stringBuilder.append((Object)this.mEnd);
        stringBuilder.append(", endFraction=");
        stringBuilder.append(this.mEndFraction);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

