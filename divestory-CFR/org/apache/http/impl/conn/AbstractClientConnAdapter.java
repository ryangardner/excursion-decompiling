/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.protocol.HttpContext;

public abstract class AbstractClientConnAdapter
implements ManagedClientConnection,
HttpContext {
    private volatile ClientConnectionManager connManager;
    private volatile long duration;
    private volatile boolean markedReusable;
    private volatile boolean released;
    private volatile OperatedClientConnection wrappedConnection;

    protected AbstractClientConnAdapter(ClientConnectionManager clientConnectionManager, OperatedClientConnection operatedClientConnection) {
        this.connManager = clientConnectionManager;
        this.wrappedConnection = operatedClientConnection;
        this.markedReusable = false;
        this.released = false;
        this.duration = Long.MAX_VALUE;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    @Override
    public void abortConnection() {
        // MONITORENTER : this
        var1_1 = this.released;
        if (var1_1) {
            // MONITOREXIT : this
            return;
        }
        this.released = true;
        this.unmarkReusable();
        try {
            this.shutdown();
lbl10: // 2 sources:
            while (this.connManager != null) {
                this.connManager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
                return;
            }
            return;
        }
        catch (IOException var2_2) {
            ** GOTO lbl10
        }
    }

    @Deprecated
    protected final void assertNotAborted() throws InterruptedIOException {
        if (this.isReleased()) throw new InterruptedIOException("Connection has been shut down");
    }

    protected final void assertValid(OperatedClientConnection operatedClientConnection) throws ConnectionShutdownException {
        if (this.isReleased()) throw new ConnectionShutdownException();
        if (operatedClientConnection == null) throw new ConnectionShutdownException();
    }

    protected void detach() {
        synchronized (this) {
            this.wrappedConnection = null;
            this.connManager = null;
            this.duration = Long.MAX_VALUE;
            return;
        }
    }

    @Override
    public void flush() throws IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        operatedClientConnection.flush();
    }

    @Override
    public Object getAttribute(String object) {
        synchronized (this) {
            OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
            this.assertValid(operatedClientConnection);
            if (!(operatedClientConnection instanceof HttpContext)) return null;
            object = ((HttpContext)((Object)operatedClientConnection)).getAttribute((String)object);
            return object;
        }
    }

    @Override
    public InetAddress getLocalAddress() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getLocalAddress();
    }

    @Override
    public int getLocalPort() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getLocalPort();
    }

    protected ClientConnectionManager getManager() {
        return this.connManager;
    }

    @Override
    public HttpConnectionMetrics getMetrics() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getMetrics();
    }

    @Override
    public InetAddress getRemoteAddress() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getRemoteAddress();
    }

    @Override
    public int getRemotePort() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getRemotePort();
    }

    @Override
    public SSLSession getSSLSession() {
        Object object = this.getWrappedConnection();
        this.assertValid((OperatedClientConnection)object);
        boolean bl = this.isOpen();
        SSLSession sSLSession = null;
        if (!bl) {
            return null;
        }
        if (!((object = object.getSocket()) instanceof SSLSocket)) return sSLSession;
        return ((SSLSocket)object).getSession();
    }

    @Override
    public int getSocketTimeout() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.getSocketTimeout();
    }

    protected OperatedClientConnection getWrappedConnection() {
        return this.wrappedConnection;
    }

    @Override
    public boolean isMarkedReusable() {
        return this.markedReusable;
    }

    @Override
    public boolean isOpen() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        if (operatedClientConnection != null) return operatedClientConnection.isOpen();
        return false;
    }

    protected boolean isReleased() {
        return this.released;
    }

    @Override
    public boolean isResponseAvailable(int n) throws IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.isResponseAvailable(n);
    }

    @Override
    public boolean isSecure() {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        return operatedClientConnection.isSecure();
    }

    @Override
    public boolean isStale() {
        if (this.isReleased()) {
            return true;
        }
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        if (operatedClientConnection != null) return operatedClientConnection.isStale();
        return true;
    }

    @Override
    public void markReusable() {
        this.markedReusable = true;
    }

    @Override
    public void receiveResponseEntity(HttpResponse httpResponse) throws HttpException, IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        this.unmarkReusable();
        operatedClientConnection.receiveResponseEntity(httpResponse);
    }

    @Override
    public HttpResponse receiveResponseHeader() throws HttpException, IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        this.unmarkReusable();
        return operatedClientConnection.receiveResponseHeader();
    }

    @Override
    public void releaseConnection() {
        synchronized (this) {
            boolean bl = this.released;
            if (bl) {
                return;
            }
            this.released = true;
            if (this.connManager == null) return;
            this.connManager.releaseConnection(this, this.duration, TimeUnit.MILLISECONDS);
            return;
        }
    }

    @Override
    public Object removeAttribute(String object) {
        synchronized (this) {
            OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
            this.assertValid(operatedClientConnection);
            if (!(operatedClientConnection instanceof HttpContext)) return null;
            object = ((HttpContext)((Object)operatedClientConnection)).removeAttribute((String)object);
            return object;
        }
    }

    @Override
    public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        this.unmarkReusable();
        operatedClientConnection.sendRequestEntity(httpEntityEnclosingRequest);
    }

    @Override
    public void sendRequestHeader(HttpRequest httpRequest) throws HttpException, IOException {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        this.unmarkReusable();
        operatedClientConnection.sendRequestHeader(httpRequest);
    }

    @Override
    public void setAttribute(String string2, Object object) {
        synchronized (this) {
            OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
            this.assertValid(operatedClientConnection);
            if (!(operatedClientConnection instanceof HttpContext)) return;
            ((HttpContext)((Object)operatedClientConnection)).setAttribute(string2, object);
            return;
        }
    }

    @Override
    public void setIdleDuration(long l, TimeUnit timeUnit) {
        if (l > 0L) {
            this.duration = timeUnit.toMillis(l);
            return;
        }
        this.duration = -1L;
    }

    @Override
    public void setSocketTimeout(int n) {
        OperatedClientConnection operatedClientConnection = this.getWrappedConnection();
        this.assertValid(operatedClientConnection);
        operatedClientConnection.setSocketTimeout(n);
    }

    @Override
    public void unmarkReusable() {
        this.markedReusable = false;
    }
}

