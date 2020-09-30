/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.testing.services;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.GenericData;

public class MockGoogleClientRequest<T>
extends AbstractGoogleClientRequest<T> {
    public MockGoogleClientRequest(AbstractGoogleClient abstractGoogleClient, String string2, String string3, HttpContent httpContent, Class<T> class_) {
        super(abstractGoogleClient, string2, string3, httpContent, class_);
    }

    @Override
    public MockGoogleClientRequest<T> set(String string2, Object object) {
        return (MockGoogleClientRequest)super.set(string2, object);
    }

    @Override
    public MockGoogleClientRequest<T> setDisableGZipContent(boolean bl) {
        return (MockGoogleClientRequest)super.setDisableGZipContent(bl);
    }

    @Override
    public MockGoogleClientRequest<T> setRequestHeaders(HttpHeaders httpHeaders) {
        return (MockGoogleClientRequest)super.setRequestHeaders(httpHeaders);
    }
}

