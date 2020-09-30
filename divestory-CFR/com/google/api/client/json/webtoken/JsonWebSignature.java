/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json.webtoken;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.api.client.util.Base64;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class JsonWebSignature
extends JsonWebToken {
    private final byte[] signatureBytes;
    private final byte[] signedContentBytes;

    public JsonWebSignature(Header header, JsonWebToken.Payload payload, byte[] arrby, byte[] arrby2) {
        super(header, payload);
        this.signatureBytes = Preconditions.checkNotNull(arrby);
        this.signedContentBytes = Preconditions.checkNotNull(arrby2);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    private static X509TrustManager getDefaultX509TrustManager() {
        try {
            Object object = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            ((TrustManagerFactory)object).init((KeyStore)null);
            TrustManager[] arrtrustManager = ((TrustManagerFactory)object).getTrustManagers();
            int n = arrtrustManager.length;
            int n2 = 0;
            while (n2 < n) {
                object = arrtrustManager[n2];
                if (object instanceof X509TrustManager) {
                    return (X509TrustManager)object;
                }
                ++n2;
            }
            return null;
        }
        catch (KeyStoreException | NoSuchAlgorithmException generalSecurityException) {
            return null;
        }
    }

    public static JsonWebSignature parse(JsonFactory jsonFactory, String string2) throws IOException {
        return JsonWebSignature.parser(jsonFactory).parse(string2);
    }

    public static Parser parser(JsonFactory jsonFactory) {
        return new Parser(jsonFactory);
    }

    public static String signUsingRsaSha256(PrivateKey arrby, JsonFactory object, Header object2, JsonWebToken.Payload payload) throws GeneralSecurityException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Base64.encodeBase64URLSafeString(((JsonFactory)object).toByteArray(object2)));
        stringBuilder.append(".");
        stringBuilder.append(Base64.encodeBase64URLSafeString(((JsonFactory)object).toByteArray(payload)));
        object = stringBuilder.toString();
        object2 = StringUtils.getBytesUtf8((String)object);
        arrby = SecurityUtils.sign(SecurityUtils.getSha256WithRsaSignatureAlgorithm(), (PrivateKey)arrby, (byte[])object2);
        object2 = new StringBuilder();
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(".");
        ((StringBuilder)object2).append(Base64.encodeBase64URLSafeString(arrby));
        return ((StringBuilder)object2).toString();
    }

    @Override
    public Header getHeader() {
        return (Header)super.getHeader();
    }

    public final byte[] getSignatureBytes() {
        byte[] arrby = this.signatureBytes;
        return Arrays.copyOf(arrby, arrby.length);
    }

    public final byte[] getSignedContentBytes() {
        byte[] arrby = this.signedContentBytes;
        return Arrays.copyOf(arrby, arrby.length);
    }

    public final X509Certificate verifySignature() throws GeneralSecurityException {
        X509TrustManager x509TrustManager = JsonWebSignature.getDefaultX509TrustManager();
        if (x509TrustManager != null) return this.verifySignature(x509TrustManager);
        return null;
    }

    public final X509Certificate verifySignature(X509TrustManager x509TrustManager) throws GeneralSecurityException {
        List<String> list = this.getHeader().getX509Certificates();
        if (list == null) return null;
        if (list.isEmpty()) {
            return null;
        }
        if (!"RS256".equals(this.getHeader().getAlgorithm())) return null;
        return SecurityUtils.verify(SecurityUtils.getSha256WithRsaSignatureAlgorithm(), x509TrustManager, list, this.signatureBytes, this.signedContentBytes);
    }

    public final boolean verifySignature(PublicKey publicKey) throws GeneralSecurityException {
        if (!"RS256".equals(this.getHeader().getAlgorithm())) return false;
        return SecurityUtils.verify(SecurityUtils.getSha256WithRsaSignatureAlgorithm(), publicKey, this.signatureBytes, this.signedContentBytes);
    }

    public static class Header
    extends JsonWebToken.Header {
        @Key(value="alg")
        private String algorithm;
        @Key(value="crit")
        private List<String> critical;
        @Key(value="jwk")
        private String jwk;
        @Key(value="jku")
        private String jwkUrl;
        @Key(value="kid")
        private String keyId;
        @Key(value="x5c")
        private ArrayList<String> x509Certificates;
        @Key(value="x5t")
        private String x509Thumbprint;
        @Key(value="x5u")
        private String x509Url;

        @Override
        public Header clone() {
            return (Header)super.clone();
        }

        public final String getAlgorithm() {
            return this.algorithm;
        }

        public final List<String> getCritical() {
            List<String> list = this.critical;
            if (list == null) return null;
            if (!list.isEmpty()) return new ArrayList<String>(this.critical);
            return null;
        }

        public final String getJwk() {
            return this.jwk;
        }

        public final String getJwkUrl() {
            return this.jwkUrl;
        }

        public final String getKeyId() {
            return this.keyId;
        }

        public final List<String> getX509Certificates() {
            return new ArrayList<String>(this.x509Certificates);
        }

        public final String getX509Thumbprint() {
            return this.x509Thumbprint;
        }

        public final String getX509Url() {
            return this.x509Url;
        }

        @Override
        public Header set(String string2, Object object) {
            return (Header)super.set(string2, object);
        }

        public Header setAlgorithm(String string2) {
            this.algorithm = string2;
            return this;
        }

        public Header setCritical(List<String> list) {
            this.critical = new ArrayList<String>(list);
            return this;
        }

        public Header setJwk(String string2) {
            this.jwk = string2;
            return this;
        }

        public Header setJwkUrl(String string2) {
            this.jwkUrl = string2;
            return this;
        }

        public Header setKeyId(String string2) {
            this.keyId = string2;
            return this;
        }

        @Override
        public Header setType(String string2) {
            super.setType(string2);
            return this;
        }

        public Header setX509Certificates(List<String> list) {
            this.x509Certificates = new ArrayList<String>(list);
            return this;
        }

        public Header setX509Thumbprint(String string2) {
            this.x509Thumbprint = string2;
            return this;
        }

        public Header setX509Url(String string2) {
            this.x509Url = string2;
            return this;
        }
    }

    public static final class Parser {
        private Class<? extends Header> headerClass = Header.class;
        private final JsonFactory jsonFactory;
        private Class<? extends JsonWebToken.Payload> payloadClass = JsonWebToken.Payload.class;

        public Parser(JsonFactory jsonFactory) {
            this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        }

        public Class<? extends Header> getHeaderClass() {
            return this.headerClass;
        }

        public JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public Class<? extends JsonWebToken.Payload> getPayloadClass() {
            return this.payloadClass;
        }

        public JsonWebSignature parse(String arrby) throws IOException {
            int n = arrby.indexOf(46);
            boolean bl = true;
            boolean bl2 = n != -1;
            Preconditions.checkArgument(bl2);
            Object object = Base64.decodeBase64(arrby.substring(0, n));
            int n2 = n + 1;
            int n3 = arrby.indexOf(46, n2);
            bl2 = n3 != -1;
            Preconditions.checkArgument(bl2);
            n = n3 + 1;
            bl2 = arrby.indexOf(46, n) == -1;
            Preconditions.checkArgument(bl2);
            byte[] arrby2 = Base64.decodeBase64(arrby.substring(n2, n3));
            byte[] arrby3 = Base64.decodeBase64(arrby.substring(n));
            arrby = StringUtils.getBytesUtf8(arrby.substring(0, n3));
            object = this.jsonFactory.fromInputStream(new ByteArrayInputStream((byte[])object), this.headerClass);
            bl2 = ((Header)object).getAlgorithm() != null ? bl : false;
            Preconditions.checkArgument(bl2);
            return new JsonWebSignature((Header)object, this.jsonFactory.fromInputStream(new ByteArrayInputStream(arrby2), this.payloadClass), arrby3, arrby);
        }

        public Parser setHeaderClass(Class<? extends Header> class_) {
            this.headerClass = class_;
            return this;
        }

        public Parser setPayloadClass(Class<? extends JsonWebToken.Payload> class_) {
            this.payloadClass = class_;
            return this;
        }
    }

}

