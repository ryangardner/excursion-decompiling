/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.net;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class InetAddresses {
    private static final Inet4Address ANY4;
    private static final int IPV4_PART_COUNT = 4;
    private static final Splitter IPV4_SPLITTER;
    private static final int IPV6_PART_COUNT = 8;
    private static final Splitter IPV6_SPLITTER;
    private static final Inet4Address LOOPBACK4;

    static {
        IPV4_SPLITTER = Splitter.on('.').limit(4);
        IPV6_SPLITTER = Splitter.on(':').limit(10);
        LOOPBACK4 = (Inet4Address)InetAddresses.forString("127.0.0.1");
        ANY4 = (Inet4Address)InetAddresses.forString("0.0.0.0");
    }

    private InetAddresses() {
    }

    private static InetAddress bytesToInetAddress(byte[] object) {
        try {
            return InetAddress.getByAddress(object);
        }
        catch (UnknownHostException unknownHostException) {
            throw new AssertionError(unknownHostException);
        }
    }

    public static int coerceToInteger(InetAddress inetAddress) {
        return ByteStreams.newDataInput(InetAddresses.getCoercedIPv4Address(inetAddress).getAddress()).readInt();
    }

    private static void compressLongestRunOfZeroes(int[] arrn) {
        int n = 0;
        int n2 = -1;
        int n3 = -1;
        int n4 = -1;
        do {
            int n5;
            int n6;
            int n7;
            if (n >= arrn.length + 1) {
                if (n2 < 2) return;
                Arrays.fill(arrn, n3, n2 + n3, -1);
                return;
            }
            if (n < arrn.length && arrn[n] == 0) {
                n7 = n2;
                n6 = n3;
                n5 = n4;
                if (n4 < 0) {
                    n5 = n;
                    n7 = n2;
                    n6 = n3;
                }
            } else {
                n7 = n2;
                n6 = n3;
                n5 = n4;
                if (n4 >= 0) {
                    n5 = n - n4;
                    n7 = n2;
                    if (n5 > n2) {
                        n7 = n5;
                        n3 = n4;
                    }
                    n5 = -1;
                    n6 = n3;
                }
            }
            ++n;
            n2 = n7;
            n3 = n6;
            n4 = n5;
        } while (true);
    }

    @NullableDecl
    private static String convertDottedQuadToHex(String string2) {
        int n = string2.lastIndexOf(58) + 1;
        String string3 = string2.substring(0, n);
        Object object = InetAddresses.textToNumericFormatV4(string2.substring(n));
        if (object == null) {
            return null;
        }
        string2 = Integer.toHexString((object[0] & 255) << 8 | object[1] & 255);
        n = object[2];
        object = Integer.toHexString(object[3] & 255 | (n & 255) << 8);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append(string2);
        stringBuilder.append(":");
        stringBuilder.append((String)object);
        return stringBuilder.toString();
    }

    public static InetAddress decrement(InetAddress inetAddress) {
        int n;
        byte[] arrby = inetAddress.getAddress();
        for (n = arrby.length - 1; n >= 0 && arrby[n] == 0; --n) {
            arrby[n] = (byte)-1;
        }
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "Decrementing %s would wrap.", (Object)inetAddress);
        arrby[n] = (byte)(arrby[n] - 1);
        return InetAddresses.bytesToInetAddress(arrby);
    }

    public static InetAddress forString(String string2) {
        byte[] arrby = InetAddresses.ipStringToBytes(string2);
        if (arrby == null) throw InetAddresses.formatIllegalArgumentException("'%s' is not an IP string literal.", string2);
        return InetAddresses.bytesToInetAddress(arrby);
    }

    public static InetAddress forUriString(String string2) {
        InetAddress inetAddress = InetAddresses.forUriStringNoThrow(string2);
        if (inetAddress == null) throw InetAddresses.formatIllegalArgumentException("Not a valid URI IP literal: '%s'", string2);
        return inetAddress;
    }

    @NullableDecl
    private static InetAddress forUriStringNoThrow(String object) {
        int n;
        Preconditions.checkNotNull(object);
        if (object.startsWith("[") && object.endsWith("]")) {
            object = object.substring(1, object.length() - 1);
            n = 16;
        } else {
            n = 4;
        }
        object = InetAddresses.ipStringToBytes((String)object);
        if (object == null) return null;
        if (((byte[])object).length == n) return InetAddresses.bytesToInetAddress(object);
        return null;
    }

    private static IllegalArgumentException formatIllegalArgumentException(String string2, Object ... arrobject) {
        return new IllegalArgumentException(String.format(Locale.ROOT, string2, arrobject));
    }

    private static InetAddress fromBigInteger(BigInteger serializable, boolean bl) {
        boolean bl2 = serializable.signum() >= 0;
        Preconditions.checkArgument(bl2, "BigInteger must be greater than or equal to 0");
        int n = bl ? 16 : 4;
        byte[] arrby = serializable.toByteArray();
        byte[] arrby2 = new byte[n];
        int n2 = Math.max(0, arrby.length - n);
        int n3 = arrby.length - n2;
        for (int i = 0; i < n2; ++i) {
            if (arrby[i] == 0) continue;
            throw InetAddresses.formatIllegalArgumentException("BigInteger cannot be converted to InetAddress because it has more than %d bytes: %s", n, serializable);
        }
        System.arraycopy(arrby, n2, arrby2, n - n3, n3);
        try {
            return InetAddress.getByAddress(arrby2);
        }
        catch (UnknownHostException unknownHostException) {
            throw new AssertionError(unknownHostException);
        }
    }

    public static Inet4Address fromIPv4BigInteger(BigInteger bigInteger) {
        return (Inet4Address)InetAddresses.fromBigInteger(bigInteger, false);
    }

    public static Inet6Address fromIPv6BigInteger(BigInteger bigInteger) {
        return (Inet6Address)InetAddresses.fromBigInteger(bigInteger, true);
    }

    public static Inet4Address fromInteger(int n) {
        return InetAddresses.getInet4Address(Ints.toByteArray(n));
    }

    public static InetAddress fromLittleEndianByteArray(byte[] arrby) throws UnknownHostException {
        byte[] arrby2 = new byte[arrby.length];
        int n = 0;
        while (n < arrby.length) {
            arrby2[n] = arrby[arrby.length - n - 1];
            ++n;
        }
        return InetAddress.getByAddress(arrby2);
    }

    public static Inet4Address get6to4IPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(InetAddresses.is6to4Address(inet6Address), "Address '%s' is not a 6to4 address.", (Object)InetAddresses.toAddrString(inet6Address));
        return InetAddresses.getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 2, 6));
    }

    public static Inet4Address getCoercedIPv4Address(InetAddress inetAddress) {
        byte[] arrby;
        int n;
        int n2;
        block4 : {
            if (inetAddress instanceof Inet4Address) {
                return (Inet4Address)inetAddress;
            }
            arrby = inetAddress.getAddress();
            for (n = 0; n < 15; ++n) {
                if (arrby[n] == 0) continue;
                n = 0;
                break block4;
            }
            n = 1;
        }
        if (n != 0 && arrby[15] == 1) {
            return LOOPBACK4;
        }
        if (n != 0 && arrby[15] == 0) {
            return ANY4;
        }
        long l = InetAddresses.hasEmbeddedIPv4ClientAddress((Inet6Address)(inetAddress = (Inet6Address)inetAddress)) ? (long)InetAddresses.getEmbeddedIPv4ClientAddress((Inet6Address)inetAddress).hashCode() : ByteBuffer.wrap(((Inet6Address)inetAddress).getAddress(), 0, 8).getLong();
        n = n2 = Hashing.murmur3_32().hashLong(l).asInt() | -536870912;
        if (n2 != -1) return InetAddresses.getInet4Address(Ints.toByteArray(n));
        n = -2;
        return InetAddresses.getInet4Address(Ints.toByteArray(n));
    }

    public static Inet4Address getCompatIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(InetAddresses.isCompatIPv4Address(inet6Address), "Address '%s' is not IPv4-compatible.", (Object)InetAddresses.toAddrString(inet6Address));
        return InetAddresses.getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static Inet4Address getEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        if (InetAddresses.isCompatIPv4Address(inet6Address)) {
            return InetAddresses.getCompatIPv4Address(inet6Address);
        }
        if (InetAddresses.is6to4Address(inet6Address)) {
            return InetAddresses.get6to4IPv4Address(inet6Address);
        }
        if (!InetAddresses.isTeredoAddress(inet6Address)) throw InetAddresses.formatIllegalArgumentException("'%s' has no embedded IPv4 address.", InetAddresses.toAddrString(inet6Address));
        return InetAddresses.getTeredoInfo(inet6Address).getClient();
    }

    private static Inet4Address getInet4Address(byte[] arrby) {
        boolean bl = arrby.length == 4;
        Preconditions.checkArgument(bl, "Byte array has invalid length for an IPv4 address: %s != 4.", arrby.length);
        return (Inet4Address)InetAddresses.bytesToInetAddress(arrby);
    }

    public static Inet4Address getIsatapIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(InetAddresses.isIsatapAddress(inet6Address), "Address '%s' is not an ISATAP address.", (Object)InetAddresses.toAddrString(inet6Address));
        return InetAddresses.getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static TeredoInfo getTeredoInfo(Inet6Address inetAddress) {
        Preconditions.checkArgument(InetAddresses.isTeredoAddress(inetAddress), "Address '%s' is not a Teredo address.", (Object)InetAddresses.toAddrString(inetAddress));
        byte[] arrby = inetAddress.getAddress();
        inetAddress = InetAddresses.getInet4Address(Arrays.copyOfRange(arrby, 4, 8));
        short s = ByteStreams.newDataInput(arrby, 8).readShort();
        short s2 = ByteStreams.newDataInput(arrby, 10).readShort();
        arrby = Arrays.copyOfRange(arrby, 12, 16);
        int n = 0;
        while (n < arrby.length) {
            arrby[n] = arrby[n];
            ++n;
        }
        return new TeredoInfo((Inet4Address)inetAddress, InetAddresses.getInet4Address(arrby), 65535 & s2, s & 65535);
    }

    public static boolean hasEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        if (InetAddresses.isCompatIPv4Address(inet6Address)) return true;
        if (InetAddresses.is6to4Address(inet6Address)) return true;
        if (InetAddresses.isTeredoAddress(inet6Address)) return true;
        return false;
    }

    private static String hextetsToIPv6String(int[] arrn) {
        StringBuilder stringBuilder = new StringBuilder(39);
        int n = 0;
        boolean bl = false;
        while (n < arrn.length) {
            boolean bl2 = arrn[n] >= 0;
            if (bl2) {
                if (bl) {
                    stringBuilder.append(':');
                }
                stringBuilder.append(Integer.toHexString(arrn[n]));
            } else if (n == 0 || bl) {
                stringBuilder.append("::");
            }
            ++n;
            bl = bl2;
        }
        return stringBuilder.toString();
    }

    public static InetAddress increment(InetAddress inetAddress) {
        boolean bl;
        byte[] arrby = inetAddress.getAddress();
        int n = arrby.length - 1;
        do {
            bl = false;
            if (n < 0 || arrby[n] != -1) break;
            arrby[n] = (byte)(false ? 1 : 0);
            --n;
        } while (true);
        if (n >= 0) {
            bl = true;
        }
        Preconditions.checkArgument(bl, "Incrementing %s would wrap.", (Object)inetAddress);
        arrby[n] = (byte)(arrby[n] + 1);
        return InetAddresses.bytesToInetAddress(arrby);
    }

    @NullableDecl
    private static byte[] ipStringToBytes(String string2) {
        int n;
        boolean bl;
        boolean bl2;
        block8 : {
            bl = false;
            bl2 = false;
            for (n = 0; n < string2.length(); ++n) {
                char c = string2.charAt(n);
                if (c == '.') {
                    bl = true;
                    continue;
                }
                if (c == ':') {
                    if (bl) {
                        return null;
                    }
                    bl2 = true;
                    continue;
                }
                if (c != '%') {
                    if (Character.digit(c, 16) != -1) continue;
                    return null;
                }
                break block8;
            }
            n = -1;
        }
        if (!bl2) {
            if (!bl) return null;
            return InetAddresses.textToNumericFormatV4(string2);
        }
        String string3 = string2;
        if (bl) {
            string3 = string2 = InetAddresses.convertDottedQuadToHex(string2);
            if (string2 == null) {
                return null;
            }
        }
        string2 = string3;
        if (n == -1) return InetAddresses.textToNumericFormatV6(string2);
        string2 = string3.substring(0, n);
        return InetAddresses.textToNumericFormatV6(string2);
    }

    public static boolean is6to4Address(Inet6Address arrby) {
        boolean bl;
        arrby = arrby.getAddress();
        boolean bl2 = bl = false;
        if (arrby[0] != 32) return bl2;
        bl2 = bl;
        if (arrby[1] != 2) return bl2;
        return true;
    }

    public static boolean isCompatIPv4Address(Inet6Address arrby) {
        if (!arrby.isIPv4CompatibleAddress()) {
            return false;
        }
        if ((arrby = arrby.getAddress())[12] != 0) return true;
        if (arrby[13] != 0) return true;
        if (arrby[14] != 0) return true;
        if (arrby[15] == 0) return false;
        if (arrby[15] != 1) return true;
        return false;
    }

    public static boolean isInetAddress(String string2) {
        if (InetAddresses.ipStringToBytes(string2) == null) return false;
        return true;
    }

    public static boolean isIsatapAddress(Inet6Address arrby) {
        boolean bl = InetAddresses.isTeredoAddress((Inet6Address)arrby);
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (((arrby = arrby.getAddress())[8] | 3) != 3) {
            return false;
        }
        bl = bl2;
        if (arrby[9] != 0) return bl;
        bl = bl2;
        if (arrby[10] != 94) return bl;
        bl = bl2;
        if (arrby[11] != -2) return bl;
        return true;
    }

    public static boolean isMappedIPv4Address(String arrby) {
        int n;
        if ((arrby = InetAddresses.ipStringToBytes((String)arrby)) == null) return false;
        if (arrby.length != 16) return false;
        int n2 = 0;
        do {
            n = 10;
            if (n2 >= 10) break;
            if (arrby[n2] != 0) {
                return false;
            }
            ++n2;
        } while (true);
        while (n < 12) {
            if (arrby[n] != -1) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public static boolean isMaximum(InetAddress arrby) {
        arrby = arrby.getAddress();
        int n = 0;
        while (n < arrby.length) {
            if (arrby[n] != -1) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public static boolean isTeredoAddress(Inet6Address arrby) {
        boolean bl;
        arrby = arrby.getAddress();
        boolean bl2 = bl = false;
        if (arrby[0] != 32) return bl2;
        bl2 = bl;
        if (arrby[1] != 1) return bl2;
        bl2 = bl;
        if (arrby[2] != 0) return bl2;
        bl2 = bl;
        if (arrby[3] != 0) return bl2;
        return true;
    }

    public static boolean isUriInetAddress(String string2) {
        if (InetAddresses.forUriStringNoThrow(string2) == null) return false;
        return true;
    }

    private static short parseHextet(String string2) {
        int n = Integer.parseInt(string2, 16);
        if (n > 65535) throw new NumberFormatException();
        return (short)n;
    }

    private static byte parseOctet(String string2) {
        int n = Integer.parseInt(string2);
        if (n > 255) throw new NumberFormatException();
        if (!string2.startsWith("0")) return (byte)n;
        if (string2.length() > 1) throw new NumberFormatException();
        return (byte)n;
    }

    @NullableDecl
    private static byte[] textToNumericFormatV4(String arrby) {
        byte[] arrby2 = new byte[4];
        try {
            arrby = IPV4_SPLITTER.split((CharSequence)arrby).iterator();
            int n = 0;
            while (arrby.hasNext()) {
                arrby2[n] = InetAddresses.parseOctet((String)arrby.next());
                ++n;
            }
            if (n != 4) return null;
            return arrby2;
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @NullableDecl
    private static byte[] textToNumericFormatV6(String object) {
        int n4;
        int n2;
        int n;
        int n3;
        if ((object = IPV6_SPLITTER.splitToList((CharSequence)((Object)object))).size() < 3) return null;
        if (object.size() > 9) {
            return null;
        }
        int n5 = -1;
        for (n3 = 1; n3 < object.size() - 1; ++n3) {
            n2 = n5;
            if (object.get(n3).length() == 0) {
                if (n5 >= 0) {
                    return null;
                }
                n2 = n3;
            }
            n5 = n2;
        }
        if (n5 >= 0) {
            n = object.size() - n5 - 1;
            if (object.get(0).length() == 0) {
                n2 = n3 = n5 - 1;
                if (n3 != 0) {
                    return null;
                }
            } else {
                n2 = n5;
            }
            n3 = n--;
            n4 = n2;
            if (Iterables.getLast(object).length() == 0) {
                n3 = n;
                n4 = n2;
                if (n != 0) {
                    return null;
                }
            }
        } else {
            n4 = object.size();
            n3 = 0;
        }
        n = 8 - (n4 + n3);
        if (n5 >= 0) {
            if (n < 1) return null;
        } else if (n != 0) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        for (n5 = 0; n5 < n4; ++n5) {
            byteBuffer.putShort(InetAddresses.parseHextet(object.get(n5)));
            continue;
        }
        n5 = 0;
        do {
            n2 = n3;
            if (n5 >= n) break;
            byteBuffer.putShort((short)0);
            ++n5;
            continue;
            break;
        } while (true);
        while (n2 > 0) {
            byteBuffer.putShort(InetAddresses.parseHextet(object.get(object.size() - n2)));
            --n2;
        }
        return byteBuffer.array();
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    public static String toAddrString(InetAddress arrn) {
        Preconditions.checkNotNull(arrn);
        if (arrn instanceof Inet4Address) {
            return arrn.getHostAddress();
        }
        Preconditions.checkArgument(arrn instanceof Inet6Address);
        byte[] arrby = arrn.getAddress();
        arrn = new int[8];
        int n = 0;
        do {
            if (n >= 8) {
                InetAddresses.compressLongestRunOfZeroes(arrn);
                return InetAddresses.hextetsToIPv6String(arrn);
            }
            int n2 = n * 2;
            arrn[n] = Ints.fromBytes((byte)0, (byte)0, arrby[n2], arrby[n2 + 1]);
            ++n;
        } while (true);
    }

    public static BigInteger toBigInteger(InetAddress inetAddress) {
        return new BigInteger(1, inetAddress.getAddress());
    }

    public static String toUriString(InetAddress inetAddress) {
        if (!(inetAddress instanceof Inet6Address)) return InetAddresses.toAddrString(inetAddress);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(InetAddresses.toAddrString(inetAddress));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static final class TeredoInfo {
        private final Inet4Address client;
        private final int flags;
        private final int port;
        private final Inet4Address server;

        public TeredoInfo(@NullableDecl Inet4Address inet4Address, @NullableDecl Inet4Address inet4Address2, int n, int n2) {
            boolean bl = true;
            boolean bl2 = n >= 0 && n <= 65535;
            Preconditions.checkArgument(bl2, "port '%s' is out of range (0 <= port <= 0xffff)", n);
            bl2 = n2 >= 0 && n2 <= 65535 ? bl : false;
            Preconditions.checkArgument(bl2, "flags '%s' is out of range (0 <= flags <= 0xffff)", n2);
            this.server = MoreObjects.firstNonNull(inet4Address, ANY4);
            this.client = MoreObjects.firstNonNull(inet4Address2, ANY4);
            this.port = n;
            this.flags = n2;
        }

        public Inet4Address getClient() {
            return this.client;
        }

        public int getFlags() {
            return this.flags;
        }

        public int getPort() {
            return this.port;
        }

        public Inet4Address getServer() {
            return this.server;
        }
    }

}

