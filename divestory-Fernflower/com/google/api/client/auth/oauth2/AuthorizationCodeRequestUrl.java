package com.google.api.client.auth.oauth2;

import java.util.Collection;
import java.util.Collections;

public class AuthorizationCodeRequestUrl extends AuthorizationRequestUrl {
   public AuthorizationCodeRequestUrl(String var1, String var2) {
      super(var1, var2, Collections.singleton("code"));
   }

   public AuthorizationCodeRequestUrl clone() {
      return (AuthorizationCodeRequestUrl)super.clone();
   }

   public AuthorizationCodeRequestUrl set(String var1, Object var2) {
      return (AuthorizationCodeRequestUrl)super.set(var1, var2);
   }

   public AuthorizationCodeRequestUrl setClientId(String var1) {
      return (AuthorizationCodeRequestUrl)super.setClientId(var1);
   }

   public AuthorizationCodeRequestUrl setRedirectUri(String var1) {
      return (AuthorizationCodeRequestUrl)super.setRedirectUri(var1);
   }

   public AuthorizationCodeRequestUrl setResponseTypes(Collection<String> var1) {
      return (AuthorizationCodeRequestUrl)super.setResponseTypes(var1);
   }

   public AuthorizationCodeRequestUrl setScopes(Collection<String> var1) {
      return (AuthorizationCodeRequestUrl)super.setScopes(var1);
   }

   public AuthorizationCodeRequestUrl setState(String var1) {
      return (AuthorizationCodeRequestUrl)super.setState(var1);
   }
}
