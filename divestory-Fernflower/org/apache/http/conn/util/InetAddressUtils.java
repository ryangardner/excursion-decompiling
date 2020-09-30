package org.apache.http.conn.util;

import java.util.regex.Pattern;

public class InetAddressUtils {
   private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
   private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
   private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

   private InetAddressUtils() {
   }

   public static boolean isIPv4Address(String var0) {
      return IPV4_PATTERN.matcher(var0).matches();
   }

   public static boolean isIPv6Address(String var0) {
      boolean var1;
      if (!isIPv6StdAddress(var0) && !isIPv6HexCompressedAddress(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean isIPv6HexCompressedAddress(String var0) {
      return IPV6_HEX_COMPRESSED_PATTERN.matcher(var0).matches();
   }

   public static boolean isIPv6StdAddress(String var0) {
      return IPV6_STD_PATTERN.matcher(var0).matches();
   }
}
