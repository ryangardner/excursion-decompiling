/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.protocol.HttpContext;

public class ResponseConnControl
implements HttpResponseInterceptor {
    @Override
    public void process(HttpResponse httpResponse, HttpContext object) throws HttpException, IOException {
        if (httpResponse == null) throw new IllegalArgumentException("HTTP response may not be null");
        if (object == null) throw new IllegalArgumentException("HTTP context may not be null");
        int n = httpResponse.getStatusLine().getStatusCode();
        if (n != 400 && n != 408 && n != 411 && n != 413 && n != 414 && n != 503 && n != 501) {
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                ProtocolVersion protocolVersion = httpResponse.getStatusLine().getProtocolVersion();
                if (httpEntity.getContentLength() < 0L && (!httpEntity.isChunked() || protocolVersion.lessEquals(HttpVersion.HTTP_1_0))) {
                    httpResponse.setHeader("Connection", "Close");
                    return;
                }
            }
            if ((object = (HttpRequest)object.getAttribute("http.request")) == null) return;
            if ((object = object.getFirstHeader("Connection")) == null) return;
            httpResponse.setHeader("Connection", object.getValue());
            return;
        }
        httpResponse.setHeader("Connection", "Close");
    }
}

