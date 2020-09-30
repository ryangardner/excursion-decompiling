/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.methods;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.AbortableHttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.CloneUtils;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionReleaseTrigger;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public abstract class HttpRequestBase
extends AbstractHttpMessage
implements HttpUriRequest,
AbortableHttpRequest,
Cloneable {
    private Lock abortLock = new ReentrantLock();
    private boolean aborted;
    private ClientConnectionRequest connRequest;
    private ConnectionReleaseTrigger releaseTrigger;
    private URI uri;

    @Override
    public void abort() {
        this.abortLock.lock();
        boolean bl = this.aborted;
        if (bl) {
            this.abortLock.unlock();
            return;
        }
        this.aborted = true;
        ClientConnectionRequest clientConnectionRequest = this.connRequest;
        ConnectionReleaseTrigger connectionReleaseTrigger = this.releaseTrigger;
        if (clientConnectionRequest != null) {
            clientConnectionRequest.abortRequest();
        }
        if (connectionReleaseTrigger == null) return;
        try {
            connectionReleaseTrigger.abortConnection();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public Object clone() throws CloneNotSupportedException {
        HttpRequestBase httpRequestBase = (HttpRequestBase)Object.super.clone();
        httpRequestBase.abortLock = new ReentrantLock();
        httpRequestBase.aborted = false;
        httpRequestBase.releaseTrigger = null;
        httpRequestBase.connRequest = null;
        httpRequestBase.headergroup = (HeaderGroup)CloneUtils.clone(this.headergroup);
        httpRequestBase.params = (HttpParams)CloneUtils.clone(this.params);
        return httpRequestBase;
    }

    @Override
    public abstract String getMethod();

    @Override
    public ProtocolVersion getProtocolVersion() {
        return HttpProtocolParams.getVersion(this.getParams());
    }

    @Override
    public RequestLine getRequestLine() {
        Object object;
        String string2 = this.getMethod();
        ProtocolVersion protocolVersion = this.getProtocolVersion();
        Object object2 = this.getURI();
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

    @Override
    public boolean isAborted() {
        return this.aborted;
    }

    @Override
    public void setConnectionRequest(ClientConnectionRequest object) throws IOException {
        this.abortLock.lock();
        try {
            if (!this.aborted) {
                this.releaseTrigger = null;
                this.connRequest = object;
                return;
            }
            object = new IOException("Request already aborted");
            throw object;
        }
        finally {
            this.abortLock.unlock();
        }
    }

    @Override
    public void setReleaseTrigger(ConnectionReleaseTrigger object) throws IOException {
        this.abortLock.lock();
        try {
            if (!this.aborted) {
                this.connRequest = null;
                this.releaseTrigger = object;
                return;
            }
            object = new IOException("Request already aborted");
            throw object;
        }
        finally {
            this.abortLock.unlock();
        }
    }

    public void setURI(URI uRI) {
        this.uri = uRI;
    }
}

