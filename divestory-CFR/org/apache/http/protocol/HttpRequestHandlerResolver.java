/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import org.apache.http.protocol.HttpRequestHandler;

public interface HttpRequestHandlerResolver {
    public HttpRequestHandler lookup(String var1);
}

