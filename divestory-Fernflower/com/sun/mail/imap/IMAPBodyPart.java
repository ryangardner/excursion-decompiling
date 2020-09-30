package com.sun.mail.imap;

import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.mail.IllegalWriteException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;

public class IMAPBodyPart extends MimeBodyPart {
   private BODYSTRUCTURE bs;
   private String description;
   private boolean headersLoaded = false;
   private IMAPMessage message;
   private String sectionId;
   private String type;

   protected IMAPBodyPart(BODYSTRUCTURE var1, String var2, IMAPMessage var3) {
      this.bs = var1;
      this.sectionId = var2;
      this.message = var3;
      this.type = (new ContentType(var1.type, var1.subtype, var1.cParams)).toString();
   }

   private void loadHeaders() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void addHeader(String var1, String var2) throws MessagingException {
      throw new IllegalWriteException("IMAPBodyPart is read-only");
   }

   public void addHeaderLine(String var1) throws MessagingException {
      throw new IllegalWriteException("IMAPBodyPart is read-only");
   }

   public Enumeration getAllHeaderLines() throws MessagingException {
      this.loadHeaders();
      return super.getAllHeaderLines();
   }

   public Enumeration getAllHeaders() throws MessagingException {
      this.loadHeaders();
      return super.getAllHeaders();
   }

   public String getContentID() throws MessagingException {
      return this.bs.id;
   }

   public String getContentMD5() throws MessagingException {
      return this.bs.md5;
   }

   protected InputStream getContentStream() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public String getContentType() throws MessagingException {
      return this.type;
   }

   public DataHandler getDataHandler() throws MessagingException {
      synchronized(this){}

      Throwable var10000;
      label157: {
         DataHandler var16;
         boolean var10001;
         label164: {
            label154:
            try {
               if (this.dh == null) {
                  if (!this.bs.isMulti()) {
                     break label154;
                  }

                  IMAPMultipartDataSource var2 = new IMAPMultipartDataSource(this, this.bs.bodies, this.sectionId, this.message);
                  DataHandler var1 = new DataHandler(var2);
                  this.dh = var1;
               }
               break label164;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label157;
            }

            try {
               if (this.bs.isNested() && this.message.isREV1()) {
                  IMAPNestedMessage var15 = new IMAPNestedMessage(this.message, this.bs.bodies[0], this.bs.envelope, this.sectionId);
                  var16 = new DataHandler(var15, this.type);
                  this.dh = var16;
               }
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label157;
            }
         }

         label145:
         try {
            var16 = super.getDataHandler();
            return var16;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            break label145;
         }
      }

      Throwable var17 = var10000;
      throw var17;
   }

   public String getDescription() throws MessagingException {
      String var1 = this.description;
      if (var1 != null) {
         return var1;
      } else if (this.bs.description == null) {
         return null;
      } else {
         try {
            this.description = MimeUtility.decodeText(this.bs.description);
         } catch (UnsupportedEncodingException var2) {
            this.description = this.bs.description;
         }

         return this.description;
      }
   }

   public String getDisposition() throws MessagingException {
      return this.bs.disposition;
   }

   public String getEncoding() throws MessagingException {
      return this.bs.encoding;
   }

   public String getFileName() throws MessagingException {
      String var1;
      if (this.bs.dParams != null) {
         var1 = this.bs.dParams.get("filename");
      } else {
         var1 = null;
      }

      String var2 = var1;
      if (var1 == null) {
         var2 = var1;
         if (this.bs.cParams != null) {
            var2 = this.bs.cParams.get("name");
         }
      }

      return var2;
   }

   public String[] getHeader(String var1) throws MessagingException {
      this.loadHeaders();
      return super.getHeader(var1);
   }

   public int getLineCount() throws MessagingException {
      return this.bs.lines;
   }

   public Enumeration getMatchingHeaderLines(String[] var1) throws MessagingException {
      this.loadHeaders();
      return super.getMatchingHeaderLines(var1);
   }

   public Enumeration getMatchingHeaders(String[] var1) throws MessagingException {
      this.loadHeaders();
      return super.getMatchingHeaders(var1);
   }

   public Enumeration getNonMatchingHeaderLines(String[] var1) throws MessagingException {
      this.loadHeaders();
      return super.getNonMatchingHeaderLines(var1);
   }

   public Enumeration getNonMatchingHeaders(String[] var1) throws MessagingException {
      this.loadHeaders();
      return super.getNonMatchingHeaders(var1);
   }

   public int getSize() throws MessagingException {
      return this.bs.size;
   }

   public void removeHeader(String var1) throws MessagingException {
      throw new IllegalWriteException("IMAPBodyPart is read-only");
   }

   public void setContent(Object var1, String var2) throws MessagingException {
      throw new IllegalWriteException("IMAPBodyPart is read-only");
   }

   public void setContent(Multipart var1) throws MessagingException {
      throw new IllegalWriteException("IMAPBodyPart is read-only");
   }

   public void setContentMD5(String var1) throws MessagingException {
      throw new IllegalWriteException("IMAPBodyPart is read-only");
   }

   public void setDataHandler(DataHandler var1) throws MessagingException {
      throw new IllegalWriteException("IMAPBodyPart is read-only");
   }

   public void setDescription(String var1, String var2) throws MessagingException {
      throw new IllegalWriteException("IMAPBodyPart is read-only");
   }

   public void setDisposition(String var1) throws MessagingException {
      throw new IllegalWriteException("IMAPBodyPart is read-only");
   }

   public void setFileName(String var1) throws MessagingException {
      throw new IllegalWriteException("IMAPBodyPart is read-only");
   }

   public void setHeader(String var1, String var2) throws MessagingException {
      throw new IllegalWriteException("IMAPBodyPart is read-only");
   }

   protected void updateHeaders() {
   }
}
