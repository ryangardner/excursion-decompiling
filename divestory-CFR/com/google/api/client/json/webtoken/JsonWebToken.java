/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json.webtoken;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import java.util.Collections;
import java.util.List;

public class JsonWebToken {
    private final Header header;
    private final Payload payload;

    public JsonWebToken(Header header, Payload payload) {
        this.header = Preconditions.checkNotNull(header);
        this.payload = Preconditions.checkNotNull(payload);
    }

    public Header getHeader() {
        return this.header;
    }

    public Payload getPayload() {
        return this.payload;
    }

    public String toString() {
        return Objects.toStringHelper(this).add("header", this.header).add("payload", this.payload).toString();
    }

    public static class Header
    extends GenericJson {
        @Key(value="cty")
        private String contentType;
        @Key(value="typ")
        private String type;

        @Override
        public Header clone() {
            return (Header)super.clone();
        }

        public final String getContentType() {
            return this.contentType;
        }

        public final String getType() {
            return this.type;
        }

        @Override
        public Header set(String string2, Object object) {
            return (Header)super.set(string2, object);
        }

        public Header setContentType(String string2) {
            this.contentType = string2;
            return this;
        }

        public Header setType(String string2) {
            this.type = string2;
            return this;
        }
    }

    public static class Payload
    extends GenericJson {
        @Key(value="aud")
        private Object audience;
        @Key(value="exp")
        private Long expirationTimeSeconds;
        @Key(value="iat")
        private Long issuedAtTimeSeconds;
        @Key(value="iss")
        private String issuer;
        @Key(value="jti")
        private String jwtId;
        @Key(value="nbf")
        private Long notBeforeTimeSeconds;
        @Key(value="sub")
        private String subject;
        @Key(value="typ")
        private String type;

        @Override
        public Payload clone() {
            return (Payload)super.clone();
        }

        public final Object getAudience() {
            return this.audience;
        }

        public final List<String> getAudienceAsList() {
            Object object = this.audience;
            if (object == null) {
                return Collections.emptyList();
            }
            if (!(object instanceof String)) return (List)object;
            return Collections.singletonList((String)object);
        }

        public final Long getExpirationTimeSeconds() {
            return this.expirationTimeSeconds;
        }

        public final Long getIssuedAtTimeSeconds() {
            return this.issuedAtTimeSeconds;
        }

        public final String getIssuer() {
            return this.issuer;
        }

        public final String getJwtId() {
            return this.jwtId;
        }

        public final Long getNotBeforeTimeSeconds() {
            return this.notBeforeTimeSeconds;
        }

        public final String getSubject() {
            return this.subject;
        }

        public final String getType() {
            return this.type;
        }

        @Override
        public Payload set(String string2, Object object) {
            return (Payload)super.set(string2, object);
        }

        public Payload setAudience(Object object) {
            this.audience = object;
            return this;
        }

        public Payload setExpirationTimeSeconds(Long l) {
            this.expirationTimeSeconds = l;
            return this;
        }

        public Payload setIssuedAtTimeSeconds(Long l) {
            this.issuedAtTimeSeconds = l;
            return this;
        }

        public Payload setIssuer(String string2) {
            this.issuer = string2;
            return this;
        }

        public Payload setJwtId(String string2) {
            this.jwtId = string2;
            return this;
        }

        public Payload setNotBeforeTimeSeconds(Long l) {
            this.notBeforeTimeSeconds = l;
            return this;
        }

        public Payload setSubject(String string2) {
            this.subject = string2;
            return this;
        }

        public Payload setType(String string2) {
            this.type = string2;
            return this;
        }
    }

}

