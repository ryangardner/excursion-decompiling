/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__RegexExtensionsKt;
import kotlin.text.SystemProperties;

@Metadata(bv={1, 0, 3}, d1={"\u0000Z\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\u001a-\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0087\b\u001a/\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0087\b\u001a\u0012\u0010\t\u001a\u00060\nj\u0002`\u000b*\u00060\nj\u0002`\u000b\u001a\u001d\u0010\t\u001a\u00060\nj\u0002`\u000b*\u00060\nj\u0002`\u000b2\u0006\u0010\u0003\u001a\u00020\fH\u0087\b\u001a\u001f\u0010\t\u001a\u00060\nj\u0002`\u000b*\u00060\nj\u0002`\u000b2\b\u0010\u0003\u001a\u0004\u0018\u00010\bH\u0087\b\u001a\u0012\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u0002\u001a\u001f\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\rH\u0087\b\u001a\u001f\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u000eH\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u000fH\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0010H\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\fH\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001f\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\bH\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0012H\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0006H\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0013H\u0087\b\u001a\u001d\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0014H\u0087\b\u001a\u001f\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0015H\u0087\b\u001a%\u0010\t\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u000e\u0010\u0003\u001a\n\u0018\u00010\u0001j\u0004\u0018\u0001`\u0002H\u0087\b\u001a\u0014\u0010\u0016\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u0002H\u0007\u001a\u001d\u0010\u0017\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0018\u001a\u00020\u0006H\u0087\b\u001a%\u0010\u0019\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0087\b\u001a5\u0010\u001a\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0087\b\u001a7\u0010\u001a\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0018\u001a\u00020\u00062\b\u0010\u0003\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0087\b\u001a!\u0010\u001b\u001a\u00020\u001c*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\fH\u0087\n\u001a-\u0010\u001d\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0015H\u0087\b\u001a7\u0010\u001e\u001a\u00020\u001c*\u00060\u0001j\u0002`\u00022\u0006\u0010\u001f\u001a\u00020\u00042\b\b\u0002\u0010 \u001a\u00020\u00062\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006H\u0087\b\u00a8\u0006!"}, d2={"appendRange", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "value", "", "startIndex", "", "endIndex", "", "appendln", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "", "Ljava/lang/StringBuffer;", "", "", "", "", "", "", "", "", "clear", "deleteAt", "index", "deleteRange", "insertRange", "set", "", "setRange", "toCharArray", "destination", "destinationOffset", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringBuilderJVMKt
extends StringsKt__RegexExtensionsKt {
    private static final StringBuilder appendRange(StringBuilder stringBuilder, CharSequence charSequence, int n, int n2) {
        stringBuilder.append(charSequence, n, n2);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.append(value, startIndex, endIndex)");
        return stringBuilder;
    }

    private static final StringBuilder appendRange(StringBuilder stringBuilder, char[] arrc, int n, int n2) {
        stringBuilder.append(arrc, n, n2 - n);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.append(value, start\u2026x, endIndex - startIndex)");
        return stringBuilder;
    }

    public static final Appendable appendln(Appendable appendable) {
        Intrinsics.checkParameterIsNotNull(appendable, "$this$appendln");
        appendable = appendable.append(SystemProperties.LINE_SEPARATOR);
        Intrinsics.checkExpressionValueIsNotNull(appendable, "append(SystemProperties.LINE_SEPARATOR)");
        return appendable;
    }

    private static final Appendable appendln(Appendable appendable, char c) {
        appendable = appendable.append(c);
        Intrinsics.checkExpressionValueIsNotNull(appendable, "append(value)");
        return StringsKt.appendln(appendable);
    }

    private static final Appendable appendln(Appendable appendable, CharSequence charSequence) {
        appendable = appendable.append(charSequence);
        Intrinsics.checkExpressionValueIsNotNull(appendable, "append(value)");
        return StringsKt.appendln(appendable);
    }

    public static final StringBuilder appendln(StringBuilder stringBuilder) {
        Intrinsics.checkParameterIsNotNull(stringBuilder, "$this$appendln");
        stringBuilder.append(SystemProperties.LINE_SEPARATOR);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(SystemProperties.LINE_SEPARATOR)");
        return stringBuilder;
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, byte by) {
        stringBuilder.append(by);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value.toInt())");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, char c) {
        stringBuilder.append(c);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, double d) {
        stringBuilder.append(d);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, float f) {
        stringBuilder.append(f);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, int n) {
        stringBuilder.append(n);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, long l) {
        stringBuilder.append(l);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, CharSequence charSequence) {
        stringBuilder.append(charSequence);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, Object object) {
        stringBuilder.append(object);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, String string2) {
        stringBuilder.append(string2);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, StringBuffer stringBuffer) {
        stringBuilder.append(stringBuffer);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, StringBuilder stringBuilder2) {
        stringBuilder.append(stringBuilder2);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, short s) {
        stringBuilder.append(s);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value.toInt())");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, boolean bl) {
        stringBuilder.append(bl);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    private static final StringBuilder appendln(StringBuilder stringBuilder, char[] arrc) {
        stringBuilder.append(arrc);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "append(value)");
        return StringsKt.appendln(stringBuilder);
    }

    public static final StringBuilder clear(StringBuilder stringBuilder) {
        Intrinsics.checkParameterIsNotNull(stringBuilder, "$this$clear");
        stringBuilder.setLength(0);
        return stringBuilder;
    }

    private static final StringBuilder deleteAt(StringBuilder stringBuilder, int n) {
        stringBuilder = stringBuilder.deleteCharAt(n);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.deleteCharAt(index)");
        return stringBuilder;
    }

    private static final StringBuilder deleteRange(StringBuilder stringBuilder, int n, int n2) {
        stringBuilder = stringBuilder.delete(n, n2);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.delete(startIndex, endIndex)");
        return stringBuilder;
    }

    private static final StringBuilder insertRange(StringBuilder stringBuilder, int n, CharSequence charSequence, int n2, int n3) {
        stringBuilder = stringBuilder.insert(n, charSequence, n2, n3);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.insert(index, value, startIndex, endIndex)");
        return stringBuilder;
    }

    private static final StringBuilder insertRange(StringBuilder stringBuilder, int n, char[] arrc, int n2, int n3) {
        stringBuilder = stringBuilder.insert(n, arrc, n2, n3 - n2);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.insert(index, value\u2026x, endIndex - startIndex)");
        return stringBuilder;
    }

    private static final void set(StringBuilder stringBuilder, int n, char c) {
        Intrinsics.checkParameterIsNotNull(stringBuilder, "$this$set");
        stringBuilder.setCharAt(n, c);
    }

    private static final StringBuilder setRange(StringBuilder stringBuilder, int n, int n2, String string2) {
        stringBuilder = stringBuilder.replace(n, n2, string2);
        Intrinsics.checkExpressionValueIsNotNull(stringBuilder, "this.replace(startIndex, endIndex, value)");
        return stringBuilder;
    }

    private static final void toCharArray(StringBuilder stringBuilder, char[] arrc, int n, int n2, int n3) {
        stringBuilder.getChars(n2, n3, arrc, n);
    }

    static /* synthetic */ void toCharArray$default(StringBuilder stringBuilder, char[] arrc, int n, int n2, int n3, int n4, Object object) {
        if ((n4 & 2) != 0) {
            n = 0;
        }
        if ((n4 & 4) != 0) {
            n2 = 0;
        }
        if ((n4 & 8) != 0) {
            n3 = stringBuilder.length();
        }
        stringBuilder.getChars(n2, n3, arrc, n);
    }
}

