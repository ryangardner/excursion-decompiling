/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.ByteString;
import okio._Platform;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0012\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0001*\u00020\u0007H\u0000\u001a\u0016\u0010\b\u001a\u00020\u0007*\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\u0001H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0001X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003\u00a8\u0006\n"}, d2={"BASE64", "", "getBASE64", "()[B", "BASE64_URL_SAFE", "getBASE64_URL_SAFE", "decodeBase64ToArray", "", "encodeBase64", "map", "okio"}, k=2, mv={1, 1, 16})
public final class _Base64 {
    private static final byte[] BASE64 = ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/").getData$okio();
    private static final byte[] BASE64_URL_SAFE = ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_").getData$okio();

    /*
     * Unable to fully structure code
     */
    public static final byte[] decodeBase64ToArray(String var0) {
        Intrinsics.checkParameterIsNotNull(var0, "$this$decodeBase64ToArray");
        for (var1_1 = var0.length(); var1_1 > 0 && ((var2_2 = var0.charAt(var1_1 - 1)) == 61 || var2_2 == 10 || var2_2 == 13 || var2_2 == 32 || var2_2 == 9); --var1_1) {
        }
        var3_3 = (int)((long)var1_1 * 6L / 8L);
        var4_4 = new byte[var3_3];
        var6_6 = 0;
        var7_7 = 0;
        var8_8 = 0;
        for (var5_5 = 0; var5_5 < var1_1; ++var5_5) {
            block16 : {
                block11 : {
                    block14 : {
                        block15 : {
                            block13 : {
                                block12 : {
                                    block10 : {
                                        var9_9 = var0.charAt(var5_5);
                                        if ('A' > var9_9 || 'Z' < var9_9) break block10;
                                        var2_2 = var9_9 - 65;
                                        break block11;
                                    }
                                    if ('a' > var9_9 || 'z' < var9_9) break block12;
                                    var2_2 = var9_9 - 71;
                                    break block11;
                                }
                                if ('0' > var9_9 || '9' < var9_9) break block13;
                                var2_2 = var9_9 + 4;
                                break block11;
                            }
                            if (var9_9 == '+' || var9_9 == '-') break block14;
                            if (var9_9 == '/' || var9_9 == '_') break block15;
                            var2_2 = var6_6;
                            var10_10 = var7_7;
                            var11_11 = var8_8;
                            if (var9_9 != '\n') {
                                var2_2 = var6_6;
                                var10_10 = var7_7;
                                var11_11 = var8_8;
                                if (var9_9 != '\r') {
                                    var2_2 = var6_6;
                                    var10_10 = var7_7;
                                    var11_11 = var8_8;
                                    if (var9_9 != ' ') {
                                        if (var9_9 != '\t') return null;
                                        var2_2 = var6_6;
                                        var10_10 = var7_7;
                                        var11_11 = var8_8;
                                        ** GOTO lbl62
                                    }
                                }
                            }
                            break block16;
                        }
                        var2_2 = 63;
                        break block11;
                    }
                    var2_2 = 62;
                }
                var7_7 = var7_7 << 6 | var2_2;
                var2_2 = ++var6_6;
                var10_10 = var7_7;
                var11_11 = var8_8;
                if (var6_6 % 4 == 0) {
                    var2_2 = var8_8 + 1;
                    var4_4[var8_8] = (byte)(var7_7 >> 16);
                    var8_8 = var2_2 + 1;
                    var4_4[var2_2] = (byte)(var7_7 >> 8);
                    var4_4[var8_8] = (byte)var7_7;
                    var11_11 = var8_8 + 1;
                    var10_10 = var7_7;
                    var2_2 = var6_6;
                }
            }
            var6_6 = var2_2;
            var7_7 = var10_10;
            var8_8 = var11_11;
        }
        var2_2 = var6_6 % 4;
        if (var2_2 == 1) return null;
        if (var2_2 != 2) {
            if (var2_2 == 3) {
                var1_1 = var7_7 << 6;
                var2_2 = var8_8 + 1;
                var4_4[var8_8] = (byte)(var1_1 >> 16);
                var8_8 = var2_2 + 1;
                var4_4[var2_2] = (byte)(var1_1 >> 8);
            }
        } else {
            var4_4[var8_8] = (byte)(var7_7 << 12 >> 16);
            ++var8_8;
        }
        if (var8_8 == var3_3) {
            return var4_4;
        }
        var0 = Arrays.copyOf(var4_4, var8_8);
        Intrinsics.checkExpressionValueIsNotNull(var0, "java.util.Arrays.copyOf(this, newSize)");
        return var0;
    }

    public static final String encodeBase64(byte[] arrby, byte[] arrby2) {
        int n;
        int n2;
        Intrinsics.checkParameterIsNotNull(arrby, "$this$encodeBase64");
        Intrinsics.checkParameterIsNotNull(arrby2, "map");
        byte[] arrby3 = new byte[(arrby.length + 2) / 3 * 4];
        int n3 = arrby.length - arrby.length % 3;
        int n4 = 0;
        for (n = 0; n < n3; ++n) {
            n2 = n + 1;
            int n5 = arrby[n];
            n = n2 + 1;
            byte by = arrby[n2];
            n2 = arrby[n];
            int n6 = n4 + 1;
            arrby3[n4] = arrby2[(n5 & 255) >> 2];
            n4 = n6 + 1;
            arrby3[n6] = arrby2[(n5 & 3) << 4 | (by & 255) >> 4];
            n5 = n4 + 1;
            arrby3[n4] = arrby2[(by & 15) << 2 | (n2 & 255) >> 6];
            n4 = n5 + 1;
            arrby3[n5] = arrby2[n2 & 63];
        }
        if ((n3 = arrby.length - n3) == 1) {
            n = arrby[n];
            n3 = n4 + 1;
            arrby3[n4] = arrby2[(n & 255) >> 2];
            n4 = n3 + 1;
            arrby3[n3] = arrby2[(n & 3) << 4];
            n = (byte)61;
            arrby3[n4] = (byte)n;
            arrby3[n4 + 1] = (byte)n;
            return _Platform.toUtf8String(arrby3);
        }
        if (n3 != 2) {
            return _Platform.toUtf8String(arrby3);
        }
        n3 = arrby[n];
        n = arrby[n + 1];
        n2 = n4 + 1;
        arrby3[n4] = arrby2[(n3 & 255) >> 2];
        n4 = n2 + 1;
        arrby3[n2] = arrby2[(n3 & 3) << 4 | (n & 255) >> 4];
        arrby3[n4] = arrby2[(n & 15) << 2];
        arrby3[n4 + 1] = (byte)61;
        return _Platform.toUtf8String(arrby3);
    }

    public static /* synthetic */ String encodeBase64$default(byte[] arrby, byte[] arrby2, int n, Object object) {
        if ((n & 1) == 0) return _Base64.encodeBase64(arrby, arrby2);
        arrby2 = BASE64;
        return _Base64.encodeBase64(arrby, arrby2);
    }

    public static final byte[] getBASE64() {
        return BASE64;
    }

    public static final byte[] getBASE64_URL_SAFE() {
        return BASE64_URL_SAFE;
    }
}

