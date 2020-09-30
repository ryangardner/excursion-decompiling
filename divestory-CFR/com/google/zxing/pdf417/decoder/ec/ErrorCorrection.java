/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.ChecksumException;
import com.google.zxing.pdf417.decoder.ec.ModulusGF;
import com.google.zxing.pdf417.decoder.ec.ModulusPoly;

public final class ErrorCorrection {
    private final ModulusGF field = ModulusGF.PDF417_GF;

    private int[] findErrorLocations(ModulusPoly modulusPoly) throws ChecksumException {
        int n = modulusPoly.getDegree();
        int[] arrn = new int[n];
        int n2 = 0;
        for (int i = 1; i < this.field.getSize() && n2 < n; ++i) {
            int n3 = n2;
            if (modulusPoly.evaluateAt(i) == 0) {
                arrn[n2] = this.field.inverse(i);
                n3 = n2 + 1;
            }
            n2 = n3;
        }
        if (n2 != n) throw ChecksumException.getChecksumInstance();
        return arrn;
    }

    private int[] findErrorMagnitudes(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int[] arrn) {
        int n;
        int n2 = modulusPoly2.getDegree();
        int[] arrn2 = new int[n2];
        for (n = 1; n <= n2; ++n) {
            arrn2[n2 - n] = this.field.multiply(n, modulusPoly2.getCoefficient(n));
        }
        modulusPoly2 = new ModulusPoly(this.field, arrn2);
        n2 = arrn.length;
        arrn2 = new int[n2];
        n = 0;
        while (n < n2) {
            int n3 = this.field.inverse(arrn[n]);
            int n4 = this.field.subtract(0, modulusPoly.evaluateAt(n3));
            n3 = this.field.inverse(modulusPoly2.evaluateAt(n3));
            arrn2[n] = this.field.multiply(n4, n3);
            ++n;
        }
        return arrn2;
    }

    private ModulusPoly[] runEuclideanAlgorithm(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int n) throws ChecksumException {
        ModulusPoly modulusPoly3 = modulusPoly;
        ModulusPoly modulusPoly4 = modulusPoly2;
        if (modulusPoly.getDegree() < modulusPoly2.getDegree()) {
            modulusPoly4 = modulusPoly;
            modulusPoly3 = modulusPoly2;
        }
        ModulusPoly modulusPoly5 = this.field.getZero();
        modulusPoly2 = this.field.getOne();
        modulusPoly = modulusPoly4;
        modulusPoly4 = modulusPoly5;
        do {
            modulusPoly5 = modulusPoly4;
            modulusPoly4 = modulusPoly3;
            modulusPoly3 = modulusPoly;
            if (modulusPoly3.getDegree() < n / 2) {
                n = modulusPoly2.getCoefficient(0);
                if (n == 0) throw ChecksumException.getChecksumInstance();
                n = this.field.inverse(n);
                return new ModulusPoly[]{modulusPoly2.multiply(n), modulusPoly3.multiply(n)};
            }
            if (modulusPoly3.isZero()) throw ChecksumException.getChecksumInstance();
            ModulusPoly modulusPoly6 = this.field.getZero();
            int n2 = modulusPoly3.getCoefficient(modulusPoly3.getDegree());
            int n3 = this.field.inverse(n2);
            modulusPoly = modulusPoly4;
            modulusPoly4 = modulusPoly6;
            while (modulusPoly.getDegree() >= modulusPoly3.getDegree() && !modulusPoly.isZero()) {
                int n4 = modulusPoly.getDegree() - modulusPoly3.getDegree();
                n2 = this.field.multiply(modulusPoly.getCoefficient(modulusPoly.getDegree()), n3);
                modulusPoly4 = modulusPoly4.add(this.field.buildMonomial(n4, n2));
                modulusPoly = modulusPoly.subtract(modulusPoly3.multiplyByMonomial(n4, n2));
            }
            modulusPoly5 = modulusPoly4.multiply(modulusPoly2).subtract(modulusPoly5).negative();
            modulusPoly4 = modulusPoly2;
            modulusPoly2 = modulusPoly5;
        } while (true);
    }

    public int decode(int[] arrn, int n, int[] object) throws ChecksumException {
        int n2;
        int n3;
        Object object2 = new ModulusPoly(this.field, arrn);
        Object object3 = new int[n];
        int n4 = 0;
        int n5 = 0;
        for (n2 = n; n2 > 0; --n2) {
            object3[n - n2] = n3 = object2.evaluateAt(this.field.exp(n2));
            if (n3 == 0) continue;
            n5 = 1;
        }
        if (n5 == 0) {
            return 0;
        }
        object2 = this.field.getOne();
        if (object != null) {
            ModulusGF modulusGF;
            n5 = ((int[])object).length;
            for (n2 = 0; n2 < n5; object2 = object2.multiply((ModulusPoly)new ModulusPoly((ModulusGF)modulusGF, (int[])new int[]{modulusGF.subtract((int)0, (int)n3), 1})), ++n2) {
                n3 = object[n2];
                n3 = this.field.exp(arrn.length - 1 - n3);
                modulusGF = this.field;
            }
        }
        object = new ModulusPoly(this.field, (int[])object3);
        object2 = this.runEuclideanAlgorithm(this.field.buildMonomial(n, 1), (ModulusPoly)object, n);
        object = object2[0];
        object3 = object2[1];
        object2 = this.findErrorLocations((ModulusPoly)object);
        object = this.findErrorMagnitudes((ModulusPoly)object3, (ModulusPoly)object, (int[])object2);
        n = n4;
        while (n < ((ModulusPoly[])object2).length) {
            n2 = arrn.length - 1 - this.field.log((int)object2[n]);
            if (n2 < 0) throw ChecksumException.getChecksumInstance();
            arrn[n2] = this.field.subtract(arrn[n2], object[n]);
            ++n;
        }
        return ((ModulusPoly[])object2).length;
    }
}

