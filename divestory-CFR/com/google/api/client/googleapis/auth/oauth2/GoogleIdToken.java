/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class GoogleIdToken
extends IdToken {
    public GoogleIdToken(JsonWebSignature.Header header, Payload payload, byte[] arrby, byte[] arrby2) {
        super(header, payload, arrby, arrby2);
    }

    public static GoogleIdToken parse(JsonFactory object, String string2) throws IOException {
        object = JsonWebSignature.parser((JsonFactory)object).setPayloadClass(Payload.class).parse(string2);
        return new GoogleIdToken(((JsonWebSignature)object).getHeader(), (Payload)((JsonWebToken)object).getPayload(), ((JsonWebSignature)object).getSignatureBytes(), ((JsonWebSignature)object).getSignedContentBytes());
    }

    @Override
    public Payload getPayload() {
        return (Payload)super.getPayload();
    }

    public boolean verify(GoogleIdTokenVerifier googleIdTokenVerifier) throws GeneralSecurityException, IOException {
        return googleIdTokenVerifier.verify(this);
    }

    public static class Payload
    extends IdToken.Payload {
        @Key(value="email")
        private String email;
        @Key(value="email_verified")
        private Object emailVerified;
        @Key(value="hd")
        private String hostedDomain;

        @Override
        public Payload clone() {
            return (Payload)super.clone();
        }

        public String getEmail() {
            return this.email;
        }

        public Boolean getEmailVerified() {
            Object object = this.emailVerified;
            if (object == null) {
                return null;
            }
            if (!(object instanceof Boolean)) return Boolean.valueOf((String)object);
            return (Boolean)object;
        }

        public String getHostedDomain() {
            return this.hostedDomain;
        }

        @Deprecated
        public String getIssuee() {
            return this.getAuthorizedParty();
        }

        @Deprecated
        public String getUserId() {
            return this.getSubject();
        }

        @Override
        public Payload set(String string2, Object object) {
            return (Payload)super.set(string2, object);
        }

        @Override
        public Payload setAccessTokenHash(String string2) {
            return (Payload)super.setAccessTokenHash(string2);
        }

        @Override
        public Payload setAudience(Object object) {
            return (Payload)super.setAudience(object);
        }

        @Override
        public Payload setAuthorizationTimeSeconds(Long l) {
            return (Payload)super.setAuthorizationTimeSeconds(l);
        }

        @Override
        public Payload setAuthorizedParty(String string2) {
            return (Payload)super.setAuthorizedParty(string2);
        }

        @Override
        public Payload setClassReference(String string2) {
            return (Payload)super.setClassReference(string2);
        }

        public Payload setEmail(String string2) {
            this.email = string2;
            return this;
        }

        public Payload setEmailVerified(Boolean bl) {
            this.emailVerified = bl;
            return this;
        }

        @Override
        public Payload setExpirationTimeSeconds(Long l) {
            return (Payload)super.setExpirationTimeSeconds(l);
        }

        public Payload setHostedDomain(String string2) {
            this.hostedDomain = string2;
            return this;
        }

        @Override
        public Payload setIssuedAtTimeSeconds(Long l) {
            return (Payload)super.setIssuedAtTimeSeconds(l);
        }

        @Deprecated
        public Payload setIssuee(String string2) {
            return this.setAuthorizedParty(string2);
        }

        @Override
        public Payload setIssuer(String string2) {
            return (Payload)super.setIssuer(string2);
        }

        @Override
        public Payload setJwtId(String string2) {
            return (Payload)super.setJwtId(string2);
        }

        @Override
        public Payload setMethodsReferences(List<String> list) {
            return (Payload)super.setMethodsReferences(list);
        }

        @Override
        public Payload setNonce(String string2) {
            return (Payload)super.setNonce(string2);
        }

        @Override
        public Payload setNotBeforeTimeSeconds(Long l) {
            return (Payload)super.setNotBeforeTimeSeconds(l);
        }

        @Override
        public Payload setSubject(String string2) {
            return (Payload)super.setSubject(string2);
        }

        @Override
        public Payload setType(String string2) {
            return (Payload)super.setType(string2);
        }

        @Deprecated
        public Payload setUserId(String string2) {
            return this.setSubject(string2);
        }
    }

}

