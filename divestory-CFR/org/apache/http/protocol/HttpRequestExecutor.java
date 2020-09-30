/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import java.net.ProtocolException;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;

public class HttpRequestExecutor {
    private static final void closeConnection(HttpClientConnection httpClientConnection) {
        try {
            httpClientConnection.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    protected boolean canResponseHaveBody(HttpRequest httpRequest, HttpResponse httpResponse) {
        boolean bl = "HEAD".equalsIgnoreCase(httpRequest.getRequestLine().getMethod());
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        int n = httpResponse.getStatusLine().getStatusCode();
        bl = bl2;
        if (n < 200) return bl;
        bl = bl2;
        if (n == 204) return bl;
        bl = bl2;
        if (n == 304) return bl;
        bl = bl2;
        if (n == 205) return bl;
        return true;
    }

    protected HttpResponse doReceiveResponse(HttpRequest httpRequest, HttpClientConnection httpClientConnection, HttpContext object) throws HttpException, IOException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (httpClientConnection == null) throw new IllegalArgumentException("HTTP connection may not be null");
        if (object == null) throw new IllegalArgumentException("HTTP context may not be null");
        object = null;
        int n = 0;
        do {
            if (object != null) {
                if (n >= 200) return object;
            }
            if (this.canResponseHaveBody(httpRequest, (HttpResponse)(object = httpClientConnection.receiveResponseHeader()))) {
                httpClientConnection.receiveResponseEntity((HttpResponse)object);
            }
            n = object.getStatusLine().getStatusCode();
        } while (true);
    }

    protected HttpResponse doSendRequest(HttpRequest object, HttpClientConnection httpClientConnection, HttpContext httpContext) throws IOException, HttpException {
        if (object == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (httpClientConnection == null) throw new IllegalArgumentException("HTTP connection may not be null");
        if (httpContext == null) throw new IllegalArgumentException("HTTP context may not be null");
        httpContext.setAttribute("http.connection", httpClientConnection);
        httpContext.setAttribute("http.request_sent", Boolean.FALSE);
        httpClientConnection.sendRequestHeader((HttpRequest)object);
        boolean bl = object instanceof HttpEntityEnclosingRequest;
        ProtocolVersion protocolVersion = null;
        HttpResponse httpResponse = null;
        if (bl) {
            int n = 1;
            protocolVersion = object.getRequestLine().getProtocolVersion();
            HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest)object;
            int n2 = n;
            HttpResponse httpResponse2 = httpResponse;
            if (httpEntityEnclosingRequest.expectContinue()) {
                n2 = n;
                httpResponse2 = httpResponse;
                if (!protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                    httpClientConnection.flush();
                    n2 = n;
                    httpResponse2 = httpResponse;
                    if (httpClientConnection.isResponseAvailable(object.getParams().getIntParameter("http.protocol.wait-for-continue", 2000))) {
                        httpResponse2 = httpClientConnection.receiveResponseHeader();
                        if (this.canResponseHaveBody((HttpRequest)object, httpResponse2)) {
                            httpClientConnection.receiveResponseEntity(httpResponse2);
                        }
                        if ((n2 = httpResponse2.getStatusLine().getStatusCode()) < 200) {
                            if (n2 != 100) {
                                object = new StringBuffer();
                                ((StringBuffer)object).append("Unexpected response: ");
                                ((StringBuffer)object).append(httpResponse2.getStatusLine());
                                throw new ProtocolException(((StringBuffer)object).toString());
                            }
                            n2 = n;
                            httpResponse2 = httpResponse;
                        } else {
                            n2 = 0;
                        }
                    }
                }
            }
            protocolVersion = httpResponse2;
            if (n2 != 0) {
                httpClientConnection.sendRequestEntity(httpEntityEnclosingRequest);
                protocolVersion = httpResponse2;
            }
        }
        httpClientConnection.flush();
        httpContext.setAttribute("http.request_sent", Boolean.TRUE);
        return protocolVersion;
    }

    public HttpResponse execute(HttpRequest httpRequest, HttpClientConnection httpClientConnection, HttpContext httpContext) throws IOException, HttpException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (httpClientConnection == null) throw new IllegalArgumentException("Client connection may not be null");
        if (httpContext == null) throw new IllegalArgumentException("HTTP context may not be null");
        try {
            HttpResponse httpResponse;
            HttpResponse httpResponse2 = httpResponse = this.doSendRequest(httpRequest, httpClientConnection, httpContext);
            if (httpResponse != null) return httpResponse2;
            return this.doReceiveResponse(httpRequest, httpClientConnection, httpContext);
        }
        catch (RuntimeException runtimeException) {
            HttpRequestExecutor.closeConnection(httpClientConnection);
            throw runtimeException;
        }
        catch (HttpException httpException) {
            HttpRequestExecutor.closeConnection(httpClientConnection);
            throw httpException;
        }
        catch (IOException iOException) {
            HttpRequestExecutor.closeConnection(httpClientConnection);
            throw iOException;
        }
    }

    public void postProcess(HttpResponse httpResponse, HttpProcessor httpProcessor, HttpContext httpContext) throws HttpException, IOException {
        if (httpResponse == null) throw new IllegalArgumentException("HTTP response may not be null");
        if (httpProcessor == null) throw new IllegalArgumentException("HTTP processor may not be null");
        if (httpContext == null) throw new IllegalArgumentException("HTTP context may not be null");
        httpContext.setAttribute("http.response", httpResponse);
        httpProcessor.process(httpResponse, httpContext);
    }

    public void preProcess(HttpRequest httpRequest, HttpProcessor httpProcessor, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        if (httpProcessor == null) throw new IllegalArgumentException("HTTP processor may not be null");
        if (httpContext == null) throw new IllegalArgumentException("HTTP context may not be null");
        httpContext.setAttribute("http.request", httpRequest);
        httpProcessor.process(httpRequest, httpContext);
    }
}

