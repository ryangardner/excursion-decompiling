/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.telnet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.telnet.InvalidTelnetOptionException;
import org.apache.commons.net.telnet.TelnetNotificationHandler;
import org.apache.commons.net.telnet.TelnetOption;
import org.apache.commons.net.telnet.TelnetOptionHandler;

class Telnet
extends SocketClient {
    static final int DEFAULT_PORT = 23;
    protected static final int TERMINAL_TYPE = 24;
    protected static final int TERMINAL_TYPE_IS = 0;
    protected static final int TERMINAL_TYPE_SEND = 1;
    static final byte[] _COMMAND_AYT;
    static final byte[] _COMMAND_DO;
    static final byte[] _COMMAND_DONT;
    static final byte[] _COMMAND_IS;
    static final byte[] _COMMAND_SB;
    static final byte[] _COMMAND_SE;
    static final byte[] _COMMAND_WILL;
    static final byte[] _COMMAND_WONT;
    static final int _DO_MASK = 2;
    static final int _REQUESTED_DO_MASK = 8;
    static final int _REQUESTED_WILL_MASK = 4;
    static final int _WILL_MASK = 1;
    static final boolean debug = false;
    static final boolean debugoptions = false;
    private TelnetNotificationHandler __notifhand = null;
    int[] _doResponse;
    int[] _options;
    int[] _willResponse;
    private volatile boolean aytFlag = true;
    private final Object aytMonitor = new Object();
    private final TelnetOptionHandler[] optionHandlers;
    private volatile OutputStream spyStream = null;
    private String terminalType = null;

    static {
        _COMMAND_DO = new byte[]{-1, -3};
        _COMMAND_DONT = new byte[]{-1, -2};
        _COMMAND_WILL = new byte[]{-1, -5};
        _COMMAND_WONT = new byte[]{-1, -4};
        _COMMAND_SB = new byte[]{-1, -6};
        _COMMAND_SE = new byte[]{-1, -16};
        _COMMAND_IS = new byte[]{24, 0};
        _COMMAND_AYT = new byte[]{-1, -10};
    }

    Telnet() {
        this.setDefaultPort(23);
        this._doResponse = new int[256];
        this._willResponse = new int[256];
        this._options = new int[256];
        this.optionHandlers = new TelnetOptionHandler[256];
    }

    Telnet(String string2) {
        this.setDefaultPort(23);
        this._doResponse = new int[256];
        this._willResponse = new int[256];
        this._options = new int[256];
        this.terminalType = string2;
        this.optionHandlers = new TelnetOptionHandler[256];
    }

    @Override
    protected void _connectAction_() throws IOException {
        TelnetOptionHandler[] arrtelnetOptionHandler;
        int n;
        int n2 = 0;
        for (n = 0; n < 256; ++n) {
            this._doResponse[n] = 0;
            this._willResponse[n] = 0;
            this._options[n] = 0;
            arrtelnetOptionHandler = this.optionHandlers;
            if (arrtelnetOptionHandler[n] == null) continue;
            arrtelnetOptionHandler[n].setDo(false);
            this.optionHandlers[n].setWill(false);
        }
        super._connectAction_();
        this._input_ = new BufferedInputStream(this._input_);
        this._output_ = new BufferedOutputStream(this._output_);
        n = n2;
        while (n < 256) {
            arrtelnetOptionHandler = this.optionHandlers;
            if (arrtelnetOptionHandler[n] != null) {
                if (arrtelnetOptionHandler[n].getInitLocal()) {
                    this._requestWill(this.optionHandlers[n].getOptionCode());
                }
                if (this.optionHandlers[n].getInitRemote()) {
                    this._requestDo(this.optionHandlers[n].getOptionCode());
                }
            }
            ++n;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    final void _processAYTResponse() {
        synchronized (this) {
            if (this.aytFlag) return;
            Object object = this.aytMonitor;
            synchronized (object) {
                this.aytFlag = true;
                this.aytMonitor.notifyAll();
                return;
            }
        }
    }

    void _processCommand(int n) {
        TelnetNotificationHandler telnetNotificationHandler = this.__notifhand;
        if (telnetNotificationHandler == null) return;
        telnetNotificationHandler.receivedNegotiation(5, n);
    }

    void _processDo(int n) throws IOException {
        boolean bl;
        Object object = this.__notifhand;
        if (object != null) {
            object.receivedNegotiation(1, n);
        }
        boolean bl2 = false;
        object = this.optionHandlers;
        if (object[n] != null) {
            bl = object[n].getAcceptLocal();
        } else {
            bl = bl2;
            if (n == 24) {
                object = this.terminalType;
                bl = bl2;
                if (object != null) {
                    bl = bl2;
                    if (((String)object).length() > 0) {
                        bl = true;
                    }
                }
            }
        }
        object = this._willResponse;
        if (object[n] > 0) {
            object[n] = object[n] - true;
            if (object[n] > 0 && this._stateIsWill(n)) {
                object = this._willResponse;
                object[n] = object[n] - true;
            }
        }
        if (this._willResponse[n] == 0 && this._requestedWont(n)) {
            if (bl) {
                this._setWantWill(n);
                this._sendWill(n);
            } else {
                object = this._willResponse;
                object[n] = object[n] + true;
                this._sendWont(n);
            }
        }
        this._setWill(n);
    }

    void _processDont(int n) throws IOException {
        int[] arrn = this.__notifhand;
        if (arrn != null) {
            arrn.receivedNegotiation(2, n);
        }
        if ((arrn = this._willResponse)[n] > 0) {
            arrn[n] = arrn[n] - 1;
            if (arrn[n] > 0 && this._stateIsWont(n)) {
                arrn = this._willResponse;
                arrn[n] = arrn[n] - 1;
            }
        }
        if (this._willResponse[n] == 0 && this._requestedWill(n)) {
            if (this._stateIsWill(n) || this._requestedWill(n)) {
                this._sendWont(n);
            }
            this._setWantWont(n);
        }
        this._setWont(n);
    }

    void _processSuboption(int[] arrn, int n) throws IOException {
        if (n <= 0) return;
        TelnetOptionHandler[] arrtelnetOptionHandler = this.optionHandlers;
        if (arrtelnetOptionHandler[arrn[0]] != null) {
            this._sendSubnegotiation(arrtelnetOptionHandler[arrn[0]].answerSubnegotiation(arrn, n));
            return;
        }
        if (n <= 1) return;
        if (arrn[0] != 24) return;
        if (arrn[1] != 1) return;
        this._sendTerminalType();
    }

    void _processWill(int n) throws IOException {
        Object[] arrobject = this.__notifhand;
        if (arrobject != null) {
            arrobject.receivedNegotiation(3, n);
        }
        boolean bl = false;
        arrobject = this.optionHandlers;
        if (arrobject[n] != null) {
            bl = arrobject[n].getAcceptRemote();
        }
        if ((arrobject = this._doResponse)[n] > 0) {
            arrobject[n] = arrobject[n] - true;
            if (arrobject[n] > 0 && this._stateIsDo(n)) {
                arrobject = this._doResponse;
                arrobject[n] = arrobject[n] - true;
            }
        }
        if (this._doResponse[n] == 0 && this._requestedDont(n)) {
            if (bl) {
                this._setWantDo(n);
                this._sendDo(n);
            } else {
                arrobject = this._doResponse;
                arrobject[n] = arrobject[n] + true;
                this._sendDont(n);
            }
        }
        this._setDo(n);
    }

    void _processWont(int n) throws IOException {
        int[] arrn = this.__notifhand;
        if (arrn != null) {
            arrn.receivedNegotiation(4, n);
        }
        if ((arrn = this._doResponse)[n] > 0) {
            arrn[n] = arrn[n] - 1;
            if (arrn[n] > 0 && this._stateIsDont(n)) {
                arrn = this._doResponse;
                arrn[n] = arrn[n] - 1;
            }
        }
        if (this._doResponse[n] == 0 && this._requestedDo(n)) {
            if (this._stateIsDo(n) || this._requestedDo(n)) {
                this._sendDont(n);
            }
            this._setWantDont(n);
        }
        this._setDont(n);
    }

    void _registerSpyStream(OutputStream outputStream2) {
        this.spyStream = outputStream2;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    final void _requestDo(int n) throws IOException {
        synchronized (this) {
            boolean bl;
            if (this._doResponse[n] == 0) {
                if (this._stateIsDo(n)) return;
            }
            if (bl = this._requestedDo(n)) {
                return;
            }
            this._setWantDo(n);
            int[] arrn = this._doResponse;
            arrn[n] = arrn[n] + 1;
            this._sendDo(n);
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    final void _requestDont(int n) throws IOException {
        synchronized (this) {
            boolean bl;
            if (this._doResponse[n] == 0) {
                if (this._stateIsDont(n)) return;
            }
            if (bl = this._requestedDont(n)) {
                return;
            }
            this._setWantDont(n);
            int[] arrn = this._doResponse;
            arrn[n] = arrn[n] + 1;
            this._sendDont(n);
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    final void _requestWill(int n) throws IOException {
        synchronized (this) {
            boolean bl;
            if (this._willResponse[n] == 0) {
                if (this._stateIsWill(n)) return;
            }
            if (bl = this._requestedWill(n)) {
                return;
            }
            this._setWantWill(n);
            int[] arrn = this._doResponse;
            arrn[n] = arrn[n] + 1;
            this._sendWill(n);
            return;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    final void _requestWont(int n) throws IOException {
        synchronized (this) {
            boolean bl;
            if (this._willResponse[n] == 0) {
                if (this._stateIsWont(n)) return;
            }
            if (bl = this._requestedWont(n)) {
                return;
            }
            this._setWantWont(n);
            int[] arrn = this._doResponse;
            arrn[n] = arrn[n] + 1;
            this._sendWont(n);
            return;
        }
    }

    boolean _requestedDo(int n) {
        if ((this._options[n] & 8) == 0) return false;
        return true;
    }

    boolean _requestedDont(int n) {
        return this._requestedDo(n) ^ true;
    }

    boolean _requestedWill(int n) {
        if ((this._options[n] & 4) == 0) return false;
        return true;
    }

    boolean _requestedWont(int n) {
        return this._requestedWill(n) ^ true;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    final boolean _sendAYT(long l) throws IOException, IllegalArgumentException, InterruptedException {
        Object object = this.aytMonitor;
        synchronized (object) {
            boolean bl;
            synchronized (this) {
                bl = false;
                this.aytFlag = false;
                this._output_.write(_COMMAND_AYT);
                this._output_.flush();
            }
            this.aytMonitor.wait(l);
            if (this.aytFlag) return true;
            this.aytFlag = true;
            return bl;
        }
    }

    final void _sendByte(int n) throws IOException {
        synchronized (this) {
            this._output_.write(n);
            this._spyWrite(n);
            return;
        }
    }

    final void _sendCommand(byte by) throws IOException {
        synchronized (this) {
            this._output_.write(255);
            this._output_.write(by);
            this._output_.flush();
            return;
        }
    }

    final void _sendDo(int n) throws IOException {
        synchronized (this) {
            this._output_.write(_COMMAND_DO);
            this._output_.write(n);
            this._output_.flush();
            return;
        }
    }

    final void _sendDont(int n) throws IOException {
        synchronized (this) {
            this._output_.write(_COMMAND_DONT);
            this._output_.write(n);
            this._output_.flush();
            return;
        }
    }

    /*
     * Enabled unnecessary exception pruning
     */
    final void _sendSubnegotiation(int[] arrn) throws IOException {
        synchronized (this) {
            if (arrn == null) return;
            this._output_.write(_COMMAND_SB);
            int n = arrn.length;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this._output_.write(_COMMAND_SE);
                    this._output_.flush();
                    return;
                }
                byte by = (byte)arrn[n2];
                if (by == -1) {
                    this._output_.write(by);
                }
                this._output_.write(by);
                ++n2;
            } while (true);
        }
    }

    final void _sendTerminalType() throws IOException {
        synchronized (this) {
            if (this.terminalType == null) return;
            this._output_.write(_COMMAND_SB);
            this._output_.write(_COMMAND_IS);
            this._output_.write(this.terminalType.getBytes(this.getCharsetName()));
            this._output_.write(_COMMAND_SE);
            this._output_.flush();
            return;
        }
    }

    final void _sendWill(int n) throws IOException {
        synchronized (this) {
            this._output_.write(_COMMAND_WILL);
            this._output_.write(n);
            this._output_.flush();
            return;
        }
    }

    final void _sendWont(int n) throws IOException {
        synchronized (this) {
            this._output_.write(_COMMAND_WONT);
            this._output_.write(n);
            this._output_.flush();
            return;
        }
    }

    void _setDo(int n) throws IOException {
        Object[] arrobject = this._options;
        arrobject[n] = arrobject[n] | 2;
        if (!this._requestedDo(n)) return;
        arrobject = this.optionHandlers;
        if (arrobject[n] == null) return;
        arrobject[n].setDo(true);
        arrobject = this.optionHandlers[n].startSubnegotiationRemote();
        if (arrobject == null) return;
        this._sendSubnegotiation((int[])arrobject);
    }

    void _setDont(int n) {
        Object[] arrobject = this._options;
        arrobject[n] = arrobject[n] & -3;
        arrobject = this.optionHandlers;
        if (arrobject[n] == null) return;
        arrobject[n].setDo(false);
    }

    void _setWantDo(int n) {
        int[] arrn = this._options;
        arrn[n] = arrn[n] | 8;
    }

    void _setWantDont(int n) {
        int[] arrn = this._options;
        arrn[n] = arrn[n] & -9;
    }

    void _setWantWill(int n) {
        int[] arrn = this._options;
        arrn[n] = arrn[n] | 4;
    }

    void _setWantWont(int n) {
        int[] arrn = this._options;
        arrn[n] = arrn[n] & -5;
    }

    void _setWill(int n) throws IOException {
        Object[] arrobject = this._options;
        arrobject[n] = arrobject[n] | 1;
        if (!this._requestedWill(n)) return;
        arrobject = this.optionHandlers;
        if (arrobject[n] == null) return;
        arrobject[n].setWill(true);
        arrobject = this.optionHandlers[n].startSubnegotiationLocal();
        if (arrobject == null) return;
        this._sendSubnegotiation((int[])arrobject);
    }

    void _setWont(int n) {
        Object[] arrobject = this._options;
        arrobject[n] = arrobject[n] & -2;
        arrobject = this.optionHandlers;
        if (arrobject[n] == null) return;
        arrobject[n].setWill(false);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    void _spyRead(int var1_1) {
        var2_2 = this.spyStream;
        if (var2_2 == null) return;
        if (var1_1 == 13) return;
        if (var1_1 != 10) ** GOTO lbl7
        try {
            var2_2.write(13);
lbl7: // 2 sources:
            var2_2.write(var1_1);
            var2_2.flush();
            return;
        }
        catch (IOException var2_3) {
            this.spyStream = null;
        }
    }

    void _spyWrite(int n) {
        OutputStream outputStream2;
        if (this._stateIsDo(1)) {
            if (this._requestedDo(1)) return;
        }
        if ((outputStream2 = this.spyStream) == null) return;
        try {
            outputStream2.write(n);
            outputStream2.flush();
            return;
        }
        catch (IOException iOException) {
            this.spyStream = null;
        }
    }

    boolean _stateIsDo(int n) {
        if ((this._options[n] & 2) == 0) return false;
        return true;
    }

    boolean _stateIsDont(int n) {
        return this._stateIsDo(n) ^ true;
    }

    boolean _stateIsWill(int n) {
        n = this._options[n];
        boolean bl = true;
        if ((n & 1) == 0) return false;
        return bl;
    }

    boolean _stateIsWont(int n) {
        return this._stateIsWill(n) ^ true;
    }

    void _stopSpyStream() {
        this.spyStream = null;
    }

    void addOptionHandler(TelnetOptionHandler telnetOptionHandler) throws InvalidTelnetOptionException, IOException {
        int n = telnetOptionHandler.getOptionCode();
        if (!TelnetOption.isValidOption(n)) throw new InvalidTelnetOptionException("Invalid Option Code", n);
        TelnetOptionHandler[] arrtelnetOptionHandler = this.optionHandlers;
        if (arrtelnetOptionHandler[n] != null) throw new InvalidTelnetOptionException("Already registered option", n);
        arrtelnetOptionHandler[n] = telnetOptionHandler;
        if (!this.isConnected()) return;
        if (telnetOptionHandler.getInitLocal()) {
            this._requestWill(n);
        }
        if (!telnetOptionHandler.getInitRemote()) return;
        this._requestDo(n);
    }

    void deleteOptionHandler(int n) throws InvalidTelnetOptionException, IOException {
        if (!TelnetOption.isValidOption(n)) throw new InvalidTelnetOptionException("Invalid Option Code", n);
        TelnetOptionHandler[] arrtelnetOptionHandler = this.optionHandlers;
        if (arrtelnetOptionHandler[n] == null) throw new InvalidTelnetOptionException("Unregistered option", n);
        TelnetOptionHandler telnetOptionHandler = arrtelnetOptionHandler[n];
        arrtelnetOptionHandler[n] = null;
        if (telnetOptionHandler.getWill()) {
            this._requestWont(n);
        }
        if (!telnetOptionHandler.getDo()) return;
        this._requestDont(n);
    }

    public void registerNotifHandler(TelnetNotificationHandler telnetNotificationHandler) {
        this.__notifhand = telnetNotificationHandler;
    }

    public void unregisterNotifHandler() {
        this.__notifhand = null;
    }
}

