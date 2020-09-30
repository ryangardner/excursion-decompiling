/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public final class GoogleClientSecrets
extends GenericJson {
    @Key
    private Details installed;
    @Key
    private Details web;

    public static GoogleClientSecrets load(JsonFactory jsonFactory, Reader reader) throws IOException {
        return jsonFactory.fromReader(reader, GoogleClientSecrets.class);
    }

    @Override
    public GoogleClientSecrets clone() {
        return (GoogleClientSecrets)super.clone();
    }

    public Details getDetails() {
        boolean bl;
        Details details;
        Details details2 = this.web;
        boolean bl2 = true;
        boolean bl3 = details2 == null;
        if (bl3 == (bl = this.installed == null)) {
            bl2 = false;
        }
        Preconditions.checkArgument(bl2);
        details2 = details = this.web;
        if (details != null) return details2;
        return this.installed;
    }

    public Details getInstalled() {
        return this.installed;
    }

    public Details getWeb() {
        return this.web;
    }

    @Override
    public GoogleClientSecrets set(String string2, Object object) {
        return (GoogleClientSecrets)super.set(string2, object);
    }

    public GoogleClientSecrets setInstalled(Details details) {
        this.installed = details;
        return this;
    }

    public GoogleClientSecrets setWeb(Details details) {
        this.web = details;
        return this;
    }

    public static final class Details
    extends GenericJson {
        @Key(value="auth_uri")
        private String authUri;
        @Key(value="client_id")
        private String clientId;
        @Key(value="client_secret")
        private String clientSecret;
        @Key(value="redirect_uris")
        private List<String> redirectUris;
        @Key(value="token_uri")
        private String tokenUri;

        @Override
        public Details clone() {
            return (Details)super.clone();
        }

        public String getAuthUri() {
            return this.authUri;
        }

        public String getClientId() {
            return this.clientId;
        }

        public String getClientSecret() {
            return this.clientSecret;
        }

        public List<String> getRedirectUris() {
            return this.redirectUris;
        }

        public String getTokenUri() {
            return this.tokenUri;
        }

        @Override
        public Details set(String string2, Object object) {
            return (Details)super.set(string2, object);
        }

        public Details setAuthUri(String string2) {
            this.authUri = string2;
            return this;
        }

        public Details setClientId(String string2) {
            this.clientId = string2;
            return this;
        }

        public Details setClientSecret(String string2) {
            this.clientSecret = string2;
            return this;
        }

        public Details setRedirectUris(List<String> list) {
            this.redirectUris = list;
            return this;
        }

        public Details setTokenUri(String string2) {
            this.tokenUri = string2;
            return this;
        }
    }

}

