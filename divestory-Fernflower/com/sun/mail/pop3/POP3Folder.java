package com.sun.mail.pop3;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Vector;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.MethodNotSupportedException;

public class POP3Folder extends Folder {
   private boolean doneUidl = false;
   private boolean exists = false;
   private Vector message_cache;
   private String name;
   private boolean opened = false;
   private Protocol port;
   private int size;
   private int total;

   POP3Folder(POP3Store var1, String var2) {
      super(var1);
      this.name = var2;
      if (var2.equalsIgnoreCase("INBOX")) {
         this.exists = true;
      }

   }

   public void appendMessages(Message[] var1) throws MessagingException {
      throw new MethodNotSupportedException("Append not supported");
   }

   void checkClosed() throws IllegalStateException {
      if (this.opened) {
         throw new IllegalStateException("Folder is Open");
      }
   }

   void checkOpen() throws IllegalStateException {
      if (!this.opened) {
         throw new IllegalStateException("Folder is not Open");
      }
   }

   void checkReadable() throws IllegalStateException {
      if (!this.opened || this.mode != 1 && this.mode != 2) {
         throw new IllegalStateException("Folder is not Readable");
      }
   }

   void checkWritable() throws IllegalStateException {
      if (!this.opened || this.mode != 2) {
         throw new IllegalStateException("Folder is not Writable");
      }
   }

   public void close(boolean param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public boolean create(int var1) throws MessagingException {
      return false;
   }

   protected POP3Message createMessage(Folder var1, int var2) throws MessagingException {
      POP3Message var6;
      label20: {
         Constructor var5 = ((POP3Store)this.store).messageConstructor;
         if (var5 != null) {
            try {
               Integer var3 = new Integer(var2);
               var6 = (POP3Message)var5.newInstance(this, var3);
               break label20;
            } catch (Exception var4) {
            }
         }

         var6 = null;
      }

      POP3Message var7 = var6;
      if (var6 == null) {
         var7 = new POP3Message(this, var2);
      }

      return var7;
   }

   public boolean delete(boolean var1) throws MessagingException {
      throw new MethodNotSupportedException("delete");
   }

   public boolean exists() {
      return this.exists;
   }

   public Message[] expunge() throws MessagingException {
      throw new MethodNotSupportedException("Expunge not supported");
   }

   public void fetch(Message[] param1, FetchProfile param2) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.close(false);
   }

   public Folder getFolder(String var1) throws MessagingException {
      throw new MessagingException("not a directory");
   }

   public String getFullName() {
      return this.name;
   }

   public Message getMessage(int var1) throws MessagingException {
      synchronized(this){}

      Throwable var10000;
      label128: {
         boolean var10001;
         Vector var2;
         try {
            this.checkOpen();
            var2 = this.message_cache;
         } catch (Throwable var16) {
            var10000 = var16;
            var10001 = false;
            break label128;
         }

         int var3 = var1 - 1;

         POP3Message var4;
         try {
            var4 = (POP3Message)var2.elementAt(var3);
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label128;
         }

         POP3Message var17 = var4;
         if (var4 != null) {
            return var17;
         }

         label115:
         try {
            var17 = this.createMessage(this, var1);
            this.message_cache.setElementAt(var17, var3);
            return var17;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label115;
         }
      }

      Throwable var18 = var10000;
      throw var18;
   }

   public int getMessageCount() throws MessagingException {
      synchronized(this){}

      Throwable var10000;
      label78: {
         boolean var1;
         boolean var10001;
         try {
            var1 = this.opened;
         } catch (Throwable var9) {
            var10000 = var9;
            var10001 = false;
            break label78;
         }

         if (!var1) {
            return -1;
         }

         int var2;
         try {
            this.checkReadable();
            var2 = this.total;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label78;
         }

         return var2;
      }

      Throwable var3 = var10000;
      throw var3;
   }

   public String getName() {
      return this.name;
   }

   public Folder getParent() {
      return new DefaultFolder((POP3Store)this.store);
   }

   public Flags getPermanentFlags() {
      return new Flags();
   }

   Protocol getProtocol() throws MessagingException {
      this.checkOpen();
      return this.port;
   }

   public char getSeparator() {
      return '\u0000';
   }

   public int getSize() throws MessagingException {
      synchronized(this){}

      int var1;
      try {
         this.checkOpen();
         var1 = this.size;
      } finally {
         ;
      }

      return var1;
   }

   public int[] getSizes() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public int getType() {
      return 1;
   }

   public String getUID(Message var1) throws MessagingException {
      synchronized(this){}

      String var11;
      try {
         this.checkOpen();
         POP3Message var9 = (POP3Message)var1;

         try {
            if (var9.uid == "UNKNOWN") {
               var9.uid = this.port.uidl(var9.getMessageNumber());
            }

            var11 = var9.uid;
         } catch (EOFException var6) {
            this.close(false);
            FolderClosedException var2 = new FolderClosedException(this, var6.toString());
            throw var2;
         } catch (IOException var7) {
            MessagingException var10 = new MessagingException("error getting UIDL", var7);
            throw var10;
         }
      } finally {
         ;
      }

      return var11;
   }

   public boolean hasNewMessages() throws MessagingException {
      return false;
   }

   public boolean isOpen() {
      if (!this.opened) {
         return false;
      } else if (this.store.isConnected()) {
         return true;
      } else {
         try {
            this.close(false);
         } catch (MessagingException var2) {
         }

         return false;
      }
   }

   public Folder[] list(String var1) throws MessagingException {
      throw new MessagingException("not a directory");
   }

   public InputStream listCommand() throws MessagingException, IOException {
      synchronized(this){}

      InputStream var1;
      try {
         this.checkOpen();
         var1 = this.port.list();
      } finally {
         ;
      }

      return var1;
   }

   protected void notifyMessageChangedListeners(int var1, Message var2) {
      super.notifyMessageChangedListeners(var1, var2);
   }

   public void open(int param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public boolean renameTo(Folder var1) throws MessagingException {
      throw new MethodNotSupportedException("renameTo");
   }
}
