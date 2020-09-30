/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Regex;
import kotlin.text.ScreenFloatValueRegEx;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringBuilderKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000X\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\n\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0005H\u0082\b\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a\r\u0010\b\u001a\u00020\t*\u00020\u0003H\u0087\b\u001a\u0015\u0010\b\u001a\u00020\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u000e\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u0003H\u0007\u001a\u0016\u0010\f\u001a\u0004\u0018\u00010\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\r\u0010\r\u001a\u00020\u000e*\u00020\u0003H\u0087\b\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007\u001a\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\r\u0010\u0012\u001a\u00020\u0013*\u00020\u0003H\u0087\b\u001a\r\u0010\u0014\u001a\u00020\u0015*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010\u0016\u001a\u00020\u0017*\u00020\u0003H\u0087\b\u001a\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0017*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u0019\u001a\r\u0010\u001a\u001a\u00020\u001b*\u00020\u0003H\u0087\b\u001a\u0013\u0010\u001c\u001a\u0004\u0018\u00010\u001b*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u001d\u001a\r\u0010\u001e\u001a\u00020\u0010*\u00020\u0003H\u0087\b\u001a\u0015\u0010\u001e\u001a\u00020\u0010*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010\u001f\u001a\u00020 *\u00020\u0003H\u0087\b\u001a\u0015\u0010\u001f\u001a\u00020 *\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\r\u0010!\u001a\u00020\"*\u00020\u0003H\u0087\b\u001a\u0015\u0010!\u001a\u00020\"*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020 2\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u001a\u0015\u0010#\u001a\u00020\u0003*\u00020\"2\u0006\u0010\u000f\u001a\u00020\u0010H\u0087\b\u00a8\u0006$"}, d2={"screenFloatValue", "T", "str", "", "parse", "Lkotlin/Function1;", "screenFloatValue$StringsKt__StringNumberConversionsJVMKt", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "toBigDecimal", "Ljava/math/BigDecimal;", "mathContext", "Ljava/math/MathContext;", "toBigDecimalOrNull", "toBigInteger", "Ljava/math/BigInteger;", "radix", "", "toBigIntegerOrNull", "toBoolean", "", "toByte", "", "toDouble", "", "toDoubleOrNull", "(Ljava/lang/String;)Ljava/lang/Double;", "toFloat", "", "toFloatOrNull", "(Ljava/lang/String;)Ljava/lang/Float;", "toInt", "toLong", "", "toShort", "", "toString", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringNumberConversionsJVMKt
extends StringsKt__StringBuilderKt {
    private static final <T> T screenFloatValue$StringsKt__StringNumberConversionsJVMKt(String string2, Function1<? super String, ? extends T> function1) {
        T t;
        T t2 = t = null;
        try {
            if (!ScreenFloatValueRegEx.value.matches(string2)) return t2;
            t2 = function1.invoke(string2);
        }
        catch (NumberFormatException numberFormatException) {
            t2 = t;
            return t2;
        }
        return t2;
    }

    private static final BigDecimal toBigDecimal(String string2) {
        return new BigDecimal(string2);
    }

    private static final BigDecimal toBigDecimal(String string2, MathContext mathContext) {
        return new BigDecimal(string2, mathContext);
    }

    public static final BigDecimal toBigDecimalOrNull(String string2) {
        BigDecimal bigDecimal;
        Intrinsics.checkParameterIsNotNull(string2, "$this$toBigDecimalOrNull");
        BigDecimal bigDecimal2 = bigDecimal = null;
        try {
            if (!ScreenFloatValueRegEx.value.matches(string2)) return bigDecimal2;
            return new BigDecimal(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return bigDecimal;
        }
    }

    public static final BigDecimal toBigDecimalOrNull(String string2, MathContext mathContext) {
        BigDecimal bigDecimal;
        Intrinsics.checkParameterIsNotNull(string2, "$this$toBigDecimalOrNull");
        Intrinsics.checkParameterIsNotNull(mathContext, "mathContext");
        BigDecimal bigDecimal2 = bigDecimal = null;
        try {
            if (!ScreenFloatValueRegEx.value.matches(string2)) return bigDecimal2;
            return new BigDecimal(string2, mathContext);
        }
        catch (NumberFormatException numberFormatException) {
            return bigDecimal;
        }
    }

    private static final BigInteger toBigInteger(String string2) {
        return new BigInteger(string2);
    }

    private static final BigInteger toBigInteger(String string2, int n) {
        return new BigInteger(string2, CharsKt.checkRadix(n));
    }

    public static final BigInteger toBigIntegerOrNull(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$toBigIntegerOrNull");
        return StringsKt.toBigIntegerOrNull(string2, 10);
    }

    public static final BigInteger toBigIntegerOrNull(String string2, int n) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$toBigIntegerOrNull");
        CharsKt.checkRadix(n);
        int n2 = string2.length();
        if (n2 == 0) return null;
        int n3 = 0;
        if (n2 == 1) {
            if (CharsKt.digitOf(string2.charAt(0), n) >= 0) return new BigInteger(string2, CharsKt.checkRadix(n));
            return null;
        }
        if (string2.charAt(0) == '-') {
            n3 = 1;
        }
        while (n3 < n2) {
            if (CharsKt.digitOf(string2.charAt(n3), n) < 0) {
                return null;
            }
            ++n3;
        }
        return new BigInteger(string2, CharsKt.checkRadix(n));
    }

    private static final boolean toBoolean(String string2) {
        return Boolean.parseBoolean(string2);
    }

    private static final byte toByte(String string2) {
        return Byte.parseByte(string2);
    }

    private static final byte toByte(String string2, int n) {
        return Byte.parseByte(string2, CharsKt.checkRadix(n));
    }

    private static final double toDouble(String string2) {
        return Double.parseDouble(string2);
    }

    public static final Double toDoubleOrNull(String string2) {
        double d;
        Double d2;
        Intrinsics.checkParameterIsNotNull(string2, "$this$toDoubleOrNull");
        Double d3 = d2 = null;
        try {
            if (!ScreenFloatValueRegEx.value.matches(string2)) return d3;
            d = Double.parseDouble(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return d2;
        }
        return d;
    }

    private static final float toFloat(String string2) {
        return Float.parseFloat(string2);
    }

    public static final Float toFloatOrNull(String string2) {
        float f;
        Float f2;
        Intrinsics.checkParameterIsNotNull(string2, "$this$toFloatOrNull");
        Float f3 = f2 = null;
        try {
            if (!ScreenFloatValueRegEx.value.matches(string2)) return f3;
            f = Float.parseFloat(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return f2;
        }
        return Float.valueOf(f);
    }

    private static final int toInt(String string2) {
        return Integer.parseInt(string2);
    }

    private static final int toInt(String string2, int n) {
        return Integer.parseInt(string2, CharsKt.checkRadix(n));
    }

    private static final long toLong(String string2) {
        return Long.parseLong(string2);
    }

    private static final long toLong(String string2, int n) {
        return Long.parseLong(string2, CharsKt.checkRadix(n));
    }

    private static final short toShort(String string2) {
        return Short.parseShort(string2);
    }

    private static final short toShort(String string2, int n) {
        return Short.parseShort(string2, CharsKt.checkRadix(n));
    }

    private static final String toString(byte by, int n) {
        String string2 = Integer.toString(by, CharsKt.checkRadix(CharsKt.checkRadix(n)));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Integer.toStri\u2026(this, checkRadix(radix))");
        return string2;
    }

    private static final String toString(int n, int n2) {
        String string2 = Integer.toString(n, CharsKt.checkRadix(n2));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Integer.toStri\u2026(this, checkRadix(radix))");
        return string2;
    }

    private static final String toString(long l, int n) {
        String string2 = Long.toString(l, CharsKt.checkRadix(n));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Long.toString(this, checkRadix(radix))");
        return string2;
    }

    private static final String toString(short s, int n) {
        String string2 = Integer.toString(s, CharsKt.checkRadix(CharsKt.checkRadix(n)));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Integer.toStri\u2026(this, checkRadix(radix))");
        return string2;
    }
}

