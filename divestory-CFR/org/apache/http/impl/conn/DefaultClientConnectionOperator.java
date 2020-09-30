/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.impl.conn.HttpInetSocketAddress;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class DefaultClientConnectionOperator
implements ClientConnectionOperator {
    private final Log log = LogFactory.getLog(this.getClass());
    protected final SchemeRegistry schemeRegistry;

    public DefaultClientConnectionOperator(SchemeRegistry schemeRegistry) {
        if (schemeRegistry == null) throw new IllegalArgumentException("Scheme registry amy not be null");
        this.schemeRegistry = schemeRegistry;
    }

    @Override
    public OperatedClientConnection createConnection() {
        return new DefaultClientConnection();
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    public void openConnection(OperatedClientConnection operatedClientConnection, HttpHost httpHost, InetAddress inetAddress, HttpContext httpContext, HttpParams httpParams) throws IOException {
        if (operatedClientConnection == null) throw new IllegalArgumentException("Connection may not be null");
        if (httpHost == null) throw new IllegalArgumentException("Target host may not be null");
        if (httpParams == null) throw new IllegalArgumentException("Parameters may not be null");
        if (operatedClientConnection.isOpen()) throw new IllegalStateException("Connection must not be open");
        Object object = this.schemeRegistry.getScheme(httpHost.getSchemeName());
        SchemeSocketFactory schemeSocketFactory = ((Scheme)object).getSchemeSocketFactory();
        InetAddress[] arrinetAddress = this.resolveHostname(httpHost.getHostName());
        int n = ((Scheme)object).resolvePort(httpHost.getPort());
        int n2 = 0;
        while (n2 < arrinetAddress.length) {
            HttpInetSocketAddress httpInetSocketAddress;
            Socket socket;
            block16 : {
                boolean bl;
                block15 : {
                    block14 : {
                        block13 : {
                            object = arrinetAddress[n2];
                            int n3 = arrinetAddress.length;
                            bl = true;
                            if (n2 != n3 - 1) {
                                bl = false;
                            }
                            socket = schemeSocketFactory.createSocket(httpParams);
                            operatedClientConnection.opening(socket, httpHost);
                            httpInetSocketAddress = new HttpInetSocketAddress(httpHost, (InetAddress)object, n);
                            object = null;
                            if (inetAddress != null) {
                                object = new InetSocketAddress(inetAddress, 0);
                            }
                            if (this.log.isDebugEnabled()) {
                                Log log = this.log;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Connecting to ");
                                stringBuilder.append(httpInetSocketAddress);
                                log.debug((Object)stringBuilder.toString());
                            }
                            object = schemeSocketFactory.connectSocket(socket, httpInetSocketAddress, (InetSocketAddress)object, httpParams);
                            if (socket != object) {
                                operatedClientConnection.opening((Socket)object, httpHost);
                                break block13;
                            }
                            object = socket;
                        }
                        try {
                            this.prepareSocket((Socket)object, httpContext, httpParams);
                            operatedClientConnection.openCompleted(schemeSocketFactory.isSecure((Socket)object), httpParams);
                            return;
                        }
                        catch (ConnectTimeoutException connectTimeoutException) {
                            break block14;
                        }
                        catch (ConnectException connectException) {
                            break block15;
                        }
                        catch (ConnectTimeoutException connectTimeoutException) {
                            // empty catch block
                        }
                    }
                    if (bl) throw object;
                    break block16;
                    catch (ConnectException connectException) {
                        // empty catch block
                    }
                }
                if (bl) throw new HttpHostConnectException(httpHost, (ConnectException)object);
            }
            if (this.log.isDebugEnabled()) {
                socket = this.log;
                object = new StringBuilder();
                ((StringBuilder)object).append("Connect to ");
                ((StringBuilder)object).append(httpInetSocketAddress);
                ((StringBuilder)object).append(" timed out. ");
                ((StringBuilder)object).append("Connection will be retried using another IP address");
                socket.debug((Object)((StringBuilder)object).toString());
            }
            ++n2;
        }
    }

    protected void prepareSocket(Socket socket, HttpContext httpContext, HttpParams httpParams) throws IOException {
        socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(httpParams));
        socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
        int n = HttpConnectionParams.getLinger(httpParams);
        if (n < 0) return;
        boolean bl = n > 0;
        socket.setSoLinger(bl, n);
    }

    protected InetAddress[] resolveHostname(String string2) throws UnknownHostException {
        return InetAddress.getAllByName(string2);
    }

    @Override
    public void updateSecureConnection(OperatedClientConnection object, HttpHost httpHost, HttpContext httpContext, HttpParams httpParams) throws IOException {
        if (object == null) throw new IllegalArgumentException("Connection may not be null");
        if (httpHost == null) throw new IllegalArgumentException("Target host may not be null");
        if (httpParams == null) throw new IllegalArgumentException("Parameters may not be null");
        if (!object.isOpen()) throw new IllegalStateException("Connection must be open");
        Object object2 = this.schemeRegistry.getScheme(httpHost.getSchemeName());
        if (!(((Scheme)object2).getSchemeSocketFactory() instanceof LayeredSchemeSocketFactory)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Target scheme (");
            ((StringBuilder)object).append(((Scheme)object2).getName());
            ((StringBuilder)object).append(") must have layered socket factory.");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object2 = (LayeredSchemeSocketFactory)((Scheme)object2).getSchemeSocketFactory();
        try {
            Socket socket = object2.createLayeredSocket(object.getSocket(), httpHost.getHostName(), httpHost.getPort(), true);
            this.prepareSocket(socket, httpContext, httpParams);
            object.update(socket, httpHost, object2.isSecure(socket), httpParams);
            return;
        }
        catch (ConnectException connectException) {
            throw new HttpHostConnectException(httpHost, connectException);
        }
    }
}

