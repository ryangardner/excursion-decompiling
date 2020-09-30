/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000D\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u001a\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0001H\b\u001a\u0011\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0007H\b\u001a1\u0010\u0010\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\b\u001a1\u0010\u0017\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\b\u001a1\u0010\u0018\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\b\u001a1\u0010\u0019\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00160\u0015H\b\u001a1\u0010\u001a\u001a\u00020\u0016*\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00160\u0015H\b\u001a1\u0010\u001c\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\b\u001a%\u0010\u001d\u001a\u00020\u001e*\u00020\u001b2\b\b\u0002\u0010\u0012\u001a\u00020\u00012\b\b\u0002\u0010\u0013\u001a\u00020\u0001H\u0007\u00a2\u0006\u0002\b\u001f\"\u000e\u0010\u0000\u001a\u00020\u0001XT\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001XT\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001XT\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001XT\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001XT\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007XT\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tXT\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001XT\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2={"HIGH_SURROGATE_HEADER", "", "LOG_SURROGATE_HEADER", "MASK_2BYTES", "MASK_3BYTES", "MASK_4BYTES", "REPLACEMENT_BYTE", "", "REPLACEMENT_CHARACTER", "", "REPLACEMENT_CODE_POINT", "isIsoControl", "", "codePoint", "isUtf8Continuation", "byte", "process2Utf8Bytes", "", "beginIndex", "endIndex", "yield", "Lkotlin/Function1;", "", "process3Utf8Bytes", "process4Utf8Bytes", "processUtf16Chars", "processUtf8Bytes", "", "processUtf8CodePoints", "utf8Size", "", "size", "okio"}, k=2, mv={1, 1, 16})
public final class Utf8 {
    public static final int HIGH_SURROGATE_HEADER = 55232;
    public static final int LOG_SURROGATE_HEADER = 56320;
    public static final int MASK_2BYTES = 3968;
    public static final int MASK_3BYTES = -123008;
    public static final int MASK_4BYTES = 3678080;
    public static final byte REPLACEMENT_BYTE = 63;
    public static final char REPLACEMENT_CHARACTER = '\ufffd';
    public static final int REPLACEMENT_CODE_POINT = 65533;

    public static final boolean isIsoControl(int n) {
        if (n >= 0) {
            if (31 >= n) return true;
        }
        if (127 > n) return false;
        if (159 < n) return false;
        return true;
    }

    public static final boolean isUtf8Continuation(byte by) {
        if ((by & 192) != 128) return false;
        return true;
    }

    public static final int process2Utf8Bytes(byte[] arrby, int n, int n2, Function1<? super Integer, Unit> function1) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$process2Utf8Bytes");
        Intrinsics.checkParameterIsNotNull(function1, "yield");
        int n3 = n + 1;
        Integer n4 = 65533;
        if (n2 <= n3) {
            function1.invoke(n4);
            return 1;
        }
        n2 = arrby[n];
        n = ((n3 = arrby[n3]) & 192) == 128 ? 1 : 0;
        if (n == 0) {
            function1.invoke(n4);
            return 1;
        }
        n = n3 ^ 3968 ^ n2 << 6;
        if (n < 128) {
            function1.invoke(n4);
            return 2;
        }
        function1.invoke((Integer)n);
        return 2;
    }

    public static final int process3Utf8Bytes(byte[] arrby, int n, int n2, Function1<? super Integer, Unit> function1) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$process3Utf8Bytes");
        Intrinsics.checkParameterIsNotNull(function1, "yield");
        int n3 = n + 2;
        int n4 = 0;
        int n5 = 0;
        Integer n6 = 65533;
        if (n2 <= n3) {
            function1.invoke(n6);
            n4 = n + 1;
            if (n2 <= n4) return 1;
            n = n5;
            if ((arrby[n4] & 192) == 128) {
                n = 1;
            }
            if (n != 0) return 2;
            return 1;
        }
        n2 = arrby[n];
        n5 = arrby[n + 1];
        if ((n = (n5 & 192) == 128 ? 1 : 0) == 0) {
            function1.invoke(n6);
            return 1;
        }
        n3 = arrby[n3];
        n = n4;
        if ((n3 & 192) == 128) {
            n = 1;
        }
        if (n == 0) {
            function1.invoke(n6);
            return 2;
        }
        n = n3 ^ -123008 ^ n5 << 6 ^ n2 << 12;
        if (n < 2048) {
            function1.invoke(n6);
            return 3;
        }
        if (55296 <= n && 57343 >= n) {
            function1.invoke(n6);
            return 3;
        }
        function1.invoke((Integer)n);
        return 3;
    }

    public static final int process4Utf8Bytes(byte[] arrby, int n, int n2, Function1<? super Integer, Unit> function1) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$process4Utf8Bytes");
        Intrinsics.checkParameterIsNotNull(function1, "yield");
        int n3 = n + 3;
        int n4 = 0;
        int n5 = 0;
        Integer n6 = 65533;
        if (n2 <= n3) {
            function1.invoke(n6);
            n4 = n + 1;
            if (n2 <= n4) return 1;
            if ((arrby[n4] & 192) != 128) return 1;
            n4 = 1;
            if (n4 == 0) {
                return 1;
            }
            n4 = n + 2;
            if (n2 <= n4) return 2;
            n = n5;
            if ((arrby[n4] & 192) == 128) {
                n = 1;
            }
            if (n != 0) return 3;
            return 2;
        }
        byte by = arrby[n];
        n5 = arrby[n + 1];
        n2 = (n5 & 192) == 128 ? 1 : 0;
        if (n2 == 0) {
            function1.invoke(n6);
            return 1;
        }
        n2 = arrby[n + 2];
        if ((n = (n2 & 192) == 128 ? 1 : 0) == 0) {
            function1.invoke(n6);
            return 2;
        }
        n3 = arrby[n3];
        n = n4;
        if ((n3 & 192) == 128) {
            n = 1;
        }
        if (n == 0) {
            function1.invoke(n6);
            return 3;
        }
        n = n3 ^ 3678080 ^ n2 << 6 ^ n5 << 12 ^ by << 18;
        if (n > 1114111) {
            function1.invoke(n6);
            return 4;
        }
        if (55296 <= n && 57343 >= n) {
            function1.invoke(n6);
            return 4;
        }
        if (n < 65536) {
            function1.invoke(n6);
            return 4;
        }
        function1.invoke((Integer)n);
        return 4;
    }

    /*
     * Unable to fully structure code
     */
    public static final void processUtf16Chars(byte[] var0, int var1_1, int var2_2, Function1<? super Character, Unit> var3_3) {
        Intrinsics.checkParameterIsNotNull(var0, "$this$processUtf16Chars");
        Intrinsics.checkParameterIsNotNull(var3_3, "yield");
        block0 : while (var1_1 < var2_2) {
            block23 : {
                block24 : {
                    block19 : {
                        block22 : {
                            block21 : {
                                block20 : {
                                    block16 : {
                                        block18 : {
                                            block17 : {
                                                var4_4 = var0[var1_1];
                                                if (var4_4 >= 0) {
                                                    var3_3.invoke(Character.valueOf((char)var4_4));
                                                    var5_5 = var1_1 + 1;
                                                    do {
                                                        var1_1 = var5_5;
                                                        if (var5_5 >= var2_2) continue block0;
                                                        var1_1 = var5_5;
                                                        if (var0[var5_5] < 0) continue block0;
                                                        var3_3.invoke(Character.valueOf((char)var0[var5_5]));
                                                        ++var5_5;
                                                    } while (true);
                                                }
                                                var6_6 = 0;
                                                var7_7 = 0;
                                                var8_8 = 0;
                                                var9_9 = 0;
                                                var5_5 = 0;
                                                if (var4_4 >> 5 != -2) break block16;
                                                var7_7 = var1_1 + 1;
                                                if (var2_2 <= var7_7) break block17;
                                                var9_9 = var0[var1_1];
                                                if (((var7_7 = var0[var7_7]) & 192) == 128) {
                                                    var5_5 = 1;
                                                }
                                                if (var5_5 != 0) break block18;
                                            }
                                            var3_3.invoke(Character.valueOf((char)65533));
                                            ** GOTO lbl98
                                        }
                                        var5_5 = var7_7 ^ 3968 ^ var9_9 << 6;
                                        var10_10 = var5_5 < 128 ? (var5_5 = (int)((char)65533)) : (var5_5 = (int)((char)var5_5));
                                        var3_3.invoke(Character.valueOf((char)var10_10));
                                        ** GOTO lbl105
                                    }
                                    if (var4_4 >> 4 != -2) break block19;
                                    var4_4 = var1_1 + 2;
                                    if (var2_2 > var4_4) break block20;
                                    var3_3.invoke(Character.valueOf((char)65533));
                                    var9_9 = var1_1 + 1;
                                    if (var2_2 <= var9_9) ** GOTO lbl98
                                    var5_5 = var6_6;
                                    if ((var0[var9_9] & 192) == 128) {
                                        var5_5 = 1;
                                    }
                                    if (var5_5 != 0) ** GOTO lbl105
                                    ** GOTO lbl98
                                }
                                var9_9 = var0[var1_1];
                                var8_8 = var0[var1_1 + 1];
                                var5_5 = (var8_8 & 192) == 128 ? 1 : 0;
                                if (var5_5 != 0) break block21;
                                var3_3.invoke(Character.valueOf((char)65533));
                                ** GOTO lbl98
                            }
                            var6_6 = var0[var4_4];
                            var5_5 = var7_7;
                            if ((var6_6 & 192) == 128) {
                                var5_5 = 1;
                            }
                            if (var5_5 != 0) break block22;
                            var3_3.invoke(Character.valueOf((char)65533));
                            ** GOTO lbl105
                        }
                        var5_5 = var6_6 ^ -123008 ^ var8_8 << 6 ^ var9_9 << 12;
                        var10_10 = var5_5 < 2048 || 55296 <= var5_5 && 57343 >= var5_5 ? (var5_5 = (int)((char)65533)) : (var5_5 = (int)((char)var5_5));
                        var3_3.invoke(Character.valueOf((char)var10_10));
                        ** GOTO lbl114
                    }
                    if (var4_4 >> 3 != -2) break block23;
                    var6_6 = var1_1 + 3;
                    if (var2_2 > var6_6) break block24;
                    var3_3.invoke(Character.valueOf('\ufffd'));
                    var5_5 = var1_1 + 1;
                    if (var2_2 <= var5_5 || (var5_5 = (var0[var5_5] & 192) == 128 ? 1 : 0) == 0) ** GOTO lbl98
                    var9_9 = var1_1 + 2;
                    if (var2_2 <= var9_9) ** GOTO lbl105
                    var5_5 = var8_8;
                    if ((var0[var9_9] & 192) == 128) {
                        var5_5 = 1;
                    }
                    if (var5_5 != 0) ** GOTO lbl114
                    ** GOTO lbl105
                }
                var7_7 = var0[var1_1];
                var8_8 = var0[var1_1 + 1];
                var5_5 = (var8_8 & 192) == 128 ? 1 : 0;
                if (var5_5 == 0) {
                    var3_3.invoke(Character.valueOf('\ufffd'));
lbl98: // 6 sources:
                    var5_5 = 1;
                } else {
                    var4_4 = var0[var1_1 + 2];
                    var5_5 = (var4_4 & 192) == 128 ? 1 : 0;
                    if (var5_5 == 0) {
                        var3_3.invoke(Character.valueOf('\ufffd'));
lbl105: // 6 sources:
                        var5_5 = 2;
                    } else {
                        var6_6 = var0[var6_6];
                        var5_5 = var9_9;
                        if ((var6_6 & 192) == 128) {
                            var5_5 = 1;
                        }
                        if (var5_5 == 0) {
                            var3_3.invoke(Character.valueOf('\ufffd'));
lbl114: // 3 sources:
                            var5_5 = 3;
                        } else {
                            var5_5 = var6_6 ^ 3678080 ^ var4_4 << 6 ^ var8_8 << 12 ^ var7_7 << 18;
                            if (var5_5 <= 1114111 && (55296 > var5_5 || 57343 < var5_5) && var5_5 >= 65536 && var5_5 != 65533) {
                                var3_3.invoke(Character.valueOf((char)((var5_5 >>> 10) + 55232)));
                                var3_3.invoke(Character.valueOf((char)((var5_5 & 1023) + 56320)));
                            } else {
                                var3_3.invoke(Character.valueOf('\ufffd'));
                            }
                            var5_5 = 4;
                        }
                    }
                }
                var1_1 += var5_5;
                continue;
            }
            var3_3.invoke(Character.valueOf('\ufffd'));
            ++var1_1;
        }
    }

    public static final void processUtf8Bytes(String string2, int n, int n2, Function1<? super Byte, Unit> function1) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$processUtf8Bytes");
        Intrinsics.checkParameterIsNotNull(function1, "yield");
        block0 : while (n < n2) {
            int n3;
            char c = string2.charAt(n);
            if (c < '') {
                function1.invoke((Byte)((byte)c));
                n3 = n + 1;
                do {
                    n = n3;
                    if (n3 >= n2) continue block0;
                    n = n3;
                    if (string2.charAt(n3) >= '') continue block0;
                    function1.invoke((Byte)((byte)string2.charAt(n3)));
                    ++n3;
                } while (true);
            }
            if (c < '\u0800') {
                function1.invoke((Byte)((byte)(c >> 6 | 192)));
                function1.invoke((Byte)((byte)(c & 63 | 128)));
            } else if ('\ud800' <= c && '\udfff' >= c) {
                char c2;
                if (c <= '\udbff' && n2 > (n3 = n + 1) && '\udc00' <= (c2 = string2.charAt(n3)) && '\udfff' >= c2) {
                    n3 = (c << 10) + string2.charAt(n3) - 56613888;
                    function1.invoke((Byte)((byte)(n3 >> 18 | 240)));
                    function1.invoke((Byte)((byte)(n3 >> 12 & 63 | 128)));
                    function1.invoke((Byte)((byte)(n3 >> 6 & 63 | 128)));
                    function1.invoke((Byte)((byte)(n3 & 63 | 128)));
                    n += 2;
                    continue;
                }
                function1.invoke((Byte)((byte)63));
            } else {
                function1.invoke((Byte)((byte)(c >> 12 | 224)));
                function1.invoke((Byte)((byte)(c >> 6 & 63 | 128)));
                function1.invoke((Byte)((byte)(c & 63 | 128)));
            }
            ++n;
        }
    }

    /*
     * Unable to fully structure code
     */
    public static final void processUtf8CodePoints(byte[] var0, int var1_1, int var2_2, Function1<? super Integer, Unit> var3_3) {
        Intrinsics.checkParameterIsNotNull(var0, "$this$processUtf8CodePoints");
        Intrinsics.checkParameterIsNotNull(var3_3, "yield");
        block0 : while (var1_1 < var2_2) {
            block21 : {
                block22 : {
                    block17 : {
                        block20 : {
                            block19 : {
                                block18 : {
                                    block14 : {
                                        block16 : {
                                            block15 : {
                                                var4_4 = var0[var1_1];
                                                if (var4_4 >= 0) {
                                                    var3_3.invoke((Integer)var4_4);
                                                    var5_5 = var1_1 + 1;
                                                    do {
                                                        var1_1 = var5_5;
                                                        if (var5_5 >= var2_2) continue block0;
                                                        var1_1 = var5_5;
                                                        if (var0[var5_5] < 0) continue block0;
                                                        var3_3.invoke((Integer)((int)var0[var5_5]));
                                                        ++var5_5;
                                                    } while (true);
                                                }
                                                var6_6 = 0;
                                                var7_7 = 0;
                                                var8_8 = 0;
                                                var9_9 = 0;
                                                var5_5 = 0;
                                                if (var4_4 >> 5 != -2) break block14;
                                                var7_7 = var1_1 + 1;
                                                if (var2_2 <= var7_7) break block15;
                                                var9_9 = var0[var1_1];
                                                if (((var7_7 = var0[var7_7]) & 192) == 128) {
                                                    var5_5 = 1;
                                                }
                                                if (var5_5 != 0) break block16;
                                            }
                                            var3_3.invoke((Integer)65533);
                                            ** GOTO lbl98
                                        }
                                        var5_5 = var7_7 ^ 3968 ^ var9_9 << 6;
                                        var10_10 = var5_5 < 128 ? Integer.valueOf(65533) : Integer.valueOf(var5_5);
                                        var3_3.invoke(var10_10);
                                        ** GOTO lbl105
                                    }
                                    if (var4_4 >> 4 != -2) break block17;
                                    var4_4 = var1_1 + 2;
                                    if (var2_2 > var4_4) break block18;
                                    var3_3.invoke((Integer)65533);
                                    var9_9 = var1_1 + 1;
                                    if (var2_2 <= var9_9) ** GOTO lbl98
                                    var5_5 = var6_6;
                                    if ((var0[var9_9] & 192) == 128) {
                                        var5_5 = 1;
                                    }
                                    if (var5_5 != 0) ** GOTO lbl105
                                    ** GOTO lbl98
                                }
                                var8_8 = var0[var1_1];
                                var9_9 = var0[var1_1 + 1];
                                var5_5 = (var9_9 & 192) == 128 ? 1 : 0;
                                if (var5_5 != 0) break block19;
                                var3_3.invoke((Integer)65533);
                                ** GOTO lbl98
                            }
                            var6_6 = var0[var4_4];
                            var5_5 = var7_7;
                            if ((var6_6 & 192) == 128) {
                                var5_5 = 1;
                            }
                            if (var5_5 != 0) break block20;
                            var3_3.invoke((Integer)65533);
                            ** GOTO lbl105
                        }
                        var5_5 = var6_6 ^ -123008 ^ var9_9 << 6 ^ var8_8 << 12;
                        var10_10 = var5_5 < 2048 || 55296 <= var5_5 && 57343 >= var5_5 ? Integer.valueOf(65533) : Integer.valueOf(var5_5);
                        var3_3.invoke(var10_10);
                        ** GOTO lbl114
                    }
                    if (var4_4 >> 3 != -2) break block21;
                    var6_6 = var1_1 + 3;
                    if (var2_2 > var6_6) break block22;
                    var3_3.invoke((Integer)65533);
                    var5_5 = var1_1 + 1;
                    if (var2_2 <= var5_5 || (var5_5 = (var0[var5_5] & 192) == 128 ? 1 : 0) == 0) ** GOTO lbl98
                    var9_9 = var1_1 + 2;
                    if (var2_2 <= var9_9) ** GOTO lbl105
                    var5_5 = var8_8;
                    if ((var0[var9_9] & 192) == 128) {
                        var5_5 = 1;
                    }
                    if (var5_5 != 0) ** GOTO lbl114
                    ** GOTO lbl105
                }
                var8_8 = var0[var1_1];
                var7_7 = var0[var1_1 + 1];
                var5_5 = (var7_7 & 192) == 128 ? 1 : 0;
                if (var5_5 == 0) {
                    var3_3.invoke((Integer)65533);
lbl98: // 6 sources:
                    var5_5 = 1;
                } else {
                    var4_4 = var0[var1_1 + 2];
                    var5_5 = (var4_4 & 192) == 128 ? 1 : 0;
                    if (var5_5 == 0) {
                        var3_3.invoke((Integer)65533);
lbl105: // 6 sources:
                        var5_5 = 2;
                    } else {
                        var6_6 = var0[var6_6];
                        var5_5 = var9_9;
                        if ((var6_6 & 192) == 128) {
                            var5_5 = 1;
                        }
                        if (var5_5 == 0) {
                            var3_3.invoke((Integer)65533);
lbl114: // 3 sources:
                            var5_5 = 3;
                        } else {
                            var5_5 = var6_6 ^ 3678080 ^ var4_4 << 6 ^ var7_7 << 12 ^ var8_8 << 18;
                            var10_10 = var5_5 > 1114111 || 55296 <= var5_5 && 57343 >= var5_5 || var5_5 < 65536 ? Integer.valueOf(65533) : Integer.valueOf(var5_5);
                            var3_3.invoke(var10_10);
                            var5_5 = 4;
                        }
                    }
                }
                var1_1 += var5_5;
                continue;
            }
            var3_3.invoke((Integer)65533);
            ++var1_1;
        }
    }

    public static final long size(String string2) {
        return Utf8.size$default(string2, 0, 0, 3, null);
    }

    public static final long size(String string2, int n) {
        return Utf8.size$default(string2, n, 0, 2, null);
    }

    public static final long size(String charSequence, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(charSequence, "$this$utf8Size");
        int n3 = 1;
        int n4 = n >= 0 ? 1 : 0;
        if (n4 == 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("beginIndex < 0: ");
            ((StringBuilder)charSequence).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
        }
        n4 = n2 >= n ? 1 : 0;
        if (n4 == 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("endIndex < beginIndex: ");
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append(" < ");
            ((StringBuilder)charSequence).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)charSequence).toString().toString());
        }
        n4 = n2 <= ((String)charSequence).length() ? n3 : 0;
        if (n4 == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("endIndex > string.length: ");
            stringBuilder.append(n2);
            stringBuilder.append(" > ");
            stringBuilder.append(((String)charSequence).length());
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        long l = 0L;
        while (n < n2) {
            char c = ((String)charSequence).charAt(n);
            if (c < '') {
                ++l;
            } else {
                if (c < '\u0800') {
                    n4 = 2;
                } else {
                    if (c >= '\ud800' && c <= '\udfff') {
                        n3 = n + 1;
                        n4 = n3 < n2 ? (int)((String)charSequence).charAt(n3) : 0;
                        if (c <= '\udbff' && n4 >= 56320 && n4 <= 57343) {
                            l += (long)4;
                            n += 2;
                            continue;
                        }
                        ++l;
                        n = n3;
                        continue;
                    }
                    n4 = 3;
                }
                l += (long)n4;
            }
            ++n;
        }
        return l;
    }

    public static /* synthetic */ long size$default(String string2, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) == 0) return Utf8.size(string2, n, n2);
        n2 = string2.length();
        return Utf8.size(string2, n, n2);
    }
}

