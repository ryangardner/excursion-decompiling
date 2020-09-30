package com.google.api.client.http.apache;

import com.google.api.client.util.Preconditions;
import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

final class HttpExtensionMethod extends HttpEntityEnclosingRequestBase {
   private final String methodName;

   public HttpExtensionMethod(String var1, String var2) {
      this.methodName = (String)Preconditions.checkNotNull(var1);
      this.setURI(URI.create(var2));
   }

   public String getMethod() {
      return this.methodName;
   }
}
