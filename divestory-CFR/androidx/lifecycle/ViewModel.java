/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.lifecycle;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class ViewModel {
    private final Map<String, Object> mBagOfTags = new HashMap<String, Object>();
    private volatile boolean mCleared = false;

    private static void closeWithRuntimeException(Object object) {
        if (!(object instanceof Closeable)) return;
        try {
            ((Closeable)object).close();
            return;
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    final void clear() {
        this.mCleared = true;
        Map<String, Object> map = this.mBagOfTags;
        if (map != null) {
            synchronized (map) {
                Iterator<Object> iterator2 = this.mBagOfTags.values().iterator();
                while (iterator2.hasNext()) {
                    ViewModel.closeWithRuntimeException(iterator2.next());
                }
            }
        }
        this.onCleared();
    }

    <T> T getTag(String object) {
        Map<String, Object> map = this.mBagOfTags;
        if (map == null) {
            return null;
        }
        synchronized (map) {
            object = this.mBagOfTags.get(object);
            return (T)object;
        }
    }

    protected void onCleared() {
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    <T> T setTagIfAbsent(String string2, T object) {
        Map<String, Object> map = this.mBagOfTags;
        // MONITORENTER : map
        Object object2 = this.mBagOfTags.get(string2);
        if (object2 == null) {
            this.mBagOfTags.put(string2, object);
        }
        // MONITOREXIT : map
        if (object2 != null) {
            object = object2;
        }
        if (!this.mCleared) return object;
        ViewModel.closeWithRuntimeException(object);
        return object;
    }
}

