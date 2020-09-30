/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ProtocolCommandSupport;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPCommand;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CRLFLineReader;

public class FTP
extends SocketClient {
    public static final int ASCII_FILE_TYPE = 0;
    public static final int BINARY_FILE_TYPE = 2;
    public static final int BLOCK_TRANSFER_MODE = 11;
    public static final int CARRIAGE_CONTROL_TEXT_FORMAT = 6;
    public static final int COMPRESSED_TRANSFER_MODE = 12;
    public static final String DEFAULT_CONTROL_ENCODING = "ISO-8859-1";
    public static final int DEFAULT_DATA_PORT = 20;
    public static final int DEFAULT_PORT = 21;
    public static final int EBCDIC_FILE_TYPE = 1;
    public static final int FILE_STRUCTURE = 7;
    public static final int LOCAL_FILE_TYPE = 3;
    public static final int NON_PRINT_TEXT_FORMAT = 4;
    public static final int PAGE_STRUCTURE = 9;
    public static final int RECORD_STRUCTURE = 8;
    public static final int REPLY_CODE_LEN = 3;
    public static final int STREAM_TRANSFER_MODE = 10;
    public static final int TELNET_TEXT_FORMAT = 5;
    private static final String __modes = "AEILNTCFRPSBC";
    protected ProtocolCommandSupport _commandSupport_;
    protected String _controlEncoding;
    protected BufferedReader _controlInput_;
    protected BufferedWriter _controlOutput_;
    protected boolean _newReplyString;
    protected int _replyCode;
    protected ArrayList<String> _replyLines;
    protected String _replyString;
    protected boolean strictMultilineParsing = false;

    public FTP() {
        this.setDefaultPort(21);
        this._replyLines = new ArrayList();
        this._newReplyString = false;
        this._replyString = null;
        this._controlEncoding = DEFAULT_CONTROL_ENCODING;
        this._commandSupport_ = new ProtocolCommandSupport(this);
    }

    private String __buildMessage(String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        if (string3 != null) {
            stringBuilder.append(' ');
            stringBuilder.append(string3);
        }
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }

    private void __getReply() throws IOException {
        this.__getReply(true);
    }

    private void __getReply(boolean bl) throws IOException {
        block5 : {
            String string2;
            this._newReplyString = true;
            this._replyLines.clear();
            String string3 = this._controlInput_.readLine();
            if (string3 == null) throw new FTPConnectionClosedException("Connection closed without indication.");
            int n = string3.length();
            if (n < 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Truncated server reply: ");
                stringBuilder.append(string3);
                throw new MalformedServerReplyException(stringBuilder.toString());
            }
            try {
                string2 = string3.substring(0, 3);
                this._replyCode = Integer.parseInt(string2);
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not parse response code.\nServer Reply: ");
                stringBuilder.append(string3);
                throw new MalformedServerReplyException(stringBuilder.toString());
            }
            this._replyLines.add(string3);
            if (n <= 3 || string3.charAt(3) != '-') break block5;
            do {
                if ((string3 = this._controlInput_.readLine()) == null) throw new FTPConnectionClosedException("Connection closed without indication.");
                this._replyLines.add(string3);
            } while (!this.isStrictMultilineParsing() ? this.__lenientCheck(string3) : this.__strictCheck(string3, string2));
        }
        if (bl) {
            this.fireReplyReceived(this._replyCode, this.getReplyString());
        }
        if (this._replyCode == 421) throw new FTPConnectionClosedException("FTP response 421 received.  Server closed connection.");
    }

    private boolean __lenientCheck(String string2) {
        int n = string2.length();
        boolean bl = false;
        if (n <= 3) return true;
        if (string2.charAt(3) == '-') return true;
        if (Character.isDigit(string2.charAt(0))) return bl;
        return true;
    }

    private void __send(String string2) throws IOException, FTPConnectionClosedException, SocketException {
        try {
            this._controlOutput_.write(string2);
            this._controlOutput_.flush();
            return;
        }
        catch (SocketException socketException) {
            if (this.isConnected()) throw socketException;
            throw new FTPConnectionClosedException("Connection unexpectedly closed.");
        }
    }

    private boolean __strictCheck(String string2, String string3) {
        if (!string2.startsWith(string3)) return true;
        if (string2.charAt(3) != ' ') return true;
        return false;
    }

    protected void __getReplyNoReport() throws IOException {
        this.__getReply(false);
    }

    protected void __noop() throws IOException {
        this.__send(this.__buildMessage(FTPCmd.NOOP.getCommand(), null));
        this.__getReplyNoReport();
    }

    @Override
    protected void _connectAction_() throws IOException {
        this._connectAction_(null);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    protected void _connectAction_(Reader object) throws IOException {
        Throwable throwable2222;
        int n;
        block5 : {
            super._connectAction_();
            this._controlInput_ = object == null ? new CRLFLineReader(new InputStreamReader(this._input_, this.getControlEncoding())) : new CRLFLineReader((Reader)object);
            this._controlOutput_ = new BufferedWriter(new OutputStreamWriter(this._output_, this.getControlEncoding()));
            if (this.connectTimeout <= 0) {
                this.__getReply();
                if (!FTPReply.isPositivePreliminary(this._replyCode)) return;
                this.__getReply();
                return;
            }
            n = this._socket_.getSoTimeout();
            this._socket_.setSoTimeout(this.connectTimeout);
            this.__getReply();
            if (!FTPReply.isPositivePreliminary(this._replyCode)) break block5;
            this.__getReply();
        }
        this._socket_.setSoTimeout(n);
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (SocketTimeoutException socketTimeoutException) {}
            {
                object = new IOException("Timed out waiting for initial connect reply");
                ((Throwable)object).initCause(socketTimeoutException);
                throw object;
            }
        }
        this._socket_.setSoTimeout(n);
        throw throwable2222;
    }

    public int abor() throws IOException {
        return this.sendCommand(FTPCmd.ABOR);
    }

    public int acct(String string2) throws IOException {
        return this.sendCommand(FTPCmd.ACCT, string2);
    }

    public int allo(int n) throws IOException {
        return this.sendCommand(FTPCmd.ALLO, Integer.toString(n));
    }

    public int allo(int n, int n2) throws IOException {
        FTPCmd fTPCmd = FTPCmd.ALLO;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(n));
        stringBuilder.append(" R ");
        stringBuilder.append(Integer.toString(n2));
        return this.sendCommand(fTPCmd, stringBuilder.toString());
    }

    public int appe(String string2) throws IOException {
        return this.sendCommand(FTPCmd.APPE, string2);
    }

    public int cdup() throws IOException {
        return this.sendCommand(FTPCmd.CDUP);
    }

    public int cwd(String string2) throws IOException {
        return this.sendCommand(FTPCmd.CWD, string2);
    }

    public int dele(String string2) throws IOException {
        return this.sendCommand(FTPCmd.DELE, string2);
    }

    @Override
    public void disconnect() throws IOException {
        super.disconnect();
        this._controlInput_ = null;
        this._controlOutput_ = null;
        this._newReplyString = false;
        this._replyString = null;
    }

    public int eprt(InetAddress inetAddress, int n) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = inetAddress.getHostAddress();
        int n2 = string2.indexOf("%");
        String string3 = string2;
        if (n2 > 0) {
            string3 = string2.substring(0, n2);
        }
        stringBuilder.append("|");
        if (inetAddress instanceof Inet4Address) {
            stringBuilder.append("1");
        } else if (inetAddress instanceof Inet6Address) {
            stringBuilder.append("2");
        }
        stringBuilder.append("|");
        stringBuilder.append(string3);
        stringBuilder.append("|");
        stringBuilder.append(n);
        stringBuilder.append("|");
        return this.sendCommand(FTPCmd.EPRT, stringBuilder.toString());
    }

    public int epsv() throws IOException {
        return this.sendCommand(FTPCmd.EPSV);
    }

    public int feat() throws IOException {
        return this.sendCommand(FTPCmd.FEAT);
    }

    @Override
    protected ProtocolCommandSupport getCommandSupport() {
        return this._commandSupport_;
    }

    public String getControlEncoding() {
        return this._controlEncoding;
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
        StringBuilder stringBuilder = new StringBuilder(256);
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

    public int help() throws IOException {
        return this.sendCommand(FTPCmd.HELP);
    }

    public int help(String string2) throws IOException {
        return this.sendCommand(FTPCmd.HELP, string2);
    }

    public boolean isStrictMultilineParsing() {
        return this.strictMultilineParsing;
    }

    public int list() throws IOException {
        return this.sendCommand(FTPCmd.LIST);
    }

    public int list(String string2) throws IOException {
        return this.sendCommand(FTPCmd.LIST, string2);
    }

    public int mdtm(String string2) throws IOException {
        return this.sendCommand(FTPCmd.MDTM, string2);
    }

    public int mfmt(String string2, String string3) throws IOException {
        FTPCmd fTPCmd = FTPCmd.MFMT;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append(" ");
        stringBuilder.append(string2);
        return this.sendCommand(fTPCmd, stringBuilder.toString());
    }

    public int mkd(String string2) throws IOException {
        return this.sendCommand(FTPCmd.MKD, string2);
    }

    public int mlsd() throws IOException {
        return this.sendCommand(FTPCmd.MLSD);
    }

    public int mlsd(String string2) throws IOException {
        return this.sendCommand(FTPCmd.MLSD, string2);
    }

    public int mlst() throws IOException {
        return this.sendCommand(FTPCmd.MLST);
    }

    public int mlst(String string2) throws IOException {
        return this.sendCommand(FTPCmd.MLST, string2);
    }

    public int mode(int n) throws IOException {
        return this.sendCommand(FTPCmd.MODE, __modes.substring(n, n + 1));
    }

    public int nlst() throws IOException {
        return this.sendCommand(FTPCmd.NLST);
    }

    public int nlst(String string2) throws IOException {
        return this.sendCommand(FTPCmd.NLST, string2);
    }

    public int noop() throws IOException {
        return this.sendCommand(FTPCmd.NOOP);
    }

    public int pass(String string2) throws IOException {
        return this.sendCommand(FTPCmd.PASS, string2);
    }

    public int pasv() throws IOException {
        return this.sendCommand(FTPCmd.PASV);
    }

    public int port(InetAddress inetAddress, int n) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(24);
        stringBuilder.append(inetAddress.getHostAddress().replace('.', ','));
        stringBuilder.append(',');
        stringBuilder.append(n >>> 8);
        stringBuilder.append(',');
        stringBuilder.append(n & 255);
        return this.sendCommand(FTPCmd.PORT, stringBuilder.toString());
    }

    public int pwd() throws IOException {
        return this.sendCommand(FTPCmd.PWD);
    }

    public int quit() throws IOException {
        return this.sendCommand(FTPCmd.QUIT);
    }

    public int rein() throws IOException {
        return this.sendCommand(FTPCmd.REIN);
    }

    public int rest(String string2) throws IOException {
        return this.sendCommand(FTPCmd.REST, string2);
    }

    public int retr(String string2) throws IOException {
        return this.sendCommand(FTPCmd.RETR, string2);
    }

    public int rmd(String string2) throws IOException {
        return this.sendCommand(FTPCmd.RMD, string2);
    }

    public int rnfr(String string2) throws IOException {
        return this.sendCommand(FTPCmd.RNFR, string2);
    }

    public int rnto(String string2) throws IOException {
        return this.sendCommand(FTPCmd.RNTO, string2);
    }

    public int sendCommand(int n) throws IOException {
        return this.sendCommand(n, null);
    }

    @Deprecated
    public int sendCommand(int n, String string2) throws IOException {
        return this.sendCommand(FTPCommand.getCommand(n), string2);
    }

    public int sendCommand(String string2) throws IOException {
        return this.sendCommand(string2, null);
    }

    public int sendCommand(String string2, String string3) throws IOException {
        if (this._controlOutput_ == null) throw new IOException("Connection is not open");
        string3 = this.__buildMessage(string2, string3);
        this.__send(string3);
        this.fireCommandSent(string2, string3);
        this.__getReply();
        return this._replyCode;
    }

    public int sendCommand(FTPCmd fTPCmd) throws IOException {
        return this.sendCommand(fTPCmd, null);
    }

    public int sendCommand(FTPCmd fTPCmd, String string2) throws IOException {
        return this.sendCommand(fTPCmd.getCommand(), string2);
    }

    public void setControlEncoding(String string2) {
        this._controlEncoding = string2;
    }

    public void setStrictMultilineParsing(boolean bl) {
        this.strictMultilineParsing = bl;
    }

    public int site(String string2) throws IOException {
        return this.sendCommand(FTPCmd.SITE, string2);
    }

    public int smnt(String string2) throws IOException {
        return this.sendCommand(FTPCmd.SMNT, string2);
    }

    public int stat() throws IOException {
        return this.sendCommand(FTPCmd.STAT);
    }

    public int stat(String string2) throws IOException {
        return this.sendCommand(FTPCmd.STAT, string2);
    }

    public int stor(String string2) throws IOException {
        return this.sendCommand(FTPCmd.STOR, string2);
    }

    public int stou() throws IOException {
        return this.sendCommand(FTPCmd.STOU);
    }

    public int stou(String string2) throws IOException {
        return this.sendCommand(FTPCmd.STOU, string2);
    }

    public int stru(int n) throws IOException {
        return this.sendCommand(FTPCmd.STRU, __modes.substring(n, n + 1));
    }

    public int syst() throws IOException {
        return this.sendCommand(FTPCmd.SYST);
    }

    public int type(int n) throws IOException {
        return this.sendCommand(FTPCmd.TYPE, __modes.substring(n, n + 1));
    }

    public int type(int n, int n2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(__modes.charAt(n));
        stringBuilder.append(' ');
        if (n == 3) {
            stringBuilder.append(n2);
            return this.sendCommand(FTPCmd.TYPE, stringBuilder.toString());
        }
        stringBuilder.append(__modes.charAt(n2));
        return this.sendCommand(FTPCmd.TYPE, stringBuilder.toString());
    }

    public int user(String string2) throws IOException {
        return this.sendCommand(FTPCmd.USER, string2);
    }
}

