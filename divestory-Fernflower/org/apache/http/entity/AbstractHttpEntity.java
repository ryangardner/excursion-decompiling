package org.apache.http.entity;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

public abstract class AbstractHttpEntity implements HttpEntity {
   protected boolean chunked;
   protected Header contentEncoding;
   protected Header contentType;

   protected AbstractHttpEntity() {
   }

   public void consumeContent() throws IOException {
   }

   public Header getContentEncoding() {
      return this.contentEncoding;
   }

   public Header getContentType() {
      return this.contentType;
   }

   public boolean isChunked() {
      return this.chunked;
   }

   public void setChunked(boolean var1) {
      this.chunked = var1;
   }

   public void setContentEncoding(String var1) {
      BasicHeader var2;
      if (var1 != null) {
         var2 = new BasicHeader("Content-Encoding", var1);
      } else {
         var2 = null;
      }

      this.setContentEncoding((Header)var2);
   }

   public void setContentEncoding(Header var1) {
      this.contentEncoding = var1;
   }

   public void setContentType(String var1) {
      BasicHeader var2;
      if (var1 != null) {
         var2 = new BasicHeader("Content-Type", var1);
      } else {
         var2 = null;
      }

      this.setContentType((Header)var2);
   }

   public void setContentType(Header var1) {
      this.contentType = var1;
   }
}
