package org.apache.http.client.methods;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.utils.CloneUtils;

public abstract class HttpEntityEnclosingRequestBase extends HttpRequestBase implements HttpEntityEnclosingRequest {
   private HttpEntity entity;

   public Object clone() throws CloneNotSupportedException {
      HttpEntityEnclosingRequestBase var1 = (HttpEntityEnclosingRequestBase)super.clone();
      HttpEntity var2 = this.entity;
      if (var2 != null) {
         var1.entity = (HttpEntity)CloneUtils.clone(var2);
      }

      return var1;
   }

   public boolean expectContinue() {
      Header var1 = this.getFirstHeader("Expect");
      boolean var2;
      if (var1 != null && "100-continue".equalsIgnoreCase(var1.getValue())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public HttpEntity getEntity() {
      return this.entity;
   }

   public void setEntity(HttpEntity var1) {
      this.entity = var1;
   }
}
