/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.methods;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.CloneUtils;

public abstract class HttpEntityEnclosingRequestBase
extends HttpRequestBase
implements HttpEntityEnclosingRequest {
    private HttpEntity entity;

    @Override
    public Object clone() throws CloneNotSupportedException {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = (HttpEntityEnclosingRequestBase)super.clone();
        HttpEntity httpEntity = this.entity;
        if (httpEntity == null) return httpEntityEnclosingRequestBase;
        httpEntityEnclosingRequestBase.entity = (HttpEntity)CloneUtils.clone(httpEntity);
        return httpEntityEnclosingRequestBase;
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

