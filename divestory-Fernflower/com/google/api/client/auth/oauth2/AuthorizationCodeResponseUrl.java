package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;

public class AuthorizationCodeResponseUrl extends GenericUrl {
   @Key
   private String code;
   @Key
   private String error;
   @Key("error_description")
   private String errorDescription;
   @Key("error_uri")
   private String errorUri;
   @Key
   private String state;

   public AuthorizationCodeResponseUrl(String var1) {
      super(var1);
      var1 = this.code;
      boolean var2 = true;
      boolean var3;
      if (var1 == null) {
         var3 = true;
      } else {
         var3 = false;
      }

      boolean var4;
      if (this.error == null) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var3 == var4) {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
   }

   public AuthorizationCodeResponseUrl clone() {
      return (AuthorizationCodeResponseUrl)super.clone();
   }

   public final String getCode() {
      return this.code;
   }

   public final String getError() {
      return this.error;
   }

   public final String getErrorDescription() {
      return this.errorDescription;
   }

   public final String getErrorUri() {
      return this.errorUri;
   }

   public final String getState() {
      return this.state;
   }

   public AuthorizationCodeResponseUrl set(String var1, Object var2) {
      return (AuthorizationCodeResponseUrl)super.set(var1, var2);
   }

   public AuthorizationCodeResponseUrl setCode(String var1) {
      this.code = var1;
      return this;
   }

   public AuthorizationCodeResponseUrl setError(String var1) {
      this.error = var1;
      return this;
   }

   public AuthorizationCodeResponseUrl setErrorDescription(String var1) {
      this.errorDescription = var1;
      return this;
   }

   public AuthorizationCodeResponseUrl setErrorUri(String var1) {
      this.errorUri = var1;
      return this;
   }

   public AuthorizationCodeResponseUrl setState(String var1) {
      this.state = var1;
      return this;
   }
}
