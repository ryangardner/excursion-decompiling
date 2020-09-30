/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okio.Buffer;
import okio.ByteString;
import okio._Base64;
import okio._Platform;
import okio._Util;

@Metadata(bv={1, 0, 3}, d1={"\u0000P\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0018\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0002\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007H\b\u001a\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a\r\u0010\u000f\u001a\u00020\u0010*\u00020\nH\b\u001a\r\u0010\u0011\u001a\u00020\u0010*\u00020\nH\b\u001a\u0015\u0010\u0012\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\nH\b\u001a\u000f\u0010\u0014\u001a\u0004\u0018\u00010\n*\u00020\u0010H\b\u001a\r\u0010\u0015\u001a\u00020\n*\u00020\u0010H\b\u001a\r\u0010\u0016\u001a\u00020\n*\u00020\u0010H\b\u001a\u0015\u0010\u0017\u001a\u00020\u0018*\u00020\n2\u0006\u0010\u0019\u001a\u00020\u0007H\b\u001a\u0015\u0010\u0017\u001a\u00020\u0018*\u00020\n2\u0006\u0010\u0019\u001a\u00020\nH\b\u001a\u0017\u0010\u001a\u001a\u00020\u0018*\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\u001bH\b\u001a\u0015\u0010\u001c\u001a\u00020\u001d*\u00020\n2\u0006\u0010\u001e\u001a\u00020\u0005H\b\u001a\r\u0010\u001f\u001a\u00020\u0005*\u00020\nH\b\u001a\r\u0010 \u001a\u00020\u0005*\u00020\nH\b\u001a\r\u0010!\u001a\u00020\u0010*\u00020\nH\b\u001a\u001d\u0010\"\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0005H\b\u001a\r\u0010$\u001a\u00020\u0007*\u00020\nH\b\u001a\u001d\u0010%\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0005H\b\u001a\u001d\u0010%\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010#\u001a\u00020\u0005H\b\u001a-\u0010&\u001a\u00020\u0018*\u00020\n2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\b\u001a-\u0010&\u001a\u00020\u0018*\u00020\n2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\b\u001a\u0015\u0010*\u001a\u00020\u0018*\u00020\n2\u0006\u0010+\u001a\u00020\u0007H\b\u001a\u0015\u0010*\u001a\u00020\u0018*\u00020\n2\u0006\u0010+\u001a\u00020\nH\b\u001a\u001d\u0010,\u001a\u00020\n*\u00020\n2\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005H\b\u001a\r\u0010/\u001a\u00020\n*\u00020\nH\b\u001a\r\u00100\u001a\u00020\n*\u00020\nH\b\u001a\r\u00101\u001a\u00020\u0007*\u00020\nH\b\u001a\u001d\u00102\u001a\u00020\n*\u00020\u00072\u0006\u0010'\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\b\u001a\r\u00103\u001a\u00020\u0010*\u00020\nH\b\u001a\r\u00104\u001a\u00020\u0010*\u00020\nH\b\u001a$\u00105\u001a\u000206*\u00020\n2\u0006\u00107\u001a\u0002082\u0006\u0010'\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\u00a8\u00069"}, d2={"HEX_DIGIT_CHARS", "", "getHEX_DIGIT_CHARS", "()[C", "codePointIndexToCharIndex", "", "s", "", "codePointCount", "commonOf", "Lokio/ByteString;", "data", "decodeHexDigit", "c", "", "commonBase64", "", "commonBase64Url", "commonCompareTo", "other", "commonDecodeBase64", "commonDecodeHex", "commonEncodeUtf8", "commonEndsWith", "", "suffix", "commonEquals", "", "commonGetByte", "", "pos", "commonGetSize", "commonHashCode", "commonHex", "commonIndexOf", "fromIndex", "commonInternalArray", "commonLastIndexOf", "commonRangeEquals", "offset", "otherOffset", "byteCount", "commonStartsWith", "prefix", "commonSubstring", "beginIndex", "endIndex", "commonToAsciiLowercase", "commonToAsciiUppercase", "commonToByteArray", "commonToByteString", "commonToString", "commonUtf8", "commonWrite", "", "buffer", "Lokio/Buffer;", "okio"}, k=2, mv={1, 1, 16})
public final class ByteStringKt {
    private static final char[] HEX_DIGIT_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /*
     * Unable to fully structure code
     */
    private static final int codePointIndexToCharIndex(byte[] var0, int var1_1) {
        var2_2 = var0.length;
        var3_3 = 0;
        var4_4 = 0;
        var5_5 = 0;
        block0 : do {
            if (var3_3 >= var2_2) return var4_4;
            var6_6 = var0[var3_3];
            if (var6_6 >= 0) {
                var7_7 = var5_5 + 1;
                if (var5_5 == var1_1) {
                    return var4_4;
                }
                if (var6_6 != 10 && var6_6 != 13) {
                    var5_5 = var6_6 >= 0 && 31 >= var6_6 || 127 <= var6_6 && 159 >= var6_6 ? 1 : 0;
                    if (var5_5 != 0) return -1;
                }
                if (var6_6 == 65533) {
                    return -1;
                }
            } else {
                if (var6_6 >> 5 == -2) {
                    var7_7 = var3_3 + 1;
                    if (var2_2 <= var7_7) {
                        if (var5_5 != var1_1) return -1;
                        return var4_4;
                    }
                    var6_6 = var0[var3_3];
                    var8_8 = var0[var7_7];
                    if ((var7_7 = (var8_8 & 192) == 128 ? 1 : 0) == 0) {
                        if (var5_5 != var1_1) return -1;
                        return var4_4;
                    }
                    if ((var6_6 = var8_8 ^ 3968 ^ var6_6 << 6) < 128) {
                        if (var5_5 != var1_1) return -1;
                        return var4_4;
                    }
                    var7_7 = var5_5 + 1;
                    if (var5_5 == var1_1) {
                        return var4_4;
                    }
                    if (var6_6 != 10 && var6_6 != 13) {
                        var5_5 = var6_6 >= 0 && 31 >= var6_6 || 127 <= var6_6 && 159 >= var6_6 ? 1 : 0;
                        if (var5_5 != 0) return -1;
                    }
                    if (var6_6 == 65533) {
                        return -1;
                    }
                    var5_5 = var6_6 < 65536 ? 1 : 2;
                    var4_4 += var5_5;
                    var3_3 += 2;
                    var5_5 = var7_7;
                    continue;
                }
                if (var6_6 >> 4 == -2) {
                    var9_9 = var3_3 + 2;
                    if (var2_2 <= var9_9) {
                        if (var5_5 != var1_1) return -1;
                        return var4_4;
                    }
                    var8_8 = var0[var3_3];
                    var6_6 = var0[var3_3 + 1];
                    var7_7 = (var6_6 & 192) == 128 ? 1 : 0;
                    if (var7_7 == 0) {
                        if (var5_5 != var1_1) return -1;
                        return var4_4;
                    }
                    var7_7 = ((var9_9 = var0[var9_9]) & 192) == 128 ? 1 : 0;
                    if (var7_7 == 0) {
                        if (var5_5 != var1_1) return -1;
                        return var4_4;
                    }
                    if ((var6_6 = var9_9 ^ -123008 ^ var6_6 << 6 ^ var8_8 << 12) < 2048) {
                        if (var5_5 != var1_1) return -1;
                        return var4_4;
                    }
                    if (55296 <= var6_6 && 57343 >= var6_6) {
                        if (var5_5 != var1_1) return -1;
                        return var4_4;
                    }
                    var7_7 = var5_5 + 1;
                    if (var5_5 == var1_1) {
                        return var4_4;
                    }
                    if (var6_6 != 10 && var6_6 != 13) {
                        var5_5 = var6_6 >= 0 && 31 >= var6_6 || 127 <= var6_6 && 159 >= var6_6 ? 1 : 0;
                        if (var5_5 != 0) return -1;
                    }
                    if (var6_6 == 65533) {
                        return -1;
                    }
                    var5_5 = var6_6 < 65536 ? 1 : 2;
                    var4_4 += var5_5;
                    var3_3 += 3;
                    var5_5 = var7_7;
                    continue;
                }
                if (var6_6 >> 3 != -2) {
                    if (var5_5 != var1_1) return -1;
                    return var4_4;
                }
                var10_10 = var3_3 + 3;
                if (var2_2 <= var10_10) {
                    if (var5_5 != var1_1) return -1;
                    return var4_4;
                }
                var8_8 = var0[var3_3];
                var6_6 = var0[var3_3 + 1];
                var7_7 = (var6_6 & 192) == 128 ? 1 : 0;
                if (var7_7 == 0) {
                    if (var5_5 != var1_1) return -1;
                    return var4_4;
                }
                var9_9 = var0[var3_3 + 2];
                var7_7 = (var9_9 & 192) == 128 ? 1 : 0;
                if (var7_7 == 0) {
                    if (var5_5 != var1_1) return -1;
                    return var4_4;
                }
                var7_7 = ((var10_10 = var0[var10_10]) & 192) == 128 ? 1 : 0;
                if (var7_7 == 0) {
                    if (var5_5 != var1_1) return -1;
                    return var4_4;
                }
                if ((var6_6 = var10_10 ^ 3678080 ^ var9_9 << 6 ^ var6_6 << 12 ^ var8_8 << 18) > 1114111) {
                    if (var5_5 != var1_1) return -1;
                    return var4_4;
                }
                if (55296 <= var6_6 && 57343 >= var6_6) {
                    if (var5_5 != var1_1) return -1;
                    return var4_4;
                }
                if (var6_6 < 65536) {
                    if (var5_5 != var1_1) return -1;
                    return var4_4;
                }
                var7_7 = var5_5 + 1;
                if (var5_5 == var1_1) {
                    return var4_4;
                }
                if (var6_6 != 10 && var6_6 != 13) {
                    var5_5 = var6_6 >= 0 && 31 >= var6_6 || 127 <= var6_6 && 159 >= var6_6 ? 1 : 0;
                    if (var5_5 != 0) return -1;
                }
                if (var6_6 == 65533) {
                    return -1;
                }
                var5_5 = var6_6 < 65536 ? 1 : 2;
                var4_4 += var5_5;
                var3_3 += 4;
                var5_5 = var7_7;
                continue;
            }
            var5_5 = var6_6 < 65536 ? 1 : 2;
            var6_6 = var3_3 + 1;
            var3_3 = var7_7;
            var7_7 = var4_4 += var5_5;
            do {
                var8_8 = var3_3;
                var3_3 = var6_6;
                var4_4 = var7_7;
                var5_5 = var8_8;
                if (var6_6 >= var2_2) continue block0;
                var3_3 = var6_6;
                var4_4 = var7_7;
                var5_5 = var8_8;
                if (var0[var6_6] < 0) ** break;
                var5_5 = var0[var6_6];
                var4_4 = var8_8 + 1;
                if (var8_8 == var1_1) {
                    return var7_7;
                }
                if (var5_5 != 10 && var5_5 != 13) {
                    var3_3 = var5_5 >= 0 && 31 >= var5_5 || 127 <= var5_5 && 159 >= var5_5 ? 1 : 0;
                    if (var3_3 != 0) return -1;
                }
                if (var5_5 == 65533) {
                    return -1;
                }
                var3_3 = var5_5 < 65536 ? 1 : 2;
                var7_7 += var3_3;
                ++var6_6;
                var3_3 = var4_4;
            } while (true);
            break;
        } while (true);
    }

    public static final String commonBase64(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonBase64");
        return _Base64.encodeBase64$default(byteString.getData$okio(), null, 1, null);
    }

    public static final String commonBase64Url(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonBase64Url");
        return _Base64.encodeBase64(byteString.getData$okio(), _Base64.getBASE64_URL_SAFE());
    }

    public static final int commonCompareTo(ByteString byteString, ByteString byteString2) {
        int n;
        int n2;
        int n3;
        block2 : {
            int n4;
            int n5;
            Intrinsics.checkParameterIsNotNull(byteString, "$this$commonCompareTo");
            Intrinsics.checkParameterIsNotNull(byteString2, "other");
            n = byteString.size();
            n3 = byteString2.size();
            int n6 = Math.min(n, n3);
            int n7 = 0;
            do {
                n2 = -1;
                if (n7 >= n6) break block2;
                n4 = byteString.getByte(n7) & 255;
                if (n4 != (n5 = byteString2.getByte(n7) & 255)) break;
                ++n7;
            } while (true);
            if (n4 >= n5) return 1;
            return n2;
        }
        if (n == n3) {
            return 0;
        }
        if (n >= n3) return 1;
        return n2;
    }

    public static final ByteString commonDecodeBase64(String object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$commonDecodeBase64");
        object = _Base64.decodeBase64ToArray((String)object);
        if (object == null) return null;
        return new ByteString((byte[])object);
    }

    public static final ByteString commonDecodeHex(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$commonDecodeHex");
        int n = string2.length();
        int n2 = 0;
        n = n % 2 == 0 ? 1 : 0;
        if (n == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected hex string: ");
            stringBuilder.append(string2);
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        int n3 = string2.length() / 2;
        byte[] arrby = new byte[n3];
        n = n2;
        while (n < n3) {
            n2 = n * 2;
            arrby[n] = (byte)((ByteStringKt.decodeHexDigit(string2.charAt(n2)) << 4) + ByteStringKt.decodeHexDigit(string2.charAt(n2 + 1)));
            ++n;
        }
        return new ByteString(arrby);
    }

    public static final ByteString commonEncodeUtf8(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "$this$commonEncodeUtf8");
        ByteString byteString = new ByteString(_Platform.asUtf8ToByteArray(string2));
        byteString.setUtf8$okio(string2);
        return byteString;
    }

    public static final boolean commonEndsWith(ByteString byteString, ByteString byteString2) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonEndsWith");
        Intrinsics.checkParameterIsNotNull(byteString2, "suffix");
        return byteString.rangeEquals(byteString.size() - byteString2.size(), byteString2, 0, byteString2.size());
    }

    public static final boolean commonEndsWith(ByteString byteString, byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonEndsWith");
        Intrinsics.checkParameterIsNotNull(arrby, "suffix");
        return byteString.rangeEquals(byteString.size() - arrby.length, arrby, 0, arrby.length);
    }

    public static final boolean commonEquals(ByteString byteString, Object object) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonEquals");
        boolean bl = true;
        if (object == byteString) {
            return bl;
        }
        if (!(object instanceof ByteString)) return false;
        if (((ByteString)(object = (ByteString)object)).size() != byteString.getData$okio().length) return false;
        if (!((ByteString)object).rangeEquals(0, byteString.getData$okio(), 0, byteString.getData$okio().length)) return false;
        return bl;
    }

    public static final byte commonGetByte(ByteString byteString, int n) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonGetByte");
        return byteString.getData$okio()[n];
    }

    public static final int commonGetSize(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonGetSize");
        return byteString.getData$okio().length;
    }

    public static final int commonHashCode(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonHashCode");
        int n = byteString.getHashCode$okio();
        if (n != 0) {
            return n;
        }
        n = Arrays.hashCode(byteString.getData$okio());
        byteString.setHashCode$okio(n);
        return n;
    }

    public static final String commonHex(ByteString arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$commonHex");
        char[] arrc = new char[arrby.getData$okio().length * 2];
        arrby = arrby.getData$okio();
        int n = arrby.length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            byte by = arrby[n2];
            int n4 = n3 + 1;
            arrc[n3] = ByteStringKt.getHEX_DIGIT_CHARS()[by >> 4 & 15];
            n3 = n4 + 1;
            arrc[n4] = ByteStringKt.getHEX_DIGIT_CHARS()[by & 15];
            ++n2;
        }
        return new String(arrc);
    }

    public static final int commonIndexOf(ByteString byteString, byte[] arrby, int n) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonIndexOf");
        Intrinsics.checkParameterIsNotNull(arrby, "other");
        int n2 = byteString.getData$okio().length - arrby.length;
        n = Math.max(n, 0);
        if (n > n2) return -1;
        while (!_Util.arrayRangeEquals(byteString.getData$okio(), n, arrby, 0, arrby.length)) {
            if (n == n2) return -1;
            ++n;
        }
        return n;
    }

    public static final byte[] commonInternalArray(ByteString byteString) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonInternalArray");
        return byteString.getData$okio();
    }

    public static final int commonLastIndexOf(ByteString byteString, ByteString byteString2, int n) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonLastIndexOf");
        Intrinsics.checkParameterIsNotNull(byteString2, "other");
        return byteString.lastIndexOf(byteString2.internalArray$okio(), n);
    }

    public static final int commonLastIndexOf(ByteString byteString, byte[] arrby, int n) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonLastIndexOf");
        Intrinsics.checkParameterIsNotNull(arrby, "other");
        n = Math.min(n, byteString.getData$okio().length - arrby.length);
        while (n >= 0) {
            if (_Util.arrayRangeEquals(byteString.getData$okio(), n, arrby, 0, arrby.length)) {
                return n;
            }
            --n;
        }
        return -1;
    }

    public static final ByteString commonOf(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "data");
        arrby = Arrays.copyOf(arrby, arrby.length);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOf(this, size)");
        return new ByteString(arrby);
    }

    public static final boolean commonRangeEquals(ByteString byteString, int n, ByteString byteString2, int n2, int n3) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonRangeEquals");
        Intrinsics.checkParameterIsNotNull(byteString2, "other");
        return byteString2.rangeEquals(n2, byteString.getData$okio(), n, n3);
    }

    public static final boolean commonRangeEquals(ByteString byteString, int n, byte[] arrby, int n2, int n3) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonRangeEquals");
        Intrinsics.checkParameterIsNotNull(arrby, "other");
        if (n < 0) return false;
        if (n > byteString.getData$okio().length - n3) return false;
        if (n2 < 0) return false;
        if (n2 > arrby.length - n3) return false;
        if (!_Util.arrayRangeEquals(byteString.getData$okio(), n, arrby, n2, n3)) return false;
        return true;
    }

    public static final boolean commonStartsWith(ByteString byteString, ByteString byteString2) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonStartsWith");
        Intrinsics.checkParameterIsNotNull(byteString2, "prefix");
        return byteString.rangeEquals(0, byteString2, 0, byteString2.size());
    }

    public static final boolean commonStartsWith(ByteString byteString, byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonStartsWith");
        Intrinsics.checkParameterIsNotNull(arrby, "prefix");
        return byteString.rangeEquals(0, arrby, 0, arrby.length);
    }

    public static final ByteString commonSubstring(ByteString byteString, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonSubstring");
        boolean bl = true;
        boolean bl2 = n >= 0;
        if (!bl2) throw (Throwable)new IllegalArgumentException("beginIndex < 0".toString());
        bl2 = n2 <= byteString.getData$okio().length;
        if (!bl2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("endIndex > length(");
            stringBuilder.append(byteString.getData$okio().length);
            stringBuilder.append(')');
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
        }
        bl2 = n2 - n >= 0 ? bl : false;
        if (!bl2) throw (Throwable)new IllegalArgumentException("endIndex < beginIndex".toString());
        if (n != 0) return new ByteString(ArraysKt.copyOfRange(byteString.getData$okio(), n, n2));
        if (n2 != byteString.getData$okio().length) return new ByteString(ArraysKt.copyOfRange(byteString.getData$okio(), n, n2));
        return byteString;
    }

    public static final ByteString commonToAsciiLowercase(ByteString arrby) {
        int n;
        byte by;
        byte by2;
        byte by3;
        block4 : {
            Intrinsics.checkParameterIsNotNull(arrby, "$this$commonToAsciiLowercase");
            n = 0;
            while (n < arrby.getData$okio().length) {
                by = arrby.getData$okio()[n];
                if (by < (by3 = (byte)65) || by > (by2 = (byte)90)) {
                    ++n;
                    continue;
                }
                break block4;
            }
            return arrby;
        }
        arrby = arrby.getData$okio();
        arrby = Arrays.copyOf(arrby, arrby.length);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOf(this, size)");
        int n2 = n + 1;
        arrby[n] = (byte)(by + 32);
        n = n2;
        while (n < arrby.length) {
            n2 = arrby[n];
            if (n2 >= by3 && n2 <= by2) {
                arrby[n] = (byte)(n2 + 32);
            }
            ++n;
        }
        return new ByteString(arrby);
    }

    public static final ByteString commonToAsciiUppercase(ByteString arrby) {
        int n;
        byte by;
        byte by2;
        byte by3;
        block4 : {
            Intrinsics.checkParameterIsNotNull(arrby, "$this$commonToAsciiUppercase");
            n = 0;
            while (n < arrby.getData$okio().length) {
                by = arrby.getData$okio()[n];
                if (by < (by3 = (byte)97) || by > (by2 = (byte)122)) {
                    ++n;
                    continue;
                }
                break block4;
            }
            return arrby;
        }
        arrby = arrby.getData$okio();
        arrby = Arrays.copyOf(arrby, arrby.length);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOf(this, size)");
        int n2 = n + 1;
        arrby[n] = (byte)(by - 32);
        n = n2;
        while (n < arrby.length) {
            n2 = arrby[n];
            if (n2 >= by3 && n2 <= by2) {
                arrby[n] = (byte)(n2 - 32);
            }
            ++n;
        }
        return new ByteString(arrby);
    }

    public static final byte[] commonToByteArray(ByteString arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$commonToByteArray");
        arrby = arrby.getData$okio();
        arrby = Arrays.copyOf(arrby, arrby.length);
        Intrinsics.checkExpressionValueIsNotNull(arrby, "java.util.Arrays.copyOf(this, size)");
        return arrby;
    }

    public static final ByteString commonToByteString(byte[] arrby, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$commonToByteString");
        _Util.checkOffsetAndCount(arrby.length, n, n2);
        return new ByteString(ArraysKt.copyOfRange(arrby, n, n2 + n));
    }

    public static final String commonToString(ByteString object) {
        Object object2 = object;
        Intrinsics.checkParameterIsNotNull(object2, "$this$commonToString");
        int n = ((ByteString)object).getData$okio().length;
        int n2 = 1;
        if (n == 0) {
            return "[size=0]";
        }
        n = 0;
        if (n != 0) {
            return "[size=0]";
        }
        n = ByteStringKt.codePointIndexToCharIndex(((ByteString)object).getData$okio(), 64);
        if (n == -1) {
            if (((ByteString)object).getData$okio().length <= 64) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("[hex=");
                ((StringBuilder)object2).append(((ByteString)object).hex());
                ((StringBuilder)object2).append(']');
                return ((StringBuilder)object2).toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[size=");
            stringBuilder.append(((ByteString)object).getData$okio().length);
            stringBuilder.append(" hex=");
            n = 64 <= ((ByteString)object).getData$okio().length ? n2 : 0;
            if (n == 0) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("endIndex > length(");
                ((StringBuilder)object2).append(((ByteString)object).getData$okio().length);
                ((StringBuilder)object2).append(')');
                throw (Throwable)new IllegalArgumentException(((StringBuilder)object2).toString().toString());
            }
            if (64 != ((ByteString)object).getData$okio().length) {
                object2 = new ByteString(ArraysKt.copyOfRange(((ByteString)object).getData$okio(), 0, 64));
            }
            stringBuilder.append(((ByteString)object2).hex());
            stringBuilder.append("\u2026]");
            return stringBuilder.toString();
        }
        CharSequence charSequence = ((ByteString)object).utf8();
        if (charSequence == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        object2 = ((String)charSequence).substring(0, n);
        Intrinsics.checkExpressionValueIsNotNull(object2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        object2 = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default((String)object2, "\\", "\\\\", false, 4, null), "\n", "\\n", false, 4, null), "\r", "\\r", false, 4, null);
        if (n < ((String)charSequence).length()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[size=");
            ((StringBuilder)charSequence).append(((ByteString)object).getData$okio().length);
            ((StringBuilder)charSequence).append(" text=");
            ((StringBuilder)charSequence).append((String)object2);
            ((StringBuilder)charSequence).append("\u2026]");
            return ((StringBuilder)charSequence).toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("[text=");
        ((StringBuilder)object).append((String)object2);
        ((StringBuilder)object).append(']');
        return ((StringBuilder)object).toString();
    }

    public static final String commonUtf8(ByteString byteString) {
        String string2;
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonUtf8");
        String string3 = string2 = byteString.getUtf8$okio();
        if (string2 != null) return string3;
        string3 = _Platform.toUtf8String(byteString.internalArray$okio());
        byteString.setUtf8$okio(string3);
        return string3;
    }

    public static final void commonWrite(ByteString byteString, Buffer buffer, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(byteString, "$this$commonWrite");
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        buffer.write(byteString.getData$okio(), n, n2);
    }

    private static final int decodeHexDigit(char c) {
        if ('0' <= c && '9' >= c) {
            return c - 48;
        }
        int n = 97;
        if ('a' <= c) {
            if ('f' >= c) return c - n + 10;
        }
        n = 65;
        if ('A' <= c && 'F' >= c) {
            return c - n + 10;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected hex digit: ");
        stringBuilder.append(c);
        throw (Throwable)new IllegalArgumentException(stringBuilder.toString());
    }

    public static final char[] getHEX_DIGIT_CHARS() {
        return HEX_DIGIT_CHARS;
    }
}

