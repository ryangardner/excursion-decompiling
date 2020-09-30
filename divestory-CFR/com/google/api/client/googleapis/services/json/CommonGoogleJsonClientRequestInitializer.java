/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.CommonGoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import java.io.IOException;

public class CommonGoogleJsonClientRequestInitializer
extends CommonGoogleClientRequestInitializer {
    @Deprecated
    public CommonGoogleJsonClientRequestInitializer() {
    }

    @Deprecated
    public CommonGoogleJsonClientRequestInitializer(String string2) {
        super(string2);
    }

    @Deprecated
    public CommonGoogleJsonClientRequestInitializer(String string2, String string3) {
        super(string2, string3);
    }

    @Override
    public final void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
        super.initialize(abstractGoogleClientRequest);
        this.initializeJsonRequest((AbstractGoogleJsonClientRequest)abstractGoogleClientRequest);
    }

    protected void initializeJsonRequest(AbstractGoogleJsonClientRequest<?> abstractGoogleJsonClientRequest) throws IOException {
    }

    public static class Builder
    extends CommonGoogleClientRequestInitializer.Builder {
        @Override
        protected Builder self() {
            return this;
        }
    }

}

