/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okio.-DeprecatedUtf8
 */
package okio;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import okio.-DeprecatedUtf8;
import okio.Utf8;

@Deprecated(message="changed in Okio 2.x")
@Metadata(bv={1, 0, 3}, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0007\u00a8\u0006\n"}, d2={"Lokio/-DeprecatedUtf8;", "", "()V", "size", "", "string", "", "beginIndex", "", "endIndex", "okio"}, k=1, mv={1, 1, 16})
public final class _DeprecatedUtf8 {
    public static final -DeprecatedUtf8 INSTANCE = new _DeprecatedUtf8();

    private _DeprecatedUtf8() {
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="string.utf8Size()", imports={"okio.utf8Size"}))
    public final long size(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "string");
        return Utf8.size$default(string2, 0, 0, 3, null);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="string.utf8Size(beginIndex, endIndex)", imports={"okio.utf8Size"}))
    public final long size(String string2, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(string2, "string");
        return Utf8.size(string2, n, n2);
    }
}

