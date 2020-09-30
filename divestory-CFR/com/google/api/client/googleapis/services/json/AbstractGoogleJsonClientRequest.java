/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.batch.BatchCallback;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonErrorContainer;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.GenericData;
import java.io.IOException;
import java.util.Set;

public abstract class AbstractGoogleJsonClientRequest<T>
extends AbstractGoogleClientRequest<T> {
    private final Object jsonContent;

    protected AbstractGoogleJsonClientRequest(AbstractGoogleJsonClient abstractGoogleJsonClient, String string2, String string3, Object object, Class<T> class_) {
        Object object2 = null;
        JsonHttpContent jsonHttpContent = null;
        if (object == null) {
            object2 = jsonHttpContent;
        } else {
            jsonHttpContent = new JsonHttpContent(abstractGoogleJsonClient.getJsonFactory(), object);
            if (!abstractGoogleJsonClient.getObjectParser().getWrapperKeys().isEmpty()) {
                object2 = "data";
            }
            object2 = jsonHttpContent.setWrapperKey((String)object2);
        }
        super(abstractGoogleJsonClient, string2, string3, (HttpContent)object2, class_);
        this.jsonContent = object;
    }

    @Override
    public AbstractGoogleJsonClient getAbstractGoogleClient() {
        return (AbstractGoogleJsonClient)super.getAbstractGoogleClient();
    }

    public Object getJsonContent() {
        return this.jsonContent;
    }

    @Override
    protected GoogleJsonResponseException newExceptionOnError(HttpResponse httpResponse) {
        return GoogleJsonResponseException.from(((AbstractGoogleJsonClient)this.getAbstractGoogleClient()).getJsonFactory(), httpResponse);
    }

    public final void queue(BatchRequest batchRequest, JsonBatchCallback<T> jsonBatchCallback) throws IOException {
        super.queue(batchRequest, GoogleJsonErrorContainer.class, jsonBatchCallback);
    }

    @Override
    public AbstractGoogleJsonClientRequest<T> set(String string2, Object object) {
        return (AbstractGoogleJsonClientRequest)super.set(string2, object);
    }

    @Override
    public AbstractGoogleJsonClientRequest<T> setDisableGZipContent(boolean bl) {
        return (AbstractGoogleJsonClientRequest)super.setDisableGZipContent(bl);
    }

    @Override
    public AbstractGoogleJsonClientRequest<T> setRequestHeaders(HttpHeaders httpHeaders) {
        return (AbstractGoogleJsonClientRequest)super.setRequestHeaders(httpHeaders);
    }
}

