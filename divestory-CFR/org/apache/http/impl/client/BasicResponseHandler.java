/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.client;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class BasicResponseHandler
implements ResponseHandler<String> {
    @Override
    public String handleResponse(HttpResponse object) throws HttpResponseException, IOException {
        StatusLine statusLine = object.getStatusLine();
        if (statusLine.getStatusCode() >= 300) throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        if ((object = object.getEntity()) != null) return EntityUtils.toString((HttpEntity)object);
        return null;
    }
}

