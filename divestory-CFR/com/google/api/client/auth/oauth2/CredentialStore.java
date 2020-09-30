/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.Credential;
import java.io.IOException;

@Deprecated
public interface CredentialStore {
    public void delete(String var1, Credential var2) throws IOException;

    public boolean load(String var1, Credential var2) throws IOException;

    public void store(String var1, Credential var2) throws IOException;
}

