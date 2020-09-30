package org.apache.http.cookie;

import java.util.List;
import org.apache.http.Header;

public interface CookieSpec {
   List<Header> formatCookies(List<Cookie> var1);

   int getVersion();

   Header getVersionHeader();

   boolean match(Cookie var1, CookieOrigin var2);

   List<Cookie> parse(Header var1, CookieOrigin var2) throws MalformedCookieException;

   void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException;
}
