/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestInterceptorList;
import org.apache.http.protocol.HttpResponseInterceptorList;

public final class ImmutableHttpProcessor
implements HttpProcessor {
    private final HttpRequestInterceptor[] requestInterceptors;
    private final HttpResponseInterceptor[] responseInterceptors;

    public ImmutableHttpProcessor(HttpRequestInterceptorList httpRequestInterceptorList, HttpResponseInterceptorList httpResponseInterceptorList) {
        int n;
        int n2;
        int n3 = 0;
        if (httpRequestInterceptorList != null) {
            n = httpRequestInterceptorList.getRequestInterceptorCount();
            this.requestInterceptors = new HttpRequestInterceptor[n];
            for (n2 = 0; n2 < n; ++n2) {
                this.requestInterceptors[n2] = httpRequestInterceptorList.getRequestInterceptor(n2);
            }
        } else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (httpResponseInterceptorList == null) {
            this.responseInterceptors = new HttpResponseInterceptor[0];
            return;
        }
        n = httpResponseInterceptorList.getResponseInterceptorCount();
        this.responseInterceptors = new HttpResponseInterceptor[n];
        n2 = n3;
        while (n2 < n) {
            this.responseInterceptors[n2] = httpResponseInterceptorList.getResponseInterceptor(n2);
            ++n2;
        }
    }

    public ImmutableHttpProcessor(HttpRequestInterceptor[] arrhttpRequestInterceptor) {
        this(arrhttpRequestInterceptor, null);
    }

    public ImmutableHttpProcessor(HttpRequestInterceptor[] arrhttpRequestInterceptor, HttpResponseInterceptor[] arrhttpResponseInterceptor) {
        int n;
        int n2;
        int n3 = 0;
        if (arrhttpRequestInterceptor != null) {
            n = arrhttpRequestInterceptor.length;
            this.requestInterceptors = new HttpRequestInterceptor[n];
            for (n2 = 0; n2 < n; ++n2) {
                this.requestInterceptors[n2] = arrhttpRequestInterceptor[n2];
            }
        } else {
            this.requestInterceptors = new HttpRequestInterceptor[0];
        }
        if (arrhttpResponseInterceptor == null) {
            this.responseInterceptors = new HttpResponseInterceptor[0];
            return;
        }
        n = arrhttpResponseInterceptor.length;
        this.responseInterceptors = new HttpResponseInterceptor[n];
        n2 = n3;
        while (n2 < n) {
            this.responseInterceptors[n2] = arrhttpResponseInterceptor[n2];
            ++n2;
        }
    }

    public ImmutableHttpProcessor(HttpResponseInterceptor[] arrhttpResponseInterceptor) {
        this(null, arrhttpResponseInterceptor);
    }

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws IOException, HttpException {
        HttpRequestInterceptor[] arrhttpRequestInterceptor;
        int n = 0;
        while (n < (arrhttpRequestInterceptor = this.requestInterceptors).length) {
            arrhttpRequestInterceptor[n].process(httpRequest, httpContext);
            ++n;
        }
    }

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws IOException, HttpException {
        HttpResponseInterceptor[] arrhttpResponseInterceptor;
        int n = 0;
        while (n < (arrhttpResponseInterceptor = this.responseInterceptors).length) {
            arrhttpResponseInterceptor[n].process(httpResponse, httpContext);
            ++n;
        }
    }
}

