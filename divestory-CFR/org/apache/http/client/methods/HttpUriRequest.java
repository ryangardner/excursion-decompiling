/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.HttpRequest;

public interface HttpUriRequest
extends HttpRequest {
    public void abort() throws UnsupportedOperationException;

    public String getMethod();

    public URI getURI();

    public boolean isAborted();
}

