package org.apache.http.impl.entity;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.ParseException;
import org.apache.http.ProtocolException;
import org.apache.http.entity.ContentLengthStrategy;

public class LaxContentLengthStrategy implements ContentLengthStrategy {
   public long determineLength(HttpMessage var1) throws HttpException {
      if (var1 == null) {
         throw new IllegalArgumentException("HTTP message may not be null");
      } else {
         boolean var2 = var1.getParams().isParameterTrue("http.protocol.strict-transfer-encoding");
         Header var3 = var1.getFirstHeader("Transfer-Encoding");
         Header var4 = var1.getFirstHeader("Content-Length");
         int var5;
         StringBuffer var14;
         if (var3 != null) {
            HeaderElement[] var15;
            try {
               var15 = var3.getElements();
            } catch (ParseException var8) {
               StringBuffer var11 = new StringBuffer();
               var11.append("Invalid Transfer-Encoding header value: ");
               var11.append(var3);
               throw new ProtocolException(var11.toString(), var8);
            }

            if (var2) {
               for(var5 = 0; var5 < var15.length; ++var5) {
                  String var12 = var15[var5].getName();
                  if (var12 != null && var12.length() > 0 && !var12.equalsIgnoreCase("chunked") && !var12.equalsIgnoreCase("identity")) {
                     var14 = new StringBuffer();
                     var14.append("Unsupported transfer encoding: ");
                     var14.append(var12);
                     throw new ProtocolException(var14.toString());
                  }
               }
            }

            var5 = var15.length;
            if ("identity".equalsIgnoreCase(var3.getValue())) {
               return -1L;
            } else if (var5 > 0 && "chunked".equalsIgnoreCase(var15[var5 - 1].getName())) {
               return -2L;
            } else if (!var2) {
               return -1L;
            } else {
               throw new ProtocolException("Chunk-encoding must be the last one applied");
            }
         } else {
            if (var4 != null) {
               Header[] var13 = var1.getHeaders("Content-Length");
               if (var2 && var13.length > 1) {
                  throw new ProtocolException("Multiple content length headers");
               }

               var5 = var13.length - 1;

               long var6;
               while(true) {
                  if (var5 >= 0) {
                     Header var10 = var13[var5];

                     try {
                        var6 = Long.parseLong(var10.getValue());
                        break;
                     } catch (NumberFormatException var9) {
                        if (!var2) {
                           --var5;
                           continue;
                        }

                        var14 = new StringBuffer();
                        var14.append("Invalid content length: ");
                        var14.append(var10.getValue());
                        throw new ProtocolException(var14.toString());
                     }
                  }

                  var6 = -1L;
                  break;
               }

               if (var6 >= 0L) {
                  return var6;
               }
            }

            return -1L;
         }
      }
   }
}
