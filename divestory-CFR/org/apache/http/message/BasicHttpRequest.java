/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import org.apache.http.HttpRequest;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class BasicHttpRequest
extends AbstractHttpMessage
implements HttpRequest {
    private final String method;
    private RequestLine requestline;
    private final String uri;

    public BasicHttpRequest(String string2, String string3) {
        if (string2 == null) throw new IllegalArgumentException("Method name may not be null");
        if (string3 == null) throw new IllegalArgumentException("Request URI may not be null");
        this.method = string2;
        this.uri = string3;
        this.requestline = null;
    }

    public BasicHttpRequest(String string2, String string3, ProtocolVersion protocolVersion) {
        this(new BasicRequestLine(string2, string3, protocolVersion));
    }

    public BasicHttpRequest(RequestLine requestLine) {
        if (requestLine == null) throw new IllegalArgumentException("Request line may not be null");
        this.requestline = requestLine;
        this.method = requestLine.getMethod();
        this.uri = requestLine.getUri();
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return this.getRequestLine().getProtocolVersion();
    }

    @Override
    public RequestLine getRequestLine() {
        if (this.requestline != null) return this.requestline;
        ProtocolVersion protocolVersion = HttpProtocolParams.getVersion(this.getParams());
        this.requestline = new BasicRequestLine(this.method, this.uri, protocolVersion);
        return this.requestline;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.method);
        stringBuffer.append(" ");
        stringBuffer.append(this.uri);
        stringBuffer.append(" ");
        stringBuffer.append(this.headergroup);
        return stringBuffer.toString();
    }
}

