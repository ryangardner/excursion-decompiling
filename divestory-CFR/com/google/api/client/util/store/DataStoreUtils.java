/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util.store;

import com.google.api.client.util.store.DataStore;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public final class DataStoreUtils {
    private DataStoreUtils() {
    }

    public static String toString(DataStore<?> object) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('{');
            boolean bl = true;
            Iterator<String> iterator2 = object.keySet().iterator();
            do {
                if (!iterator2.hasNext()) {
                    stringBuilder.append('}');
                    return stringBuilder.toString();
                }
                String string2 = iterator2.next();
                if (bl) {
                    bl = false;
                } else {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(string2);
                stringBuilder.append('=');
                stringBuilder.append(object.get(string2));
            } while (true);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }
}

