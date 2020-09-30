package com.sun.mail.dsn;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;

public class MessageHeaders extends MimeMessage {
   public MessageHeaders() throws MessagingException {
      super((Session)null);
      this.content = new byte[0];
   }

   public MessageHeaders(InputStream var1) throws MessagingException {
      super((Session)null, var1);
      this.content = new byte[0];
   }

   public MessageHeaders(InternetHeaders var1) throws MessagingException {
      super((Session)null);
      this.headers = var1;
      this.content = new byte[0];
   }

   protected InputStream getContentStream() {
      return new ByteArrayInputStream(this.content);
   }

   public InputStream getInputStream() {
      return new ByteArrayInputStream(this.content);
   }

   public int getSize() {
      return 0;
   }

   public void setDataHandler(DataHandler var1) throws MessagingException {
      throw new MessagingException("Can't set content for MessageHeaders");
   }
}
