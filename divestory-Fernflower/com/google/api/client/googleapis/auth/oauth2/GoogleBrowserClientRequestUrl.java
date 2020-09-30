package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.BrowserClientRequestUrl;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

public class GoogleBrowserClientRequestUrl extends BrowserClientRequestUrl {
   @Key("approval_prompt")
   private String approvalPrompt;

   public GoogleBrowserClientRequestUrl(GoogleClientSecrets var1, String var2, Collection<String> var3) {
      this(var1.getDetails().getClientId(), var2, var3);
   }

   public GoogleBrowserClientRequestUrl(String var1, String var2, Collection<String> var3) {
      super("https://accounts.google.com/o/oauth2/auth", var1);
      this.setRedirectUri(var2);
      this.setScopes(var3);
   }

   public GoogleBrowserClientRequestUrl clone() {
      return (GoogleBrowserClientRequestUrl)super.clone();
   }

   public final String getApprovalPrompt() {
      return this.approvalPrompt;
   }

   public GoogleBrowserClientRequestUrl set(String var1, Object var2) {
      return (GoogleBrowserClientRequestUrl)super.set(var1, var2);
   }

   public GoogleBrowserClientRequestUrl setApprovalPrompt(String var1) {
      this.approvalPrompt = var1;
      return this;
   }

   public GoogleBrowserClientRequestUrl setClientId(String var1) {
      return (GoogleBrowserClientRequestUrl)super.setClientId(var1);
   }

   public GoogleBrowserClientRequestUrl setRedirectUri(String var1) {
      return (GoogleBrowserClientRequestUrl)super.setRedirectUri(var1);
   }

   public GoogleBrowserClientRequestUrl setResponseTypes(Collection<String> var1) {
      return (GoogleBrowserClientRequestUrl)super.setResponseTypes(var1);
   }

   public GoogleBrowserClientRequestUrl setScopes(Collection<String> var1) {
      Preconditions.checkArgument(var1.iterator().hasNext());
      return (GoogleBrowserClientRequestUrl)super.setScopes(var1);
   }

   public GoogleBrowserClientRequestUrl setState(String var1) {
      return (GoogleBrowserClientRequestUrl)super.setState(var1);
   }
}
