/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.util;

import android.os.Build;
import java.util.Arrays;
import java.util.Objects;

public class ObjectsCompat {
    private ObjectsCompat() {
    }

    public static boolean equals(Object object, Object object2) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Objects.equals(object, object2);
        }
        if (object == object2) return true;
        if (object == null) return false;
        if (!object.equals(object2)) return false;
        return true;
    }

    public static int hash(Object ... arrobject) {
        if (Build.VERSION.SDK_INT < 19) return Arrays.hashCode(arrobject);
        return Objects.hash(arrobject);
    }

    public static int hashCode(Object object) {
        if (object == null) return 0;
        return object.hashCode();
    }
}

