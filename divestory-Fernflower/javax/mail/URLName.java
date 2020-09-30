package javax.mail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;
import java.util.Locale;

public class URLName {
   static final int caseDiff = 32;
   private static boolean doEncode;
   static BitSet dontNeedEncoding;
   private String file;
   protected String fullURL;
   private int hashCode;
   private String host;
   private InetAddress hostAddress;
   private boolean hostAddressKnown;
   private String password;
   private int port;
   private String protocol;
   private String ref;
   private String username;

   static {
      // $FF: Couldn't be decompiled
   }

   public URLName(String var1) {
      this.hostAddressKnown = false;
      this.port = -1;
      this.hashCode = 0;
      this.parseString(var1);
   }

   public URLName(String var1, String var2, int var3, String var4, String var5, String var6) {
      label20: {
         super();
         this.hostAddressKnown = false;
         this.port = -1;
         this.hashCode = 0;
         this.protocol = var1;
         this.host = var2;
         this.port = var3;
         if (var4 != null) {
            var3 = var4.indexOf(35);
            if (var3 != -1) {
               this.file = var4.substring(0, var3);
               this.ref = var4.substring(var3 + 1);
               break label20;
            }
         }

         this.file = var4;
         this.ref = null;
      }

      var1 = var5;
      if (doEncode) {
         var1 = encode(var5);
      }

      this.username = var1;
      var1 = var6;
      if (doEncode) {
         var1 = encode(var6);
      }

      this.password = var1;
   }

   public URLName(URL var1) {
      this(var1.toString());
   }

   private static String _encode(String var0) {
      StringBuffer var1 = new StringBuffer(var0.length());
      ByteArrayOutputStream var2 = new ByteArrayOutputStream(10);
      OutputStreamWriter var3 = new OutputStreamWriter(var2);

      for(int var4 = 0; var4 < var0.length(); ++var4) {
         char var5 = var0.charAt(var4);
         if (dontNeedEncoding.get(var5)) {
            char var11 = var5;
            if (var5 == ' ') {
               var11 = '+';
            }

            var1.append((char)var11);
         } else {
            try {
               var3.write(var5);
               var3.flush();
            } catch (IOException var10) {
               var2.reset();
               continue;
            }

            byte[] var7 = var2.toByteArray();

            for(int var6 = 0; var6 < var7.length; ++var6) {
               var1.append('%');
               char var8 = Character.forDigit(var7[var6] >> 4 & 15, 16);
               char var9 = var8;
               if (Character.isLetter(var8)) {
                  var5 = (char)(var8 - 32);
                  var9 = var5;
               }

               var1.append(var9);
               var8 = Character.forDigit(var7[var6] & 15, 16);
               var9 = var8;
               if (Character.isLetter(var8)) {
                  var5 = (char)(var8 - 32);
                  var9 = var5;
               }

               var1.append(var9);
            }

            var2.reset();
         }
      }

      return var1.toString();
   }

   static String decode(String var0) {
      if (var0 == null) {
         return null;
      } else if (indexOfAny(var0, "+%") == -1) {
         return var0;
      } else {
         StringBuffer var1 = new StringBuffer();

         for(int var2 = 0; var2 < var0.length(); ++var2) {
            char var4 = var0.charAt(var2);
            if (var4 != '%') {
               if (var4 != '+') {
                  var1.append(var4);
               } else {
                  var1.append(' ');
               }
            } else {
               try {
                  var1.append((char)Integer.parseInt(var0.substring(var2 + 1, var2 + 3), 16));
               } catch (NumberFormatException var5) {
                  throw new IllegalArgumentException();
               }

               var2 += 2;
            }
         }

         var0 = var1.toString();

         String var7;
         try {
            byte[] var3 = var0.getBytes("8859_1");
            var7 = new String(var3);
         } catch (UnsupportedEncodingException var6) {
            return var0;
         }

         var0 = var7;
         return var0;
      }
   }

   static String encode(String var0) {
      if (var0 == null) {
         return null;
      } else {
         for(int var1 = 0; var1 < var0.length(); ++var1) {
            char var2 = var0.charAt(var1);
            if (var2 == ' ' || !dontNeedEncoding.get(var2)) {
               return _encode(var0);
            }
         }

         return var0;
      }
   }

   private InetAddress getHostAddress() {
      // $FF: Couldn't be decompiled
   }

   private static int indexOfAny(String var0, String var1) {
      return indexOfAny(var0, var1, 0);
   }

   private static int indexOfAny(String param0, String param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof URLName)) {
         return false;
      } else {
         URLName var2 = (URLName)var1;
         String var5 = var2.protocol;
         if (var5 != null && var5.equals(this.protocol)) {
            InetAddress var6 = this.getHostAddress();
            InetAddress var3 = var2.getHostAddress();
            String var7;
            if (var6 != null && var3 != null) {
               if (!var6.equals(var3)) {
                  return false;
               }
            } else {
               label72: {
                  var5 = this.host;
                  if (var5 != null) {
                     var7 = var2.host;
                     if (var7 != null) {
                        if (!var5.equalsIgnoreCase(var7)) {
                           return false;
                        }
                        break label72;
                     }
                  }

                  if (this.host != var2.host) {
                     return false;
                  }
               }
            }

            var7 = this.username;
            var5 = var2.username;
            if (var7 == var5 || var7 != null && var7.equals(var5)) {
               String var4 = this.file;
               var7 = "";
               var5 = var4;
               if (var4 == null) {
                  var5 = "";
               }

               var4 = var2.file;
               if (var4 != null) {
                  var7 = var4;
               }

               if (!var5.equals(var7)) {
                  return false;
               } else {
                  return this.port == var2.port;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public String getFile() {
      return this.file;
   }

   public String getHost() {
      return this.host;
   }

   public String getPassword() {
      String var1;
      if (doEncode) {
         var1 = decode(this.password);
      } else {
         var1 = this.password;
      }

      return var1;
   }

   public int getPort() {
      return this.port;
   }

   public String getProtocol() {
      return this.protocol;
   }

   public String getRef() {
      return this.ref;
   }

   public URL getURL() throws MalformedURLException {
      return new URL(this.getProtocol(), this.getHost(), this.getPort(), this.getFile());
   }

   public String getUsername() {
      String var1;
      if (doEncode) {
         var1 = decode(this.username);
      } else {
         var1 = this.username;
      }

      return var1;
   }

   public int hashCode() {
      int var1 = this.hashCode;
      if (var1 != 0) {
         return var1;
      } else {
         String var2 = this.protocol;
         if (var2 != null) {
            this.hashCode = var1 + var2.hashCode();
         }

         InetAddress var3 = this.getHostAddress();
         if (var3 != null) {
            this.hashCode += var3.hashCode();
         } else {
            var2 = this.host;
            if (var2 != null) {
               this.hashCode += var2.toLowerCase(Locale.ENGLISH).hashCode();
            }
         }

         var2 = this.username;
         if (var2 != null) {
            this.hashCode += var2.hashCode();
         }

         var2 = this.file;
         if (var2 != null) {
            this.hashCode += var2.hashCode();
         }

         var1 = this.hashCode + this.port;
         this.hashCode = var1;
         return var1;
      }
   }

   protected void parseString(String var1) {
      this.password = null;
      this.username = null;
      this.host = null;
      this.ref = null;
      this.file = null;
      this.protocol = null;
      this.port = -1;
      int var2 = var1.length();
      int var3 = var1.indexOf(58);
      if (var3 != -1) {
         this.protocol = var1.substring(0, var3);
      }

      int var4 = var3 + 1;
      if (var1.regionMatches(var4, "//", 0, 2)) {
         var3 += 3;
         var4 = var1.indexOf(47, var3);
         String var5;
         if (var4 != -1) {
            var5 = var1.substring(var3, var4);
            var3 = var4 + 1;
            if (var3 < var2) {
               this.file = var1.substring(var3);
               var1 = var5;
            } else {
               this.file = "";
               var1 = var5;
            }
         } else {
            var1 = var1.substring(var3);
         }

         var2 = var1.indexOf(64);
         var5 = var1;
         if (var2 != -1) {
            String var6 = var1.substring(0, var2);
            var5 = var1.substring(var2 + 1);
            var2 = var6.indexOf(58);
            if (var2 != -1) {
               this.username = var6.substring(0, var2);
               this.password = var6.substring(var2 + 1);
            } else {
               this.username = var6;
            }
         }

         if (var5.length() > 0 && var5.charAt(0) == '[') {
            var2 = var5.indexOf(58, var5.indexOf(93));
         } else {
            var2 = var5.indexOf(58);
         }

         if (var2 != -1) {
            var1 = var5.substring(var2 + 1);
            if (var1.length() > 0) {
               try {
                  this.port = Integer.parseInt(var1);
               } catch (NumberFormatException var7) {
                  this.port = -1;
               }
            }

            this.host = var5.substring(0, var2);
         } else {
            this.host = var5;
         }
      } else if (var4 < var2) {
         this.file = var1.substring(var4);
      }

      var1 = this.file;
      if (var1 != null) {
         var2 = var1.indexOf(35);
         if (var2 != -1) {
            this.ref = this.file.substring(var2 + 1);
            this.file = this.file.substring(0, var2);
         }
      }

   }

   public String toString() {
      if (this.fullURL == null) {
         StringBuffer var1 = new StringBuffer();
         String var2 = this.protocol;
         if (var2 != null) {
            var1.append(var2);
            var1.append(":");
         }

         if (this.username != null || this.host != null) {
            var1.append("//");
            var2 = this.username;
            if (var2 != null) {
               var1.append(var2);
               if (this.password != null) {
                  var1.append(":");
                  var1.append(this.password);
               }

               var1.append("@");
            }

            var2 = this.host;
            if (var2 != null) {
               var1.append(var2);
            }

            if (this.port != -1) {
               var1.append(":");
               var1.append(Integer.toString(this.port));
            }

            if (this.file != null) {
               var1.append("/");
            }
         }

         var2 = this.file;
         if (var2 != null) {
            var1.append(var2);
         }

         if (this.ref != null) {
            var1.append("#");
            var1.append(this.ref);
         }

         this.fullURL = var1.toString();
      }

      return this.fullURL;
   }
}
