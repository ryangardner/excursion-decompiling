/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestInterceptorList;
import org.apache.http.protocol.HttpResponseInterceptorList;

public final class BasicHttpProcessor
implements HttpProcessor,
HttpRequestInterceptorList,
HttpResponseInterceptorList,
Cloneable {
    protected final List requestInterceptors = new ArrayList();
    protected final List responseInterceptors = new ArrayList();

    public final void addInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
        this.addRequestInterceptor(httpRequestInterceptor);
    }

    public final void addInterceptor(HttpRequestInterceptor httpRequestInterceptor, int n) {
        this.addRequestInterceptor(httpRequestInterceptor, n);
    }

    public final void addInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        this.addResponseInterceptor(httpResponseInterceptor);
    }

    public final void addInterceptor(HttpResponseInterceptor httpResponseInterceptor, int n) {
        this.addResponseInterceptor(httpResponseInterceptor, n);
    }

    @Override
    public void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return;
        }
        this.requestInterceptors.add(httpRequestInterceptor);
    }

    @Override
    public void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor, int n) {
        if (httpRequestInterceptor == null) {
            return;
        }
        this.requestInterceptors.add(n, httpRequestInterceptor);
    }

    @Override
    public void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return;
        }
        this.responseInterceptors.add(httpResponseInterceptor);
    }

    @Override
    public void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor, int n) {
        if (httpResponseInterceptor == null) {
            return;
        }
        this.responseInterceptors.add(n, httpResponseInterceptor);
    }

    public void clearInterceptors() {
        this.clearRequestInterceptors();
        this.clearResponseInterceptors();
    }

    @Override
    public void clearRequestInterceptors() {
        this.requestInterceptors.clear();
    }

    @Override
    public void clearResponseInterceptors() {
        this.responseInterceptors.clear();
    }

    public Object clone() throws CloneNotSupportedException {
        BasicHttpProcessor basicHttpProcessor = (BasicHttpProcessor)super.clone();
        this.copyInterceptors(basicHttpProcessor);
        return basicHttpProcessor;
    }

    public BasicHttpProcessor copy() {
        BasicHttpProcessor basicHttpProcessor = new BasicHttpProcessor();
        this.copyInterceptors(basicHttpProcessor);
        return basicHttpProcessor;
    }

    protected void copyInterceptors(BasicHttpProcessor basicHttpProcessor) {
        basicHttpProcessor.requestInterceptors.clear();
        basicHttpProcessor.requestInterceptors.addAll(this.requestInterceptors);
        basicHttpProcessor.responseInterceptors.clear();
        basicHttpProcessor.responseInterceptors.addAll(this.responseInterceptors);
    }

    @Override
    public HttpRequestInterceptor getRequestInterceptor(int n) {
        if (n < 0) return null;
        if (n < this.requestInterceptors.size()) return (HttpRequestInterceptor)this.requestInterceptors.get(n);
        return null;
    }

    @Override
    public int getRequestInterceptorCount() {
        return this.requestInterceptors.size();
    }

    @Override
    public HttpResponseInterceptor getResponseInterceptor(int n) {
        if (n < 0) return null;
        if (n < this.responseInterceptors.size()) return (HttpResponseInterceptor)this.responseInterceptors.get(n);
        return null;
    }

    @Override
    public int getResponseInterceptorCount() {
        return this.responseInterceptors.size();
    }

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws IOException, HttpException {
        int n = 0;
        while (n < this.requestInterceptors.size()) {
            ((HttpRequestInterceptor)this.requestInterceptors.get(n)).process(httpRequest, httpContext);
            ++n;
        }
    }

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws IOException, HttpException {
        int n = 0;
        while (n < this.responseInterceptors.size()) {
            ((HttpResponseInterceptor)this.responseInterceptors.get(n)).process(httpResponse, httpContext);
            ++n;
        }
    }

    @Override
    public void removeRequestInterceptorByClass(Class class_) {
        Iterator iterator2 = this.requestInterceptors.iterator();
        while (iterator2.hasNext()) {
            if (!iterator2.next().getClass().equals((Object)class_)) continue;
            iterator2.remove();
        }
    }

    @Override
    public void removeResponseInterceptorByClass(Class class_) {
        Iterator iterator2 = this.responseInterceptors.iterator();
        while (iterator2.hasNext()) {
            if (!iterator2.next().getClass().equals((Object)class_)) continue;
            iterator2.remove();
        }
    }

    @Override
    public void setInterceptors(List list) {
        if (list == null) throw new IllegalArgumentException("List must not be null.");
        this.requestInterceptors.clear();
        this.responseInterceptors.clear();
        int n = 0;
        while (n < list.size()) {
            Object e = list.get(n);
            if (e instanceof HttpRequestInterceptor) {
                this.addInterceptor((HttpRequestInterceptor)e);
            }
            if (e instanceof HttpResponseInterceptor) {
                this.addInterceptor((HttpResponseInterceptor)e);
            }
            ++n;
        }
    }
}

