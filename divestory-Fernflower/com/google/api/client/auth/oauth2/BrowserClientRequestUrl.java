package com.google.api.client.auth.oauth2;

import java.util.Collection;
import java.util.Collections;

public class BrowserClientRequestUrl extends AuthorizationRequestUrl {
   public BrowserClientRequestUrl(String var1, String var2) {
      super(var1, var2, Collections.singleton("token"));
   }

   public BrowserClientRequestUrl clone() {
      return (BrowserClientRequestUrl)super.clone();
   }

   public BrowserClientRequestUrl set(String var1, Object var2) {
      return (BrowserClientRequestUrl)super.set(var1, var2);
   }

   public BrowserClientRequestUrl setClientId(String var1) {
      return (BrowserClientRequestUrl)super.setClientId(var1);
   }

   public BrowserClientRequestUrl setRedirectUri(String var1) {
      return (BrowserClientRequestUrl)super.setRedirectUri(var1);
   }

   public BrowserClientRequestUrl setResponseTypes(Collection<String> var1) {
      return (BrowserClientRequestUrl)super.setResponseTypes(var1);
   }

   public BrowserClientRequestUrl setScopes(Collection<String> var1) {
      return (BrowserClientRequestUrl)super.setScopes(var1);
   }

   public BrowserClientRequestUrl setState(String var1) {
      return (BrowserClientRequestUrl)super.setState(var1);
   }
}
