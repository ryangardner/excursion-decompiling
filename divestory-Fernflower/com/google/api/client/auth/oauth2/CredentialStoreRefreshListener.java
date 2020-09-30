package com.google.api.client.auth.oauth2;

import com.google.api.client.util.Preconditions;
import java.io.IOException;

@Deprecated
public final class CredentialStoreRefreshListener implements CredentialRefreshListener {
   private final CredentialStore credentialStore;
   private final String userId;

   public CredentialStoreRefreshListener(String var1, CredentialStore var2) {
      this.userId = (String)Preconditions.checkNotNull(var1);
      this.credentialStore = (CredentialStore)Preconditions.checkNotNull(var2);
   }

   public CredentialStore getCredentialStore() {
      return this.credentialStore;
   }

   public void makePersistent(Credential var1) throws IOException {
      this.credentialStore.store(this.userId, var1);
   }

   public void onTokenErrorResponse(Credential var1, TokenErrorResponse var2) throws IOException {
      this.makePersistent(var1);
   }

   public void onTokenResponse(Credential var1, TokenResponse var2) throws IOException {
      this.makePersistent(var1);
   }
}
