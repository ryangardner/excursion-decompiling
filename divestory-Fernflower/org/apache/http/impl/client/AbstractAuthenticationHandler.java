package org.apache.http.impl.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractAuthenticationHandler implements AuthenticationHandler {
   private static final List<String> DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList(Arrays.asList("negotiate", "NTLM", "Digest", "Basic"));
   private final Log log = LogFactory.getLog(this.getClass());

   protected List<String> getAuthPreferences() {
      return DEFAULT_SCHEME_PRIORITY;
   }

   protected List<String> getAuthPreferences(HttpResponse var1, HttpContext var2) {
      return this.getAuthPreferences();
   }

   protected Map<String, Header> parseChallenges(Header[] var1) throws MalformedChallengeException {
      HashMap var2 = new HashMap(var1.length);
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Header var5 = var1[var4];
         CharArrayBuffer var7;
         int var8;
         if (var5 instanceof FormattedHeader) {
            FormattedHeader var6 = (FormattedHeader)var5;
            var7 = var6.getBuffer();
            var8 = var6.getValuePos();
         } else {
            String var10 = var5.getValue();
            if (var10 == null) {
               throw new MalformedChallengeException("Header value is null");
            }

            var7 = new CharArrayBuffer(var10.length());
            var7.append(var10);
            var8 = 0;
         }

         while(var8 < var7.length() && HTTP.isWhitespace(var7.charAt(var8))) {
            ++var8;
         }

         int var9;
         for(var9 = var8; var9 < var7.length() && !HTTP.isWhitespace(var7.charAt(var9)); ++var9) {
         }

         var2.put(var7.substring(var8, var9).toLowerCase(Locale.ENGLISH), var5);
      }

      return var2;
   }

   public AuthScheme selectScheme(Map<String, Header> var1, HttpResponse var2, HttpContext var3) throws AuthenticationException {
      AuthSchemeRegistry var4 = (AuthSchemeRegistry)var3.getAttribute("http.authscheme-registry");
      if (var4 == null) {
         throw new IllegalStateException("AuthScheme registry not set in HTTP context");
      } else {
         List var5 = this.getAuthPreferences(var2, var3);
         List var11 = var5;
         if (var5 == null) {
            var11 = DEFAULT_SCHEME_PRIORITY;
         }

         if (this.log.isDebugEnabled()) {
            Log var6 = this.log;
            StringBuilder var14 = new StringBuilder();
            var14.append("Authentication schemes in the order of preference: ");
            var14.append(var11);
            var6.debug(var14.toString());
         }

         var5 = null;
         Iterator var16 = var11.iterator();

         AuthScheme var12;
         while(true) {
            var12 = var5;
            if (!var16.hasNext()) {
               break;
            }

            String var7 = (String)var16.next();
            if ((Header)var1.get(var7.toLowerCase(Locale.ENGLISH)) != null) {
               Log var15;
               StringBuilder var17;
               if (this.log.isDebugEnabled()) {
                  var15 = this.log;
                  var17 = new StringBuilder();
                  var17.append(var7);
                  var17.append(" authentication scheme selected");
                  var15.debug(var17.toString());
               }

               try {
                  var12 = var4.getAuthScheme(var7, var2.getParams());
                  break;
               } catch (IllegalStateException var9) {
                  if (this.log.isWarnEnabled()) {
                     var15 = this.log;
                     var17 = new StringBuilder();
                     var17.append("Authentication scheme ");
                     var17.append(var7);
                     var17.append(" not supported");
                     var15.warn(var17.toString());
                  }
               }
            } else if (this.log.isDebugEnabled()) {
               Log var8 = this.log;
               StringBuilder var13 = new StringBuilder();
               var13.append("Challenge for ");
               var13.append(var7);
               var13.append(" authentication scheme not available");
               var8.debug(var13.toString());
            }
         }

         if (var12 != null) {
            return var12;
         } else {
            StringBuilder var10 = new StringBuilder();
            var10.append("Unable to respond to any of these challenges: ");
            var10.append(var1);
            throw new AuthenticationException(var10.toString());
         }
      }
   }
}
