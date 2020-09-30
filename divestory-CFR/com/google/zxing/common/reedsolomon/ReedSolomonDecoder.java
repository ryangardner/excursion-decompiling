/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common.reedsolomon;

import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.GenericGFPoly;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class ReedSolomonDecoder {
    private final GenericGF field;

    public ReedSolomonDecoder(GenericGF genericGF) {
        this.field = genericGF;
    }

    private int[] findErrorLocations(GenericGFPoly genericGFPoly) throws ReedSolomonException {
        int n = genericGFPoly.getDegree();
        int n2 = 0;
        if (n == 1) {
            return new int[]{genericGFPoly.getCoefficient(1)};
        }
        int[] arrn = new int[n];
        for (int i = 1; i < this.field.getSize() && n2 < n; ++i) {
            int n3 = n2;
            if (genericGFPoly.evaluateAt(i) == 0) {
                arrn[n2] = this.field.inverse(i);
                n3 = n2 + 1;
            }
            n2 = n3;
        }
        if (n2 != n) throw new ReedSolomonException("Error locator degree does not match number of roots");
        return arrn;
    }

    private int[] findErrorMagnitudes(GenericGFPoly genericGFPoly, int[] arrn) {
        int n = arrn.length;
        int[] arrn2 = new int[n];
        int n2 = 0;
        while (n2 < n) {
            int n3 = this.field.inverse(arrn[n2]);
            int n4 = 1;
            for (int i = 0; i < n; ++i) {
                int n5 = n4;
                if (n2 != i) {
                    n5 = this.field.multiply(arrn[i], n3);
                    n5 = (n5 & 1) == 0 ? (n5 |= 1) : (n5 &= -2);
                    n5 = this.field.multiply(n4, n5);
                }
                n4 = n5;
            }
            arrn2[n2] = this.field.multiply(genericGFPoly.evaluateAt(n3), this.field.inverse(n4));
            if (this.field.getGeneratorBase() != 0) {
                arrn2[n2] = this.field.multiply(arrn2[n2], n3);
            }
            ++n2;
        }
        return arrn2;
    }

    private GenericGFPoly[] runEuclideanAlgorithm(GenericGFPoly genericGFPoly, GenericGFPoly genericGFPoly2, int n) throws ReedSolomonException {
        GenericGFPoly genericGFPoly3 = genericGFPoly;
        GenericGFPoly genericGFPoly4 = genericGFPoly2;
        if (genericGFPoly.getDegree() < genericGFPoly2.getDegree()) {
            genericGFPoly4 = genericGFPoly;
            genericGFPoly3 = genericGFPoly2;
        }
        GenericGFPoly genericGFPoly5 = this.field.getZero();
        genericGFPoly2 = this.field.getOne();
        genericGFPoly = genericGFPoly4;
        genericGFPoly4 = genericGFPoly5;
        do {
            genericGFPoly5 = genericGFPoly4;
            genericGFPoly4 = genericGFPoly3;
            genericGFPoly3 = genericGFPoly;
            if (genericGFPoly3.getDegree() < n / 2) {
                n = genericGFPoly2.getCoefficient(0);
                if (n == 0) throw new ReedSolomonException("sigmaTilde(0) was zero");
                n = this.field.inverse(n);
                return new GenericGFPoly[]{genericGFPoly2.multiply(n), genericGFPoly3.multiply(n)};
            }
            if (genericGFPoly3.isZero()) throw new ReedSolomonException("r_{i-1} was zero");
            GenericGFPoly genericGFPoly6 = this.field.getZero();
            int n2 = genericGFPoly3.getCoefficient(genericGFPoly3.getDegree());
            int n3 = this.field.inverse(n2);
            genericGFPoly = genericGFPoly4;
            genericGFPoly4 = genericGFPoly6;
            while (genericGFPoly.getDegree() >= genericGFPoly3.getDegree() && !genericGFPoly.isZero()) {
                int n4 = genericGFPoly.getDegree() - genericGFPoly3.getDegree();
                n2 = this.field.multiply(genericGFPoly.getCoefficient(genericGFPoly.getDegree()), n3);
                genericGFPoly4 = genericGFPoly4.addOrSubtract(this.field.buildMonomial(n4, n2));
                genericGFPoly = genericGFPoly.addOrSubtract(genericGFPoly3.multiplyByMonomial(n4, n2));
            }
            genericGFPoly5 = genericGFPoly4.multiply(genericGFPoly2).addOrSubtract(genericGFPoly5);
            if (genericGFPoly.getDegree() >= genericGFPoly3.getDegree()) throw new IllegalStateException("Division algorithm failed to reduce polynomial?");
            genericGFPoly4 = genericGFPoly2;
            genericGFPoly2 = genericGFPoly5;
        } while (true);
    }

    public void decode(int[] arrn, int n) throws ReedSolomonException {
        int[] arrn2;
        int n2;
        GenericGFPoly genericGFPoly = new GenericGFPoly(this.field, arrn);
        Object object = new int[n];
        int n3 = 0;
        boolean bl = true;
        for (n2 = 0; n2 < n; ++n2) {
            int n4;
            arrn2 = this.field;
            object[n - 1 - n2] = n4 = genericGFPoly.evaluateAt(arrn2.exp(arrn2.getGeneratorBase() + n2));
            if (n4 == 0) continue;
            bl = false;
        }
        if (bl) {
            return;
        }
        arrn2 = new GenericGFPoly(this.field, (int[])object);
        object = this.runEuclideanAlgorithm(this.field.buildMonomial(n, 1), (GenericGFPoly)arrn2, n);
        arrn2 = (int[])object[0];
        object = object[1];
        arrn2 = this.findErrorLocations((GenericGFPoly)arrn2);
        object = this.findErrorMagnitudes((GenericGFPoly)object, arrn2);
        n = n3;
        while (n < arrn2.length) {
            n2 = arrn.length - 1 - this.field.log(arrn2[n]);
            if (n2 < 0) throw new ReedSolomonException("Bad error location");
            arrn[n2] = GenericGF.addOrSubtract(arrn[n2], object[n]);
            ++n;
        }
    }
}

