/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.bsd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import org.apache.commons.net.bsd.RExecClient;
import org.apache.commons.net.io.SocketInputStream;

public class RCommandClient
extends RExecClient {
    public static final int DEFAULT_PORT = 514;
    public static final int MAX_CLIENT_PORT = 1023;
    public static final int MIN_CLIENT_PORT = 512;

    public RCommandClient() {
        this.setDefaultPort(514);
    }

    @Override
    InputStream _createErrorStream() throws IOException {
        Object object;
        block4 : {
            for (int i = 1023; i >= 512; --i) {
                try {
                    object = this._serverSocketFactory_.createServerSocket(i, 1, this.getLocalAddress());
                    break block4;
                }
                catch (SocketException socketException) {
                    continue;
                }
            }
            object = null;
        }
        if (object == null) throw new BindException("All ports in use.");
        this._output_.write(Integer.toString(((ServerSocket)object).getLocalPort()).getBytes("UTF-8"));
        this._output_.write(0);
        this._output_.flush();
        Socket socket = ((ServerSocket)object).accept();
        ((ServerSocket)object).close();
        if (!this.isRemoteVerificationEnabled()) return new SocketInputStream(socket, socket.getInputStream());
        if (this.verifyRemote(socket)) {
            return new SocketInputStream(socket, socket.getInputStream());
        }
        socket.close();
        object = new StringBuilder();
        ((StringBuilder)object).append("Security violation: unexpected connection attempt by ");
        ((StringBuilder)object).append(socket.getInetAddress().getHostAddress());
        throw new IOException(((StringBuilder)object).toString());
    }

    @Override
    public void connect(String string2, int n) throws SocketException, IOException, UnknownHostException {
        this.connect(InetAddress.getByName(string2), n, InetAddress.getLocalHost());
    }

    public void connect(String string2, int n, InetAddress inetAddress) throws SocketException, IOException {
        this.connect(InetAddress.getByName(string2), n, inetAddress);
    }

    @Override
    public void connect(String charSequence, int n, InetAddress inetAddress, int n2) throws SocketException, IOException, IllegalArgumentException, UnknownHostException {
        if (n2 >= 512 && n2 <= 1023) {
            super.connect((String)charSequence, n, inetAddress, n2);
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Invalid port number ");
        ((StringBuilder)charSequence).append(n2);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Override
    public void connect(InetAddress inetAddress, int n) throws SocketException, IOException {
        this.connect(inetAddress, n, InetAddress.getLocalHost());
    }

    public void connect(InetAddress inetAddress, int n, InetAddress inetAddress2) throws SocketException, BindException, IOException {
        int n2;
        for (n2 = 1023; n2 >= 512; --n2) {
            try {
                this._socket_ = this._socketFactory_.createSocket(inetAddress, n, inetAddress2, n2);
                break;
            }
            catch (BindException | SocketException socketException) {
                continue;
            }
        }
        if (n2 < 512) throw new BindException("All ports in use or insufficient permssion.");
        this._connectAction_();
    }

    @Override
    public void connect(InetAddress serializable, int n, InetAddress inetAddress, int n2) throws SocketException, IOException, IllegalArgumentException {
        if (n2 >= 512 && n2 <= 1023) {
            super.connect((InetAddress)serializable, n, inetAddress, n2);
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Invalid port number ");
        ((StringBuilder)serializable).append(n2);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public void rcommand(String string2, String string3, String string4) throws IOException {
        this.rcommand(string2, string3, string4, false);
    }

    public void rcommand(String string2, String string3, String string4, boolean bl) throws IOException {
        this.rexec(string2, string3, string4, bl);
    }
}

