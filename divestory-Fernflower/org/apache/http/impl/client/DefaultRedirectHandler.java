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
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Deprecated
public class DefaultRedirectHandler implements RedirectHandler {
   private static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
   private final Log log = LogFactory.getLog(this.getClass());

   public URI getLocationURI(HttpResponse var1, HttpContext var2) throws ProtocolException {
      if (var1 != null) {
         Header var3 = var1.getFirstHeader("location");
         if (var3 != null) {
            String var4 = var3.getValue();
            if (this.log.isDebugEnabled()) {
               Log var16 = this.log;
               StringBuilder var5 = new StringBuilder();
               var5.append("Redirect requested to location '");
               var5.append(var4);
               var5.append("'");
               var16.debug(var5.toString());
            }

            StringBuilder var10;
            URI var17;
            try {
               var17 = new URI(var4);
            } catch (URISyntaxException var9) {
               var10 = new StringBuilder();
               var10.append("Invalid redirect URI: ");
               var10.append(var4);
               throw new ProtocolException(var10.toString(), var9);
            }

            HttpParams var19 = var1.getParams();
            URI var11 = var17;
            if (!var17.isAbsolute()) {
               if (var19.isParameterTrue("http.protocol.reject-relative-redirect")) {
                  var10 = new StringBuilder();
                  var10.append("Relative redirect location '");
                  var10.append(var17);
                  var10.append("' not allowed");
                  throw new ProtocolException(var10.toString());
               }

               HttpHost var6 = (HttpHost)var2.getAttribute("http.target_host");
               if (var6 == null) {
                  throw new IllegalStateException("Target host not available in the HTTP context");
               }

               HttpRequest var12 = (HttpRequest)var2.getAttribute("http.request");

               try {
                  URI var21 = new URI(var12.getRequestLine().getUri());
                  var11 = URIUtils.resolve(URIUtils.rewriteURI(var21, var6, true), var17);
               } catch (URISyntaxException var8) {
                  throw new ProtocolException(var8.getMessage(), var8);
               }
            }

            if (var19.isParameterFalse("http.protocol.allow-circular-redirects")) {
               RedirectLocations var20 = (RedirectLocations)var2.getAttribute("http.protocol.redirect-locations");
               RedirectLocations var18 = var20;
               if (var20 == null) {
                  var18 = new RedirectLocations();
                  var2.setAttribute("http.protocol.redirect-locations", var18);
               }

               URI var15;
               if (var11.getFragment() != null) {
                  try {
                     HttpHost var14 = new HttpHost(var11.getHost(), var11.getPort(), var11.getScheme());
                     var15 = URIUtils.rewriteURI(var11, var14, true);
                  } catch (URISyntaxException var7) {
                     throw new ProtocolException(var7.getMessage(), var7);
                  }
               } else {
                  var15 = var11;
               }

               if (var18.contains(var15)) {
                  var10 = new StringBuilder();
                  var10.append("Circular redirect to '");
                  var10.append(var15);
                  var10.append("'");
                  throw new CircularRedirectException(var10.toString());
               }

               var18.add(var15);
            }

            return var11;
         } else {
            StringBuilder var13 = new StringBuilder();
            var13.append("Received redirect response ");
            var13.append(var1.getStatusLine());
            var13.append(" but no location header");
            throw new ProtocolException(var13.toString());
         }
      } else {
         throw new IllegalArgumentException("HTTP response may not be null");
      }
   }

   public boolean isRedirectRequested(HttpResponse var1, HttpContext var2) {
      if (var1 != null) {
         int var3 = var1.getStatusLine().getStatusCode();
         boolean var4 = false;
         if (var3 != 307) {
            switch(var3) {
            case 301:
            case 302:
               break;
            case 303:
               return true;
            default:
               return false;
            }
         }

         String var5 = ((HttpRequest)var2.getAttribute("http.request")).getRequestLine().getMethod();
         if (var5.equalsIgnoreCase("GET") || var5.equalsIgnoreCase("HEAD")) {
            var4 = true;
         }

         return var4;
      } else {
         throw new IllegalArgumentException("HTTP response may not be null");
      }
   }
}
