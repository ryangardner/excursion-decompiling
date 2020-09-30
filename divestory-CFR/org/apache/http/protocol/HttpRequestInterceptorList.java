/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.util.List;
import org.apache.http.HttpRequestInterceptor;

public interface HttpRequestInterceptorList {
    public void addRequestInterceptor(HttpRequestInterceptor var1);

    public void addRequestInterceptor(HttpRequestInterceptor var1, int var2);

    public void clearRequestInterceptors();

    public HttpRequestInterceptor getRequestInterceptor(int var1);

    public int getRequestInterceptorCount();

    public void removeRequestInterceptorByClass(Class var1);

    public void setInterceptors(List var1);
}

