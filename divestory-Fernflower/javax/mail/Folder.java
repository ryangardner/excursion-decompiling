package javax.mail;

import java.util.Vector;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;
import javax.mail.event.MailEvent;
import javax.mail.event.MessageChangedEvent;
import javax.mail.event.MessageChangedListener;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import javax.mail.search.SearchTerm;

public abstract class Folder {
   public static final int HOLDS_FOLDERS = 2;
   public static final int HOLDS_MESSAGES = 1;
   public static final int READ_ONLY = 1;
   public static final int READ_WRITE = 2;
   private volatile Vector connectionListeners = null;
   private volatile Vector folderListeners = null;
   private volatile Vector messageChangedListeners = null;
   private volatile Vector messageCountListeners = null;
   protected int mode = -1;
   private EventQueue q;
   private Object qLock = new Object();
   protected Store store;

   protected Folder(Store var1) {
      this.store = var1;
   }

   private void queueEvent(MailEvent var1, Vector var2) {
      Object var3 = this.qLock;
      synchronized(var3){}

      label136: {
         Throwable var10000;
         boolean var10001;
         label131: {
            try {
               if (this.q == null) {
                  EventQueue var4 = new EventQueue();
                  this.q = var4;
               }
            } catch (Throwable var16) {
               var10000 = var16;
               var10001 = false;
               break label131;
            }

            label128:
            try {
               break label136;
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label128;
            }
         }

         while(true) {
            Throwable var17 = var10000;

            try {
               throw var17;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               continue;
            }
         }
      }

      var2 = (Vector)var2.clone();
      this.q.enqueue(var1, var2);
   }

   private void terminateQueue() {
      Object var1 = this.qLock;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (this.q != null) {
               Vector var2 = new Vector();
               var2.setSize(1);
               EventQueue var3 = this.q;
               Folder.TerminatorEvent var4 = new Folder.TerminatorEvent();
               var3.enqueue(var4, var2);
               this.q = null;
            }
         } catch (Throwable var16) {
            var10000 = var16;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            return;
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var17 = var10000;

         try {
            throw var17;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            continue;
         }
      }
   }

   public void addConnectionListener(ConnectionListener var1) {
      synchronized(this){}

      try {
         if (this.connectionListeners == null) {
            Vector var2 = new Vector();
            this.connectionListeners = var2;
         }

         this.connectionListeners.addElement(var1);
      } finally {
         ;
      }

   }

   public void addFolderListener(FolderListener var1) {
      synchronized(this){}

      try {
         if (this.folderListeners == null) {
            Vector var2 = new Vector();
            this.folderListeners = var2;
         }

         this.folderListeners.addElement(var1);
      } finally {
         ;
      }

   }

   public void addMessageChangedListener(MessageChangedListener var1) {
      synchronized(this){}

      try {
         if (this.messageChangedListeners == null) {
            Vector var2 = new Vector();
            this.messageChangedListeners = var2;
         }

         this.messageChangedListeners.addElement(var1);
      } finally {
         ;
      }

   }

   public void addMessageCountListener(MessageCountListener var1) {
      synchronized(this){}

      try {
         if (this.messageCountListeners == null) {
            Vector var2 = new Vector();
            this.messageCountListeners = var2;
         }

         this.messageCountListeners.addElement(var1);
      } finally {
         ;
      }

   }

   public abstract void appendMessages(Message[] var1) throws MessagingException;

   public abstract void close(boolean var1) throws MessagingException;

   public void copyMessages(Message[] var1, Folder var2) throws MessagingException {
      if (var2.exists()) {
         var2.appendMessages(var1);
      } else {
         StringBuilder var3 = new StringBuilder(String.valueOf(var2.getFullName()));
         var3.append(" does not exist");
         throw new FolderNotFoundException(var3.toString(), var2);
      }
   }

   public abstract boolean create(int var1) throws MessagingException;

   public abstract boolean delete(boolean var1) throws MessagingException;

   public abstract boolean exists() throws MessagingException;

   public abstract Message[] expunge() throws MessagingException;

   public void fetch(Message[] var1, FetchProfile var2) throws MessagingException {
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.terminateQueue();
   }

   public int getDeletedMessageCount() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public abstract Folder getFolder(String var1) throws MessagingException;

   public abstract String getFullName();

   public abstract Message getMessage(int var1) throws MessagingException;

   public abstract int getMessageCount() throws MessagingException;

   public Message[] getMessages() throws MessagingException {
      synchronized(this){}

      Throwable var10000;
      label165: {
         Message[] var16;
         int var1;
         boolean var10001;
         label160: {
            try {
               if (this.isOpen()) {
                  var1 = this.getMessageCount();
                  var16 = new Message[var1];
                  break label160;
               }
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label165;
            }

            try {
               IllegalStateException var2 = new IllegalStateException("Folder not open");
               throw var2;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label165;
            }
         }

         int var3 = 1;

         while(true) {
            if (var3 > var1) {
               return var16;
            }

            try {
               var16[var3 - 1] = this.getMessage(var3);
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break;
            }

            ++var3;
         }
      }

      Throwable var17 = var10000;
      throw var17;
   }

   public Message[] getMessages(int var1, int var2) throws MessagingException {
      synchronized(this){}

      Throwable var10000;
      label92: {
         boolean var10001;
         Message[] var3;
         try {
            var3 = new Message[var2 - var1 + 1];
         } catch (Throwable var10) {
            var10000 = var10;
            var10001 = false;
            break label92;
         }

         int var4 = var1;

         while(true) {
            if (var4 > var2) {
               return var3;
            }

            try {
               var3[var4 - var1] = this.getMessage(var4);
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break;
            }

            ++var4;
         }
      }

      Throwable var11 = var10000;
      throw var11;
   }

   public Message[] getMessages(int[] var1) throws MessagingException {
      synchronized(this){}

      Throwable var10000;
      label92: {
         boolean var10001;
         int var2;
         Message[] var3;
         try {
            var2 = var1.length;
            var3 = new Message[var2];
         } catch (Throwable var10) {
            var10000 = var10;
            var10001 = false;
            break label92;
         }

         int var4 = 0;

         while(true) {
            if (var4 >= var2) {
               return var3;
            }

            try {
               var3[var4] = this.getMessage(var1[var4]);
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break;
            }

            ++var4;
         }
      }

      Throwable var11 = var10000;
      throw var11;
   }

   public int getMode() {
      if (this.isOpen()) {
         return this.mode;
      } else {
         throw new IllegalStateException("Folder not open");
      }
   }

   public abstract String getName();

   public int getNewMessageCount() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public abstract Folder getParent() throws MessagingException;

   public abstract Flags getPermanentFlags();

   public abstract char getSeparator() throws MessagingException;

   public Store getStore() {
      return this.store;
   }

   public abstract int getType() throws MessagingException;

   public URLName getURLName() throws MessagingException {
      URLName var1 = this.getStore().getURLName();
      String var2 = this.getFullName();
      StringBuffer var3 = new StringBuffer();
      this.getSeparator();
      if (var2 != null) {
         var3.append(var2);
      }

      return new URLName(var1.getProtocol(), var1.getHost(), var1.getPort(), var3.toString(), var1.getUsername(), (String)null);
   }

   public int getUnreadMessageCount() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public abstract boolean hasNewMessages() throws MessagingException;

   public abstract boolean isOpen();

   public boolean isSubscribed() {
      return true;
   }

   public Folder[] list() throws MessagingException {
      return this.list("%");
   }

   public abstract Folder[] list(String var1) throws MessagingException;

   public Folder[] listSubscribed() throws MessagingException {
      return this.listSubscribed("%");
   }

   public Folder[] listSubscribed(String var1) throws MessagingException {
      return this.list(var1);
   }

   protected void notifyConnectionListeners(int var1) {
      if (this.connectionListeners != null) {
         this.queueEvent(new ConnectionEvent(this, var1), this.connectionListeners);
      }

      if (var1 == 3) {
         this.terminateQueue();
      }

   }

   protected void notifyFolderListeners(int var1) {
      if (this.folderListeners != null) {
         this.queueEvent(new FolderEvent(this, this, var1), this.folderListeners);
      }

      this.store.notifyFolderListeners(var1, this);
   }

   protected void notifyFolderRenamedListeners(Folder var1) {
      if (this.folderListeners != null) {
         this.queueEvent(new FolderEvent(this, this, var1, 3), this.folderListeners);
      }

      this.store.notifyFolderRenamedListeners(this, var1);
   }

   protected void notifyMessageAddedListeners(Message[] var1) {
      if (this.messageCountListeners != null) {
         this.queueEvent(new MessageCountEvent(this, 1, false, var1), this.messageCountListeners);
      }
   }

   protected void notifyMessageChangedListeners(int var1, Message var2) {
      if (this.messageChangedListeners != null) {
         this.queueEvent(new MessageChangedEvent(this, var1, var2), this.messageChangedListeners);
      }
   }

   protected void notifyMessageRemovedListeners(boolean var1, Message[] var2) {
      if (this.messageCountListeners != null) {
         this.queueEvent(new MessageCountEvent(this, 2, var1, var2), this.messageCountListeners);
      }
   }

   public abstract void open(int var1) throws MessagingException;

   public void removeConnectionListener(ConnectionListener var1) {
      synchronized(this){}

      try {
         if (this.connectionListeners != null) {
            this.connectionListeners.removeElement(var1);
         }
      } finally {
         ;
      }

   }

   public void removeFolderListener(FolderListener var1) {
      synchronized(this){}

      try {
         if (this.folderListeners != null) {
            this.folderListeners.removeElement(var1);
         }
      } finally {
         ;
      }

   }

   public void removeMessageChangedListener(MessageChangedListener var1) {
      synchronized(this){}

      try {
         if (this.messageChangedListeners != null) {
            this.messageChangedListeners.removeElement(var1);
         }
      } finally {
         ;
      }

   }

   public void removeMessageCountListener(MessageCountListener var1) {
      synchronized(this){}

      try {
         if (this.messageCountListeners != null) {
            this.messageCountListeners.removeElement(var1);
         }
      } finally {
         ;
      }

   }

   public abstract boolean renameTo(Folder var1) throws MessagingException;

   public Message[] search(SearchTerm var1) throws MessagingException {
      return this.search(var1, this.getMessages());
   }

   public Message[] search(SearchTerm var1, Message[] var2) throws MessagingException {
      Vector var3 = new Vector();

      for(int var4 = 0; var4 < var2.length; ++var4) {
         try {
            if (var2[var4].match(var1)) {
               var3.addElement(var2[var4]);
            }
         } catch (MessageRemovedException var6) {
         }
      }

      Message[] var7 = new Message[var3.size()];
      var3.copyInto(var7);
      return var7;
   }

   public void setFlags(int var1, int var2, Flags var3, boolean var4) throws MessagingException {
      synchronized(this){}

      for(; var1 <= var2; ++var1) {
         boolean var8 = false;

         try {
            var8 = true;
            this.getMessage(var1).setFlags(var3, var4);
            var8 = false;
         } catch (MessageRemovedException var9) {
            var8 = false;
         } finally {
            if (var8) {
               ;
            }
         }
      }

   }

   public void setFlags(int[] param1, Flags param2, boolean param3) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void setFlags(Message[] param1, Flags param2, boolean param3) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void setSubscribed(boolean var1) throws MessagingException {
      throw new MethodNotSupportedException();
   }

   public String toString() {
      String var1 = this.getFullName();
      return var1 != null ? var1 : super.toString();
   }

   static class TerminatorEvent extends MailEvent {
      private static final long serialVersionUID = 3765761925441296565L;

      TerminatorEvent() {
         super(new Object());
      }

      public void dispatch(Object var1) {
         Thread.currentThread().interrupt();
      }
   }
}
