package com.google.api.client.auth.oauth2;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.util.Data;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class BearerToken {
   static final Pattern INVALID_TOKEN_ERROR = Pattern.compile("\\s*error\\s*=\\s*\"?invalid_token\"?");
   static final String PARAM_NAME = "access_token";

   public static Credential.AccessMethod authorizationHeaderAccessMethod() {
      return new BearerToken.AuthorizationHeaderAccessMethod();
   }

   public static Credential.AccessMethod formEncodedBodyAccessMethod() {
      return new BearerToken.FormEncodedBodyAccessMethod();
   }

   public static Credential.AccessMethod queryParameterAccessMethod() {
      return new BearerToken.QueryParameterAccessMethod();
   }

   static final class AuthorizationHeaderAccessMethod implements Credential.AccessMethod {
      static final String HEADER_PREFIX = "Bearer ";

      public String getAccessTokenFromRequest(HttpRequest var1) {
         List var3 = var1.getHeaders().getAuthorizationAsList();
         if (var3 != null) {
            Iterator var2 = var3.iterator();

            while(var2.hasNext()) {
               String var4 = (String)var2.next();
               if (var4.startsWith("Bearer ")) {
                  return var4.substring(7);
               }
            }
         }

         return null;
      }

      public void intercept(HttpRequest var1, String var2) throws IOException {
         HttpHeaders var4 = var1.getHeaders();
         StringBuilder var3 = new StringBuilder();
         var3.append("Bearer ");
         var3.append(var2);
         var4.setAuthorization(var3.toString());
      }
   }

   static final class FormEncodedBodyAccessMethod implements Credential.AccessMethod {
      private static Map<String, Object> getData(HttpRequest var0) {
         return Data.mapOf(UrlEncodedContent.getContent(var0).getData());
      }

      public String getAccessTokenFromRequest(HttpRequest var1) {
         Object var2 = getData(var1).get("access_token");
         String var3;
         if (var2 == null) {
            var3 = null;
         } else {
            var3 = var2.toString();
         }

         return var3;
      }

      public void intercept(HttpRequest var1, String var2) throws IOException {
         Preconditions.checkArgument("GET".equals(var1.getRequestMethod()) ^ true, "HTTP GET method is not supported");
         getData(var1).put("access_token", var2);
      }
   }

   static final class QueryParameterAccessMethod implements Credential.AccessMethod {
      public String getAccessTokenFromRequest(HttpRequest var1) {
         Object var2 = var1.getUrl().get("access_token");
         String var3;
         if (var2 == null) {
            var3 = null;
         } else {
            var3 = var2.toString();
         }

         return var3;
      }

      public void intercept(HttpRequest var1, String var2) throws IOException {
         var1.getUrl().set("access_token", var2);
      }
   }
}
