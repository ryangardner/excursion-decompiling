/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.protocol.HttpContext;

public class RequestContent
implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest object, HttpContext object2) throws HttpException, IOException {
        if (object == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (!(object instanceof HttpEntityEnclosingRequest)) return;
        if (object.containsHeader("Transfer-Encoding")) throw new ProtocolException("Transfer-encoding header already present");
        if (object.containsHeader("Content-Length")) throw new ProtocolException("Content-Length header already present");
        object2 = object.getRequestLine().getProtocolVersion();
        HttpEntity httpEntity = ((HttpEntityEnclosingRequest)object).getEntity();
        if (httpEntity == null) {
            object.addHeader("Content-Length", "0");
            return;
        }
        if (!httpEntity.isChunked() && httpEntity.getContentLength() >= 0L) {
            object.addHeader("Content-Length", Long.toString(httpEntity.getContentLength()));
        } else {
            if (((ProtocolVersion)object2).lessEquals(HttpVersion.HTTP_1_0)) {
                object = new StringBuffer();
                ((StringBuffer)object).append("Chunked transfer encoding not allowed for ");
                ((StringBuffer)object).append(object2);
                throw new ProtocolException(((StringBuffer)object).toString());
            }
            object.addHeader("Transfer-Encoding", "chunked");
        }
        if (httpEntity.getContentType() != null && !object.containsHeader("Content-Type")) {
            object.addHeader(httpEntity.getContentType());
        }
        if (httpEntity.getContentEncoding() == null) return;
        if (object.containsHeader("Content-Encoding")) return;
        object.addHeader(httpEntity.getContentEncoding());
    }
}

