/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common.reedsolomon;

import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.GenericGFPoly;
import java.util.ArrayList;
import java.util.List;

public final class ReedSolomonEncoder {
    private final List<GenericGFPoly> cachedGenerators;
    private final GenericGF field;

    public ReedSolomonEncoder(GenericGF genericGF) {
        this.field = genericGF;
        ArrayList<GenericGFPoly> arrayList = new ArrayList<GenericGFPoly>();
        this.cachedGenerators = arrayList;
        arrayList.add(new GenericGFPoly(genericGF, new int[]{1}));
    }

    private GenericGFPoly buildGenerator(int n) {
        if (n < this.cachedGenerators.size()) return this.cachedGenerators.get(n);
        Object object = this.cachedGenerators;
        object = object.get(object.size() - 1);
        int n2 = this.cachedGenerators.size();
        while (n2 <= n) {
            GenericGF genericGF = this.field;
            object = ((GenericGFPoly)object).multiply(new GenericGFPoly(genericGF, new int[]{1, genericGF.exp(n2 - 1 + genericGF.getGeneratorBase())}));
            this.cachedGenerators.add((GenericGFPoly)object);
            ++n2;
        }
        return this.cachedGenerators.get(n);
    }

    public void encode(int[] arrn, int n) {
        if (n == 0) throw new IllegalArgumentException("No error correction bytes");
        int n2 = arrn.length - n;
        if (n2 <= 0) throw new IllegalArgumentException("No data bytes provided");
        int[] arrn2 = this.buildGenerator(n);
        int[] arrn3 = new int[n2];
        System.arraycopy(arrn, 0, arrn3, 0, n2);
        arrn2 = new GenericGFPoly(this.field, arrn3).multiplyByMonomial(n, 1).divide((GenericGFPoly)arrn2)[1].getCoefficients();
        int n3 = n - arrn2.length;
        n = 0;
        do {
            if (n >= n3) {
                System.arraycopy(arrn2, 0, arrn, n2 + n3, arrn2.length);
                return;
            }
            arrn[n2 + n] = 0;
            ++n;
        } while (true);
    }
}

