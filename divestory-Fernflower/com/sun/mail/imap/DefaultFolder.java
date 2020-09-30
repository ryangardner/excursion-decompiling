package com.sun.mail.imap;

import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.ListInfo;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;

public class DefaultFolder extends IMAPFolder {
   protected DefaultFolder(IMAPStore var1) {
      super("", '\uffff', var1);
      this.exists = true;
      this.type = 2;
   }

   public void appendMessages(Message[] var1) throws MessagingException {
      throw new MethodNotSupportedException("Cannot append to Default Folder");
   }

   public boolean delete(boolean var1) throws MessagingException {
      throw new MethodNotSupportedException("Cannot delete Default Folder");
   }

   public Message[] expunge() throws MessagingException {
      throw new MethodNotSupportedException("Cannot expunge Default Folder");
   }

   public Folder getFolder(String var1) throws MessagingException {
      return new IMAPFolder(var1, '\uffff', (IMAPStore)this.store);
   }

   public String getName() {
      return this.fullName;
   }

   public Folder getParent() {
      return null;
   }

   public boolean hasNewMessages() throws MessagingException {
      return false;
   }

   public Folder[] list(final String var1) throws MessagingException {
      ListInfo[] var2 = (ListInfo[])null;
      var2 = (ListInfo[])this.doCommand(new IMAPFolder.ProtocolCommand() {
         public Object doCommand(IMAPProtocol var1x) throws ProtocolException {
            return var1x.list("", var1);
         }
      });
      int var3 = 0;
      if (var2 == null) {
         return new Folder[0];
      } else {
         int var4 = var2.length;

         IMAPFolder[] var5;
         for(var5 = new IMAPFolder[var4]; var3 < var4; ++var3) {
            var5[var3] = new IMAPFolder(var2[var3], (IMAPStore)this.store);
         }

         return var5;
      }
   }

   public Folder[] listSubscribed(final String var1) throws MessagingException {
      ListInfo[] var2 = (ListInfo[])null;
      var2 = (ListInfo[])this.doCommand(new IMAPFolder.ProtocolCommand() {
         public Object doCommand(IMAPProtocol var1x) throws ProtocolException {
            return var1x.lsub("", var1);
         }
      });
      int var3 = 0;
      if (var2 == null) {
         return new Folder[0];
      } else {
         int var4 = var2.length;

         IMAPFolder[] var5;
         for(var5 = new IMAPFolder[var4]; var3 < var4; ++var3) {
            var5[var3] = new IMAPFolder(var2[var3], (IMAPStore)this.store);
         }

         return var5;
      }
   }

   public boolean renameTo(Folder var1) throws MessagingException {
      throw new MethodNotSupportedException("Cannot rename Default Folder");
   }
}
