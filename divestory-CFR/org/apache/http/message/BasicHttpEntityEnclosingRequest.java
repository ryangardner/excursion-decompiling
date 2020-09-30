/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.message.BasicHttpRequest;

public class BasicHttpEntityEnclosingRequest
extends BasicHttpRequest
implements HttpEntityEnclosingRequest {
    private HttpEntity entity;

    public BasicHttpEntityEnclosingRequest(String string2, String string3) {
        super(string2, string3);
    }

    public BasicHttpEntityEnclosingRequest(String string2, String string3, ProtocolVersion protocolVersion) {
        super(string2, string3, protocolVersion);
    }

    public BasicHttpEntityEnclosingRequest(RequestLine requestLine) {
        super(requestLine);
    }

    @Override
    public boolean expectContinue() {
        Header header = this.getFirstHeader("Expect");
        if (header == null) return false;
        if (!"100-continue".equalsIgnoreCase(header.getValue())) return false;
        return true;
    }

    @Override
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override
    public void setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity;
    }
}

