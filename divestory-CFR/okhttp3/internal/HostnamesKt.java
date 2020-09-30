/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal;

import java.io.Serializable;
import java.net.IDN;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okio.Buffer;

@Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0002\u001a\"\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u001a\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\f\u0010\r\u001a\u00020\u0001*\u00020\u0003H\u0002\u001a\f\u0010\u000e\u001a\u0004\u0018\u00010\u0003*\u00020\u0003\u00a8\u0006\u000f"}, d2={"decodeIpv4Suffix", "", "input", "", "pos", "", "limit", "address", "", "addressOffset", "decodeIpv6", "Ljava/net/InetAddress;", "inet6AddressToAscii", "containsInvalidHostnameAsciiCodes", "toCanonicalHost", "okhttp"}, k=2, mv={1, 1, 16})
public final class HostnamesKt {
    private static final boolean containsInvalidHostnameAsciiCodes(String string2) {
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            char c = string2.charAt(n2);
            if (c <= '\u001f') return true;
            if (c >= '') {
                return true;
            }
            if (StringsKt.indexOf$default((CharSequence)" #%/:?@[\\]", c, 0, false, 6, null) != -1) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private static final boolean decodeIpv4Suffix(String string2, int n, int n2, byte[] arrby, int n3) {
        int n4 = n3;
        int n5 = n;
        do {
            char c;
            boolean bl = false;
            if (n5 >= n2) {
                if (n4 != n3 + 4) return bl;
                return true;
            }
            if (n4 == arrby.length) {
                return false;
            }
            n = n5;
            if (n4 != n3) {
                if (string2.charAt(n5) != '.') {
                    return false;
                }
                n = n5 + 1;
            }
            int n6 = 0;
            for (n5 = n; n5 < n2 && (c = string2.charAt(n5)) >= '0' && c <= '9'; ++n5) {
                if (n6 == 0 && n != n5) {
                    return false;
                }
                if ((n6 = n6 * 10 + c - 48) <= 255) continue;
                return false;
            }
            if (n5 - n == 0) {
                return false;
            }
            arrby[n4] = (byte)n6;
            ++n4;
        } while (true);
    }

    private static final InetAddress decodeIpv6(String string2, int n, int n2) {
        byte[] arrby = new byte[16];
        int n3 = 0;
        int n4 = -1;
        int n5 = -1;
        int n6 = n;
        do {
            int n7;
            block12 : {
                block7 : {
                    block10 : {
                        block11 : {
                            block8 : {
                                block9 : {
                                    n = n3;
                                    n7 = n4;
                                    if (n6 >= n2) break block7;
                                    if (n3 == 16) {
                                        return null;
                                    }
                                    n7 = n6 + 2;
                                    if (n7 > n2 || !StringsKt.startsWith$default(string2, "::", n6, false, 4, null)) break block8;
                                    if (n4 != -1) {
                                        return null;
                                    }
                                    n = n3 + 2;
                                    if (n7 != n2) break block9;
                                    n7 = n;
                                    break block7;
                                }
                                n6 = n7;
                                n4 = n;
                                n3 = n;
                                n = n6;
                                break block10;
                            }
                            n = n6;
                            if (n3 == 0) break block10;
                            if (StringsKt.startsWith$default(string2, ":", n6, false, 4, null)) break block11;
                            if (!StringsKt.startsWith$default(string2, ".", n6, false, 4, null)) return null;
                            if (!HostnamesKt.decodeIpv4Suffix(string2, n5, n2, arrby, n3 - 2)) {
                                return null;
                            }
                            n = n3 + 2;
                            n7 = n4;
                            break block7;
                        }
                        n = n6 + 1;
                    }
                    n5 = 0;
                    break block12;
                }
                if (n == 16) return InetAddress.getByAddress(arrby);
                if (n7 == -1) {
                    return null;
                }
                n2 = n - n7;
                System.arraycopy(arrby, n7, arrby, 16 - n2, n2);
                Arrays.fill(arrby, n7, 16 - n + n7, (byte)(false ? 1 : 0));
                return InetAddress.getByAddress(arrby);
            }
            for (n6 = n; n6 < n2 && (n7 = Util.parseHexDigit(string2.charAt(n6))) != -1; ++n6) {
                n5 = (n5 << 4) + n7;
            }
            n7 = n6 - n;
            if (n7 == 0) return null;
            if (n7 > 4) {
                return null;
            }
            n7 = n3 + 1;
            arrby[n3] = (byte)(n5 >>> 8 & 255);
            n3 = n7 + 1;
            arrby[n7] = (byte)(n5 & 255);
            n5 = n;
        } while (true);
    }

    private static final String inet6AddressToAscii(byte[] arrby) {
        int n;
        int n2 = 0;
        int n3 = -1;
        int n4 = 0;
        int n5 = 0;
        while (n4 < arrby.length) {
            for (n = n4; n < 16 && arrby[n] == 0 && arrby[n + 1] == 0; n += 2) {
            }
            int n6 = n - n4;
            int n7 = n3;
            int n8 = n5;
            if (n6 > n5) {
                n7 = n3;
                n8 = n5;
                if (n6 >= 4) {
                    n8 = n6;
                    n7 = n4;
                }
            }
            n4 = n + 2;
            n3 = n7;
            n5 = n8;
        }
        Buffer buffer = new Buffer();
        n4 = n2;
        while (n4 < arrby.length) {
            if (n4 == n3) {
                buffer.writeByte(58);
                n4 = n = n4 + n5;
                if (n != 16) continue;
                buffer.writeByte(58);
                n4 = n;
                continue;
            }
            if (n4 > 0) {
                buffer.writeByte(58);
            }
            buffer.writeHexadecimalUnsignedLong(Util.and(arrby[n4], 255) << 8 | Util.and(arrby[n4 + 1], 255));
            n4 += 2;
        }
        return buffer.readUtf8();
    }

    public static final String toCanonicalHost(String object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toCanonicalHost");
        byte[] arrby = (byte[])object;
        CharSequence charSequence = ":";
        boolean bl = false;
        Serializable serializable = null;
        if (StringsKt.contains$default((CharSequence)arrby, charSequence, false, 2, null)) {
            serializable = StringsKt.startsWith$default((String)object, "[", false, 2, null) && StringsKt.endsWith$default((String)object, "]", false, 2, null) ? HostnamesKt.decodeIpv6((String)object, 1, ((String)object).length() - 1) : HostnamesKt.decodeIpv6((String)object, 0, ((String)object).length());
            if (serializable == null) return null;
            arrby = ((InetAddress)serializable).getAddress();
            if (arrby.length == 16) {
                Intrinsics.checkExpressionValueIsNotNull(arrby, "address");
                return HostnamesKt.inet6AddressToAscii(arrby);
            }
            if (arrby.length == 4) {
                return ((InetAddress)serializable).getHostAddress();
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Invalid IPv6 address: '");
            ((StringBuilder)serializable).append((String)object);
            ((StringBuilder)serializable).append('\'');
            throw (Throwable)((Object)new AssertionError((Object)((StringBuilder)serializable).toString()));
        }
        try {
            arrby = IDN.toASCII((String)object);
            Intrinsics.checkExpressionValueIsNotNull(arrby, "IDN.toASCII(host)");
            object = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(object, "Locale.US");
            if (arrby == null) {
                object = new TypeCastException("null cannot be cast to non-null type java.lang.String");
                throw object;
            }
            object = arrby.toLowerCase((Locale)object);
            Intrinsics.checkExpressionValueIsNotNull(object, "(this as java.lang.String).toLowerCase(locale)");
            if (((CharSequence)object).length() == 0) {
                return null;
            }
            if (bl) {
                return null;
            }
            if (!HostnamesKt.containsInvalidHostnameAsciiCodes((String)object)) return object;
            return serializable;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }
}

