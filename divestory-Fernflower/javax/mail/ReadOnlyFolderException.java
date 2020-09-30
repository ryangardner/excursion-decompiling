package javax.mail;

public class ReadOnlyFolderException extends MessagingException {
   private static final long serialVersionUID = 5711829372799039325L;
   private transient Folder folder;

   public ReadOnlyFolderException(Folder var1) {
      this(var1, (String)null);
   }

   public ReadOnlyFolderException(Folder var1, String var2) {
      super(var2);
      this.folder = var1;
   }

   public Folder getFolder() {
      return this.folder;
   }
}
