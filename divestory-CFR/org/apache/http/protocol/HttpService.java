/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpServerConnection;
import org.apache.http.HttpVersion;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.UnsupportedHttpVersionException;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpExpectationVerifier;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpRequestHandlerResolver;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

public class HttpService {
    private volatile ConnectionReuseStrategy connStrategy = null;
    private volatile HttpExpectationVerifier expectationVerifier = null;
    private volatile HttpRequestHandlerResolver handlerResolver = null;
    private volatile HttpParams params = null;
    private volatile HttpProcessor processor = null;
    private volatile HttpResponseFactory responseFactory = null;

    public HttpService(HttpProcessor httpProcessor, ConnectionReuseStrategy connectionReuseStrategy, HttpResponseFactory httpResponseFactory) {
        this.setHttpProcessor(httpProcessor);
        this.setConnReuseStrategy(connectionReuseStrategy);
        this.setResponseFactory(httpResponseFactory);
    }

    public HttpService(HttpProcessor httpProcessor, ConnectionReuseStrategy connectionReuseStrategy, HttpResponseFactory httpResponseFactory, HttpRequestHandlerResolver httpRequestHandlerResolver, HttpParams httpParams) {
        this(httpProcessor, connectionReuseStrategy, httpResponseFactory, httpRequestHandlerResolver, null, httpParams);
    }

    public HttpService(HttpProcessor httpProcessor, ConnectionReuseStrategy connectionReuseStrategy, HttpResponseFactory httpResponseFactory, HttpRequestHandlerResolver httpRequestHandlerResolver, HttpExpectationVerifier httpExpectationVerifier, HttpParams httpParams) {
        if (httpProcessor == null) throw new IllegalArgumentException("HTTP processor may not be null");
        if (connectionReuseStrategy == null) throw new IllegalArgumentException("Connection reuse strategy may not be null");
        if (httpResponseFactory == null) throw new IllegalArgumentException("Response factory may not be null");
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        this.processor = httpProcessor;
        this.connStrategy = connectionReuseStrategy;
        this.responseFactory = httpResponseFactory;
        this.handlerResolver = httpRequestHandlerResolver;
        this.expectationVerifier = httpExpectationVerifier;
        this.params = httpParams;
    }

    protected void doService(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Object object;
        if (this.handlerResolver != null) {
            object = httpRequest.getRequestLine().getUri();
            object = this.handlerResolver.lookup((String)object);
        } else {
            object = null;
        }
        if (object != null) {
            object.handle(httpRequest, httpResponse, httpContext);
            return;
        }
        httpResponse.setStatusCode(501);
    }

    public HttpParams getParams() {
        return this.params;
    }

    protected void handleException(HttpException object, HttpResponse httpResponse) {
        if (object instanceof MethodNotSupportedException) {
            httpResponse.setStatusCode(501);
        } else if (object instanceof UnsupportedHttpVersionException) {
            httpResponse.setStatusCode(505);
        } else if (object instanceof ProtocolException) {
            httpResponse.setStatusCode(400);
        } else {
            httpResponse.setStatusCode(500);
        }
        object = new ByteArrayEntity(EncodingUtils.getAsciiBytes(((Throwable)object).getMessage()));
        ((AbstractHttpEntity)object).setContentType("text/plain; charset=US-ASCII");
        httpResponse.setEntity((HttpEntity)object);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public void handleRequest(HttpServerConnection var1_1, HttpContext var2_2) throws IOException, HttpException {
        var2_2.setAttribute("http.connection", var1_1);
        var3_3 = var1_1.receiveRequestHeader();
        var4_4 = new DefaultedHttpParams(var3_3.getParams(), this.params);
        var3_3.setParams((HttpParams)var4_4);
        var5_6 = var4_4 = var3_3.getRequestLine().getProtocolVersion();
        if (!var4_4.lessEquals(HttpVersion.HTTP_1_1)) {
            var5_6 = HttpVersion.HTTP_1_1;
        }
        var6_7 = var3_3 instanceof HttpEntityEnclosingRequest;
        var7_8 = null;
        var8_9 = var7_8;
        if (!var6_7) ** GOTO lbl40
        if (!((HttpEntityEnclosingRequest)var3_3).expectContinue()) ** GOTO lbl38
        var8_9 = this.responseFactory.newHttpResponse((ProtocolVersion)var5_6, 100, var2_2);
        var4_4 = new DefaultedHttpParams(var8_9.getParams(), this.params);
        var8_9.setParams((HttpParams)var4_4);
        var9_10 = this.expectationVerifier;
        var4_4 = var8_9;
        if (var9_10 == null) ** GOTO lbl30
        try {
            this.expectationVerifier.verify(var3_3, (HttpResponse)var8_9, var2_2);
            var4_4 = var8_9;
            ** GOTO lbl30
        }
        catch (HttpException var9_11) {
            try {
                block10 : {
                    var4_4 = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, var2_2);
                    var8_9 = new DefaultedHttpParams(var4_4.getParams(), this.params);
                    var4_4.setParams((HttpParams)var8_9);
                    this.handleException(var9_11, (HttpResponse)var4_4);
lbl30: // 3 sources:
                    if (var4_4.getStatusLine().getStatusCode() < 200) {
                        var1_1.sendResponseHeader((HttpResponse)var4_4);
                        var1_1.flush();
                        var1_1.receiveRequestEntity((HttpEntityEnclosingRequest)var3_3);
                        var8_9 = var7_8;
                    } else {
                        var8_9 = var4_4;
                    }
                    break block10;
lbl38: // 1 sources:
                    var1_1.receiveRequestEntity((HttpEntityEnclosingRequest)var3_3);
                    var8_9 = var7_8;
                }
                var4_4 = var8_9;
                if (var8_9 == null) {
                    var4_4 = this.responseFactory.newHttpResponse((ProtocolVersion)var5_6, 200, var2_2);
                    var8_9 = new DefaultedHttpParams(var4_4.getParams(), this.params);
                    var4_4.setParams((HttpParams)var8_9);
                    var2_2.setAttribute("http.request", var3_3);
                    var2_2.setAttribute("http.response", var4_4);
                    this.processor.process(var3_3, var2_2);
                    this.doService(var3_3, (HttpResponse)var4_4, var2_2);
                }
                var8_9 = var4_4;
                if (var3_3 instanceof HttpEntityEnclosingRequest) {
                    EntityUtils.consume(((HttpEntityEnclosingRequest)var3_3).getEntity());
                    var8_9 = var4_4;
                }
            }
            catch (HttpException var4_5) {
                var8_9 = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, var2_2);
                var8_9.setParams(new DefaultedHttpParams(var8_9.getParams(), this.params));
                this.handleException(var4_5, (HttpResponse)var8_9);
            }
        }
        this.processor.process((HttpResponse)var8_9, var2_2);
        var1_1.sendResponseHeader((HttpResponse)var8_9);
        var1_1.sendResponseEntity((HttpResponse)var8_9);
        var1_1.flush();
        if (this.connStrategy.keepAlive((HttpResponse)var8_9, var2_2) != false) return;
        var1_1.close();
    }

    public void setConnReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        if (connectionReuseStrategy == null) throw new IllegalArgumentException("Connection reuse strategy may not be null");
        this.connStrategy = connectionReuseStrategy;
    }

    public void setExpectationVerifier(HttpExpectationVerifier httpExpectationVerifier) {
        this.expectationVerifier = httpExpectationVerifier;
    }

    public void setHandlerResolver(HttpRequestHandlerResolver httpRequestHandlerResolver) {
        this.handlerResolver = httpRequestHandlerResolver;
    }

    public void setHttpProcessor(HttpProcessor httpProcessor) {
        if (httpProcessor == null) throw new IllegalArgumentException("HTTP processor may not be null");
        this.processor = httpProcessor;
    }

    public void setParams(HttpParams httpParams) {
        this.params = httpParams;
    }

    public void setResponseFactory(HttpResponseFactory httpResponseFactory) {
        if (httpResponseFactory == null) throw new IllegalArgumentException("Response factory may not be null");
        this.responseFactory = httpResponseFactory;
    }
}

