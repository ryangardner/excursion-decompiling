/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;

public interface CredentialsProvider {
    public void clear();

    public Credentials getCredentials(AuthScope var1);

    public void setCredentials(AuthScope var1, Credentials var2);
}

