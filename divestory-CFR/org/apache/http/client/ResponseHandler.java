/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

public interface ResponseHandler<T> {
    public T handleResponse(HttpResponse var1) throws ClientProtocolException, IOException;
}

