/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.batch.json;

import com.google.api.client.googleapis.batch.BatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonErrorContainer;
import com.google.api.client.http.HttpHeaders;
import java.io.IOException;

public abstract class JsonBatchCallback<T>
implements BatchCallback<T, GoogleJsonErrorContainer> {
    @Override
    public abstract void onFailure(GoogleJsonError var1, HttpHeaders var2) throws IOException;

    @Override
    public final void onFailure(GoogleJsonErrorContainer googleJsonErrorContainer, HttpHeaders httpHeaders) throws IOException {
        this.onFailure(googleJsonErrorContainer.getError(), httpHeaders);
    }
}

