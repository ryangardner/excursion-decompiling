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
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.apache.commons.net.io.CRLFLineReader;
import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.util.SSLContextUtils;
import org.apache.commons.net.util.SSLSocketUtils;

public class SMTPSClient
extends SMTPClient {
    private static final String DEFAULT_PROTOCOL = "TLS";
    private SSLContext context = null;
    private HostnameVerifier hostnameVerifier = null;
    private final boolean isImplicit;
    private KeyManager keyManager = null;
    private final String protocol;
    private String[] protocols = null;
    private String[] suites = null;
    private boolean tlsEndpointChecking;
    private TrustManager trustManager = null;

    public SMTPSClient() {
        this(DEFAULT_PROTOCOL, false);
    }

    public SMTPSClient(String string2) {
        this(string2, false);
    }

    public SMTPSClient(String string2, boolean bl) {
        this.protocol = string2;
        this.isImplicit = bl;
    }

    public SMTPSClient(String string2, boolean bl, String string3) {
        super(string3);
        this.protocol = string2;
        this.isImplicit = bl;
    }

    public SMTPSClient(SSLContext sSLContext) {
        this(false, sSLContext);
    }

    public SMTPSClient(boolean bl) {
        this(DEFAULT_PROTOCOL, bl);
    }

    public SMTPSClient(boolean bl, SSLContext sSLContext) {
        this.isImplicit = bl;
        this.context = sSLContext;
        this.protocol = DEFAULT_PROTOCOL;
    }

    private void initSSLContext() throws IOException {
        if (this.context != null) return;
        this.context = SSLContextUtils.createSSLContext(this.protocol, this.getKeyManager(), this.getTrustManager());
    }

    private void performSSLNegotiation() throws IOException {
        Object object;
        this.initSSLContext();
        Object object2 = this.context.getSocketFactory();
        String string2 = this._hostname_ != null ? this._hostname_ : this.getRemoteAddress().getHostAddress();
        int n = this.getRemotePort();
        object2 = (SSLSocket)((SSLSocketFactory)object2).createSocket(this._socket_, string2, n, true);
        ((SSLSocket)object2).setEnableSessionCreation(true);
        ((SSLSocket)object2).setUseClientMode(true);
        if (this.tlsEndpointChecking) {
            SSLSocketUtils.enableEndpointNameVerification((SSLSocket)object2);
        }
        if ((object = this.protocols) != null) {
            ((SSLSocket)object2).setEnabledProtocols((String[])object);
        }
        if ((object = this.suites) != null) {
            ((SSLSocket)object2).setEnabledCipherSuites((String[])object);
        }
        ((SSLSocket)object2).startHandshake();
        this._socket_ = object2;
        this._input_ = ((Socket)object2).getInputStream();
        this._output_ = ((Socket)object2).getOutputStream();
        this._reader = new CRLFLineReader(new InputStreamReader(this._input_, this.encoding));
        this._writer = new BufferedWriter(new OutputStreamWriter(this._output_, this.encoding));
        object = this.hostnameVerifier;
        if (object == null) return;
        if (!object.verify(string2, ((SSLSocket)object2).getSession())) throw new SSLHandshakeException("Hostname doesn't match certificate");
    }

    @Override
    protected void _connectAction_() throws IOException {
        if (this.isImplicit) {
            this.performSSLNegotiation();
        }
        super._connectAction_();
    }

    public boolean execTLS() throws IOException {
        if (!SMTPReply.isPositiveCompletion(this.sendCommand("STARTTLS"))) {
            return false;
        }
        this.performSSLNegotiation();
        return true;
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

    public KeyManager getKeyManager() {
        return this.keyManager;
    }

    public TrustManager getTrustManager() {
        return this.trustManager;
    }

    public boolean isEndpointCheckingEnabled() {
        return this.tlsEndpointChecking;
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

    public void setEndpointCheckingEnabled(boolean bl) {
        this.tlsEndpointChecking = bl;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public void setKeyManager(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public void setTrustManager(TrustManager trustManager) {
        this.trustManager = trustManager;
    }
}

