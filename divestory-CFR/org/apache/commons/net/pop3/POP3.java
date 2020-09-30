/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.pop3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.CRLFLineReader;
import org.apache.commons.net.pop3.POP3Command;

public class POP3
extends SocketClient {
    public static final int AUTHORIZATION_STATE = 0;
    public static final int DEFAULT_PORT = 110;
    public static final int DISCONNECTED_STATE = -1;
    public static final int TRANSACTION_STATE = 1;
    public static final int UPDATE_STATE = 2;
    static final String _DEFAULT_ENCODING = "ISO-8859-1";
    static final String _ERROR = "-ERR";
    static final String _OK = "+OK";
    static final String _OK_INT = "+ ";
    private int __popState;
    protected ProtocolCommandSupport _commandSupport_;
    String _lastReplyLine;
    BufferedReader _reader;
    int _replyCode;
    List<String> _replyLines;
    BufferedWriter _writer;

    public POP3() {
        this.setDefaultPort(110);
        this.__popState = -1;
        this._reader = null;
        this._writer = null;
        this._replyLines = new ArrayList<String>();
        this._commandSupport_ = new ProtocolCommandSupport(this);
    }

    private void __getReply() throws IOException {
        this._replyLines.clear();
        String string2 = this._reader.readLine();
        if (string2 == null) throw new EOFException("Connection closed without indication.");
        if (string2.startsWith(_OK)) {
            this._replyCode = 0;
        } else if (string2.startsWith(_ERROR)) {
            this._replyCode = 1;
        } else {
            if (!string2.startsWith(_OK_INT)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Received invalid POP3 protocol response from server.");
                stringBuilder.append(string2);
                throw new MalformedServerReplyException(stringBuilder.toString());
            }
            this._replyCode = 2;
        }
        this._replyLines.add(string2);
        this._lastReplyLine = string2;
        this.fireReplyReceived(this._replyCode, this.getReplyString());
    }

    @Override
    protected void _connectAction_() throws IOException {
        super._connectAction_();
        this._reader = new CRLFLineReader(new InputStreamReader(this._input_, _DEFAULT_ENCODING));
        this._writer = new BufferedWriter(new OutputStreamWriter(this._output_, _DEFAULT_ENCODING));
        this.__getReply();
        this.setState(0);
    }

    @Override
    public void disconnect() throws IOException {
        super.disconnect();
        this._reader = null;
        this._writer = null;
        this._lastReplyLine = null;
        this._replyLines.clear();
        this.setState(-1);
    }

    public void getAdditionalReply() throws IOException {
        String string2 = this._reader.readLine();
        while (string2 != null) {
            this._replyLines.add(string2);
            if (string2.equals(".")) {
                return;
            }
            string2 = this._reader.readLine();
        }
    }

    @Override
    protected ProtocolCommandSupport getCommandSupport() {
        return this._commandSupport_;
    }

    public String getReplyString() {
        StringBuilder stringBuilder = new StringBuilder(256);
        Iterator<String> iterator2 = this._replyLines.iterator();
        while (iterator2.hasNext()) {
            stringBuilder.append(iterator2.next());
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }

    public String[] getReplyStrings() {
        List<String> list = this._replyLines;
        return list.toArray(new String[list.size()]);
    }

    public int getState() {
        return this.__popState;
    }

    public void removeProtocolCommandistener(ProtocolCommandListener protocolCommandListener) {
        this.removeProtocolCommandListener(protocolCommandListener);
    }

    public int sendCommand(int n) throws IOException {
        return this.sendCommand(POP3Command._commands[n], null);
    }

    public int sendCommand(int n, String string2) throws IOException {
        return this.sendCommand(POP3Command._commands[n], string2);
    }

    public int sendCommand(String string2) throws IOException {
        return this.sendCommand(string2, null);
    }

    public int sendCommand(String string2, String string3) throws IOException {
        if (this._writer == null) throw new IllegalStateException("Socket is not connected");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        if (string3 != null) {
            stringBuilder.append(' ');
            stringBuilder.append(string3);
        }
        stringBuilder.append("\r\n");
        string3 = stringBuilder.toString();
        this._writer.write(string3);
        this._writer.flush();
        this.fireCommandSent(string2, string3);
        this.__getReply();
        return this._replyCode;
    }

    public void setState(int n) {
        this.__popState = n;
    }
}

