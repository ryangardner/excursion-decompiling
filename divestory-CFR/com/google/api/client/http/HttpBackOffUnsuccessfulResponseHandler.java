/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.util.BackOff;
import com.google.api.client.util.BackOffUtils;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sleeper;
import java.io.IOException;

public class HttpBackOffUnsuccessfulResponseHandler
implements HttpUnsuccessfulResponseHandler {
    private final BackOff backOff;
    private BackOffRequired backOffRequired = BackOffRequired.ON_SERVER_ERROR;
    private Sleeper sleeper = Sleeper.DEFAULT;

    public HttpBackOffUnsuccessfulResponseHandler(BackOff backOff) {
        this.backOff = Preconditions.checkNotNull(backOff);
    }

    public final BackOff getBackOff() {
        return this.backOff;
    }

    public final BackOffRequired getBackOffRequired() {
        return this.backOffRequired;
    }

    public final Sleeper getSleeper() {
        return this.sleeper;
    }

    @Override
    public boolean handleResponse(HttpRequest httpRequest, HttpResponse httpResponse, boolean bl) throws IOException {
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        bl = bl2;
        if (!this.backOffRequired.isRequired(httpResponse)) return bl;
        try {
            return BackOffUtils.next(this.sleeper, this.backOff);
        }
        catch (InterruptedException interruptedException) {
            return bl2;
        }
    }

    public HttpBackOffUnsuccessfulResponseHandler setBackOffRequired(BackOffRequired backOffRequired) {
        this.backOffRequired = Preconditions.checkNotNull(backOffRequired);
        return this;
    }

    public HttpBackOffUnsuccessfulResponseHandler setSleeper(Sleeper sleeper) {
        this.sleeper = Preconditions.checkNotNull(sleeper);
        return this;
    }

    public static interface BackOffRequired {
        public static final BackOffRequired ALWAYS = new BackOffRequired(){

            @Override
            public boolean isRequired(HttpResponse httpResponse) {
                return true;
            }
        };
        public static final BackOffRequired ON_SERVER_ERROR = new BackOffRequired(){

            @Override
            public boolean isRequired(HttpResponse httpResponse) {
                if (httpResponse.getStatusCode() / 100 != 5) return false;
                return true;
            }
        };

        public boolean isRequired(HttpResponse var1);

    }

}

