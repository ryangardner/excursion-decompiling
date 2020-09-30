/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.util.List;
import org.apache.http.HttpResponseInterceptor;

public interface HttpResponseInterceptorList {
    public void addResponseInterceptor(HttpResponseInterceptor var1);

    public void addResponseInterceptor(HttpResponseInterceptor var1, int var2);

    public void clearResponseInterceptors();

    public HttpResponseInterceptor getResponseInterceptor(int var1);

    public int getResponseInterceptorCount();

    public void removeResponseInterceptorByClass(Class var1);

    public void setInterceptors(List var1);
}

