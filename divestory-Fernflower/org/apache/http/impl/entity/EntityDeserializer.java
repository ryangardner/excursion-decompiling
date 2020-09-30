package org.apache.http.impl.entity;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.io.ChunkedInputStream;
import org.apache.http.impl.io.ContentLengthInputStream;
import org.apache.http.impl.io.IdentityInputStream;
import org.apache.http.io.SessionInputBuffer;

public class EntityDeserializer {
   private final ContentLengthStrategy lenStrategy;

   public EntityDeserializer(ContentLengthStrategy var1) {
      if (var1 != null) {
         this.lenStrategy = var1;
      } else {
         throw new IllegalArgumentException("Content length strategy may not be null");
      }
   }

   public HttpEntity deserialize(SessionInputBuffer var1, HttpMessage var2) throws HttpException, IOException {
      if (var1 != null) {
         if (var2 != null) {
            return this.doDeserialize(var1, var2);
         } else {
            throw new IllegalArgumentException("HTTP message may not be null");
         }
      } else {
         throw new IllegalArgumentException("Session input buffer may not be null");
      }
   }

   protected BasicHttpEntity doDeserialize(SessionInputBuffer var1, HttpMessage var2) throws HttpException, IOException {
      BasicHttpEntity var3 = new BasicHttpEntity();
      long var4 = this.lenStrategy.determineLength(var2);
      if (var4 == -2L) {
         var3.setChunked(true);
         var3.setContentLength(-1L);
         var3.setContent(new ChunkedInputStream(var1));
      } else if (var4 == -1L) {
         var3.setChunked(false);
         var3.setContentLength(-1L);
         var3.setContent(new IdentityInputStream(var1));
      } else {
         var3.setChunked(false);
         var3.setContentLength(var4);
         var3.setContent(new ContentLengthInputStream(var1, var4));
      }

      Header var6 = var2.getFirstHeader("Content-Type");
      if (var6 != null) {
         var3.setContentType(var6);
      }

      var6 = var2.getFirstHeader("Content-Encoding");
      if (var6 != null) {
         var3.setContentEncoding(var6);
      }

      return var3;
   }
}
