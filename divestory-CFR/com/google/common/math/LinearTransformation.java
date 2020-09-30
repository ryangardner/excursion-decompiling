/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.math.DoubleUtils;
import com.google.errorprone.annotations.concurrent.LazyInit;

public abstract class LinearTransformation {
    public static LinearTransformation forNaN() {
        return NaNLinearTransformation.INSTANCE;
    }

    public static LinearTransformation horizontal(double d) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d));
        return new RegularLinearTransformation(0.0, d);
    }

    public static LinearTransformationBuilder mapping(double d, double d2) {
        boolean bl = DoubleUtils.isFinite(d) && DoubleUtils.isFinite(d2);
        Preconditions.checkArgument(bl);
        return new LinearTransformationBuilder(d, d2);
    }

    public static LinearTransformation vertical(double d) {
        Preconditions.checkArgument(DoubleUtils.isFinite(d));
        return new VerticalLinearTransformation(d);
    }

    public abstract LinearTransformation inverse();

    public abstract boolean isHorizontal();

    public abstract boolean isVertical();

    public abstract double slope();

    public abstract double transform(double var1);

    public static final class LinearTransformationBuilder {
        private final double x1;
        private final double y1;

        private LinearTransformationBuilder(double d, double d2) {
            this.x1 = d;
            this.y1 = d2;
        }

        public LinearTransformation and(double d, double d2) {
            boolean bl = DoubleUtils.isFinite(d);
            boolean bl2 = true;
            bl = bl && DoubleUtils.isFinite(d2);
            Preconditions.checkArgument(bl);
            double d3 = this.x1;
            if (d != d3) return this.withSlope((d2 - this.y1) / (d - d3));
            bl = d2 != this.y1 ? bl2 : false;
            Preconditions.checkArgument(bl);
            return new VerticalLinearTransformation(this.x1);
        }

        public LinearTransformation withSlope(double d) {
            Preconditions.checkArgument(Double.isNaN(d) ^ true);
            if (!DoubleUtils.isFinite(d)) return new VerticalLinearTransformation(this.x1);
            return new RegularLinearTransformation(d, this.y1 - this.x1 * d);
        }
    }

    private static final class NaNLinearTransformation
    extends LinearTransformation {
        static final NaNLinearTransformation INSTANCE = new NaNLinearTransformation();

        private NaNLinearTransformation() {
        }

        @Override
        public LinearTransformation inverse() {
            return this;
        }

        @Override
        public boolean isHorizontal() {
            return false;
        }

        @Override
        public boolean isVertical() {
            return false;
        }

        @Override
        public double slope() {
            return Double.NaN;
        }

        public String toString() {
            return "NaN";
        }

        @Override
        public double transform(double d) {
            return Double.NaN;
        }
    }

    private static final class RegularLinearTransformation
    extends LinearTransformation {
        @LazyInit
        LinearTransformation inverse;
        final double slope;
        final double yIntercept;

        RegularLinearTransformation(double d, double d2) {
            this.slope = d;
            this.yIntercept = d2;
            this.inverse = null;
        }

        RegularLinearTransformation(double d, double d2, LinearTransformation linearTransformation) {
            this.slope = d;
            this.yIntercept = d2;
            this.inverse = linearTransformation;
        }

        private LinearTransformation createInverse() {
            if (this.slope == 0.0) return new VerticalLinearTransformation(this.yIntercept, this);
            double d = this.slope;
            return new RegularLinearTransformation(1.0 / d, this.yIntercept * -1.0 / d, this);
        }

        @Override
        public LinearTransformation inverse() {
            LinearTransformation linearTransformation;
            LinearTransformation linearTransformation2 = linearTransformation = this.inverse;
            if (linearTransformation != null) return linearTransformation2;
            this.inverse = linearTransformation2 = this.createInverse();
            return linearTransformation2;
        }

        @Override
        public boolean isHorizontal() {
            if (this.slope != 0.0) return false;
            return true;
        }

        @Override
        public boolean isVertical() {
            return false;
        }

        @Override
        public double slope() {
            return this.slope;
        }

        public String toString() {
            return String.format("y = %g * x + %g", this.slope, this.yIntercept);
        }

        @Override
        public double transform(double d) {
            return d * this.slope + this.yIntercept;
        }
    }

    private static final class VerticalLinearTransformation
    extends LinearTransformation {
        @LazyInit
        LinearTransformation inverse;
        final double x;

        VerticalLinearTransformation(double d) {
            this.x = d;
            this.inverse = null;
        }

        VerticalLinearTransformation(double d, LinearTransformation linearTransformation) {
            this.x = d;
            this.inverse = linearTransformation;
        }

        private LinearTransformation createInverse() {
            return new RegularLinearTransformation(0.0, this.x, this);
        }

        @Override
        public LinearTransformation inverse() {
            LinearTransformation linearTransformation;
            LinearTransformation linearTransformation2 = linearTransformation = this.inverse;
            if (linearTransformation != null) return linearTransformation2;
            this.inverse = linearTransformation2 = this.createInverse();
            return linearTransformation2;
        }

        @Override
        public boolean isHorizontal() {
            return false;
        }

        @Override
        public boolean isVertical() {
            return true;
        }

        @Override
        public double slope() {
            throw new IllegalStateException();
        }

        public String toString() {
            return String.format("x = %g", this.x);
        }

        @Override
        public double transform(double d) {
            throw new IllegalStateException();
        }
    }

}

