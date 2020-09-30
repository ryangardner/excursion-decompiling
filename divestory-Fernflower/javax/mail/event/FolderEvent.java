package javax.mail.event;

import javax.mail.Folder;

public class FolderEvent extends MailEvent {
   public static final int CREATED = 1;
   public static final int DELETED = 2;
   public static final int RENAMED = 3;
   private static final long serialVersionUID = 5278131310563694307L;
   protected transient Folder folder;
   protected transient Folder newFolder;
   protected int type;

   public FolderEvent(Object var1, Folder var2, int var3) {
      this(var1, var2, var2, var3);
   }

   public FolderEvent(Object var1, Folder var2, Folder var3, int var4) {
      super(var1);
      this.folder = var2;
      this.newFolder = var3;
      this.type = var4;
   }

   public void dispatch(Object var1) {
      int var2 = this.type;
      if (var2 == 1) {
         ((FolderListener)var1).folderCreated(this);
      } else if (var2 == 2) {
         ((FolderListener)var1).folderDeleted(this);
      } else if (var2 == 3) {
         ((FolderListener)var1).folderRenamed(this);
      }

   }

   public Folder getFolder() {
      return this.folder;
   }

   public Folder getNewFolder() {
      return this.newFolder;
   }

   public int getType() {
      return this.type;
   }
}
