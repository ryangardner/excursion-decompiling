/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.ConsumingInputStream;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.Charsets;
import com.google.api.client.util.IOUtils;
import com.google.api.client.util.LoggingInputStream;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

public final class HttpResponse {
    private static final String CONTENT_ENCODING_GZIP = "gzip";
    private static final String CONTENT_ENCODING_XGZIP = "x-gzip";
    private InputStream content;
    private final String contentEncoding;
    private int contentLoggingLimit;
    private boolean contentRead;
    private final String contentType;
    private boolean loggingEnabled;
    private final HttpMediaType mediaType;
    private final HttpRequest request;
    LowLevelHttpResponse response;
    private final boolean returnRawInputStream;
    private final int statusCode;
    private final String statusMessage;

    HttpResponse(HttpRequest httpRequest, LowLevelHttpResponse object) throws IOException {
        StringBuilder stringBuilder;
        this.request = httpRequest;
        this.returnRawInputStream = httpRequest.getResponseReturnRawInputStream();
        this.contentLoggingLimit = httpRequest.getContentLoggingLimit();
        this.loggingEnabled = httpRequest.isLoggingEnabled();
        this.response = object;
        this.contentEncoding = ((LowLevelHttpResponse)object).getContentEncoding();
        int n = ((LowLevelHttpResponse)object).getStatusCode();
        int n2 = 0;
        int n3 = n;
        if (n < 0) {
            n3 = 0;
        }
        this.statusCode = n3;
        Object object2 = ((LowLevelHttpResponse)object).getReasonPhrase();
        this.statusMessage = object2;
        Logger logger = HttpTransport.LOGGER;
        n3 = n2;
        if (this.loggingEnabled) {
            n3 = n2;
            if (logger.isLoggable(Level.CONFIG)) {
                n3 = 1;
            }
        }
        CharSequence charSequence = null;
        if (n3 != 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("-------------- RESPONSE --------------");
            stringBuilder.append(StringUtils.LINE_SEPARATOR);
            String string2 = ((LowLevelHttpResponse)object).getStatusLine();
            if (string2 != null) {
                stringBuilder.append(string2);
            } else {
                stringBuilder.append(this.statusCode);
                if (object2 != null) {
                    stringBuilder.append(' ');
                    stringBuilder.append((String)object2);
                }
            }
            stringBuilder.append(StringUtils.LINE_SEPARATOR);
        } else {
            stringBuilder = null;
        }
        object2 = httpRequest.getResponseHeaders();
        if (n3 != 0) {
            charSequence = stringBuilder;
        }
        ((HttpHeaders)object2).fromHttpResponse((LowLevelHttpResponse)object, (StringBuilder)charSequence);
        charSequence = ((LowLevelHttpResponse)object).getContentType();
        object = charSequence;
        if (charSequence == null) {
            object = httpRequest.getResponseHeaders().getContentType();
        }
        this.contentType = object;
        this.mediaType = HttpResponse.parseMediaType((String)object);
        if (n3 == 0) return;
        logger.config(stringBuilder.toString());
    }

    private boolean hasMessageBody() throws IOException {
        int n = this.getStatusCode();
        if (!this.getRequest().getRequestMethod().equals("HEAD") && n / 100 != 1 && n != 204) {
            if (n != 304) return true;
        }
        this.ignore();
        return false;
    }

    private static HttpMediaType parseMediaType(String object) {
        if (object == null) {
            return null;
        }
        try {
            return new HttpMediaType((String)object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    public void disconnect() throws IOException {
        this.ignore();
        this.response.disconnect();
    }

    public void download(OutputStream outputStream2) throws IOException {
        IOUtils.copy(this.getContent(), outputStream2);
    }

    public InputStream getContent() throws IOException {
        block8 : {
            if (this.contentRead) return this.content;
            InputStream inputStream2 = this.response.getContent();
            if (inputStream2 == null) break block8;
            InputStream inputStream3 = inputStream2;
            InputStream inputStream4 = inputStream2;
            InputStream inputStream5 = inputStream2;
            try {
                Object object;
                block9 : {
                    block10 : {
                        if (this.returnRawInputStream) break block9;
                        inputStream3 = inputStream2;
                        inputStream4 = inputStream2;
                        inputStream5 = inputStream2;
                        if (this.contentEncoding == null) break block9;
                        inputStream4 = inputStream2;
                        inputStream5 = inputStream2;
                        object = this.contentEncoding.trim().toLowerCase(Locale.ENGLISH);
                        inputStream4 = inputStream2;
                        inputStream5 = inputStream2;
                        if (CONTENT_ENCODING_GZIP.equals(object)) break block10;
                        inputStream3 = inputStream2;
                        inputStream4 = inputStream2;
                        inputStream5 = inputStream2;
                        if (!CONTENT_ENCODING_XGZIP.equals(object)) break block9;
                    }
                    inputStream4 = inputStream2;
                    inputStream5 = inputStream2;
                    inputStream4 = inputStream2;
                    inputStream5 = inputStream2;
                    inputStream4 = inputStream2;
                    inputStream5 = inputStream2;
                    object = new GZIPInputStream(inputStream2);
                    inputStream4 = inputStream2;
                    inputStream5 = inputStream2;
                    inputStream3 = new ConsumingInputStream((InputStream)object);
                }
                inputStream4 = inputStream3;
                inputStream5 = inputStream3;
                object = HttpTransport.LOGGER;
                inputStream2 = inputStream3;
                inputStream4 = inputStream3;
                inputStream5 = inputStream3;
                if (this.loggingEnabled) {
                    inputStream2 = inputStream3;
                    inputStream4 = inputStream3;
                    inputStream5 = inputStream3;
                    if (((Logger)object).isLoggable(Level.CONFIG)) {
                        inputStream4 = inputStream3;
                        inputStream5 = inputStream3;
                        inputStream4 = inputStream3;
                        inputStream5 = inputStream3;
                        inputStream2 = new LoggingInputStream(inputStream3, (Logger)object, Level.CONFIG, this.contentLoggingLimit);
                    }
                }
                inputStream4 = inputStream2;
                inputStream5 = inputStream2;
                this.content = inputStream2;
            }
            catch (Throwable throwable) {
                inputStream4.close();
                throw throwable;
            }
            catch (EOFException eOFException) {
                inputStream5.close();
            }
        }
        this.contentRead = true;
        return this.content;
    }

    public Charset getContentCharset() {
        HttpMediaType httpMediaType = this.mediaType;
        if (httpMediaType == null) return Charsets.ISO_8859_1;
        if (httpMediaType.getCharsetParameter() == null) return Charsets.ISO_8859_1;
        return this.mediaType.getCharsetParameter();
    }

    public String getContentEncoding() {
        return this.contentEncoding;
    }

    public int getContentLoggingLimit() {
        return this.contentLoggingLimit;
    }

    public String getContentType() {
        return this.contentType;
    }

    public HttpHeaders getHeaders() {
        return this.request.getResponseHeaders();
    }

    public HttpMediaType getMediaType() {
        return this.mediaType;
    }

    public HttpRequest getRequest() {
        return this.request;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public HttpTransport getTransport() {
        return this.request.getTransport();
    }

    public void ignore() throws IOException {
        InputStream inputStream2 = this.getContent();
        if (inputStream2 == null) return;
        inputStream2.close();
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public boolean isSuccessStatusCode() {
        return HttpStatusCodes.isSuccess(this.statusCode);
    }

    public <T> T parseAs(Class<T> class_) throws IOException {
        if (this.hasMessageBody()) return this.request.getParser().parseAndClose(this.getContent(), this.getContentCharset(), class_);
        return null;
    }

    public Object parseAs(Type type) throws IOException {
        if (this.hasMessageBody()) return this.request.getParser().parseAndClose(this.getContent(), this.getContentCharset(), type);
        return null;
    }

    public String parseAsString() throws IOException {
        InputStream inputStream2 = this.getContent();
        if (inputStream2 == null) {
            return "";
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream2, byteArrayOutputStream);
        return byteArrayOutputStream.toString(this.getContentCharset().name());
    }

    public HttpResponse setContentLoggingLimit(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "The content logging limit must be non-negative.");
        this.contentLoggingLimit = n;
        return this;
    }

    public HttpResponse setLoggingEnabled(boolean bl) {
        this.loggingEnabled = bl;
        return this;
    }
}

