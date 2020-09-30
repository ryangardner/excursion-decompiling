/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.commons.net.telnet.Telnet;
import org.apache.commons.net.telnet.TelnetInputListener;
import org.apache.commons.net.telnet.TelnetInputStream;
import org.apache.commons.net.telnet.TelnetNotificationHandler;
import org.apache.commons.net.telnet.TelnetOptionHandler;
import org.apache.commons.net.telnet.TelnetOutputStream;

public class TelnetClient
extends Telnet {
    private InputStream __input = null;
    private OutputStream __output = null;
    private TelnetInputListener inputListener;
    protected boolean readerThread = true;

    public TelnetClient() {
        super("VT100");
    }

    public TelnetClient(String string2) {
        super(string2);
    }

    void _closeOutputStream() throws IOException {
        this._output_.close();
    }

    @Override
    protected void _connectAction_() throws IOException {
        super._connectAction_();
        TelnetInputStream telnetInputStream = new TelnetInputStream(this._input_, this, this.readerThread);
        if (this.readerThread) {
            telnetInputStream._start();
        }
        this.__input = new BufferedInputStream(telnetInputStream);
        this.__output = new TelnetOutputStream(this);
    }

    void _flushOutputStream() throws IOException {
        this._output_.flush();
    }

    @Override
    public void addOptionHandler(TelnetOptionHandler telnetOptionHandler) throws InvalidTelnetOptionException, IOException {
        super.addOptionHandler(telnetOptionHandler);
    }

    @Override
    public void deleteOptionHandler(int n) throws InvalidTelnetOptionException, IOException {
        super.deleteOptionHandler(n);
    }

    @Override
    public void disconnect() throws IOException {
        Closeable closeable = this.__input;
        if (closeable != null) {
            ((InputStream)closeable).close();
        }
        if ((closeable = this.__output) != null) {
            ((OutputStream)closeable).close();
        }
        super.disconnect();
    }

    public InputStream getInputStream() {
        return this.__input;
    }

    public boolean getLocalOptionState(int n) {
        if (!this._stateIsWill(n)) return false;
        if (!this._requestedWill(n)) return false;
        return true;
    }

    public OutputStream getOutputStream() {
        return this.__output;
    }

    public boolean getReaderThread() {
        return this.readerThread;
    }

    public boolean getRemoteOptionState(int n) {
        if (!this._stateIsDo(n)) return false;
        if (!this._requestedDo(n)) return false;
        return true;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    void notifyInputListener() {
        TelnetInputListener telnetInputListener;
        synchronized (this) {
            telnetInputListener = this.inputListener;
        }
        if (telnetInputListener == null) return;
        telnetInputListener.telnetInputAvailable();
    }

    public void registerInputListener(TelnetInputListener telnetInputListener) {
        synchronized (this) {
            this.inputListener = telnetInputListener;
            return;
        }
    }

    @Override
    public void registerNotifHandler(TelnetNotificationHandler telnetNotificationHandler) {
        super.registerNotifHandler(telnetNotificationHandler);
    }

    public void registerSpyStream(OutputStream outputStream2) {
        super._registerSpyStream(outputStream2);
    }

    public boolean sendAYT(long l) throws IOException, IllegalArgumentException, InterruptedException {
        return this._sendAYT(l);
    }

    public void sendCommand(byte by) throws IOException, IllegalArgumentException {
        this._sendCommand(by);
    }

    public void sendSubnegotiation(int[] arrn) throws IOException, IllegalArgumentException {
        if (arrn.length < 1) throw new IllegalArgumentException("zero length message");
        this._sendSubnegotiation(arrn);
    }

    public void setReaderThread(boolean bl) {
        this.readerThread = bl;
    }

    public void stopSpyStream() {
        super._stopSpyStream();
    }

    public void unregisterInputListener() {
        synchronized (this) {
            this.inputListener = null;
            return;
        }
    }

    @Override
    public void unregisterNotifHandler() {
        super.unregisterNotifHandler();
    }
}

