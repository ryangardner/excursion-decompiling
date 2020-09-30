/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.pdf417.decoder.ec.ModulusGF;

final class ModulusPoly {
    private final int[] coefficients;
    private final ModulusGF field;

    ModulusPoly(ModulusGF arrn, int[] arrn2) {
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

    ModulusPoly add(ModulusPoly arrn) {
        int[] arrn2;
        if (!this.field.equals(arrn.field)) throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        if (this.isZero()) {
            return arrn;
        }
        if (arrn.isZero()) {
            return this;
        }
        int[] arrn3 = this.coefficients;
        arrn = arrn.coefficients;
        if (arrn3.length <= arrn.length) {
            arrn2 = arrn;
            arrn = arrn3;
            arrn3 = arrn2;
        }
        arrn2 = new int[arrn3.length];
        int n = arrn3.length - arrn.length;
        System.arraycopy(arrn3, 0, arrn2, 0, n);
        int n2 = n;
        while (n2 < arrn3.length) {
            arrn2[n2] = this.field.add(arrn[n2 - n], arrn3[n2]);
            ++n2;
        }
        return new ModulusPoly(this.field, arrn2);
    }

    ModulusPoly[] divide(ModulusPoly modulusPoly) {
        if (!this.field.equals(modulusPoly.field)) throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        if (modulusPoly.isZero()) throw new IllegalArgumentException("Divide by 0");
        ModulusPoly modulusPoly2 = this.field.getZero();
        int n = modulusPoly.getCoefficient(modulusPoly.getDegree());
        int n2 = this.field.inverse(n);
        ModulusPoly modulusPoly3 = this;
        while (modulusPoly3.getDegree() >= modulusPoly.getDegree() && !modulusPoly3.isZero()) {
            int n3 = modulusPoly3.getDegree() - modulusPoly.getDegree();
            n = this.field.multiply(modulusPoly3.getCoefficient(modulusPoly3.getDegree()), n2);
            ModulusPoly modulusPoly4 = modulusPoly.multiplyByMonomial(n3, n);
            modulusPoly2 = modulusPoly2.add(this.field.buildMonomial(n3, n));
            modulusPoly3 = modulusPoly3.subtract(modulusPoly4);
        }
        return new ModulusPoly[]{modulusPoly2, modulusPoly3};
    }

    int evaluateAt(int n) {
        int n2 = 0;
        if (n == 0) {
            return this.getCoefficient(0);
        }
        Object object = this.coefficients;
        int n3 = ((int[])object).length;
        int n4 = 1;
        if (n == 1) {
            n3 = ((int[])object).length;
            n4 = 0;
            n = n2;
            while (n < n3) {
                n2 = object[n];
                n4 = this.field.add(n4, n2);
                ++n;
            }
            return n4;
        }
        n2 = object[0];
        while (n4 < n3) {
            object = this.field;
            n2 = ((ModulusGF)object).add(((ModulusGF)object).multiply(n, n2), this.coefficients[n4]);
            ++n4;
        }
        return n2;
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

    ModulusPoly multiply(int n) {
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
        return new ModulusPoly(this.field, arrn);
    }

    ModulusPoly multiply(ModulusPoly object) {
        if (!this.field.equals(((ModulusPoly)object).field)) throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        if (this.isZero()) return this.field.getZero();
        if (((ModulusPoly)object).isZero()) {
            return this.field.getZero();
        }
        int[] arrn = this.coefficients;
        int n = arrn.length;
        int[] arrn2 = ((ModulusPoly)object).coefficients;
        int n2 = arrn2.length;
        int[] arrn3 = new int[n + n2 - 1];
        int n3 = 0;
        while (n3 < n) {
            int n4 = arrn[n3];
            for (int i = 0; i < n2; ++i) {
                int n5 = n3 + i;
                object = this.field;
                arrn3[n5] = ((ModulusGF)object).add(arrn3[n5], ((ModulusGF)object).multiply(n4, arrn2[i]));
            }
            ++n3;
        }
        return new ModulusPoly(this.field, arrn3);
    }

    ModulusPoly multiplyByMonomial(int n, int n2) {
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
        return new ModulusPoly(this.field, arrn);
    }

    ModulusPoly negative() {
        int n = this.coefficients.length;
        int[] arrn = new int[n];
        int n2 = 0;
        while (n2 < n) {
            arrn[n2] = this.field.subtract(0, this.coefficients[n2]);
            ++n2;
        }
        return new ModulusPoly(this.field, arrn);
    }

    ModulusPoly subtract(ModulusPoly modulusPoly) {
        if (!this.field.equals(modulusPoly.field)) throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        if (!modulusPoly.isZero()) return this.add(modulusPoly.negative());
        return this;
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
                    stringBuilder.append(n3);
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

