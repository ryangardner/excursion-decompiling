package org.apache.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;

public final class EntityUtils {
   private EntityUtils() {
   }

   public static void consume(HttpEntity var0) throws IOException {
      if (var0 != null) {
         if (var0.isStreaming()) {
            InputStream var1 = var0.getContent();
            if (var1 != null) {
               var1.close();
            }
         }

      }
   }

   public static String getContentCharSet(HttpEntity var0) throws ParseException {
      if (var0 != null) {
         Object var1 = null;
         String var2 = (String)var1;
         if (var0.getContentType() != null) {
            HeaderElement[] var3 = var0.getContentType().getElements();
            var2 = (String)var1;
            if (var3.length > 0) {
               NameValuePair var4 = var3[0].getParameterByName("charset");
               var2 = (String)var1;
               if (var4 != null) {
                  var2 = var4.getValue();
               }
            }
         }

         return var2;
      } else {
         throw new IllegalArgumentException("HTTP entity may not be null");
      }
   }

   public static String getContentMimeType(HttpEntity var0) throws ParseException {
      if (var0 != null) {
         Object var1 = null;
         String var2 = (String)var1;
         if (var0.getContentType() != null) {
            HeaderElement[] var3 = var0.getContentType().getElements();
            var2 = (String)var1;
            if (var3.length > 0) {
               var2 = var3[0].getName();
            }
         }

         return var2;
      } else {
         throw new IllegalArgumentException("HTTP entity may not be null");
      }
   }

   public static byte[] toByteArray(HttpEntity var0) throws IOException {
      if (var0 != null) {
         InputStream var1 = var0.getContent();
         if (var1 == null) {
            return null;
         } else {
            Throwable var10000;
            label442: {
               int var2;
               boolean var10001;
               label432: {
                  try {
                     if (var0.getContentLength() <= 2147483647L) {
                        var2 = (int)var0.getContentLength();
                        break label432;
                     }
                  } catch (Throwable var46) {
                     var10000 = var46;
                     var10001 = false;
                     break label442;
                  }

                  try {
                     IllegalArgumentException var47 = new IllegalArgumentException("HTTP entity too large to be buffered in memory");
                     throw var47;
                  } catch (Throwable var45) {
                     var10000 = var45;
                     var10001 = false;
                     break label442;
                  }
               }

               int var3 = var2;
               if (var2 < 0) {
                  var3 = 4096;
               }

               byte[] var4;
               ByteArrayBuffer var48;
               try {
                  var48 = new ByteArrayBuffer(var3);
                  var4 = new byte[4096];
               } catch (Throwable var44) {
                  var10000 = var44;
                  var10001 = false;
                  break label442;
               }

               while(true) {
                  try {
                     var3 = var1.read(var4);
                  } catch (Throwable var42) {
                     var10000 = var42;
                     var10001 = false;
                     break;
                  }

                  if (var3 == -1) {
                     byte[] var50;
                     try {
                        var50 = var48.toByteArray();
                     } catch (Throwable var41) {
                        var10000 = var41;
                        var10001 = false;
                        break;
                     }

                     var1.close();
                     return var50;
                  }

                  try {
                     var48.append((byte[])var4, 0, var3);
                  } catch (Throwable var43) {
                     var10000 = var43;
                     var10001 = false;
                     break;
                  }
               }
            }

            Throwable var49 = var10000;
            var1.close();
            throw var49;
         }
      } else {
         throw new IllegalArgumentException("HTTP entity may not be null");
      }
   }

   public static String toString(HttpEntity var0) throws IOException, ParseException {
      return toString(var0, (String)null);
   }

   public static String toString(HttpEntity var0, String var1) throws IOException, ParseException {
      if (var0 != null) {
         InputStream var2 = var0.getContent();
         if (var2 == null) {
            return null;
         } else {
            Throwable var10000;
            label616: {
               boolean var10001;
               label625: {
                  int var3;
                  try {
                     if (var0.getContentLength() > 2147483647L) {
                        break label625;
                     }

                     var3 = (int)var0.getContentLength();
                  } catch (Throwable var61) {
                     var10000 = var61;
                     var10001 = false;
                     break label616;
                  }

                  int var4 = var3;
                  if (var3 < 0) {
                     var4 = 4096;
                  }

                  String var62;
                  try {
                     var62 = getContentCharSet(var0);
                  } catch (Throwable var59) {
                     var10000 = var59;
                     var10001 = false;
                     break label616;
                  }

                  if (var62 != null) {
                     var1 = var62;
                  }

                  var62 = var1;
                  if (var1 == null) {
                     var62 = "ISO-8859-1";
                  }

                  InputStreamReader var64;
                  CharArrayBuffer var5;
                  char[] var63;
                  try {
                     var64 = new InputStreamReader(var2, var62);
                     var5 = new CharArrayBuffer(var4);
                     var63 = new char[1024];
                  } catch (Throwable var58) {
                     var10000 = var58;
                     var10001 = false;
                     break label616;
                  }

                  while(true) {
                     try {
                        var4 = var64.read(var63);
                     } catch (Throwable var56) {
                        var10000 = var56;
                        var10001 = false;
                        break label616;
                     }

                     if (var4 == -1) {
                        try {
                           var62 = var5.toString();
                        } catch (Throwable var55) {
                           var10000 = var55;
                           var10001 = false;
                           break label616;
                        }

                        var2.close();
                        return var62;
                     }

                     try {
                        var5.append((char[])var63, 0, var4);
                     } catch (Throwable var57) {
                        var10000 = var57;
                        var10001 = false;
                        break label616;
                     }
                  }
               }

               label609:
               try {
                  IllegalArgumentException var66 = new IllegalArgumentException("HTTP entity too large to be buffered in memory");
                  throw var66;
               } catch (Throwable var60) {
                  var10000 = var60;
                  var10001 = false;
                  break label609;
               }
            }

            Throwable var65 = var10000;
            var2.close();
            throw var65;
         }
      } else {
         throw new IllegalArgumentException("HTTP entity may not be null");
      }
   }
}
