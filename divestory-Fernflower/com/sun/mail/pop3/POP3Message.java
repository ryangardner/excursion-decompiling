package com.sun.mail.pop3;

import java.io.InputStream;
import java.util.Enumeration;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.IllegalWriteException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class POP3Message extends MimeMessage {
   static final String UNKNOWN = "UNKNOWN";
   private POP3Folder folder;
   private int hdrSize = -1;
   private int msgSize = -1;
   String uid = "UNKNOWN";

   public POP3Message(Folder var1, int var2) throws MessagingException {
      super(var1, var2);
      this.folder = (POP3Folder)var1;
   }

   private void loadHeaders() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void addHeader(String var1, String var2) throws MessagingException {
      throw new IllegalWriteException("POP3 messages are read-only");
   }

   public void addHeaderLine(String var1) throws MessagingException {
      throw new IllegalWriteException("POP3 messages are read-only");
   }

   public Enumeration getAllHeaderLines() throws MessagingException {
      if (this.headers == null) {
         this.loadHeaders();
      }

      return this.headers.getAllHeaderLines();
   }

   public Enumeration getAllHeaders() throws MessagingException {
      if (this.headers == null) {
         this.loadHeaders();
      }

      return this.headers.getAllHeaders();
   }

   protected InputStream getContentStream() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public String getHeader(String var1, String var2) throws MessagingException {
      if (this.headers == null) {
         this.loadHeaders();
      }

      return this.headers.getHeader(var1, var2);
   }

   public String[] getHeader(String var1) throws MessagingException {
      if (this.headers == null) {
         this.loadHeaders();
      }

      return this.headers.getHeader(var1);
   }

   public Enumeration getMatchingHeaderLines(String[] var1) throws MessagingException {
      if (this.headers == null) {
         this.loadHeaders();
      }

      return this.headers.getMatchingHeaderLines(var1);
   }

   public Enumeration getMatchingHeaders(String[] var1) throws MessagingException {
      if (this.headers == null) {
         this.loadHeaders();
      }

      return this.headers.getMatchingHeaders(var1);
   }

   public Enumeration getNonMatchingHeaderLines(String[] var1) throws MessagingException {
      if (this.headers == null) {
         this.loadHeaders();
      }

      return this.headers.getNonMatchingHeaderLines(var1);
   }

   public Enumeration getNonMatchingHeaders(String[] var1) throws MessagingException {
      if (this.headers == null) {
         this.loadHeaders();
      }

      return this.headers.getNonMatchingHeaders(var1);
   }

   public int getSize() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void invalidate(boolean var1) {
      synchronized(this){}

      Throwable var10000;
      label75: {
         boolean var10001;
         try {
            this.content = null;
            this.contentStream = null;
            this.msgSize = -1;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label75;
         }

         if (!var1) {
            return;
         }

         label66:
         try {
            this.headers = null;
            this.hdrSize = -1;
            return;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label66;
         }
      }

      Throwable var2 = var10000;
      throw var2;
   }

   public void removeHeader(String var1) throws MessagingException {
      throw new IllegalWriteException("POP3 messages are read-only");
   }

   public void saveChanges() throws MessagingException {
      throw new IllegalWriteException("POP3 messages are read-only");
   }

   public void setFlags(Flags var1, boolean var2) throws MessagingException {
      Flags var3 = (Flags)this.flags.clone();
      super.setFlags(var1, var2);
      if (!this.flags.equals(var3)) {
         this.folder.notifyMessageChangedListeners(1, this);
      }

   }

   public void setHeader(String var1, String var2) throws MessagingException {
      throw new IllegalWriteException("POP3 messages are read-only");
   }

   public InputStream top(int param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }
}
