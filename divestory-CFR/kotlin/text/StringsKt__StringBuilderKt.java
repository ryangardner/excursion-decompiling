/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.text;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringBuilderJVMKt;

@Metadata(bv={1, 0, 3}, d1={"\u00004\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\u001a.\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u001b\u0010\u0004\u001a\u0017\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\u0002\b\tH\u0087\b\u001a&\u0010\u0000\u001a\u00020\u00012\u001b\u0010\u0004\u001a\u0017\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\u0002\b\tH\u0087\b\u001a\u001f\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a/\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\f0\u000e\"\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\u000f\u001a/\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000e\"\u0004\u0018\u00010\u0001\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006\u0011"}, d2={"buildString", "", "capacity", "", "builderAction", "Lkotlin/Function1;", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "", "Lkotlin/ExtensionFunctionType;", "append", "obj", "", "value", "", "(Ljava/lang/StringBuilder;[Ljava/lang/Object;)Ljava/lang/StringBuilder;", "(Ljava/lang/StringBuilder;[Ljava/lang/String;)Ljava/lang/StringBuilder;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringBuilderKt
extends StringsKt__StringBuilderJVMKt {
    @Deprecated(level=DeprecationLevel.WARNING, message="Use append(value: Any?) instead", replaceWith=@ReplaceWith(expression="append(value = obj)", imports={}))
    private static final StringBuilder append(StringBuilder stringBuilder, Object object) {
        stringBuilder.append(object);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.append(obj)");
        return stringBuilder;
    }

    public static final StringBuilder append(StringBuilder stringBuilder, Object ... arrobject) {
        Intrinsics.checkParameterIsNotNull(stringBuilder, "$this$append");
        Intrinsics.checkParameterIsNotNull(arrobject, "value");
        int n = arrobject.length;
        int n2 = 0;
        while (n2 < n) {
            stringBuilder.append(arrobject[n2]);
            ++n2;
        }
        return stringBuilder;
    }

    public static final StringBuilder append(StringBuilder stringBuilder, String ... arrstring) {
        Intrinsics.checkParameterIsNotNull(stringBuilder, "$this$append");
        Intrinsics.checkParameterIsNotNull(arrstring, "value");
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            stringBuilder.append(arrstring[n2]);
            ++n2;
        }
        return stringBuilder;
    }

    private static final String buildString(int n, Function1<? super StringBuilder, Unit> object) {
        StringBuilder stringBuilder = new StringBuilder(n);
        object.invoke((StringBuilder)stringBuilder);
        object = stringBuilder.toString();
        Intrinsics.checkExpressionValueIsNotNull(object, "StringBuilder(capacity).\u2026builderAction).toString()");
        return object;
    }

    private static final String buildString(Function1<? super StringBuilder, Unit> object) {
        StringBuilder stringBuilder = new StringBuilder();
        object.invoke((StringBuilder)stringBuilder);
        object = stringBuilder.toString();
        Intrinsics.checkExpressionValueIsNotNull(object, "StringBuilder().apply(builderAction).toString()");
        return object;
    }
}

