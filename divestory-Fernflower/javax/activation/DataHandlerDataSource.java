package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DataHandlerDataSource implements DataSource {
   DataHandler dataHandler = null;

   public DataHandlerDataSource(DataHandler var1) {
      this.dataHandler = var1;
   }

   public String getContentType() {
      return this.dataHandler.getContentType();
   }

   public InputStream getInputStream() throws IOException {
      return this.dataHandler.getInputStream();
   }

   public String getName() {
      return this.dataHandler.getName();
   }

   public OutputStream getOutputStream() throws IOException {
      return this.dataHandler.getOutputStream();
   }
}
