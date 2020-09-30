/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.batch;

import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

class HttpRequestContent
extends AbstractHttpContent {
    private static final String HTTP_VERSION = "HTTP/1.1";
    static final String NEWLINE = "\r\n";
    private final HttpRequest request;

    HttpRequestContent(HttpRequest httpRequest) {
        super("application/http");
        this.request = httpRequest;
    }

    @Override
    public void writeTo(OutputStream outputStream2) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream2, this.getCharset());
        outputStreamWriter.write(this.request.getRequestMethod());
        outputStreamWriter.write(" ");
        outputStreamWriter.write(this.request.getUrl().build());
        outputStreamWriter.write(" ");
        outputStreamWriter.write(HTTP_VERSION);
        outputStreamWriter.write(NEWLINE);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.fromHttpHeaders(this.request.getHeaders());
        httpHeaders.setAcceptEncoding(null).setUserAgent(null).setContentEncoding(null).setContentType(null).setContentLength(null);
        HttpContent httpContent = this.request.getContent();
        if (httpContent != null) {
            httpHeaders.setContentType(httpContent.getType());
            long l = httpContent.getLength();
            if (l != -1L) {
                httpHeaders.setContentLength(l);
            }
        }
        HttpHeaders.serializeHeadersForMultipartRequests(httpHeaders, null, null, outputStreamWriter);
        outputStreamWriter.write(NEWLINE);
        ((Writer)outputStreamWriter).flush();
        if (httpContent == null) return;
        httpContent.writeTo(outputStream2);
    }
}

