/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common.reedsolomon;

import com.google.zxing.common.reedsolomon.GenericGF;

final class GenericGFPoly {
    private final int[] coefficients;
    private final GenericGF field;

    GenericGFPoly(GenericGF arrn, int[] arrn2) {
        if (arrn2.length == 0) throw new IllegalArgumentException();
        this.field = arrn;
        int n = arrn2.length;
        if (n > 1 && arrn2[0] == 0) {
            int n2;
            for (n2 = 1; n2 < n && arrn2[n2] == 0; ++n2) {
            }
            if (n2 == n) {
                this.coefficients = new int[]{0};
                return;
            }
            arrn = new int[n - n2];
            this.coefficients = arrn;
            System.arraycopy(arrn2, n2, arrn, 0, arrn.length);
            return;
        }
        this.coefficients = arrn2;
    }

    GenericGFPoly addOrSubtract(GenericGFPoly arrn) {
        if (!this.field.equals(arrn.field)) throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        if (this.isZero()) {
            return arrn;
        }
        if (arrn.isZero()) {
            return this;
        }
        int[] arrn2 = this.coefficients;
        int[] arrn3 = arrn.coefficients;
        if (arrn2.length > arrn3.length) {
            arrn = arrn2;
            arrn2 = arrn3;
        } else {
            arrn = arrn3;
        }
        arrn3 = new int[arrn.length];
        int n = arrn.length - arrn2.length;
        System.arraycopy(arrn, 0, arrn3, 0, n);
        int n2 = n;
        while (n2 < arrn.length) {
            arrn3[n2] = GenericGF.addOrSubtract(arrn2[n2 - n], arrn[n2]);
            ++n2;
        }
        return new GenericGFPoly(this.field, arrn3);
    }

    GenericGFPoly[] divide(GenericGFPoly genericGFPoly) {
        if (!this.field.equals(genericGFPoly.field)) throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        if (genericGFPoly.isZero()) throw new IllegalArgumentException("Divide by 0");
        GenericGFPoly genericGFPoly2 = this.field.getZero();
        int n = genericGFPoly.getCoefficient(genericGFPoly.getDegree());
        int n2 = this.field.inverse(n);
        GenericGFPoly genericGFPoly3 = this;
        while (genericGFPoly3.getDegree() >= genericGFPoly.getDegree() && !genericGFPoly3.isZero()) {
            int n3 = genericGFPoly3.getDegree() - genericGFPoly.getDegree();
            n = this.field.multiply(genericGFPoly3.getCoefficient(genericGFPoly3.getDegree()), n2);
            GenericGFPoly genericGFPoly4 = genericGFPoly.multiplyByMonomial(n3, n);
            genericGFPoly2 = genericGFPoly2.addOrSubtract(this.field.buildMonomial(n3, n));
            genericGFPoly3 = genericGFPoly3.addOrSubtract(genericGFPoly4);
        }
        return new GenericGFPoly[]{genericGFPoly2, genericGFPoly3};
    }

    int evaluateAt(int n) {
        int n2 = 0;
        if (n == 0) {
            return this.getCoefficient(0);
        }
        int[] arrn = this.coefficients;
        int n3 = arrn.length;
        int n4 = 1;
        if (n == 1) {
            n4 = arrn.length;
            n = 0;
            while (n2 < n4) {
                n = GenericGF.addOrSubtract(n, arrn[n2]);
                ++n2;
            }
            return n;
        }
        int n5 = arrn[0];
        n2 = n4;
        n4 = n5;
        while (n2 < n3) {
            n4 = GenericGF.addOrSubtract(this.field.multiply(n, n4), this.coefficients[n2]);
            ++n2;
        }
        return n4;
    }

    int getCoefficient(int n) {
        int[] arrn = this.coefficients;
        return arrn[arrn.length - 1 - n];
    }

    int[] getCoefficients() {
        return this.coefficients;
    }

    int getDegree() {
        return this.coefficients.length - 1;
    }

    boolean isZero() {
        int[] arrn = this.coefficients;
        boolean bl = false;
        if (arrn[0] != 0) return bl;
        return true;
    }

    GenericGFPoly multiply(int n) {
        if (n == 0) {
            return this.field.getZero();
        }
        if (n == 1) {
            return this;
        }
        int n2 = this.coefficients.length;
        int[] arrn = new int[n2];
        int n3 = 0;
        while (n3 < n2) {
            arrn[n3] = this.field.multiply(this.coefficients[n3], n);
            ++n3;
        }
        return new GenericGFPoly(this.field, arrn);
    }

    GenericGFPoly multiply(GenericGFPoly arrn) {
        if (!this.field.equals(arrn.field)) throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        if (this.isZero()) return this.field.getZero();
        if (arrn.isZero()) {
            return this.field.getZero();
        }
        int[] arrn2 = this.coefficients;
        int n = arrn2.length;
        int[] arrn3 = arrn.coefficients;
        int n2 = arrn3.length;
        arrn = new int[n + n2 - 1];
        int n3 = 0;
        while (n3 < n) {
            int n4 = arrn2[n3];
            for (int i = 0; i < n2; ++i) {
                int n5 = n3 + i;
                arrn[n5] = GenericGF.addOrSubtract(arrn[n5], this.field.multiply(n4, arrn3[i]));
            }
            ++n3;
        }
        return new GenericGFPoly(this.field, arrn);
    }

    GenericGFPoly multiplyByMonomial(int n, int n2) {
        if (n < 0) throw new IllegalArgumentException();
        if (n2 == 0) {
            return this.field.getZero();
        }
        int n3 = this.coefficients.length;
        int[] arrn = new int[n + n3];
        n = 0;
        while (n < n3) {
            arrn[n] = this.field.multiply(this.coefficients[n], n2);
            ++n;
        }
        return new GenericGFPoly(this.field, arrn);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.getDegree() * 8);
        int n = this.getDegree();
        while (n >= 0) {
            int n2 = this.getCoefficient(n);
            if (n2 != 0) {
                int n3;
                if (n2 < 0) {
                    stringBuilder.append(" - ");
                    n3 = -n2;
                } else {
                    n3 = n2;
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(" + ");
                        n3 = n2;
                    }
                }
                if (n == 0 || n3 != 1) {
                    if ((n3 = this.field.log(n3)) == 0) {
                        stringBuilder.append('1');
                    } else if (n3 == 1) {
                        stringBuilder.append('a');
                    } else {
                        stringBuilder.append("a^");
                        stringBuilder.append(n3);
                    }
                }
                if (n != 0) {
                    if (n == 1) {
                        stringBuilder.append('x');
                    } else {
                        stringBuilder.append("x^");
                        stringBuilder.append(n);
                    }
                }
            }
            --n;
        }
        return stringBuilder.toString();
    }
}

