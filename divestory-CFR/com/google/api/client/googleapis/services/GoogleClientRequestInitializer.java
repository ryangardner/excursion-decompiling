/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.services;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import java.io.IOException;

public interface GoogleClientRequestInitializer {
    public void initialize(AbstractGoogleClientRequest<?> var1) throws IOException;
}

