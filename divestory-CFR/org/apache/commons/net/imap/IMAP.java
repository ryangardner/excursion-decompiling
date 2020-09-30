/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.imap;

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
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.imap.IMAPCommand;
import org.apache.commons.net.imap.IMAPReply;
import org.apache.commons.net.io.CRLFLineReader;

public class IMAP
extends SocketClient {
    public static final int DEFAULT_PORT = 143;
    public static final IMAPChunkListener TRUE_CHUNK_LISTENER = new IMAPChunkListener(){

        @Override
        public boolean chunkReceived(IMAP iMAP) {
            return true;
        }
    };
    protected static final String __DEFAULT_ENCODING = "ISO-8859-1";
    private volatile IMAPChunkListener __chunkListener;
    private IMAPState __state;
    protected BufferedWriter __writer;
    private final char[] _initialID = new char[]{'A', 'A', 'A', 'A'};
    protected BufferedReader _reader;
    private int _replyCode;
    private final List<String> _replyLines;

    public IMAP() {
        this.setDefaultPort(143);
        this.__state = IMAPState.DISCONNECTED_STATE;
        this._reader = null;
        this.__writer = null;
        this._replyLines = new ArrayList<String>();
        this.createCommandSupport();
    }

    private void __getReply() throws IOException {
        this.__getReply(true);
    }

    private void __getReply(boolean bl) throws IOException {
        this._replyLines.clear();
        Object object = this._reader.readLine();
        if (object == null) throw new EOFException("Connection closed without indication.");
        this._replyLines.add((String)object);
        if (!bl) {
            this._replyCode = IMAPReply.getUntaggedReplyCode((String)object);
        } else {
            while (IMAPReply.isUntagged((String)object)) {
                int n = IMAPReply.literalCount((String)object);
                boolean bl2 = n >= 0;
                while (n >= 0) {
                    object = this._reader.readLine();
                    if (object == null) throw new EOFException("Connection closed without indication.");
                    this._replyLines.add((String)object);
                    n -= ((String)object).length() + 2;
                }
                if (bl2 && (object = this.__chunkListener) != null && object.chunkReceived(this)) {
                    this.fireReplyReceived(3, this.getReplyString());
                    this._replyLines.clear();
                }
                if ((object = this._reader.readLine()) == null) throw new EOFException("Connection closed without indication.");
                this._replyLines.add((String)object);
            }
            this._replyCode = IMAPReply.getReplyCode((String)object);
        }
        this.fireReplyReceived(this._replyCode, this.getReplyString());
    }

    private int sendCommandWithID(String string2, String string3, String string4) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (string2 != null) {
            stringBuilder.append(string2);
            stringBuilder.append(' ');
        }
        stringBuilder.append(string3);
        if (string4 != null) {
            stringBuilder.append(' ');
            stringBuilder.append(string4);
        }
        stringBuilder.append("\r\n");
        string2 = stringBuilder.toString();
        this.__writer.write(string2);
        this.__writer.flush();
        this.fireCommandSent(string3, string2);
        this.__getReply();
        return this._replyCode;
    }

    @Override
    protected void _connectAction_() throws IOException {
        super._connectAction_();
        this._reader = new CRLFLineReader(new InputStreamReader(this._input_, __DEFAULT_ENCODING));
        this.__writer = new BufferedWriter(new OutputStreamWriter(this._output_, __DEFAULT_ENCODING));
        int n = this.getSoTimeout();
        if (n <= 0) {
            this.setSoTimeout(this.connectTimeout);
        }
        this.__getReply(false);
        if (n <= 0) {
            this.setSoTimeout(n);
        }
        this.setState(IMAPState.NOT_AUTH_STATE);
    }

    @Override
    public void disconnect() throws IOException {
        super.disconnect();
        this._reader = null;
        this.__writer = null;
        this._replyLines.clear();
        this.setState(IMAPState.DISCONNECTED_STATE);
    }

    public boolean doCommand(IMAPCommand iMAPCommand) throws IOException {
        return IMAPReply.isSuccess(this.sendCommand(iMAPCommand));
    }

    public boolean doCommand(IMAPCommand iMAPCommand, String string2) throws IOException {
        return IMAPReply.isSuccess(this.sendCommand(iMAPCommand, string2));
    }

    @Override
    protected void fireReplyReceived(int n, String string2) {
        if (this.getCommandSupport().getListenerCount() <= 0) return;
        this.getCommandSupport().fireReplyReceived(n, this.getReplyString());
    }

    protected String generateCommandID() {
        String string2 = new String(this._initialID);
        int n = this._initialID.length - 1;
        boolean bl = true;
        while (bl) {
            if (n < 0) return string2;
            char[] arrc = this._initialID;
            if (arrc[n] == 'Z') {
                arrc[n] = (char)65;
            } else {
                arrc[n] = (char)(arrc[n] + '\u0001');
                bl = false;
            }
            --n;
        }
        return string2;
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

    public IMAPState getState() {
        return this.__state;
    }

    public int sendCommand(String string2) throws IOException {
        return this.sendCommand(string2, null);
    }

    public int sendCommand(String string2, String string3) throws IOException {
        return this.sendCommandWithID(this.generateCommandID(), string2, string3);
    }

    public int sendCommand(IMAPCommand iMAPCommand) throws IOException {
        return this.sendCommand(iMAPCommand, null);
    }

    public int sendCommand(IMAPCommand iMAPCommand, String string2) throws IOException {
        return this.sendCommand(iMAPCommand.getIMAPCommand(), string2);
    }

    public int sendData(String string2) throws IOException {
        return this.sendCommandWithID(null, string2, null);
    }

    public void setChunkListener(IMAPChunkListener iMAPChunkListener) {
        this.__chunkListener = iMAPChunkListener;
    }

    protected void setState(IMAPState iMAPState) {
        this.__state = iMAPState;
    }

    public static interface IMAPChunkListener {
        public boolean chunkReceived(IMAP var1);
    }

    public static final class IMAPState
    extends Enum<IMAPState> {
        private static final /* synthetic */ IMAPState[] $VALUES;
        public static final /* enum */ IMAPState AUTH_STATE;
        public static final /* enum */ IMAPState DISCONNECTED_STATE;
        public static final /* enum */ IMAPState LOGOUT_STATE;
        public static final /* enum */ IMAPState NOT_AUTH_STATE;

        static {
            IMAPState iMAPState;
            DISCONNECTED_STATE = new IMAPState();
            NOT_AUTH_STATE = new IMAPState();
            AUTH_STATE = new IMAPState();
            LOGOUT_STATE = iMAPState = new IMAPState();
            $VALUES = new IMAPState[]{DISCONNECTED_STATE, NOT_AUTH_STATE, AUTH_STATE, iMAPState};
        }

        public static IMAPState valueOf(String string2) {
            return Enum.valueOf(IMAPState.class, string2);
        }

        public static IMAPState[] values() {
            return (IMAPState[])$VALUES.clone();
        }
    }

}

