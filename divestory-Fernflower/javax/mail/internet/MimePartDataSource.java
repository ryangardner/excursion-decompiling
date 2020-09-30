package javax.mail.internet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownServiceException;
import javax.activation.DataSource;
import javax.mail.MessageAware;
import javax.mail.MessageContext;
import javax.mail.MessagingException;

public class MimePartDataSource implements DataSource, MessageAware {
   private static boolean ignoreMultipartEncoding;
   private MessageContext context;
   protected MimePart part;

   static {
      label36: {
         String var0;
         boolean var10001;
         try {
            var0 = System.getProperty("mail.mime.ignoremultipartencoding");
         } catch (SecurityException var4) {
            var10001 = false;
            break label36;
         }

         boolean var1;
         label29: {
            label28: {
               if (var0 != null) {
                  try {
                     if (var0.equalsIgnoreCase("false")) {
                        break label28;
                     }
                  } catch (SecurityException var3) {
                     var10001 = false;
                     break label36;
                  }
               }

               var1 = true;
               break label29;
            }

            var1 = false;
         }

         try {
            ignoreMultipartEncoding = var1;
         } catch (SecurityException var2) {
            var10001 = false;
         }
      }

   }

   public MimePartDataSource(MimePart var1) {
      this.part = var1;
   }

   private static String restrictEncoding(String var0, MimePart var1) throws MessagingException {
      String var2 = var0;
      if (ignoreMultipartEncoding) {
         if (var0 == null) {
            var2 = var0;
         } else {
            var2 = var0;
            if (!var0.equalsIgnoreCase("7bit")) {
               var2 = var0;
               if (!var0.equalsIgnoreCase("8bit")) {
                  if (var0.equalsIgnoreCase("binary")) {
                     var2 = var0;
                  } else {
                     String var5 = var1.getContentType();
                     if (var5 == null) {
                        return var0;
                     }

                     label28: {
                        boolean var3;
                        try {
                           ContentType var6 = new ContentType(var5);
                           if (var6.match("multipart/*")) {
                              break label28;
                           }

                           var3 = var6.match("message/*");
                        } catch (ParseException var4) {
                           var2 = var0;
                           return var2;
                        }

                        var2 = var0;
                        if (!var3) {
                           return var2;
                        }
                     }

                     var2 = null;
                  }
               }
            }
         }
      }

      return var2;
   }

   public String getContentType() {
      try {
         String var1 = this.part.getContentType();
         return var1;
      } catch (MessagingException var2) {
         return "application/octet-stream";
      }
   }

   public InputStream getInputStream() throws IOException {
      MessagingException var10000;
      MessagingException var1;
      label54: {
         boolean var10001;
         InputStream var9;
         label53: {
            try {
               if (this.part instanceof MimeBodyPart) {
                  var9 = ((MimeBodyPart)this.part).getContentStream();
                  break label53;
               }
            } catch (MessagingException var8) {
               var10000 = var8;
               var10001 = false;
               break label54;
            }

            try {
               if (this.part instanceof MimeMessage) {
                  var9 = ((MimeMessage)this.part).getContentStream();
                  break label53;
               }
            } catch (MessagingException var7) {
               var10000 = var7;
               var10001 = false;
               break label54;
            }

            try {
               var1 = new MessagingException("Unknown part");
               throw var1;
            } catch (MessagingException var6) {
               var10000 = var6;
               var10001 = false;
               break label54;
            }
         }

         String var2;
         try {
            var2 = restrictEncoding(this.part.getEncoding(), this.part);
         } catch (MessagingException var5) {
            var10000 = var5;
            var10001 = false;
            break label54;
         }

         InputStream var3 = var9;
         if (var2 != null) {
            try {
               var3 = MimeUtility.decode(var9, var2);
            } catch (MessagingException var4) {
               var10000 = var4;
               var10001 = false;
               break label54;
            }
         }

         return var3;
      }

      var1 = var10000;
      throw new IOException(var1.getMessage());
   }

   public MessageContext getMessageContext() {
      synchronized(this){}

      MessageContext var1;
      try {
         if (this.context == null) {
            var1 = new MessageContext(this.part);
            this.context = var1;
         }

         var1 = this.context;
      } finally {
         ;
      }

      return var1;
   }

   public String getName() {
      try {
         if (this.part instanceof MimeBodyPart) {
            String var1 = ((MimeBodyPart)this.part).getFileName();
            return var1;
         }
      } catch (MessagingException var2) {
      }

      return "";
   }

   public OutputStream getOutputStream() throws IOException {
      throw new UnknownServiceException();
   }
}
