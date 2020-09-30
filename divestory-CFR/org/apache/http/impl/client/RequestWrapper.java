/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class RequestWrapper
extends AbstractHttpMessage
implements HttpUriRequest {
    private int execCount;
    private String method;
    private final HttpRequest original;
    private URI uri;
    private ProtocolVersion version;

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public RequestWrapper(HttpRequest httpRequest) throws ProtocolException {
        RequestLine requestLine;
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        this.original = httpRequest;
        this.setParams(httpRequest.getParams());
        this.setHeaders(httpRequest.getAllHeaders());
        if (httpRequest instanceof HttpUriRequest) {
            httpRequest = (HttpUriRequest)httpRequest;
            this.uri = httpRequest.getURI();
            this.method = httpRequest.getMethod();
            this.version = null;
        } else {
            URI uRI;
            requestLine = httpRequest.getRequestLine();
            this.uri = uRI = new URI(requestLine.getUri());
            this.method = requestLine.getMethod();
            this.version = httpRequest.getProtocolVersion();
        }
        this.execCount = 0;
        return;
        catch (URISyntaxException uRISyntaxException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid request URI: ");
            stringBuilder.append(requestLine.getUri());
            throw new ProtocolException(stringBuilder.toString(), uRISyntaxException);
        }
    }

    @Override
    public void abort() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public int getExecCount() {
        return this.execCount;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    public HttpRequest getOriginal() {
        return this.original;
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        if (this.version != null) return this.version;
        this.version = HttpProtocolParams.getVersion(this.getParams());
        return this.version;
    }

    @Override
    public RequestLine getRequestLine() {
        Object object;
        String string2 = this.getMethod();
        ProtocolVersion protocolVersion = this.getProtocolVersion();
        Object object2 = this.uri;
        object2 = object2 != null ? ((URI)object2).toASCIIString() : null;
        if (object2 != null) {
            object = object2;
            if (((String)object2).length() != 0) return new BasicRequestLine(string2, (String)object, protocolVersion);
        }
        object = "/";
        return new BasicRequestLine(string2, (String)object, protocolVersion);
    }

    @Override
    public URI getURI() {
        return this.uri;
    }

    public void incrementExecCount() {
        ++this.execCount;
    }

    @Override
    public boolean isAborted() {
        return false;
    }

    public boolean isRepeatable() {
        return true;
    }

    public void resetHeaders() {
        this.headergroup.clear();
        this.setHeaders(this.original.getAllHeaders());
    }

    public void setMethod(String string2) {
        if (string2 == null) throw new IllegalArgumentException("Method name may not be null");
        this.method = string2;
    }

    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.version = protocolVersion;
    }

    public void setURI(URI uRI) {
        this.uri = uRI;
    }
}

