/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpRequest;
import java.io.IOException;

public interface HttpIOExceptionHandler {
    public boolean handleIOException(HttpRequest var1, boolean var2) throws IOException;
}

