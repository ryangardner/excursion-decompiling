/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.text;

import kotlin.Metadata;
import kotlin.text.CharsKt;
import kotlin.text.CharsKt__CharJVMKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0014\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0004\u001a\u00020\u0001\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0007H\u0087\n\u00a8\u0006\b"}, d2={"equals", "", "", "other", "ignoreCase", "isSurrogate", "plus", "", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/CharsKt")
class CharsKt__CharKt
extends CharsKt__CharJVMKt {
    public static final boolean equals(char c, char c2, boolean bl) {
        if (c == c2) {
            return true;
        }
        if (!bl) {
            return false;
        }
        if (Character.toUpperCase(c) == Character.toUpperCase(c2)) {
            return true;
        }
        if (Character.toLowerCase(c) != Character.toLowerCase(c2)) return false;
        return true;
    }

    public static /* synthetic */ boolean equals$default(char c, char c2, boolean bl, int n, Object object) {
        if ((n & 2) == 0) return CharsKt.equals(c, c2, bl);
        bl = false;
        return CharsKt.equals(c, c2, bl);
    }

    public static final boolean isSurrogate(char c) {
        if ('\ud800' > c) return false;
        if ('\udfff' < c) return false;
        return true;
    }

    private static final String plus(char c, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(c));
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }
}

