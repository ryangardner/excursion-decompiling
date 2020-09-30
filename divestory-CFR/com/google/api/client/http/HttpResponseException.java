/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import java.io.IOException;

public class HttpResponseException
extends IOException {
    private static final long serialVersionUID = -1875819453475890043L;
    private final String content;
    private final transient HttpHeaders headers;
    private final int statusCode;
    private final String statusMessage;

    public HttpResponseException(HttpResponse httpResponse) {
        this(new Builder(httpResponse));
    }

    protected HttpResponseException(Builder builder) {
        super(builder.message);
        this.statusCode = builder.statusCode;
        this.statusMessage = builder.statusMessage;
        this.headers = builder.headers;
        this.content = builder.content;
    }

    public static StringBuilder computeMessageBuffer(HttpResponse object) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = ((HttpResponse)object).getStatusCode();
        if (n != 0) {
            stringBuilder.append(n);
        }
        if ((object = ((HttpResponse)object).getStatusMessage()) == null) return stringBuilder;
        if (n != 0) {
            stringBuilder.append(' ');
        }
        stringBuilder.append((String)object);
        return stringBuilder;
    }

    public final String getContent() {
        return this.content;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public final int getStatusCode() {
        return this.statusCode;
    }

    public final String getStatusMessage() {
        return this.statusMessage;
    }

    public final boolean isSuccessStatusCode() {
        return HttpStatusCodes.isSuccess(this.statusCode);
    }

    public static class Builder {
        String content;
        HttpHeaders headers;
        String message;
        int statusCode;
        String statusMessage;

        public Builder(int n, String string2, HttpHeaders httpHeaders) {
            this.setStatusCode(n);
            this.setStatusMessage(string2);
            this.setHeaders(httpHeaders);
        }

        public Builder(HttpResponse object) {
            this(((HttpResponse)object).getStatusCode(), ((HttpResponse)object).getStatusMessage(), ((HttpResponse)object).getHeaders());
            try {
                String string2;
                this.content = string2 = ((HttpResponse)object).parseAsString();
                if (string2.length() == 0) {
                    this.content = null;
                }
            }
            catch (IllegalArgumentException illegalArgumentException) {
                illegalArgumentException.printStackTrace();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            object = HttpResponseException.computeMessageBuffer((HttpResponse)object);
            if (this.content != null) {
                ((StringBuilder)object).append(StringUtils.LINE_SEPARATOR);
                ((StringBuilder)object).append(this.content);
            }
            this.message = ((StringBuilder)object).toString();
        }

        public HttpResponseException build() {
            return new HttpResponseException(this);
        }

        public final String getContent() {
            return this.content;
        }

        public HttpHeaders getHeaders() {
            return this.headers;
        }

        public final String getMessage() {
            return this.message;
        }

        public final int getStatusCode() {
            return this.statusCode;
        }

        public final String getStatusMessage() {
            return this.statusMessage;
        }

        public Builder setContent(String string2) {
            this.content = string2;
            return this;
        }

        public Builder setHeaders(HttpHeaders httpHeaders) {
            this.headers = Preconditions.checkNotNull(httpHeaders);
            return this;
        }

        public Builder setMessage(String string2) {
            this.message = string2;
            return this;
        }

        public Builder setStatusCode(int n) {
            boolean bl = n >= 0;
            Preconditions.checkArgument(bl);
            this.statusCode = n;
            return this;
        }

        public Builder setStatusMessage(String string2) {
            this.statusMessage = string2;
            return this;
        }
    }

}

