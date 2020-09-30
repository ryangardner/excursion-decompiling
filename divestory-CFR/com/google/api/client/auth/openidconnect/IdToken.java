/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.openidconnect;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IdToken
extends JsonWebSignature {
    public IdToken(JsonWebSignature.Header header, Payload payload, byte[] arrby, byte[] arrby2) {
        super(header, payload, arrby, arrby2);
    }

    public static IdToken parse(JsonFactory object, String string2) throws IOException {
        object = JsonWebSignature.parser((JsonFactory)object).setPayloadClass(Payload.class).parse(string2);
        return new IdToken(((JsonWebSignature)object).getHeader(), (Payload)((JsonWebToken)object).getPayload(), ((JsonWebSignature)object).getSignatureBytes(), ((JsonWebSignature)object).getSignedContentBytes());
    }

    @Override
    public Payload getPayload() {
        return (Payload)super.getPayload();
    }

    public final boolean verifyAudience(Collection<String> collection) {
        List<String> list = this.getPayload().getAudienceAsList();
        if (!list.isEmpty()) return collection.containsAll(list);
        return false;
    }

    public final boolean verifyExpirationTime(long l, long l2) {
        if (l > (this.getPayload().getExpirationTimeSeconds() + l2) * 1000L) return false;
        return true;
    }

    public final boolean verifyIssuedAtTime(long l, long l2) {
        if (l < (this.getPayload().getIssuedAtTimeSeconds() - l2) * 1000L) return false;
        return true;
    }

    public final boolean verifyIssuer(String string2) {
        return this.verifyIssuer(Collections.singleton(string2));
    }

    public final boolean verifyIssuer(Collection<String> collection) {
        return collection.contains(this.getPayload().getIssuer());
    }

    public final boolean verifyTime(long l, long l2) {
        if (!this.verifyExpirationTime(l, l2)) return false;
        if (!this.verifyIssuedAtTime(l, l2)) return false;
        return true;
    }

    public static class Payload
    extends JsonWebToken.Payload {
        @Key(value="at_hash")
        private String accessTokenHash;
        @Key(value="auth_time")
        private Long authorizationTimeSeconds;
        @Key(value="azp")
        private String authorizedParty;
        @Key(value="acr")
        private String classReference;
        @Key(value="amr")
        private List<String> methodsReferences;
        @Key
        private String nonce;

        @Override
        public Payload clone() {
            return (Payload)super.clone();
        }

        public final String getAccessTokenHash() {
            return this.accessTokenHash;
        }

        public final Long getAuthorizationTimeSeconds() {
            return this.authorizationTimeSeconds;
        }

        public final String getAuthorizedParty() {
            return this.authorizedParty;
        }

        public final String getClassReference() {
            return this.classReference;
        }

        public final List<String> getMethodsReferences() {
            return this.methodsReferences;
        }

        public final String getNonce() {
            return this.nonce;
        }

        @Override
        public Payload set(String string2, Object object) {
            return (Payload)super.set(string2, object);
        }

        public Payload setAccessTokenHash(String string2) {
            this.accessTokenHash = string2;
            return this;
        }

        @Override
        public Payload setAudience(Object object) {
            return (Payload)super.setAudience(object);
        }

        public Payload setAuthorizationTimeSeconds(Long l) {
            this.authorizationTimeSeconds = l;
            return this;
        }

        public Payload setAuthorizedParty(String string2) {
            this.authorizedParty = string2;
            return this;
        }

        public Payload setClassReference(String string2) {
            this.classReference = string2;
            return this;
        }

        @Override
        public Payload setExpirationTimeSeconds(Long l) {
            return (Payload)super.setExpirationTimeSeconds(l);
        }

        @Override
        public Payload setIssuedAtTimeSeconds(Long l) {
            return (Payload)super.setIssuedAtTimeSeconds(l);
        }

        @Override
        public Payload setIssuer(String string2) {
            return (Payload)super.setIssuer(string2);
        }

        @Override
        public Payload setJwtId(String string2) {
            return (Payload)super.setJwtId(string2);
        }

        public Payload setMethodsReferences(List<String> list) {
            this.methodsReferences = list;
            return this;
        }

        public Payload setNonce(String string2) {
            this.nonce = string2;
            return this;
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
    }

}

