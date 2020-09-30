package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.apache.commons.net.util.Base64;
import org.apache.commons.net.util.SSLContextUtils;
import org.apache.commons.net.util.SSLSocketUtils;
import org.apache.commons.net.util.TrustManagerUtils;

public class FTPSClient extends FTPClient {
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
   private static final String[] PROT_COMMAND_VALUE = new String[]{"C", "E", "S", "P"};
   @Deprecated
   public static String PROVIDER;
   @Deprecated
   public static String STORE_TYPE;
   @Deprecated
   public static String TRUSTSTORE_ALGORITHM;
   private String auth;
   private SSLContext context;
   private HostnameVerifier hostnameVerifier;
   private boolean isClientMode;
   private boolean isCreation;
   private final boolean isImplicit;
   private boolean isNeedClientAuth;
   private boolean isWantClientAuth;
   private KeyManager keyManager;
   private Socket plainSocket;
   private final String protocol;
   private String[] protocols;
   private String[] suites;
   private boolean tlsEndpointChecking;
   private TrustManager trustManager;

   public FTPSClient() {
      this("TLS", false);
   }

   public FTPSClient(String var1) {
      this(var1, false);
   }

   public FTPSClient(String var1, boolean var2) {
      this.auth = "TLS";
      this.isCreation = true;
      this.isClientMode = true;
      this.isNeedClientAuth = false;
      this.isWantClientAuth = false;
      this.suites = null;
      this.protocols = null;
      this.trustManager = TrustManagerUtils.getValidateServerCertificateTrustManager();
      this.keyManager = null;
      this.hostnameVerifier = null;
      this.protocol = var1;
      this.isImplicit = var2;
      if (var2) {
         this.setDefaultPort(990);
      }

   }

   public FTPSClient(SSLContext var1) {
      this(false, var1);
   }

   public FTPSClient(boolean var1) {
      this("TLS", var1);
   }

   public FTPSClient(boolean var1, SSLContext var2) {
      this("TLS", var1);
      this.context = var2;
   }

   private boolean checkPROTValue(String var1) {
      String[] var2 = PROT_COMMAND_VALUE;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         if (var2[var4].equals(var1)) {
            return true;
         }
      }

      return false;
   }

   private String extractPrefixedData(String var1, String var2) {
      int var3 = var2.indexOf(var1);
      return var3 == -1 ? null : var2.substring(var3 + var1.length()).trim();
   }

   private KeyManager getKeyManager() {
      return this.keyManager;
   }

   private void initSslContext() throws IOException {
      if (this.context == null) {
         this.context = SSLContextUtils.createSSLContext(this.protocol, this.getKeyManager(), this.getTrustManager());
      }

   }

   protected void _connectAction_() throws IOException {
      if (this.isImplicit) {
         this.sslNegotiation();
      }

      super._connectAction_();
      if (!this.isImplicit) {
         this.execAUTH();
         this.sslNegotiation();
      }

   }

   @Deprecated
   protected Socket _openDataConnection_(int var1, String var2) throws IOException {
      return this._openDataConnection_(FTPCommand.getCommand(var1), var2);
   }

   protected Socket _openDataConnection_(String var1, String var2) throws IOException {
      Socket var5 = super._openDataConnection_(var1, var2);
      this._prepareDataSocket_(var5);
      if (var5 instanceof SSLSocket) {
         SSLSocket var4 = (SSLSocket)var5;
         var4.setUseClientMode(this.isClientMode);
         var4.setEnableSessionCreation(this.isCreation);
         if (!this.isClientMode) {
            var4.setNeedClientAuth(this.isNeedClientAuth);
            var4.setWantClientAuth(this.isWantClientAuth);
         }

         String[] var3 = this.suites;
         if (var3 != null) {
            var4.setEnabledCipherSuites(var3);
         }

         var3 = this.protocols;
         if (var3 != null) {
            var4.setEnabledProtocols(var3);
         }

         var4.startHandshake();
      }

      return var5;
   }

   protected void _prepareDataSocket_(Socket var1) throws IOException {
   }

   public void disconnect() throws IOException {
      super.disconnect();
      this.setSocketFactory((SocketFactory)null);
      this.setServerSocketFactory((ServerSocketFactory)null);
   }

   public int execADAT(byte[] var1) throws IOException {
      return var1 != null ? this.sendCommand("ADAT", Base64.encodeBase64StringUnChunked(var1)) : this.sendCommand("ADAT");
   }

   public int execAUTH(String var1) throws IOException {
      return this.sendCommand("AUTH", var1);
   }

   protected void execAUTH() throws SSLException, IOException {
      int var1 = this.sendCommand("AUTH", this.auth);
      if (334 != var1 && 234 != var1) {
         throw new SSLException(this.getReplyString());
      }
   }

   public int execCCC() throws IOException {
      return this.sendCommand("CCC");
   }

   public int execCONF(byte[] var1) throws IOException {
      return var1 != null ? this.sendCommand("CONF", Base64.encodeBase64StringUnChunked(var1)) : this.sendCommand("CONF", "");
   }

   public int execENC(byte[] var1) throws IOException {
      return var1 != null ? this.sendCommand("ENC", Base64.encodeBase64StringUnChunked(var1)) : this.sendCommand("ENC", "");
   }

   public int execMIC(byte[] var1) throws IOException {
      return var1 != null ? this.sendCommand("MIC", Base64.encodeBase64StringUnChunked(var1)) : this.sendCommand("MIC", "");
   }

   public void execPBSZ(long var1) throws SSLException, IOException {
      if (var1 >= 0L && 4294967295L >= var1) {
         if (200 != this.sendCommand("PBSZ", String.valueOf(var1))) {
            throw new SSLException(this.getReplyString());
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public void execPROT(String var1) throws SSLException, IOException {
      String var2 = var1;
      if (var1 == null) {
         var2 = "C";
      }

      if (this.checkPROTValue(var2)) {
         if (200 == this.sendCommand("PROT", var2)) {
            if ("C".equals(var2)) {
               this.setSocketFactory((SocketFactory)null);
               this.setServerSocketFactory((ServerSocketFactory)null);
            } else {
               this.setSocketFactory(new FTPSSocketFactory(this.context));
               this.setServerSocketFactory(new FTPSServerSocketFactory(this.context));
               this.initSslContext();
            }

         } else {
            throw new SSLException(this.getReplyString());
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public String getAuthValue() {
      return this.auth;
   }

   public boolean getEnableSessionCreation() {
      return this._socket_ instanceof SSLSocket ? ((SSLSocket)this._socket_).getEnableSessionCreation() : false;
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

   public boolean getNeedClientAuth() {
      return this._socket_ instanceof SSLSocket ? ((SSLSocket)this._socket_).getNeedClientAuth() : false;
   }

   public TrustManager getTrustManager() {
      return this.trustManager;
   }

   public boolean getUseClientMode() {
      return this._socket_ instanceof SSLSocket ? ((SSLSocket)this._socket_).getUseClientMode() : false;
   }

   public boolean getWantClientAuth() {
      return this._socket_ instanceof SSLSocket ? ((SSLSocket)this._socket_).getWantClientAuth() : false;
   }

   public boolean isEndpointCheckingEnabled() {
      return this.tlsEndpointChecking;
   }

   public byte[] parseADATReply(String var1) {
      return var1 == null ? null : Base64.decodeBase64(this.extractPrefixedData("ADAT=", var1));
   }

   public long parsePBSZ(long var1) throws SSLException, IOException {
      this.execPBSZ(var1);
      String var3 = this.extractPrefixedData("PBSZ=", this.getReplyString());
      long var4 = var1;
      if (var3 != null) {
         long var6 = Long.parseLong(var3);
         var4 = var1;
         if (var6 < var1) {
            var4 = var6;
         }
      }

      return var4;
   }

   public int sendCommand(String var1, String var2) throws IOException {
      int var3 = super.sendCommand(var1, var2);
      if ("CCC".equals(var1)) {
         if (200 != var3) {
            throw new SSLException(this.getReplyString());
         }

         this._socket_.close();
         this._socket_ = this.plainSocket;
         this._controlInput_ = new BufferedReader(new InputStreamReader(this._socket_.getInputStream(), this.getControlEncoding()));
         this._controlOutput_ = new BufferedWriter(new OutputStreamWriter(this._socket_.getOutputStream(), this.getControlEncoding()));
      }

      return var3;
   }

   public void setAuthValue(String var1) {
      this.auth = var1;
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

   public void setEnabledSessionCreation(boolean var1) {
      this.isCreation = var1;
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

   public void setNeedClientAuth(boolean var1) {
      this.isNeedClientAuth = var1;
   }

   public void setTrustManager(TrustManager var1) {
      this.trustManager = var1;
   }

   public void setUseClientMode(boolean var1) {
      this.isClientMode = var1;
   }

   public void setWantClientAuth(boolean var1) {
      this.isWantClientAuth = var1;
   }

   protected void sslNegotiation() throws IOException {
      this.plainSocket = this._socket_;
      this.initSslContext();
      SSLSocketFactory var1 = this.context.getSocketFactory();
      String var2;
      if (this._hostname_ != null) {
         var2 = this._hostname_;
      } else {
         var2 = this.getRemoteAddress().getHostAddress();
      }

      int var3 = this._socket_.getPort();
      SSLSocket var5 = (SSLSocket)var1.createSocket(this._socket_, var2, var3, false);
      var5.setEnableSessionCreation(this.isCreation);
      var5.setUseClientMode(this.isClientMode);
      if (this.isClientMode) {
         if (this.tlsEndpointChecking) {
            SSLSocketUtils.enableEndpointNameVerification(var5);
         }
      } else {
         var5.setNeedClientAuth(this.isNeedClientAuth);
         var5.setWantClientAuth(this.isWantClientAuth);
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
      this._controlInput_ = new BufferedReader(new InputStreamReader(var5.getInputStream(), this.getControlEncoding()));
      this._controlOutput_ = new BufferedWriter(new OutputStreamWriter(var5.getOutputStream(), this.getControlEncoding()));
      if (this.isClientMode) {
         HostnameVerifier var6 = this.hostnameVerifier;
         if (var6 != null && !var6.verify(var2, var5.getSession())) {
            throw new SSLHandshakeException("Hostname doesn't match certificate");
         }
      }

   }
}
