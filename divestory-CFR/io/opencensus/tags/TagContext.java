/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.tags.Tag;
import java.util.HashMap;
import java.util.Iterator;
import javax.annotation.Nullable;

public abstract class TagContext {
    public boolean equals(@Nullable Object iterator2) {
        if (!(iterator2 instanceof TagContext)) {
            return false;
        }
        iterator2 = (TagContext)((Object)iterator2);
        Object object = this.getIterator();
        iterator2 = ((TagContext)((Object)iterator2)).getIterator();
        HashMap<Object, Integer> hashMap = new HashMap<Object, Integer>();
        while (object != null && object.hasNext()) {
            Tag tag = object.next();
            if (hashMap.containsKey(tag)) {
                hashMap.put(tag, (Integer)hashMap.get(tag) + 1);
                continue;
            }
            hashMap.put(tag, 1);
        }
        while (iterator2 != null) {
            if (!iterator2.hasNext()) return hashMap.isEmpty();
            object = iterator2.next();
            if (!hashMap.containsKey(object)) {
                return false;
            }
            int n = (Integer)hashMap.get(object);
            if (n > 1) {
                hashMap.put(object, n - 1);
                continue;
            }
            hashMap.remove(object);
        }
        return hashMap.isEmpty();
    }

    protected abstract Iterator<Tag> getIterator();

    public final int hashCode() {
        Iterator<Tag> iterator2 = this.getIterator();
        int n = 0;
        if (iterator2 == null) {
            return 0;
        }
        while (iterator2.hasNext()) {
            Tag tag = iterator2.next();
            if (tag == null) continue;
            n += tag.hashCode();
        }
        return n;
    }

    public String toString() {
        return "TagContext";
    }
}

