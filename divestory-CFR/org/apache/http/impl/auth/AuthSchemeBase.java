/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.auth;

import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;

public abstract class AuthSchemeBase
implements ContextAwareAuthScheme {
    private boolean proxy;

    @Override
    public Header authenticate(Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest);
    }

    public boolean isProxy() {
        return this.proxy;
    }

    protected abstract void parseChallenge(CharArrayBuffer var1, int var2, int var3) throws MalformedChallengeException;

    @Override
    public void processChallenge(Header object) throws MalformedChallengeException {
        int n;
        if (object == null) throw new IllegalArgumentException("Header may not be null");
        Object object2 = object.getName();
        boolean bl = ((String)object2).equalsIgnoreCase("WWW-Authenticate");
        int n2 = 0;
        if (bl) {
            this.proxy = false;
        } else {
            if (!((String)object2).equalsIgnoreCase("Proxy-Authenticate")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected header name: ");
                ((StringBuilder)object).append((String)object2);
                throw new MalformedChallengeException(((StringBuilder)object).toString());
            }
            this.proxy = true;
        }
        if (object instanceof FormattedHeader) {
            object2 = (FormattedHeader)object;
            object = object2.getBuffer();
            n2 = object2.getValuePos();
        } else {
            object2 = object.getValue();
            if (object2 == null) throw new MalformedChallengeException("Header value is null");
            object = new CharArrayBuffer(((String)object2).length());
            ((CharArrayBuffer)object).append((String)object2);
        }
        while (n2 < ((CharArrayBuffer)object).length() && HTTP.isWhitespace(((CharArrayBuffer)object).charAt(n2))) {
            ++n2;
        }
        for (n = n2; n < ((CharArrayBuffer)object).length() && !HTTP.isWhitespace(((CharArrayBuffer)object).charAt(n)); ++n) {
        }
        object2 = ((CharArrayBuffer)object).substring(n2, n);
        if (((String)object2).equalsIgnoreCase(this.getSchemeName())) {
            this.parseChallenge((CharArrayBuffer)object, n, ((CharArrayBuffer)object).length());
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid scheme identifier: ");
        ((StringBuilder)object).append((String)object2);
        throw new MalformedChallengeException(((StringBuilder)object).toString());
    }

    public String toString() {
        return this.getSchemeName();
    }
}

