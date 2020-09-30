/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.batch;

import com.google.api.client.http.HttpHeaders;
import java.io.IOException;

public interface BatchCallback<T, E> {
    public void onFailure(E var1, HttpHeaders var2) throws IOException;

    public void onSuccess(T var1, HttpHeaders var2) throws IOException;
}

