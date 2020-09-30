/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SpreadBuilder {
    private final ArrayList<Object> list;

    public SpreadBuilder(int n) {
        this.list = new ArrayList(n);
    }

    public void add(Object object) {
        this.list.add(object);
    }

    public void addSpread(Object object) {
        if (object == null) {
            return;
        }
        if (object instanceof Object[]) {
            if (((Object[])(object = (Object[])object)).length <= 0) return;
            ArrayList<Object> arrayList = this.list;
            arrayList.ensureCapacity(arrayList.size() + ((Object[])object).length);
            Collections.addAll(this.list, object);
            return;
        }
        if (object instanceof Collection) {
            this.list.addAll((Collection)object);
            return;
        }
        if (object instanceof Iterable) {
            Iterator iterator2 = ((Iterable)object).iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                this.list.add(object);
            }
            return;
        }
        if (!(object instanceof Iterator)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Don't know how to spread ");
            stringBuilder.append(object.getClass());
            throw new UnsupportedOperationException(stringBuilder.toString());
        }
        object = (Iterator)object;
        while (object.hasNext()) {
            this.list.add(object.next());
        }
    }

    public int size() {
        return this.list.size();
    }

    public Object[] toArray(Object[] arrobject) {
        return this.list.toArray(arrobject);
    }
}

