/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.client.methods.HttpRequestBase;

public class HttpHead
extends HttpRequestBase {
    public static final String METHOD_NAME = "HEAD";

    public HttpHead() {
    }

    public HttpHead(String string2) {
        this.setURI(URI.create(string2));
    }

    public HttpHead(URI uRI) {
        this.setURI(uRI);
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}

