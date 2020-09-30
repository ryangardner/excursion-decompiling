package com.google.api.client.auth.oauth2;

import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;

public final class DataStoreCredentialRefreshListener implements CredentialRefreshListener {
   private final DataStore<StoredCredential> credentialDataStore;
   private final String userId;

   public DataStoreCredentialRefreshListener(String var1, DataStore<StoredCredential> var2) {
      this.userId = (String)Preconditions.checkNotNull(var1);
      this.credentialDataStore = (DataStore)Preconditions.checkNotNull(var2);
   }

   public DataStoreCredentialRefreshListener(String var1, DataStoreFactory var2) throws IOException {
      this(var1, StoredCredential.getDefaultDataStore(var2));
   }

   public DataStore<StoredCredential> getCredentialDataStore() {
      return this.credentialDataStore;
   }

   public void makePersistent(Credential var1) throws IOException {
      this.credentialDataStore.set(this.userId, new StoredCredential(var1));
   }

   public void onTokenErrorResponse(Credential var1, TokenErrorResponse var2) throws IOException {
      this.makePersistent(var1);
   }

   public void onTokenResponse(Credential var1, TokenResponse var2) throws IOException {
      this.makePersistent(var1);
   }
}
