package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.params.HttpParams;

public class RFC2965SpecFactory implements CookieSpecFactory {
   public CookieSpec newInstance(HttpParams var1) {
      if (var1 != null) {
         String[] var2 = null;
         Collection var3 = (Collection)var1.getParameter("http.protocol.cookie-datepatterns");
         if (var3 != null) {
            var2 = (String[])var3.toArray(new String[var3.size()]);
         }

         return new RFC2965Spec(var2, var1.getBooleanParameter("http.protocol.single-cookie-header", false));
      } else {
         return new RFC2965Spec();
      }
   }
}
