package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.protocol.HttpContext;

public class ResponseContentEncoding implements HttpResponseInterceptor {
   public void process(HttpResponse var1, HttpContext var2) throws HttpException, IOException {
      HttpEntity var5 = var1.getEntity();
      if (var5 != null) {
         Header var6 = var5.getContentEncoding();
         if (var6 != null) {
            HeaderElement[] var7 = var6.getElements();
            if (var7.length > 0) {
               HeaderElement var8 = var7[0];
               String var3 = var8.getName().toLowerCase(Locale.US);
               if (!"gzip".equals(var3) && !"x-gzip".equals(var3)) {
                  if ("deflate".equals(var3)) {
                     var1.setEntity(new DeflateDecompressingEntity(var1.getEntity()));
                     return;
                  }

                  if ("identity".equals(var3)) {
                     return;
                  }

                  StringBuilder var4 = new StringBuilder();
                  var4.append("Unsupported Content-Coding: ");
                  var4.append(var8.getName());
                  throw new HttpException(var4.toString());
               }

               var1.setEntity(new GzipDecompressingEntity(var1.getEntity()));
            }
         }
      }

   }
}
