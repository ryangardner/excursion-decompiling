/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.protocol.HttpContext;

@Deprecated
public interface RedirectHandler {
    public URI getLocationURI(HttpResponse var1, HttpContext var2) throws ProtocolException;

    public boolean isRedirectRequested(HttpResponse var1, HttpContext var2);
}

