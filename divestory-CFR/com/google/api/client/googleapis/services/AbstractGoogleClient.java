/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.services;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Strings;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class AbstractGoogleClient {
    private static final Logger logger = Logger.getLogger(AbstractGoogleClient.class.getName());
    private final String applicationName;
    private final String batchPath;
    private final GoogleClientRequestInitializer googleClientRequestInitializer;
    private final ObjectParser objectParser;
    private final HttpRequestFactory requestFactory;
    private final String rootUrl;
    private final String servicePath;
    private final boolean suppressPatternChecks;
    private final boolean suppressRequiredParameterChecks;

    protected AbstractGoogleClient(Builder builder) {
        this.googleClientRequestInitializer = builder.googleClientRequestInitializer;
        this.rootUrl = AbstractGoogleClient.normalizeRootUrl(builder.rootUrl);
        this.servicePath = AbstractGoogleClient.normalizeServicePath(builder.servicePath);
        this.batchPath = builder.batchPath;
        if (Strings.isNullOrEmpty(builder.applicationName)) {
            logger.warning("Application name is not set. Call Builder#setApplicationName.");
        }
        this.applicationName = builder.applicationName;
        HttpRequestFactory httpRequestFactory = builder.httpRequestInitializer == null ? builder.transport.createRequestFactory() : builder.transport.createRequestFactory(builder.httpRequestInitializer);
        this.requestFactory = httpRequestFactory;
        this.objectParser = builder.objectParser;
        this.suppressPatternChecks = builder.suppressPatternChecks;
        this.suppressRequiredParameterChecks = builder.suppressRequiredParameterChecks;
    }

    static String normalizeRootUrl(String string2) {
        Preconditions.checkNotNull(string2, "root URL cannot be null.");
        CharSequence charSequence = string2;
        if (string2.endsWith("/")) return charSequence;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("/");
        return ((StringBuilder)charSequence).toString();
    }

    static String normalizeServicePath(String string2) {
        Preconditions.checkNotNull(string2, "service path cannot be null");
        if (string2.length() == 1) {
            Preconditions.checkArgument("/".equals(string2), "service path must equal \"/\" if it is of length 1.");
            return "";
        }
        CharSequence charSequence = string2;
        if (string2.length() <= 0) return charSequence;
        String string3 = string2;
        if (!string2.endsWith("/")) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("/");
            string3 = ((StringBuilder)charSequence).toString();
        }
        charSequence = string3;
        if (!string3.startsWith("/")) return charSequence;
        return string3.substring(1);
    }

    public final BatchRequest batch() {
        return this.batch(null);
    }

    public final BatchRequest batch(HttpRequestInitializer object) {
        object = new BatchRequest(this.getRequestFactory().getTransport(), (HttpRequestInitializer)object);
        if (Strings.isNullOrEmpty(this.batchPath)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getRootUrl());
            stringBuilder.append("batch");
            ((BatchRequest)object).setBatchUrl(new GenericUrl(stringBuilder.toString()));
            return object;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getRootUrl());
        stringBuilder.append(this.batchPath);
        ((BatchRequest)object).setBatchUrl(new GenericUrl(stringBuilder.toString()));
        return object;
    }

    public final String getApplicationName() {
        return this.applicationName;
    }

    public final String getBaseUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.rootUrl);
        stringBuilder.append(this.servicePath);
        return stringBuilder.toString();
    }

    public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
        return this.googleClientRequestInitializer;
    }

    public ObjectParser getObjectParser() {
        return this.objectParser;
    }

    public final HttpRequestFactory getRequestFactory() {
        return this.requestFactory;
    }

    public final String getRootUrl() {
        return this.rootUrl;
    }

    public final String getServicePath() {
        return this.servicePath;
    }

    public final boolean getSuppressPatternChecks() {
        return this.suppressPatternChecks;
    }

    public final boolean getSuppressRequiredParameterChecks() {
        return this.suppressRequiredParameterChecks;
    }

    protected void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
        if (this.getGoogleClientRequestInitializer() == null) return;
        this.getGoogleClientRequestInitializer().initialize(abstractGoogleClientRequest);
    }

    public static abstract class Builder {
        String applicationName;
        String batchPath;
        GoogleClientRequestInitializer googleClientRequestInitializer;
        HttpRequestInitializer httpRequestInitializer;
        final ObjectParser objectParser;
        String rootUrl;
        String servicePath;
        boolean suppressPatternChecks;
        boolean suppressRequiredParameterChecks;
        final HttpTransport transport;

        protected Builder(HttpTransport httpTransport, String string2, String string3, ObjectParser objectParser, HttpRequestInitializer httpRequestInitializer) {
            this.transport = Preconditions.checkNotNull(httpTransport);
            this.objectParser = objectParser;
            this.setRootUrl(string2);
            this.setServicePath(string3);
            this.httpRequestInitializer = httpRequestInitializer;
        }

        public abstract AbstractGoogleClient build();

        public final String getApplicationName() {
            return this.applicationName;
        }

        public final GoogleClientRequestInitializer getGoogleClientRequestInitializer() {
            return this.googleClientRequestInitializer;
        }

        public final HttpRequestInitializer getHttpRequestInitializer() {
            return this.httpRequestInitializer;
        }

        public ObjectParser getObjectParser() {
            return this.objectParser;
        }

        public final String getRootUrl() {
            return this.rootUrl;
        }

        public final String getServicePath() {
            return this.servicePath;
        }

        public final boolean getSuppressPatternChecks() {
            return this.suppressPatternChecks;
        }

        public final boolean getSuppressRequiredParameterChecks() {
            return this.suppressRequiredParameterChecks;
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public Builder setApplicationName(String string2) {
            this.applicationName = string2;
            return this;
        }

        public Builder setBatchPath(String string2) {
            this.batchPath = string2;
            return this;
        }

        public Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer googleClientRequestInitializer) {
            this.googleClientRequestInitializer = googleClientRequestInitializer;
            return this;
        }

        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            this.httpRequestInitializer = httpRequestInitializer;
            return this;
        }

        public Builder setRootUrl(String string2) {
            this.rootUrl = AbstractGoogleClient.normalizeRootUrl(string2);
            return this;
        }

        public Builder setServicePath(String string2) {
            this.servicePath = AbstractGoogleClient.normalizeServicePath(string2);
            return this;
        }

        public Builder setSuppressAllChecks(boolean bl) {
            return this.setSuppressPatternChecks(true).setSuppressRequiredParameterChecks(true);
        }

        public Builder setSuppressPatternChecks(boolean bl) {
            this.suppressPatternChecks = bl;
            return this;
        }

        public Builder setSuppressRequiredParameterChecks(boolean bl) {
            this.suppressRequiredParameterChecks = bl;
            return this;
        }
    }

}

