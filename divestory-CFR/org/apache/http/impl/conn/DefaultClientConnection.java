/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.impl.SocketHttpClientConnection;
import org.apache.http.impl.conn.DefaultResponseParser;
import org.apache.http.impl.conn.LoggingSessionInputBuffer;
import org.apache.http.impl.conn.LoggingSessionOutputBuffer;
import org.apache.http.impl.conn.Wire;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.LineParser;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

public class DefaultClientConnection
extends SocketHttpClientConnection
implements OperatedClientConnection,
HttpContext {
    private final Map<String, Object> attributes = new HashMap<String, Object>();
    private boolean connSecure;
    private final Log headerLog = LogFactory.getLog((String)"org.apache.http.headers");
    private final Log log = LogFactory.getLog(this.getClass());
    private volatile boolean shutdown;
    private volatile Socket socket;
    private HttpHost targetHost;
    private final Log wireLog = LogFactory.getLog((String)"org.apache.http.wire");

    @Override
    public void close() throws IOException {
        try {
            super.close();
            this.log.debug((Object)"Connection closed");
            return;
        }
        catch (IOException iOException) {
            this.log.debug((Object)"I/O error closing connection", (Throwable)iOException);
        }
    }

    @Override
    protected HttpMessageParser createResponseParser(SessionInputBuffer sessionInputBuffer, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        return new DefaultResponseParser(sessionInputBuffer, null, httpResponseFactory, httpParams);
    }

    @Override
    protected SessionInputBuffer createSessionInputBuffer(Socket object, int n, HttpParams httpParams) throws IOException {
        int n2 = n;
        if (n == -1) {
            n2 = 8192;
        }
        SessionInputBuffer sessionInputBuffer = super.createSessionInputBuffer((Socket)object, n2, httpParams);
        object = sessionInputBuffer;
        if (!this.wireLog.isDebugEnabled()) return object;
        return new LoggingSessionInputBuffer(sessionInputBuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(httpParams));
    }

    @Override
    protected SessionOutputBuffer createSessionOutputBuffer(Socket object, int n, HttpParams httpParams) throws IOException {
        int n2 = n;
        if (n == -1) {
            n2 = 8192;
        }
        SessionOutputBuffer sessionOutputBuffer = super.createSessionOutputBuffer((Socket)object, n2, httpParams);
        object = sessionOutputBuffer;
        if (!this.wireLog.isDebugEnabled()) return object;
        return new LoggingSessionOutputBuffer(sessionOutputBuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(httpParams));
    }

    @Override
    public Object getAttribute(String string2) {
        return this.attributes.get(string2);
    }

    @Override
    public final Socket getSocket() {
        return this.socket;
    }

    @Override
    public final HttpHost getTargetHost() {
        return this.targetHost;
    }

    @Override
    public final boolean isSecure() {
        return this.connSecure;
    }

    @Override
    public void openCompleted(boolean bl, HttpParams httpParams) throws IOException {
        this.assertNotOpen();
        if (httpParams == null) throw new IllegalArgumentException("Parameters must not be null.");
        this.connSecure = bl;
        this.bind(this.socket, httpParams);
    }

    @Override
    public void opening(Socket socket, HttpHost httpHost) throws IOException {
        this.assertNotOpen();
        this.socket = socket;
        this.targetHost = httpHost;
        if (!this.shutdown) {
            return;
        }
        socket.close();
        throw new IOException("Connection already shutdown");
    }

    @Override
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        Object object;
        StringBuilder stringBuilder;
        HttpResponse httpResponse = super.receiveResponseHeader();
        if (this.log.isDebugEnabled()) {
            object = this.log;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Receiving response: ");
            stringBuilder.append(httpResponse.getStatusLine());
            object.debug((Object)stringBuilder.toString());
        }
        if (!this.headerLog.isDebugEnabled()) return httpResponse;
        stringBuilder = this.headerLog;
        object = new StringBuilder();
        ((StringBuilder)object).append("<< ");
        ((StringBuilder)object).append(httpResponse.getStatusLine().toString());
        stringBuilder.debug((Object)((StringBuilder)object).toString());
        Header[] arrheader = httpResponse.getAllHeaders();
        int n = arrheader.length;
        int n2 = 0;
        while (n2 < n) {
            object = arrheader[n2];
            Log log = this.headerLog;
            stringBuilder = new StringBuilder();
            stringBuilder.append("<< ");
            stringBuilder.append(object.toString());
            log.debug((Object)stringBuilder.toString());
            ++n2;
        }
        return httpResponse;
    }

    @Override
    public Object removeAttribute(String string2) {
        return this.attributes.remove(string2);
    }

    @Override
    public void sendRequestHeader(HttpRequest arrheader) throws HttpException, IOException {
        Log log;
        Object object;
        if (this.log.isDebugEnabled()) {
            log = this.log;
            object = new StringBuilder();
            ((StringBuilder)object).append("Sending request: ");
            ((StringBuilder)object).append(arrheader.getRequestLine());
            log.debug((Object)((StringBuilder)object).toString());
        }
        super.sendRequestHeader((HttpRequest)arrheader);
        if (!this.headerLog.isDebugEnabled()) return;
        log = this.headerLog;
        object = new StringBuilder();
        ((StringBuilder)object).append(">> ");
        ((StringBuilder)object).append(arrheader.getRequestLine().toString());
        log.debug((Object)((StringBuilder)object).toString());
        arrheader = arrheader.getAllHeaders();
        int n = arrheader.length;
        int n2 = 0;
        while (n2 < n) {
            object = arrheader[n2];
            log = this.headerLog;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(">> ");
            stringBuilder.append(object.toString());
            log.debug((Object)stringBuilder.toString());
            ++n2;
        }
    }

    @Override
    public void setAttribute(String string2, Object object) {
        this.attributes.put(string2, object);
    }

    @Override
    public void shutdown() throws IOException {
        this.shutdown = true;
        try {
            super.shutdown();
            this.log.debug((Object)"Connection shut down");
            Socket socket = this.socket;
            if (socket == null) return;
            socket.close();
            return;
        }
        catch (IOException iOException) {
            this.log.debug((Object)"I/O error shutting down connection", (Throwable)iOException);
        }
    }

    @Override
    public void update(Socket socket, HttpHost httpHost, boolean bl, HttpParams httpParams) throws IOException {
        this.assertOpen();
        if (httpHost == null) throw new IllegalArgumentException("Target host must not be null.");
        if (httpParams == null) throw new IllegalArgumentException("Parameters must not be null.");
        if (socket != null) {
            this.socket = socket;
            this.bind(socket, httpParams);
        }
        this.targetHost = httpHost;
        this.connSecure = bl;
    }
}

