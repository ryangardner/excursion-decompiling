package org.apache.http.impl.client;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class BasicResponseHandler implements ResponseHandler<String> {
   public String handleResponse(HttpResponse var1) throws HttpResponseException, IOException {
      StatusLine var2 = var1.getStatusLine();
      if (var2.getStatusCode() < 300) {
         HttpEntity var3 = var1.getEntity();
         String var4;
         if (var3 == null) {
            var4 = null;
         } else {
            var4 = EntityUtils.toString(var3);
         }

         return var4;
      } else {
         throw new HttpResponseException(var2.getStatusCode(), var2.getReasonPhrase());
      }
   }
}
