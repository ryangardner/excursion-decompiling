/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InetAddressUtils {
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN;
    private static final Pattern IPV6_STD_PATTERN;

    static {
        IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
        IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
    }

    private InetAddressUtils() {
    }

    public static boolean isIPv4Address(String string2) {
        return IPV4_PATTERN.matcher(string2).matches();
    }

    public static boolean isIPv6Address(String string2) {
        if (InetAddressUtils.isIPv6StdAddress(string2)) return true;
        if (InetAddressUtils.isIPv6HexCompressedAddress(string2)) return true;
        return false;
    }

    public static boolean isIPv6HexCompressedAddress(String string2) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(string2).matches();
    }

    public static boolean isIPv6StdAddress(String string2) {
        return IPV6_STD_PATTERN.matcher(string2).matches();
    }
}

