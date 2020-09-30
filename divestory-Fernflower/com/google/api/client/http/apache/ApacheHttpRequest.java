package com.google.api.client.http.apache;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

final class ApacheHttpRequest extends LowLevelHttpRequest {
   private final HttpClient httpClient;
   private final HttpRequestBase request;

   ApacheHttpRequest(HttpClient var1, HttpRequestBase var2) {
      this.httpClient = var1;
      this.request = var2;
   }

   public void addHeader(String var1, String var2) {
      this.request.addHeader(var1, var2);
   }

   public LowLevelHttpResponse execute() throws IOException {
      HttpRequestBase var1;
      if (this.getStreamingContent() != null) {
         var1 = this.request;
         Preconditions.checkState(var1 instanceof HttpEntityEnclosingRequest, "Apache HTTP client does not support %s requests with content.", var1.getRequestLine().getMethod());
         ContentEntity var2 = new ContentEntity(this.getContentLength(), this.getStreamingContent());
         var2.setContentEncoding(this.getContentEncoding());
         var2.setContentType(this.getContentType());
         if (this.getContentLength() == -1L) {
            var2.setChunked(true);
         }

         ((HttpEntityEnclosingRequest)this.request).setEntity(var2);
      }

      var1 = this.request;
      return new ApacheHttpResponse(var1, this.httpClient.execute(var1));
   }

   public void setTimeout(int var1, int var2) throws IOException {
      HttpParams var3 = this.request.getParams();
      ConnManagerParams.setTimeout(var3, (long)var1);
      HttpConnectionParams.setConnectionTimeout(var3, var1);
      HttpConnectionParams.setSoTimeout(var3, var2);
   }
}
