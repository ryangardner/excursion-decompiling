/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0016\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u001e\u0010\u0003\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u00a8\u0006\u0007"}, d2={"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "beginIndex", "", "endIndex", "okio"}, k=2, mv={1, 1, 16})
public final class _Utf8Kt {
    public static final byte[] commonAsUtf8ToByteArray(String arrby) {
        int n;
        Intrinsics.checkParameterIsNotNull(arrby, "$this$commonAsUtf8ToByteArray");
        byte[] arrby2 = new byte[arrby.length() * 4];
        int n2 = arrby.length();
        int n3 = 0;
        do {
            if (n3 >= n2) {
                arrby = Arrays.copyOf(arrby2, arrby.length());
                Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOf(this, newSize)");
                return arrby;
            }
            n = arrby.charAt(n3);
            if (n >= 128) break;
            arrby2[n3] = (byte)n;
            ++n3;
        } while (true);
        int n4 = arrby.length();
        n = n3;
        n2 = n3;
        do {
            block10 : {
                block7 : {
                    int n5;
                    int n6;
                    block8 : {
                        block9 : {
                            block6 : {
                                if (n2 >= n4) {
                                    arrby = Arrays.copyOf(arrby2, n);
                                    Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOf(this, newSize)");
                                    return arrby;
                                }
                                n3 = arrby.charAt(n2);
                                if (n3 < 128) {
                                    n6 = n3;
                                    n3 = n + 1;
                                    arrby2[n] = (byte)n6;
                                    ++n2;
                                    while (n2 < n4 && arrby.charAt(n2) < '') {
                                        arrby2[n3] = (byte)arrby.charAt(n2);
                                        ++n2;
                                        ++n3;
                                    }
                                    n = n3;
                                    continue;
                                }
                                if (n3 >= 2048) break block6;
                                n5 = n3 >> 6 | 192;
                                n6 = n + 1;
                                arrby2[n] = (byte)n5;
                                n = (byte)(n3 & 63 | 128);
                                n3 = n6 + 1;
                                arrby2[n6] = (byte)n;
                                break block7;
                            }
                            if (55296 > n3 || 57343 < n3) break block8;
                            if (n3 > 56319 || n4 <= (n5 = n2 + 1) || 56320 > (n6 = (int)arrby.charAt(n5)) || 57343 < n6) break block9;
                            n3 = (n3 << 10) + arrby.charAt(n5) - 56613888;
                            n5 = (byte)(n3 >> 18 | 240);
                            n6 = n + 1;
                            arrby2[n] = (byte)n5;
                            n5 = (byte)(n3 >> 12 & 63 | 128);
                            n = n6 + 1;
                            arrby2[n6] = (byte)n5;
                            n5 = (byte)(n3 >> 6 & 63 | 128);
                            n6 = n + 1;
                            arrby2[n] = (byte)n5;
                            n = (byte)(n3 & 63 | 128);
                            n3 = n6 + 1;
                            arrby2[n6] = (byte)n;
                            n2 += 2;
                            break block10;
                        }
                        n3 = n + 1;
                        arrby2[n] = (byte)63;
                        break block7;
                    }
                    n5 = (byte)(n3 >> 12 | 224);
                    n6 = n + 1;
                    arrby2[n] = (byte)n5;
                    n5 = (byte)(n3 >> 6 & 63 | 128);
                    n = n6 + 1;
                    arrby2[n6] = (byte)n5;
                    n6 = (byte)(n3 & 63 | 128);
                    n3 = n + 1;
                    arrby2[n] = (byte)n6;
                }
                ++n2;
            }
            n = n3;
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    public static final String commonToUtf8String(byte[] var0, int var1_1, int var2_2) {
        var3_3 = var1_1;
        Intrinsics.checkParameterIsNotNull(var0, "$this$commonToUtf8String");
        if (var3_3 < 0 || var2_2 > var0.length || var3_3 > var2_2) {
            var4_5 = new StringBuilder();
            var4_5.append("size=");
            var4_5.append(var0.length);
            var4_5.append(" beginIndex=");
            var4_5.append(var3_3);
            var4_5.append(" endIndex=");
            var4_5.append(var2_2);
            throw (Throwable)new ArrayIndexOutOfBoundsException(var4_5.toString());
        }
        var4_4 = new char[var2_2 - var3_3];
        var1_1 = 0;
        while (var3_3 < var2_2) {
            block36 : {
                block37 : {
                    block38 : {
                        block34 : {
                            block30 : {
                                block35 : {
                                    block31 : {
                                        block33 : {
                                            block32 : {
                                                var5_6 = var0[var3_3];
                                                if (var5_6 >= 0) {
                                                    var6_7 = var5_6;
                                                    var5_6 = var1_1 + 1;
                                                    var4_4[var1_1] = (char)var6_7;
                                                    var1_1 = var5_6;
                                                    var5_6 = ++var3_3;
                                                    do {
                                                        var3_3 = var5_6;
                                                        var6_7 = var1_1;
                                                        if (var5_6 < var2_2) {
                                                            var3_3 = var5_6;
                                                            var6_7 = var1_1++;
                                                            if (var0[var5_6] >= 0) {
                                                                var4_4[var1_1] = (char)var0[var5_6];
                                                                ++var5_6;
                                                                continue;
                                                            }
                                                        }
                                                        break block30;
                                                        break;
                                                    } while (true);
                                                }
                                                if (var5_6 >> 5 != -2) break block31;
                                                var5_6 = var3_3 + 1;
                                                if (var2_2 > var5_6) break block32;
                                                var6_7 = (char)65533;
                                                var5_6 = var1_1 + 1;
                                                var4_4[var1_1] = (char)var6_7;
                                                var1_1 = var5_6;
                                                ** GOTO lbl93
                                            }
                                            var6_7 = var0[var3_3];
                                            var7_8 = var0[var5_6];
                                            if ((var5_6 = (var7_8 & 192) == 128 ? 1 : 0) != 0) break block33;
                                            var6_7 = (char)65533;
                                            var5_6 = var1_1 + 1;
                                            var4_4[var1_1] = (char)var6_7;
                                            var1_1 = var5_6;
                                            ** GOTO lbl93
                                        }
                                        var5_6 = var7_8 ^ 3968 ^ var6_7 << 6;
                                        if (var5_6 < 128) {
                                            var6_7 = (char)65533;
                                            var5_6 = var1_1 + 1;
                                            var4_4[var1_1] = (char)var6_7;
                                            var1_1 = var5_6;
                                        } else {
                                            var6_7 = (char)var5_6;
                                            var5_6 = var1_1 + 1;
                                            var4_4[var1_1] = (char)var6_7;
                                            var1_1 = var5_6;
                                        }
                                        ** GOTO lbl101
                                    }
                                    if (var5_6 >> 4 != -2) break block34;
                                    var8_9 = var3_3 + 2;
                                    if (var2_2 > var8_9) break block35;
                                    var6_7 = (char)65533;
                                    var5_6 = var1_1 + 1;
                                    var4_4[var1_1] = (char)var6_7;
                                    var6_7 = var3_3 + 1;
                                    var1_1 = var5_6;
                                    if (var2_2 <= var6_7) ** GOTO lbl93
                                    var6_7 = (var0[var6_7] & 192) == 128 ? 1 : 0;
                                    var1_1 = var5_6;
                                    if (var6_7 != 0) ** GOTO lbl101
                                    var1_1 = var5_6;
                                    ** GOTO lbl93
                                }
                                var7_8 = var0[var3_3];
                                var6_7 = var0[var3_3 + 1];
                                var5_6 = (var6_7 & 192) == 128 ? 1 : 0;
                                if (var5_6 == 0) {
                                    var6_7 = (char)65533;
                                    var5_6 = var1_1 + 1;
                                    var4_4[var1_1] = (char)var6_7;
                                    var1_1 = var5_6;
lbl93: // 5 sources:
                                    var5_6 = 1;
                                } else {
                                    var5_6 = ((var8_9 = var0[var8_9]) & 192) == 128 ? 1 : 0;
                                    if (var5_6 == 0) {
                                        var6_7 = (char)65533;
                                        var5_6 = var1_1 + 1;
                                        var4_4[var1_1] = (char)var6_7;
                                        var1_1 = var5_6;
lbl101: // 4 sources:
                                        var5_6 = 2;
                                    } else {
                                        var5_6 = var8_9 ^ -123008 ^ var6_7 << 6 ^ var7_8 << 12;
                                        if (var5_6 < 2048) {
                                            var6_7 = (char)65533;
                                            var5_6 = var1_1 + 1;
                                            var4_4[var1_1] = (char)var6_7;
                                            var1_1 = var5_6;
                                        } else if (55296 <= var5_6 && 57343 >= var5_6) {
                                            var6_7 = (char)65533;
                                            var5_6 = var1_1 + 1;
                                            var4_4[var1_1] = (char)var6_7;
                                            var1_1 = var5_6;
                                        } else {
                                            var6_7 = (char)var5_6;
                                            var5_6 = var1_1 + 1;
                                            var4_4[var1_1] = (char)var6_7;
                                            var1_1 = var5_6;
                                        }
                                        var5_6 = 3;
                                    }
                                }
                                var3_3 += var5_6;
                                var6_7 = var1_1;
                            }
                            var1_1 = var6_7;
                            continue;
                        }
                        if (var5_6 >> 3 != -2) break block36;
                        var9_10 = var3_3 + 3;
                        if (var2_2 > var9_10) break block37;
                        var5_6 = var1_1 + 1;
                        var4_4[var1_1] = (char)65533;
                        var6_7 = var3_3 + 1;
                        var1_1 = var5_6;
                        if (var2_2 <= var6_7) ** GOTO lbl156
                        var1_1 = (var0[var6_7] & 192) == 128 ? 1 : 0;
                        if (var1_1 != 0) break block38;
                        var1_1 = var5_6;
                        ** GOTO lbl156
                    }
                    var6_7 = var3_3 + 2;
                    var1_1 = var5_6;
                    if (var2_2 <= var6_7) ** GOTO lbl164
                    var6_7 = (var0[var6_7] & 192) == 128 ? 1 : 0;
                    var1_1 = var5_6;
                    if (var6_7 != 0) ** GOTO lbl171
                    var1_1 = var5_6;
                    ** GOTO lbl164
                }
                var6_7 = var0[var3_3];
                var7_8 = var0[var3_3 + 1];
                var5_6 = (var7_8 & 192) == 128 ? 1 : 0;
                if (var5_6 == 0) {
                    var5_6 = var1_1 + 1;
                    var4_4[var1_1] = (char)65533;
                    var1_1 = var5_6;
lbl156: // 3 sources:
                    var5_6 = 1;
                } else {
                    var8_9 = var0[var3_3 + 2];
                    var5_6 = (var8_9 & 192) == 128 ? 1 : 0;
                    if (var5_6 == 0) {
                        var5_6 = var1_1 + 1;
                        var4_4[var1_1] = (char)65533;
                        var1_1 = var5_6;
lbl164: // 3 sources:
                        var5_6 = 2;
                    } else {
                        var5_6 = ((var9_10 = var0[var9_10]) & 192) == 128 ? 1 : 0;
                        if (var5_6 == 0) {
                            var5_6 = var1_1 + 1;
                            var4_4[var1_1] = (char)65533;
                            var1_1 = var5_6;
lbl171: // 2 sources:
                            var5_6 = 3;
                        } else {
                            if ((var6_7 = var9_10 ^ 3678080 ^ var8_9 << 6 ^ var7_8 << 12 ^ var6_7 << 18) > 1114111) {
                                var5_6 = var1_1 + 1;
                                var4_4[var1_1] = (char)65533;
                                var1_1 = var5_6;
                            } else if (55296 <= var6_7 && 57343 >= var6_7) {
                                var5_6 = var1_1 + 1;
                                var4_4[var1_1] = (char)65533;
                                var1_1 = var5_6;
                            } else if (var6_7 < 65536) {
                                var5_6 = var1_1 + 1;
                                var4_4[var1_1] = (char)65533;
                                var1_1 = var5_6;
                            } else if (var6_7 != 65533) {
                                var7_8 = (char)((var6_7 >>> 10) + 55232);
                                var5_6 = var1_1 + 1;
                                var4_4[var1_1] = var7_8;
                                var6_7 = (char)((var6_7 & 1023) + 56320);
                                var1_1 = var5_6 + 1;
                                var4_4[var5_6] = (char)var6_7;
                            } else {
                                var5_6 = var1_1 + 1;
                                var4_4[var1_1] = (char)65533;
                                var1_1 = var5_6;
                            }
                            var5_6 = 4;
                        }
                    }
                }
                var3_3 += var5_6;
                continue;
            }
            var5_6 = var1_1 + 1;
            var4_4[var1_1] = (char)65533;
            ++var3_3;
            var1_1 = var5_6;
        }
        return new String(var4_4, 0, var1_1);
    }

    public static /* synthetic */ String commonToUtf8String$default(byte[] arrby, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) == 0) return _Utf8Kt.commonToUtf8String(arrby, n, n2);
        n2 = arrby.length;
        return _Utf8Kt.commonToUtf8String(arrby, n, n2);
    }
}

