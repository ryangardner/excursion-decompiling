package org.apache.http.protocol;

import java.util.Map;

public class HttpRequestHandlerRegistry implements HttpRequestHandlerResolver {
   private final UriPatternMatcher matcher = new UriPatternMatcher();

   public HttpRequestHandler lookup(String var1) {
      return (HttpRequestHandler)this.matcher.lookup(var1);
   }

   protected boolean matchUriRequestPattern(String var1, String var2) {
      return this.matcher.matchUriRequestPattern(var1, var2);
   }

   public void register(String var1, HttpRequestHandler var2) {
      if (var1 != null) {
         if (var2 != null) {
            this.matcher.register(var1, var2);
         } else {
            throw new IllegalArgumentException("Request handler may not be null");
         }
      } else {
         throw new IllegalArgumentException("URI request pattern may not be null");
      }
   }

   public void setHandlers(Map var1) {
      this.matcher.setObjects(var1);
   }

   public void unregister(String var1) {
      this.matcher.unregister(var1);
   }
}
