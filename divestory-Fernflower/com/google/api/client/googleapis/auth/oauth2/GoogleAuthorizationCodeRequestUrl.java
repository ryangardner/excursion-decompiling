package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

public class GoogleAuthorizationCodeRequestUrl extends AuthorizationCodeRequestUrl {
   @Key("access_type")
   private String accessType;
   @Key("approval_prompt")
   private String approvalPrompt;

   public GoogleAuthorizationCodeRequestUrl(GoogleClientSecrets var1, String var2, Collection<String> var3) {
      this(var1.getDetails().getClientId(), var2, var3);
   }

   public GoogleAuthorizationCodeRequestUrl(String var1, String var2, String var3, Collection<String> var4) {
      super(var1, var2);
      this.setRedirectUri(var3);
      this.setScopes(var4);
   }

   public GoogleAuthorizationCodeRequestUrl(String var1, String var2, Collection<String> var3) {
      this("https://accounts.google.com/o/oauth2/auth", var1, var2, var3);
   }

   public GoogleAuthorizationCodeRequestUrl clone() {
      return (GoogleAuthorizationCodeRequestUrl)super.clone();
   }

   public final String getAccessType() {
      return this.accessType;
   }

   public final String getApprovalPrompt() {
      return this.approvalPrompt;
   }

   public GoogleAuthorizationCodeRequestUrl set(String var1, Object var2) {
      return (GoogleAuthorizationCodeRequestUrl)super.set(var1, var2);
   }

   public GoogleAuthorizationCodeRequestUrl setAccessType(String var1) {
      this.accessType = var1;
      return this;
   }

   public GoogleAuthorizationCodeRequestUrl setApprovalPrompt(String var1) {
      this.approvalPrompt = var1;
      return this;
   }

   public GoogleAuthorizationCodeRequestUrl setClientId(String var1) {
      return (GoogleAuthorizationCodeRequestUrl)super.setClientId(var1);
   }

   public GoogleAuthorizationCodeRequestUrl setRedirectUri(String var1) {
      Preconditions.checkNotNull(var1);
      return (GoogleAuthorizationCodeRequestUrl)super.setRedirectUri(var1);
   }

   public GoogleAuthorizationCodeRequestUrl setResponseTypes(Collection<String> var1) {
      return (GoogleAuthorizationCodeRequestUrl)super.setResponseTypes(var1);
   }

   public GoogleAuthorizationCodeRequestUrl setScopes(Collection<String> var1) {
      Preconditions.checkArgument(var1.iterator().hasNext());
      return (GoogleAuthorizationCodeRequestUrl)super.setScopes(var1);
   }

   public GoogleAuthorizationCodeRequestUrl setState(String var1) {
      return (GoogleAuthorizationCodeRequestUrl)super.setState(var1);
   }
}
