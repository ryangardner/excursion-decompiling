package com.sun.mail.pop3;

import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.SocketFetcher;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

class Protocol {
   private static final String CRLF = "\r\n";
   private static final int POP3_PORT = 110;
   private static char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private String apopChallenge;
   private boolean debug;
   private DataInputStream input;
   private PrintStream out;
   private PrintWriter output;
   private Socket socket;

   Protocol(String var1, int var2, boolean var3, PrintStream var4, Properties var5, String var6, boolean var7) throws IOException {
      boolean var8 = false;
      this.debug = false;
      this.apopChallenge = null;
      this.debug = var3;
      this.out = var4;
      StringBuilder var9 = new StringBuilder(String.valueOf(var6));
      var9.append(".apop.enable");
      String var32 = var5.getProperty(var9.toString());
      boolean var10 = var8;
      if (var32 != null) {
         var10 = var8;
         if (var32.equalsIgnoreCase("true")) {
            var10 = true;
         }
      }

      int var31 = var2;
      if (var2 == -1) {
         var31 = 110;
      }

      Response var26;
      label182: {
         IOException var10000;
         label175: {
            boolean var10001;
            if (var3) {
               try {
                  var9 = new StringBuilder("DEBUG POP3: connecting to host \"");
                  var9.append(var1);
                  var9.append("\", port ");
                  var9.append(var31);
                  var9.append(", isSSL ");
                  var9.append(var7);
                  var4.println(var9.toString());
               } catch (IOException var22) {
                  var10000 = var22;
                  var10001 = false;
                  break label175;
               }
            }

            try {
               this.socket = SocketFetcher.getSocket(var1, var31, var5, var6, var7);
               BufferedInputStream var24 = new BufferedInputStream(this.socket.getInputStream());
               DataInputStream var28 = new DataInputStream(var24);
               this.input = var28;
               OutputStreamWriter var25 = new OutputStreamWriter(this.socket.getOutputStream(), "iso-8859-1");
               BufferedWriter var30 = new BufferedWriter(var25);
               PrintWriter var29 = new PrintWriter(var30);
               this.output = var29;
               var26 = this.simpleCommand((String)null);
               break label182;
            } catch (IOException var21) {
               var10000 = var21;
               var10001 = false;
            }
         }

         IOException var23 = var10000;

         try {
            this.socket.close();
         } finally {
            throw var23;
         }

         throw var23;
      }

      if (var26.ok) {
         if (var10) {
            int var33 = var26.data.indexOf(60);
            var2 = var26.data.indexOf(62, var33);
            if (var33 != -1 && var2 != -1) {
               this.apopChallenge = var26.data.substring(var33, var2 + 1);
            }

            if (var3) {
               StringBuilder var27 = new StringBuilder("DEBUG POP3: APOP challenge: ");
               var27.append(this.apopChallenge);
               var4.println(var27.toString());
            }
         }

      } else {
         try {
            this.socket.close();
         } finally {
            throw new IOException("Connect failed");
         }

         throw new IOException("Connect failed");
      }
   }

   private String getDigest(String var1) {
      StringBuilder var2 = new StringBuilder(String.valueOf(this.apopChallenge));
      var2.append(var1);
      var1 = var2.toString();

      byte[] var4;
      try {
         var4 = MessageDigest.getInstance("MD5").digest(var1.getBytes("iso-8859-1"));
      } catch (UnsupportedEncodingException | NoSuchAlgorithmException var3) {
         return null;
      }

      return toHex(var4);
   }

   private Response multilineCommand(String var1, int var2) throws IOException {
      Response var3 = this.simpleCommand(var1);
      if (!var3.ok) {
         return var3;
      } else {
         SharedByteArrayOutputStream var5 = new SharedByteArrayOutputStream(var2);
         int var4 = 10;

         while(true) {
            var2 = this.input.read();
            if (var2 < 0) {
               break;
            }

            if (var4 == 10 && var2 == 46) {
               if (this.debug) {
                  this.out.write(var2);
               }

               var4 = this.input.read();
               var2 = var4;
               if (var4 == 13) {
                  if (this.debug) {
                     this.out.write(var4);
                  }

                  var4 = this.input.read();
                  var2 = var4;
                  if (this.debug) {
                     this.out.write(var4);
                     var2 = var4;
                  }
                  break;
               }
            }

            var5.write(var2);
            var4 = var2;
            if (this.debug) {
               this.out.write(var2);
               var4 = var2;
            }
         }

         if (var2 >= 0) {
            var3.bytes = var5.toStream();
            return var3;
         } else {
            throw new EOFException("EOF on socket");
         }
      }
   }

   private Response simpleCommand(String var1) throws IOException {
      if (this.socket != null) {
         PrintStream var2;
         StringBuilder var3;
         if (var1 != null) {
            if (this.debug) {
               var2 = this.out;
               var3 = new StringBuilder("C: ");
               var3.append(var1);
               var2.println(var3.toString());
            }

            StringBuilder var5 = new StringBuilder(String.valueOf(var1));
            var5.append("\r\n");
            var1 = var5.toString();
            this.output.print(var1);
            this.output.flush();
         }

         var1 = this.input.readLine();
         if (var1 == null) {
            if (this.debug) {
               this.out.println("S: EOF");
            }

            throw new EOFException("EOF on socket");
         } else {
            if (this.debug) {
               var2 = this.out;
               var3 = new StringBuilder("S: ");
               var3.append(var1);
               var2.println(var3.toString());
            }

            Response var6 = new Response();
            if (var1.startsWith("+OK")) {
               var6.ok = true;
            } else {
               if (!var1.startsWith("-ERR")) {
                  var3 = new StringBuilder("Unexpected response: ");
                  var3.append(var1);
                  throw new IOException(var3.toString());
               }

               var6.ok = false;
            }

            int var4 = var1.indexOf(32);
            if (var4 >= 0) {
               var6.data = var1.substring(var4 + 1);
            }

            return var6;
         }
      } else {
         throw new IOException("Folder is closed");
      }
   }

   private static String toHex(byte[] var0) {
      char[] var1 = new char[var0.length * 2];
      int var2 = 0;

      for(int var3 = 0; var2 < var0.length; ++var2) {
         int var4 = var0[var2] & 255;
         int var5 = var3 + 1;
         char[] var6 = digits;
         var1[var3] = (char)var6[var4 >> 4];
         var3 = var5 + 1;
         var1[var5] = (char)var6[var4 & 15];
      }

      return new String(var1);
   }

   boolean dele(int var1) throws IOException {
      synchronized(this){}

      boolean var3;
      try {
         StringBuilder var2 = new StringBuilder("DELE ");
         var2.append(var1);
         var3 = this.simpleCommand(var2.toString()).ok;
      } finally {
         ;
      }

      return var3;
   }

   protected void finalize() throws Throwable {
      super.finalize();
      if (this.socket != null) {
         this.quit();
      }

   }

   int list(int param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   InputStream list() throws IOException {
      synchronized(this){}

      InputStream var1;
      try {
         var1 = this.multilineCommand("LIST", 128).bytes;
      } finally {
         ;
      }

      return var1;
   }

   String login(String var1, String var2) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label622: {
         String var3;
         boolean var10001;
         label596: {
            try {
               if (this.apopChallenge != null) {
                  var3 = this.getDigest(var2);
                  break label596;
               }
            } catch (Throwable var45) {
               var10000 = var45;
               var10001 = false;
               break label622;
            }

            var3 = null;
         }

         Response var46;
         label604: {
            label587: {
               try {
                  if (this.apopChallenge == null) {
                     break label587;
                  }
               } catch (Throwable var44) {
                  var10000 = var44;
                  var10001 = false;
                  break label622;
               }

               if (var3 != null) {
                  try {
                     StringBuilder var47 = new StringBuilder("APOP ");
                     var47.append(var1);
                     var47.append(" ");
                     var47.append(var3);
                     var46 = this.simpleCommand(var47.toString());
                     break label604;
                  } catch (Throwable var42) {
                     var10000 = var42;
                     var10001 = false;
                     break label622;
                  }
               }
            }

            label623: {
               try {
                  StringBuilder var50 = new StringBuilder("USER ");
                  var50.append(var1);
                  var46 = this.simpleCommand(var50.toString());
                  if (var46.ok) {
                     break label623;
                  }

                  if (var46.data != null) {
                     var1 = var46.data;
                     return var1;
                  }
               } catch (Throwable var43) {
                  var10000 = var43;
                  var10001 = false;
                  break label622;
               }

               var1 = "USER command failed";
               return var1;
            }

            try {
               StringBuilder var48 = new StringBuilder("PASS ");
               var48.append(var2);
               var46 = this.simpleCommand(var48.toString());
            } catch (Throwable var41) {
               var10000 = var41;
               var10001 = false;
               break label622;
            }
         }

         try {
            if (var46.ok) {
               return null;
            }

            if (var46.data != null) {
               var1 = var46.data;
               return var1;
            }
         } catch (Throwable var40) {
            var10000 = var40;
            var10001 = false;
            break label622;
         }

         var1 = "login failed";
         return var1;
      }

      Throwable var49 = var10000;
      throw var49;
   }

   boolean noop() throws IOException {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.simpleCommand("NOOP").ok;
      } finally {
         ;
      }

      return var1;
   }

   boolean quit() throws IOException {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.simpleCommand("QUIT").ok;
      } finally {
         try {
            this.socket.close();
         } finally {
            try {
               this.socket = null;
               this.input = null;
               this.output = null;
            } catch (Throwable var47) {
               boolean var10001 = false;
               throw var47;
            }
         }
      }

      return var1;
   }

   InputStream retr(int var1, int var2) throws IOException {
      synchronized(this){}

      InputStream var6;
      try {
         StringBuilder var3 = new StringBuilder("RETR ");
         var3.append(var1);
         var6 = this.multilineCommand(var3.toString(), var2).bytes;
      } finally {
         ;
      }

      return var6;
   }

   boolean rset() throws IOException {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.simpleCommand("RSET").ok;
      } finally {
         ;
      }

      return var1;
   }

   Status stat() throws IOException {
      // $FF: Couldn't be decompiled
   }

   InputStream top(int var1, int var2) throws IOException {
      synchronized(this){}

      InputStream var6;
      try {
         StringBuilder var3 = new StringBuilder("TOP ");
         var3.append(var1);
         var3.append(" ");
         var3.append(var2);
         var6 = this.multilineCommand(var3.toString(), 0).bytes;
      } finally {
         ;
      }

      return var6;
   }

   String uidl(int var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label140: {
         Response var16;
         boolean var10001;
         boolean var3;
         try {
            StringBuilder var2 = new StringBuilder("UIDL ");
            var2.append(var1);
            var16 = this.simpleCommand(var2.toString());
            var3 = var16.ok;
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label140;
         }

         if (!var3) {
            return null;
         }

         try {
            var1 = var16.data.indexOf(32);
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label140;
         }

         if (var1 <= 0) {
            return null;
         }

         String var18;
         try {
            var18 = var16.data.substring(var1 + 1);
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label140;
         }

         return var18;
      }

      Throwable var17 = var10000;
      throw var17;
   }

   boolean uidl(String[] var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label536: {
         Response var2;
         boolean var3;
         boolean var10001;
         try {
            var2 = this.multilineCommand("UIDL", var1.length * 15);
            var3 = var2.ok;
         } catch (Throwable var62) {
            var10000 = var62;
            var10001 = false;
            break label536;
         }

         if (!var3) {
            return false;
         }

         LineInputStream var4;
         try {
            var4 = new LineInputStream(var2.bytes);
         } catch (Throwable var61) {
            var10000 = var61;
            var10001 = false;
            break label536;
         }

         while(true) {
            String var64;
            try {
               var64 = var4.readLine();
            } catch (Throwable var59) {
               var10000 = var59;
               var10001 = false;
               break;
            }

            if (var64 == null) {
               return true;
            }

            int var5;
            try {
               var5 = var64.indexOf(32);
            } catch (Throwable var58) {
               var10000 = var58;
               var10001 = false;
               break;
            }

            if (var5 >= 1) {
               try {
                  if (var5 >= var64.length()) {
                     continue;
                  }
               } catch (Throwable var60) {
                  var10000 = var60;
                  var10001 = false;
                  break;
               }

               int var6;
               try {
                  var6 = Integer.parseInt(var64.substring(0, var5));
               } catch (Throwable var57) {
                  var10000 = var57;
                  var10001 = false;
                  break;
               }

               if (var6 > 0) {
                  try {
                     if (var6 <= var1.length) {
                        var1[var6 - 1] = var64.substring(var5 + 1);
                     }
                  } catch (Throwable var56) {
                     var10000 = var56;
                     var10001 = false;
                     break;
                  }
               }
            }
         }
      }

      Throwable var63 = var10000;
      throw var63;
   }
}
