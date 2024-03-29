/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl;

import java.io.IOException;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.StatusLine;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.HttpConnectionMetricsImpl;
import org.apache.http.impl.entity.EntityDeserializer;
import org.apache.http.impl.entity.EntitySerializer;
import org.apache.http.impl.entity.LaxContentLengthStrategy;
import org.apache.http.impl.entity.StrictContentLengthStrategy;
import org.apache.http.impl.io.HttpRequestWriter;
import org.apache.http.impl.io.HttpResponseParser;
import org.apache.http.io.EofSensor;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageWriter;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.LineFormatter;
import org.apache.http.message.LineParser;
import org.apache.http.params.HttpParams;

public abstract class AbstractHttpClientConnection
implements HttpClientConnection {
    private final EntityDeserializer entitydeserializer = this.createEntityDeserializer();
    private final EntitySerializer entityserializer = this.createEntitySerializer();
    private EofSensor eofSensor = null;
    private SessionInputBuffer inbuffer = null;
    private HttpConnectionMetricsImpl metrics = null;
    private SessionOutputBuffer outbuffer = null;
    private HttpMessageWriter requestWriter = null;
    private HttpMessageParser responseParser = null;

    protected abstract void assertOpen() throws IllegalStateException;

    protected HttpConnectionMetricsImpl createConnectionMetrics(HttpTransportMetrics httpTransportMetrics, HttpTransportMetrics httpTransportMetrics2) {
        return new HttpConnectionMetricsImpl(httpTransportMetrics, httpTransportMetrics2);
    }

    protected EntityDeserializer createEntityDeserializer() {
        return new EntityDeserializer(new LaxContentLengthStrategy());
    }

    protected EntitySerializer createEntitySerializer() {
        return new EntitySerializer(new StrictContentLengthStrategy());
    }

    protected HttpResponseFactory createHttpResponseFactory() {
        return new DefaultHttpResponseFactory();
    }

    protected HttpMessageWriter createRequestWriter(SessionOutputBuffer sessionOutputBuffer, HttpParams httpParams) {
        return new HttpRequestWriter(sessionOutputBuffer, null, httpParams);
    }

    protected HttpMessageParser createResponseParser(SessionInputBuffer sessionInputBuffer, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        return new HttpResponseParser(sessionInputBuffer, null, httpResponseFactory, httpParams);
    }

    protected void doFlush() throws IOException {
        this.outbuffer.flush();
    }

    @Override
    public void flush() throws IOException {
        this.assertOpen();
        this.doFlush();
    }

    @Override
    public HttpConnectionMetrics getMetrics() {
        return this.metrics;
    }

    protected void init(SessionInputBuffer sessionInputBuffer, SessionOutputBuffer sessionOutputBuffer, HttpParams httpParams) {
        if (sessionInputBuffer == null) throw new IllegalArgumentException("Input session buffer may not be null");
        if (sessionOutputBuffer == null) throw new IllegalArgumentException("Output session buffer may not be null");
        this.inbuffer = sessionInputBuffer;
        this.outbuffer = sessionOutputBuffer;
        if (sessionInputBuffer instanceof EofSensor) {
            this.eofSensor = (EofSensor)((Object)sessionInputBuffer);
        }
        this.responseParser = this.createResponseParser(sessionInputBuffer, this.createHttpResponseFactory(), httpParams);
        this.requestWriter = this.createRequestWriter(sessionOutputBuffer, httpParams);
        this.metrics = this.createConnectionMetrics(sessionInputBuffer.getMetrics(), sessionOutputBuffer.getMetrics());
    }

    protected boolean isEof() {
        EofSensor eofSensor = this.eofSensor;
        if (eofSensor == null) return false;
        if (!eofSensor.isEof()) return false;
        return true;
    }

    @Override
    public boolean isResponseAvailable(int n) throws IOException {
        this.assertOpen();
        return this.inbuffer.isDataAvailable(n);
    }

    @Override
    public boolean isStale() {
        if (!this.isOpen()) {
            return true;
        }
        if (this.isEof()) {
            return true;
        }
        try {
            this.inbuffer.isDataAvailable(1);
            return this.isEof();
        }
        catch (IOException iOException) {
            return true;
        }
    }

    @Override
    public void receiveResponseEntity(HttpResponse httpResponse) throws HttpException, IOException {
        if (httpResponse == null) throw new IllegalArgumentException("HTTP response may not be null");
        this.assertOpen();
        httpResponse.setEntity(this.entitydeserializer.deserialize(this.inbuffer, httpResponse));
    }

    @Override
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        this.assertOpen();
        HttpResponse httpResponse = (HttpResponse)this.responseParser.parse();
        if (httpResponse.getStatusLine().getStatusCode() < 200) return httpResponse;
        this.metrics.incrementResponseCount();
        return httpResponse;
    }

    @Override
    public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        if (httpEntityEnclosingRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        this.assertOpen();
        if (httpEntityEnclosingRequest.getEntity() == null) {
            return;
        }
        this.entityserializer.serialize(this.outbuffer, httpEntityEnclosingRequest, httpEntityEnclosingRequest.getEntity());
    }

    @Override
    public void sendRequestHeader(HttpRequest httpRequest) throws HttpException, IOException {
        if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
        this.assertOpen();
        this.requestWriter.write(httpRequest);
        this.metrics.incrementRequestCount();
    }
}

