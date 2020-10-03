package com.company;


import javax.naming.NameNotFoundException;
import javax.swing.text.View;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringOp {
   public static final byte bESCAPE = 37;
   public static final String sESCAPE = "%";
   public static final String sESCAPE2 = "%%";

   public static String ASCII_to_UTF8(String var0) {
      Object var1 = null;
      if (var0 == null) {
         return null;
      } else {
         char[] var2 = var0.toCharArray();
         char[] var3 = new char[var2.length];
         byte var4 = 0;
         int var5 = 0;

         int var6;
         for(var6 = 0; var5 < var2.length; ++var5) {
            if (var2[var5] == '%') {
               ++var5;
               if (var2[var5] != '%') {
                  var3[var6] = (char)((char)Integer.parseInt(var0.substring(var5, var5 + 2), 16));
                  ++var6;
                  ++var5;
                  continue;
               }

               var3[var6] = (char)37;
            } else {
               var3[var6] = (char)var2[var5];
            }

            ++var6;
         }

         byte[] var8 = new byte[var6];

         for(var5 = var4; var5 < var6; ++var5) {
            var8[var5] = (byte)((byte)var3[var5]);
         }

         try {
            var0 = new String(var8, "UTF-8");
         } catch (UnsupportedEncodingException var7) {
            var0 = (String)var1;
         }

         return var0;
      }
   }

   public static byte[] ASCII_to_binary(String var0) {
      if (var0 == null) {
         return null;
      } else {
         int var1 = strlen(var0);
         byte[] var2 = new byte[var1 / 2];
         int var3 = 0;

         int var5;
         for(int var4 = 0; var3 < var1; var3 = var5) {
            var5 = var3 + 2;
            var2[var4] = (byte)((byte)Integer.parseInt(var0.substring(var3, var5), 16));
            ++var4;
         }

         return var2;
      }
   }

   public static byte[] ASCII_to_binary(byte[] var0, int var1) {
      byte[] var2 = new byte[var1];
      System.arraycopy(var0, 0, var2, 0, var1);

      String var4;
      try {
         var4 = new String(var2, "UTF-8");
      } catch (UnsupportedEncodingException var3) {
         var4 = null;
      }

      return ASCII_to_binary(var4);
   }

   public static float[] ArrayBytesToFloats(byte[] var0, int var1, ByteOrder var2) {
      float[] var3 = new float[var1 / 4];
      ByteBuffer.wrap(var0, 0, var1).order(var2).asFloatBuffer().get(var3);
      return var3;
   }

   public static short[] ArrayBytesToShorts(byte[] var0, int var1, ByteOrder var2) {
      if (var0 == null) {
         return null;
      } else {
         short[] var3 = new short[var1 / 2];
         ByteBuffer.wrap(var0, 0, var1).order(var2).asShortBuffer().get(var3);
         return var3;
      }
   }

   public static byte[] ArrayFloatsToBytes(float[] var0, int var1, ByteOrder var2) {
      byte[] var3 = new byte[var1 * 4];
      ByteBuffer.wrap(var3).order(var2).asFloatBuffer().put(var0, 0, var1);
      return var3;
   }

   public static byte[] ArrayShortsToBytes(short[] var0, int var1, ByteOrder var2) {
      if (var0 == null) {
         return null;
      } else {
         byte[] var3 = new byte[var1 * 2];
         ByteBuffer.wrap(var3).order(var2).asShortBuffer().put(var0, 0, var1);
         return var3;
      }
   }

   public static String ByteArrayToString(byte[] var0) {
      return new String(var0, 0, var0.length);
   }

   public static String ByteArrayToString(byte[] var0, Charset var1) {
      String var2;
      String var4;
      try {
         var2 = new String(var0, String.valueOf(var1));
      } catch (UnsupportedEncodingException var3) {
         var4 = null;
         return var4;
      }

      var4 = var2;
      return var4;
   }

   public static void FloatsGain(float[] var0, float var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         var0[var2] *= var1;
      }

   }

   public static String HEX_to_DECIMAL(String var0) {
      return String.valueOf(Integer.parseInt(var0, 16));
   }

   public static String HourMinuteSecondToString(int var0, int var1, int var2, String var3) {
      StringBuilder var4 = new StringBuilder();
      var4.append(String.format("%02d", var0));
      var4.append(var3);
      var4.append(String.format("%02d", var1));
      var4.append(var3);
      var4.append(String.format("%02d", var2));
      return var4.toString();
   }

   public static String HourMinuteToString(int var0, int var1, String var2) {
      StringBuilder var3 = new StringBuilder();
      String var4 = String.format("%02d", var0);
      String var5 = String.format("%02d", var1);
      var3.append(var4);
      var3.append(var2);
      var3.append(var5);
      return var3.toString();
   }

   public static String SecToStringMinSec(int var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(String.format("%02d", var0 / 60));
      var2.append(var1);
      var2.append(String.format("%02d", var0 % 60));
      return var2.toString();
   }

   public static void ShortsDeduct(short[] var0, short[] var1) {
      int var2 = Math.min(var0.length, var1.length);

      for(int var3 = 0; var3 < var2; ++var3) {
         var0[var3] = (short)((short)(var0[var3] - var1[var3]));
      }

   }

   public static void ShortsGain(short[] var0, float var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         var0[var2] = (short)((short)((int)((float)var0[var2] * var1)));
      }

   }

   public static String UTF8_to_ASCII(String var0) {
      byte[] var1 = null;
      if (var0 == null) {
         return null;
      } else {
         label44: {
            byte[] var8;
            try {
               var8 = var0.getBytes("UTF8");
            } catch (UnsupportedEncodingException var7) {
               break label44;
            }

            var1 = var8;
         }

         int var2 = 0;

         for(var0 = ""; var2 < var1.length; ++var2) {
            byte var3 = var1[var2];
            int var4;
            if (var3 < 0) {
               var4 = var3 + 256;
            } else {
               var4 = var3;
            }

            StringBuilder var5;
            if (var3 == 37) {
               var5 = new StringBuilder();
               var5.append(var0);
               var5.append("%%");
               var0 = var5.toString();
            } else if (var4 <= 127 && var4 >= 47) {
               var5 = new StringBuilder();
               var5.append(var0);
               var5.append(Character.toString((char)var3));
               var0 = var5.toString();
            } else {
               var5 = new StringBuilder();
               var5.append(var0);
               var5.append("%");
               String var10 = var5.toString();
               String var6 = Integer.toHexString(var4);
               var0 = var10;
               if (var6.length() < 2) {
                  StringBuilder var9 = new StringBuilder();
                  var9.append(var10);
                  var9.append("0");
                  var0 = var9.toString();
               }

               var5 = new StringBuilder();
               var5.append(var0);
               var5.append(var6);
               var0 = var5.toString();
            }
         }

         return var0;
      }
   }


   public static byte[] combineByteArrays(byte[] var0, byte[] var1) {
      byte[] var2 = new byte[var0.length + var1.length];
      System.arraycopy(var0, 0, var2, 0, var0.length);
      System.arraycopy(var1, 0, var2, var0.length, var1.length);
      return var2;
   }

   public static byte[] getAbsMaxAverageOfBytes(byte[] var0, int var1) {
      byte[] var2 = new byte[]{0, 0};
      float var3 = 0.0F;
      int var4 = 0;

      int var5;
      int var8;
      for(var5 = 0; var4 < var1; var5 = var8) {
         byte var6 = var0[var4];
         int var7 = var6;
         if (var6 < 0) {
            var7 = var6 * -1;
         }

         var8 = var5;
         if (var7 > var5) {
            var8 = var7;
         }

         var3 += (float)(var7 / var1);
         ++var4;
      }

      var2[0] = (byte)((byte)var5);
      var2[1] = (byte)((byte)((int)var3));
      return var2;
   }

   public static short[] getAbsMaxAverageOfShorts(short[] var0, int var1) {
      short[] var2 = new short[]{0, 0};
      float var3 = 0.0F;
      int var4 = 0;

      short var5;
      short var6;
      for(var5 = 0; var4 < var1; var5 = var6) {
         var6 = var0[var4];
         short var7 = var6;
         if (var6 < 0) {
            var7 = (short)(var6 * -1);
         }

         var6 = var5;
         if (var7 > var5) {
            var6 = var7;
         }

         var3 += (float)(var7 / var1);
         ++var4;
      }

      var2[0] = (short)var5;
      var2[1] = (short)((short)((int)var3));
      return var2;
   }

//   public static String getCheckFromBytes(byte[] var0) {
//      int var1 = 0;
//
//      int var2;
//      for(var2 = 0; var1 < var0.length; ++var1) {
//         var2 += var0[var1] & 255;
//      }
//
//      return String.valueOf(MathOp.mod(var2, var0.length));
//   }

   public static HashMap<String, String> getHashMapFromString(String var0) {
      int var1 = strlen(var0);
      HashMap var2 = null;
      if (var1 <= 0) {
         return null;
      } else {
         String[] var3 = var0.split("&");
         HashMap var5 = var2;
         if (var3.length > 0) {
            var2 = new HashMap();
            int var4 = var3.length;
            var1 = 0;

            while(true) {
               var5 = var2;
               if (var1 >= var4) {
                  break;
               }

               String[] var6 = var3[var1].split("=");
               var2.put(var6[0], var6[1]);
               ++var1;
            }
         }

         return var5;
      }
   }

   public static String getHexFromBytes(byte[] var0, boolean var1, String var2) {
      if (var0 != null && var0.length != 0) {
         String var3 = new String();

         for(int var4 = 0; var4 < var0.length; ++var4) {
            String var5 = var3;
            if (strlen(var2) > 0) {
               label36: {
                  if (var4 <= 0) {
                     var5 = var3;
                     if (!var1) {
                        break label36;
                     }
                  }

                  StringBuilder var8 = new StringBuilder();
                  var8.append(var3);
                  var8.append(var2);
                  var5 = var8.toString();
               }
            }

            String var6 = Integer.toHexString(var0[var4] & 255);
            var3 = var6;
            if (var6.length() == 1) {
               StringBuilder var7 = new StringBuilder();
               var7.append('0');
               var7.append(var6);
               var3 = var7.toString();
            }

            StringBuilder var9 = new StringBuilder();
            var9.append(var5);
            var9.append(var3);
            var3 = var9.toString();
         }

         return var3;
      } else {
         return null;
      }
   }
//
//   public static String getLocationName(Context var0, double var1, double var3) {
//      String var5 = "Not Found";
//      Geocoder var14 = new Geocoder(var0, Locale.getDefault());
//      String var6 = var5;
//
//      IOException var10000;
//      label61: {
//         Iterator var7;
//         boolean var10001;
//         try {
//            var7 = var14.getFromLocation(var1, var3, 10).iterator();
//         } catch (IOException var13) {
//            var10000 = var13;
//            var10001 = false;
//            break label61;
//         }
//
//         String var15 = var5;
//
//         while(true) {
//            var6 = var15;
//            var5 = var15;
//
//            try {
//               if (!var7.hasNext()) {
//                  return var5;
//               }
//            } catch (IOException var12) {
//               var10000 = var12;
//               var10001 = false;
//               break;
//            }
//
//            var6 = var15;
//
//            Address var17;
//            try {
//               var17 = (Address)var7.next();
//            } catch (IOException var11) {
//               var10000 = var11;
//               var10001 = false;
//               break;
//            }
//
//            if (var17 != null) {
//               var6 = var15;
//
//               try {
//                  var5 = var17.getLocality();
//               } catch (IOException var10) {
//                  var10000 = var10;
//                  var10001 = false;
//                  break;
//               }
//
//               if (var5 != null) {
//                  var6 = var15;
//
//                  boolean var8;
//                  try {
//                     var8 = var5.equals("");
//                  } catch (IOException var9) {
//                     var10000 = var9;
//                     var10001 = false;
//                     break;
//                  }
//
//                  if (!var8) {
//                     var15 = var5;
//                  }
//               }
//            }
//         }
//      }
//
//      IOException var16 = var10000;
//      var16.printStackTrace();
//      var5 = var6;
//      return var5;
//   }

   public static String[] getStringArrayFromArrayList(ArrayList<String> var0) {
      String[] var1 = new String[var0.size()];

      for(int var2 = 0; var2 < var0.size(); ++var2) {
         var1[var2] = (String)var0.get(var2);
      }

      return var1;
   }


   public static String get_HexString_normalized(String var0) {
      boolean var1 = var0.contains(" ");
      Object var2 = null;
      String[] var3;
      if (var1) {
         var3 = var0.split(" ");
      } else if (var0.contains(":")) {
         var3 = var0.split(":");
      } else {
         if (strlen(var0) % 2 != 0) {
            return null;
         }

         var3 = null;
      }

      if (var3 != null) {
         int var4 = 0;

         for(var0 = ""; var4 < var3.length; ++var4) {
            String var5 = var3[var4];
            if (strlen(var5) > 2) {
               return null;
            }

            String var6 = var5;
            if (strlen(var5) < 2) {
               StringBuilder var8 = new StringBuilder();
               var8.append("0");
               var8.append(var5);
               var6 = var8.toString();
            }

            StringBuilder var7 = new StringBuilder();
            var7.append(var0);
            var7.append(var6);
            var0 = var7.toString();
         }
      }

      if (!isHexString(var0)) {
         var0 = (String)var2;
      }

      return var0;
   }


   public static String intToStr(int var0, int var1) {
      if (var0 >= 0 && var1 >= 2 && var1 <= 36) {
         String var2;
         for(var2 = ""; var0 > 0; var0 /= var1) {
            int var3 = var0 % var1;
            StringBuilder var4 = new StringBuilder();
            var4.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(var3, var3 + 1));
            var4.append(var2);
            var2 = var4.toString();
         }

         return var2;
      } else {
         return null;
      }
   }

   public static boolean isBytesEqual(byte[] var0, byte[] var1) {
      if (var0 != null && var1 != null) {
         for(int var2 = 0; var2 < var0.length; ++var2) {
            if (var0[var2] != var1[var2]) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean isHexString(String var0) {
      return Pattern.compile("[A-Fa-f0-9]+").matcher(var0).matches();
   }

   public static String keepOnlyAlphabetNumber(String var0) {
      String var1 = null;
      if (var0 == null) {
         return null;
      } else {
         byte[] var6;
         try {
            var6 = var0.getBytes("UTF8");
         } catch (UnsupportedEncodingException var5) {
            var6 = (byte[]) null;
         }

         int var2 = 0;

         String var4;
         for(var1 = ""; var2 < var6.length; var1 = var4) {
            label49: {
               byte var3 = var6[var2];
               if ((var3 < 48 || var3 > 57) && (var3 < 65 || var3 > 90)) {
                  var4 = var1;
                  if (var3 < 97) {
                     break label49;
                  }

                  var4 = var1;
                  if (var3 > 122) {
                     break label49;
                  }
               }

               StringBuilder var7 = new StringBuilder();
               var7.append(var1);
               var7.append(Character.toString((char)var3));
               var4 = var7.toString();
            }

            ++var2;
         }

         return var1;
      }
   }

   public static String limitLengthString(String var0, int var1) {
      if (var0.length() < var1) {
         return var0;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0.substring(0, var1));
         var2.append(" ...");
         return var2.toString();
      }
   }

   public static int strlen(String var0) {
      int var1;
      if (var0 != null) {
         var1 = var0.length();
      } else {
         var1 = 0;
      }

      return var1;
   }

   public static byte[] uint16ToByteArray(int var0) {
      return new byte[]{(byte)(var0 & 255), (byte)(var0 >> 8 & 255)};
   }

   public String getBig5FromUTF8(String var1) {
      String var2;
      try {
         var2 = new String(var1.getBytes("UTF-8"), "big5");
      } catch (Exception var3) {
         var3.printStackTrace();
         var1 = "";
         return var1;
      }

      var1 = var2;
      return var1;
   }

   public String getUTF8FromBig5(String var1) {
      String var2;
      try {
         var2 = new String(var1.getBytes("big5"), "UTF-8");
      } catch (Exception var3) {
         var3.printStackTrace();
         var1 = "";
         return var1;
      }

      var1 = var2;
      return var1;
   }
}
