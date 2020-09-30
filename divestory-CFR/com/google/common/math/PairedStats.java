/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.math;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.LinearTransformation;
import com.google.common.math.Stats;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class PairedStats
implements Serializable {
    private static final int BYTES = 88;
    private static final long serialVersionUID = 0L;
    private final double sumOfProductsOfDeltas;
    private final Stats xStats;
    private final Stats yStats;

    PairedStats(Stats stats, Stats stats2, double d) {
        this.xStats = stats;
        this.yStats = stats2;
        this.sumOfProductsOfDeltas = d;
    }

    private static double ensureInUnitRange(double d) {
        if (d >= 1.0) {
            return 1.0;
        }
        if (!(d <= -1.0)) return d;
        return -1.0;
    }

    private static double ensurePositive(double d) {
        if (!(d > 0.0)) return Double.MIN_VALUE;
        return d;
    }

    public static PairedStats fromByteArray(byte[] object) {
        Preconditions.checkNotNull(object);
        boolean bl = ((byte[])object).length == 88;
        Preconditions.checkArgument(bl, "Expected PairedStats.BYTES = %s, got %s", 88, ((byte[])object).length);
        object = ByteBuffer.wrap((byte[])object).order(ByteOrder.LITTLE_ENDIAN);
        return new PairedStats(Stats.readFrom((ByteBuffer)object), Stats.readFrom((ByteBuffer)object), ((ByteBuffer)object).getDouble());
    }

    public long count() {
        return this.xStats.count();
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (PairedStats)object;
        boolean bl2 = bl;
        if (!this.xStats.equals(((PairedStats)object).xStats)) return bl2;
        bl2 = bl;
        if (!this.yStats.equals(((PairedStats)object).yStats)) return bl2;
        bl2 = bl;
        if (Double.doubleToLongBits(this.sumOfProductsOfDeltas) != Double.doubleToLongBits(((PairedStats)object).sumOfProductsOfDeltas)) return bl2;
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.xStats, this.yStats, this.sumOfProductsOfDeltas);
    }

    public LinearTransformation leastSquaresFit() {
        long l = this.count();
        boolean bl = true;
        boolean bl2 = l > 1L;
        Preconditions.checkState(bl2);
        if (Double.isNaN(this.sumOfProductsOfDeltas)) {
            return LinearTransformation.forNaN();
        }
        double d = this.xStats.sumOfSquaresOfDeltas();
        if (d > 0.0) {
            if (!(this.yStats.sumOfSquaresOfDeltas() > 0.0)) return LinearTransformation.horizontal(this.yStats.mean());
            return LinearTransformation.mapping(this.xStats.mean(), this.yStats.mean()).withSlope(this.sumOfProductsOfDeltas / d);
        }
        bl2 = this.yStats.sumOfSquaresOfDeltas() > 0.0 ? bl : false;
        Preconditions.checkState(bl2);
        return LinearTransformation.vertical(this.xStats.mean());
    }

    public double pearsonsCorrelationCoefficient() {
        long l = this.count();
        boolean bl = true;
        boolean bl2 = l > 1L;
        Preconditions.checkState(bl2);
        if (Double.isNaN(this.sumOfProductsOfDeltas)) {
            return Double.NaN;
        }
        double d = this.xStats().sumOfSquaresOfDeltas();
        double d2 = this.yStats().sumOfSquaresOfDeltas();
        bl2 = d > 0.0;
        Preconditions.checkState(bl2);
        bl2 = d2 > 0.0 ? bl : false;
        Preconditions.checkState(bl2);
        d2 = PairedStats.ensurePositive(d * d2);
        return PairedStats.ensureInUnitRange(this.sumOfProductsOfDeltas / Math.sqrt(d2));
    }

    public double populationCovariance() {
        boolean bl = this.count() != 0L;
        Preconditions.checkState(bl);
        return this.sumOfProductsOfDeltas / (double)this.count();
    }

    public double sampleCovariance() {
        boolean bl = this.count() > 1L;
        Preconditions.checkState(bl);
        return this.sumOfProductsOfDeltas / (double)(this.count() - 1L);
    }

    double sumOfProductsOfDeltas() {
        return this.sumOfProductsOfDeltas;
    }

    public byte[] toByteArray() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(88).order(ByteOrder.LITTLE_ENDIAN);
        this.xStats.writeTo(byteBuffer);
        this.yStats.writeTo(byteBuffer);
        byteBuffer.putDouble(this.sumOfProductsOfDeltas);
        return byteBuffer.array();
    }

    public String toString() {
        if (this.count() <= 0L) return MoreObjects.toStringHelper(this).add("xStats", this.xStats).add("yStats", this.yStats).toString();
        return MoreObjects.toStringHelper(this).add("xStats", this.xStats).add("yStats", this.yStats).add("populationCovariance", this.populationCovariance()).toString();
    }

    public Stats xStats() {
        return this.xStats;
    }

    public Stats yStats() {
        return this.yStats;
    }
}

