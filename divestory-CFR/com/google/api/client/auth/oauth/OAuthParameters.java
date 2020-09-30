/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth;

import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.escape.PercentEscaper;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

public final class OAuthParameters
implements HttpExecuteInterceptor,
HttpRequestInitializer {
    private static final PercentEscaper ESCAPER;
    private static final SecureRandom RANDOM;
    public String callback;
    public String consumerKey;
    public String nonce;
    public String realm;
    public String signature;
    public String signatureMethod;
    public OAuthSigner signer;
    public String timestamp;
    public String token;
    public String verifier;
    public String version;

    static {
        RANDOM = new SecureRandom();
        ESCAPER = new PercentEscaper("-_.~", false);
    }

    private void appendParameter(StringBuilder stringBuilder, String string2, String string3) {
        if (string3 == null) return;
        stringBuilder.append(' ');
        stringBuilder.append(OAuthParameters.escape(string2));
        stringBuilder.append("=\"");
        stringBuilder.append(OAuthParameters.escape(string3));
        stringBuilder.append("\",");
    }

    public static String escape(String string2) {
        return ESCAPER.escape(string2);
    }

    private void putParameter(Multiset<Parameter> multiset, String string2, Object object) {
        String string3 = OAuthParameters.escape(string2);
        string2 = object == null ? null : OAuthParameters.escape(object.toString());
        multiset.add(new Parameter(string3, string2));
    }

    private void putParameterIfValueNotNull(Multiset<Parameter> multiset, String string2, String string3) {
        if (string3 == null) return;
        this.putParameter(multiset, string2, string3);
    }

    public void computeNonce() {
        this.nonce = Long.toHexString(Math.abs(RANDOM.nextLong()));
    }

    public void computeSignature(String string2, GenericUrl object) throws GeneralSecurityException {
        OAuthSigner oAuthSigner;
        Object object2;
        int n;
        Object object4;
        Object object32;
        block11 : {
            block10 : {
                int n2;
                oAuthSigner = this.signer;
                object2 = oAuthSigner.getSignatureMethod();
                this.signatureMethod = object2;
                object4 = TreeMultiset.create();
                this.putParameterIfValueNotNull((Multiset<Parameter>)object4, "oauth_callback", this.callback);
                this.putParameterIfValueNotNull((Multiset<Parameter>)object4, "oauth_consumer_key", this.consumerKey);
                this.putParameterIfValueNotNull((Multiset<Parameter>)object4, "oauth_nonce", this.nonce);
                this.putParameterIfValueNotNull((Multiset<Parameter>)object4, "oauth_signature_method", (String)object2);
                this.putParameterIfValueNotNull((Multiset<Parameter>)object4, "oauth_timestamp", this.timestamp);
                this.putParameterIfValueNotNull((Multiset<Parameter>)object4, "oauth_token", this.token);
                this.putParameterIfValueNotNull((Multiset<Parameter>)object4, "oauth_verifier", this.verifier);
                this.putParameterIfValueNotNull((Multiset<Parameter>)object4, "oauth_version", this.version);
                for (Map.Entry entry : ((GenericData)object).entrySet()) {
                    object32 = entry.getValue();
                    if (object32 == null) continue;
                    String string3 = (String)entry.getKey();
                    if (object32 instanceof Collection) {
                        object32 = ((Collection)object32).iterator();
                        while (object32.hasNext()) {
                            this.putParameter((Multiset<Parameter>)object4, string3, object32.next());
                        }
                        continue;
                    }
                    this.putParameter((Multiset<Parameter>)object4, string3, object32);
                }
                object2 = new StringBuilder();
                n = 1;
                for (Object object32 : object4.elementSet()) {
                    if (n != 0) {
                        n2 = 0;
                    } else {
                        ((StringBuilder)object2).append('&');
                        n2 = n;
                    }
                    ((StringBuilder)object2).append(((Parameter)object32).getKey());
                    object32 = ((Parameter)object32).getValue();
                    n = n2;
                    if (object32 == null) continue;
                    ((StringBuilder)object2).append('=');
                    ((StringBuilder)object2).append((String)object32);
                    n = n2;
                }
                object4 = ((StringBuilder)object2).toString();
                object32 = new GenericUrl();
                object2 = ((GenericUrl)object).getScheme();
                ((GenericUrl)object32).setScheme((String)object2);
                ((GenericUrl)object32).setHost(((GenericUrl)object).getHost());
                ((GenericUrl)object32).setPathParts(((GenericUrl)object).getPathParts());
                n2 = ((GenericUrl)object).getPort();
                if ("http".equals(object2) && n2 == 80) break block10;
                n = n2;
                if (!"https".equals(object2)) break block11;
                n = n2;
                if (n2 != 443) break block11;
            }
            n = -1;
        }
        ((GenericUrl)object32).setPort(n);
        object = ((GenericUrl)object32).build();
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(OAuthParameters.escape(string2));
        ((StringBuilder)object2).append('&');
        ((StringBuilder)object2).append(OAuthParameters.escape((String)object));
        ((StringBuilder)object2).append('&');
        ((StringBuilder)object2).append(OAuthParameters.escape((String)object4));
        this.signature = oAuthSigner.computeSignature(((StringBuilder)object2).toString());
    }

    public void computeTimestamp() {
        this.timestamp = Long.toString(System.currentTimeMillis() / 1000L);
    }

    public String getAuthorizationHeader() {
        StringBuilder stringBuilder = new StringBuilder("OAuth");
        this.appendParameter(stringBuilder, "realm", this.realm);
        this.appendParameter(stringBuilder, "oauth_callback", this.callback);
        this.appendParameter(stringBuilder, "oauth_consumer_key", this.consumerKey);
        this.appendParameter(stringBuilder, "oauth_nonce", this.nonce);
        this.appendParameter(stringBuilder, "oauth_signature", this.signature);
        this.appendParameter(stringBuilder, "oauth_signature_method", this.signatureMethod);
        this.appendParameter(stringBuilder, "oauth_timestamp", this.timestamp);
        this.appendParameter(stringBuilder, "oauth_token", this.token);
        this.appendParameter(stringBuilder, "oauth_verifier", this.verifier);
        this.appendParameter(stringBuilder, "oauth_version", this.version);
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    @Override
    public void initialize(HttpRequest httpRequest) throws IOException {
        httpRequest.setInterceptor(this);
    }

    @Override
    public void intercept(HttpRequest httpRequest) throws IOException {
        this.computeNonce();
        this.computeTimestamp();
        try {
            this.computeSignature(httpRequest.getRequestMethod(), httpRequest.getUrl());
            httpRequest.getHeaders().setAuthorization(this.getAuthorizationHeader());
        }
        catch (GeneralSecurityException generalSecurityException) {
            IOException iOException = new IOException();
            iOException.initCause(generalSecurityException);
            throw iOException;
        }
    }

    private static class Parameter
    implements Comparable<Parameter> {
        private final String key;
        private final String value;

        public Parameter(String string2, String string3) {
            this.key = string2;
            this.value = string3;
        }

        @Override
        public int compareTo(Parameter parameter) {
            int n;
            int n2 = n = this.key.compareTo(parameter.key);
            if (n != 0) return n2;
            return this.value.compareTo(parameter.value);
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }
    }

}

