package com.sun.mail.util;

import java.io.IOException;
import javax.mail.Folder;

public class FolderClosedIOException extends IOException {
   private static final long serialVersionUID = 4281122580365555735L;
   private transient Folder folder;

   public FolderClosedIOException(Folder var1) {
      this(var1, (String)null);
   }

   public FolderClosedIOException(Folder var1, String var2) {
      super(var2);
      this.folder = var1;
   }

   public Folder getFolder() {
      return this.folder;
   }
}
