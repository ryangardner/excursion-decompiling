/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpPost
extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "POST";

    public HttpPost() {
    }

    public HttpPost(String string2) {
        this.setURI(URI.create(string2));
    }

    public HttpPost(URI uRI) {
        this.setURI(uRI);
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}

