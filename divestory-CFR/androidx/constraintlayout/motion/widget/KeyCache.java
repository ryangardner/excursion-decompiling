/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.motion.widget;

import java.util.Arrays;
import java.util.HashMap;

public class KeyCache {
    HashMap<Object, HashMap<String, float[]>> map = new HashMap();

    float getFloatValue(Object arrf, String string2, int n) {
        if (!this.map.containsKey(arrf)) {
            return Float.NaN;
        }
        if (!(arrf = this.map.get(arrf)).containsKey(string2)) {
            return Float.NaN;
        }
        if ((arrf = arrf.get(string2)).length <= n) return Float.NaN;
        return arrf[n];
    }

    void setFloatValue(Object arrf, String string2, int n, float f) {
        float[] arrf2;
        if (!this.map.containsKey(arrf)) {
            HashMap<String, float[]> hashMap = new HashMap<String, float[]>();
            float[] arrf3 = new float[n + 1];
            arrf3[n] = f;
            hashMap.put(string2, arrf3);
            this.map.put(arrf, hashMap);
            return;
        }
        HashMap<String, float[]> hashMap = this.map.get(arrf);
        if (!hashMap.containsKey(string2)) {
            float[] arrf4 = new float[n + 1];
            arrf4[n] = f;
            hashMap.put(string2, arrf4);
            this.map.put(arrf, hashMap);
            return;
        }
        arrf = arrf2 = hashMap.get(string2);
        if (arrf2.length <= n) {
            arrf = Arrays.copyOf(arrf2, n + 1);
        }
        arrf[n] = f;
        hashMap.put(string2, arrf);
    }
}

