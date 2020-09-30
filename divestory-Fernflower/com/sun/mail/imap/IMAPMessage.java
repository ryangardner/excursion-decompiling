package com.sun.mail.imap;

import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.IMAPProtocol;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.FolderClosedException;
import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class IMAPMessage extends MimeMessage {
   private static String EnvelopeCmd;
   protected BODYSTRUCTURE bs;
   private String description;
   protected ENVELOPE envelope;
   private boolean headersLoaded = false;
   private Hashtable loadedHeaders;
   private boolean peek;
   private Date receivedDate;
   protected String sectionId;
   private int seqnum;
   private int size = -1;
   private String subject;
   private String type;
   private long uid = -1L;

   protected IMAPMessage(IMAPFolder var1, int var2, int var3) {
      super(var1, var2);
      this.seqnum = var3;
      this.flags = null;
   }

   protected IMAPMessage(Session var1) {
      super(var1);
   }

   private BODYSTRUCTURE _getBodyStructure() {
      return this.bs;
   }

   private ENVELOPE _getEnvelope() {
      return this.envelope;
   }

   private Flags _getFlags() {
      return this.flags;
   }

   private InternetAddress[] aaclone(InternetAddress[] var1) {
      return var1 == null ? null : (InternetAddress[])var1.clone();
   }

   // $FF: synthetic method
   static ENVELOPE access$0(IMAPMessage var0) {
      return var0._getEnvelope();
   }

   // $FF: synthetic method
   static Flags access$1(IMAPMessage var0) {
      return var0._getFlags();
   }

   // $FF: synthetic method
   static BODYSTRUCTURE access$2(IMAPMessage var0) {
      return var0._getBodyStructure();
   }

   // $FF: synthetic method
   static boolean access$3(IMAPMessage var0) {
      return var0.areHeadersLoaded();
   }

   // $FF: synthetic method
   static int access$4(IMAPMessage var0) {
      return var0.size;
   }

   // $FF: synthetic method
   static boolean access$5(IMAPMessage var0, String var1) {
      return var0.isHeaderLoaded(var1);
   }

   private boolean areHeadersLoaded() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.headersLoaded;
      } finally {
         ;
      }

      return var1;
   }

   private static String craftHeaderCmd(IMAPProtocol var0, String[] var1) {
      StringBuffer var2;
      if (var0.isREV1()) {
         var2 = new StringBuffer("BODY.PEEK[HEADER.FIELDS (");
      } else {
         var2 = new StringBuffer("RFC822.HEADER.LINES (");
      }

      for(int var3 = 0; var3 < var1.length; ++var3) {
         if (var3 > 0) {
            var2.append(" ");
         }

         var2.append(var1[var3]);
      }

      if (var0.isREV1()) {
         var2.append(")]");
      } else {
         var2.append(")");
      }

      return var2.toString();
   }

   static void fetch(IMAPFolder param0, Message[] param1, FetchProfile param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private boolean isHeaderLoaded(String var1) {
      synchronized(this){}

      Throwable var10000;
      label104: {
         boolean var10001;
         boolean var2;
         try {
            var2 = this.headersLoaded;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label104;
         }

         if (var2) {
            return true;
         }

         try {
            if (this.loadedHeaders != null) {
               var2 = this.loadedHeaders.containsKey(var1.toUpperCase(Locale.ENGLISH));
               return var2;
            }
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label104;
         }

         var2 = false;
         return var2;
      }

      Throwable var9 = var10000;
      throw var9;
   }

   private void loadBODYSTRUCTURE() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void loadEnvelope() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void loadFlags() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void loadHeaders() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void setHeaderLoaded(String var1) {
      synchronized(this){}

      try {
         if (this.loadedHeaders == null) {
            Hashtable var2 = new Hashtable(1);
            this.loadedHeaders = var2;
         }

         this.loadedHeaders.put(var1.toUpperCase(Locale.ENGLISH), var1);
      } finally {
         ;
      }

   }

   private void setHeadersLoaded(boolean var1) {
      synchronized(this){}

      try {
         this.headersLoaded = var1;
      } finally {
         ;
      }

   }

   private String toSection(String var1) {
      if (this.sectionId == null) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder(String.valueOf(this.sectionId));
         var2.append(".");
         var2.append(var1);
         return var2.toString();
      }
   }

   Session _getSession() {
      return this.session;
   }

   void _setFlags(Flags var1) {
      this.flags = var1;
   }

   public void addFrom(Address[] var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void addHeader(String var1, String var2) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void addHeaderLine(String var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void addRecipients(Message.RecipientType var1, Address[] var2) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   protected void checkExpunged() throws MessageRemovedException {
      if (this.expunged) {
         throw new MessageRemovedException();
      }
   }

   protected void forceCheckExpunged() throws MessageRemovedException, FolderClosedException {
      Object var1 = this.getMessageCacheLock();
      synchronized(var1){}

      label237: {
         Throwable var10000;
         boolean var10001;
         label238: {
            label230: {
               ConnectionException var2;
               try {
                  try {
                     this.getProtocol().noop();
                     break label230;
                  } catch (ConnectionException var31) {
                     var2 = var31;
                  } catch (ProtocolException var32) {
                     break label230;
                  }
               } catch (Throwable var33) {
                  var10000 = var33;
                  var10001 = false;
                  break label238;
               }

               try {
                  FolderClosedException var3 = new FolderClosedException(this.folder, var2.getMessage());
                  throw var3;
               } catch (Throwable var29) {
                  var10000 = var29;
                  var10001 = false;
                  break label238;
               }
            }

            label223:
            try {
               break label237;
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break label223;
            }
         }

         while(true) {
            Throwable var34 = var10000;

            try {
               throw var34;
            } catch (Throwable var28) {
               var10000 = var28;
               var10001 = false;
               continue;
            }
         }
      }

      if (this.expunged) {
         throw new MessageRemovedException();
      }
   }

   public Enumeration getAllHeaderLines() throws MessagingException {
      this.checkExpunged();
      this.loadHeaders();
      return super.getAllHeaderLines();
   }

   public Enumeration getAllHeaders() throws MessagingException {
      this.checkExpunged();
      this.loadHeaders();
      return super.getAllHeaders();
   }

   public String getContentID() throws MessagingException {
      this.checkExpunged();
      this.loadBODYSTRUCTURE();
      return this.bs.id;
   }

   public String[] getContentLanguage() throws MessagingException {
      this.checkExpunged();
      this.loadBODYSTRUCTURE();
      return this.bs.language != null ? (String[])this.bs.language.clone() : null;
   }

   public String getContentMD5() throws MessagingException {
      this.checkExpunged();
      this.loadBODYSTRUCTURE();
      return this.bs.md5;
   }

   protected InputStream getContentStream() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public String getContentType() throws MessagingException {
      this.checkExpunged();
      if (this.type == null) {
         this.loadBODYSTRUCTURE();
         this.type = (new ContentType(this.bs.type, this.bs.subtype, this.bs.cParams)).toString();
      }

      return this.type;
   }

   public DataHandler getDataHandler() throws MessagingException {
      synchronized(this){}

      Throwable var10000;
      label442: {
         DataHandler var48;
         boolean var10001;
         label449: {
            try {
               this.checkExpunged();
               if (this.dh != null) {
                  break label449;
               }

               this.loadBODYSTRUCTURE();
               if (this.type == null) {
                  ContentType var1 = new ContentType(this.bs.type, this.bs.subtype, this.bs.cParams);
                  this.type = var1.toString();
               }
            } catch (Throwable var45) {
               var10000 = var45;
               var10001 = false;
               break label442;
            }

            try {
               if (this.bs.isMulti()) {
                  IMAPMultipartDataSource var2 = new IMAPMultipartDataSource(this, this.bs.bodies, this.sectionId, this);
                  var48 = new DataHandler(var2);
                  this.dh = var48;
                  break label449;
               }
            } catch (Throwable var47) {
               var10000 = var47;
               var10001 = false;
               break label442;
            }

            DataHandler var3;
            IMAPNestedMessage var4;
            BODYSTRUCTURE var5;
            String var49;
            ENVELOPE var51;
            label432: {
               label431: {
                  try {
                     if (!this.bs.isNested() || !this.isREV1()) {
                        break label449;
                     }

                     var3 = new DataHandler;
                     var4 = new IMAPNestedMessage;
                     var5 = this.bs.bodies[0];
                     var51 = this.bs.envelope;
                     if (this.sectionId != null) {
                        break label431;
                     }
                  } catch (Throwable var46) {
                     var10000 = var46;
                     var10001 = false;
                     break label442;
                  }

                  var49 = "1";
                  break label432;
               }

               try {
                  StringBuilder var50 = new StringBuilder(String.valueOf(this.sectionId));
                  var50.append(".1");
                  var49 = var50.toString();
               } catch (Throwable var44) {
                  var10000 = var44;
                  var10001 = false;
                  break label442;
               }
            }

            try {
               var4.<init>(this, var5, var51, var49);
               var3.<init>(var4, this.type);
               this.dh = var3;
            } catch (Throwable var43) {
               var10000 = var43;
               var10001 = false;
               break label442;
            }
         }

         label414:
         try {
            var48 = super.getDataHandler();
            return var48;
         } catch (Throwable var42) {
            var10000 = var42;
            var10001 = false;
            break label414;
         }
      }

      Throwable var52 = var10000;
      throw var52;
   }

   public String getDescription() throws MessagingException {
      this.checkExpunged();
      String var1 = this.description;
      if (var1 != null) {
         return var1;
      } else {
         this.loadBODYSTRUCTURE();
         if (this.bs.description == null) {
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
   }

   public String getDisposition() throws MessagingException {
      this.checkExpunged();
      this.loadBODYSTRUCTURE();
      return this.bs.disposition;
   }

   public String getEncoding() throws MessagingException {
      this.checkExpunged();
      this.loadBODYSTRUCTURE();
      return this.bs.encoding;
   }

   protected int getFetchBlockSize() {
      return ((IMAPStore)this.folder.getStore()).getFetchBlockSize();
   }

   public String getFileName() throws MessagingException {
      this.checkExpunged();
      this.loadBODYSTRUCTURE();
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

   public Flags getFlags() throws MessagingException {
      synchronized(this){}

      Flags var1;
      try {
         this.checkExpunged();
         this.loadFlags();
         var1 = super.getFlags();
      } finally {
         ;
      }

      return var1;
   }

   public Address[] getFrom() throws MessagingException {
      this.checkExpunged();
      this.loadEnvelope();
      return this.aaclone(this.envelope.from);
   }

   public String getHeader(String var1, String var2) throws MessagingException {
      this.checkExpunged();
      return this.getHeader(var1) == null ? null : this.headers.getHeader(var1, var2);
   }

   public String[] getHeader(String param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public String getInReplyTo() throws MessagingException {
      this.checkExpunged();
      this.loadEnvelope();
      return this.envelope.inReplyTo;
   }

   public int getLineCount() throws MessagingException {
      this.checkExpunged();
      this.loadBODYSTRUCTURE();
      return this.bs.lines;
   }

   public Enumeration getMatchingHeaderLines(String[] var1) throws MessagingException {
      this.checkExpunged();
      this.loadHeaders();
      return super.getMatchingHeaderLines(var1);
   }

   public Enumeration getMatchingHeaders(String[] var1) throws MessagingException {
      this.checkExpunged();
      this.loadHeaders();
      return super.getMatchingHeaders(var1);
   }

   protected Object getMessageCacheLock() {
      return ((IMAPFolder)this.folder).messageCacheLock;
   }

   public String getMessageID() throws MessagingException {
      this.checkExpunged();
      this.loadEnvelope();
      return this.envelope.messageId;
   }

   public Enumeration getNonMatchingHeaderLines(String[] var1) throws MessagingException {
      this.checkExpunged();
      this.loadHeaders();
      return super.getNonMatchingHeaderLines(var1);
   }

   public Enumeration getNonMatchingHeaders(String[] var1) throws MessagingException {
      this.checkExpunged();
      this.loadHeaders();
      return super.getNonMatchingHeaders(var1);
   }

   public boolean getPeek() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.peek;
      } finally {
         ;
      }

      return var1;
   }

   protected IMAPProtocol getProtocol() throws ProtocolException, FolderClosedException {
      ((IMAPFolder)this.folder).waitIfIdle();
      IMAPProtocol var1 = ((IMAPFolder)this.folder).protocol;
      if (var1 != null) {
         return var1;
      } else {
         throw new FolderClosedException(this.folder);
      }
   }

   public Date getReceivedDate() throws MessagingException {
      this.checkExpunged();
      this.loadEnvelope();
      return this.receivedDate == null ? null : new Date(this.receivedDate.getTime());
   }

   public Address[] getRecipients(Message.RecipientType var1) throws MessagingException {
      this.checkExpunged();
      this.loadEnvelope();
      if (var1 == Message.RecipientType.TO) {
         return this.aaclone(this.envelope.to);
      } else if (var1 == Message.RecipientType.CC) {
         return this.aaclone(this.envelope.cc);
      } else {
         return (Address[])(var1 == Message.RecipientType.BCC ? this.aaclone(this.envelope.bcc) : super.getRecipients(var1));
      }
   }

   public Address[] getReplyTo() throws MessagingException {
      this.checkExpunged();
      this.loadEnvelope();
      return this.aaclone(this.envelope.replyTo);
   }

   public Address getSender() throws MessagingException {
      this.checkExpunged();
      this.loadEnvelope();
      return this.envelope.sender != null ? this.envelope.sender[0] : null;
   }

   public Date getSentDate() throws MessagingException {
      this.checkExpunged();
      this.loadEnvelope();
      return this.envelope.date == null ? null : new Date(this.envelope.date.getTime());
   }

   protected int getSequenceNumber() {
      return this.seqnum;
   }

   public int getSize() throws MessagingException {
      this.checkExpunged();
      if (this.size == -1) {
         this.loadEnvelope();
      }

      return this.size;
   }

   public String getSubject() throws MessagingException {
      this.checkExpunged();
      String var1 = this.subject;
      if (var1 != null) {
         return var1;
      } else {
         this.loadEnvelope();
         if (this.envelope.subject == null) {
            return null;
         } else {
            try {
               this.subject = MimeUtility.decodeText(this.envelope.subject);
            } catch (UnsupportedEncodingException var2) {
               this.subject = this.envelope.subject;
            }

            return this.subject;
         }
      }
   }

   protected long getUID() {
      return this.uid;
   }

   public void invalidateHeaders() {
      synchronized(this){}

      try {
         this.headersLoaded = false;
         this.loadedHeaders = null;
         this.envelope = null;
         this.bs = null;
         this.receivedDate = null;
         this.size = -1;
         this.type = null;
         this.subject = null;
         this.description = null;
      } finally {
         ;
      }

   }

   protected boolean isREV1() throws FolderClosedException {
      IMAPProtocol var1 = ((IMAPFolder)this.folder).protocol;
      if (var1 != null) {
         return var1.isREV1();
      } else {
         throw new FolderClosedException(this.folder);
      }
   }

   public boolean isSet(Flags.Flag var1) throws MessagingException {
      synchronized(this){}

      boolean var2;
      try {
         this.checkExpunged();
         this.loadFlags();
         var2 = super.isSet(var1);
      } finally {
         ;
      }

      return var2;
   }

   public void removeHeader(String var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void setContentID(String var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void setContentLanguage(String[] var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void setContentMD5(String var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void setDataHandler(DataHandler var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void setDescription(String var1, String var2) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void setDisposition(String var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   protected void setExpunged(boolean var1) {
      super.setExpunged(var1);
      this.seqnum = -1;
   }

   public void setFileName(String var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void setFlags(Flags param1, boolean param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void setFrom(Address var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void setHeader(String var1, String var2) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   protected void setMessageNumber(int var1) {
      super.setMessageNumber(var1);
   }

   public void setPeek(boolean var1) {
      synchronized(this){}

      try {
         this.peek = var1;
      } finally {
         ;
      }

   }

   public void setRecipients(Message.RecipientType var1, Address[] var2) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void setReplyTo(Address[] var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void setSender(Address var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   public void setSentDate(Date var1) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   protected void setSequenceNumber(int var1) {
      this.seqnum = var1;
   }

   public void setSubject(String var1, String var2) throws MessagingException {
      throw new IllegalWriteException("IMAPMessage is read-only");
   }

   protected void setUID(long var1) {
      this.uid = var1;
   }

   public void writeTo(OutputStream param1) throws IOException, MessagingException {
      // $FF: Couldn't be decompiled
   }
}
