package com.company;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class ByteOp {
   private static byte[] HEX_ARRAY;

   static {
      try {
         HEX_ARRAY = "0123456789ABCDEF".getBytes("UTF-8");
      } catch (UnsupportedEncodingException var1) {
         var1.printStackTrace();
      }

   }

   public static Object ByteArrayToObject(byte[] var0) {
      ByteArrayInputStream var1 = new ByteArrayInputStream(var0);
      Object var2 = null;
      ObjectInputStream var3 = null;

      ObjectInputStream var22;
      label151: {
         Object var23;
         label156: {
            label149: {
               label148: {
                  try {
                     var22 = new ObjectInputStream(var1);
                     break label148;
                  } catch (IOException var20) {
                  } finally {
                     ;
                  }

                  var22 = null;
                  break label149;
               }

               try {
                  try {
                     var23 = var22.readObject();
                     break label156;
                  } catch (ClassNotFoundException | IOException var18) {
                  }
               } catch (Throwable var19) {
                  var3 = var22;
                  if (var22 != null) {
                     try {
                        var3.close();
                     } catch (IOException var16) {
                     }
                  }

                  throw var19;
               }
            }

            if (var22 == null) {
               return var2;
            }

            var2 = var3;
            break label151;
         }

         var2 = var23;
      }

      try {
         var22.close();
      } catch (IOException var17) {
      }

      return var2;
   }

   public static int Int32PcmArrayToByte8(int[][] var0, byte[] var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      if (var0.length < var4) {
         return 0;
      } else if (var0[0].length < var3) {
         return 0;
      } else {
         int var8 = var5 / 8;
         var5 = var6 / 8;
         if (var6 <= 0) {
            var5 = var8;
         }

         ByteBuffer var9 = ByteBuffer.allocate(4);

         int var11;
         for(var6 = 0; var6 < var3; var2 = var11) {
            int var10 = 0;

            for(var11 = var2; var10 < var4; ++var10) {
               var9.clear();
               if (var7) {
                  var9.order(ByteOrder.LITTLE_ENDIAN);
               } else {
                  var9.order(ByteOrder.BIG_ENDIAN);
               }

               var9.putInt(var0[var10][var6]);
               byte[] var12 = var9.array();

               for(int var13 = 0; var13 < var5; ++var11) {
                  var2 = var8 - var5 + var13;
                  byte var14;
                  if (var2 >= 0 && var2 < 3) {
                     if (var7) {
                        var14 = var12[var2];
                     } else {
                        var14 = var12[3 - var2];
                     }
                  } else {
                     var14 = 0;
                  }

                  var1[var11] = (byte)var14;
                  ++var13;
               }
            }

            ++var6;
         }

         return var2;
      }
   }

   public static byte[] ObjectToByteArray(Object var0) {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();

      byte[] var11;
      try {
         ObjectOutputStream var2 = new ObjectOutputStream(var1);
         var2.writeObject(var0);
         var2.flush();
         var11 = var1.toByteArray();
         return var11;
      } catch (IOException var9) {
      } finally {
         try {
            var1.close();
         } catch (IOException var8) {
         }

      }

      var11 = null;
      return var11;
   }

   public static int b2ui(byte var0) {
      return var0 & 255;
   }

   public static byte[] combineByteArray(byte[]... var0) {
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 += var0[var2].length;
      }

      byte[] var4 = new byte[var3];
      var1 = var0.length;
      var2 = 0;

      for(var3 = 0; var2 < var1; ++var2) {
         byte[] var5 = var0[var2];
         System.arraycopy(var5, 0, var4, var3, var5.length);
         var3 += var5.length;
      }

      return var4;
   }

   public static byte[] get_2_bytes_from_Integer_unsigned(long var0, ByteOrder var2) {
      byte[] var3 = new byte[2];
      if (var2 == ByteOrder.LITTLE_ENDIAN) {
         var3[0] = (byte)((byte)((int)(var0 & 255L)));
         var3[1] = (byte)((byte)((int)(var0 >> 8 & 255L)));
      } else {
         var3[1] = (byte)((byte)((int)(var0 & 255L)));
         var3[0] = (byte)((byte)((int)(var0 >> 8 & 255L)));
      }

      return var3;
   }

   public static byte[] get_4_bytes_from_Long_unsigned(long var0, ByteOrder var2) {
      byte[] var3 = new byte[4];
      if (var2 == ByteOrder.LITTLE_ENDIAN) {
         var3[0] = (byte)((byte)((int)(var0 & 255L)));
         var3[1] = (byte)((byte)((int)(var0 >> 8 & 255L)));
         var3[2] = (byte)((byte)((int)(var0 >> 16 & 255L)));
         var3[3] = (byte)((byte)((int)(var0 >> 24 & 255L)));
      } else {
         var3[3] = (byte)((byte)((int)(var0 & 255L)));
         var3[2] = (byte)((byte)((int)(var0 >> 8 & 255L)));
         var3[1] = (byte)((byte)((int)(var0 >> 16 & 255L)));
         var3[0] = (byte)((byte)((int)(var0 >> 24 & 255L)));
      }

      return var3;
   }

   public static byte[] get_BytesArray_from_HexString(String var0) {
      int var1 = var0.length();
      if (var1 % 2 != 0) {
         return null;
      } else {
         byte[] var2 = new byte[var1 / 2];

         for(int var3 = 0; var3 < var1; var3 += 2) {
            var2[var3 / 2] = (byte)((byte)((Character.digit(var0.charAt(var3), 16) << 4) + Character.digit(var0.charAt(var3 + 1), 16)));
         }

         return var2;
      }
   }

   public static byte[] get_BytesArray_from_String(String var0) {
      byte[] var2;
      try {
         var2 = var0.getBytes("UTF-8");
      } catch (UnsupportedEncodingException var1) {
         var2 = null;
      }

      return var2;
   }

   public static String get_HexString_from_BytesArray(byte[] var0) {
      byte[] var1 = new byte[var0.length * 2];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         int var3 = b2ui(var0[var2]);
         int var4 = var2 * 2;
         byte[] var5 = HEX_ARRAY;
         var1[var4] = (byte)var5[var3 >>> 4];
         var1[var4 + 1] = (byte)var5[var3 & 15];
      }

      return new String(var1, StandardCharsets.UTF_8);
   }

   public static String get_HexString_from_BytesArray(byte[] var0, String var1) {
      if (var1 != null && var1.length() != 0) {
         byte[] var2 = new byte[2];
         String var3 = "";

         for(int var4 = 0; var4 < var0.length; ++var4) {
            int var5 = b2ui(var0[var4]);
            byte[] var6 = HEX_ARRAY;
            var2[0] = (byte)var6[var5 >>> 4];
            var2[1] = (byte)var6[var5 & 15];
            String var8 = new String(var2, StandardCharsets.UTF_8);
            StringBuilder var7;
            if (var4 == 0) {
               var7 = new StringBuilder();
               var7.append(var3);
               var7.append(var8);
               var3 = var7.toString();
            } else {
               var7 = new StringBuilder();
               var7.append(var3);
               var7.append(var1);
               var7.append(var8);
               var3 = var7.toString();
            }
         }

         return var3;
      } else {
         return get_HexString_from_BytesArray(var0);
      }
   }

   public static int get_Integer_from_2_bytes_unsigned(byte[] var0, ByteOrder var1) {
      if (var0 != null && var0.length >= 2) {
         int var2;
         int var3;
         if (var1 == ByteOrder.LITTLE_ENDIAN) {
            var2 = b2ui(var0[1]) << 8;
            var3 = b2ui(var0[0]);
         } else {
            var2 = b2ui(var0[0]) << 8;
            var3 = b2ui(var0[1]);
         }

         return var2 + var3;
      } else {
         return 0;
      }
   }

   public static int get_Integer_from_4_bytes_signed(byte[] var0, ByteOrder var1) {
      ByteBuffer var2 = ByteBuffer.wrap(var0, 0, 4);
      var2.order(var1);
      return var2.getInt();
   }

   public static long get_Long_from_4_bytes_unsigned(byte[] var0, ByteOrder var1) {
      if (var0 != null && var0.length >= 4) {
         int var2;
         int var3;
         if (var1 == ByteOrder.LITTLE_ENDIAN) {
            var2 = (b2ui(var0[3]) << 24) + (b2ui(var0[2]) << 16) + (b2ui(var0[1]) << 8);
            var3 = b2ui(var0[0]);
         } else {
            var2 = (b2ui(var0[0]) << 24) + (b2ui(var0[1]) << 16) + (b2ui(var0[2]) << 8);
            var3 = b2ui(var0[3]);
         }

         return (long)(var2 + var3);
      } else {
         return 0L;
      }
   }

   public static long get_Long_from_8_bytes_signed(byte[] var0, ByteOrder var1) {
      ByteBuffer var2 = ByteBuffer.wrap(var0, 0, 8);
      var2.order(var1);
      return var2.getLong();
   }

   public static short get_Short_from_2_bytes_signed(byte[] var0, ByteOrder var1) {
      ByteBuffer var2 = ByteBuffer.wrap(var0, 0, 2);
      var2.order(var1);
      return var2.getShort();
   }

   public static String get_String_from_BytesArray(byte[] var0) {
      String var1;
      String var3;
      try {
         var1 = new String(var0, "UTF-8");
      } catch (UnsupportedEncodingException var2) {
         var3 = null;
         return var3;
      }

      var3 = var1;
      return var3;
   }

   public static String get_String_from_BytesArray(byte[] var0, int var1, int var2) {
      return get_String_from_BytesArray(slice_BytesArray(var0, var1, var2));
   }

   public static byte i2b(int var0) {
      return (byte)var0;
   }

   public static long int32ToLong(byte[] var0, ByteOrder var1) {
      return (long)get_Integer_from_4_bytes_signed(var0, var1);
   }

   public static byte[] intToUint16(int var0, ByteOrder var1) {
      return get_2_bytes_from_Integer_unsigned((long)var0, var1);
   }

   public static byte[] longToUint32(long var0, ByteOrder var2) {
      return get_4_bytes_from_Long_unsigned(var0, var2);
   }

   public static byte[] slice_BytesArray(byte[] var0, int var1, int var2) {
      return Arrays.copyOfRange(var0, var1, var2 + var1);
   }

   public static int uint16ToInt(byte[] var0, ByteOrder var1) {
      return get_Integer_from_2_bytes_unsigned(var0, var1);
   }

   public static long uint32ToLong(byte[] var0, ByteOrder var1) {
      return get_Long_from_4_bytes_unsigned(var0, var1);
   }

   /// not part of byte op originally - from
   // http://www.java2s.com/Code/Java/Data-Type/ConverttofromHexstring.htm
   public static byte[] fromHexString(String s) {
      int length = s.length() / 2;
      byte[] bytes = new byte[length];
      for (int i = 0; i < length; i++) {
         bytes[i] = (byte) ((Character.digit(s.charAt(i * 2), 16) << 4) | Character
                 .digit(s.charAt((i * 2) + 1), 16));
      }
      return bytes;
   }

   public static byte[] fromBase64String(String b) {
      return Base64.getDecoder().decode(b);
   }
}
