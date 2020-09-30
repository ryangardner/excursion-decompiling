/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.services;

import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.googleapis.MethodOverride;
import com.google.api.client.googleapis.batch.BatchCallback;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GZipEncoding;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpEncoding;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpResponseInterceptor;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UriTemplate;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.common.base.StandardSystemProperty;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractGoogleClientRequest<T>
extends GenericData {
    private static final String API_VERSION_HEADER = "X-Goog-Api-Client";
    public static final String USER_AGENT_SUFFIX = "Google-API-Java-Client";
    private final AbstractGoogleClient abstractGoogleClient;
    private boolean disableGZipContent;
    private MediaHttpDownloader downloader;
    private final HttpContent httpContent;
    private HttpHeaders lastResponseHeaders;
    private int lastStatusCode = -1;
    private String lastStatusMessage;
    private HttpHeaders requestHeaders = new HttpHeaders();
    private final String requestMethod;
    private Class<T> responseClass;
    private boolean returnRawInputStream;
    private MediaHttpUploader uploader;
    private final String uriTemplate;

    protected AbstractGoogleClientRequest(AbstractGoogleClient object, String object2, String charSequence, HttpContent httpContent, Class<T> class_) {
        this.responseClass = Preconditions.checkNotNull(class_);
        this.abstractGoogleClient = Preconditions.checkNotNull(object);
        this.requestMethod = Preconditions.checkNotNull(object2);
        this.uriTemplate = Preconditions.checkNotNull(charSequence);
        this.httpContent = httpContent;
        object = ((AbstractGoogleClient)object).getApplicationName();
        if (object != null) {
            object2 = this.requestHeaders;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(USER_AGENT_SUFFIX);
            ((StringBuilder)charSequence).append("/");
            ((StringBuilder)charSequence).append(GoogleUtils.VERSION);
            ((HttpHeaders)object2).setUserAgent(((StringBuilder)charSequence).toString());
        } else {
            object2 = this.requestHeaders;
            object = new StringBuilder();
            ((StringBuilder)object).append("Google-API-Java-Client/");
            ((StringBuilder)object).append(GoogleUtils.VERSION);
            ((HttpHeaders)object2).setUserAgent(((StringBuilder)object).toString());
        }
        this.requestHeaders.set(API_VERSION_HEADER, ApiClientVersion.DEFAULT_VERSION);
    }

    private HttpRequest buildHttpRequest(boolean bl) throws IOException {
        Object object = this.uploader;
        boolean bl2 = true;
        boolean bl3 = object == null;
        Preconditions.checkArgument(bl3);
        bl3 = bl2;
        if (bl) {
            bl3 = this.requestMethod.equals("GET") ? bl2 : false;
        }
        Preconditions.checkArgument(bl3);
        object = bl ? "HEAD" : this.requestMethod;
        object = this.getAbstractGoogleClient().getRequestFactory().buildRequest((String)object, this.buildHttpRequestUrl(), this.httpContent);
        new MethodOverride().intercept((HttpRequest)object);
        ((HttpRequest)object).setParser(this.getAbstractGoogleClient().getObjectParser());
        if (this.httpContent == null && (this.requestMethod.equals("POST") || this.requestMethod.equals("PUT") || this.requestMethod.equals("PATCH"))) {
            ((HttpRequest)object).setContent(new EmptyContent());
        }
        ((HttpRequest)object).getHeaders().putAll(this.requestHeaders);
        if (!this.disableGZipContent) {
            ((HttpRequest)object).setEncoding(new GZipEncoding());
        }
        ((HttpRequest)object).setResponseReturnRawInputStream(this.returnRawInputStream);
        ((HttpRequest)object).setResponseInterceptor(new HttpResponseInterceptor(((HttpRequest)object).getResponseInterceptor(), (HttpRequest)object){
            final /* synthetic */ HttpRequest val$httpRequest;
            final /* synthetic */ HttpResponseInterceptor val$responseInterceptor;
            {
                this.val$responseInterceptor = httpResponseInterceptor;
                this.val$httpRequest = httpRequest;
            }

            @Override
            public void interceptResponse(HttpResponse httpResponse) throws IOException {
                HttpResponseInterceptor httpResponseInterceptor = this.val$responseInterceptor;
                if (httpResponseInterceptor != null) {
                    httpResponseInterceptor.interceptResponse(httpResponse);
                }
                if (httpResponse.isSuccessStatusCode()) return;
                if (this.val$httpRequest.getThrowExceptionOnExecuteError()) throw AbstractGoogleClientRequest.this.newExceptionOnError(httpResponse);
            }
        });
        return object;
    }

    private HttpResponse executeUnparsed(boolean bl) throws IOException {
        Object object;
        if (this.uploader == null) {
            object = this.buildHttpRequest(bl).execute();
        } else {
            object = this.buildHttpRequestUrl();
            bl = this.getAbstractGoogleClient().getRequestFactory().buildRequest(this.requestMethod, (GenericUrl)object, this.httpContent).getThrowExceptionOnExecuteError();
            HttpResponse httpResponse = this.uploader.setInitiationHeaders(this.requestHeaders).setDisableGZipContent(this.disableGZipContent).upload((GenericUrl)object);
            httpResponse.getRequest().setParser(this.getAbstractGoogleClient().getObjectParser());
            object = httpResponse;
            if (bl) {
                if (!httpResponse.isSuccessStatusCode()) throw this.newExceptionOnError(httpResponse);
                object = httpResponse;
            }
        }
        this.lastResponseHeaders = ((HttpResponse)object).getHeaders();
        this.lastStatusCode = ((HttpResponse)object).getStatusCode();
        this.lastStatusMessage = ((HttpResponse)object).getStatusMessage();
        return object;
    }

    public HttpRequest buildHttpRequest() throws IOException {
        return this.buildHttpRequest(false);
    }

    public GenericUrl buildHttpRequestUrl() {
        return new GenericUrl(UriTemplate.expand(this.abstractGoogleClient.getBaseUrl(), this.uriTemplate, this, true));
    }

    protected HttpRequest buildHttpRequestUsingHead() throws IOException {
        return this.buildHttpRequest(true);
    }

    protected final void checkRequiredParameter(Object object, String string2) {
        boolean bl = this.abstractGoogleClient.getSuppressRequiredParameterChecks() || object != null;
        Preconditions.checkArgument(bl, "Required parameter %s must be specified", string2);
    }

    public T execute() throws IOException {
        return this.executeUnparsed().parseAs(this.responseClass);
    }

    public void executeAndDownloadTo(OutputStream outputStream2) throws IOException {
        this.executeUnparsed().download(outputStream2);
    }

    public InputStream executeAsInputStream() throws IOException {
        return this.executeUnparsed().getContent();
    }

    protected HttpResponse executeMedia() throws IOException {
        this.set("alt", "media");
        return this.executeUnparsed();
    }

    protected void executeMediaAndDownloadTo(OutputStream outputStream2) throws IOException {
        MediaHttpDownloader mediaHttpDownloader = this.downloader;
        if (mediaHttpDownloader == null) {
            this.executeMedia().download(outputStream2);
            return;
        }
        mediaHttpDownloader.download(this.buildHttpRequestUrl(), this.requestHeaders, outputStream2);
    }

    protected InputStream executeMediaAsInputStream() throws IOException {
        return this.executeMedia().getContent();
    }

    public HttpResponse executeUnparsed() throws IOException {
        return this.executeUnparsed(false);
    }

    protected HttpResponse executeUsingHead() throws IOException {
        boolean bl = this.uploader == null;
        Preconditions.checkArgument(bl);
        HttpResponse httpResponse = this.executeUnparsed(true);
        httpResponse.ignore();
        return httpResponse;
    }

    public AbstractGoogleClient getAbstractGoogleClient() {
        return this.abstractGoogleClient;
    }

    public final boolean getDisableGZipContent() {
        return this.disableGZipContent;
    }

    public final HttpContent getHttpContent() {
        return this.httpContent;
    }

    public final HttpHeaders getLastResponseHeaders() {
        return this.lastResponseHeaders;
    }

    public final int getLastStatusCode() {
        return this.lastStatusCode;
    }

    public final String getLastStatusMessage() {
        return this.lastStatusMessage;
    }

    public final MediaHttpDownloader getMediaHttpDownloader() {
        return this.downloader;
    }

    public final MediaHttpUploader getMediaHttpUploader() {
        return this.uploader;
    }

    public final HttpHeaders getRequestHeaders() {
        return this.requestHeaders;
    }

    public final String getRequestMethod() {
        return this.requestMethod;
    }

    public final Class<T> getResponseClass() {
        return this.responseClass;
    }

    public final boolean getReturnRawInputSteam() {
        return this.returnRawInputStream;
    }

    public final String getUriTemplate() {
        return this.uriTemplate;
    }

    protected final void initializeMediaDownload() {
        HttpRequestFactory httpRequestFactory = this.abstractGoogleClient.getRequestFactory();
        this.downloader = new MediaHttpDownloader(httpRequestFactory.getTransport(), httpRequestFactory.getInitializer());
    }

    protected final void initializeMediaUpload(AbstractInputStreamContent object) {
        HttpRequestFactory httpRequestFactory = this.abstractGoogleClient.getRequestFactory();
        this.uploader = object = new MediaHttpUploader((AbstractInputStreamContent)object, httpRequestFactory.getTransport(), httpRequestFactory.getInitializer());
        ((MediaHttpUploader)object).setInitiationRequestMethod(this.requestMethod);
        object = this.httpContent;
        if (object == null) return;
        this.uploader.setMetadata((HttpContent)object);
    }

    protected IOException newExceptionOnError(HttpResponse httpResponse) {
        return new HttpResponseException(httpResponse);
    }

    public final <E> void queue(BatchRequest batchRequest, Class<E> class_, BatchCallback<T, E> batchCallback) throws IOException {
        boolean bl = this.uploader == null;
        Preconditions.checkArgument(bl, "Batching media requests is not supported");
        batchRequest.queue(this.buildHttpRequest(), this.getResponseClass(), class_, batchCallback);
    }

    @Override
    public AbstractGoogleClientRequest<T> set(String string2, Object object) {
        return (AbstractGoogleClientRequest)super.set(string2, object);
    }

    public AbstractGoogleClientRequest<T> setDisableGZipContent(boolean bl) {
        this.disableGZipContent = bl;
        return this;
    }

    public AbstractGoogleClientRequest<T> setRequestHeaders(HttpHeaders httpHeaders) {
        this.requestHeaders = httpHeaders;
        return this;
    }

    public AbstractGoogleClientRequest<T> setReturnRawInputStream(boolean bl) {
        this.returnRawInputStream = bl;
        return this;
    }

    static class ApiClientVersion {
        static final String DEFAULT_VERSION = new ApiClientVersion().toString();
        private final String versionString;

        ApiClientVersion() {
            this(ApiClientVersion.getJavaVersion(), StandardSystemProperty.OS_NAME.value(), StandardSystemProperty.OS_VERSION.value(), GoogleUtils.VERSION);
        }

        ApiClientVersion(String string2, String string3, String string4, String string5) {
            StringBuilder stringBuilder = new StringBuilder("gl-java/");
            stringBuilder.append(ApiClientVersion.formatSemver(string2));
            stringBuilder.append(" gdcl/");
            stringBuilder.append(ApiClientVersion.formatSemver(string5));
            if (string3 != null && string4 != null) {
                stringBuilder.append(" ");
                stringBuilder.append(ApiClientVersion.formatName(string3));
                stringBuilder.append("/");
                stringBuilder.append(ApiClientVersion.formatSemver(string4));
            }
            this.versionString = stringBuilder.toString();
        }

        private static String formatName(String string2) {
            return string2.toLowerCase().replaceAll("[^\\w\\d\\-]", "-");
        }

        private static String formatSemver(String string2) {
            return ApiClientVersion.formatSemver(string2, string2);
        }

        private static String formatSemver(String object, String string2) {
            if (object == null) {
                return null;
            }
            object = Pattern.compile("(\\d+\\.\\d+\\.\\d+).*").matcher((CharSequence)object);
            if (!((Matcher)object).find()) return string2;
            return ((Matcher)object).group(1);
        }

        private static String getJavaVersion() {
            Object object = System.getProperty("java.version");
            if (object == null) {
                return null;
            }
            CharSequence charSequence = ApiClientVersion.formatSemver((String)object, null);
            if (charSequence != null) {
                return charSequence;
            }
            object = Pattern.compile("^(\\d+)[^\\d]?").matcher((CharSequence)object);
            if (!((Matcher)object).find()) return null;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(((Matcher)object).group(1));
            ((StringBuilder)charSequence).append(".0.0");
            return ((StringBuilder)charSequence).toString();
        }

        public String toString() {
            return this.versionString;
        }
    }

}

