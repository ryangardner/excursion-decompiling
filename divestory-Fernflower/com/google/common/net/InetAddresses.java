package com.google.common.net;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
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
   private static final Inet4Address ANY4 = (Inet4Address)forString("0.0.0.0");
   private static final int IPV4_PART_COUNT = 4;
   private static final Splitter IPV4_SPLITTER = Splitter.on('.').limit(4);
   private static final int IPV6_PART_COUNT = 8;
   private static final Splitter IPV6_SPLITTER = Splitter.on(':').limit(10);
   private static final Inet4Address LOOPBACK4 = (Inet4Address)forString("127.0.0.1");

   private InetAddresses() {
   }

   private static InetAddress bytesToInetAddress(byte[] var0) {
      try {
         InetAddress var2 = InetAddress.getByAddress(var0);
         return var2;
      } catch (UnknownHostException var1) {
         throw new AssertionError(var1);
      }
   }

   public static int coerceToInteger(InetAddress var0) {
      return ByteStreams.newDataInput(getCoercedIPv4Address(var0).getAddress()).readInt();
   }

   private static void compressLongestRunOfZeroes(int[] var0) {
      int var1 = 0;
      int var2 = -1;
      int var3 = -1;

      int var7;
      for(int var4 = -1; var1 < var0.length + 1; var4 = var7) {
         int var5;
         int var6;
         if (var1 < var0.length && var0[var1] == 0) {
            var5 = var2;
            var6 = var3;
            var7 = var4;
            if (var4 < 0) {
               var7 = var1;
               var5 = var2;
               var6 = var3;
            }
         } else {
            var5 = var2;
            var6 = var3;
            var7 = var4;
            if (var4 >= 0) {
               var7 = var1 - var4;
               var5 = var2;
               if (var7 > var2) {
                  var5 = var7;
                  var3 = var4;
               }

               var7 = -1;
               var6 = var3;
            }
         }

         ++var1;
         var2 = var5;
         var3 = var6;
      }

      if (var2 >= 2) {
         Arrays.fill(var0, var3, var2 + var3, -1);
      }

   }

   @NullableDecl
   private static String convertDottedQuadToHex(String var0) {
      int var1 = var0.lastIndexOf(58) + 1;
      String var2 = var0.substring(0, var1);
      byte[] var3 = textToNumericFormatV4(var0.substring(var1));
      if (var3 == null) {
         return null;
      } else {
         var0 = Integer.toHexString((var3[0] & 255) << 8 | var3[1] & 255);
         byte var5 = var3[2];
         String var6 = Integer.toHexString(var3[3] & 255 | (var5 & 255) << 8);
         StringBuilder var4 = new StringBuilder();
         var4.append(var2);
         var4.append(var0);
         var4.append(":");
         var4.append(var6);
         return var4.toString();
      }
   }

   public static InetAddress decrement(InetAddress var0) {
      byte[] var1 = var0.getAddress();

      int var2;
      for(var2 = var1.length - 1; var2 >= 0 && var1[var2] == 0; --var2) {
         var1[var2] = (byte)-1;
      }

      boolean var3;
      if (var2 >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "Decrementing %s would wrap.", (Object)var0);
      var1[var2] = (byte)((byte)(var1[var2] - 1));
      return bytesToInetAddress(var1);
   }

   public static InetAddress forString(String var0) {
      byte[] var1 = ipStringToBytes(var0);
      if (var1 != null) {
         return bytesToInetAddress(var1);
      } else {
         throw formatIllegalArgumentException("'%s' is not an IP string literal.", var0);
      }
   }

   public static InetAddress forUriString(String var0) {
      InetAddress var1 = forUriStringNoThrow(var0);
      if (var1 != null) {
         return var1;
      } else {
         throw formatIllegalArgumentException("Not a valid URI IP literal: '%s'", var0);
      }
   }

   @NullableDecl
   private static InetAddress forUriStringNoThrow(String var0) {
      Preconditions.checkNotNull(var0);
      byte var1;
      if (var0.startsWith("[") && var0.endsWith("]")) {
         var0 = var0.substring(1, var0.length() - 1);
         var1 = 16;
      } else {
         var1 = 4;
      }

      byte[] var2 = ipStringToBytes(var0);
      return var2 != null && var2.length == var1 ? bytesToInetAddress(var2) : null;
   }

   private static IllegalArgumentException formatIllegalArgumentException(String var0, Object... var1) {
      return new IllegalArgumentException(String.format(Locale.ROOT, var0, var1));
   }

   private static InetAddress fromBigInteger(BigInteger var0, boolean var1) {
      boolean var2;
      if (var0.signum() >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "BigInteger must be greater than or equal to 0");
      byte var3;
      if (var1) {
         var3 = 16;
      } else {
         var3 = 4;
      }

      byte[] var4 = var0.toByteArray();
      byte[] var5 = new byte[var3];
      int var6 = Math.max(0, var4.length - var3);
      int var7 = var4.length - var6;

      for(int var8 = 0; var8 < var6; ++var8) {
         if (var4[var8] != 0) {
            throw formatIllegalArgumentException("BigInteger cannot be converted to InetAddress because it has more than %d bytes: %s", Integer.valueOf(var3), var0);
         }
      }

      System.arraycopy(var4, var6, var5, var3 - var7, var7);

      try {
         InetAddress var10 = InetAddress.getByAddress(var5);
         return var10;
      } catch (UnknownHostException var9) {
         throw new AssertionError(var9);
      }
   }

   public static Inet4Address fromIPv4BigInteger(BigInteger var0) {
      return (Inet4Address)fromBigInteger(var0, false);
   }

   public static Inet6Address fromIPv6BigInteger(BigInteger var0) {
      return (Inet6Address)fromBigInteger(var0, true);
   }

   public static Inet4Address fromInteger(int var0) {
      return getInet4Address(Ints.toByteArray(var0));
   }

   public static InetAddress fromLittleEndianByteArray(byte[] var0) throws UnknownHostException {
      byte[] var1 = new byte[var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var1[var2] = (byte)var0[var0.length - var2 - 1];
      }

      return InetAddress.getByAddress(var1);
   }

   public static Inet4Address get6to4IPv4Address(Inet6Address var0) {
      Preconditions.checkArgument(is6to4Address(var0), "Address '%s' is not a 6to4 address.", (Object)toAddrString(var0));
      return getInet4Address(Arrays.copyOfRange(var0.getAddress(), 2, 6));
   }

   public static Inet4Address getCoercedIPv4Address(InetAddress var0) {
      if (var0 instanceof Inet4Address) {
         return (Inet4Address)var0;
      } else {
         byte[] var1 = var0.getAddress();
         int var2 = 0;

         boolean var7;
         while(true) {
            if (var2 >= 15) {
               var7 = true;
               break;
            }

            if (var1[var2] != 0) {
               var7 = false;
               break;
            }

            ++var2;
         }

         if (var7 && var1[15] == 1) {
            return LOOPBACK4;
         } else if (var7 && var1[15] == 0) {
            return ANY4;
         } else {
            Inet6Address var6 = (Inet6Address)var0;
            long var3;
            if (hasEmbeddedIPv4ClientAddress(var6)) {
               var3 = (long)getEmbeddedIPv4ClientAddress(var6).hashCode();
            } else {
               var3 = ByteBuffer.wrap(var6.getAddress(), 0, 8).getLong();
            }

            int var5 = Hashing.murmur3_32().hashLong(var3).asInt() | -536870912;
            var2 = var5;
            if (var5 == -1) {
               var2 = -2;
            }

            return getInet4Address(Ints.toByteArray(var2));
         }
      }
   }

   public static Inet4Address getCompatIPv4Address(Inet6Address var0) {
      Preconditions.checkArgument(isCompatIPv4Address(var0), "Address '%s' is not IPv4-compatible.", (Object)toAddrString(var0));
      return getInet4Address(Arrays.copyOfRange(var0.getAddress(), 12, 16));
   }

   public static Inet4Address getEmbeddedIPv4ClientAddress(Inet6Address var0) {
      if (isCompatIPv4Address(var0)) {
         return getCompatIPv4Address(var0);
      } else if (is6to4Address(var0)) {
         return get6to4IPv4Address(var0);
      } else if (isTeredoAddress(var0)) {
         return getTeredoInfo(var0).getClient();
      } else {
         throw formatIllegalArgumentException("'%s' has no embedded IPv4 address.", toAddrString(var0));
      }
   }

   private static Inet4Address getInet4Address(byte[] var0) {
      boolean var1;
      if (var0.length == 4) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Byte array has invalid length for an IPv4 address: %s != 4.", var0.length);
      return (Inet4Address)bytesToInetAddress(var0);
   }

   public static Inet4Address getIsatapIPv4Address(Inet6Address var0) {
      Preconditions.checkArgument(isIsatapAddress(var0), "Address '%s' is not an ISATAP address.", (Object)toAddrString(var0));
      return getInet4Address(Arrays.copyOfRange(var0.getAddress(), 12, 16));
   }

   public static InetAddresses.TeredoInfo getTeredoInfo(Inet6Address var0) {
      Preconditions.checkArgument(isTeredoAddress(var0), "Address '%s' is not a Teredo address.", (Object)toAddrString(var0));
      byte[] var1 = var0.getAddress();
      Inet4Address var5 = getInet4Address(Arrays.copyOfRange(var1, 4, 8));
      short var2 = ByteStreams.newDataInput(var1, 8).readShort();
      short var3 = ByteStreams.newDataInput(var1, 10).readShort();
      var1 = Arrays.copyOfRange(var1, 12, 16);

      for(int var4 = 0; var4 < var1.length; ++var4) {
         var1[var4] = (byte)((byte)var1[var4]);
      }

      return new InetAddresses.TeredoInfo(var5, getInet4Address(var1), '\uffff' & var3, var2 & '\uffff');
   }

   public static boolean hasEmbeddedIPv4ClientAddress(Inet6Address var0) {
      boolean var1;
      if (!isCompatIPv4Address(var0) && !is6to4Address(var0) && !isTeredoAddress(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private static String hextetsToIPv6String(int[] var0) {
      StringBuilder var1 = new StringBuilder(39);
      int var2 = 0;

      boolean var4;
      for(boolean var3 = false; var2 < var0.length; var3 = var4) {
         if (var0[var2] >= 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            if (var3) {
               var1.append(':');
            }

            var1.append(Integer.toHexString(var0[var2]));
         } else if (var2 == 0 || var3) {
            var1.append("::");
         }

         ++var2;
      }

      return var1.toString();
   }

   public static InetAddress increment(InetAddress var0) {
      byte[] var1 = var0.getAddress();
      int var2 = var1.length - 1;

      while(true) {
         boolean var3 = false;
         if (var2 < 0 || var1[var2] != -1) {
            if (var2 >= 0) {
               var3 = true;
            }

            Preconditions.checkArgument(var3, "Incrementing %s would wrap.", (Object)var0);
            var1[var2] = (byte)((byte)(var1[var2] + 1));
            return bytesToInetAddress(var1);
         }

         var1[var2] = (byte)0;
         --var2;
      }
   }

   @NullableDecl
   private static byte[] ipStringToBytes(String var0) {
      int var1 = 0;
      boolean var2 = false;
      boolean var3 = false;

      while(true) {
         if (var1 >= var0.length()) {
            var1 = -1;
            break;
         }

         char var4 = var0.charAt(var1);
         if (var4 == '.') {
            var2 = true;
         } else if (var4 == ':') {
            if (var2) {
               return null;
            }

            var3 = true;
         } else {
            if (var4 == '%') {
               break;
            }

            if (Character.digit(var4, 16) == -1) {
               return null;
            }
         }

         ++var1;
      }

      if (var3) {
         String var5 = var0;
         if (var2) {
            var0 = convertDottedQuadToHex(var0);
            var5 = var0;
            if (var0 == null) {
               return null;
            }
         }

         var0 = var5;
         if (var1 != -1) {
            var0 = var5.substring(0, var1);
         }

         return textToNumericFormatV6(var0);
      } else {
         return var2 ? textToNumericFormatV4(var0) : null;
      }
   }

   public static boolean is6to4Address(Inet6Address var0) {
      byte[] var3 = var0.getAddress();
      boolean var1 = false;
      boolean var2 = var1;
      if (var3[0] == 32) {
         var2 = var1;
         if (var3[1] == 2) {
            var2 = true;
         }
      }

      return var2;
   }

   public static boolean isCompatIPv4Address(Inet6Address var0) {
      if (!var0.isIPv4CompatibleAddress()) {
         return false;
      } else {
         byte[] var1 = var0.getAddress();
         return var1[12] != 0 || var1[13] != 0 || var1[14] != 0 || var1[15] != 0 && var1[15] != 1;
      }
   }

   public static boolean isInetAddress(String var0) {
      boolean var1;
      if (ipStringToBytes(var0) != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isIsatapAddress(Inet6Address var0) {
      boolean var1 = isTeredoAddress(var0);
      boolean var2 = false;
      if (var1) {
         return false;
      } else {
         byte[] var3 = var0.getAddress();
         if ((var3[8] | 3) != 3) {
            return false;
         } else {
            var1 = var2;
            if (var3[9] == 0) {
               var1 = var2;
               if (var3[10] == 94) {
                  var1 = var2;
                  if (var3[11] == -2) {
                     var1 = true;
                  }
               }
            }

            return var1;
         }
      }
   }

   public static boolean isMappedIPv4Address(String var0) {
      byte[] var3 = ipStringToBytes(var0);
      if (var3 != null && var3.length == 16) {
         int var1 = 0;

         while(true) {
            int var2 = 10;
            if (var1 >= 10) {
               while(var2 < 12) {
                  if (var3[var2] != -1) {
                     return false;
                  }

                  ++var2;
               }

               return true;
            }

            if (var3[var1] != 0) {
               return false;
            }

            ++var1;
         }
      } else {
         return false;
      }
   }

   public static boolean isMaximum(InetAddress var0) {
      byte[] var2 = var0.getAddress();

      for(int var1 = 0; var1 < var2.length; ++var1) {
         if (var2[var1] != -1) {
            return false;
         }
      }

      return true;
   }

   public static boolean isTeredoAddress(Inet6Address var0) {
      byte[] var3 = var0.getAddress();
      boolean var1 = false;
      boolean var2 = var1;
      if (var3[0] == 32) {
         var2 = var1;
         if (var3[1] == 1) {
            var2 = var1;
            if (var3[2] == 0) {
               var2 = var1;
               if (var3[3] == 0) {
                  var2 = true;
               }
            }
         }
      }

      return var2;
   }

   public static boolean isUriInetAddress(String var0) {
      boolean var1;
      if (forUriStringNoThrow(var0) != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static short parseHextet(String var0) {
      int var1 = Integer.parseInt(var0, 16);
      if (var1 <= 65535) {
         return (short)var1;
      } else {
         throw new NumberFormatException();
      }
   }

   private static byte parseOctet(String var0) {
      int var1 = Integer.parseInt(var0);
      if (var1 > 255 || var0.startsWith("0") && var0.length() > 1) {
         throw new NumberFormatException();
      } else {
         return (byte)var1;
      }
   }

   @NullableDecl
   private static byte[] textToNumericFormatV4(String var0) {
      byte[] var1 = new byte[4];

      boolean var10001;
      Iterator var5;
      try {
         var5 = IPV4_SPLITTER.split(var0).iterator();
      } catch (NumberFormatException var4) {
         var10001 = false;
         return null;
      }

      int var2 = 0;

      while(true) {
         try {
            if (!var5.hasNext()) {
               break;
            }

            var1[var2] = parseOctet((String)var5.next());
         } catch (NumberFormatException var3) {
            var10001 = false;
            return null;
         }

         ++var2;
      }

      byte[] var6;
      if (var2 == 4) {
         var6 = var1;
      } else {
         var6 = null;
      }

      return var6;
   }

   @NullableDecl
   private static byte[] textToNumericFormatV6(String var0) {
      List var10 = IPV6_SPLITTER.splitToList(var0);
      if (var10.size() >= 3 && var10.size() <= 9) {
         int var1 = -1;

         int var2;
         int var3;
         for(var2 = 1; var2 < var10.size() - 1; var1 = var3) {
            var3 = var1;
            if (((String)var10.get(var2)).length() == 0) {
               if (var1 >= 0) {
                  return null;
               }

               var3 = var2;
            }

            ++var2;
         }

         int var4;
         int var5;
         if (var1 >= 0) {
            var4 = var10.size() - var1 - 1;
            if (((String)var10.get(0)).length() == 0) {
               var2 = var1 - 1;
               var3 = var2;
               if (var2 != 0) {
                  return null;
               }
            } else {
               var3 = var1;
            }

            var2 = var4;
            var5 = var3;
            if (((String)Iterables.getLast(var10)).length() == 0) {
               --var4;
               var2 = var4;
               var5 = var3;
               if (var4 != 0) {
                  return null;
               }
            }
         } else {
            var5 = var10.size();
            var2 = 0;
         }

         label88: {
            var4 = 8 - (var5 + var2);
            if (var1 >= 0) {
               if (var4 >= 1) {
                  break label88;
               }
            } else if (var4 == 0) {
               break label88;
            }

            return null;
         }

         ByteBuffer var6 = ByteBuffer.allocate(16);
         var1 = 0;

         while(true) {
            boolean var10001;
            if (var1 < var5) {
               try {
                  var6.putShort(parseHextet((String)var10.get(var1)));
               } catch (NumberFormatException var7) {
                  var10001 = false;
                  return null;
               }

               ++var1;
            } else {
               var1 = 0;

               while(true) {
                  var3 = var2;
                  if (var1 >= var4) {
                     for(; var3 > 0; --var3) {
                        try {
                           var6.putShort(parseHextet((String)var10.get(var10.size() - var3)));
                        } catch (NumberFormatException var8) {
                           var10001 = false;
                           return null;
                        }
                     }

                     return var6.array();
                  }

                  try {
                     var6.putShort((short)0);
                  } catch (NumberFormatException var9) {
                     var10001 = false;
                     return null;
                  }

                  ++var1;
               }
            }
         }
      } else {
         return null;
      }
   }

   public static String toAddrString(InetAddress var0) {
      Preconditions.checkNotNull(var0);
      if (var0 instanceof Inet4Address) {
         return var0.getHostAddress();
      } else {
         Preconditions.checkArgument(var0 instanceof Inet6Address);
         byte[] var1 = var0.getAddress();
         int[] var4 = new int[8];

         for(int var2 = 0; var2 < 8; ++var2) {
            int var3 = var2 * 2;
            var4[var2] = Ints.fromBytes((byte)0, (byte)0, var1[var3], var1[var3 + 1]);
         }

         compressLongestRunOfZeroes(var4);
         return hextetsToIPv6String(var4);
      }
   }

   public static BigInteger toBigInteger(InetAddress var0) {
      return new BigInteger(1, var0.getAddress());
   }

   public static String toUriString(InetAddress var0) {
      if (var0 instanceof Inet6Address) {
         StringBuilder var1 = new StringBuilder();
         var1.append("[");
         var1.append(toAddrString(var0));
         var1.append("]");
         return var1.toString();
      } else {
         return toAddrString(var0);
      }
   }

   public static final class TeredoInfo {
      private final Inet4Address client;
      private final int flags;
      private final int port;
      private final Inet4Address server;

      public TeredoInfo(@NullableDecl Inet4Address var1, @NullableDecl Inet4Address var2, int var3, int var4) {
         boolean var5 = true;
         boolean var6;
         if (var3 >= 0 && var3 <= 65535) {
            var6 = true;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "port '%s' is out of range (0 <= port <= 0xffff)", var3);
         if (var4 >= 0 && var4 <= 65535) {
            var6 = var5;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "flags '%s' is out of range (0 <= flags <= 0xffff)", var4);
         this.server = (Inet4Address)MoreObjects.firstNonNull(var1, InetAddresses.ANY4);
         this.client = (Inet4Address)MoreObjects.firstNonNull(var2, InetAddresses.ANY4);
         this.port = var3;
         this.flags = var4;
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
