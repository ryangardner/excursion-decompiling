/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u00000\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0005H\b\u00a2\u0006\u0002\u0010\u0006\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0000\u001a\f\u0010\n\u001a\u00020\t*\u00020\bH\u0000*\n\u0010\u000b\"\u00020\f2\u00020\f*\n\u0010\r\"\u00020\u000e2\u00020\u000e*\n\u0010\u000f\"\u00020\u00102\u00020\u0010\u00a8\u0006\u0011"}, d2={"synchronized", "R", "lock", "", "block", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "asUtf8ToByteArray", "", "", "toUtf8String", "ArrayIndexOutOfBoundsException", "Ljava/lang/ArrayIndexOutOfBoundsException;", "EOFException", "Ljava/io/EOFException;", "IOException", "Ljava/io/IOException;", "okio"}, k=2, mv={1, 1, 16})
public final class _Platform {
    public static final byte[] asUtf8ToByteArray(String arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$asUtf8ToByteArray");
        arrby = arrby.getBytes(Charsets.UTF_8);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "(this as java.lang.String).getBytes(charset)");
        return arrby;
    }

    public static final <R> R synchronized(Object object, Function0<? extends R> function0) {
        Intrinsics.checkParameterIsNotNull(object, "lock");
        Intrinsics.checkParameterIsNotNull(function0, "block");
        synchronized (object) {
            try {
                function0 = function0.invoke();
                return (R)function0;
            }
            finally {
                InlineMarker.finallyStart(1);
                // MONITOREXIT [1, 3] lbl9 : MonitorExitStatement: MONITOREXIT : var0
                InlineMarker.finallyEnd(1);
            }
        }
    }

    public static final String toUtf8String(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$toUtf8String");
        return new String(arrby, Charsets.UTF_8);
    }
}

