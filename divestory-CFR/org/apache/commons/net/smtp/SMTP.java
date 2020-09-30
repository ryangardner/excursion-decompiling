/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.smtp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.CRLFLineReader;
import org.apache.commons.net.smtp.SMTPCommand;
import org.apache.commons.net.smtp.SMTPConnectionClosedException;

public class SMTP
extends SocketClient {
    public static final int DEFAULT_PORT = 25;
    private static final String __DEFAULT_ENCODING = "ISO-8859-1";
    protected ProtocolCommandSupport _commandSupport_;
    private boolean _newReplyString;
    BufferedReader _reader;
    private int _replyCode;
    private final ArrayList<String> _replyLines;
    private String _replyString;
    BufferedWriter _writer;
    protected final String encoding;

    public SMTP() {
        this(__DEFAULT_ENCODING);
    }

    public SMTP(String string2) {
        this.setDefaultPort(25);
        this._replyLines = new ArrayList();
        this._newReplyString = false;
        this._replyString = null;
        this._commandSupport_ = new ProtocolCommandSupport(this);
        this.encoding = string2;
    }

    private void __getReply() throws IOException {
        this._newReplyString = true;
        this._replyLines.clear();
        String string2 = this._reader.readLine();
        if (string2 == null) throw new SMTPConnectionClosedException("Connection closed without indication.");
        int n = string2.length();
        if (n < 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Truncated server reply: ");
            stringBuilder.append(string2);
            throw new MalformedServerReplyException(stringBuilder.toString());
        }
        try {
            this._replyCode = Integer.parseInt(string2.substring(0, 3));
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not parse response code.\nServer Reply: ");
            stringBuilder.append(string2);
            throw new MalformedServerReplyException(stringBuilder.toString());
        }
        this._replyLines.add(string2);
        if (n > 3 && string2.charAt(3) == '-') {
            do {
                if ((string2 = this._reader.readLine()) == null) throw new SMTPConnectionClosedException("Connection closed without indication.");
                this._replyLines.add(string2);
            } while (string2.length() < 4 || string2.charAt(3) == '-' || !Character.isDigit(string2.charAt(0)));
        }
        this.fireReplyReceived(this._replyCode, this.getReplyString());
        if (this._replyCode == 421) throw new SMTPConnectionClosedException("SMTP response 421 received.  Server closed connection.");
        return;
    }

    private int __sendCommand(int n, String string2, boolean bl) throws IOException {
        return this.__sendCommand(SMTPCommand.getCommand(n), string2, bl);
    }

    private int __sendCommand(String string2, String object, boolean bl) throws IOException {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string2);
        if (object != null) {
            if (bl) {
                charSequence.append(' ');
            }
            charSequence.append((String)object);
        }
        charSequence.append("\r\n");
        object = this._writer;
        charSequence = charSequence.toString();
        ((Writer)object).write((String)charSequence);
        this._writer.flush();
        this.fireCommandSent(string2, (String)charSequence);
        this.__getReply();
        return this._replyCode;
    }

    @Override
    protected void _connectAction_() throws IOException {
        super._connectAction_();
        this._reader = new CRLFLineReader(new InputStreamReader(this._input_, this.encoding));
        this._writer = new BufferedWriter(new OutputStreamWriter(this._output_, this.encoding));
        this.__getReply();
    }

    public int data() throws IOException {
        return this.sendCommand(3);
    }

    @Override
    public void disconnect() throws IOException {
        super.disconnect();
        this._reader = null;
        this._writer = null;
        this._replyString = null;
        this._replyLines.clear();
        this._newReplyString = false;
    }

    public int expn(String string2) throws IOException {
        return this.sendCommand(9, string2);
    }

    @Override
    protected ProtocolCommandSupport getCommandSupport() {
        return this._commandSupport_;
    }

    public int getReply() throws IOException {
        this.__getReply();
        return this._replyCode;
    }

    public int getReplyCode() {
        return this._replyCode;
    }

    public String getReplyString() {
        if (!this._newReplyString) {
            return this._replyString;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object object = this._replyLines.iterator();
        do {
            if (!object.hasNext()) {
                this._newReplyString = false;
                this._replyString = object = stringBuilder.toString();
                return object;
            }
            stringBuilder.append(object.next());
            stringBuilder.append("\r\n");
        } while (true);
    }

    public String[] getReplyStrings() {
        ArrayList<String> arrayList = this._replyLines;
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public int helo(String string2) throws IOException {
        return this.sendCommand(0, string2);
    }

    public int help() throws IOException {
        return this.sendCommand(10);
    }

    public int help(String string2) throws IOException {
        return this.sendCommand(10, string2);
    }

    public int mail(String string2) throws IOException {
        return this.__sendCommand(1, string2, false);
    }

    public int noop() throws IOException {
        return this.sendCommand(11);
    }

    public int quit() throws IOException {
        return this.sendCommand(13);
    }

    public int rcpt(String string2) throws IOException {
        return this.__sendCommand(2, string2, false);
    }

    public void removeProtocolCommandistener(ProtocolCommandListener protocolCommandListener) {
        this.removeProtocolCommandListener(protocolCommandListener);
    }

    public int rset() throws IOException {
        return this.sendCommand(7);
    }

    public int saml(String string2) throws IOException {
        return this.sendCommand(6, string2);
    }

    public int send(String string2) throws IOException {
        return this.sendCommand(4, string2);
    }

    public int sendCommand(int n) throws IOException {
        return this.sendCommand(n, null);
    }

    public int sendCommand(int n, String string2) throws IOException {
        return this.sendCommand(SMTPCommand.getCommand(n), string2);
    }

    public int sendCommand(String string2) throws IOException {
        return this.sendCommand(string2, null);
    }

    public int sendCommand(String string2, String string3) throws IOException {
        return this.__sendCommand(string2, string3, true);
    }

    public int soml(String string2) throws IOException {
        return this.sendCommand(5, string2);
    }

    public int turn() throws IOException {
        return this.sendCommand(12);
    }

    public int vrfy(String string2) throws IOException {
        return this.sendCommand(8, string2);
    }
}

