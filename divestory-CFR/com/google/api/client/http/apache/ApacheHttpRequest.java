/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http.apache;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.http.apache.ApacheHttpResponse;
import com.google.api.client.http.apache.ContentEntity;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

final class ApacheHttpRequest
extends LowLevelHttpRequest {
    private final HttpClient httpClient;
    private final HttpRequestBase request;

    ApacheHttpRequest(HttpClient httpClient, HttpRequestBase httpRequestBase) {
        this.httpClient = httpClient;
        this.request = httpRequestBase;
    }

    @Override
    public void addHeader(String string2, String string3) {
        this.request.addHeader(string2, string3);
    }

    @Override
    public LowLevelHttpResponse execute() throws IOException {
        Object object;
        if (this.getStreamingContent() != null) {
            object = this.request;
            Preconditions.checkState(object instanceof HttpEntityEnclosingRequest, "Apache HTTP client does not support %s requests with content.", ((HttpRequestBase)object).getRequestLine().getMethod());
            object = new ContentEntity(this.getContentLength(), this.getStreamingContent());
            ((AbstractHttpEntity)object).setContentEncoding(this.getContentEncoding());
            ((AbstractHttpEntity)object).setContentType(this.getContentType());
            if (this.getContentLength() == -1L) {
                ((AbstractHttpEntity)object).setChunked(true);
            }
            ((HttpEntityEnclosingRequest)((Object)this.request)).setEntity((HttpEntity)object);
        }
        object = this.request;
        return new ApacheHttpResponse((HttpRequestBase)object, this.httpClient.execute((HttpUriRequest)object));
    }

    @Override
    public void setTimeout(int n, int n2) throws IOException {
        HttpParams httpParams = this.request.getParams();
        ConnManagerParams.setTimeout(httpParams, n);
        HttpConnectionParams.setConnectionTimeout(httpParams, n);
        HttpConnectionParams.setSoTimeout(httpParams, n2);
    }
}

