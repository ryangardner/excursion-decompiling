/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class BarcodeValue {
    private final Map<Integer, Integer> values = new HashMap<Integer, Integer>();

    BarcodeValue() {
    }

    public Integer getConfidence(int n) {
        return this.values.get(n);
    }

    int[] getValue() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        Iterator<Map.Entry<Integer, Integer>> iterator2 = this.values.entrySet().iterator();
        int n = -1;
        while (iterator2.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator2.next();
            if (entry.getValue() > n) {
                n = entry.getValue();
                arrayList.clear();
                arrayList.add(entry.getKey());
                continue;
            }
            if (entry.getValue() != n) continue;
            arrayList.add(entry.getKey());
        }
        return PDF417Common.toIntArray(arrayList);
    }

    void setValue(int n) {
        Integer n2;
        Integer n3 = n2 = this.values.get(n);
        if (n2 == null) {
            n3 = 0;
        }
        int n4 = n3;
        this.values.put(n, n4 + 1);
    }
}

