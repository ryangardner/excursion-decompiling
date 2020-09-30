package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

public class AuthorizationRequestUrl extends GenericUrl {
   @Key("client_id")
   private String clientId;
   @Key("redirect_uri")
   private String redirectUri;
   @Key("response_type")
   private String responseTypes;
   @Key("scope")
   private String scopes;
   @Key
   private String state;

   public AuthorizationRequestUrl(String var1, String var2, Collection<String> var3) {
      super(var1);
      boolean var4;
      if (this.getFragment() == null) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4);
      this.setClientId(var2);
      this.setResponseTypes(var3);
   }

   public AuthorizationRequestUrl clone() {
      return (AuthorizationRequestUrl)super.clone();
   }

   public final String getClientId() {
      return this.clientId;
   }

   public final String getRedirectUri() {
      return this.redirectUri;
   }

   public final String getResponseTypes() {
      return this.responseTypes;
   }

   public final String getScopes() {
      return this.scopes;
   }

   public final String getState() {
      return this.state;
   }

   public AuthorizationRequestUrl set(String var1, Object var2) {
      return (AuthorizationRequestUrl)super.set(var1, var2);
   }

   public AuthorizationRequestUrl setClientId(String var1) {
      this.clientId = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public AuthorizationRequestUrl setRedirectUri(String var1) {
      this.redirectUri = var1;
      return this;
   }

   public AuthorizationRequestUrl setResponseTypes(Collection<String> var1) {
      this.responseTypes = Joiner.on(' ').join(var1);
      return this;
   }

   public AuthorizationRequestUrl setScopes(Collection<String> var1) {
      String var2;
      if (var1 != null && var1.iterator().hasNext()) {
         var2 = Joiner.on(' ').join(var1);
      } else {
         var2 = null;
      }

      this.scopes = var2;
      return this;
   }

   public AuthorizationRequestUrl setState(String var1) {
      this.state = var1;
      return this;
   }
}
