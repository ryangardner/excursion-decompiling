package org.apache.http.message;

import org.apache.http.HttpRequest;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.params.HttpProtocolParams;

public class BasicHttpRequest extends AbstractHttpMessage implements HttpRequest {
   private final String method;
   private RequestLine requestline;
   private final String uri;

   public BasicHttpRequest(String var1, String var2) {
      if (var1 != null) {
         if (var2 != null) {
            this.method = var1;
            this.uri = var2;
            this.requestline = null;
         } else {
            throw new IllegalArgumentException("Request URI may not be null");
         }
      } else {
         throw new IllegalArgumentException("Method name may not be null");
      }
   }

   public BasicHttpRequest(String var1, String var2, ProtocolVersion var3) {
      this(new BasicRequestLine(var1, var2, var3));
   }

   public BasicHttpRequest(RequestLine var1) {
      if (var1 != null) {
         this.requestline = var1;
         this.method = var1.getMethod();
         this.uri = var1.getUri();
      } else {
         throw new IllegalArgumentException("Request line may not be null");
      }
   }

   public ProtocolVersion getProtocolVersion() {
      return this.getRequestLine().getProtocolVersion();
   }

   public RequestLine getRequestLine() {
      if (this.requestline == null) {
         ProtocolVersion var1 = HttpProtocolParams.getVersion(this.getParams());
         this.requestline = new BasicRequestLine(this.method, this.uri, var1);
      }

      return this.requestline;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(this.method);
      var1.append(" ");
      var1.append(this.uri);
      var1.append(" ");
      var1.append(this.headergroup);
      return var1.toString();
   }
}
