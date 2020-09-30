/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http.apache;

import com.google.api.client.util.Preconditions;
import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

final class HttpExtensionMethod
extends HttpEntityEnclosingRequestBase {
    private final String methodName;

    public HttpExtensionMethod(String string2, String string3) {
        this.methodName = Preconditions.checkNotNull(string2);
        this.setURI(URI.create(string3));
    }

    @Override
    public String getMethod() {
        return this.methodName;
    }
}

