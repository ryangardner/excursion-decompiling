/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.client.methods.HttpRequestBase;

public class HttpDelete
extends HttpRequestBase {
    public static final String METHOD_NAME = "DELETE";

    public HttpDelete() {
    }

    public HttpDelete(String string2) {
        this.setURI(URI.create(string2));
    }

    public HttpDelete(URI uRI) {
        this.setURI(uRI);
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}

