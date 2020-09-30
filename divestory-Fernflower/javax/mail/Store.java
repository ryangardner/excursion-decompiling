package javax.mail;

import java.util.Vector;
import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;
import javax.mail.event.StoreEvent;
import javax.mail.event.StoreListener;

public abstract class Store extends Service {
   private volatile Vector folderListeners = null;
   private volatile Vector storeListeners = null;

   protected Store(Session var1, URLName var2) {
      super(var1, var2);
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

   public void addStoreListener(StoreListener var1) {
      synchronized(this){}

      try {
         if (this.storeListeners == null) {
            Vector var2 = new Vector();
            this.storeListeners = var2;
         }

         this.storeListeners.addElement(var1);
      } finally {
         ;
      }

   }

   public abstract Folder getDefaultFolder() throws MessagingException;

   public abstract Folder getFolder(String var1) throws MessagingException;

   public abstract Folder getFolder(URLName var1) throws MessagingException;

   public Folder[] getPersonalNamespaces() throws MessagingException {
      return new Folder[]{this.getDefaultFolder()};
   }

   public Folder[] getSharedNamespaces() throws MessagingException {
      return new Folder[0];
   }

   public Folder[] getUserNamespaces(String var1) throws MessagingException {
      return new Folder[0];
   }

   protected void notifyFolderListeners(int var1, Folder var2) {
      if (this.folderListeners != null) {
         this.queueEvent(new FolderEvent(this, var2, var1), this.folderListeners);
      }
   }

   protected void notifyFolderRenamedListeners(Folder var1, Folder var2) {
      if (this.folderListeners != null) {
         this.queueEvent(new FolderEvent(this, var1, var2, 3), this.folderListeners);
      }
   }

   protected void notifyStoreListeners(int var1, String var2) {
      if (this.storeListeners != null) {
         this.queueEvent(new StoreEvent(this, var1, var2), this.storeListeners);
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

   public void removeStoreListener(StoreListener var1) {
      synchronized(this){}

      try {
         if (this.storeListeners != null) {
            this.storeListeners.removeElement(var1);
         }
      } finally {
         ;
      }

   }
}
