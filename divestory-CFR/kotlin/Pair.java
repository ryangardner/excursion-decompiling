/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u0001*\u0006\b\u0001\u0010\u0002 \u00012\u00060\u0003j\u0002`\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00028\u0001\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\f\u001a\u00028\u0000H\u00c6\u0003\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\r\u001a\u00028\u0001H\u00c6\u0003\u00a2\u0006\u0002\u0010\tJ.\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0005\u001a\u00028\u00002\b\b\u0002\u0010\u0006\u001a\u00028\u0001H\u00c6\u0001\u00a2\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u0013\u0010\u0005\u001a\u00028\u0000\u00a2\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u00028\u0001\u00a2\u0006\n\n\u0002\u0010\n\u001a\u0004\b\u000b\u0010\t\u00a8\u0006\u0018"}, d2={"Lkotlin/Pair;", "A", "B", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "first", "second", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getFirst", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getSecond", "component1", "component2", "copy", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class Pair<A, B>
implements Serializable {
    private final A first;
    private final B second;

    public Pair(A a, B b) {
        this.first = a;
        this.second = b;
    }

    public static /* synthetic */ Pair copy$default(Pair pair, Object object, Object object2, int n, Object object3) {
        if ((n & 1) != 0) {
            object = pair.first;
        }
        if ((n & 2) == 0) return pair.copy(object, object2);
        object2 = pair.second;
        return pair.copy(object, object2);
    }

    public final A component1() {
        return this.first;
    }

    public final B component2() {
        return this.second;
    }

    public final Pair<A, B> copy(A a, B b) {
        return new Pair<A, B>(a, b);
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Pair)) return false;
        object = (Pair)object;
        if (!Intrinsics.areEqual(this.first, ((Pair)object).first)) return false;
        if (!Intrinsics.areEqual(this.second, ((Pair)object).second)) return false;
        return true;
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    public int hashCode() {
        Object object = this.first;
        int n = 0;
        int n2 = object != null ? object.hashCode() : 0;
        object = this.second;
        if (object == null) return n2 * 31 + n;
        n = object.hashCode();
        return n2 * 31 + n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(');
        stringBuilder.append(this.first);
        stringBuilder.append(", ");
        stringBuilder.append(this.second);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

