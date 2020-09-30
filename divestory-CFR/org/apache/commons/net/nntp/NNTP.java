/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.nntp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.CRLFLineReader;
import org.apache.commons.net.nntp.NNTPCommand;
import org.apache.commons.net.nntp.NNTPConnectionClosedException;

public class NNTP
extends SocketClient {
    public static final int DEFAULT_PORT = 119;
    private static final String __DEFAULT_ENCODING = "ISO-8859-1";
    protected ProtocolCommandSupport _commandSupport_;
    boolean _isAllowedToPost;
    protected BufferedReader _reader_;
    int _replyCode;
    String _replyString;
    protected BufferedWriter _writer_;

    public NNTP() {
        this.setDefaultPort(119);
        this._replyString = null;
        this._reader_ = null;
        this._writer_ = null;
        this._isAllowedToPost = false;
        this._commandSupport_ = new ProtocolCommandSupport(this);
    }

    private void __getReply() throws IOException {
        int n;
        CharSequence charSequence = this._reader_.readLine();
        this._replyString = charSequence;
        if (charSequence == null) throw new NNTPConnectionClosedException("Connection closed without indication.");
        if (((String)charSequence).length() < 3) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Truncated server reply: ");
            ((StringBuilder)charSequence).append(this._replyString);
            throw new MalformedServerReplyException(((StringBuilder)charSequence).toString());
        }
        try {
            this._replyCode = n = Integer.parseInt(this._replyString.substring(0, 3));
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not parse response code.\nServer Reply: ");
            stringBuilder.append(this._replyString);
            throw new MalformedServerReplyException(stringBuilder.toString());
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this._replyString);
        ((StringBuilder)charSequence).append("\r\n");
        this.fireReplyReceived(n, ((StringBuilder)charSequence).toString());
        if (this._replyCode == 400) throw new NNTPConnectionClosedException("NNTP response 400 received.  Server closed connection.");
    }

    @Override
    protected void _connectAction_() throws IOException {
        super._connectAction_();
        this._reader_ = new CRLFLineReader(new InputStreamReader(this._input_, __DEFAULT_ENCODING));
        this._writer_ = new BufferedWriter(new OutputStreamWriter(this._output_, __DEFAULT_ENCODING));
        this.__getReply();
        boolean bl = this._replyCode == 200;
        this._isAllowedToPost = bl;
    }

    public int article() throws IOException {
        return this.sendCommand(0);
    }

    @Deprecated
    public int article(int n) throws IOException {
        return this.article((long)n);
    }

    public int article(long l) throws IOException {
        return this.sendCommand(0, Long.toString(l));
    }

    public int article(String string2) throws IOException {
        return this.sendCommand(0, string2);
    }

    public int authinfoPass(String string2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PASS ");
        stringBuilder.append(string2);
        return this.sendCommand(15, stringBuilder.toString());
    }

    public int authinfoUser(String string2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("USER ");
        stringBuilder.append(string2);
        return this.sendCommand(15, stringBuilder.toString());
    }

    public int body() throws IOException {
        return this.sendCommand(1);
    }

    @Deprecated
    public int body(int n) throws IOException {
        return this.body((long)n);
    }

    public int body(long l) throws IOException {
        return this.sendCommand(1, Long.toString(l));
    }

    public int body(String string2) throws IOException {
        return this.sendCommand(1, string2);
    }

    @Override
    public void disconnect() throws IOException {
        super.disconnect();
        this._reader_ = null;
        this._writer_ = null;
        this._replyString = null;
        this._isAllowedToPost = false;
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
        return this._replyString;
    }

    public int group(String string2) throws IOException {
        return this.sendCommand(2, string2);
    }

    public int head() throws IOException {
        return this.sendCommand(3);
    }

    @Deprecated
    public int head(int n) throws IOException {
        return this.head((long)n);
    }

    public int head(long l) throws IOException {
        return this.sendCommand(3, Long.toString(l));
    }

    public int head(String string2) throws IOException {
        return this.sendCommand(3, string2);
    }

    public int help() throws IOException {
        return this.sendCommand(4);
    }

    public int ihave(String string2) throws IOException {
        return this.sendCommand(5, string2);
    }

    public boolean isAllowedToPost() {
        return this._isAllowedToPost;
    }

    public int last() throws IOException {
        return this.sendCommand(6);
    }

    public int list() throws IOException {
        return this.sendCommand(7);
    }

    public int listActive(String string2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder("ACTIVE ");
        stringBuilder.append(string2);
        return this.sendCommand(7, stringBuilder.toString());
    }

    public int newgroups(String string2, String string3, boolean bl, String string4) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(' ');
        stringBuilder.append(string3);
        if (bl) {
            stringBuilder.append(' ');
            stringBuilder.append("GMT");
        }
        if (string4 == null) return this.sendCommand(8, stringBuilder.toString());
        stringBuilder.append(" <");
        stringBuilder.append(string4);
        stringBuilder.append('>');
        return this.sendCommand(8, stringBuilder.toString());
    }

    public int newnews(String string2, String string3, String string4, boolean bl, String string5) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(' ');
        stringBuilder.append(string3);
        stringBuilder.append(' ');
        stringBuilder.append(string4);
        if (bl) {
            stringBuilder.append(' ');
            stringBuilder.append("GMT");
        }
        if (string5 == null) return this.sendCommand(9, stringBuilder.toString());
        stringBuilder.append(" <");
        stringBuilder.append(string5);
        stringBuilder.append('>');
        return this.sendCommand(9, stringBuilder.toString());
    }

    public int next() throws IOException {
        return this.sendCommand(10);
    }

    public int post() throws IOException {
        return this.sendCommand(11);
    }

    public int quit() throws IOException {
        return this.sendCommand(12);
    }

    public int sendCommand(int n) throws IOException {
        return this.sendCommand(n, null);
    }

    public int sendCommand(int n, String string2) throws IOException {
        return this.sendCommand(NNTPCommand.getCommand(n), string2);
    }

    public int sendCommand(String string2) throws IOException {
        return this.sendCommand(string2, null);
    }

    public int sendCommand(String string2, String object) throws IOException {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string2);
        if (object != null) {
            charSequence.append(' ');
            charSequence.append((String)object);
        }
        charSequence.append("\r\n");
        object = this._writer_;
        charSequence = charSequence.toString();
        ((Writer)object).write((String)charSequence);
        this._writer_.flush();
        this.fireCommandSent(string2, (String)charSequence);
        this.__getReply();
        return this._replyCode;
    }

    public int stat() throws IOException {
        return this.sendCommand(14);
    }

    @Deprecated
    public int stat(int n) throws IOException {
        return this.stat((long)n);
    }

    public int stat(long l) throws IOException {
        return this.sendCommand(14, Long.toString(l));
    }

    public int stat(String string2) throws IOException {
        return this.sendCommand(14, string2);
    }

    public int xhdr(String charSequence, String string2) throws IOException {
        charSequence = new StringBuilder((String)charSequence);
        ((StringBuilder)charSequence).append(" ");
        ((StringBuilder)charSequence).append(string2);
        return this.sendCommand(17, ((StringBuilder)charSequence).toString());
    }

    public int xover(String string2) throws IOException {
        return this.sendCommand(16, string2);
    }
}

