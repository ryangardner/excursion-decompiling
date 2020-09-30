/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.protocol.HttpContext;

public class ResponseContent
implements HttpResponseInterceptor {
    @Override
    public void process(HttpResponse httpResponse, HttpContext object) throws HttpException, IOException {
        if (httpResponse == null) throw new IllegalArgumentException("HTTP response may not be null");
        if (httpResponse.containsHeader("Transfer-Encoding")) throw new ProtocolException("Transfer-encoding header already present");
        if (httpResponse.containsHeader("Content-Length")) throw new ProtocolException("Content-Length header already present");
        object = httpResponse.getStatusLine().getProtocolVersion();
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity == null) {
            int n = httpResponse.getStatusLine().getStatusCode();
            if (n == 204) return;
            if (n == 304) return;
            if (n == 205) return;
            httpResponse.addHeader("Content-Length", "0");
            return;
        }
        long l = httpEntity.getContentLength();
        if (httpEntity.isChunked() && !((ProtocolVersion)object).lessEquals(HttpVersion.HTTP_1_0)) {
            httpResponse.addHeader("Transfer-Encoding", "chunked");
        } else if (l >= 0L) {
            httpResponse.addHeader("Content-Length", Long.toString(httpEntity.getContentLength()));
        }
        if (httpEntity.getContentType() != null && !httpResponse.containsHeader("Content-Type")) {
            httpResponse.addHeader(httpEntity.getContentType());
        }
        if (httpEntity.getContentEncoding() == null) return;
        if (httpResponse.containsHeader("Content-Encoding")) return;
        httpResponse.addHeader(httpEntity.getContentEncoding());
    }
}

