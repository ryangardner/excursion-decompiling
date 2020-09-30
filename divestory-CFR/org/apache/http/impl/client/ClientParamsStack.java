/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import org.apache.http.params.AbstractHttpParams;
import org.apache.http.params.HttpParams;

public class ClientParamsStack
extends AbstractHttpParams {
    protected final HttpParams applicationParams;
    protected final HttpParams clientParams;
    protected final HttpParams overrideParams;
    protected final HttpParams requestParams;

    public ClientParamsStack(ClientParamsStack clientParamsStack) {
        this(clientParamsStack.getApplicationParams(), clientParamsStack.getClientParams(), clientParamsStack.getRequestParams(), clientParamsStack.getOverrideParams());
    }

    public ClientParamsStack(ClientParamsStack clientParamsStack, HttpParams httpParams, HttpParams httpParams2, HttpParams httpParams3, HttpParams httpParams4) {
        if (httpParams == null) {
            httpParams = clientParamsStack.getApplicationParams();
        }
        if (httpParams2 == null) {
            httpParams2 = clientParamsStack.getClientParams();
        }
        if (httpParams3 == null) {
            httpParams3 = clientParamsStack.getRequestParams();
        }
        if (httpParams4 == null) {
            httpParams4 = clientParamsStack.getOverrideParams();
        }
        this(httpParams, httpParams2, httpParams3, httpParams4);
    }

    public ClientParamsStack(HttpParams httpParams, HttpParams httpParams2, HttpParams httpParams3, HttpParams httpParams4) {
        this.applicationParams = httpParams;
        this.clientParams = httpParams2;
        this.requestParams = httpParams3;
        this.overrideParams = httpParams4;
    }

    @Override
    public HttpParams copy() {
        return this;
    }

    public final HttpParams getApplicationParams() {
        return this.applicationParams;
    }

    public final HttpParams getClientParams() {
        return this.clientParams;
    }

    public final HttpParams getOverrideParams() {
        return this.overrideParams;
    }

    @Override
    public Object getParameter(String string2) {
        HttpParams httpParams;
        if (string2 == null) throw new IllegalArgumentException("Parameter name must not be null.");
        Object object = null;
        Object object2 = this.overrideParams;
        if (object2 != null) {
            object = object2.getParameter(string2);
        }
        object2 = object;
        if (object == null) {
            httpParams = this.requestParams;
            object2 = object;
            if (httpParams != null) {
                object2 = httpParams.getParameter(string2);
            }
        }
        object = object2;
        if (object2 == null) {
            httpParams = this.clientParams;
            object = object2;
            if (httpParams != null) {
                object = httpParams.getParameter(string2);
            }
        }
        object2 = object;
        if (object != null) return object2;
        httpParams = this.applicationParams;
        object2 = object;
        if (httpParams == null) return object2;
        return httpParams.getParameter(string2);
    }

    public final HttpParams getRequestParams() {
        return this.requestParams;
    }

    @Override
    public boolean removeParameter(String string2) {
        throw new UnsupportedOperationException("Removing parameters in a stack is not supported.");
    }

    @Override
    public HttpParams setParameter(String string2, Object object) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Setting parameters in a stack is not supported.");
    }
}

