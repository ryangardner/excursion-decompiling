package javax.mail.internet;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.FolderClosedIOException;
import com.sun.mail.util.LineOutputStream;
import com.sun.mail.util.MessageRemovedIOException;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.util.SharedByteArrayInputStream;

public class MimeMessage extends Message implements MimePart {
   private static final Flags answeredFlag;
   private static MailDateFormat mailDateFormat = new MailDateFormat();
   Object cachedContent;
   protected byte[] content;
   protected InputStream contentStream;
   protected DataHandler dh;
   protected Flags flags;
   protected InternetHeaders headers;
   protected boolean modified;
   protected boolean saved;
   private boolean strict;

   static {
      answeredFlag = new Flags(Flags.Flag.ANSWERED);
   }

   protected MimeMessage(Folder var1, int var2) {
      super(var1, var2);
      this.modified = false;
      this.saved = false;
      this.strict = true;
      this.flags = new Flags();
      this.saved = true;
      this.initStrict();
   }

   protected MimeMessage(Folder var1, InputStream var2, int var3) throws MessagingException {
      this(var1, var3);
      this.initStrict();
      this.parse(var2);
   }

   protected MimeMessage(Folder var1, InternetHeaders var2, byte[] var3, int var4) throws MessagingException {
      this(var1, var4);
      this.headers = var2;
      this.content = var3;
      this.initStrict();
   }

   public MimeMessage(Session var1) {
      super(var1);
      this.modified = false;
      this.saved = false;
      this.strict = true;
      this.modified = true;
      this.headers = new InternetHeaders();
      this.flags = new Flags();
      this.initStrict();
   }

   public MimeMessage(Session var1, InputStream var2) throws MessagingException {
      super(var1);
      this.modified = false;
      this.saved = false;
      this.strict = true;
      this.flags = new Flags();
      this.initStrict();
      this.parse(var2);
      this.saved = true;
   }

   public MimeMessage(MimeMessage var1) throws MessagingException {
      super(var1.session);
      this.modified = false;
      this.saved = false;
      this.strict = true;
      this.flags = var1.getFlags();
      int var2 = var1.getSize();
      ByteArrayOutputStream var3;
      if (var2 > 0) {
         var3 = new ByteArrayOutputStream(var2);
      } else {
         var3 = new ByteArrayOutputStream();
      }

      try {
         this.strict = var1.strict;
         var1.writeTo(var3);
         var3.close();
         SharedByteArrayInputStream var5 = new SharedByteArrayInputStream(var3.toByteArray());
         this.parse(var5);
         var5.close();
         this.saved = true;
      } catch (IOException var4) {
         throw new MessagingException("IOException while copying message", var4);
      }
   }

   private void addAddressHeader(String var1, Address[] var2) throws MessagingException {
      String var3 = InternetAddress.toString(var2);
      if (var3 != null) {
         this.addHeader(var1, var3);
      }
   }

   private Address[] eliminateDuplicates(Vector var1, Address[] var2) {
      if (var2 == null) {
         return null;
      } else {
         byte var3 = 0;
         int var4 = 0;

         int var5;
         int var6;
         for(var5 = 0; var4 < var2.length; ++var4) {
            boolean var8;
            label52: {
               for(var6 = 0; var6 < var1.size(); ++var6) {
                  if (((InternetAddress)var1.elementAt(var6)).equals(var2[var4])) {
                     ++var5;
                     var2[var4] = null;
                     var8 = true;
                     break label52;
                  }
               }

               var8 = false;
            }

            if (!var8) {
               var1.addElement(var2[var4]);
            }
         }

         Object var7 = var2;
         if (var5 != 0) {
            if (var2 instanceof InternetAddress[]) {
               var7 = new InternetAddress[var2.length - var5];
            } else {
               var7 = new Address[var2.length - var5];
            }

            var5 = 0;

            for(var4 = var3; var4 < var2.length; var5 = var6) {
               var6 = var5;
               if (var2[var4] != null) {
                  ((Object[])var7)[var5] = var2[var4];
                  var6 = var5 + 1;
               }

               ++var4;
            }
         }

         return (Address[])var7;
      }
   }

   private Address[] getAddressHeader(String var1) throws MessagingException {
      var1 = this.getHeader(var1, ",");
      InternetAddress[] var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = InternetAddress.parseHeader(var1, this.strict);
      }

      return var2;
   }

   private String getHeaderName(Message.RecipientType var1) throws MessagingException {
      String var2;
      if (var1 == Message.RecipientType.TO) {
         var2 = "To";
      } else if (var1 == Message.RecipientType.CC) {
         var2 = "Cc";
      } else if (var1 == Message.RecipientType.BCC) {
         var2 = "Bcc";
      } else {
         if (var1 != MimeMessage.RecipientType.NEWSGROUPS) {
            throw new MessagingException("Invalid Recipient Type");
         }

         var2 = "Newsgroups";
      }

      return var2;
   }

   private void initStrict() {
      if (this.session != null) {
         String var1 = this.session.getProperty("mail.mime.address.strict");
         boolean var2;
         if (var1 != null && var1.equalsIgnoreCase("false")) {
            var2 = false;
         } else {
            var2 = true;
         }

         this.strict = var2;
      }

   }

   private void setAddressHeader(String var1, Address[] var2) throws MessagingException {
      String var3 = InternetAddress.toString(var2);
      if (var3 == null) {
         this.removeHeader(var1);
      } else {
         this.setHeader(var1, var3);
      }

   }

   public void addFrom(Address[] var1) throws MessagingException {
      this.addAddressHeader("From", var1);
   }

   public void addHeader(String var1, String var2) throws MessagingException {
      this.headers.addHeader(var1, var2);
   }

   public void addHeaderLine(String var1) throws MessagingException {
      this.headers.addHeaderLine(var1);
   }

   public void addRecipients(Message.RecipientType var1, String var2) throws MessagingException {
      if (var1 == MimeMessage.RecipientType.NEWSGROUPS) {
         if (var2 != null && var2.length() != 0) {
            this.addHeader("Newsgroups", var2);
         }
      } else {
         this.addAddressHeader(this.getHeaderName(var1), InternetAddress.parse(var2));
      }

   }

   public void addRecipients(Message.RecipientType var1, Address[] var2) throws MessagingException {
      if (var1 == MimeMessage.RecipientType.NEWSGROUPS) {
         String var3 = NewsAddress.toString(var2);
         if (var3 != null) {
            this.addHeader("Newsgroups", var3);
         }
      } else {
         this.addAddressHeader(this.getHeaderName(var1), var2);
      }

   }

   protected InternetHeaders createInternetHeaders(InputStream var1) throws MessagingException {
      return new InternetHeaders(var1);
   }

   protected MimeMessage createMimeMessage(Session var1) throws MessagingException {
      return new MimeMessage(var1);
   }

   public Enumeration getAllHeaderLines() throws MessagingException {
      return this.headers.getAllHeaderLines();
   }

   public Enumeration getAllHeaders() throws MessagingException {
      return this.headers.getAllHeaders();
   }

   public Address[] getAllRecipients() throws MessagingException {
      Address[] var1 = super.getAllRecipients();
      Address[] var2 = this.getRecipients(MimeMessage.RecipientType.NEWSGROUPS);
      if (var2 == null) {
         return var1;
      } else if (var1 == null) {
         return var2;
      } else {
         Address[] var3 = new Address[var1.length + var2.length];
         System.arraycopy(var1, 0, var3, 0, var1.length);
         System.arraycopy(var2, 0, var3, var1.length, var2.length);
         return var3;
      }
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

         if (MimeBodyPart.cacheMultipart && (var1 instanceof Multipart || var1 instanceof Message) && (this.content != null || this.contentStream != null)) {
            this.cachedContent = var1;
         }

         return var1;
      }
   }

   public String getContentID() throws MessagingException {
      return this.getHeader("Content-Id", (String)null);
   }

   public String[] getContentLanguage() throws MessagingException {
      return MimeBodyPart.getContentLanguage(this);
   }

   public String getContentMD5() throws MessagingException {
      return this.getHeader("Content-MD5", (String)null);
   }

   protected InputStream getContentStream() throws MessagingException {
      InputStream var1 = this.contentStream;
      if (var1 != null) {
         return ((SharedInputStream)var1).newStream(0L, -1L);
      } else if (this.content != null) {
         return new SharedByteArrayInputStream(this.content);
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
      synchronized(this){}

      DataHandler var5;
      try {
         if (this.dh == null) {
            MimePartDataSource var2 = new MimePartDataSource(this);
            DataHandler var1 = new DataHandler(var2);
            this.dh = var1;
         }

         var5 = this.dh;
      } finally {
         ;
      }

      return var5;
   }

   public String getDescription() throws MessagingException {
      return MimeBodyPart.getDescription(this);
   }

   public String getDisposition() throws MessagingException {
      return MimeBodyPart.getDisposition(this);
   }

   public String getEncoding() throws MessagingException {
      return MimeBodyPart.getEncoding(this);
   }

   public String getFileName() throws MessagingException {
      return MimeBodyPart.getFileName(this);
   }

   public Flags getFlags() throws MessagingException {
      synchronized(this){}

      Flags var1;
      try {
         var1 = (Flags)this.flags.clone();
      } finally {
         ;
      }

      return var1;
   }

   public Address[] getFrom() throws MessagingException {
      Address[] var1 = this.getAddressHeader("From");
      Address[] var2 = var1;
      if (var1 == null) {
         var2 = this.getAddressHeader("Sender");
      }

      return var2;
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

   public String getMessageID() throws MessagingException {
      return this.getHeader("Message-ID", (String)null);
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

   public Date getReceivedDate() throws MessagingException {
      return null;
   }

   public Address[] getRecipients(Message.RecipientType var1) throws MessagingException {
      if (var1 == MimeMessage.RecipientType.NEWSGROUPS) {
         String var2 = this.getHeader("Newsgroups", ",");
         NewsAddress[] var3;
         if (var2 == null) {
            var3 = null;
         } else {
            var3 = NewsAddress.parse(var2);
         }

         return var3;
      } else {
         return this.getAddressHeader(this.getHeaderName(var1));
      }
   }

   public Address[] getReplyTo() throws MessagingException {
      Address[] var1 = this.getAddressHeader("Reply-To");
      Address[] var2 = var1;
      if (var1 == null) {
         var2 = this.getFrom();
      }

      return var2;
   }

   public Address getSender() throws MessagingException {
      Address[] var1 = this.getAddressHeader("Sender");
      return var1 != null && var1.length != 0 ? var1[0] : null;
   }

   public Date getSentDate() throws MessagingException {
      // $FF: Couldn't be decompiled
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

   public String getSubject() throws MessagingException {
      String var1 = this.getHeader("Subject", (String)null);
      if (var1 == null) {
         return null;
      } else {
         String var2;
         try {
            var2 = MimeUtility.decodeText(MimeUtility.unfold(var1));
         } catch (UnsupportedEncodingException var3) {
            return var1;
         }

         var1 = var2;
         return var1;
      }
   }

   public boolean isMimeType(String var1) throws MessagingException {
      return MimeBodyPart.isMimeType(this, var1);
   }

   public boolean isSet(Flags.Flag var1) throws MessagingException {
      synchronized(this){}

      boolean var2;
      try {
         var2 = this.flags.contains(var1);
      } finally {
         ;
      }

      return var2;
   }

   protected void parse(InputStream var1) throws MessagingException {
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

      this.headers = this.createInternetHeaders((InputStream)var2);
      if (var2 instanceof SharedInputStream) {
         SharedInputStream var4 = (SharedInputStream)var2;
         this.contentStream = var4.newStream(var4.getPosition(), -1L);
      } else {
         try {
            this.content = ASCIIUtility.getBytes((InputStream)var2);
         } catch (IOException var3) {
            throw new MessagingException("IOException", var3);
         }
      }

      this.modified = false;
   }

   public void removeHeader(String var1) throws MessagingException {
      this.headers.removeHeader(var1);
   }

   public Message reply(boolean var1) throws MessagingException {
      MimeMessage var2 = this.createMimeMessage(this.session);
      String var3 = this.getHeader("Subject", (String)null);
      String var4;
      StringBuilder var10;
      if (var3 != null) {
         var4 = var3;
         if (!var3.regionMatches(true, 0, "Re: ", 0, 4)) {
            var10 = new StringBuilder("Re: ");
            var10.append(var3);
            var4 = var10.toString();
         }

         var2.setHeader("Subject", var4);
      }

      Address[] var5 = this.getReplyTo();
      var2.setRecipients(Message.RecipientType.TO, var5);
      if (var1) {
         Vector var9 = new Vector();
         InternetAddress var11 = InternetAddress.getLocalAddress(this.session);
         if (var11 != null) {
            var9.addElement(var11);
         }

         if (this.session != null) {
            var4 = this.session.getProperty("mail.alternates");
         } else {
            var4 = null;
         }

         boolean var6 = false;
         if (var4 != null) {
            this.eliminateDuplicates(var9, InternetAddress.parse(var4, false));
         }

         if (this.session != null) {
            var4 = this.session.getProperty("mail.replyallcc");
         } else {
            var4 = null;
         }

         boolean var7 = var6;
         if (var4 != null) {
            var7 = var6;
            if (var4.equalsIgnoreCase("true")) {
               var7 = true;
            }
         }

         this.eliminateDuplicates(var9, var5);
         Address[] var13 = this.eliminateDuplicates(var9, this.getRecipients(Message.RecipientType.TO));
         if (var13 != null && var13.length > 0) {
            if (var7) {
               var2.addRecipients(Message.RecipientType.CC, var13);
            } else {
               var2.addRecipients(Message.RecipientType.TO, var13);
            }
         }

         var13 = this.eliminateDuplicates(var9, this.getRecipients(Message.RecipientType.CC));
         if (var13 != null && var13.length > 0) {
            var2.addRecipients(Message.RecipientType.CC, var13);
         }

         var13 = this.getRecipients(MimeMessage.RecipientType.NEWSGROUPS);
         if (var13 != null && var13.length > 0) {
            var2.setRecipients(MimeMessage.RecipientType.NEWSGROUPS, (Address[])var13);
         }
      }

      String var12 = this.getHeader("Message-Id", (String)null);
      if (var12 != null) {
         var2.setHeader("In-Reply-To", var12);
      }

      var3 = this.getHeader("References", " ");
      var4 = var3;
      if (var3 == null) {
         var4 = this.getHeader("In-Reply-To", " ");
      }

      if (var12 != null) {
         var3 = var12;
         if (var4 != null) {
            var10 = new StringBuilder(String.valueOf(MimeUtility.unfold(var4)));
            var10.append(" ");
            var10.append(var12);
            var3 = var10.toString();
         }
      } else {
         var3 = var4;
      }

      if (var3 != null) {
         var2.setHeader("References", MimeUtility.fold(12, var3));
      }

      try {
         this.setFlags(answeredFlag, true);
      } catch (MessagingException var8) {
      }

      return var2;
   }

   public void saveChanges() throws MessagingException {
      this.modified = true;
      this.saved = true;
      this.updateHeaders();
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
      MimeBodyPart.setContentLanguage(this, var1);
   }

   public void setContentMD5(String var1) throws MessagingException {
      this.setHeader("Content-MD5", var1);
   }

   public void setDataHandler(DataHandler var1) throws MessagingException {
      synchronized(this){}

      try {
         this.dh = var1;
         this.cachedContent = null;
         MimeBodyPart.invalidateContentHeaders(this);
      } finally {
         ;
      }

   }

   public void setDescription(String var1) throws MessagingException {
      this.setDescription(var1, (String)null);
   }

   public void setDescription(String var1, String var2) throws MessagingException {
      MimeBodyPart.setDescription(this, var1, var2);
   }

   public void setDisposition(String var1) throws MessagingException {
      MimeBodyPart.setDisposition(this, var1);
   }

   public void setFileName(String var1) throws MessagingException {
      MimeBodyPart.setFileName(this, var1);
   }

   public void setFlags(Flags var1, boolean var2) throws MessagingException {
      synchronized(this){}
      Throwable var10000;
      boolean var10001;
      if (var2) {
         label53:
         try {
            this.flags.add(var1);
            return;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label53;
         }
      } else {
         label55:
         try {
            this.flags.remove(var1);
            return;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label55;
         }
      }

      Throwable var9 = var10000;
      throw var9;
   }

   public void setFrom() throws MessagingException {
      InternetAddress var1 = InternetAddress.getLocalAddress(this.session);
      if (var1 != null) {
         this.setFrom(var1);
      } else {
         throw new MessagingException("No From address");
      }
   }

   public void setFrom(Address var1) throws MessagingException {
      if (var1 == null) {
         this.removeHeader("From");
      } else {
         this.setHeader("From", var1.toString());
      }

   }

   public void setHeader(String var1, String var2) throws MessagingException {
      this.headers.setHeader(var1, var2);
   }

   public void setRecipients(Message.RecipientType var1, String var2) throws MessagingException {
      if (var1 == MimeMessage.RecipientType.NEWSGROUPS) {
         if (var2 != null && var2.length() != 0) {
            this.setHeader("Newsgroups", var2);
         } else {
            this.removeHeader("Newsgroups");
         }
      } else {
         this.setAddressHeader(this.getHeaderName(var1), InternetAddress.parse(var2));
      }

   }

   public void setRecipients(Message.RecipientType var1, Address[] var2) throws MessagingException {
      if (var1 == MimeMessage.RecipientType.NEWSGROUPS) {
         if (var2 != null && var2.length != 0) {
            this.setHeader("Newsgroups", NewsAddress.toString(var2));
         } else {
            this.removeHeader("Newsgroups");
         }
      } else {
         this.setAddressHeader(this.getHeaderName(var1), var2);
      }

   }

   public void setReplyTo(Address[] var1) throws MessagingException {
      this.setAddressHeader("Reply-To", var1);
   }

   public void setSender(Address var1) throws MessagingException {
      if (var1 == null) {
         this.removeHeader("Sender");
      } else {
         this.setHeader("Sender", var1.toString());
      }

   }

   public void setSentDate(Date param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void setSubject(String var1) throws MessagingException {
      this.setSubject(var1, (String)null);
   }

   public void setSubject(String var1, String var2) throws MessagingException {
      if (var1 == null) {
         this.removeHeader("Subject");
      } else {
         try {
            this.setHeader("Subject", MimeUtility.fold(9, MimeUtility.encodeText(var1, var2, (String)null)));
         } catch (UnsupportedEncodingException var3) {
            throw new MessagingException("Encoding error", var3);
         }
      }

   }

   public void setText(String var1) throws MessagingException {
      this.setText(var1, (String)null);
   }

   public void setText(String var1, String var2) throws MessagingException {
      MimeBodyPart.setText(this, var1, var2, "plain");
   }

   public void setText(String var1, String var2, String var3) throws MessagingException {
      MimeBodyPart.setText(this, var1, var2, var3);
   }

   protected void updateHeaders() throws MessagingException {
      MimeBodyPart.updateHeaders(this);
      this.setHeader("MIME-Version", "1.0");
      this.updateMessageID();
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

   protected void updateMessageID() throws MessagingException {
      StringBuilder var1 = new StringBuilder("<");
      var1.append(UniqueValue.getUniqueMessageIDValue(this.session));
      var1.append(">");
      this.setHeader("Message-ID", var1.toString());
   }

   public void writeTo(OutputStream var1) throws IOException, MessagingException {
      this.writeTo(var1, (String[])null);
   }

   public void writeTo(OutputStream var1, String[] var2) throws IOException, MessagingException {
      if (!this.saved) {
         this.saveChanges();
      }

      if (this.modified) {
         MimeBodyPart.writeTo(this, var1, var2);
      } else {
         Enumeration var3 = this.getNonMatchingHeaderLines(var2);
         LineOutputStream var5 = new LineOutputStream(var1);

         while(var3.hasMoreElements()) {
            var5.writeln((String)var3.nextElement());
         }

         var5.writeln();
         byte[] var6 = this.content;
         if (var6 == null) {
            InputStream var7 = this.getContentStream();
            var6 = new byte[8192];

            while(true) {
               int var4 = var7.read(var6);
               if (var4 <= 0) {
                  var7.close();
                  var6 = (byte[])null;
                  break;
               }

               var1.write(var6, 0, var4);
            }
         } else {
            var1.write(var6);
         }

         var1.flush();
      }
   }

   public static class RecipientType extends Message.RecipientType {
      public static final MimeMessage.RecipientType NEWSGROUPS = new MimeMessage.RecipientType("Newsgroups");
      private static final long serialVersionUID = -5468290701714395543L;

      protected RecipientType(String var1) {
         super(var1);
      }

      protected Object readResolve() throws ObjectStreamException {
         return this.type.equals("Newsgroups") ? NEWSGROUPS : super.readResolve();
      }
   }
}
