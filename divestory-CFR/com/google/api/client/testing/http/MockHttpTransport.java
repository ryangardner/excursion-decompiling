/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.testing.http;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class MockHttpTransport
extends HttpTransport {
    private MockLowLevelHttpRequest lowLevelHttpRequest;
    private MockLowLevelHttpResponse lowLevelHttpResponse;
    private Set<String> supportedMethods;

    public MockHttpTransport() {
    }

    protected MockHttpTransport(Builder builder) {
        this.supportedMethods = builder.supportedMethods;
        this.lowLevelHttpRequest = builder.lowLevelHttpRequest;
        this.lowLevelHttpResponse = builder.lowLevelHttpResponse;
    }

    @Override
    public LowLevelHttpRequest buildRequest(String object, String object2) throws IOException {
        Preconditions.checkArgument(this.supportsMethod((String)object), "HTTP method %s not supported", object);
        object = this.lowLevelHttpRequest;
        if (object != null) {
            return object;
        }
        this.lowLevelHttpRequest = object2 = new MockLowLevelHttpRequest((String)object2);
        object = this.lowLevelHttpResponse;
        if (object == null) return this.lowLevelHttpRequest;
        ((MockLowLevelHttpRequest)object2).setResponse((MockLowLevelHttpResponse)object);
        return this.lowLevelHttpRequest;
    }

    public final MockLowLevelHttpRequest getLowLevelHttpRequest() {
        return this.lowLevelHttpRequest;
    }

    public final Set<String> getSupportedMethods() {
        Set<String> set = this.supportedMethods;
        if (set != null) return Collections.unmodifiableSet(set);
        return null;
    }

    @Override
    public boolean supportsMethod(String string2) throws IOException {
        Set<String> set = this.supportedMethods;
        if (set == null) return true;
        if (set.contains(string2)) return true;
        return false;
    }

    public static class Builder {
        MockLowLevelHttpRequest lowLevelHttpRequest;
        MockLowLevelHttpResponse lowLevelHttpResponse;
        Set<String> supportedMethods;

        public MockHttpTransport build() {
            return new MockHttpTransport(this);
        }

        public final MockLowLevelHttpRequest getLowLevelHttpRequest() {
            return this.lowLevelHttpRequest;
        }

        MockLowLevelHttpResponse getLowLevelHttpResponse() {
            return this.lowLevelHttpResponse;
        }

        public final Set<String> getSupportedMethods() {
            return this.supportedMethods;
        }

        public final Builder setLowLevelHttpRequest(MockLowLevelHttpRequest mockLowLevelHttpRequest) {
            boolean bl = this.lowLevelHttpResponse == null;
            Preconditions.checkState(bl, "Cannnot set a low level HTTP request when a low level HTTP response has been set.");
            this.lowLevelHttpRequest = mockLowLevelHttpRequest;
            return this;
        }

        public final Builder setLowLevelHttpResponse(MockLowLevelHttpResponse mockLowLevelHttpResponse) {
            boolean bl = this.lowLevelHttpRequest == null;
            Preconditions.checkState(bl, "Cannot set a low level HTTP response when a low level HTTP request has been set.");
            this.lowLevelHttpResponse = mockLowLevelHttpResponse;
            return this;
        }

        public final Builder setSupportedMethods(Set<String> set) {
            this.supportedMethods = set;
            return this;
        }
    }

}

