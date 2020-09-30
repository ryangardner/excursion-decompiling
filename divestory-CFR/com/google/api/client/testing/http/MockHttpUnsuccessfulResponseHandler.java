/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.http;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import java.io.IOException;

public class MockHttpUnsuccessfulResponseHandler
implements HttpUnsuccessfulResponseHandler {
    private boolean isCalled;
    private boolean successfullyHandleResponse;

    public MockHttpUnsuccessfulResponseHandler(boolean bl) {
        this.successfullyHandleResponse = bl;
    }

    @Override
    public boolean handleResponse(HttpRequest httpRequest, HttpResponse httpResponse, boolean bl) throws IOException {
        this.isCalled = true;
        return this.successfullyHandleResponse;
    }

    public boolean isCalled() {
        return this.isCalled;
    }
}

