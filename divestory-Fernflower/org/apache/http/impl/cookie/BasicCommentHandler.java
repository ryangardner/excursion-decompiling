package org.apache.http.impl.cookie;

import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

public class BasicCommentHandler extends AbstractCookieAttributeHandler {
   public void parse(SetCookie var1, String var2) throws MalformedCookieException {
      if (var1 != null) {
         var1.setComment(var2);
      } else {
         throw new IllegalArgumentException("Cookie may not be null");
      }
   }
}
