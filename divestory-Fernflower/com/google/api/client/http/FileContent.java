package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class FileContent extends AbstractInputStreamContent {
   private final File file;

   public FileContent(String var1, File var2) {
      super(var1);
      this.file = (File)Preconditions.checkNotNull(var2);
   }

   public File getFile() {
      return this.file;
   }

   public InputStream getInputStream() throws FileNotFoundException {
      return new FileInputStream(this.file);
   }

   public long getLength() {
      return this.file.length();
   }

   public boolean retrySupported() {
      return true;
   }

   public FileContent setCloseInputStream(boolean var1) {
      return (FileContent)super.setCloseInputStream(var1);
   }

   public FileContent setType(String var1) {
      return (FileContent)super.setType(var1);
   }
}
