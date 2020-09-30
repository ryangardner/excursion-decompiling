/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MapUtils {
    public static void writeStringMapToJson(StringBuilder stringBuilder, HashMap<String, String> hashMap) {
        stringBuilder.append("{");
        Iterator<String> iterator2 = hashMap.keySet().iterator();
        boolean bl = true;
        do {
            if (!iterator2.hasNext()) {
                stringBuilder.append("}");
                return;
            }
            String string2 = iterator2.next();
            if (!bl) {
                stringBuilder.append(",");
            } else {
                bl = false;
            }
            String string3 = hashMap.get(string2);
            stringBuilder.append("\"");
            stringBuilder.append(string2);
            stringBuilder.append("\":");
            if (string3 == null) {
                stringBuilder.append("null");
                continue;
            }
            stringBuilder.append("\"");
            stringBuilder.append(string3);
            stringBuilder.append("\"");
        } while (true);
    }
}

