package javax.mail;

public class FolderNotFoundException extends MessagingException {
   private static final long serialVersionUID = 472612108891249403L;
   private transient Folder folder;

   public FolderNotFoundException() {
   }

   public FolderNotFoundException(String var1, Folder var2) {
      super(var1);
      this.folder = var2;
   }

   public FolderNotFoundException(Folder var1) {
      this.folder = var1;
   }

   public FolderNotFoundException(Folder var1, String var2) {
      super(var2);
      this.folder = var1;
   }

   public Folder getFolder() {
      return this.folder;
   }
}
