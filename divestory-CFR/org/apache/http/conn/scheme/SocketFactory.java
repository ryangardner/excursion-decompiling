/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.HttpParams;

@Deprecated
public interface SocketFactory {
    public Socket connectSocket(Socket var1, String var2, int var3, InetAddress var4, int var5, HttpParams var6) throws IOException, UnknownHostException, ConnectTimeoutException;

    public Socket createSocket() throws IOException;

    public boolean isSecure(Socket var1) throws IllegalArgumentException;
}
