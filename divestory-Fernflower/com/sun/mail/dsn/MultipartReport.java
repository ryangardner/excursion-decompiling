package com.sun.mail.dsn;

import java.util.Vector;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MultipartReport extends MimeMultipart {
   protected boolean constructed;

   public MultipartReport() throws MessagingException {
      super("report");
      this.setBodyPart(new MimeBodyPart(), 0);
      this.setBodyPart(new MimeBodyPart(), 1);
      this.constructed = true;
   }

   public MultipartReport(String var1, DeliveryStatus var2) throws MessagingException {
      super("report");
      ContentType var3 = new ContentType(this.contentType);
      var3.setParameter("report-type", "delivery-status");
      this.contentType = var3.toString();
      MimeBodyPart var5 = new MimeBodyPart();
      var5.setText(var1);
      this.setBodyPart(var5, 0);
      MimeBodyPart var4 = new MimeBodyPart();
      var4.setContent(var2, "message/delivery-status");
      this.setBodyPart(var4, 1);
      this.constructed = true;
   }

   public MultipartReport(String var1, DeliveryStatus var2, InternetHeaders var3) throws MessagingException {
      this(var1, var2);
      if (var3 != null) {
         MimeBodyPart var4 = new MimeBodyPart();
         var4.setContent(new MessageHeaders(var3), "text/rfc822-headers");
         this.setBodyPart(var4, 2);
      }

   }

   public MultipartReport(String var1, DeliveryStatus var2, MimeMessage var3) throws MessagingException {
      this(var1, var2);
      if (var3 != null) {
         MimeBodyPart var4 = new MimeBodyPart();
         var4.setContent(var3, "message/rfc822");
         this.setBodyPart(var4, 2);
      }

   }

   public MultipartReport(DataSource var1) throws MessagingException {
      super(var1);
      this.parse();
      this.constructed = true;
   }

   private void setBodyPart(BodyPart var1, int var2) throws MessagingException {
      synchronized(this){}

      try {
         if (this.parts == null) {
            Vector var3 = new Vector();
            this.parts = var3;
         }

         if (var2 < this.parts.size()) {
            super.removeBodyPart(var2);
         }

         super.addBodyPart(var1, var2);
      } finally {
         ;
      }

   }

   public void addBodyPart(BodyPart var1) throws MessagingException {
      synchronized(this){}

      try {
         if (this.constructed) {
            MessagingException var4 = new MessagingException("Can't add body parts to multipart/report 1");
            throw var4;
         }

         super.addBodyPart(var1);
      } finally {
         ;
      }

   }

   public void addBodyPart(BodyPart var1, int var2) throws MessagingException {
      synchronized(this){}

      try {
         MessagingException var5 = new MessagingException("Can't add body parts to multipart/report 2");
         throw var5;
      } finally {
         ;
      }
   }

   public DeliveryStatus getDeliveryStatus() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public MimeMessage getReturnedMessage() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public String getText() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public MimeBodyPart getTextBodyPart() throws MessagingException {
      synchronized(this){}

      MimeBodyPart var1;
      try {
         var1 = (MimeBodyPart)this.getBodyPart(0);
      } finally {
         ;
      }

      return var1;
   }

   public void removeBodyPart(int var1) throws MessagingException {
      throw new MessagingException("Can't remove body parts from multipart/report");
   }

   public boolean removeBodyPart(BodyPart var1) throws MessagingException {
      throw new MessagingException("Can't remove body parts from multipart/report");
   }

   public void setDeliveryStatus(DeliveryStatus var1) throws MessagingException {
      synchronized(this){}

      try {
         MimeBodyPart var2 = new MimeBodyPart();
         var2.setContent(var1, "message/delivery-status");
         this.setBodyPart(var2, 2);
         ContentType var5 = new ContentType(this.contentType);
         var5.setParameter("report-type", "delivery-status");
         this.contentType = var5.toString();
      } finally {
         ;
      }

   }

   public void setReturnedMessage(MimeMessage var1) throws MessagingException {
      synchronized(this){}
      Throwable var10000;
      boolean var10001;
      if (var1 == null) {
         label172: {
            try {
               BodyPart var23 = (BodyPart)this.parts.elementAt(2);
               super.removeBodyPart(2);
            } catch (Throwable var19) {
               var10000 = var19;
               var10001 = false;
               break label172;
            }

            return;
         }
      } else {
         label188: {
            MimeBodyPart var2;
            label189: {
               try {
                  var2 = new MimeBodyPart();
                  if (var1 instanceof MessageHeaders) {
                     var2.setContent(var1, "text/rfc822-headers");
                     break label189;
                  }
               } catch (Throwable var22) {
                  var10000 = var22;
                  var10001 = false;
                  break label188;
               }

               try {
                  var2.setContent(var1, "message/rfc822");
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label188;
               }
            }

            try {
               this.setBodyPart(var2, 2);
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               break label188;
            }

            return;
         }
      }

      Throwable var24 = var10000;
      throw var24;
   }

   public void setSubType(String var1) throws MessagingException {
      synchronized(this){}

      try {
         MessagingException var4 = new MessagingException("Can't change subtype of MultipartReport");
         throw var4;
      } finally {
         ;
      }
   }

   public void setText(String var1) throws MessagingException {
      synchronized(this){}

      try {
         MimeBodyPart var2 = new MimeBodyPart();
         var2.setText(var1);
         this.setBodyPart(var2, 0);
      } finally {
         ;
      }

   }

   public void setTextBodyPart(MimeBodyPart var1) throws MessagingException {
      synchronized(this){}

      try {
         this.setBodyPart(var1, 0);
      } finally {
         ;
      }

   }
}
