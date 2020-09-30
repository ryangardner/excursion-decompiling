/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000.\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000\u001a\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u0006\u001a\u001b\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\t\u001a\u0013\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u000b\u001a\u001b\u0010\n\u001a\u0004\u0018\u00010\b*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\f\u001a\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u000f\u001a\u001b\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\u0010\u001a\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u0003H\u0007\u00a2\u0006\u0002\u0010\u0013\u001a\u001b\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0007\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0015"}, d2={"numberFormatError", "", "input", "", "toByteOrNull", "", "(Ljava/lang/String;)Ljava/lang/Byte;", "radix", "", "(Ljava/lang/String;I)Ljava/lang/Byte;", "toIntOrNull", "(Ljava/lang/String;)Ljava/lang/Integer;", "(Ljava/lang/String;I)Ljava/lang/Integer;", "toLongOrNull", "", "(Ljava/lang/String;)Ljava/lang/Long;", "(Ljava/lang/String;I)Ljava/lang/Long;", "toShortOrNull", "", "(Ljava/lang/String;)Ljava/lang/Short;", "(Ljava/lang/String;I)Ljava/lang/Short;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/text/StringsKt")
class StringsKt__StringNumberConversionsKt
extends StringsKt__StringNumberConversionsJVMKt {
    public static final Void numberFormatError(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "input");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid number format: '");
        stringBuilder.append(string2);
        stringBuilder.append('\'');
        throw (Throwable)new NumberFormatException(stringBuilder.toString());
    }

    public static final Byte toByteOrNull(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$toByteOrNull");
        return StringsKt.toByteOrNull(string2, 10);
    }

    public static final Byte toByteOrNull(String object, int n) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toByteOrNull");
        object = StringsKt.toIntOrNull((String)object, n);
        if (object == null) return null;
        n = (Integer)object;
        if (n < -128) return null;
        if (n <= 127) return (byte)n;
        return null;
    }

    public static final Integer toIntOrNull(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$toIntOrNull");
        return StringsKt.toIntOrNull(string2, 10);
    }

    public static final Integer toIntOrNull(String object, int n) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toIntOrNull");
        CharsKt.checkRadix(n);
        int n2 = ((String)object).length();
        if (n2 == 0) {
            return null;
        }
        int n3 = 0;
        char c = ((String)object).charAt(0);
        int n4 = -2147483647;
        int n5 = 1;
        if (c < '0') {
            if (n2 == 1) {
                return null;
            }
            if (c == '-') {
                n4 = Integer.MIN_VALUE;
                c = '\u0001';
            } else {
                if (c != '+') return null;
                c = '\u0000';
            }
        } else {
            c = '\u0000';
            n5 = 0;
        }
        int n6 = -59652323;
        while (n5 < n2) {
            int n7 = CharsKt.digitOf(((String)object).charAt(n5), n);
            if (n7 < 0) {
                return null;
            }
            int n8 = n6;
            if (n3 < n6) {
                if (n6 != -59652323) return null;
                n8 = n6 = n4 / n;
                if (n3 < n6) {
                    return null;
                }
            }
            if ((n6 = n3 * n) < n4 + n7) {
                return null;
            }
            n3 = n6 - n7;
            ++n5;
            n6 = n8;
        }
        if (c == '\u0000') return -n3;
        return n3;
    }

    public static final Long toLongOrNull(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$toLongOrNull");
        return StringsKt.toLongOrNull(string2, 10);
    }

    /*
     * Unable to fully structure code
     */
    public static final Long toLongOrNull(String var0, int var1_1) {
        Intrinsics.checkParameterIsNotNull(var0, "$this$toLongOrNull");
        CharsKt.checkRadix(var1_1);
        var2_2 = var0.length();
        if (var2_2 == 0) {
            return null;
        }
        var3_3 = 0;
        var4_4 = var0.charAt(0);
        var5_5 = -9223372036854775807L;
        var7_6 = true;
        if (var4_4 >= 48) ** GOTO lbl20
        if (var2_2 == 1) {
            return null;
        }
        if (var4_4 == 45) {
            var5_5 = Long.MIN_VALUE;
            var3_3 = 1;
        } else {
            if (var4_4 != 43) return null;
            var3_3 = 1;
lbl20: // 2 sources:
            var7_6 = false;
        }
        var8_7 = 0L;
        var10_8 = -256204778801521550L;
        while (var3_3 < var2_2) {
            var4_4 = CharsKt.digitOf(var0.charAt(var3_3), var1_1);
            if (var4_4 < 0) {
                return null;
            }
            var12_9 = var10_8;
            if (var8_7 < var10_8) {
                if (var10_8 != -256204778801521550L) return null;
                var12_9 = var10_8 = var5_5 / (long)var1_1;
                if (var8_7 < var10_8) {
                    return null;
                }
            }
            if ((var8_7 *= (long)var1_1) < var5_5 + (var10_8 = (long)var4_4)) {
                return null;
            }
            var8_7 -= var10_8;
            ++var3_3;
            var10_8 = var12_9;
        }
        if (var7_6 == false) return -var8_7;
        return var8_7;
    }

    public static final Short toShortOrNull(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$toShortOrNull");
        return StringsKt.toShortOrNull(string2, 10);
    }

    public static final Short toShortOrNull(String object, int n) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toShortOrNull");
        object = StringsKt.toIntOrNull((String)object, n);
        if (object == null) return null;
        n = (Integer)object;
        if (n < -32768) return null;
        if (n <= 32767) return (short)n;
        return null;
    }
}

