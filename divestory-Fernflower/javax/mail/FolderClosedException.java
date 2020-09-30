package javax.mail;

public class FolderClosedException extends MessagingException {
   private static final long serialVersionUID = 1687879213433302315L;
   private transient Folder folder;

   public FolderClosedException(Folder var1) {
      this(var1, (String)null);
   }

   public FolderClosedException(Folder var1, String var2) {
      super(var2);
      this.folder = var1;
   }

   public Folder getFolder() {
      return this.folder;
   }
}
