/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.Socket;
import org.apache.http.impl.io.AbstractSessionInputBuffer;
import org.apache.http.io.EofSensor;
import org.apache.http.params.HttpParams;

public class SocketInputBuffer
extends AbstractSessionInputBuffer
implements EofSensor {
    private static final Class SOCKET_TIMEOUT_CLASS = SocketInputBuffer.SocketTimeoutExceptionClass();
    private boolean eof;
    private final Socket socket;

    public SocketInputBuffer(Socket socket, int n, HttpParams httpParams) throws IOException {
        if (socket == null) throw new IllegalArgumentException("Socket may not be null");
        this.socket = socket;
        this.eof = false;
        int n2 = n;
        if (n < 0) {
            n2 = socket.getReceiveBufferSize();
        }
        n = n2;
        if (n2 < 1024) {
            n = 1024;
        }
        this.init(socket.getInputStream(), n, httpParams);
    }

    private static Class SocketTimeoutExceptionClass() {
        try {
            return Class.forName("java.net.SocketTimeoutException");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    private static boolean isSocketTimeoutException(InterruptedIOException interruptedIOException) {
        Class class_ = SOCKET_TIMEOUT_CLASS;
        if (class_ == null) return true;
        return class_.isInstance(interruptedIOException);
    }

    @Override
    protected int fillBuffer() throws IOException {
        int n = super.fillBuffer();
        boolean bl = n == -1;
        this.eof = bl;
        return n;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    @Override
    public boolean isDataAvailable(int var1_1) throws IOException {
        var3_3 = var2_2 = this.hasBufferedData();
        if (var2_2 != false) return var3_3;
        var4_4 = this.socket.getSoTimeout();
        this.socket.setSoTimeout(var1_1);
        this.fillBuffer();
        var2_2 = var3_3 = this.hasBufferedData();
lbl9: // 2 sources:
        do {
            this.socket.setSoTimeout(var4_4);
            return var2_2;
            break;
        } while (true);
        {
            catch (Throwable var5_5) {
            }
            catch (InterruptedIOException var5_6) {}
            {
                if (SocketInputBuffer.isSocketTimeoutException(var5_6) == false) throw var5_6;
                ** continue;
            }
        }
        this.socket.setSoTimeout(var4_4);
        throw var5_5;
    }

    @Override
    public boolean isEof() {
        return this.eof;
    }
}

