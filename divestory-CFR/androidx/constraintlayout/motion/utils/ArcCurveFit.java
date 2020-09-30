/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.motion.utils.CurveFit;
import java.util.Arrays;

class ArcCurveFit
extends CurveFit {
    public static final int ARC_START_FLIP = 3;
    public static final int ARC_START_HORIZONTAL = 2;
    public static final int ARC_START_LINEAR = 0;
    public static final int ARC_START_VERTICAL = 1;
    private static final int START_HORIZONTAL = 2;
    private static final int START_LINEAR = 3;
    private static final int START_VERTICAL = 1;
    Arc[] mArcs;
    private final double[] mTime;

    public ArcCurveFit(int[] arrn, double[] arrd, double[][] arrd2) {
        this.mTime = arrd;
        this.mArcs = new Arc[arrd.length - 1];
        int n = 0;
        int n2 = 1;
        int n3 = 1;
        while (n < this.mArcs.length) {
            int n4 = arrn[n];
            if (n4 != 0) {
                if (n4 != 1) {
                    if (n4 != 2) {
                        if (n4 == 3) {
                            n2 = n2 == 1 ? 2 : 1;
                            n3 = n2;
                        }
                    } else {
                        n2 = 2;
                        n3 = 2;
                    }
                } else {
                    n2 = 1;
                    n3 = 1;
                }
            } else {
                n3 = 3;
            }
            Arc[] arrarc = this.mArcs;
            double d = arrd[n];
            n4 = n + 1;
            arrarc[n] = new Arc(n3, d, arrd[n4], arrd2[n][0], arrd2[n][1], arrd2[n4][0], arrd2[n4][1]);
            n = n4;
        }
    }

    @Override
    public double getPos(double d, int n) {
        double d2;
        int n2;
        block6 : {
            Arc[] arrarc = this.mArcs;
            int n3 = 0;
            if (d < arrarc[0].mTime1) {
                d2 = this.mArcs[0].mTime1;
                n2 = n3;
            } else {
                arrarc = this.mArcs;
                n2 = n3;
                d2 = d;
                if (d > arrarc[arrarc.length - 1].mTime2) {
                    arrarc = this.mArcs;
                    d2 = arrarc[arrarc.length - 1].mTime2;
                    n2 = n3;
                }
            }
            while (n2 < (arrarc = this.mArcs).length) {
                if (d2 <= arrarc[n2].mTime2) {
                    if (this.mArcs[n2].linear) {
                        if (n != 0) return this.mArcs[n2].getLinearY(d2);
                        return this.mArcs[n2].getLinearX(d2);
                    }
                    break block6;
                }
                ++n2;
            }
            return Double.NaN;
        }
        this.mArcs[n2].setPoint(d2);
        if (n != 0) return this.mArcs[n2].getY();
        return this.mArcs[n2].getX();
    }

    @Override
    public void getPos(double d, double[] arrd) {
        int n;
        block5 : {
            double d2 = d;
            if (d < this.mArcs[0].mTime1) {
                d2 = this.mArcs[0].mTime1;
            }
            Arc[] arrarc = this.mArcs;
            d = d2;
            if (d2 > arrarc[arrarc.length - 1].mTime2) {
                arrarc = this.mArcs;
                d = arrarc[arrarc.length - 1].mTime2;
            }
            n = 0;
            while (n < (arrarc = this.mArcs).length) {
                if (d <= arrarc[n].mTime2) {
                    if (this.mArcs[n].linear) {
                        arrd[0] = this.mArcs[n].getLinearX(d);
                        arrd[1] = this.mArcs[n].getLinearY(d);
                        return;
                    }
                    break block5;
                }
                ++n;
            }
            return;
        }
        this.mArcs[n].setPoint(d);
        arrd[0] = this.mArcs[n].getX();
        arrd[1] = this.mArcs[n].getY();
    }

    @Override
    public void getPos(double d, float[] arrf) {
        int n;
        double d2;
        block6 : {
            Arc[] arrarc;
            if (d < this.mArcs[0].mTime1) {
                d2 = this.mArcs[0].mTime1;
            } else {
                arrarc = this.mArcs;
                d2 = d;
                if (d > arrarc[arrarc.length - 1].mTime2) {
                    arrarc = this.mArcs;
                    d2 = arrarc[arrarc.length - 1].mTime2;
                }
            }
            n = 0;
            while (n < (arrarc = this.mArcs).length) {
                if (d2 <= arrarc[n].mTime2) {
                    if (this.mArcs[n].linear) {
                        arrf[0] = (float)this.mArcs[n].getLinearX(d2);
                        arrf[1] = (float)this.mArcs[n].getLinearY(d2);
                        return;
                    }
                    break block6;
                }
                ++n;
            }
            return;
        }
        this.mArcs[n].setPoint(d2);
        arrf[0] = (float)this.mArcs[n].getX();
        arrf[1] = (float)this.mArcs[n].getY();
    }

    @Override
    public double getSlope(double d, int n) {
        int n2;
        block5 : {
            Arc[] arrarc = this.mArcs;
            int n3 = 0;
            double d2 = d;
            if (d < arrarc[0].mTime1) {
                d2 = this.mArcs[0].mTime1;
            }
            arrarc = this.mArcs;
            n2 = n3;
            d = d2;
            if (d2 > arrarc[arrarc.length - 1].mTime2) {
                arrarc = this.mArcs;
                d = arrarc[arrarc.length - 1].mTime2;
                n2 = n3;
            }
            while (n2 < (arrarc = this.mArcs).length) {
                if (d <= arrarc[n2].mTime2) {
                    if (this.mArcs[n2].linear) {
                        if (n != 0) return this.mArcs[n2].getLinearDY(d);
                        return this.mArcs[n2].getLinearDX(d);
                    }
                    break block5;
                }
                ++n2;
            }
            return Double.NaN;
        }
        this.mArcs[n2].setPoint(d);
        if (n != 0) return this.mArcs[n2].getDY();
        return this.mArcs[n2].getDX();
    }

    @Override
    public void getSlope(double d, double[] arrd) {
        int n;
        double d2;
        block6 : {
            Arc[] arrarc;
            if (d < this.mArcs[0].mTime1) {
                d2 = this.mArcs[0].mTime1;
            } else {
                arrarc = this.mArcs;
                d2 = d;
                if (d > arrarc[arrarc.length - 1].mTime2) {
                    arrarc = this.mArcs;
                    d2 = arrarc[arrarc.length - 1].mTime2;
                }
            }
            n = 0;
            while (n < (arrarc = this.mArcs).length) {
                if (d2 <= arrarc[n].mTime2) {
                    if (this.mArcs[n].linear) {
                        arrd[0] = this.mArcs[n].getLinearDX(d2);
                        arrd[1] = this.mArcs[n].getLinearDY(d2);
                        return;
                    }
                    break block6;
                }
                ++n;
            }
            return;
        }
        this.mArcs[n].setPoint(d2);
        arrd[0] = this.mArcs[n].getDX();
        arrd[1] = this.mArcs[n].getDY();
    }

    @Override
    public double[] getTimePoints() {
        return this.mTime;
    }

    private static class Arc {
        private static final double EPSILON = 0.001;
        private static final String TAG = "Arc";
        private static double[] ourPercent = new double[91];
        boolean linear;
        double mArcDistance;
        double mArcVelocity;
        double mEllipseA;
        double mEllipseB;
        double mEllipseCenterX;
        double mEllipseCenterY;
        double[] mLut;
        double mOneOverDeltaTime;
        double mTime1;
        double mTime2;
        double mTmpCosAngle;
        double mTmpSinAngle;
        boolean mVertical;
        double mX1;
        double mX2;
        double mY1;
        double mY2;

        Arc(int n, double d, double d2, double d3, double d4, double d5, double d6) {
            boolean bl = false;
            this.linear = false;
            if (n == 1) {
                bl = true;
            }
            this.mVertical = bl;
            this.mTime1 = d;
            this.mTime2 = d2;
            this.mOneOverDeltaTime = 1.0 / (d2 - d);
            if (3 == n) {
                this.linear = true;
            }
            d2 = d5 - d3;
            d = d6 - d4;
            if (!(this.linear || Math.abs(d2) < 0.001 || Math.abs(d) < 0.001)) {
                this.mLut = new double[101];
                n = this.mVertical ? -1 : 1;
                this.mEllipseA = d2 * (double)n;
                n = this.mVertical ? 1 : -1;
                this.mEllipseB = d * (double)n;
                d = this.mVertical ? d5 : d3;
                this.mEllipseCenterX = d;
                d = this.mVertical ? d4 : d6;
                this.mEllipseCenterY = d;
                this.buildTable(d3, d4, d5, d6);
                this.mArcVelocity = this.mArcDistance * this.mOneOverDeltaTime;
                return;
            }
            this.linear = true;
            this.mX1 = d3;
            this.mX2 = d5;
            this.mY1 = d4;
            this.mY2 = d6;
            this.mArcDistance = d3 = Math.hypot(d, d2);
            this.mArcVelocity = d3 * this.mOneOverDeltaTime;
            d3 = this.mTime2;
            d4 = this.mTime1;
            this.mEllipseCenterX = d2 / (d3 - d4);
            this.mEllipseCenterY = d / (d3 - d4);
        }

        private void buildTable(double d, double d2, double d3, double d4) {
            double[] arrd;
            int n;
            double d5 = 0.0;
            double d6 = 0.0;
            double d7 = 0.0;
            for (n = 0; n < (arrd = ourPercent).length; ++n) {
                double d8 = Math.toRadians((double)n * 90.0 / (double)(arrd.length - 1));
                double d9 = Math.sin(d8);
                d8 = Math.cos(d8);
                d9 *= d3 - d;
                d8 *= d2 - d4;
                if (n > 0) {
                    Arc.ourPercent[n] = d5 += Math.hypot(d9 - d6, d8 - d7);
                }
                d7 = d8;
                d6 = d9;
            }
            this.mArcDistance = d5;
            for (n = 0; n < (arrd = ourPercent).length; ++n) {
                arrd[n] = arrd[n] / d5;
            }
            n = 0;
            while (n < (arrd = this.mLut).length) {
                d = (double)n / (double)(arrd.length - 1);
                int n2 = Arrays.binarySearch(ourPercent, d);
                if (n2 >= 0) {
                    this.mLut[n] = n2 / (ourPercent.length - 1);
                } else if (n2 == -1) {
                    this.mLut[n] = 0.0;
                } else {
                    n2 = -n2;
                    int n3 = n2 - 2;
                    d2 = n3;
                    arrd = ourPercent;
                    this.mLut[n] = d = (d2 + (d - arrd[n3]) / (arrd[n2 - 1] - arrd[n3])) / (double)(arrd.length - 1);
                }
                ++n;
            }
        }

        double getDX() {
            double d = this.mEllipseA * this.mTmpCosAngle;
            double d2 = -this.mEllipseB;
            double d3 = this.mTmpSinAngle;
            d3 = this.mArcVelocity / Math.hypot(d, d2 * d3);
            d2 = d;
            if (!this.mVertical) return d2 * d3;
            d2 = -d;
            return d2 * d3;
        }

        double getDY() {
            double d = this.mEllipseA;
            double d2 = this.mTmpCosAngle;
            double d3 = -this.mEllipseB * this.mTmpSinAngle;
            d = this.mArcVelocity / Math.hypot(d * d2, d3);
            if (this.mVertical) {
                return -d3 * d;
            }
            d3 *= d;
            return d3;
        }

        public double getLinearDX(double d) {
            return this.mEllipseCenterX;
        }

        public double getLinearDY(double d) {
            return this.mEllipseCenterY;
        }

        public double getLinearX(double d) {
            double d2 = this.mTime1;
            double d3 = this.mOneOverDeltaTime;
            double d4 = this.mX1;
            return d4 + (d - d2) * d3 * (this.mX2 - d4);
        }

        public double getLinearY(double d) {
            double d2 = this.mTime1;
            double d3 = this.mOneOverDeltaTime;
            double d4 = this.mY1;
            return d4 + (d - d2) * d3 * (this.mY2 - d4);
        }

        double getX() {
            return this.mEllipseCenterX + this.mEllipseA * this.mTmpSinAngle;
        }

        double getY() {
            return this.mEllipseCenterY + this.mEllipseB * this.mTmpCosAngle;
        }

        double lookup(double d) {
            if (d <= 0.0) {
                return 0.0;
            }
            if (d >= 1.0) {
                return 1.0;
            }
            double[] arrd = this.mLut;
            double d2 = d * (double)(arrd.length - 1);
            int n = (int)d2;
            d = n;
            return arrd[n] + (d2 - d) * (arrd[n + 1] - arrd[n]);
        }

        void setPoint(double d) {
            d = this.mVertical ? this.mTime2 - d : (d -= this.mTime1);
            d = this.lookup(d * this.mOneOverDeltaTime) * 1.5707963267948966;
            this.mTmpSinAngle = Math.sin(d);
            this.mTmpCosAngle = Math.cos(d);
        }
    }

}

