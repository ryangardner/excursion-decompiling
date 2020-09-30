/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;

public interface AuthScheme {
    @Deprecated
    public Header authenticate(Credentials var1, HttpRequest var2) throws AuthenticationException;

    public String getParameter(String var1);

    public String getRealm();

    public String getSchemeName();

    public boolean isComplete();

    public boolean isConnectionBased();

    public void processChallenge(Header var1) throws MalformedChallengeException;
}

