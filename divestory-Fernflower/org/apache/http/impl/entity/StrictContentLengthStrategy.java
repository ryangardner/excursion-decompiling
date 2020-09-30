package org.apache.http.impl.entity;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.entity.ContentLengthStrategy;

public class StrictContentLengthStrategy implements ContentLengthStrategy {
   public long determineLength(HttpMessage var1) throws HttpException {
      if (var1 != null) {
         Header var2 = var1.getFirstHeader("Transfer-Encoding");
         Header var3 = var1.getFirstHeader("Content-Length");
         StringBuffer var9;
         if (var2 != null) {
            String var10 = var2.getValue();
            if ("chunked".equalsIgnoreCase(var10)) {
               if (!var1.getProtocolVersion().lessEquals(HttpVersion.HTTP_1_0)) {
                  return -2L;
               } else {
                  var9 = new StringBuffer();
                  var9.append("Chunked transfer encoding not allowed for ");
                  var9.append(var1.getProtocolVersion());
                  throw new ProtocolException(var9.toString());
               }
            } else if ("identity".equalsIgnoreCase(var10)) {
               return -1L;
            } else {
               StringBuffer var8 = new StringBuffer();
               var8.append("Unsupported transfer encoding: ");
               var8.append(var10);
               throw new ProtocolException(var8.toString());
            }
         } else if (var3 != null) {
            String var7 = var3.getValue();

            try {
               long var4 = Long.parseLong(var7);
               return var4;
            } catch (NumberFormatException var6) {
               var9 = new StringBuffer();
               var9.append("Invalid content length: ");
               var9.append(var7);
               throw new ProtocolException(var9.toString());
            }
         } else {
            return -1L;
         }
      } else {
         throw new IllegalArgumentException("HTTP message may not be null");
      }
   }
}
