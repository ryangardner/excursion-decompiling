/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.ProtocolException;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.RequestWrapper;

public class EntityEnclosingRequestWrapper
extends RequestWrapper
implements HttpEntityEnclosingRequest {
    private boolean consumed;
    private HttpEntity entity;

    public EntityEnclosingRequestWrapper(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws ProtocolException {
        super(httpEntityEnclosingRequest);
        this.setEntity(httpEntityEnclosingRequest.getEntity());
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
    public boolean isRepeatable() {
        HttpEntity httpEntity = this.entity;
        if (httpEntity == null) return true;
        if (httpEntity.isRepeatable()) return true;
        if (!this.consumed) return true;
        return false;
    }

    @Override
    public void setEntity(HttpEntity httpEntity) {
        httpEntity = httpEntity != null ? new EntityWrapper(httpEntity) : null;
        this.entity = httpEntity;
        this.consumed = false;
    }

    class EntityWrapper
    extends HttpEntityWrapper {
        EntityWrapper(HttpEntity httpEntity) {
            super(httpEntity);
        }

        @Deprecated
        @Override
        public void consumeContent() throws IOException {
            EntityEnclosingRequestWrapper.this.consumed = true;
            super.consumeContent();
        }

        @Override
        public InputStream getContent() throws IOException {
            EntityEnclosingRequestWrapper.this.consumed = true;
            return super.getContent();
        }

        @Override
        public void writeTo(OutputStream outputStream2) throws IOException {
            EntityEnclosingRequestWrapper.this.consumed = true;
            super.writeTo(outputStream2);
        }
    }

}

