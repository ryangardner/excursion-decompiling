/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpRequest;
import java.io.IOException;

public interface HttpRequestInitializer {
    public void initialize(HttpRequest var1) throws IOException;
}

