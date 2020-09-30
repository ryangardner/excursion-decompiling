package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLDataSource implements DataSource {
   private URL url = null;
   private URLConnection url_conn = null;

   public URLDataSource(URL var1) {
      this.url = var1;
   }

   public String getContentType() {
      try {
         if (this.url_conn == null) {
            this.url_conn = this.url.openConnection();
         }
      } catch (IOException var3) {
      }

      URLConnection var1 = this.url_conn;
      String var4;
      if (var1 != null) {
         var4 = var1.getContentType();
      } else {
         var4 = null;
      }

      String var2 = var4;
      if (var4 == null) {
         var2 = "application/octet-stream";
      }

      return var2;
   }

   public InputStream getInputStream() throws IOException {
      return this.url.openStream();
   }

   public String getName() {
      return this.url.getFile();
   }

   public OutputStream getOutputStream() throws IOException {
      URLConnection var1 = this.url.openConnection();
      this.url_conn = var1;
      if (var1 != null) {
         var1.setDoOutput(true);
         return this.url_conn.getOutputStream();
      } else {
         return null;
      }
   }

   public URL getURL() {
      return this.url;
   }
}
