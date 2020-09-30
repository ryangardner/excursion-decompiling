package org.apache.http;

import java.io.Serializable;
import java.util.Locale;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.LangUtils;

public final class HttpHost implements Cloneable, Serializable {
   public static final String DEFAULT_SCHEME_NAME = "http";
   private static final long serialVersionUID = -7529410654042457626L;
   protected final String hostname;
   protected final String lcHostname;
   protected final int port;
   protected final String schemeName;

   public HttpHost(String var1) {
      this(var1, -1, (String)null);
   }

   public HttpHost(String var1, int var2) {
      this(var1, var2, (String)null);
   }

   public HttpHost(String var1, int var2, String var3) {
      if (var1 != null) {
         this.hostname = var1;
         this.lcHostname = var1.toLowerCase(Locale.ENGLISH);
         if (var3 != null) {
            this.schemeName = var3.toLowerCase(Locale.ENGLISH);
         } else {
            this.schemeName = "http";
         }

         this.port = var2;
      } else {
         throw new IllegalArgumentException("Host name may not be null");
      }
   }

   public HttpHost(HttpHost var1) {
      this(var1.hostname, var1.port, var1.schemeName);
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof HttpHost)) {
         return false;
      } else {
         HttpHost var3 = (HttpHost)var1;
         if (!this.lcHostname.equals(var3.lcHostname) || this.port != var3.port || !this.schemeName.equals(var3.schemeName)) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getHostName() {
      return this.hostname;
   }

   public int getPort() {
      return this.port;
   }

   public String getSchemeName() {
      return this.schemeName;
   }

   public int hashCode() {
      return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.lcHostname), this.port), this.schemeName);
   }

   public String toHostString() {
      if (this.port != -1) {
         CharArrayBuffer var1 = new CharArrayBuffer(this.hostname.length() + 6);
         var1.append(this.hostname);
         var1.append(":");
         var1.append(Integer.toString(this.port));
         return var1.toString();
      } else {
         return this.hostname;
      }
   }

   public String toString() {
      return this.toURI();
   }

   public String toURI() {
      CharArrayBuffer var1 = new CharArrayBuffer(32);
      var1.append(this.schemeName);
      var1.append("://");
      var1.append(this.hostname);
      if (this.port != -1) {
         var1.append(':');
         var1.append(Integer.toString(this.port));
      }

      return var1.toString();
   }
}
