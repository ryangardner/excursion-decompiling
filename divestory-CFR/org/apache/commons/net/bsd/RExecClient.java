/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.bsd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.SocketInputStream;

public class RExecClient
extends SocketClient {
    public static final int DEFAULT_PORT = 512;
    protected static final char NULL_CHAR = '\u0000';
    private boolean __remoteVerificationEnabled;
    protected InputStream _errorStream_ = null;

    public RExecClient() {
        this.setDefaultPort(512);
    }

    InputStream _createErrorStream() throws IOException {
        Object object = this._serverSocketFactory_.createServerSocket(0, 1, this.getLocalAddress());
        this._output_.write(Integer.toString(((ServerSocket)object).getLocalPort()).getBytes("UTF-8"));
        this._output_.write(0);
        this._output_.flush();
        Socket socket = ((ServerSocket)object).accept();
        ((ServerSocket)object).close();
        if (!this.__remoteVerificationEnabled) return new SocketInputStream(socket, socket.getInputStream());
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
    public void disconnect() throws IOException {
        InputStream inputStream2 = this._errorStream_;
        if (inputStream2 != null) {
            inputStream2.close();
        }
        this._errorStream_ = null;
        super.disconnect();
    }

    public InputStream getErrorStream() {
        return this._errorStream_;
    }

    public InputStream getInputStream() {
        return this._input_;
    }

    public OutputStream getOutputStream() {
        return this._output_;
    }

    public final boolean isRemoteVerificationEnabled() {
        return this.__remoteVerificationEnabled;
    }

    public void rexec(String string2, String string3, String string4) throws IOException {
        this.rexec(string2, string3, string4, false);
    }

    public void rexec(String charSequence, String string2, String string3, boolean bl) throws IOException {
        if (bl) {
            this._errorStream_ = this._createErrorStream();
        } else {
            this._output_.write(0);
        }
        this._output_.write(((String)charSequence).getBytes(this.getCharsetName()));
        this._output_.write(0);
        this._output_.write(string2.getBytes(this.getCharsetName()));
        this._output_.write(0);
        this._output_.write(string3.getBytes(this.getCharsetName()));
        this._output_.write(0);
        this._output_.flush();
        int n = this._input_.read();
        if (n <= 0) {
            if (n < 0) throw new IOException("Server closed connection.");
            return;
        }
        charSequence = new StringBuilder();
        while ((n = this._input_.read()) != -1) {
            if (n == 10) throw new IOException(((StringBuilder)charSequence).toString());
            ((StringBuilder)charSequence).append((char)n);
        }
        throw new IOException(((StringBuilder)charSequence).toString());
    }

    public final void setRemoteVerificationEnabled(boolean bl) {
        this.__remoteVerificationEnabled = bl;
    }
}

