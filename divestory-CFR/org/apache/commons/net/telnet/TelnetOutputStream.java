/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.telnet.TelnetClient;

final class TelnetOutputStream
extends OutputStream {
    private final TelnetClient __client;
    private final boolean __convertCRtoCRLF;
    private boolean __lastWasCR = false;

    TelnetOutputStream(TelnetClient telnetClient) {
        this.__convertCRtoCRLF = true;
        this.__client = telnetClient;
    }

    @Override
    public void close() throws IOException {
        this.__client._closeOutputStream();
    }

    @Override
    public void flush() throws IOException {
        this.__client._flushOutputStream();
    }

    @Override
    public void write(int n) throws IOException {
        TelnetClient telnetClient = this.__client;
        synchronized (telnetClient) {
            n &= 255;
            if (this.__client._requestedWont(0)) {
                if (this.__lastWasCR) {
                    this.__client._sendByte(10);
                    if (n == 10) {
                        this.__lastWasCR = false;
                        return;
                    }
                }
                if (n != 10) {
                    if (n != 13) {
                        if (n != 255) {
                            this.__client._sendByte(n);
                            this.__lastWasCR = false;
                        } else {
                            this.__client._sendByte(255);
                            this.__client._sendByte(255);
                            this.__lastWasCR = false;
                        }
                    } else {
                        this.__client._sendByte(13);
                        this.__lastWasCR = true;
                    }
                } else {
                    if (!this.__lastWasCR) {
                        this.__client._sendByte(13);
                    }
                    this.__client._sendByte(n);
                    this.__lastWasCR = false;
                }
            } else if (n == 255) {
                this.__client._sendByte(n);
                this.__client._sendByte(255);
            } else {
                this.__client._sendByte(n);
            }
            return;
        }
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.write(arrby, 0, arrby.length);
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        TelnetClient telnetClient = this.__client;
        synchronized (telnetClient) {
            void var3_3;
            while (--var3_3 > 0) {
                void var2_2;
                this.write(arrby[var2_2]);
                ++var2_2;
            }
            return;
        }
    }
}

