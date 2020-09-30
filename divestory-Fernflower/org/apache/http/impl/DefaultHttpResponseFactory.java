package org.apache.http.impl;

import java.util.Locale;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.ProtocolVersion;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HttpContext;

public class DefaultHttpResponseFactory implements HttpResponseFactory {
   protected final ReasonPhraseCatalog reasonCatalog;

   public DefaultHttpResponseFactory() {
      this(EnglishReasonPhraseCatalog.INSTANCE);
   }

   public DefaultHttpResponseFactory(ReasonPhraseCatalog var1) {
      if (var1 != null) {
         this.reasonCatalog = var1;
      } else {
         throw new IllegalArgumentException("Reason phrase catalog must not be null.");
      }
   }

   protected Locale determineLocale(HttpContext var1) {
      return Locale.getDefault();
   }

   public HttpResponse newHttpResponse(ProtocolVersion var1, int var2, HttpContext var3) {
      if (var1 != null) {
         Locale var4 = this.determineLocale(var3);
         return new BasicHttpResponse(new BasicStatusLine(var1, var2, this.reasonCatalog.getReason(var2, var4)), this.reasonCatalog, var4);
      } else {
         throw new IllegalArgumentException("HTTP version may not be null");
      }
   }

   public HttpResponse newHttpResponse(StatusLine var1, HttpContext var2) {
      if (var1 != null) {
         Locale var3 = this.determineLocale(var2);
         return new BasicHttpResponse(var1, this.reasonCatalog, var3);
      } else {
         throw new IllegalArgumentException("Status line may not be null");
      }
   }
}
