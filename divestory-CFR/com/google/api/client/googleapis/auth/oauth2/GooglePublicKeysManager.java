/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.JsonToken;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GooglePublicKeysManager {
    private static final Pattern MAX_AGE_PATTERN = Pattern.compile("\\s*max-age\\s*=\\s*(\\d+)\\s*");
    private static final long REFRESH_SKEW_MILLIS = 300000L;
    private final Clock clock;
    private long expirationTimeMilliseconds;
    private final JsonFactory jsonFactory;
    private final Lock lock = new ReentrantLock();
    private final String publicCertsEncodedUrl;
    private List<PublicKey> publicKeys;
    private final HttpTransport transport;

    protected GooglePublicKeysManager(Builder builder) {
        this.transport = builder.transport;
        this.jsonFactory = builder.jsonFactory;
        this.clock = builder.clock;
        this.publicCertsEncodedUrl = builder.publicCertsEncodedUrl;
    }

    public GooglePublicKeysManager(HttpTransport httpTransport, JsonFactory jsonFactory) {
        this(new Builder(httpTransport, jsonFactory));
    }

    long getCacheTimeInSec(HttpHeaders httpHeaders) {
        long l;
        if (httpHeaders.getCacheControl() != null) {
            for (String string2 : httpHeaders.getCacheControl().split(",")) {
                Matcher object = MAX_AGE_PATTERN.matcher(string2);
                if (!object.matches()) continue;
                l = Long.parseLong(object.group(1));
                break;
            }
        } else {
            l = 0L;
        }
        long l2 = l;
        if (httpHeaders.getAge() == null) return Math.max(0L, l2);
        l2 = l - httpHeaders.getAge();
        return Math.max(0L, l2);
    }

    public final Clock getClock() {
        return this.clock;
    }

    public final long getExpirationTimeMilliseconds() {
        return this.expirationTimeMilliseconds;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final String getPublicCertsEncodedUrl() {
        return this.publicCertsEncodedUrl;
    }

    public final List<PublicKey> getPublicKeys() throws GeneralSecurityException, IOException {
        this.lock.lock();
        try {
            if (this.publicKeys == null || this.clock.currentTimeMillis() + 300000L > this.expirationTimeMilliseconds) {
                this.refresh();
            }
            List<PublicKey> list = this.publicKeys;
            return list;
        }
        finally {
            this.lock.unlock();
        }
    }

    public final HttpTransport getTransport() {
        return this.transport;
    }

    public GooglePublicKeysManager refresh() throws GeneralSecurityException, IOException {
        this.lock.lock();
        Object object = new ArrayList();
        this.publicKeys = object;
        CertificateFactory certificateFactory = SecurityUtils.getX509CertificateFactory();
        object = this.transport.createRequestFactory();
        Object object2 = new GenericUrl(this.publicCertsEncodedUrl);
        object = ((HttpRequestFactory)object).buildGetRequest((GenericUrl)object2).execute();
        this.expirationTimeMilliseconds = this.clock.currentTimeMillis() + this.getCacheTimeInSec(((HttpResponse)object).getHeaders()) * 1000L;
        JsonParser jsonParser = this.jsonFactory.createJsonParser(((HttpResponse)object).getContent());
        object2 = jsonParser.getCurrentToken();
        object = object2;
        if (object2 == null) {
            object = jsonParser.nextToken();
        }
        boolean bl = object == JsonToken.START_OBJECT;
        Preconditions.checkArgument(bl);
        try {
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                jsonParser.nextToken();
                object2 = jsonParser.getText();
                object = new ByteArrayInputStream(StringUtils.getBytesUtf8((String)object2));
                object = (X509Certificate)certificateFactory.generateCertificate((InputStream)object);
                this.publicKeys.add(((Certificate)object).getPublicKey());
            }
            this.publicKeys = Collections.unmodifiableList(this.publicKeys);
        }
        catch (Throwable throwable) {
            jsonParser.close();
            throw throwable;
        }
        try {
            jsonParser.close();
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    public static class Builder {
        Clock clock = Clock.SYSTEM;
        final JsonFactory jsonFactory;
        String publicCertsEncodedUrl = "https://www.googleapis.com/oauth2/v1/certs";
        final HttpTransport transport;

        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory) {
            this.transport = Preconditions.checkNotNull(httpTransport);
            this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        }

        public GooglePublicKeysManager build() {
            return new GooglePublicKeysManager(this);
        }

        public final Clock getClock() {
            return this.clock;
        }

        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public final String getPublicCertsEncodedUrl() {
            return this.publicCertsEncodedUrl;
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public Builder setClock(Clock clock) {
            this.clock = Preconditions.checkNotNull(clock);
            return this;
        }

        public Builder setPublicCertsEncodedUrl(String string2) {
            this.publicCertsEncodedUrl = Preconditions.checkNotNull(string2);
            return this;
        }
    }

}

