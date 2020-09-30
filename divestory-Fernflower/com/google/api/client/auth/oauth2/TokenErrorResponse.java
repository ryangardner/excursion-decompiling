package com.google.api.client.auth.oauth2;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;

public class TokenErrorResponse extends GenericJson {
   @Key
   private String error;
   @Key("error_description")
   private String errorDescription;
   @Key("error_uri")
   private String errorUri;

   public TokenErrorResponse clone() {
      return (TokenErrorResponse)super.clone();
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

   public TokenErrorResponse set(String var1, Object var2) {
      return (TokenErrorResponse)super.set(var1, var2);
   }

   public TokenErrorResponse setError(String var1) {
      this.error = (String)Preconditions.checkNotNull(var1);
      return this;
   }

   public TokenErrorResponse setErrorDescription(String var1) {
      this.errorDescription = var1;
      return this;
   }

   public TokenErrorResponse setErrorUri(String var1) {
      this.errorUri = var1;
      return this;
   }
}
