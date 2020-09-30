package com.google.api.client.auth.oauth2;

import java.io.IOException;

@Deprecated
public interface CredentialStore {
   void delete(String var1, Credential var2) throws IOException;

   boolean load(String var1, Credential var2) throws IOException;

   void store(String var1, Credential var2) throws IOException;
}
