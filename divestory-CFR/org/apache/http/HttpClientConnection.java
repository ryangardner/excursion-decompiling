/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import java.io.IOException;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

public interface HttpClientConnection
extends HttpConnection {
    public void flush() throws IOException;

    public boolean isResponseAvailable(int var1) throws IOException;

    public void receiveResponseEntity(HttpResponse var1) throws HttpException, IOException;

    public HttpResponse receiveResponseHeader() throws HttpException, IOException;

    public void sendRequestEntity(HttpEntityEnclosingRequest var1) throws HttpException, IOException;

    public void sendRequestHeader(HttpRequest var1) throws HttpException, IOException;
}

