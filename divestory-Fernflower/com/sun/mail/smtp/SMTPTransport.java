package com.sun.mail.smtp;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.SocketFetcher;
import com.sun.mail.util.TraceInputStream;
import com.sun.mail.util.TraceOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.internet.ParseException;

public class SMTPTransport extends Transport {
   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final byte[] CRLF = new byte[]{13, 10};
   private static final String UNKNOWN = "UNKNOWN";
   private static char[] hexchar = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private static final String[] ignoreList = new String[]{"Bcc", "Content-Length"};
   private Address[] addresses;
   private SMTPOutputStream dataStream;
   private int defaultPort;
   private MessagingException exception;
   private Hashtable extMap;
   private Address[] invalidAddr;
   private boolean isSSL;
   private int lastReturnCode;
   private String lastServerResponse;
   private LineInputStream lineInputStream;
   private String localHostName;
   private DigestMD5 md5support;
   private MimeMessage message;
   private String name;
   private PrintStream out;
   private boolean quitWait;
   private boolean reportSuccess;
   private String saslRealm;
   private boolean sendPartiallyFailed;
   private BufferedInputStream serverInput;
   private OutputStream serverOutput;
   private Socket serverSocket;
   private boolean useRset;
   private boolean useStartTLS;
   private Address[] validSentAddr;
   private Address[] validUnsentAddr;

   public SMTPTransport(Session var1, URLName var2) {
      this(var1, var2, "smtp", 25, false);
   }

   protected SMTPTransport(Session var1, URLName var2, String var3, int var4, boolean var5) {
      super(var1, var2);
      this.name = "smtp";
      this.defaultPort = 25;
      boolean var6 = false;
      this.isSSL = false;
      this.sendPartiallyFailed = false;
      this.quitWait = false;
      this.saslRealm = "UNKNOWN";
      if (var2 != null) {
         var3 = var2.getProtocol();
      }

      this.name = var3;
      this.defaultPort = var4;
      this.isSSL = var5;
      this.out = var1.getDebugOut();
      StringBuilder var8 = new StringBuilder("mail.");
      var8.append(var3);
      var8.append(".quitwait");
      String var9 = var1.getProperty(var8.toString());
      if (var9 != null && !var9.equalsIgnoreCase("true")) {
         var5 = false;
      } else {
         var5 = true;
      }

      this.quitWait = var5;
      var8 = new StringBuilder("mail.");
      var8.append(var3);
      var8.append(".reportsuccess");
      var9 = var1.getProperty(var8.toString());
      if (var9 != null && var9.equalsIgnoreCase("true")) {
         var5 = true;
      } else {
         var5 = false;
      }

      this.reportSuccess = var5;
      var8 = new StringBuilder("mail.");
      var8.append(var3);
      var8.append(".starttls.enable");
      var9 = var1.getProperty(var8.toString());
      if (var9 != null && var9.equalsIgnoreCase("true")) {
         var5 = true;
      } else {
         var5 = false;
      }

      this.useStartTLS = var5;
      var8 = new StringBuilder("mail.");
      var8.append(var3);
      var8.append(".userset");
      String var7 = var1.getProperty(var8.toString());
      var5 = var6;
      if (var7 != null) {
         var5 = var6;
         if (var7.equalsIgnoreCase("true")) {
            var5 = true;
         }
      }

      this.useRset = var5;
   }

   private void closeConnection() throws MessagingException {
      try {
         if (this.serverSocket != null) {
            this.serverSocket.close();
         }
      } catch (IOException var5) {
         MessagingException var2 = new MessagingException("Server Close Failed", var5);
         throw var2;
      } finally {
         this.serverSocket = null;
         this.serverOutput = null;
         this.serverInput = null;
         this.lineInputStream = null;
         if (super.isConnected()) {
            super.close();
         }

      }

   }

   private boolean convertTo8Bit(MimePart var1) {
      boolean var2 = false;
      int var3 = 0;

      boolean var5;
      label106: {
         boolean var10001;
         String var4;
         label108: {
            try {
               if (var1.isMimeType("text/*")) {
                  var4 = var1.getEncoding();
                  break label108;
               }
            } catch (MessagingException | IOException var12) {
               var10001 = false;
               break label106;
            }

            var5 = var2;

            int var6;
            MimeMultipart var13;
            try {
               if (!var1.isMimeType("multipart/*")) {
                  return var5;
               }

               var13 = (MimeMultipart)var1.getContent();
               var6 = var13.getCount();
            } catch (MessagingException | IOException var11) {
               var10001 = false;
               break label106;
            }

            for(var5 = false; var3 < var6; ++var3) {
               try {
                  var2 = this.convertTo8Bit((MimePart)var13.getBodyPart(var3));
               } catch (MessagingException | IOException var7) {
                  return var5;
               }

               if (var2) {
                  var5 = true;
               }
            }

            return var5;
         }

         var5 = var2;
         if (var4 == null) {
            return var5;
         }

         label88: {
            try {
               if (var4.equalsIgnoreCase("quoted-printable")) {
                  break label88;
               }
            } catch (MessagingException | IOException var10) {
               var10001 = false;
               break label106;
            }

            var5 = var2;

            try {
               if (!var4.equalsIgnoreCase("base64")) {
                  return var5;
               }
            } catch (MessagingException | IOException var9) {
               var10001 = false;
               break label106;
            }
         }

         var5 = var2;

         try {
            if (!this.is8Bit(var1.getInputStream())) {
               return var5;
            }

            var1.setContent(var1.getContent(), var1.getContentType());
            var1.setHeader("Content-Transfer-Encoding", "8bit");
         } catch (MessagingException | IOException var8) {
            var10001 = false;
            break label106;
         }

         var5 = true;
         return var5;
      }

      var5 = var2;
      return var5;
   }

   private void expandGroups() {
      Vector var1 = null;
      int var2 = 0;

      while(true) {
         Address[] var3 = this.addresses;
         if (var2 >= var3.length) {
            if (var1 != null) {
               InternetAddress[] var12 = new InternetAddress[var1.size()];
               var1.copyInto(var12);
               this.addresses = var12;
            }

            return;
         }

         InternetAddress var4 = (InternetAddress)var3[var2];
         Vector var11;
         if (!var4.isGroup()) {
            var11 = var1;
            if (var1 != null) {
               var1.addElement(var4);
               var11 = var1;
            }
         } else {
            label73: {
               var11 = var1;
               int var5;
               if (var1 == null) {
                  var11 = new Vector();

                  for(var5 = 0; var5 < var2; ++var5) {
                     var11.addElement(this.addresses[var5]);
                  }
               }

               label58: {
                  boolean var10001;
                  InternetAddress[] var10;
                  try {
                     var10 = var4.getGroup(true);
                  } catch (ParseException var9) {
                     var10001 = false;
                     break label58;
                  }

                  if (var10 != null) {
                     var5 = 0;

                     while(true) {
                        try {
                           if (var5 >= var10.length) {
                              break label73;
                           }
                        } catch (ParseException var7) {
                           var10001 = false;
                           break;
                        }

                        try {
                           var11.addElement(var10[var5]);
                        } catch (ParseException var6) {
                           var10001 = false;
                           break;
                        }

                        ++var5;
                     }
                  } else {
                     try {
                        var11.addElement(var4);
                        break label73;
                     } catch (ParseException var8) {
                        var10001 = false;
                     }
                  }
               }

               var11.addElement(var4);
            }
         }

         ++var2;
         var1 = var11;
      }
   }

   private DigestMD5 getMD5() {
      synchronized(this){}

      Throwable var10000;
      label155: {
         boolean var10001;
         label161: {
            DigestMD5 var1;
            PrintStream var2;
            label152: {
               try {
                  if (this.md5support != null) {
                     break label161;
                  }

                  var1 = new DigestMD5;
                  if (this.debug) {
                     var2 = this.out;
                     break label152;
                  }
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label155;
               }

               var2 = null;
            }

            try {
               var1.<init>(var2);
               this.md5support = var1;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label155;
            }
         }

         label140:
         try {
            DigestMD5 var16 = this.md5support;
            return var16;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            break label140;
         }
      }

      Throwable var15 = var10000;
      throw var15;
   }

   private void initStreams() throws IOException {
      Properties var1 = this.session.getProperties();
      PrintStream var2 = this.session.getDebugOut();
      boolean var3 = this.session.getDebug();
      String var5 = var1.getProperty("mail.debug.quote");
      boolean var4;
      if (var5 != null && var5.equalsIgnoreCase("true")) {
         var4 = true;
      } else {
         var4 = false;
      }

      TraceInputStream var6 = new TraceInputStream(this.serverSocket.getInputStream(), var2);
      var6.setTrace(var3);
      var6.setQuote(var4);
      TraceOutputStream var7 = new TraceOutputStream(this.serverSocket.getOutputStream(), var2);
      var7.setTrace(var3);
      var7.setQuote(var4);
      this.serverOutput = new BufferedOutputStream(var7);
      this.serverInput = new BufferedInputStream(var6);
      this.lineInputStream = new LineInputStream(this.serverInput);
   }

   private boolean is8Bit(InputStream var1) {
      boolean var2 = false;
      int var3 = 0;

      while(true) {
         int var4;
         try {
            var4 = var1.read();
         } catch (IOException var6) {
            return false;
         }

         if (var4 < 0) {
            if (this.debug && var2) {
               this.out.println("DEBUG SMTP: found an 8bit part");
            }

            return var2;
         }

         int var5 = var4 & 255;
         if (var5 != 13 && var5 != 10) {
            if (var5 == 0) {
               return false;
            }

            ++var3;
            var4 = var3;
            if (var3 > 998) {
               return false;
            }
         } else {
            var4 = 0;
         }

         var3 = var4;
         if (var5 > 127) {
            var2 = true;
            var3 = var4;
         }
      }
   }

   private boolean isNotLastLine(String var1) {
      return var1 != null && var1.length() >= 4 && var1.charAt(3) == '-';
   }

   private void issueSendCommand(String var1, int var2) throws MessagingException {
      this.sendCommand(var1);
      int var3 = this.readServerResponse();
      if (var3 != var2) {
         Address[] var4 = this.validSentAddr;
         if (var4 == null) {
            var2 = 0;
         } else {
            var2 = var4.length;
         }

         var4 = this.validUnsentAddr;
         int var5;
         if (var4 == null) {
            var5 = 0;
         } else {
            var5 = var4.length;
         }

         var4 = new Address[var2 + var5];
         if (var2 > 0) {
            System.arraycopy(this.validSentAddr, 0, var4, 0, var2);
         }

         if (var5 > 0) {
            System.arraycopy(this.validUnsentAddr, 0, var4, var2, var5);
         }

         this.validSentAddr = null;
         this.validUnsentAddr = var4;
         if (this.debug) {
            PrintStream var7 = this.out;
            StringBuilder var6 = new StringBuilder("DEBUG SMTP: got response code ");
            var6.append(var3);
            var6.append(", with response: ");
            var6.append(this.lastServerResponse);
            var7.println(var6.toString());
         }

         String var8 = this.lastServerResponse;
         var2 = this.lastReturnCode;
         if (this.serverSocket != null) {
            this.issueCommand("RSET", 250);
         }

         this.lastServerResponse = var8;
         this.lastReturnCode = var2;
         throw new SMTPSendFailedException(var1, var3, this.lastServerResponse, this.exception, this.validSentAddr, this.validUnsentAddr, this.invalidAddr);
      }
   }

   private String normalizeAddress(String var1) {
      String var2 = var1;
      if (!var1.startsWith("<")) {
         var2 = var1;
         if (!var1.endsWith(">")) {
            StringBuilder var3 = new StringBuilder("<");
            var3.append(var1);
            var3.append(">");
            var2 = var3.toString();
         }
      }

      return var2;
   }

   private void openServer() throws MessagingException {
      String var1 = "UNKNOWN";
      int var2 = -1;
      String var3 = var1;

      StringBuilder var6;
      IOException var10000;
      label372: {
         int var4;
         boolean var10001;
         try {
            var4 = this.serverSocket.getPort();
         } catch (IOException var56) {
            var10000 = var56;
            var10001 = false;
            break label372;
         }

         var3 = var1;
         var2 = var4;

         try {
            var1 = this.serverSocket.getInetAddress().getHostName();
         } catch (IOException var55) {
            var10000 = var55;
            var10001 = false;
            break label372;
         }

         var3 = var1;
         var2 = var4;

         PrintStream var5;
         label373: {
            try {
               if (!this.debug) {
                  break label373;
               }
            } catch (IOException var54) {
               var10000 = var54;
               var10001 = false;
               break label372;
            }

            var3 = var1;
            var2 = var4;

            try {
               var5 = this.out;
            } catch (IOException var53) {
               var10000 = var53;
               var10001 = false;
               break label372;
            }

            var3 = var1;
            var2 = var4;

            try {
               var6 = new StringBuilder;
            } catch (IOException var52) {
               var10000 = var52;
               var10001 = false;
               break label372;
            }

            var3 = var1;
            var2 = var4;

            try {
               var6.<init>("DEBUG SMTP: starting protocol to host \"");
            } catch (IOException var51) {
               var10000 = var51;
               var10001 = false;
               break label372;
            }

            var3 = var1;
            var2 = var4;

            try {
               var6.append(var1);
            } catch (IOException var50) {
               var10000 = var50;
               var10001 = false;
               break label372;
            }

            var3 = var1;
            var2 = var4;

            try {
               var6.append("\", port ");
            } catch (IOException var49) {
               var10000 = var49;
               var10001 = false;
               break label372;
            }

            var3 = var1;
            var2 = var4;

            try {
               var6.append(var4);
            } catch (IOException var48) {
               var10000 = var48;
               var10001 = false;
               break label372;
            }

            var3 = var1;
            var2 = var4;

            try {
               var5.println(var6.toString());
            } catch (IOException var47) {
               var10000 = var47;
               var10001 = false;
               break label372;
            }
         }

         var3 = var1;
         var2 = var4;

         try {
            this.initStreams();
         } catch (IOException var46) {
            var10000 = var46;
            var10001 = false;
            break label372;
         }

         var3 = var1;
         var2 = var4;

         int var7;
         try {
            var7 = this.readServerResponse();
         } catch (IOException var45) {
            var10000 = var45;
            var10001 = false;
            break label372;
         }

         if (var7 != 220) {
            label374: {
               var3 = var1;
               var2 = var4;

               try {
                  this.serverSocket.close();
               } catch (IOException var34) {
                  var10000 = var34;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  this.serverSocket = null;
               } catch (IOException var33) {
                  var10000 = var33;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  this.serverOutput = null;
               } catch (IOException var32) {
                  var10000 = var32;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  this.serverInput = null;
               } catch (IOException var31) {
                  var10000 = var31;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  this.lineInputStream = null;
               } catch (IOException var30) {
                  var10000 = var30;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               boolean var8;
               try {
                  var8 = this.debug;
               } catch (IOException var29) {
                  var10000 = var29;
                  var10001 = false;
                  break label374;
               }

               if (var8) {
                  var3 = var1;
                  var2 = var4;

                  try {
                     var5 = this.out;
                  } catch (IOException var28) {
                     var10000 = var28;
                     var10001 = false;
                     break label374;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6 = new StringBuilder;
                  } catch (IOException var27) {
                     var10000 = var27;
                     var10001 = false;
                     break label374;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.<init>("DEBUG SMTP: got bad greeting from host \"");
                  } catch (IOException var26) {
                     var10000 = var26;
                     var10001 = false;
                     break label374;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.append(var1);
                  } catch (IOException var25) {
                     var10000 = var25;
                     var10001 = false;
                     break label374;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.append("\", port: ");
                  } catch (IOException var24) {
                     var10000 = var24;
                     var10001 = false;
                     break label374;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.append(var4);
                  } catch (IOException var23) {
                     var10000 = var23;
                     var10001 = false;
                     break label374;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.append(", response: ");
                  } catch (IOException var22) {
                     var10000 = var22;
                     var10001 = false;
                     break label374;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.append(var7);
                  } catch (IOException var21) {
                     var10000 = var21;
                     var10001 = false;
                     break label374;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.append("\n");
                  } catch (IOException var20) {
                     var10000 = var20;
                     var10001 = false;
                     break label374;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var5.println(var6.toString());
                  } catch (IOException var19) {
                     var10000 = var19;
                     var10001 = false;
                     break label374;
                  }
               }

               var3 = var1;
               var2 = var4;

               MessagingException var59;
               try {
                  var59 = new MessagingException;
               } catch (IOException var18) {
                  var10000 = var18;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               StringBuilder var58;
               try {
                  var58 = new StringBuilder;
               } catch (IOException var17) {
                  var10000 = var17;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  var58.<init>("Got bad greeting from SMTP host: ");
               } catch (IOException var16) {
                  var10000 = var16;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  var58.append(var1);
               } catch (IOException var15) {
                  var10000 = var15;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  var58.append(", port: ");
               } catch (IOException var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  var58.append(var4);
               } catch (IOException var13) {
                  var10000 = var13;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  var58.append(", response: ");
               } catch (IOException var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  var58.append(var7);
               } catch (IOException var11) {
                  var10000 = var11;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  var59.<init>(var58.toString());
               } catch (IOException var10) {
                  var10000 = var10;
                  var10001 = false;
                  break label374;
               }

               var3 = var1;
               var2 = var4;

               try {
                  throw var59;
               } catch (IOException var9) {
                  var10000 = var9;
                  var10001 = false;
               }
            }
         } else {
            label376: {
               var3 = var1;
               var2 = var4;

               label377: {
                  try {
                     if (!this.debug) {
                        break label377;
                     }
                  } catch (IOException var44) {
                     var10000 = var44;
                     var10001 = false;
                     break label376;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var5 = this.out;
                  } catch (IOException var43) {
                     var10000 = var43;
                     var10001 = false;
                     break label376;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6 = new StringBuilder;
                  } catch (IOException var42) {
                     var10000 = var42;
                     var10001 = false;
                     break label376;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.<init>("DEBUG SMTP: protocol started to host \"");
                  } catch (IOException var41) {
                     var10000 = var41;
                     var10001 = false;
                     break label376;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.append(var1);
                  } catch (IOException var40) {
                     var10000 = var40;
                     var10001 = false;
                     break label376;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.append("\", port: ");
                  } catch (IOException var39) {
                     var10000 = var39;
                     var10001 = false;
                     break label376;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.append(var4);
                  } catch (IOException var38) {
                     var10000 = var38;
                     var10001 = false;
                     break label376;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var6.append("\n");
                  } catch (IOException var37) {
                     var10000 = var37;
                     var10001 = false;
                     break label376;
                  }

                  var3 = var1;
                  var2 = var4;

                  try {
                     var5.println(var6.toString());
                  } catch (IOException var36) {
                     var10000 = var36;
                     var10001 = false;
                     break label376;
                  }
               }

               try {
                  return;
               } catch (IOException var35) {
                  var10000 = var35;
                  var10001 = false;
               }
            }
         }
      }

      IOException var57 = var10000;
      var6 = new StringBuilder("Could not start protocol to SMTP host: ");
      var6.append(var3);
      var6.append(", port: ");
      var6.append(var2);
      throw new MessagingException(var6.toString(), var57);
   }

   private void openServer(String var1, int var2) throws MessagingException {
      PrintStream var3;
      StringBuilder var4;
      if (this.debug) {
         var3 = this.out;
         var4 = new StringBuilder("DEBUG SMTP: trying to connect to host \"");
         var4.append(var1);
         var4.append("\", port ");
         var4.append(var2);
         var4.append(", isSSL ");
         var4.append(this.isSSL);
         var3.println(var4.toString());
      }

      int var5 = var2;

      UnknownHostException var104;
      label443: {
         IOException var10000;
         label449: {
            boolean var10001;
            Properties var100;
            try {
               var100 = this.session.getProperties();
            } catch (UnknownHostException var96) {
               var104 = var96;
               var10001 = false;
               break label443;
            } catch (IOException var97) {
               var10000 = var97;
               var10001 = false;
               break label449;
            }

            var5 = var2;

            StringBuilder var98;
            try {
               var98 = new StringBuilder;
            } catch (UnknownHostException var94) {
               var104 = var94;
               var10001 = false;
               break label443;
            } catch (IOException var95) {
               var10000 = var95;
               var10001 = false;
               break label449;
            }

            var5 = var2;

            try {
               var98.<init>("mail.");
            } catch (UnknownHostException var92) {
               var104 = var92;
               var10001 = false;
               break label443;
            } catch (IOException var93) {
               var10000 = var93;
               var10001 = false;
               break label449;
            }

            var5 = var2;

            try {
               var98.append(this.name);
            } catch (UnknownHostException var90) {
               var104 = var90;
               var10001 = false;
               break label443;
            } catch (IOException var91) {
               var10000 = var91;
               var10001 = false;
               break label449;
            }

            var5 = var2;

            Socket var99;
            try {
               var99 = SocketFetcher.getSocket(var1, var2, var100, var98.toString(), this.isSSL);
            } catch (UnknownHostException var88) {
               var104 = var88;
               var10001 = false;
               break label443;
            } catch (IOException var89) {
               var10000 = var89;
               var10001 = false;
               break label449;
            }

            var5 = var2;

            try {
               this.serverSocket = var99;
            } catch (UnknownHostException var86) {
               var104 = var86;
               var10001 = false;
               break label443;
            } catch (IOException var87) {
               var10000 = var87;
               var10001 = false;
               break label449;
            }

            var5 = var2;

            try {
               var2 = var99.getPort();
            } catch (UnknownHostException var84) {
               var104 = var84;
               var10001 = false;
               break label443;
            } catch (IOException var85) {
               var10000 = var85;
               var10001 = false;
               break label449;
            }

            var5 = var2;

            try {
               this.initStreams();
            } catch (UnknownHostException var82) {
               var104 = var82;
               var10001 = false;
               break label443;
            } catch (IOException var83) {
               var10000 = var83;
               var10001 = false;
               break label449;
            }

            var5 = var2;

            int var6;
            try {
               var6 = this.readServerResponse();
            } catch (UnknownHostException var80) {
               var104 = var80;
               var10001 = false;
               break label443;
            } catch (IOException var81) {
               var10000 = var81;
               var10001 = false;
               break label449;
            }

            if (var6 != 220) {
               label450: {
                  var5 = var2;

                  try {
                     this.serverSocket.close();
                  } catch (UnknownHostException var58) {
                     var104 = var58;
                     var10001 = false;
                     break label443;
                  } catch (IOException var59) {
                     var10000 = var59;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     this.serverSocket = null;
                  } catch (UnknownHostException var56) {
                     var104 = var56;
                     var10001 = false;
                     break label443;
                  } catch (IOException var57) {
                     var10000 = var57;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     this.serverOutput = null;
                  } catch (UnknownHostException var54) {
                     var104 = var54;
                     var10001 = false;
                     break label443;
                  } catch (IOException var55) {
                     var10000 = var55;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     this.serverInput = null;
                  } catch (UnknownHostException var52) {
                     var104 = var52;
                     var10001 = false;
                     break label443;
                  } catch (IOException var53) {
                     var10000 = var53;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     this.lineInputStream = null;
                  } catch (UnknownHostException var50) {
                     var104 = var50;
                     var10001 = false;
                     break label443;
                  } catch (IOException var51) {
                     var10000 = var51;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  boolean var7;
                  try {
                     var7 = this.debug;
                  } catch (UnknownHostException var48) {
                     var104 = var48;
                     var10001 = false;
                     break label443;
                  } catch (IOException var49) {
                     var10000 = var49;
                     var10001 = false;
                     break label450;
                  }

                  if (var7) {
                     var5 = var2;

                     try {
                        var3 = this.out;
                     } catch (UnknownHostException var46) {
                        var104 = var46;
                        var10001 = false;
                        break label443;
                     } catch (IOException var47) {
                        var10000 = var47;
                        var10001 = false;
                        break label450;
                     }

                     var5 = var2;

                     try {
                        var4 = new StringBuilder;
                     } catch (UnknownHostException var44) {
                        var104 = var44;
                        var10001 = false;
                        break label443;
                     } catch (IOException var45) {
                        var10000 = var45;
                        var10001 = false;
                        break label450;
                     }

                     var5 = var2;

                     try {
                        var4.<init>("DEBUG SMTP: could not connect to host \"");
                     } catch (UnknownHostException var42) {
                        var104 = var42;
                        var10001 = false;
                        break label443;
                     } catch (IOException var43) {
                        var10000 = var43;
                        var10001 = false;
                        break label450;
                     }

                     var5 = var2;

                     try {
                        var4.append(var1);
                     } catch (UnknownHostException var40) {
                        var104 = var40;
                        var10001 = false;
                        break label443;
                     } catch (IOException var41) {
                        var10000 = var41;
                        var10001 = false;
                        break label450;
                     }

                     var5 = var2;

                     try {
                        var4.append("\", port: ");
                     } catch (UnknownHostException var38) {
                        var104 = var38;
                        var10001 = false;
                        break label443;
                     } catch (IOException var39) {
                        var10000 = var39;
                        var10001 = false;
                        break label450;
                     }

                     var5 = var2;

                     try {
                        var4.append(var2);
                     } catch (UnknownHostException var36) {
                        var104 = var36;
                        var10001 = false;
                        break label443;
                     } catch (IOException var37) {
                        var10000 = var37;
                        var10001 = false;
                        break label450;
                     }

                     var5 = var2;

                     try {
                        var4.append(", response: ");
                     } catch (UnknownHostException var34) {
                        var104 = var34;
                        var10001 = false;
                        break label443;
                     } catch (IOException var35) {
                        var10000 = var35;
                        var10001 = false;
                        break label450;
                     }

                     var5 = var2;

                     try {
                        var4.append(var6);
                     } catch (UnknownHostException var32) {
                        var104 = var32;
                        var10001 = false;
                        break label443;
                     } catch (IOException var33) {
                        var10000 = var33;
                        var10001 = false;
                        break label450;
                     }

                     var5 = var2;

                     try {
                        var4.append("\n");
                     } catch (UnknownHostException var30) {
                        var104 = var30;
                        var10001 = false;
                        break label443;
                     } catch (IOException var31) {
                        var10000 = var31;
                        var10001 = false;
                        break label450;
                     }

                     var5 = var2;

                     try {
                        var3.println(var4.toString());
                     } catch (UnknownHostException var28) {
                        var104 = var28;
                        var10001 = false;
                        break label443;
                     } catch (IOException var29) {
                        var10000 = var29;
                        var10001 = false;
                        break label450;
                     }
                  }

                  var5 = var2;

                  MessagingException var102;
                  try {
                     var102 = new MessagingException;
                  } catch (UnknownHostException var26) {
                     var104 = var26;
                     var10001 = false;
                     break label443;
                  } catch (IOException var27) {
                     var10000 = var27;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     var98 = new StringBuilder;
                  } catch (UnknownHostException var24) {
                     var104 = var24;
                     var10001 = false;
                     break label443;
                  } catch (IOException var25) {
                     var10000 = var25;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     var98.<init>("Could not connect to SMTP host: ");
                  } catch (UnknownHostException var22) {
                     var104 = var22;
                     var10001 = false;
                     break label443;
                  } catch (IOException var23) {
                     var10000 = var23;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     var98.append(var1);
                  } catch (UnknownHostException var20) {
                     var104 = var20;
                     var10001 = false;
                     break label443;
                  } catch (IOException var21) {
                     var10000 = var21;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     var98.append(", port: ");
                  } catch (UnknownHostException var18) {
                     var104 = var18;
                     var10001 = false;
                     break label443;
                  } catch (IOException var19) {
                     var10000 = var19;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     var98.append(var2);
                  } catch (UnknownHostException var16) {
                     var104 = var16;
                     var10001 = false;
                     break label443;
                  } catch (IOException var17) {
                     var10000 = var17;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     var98.append(", response: ");
                  } catch (UnknownHostException var14) {
                     var104 = var14;
                     var10001 = false;
                     break label443;
                  } catch (IOException var15) {
                     var10000 = var15;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     var98.append(var6);
                  } catch (UnknownHostException var12) {
                     var104 = var12;
                     var10001 = false;
                     break label443;
                  } catch (IOException var13) {
                     var10000 = var13;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     var102.<init>(var98.toString());
                  } catch (UnknownHostException var10) {
                     var104 = var10;
                     var10001 = false;
                     break label443;
                  } catch (IOException var11) {
                     var10000 = var11;
                     var10001 = false;
                     break label450;
                  }

                  var5 = var2;

                  try {
                     throw var102;
                  } catch (UnknownHostException var8) {
                     var104 = var8;
                     var10001 = false;
                     break label443;
                  } catch (IOException var9) {
                     var10000 = var9;
                     var10001 = false;
                  }
               }
            } else {
               label452: {
                  var5 = var2;

                  label453: {
                     try {
                        if (!this.debug) {
                           break label453;
                        }
                     } catch (UnknownHostException var78) {
                        var104 = var78;
                        var10001 = false;
                        break label443;
                     } catch (IOException var79) {
                        var10000 = var79;
                        var10001 = false;
                        break label452;
                     }

                     var5 = var2;

                     try {
                        var3 = this.out;
                     } catch (UnknownHostException var76) {
                        var104 = var76;
                        var10001 = false;
                        break label443;
                     } catch (IOException var77) {
                        var10000 = var77;
                        var10001 = false;
                        break label452;
                     }

                     var5 = var2;

                     try {
                        var4 = new StringBuilder;
                     } catch (UnknownHostException var74) {
                        var104 = var74;
                        var10001 = false;
                        break label443;
                     } catch (IOException var75) {
                        var10000 = var75;
                        var10001 = false;
                        break label452;
                     }

                     var5 = var2;

                     try {
                        var4.<init>("DEBUG SMTP: connected to host \"");
                     } catch (UnknownHostException var72) {
                        var104 = var72;
                        var10001 = false;
                        break label443;
                     } catch (IOException var73) {
                        var10000 = var73;
                        var10001 = false;
                        break label452;
                     }

                     var5 = var2;

                     try {
                        var4.append(var1);
                     } catch (UnknownHostException var70) {
                        var104 = var70;
                        var10001 = false;
                        break label443;
                     } catch (IOException var71) {
                        var10000 = var71;
                        var10001 = false;
                        break label452;
                     }

                     var5 = var2;

                     try {
                        var4.append("\", port: ");
                     } catch (UnknownHostException var68) {
                        var104 = var68;
                        var10001 = false;
                        break label443;
                     } catch (IOException var69) {
                        var10000 = var69;
                        var10001 = false;
                        break label452;
                     }

                     var5 = var2;

                     try {
                        var4.append(var2);
                     } catch (UnknownHostException var66) {
                        var104 = var66;
                        var10001 = false;
                        break label443;
                     } catch (IOException var67) {
                        var10000 = var67;
                        var10001 = false;
                        break label452;
                     }

                     var5 = var2;

                     try {
                        var4.append("\n");
                     } catch (UnknownHostException var64) {
                        var104 = var64;
                        var10001 = false;
                        break label443;
                     } catch (IOException var65) {
                        var10000 = var65;
                        var10001 = false;
                        break label452;
                     }

                     var5 = var2;

                     try {
                        var3.println(var4.toString());
                     } catch (UnknownHostException var62) {
                        var104 = var62;
                        var10001 = false;
                        break label443;
                     } catch (IOException var63) {
                        var10000 = var63;
                        var10001 = false;
                        break label452;
                     }
                  }

                  try {
                     return;
                  } catch (UnknownHostException var60) {
                     var104 = var60;
                     var10001 = false;
                     break label443;
                  } catch (IOException var61) {
                     var10000 = var61;
                     var10001 = false;
                  }
               }
            }
         }

         IOException var101 = var10000;
         var4 = new StringBuilder("Could not connect to SMTP host: ");
         var4.append(var1);
         var4.append(", port: ");
         var4.append(var5);
         throw new MessagingException(var4.toString(), var101);
      }

      UnknownHostException var103 = var104;
      var4 = new StringBuilder("Unknown SMTP host: ");
      var4.append(var1);
      throw new MessagingException(var4.toString(), var103);
   }

   private void sendCommand(byte[] var1) throws MessagingException {
      try {
         this.serverOutput.write(var1);
         this.serverOutput.write(CRLF);
         this.serverOutput.flush();
      } catch (IOException var2) {
         throw new MessagingException("Can't send command to SMTP host", var2);
      }
   }

   protected static String xtext(String var0) {
      StringBuffer var1 = null;

      StringBuffer var4;
      for(int var2 = 0; var2 < var0.length(); var1 = var4) {
         char var3 = var0.charAt(var2);
         if (var3 >= 128) {
            StringBuilder var5 = new StringBuilder("Non-ASCII character in SMTP submitter: ");
            var5.append(var0);
            throw new IllegalArgumentException(var5.toString());
         }

         if (var3 >= '!' && var3 <= '~' && var3 != '+' && var3 != '=') {
            var4 = var1;
            if (var1 != null) {
               var1.append(var3);
               var4 = var1;
            }
         } else {
            var4 = var1;
            if (var1 == null) {
               var4 = new StringBuffer(var0.length() + 4);
               var4.append(var0.substring(0, var2));
            }

            var4.append('+');
            var4.append(hexchar[(var3 & 240) >> 4]);
            var4.append(hexchar[var3 & 15]);
         }

         ++var2;
      }

      if (var1 != null) {
         var0 = var1.toString();
      }

      return var0;
   }

   protected void checkConnected() {
      if (!super.isConnected()) {
         throw new IllegalStateException("Not connected");
      }
   }

   public void close() throws MessagingException {
      synchronized(this){}

      Throwable var3;
      Throwable var10000;
      label357: {
         boolean var1;
         boolean var10001;
         try {
            var1 = super.isConnected();
         } catch (Throwable var34) {
            var10000 = var34;
            var10001 = false;
            break label357;
         }

         if (!var1) {
            return;
         }

         label358: {
            label359: {
               int var2;
               try {
                  if (this.serverSocket == null) {
                     break label358;
                  }

                  this.sendCommand("QUIT");
                  if (!this.quitWait) {
                     break label358;
                  }

                  var2 = this.readServerResponse();
               } catch (Throwable var33) {
                  var10000 = var33;
                  var10001 = false;
                  break label359;
               }

               if (var2 == 221 || var2 == -1) {
                  break label358;
               }

               label342:
               try {
                  PrintStream var35 = this.out;
                  StringBuilder var4 = new StringBuilder("DEBUG SMTP: QUIT failed with ");
                  var4.append(var2);
                  var35.println(var4.toString());
                  break label358;
               } catch (Throwable var32) {
                  var10000 = var32;
                  var10001 = false;
                  break label342;
               }
            }

            var3 = var10000;

            try {
               this.closeConnection();
               throw var3;
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break label357;
            }
         }

         label331:
         try {
            this.closeConnection();
            return;
         } catch (Throwable var31) {
            var10000 = var31;
            var10001 = false;
            break label331;
         }
      }

      var3 = var10000;
      throw var3;
   }

   public void connect(Socket var1) throws MessagingException {
      synchronized(this){}

      try {
         this.serverSocket = var1;
         super.connect();
      } finally {
         ;
      }

   }

   protected OutputStream data() throws MessagingException {
      this.issueSendCommand("DATA", 354);
      SMTPOutputStream var1 = new SMTPOutputStream(this.serverOutput);
      this.dataStream = var1;
      return var1;
   }

   protected boolean ehlo(String var1) throws MessagingException {
      if (var1 != null) {
         StringBuilder var2 = new StringBuilder("EHLO ");
         var2.append(var1);
         var1 = var2.toString();
      } else {
         var1 = "EHLO";
      }

      this.sendCommand(var1);
      int var3 = this.readServerResponse();
      if (var3 == 250) {
         BufferedReader var4 = new BufferedReader(new StringReader(this.lastServerResponse));
         this.extMap = new Hashtable();
         boolean var5 = true;

         while(true) {
            while(true) {
               while(true) {
                  boolean var10001;
                  try {
                     var1 = var4.readLine();
                  } catch (IOException var14) {
                     var10001 = false;
                     return var3 == 250;
                  }

                  if (var1 == null) {
                     return var3 == 250;
                  }

                  if (var5) {
                     var5 = false;
                  } else {
                     try {
                        if (var1.length() < 5) {
                           continue;
                        }
                     } catch (IOException var13) {
                        var10001 = false;
                        return var3 == 250;
                     }

                     String var6;
                     int var7;
                     try {
                        var6 = var1.substring(4);
                        var7 = var6.indexOf(32);
                     } catch (IOException var12) {
                        var10001 = false;
                        return var3 == 250;
                     }

                     String var15 = "";
                     var1 = var6;
                     if (var7 > 0) {
                        try {
                           var15 = var6.substring(var7 + 1);
                           var1 = var6.substring(0, var7);
                        } catch (IOException var11) {
                           var10001 = false;
                           return var3 == 250;
                        }
                     }

                     try {
                        if (this.debug) {
                           PrintStream var8 = this.out;
                           StringBuilder var16 = new StringBuilder("DEBUG SMTP: Found extension \"");
                           var16.append(var1);
                           var16.append("\", arg \"");
                           var16.append(var15);
                           var16.append("\"");
                           var8.println(var16.toString());
                        }
                     } catch (IOException var10) {
                        var10001 = false;
                        return var3 == 250;
                     }

                     try {
                        this.extMap.put(var1.toUpperCase(Locale.ENGLISH), var15);
                     } catch (IOException var9) {
                        var10001 = false;
                        return var3 == 250;
                     }
                  }
               }
            }
         }
      } else {
         return var3 == 250;
      }
   }

   protected void finalize() throws Throwable {
      super.finalize();

      try {
         this.closeConnection();
      } catch (MessagingException var2) {
      }

   }

   protected void finishData() throws IOException, MessagingException {
      this.dataStream.ensureAtBOL();
      this.issueSendCommand(".", 250);
   }

   public String getExtensionParameter(String var1) {
      Hashtable var2 = this.extMap;
      if (var2 == null) {
         var1 = null;
      } else {
         var1 = (String)var2.get(var1.toUpperCase(Locale.ENGLISH));
      }

      return var1;
   }

   public int getLastReturnCode() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.lastReturnCode;
      } finally {
         ;
      }

      return var1;
   }

   public String getLastServerResponse() {
      synchronized(this){}

      String var1;
      try {
         var1 = this.lastServerResponse;
      } finally {
         ;
      }

      return var1;
   }

   public String getLocalHost() {
      // $FF: Couldn't be decompiled
   }

   public boolean getReportSuccess() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.reportSuccess;
      } finally {
         ;
      }

      return var1;
   }

   public String getSASLRealm() {
      synchronized(this){}

      Throwable var10000;
      label120: {
         boolean var10001;
         String var15;
         label119: {
            try {
               if (this.saslRealm != "UNKNOWN") {
                  break label119;
               }

               Session var1 = this.session;
               StringBuilder var2 = new StringBuilder("mail.");
               var2.append(this.name);
               var2.append(".sasl.realm");
               var15 = var1.getProperty(var2.toString());
               this.saslRealm = var15;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label120;
            }

            if (var15 == null) {
               try {
                  Session var18 = this.session;
                  StringBuilder var16 = new StringBuilder("mail.");
                  var16.append(this.name);
                  var16.append(".saslrealm");
                  this.saslRealm = var18.getProperty(var16.toString());
               } catch (Throwable var13) {
                  var10000 = var13;
                  var10001 = false;
                  break label120;
               }
            }
         }

         label111:
         try {
            var15 = this.saslRealm;
            return var15;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            break label111;
         }
      }

      Throwable var17 = var10000;
      throw var17;
   }

   public boolean getStartTLS() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.useStartTLS;
      } finally {
         ;
      }

      return var1;
   }

   public boolean getUseRset() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.useRset;
      } finally {
         ;
      }

      return var1;
   }

   protected void helo(String var1) throws MessagingException {
      if (var1 != null) {
         StringBuilder var2 = new StringBuilder("HELO ");
         var2.append(var1);
         this.issueCommand(var2.toString(), 250);
      } else {
         this.issueCommand("HELO", 250);
      }

   }

   public boolean isConnected() {
      // $FF: Couldn't be decompiled
   }

   public void issueCommand(String var1, int var2) throws MessagingException {
      synchronized(this){}

      Throwable var10000;
      label72: {
         boolean var10001;
         int var3;
         try {
            this.sendCommand(var1);
            var3 = this.readServerResponse();
         } catch (Throwable var9) {
            var10000 = var9;
            var10001 = false;
            break label72;
         }

         if (var3 == var2) {
            return;
         }

         label63:
         try {
            MessagingException var11 = new MessagingException(this.lastServerResponse);
            throw var11;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label63;
         }
      }

      Throwable var10 = var10000;
      throw var10;
   }

   protected void mailFrom() throws MessagingException {
      MimeMessage var1 = this.message;
      boolean var2 = var1 instanceof SMTPMessage;
      Object var3 = null;
      String var4;
      if (var2) {
         var4 = ((SMTPMessage)var1).getEnvelopeFrom();
      } else {
         var4 = null;
      }

      String var9;
      Session var10;
      StringBuilder var14;
      label91: {
         if (var4 != null) {
            var9 = var4;
            if (var4.length() > 0) {
               break label91;
            }
         }

         var10 = this.session;
         var14 = new StringBuilder("mail.");
         var14.append(this.name);
         var14.append(".from");
         var9 = var10.getProperty(var14.toString());
      }

      label95: {
         if (var9 != null) {
            var4 = var9;
            if (var9.length() > 0) {
               break label95;
            }
         }

         Object var12;
         label81: {
            var1 = this.message;
            if (var1 != null) {
               Address[] var11 = var1.getFrom();
               if (var11 != null && var11.length > 0) {
                  var12 = var11[0];
                  break label81;
               }
            }

            var12 = InternetAddress.getLocalAddress(this.session);
         }

         if (var12 == null) {
            throw new MessagingException("can't determine local email address");
         }

         var4 = ((InternetAddress)var12).getAddress();
      }

      StringBuilder var13 = new StringBuilder("MAIL FROM:");
      var13.append(this.normalizeAddress(var4));
      String var5 = var13.toString();
      var9 = var5;
      Session var17;
      if (this.supportsExtension("DSN")) {
         var1 = this.message;
         if (var1 instanceof SMTPMessage) {
            var9 = ((SMTPMessage)var1).getDSNRet();
         } else {
            var9 = null;
         }

         var4 = var9;
         if (var9 == null) {
            var17 = this.session;
            var13 = new StringBuilder("mail.");
            var13.append(this.name);
            var13.append(".dsn.ret");
            var4 = var17.getProperty(var13.toString());
         }

         var9 = var5;
         if (var4 != null) {
            var13 = new StringBuilder(String.valueOf(var5));
            var13.append(" RET=");
            var13.append(var4);
            var9 = var13.toString();
         }
      }

      var4 = var9;
      StringBuilder var15;
      if (this.supportsExtension("AUTH")) {
         MimeMessage var18 = this.message;
         if (var18 instanceof SMTPMessage) {
            var4 = ((SMTPMessage)var18).getSubmitter();
         } else {
            var4 = null;
         }

         var5 = var4;
         if (var4 == null) {
            var17 = this.session;
            var15 = new StringBuilder("mail.");
            var15.append(this.name);
            var15.append(".submitter");
            var5 = var17.getProperty(var15.toString());
         }

         var4 = var9;
         if (var5 != null) {
            try {
               var4 = xtext(var5);
               StringBuilder var6 = new StringBuilder(String.valueOf(var9));
               var6.append(" AUTH=");
               var6.append(var4);
               var4 = var6.toString();
            } catch (IllegalArgumentException var8) {
               var4 = var9;
               if (this.debug) {
                  PrintStream var7 = this.out;
                  var14 = new StringBuilder("DEBUG SMTP: ignoring invalid submitter: ");
                  var14.append(var5);
                  var14.append(", Exception: ");
                  var14.append(var8);
                  var7.println(var14.toString());
                  var4 = var9;
               }
            }
         }
      }

      MimeMessage var16 = this.message;
      var9 = (String)var3;
      if (var16 instanceof SMTPMessage) {
         var9 = ((SMTPMessage)var16).getMailExtension();
      }

      var5 = var9;
      if (var9 == null) {
         var10 = this.session;
         var15 = new StringBuilder("mail.");
         var15.append(this.name);
         var15.append(".mailextension");
         var5 = var10.getProperty(var15.toString());
      }

      var9 = var4;
      if (var5 != null) {
         var9 = var4;
         if (var5.length() > 0) {
            var13 = new StringBuilder(String.valueOf(var4));
            var13.append(" ");
            var13.append(var5);
            var9 = var13.toString();
         }
      }

      this.issueSendCommand(var9, 250);
   }

   protected boolean protocolConnect(String param1, int param2, String param3, String param4) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   protected void rcptTo() throws MessagingException {
      Vector var1 = new Vector();
      Vector var2 = new Vector();
      Vector var3 = new Vector();
      this.invalidAddr = null;
      this.validUnsentAddr = null;
      this.validSentAddr = null;
      MimeMessage var4 = this.message;
      boolean var5;
      if (var4 instanceof SMTPMessage) {
         var5 = ((SMTPMessage)var4).getSendPartial();
      } else {
         var5 = false;
      }

      boolean var6 = var5;
      StringBuilder var7;
      String var22;
      if (!var5) {
         Session var21 = this.session;
         var7 = new StringBuilder("mail.");
         var7.append(this.name);
         var7.append(".sendpartial");
         var22 = var21.getProperty(var7.toString());
         if (var22 != null && var22.equalsIgnoreCase("true")) {
            var6 = true;
         } else {
            var6 = false;
         }
      }

      if (this.debug && var6) {
         this.out.println("DEBUG SMTP: sendPartial set");
      }

      boolean var8;
      StringBuilder var23;
      String var25;
      label528: {
         if (this.supportsExtension("DSN")) {
            var4 = this.message;
            if (var4 instanceof SMTPMessage) {
               var25 = ((SMTPMessage)var4).getDSNNotify();
            } else {
               var25 = null;
            }

            var22 = var25;
            if (var25 == null) {
               Session var26 = this.session;
               var23 = new StringBuilder("mail.");
               var23.append(this.name);
               var23.append(".dsn.notify");
               var22 = var26.getProperty(var23.toString());
            }

            var25 = var22;
            if (var22 != null) {
               var8 = true;
               var25 = var22;
               break label528;
            }
         } else {
            var25 = null;
         }

         var8 = false;
      }

      Object var24 = null;
      int var9 = 0;
      boolean var10 = false;

      while(true) {
         Address[] var11 = this.addresses;
         int var29;
         if (var9 >= var11.length) {
            var8 = var10;
            if (var6) {
               var8 = var10;
               if (var1.size() == 0) {
                  var8 = true;
               }
            }

            Address[] var28;
            if (var8) {
               var28 = new Address[var3.size()];
               this.invalidAddr = var28;
               var3.copyInto(var28);
               this.validUnsentAddr = new Address[var1.size() + var2.size()];
               var9 = 0;

               for(var29 = 0; var9 < var1.size(); ++var29) {
                  this.validUnsentAddr[var29] = (Address)var1.elementAt(var9);
                  ++var9;
               }

               for(var9 = 0; var9 < var2.size(); ++var29) {
                  this.validUnsentAddr[var29] = (Address)var2.elementAt(var9);
                  ++var9;
               }
            } else if (this.reportSuccess || var6 && (var3.size() > 0 || var2.size() > 0)) {
               this.sendPartiallyFailed = true;
               this.exception = (MessagingException)var24;
               var28 = new Address[var3.size()];
               this.invalidAddr = var28;
               var3.copyInto(var28);
               var28 = new Address[var2.size()];
               this.validUnsentAddr = var28;
               var2.copyInto(var28);
               var28 = new Address[var1.size()];
               this.validSentAddr = var28;
               var1.copyInto(var28);
            } else {
               this.validSentAddr = this.addresses;
            }

            if (this.debug) {
               var28 = this.validSentAddr;
               PrintStream var34;
               if (var28 != null && var28.length > 0) {
                  this.out.println("DEBUG SMTP: Verified Addresses");

                  for(var29 = 0; var29 < this.validSentAddr.length; ++var29) {
                     var34 = this.out;
                     var7 = new StringBuilder("DEBUG SMTP:   ");
                     var7.append(this.validSentAddr[var29]);
                     var34.println(var7.toString());
                  }
               }

               var28 = this.validUnsentAddr;
               if (var28 != null && var28.length > 0) {
                  this.out.println("DEBUG SMTP: Valid Unsent Addresses");

                  for(var29 = 0; var29 < this.validUnsentAddr.length; ++var29) {
                     var34 = this.out;
                     var7 = new StringBuilder("DEBUG SMTP:   ");
                     var7.append(this.validUnsentAddr[var29]);
                     var34.println(var7.toString());
                  }
               }

               var28 = this.invalidAddr;
               if (var28 != null && var28.length > 0) {
                  this.out.println("DEBUG SMTP: Invalid Addresses");

                  for(var29 = 0; var29 < this.invalidAddr.length; ++var29) {
                     var34 = this.out;
                     var7 = new StringBuilder("DEBUG SMTP:   ");
                     var7.append(this.invalidAddr[var29]);
                     var34.println(var7.toString());
                  }
               }
            }

            if (var8) {
               if (this.debug) {
                  this.out.println("DEBUG SMTP: Sending failed because of invalid destination addresses");
               }

               this.notifyTransportListeners(2, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, this.message);
               var25 = this.lastServerResponse;
               var29 = this.lastReturnCode;

               try {
                  if (this.serverSocket != null) {
                     this.issueCommand("RSET", 250);
                  }
               } catch (MessagingException var19) {
                  try {
                     this.close();
                  } catch (MessagingException var18) {
                     if (this.debug) {
                        var18.printStackTrace(this.out);
                     }
                  }
               } finally {
                  this.lastServerResponse = var25;
                  this.lastReturnCode = var29;
               }

               throw new SendFailedException("Invalid Addresses", (Exception)var24, this.validSentAddr, this.validUnsentAddr, this.invalidAddr);
            }

            return;
         }

         InternetAddress var12 = (InternetAddress)var11[var9];
         StringBuilder var30 = new StringBuilder("RCPT TO:");
         var30.append(this.normalizeAddress(var12.getAddress()));
         String var13 = var30.toString();
         String var31 = var13;
         if (var8) {
            var30 = new StringBuilder(String.valueOf(var13));
            var30.append(" NOTIFY=");
            var30.append(var25);
            var31 = var30.toString();
         }

         this.sendCommand(var31);
         int var14 = this.readServerResponse();
         if (var14 != 250 && var14 != 251) {
            label564: {
               SMTPAddressFailedException var33;
               if (var14 != 501 && var14 != 503) {
                  label508:
                  switch(var14) {
                  default:
                     switch(var14) {
                     case 550:
                     case 551:
                     case 553:
                        break label508;
                     case 552:
                        break;
                     default:
                        if (var14 >= 400 && var14 <= 499) {
                           var2.addElement(var12);
                        } else {
                           if (var14 < 500 || var14 > 599) {
                              if (this.debug) {
                                 PrintStream var27 = this.out;
                                 var23 = new StringBuilder("DEBUG SMTP: got response code ");
                                 var23.append(var14);
                                 var23.append(", with response: ");
                                 var23.append(this.lastServerResponse);
                                 var27.println(var23.toString());
                              }

                              var22 = this.lastServerResponse;
                              var29 = this.lastReturnCode;
                              if (this.serverSocket != null) {
                                 this.issueCommand("RSET", 250);
                              }

                              this.lastServerResponse = var22;
                              this.lastReturnCode = var29;
                              throw new SMTPAddressFailedException(var12, var31, var14, var22);
                           }

                           var3.addElement(var12);
                        }

                        if (!var6) {
                           var10 = true;
                        }

                        var33 = new SMTPAddressFailedException(var12, var31, var14, this.lastServerResponse);
                        if (var24 == null) {
                           var24 = var33;
                        } else {
                           ((MessagingException)var24).setNextException(var33);
                        }
                        break label564;
                     }
                  case 450:
                  case 451:
                  case 452:
                     if (!var6) {
                        var10 = true;
                     }

                     var2.addElement(var12);
                     var33 = new SMTPAddressFailedException(var12, var31, var14, this.lastServerResponse);
                     if (var24 == null) {
                        var24 = var33;
                     } else {
                        ((MessagingException)var24).setNextException(var33);
                     }
                     break label564;
                  }
               }

               if (!var6) {
                  var10 = true;
               }

               var3.addElement(var12);
               var33 = new SMTPAddressFailedException(var12, var31, var14, this.lastServerResponse);
               if (var24 == null) {
                  var24 = var33;
               } else {
                  ((MessagingException)var24).setNextException(var33);
               }
            }
         } else {
            var1.addElement(var12);
            if (this.reportSuccess) {
               SMTPAddressSucceededException var32 = new SMTPAddressSucceededException(var12, var31, var14, this.lastServerResponse);
               if (var24 == null) {
                  var24 = var32;
               } else {
                  ((MessagingException)var24).setNextException(var32);
               }
            }
         }

         ++var9;
      }
   }

   protected int readServerResponse() throws MessagingException {
      StringBuffer var1 = new StringBuffer(100);

      IOException var10000;
      while(true) {
         String var2;
         boolean var10001;
         try {
            var2 = this.lineInputStream.readLine();
         } catch (IOException var13) {
            var10000 = var13;
            var10001 = false;
            break;
         }

         StringBuilder var3;
         PrintStream var15;
         if (var2 == null) {
            String var14;
            try {
               var14 = var1.toString();
            } catch (IOException var11) {
               var10000 = var11;
               var10001 = false;
               break;
            }

            var2 = var14;

            label85: {
               try {
                  if (var14.length() != 0) {
                     break label85;
                  }
               } catch (IOException var10) {
                  var10000 = var10;
                  var10001 = false;
                  break;
               }

               var2 = "[EOF]";
            }

            try {
               this.lastServerResponse = var2;
               this.lastReturnCode = -1;
               if (this.debug) {
                  var15 = this.out;
                  var3 = new StringBuilder("DEBUG SMTP: EOF: ");
                  var3.append(var2);
                  var15.println(var3.toString());
               }

               return -1;
            } catch (IOException var9) {
               var10000 = var9;
               var10001 = false;
               break;
            }
         } else {
            try {
               var1.append(var2);
               var1.append("\n");
               if (this.isNotLastLine(var2)) {
                  continue;
               }

               var2 = var1.toString();
            } catch (IOException var12) {
               var10000 = var12;
               var10001 = false;
               break;
            }

            int var4;
            label74: {
               if (var2 != null && var2.length() >= 3) {
                  try {
                     var4 = Integer.parseInt(var2.substring(0, 3));
                     break label74;
                  } catch (NumberFormatException var7) {
                     try {
                        this.close();
                     } catch (MessagingException var5) {
                        if (this.debug) {
                           var5.printStackTrace(this.out);
                        }
                     }
                  } catch (StringIndexOutOfBoundsException var8) {
                     try {
                        this.close();
                     } catch (MessagingException var6) {
                        if (this.debug) {
                           var6.printStackTrace(this.out);
                        }
                     }
                  }
               }

               var4 = -1;
            }

            if (var4 == -1 && this.debug) {
               var15 = this.out;
               var3 = new StringBuilder("DEBUG SMTP: bad server response: ");
               var3.append(var2);
               var15.println(var3.toString());
            }

            this.lastServerResponse = var2;
            this.lastReturnCode = var4;
            return var4;
         }
      }

      IOException var17 = var10000;
      if (this.debug) {
         PrintStream var18 = this.out;
         StringBuilder var16 = new StringBuilder("DEBUG SMTP: exception reading response: ");
         var16.append(var17);
         var18.println(var16.toString());
      }

      this.lastServerResponse = "";
      this.lastReturnCode = 0;
      throw new MessagingException("Exception reading response", var17);
   }

   protected void sendCommand(String var1) throws MessagingException {
      this.sendCommand(ASCIIUtility.getBytes(var1));
   }

   public void sendMessage(Message param1, Address[] param2) throws MessagingException, SendFailedException {
      // $FF: Couldn't be decompiled
   }

   public void setLocalHost(String var1) {
      synchronized(this){}

      try {
         this.localHostName = var1;
      } finally {
         ;
      }

   }

   public void setReportSuccess(boolean var1) {
      synchronized(this){}

      try {
         this.reportSuccess = var1;
      } finally {
         ;
      }

   }

   public void setSASLRealm(String var1) {
      synchronized(this){}

      try {
         this.saslRealm = var1;
      } finally {
         ;
      }

   }

   public void setStartTLS(boolean var1) {
      synchronized(this){}

      try {
         this.useStartTLS = var1;
      } finally {
         ;
      }

   }

   public void setUseRset(boolean var1) {
      synchronized(this){}

      try {
         this.useRset = var1;
      } finally {
         ;
      }

   }

   public int simpleCommand(String var1) throws MessagingException {
      synchronized(this){}

      int var2;
      try {
         this.sendCommand(var1);
         var2 = this.readServerResponse();
      } finally {
         ;
      }

      return var2;
   }

   protected int simpleCommand(byte[] var1) throws MessagingException {
      this.sendCommand(var1);
      return this.readServerResponse();
   }

   protected void startTLS() throws MessagingException {
      this.issueCommand("STARTTLS", 220);

      try {
         Socket var1 = this.serverSocket;
         Properties var2 = this.session.getProperties();
         StringBuilder var3 = new StringBuilder("mail.");
         var3.append(this.name);
         this.serverSocket = SocketFetcher.startTLS(var1, var2, var3.toString());
         this.initStreams();
      } catch (IOException var4) {
         this.closeConnection();
         throw new MessagingException("Could not convert socket to TLS", var4);
      }
   }

   protected boolean supportsAuthentication(String var1) {
      Hashtable var2 = this.extMap;
      if (var2 == null) {
         return false;
      } else {
         String var3 = (String)var2.get("AUTH");
         if (var3 == null) {
            return false;
         } else {
            StringTokenizer var4 = new StringTokenizer(var3);

            while(var4.hasMoreTokens()) {
               if (var4.nextToken().equalsIgnoreCase(var1)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean supportsExtension(String var1) {
      Hashtable var2 = this.extMap;
      return var2 != null && var2.get(var1.toUpperCase(Locale.ENGLISH)) != null;
   }
}
