package org.apache.http.impl.client;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpRequest;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.params.HttpProtocolParams;

public class RequestWrapper extends AbstractHttpMessage implements HttpUriRequest {
   private int execCount;
   private String method;
   private final HttpRequest original;
   private URI uri;
   private ProtocolVersion version;

   public RequestWrapper(HttpRequest var1) throws ProtocolException {
      if (var1 != null) {
         this.original = var1;
         this.setParams(var1.getParams());
         this.setHeaders(var1.getAllHeaders());
         if (var1 instanceof HttpUriRequest) {
            HttpUriRequest var5 = (HttpUriRequest)var1;
            this.uri = var5.getURI();
            this.method = var5.getMethod();
            this.version = null;
         } else {
            RequestLine var2 = var1.getRequestLine();

            try {
               URI var6 = new URI(var2.getUri());
               this.uri = var6;
            } catch (URISyntaxException var4) {
               StringBuilder var3 = new StringBuilder();
               var3.append("Invalid request URI: ");
               var3.append(var2.getUri());
               throw new ProtocolException(var3.toString(), var4);
            }

            this.method = var2.getMethod();
            this.version = var1.getProtocolVersion();
         }

         this.execCount = 0;
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }

   public void abort() throws UnsupportedOperationException {
      throw new UnsupportedOperationException();
   }

   public int getExecCount() {
      return this.execCount;
   }

   public String getMethod() {
      return this.method;
   }

   public HttpRequest getOriginal() {
      return this.original;
   }

   public ProtocolVersion getProtocolVersion() {
      if (this.version == null) {
         this.version = HttpProtocolParams.getVersion(this.getParams());
      }

      return this.version;
   }

   public RequestLine getRequestLine() {
      String var1 = this.getMethod();
      ProtocolVersion var2 = this.getProtocolVersion();
      URI var3 = this.uri;
      String var5;
      if (var3 != null) {
         var5 = var3.toASCIIString();
      } else {
         var5 = null;
      }

      String var4;
      if (var5 != null) {
         var4 = var5;
         if (var5.length() != 0) {
            return new BasicRequestLine(var1, var4, var2);
         }
      }

      var4 = "/";
      return new BasicRequestLine(var1, var4, var2);
   }

   public URI getURI() {
      return this.uri;
   }

   public void incrementExecCount() {
      ++this.execCount;
   }

   public boolean isAborted() {
      return false;
   }

   public boolean isRepeatable() {
      return true;
   }

   public void resetHeaders() {
      this.headergroup.clear();
      this.setHeaders(this.original.getAllHeaders());
   }

   public void setMethod(String var1) {
      if (var1 != null) {
         this.method = var1;
      } else {
         throw new IllegalArgumentException("Method name may not be null");
      }
   }

   public void setProtocolVersion(ProtocolVersion var1) {
      this.version = var1;
   }

   public void setURI(URI var1) {
      this.uri = var1;
   }
}
