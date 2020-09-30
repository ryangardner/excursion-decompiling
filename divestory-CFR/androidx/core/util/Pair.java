/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.core.util;

import androidx.core.util.ObjectsCompat;

public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }

    public static <A, B> Pair<A, B> create(A a, B b) {
        return new Pair<A, B>(a, b);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Pair;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (Pair)object;
        bl = bl2;
        if (!ObjectsCompat.equals(((Pair)object).first, this.first)) return bl;
        bl = bl2;
        if (!ObjectsCompat.equals(((Pair)object).second, this.second)) return bl;
        return true;
    }

    public int hashCode() {
        Object object = this.first;
        int n = 0;
        int n2 = object == null ? 0 : object.hashCode();
        object = this.second;
        if (object == null) {
            return n2 ^ n;
        }
        n = object.hashCode();
        return n2 ^ n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Pair{");
        stringBuilder.append(String.valueOf(this.first));
        stringBuilder.append(" ");
        stringBuilder.append(String.valueOf(this.second));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

