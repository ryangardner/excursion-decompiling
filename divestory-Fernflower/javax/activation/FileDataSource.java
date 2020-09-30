package javax.activation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileDataSource implements DataSource {
   private File _file;
   private FileTypeMap typeMap;

   public FileDataSource(File var1) {
      this._file = null;
      this.typeMap = null;
      this._file = var1;
   }

   public FileDataSource(String var1) {
      this(new File(var1));
   }

   public String getContentType() {
      FileTypeMap var1 = this.typeMap;
      return var1 == null ? FileTypeMap.getDefaultFileTypeMap().getContentType(this._file) : var1.getContentType(this._file);
   }

   public File getFile() {
      return this._file;
   }

   public InputStream getInputStream() throws IOException {
      return new FileInputStream(this._file);
   }

   public String getName() {
      return this._file.getName();
   }

   public OutputStream getOutputStream() throws IOException {
      return new FileOutputStream(this._file);
   }

   public void setFileTypeMap(FileTypeMap var1) {
      this.typeMap = var1;
   }
}
