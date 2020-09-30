package org.apache.commons.net.imap;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.apache.commons.net.io.CRLFLineReader;
import org.apache.commons.net.util.SSLContextUtils;
import org.apache.commons.net.util.SSLSocketUtils;

public class IMAPSClient extends IMAPClient {
   public static final int DEFAULT_IMAPS_PORT = 993;
   public static final String DEFAULT_PROTOCOL = "TLS";
   private SSLContext context;
   private HostnameVerifier hostnameVerifier;
   private final boolean isImplicit;
   private KeyManager keyManager;
   private final String protocol;
   private String[] protocols;
   private String[] suites;
   private boolean tlsEndpointChecking;
   private TrustManager trustManager;

   public IMAPSClient() {
      this("TLS", false);
   }

   public IMAPSClient(String var1) {
      this(var1, false);
   }

   public IMAPSClient(String var1, boolean var2) {
      this(var1, var2, (SSLContext)null);
   }

   public IMAPSClient(String var1, boolean var2, SSLContext var3) {
      this.context = null;
      this.suites = null;
      this.protocols = null;
      this.trustManager = null;
      this.keyManager = null;
      this.hostnameVerifier = null;
      this.setDefaultPort(993);
      this.protocol = var1;
      this.isImplicit = var2;
      this.context = var3;
   }

   public IMAPSClient(SSLContext var1) {
      this(false, var1);
   }

   public IMAPSClient(boolean var1) {
      this("TLS", var1);
   }

   public IMAPSClient(boolean var1, SSLContext var2) {
      this("TLS", var1, var2);
   }

   private KeyManager getKeyManager() {
      return this.keyManager;
   }

   private void initSSLContext() throws IOException {
      if (this.context == null) {
         this.context = SSLContextUtils.createSSLContext(this.protocol, this.getKeyManager(), this.getTrustManager());
      }

   }

   private void performSSLNegotiation() throws IOException {
      this.initSSLContext();
      SSLSocketFactory var1 = this.context.getSocketFactory();
      String var2;
      if (this._hostname_ != null) {
         var2 = this._hostname_;
      } else {
         var2 = this.getRemoteAddress().getHostAddress();
      }

      int var3 = this.getRemotePort();
      SSLSocket var5 = (SSLSocket)var1.createSocket(this._socket_, var2, var3, true);
      var5.setEnableSessionCreation(true);
      var5.setUseClientMode(true);
      if (this.tlsEndpointChecking) {
         SSLSocketUtils.enableEndpointNameVerification(var5);
      }

      String[] var4 = this.protocols;
      if (var4 != null) {
         var5.setEnabledProtocols(var4);
      }

      var4 = this.suites;
      if (var4 != null) {
         var5.setEnabledCipherSuites(var4);
      }

      var5.startHandshake();
      this._socket_ = var5;
      this._input_ = var5.getInputStream();
      this._output_ = var5.getOutputStream();
      this._reader = new CRLFLineReader(new InputStreamReader(this._input_, "ISO-8859-1"));
      this.__writer = new BufferedWriter(new OutputStreamWriter(this._output_, "ISO-8859-1"));
      HostnameVerifier var6 = this.hostnameVerifier;
      if (var6 != null && !var6.verify(var2, var5.getSession())) {
         throw new SSLHandshakeException("Hostname doesn't match certificate");
      }
   }

   protected void _connectAction_() throws IOException {
      if (this.isImplicit) {
         this.performSSLNegotiation();
      }

      super._connectAction_();
   }

   public boolean execTLS() throws SSLException, IOException {
      if (this.sendCommand(IMAPCommand.getCommand(IMAPCommand.STARTTLS)) != 0) {
         return false;
      } else {
         this.performSSLNegotiation();
         return true;
      }
   }

   public String[] getEnabledCipherSuites() {
      return this._socket_ instanceof SSLSocket ? ((SSLSocket)this._socket_).getEnabledCipherSuites() : null;
   }

   public String[] getEnabledProtocols() {
      return this._socket_ instanceof SSLSocket ? ((SSLSocket)this._socket_).getEnabledProtocols() : null;
   }

   public HostnameVerifier getHostnameVerifier() {
      return this.hostnameVerifier;
   }

   public TrustManager getTrustManager() {
      return this.trustManager;
   }

   public boolean isEndpointCheckingEnabled() {
      return this.tlsEndpointChecking;
   }

   public void setEnabledCipherSuites(String[] var1) {
      String[] var2 = new String[var1.length];
      this.suites = var2;
      System.arraycopy(var1, 0, var2, 0, var1.length);
   }

   public void setEnabledProtocols(String[] var1) {
      String[] var2 = new String[var1.length];
      this.protocols = var2;
      System.arraycopy(var1, 0, var2, 0, var1.length);
   }

   public void setEndpointCheckingEnabled(boolean var1) {
      this.tlsEndpointChecking = var1;
   }

   public void setHostnameVerifier(HostnameVerifier var1) {
      this.hostnameVerifier = var1;
   }

   public void setKeyManager(KeyManager var1) {
      this.keyManager = var1;
   }

   public void setTrustManager(TrustManager var1) {
      this.trustManager = var1;
   }
}
