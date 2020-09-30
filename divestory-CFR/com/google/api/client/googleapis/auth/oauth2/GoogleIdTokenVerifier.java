/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.auth.openidconnect.IdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GooglePublicKeysManager;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GoogleIdTokenVerifier
extends IdTokenVerifier {
    private final GooglePublicKeysManager publicKeys;

    protected GoogleIdTokenVerifier(Builder builder) {
        super(builder);
        this.publicKeys = builder.publicKeys;
    }

    public GoogleIdTokenVerifier(GooglePublicKeysManager googlePublicKeysManager) {
        this(new Builder(googlePublicKeysManager));
    }

    public GoogleIdTokenVerifier(HttpTransport httpTransport, JsonFactory jsonFactory) {
        this(new Builder(httpTransport, jsonFactory));
    }

    @Deprecated
    public final long getExpirationTimeMilliseconds() {
        return this.publicKeys.getExpirationTimeMilliseconds();
    }

    public final JsonFactory getJsonFactory() {
        return this.publicKeys.getJsonFactory();
    }

    @Deprecated
    public final String getPublicCertsEncodedUrl() {
        return this.publicKeys.getPublicCertsEncodedUrl();
    }

    @Deprecated
    public final List<PublicKey> getPublicKeys() throws GeneralSecurityException, IOException {
        return this.publicKeys.getPublicKeys();
    }

    public final GooglePublicKeysManager getPublicKeysManager() {
        return this.publicKeys;
    }

    public final HttpTransport getTransport() {
        return this.publicKeys.getTransport();
    }

    @Deprecated
    public GoogleIdTokenVerifier loadPublicCerts() throws GeneralSecurityException, IOException {
        this.publicKeys.refresh();
        return this;
    }

    public GoogleIdToken verify(String object) throws GeneralSecurityException, IOException {
        object = GoogleIdToken.parse(this.getJsonFactory(), (String)object);
        if (!this.verify((GoogleIdToken)object)) return null;
        return object;
    }

    public boolean verify(GoogleIdToken googleIdToken) throws GeneralSecurityException, IOException {
        if (!super.verify(googleIdToken)) {
            return false;
        }
        Iterator<PublicKey> iterator2 = this.publicKeys.getPublicKeys().iterator();
        do {
            if (!iterator2.hasNext()) return false;
        } while (!googleIdToken.verifySignature(iterator2.next()));
        return true;
    }

    public static class Builder
    extends IdTokenVerifier.Builder {
        GooglePublicKeysManager publicKeys;

        public Builder(GooglePublicKeysManager googlePublicKeysManager) {
            this.publicKeys = Preconditions.checkNotNull(googlePublicKeysManager);
            this.setIssuers(Arrays.asList("accounts.google.com", "https://accounts.google.com"));
        }

        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory) {
            this(new GooglePublicKeysManager(httpTransport, jsonFactory));
        }

        @Override
        public GoogleIdTokenVerifier build() {
            return new GoogleIdTokenVerifier(this);
        }

        public final JsonFactory getJsonFactory() {
            return this.publicKeys.getJsonFactory();
        }

        public final GooglePublicKeysManager getPublicCerts() {
            return this.publicKeys;
        }

        @Deprecated
        public final String getPublicCertsEncodedUrl() {
            return this.publicKeys.getPublicCertsEncodedUrl();
        }

        public final HttpTransport getTransport() {
            return this.publicKeys.getTransport();
        }

        @Override
        public Builder setAcceptableTimeSkewSeconds(long l) {
            return (Builder)super.setAcceptableTimeSkewSeconds(l);
        }

        @Override
        public Builder setAudience(Collection<String> collection) {
            return (Builder)super.setAudience(collection);
        }

        @Override
        public Builder setClock(Clock clock) {
            return (Builder)super.setClock(clock);
        }

        @Override
        public Builder setIssuer(String string2) {
            return (Builder)super.setIssuer(string2);
        }

        @Override
        public Builder setIssuers(Collection<String> collection) {
            return (Builder)super.setIssuers(collection);
        }

        @Deprecated
        public Builder setPublicCertsEncodedUrl(String string2) {
            this.publicKeys = new GooglePublicKeysManager.Builder(this.getTransport(), this.getJsonFactory()).setPublicCertsEncodedUrl(string2).setClock(this.publicKeys.getClock()).build();
            return this;
        }
    }

}

