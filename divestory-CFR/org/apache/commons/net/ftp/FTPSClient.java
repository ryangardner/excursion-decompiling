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
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPCommand;
import org.apache.commons.net.ftp.FTPSServerSocketFactory;
import org.apache.commons.net.ftp.FTPSSocketFactory;
import org.apache.commons.net.util.Base64;
import org.apache.commons.net.util.SSLContextUtils;
import org.apache.commons.net.util.SSLSocketUtils;
import org.apache.commons.net.util.TrustManagerUtils;

public class FTPSClient
extends FTPClient {
    private static final String CMD_ADAT = "ADAT";
    private static final String CMD_AUTH = "AUTH";
    private static final String CMD_CCC = "CCC";
    private static final String CMD_CONF = "CONF";
    private static final String CMD_ENC = "ENC";
    private static final String CMD_MIC = "MIC";
    private static final String CMD_PBSZ = "PBSZ";
    private static final String CMD_PROT = "PROT";
    public static final int DEFAULT_FTPS_DATA_PORT = 989;
    public static final int DEFAULT_FTPS_PORT = 990;
    private static final String DEFAULT_PROT = "C";
    private static final String DEFAULT_PROTOCOL = "TLS";
    @Deprecated
    public static String KEYSTORE_ALGORITHM;
    private static final String[] PROT_COMMAND_VALUE;
    @Deprecated
    public static String PROVIDER;
    @Deprecated
    public static String STORE_TYPE;
    @Deprecated
    public static String TRUSTSTORE_ALGORITHM;
    private String auth = "TLS";
    private SSLContext context;
    private HostnameVerifier hostnameVerifier = null;
    private boolean isClientMode = true;
    private boolean isCreation = true;
    private final boolean isImplicit;
    private boolean isNeedClientAuth = false;
    private boolean isWantClientAuth = false;
    private KeyManager keyManager = null;
    private Socket plainSocket;
    private final String protocol;
    private String[] protocols = null;
    private String[] suites = null;
    private boolean tlsEndpointChecking;
    private TrustManager trustManager = TrustManagerUtils.getValidateServerCertificateTrustManager();

    static {
        PROT_COMMAND_VALUE = new String[]{DEFAULT_PROT, "E", "S", "P"};
    }

    public FTPSClient() {
        this(DEFAULT_PROTOCOL, false);
    }

    public FTPSClient(String string2) {
        this(string2, false);
    }

    public FTPSClient(String string2, boolean bl) {
        this.protocol = string2;
        this.isImplicit = bl;
        if (!bl) return;
        this.setDefaultPort(990);
    }

    public FTPSClient(SSLContext sSLContext) {
        this(false, sSLContext);
    }

    public FTPSClient(boolean bl) {
        this(DEFAULT_PROTOCOL, bl);
    }

    public FTPSClient(boolean bl, SSLContext sSLContext) {
        this(DEFAULT_PROTOCOL, bl);
        this.context = sSLContext;
    }

    private boolean checkPROTValue(String string2) {
        String[] arrstring = PROT_COMMAND_VALUE;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrstring[n2].equals(string2)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private String extractPrefixedData(String string2, String string3) {
        int n = string3.indexOf(string2);
        if (n != -1) return string3.substring(n + string2.length()).trim();
        return null;
    }

    private KeyManager getKeyManager() {
        return this.keyManager;
    }

    private void initSslContext() throws IOException {
        if (this.context != null) return;
        this.context = SSLContextUtils.createSSLContext(this.protocol, this.getKeyManager(), this.getTrustManager());
    }

    @Override
    protected void _connectAction_() throws IOException {
        if (this.isImplicit) {
            this.sslNegotiation();
        }
        super._connectAction_();
        if (this.isImplicit) return;
        this.execAUTH();
        this.sslNegotiation();
    }

    @Deprecated
    @Override
    protected Socket _openDataConnection_(int n, String string2) throws IOException {
        return this._openDataConnection_(FTPCommand.getCommand(n), string2);
    }

    @Override
    protected Socket _openDataConnection_(String object, String object2) throws IOException {
        String[] arrstring;
        object2 = super._openDataConnection_((String)object, (String)object2);
        this._prepareDataSocket_((Socket)object2);
        if (!(object2 instanceof SSLSocket)) return object2;
        object = (SSLSocket)object2;
        ((SSLSocket)object).setUseClientMode(this.isClientMode);
        ((SSLSocket)object).setEnableSessionCreation(this.isCreation);
        if (!this.isClientMode) {
            ((SSLSocket)object).setNeedClientAuth(this.isNeedClientAuth);
            ((SSLSocket)object).setWantClientAuth(this.isWantClientAuth);
        }
        if ((arrstring = this.suites) != null) {
            ((SSLSocket)object).setEnabledCipherSuites(arrstring);
        }
        if ((arrstring = this.protocols) != null) {
            ((SSLSocket)object).setEnabledProtocols(arrstring);
        }
        ((SSLSocket)object).startHandshake();
        return object2;
    }

    protected void _prepareDataSocket_(Socket socket) throws IOException {
    }

    @Override
    public void disconnect() throws IOException {
        super.disconnect();
        this.setSocketFactory(null);
        this.setServerSocketFactory(null);
    }

    public int execADAT(byte[] arrby) throws IOException {
        if (arrby == null) return this.sendCommand(CMD_ADAT);
        return this.sendCommand(CMD_ADAT, Base64.encodeBase64StringUnChunked(arrby));
    }

    public int execAUTH(String string2) throws IOException {
        return this.sendCommand(CMD_AUTH, string2);
    }

    protected void execAUTH() throws SSLException, IOException {
        int n = this.sendCommand(CMD_AUTH, this.auth);
        if (334 == n) {
            return;
        }
        if (234 != n) throw new SSLException(this.getReplyString());
    }

    public int execCCC() throws IOException {
        return this.sendCommand(CMD_CCC);
    }

    public int execCONF(byte[] arrby) throws IOException {
        if (arrby == null) return this.sendCommand(CMD_CONF, "");
        return this.sendCommand(CMD_CONF, Base64.encodeBase64StringUnChunked(arrby));
    }

    public int execENC(byte[] arrby) throws IOException {
        if (arrby == null) return this.sendCommand(CMD_ENC, "");
        return this.sendCommand(CMD_ENC, Base64.encodeBase64StringUnChunked(arrby));
    }

    public int execMIC(byte[] arrby) throws IOException {
        if (arrby == null) return this.sendCommand(CMD_MIC, "");
        return this.sendCommand(CMD_MIC, Base64.encodeBase64StringUnChunked(arrby));
    }

    public void execPBSZ(long l) throws SSLException, IOException {
        if (l < 0L) throw new IllegalArgumentException();
        if (0xFFFFFFFFL < l) throw new IllegalArgumentException();
        if (200 != this.sendCommand(CMD_PBSZ, String.valueOf(l))) throw new SSLException(this.getReplyString());
    }

    public void execPROT(String string2) throws SSLException, IOException {
        String string3 = string2;
        if (string2 == null) {
            string3 = DEFAULT_PROT;
        }
        if (!this.checkPROTValue(string3)) throw new IllegalArgumentException();
        if (200 != this.sendCommand(CMD_PROT, string3)) throw new SSLException(this.getReplyString());
        if (DEFAULT_PROT.equals(string3)) {
            this.setSocketFactory(null);
            this.setServerSocketFactory(null);
            return;
        }
        this.setSocketFactory(new FTPSSocketFactory(this.context));
        this.setServerSocketFactory(new FTPSServerSocketFactory(this.context));
        this.initSslContext();
    }

    public String getAuthValue() {
        return this.auth;
    }

    public boolean getEnableSessionCreation() {
        if (!(this._socket_ instanceof SSLSocket)) return false;
        return ((SSLSocket)this._socket_).getEnableSessionCreation();
    }

    public String[] getEnabledCipherSuites() {
        if (!(this._socket_ instanceof SSLSocket)) return null;
        return ((SSLSocket)this._socket_).getEnabledCipherSuites();
    }

    public String[] getEnabledProtocols() {
        if (!(this._socket_ instanceof SSLSocket)) return null;
        return ((SSLSocket)this._socket_).getEnabledProtocols();
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public boolean getNeedClientAuth() {
        if (!(this._socket_ instanceof SSLSocket)) return false;
        return ((SSLSocket)this._socket_).getNeedClientAuth();
    }

    public TrustManager getTrustManager() {
        return this.trustManager;
    }

    public boolean getUseClientMode() {
        if (!(this._socket_ instanceof SSLSocket)) return false;
        return ((SSLSocket)this._socket_).getUseClientMode();
    }

    public boolean getWantClientAuth() {
        if (!(this._socket_ instanceof SSLSocket)) return false;
        return ((SSLSocket)this._socket_).getWantClientAuth();
    }

    public boolean isEndpointCheckingEnabled() {
        return this.tlsEndpointChecking;
    }

    public byte[] parseADATReply(String string2) {
        if (string2 != null) return Base64.decodeBase64(this.extractPrefixedData("ADAT=", string2));
        return null;
    }

    public long parsePBSZ(long l) throws SSLException, IOException {
        this.execPBSZ(l);
        String string2 = this.extractPrefixedData("PBSZ=", this.getReplyString());
        long l2 = l;
        if (string2 == null) return l2;
        long l3 = Long.parseLong(string2);
        l2 = l;
        if (l3 >= l) return l2;
        return l3;
    }

    @Override
    public int sendCommand(String string2, String string3) throws IOException {
        int n = super.sendCommand(string2, string3);
        if (!CMD_CCC.equals(string2)) return n;
        if (200 != n) throw new SSLException(this.getReplyString());
        this._socket_.close();
        this._socket_ = this.plainSocket;
        this._controlInput_ = new BufferedReader(new InputStreamReader(this._socket_.getInputStream(), this.getControlEncoding()));
        this._controlOutput_ = new BufferedWriter(new OutputStreamWriter(this._socket_.getOutputStream(), this.getControlEncoding()));
        return n;
    }

    public void setAuthValue(String string2) {
        this.auth = string2;
    }

    public void setEnabledCipherSuites(String[] arrstring) {
        String[] arrstring2 = new String[arrstring.length];
        this.suites = arrstring2;
        System.arraycopy(arrstring, 0, arrstring2, 0, arrstring.length);
    }

    public void setEnabledProtocols(String[] arrstring) {
        String[] arrstring2 = new String[arrstring.length];
        this.protocols = arrstring2;
        System.arraycopy(arrstring, 0, arrstring2, 0, arrstring.length);
    }

    public void setEnabledSessionCreation(boolean bl) {
        this.isCreation = bl;
    }

    public void setEndpointCheckingEnabled(boolean bl) {
        this.tlsEndpointChecking = bl;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public void setKeyManager(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public void setNeedClientAuth(boolean bl) {
        this.isNeedClientAuth = bl;
    }

    public void setTrustManager(TrustManager trustManager) {
        this.trustManager = trustManager;
    }

    public void setUseClientMode(boolean bl) {
        this.isClientMode = bl;
    }

    public void setWantClientAuth(boolean bl) {
        this.isWantClientAuth = bl;
    }

    protected void sslNegotiation() throws IOException {
        Object object;
        this.plainSocket = this._socket_;
        this.initSslContext();
        Object object2 = this.context.getSocketFactory();
        String string2 = this._hostname_ != null ? this._hostname_ : this.getRemoteAddress().getHostAddress();
        int n = this._socket_.getPort();
        object2 = (SSLSocket)((SSLSocketFactory)object2).createSocket(this._socket_, string2, n, false);
        ((SSLSocket)object2).setEnableSessionCreation(this.isCreation);
        ((SSLSocket)object2).setUseClientMode(this.isClientMode);
        if (this.isClientMode) {
            if (this.tlsEndpointChecking) {
                SSLSocketUtils.enableEndpointNameVerification((SSLSocket)object2);
            }
        } else {
            ((SSLSocket)object2).setNeedClientAuth(this.isNeedClientAuth);
            ((SSLSocket)object2).setWantClientAuth(this.isWantClientAuth);
        }
        if ((object = this.protocols) != null) {
            ((SSLSocket)object2).setEnabledProtocols((String[])object);
        }
        if ((object = this.suites) != null) {
            ((SSLSocket)object2).setEnabledCipherSuites((String[])object);
        }
        ((SSLSocket)object2).startHandshake();
        this._socket_ = object2;
        this._controlInput_ = new BufferedReader(new InputStreamReader(((Socket)object2).getInputStream(), this.getControlEncoding()));
        this._controlOutput_ = new BufferedWriter(new OutputStreamWriter(((Socket)object2).getOutputStream(), this.getControlEncoding()));
        if (!this.isClientMode) return;
        object = this.hostnameVerifier;
        if (object == null) return;
        if (!object.verify(string2, ((SSLSocket)object2).getSession())) throw new SSLHandshakeException("Hostname doesn't match certificate");
    }
}

