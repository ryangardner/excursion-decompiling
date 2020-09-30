package javax.mail.internet;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.FolderClosedIOException;
import com.sun.mail.util.LineOutputStream;
import com.sun.mail.util.MessageRemovedIOException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;

public class MimeBodyPart extends BodyPart implements MimePart {
   static boolean cacheMultipart;
   private static boolean decodeFileName;
   private static boolean encodeFileName;
   private static boolean setContentTypeFileName;
   private static boolean setDefaultTextCharset;
   private Object cachedContent;
   protected byte[] content;
   protected InputStream contentStream;
   protected DataHandler dh;
   protected InternetHeaders headers;

   static {
      label120: {
         String var0;
         boolean var10001;
         try {
            var0 = System.getProperty("mail.mime.setdefaulttextcharset");
         } catch (SecurityException var13) {
            var10001 = false;
            break label120;
         }

         boolean var1;
         boolean var2;
         label113: {
            label112: {
               var1 = false;
               if (var0 != null) {
                  try {
                     if (var0.equalsIgnoreCase("false")) {
                        break label112;
                     }
                  } catch (SecurityException var12) {
                     var10001 = false;
                     break label120;
                  }
               }

               var2 = true;
               break label113;
            }

            var2 = false;
         }

         try {
            setDefaultTextCharset = var2;
            var0 = System.getProperty("mail.mime.setcontenttypefilename");
         } catch (SecurityException var11) {
            var10001 = false;
            break label120;
         }

         label101: {
            label100: {
               if (var0 != null) {
                  try {
                     if (var0.equalsIgnoreCase("false")) {
                        break label100;
                     }
                  } catch (SecurityException var10) {
                     var10001 = false;
                     break label120;
                  }
               }

               var2 = true;
               break label101;
            }

            var2 = false;
         }

         try {
            setContentTypeFileName = var2;
            var0 = System.getProperty("mail.mime.encodefilename");
         } catch (SecurityException var9) {
            var10001 = false;
            break label120;
         }

         label89: {
            label88: {
               if (var0 != null) {
                  try {
                     if (!var0.equalsIgnoreCase("false")) {
                        break label88;
                     }
                  } catch (SecurityException var8) {
                     var10001 = false;
                     break label120;
                  }
               }

               var2 = false;
               break label89;
            }

            var2 = true;
         }

         try {
            encodeFileName = var2;
            var0 = System.getProperty("mail.mime.decodefilename");
         } catch (SecurityException var7) {
            var10001 = false;
            break label120;
         }

         label77: {
            label76: {
               if (var0 != null) {
                  try {
                     if (!var0.equalsIgnoreCase("false")) {
                        break label76;
                     }
                  } catch (SecurityException var6) {
                     var10001 = false;
                     break label120;
                  }
               }

               var2 = false;
               break label77;
            }

            var2 = true;
         }

         try {
            decodeFileName = var2;
            var0 = System.getProperty("mail.mime.cachemultipart");
         } catch (SecurityException var5) {
            var10001 = false;
            break label120;
         }

         label65: {
            label64: {
               if (var0 != null) {
                  try {
                     if (var0.equalsIgnoreCase("false")) {
                        break label64;
                     }
                  } catch (SecurityException var4) {
                     var10001 = false;
                     break label120;
                  }
               }

               var2 = true;
               break label65;
            }

            var2 = var1;
         }

         try {
            cacheMultipart = var2;
         } catch (SecurityException var3) {
            var10001 = false;
         }
      }

   }

   public MimeBodyPart() {
      this.headers = new InternetHeaders();
   }

   public MimeBodyPart(InputStream var1) throws MessagingException {
      Object var2 = var1;
      if (!(var1 instanceof ByteArrayInputStream)) {
         var2 = var1;
         if (!(var1 instanceof BufferedInputStream)) {
            var2 = var1;
            if (!(var1 instanceof SharedInputStream)) {
               var2 = new BufferedInputStream(var1);
            }
         }
      }

      this.headers = new InternetHeaders((InputStream)var2);
      if (var2 instanceof SharedInputStream) {
         SharedInputStream var4 = (SharedInputStream)var2;
         this.contentStream = var4.newStream(var4.getPosition(), -1L);
      } else {
         try {
            this.content = ASCIIUtility.getBytes((InputStream)var2);
         } catch (IOException var3) {
            throw new MessagingException("Error reading input stream", var3);
         }
      }

   }

   public MimeBodyPart(InternetHeaders var1, byte[] var2) throws MessagingException {
      this.headers = var1;
      this.content = var2;
   }

   static String[] getContentLanguage(MimePart var0) throws MessagingException {
      String var4 = var0.getHeader("Content-Language", (String)null);
      if (var4 == null) {
         return null;
      } else {
         HeaderTokenizer var1 = new HeaderTokenizer(var4, "()<>@,;:\\\"\t []/?=");
         Vector var5 = new Vector();

         while(true) {
            HeaderTokenizer.Token var2 = var1.next();
            int var3 = var2.getType();
            if (var3 == -4) {
               if (var5.size() == 0) {
                  return null;
               }

               String[] var6 = new String[var5.size()];
               var5.copyInto(var6);
               return var6;
            }

            if (var3 == -1) {
               var5.addElement(var2.getValue());
            }
         }
      }
   }

   static String getDescription(MimePart var0) throws MessagingException {
      String var3 = var0.getHeader("Content-Description", (String)null);
      if (var3 == null) {
         return null;
      } else {
         String var1;
         try {
            var1 = MimeUtility.decodeText(MimeUtility.unfold(var3));
         } catch (UnsupportedEncodingException var2) {
            return var3;
         }

         var3 = var1;
         return var3;
      }
   }

   static String getDisposition(MimePart var0) throws MessagingException {
      String var1 = var0.getHeader("Content-Disposition", (String)null);
      return var1 == null ? null : (new ContentDisposition(var1)).getDisposition();
   }

   static String getEncoding(MimePart var0) throws MessagingException {
      String var4 = var0.getHeader("Content-Transfer-Encoding", (String)null);
      if (var4 == null) {
         return null;
      } else {
         String var1 = var4.trim();
         var4 = var1;
         if (!var1.equalsIgnoreCase("7bit")) {
            var4 = var1;
            if (!var1.equalsIgnoreCase("8bit")) {
               var4 = var1;
               if (!var1.equalsIgnoreCase("quoted-printable")) {
                  var4 = var1;
                  if (!var1.equalsIgnoreCase("binary")) {
                     if (var1.equalsIgnoreCase("base64")) {
                        var4 = var1;
                     } else {
                        HeaderTokenizer var2 = new HeaderTokenizer(var1, "()<>@,;:\\\"\t []/?=");

                        int var3;
                        HeaderTokenizer.Token var5;
                        do {
                           var5 = var2.next();
                           var3 = var5.getType();
                           if (var3 == -4) {
                              return var1;
                           }
                        } while(var3 != -1);

                        var4 = var5.getValue();
                     }
                  }
               }
            }
         }

         return var4;
      }
   }

   static String getFileName(MimePart var0) throws MessagingException {
      String var1 = var0.getHeader("Content-Disposition", (String)null);
      if (var1 != null) {
         var1 = (new ContentDisposition(var1)).getParameter("filename");
      } else {
         var1 = null;
      }

      String var2 = var1;
      String var5;
      if (var1 == null) {
         var5 = var0.getHeader("Content-Type", (String)null);
         var2 = var1;
         if (var5 != null) {
            try {
               ContentType var6 = new ContentType(var5);
               var2 = var6.getParameter("name");
            } catch (ParseException var4) {
               var2 = var1;
            }
         }
      }

      var5 = var2;
      if (decodeFileName) {
         var5 = var2;
         if (var2 != null) {
            try {
               var5 = MimeUtility.decodeText(var2);
            } catch (UnsupportedEncodingException var3) {
               throw new MessagingException("Can't decode filename", var3);
            }
         }
      }

      return var5;
   }

   static void invalidateContentHeaders(MimePart var0) throws MessagingException {
      var0.removeHeader("Content-Type");
      var0.removeHeader("Content-Transfer-Encoding");
   }

   static boolean isMimeType(MimePart var0, String var1) throws MessagingException {
      try {
         ContentType var2 = new ContentType(var0.getContentType());
         boolean var3 = var2.match(var1);
         return var3;
      } catch (ParseException var4) {
         return var0.getContentType().equalsIgnoreCase(var1);
      }
   }

   static void setContentLanguage(MimePart var0, String[] var1) throws MessagingException {
      StringBuffer var2 = new StringBuffer(var1[0]);

      for(int var3 = 1; var3 < var1.length; ++var3) {
         var2.append(',');
         var2.append(var1[var3]);
      }

      var0.setHeader("Content-Language", var2.toString());
   }

   static void setDescription(MimePart var0, String var1, String var2) throws MessagingException {
      if (var1 == null) {
         var0.removeHeader("Content-Description");
      } else {
         try {
            var0.setHeader("Content-Description", MimeUtility.fold(21, MimeUtility.encodeText(var1, var2, (String)null)));
         } catch (UnsupportedEncodingException var3) {
            throw new MessagingException("Encoding error", var3);
         }
      }
   }

   static void setDisposition(MimePart var0, String var1) throws MessagingException {
      if (var1 == null) {
         var0.removeHeader("Content-Disposition");
      } else {
         String var2 = var0.getHeader("Content-Disposition", (String)null);
         String var3 = var1;
         if (var2 != null) {
            ContentDisposition var4 = new ContentDisposition(var2);
            var4.setDisposition(var1);
            var3 = var4.toString();
         }

         var0.setHeader("Content-Disposition", var3);
      }

   }

   static void setEncoding(MimePart var0, String var1) throws MessagingException {
      var0.setHeader("Content-Transfer-Encoding", var1);
   }

   static void setFileName(MimePart var0, String var1) throws MessagingException {
      String var2 = var1;
      if (encodeFileName) {
         var2 = var1;
         if (var1 != null) {
            try {
               var2 = MimeUtility.encodeText(var1);
            } catch (UnsupportedEncodingException var5) {
               throw new MessagingException("Can't encode filename", var5);
            }
         }
      }

      String var3 = var0.getHeader("Content-Disposition", (String)null);
      var1 = var3;
      if (var3 == null) {
         var1 = "attachment";
      }

      ContentDisposition var6 = new ContentDisposition(var1);
      var6.setParameter("filename", var2);
      var0.setHeader("Content-Disposition", var6.toString());
      if (setContentTypeFileName) {
         var3 = var0.getHeader("Content-Type", (String)null);
         if (var3 != null) {
            try {
               ContentType var7 = new ContentType(var3);
               var7.setParameter("name", var2);
               var0.setHeader("Content-Type", var7.toString());
            } catch (ParseException var4) {
            }
         }
      }

   }

   static void setText(MimePart var0, String var1, String var2, String var3) throws MessagingException {
      String var4 = var2;
      if (var2 == null) {
         if (MimeUtility.checkAscii(var1) != 1) {
            var4 = MimeUtility.getDefaultMIMECharset();
         } else {
            var4 = "us-ascii";
         }
      }

      StringBuilder var5 = new StringBuilder("text/");
      var5.append(var3);
      var5.append("; charset=");
      var5.append(MimeUtility.quote(var4, "()<>@,;:\\\"\t []/?="));
      var0.setContent(var1, var5.toString());
   }

   static void updateHeaders(MimePart var0) throws MessagingException {
      DataHandler var1 = var0.getDataHandler();
      if (var1 != null) {
         IOException var10000;
         label199: {
            String var2;
            String[] var3;
            boolean var10001;
            try {
               var2 = var1.getContentType();
               var3 = var0.getHeader("Content-Type");
            } catch (IOException var26) {
               var10000 = var26;
               var10001 = false;
               break label199;
            }

            boolean var4 = false;
            boolean var5;
            if (var3 == null) {
               var5 = true;
            } else {
               var5 = false;
            }

            ContentType var6;
            label189: {
               Object var32;
               label188: {
                  label187: {
                     label186: {
                        label200: {
                           label212: {
                              try {
                                 var6 = new ContentType(var2);
                                 if (!var6.match("multipart/*")) {
                                    break label186;
                                 }

                                 if (var0 instanceof MimeBodyPart) {
                                    MimeBodyPart var31 = (MimeBodyPart)var0;
                                    if (var31.cachedContent == null) {
                                       break label212;
                                    }

                                    var32 = var31.cachedContent;
                                    break label200;
                                 }
                              } catch (IOException var25) {
                                 var10000 = var25;
                                 var10001 = false;
                                 break label199;
                              }

                              label203: {
                                 try {
                                    if (!(var0 instanceof MimeMessage)) {
                                       break label203;
                                    }

                                    MimeMessage var33 = (MimeMessage)var0;
                                    if (var33.cachedContent != null) {
                                       var32 = var33.cachedContent;
                                       break label200;
                                    }
                                 } catch (IOException var24) {
                                    var10000 = var24;
                                    var10001 = false;
                                    break label199;
                                 }

                                 try {
                                    var32 = var1.getContent();
                                    break label200;
                                 } catch (IOException var20) {
                                    var10000 = var20;
                                    var10001 = false;
                                    break label199;
                                 }
                              }

                              try {
                                 var32 = var1.getContent();
                                 break label200;
                              } catch (IOException var19) {
                                 var10000 = var19;
                                 var10001 = false;
                                 break label199;
                              }
                           }

                           try {
                              var32 = var1.getContent();
                           } catch (IOException var21) {
                              var10000 = var21;
                              var10001 = false;
                              break label199;
                           }
                        }

                        try {
                           if (!(var32 instanceof MimeMultipart)) {
                              break label188;
                           }

                           ((MimeMultipart)var32).updateHeaders();
                           break label187;
                        } catch (IOException var23) {
                           var10000 = var23;
                           var10001 = false;
                           break label199;
                        }
                     }

                     try {
                        if (!var6.match("message/rfc822")) {
                           break label189;
                        }
                     } catch (IOException var22) {
                        var10000 = var22;
                        var10001 = false;
                        break label199;
                     }
                  }

                  var4 = true;
                  break label189;
               }

               try {
                  StringBuilder var27 = new StringBuilder("MIME part of type \"");
                  var27.append(var2);
                  var27.append("\" contains object of type ");
                  var27.append(var32.getClass().getName());
                  var27.append(" instead of MimeMultipart");
                  MessagingException var35 = new MessagingException(var27.toString());
                  throw var35;
               } catch (IOException var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label199;
               }
            }

            String var34 = var2;
            if (!var4) {
               try {
                  if (var0.getHeader("Content-Transfer-Encoding") == null) {
                     setEncoding(var0, MimeUtility.getEncoding(var1));
                  }
               } catch (IOException var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label199;
               }

               var34 = var2;
               if (var5) {
                  label205: {
                     var34 = var2;

                     try {
                        if (!setDefaultTextCharset) {
                           break label205;
                        }
                     } catch (IOException var18) {
                        var10000 = var18;
                        var10001 = false;
                        break label199;
                     }

                     var34 = var2;

                     try {
                        if (!var6.match("text/*")) {
                           break label205;
                        }
                     } catch (IOException var17) {
                        var10000 = var17;
                        var10001 = false;
                        break label199;
                     }

                     var34 = var2;

                     try {
                        if (var6.getParameter("charset") != null) {
                           break label205;
                        }

                        var34 = var0.getEncoding();
                     } catch (IOException var16) {
                        var10000 = var16;
                        var10001 = false;
                        break label199;
                     }

                     label211: {
                        if (var34 != null) {
                           label210: {
                              try {
                                 if (!var34.equalsIgnoreCase("7bit")) {
                                    break label210;
                                 }
                              } catch (IOException var15) {
                                 var10000 = var15;
                                 var10001 = false;
                                 break label199;
                              }

                              var34 = "us-ascii";
                              break label211;
                           }
                        }

                        try {
                           var34 = MimeUtility.getDefaultMIMECharset();
                        } catch (IOException var13) {
                           var10000 = var13;
                           var10001 = false;
                           break label199;
                        }
                     }

                     try {
                        var6.setParameter("charset", var34);
                        var34 = var6.toString();
                     } catch (IOException var12) {
                        var10000 = var12;
                        var10001 = false;
                        break label199;
                     }
                  }
               }
            }

            if (var5) {
               String var29;
               try {
                  var29 = var0.getHeader("Content-Disposition", (String)null);
               } catch (IOException var11) {
                  var10000 = var11;
                  var10001 = false;
                  break label199;
               }

               var2 = var34;
               if (var29 != null) {
                  try {
                     ContentDisposition var30 = new ContentDisposition(var29);
                     var29 = var30.getParameter("filename");
                  } catch (IOException var10) {
                     var10000 = var10;
                     var10001 = false;
                     break label199;
                  }

                  var2 = var34;
                  if (var29 != null) {
                     try {
                        var6.setParameter("name", var29);
                        var2 = var6.toString();
                     } catch (IOException var9) {
                        var10000 = var9;
                        var10001 = false;
                        break label199;
                     }
                  }
               }

               try {
                  var0.setHeader("Content-Type", var2);
               } catch (IOException var8) {
                  var10000 = var8;
                  var10001 = false;
                  break label199;
               }
            }

            return;
         }

         IOException var28 = var10000;
         throw new MessagingException("IOException updating headers", var28);
      }
   }

   static void writeTo(MimePart var0, OutputStream var1, String[] var2) throws IOException, MessagingException {
      LineOutputStream var3;
      if (var1 instanceof LineOutputStream) {
         var3 = (LineOutputStream)var1;
      } else {
         var3 = new LineOutputStream(var1);
      }

      Enumeration var4 = var0.getNonMatchingHeaderLines(var2);

      while(var4.hasMoreElements()) {
         var3.writeln((String)var4.nextElement());
      }

      var3.writeln();
      var1 = MimeUtility.encode(var1, var0.getEncoding());
      var0.getDataHandler().writeTo(var1);
      var1.flush();
   }

   public void addHeader(String var1, String var2) throws MessagingException {
      this.headers.addHeader(var1, var2);
   }

   public void addHeaderLine(String var1) throws MessagingException {
      this.headers.addHeaderLine(var1);
   }

   public void attachFile(File var1) throws IOException, MessagingException {
      FileDataSource var2 = new FileDataSource(var1);
      this.setDataHandler(new DataHandler(var2));
      this.setFileName(var2.getName());
   }

   public void attachFile(String var1) throws IOException, MessagingException {
      this.attachFile(new File(var1));
   }

   public Enumeration getAllHeaderLines() throws MessagingException {
      return this.headers.getAllHeaderLines();
   }

   public Enumeration getAllHeaders() throws MessagingException {
      return this.headers.getAllHeaders();
   }

   public Object getContent() throws IOException, MessagingException {
      Object var1 = this.cachedContent;
      if (var1 != null) {
         return var1;
      } else {
         try {
            var1 = this.getDataHandler().getContent();
         } catch (FolderClosedIOException var2) {
            throw new FolderClosedException(var2.getFolder(), var2.getMessage());
         } catch (MessageRemovedIOException var3) {
            throw new MessageRemovedException(var3.getMessage());
         }

         if (cacheMultipart && (var1 instanceof Multipart || var1 instanceof Message) && (this.content != null || this.contentStream != null)) {
            this.cachedContent = var1;
         }

         return var1;
      }
   }

   public String getContentID() throws MessagingException {
      return this.getHeader("Content-Id", (String)null);
   }

   public String[] getContentLanguage() throws MessagingException {
      return getContentLanguage(this);
   }

   public String getContentMD5() throws MessagingException {
      return this.getHeader("Content-MD5", (String)null);
   }

   protected InputStream getContentStream() throws MessagingException {
      InputStream var1 = this.contentStream;
      if (var1 != null) {
         return ((SharedInputStream)var1).newStream(0L, -1L);
      } else if (this.content != null) {
         return new ByteArrayInputStream(this.content);
      } else {
         throw new MessagingException("No content");
      }
   }

   public String getContentType() throws MessagingException {
      String var1 = this.getHeader("Content-Type", (String)null);
      String var2 = var1;
      if (var1 == null) {
         var2 = "text/plain";
      }

      return var2;
   }

   public DataHandler getDataHandler() throws MessagingException {
      if (this.dh == null) {
         this.dh = new DataHandler(new MimePartDataSource(this));
      }

      return this.dh;
   }

   public String getDescription() throws MessagingException {
      return getDescription(this);
   }

   public String getDisposition() throws MessagingException {
      return getDisposition(this);
   }

   public String getEncoding() throws MessagingException {
      return getEncoding(this);
   }

   public String getFileName() throws MessagingException {
      return getFileName(this);
   }

   public String getHeader(String var1, String var2) throws MessagingException {
      return this.headers.getHeader(var1, var2);
   }

   public String[] getHeader(String var1) throws MessagingException {
      return this.headers.getHeader(var1);
   }

   public InputStream getInputStream() throws IOException, MessagingException {
      return this.getDataHandler().getInputStream();
   }

   public int getLineCount() throws MessagingException {
      return -1;
   }

   public Enumeration getMatchingHeaderLines(String[] var1) throws MessagingException {
      return this.headers.getMatchingHeaderLines(var1);
   }

   public Enumeration getMatchingHeaders(String[] var1) throws MessagingException {
      return this.headers.getMatchingHeaders(var1);
   }

   public Enumeration getNonMatchingHeaderLines(String[] var1) throws MessagingException {
      return this.headers.getNonMatchingHeaderLines(var1);
   }

   public Enumeration getNonMatchingHeaders(String[] var1) throws MessagingException {
      return this.headers.getNonMatchingHeaders(var1);
   }

   public InputStream getRawInputStream() throws MessagingException {
      return this.getContentStream();
   }

   public int getSize() throws MessagingException {
      byte[] var1 = this.content;
      if (var1 != null) {
         return var1.length;
      } else {
         InputStream var4 = this.contentStream;
         if (var4 != null) {
            int var2;
            try {
               var2 = var4.available();
            } catch (IOException var3) {
               return -1;
            }

            if (var2 > 0) {
               return var2;
            }
         }

         return -1;
      }
   }

   public boolean isMimeType(String var1) throws MessagingException {
      return isMimeType(this, var1);
   }

   public void removeHeader(String var1) throws MessagingException {
      this.headers.removeHeader(var1);
   }

   public void saveFile(File var1) throws IOException, MessagingException {
      InputStream var2 = null;
      Throwable var3 = null;

      BufferedOutputStream var4;
      try {
         FileOutputStream var5 = new FileOutputStream(var1);
         var4 = new BufferedOutputStream(var5);
      } finally {
         ;
      }

      InputStream var61 = var3;

      Throwable var10000;
      label452: {
         boolean var10001;
         try {
            var2 = this.getInputStream();
         } catch (Throwable var60) {
            var10000 = var60;
            var10001 = false;
            break label452;
         }

         var61 = var2;

         byte[] var62;
         try {
            var62 = new byte[8192];
         } catch (Throwable var59) {
            var10000 = var59;
            var10001 = false;
            break label452;
         }

         while(true) {
            var61 = var2;

            int var6;
            try {
               var6 = var2.read(var62);
            } catch (Throwable var58) {
               var10000 = var58;
               var10001 = false;
               break;
            }

            if (var6 <= 0) {
               if (var2 != null) {
                  try {
                     var2.close();
                  } catch (IOException var55) {
                  }
               }

               try {
                  var4.close();
               } catch (IOException var54) {
               }

               return;
            }

            var61 = var2;

            try {
               var4.write(var62, 0, var6);
            } catch (Throwable var57) {
               var10000 = var57;
               var10001 = false;
               break;
            }
         }
      }

      var3 = var10000;
      var2 = var61;
      if (var61 != null) {
         try {
            var2.close();
         } catch (IOException var53) {
         }
      }

      if (var4 != null) {
         try {
            var4.close();
         } catch (IOException var52) {
         }
      }

      throw var3;
   }

   public void saveFile(String var1) throws IOException, MessagingException {
      this.saveFile(new File(var1));
   }

   public void setContent(Object var1, String var2) throws MessagingException {
      if (var1 instanceof Multipart) {
         this.setContent((Multipart)var1);
      } else {
         this.setDataHandler(new DataHandler(var1, var2));
      }

   }

   public void setContent(Multipart var1) throws MessagingException {
      this.setDataHandler(new DataHandler(var1, var1.getContentType()));
      var1.setParent(this);
   }

   public void setContentID(String var1) throws MessagingException {
      if (var1 == null) {
         this.removeHeader("Content-ID");
      } else {
         this.setHeader("Content-ID", var1);
      }

   }

   public void setContentLanguage(String[] var1) throws MessagingException {
      setContentLanguage(this, var1);
   }

   public void setContentMD5(String var1) throws MessagingException {
      this.setHeader("Content-MD5", var1);
   }

   public void setDataHandler(DataHandler var1) throws MessagingException {
      this.dh = var1;
      this.cachedContent = null;
      invalidateContentHeaders(this);
   }

   public void setDescription(String var1) throws MessagingException {
      this.setDescription(var1, (String)null);
   }

   public void setDescription(String var1, String var2) throws MessagingException {
      setDescription(this, var1, var2);
   }

   public void setDisposition(String var1) throws MessagingException {
      setDisposition(this, var1);
   }

   public void setFileName(String var1) throws MessagingException {
      setFileName(this, var1);
   }

   public void setHeader(String var1, String var2) throws MessagingException {
      this.headers.setHeader(var1, var2);
   }

   public void setText(String var1) throws MessagingException {
      this.setText(var1, (String)null);
   }

   public void setText(String var1, String var2) throws MessagingException {
      setText(this, var1, var2, "plain");
   }

   public void setText(String var1, String var2, String var3) throws MessagingException {
      setText(this, var1, var2, var3);
   }

   protected void updateHeaders() throws MessagingException {
      updateHeaders(this);
      if (this.cachedContent != null) {
         this.dh = new DataHandler(this.cachedContent, this.getContentType());
         this.cachedContent = null;
         this.content = null;
         InputStream var1 = this.contentStream;
         if (var1 != null) {
            try {
               var1.close();
            } catch (IOException var2) {
            }
         }

         this.contentStream = null;
      }

   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      writeTo(this, var1, (String[])null);
   }
}
