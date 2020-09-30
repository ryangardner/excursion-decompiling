package com.sun.mail.imap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import com.sun.mail.imap.protocol.ENVELOPE;
import com.sun.mail.imap.protocol.IMAPProtocol;
import javax.mail.Flags;
import javax.mail.FolderClosedException;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;

public class IMAPNestedMessage extends IMAPMessage {
   private IMAPMessage msg;

   IMAPNestedMessage(IMAPMessage var1, BODYSTRUCTURE var2, ENVELOPE var3, String var4) {
      super(var1._getSession());
      this.msg = var1;
      this.bs = var2;
      this.envelope = var3;
      this.sectionId = var4;
   }

   protected void checkExpunged() throws MessageRemovedException {
      this.msg.checkExpunged();
   }

   protected int getFetchBlockSize() {
      return this.msg.getFetchBlockSize();
   }

   protected Object getMessageCacheLock() {
      return this.msg.getMessageCacheLock();
   }

   protected IMAPProtocol getProtocol() throws ProtocolException, FolderClosedException {
      return this.msg.getProtocol();
   }

   protected int getSequenceNumber() {
      return this.msg.getSequenceNumber();
   }

   public int getSize() throws MessagingException {
      return this.bs.size;
   }

   public boolean isExpunged() {
      return this.msg.isExpunged();
   }

   protected boolean isREV1() throws FolderClosedException {
      return this.msg.isREV1();
   }

   public void setFlags(Flags var1, boolean var2) throws MessagingException {
      synchronized(this){}

      try {
         MethodNotSupportedException var5 = new MethodNotSupportedException("Cannot set flags on this nested message");
         throw var5;
      } finally {
         ;
      }
   }
}
