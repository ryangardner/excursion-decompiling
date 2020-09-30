/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http.apache;

import com.google.api.client.http.LowLevelHttpResponse;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpRequestBase;

final class ApacheHttpResponse
extends LowLevelHttpResponse {
    private final Header[] allHeaders;
    private final HttpRequestBase request;
    private final HttpResponse response;

    ApacheHttpResponse(HttpRequestBase httpRequestBase, HttpResponse httpResponse) {
        this.request = httpRequestBase;
        this.response = httpResponse;
        this.allHeaders = httpResponse.getAllHeaders();
    }

    @Override
    public void disconnect() {
        this.request.abort();
    }

    @Override
    public InputStream getContent() throws IOException {
        HttpEntity httpEntity = this.response.getEntity();
        if (httpEntity != null) return httpEntity.getContent();
        return null;
    }

    @Override
    public String getContentEncoding() {
        Object object = this.response.getEntity();
        if (object == null) return null;
        if ((object = object.getContentEncoding()) == null) return null;
        return object.getValue();
    }

    @Override
    public long getContentLength() {
        HttpEntity httpEntity = this.response.getEntity();
        if (httpEntity != null) return httpEntity.getContentLength();
        return -1L;
    }

    @Override
    public String getContentType() {
        Object object = this.response.getEntity();
        if (object == null) return null;
        if ((object = object.getContentType()) == null) return null;
        return object.getValue();
    }

    @Override
    public int getHeaderCount() {
        return this.allHeaders.length;
    }

    @Override
    public String getHeaderName(int n) {
        return this.allHeaders[n].getName();
    }

    @Override
    public String getHeaderValue(int n) {
        return this.allHeaders[n].getValue();
    }

    public String getHeaderValue(String string2) {
        return this.response.getLastHeader(string2).getValue();
    }

    @Override
    public String getReasonPhrase() {
        StatusLine statusLine = this.response.getStatusLine();
        if (statusLine != null) return statusLine.getReasonPhrase();
        return null;
    }

    @Override
    public int getStatusCode() {
        StatusLine statusLine = this.response.getStatusLine();
        if (statusLine != null) return statusLine.getStatusCode();
        return 0;
    }

    @Override
    public String getStatusLine() {
        StatusLine statusLine = this.response.getStatusLine();
        if (statusLine != null) return statusLine.toString();
        return null;
    }
}

