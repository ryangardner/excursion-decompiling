/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.auth;

import java.security.Principal;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.params.AuthParams;
import org.apache.http.impl.auth.RFC2617Scheme;
import org.apache.http.message.BufferedHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class BasicScheme
extends RFC2617Scheme {
    private boolean complete = false;

    public static Header authenticate(Credentials object, String arrby, boolean bl) {
        if (object == null) throw new IllegalArgumentException("Credentials may not be null");
        if (arrby == null) throw new IllegalArgumentException("charset may not be null");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(object.getUserPrincipal().getName());
        stringBuilder.append(":");
        object = object.getPassword() == null ? "null" : object.getPassword();
        stringBuilder.append((String)object);
        arrby = Base64.encodeBase64(EncodingUtils.getBytes(stringBuilder.toString(), (String)arrby));
        object = new CharArrayBuffer(32);
        if (bl) {
            ((CharArrayBuffer)object).append("Proxy-Authorization");
        } else {
            ((CharArrayBuffer)object).append("Authorization");
        }
        ((CharArrayBuffer)object).append(": Basic ");
        ((CharArrayBuffer)object).append(arrby, 0, arrby.length);
        return new BufferedHeader((CharArrayBuffer)object);
    }

    @Override
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        if (credentials == null) throw new IllegalArgumentException("Credentials may not be null");
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        return BasicScheme.authenticate(credentials, AuthParams.getCredentialCharset(httpRequest.getParams()), this.isProxy());
    }

    @Override
    public String getSchemeName() {
        return "basic";
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }

    @Override
    public boolean isConnectionBased() {
        return false;
    }

    @Override
    public void processChallenge(Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        this.complete = true;
    }
}

