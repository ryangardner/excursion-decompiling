/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.api.client.util.Clock;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.PemReader;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collection;
import java.util.Collections;

@Deprecated
public class GoogleCredential
extends Credential {
    static final String SERVICE_ACCOUNT_FILE_TYPE = "service_account";
    static final String USER_FILE_TYPE = "authorized_user";
    private static DefaultCredentialProvider defaultCredentialProvider = new DefaultCredentialProvider();
    private String serviceAccountId;
    private PrivateKey serviceAccountPrivateKey;
    private String serviceAccountPrivateKeyId;
    private String serviceAccountProjectId;
    private Collection<String> serviceAccountScopes;
    private String serviceAccountUser;

    public GoogleCredential() {
        this(new Builder());
    }

    protected GoogleCredential(Builder builder) {
        super(builder);
        if (builder.serviceAccountPrivateKey == null) {
            boolean bl = builder.serviceAccountId == null && builder.serviceAccountScopes == null && builder.serviceAccountUser == null;
            Preconditions.checkArgument(bl);
            return;
        }
        this.serviceAccountId = Preconditions.checkNotNull(builder.serviceAccountId);
        this.serviceAccountProjectId = builder.serviceAccountProjectId;
        Collection<Object> collection = builder.serviceAccountScopes == null ? Collections.emptyList() : Collections.unmodifiableCollection(builder.serviceAccountScopes);
        this.serviceAccountScopes = collection;
        this.serviceAccountPrivateKey = builder.serviceAccountPrivateKey;
        this.serviceAccountPrivateKeyId = builder.serviceAccountPrivateKeyId;
        this.serviceAccountUser = builder.serviceAccountUser;
    }

    public static GoogleCredential fromStream(InputStream inputStream2) throws IOException {
        return GoogleCredential.fromStream(inputStream2, Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
    }

    public static GoogleCredential fromStream(InputStream object, HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(httpTransport);
        Preconditions.checkNotNull(jsonFactory);
        object = new JsonObjectParser(jsonFactory).parseAndClose((InputStream)object, OAuth2Utils.UTF_8, GenericJson.class);
        String string2 = (String)((GenericData)object).get("type");
        if (string2 == null) throw new IOException("Error reading credentials from stream, 'type' field not specified.");
        if (USER_FILE_TYPE.equals(string2)) {
            return GoogleCredential.fromStreamUser((GenericJson)object, httpTransport, jsonFactory);
        }
        if (!SERVICE_ACCOUNT_FILE_TYPE.equals(string2)) throw new IOException(String.format("Error reading credentials from stream, 'type' value '%s' not recognized. Expecting '%s' or '%s'.", string2, USER_FILE_TYPE, SERVICE_ACCOUNT_FILE_TYPE));
        return GoogleCredential.fromStreamServiceAccount((GenericJson)object, httpTransport, jsonFactory);
    }

    private static GoogleCredential fromStreamServiceAccount(GenericJson object, HttpTransport object2, JsonFactory object3) throws IOException {
        Object object4 = (String)((GenericData)object).get("client_id");
        String string2 = (String)((GenericData)object).get("client_email");
        Object object5 = (String)((GenericData)object).get("private_key");
        String string3 = (String)((GenericData)object).get("private_key_id");
        if (object4 == null) throw new IOException("Error reading service account credential from stream, expecting  'client_id', 'client_email', 'private_key' and 'private_key_id'.");
        if (string2 == null) throw new IOException("Error reading service account credential from stream, expecting  'client_id', 'client_email', 'private_key' and 'private_key_id'.");
        if (object5 == null) throw new IOException("Error reading service account credential from stream, expecting  'client_id', 'client_email', 'private_key' and 'private_key_id'.");
        if (string3 == null) throw new IOException("Error reading service account credential from stream, expecting  'client_id', 'client_email', 'private_key' and 'private_key_id'.");
        object4 = GoogleCredential.privateKeyFromPkcs8((String)object5);
        object5 = Collections.emptyList();
        object2 = ((Builder)((Builder)new Builder().setTransport((HttpTransport)object2)).setJsonFactory((JsonFactory)object3)).setServiceAccountId(string2).setServiceAccountScopes((Collection<String>)object5).setServiceAccountPrivateKey((PrivateKey)object4).setServiceAccountPrivateKeyId(string3);
        object3 = (String)((GenericData)object).get("token_uri");
        if (object3 != null) {
            ((Builder)object2).setTokenServerEncodedUrl((String)object3);
        }
        if ((object = (String)((GenericData)object).get("project_id")) == null) return ((Builder)object2).build();
        ((Builder)object2).setServiceAccountProjectId((String)object);
        return ((Builder)object2).build();
    }

    private static GoogleCredential fromStreamUser(GenericJson object, HttpTransport object2, JsonFactory jsonFactory) throws IOException {
        String string2 = (String)((GenericData)object).get("client_id");
        String string3 = (String)((GenericData)object).get("client_secret");
        object = (String)((GenericData)object).get("refresh_token");
        if (string2 == null) throw new IOException("Error reading user credential from stream,  expecting 'client_id', 'client_secret' and 'refresh_token'.");
        if (string3 == null) throw new IOException("Error reading user credential from stream,  expecting 'client_id', 'client_secret' and 'refresh_token'.");
        if (object == null) throw new IOException("Error reading user credential from stream,  expecting 'client_id', 'client_secret' and 'refresh_token'.");
        object2 = ((Builder)((Builder)new Builder().setClientSecrets(string2, string3).setTransport((HttpTransport)object2)).setJsonFactory(jsonFactory)).build();
        ((GoogleCredential)object2).setRefreshToken((String)object);
        ((Credential)object2).refreshToken();
        return object2;
    }

    public static GoogleCredential getApplicationDefault() throws IOException {
        return GoogleCredential.getApplicationDefault(Utils.getDefaultTransport(), Utils.getDefaultJsonFactory());
    }

    public static GoogleCredential getApplicationDefault(HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        Preconditions.checkNotNull(httpTransport);
        Preconditions.checkNotNull(jsonFactory);
        return defaultCredentialProvider.getDefaultCredential(httpTransport, jsonFactory);
    }

    /*
     * WARNING - void declaration
     */
    private static PrivateKey privateKeyFromPkcs8(String object) throws IOException {
        void var0_3;
        if ((object = PemReader.readFirstSectionAndClose(new StringReader((String)object), "PRIVATE KEY")) == null) throw new IOException("Invalid PKCS8 data.");
        object = new PKCS8EncodedKeySpec(((PemReader.Section)object).getBase64DecodedBytes());
        try {
            return SecurityUtils.getRsaKeyFactory().generatePrivate((KeySpec)object);
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            throw OAuth2Utils.exceptionWithCause(new IOException("Unexpected exception reading PKCS data"), (Throwable)var0_3);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
        throw OAuth2Utils.exceptionWithCause(new IOException("Unexpected exception reading PKCS data"), (Throwable)var0_3);
    }

    public GoogleCredential createDelegated(String string2) {
        if (this.serviceAccountPrivateKey != null) return this.toBuilder().setServiceAccountUser(string2).build();
        return this;
    }

    public GoogleCredential createScoped(Collection<String> collection) {
        if (this.serviceAccountPrivateKey != null) return this.toBuilder().setServiceAccountScopes(collection).build();
        return this;
    }

    public boolean createScopedRequired() {
        Collection<String> collection = this.serviceAccountPrivateKey;
        boolean bl = false;
        if (collection == null) {
            return false;
        }
        collection = this.serviceAccountScopes;
        if (collection == null) return true;
        if (!collection.isEmpty()) return bl;
        return true;
    }

    @Override
    protected TokenResponse executeRefreshToken() throws IOException {
        if (this.serviceAccountPrivateKey == null) {
            return super.executeRefreshToken();
        }
        Object object = new JsonWebSignature.Header();
        ((JsonWebSignature.Header)object).setAlgorithm("RS256");
        ((JsonWebSignature.Header)object).setType("JWT");
        ((JsonWebSignature.Header)object).setKeyId(this.serviceAccountPrivateKeyId);
        Object object2 = new JsonWebToken.Payload();
        long l = this.getClock().currentTimeMillis();
        ((JsonWebToken.Payload)object2).setIssuer(this.serviceAccountId);
        ((JsonWebToken.Payload)object2).setAudience(this.getTokenServerEncodedUrl());
        ((JsonWebToken.Payload)object2).setIssuedAtTimeSeconds(l /= 1000L);
        ((JsonWebToken.Payload)object2).setExpirationTimeSeconds(l + 3600L);
        ((JsonWebToken.Payload)object2).setSubject(this.serviceAccountUser);
        ((GenericData)object2).put("scope", (Object)Joiner.on(' ').join(this.serviceAccountScopes));
        try {
            String string2 = JsonWebSignature.signUsingRsaSha256(this.serviceAccountPrivateKey, this.getJsonFactory(), (JsonWebSignature.Header)object, (JsonWebToken.Payload)object2);
            HttpTransport httpTransport = this.getTransport();
            object2 = this.getJsonFactory();
            GenericUrl genericUrl = new GenericUrl(this.getTokenServerEncodedUrl());
            object = new TokenRequest(httpTransport, (JsonFactory)object2, genericUrl, "urn:ietf:params:oauth:grant-type:jwt-bearer");
            ((GenericData)object).put("assertion", (Object)string2);
            return ((TokenRequest)object).execute();
        }
        catch (GeneralSecurityException generalSecurityException) {
            object = new IOException();
            ((Throwable)object).initCause(generalSecurityException);
            throw object;
        }
    }

    public final String getServiceAccountId() {
        return this.serviceAccountId;
    }

    public final PrivateKey getServiceAccountPrivateKey() {
        return this.serviceAccountPrivateKey;
    }

    public final String getServiceAccountPrivateKeyId() {
        return this.serviceAccountPrivateKeyId;
    }

    public final String getServiceAccountProjectId() {
        return this.serviceAccountProjectId;
    }

    public final Collection<String> getServiceAccountScopes() {
        return this.serviceAccountScopes;
    }

    public final String getServiceAccountScopesAsString() {
        if (this.serviceAccountScopes != null) return Joiner.on(' ').join(this.serviceAccountScopes);
        return null;
    }

    public final String getServiceAccountUser() {
        return this.serviceAccountUser;
    }

    @Override
    public GoogleCredential setAccessToken(String string2) {
        return (GoogleCredential)super.setAccessToken(string2);
    }

    @Override
    public GoogleCredential setExpirationTimeMilliseconds(Long l) {
        return (GoogleCredential)super.setExpirationTimeMilliseconds(l);
    }

    @Override
    public GoogleCredential setExpiresInSeconds(Long l) {
        return (GoogleCredential)super.setExpiresInSeconds(l);
    }

    @Override
    public GoogleCredential setFromTokenResponse(TokenResponse tokenResponse) {
        return (GoogleCredential)super.setFromTokenResponse(tokenResponse);
    }

    @Override
    public GoogleCredential setRefreshToken(String string2) {
        if (string2 == null) return (GoogleCredential)super.setRefreshToken(string2);
        boolean bl = this.getJsonFactory() != null && this.getTransport() != null && this.getClientAuthentication() != null;
        Preconditions.checkArgument(bl, "Please use the Builder and call setJsonFactory, setTransport and setClientSecrets");
        return (GoogleCredential)super.setRefreshToken(string2);
    }

    public Builder toBuilder() {
        Credential.Builder builder = ((Builder)((Builder)((Builder)new Builder().setServiceAccountPrivateKey(this.serviceAccountPrivateKey).setServiceAccountPrivateKeyId(this.serviceAccountPrivateKeyId).setServiceAccountId(this.serviceAccountId).setServiceAccountProjectId(this.serviceAccountProjectId).setServiceAccountUser(this.serviceAccountUser).setServiceAccountScopes(this.serviceAccountScopes).setTokenServerEncodedUrl(this.getTokenServerEncodedUrl())).setTransport(this.getTransport())).setJsonFactory(this.getJsonFactory())).setClock(this.getClock());
        ((Builder)builder).setClientAuthentication(this.getClientAuthentication());
        return builder;
    }

    public static class Builder
    extends Credential.Builder {
        String serviceAccountId;
        PrivateKey serviceAccountPrivateKey;
        String serviceAccountPrivateKeyId;
        String serviceAccountProjectId;
        Collection<String> serviceAccountScopes;
        String serviceAccountUser;

        public Builder() {
            super(BearerToken.authorizationHeaderAccessMethod());
            this.setTokenServerEncodedUrl("https://oauth2.googleapis.com/token");
        }

        @Override
        public Builder addRefreshListener(CredentialRefreshListener credentialRefreshListener) {
            return (Builder)super.addRefreshListener(credentialRefreshListener);
        }

        @Override
        public GoogleCredential build() {
            return new GoogleCredential(this);
        }

        public final String getServiceAccountId() {
            return this.serviceAccountId;
        }

        public final PrivateKey getServiceAccountPrivateKey() {
            return this.serviceAccountPrivateKey;
        }

        public final String getServiceAccountPrivateKeyId() {
            return this.serviceAccountPrivateKeyId;
        }

        public final String getServiceAccountProjectId() {
            return this.serviceAccountProjectId;
        }

        public final Collection<String> getServiceAccountScopes() {
            return this.serviceAccountScopes;
        }

        public final String getServiceAccountUser() {
            return this.serviceAccountUser;
        }

        @Override
        public Builder setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
            return (Builder)super.setClientAuthentication(httpExecuteInterceptor);
        }

        public Builder setClientSecrets(GoogleClientSecrets genericJson) {
            genericJson = ((GoogleClientSecrets)genericJson).getDetails();
            this.setClientAuthentication(new ClientParametersAuthentication(((GoogleClientSecrets.Details)genericJson).getClientId(), ((GoogleClientSecrets.Details)genericJson).getClientSecret()));
            return this;
        }

        public Builder setClientSecrets(String string2, String string3) {
            this.setClientAuthentication(new ClientParametersAuthentication(string2, string3));
            return this;
        }

        @Override
        public Builder setClock(Clock clock) {
            return (Builder)super.setClock(clock);
        }

        @Override
        public Builder setJsonFactory(JsonFactory jsonFactory) {
            return (Builder)super.setJsonFactory(jsonFactory);
        }

        @Override
        public Builder setRefreshListeners(Collection<CredentialRefreshListener> collection) {
            return (Builder)super.setRefreshListeners(collection);
        }

        @Override
        public Builder setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder)super.setRequestInitializer(httpRequestInitializer);
        }

        public Builder setServiceAccountId(String string2) {
            this.serviceAccountId = string2;
            return this;
        }

        public Builder setServiceAccountPrivateKey(PrivateKey privateKey) {
            this.serviceAccountPrivateKey = privateKey;
            return this;
        }

        public Builder setServiceAccountPrivateKeyFromP12File(File file) throws GeneralSecurityException, IOException {
            this.setServiceAccountPrivateKeyFromP12File(new FileInputStream(file));
            return this;
        }

        public Builder setServiceAccountPrivateKeyFromP12File(InputStream inputStream2) throws GeneralSecurityException, IOException {
            this.serviceAccountPrivateKey = SecurityUtils.loadPrivateKeyFromKeyStore(SecurityUtils.getPkcs12KeyStore(), inputStream2, "notasecret", "privatekey", "notasecret");
            return this;
        }

        public Builder setServiceAccountPrivateKeyFromPemFile(File arrby) throws GeneralSecurityException, IOException {
            arrby = PemReader.readFirstSectionAndClose(new FileReader((File)arrby), "PRIVATE KEY").getBase64DecodedBytes();
            this.serviceAccountPrivateKey = SecurityUtils.getRsaKeyFactory().generatePrivate(new PKCS8EncodedKeySpec(arrby));
            return this;
        }

        public Builder setServiceAccountPrivateKeyId(String string2) {
            this.serviceAccountPrivateKeyId = string2;
            return this;
        }

        public Builder setServiceAccountProjectId(String string2) {
            this.serviceAccountProjectId = string2;
            return this;
        }

        public Builder setServiceAccountScopes(Collection<String> collection) {
            this.serviceAccountScopes = collection;
            return this;
        }

        public Builder setServiceAccountUser(String string2) {
            this.serviceAccountUser = string2;
            return this;
        }

        @Override
        public Builder setTokenServerEncodedUrl(String string2) {
            return (Builder)super.setTokenServerEncodedUrl(string2);
        }

        @Override
        public Builder setTokenServerUrl(GenericUrl genericUrl) {
            return (Builder)super.setTokenServerUrl(genericUrl);
        }

        @Override
        public Builder setTransport(HttpTransport httpTransport) {
            return (Builder)super.setTransport(httpTransport);
        }
    }

}

