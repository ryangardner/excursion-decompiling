package javax.mail.internet;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import com.sun.mail.util.BEncoderStream;
import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.QDecoderStream;
import com.sun.mail.util.QEncoderStream;
import com.sun.mail.util.QPDecoderStream;
import com.sun.mail.util.QPEncoderStream;
import com.sun.mail.util.UUDecoderStream;
import com.sun.mail.util.UUEncoderStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;

public class MimeUtility {
   public static final int ALL = -1;
   static final int ALL_ASCII = 1;
   static final int MOSTLY_ASCII = 2;
   static final int MOSTLY_NONASCII = 3;
   private static boolean decodeStrict;
   private static String defaultJavaCharset;
   private static String defaultMIMECharset;
   private static boolean encodeEolStrict;
   private static boolean foldEncodedWords;
   private static boolean foldText;
   private static Hashtable java2mime;
   private static Hashtable mime2java;

   static {
      // $FF: Couldn't be decompiled
   }

   private MimeUtility() {
   }

   static int checkAscii(InputStream var0, int var1, boolean var2) {
      int var3 = var1;
      boolean var4;
      if (encodeEolStrict && var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      byte[] var5 = (byte[])null;
      byte var6 = -1;
      var1 = 4096;
      int var7 = var1;
      if (var3 != 0) {
         if (var3 != -1) {
            var1 = Math.min(var3, 4096);
         }

         var5 = new byte[var1];
         var7 = var1;
      }

      int var8 = 0;
      int var9 = 0;
      boolean var10 = false;
      boolean var22 = false;
      int var11 = 0;

      label121:
      while(true) {
         byte var12 = 0;
         if (var3 == 0) {
            break;
         }

         int var13 = var8;
         int var14 = var9;
         boolean var15 = var10;
         boolean var16 = var22;

         label131: {
            int var17;
            boolean var10001;
            try {
               var17 = var0.read(var5, 0, var7);
            } catch (IOException var21) {
               var10001 = false;
               break label131;
            }

            if (var17 == var6) {
               break;
            }

            int var18 = 0;
            var13 = var11;
            boolean var25 = var22;
            boolean var26 = var10;
            int var27 = var12;

            while(true) {
               if (var18 >= var17) {
                  var1 = var3;
                  if (var3 != var6) {
                     var1 = var3 - var17;
                  }

                  var3 = var1;
                  var10 = var26;
                  var22 = var25;
                  var11 = var13;
                  continue label121;
               }

               int var23 = var5[var18] & 255;
               var22 = var26;
               if (var4) {
                  label147: {
                     if (var27 != 13 || var23 == 10) {
                        var22 = var26;
                        if (var27 == 13) {
                           break label147;
                        }

                        var22 = var26;
                        if (var23 != 10) {
                           break label147;
                        }
                     }

                     var22 = true;
                  }
               }

               int var24;
               if (var23 != 13 && var23 != 10) {
                  ++var13;
                  var24 = var13;
                  if (var13 > 998) {
                     var25 = true;
                     var24 = var13;
                  }
               } else {
                  var24 = 0;
               }

               var13 = var8;
               var14 = var9;
               var15 = var22;
               var16 = var25;

               boolean var19;
               try {
                  var19 = nonascii(var23);
               } catch (IOException var20) {
                  var10001 = false;
                  break;
               }

               if (var19) {
                  if (var2) {
                     return 3;
                  }

                  ++var9;
               } else {
                  ++var8;
               }

               ++var18;
               var27 = var23;
               var6 = -1;
               var26 = var22;
               var13 = var24;
            }
         }

         var22 = var16;
         var10 = var15;
         var9 = var14;
         var8 = var13;
         break;
      }

      if (var3 == 0 && var2) {
         return 3;
      } else if (var9 == 0) {
         if (var10) {
            return 3;
         } else {
            return var22 ? 2 : 1;
         }
      } else {
         return var8 > var9 ? 2 : 3;
      }
   }

   static int checkAscii(String var0) {
      int var1 = var0.length();
      int var2 = 0;
      int var3 = 0;

      int var4;
      for(var4 = 0; var2 < var1; ++var2) {
         if (nonascii(var0.charAt(var2))) {
            ++var3;
         } else {
            ++var4;
         }
      }

      if (var3 == 0) {
         return 1;
      } else if (var4 > var3) {
         return 2;
      } else {
         return 3;
      }
   }

   static int checkAscii(byte[] var0) {
      int var1 = 0;
      int var2 = 0;

      int var3;
      for(var3 = 0; var1 < var0.length; ++var1) {
         if (nonascii(var0[var1] & 255)) {
            ++var2;
         } else {
            ++var3;
         }
      }

      if (var2 == 0) {
         return 1;
      } else if (var3 > var2) {
         return 2;
      } else {
         return 3;
      }
   }

   public static InputStream decode(InputStream var0, String var1) throws MessagingException {
      if (var1.equalsIgnoreCase("base64")) {
         return new BASE64DecoderStream(var0);
      } else if (var1.equalsIgnoreCase("quoted-printable")) {
         return new QPDecoderStream(var0);
      } else if (!var1.equalsIgnoreCase("uuencode") && !var1.equalsIgnoreCase("x-uuencode") && !var1.equalsIgnoreCase("x-uue")) {
         if (!var1.equalsIgnoreCase("binary") && !var1.equalsIgnoreCase("7bit") && !var1.equalsIgnoreCase("8bit")) {
            StringBuilder var2 = new StringBuilder("Unknown encoding: ");
            var2.append(var1);
            throw new MessagingException(var2.toString());
         } else {
            return var0;
         }
      } else {
         return new UUDecoderStream(var0);
      }
   }

   private static String decodeInnerWords(String var0) throws UnsupportedEncodingException {
      StringBuffer var1 = new StringBuffer();
      int var2 = 0;

      while(true) {
         int var3 = var0.indexOf("=?", var2);
         if (var3 < 0) {
            break;
         }

         var1.append(var0.substring(var2, var3));
         int var4 = var0.indexOf(63, var3 + 2);
         if (var4 < 0) {
            break;
         }

         var4 = var0.indexOf(63, var4 + 1);
         if (var4 < 0) {
            break;
         }

         var4 = var0.indexOf("?=", var4 + 1);
         if (var4 < 0) {
            break;
         }

         var2 = var4 + 2;
         String var5 = var0.substring(var3, var2);

         label39: {
            String var6;
            try {
               var6 = decodeWord(var5);
            } catch (ParseException var7) {
               break label39;
            }

            var5 = var6;
         }

         var1.append(var5);
      }

      if (var2 == 0) {
         return var0;
      } else {
         if (var2 < var0.length()) {
            var1.append(var0.substring(var2));
         }

         return var1.toString();
      }
   }

   public static String decodeText(String var0) throws UnsupportedEncodingException {
      if (var0.indexOf("=?") == -1) {
         return var0;
      } else {
         StringTokenizer var1 = new StringTokenizer(var0, " \t\n\r", true);
         StringBuffer var2 = new StringBuffer();
         StringBuffer var3 = new StringBuffer();
         boolean var4 = false;

         while(true) {
            while(var1.hasMoreTokens()) {
               var0 = var1.nextToken();
               char var5 = var0.charAt(0);
               if (var5 != ' ' && var5 != '\t' && var5 != '\r' && var5 != '\n') {
                  label72: {
                     String var6;
                     label80: {
                        label81: {
                           boolean var10001;
                           try {
                              var6 = decodeWord(var0);
                           } catch (ParseException var8) {
                              var10001 = false;
                              break label81;
                           }

                           if (var4) {
                              break label80;
                           }

                           try {
                              if (var3.length() > 0) {
                                 var2.append(var3);
                              }
                              break label80;
                           } catch (ParseException var7) {
                              var10001 = false;
                           }
                        }

                        if (!decodeStrict) {
                           var6 = decodeInnerWords(var0);
                           if (var6 != var0) {
                              if ((!var4 || !var0.startsWith("=?")) && var3.length() > 0) {
                                 var2.append(var3);
                              }

                              var4 = var0.endsWith("?=");
                              var0 = var6;
                              break label72;
                           }

                           if (var3.length() > 0) {
                              var2.append(var3);
                           }
                        } else if (var3.length() > 0) {
                           var2.append(var3);
                        }

                        var4 = false;
                        break label72;
                     }

                     var0 = var6;
                     var4 = true;
                  }

                  var2.append(var0);
                  var3.setLength(0);
               } else {
                  var3.append(var5);
               }
            }

            var2.append(var3);
            return var2.toString();
         }
      }
   }

   public static String decodeWord(String var0) throws ParseException, UnsupportedEncodingException {
      StringBuilder var7;
      if (!var0.startsWith("=?")) {
         var7 = new StringBuilder("encoded word does not start with \"=?\": ");
         var7.append(var0);
         throw new ParseException(var7.toString());
      } else {
         int var1 = var0.indexOf(63, 2);
         if (var1 != -1) {
            String var2 = javaCharset(var0.substring(2, var1));
            ++var1;
            int var3 = var0.indexOf(63, var1);
            if (var3 == -1) {
               var7 = new StringBuilder("encoded word does not include encoding: ");
               var7.append(var0);
               throw new ParseException(var7.toString());
            } else {
               String var4 = var0.substring(var1, var3);
               ++var3;
               var1 = var0.indexOf("?=", var3);
               if (var1 != -1) {
                  String var5 = var0.substring(var3, var1);

                  UnsupportedEncodingException var39;
                  UnsupportedEncodingException var45;
                  label147: {
                     IOException var10000;
                     label195: {
                        boolean var10001;
                        try {
                           var3 = var5.length();
                        } catch (UnsupportedEncodingException var36) {
                           var45 = var36;
                           var10001 = false;
                           break label147;
                        } catch (IOException var37) {
                           var10000 = var37;
                           var10001 = false;
                           break label195;
                        } catch (IllegalArgumentException var38) {
                           var10001 = false;
                           throw new UnsupportedEncodingException(var2);
                        }

                        label167: {
                           String var6 = "";
                           String var43 = var6;
                           if (var3 > 0) {
                              ByteArrayInputStream var8;
                              Object var44;
                              label137: {
                                 try {
                                    var8 = new ByteArrayInputStream(ASCIIUtility.getBytes(var5));
                                    if (var4.equalsIgnoreCase("B")) {
                                       var44 = new BASE64DecoderStream(var8);
                                       break label137;
                                    }
                                 } catch (UnsupportedEncodingException var33) {
                                    var45 = var33;
                                    var10001 = false;
                                    break label147;
                                 } catch (IOException var34) {
                                    var10000 = var34;
                                    var10001 = false;
                                    break label195;
                                 } catch (IllegalArgumentException var35) {
                                    var10001 = false;
                                    throw new UnsupportedEncodingException(var2);
                                 }

                                 try {
                                    if (!var4.equalsIgnoreCase("Q")) {
                                       break label167;
                                    }

                                    var44 = new QDecoderStream(var8);
                                 } catch (UnsupportedEncodingException var30) {
                                    var45 = var30;
                                    var10001 = false;
                                    break label147;
                                 } catch (IOException var31) {
                                    var10000 = var31;
                                    var10001 = false;
                                    break label195;
                                 } catch (IllegalArgumentException var32) {
                                    var10001 = false;
                                    throw new UnsupportedEncodingException(var2);
                                 }
                              }

                              byte[] var41;
                              try {
                                 var3 = var8.available();
                                 var41 = new byte[var3];
                                 var3 = ((InputStream)var44).read(var41, 0, var3);
                              } catch (UnsupportedEncodingException var27) {
                                 var45 = var27;
                                 var10001 = false;
                                 break label147;
                              } catch (IOException var28) {
                                 var10000 = var28;
                                 var10001 = false;
                                 break label195;
                              } catch (IllegalArgumentException var29) {
                                 var10001 = false;
                                 throw new UnsupportedEncodingException(var2);
                              }

                              if (var3 <= 0) {
                                 var43 = var6;
                              } else {
                                 try {
                                    var43 = new String(var41, 0, var3, var2);
                                 } catch (UnsupportedEncodingException var24) {
                                    var45 = var24;
                                    var10001 = false;
                                    break label147;
                                 } catch (IOException var25) {
                                    var10000 = var25;
                                    var10001 = false;
                                    break label195;
                                 } catch (IllegalArgumentException var26) {
                                    var10001 = false;
                                    throw new UnsupportedEncodingException(var2);
                                 }
                              }
                           }

                           var1 += 2;
                           var6 = var43;

                           label162: {
                              try {
                                 if (var1 >= var0.length()) {
                                    break label162;
                                 }

                                 var6 = var0.substring(var1);
                              } catch (UnsupportedEncodingException var21) {
                                 var45 = var21;
                                 var10001 = false;
                                 break label147;
                              } catch (IOException var22) {
                                 var10000 = var22;
                                 var10001 = false;
                                 break label195;
                              } catch (IllegalArgumentException var23) {
                                 var10001 = false;
                                 throw new UnsupportedEncodingException(var2);
                              }

                              var0 = var6;

                              try {
                                 if (!decodeStrict) {
                                    var0 = decodeInnerWords(var6);
                                 }
                              } catch (UnsupportedEncodingException var18) {
                                 var45 = var18;
                                 var10001 = false;
                                 break label147;
                              } catch (IOException var19) {
                                 var10000 = var19;
                                 var10001 = false;
                                 break label195;
                              } catch (IllegalArgumentException var20) {
                                 var10001 = false;
                                 throw new UnsupportedEncodingException(var2);
                              }

                              try {
                                 StringBuilder var42 = new StringBuilder(var43);
                                 var42.append(var0);
                                 var6 = var42.toString();
                              } catch (UnsupportedEncodingException var15) {
                                 var45 = var15;
                                 var10001 = false;
                                 break label147;
                              } catch (IOException var16) {
                                 var10000 = var16;
                                 var10001 = false;
                                 break label195;
                              } catch (IllegalArgumentException var17) {
                                 var10001 = false;
                                 throw new UnsupportedEncodingException(var2);
                              }
                           }

                           try {
                              return var6;
                           } catch (IllegalArgumentException var12) {
                              var10001 = false;
                              throw new UnsupportedEncodingException(var2);
                           } catch (UnsupportedEncodingException var13) {
                              var45 = var13;
                              var10001 = false;
                              break label147;
                           } catch (IOException var14) {
                              var10000 = var14;
                              var10001 = false;
                              break label195;
                           }
                        }

                        try {
                           var7 = new StringBuilder("unknown encoding: ");
                           var7.append(var4);
                           var39 = new UnsupportedEncodingException(var7.toString());
                           throw var39;
                        } catch (UnsupportedEncodingException var9) {
                           var45 = var9;
                           var10001 = false;
                           break label147;
                        } catch (IOException var10) {
                           var10000 = var10;
                           var10001 = false;
                        } catch (IllegalArgumentException var11) {
                           var10001 = false;
                           throw new UnsupportedEncodingException(var2);
                        }
                     }

                     IOException var40 = var10000;
                     throw new ParseException(var40.toString());
                  }

                  var39 = var45;
                  throw var39;
               } else {
                  var7 = new StringBuilder("encoded word does not end with \"?=\": ");
                  var7.append(var0);
                  throw new ParseException(var7.toString());
               }
            }
         } else {
            var7 = new StringBuilder("encoded word does not include charset: ");
            var7.append(var0);
            throw new ParseException(var7.toString());
         }
      }
   }

   private static void doEncode(String var0, boolean var1, String var2, int var3, String var4, boolean var5, boolean var6, StringBuffer var7) throws UnsupportedEncodingException {
      byte[] var8 = var0.getBytes(var2);
      int var9;
      if (var1) {
         var9 = BEncoderStream.encodedLength(var8);
      } else {
         var9 = QEncoderStream.encodedLength(var8, var6);
      }

      byte var10 = 0;
      if (var9 > var3) {
         var9 = var0.length();
         if (var9 > 1) {
            int var15 = var9 / 2;
            doEncode(var0.substring(0, var15), var1, var2, var3, var4, var5, var6, var7);
            doEncode(var0.substring(var15, var9), var1, var2, var3, var4, false, var6, var7);
            return;
         }
      }

      ByteArrayOutputStream var14 = new ByteArrayOutputStream();
      Object var12;
      if (var1) {
         var12 = new BEncoderStream(var14);
      } else {
         var12 = new QEncoderStream(var14, var6);
      }

      try {
         ((OutputStream)var12).write(var8);
         ((OutputStream)var12).close();
      } catch (IOException var11) {
      }

      byte[] var13 = var14.toByteArray();
      if (!var5) {
         if (foldEncodedWords) {
            var7.append("\r\n ");
         } else {
            var7.append(" ");
         }
      }

      var7.append(var4);

      for(var3 = var10; var3 < var13.length; ++var3) {
         var7.append((char)var13[var3]);
      }

      var7.append("?=");
   }

   public static OutputStream encode(OutputStream var0, String var1) throws MessagingException {
      if (var1 == null) {
         return var0;
      } else if (var1.equalsIgnoreCase("base64")) {
         return new BASE64EncoderStream(var0);
      } else if (var1.equalsIgnoreCase("quoted-printable")) {
         return new QPEncoderStream(var0);
      } else if (!var1.equalsIgnoreCase("uuencode") && !var1.equalsIgnoreCase("x-uuencode") && !var1.equalsIgnoreCase("x-uue")) {
         if (!var1.equalsIgnoreCase("binary") && !var1.equalsIgnoreCase("7bit") && !var1.equalsIgnoreCase("8bit")) {
            StringBuilder var2 = new StringBuilder("Unknown encoding: ");
            var2.append(var1);
            throw new MessagingException(var2.toString());
         } else {
            return var0;
         }
      } else {
         return new UUEncoderStream(var0);
      }
   }

   public static OutputStream encode(OutputStream var0, String var1, String var2) throws MessagingException {
      if (var1 == null) {
         return var0;
      } else if (var1.equalsIgnoreCase("base64")) {
         return new BASE64EncoderStream(var0);
      } else if (var1.equalsIgnoreCase("quoted-printable")) {
         return new QPEncoderStream(var0);
      } else if (!var1.equalsIgnoreCase("uuencode") && !var1.equalsIgnoreCase("x-uuencode") && !var1.equalsIgnoreCase("x-uue")) {
         if (!var1.equalsIgnoreCase("binary") && !var1.equalsIgnoreCase("7bit") && !var1.equalsIgnoreCase("8bit")) {
            StringBuilder var3 = new StringBuilder("Unknown encoding: ");
            var3.append(var1);
            throw new MessagingException(var3.toString());
         } else {
            return var0;
         }
      } else {
         return new UUEncoderStream(var0, var2);
      }
   }

   public static String encodeText(String var0) throws UnsupportedEncodingException {
      return encodeText(var0, (String)null, (String)null);
   }

   public static String encodeText(String var0, String var1, String var2) throws UnsupportedEncodingException {
      return encodeWord(var0, var1, var2, false);
   }

   public static String encodeWord(String var0) throws UnsupportedEncodingException {
      return encodeWord(var0, (String)null, (String)null);
   }

   public static String encodeWord(String var0, String var1, String var2) throws UnsupportedEncodingException {
      return encodeWord(var0, var1, var2, true);
   }

   private static String encodeWord(String var0, String var1, String var2, boolean var3) throws UnsupportedEncodingException {
      int var4 = checkAscii(var0);
      if (var4 == 1) {
         return var0;
      } else {
         String var5;
         String var6;
         if (var1 == null) {
            var5 = getDefaultJavaCharset();
            var6 = getDefaultMIMECharset();
         } else {
            var5 = javaCharset(var1);
            var6 = var1;
         }

         var1 = var2;
         if (var2 == null) {
            if (var4 != 3) {
               var1 = "Q";
            } else {
               var1 = "B";
            }
         }

         boolean var7;
         if (var1.equalsIgnoreCase("B")) {
            var7 = true;
         } else {
            if (!var1.equalsIgnoreCase("Q")) {
               StringBuilder var9 = new StringBuilder("Unknown transfer encoding: ");
               var9.append(var1);
               throw new UnsupportedEncodingException(var9.toString());
            }

            var7 = false;
         }

         StringBuffer var10 = new StringBuffer();
         var4 = var6.length();
         StringBuilder var8 = new StringBuilder("=?");
         var8.append(var6);
         var8.append("?");
         var8.append(var1);
         var8.append("?");
         doEncode(var0, var7, var5, 68 - var4, var8.toString(), true, var3, var10);
         return var10.toString();
      }
   }

   public static String fold(int var0, String var1) {
      if (!foldText) {
         return var1;
      } else {
         int var2;
         for(var2 = var1.length() - 1; var2 >= 0; --var2) {
            char var3 = var1.charAt(var2);
            if (var3 != ' ' && var3 != '\t' && var3 != '\r' && var3 != '\n') {
               break;
            }
         }

         String var4 = var1;
         if (var2 != var1.length() - 1) {
            var4 = var1.substring(0, var2 + 1);
         }

         if (var4.length() + var0 <= 76) {
            return var4;
         } else {
            StringBuffer var5 = new StringBuffer(var4.length() + 4);
            var1 = var4;
            char var6 = 0;

            char var7;
            for(var2 = var0; var1.length() + var2 > 76; var6 = var7) {
               var0 = 0;

               int var9;
               int var10;
               for(var10 = -1; var0 < var1.length() && (var10 == -1 || var2 + var0 <= 76); var10 = var9) {
                  char var8;
                  label57: {
                     var8 = var1.charAt(var0);
                     if (var8 != ' ') {
                        var9 = var10;
                        if (var8 != '\t') {
                           break label57;
                        }
                     }

                     var9 = var10;
                     if (var6 != ' ') {
                        var9 = var10;
                        if (var6 != '\t') {
                           var9 = var0;
                        }
                     }
                  }

                  ++var0;
                  var6 = var8;
               }

               if (var10 == -1) {
                  var5.append(var1);
                  var1 = "";
                  break;
               }

               var5.append(var1.substring(0, var10));
               var5.append("\r\n");
               var7 = var1.charAt(var10);
               var5.append(var7);
               var1 = var1.substring(var10 + 1);
               var2 = 1;
            }

            var5.append(var1);
            return var5.toString();
         }
      }
   }

   public static String getDefaultJavaCharset() {
      if (defaultJavaCharset == null) {
         String var0 = null;

         label29: {
            String var1;
            try {
               var1 = System.getProperty("mail.mime.charset");
            } catch (SecurityException var3) {
               break label29;
            }

            var0 = var1;
         }

         if (var0 != null && var0.length() > 0) {
            var0 = javaCharset(var0);
            defaultJavaCharset = var0;
            return var0;
         }

         try {
            defaultJavaCharset = System.getProperty("file.encoding", "8859_1");
         } catch (SecurityException var2) {
            var0 = (new InputStreamReader(new MimeUtility$1NullInputStream())).getEncoding();
            defaultJavaCharset = var0;
            if (var0 == null) {
               defaultJavaCharset = "8859_1";
            }
         }
      }

      return defaultJavaCharset;
   }

   static String getDefaultMIMECharset() {
      if (defaultMIMECharset == null) {
         try {
            defaultMIMECharset = System.getProperty("mail.mime.charset");
         } catch (SecurityException var1) {
         }
      }

      if (defaultMIMECharset == null) {
         defaultMIMECharset = mimeCharset(getDefaultJavaCharset());
      }

      return defaultMIMECharset;
   }

   public static String getEncoding(DataHandler var0) {
      String var1 = "base64";
      if (var0.getName() != null) {
         return getEncoding(var0.getDataSource());
      } else {
         ContentType var2;
         String var7;
         try {
            var2 = new ContentType(var0.getContentType());
         } catch (Exception var6) {
            var7 = var1;
            return var7;
         }

         AsciiOutputStream var8;
         if (var2.match("text/*")) {
            var8 = new AsciiOutputStream(false, false);

            try {
               var0.writeTo(var8);
            } catch (IOException var5) {
            }

            int var3 = var8.getAscii();
            if (var3 != 1) {
               if (var3 != 2) {
                  var7 = var1;
               } else {
                  var7 = "quoted-printable";
               }

               return var7;
            }
         } else {
            var8 = new AsciiOutputStream(true, encodeEolStrict);

            try {
               var0.writeTo(var8);
            } catch (IOException var4) {
            }

            var7 = var1;
            if (var8.getAscii() != 1) {
               return var7;
            }
         }

         var7 = "7bit";
         return var7;
      }
   }

   public static String getEncoding(DataSource var0) {
      String var1 = "base64";
      String var2 = var1;

      String var10;
      label50: {
         boolean var10001;
         ContentType var3;
         try {
            var3 = new ContentType;
         } catch (Exception var9) {
            var10001 = false;
            break label50;
         }

         var2 = var1;

         try {
            var3.<init>(var0.getContentType());
         } catch (Exception var8) {
            var10001 = false;
            break label50;
         }

         var2 = var1;

         InputStream var4;
         try {
            var4 = var0.getInputStream();
         } catch (Exception var7) {
            var10001 = false;
            break label50;
         }

         int var5 = checkAscii(var4, -1, var3.match("text/*") ^ true);
         if (var5 != 1) {
            if (var5 != 2) {
               var10 = var1;
            } else {
               var10 = "quoted-printable";
            }
         } else {
            var10 = "7bit";
         }

         var2 = var10;

         try {
            var4.close();
            return var10;
         } catch (IOException var6) {
            var10001 = false;
         }
      }

      var10 = var2;
      return var10;
   }

   private static int indexOfAny(String var0, String var1) {
      return indexOfAny(var0, var1, 0);
   }

   private static int indexOfAny(String param0, String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public static String javaCharset(String var0) {
      Hashtable var1 = mime2java;
      String var2 = var0;
      if (var1 != null) {
         if (var0 == null) {
            var2 = var0;
         } else {
            var2 = (String)var1.get(var0.toLowerCase(Locale.ENGLISH));
            if (var2 == null) {
               var2 = var0;
            }
         }
      }

      return var2;
   }

   private static void loadMappings(LineInputStream var0, Hashtable var1) {
      while(true) {
         String var2;
         try {
            var2 = var0.readLine();
         } catch (IOException var5) {
            return;
         }

         if (var2 == null || var2.startsWith("--") && var2.endsWith("--")) {
            return;
         }

         if (var2.trim().length() != 0 && !var2.startsWith("#")) {
            StringTokenizer var3 = new StringTokenizer(var2, " \t");

            try {
               var2 = var3.nextToken();
               String var6 = var3.nextToken();
               var1.put(var2.toLowerCase(Locale.ENGLISH), var6);
            } catch (NoSuchElementException var4) {
            }
         }
      }
   }

   public static String mimeCharset(String var0) {
      Hashtable var1 = java2mime;
      String var2 = var0;
      if (var1 != null) {
         if (var0 == null) {
            var2 = var0;
         } else {
            var2 = (String)var1.get(var0.toLowerCase(Locale.ENGLISH));
            if (var2 == null) {
               var2 = var0;
            }
         }
      }

      return var2;
   }

   static final boolean nonascii(int var0) {
      return var0 >= 127 || var0 < 32 && var0 != 13 && var0 != 10 && var0 != 9;
   }

   public static String quote(String var0, String var1) {
      int var2 = var0.length();
      byte var3 = 0;
      int var4 = 0;

      boolean var5;
      StringBuffer var8;
      for(var5 = false; var4 < var2; ++var4) {
         char var6 = var0.charAt(var4);
         if (var6 == '"' || var6 == '\\' || var6 == '\r' || var6 == '\n') {
            var8 = new StringBuffer(var2 + 3);
            var8.append('"');
            var8.append(var0.substring(0, var4));
            int var10 = var4;

            char var7;
            for(char var9 = (char)var3; var10 < var2; var9 = var7) {
               var7 = var0.charAt(var10);
               if ((var7 == '"' || var7 == '\\' || var7 == '\r' || var7 == '\n') && (var7 != '\n' || var9 != '\r')) {
                  var8.append('\\');
               }

               var8.append(var7);
               ++var10;
            }

            var8.append('"');
            return var8.toString();
         }

         if (var6 < ' ' || var6 >= 127 || var1.indexOf(var6) >= 0) {
            var5 = true;
         }
      }

      var1 = var0;
      if (var5) {
         var8 = new StringBuffer(var2 + 2);
         var8.append('"');
         var8.append(var0);
         var8.append('"');
         var1 = var8.toString();
      }

      return var1;
   }

   public static String unfold(String var0) {
      if (!foldText) {
         return var0;
      } else {
         StringBuffer var1 = null;

         while(true) {
            while(true) {
               int var2 = indexOfAny(var0, "\r\n");
               if (var2 < 0) {
                  String var8 = var0;
                  if (var1 != null) {
                     var1.append(var0);
                     var8 = var1.toString();
                  }

                  return var8;
               }

               int var4 = var0.length();
               int var5 = var2 + 1;
               int var6 = var5;
               if (var5 < var4) {
                  var6 = var5;
                  if (var0.charAt(var5 - 1) == '\r') {
                     var6 = var5;
                     if (var0.charAt(var5) == '\n') {
                        var6 = var5 + 1;
                     }
                  }
               }

               StringBuffer var3;
               if (var2 != 0) {
                  var5 = var2 - 1;
                  if (var0.charAt(var5) == '\\') {
                     var3 = var1;
                     if (var1 == null) {
                        var3 = new StringBuffer(var0.length());
                     }

                     var3.append(var0.substring(0, var5));
                     var3.append(var0.substring(var2, var6));
                     var0 = var0.substring(var6);
                     var1 = var3;
                     continue;
                  }
               }

               char var7;
               label81: {
                  if (var6 < var4) {
                     var7 = var0.charAt(var6);
                     var5 = var6;
                     if (var7 == ' ') {
                        break label81;
                     }

                     if (var7 == '\t') {
                        var5 = var6;
                        break label81;
                     }
                  }

                  var3 = var1;
                  if (var1 == null) {
                     var3 = new StringBuffer(var0.length());
                  }

                  var3.append(var0.substring(0, var6));
                  var0 = var0.substring(var6);
                  var1 = var3;
                  continue;
               }

               while(true) {
                  var6 = var5 + 1;
                  if (var6 >= var4) {
                     break;
                  }

                  var7 = var0.charAt(var6);
                  var5 = var6;
                  if (var7 != ' ') {
                     var5 = var6;
                     if (var7 != '\t') {
                        break;
                     }
                  }
               }

               var3 = var1;
               if (var1 == null) {
                  var3 = new StringBuffer(var0.length());
               }

               if (var2 != 0) {
                  var3.append(var0.substring(0, var2));
                  var3.append(' ');
               }

               var0 = var0.substring(var6);
               var1 = var3;
            }
         }
      }
   }
}
