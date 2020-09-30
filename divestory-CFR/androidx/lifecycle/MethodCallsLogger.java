/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.lifecycle;

import java.util.HashMap;
import java.util.Map;

public class MethodCallsLogger {
    private Map<String, Integer> mCalledMethods = new HashMap<String, Integer>();

    public boolean approveCall(String string2, int n) {
        Integer n2 = this.mCalledMethods.get(string2);
        boolean bl = false;
        int n3 = n2 != null ? n2 : 0;
        if ((n3 & n) != 0) {
            bl = true;
        }
        this.mCalledMethods.put(string2, n | n3);
        return bl ^ true;
    }
}

