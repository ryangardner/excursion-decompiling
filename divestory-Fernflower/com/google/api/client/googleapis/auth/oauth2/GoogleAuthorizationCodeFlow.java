package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.CredentialStore;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.util.Collection;

public class GoogleAuthorizationCodeFlow extends AuthorizationCodeFlow {
   private final String accessType;
   private final String approvalPrompt;

   protected GoogleAuthorizationCodeFlow(GoogleAuthorizationCodeFlow.Builder var1) {
      super(var1);
      this.accessType = var1.accessType;
      this.approvalPrompt = var1.approvalPrompt;
   }

   public GoogleAuthorizationCodeFlow(HttpTransport var1, JsonFactory var2, String var3, String var4, Collection<String> var5) {
      this(new GoogleAuthorizationCodeFlow.Builder(var1, var2, var3, var4, var5));
   }

   public final String getAccessType() {
      return this.accessType;
   }

   public final String getApprovalPrompt() {
      return this.approvalPrompt;
   }

   public GoogleAuthorizationCodeRequestUrl newAuthorizationUrl() {
      return (new GoogleAuthorizationCodeRequestUrl(this.getAuthorizationServerEncodedUrl(), this.getClientId(), "", this.getScopes())).setAccessType(this.accessType).setApprovalPrompt(this.approvalPrompt);
   }

   public GoogleAuthorizationCodeTokenRequest newTokenRequest(String var1) {
      return (new GoogleAuthorizationCodeTokenRequest(this.getTransport(), this.getJsonFactory(), this.getTokenServerEncodedUrl(), "", "", var1, "")).setClientAuthentication(this.getClientAuthentication()).setRequestInitializer(this.getRequestInitializer()).setScopes(this.getScopes());
   }

   public static class Builder extends AuthorizationCodeFlow.Builder {
      String accessType;
      String approvalPrompt;

      public Builder(HttpTransport var1, JsonFactory var2, GoogleClientSecrets var3, Collection<String> var4) {
         super(BearerToken.authorizationHeaderAccessMethod(), var1, var2, new GenericUrl("https://oauth2.googleapis.com/token"), new ClientParametersAuthentication(var3.getDetails().getClientId(), var3.getDetails().getClientSecret()), var3.getDetails().getClientId(), "https://accounts.google.com/o/oauth2/auth");
         this.setScopes(var4);
      }

      public Builder(HttpTransport var1, JsonFactory var2, String var3, String var4, Collection<String> var5) {
         super(BearerToken.authorizationHeaderAccessMethod(), var1, var2, new GenericUrl("https://oauth2.googleapis.com/token"), new ClientParametersAuthentication(var3, var4), var3, "https://accounts.google.com/o/oauth2/auth");
         this.setScopes(var5);
      }

      public GoogleAuthorizationCodeFlow.Builder addRefreshListener(CredentialRefreshListener var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.addRefreshListener(var1);
      }

      public GoogleAuthorizationCodeFlow build() {
         return new GoogleAuthorizationCodeFlow(this);
      }

      public final String getAccessType() {
         return this.accessType;
      }

      public final String getApprovalPrompt() {
         return this.approvalPrompt;
      }

      public GoogleAuthorizationCodeFlow.Builder setAccessType(String var1) {
         this.accessType = var1;
         return this;
      }

      public GoogleAuthorizationCodeFlow.Builder setApprovalPrompt(String var1) {
         this.approvalPrompt = var1;
         return this;
      }

      public GoogleAuthorizationCodeFlow.Builder setAuthorizationServerEncodedUrl(String var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setAuthorizationServerEncodedUrl(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setClientAuthentication(HttpExecuteInterceptor var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setClientAuthentication(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setClientId(String var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setClientId(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setClock(Clock var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setClock(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setCredentialCreatedListener(AuthorizationCodeFlow.CredentialCreatedListener var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setCredentialCreatedListener(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setCredentialDataStore(DataStore<StoredCredential> var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setCredentialDataStore(var1);
      }

      @Deprecated
      public GoogleAuthorizationCodeFlow.Builder setCredentialStore(CredentialStore var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setCredentialStore(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setDataStoreFactory(DataStoreFactory var1) throws IOException {
         return (GoogleAuthorizationCodeFlow.Builder)super.setDataStoreFactory(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setJsonFactory(JsonFactory var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setJsonFactory(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setMethod(Credential.AccessMethod var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setMethod(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setRefreshListeners(Collection<CredentialRefreshListener> var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setRefreshListeners(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setRequestInitializer(HttpRequestInitializer var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setRequestInitializer(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setScopes(Collection<String> var1) {
         Preconditions.checkState(var1.isEmpty() ^ true);
         return (GoogleAuthorizationCodeFlow.Builder)super.setScopes(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setTokenServerUrl(GenericUrl var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setTokenServerUrl(var1);
      }

      public GoogleAuthorizationCodeFlow.Builder setTransport(HttpTransport var1) {
         return (GoogleAuthorizationCodeFlow.Builder)super.setTransport(var1);
      }
   }
}
