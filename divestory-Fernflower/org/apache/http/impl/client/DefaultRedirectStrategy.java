package org.apache.http.impl.client;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class DefaultRedirectStrategy implements RedirectStrategy {
   public static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
   private final Log log = LogFactory.getLog(this.getClass());

   protected URI createLocationURI(String var1) throws ProtocolException {
      try {
         URI var5 = new URI(var1);
         return var5;
      } catch (URISyntaxException var4) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Invalid redirect URI: ");
         var2.append(var1);
         throw new ProtocolException(var2.toString(), var4);
      }
   }

   public URI getLocationURI(HttpRequest var1, HttpResponse var2, HttpContext var3) throws ProtocolException {
      if (var2 != null) {
         Header var4 = var2.getFirstHeader("location");
         StringBuilder var9;
         if (var4 != null) {
            String var5 = var4.getValue();
            if (this.log.isDebugEnabled()) {
               Log var15 = this.log;
               StringBuilder var6 = new StringBuilder();
               var6.append("Redirect requested to location '");
               var6.append(var5);
               var6.append("'");
               var15.debug(var6.toString());
            }

            URI var16 = this.createLocationURI(var5);
            HttpParams var19 = var2.getParams();
            URI var11 = var16;
            if (!var16.isAbsolute()) {
               if (var19.isParameterTrue("http.protocol.reject-relative-redirect")) {
                  var9 = new StringBuilder();
                  var9.append("Relative redirect location '");
                  var9.append(var16);
                  var9.append("' not allowed");
                  throw new ProtocolException(var9.toString());
               }

               HttpHost var12 = (HttpHost)var3.getAttribute("http.target_host");
               if (var12 == null) {
                  throw new IllegalStateException("Target host not available in the HTTP context");
               }

               try {
                  URI var18 = new URI(var1.getRequestLine().getUri());
                  var11 = URIUtils.resolve(URIUtils.rewriteURI(var18, var12, true), var16);
               } catch (URISyntaxException var8) {
                  throw new ProtocolException(var8.getMessage(), var8);
               }
            }

            if (var19.isParameterFalse("http.protocol.allow-circular-redirects")) {
               RedirectLocations var17 = (RedirectLocations)var3.getAttribute("http.protocol.redirect-locations");
               RedirectLocations var10 = var17;
               if (var17 == null) {
                  var10 = new RedirectLocations();
                  var3.setAttribute("http.protocol.redirect-locations", var10);
               }

               URI var14;
               if (var11.getFragment() != null) {
                  try {
                     HttpHost var13 = new HttpHost(var11.getHost(), var11.getPort(), var11.getScheme());
                     var14 = URIUtils.rewriteURI(var11, var13, true);
                  } catch (URISyntaxException var7) {
                     throw new ProtocolException(var7.getMessage(), var7);
                  }
               } else {
                  var14 = var11;
               }

               if (var10.contains(var14)) {
                  var9 = new StringBuilder();
                  var9.append("Circular redirect to '");
                  var9.append(var14);
                  var9.append("'");
                  throw new CircularRedirectException(var9.toString());
               }

               var10.add(var14);
            }

            return var11;
         } else {
            var9 = new StringBuilder();
            var9.append("Received redirect response ");
            var9.append(var2.getStatusLine());
            var9.append(" but no location header");
            throw new ProtocolException(var9.toString());
         }
      } else {
         throw new IllegalArgumentException("HTTP response may not be null");
      }
   }

   public HttpUriRequest getRedirect(HttpRequest var1, HttpResponse var2, HttpContext var3) throws ProtocolException {
      URI var4 = this.getLocationURI(var1, var2, var3);
      return (HttpUriRequest)(var1.getRequestLine().getMethod().equalsIgnoreCase("HEAD") ? new HttpHead(var4) : new HttpGet(var4));
   }

   public boolean isRedirected(HttpRequest var1, HttpResponse var2, HttpContext var3) throws ProtocolException {
      if (var2 == null) {
         throw new IllegalArgumentException("HTTP response may not be null");
      } else {
         int var4 = var2.getStatusLine().getStatusCode();
         String var7 = var1.getRequestLine().getMethod();
         Header var8 = var2.getFirstHeader("location");
         boolean var5 = false;
         boolean var6 = false;
         if (var4 != 307) {
            switch(var4) {
            case 301:
               break;
            case 302:
               if (!var7.equalsIgnoreCase("GET")) {
                  var5 = var6;
                  if (!var7.equalsIgnoreCase("HEAD")) {
                     return var5;
                  }
               }

               var5 = var6;
               if (var8 != null) {
                  var5 = true;
               }

               return var5;
            case 303:
               return true;
            default:
               return false;
            }
         }

         if (var7.equalsIgnoreCase("GET") || var7.equalsIgnoreCase("HEAD")) {
            var5 = true;
         }

         return var5;
      }
   }
}
