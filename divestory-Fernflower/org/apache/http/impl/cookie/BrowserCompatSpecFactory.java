package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.params.HttpParams;

public class BrowserCompatSpecFactory implements CookieSpecFactory {
   public CookieSpec newInstance(HttpParams var1) {
      if (var1 != null) {
         Object var2 = null;
         Collection var3 = (Collection)var1.getParameter("http.protocol.cookie-datepatterns");
         String[] var4 = (String[])var2;
         if (var3 != null) {
            var4 = (String[])var3.toArray(new String[var3.size()]);
         }

         return new BrowserCompatSpec(var4);
      } else {
         return new BrowserCompatSpec();
      }
   }
}
