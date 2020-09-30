/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.testing.services.json;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.googleapis.testing.services.json.MockGoogleJsonClient;
import com.google.api.client.http.HttpHeaders;

public class MockGoogleJsonClientRequest<T>
extends AbstractGoogleJsonClientRequest<T> {
    public MockGoogleJsonClientRequest(AbstractGoogleJsonClient abstractGoogleJsonClient, String string2, String string3, Object object, Class<T> class_) {
        super(abstractGoogleJsonClient, string2, string3, object, class_);
    }

    @Override
    public MockGoogleJsonClient getAbstractGoogleClient() {
        return (MockGoogleJsonClient)super.getAbstractGoogleClient();
    }

    @Override
    public MockGoogleJsonClientRequest<T> setDisableGZipContent(boolean bl) {
        return (MockGoogleJsonClientRequest)super.setDisableGZipContent(bl);
    }

    @Override
    public MockGoogleJsonClientRequest<T> setRequestHeaders(HttpHeaders httpHeaders) {
        return (MockGoogleJsonClientRequest)super.setRequestHeaders(httpHeaders);
    }
}

