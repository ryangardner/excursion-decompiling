/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.testing.json;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import java.io.IOException;

public final class GoogleJsonResponseExceptionFactoryTesting {
    public static GoogleJsonResponseException newMock(JsonFactory jsonFactory, int n, String object) throws IOException {
        MockLowLevelHttpResponse mockLowLevelHttpResponse = new MockLowLevelHttpResponse().setStatusCode(n).setReasonPhrase((String)object).setContentType("application/json; charset=UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ \"error\": { \"errors\": [ { \"reason\": \"");
        stringBuilder.append((String)object);
        stringBuilder.append("\" } ], \"code\": ");
        stringBuilder.append(n);
        stringBuilder.append(" } }");
        object = mockLowLevelHttpResponse.setContent(stringBuilder.toString());
        object = new MockHttpTransport.Builder().setLowLevelHttpResponse((MockLowLevelHttpResponse)object).build().createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
        ((HttpRequest)object).setThrowExceptionOnExecuteError(false);
        return GoogleJsonResponseException.from(jsonFactory, ((HttpRequest)object).execute());
    }
}

