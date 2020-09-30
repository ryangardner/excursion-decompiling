package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpMessage;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public abstract class AbstractHttpMessage implements HttpMessage {
   protected HeaderGroup headergroup;
   protected HttpParams params;

   protected AbstractHttpMessage() {
      this((HttpParams)null);
   }

   protected AbstractHttpMessage(HttpParams var1) {
      this.headergroup = new HeaderGroup();
      this.params = var1;
   }

   public void addHeader(String var1, String var2) {
      if (var1 != null) {
         this.headergroup.addHeader(new BasicHeader(var1, var2));
      } else {
         throw new IllegalArgumentException("Header name may not be null");
      }
   }

   public void addHeader(Header var1) {
      this.headergroup.addHeader(var1);
   }

   public boolean containsHeader(String var1) {
      return this.headergroup.containsHeader(var1);
   }

   public Header[] getAllHeaders() {
      return this.headergroup.getAllHeaders();
   }

   public Header getFirstHeader(String var1) {
      return this.headergroup.getFirstHeader(var1);
   }

   public Header[] getHeaders(String var1) {
      return this.headergroup.getHeaders(var1);
   }

   public Header getLastHeader(String var1) {
      return this.headergroup.getLastHeader(var1);
   }

   public HttpParams getParams() {
      if (this.params == null) {
         this.params = new BasicHttpParams();
      }

      return this.params;
   }

   public HeaderIterator headerIterator() {
      return this.headergroup.iterator();
   }

   public HeaderIterator headerIterator(String var1) {
      return this.headergroup.iterator(var1);
   }

   public void removeHeader(Header var1) {
      this.headergroup.removeHeader(var1);
   }

   public void removeHeaders(String var1) {
      if (var1 != null) {
         HeaderIterator var2 = this.headergroup.iterator();

         while(var2.hasNext()) {
            if (var1.equalsIgnoreCase(((Header)var2.next()).getName())) {
               var2.remove();
            }
         }

      }
   }

   public void setHeader(String var1, String var2) {
      if (var1 != null) {
         this.headergroup.updateHeader(new BasicHeader(var1, var2));
      } else {
         throw new IllegalArgumentException("Header name may not be null");
      }
   }

   public void setHeader(Header var1) {
      this.headergroup.updateHeader(var1);
   }

   public void setHeaders(Header[] var1) {
      this.headergroup.setHeaders(var1);
   }

   public void setParams(HttpParams var1) {
      if (var1 != null) {
         this.params = var1;
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }
}
