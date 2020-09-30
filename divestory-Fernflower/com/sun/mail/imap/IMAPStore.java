package com.sun.mail.imap;

import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.iap.ResponseHandler;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.Namespaces;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Quota;
import javax.mail.QuotaAwareStore;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public class IMAPStore extends Store implements QuotaAwareStore, ResponseHandler {
   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   public static final int RESPONSE = 1000;
   private int appendBufferSize;
   private String authorizationID;
   private int blksize;
   private volatile boolean connected;
   private int defaultPort;
   private boolean disableAuthLogin;
   private boolean disableAuthPlain;
   private boolean enableImapEvents;
   private boolean enableSASL;
   private boolean enableStartTLS;
   private boolean forcePasswordRefresh;
   private String host;
   private boolean isSSL;
   private int minIdleTime;
   private String name;
   private Namespaces namespaces;
   private PrintStream out;
   private String password;
   private IMAPStore.ConnectionPool pool;
   private int port;
   private String proxyAuthUser;
   private String[] saslMechanisms;
   private String saslRealm;
   private int statusCacheTimeout;
   private String user;

   public IMAPStore(Session var1, URLName var2) {
      this(var1, var2, "imap", 143, false);
   }

   protected IMAPStore(Session var1, URLName var2, String var3, int var4, boolean var5) {
      super(var1, var2);
      this.name = "imap";
      this.defaultPort = 143;
      this.isSSL = false;
      this.port = -1;
      this.blksize = 16384;
      this.statusCacheTimeout = 1000;
      this.appendBufferSize = -1;
      this.minIdleTime = 10;
      this.disableAuthLogin = false;
      this.disableAuthPlain = false;
      this.enableStartTLS = false;
      this.enableSASL = false;
      this.forcePasswordRefresh = false;
      this.enableImapEvents = false;
      this.connected = false;
      this.pool = new IMAPStore.ConnectionPool();
      if (var2 != null) {
         var3 = var2.getProtocol();
      }

      this.name = var3;
      this.defaultPort = var4;
      this.isSSL = var5;
      this.pool.lastTimePruned = System.currentTimeMillis();
      this.debug = var1.getDebug();
      PrintStream var15 = var1.getDebugOut();
      this.out = var15;
      if (var15 == null) {
         this.out = System.out;
      }

      StringBuilder var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".connectionpool.debug");
      String var17 = var1.getProperty(var16.toString());
      if (var17 != null && var17.equalsIgnoreCase("true")) {
         this.pool.debug = true;
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".partialfetch");
      var17 = var1.getProperty(var16.toString());
      PrintStream var6;
      if (var17 != null && var17.equalsIgnoreCase("false")) {
         this.blksize = -1;
         if (this.debug) {
            this.out.println("DEBUG: mail.imap.partialfetch: false");
         }
      } else {
         var16 = new StringBuilder("mail.");
         var16.append(var3);
         var16.append(".fetchsize");
         var17 = var1.getProperty(var16.toString());
         if (var17 != null) {
            this.blksize = Integer.parseInt(var17);
         }

         if (this.debug) {
            var6 = this.out;
            var16 = new StringBuilder("DEBUG: mail.imap.fetchsize: ");
            var16.append(this.blksize);
            var6.println(var16.toString());
         }
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".statuscachetimeout");
      var17 = var1.getProperty(var16.toString());
      StringBuilder var18;
      if (var17 != null) {
         this.statusCacheTimeout = Integer.parseInt(var17);
         if (this.debug) {
            var15 = this.out;
            var18 = new StringBuilder("DEBUG: mail.imap.statuscachetimeout: ");
            var18.append(this.statusCacheTimeout);
            var15.println(var18.toString());
         }
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".appendbuffersize");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null) {
         this.appendBufferSize = Integer.parseInt(var17);
         if (this.debug) {
            var6 = this.out;
            var16 = new StringBuilder("DEBUG: mail.imap.appendbuffersize: ");
            var16.append(this.appendBufferSize);
            var6.println(var16.toString());
         }
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".minidletime");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null) {
         this.minIdleTime = Integer.parseInt(var17);
         if (this.debug) {
            var6 = this.out;
            var16 = new StringBuilder("DEBUG: mail.imap.minidletime: ");
            var16.append(this.minIdleTime);
            var6.println(var16.toString());
         }
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".connectionpoolsize");
      var17 = var1.getProperty(var16.toString());
      boolean var10001;
      if (var17 != null) {
         label243: {
            try {
               var4 = Integer.parseInt(var17);
            } catch (NumberFormatException var13) {
               var10001 = false;
               break label243;
            }

            if (var4 > 0) {
               try {
                  this.pool.poolSize = var4;
               } catch (NumberFormatException var12) {
                  var10001 = false;
               }
            }
         }

         if (this.pool.debug) {
            var6 = this.out;
            var16 = new StringBuilder("DEBUG: mail.imap.connectionpoolsize: ");
            var16.append(this.pool.poolSize);
            var6.println(var16.toString());
         }
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".connectionpooltimeout");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null) {
         label244: {
            try {
               var4 = Integer.parseInt(var17);
            } catch (NumberFormatException var11) {
               var10001 = false;
               break label244;
            }

            if (var4 > 0) {
               try {
                  this.pool.clientTimeoutInterval = (long)var4;
               } catch (NumberFormatException var10) {
                  var10001 = false;
               }
            }
         }

         if (this.pool.debug) {
            var15 = this.out;
            var18 = new StringBuilder("DEBUG: mail.imap.connectionpooltimeout: ");
            var18.append(this.pool.clientTimeoutInterval);
            var15.println(var18.toString());
         }
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".servertimeout");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null) {
         label245: {
            try {
               var4 = Integer.parseInt(var17);
            } catch (NumberFormatException var9) {
               var10001 = false;
               break label245;
            }

            if (var4 > 0) {
               try {
                  this.pool.serverTimeoutInterval = (long)var4;
               } catch (NumberFormatException var8) {
                  var10001 = false;
               }
            }
         }

         if (this.pool.debug) {
            var15 = this.out;
            var18 = new StringBuilder("DEBUG: mail.imap.servertimeout: ");
            var18.append(this.pool.serverTimeoutInterval);
            var15.println(var18.toString());
         }
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".separatestoreconnection");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null && var17.equalsIgnoreCase("true")) {
         if (this.pool.debug) {
            this.out.println("DEBUG: dedicate a store connection");
         }

         this.pool.separateStoreConnection = true;
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".proxyauth.user");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null) {
         this.proxyAuthUser = var17;
         if (this.debug) {
            var15 = this.out;
            var18 = new StringBuilder("DEBUG: mail.imap.proxyauth.user: ");
            var18.append(this.proxyAuthUser);
            var15.println(var18.toString());
         }
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".auth.login.disable");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null && var17.equalsIgnoreCase("true")) {
         if (this.debug) {
            this.out.println("DEBUG: disable AUTH=LOGIN");
         }

         this.disableAuthLogin = true;
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".auth.plain.disable");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null && var17.equalsIgnoreCase("true")) {
         if (this.debug) {
            this.out.println("DEBUG: disable AUTH=PLAIN");
         }

         this.disableAuthPlain = true;
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".starttls.enable");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null && var17.equalsIgnoreCase("true")) {
         if (this.debug) {
            this.out.println("DEBUG: enable STARTTLS");
         }

         this.enableStartTLS = true;
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".sasl.enable");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null && var17.equalsIgnoreCase("true")) {
         if (this.debug) {
            this.out.println("DEBUG: enable SASL");
         }

         this.enableSASL = true;
      }

      if (this.enableSASL) {
         var16 = new StringBuilder("mail.");
         var16.append(var3);
         var16.append(".sasl.mechanisms");
         String var20 = var1.getProperty(var16.toString());
         if (var20 != null && var20.length() > 0) {
            if (this.debug) {
               var15 = this.out;
               StringBuilder var7 = new StringBuilder("DEBUG: SASL mechanisms allowed: ");
               var7.append(var20);
               var15.println(var7.toString());
            }

            Vector var23 = new Vector(5);
            StringTokenizer var21 = new StringTokenizer(var20, " ,");

            while(var21.hasMoreTokens()) {
               String var19 = var21.nextToken();
               if (var19.length() > 0) {
                  var23.addElement(var19);
               }
            }

            String[] var22 = new String[var23.size()];
            this.saslMechanisms = var22;
            var23.copyInto(var22);
         }
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".sasl.authorizationid");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null) {
         this.authorizationID = var17;
         if (this.debug) {
            var15 = this.out;
            var18 = new StringBuilder("DEBUG: mail.imap.sasl.authorizationid: ");
            var18.append(this.authorizationID);
            var15.println(var18.toString());
         }
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".sasl.realm");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null) {
         this.saslRealm = var17;
         if (this.debug) {
            var15 = this.out;
            var18 = new StringBuilder("DEBUG: mail.imap.sasl.realm: ");
            var18.append(this.saslRealm);
            var15.println(var18.toString());
         }
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".forcepasswordrefresh");
      var17 = var1.getProperty(var16.toString());
      if (var17 != null && var17.equalsIgnoreCase("true")) {
         if (this.debug) {
            this.out.println("DEBUG: enable forcePasswordRefresh");
         }

         this.forcePasswordRefresh = true;
      }

      var16 = new StringBuilder("mail.");
      var16.append(var3);
      var16.append(".enableimapevents");
      String var14 = var1.getProperty(var16.toString());
      if (var14 != null && var14.equalsIgnoreCase("true")) {
         if (this.debug) {
            this.out.println("DEBUG: enable IMAP events");
         }

         this.enableImapEvents = true;
      }

   }

   private void checkConnected() {
      if (!this.connected) {
         super.setConnected(false);
         throw new IllegalStateException("Not connected");
      }
   }

   private void cleanup() {
      this.cleanup(false);
   }

   private void cleanup(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   private void emptyConnectionPool(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   private Namespaces getNamespaces() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   private void login(IMAPProtocol var1, String var2, String var3) throws ProtocolException {
      if (this.enableStartTLS && var1.hasCapability("STARTTLS")) {
         var1.startTLS();
         var1.capability();
      }

      if (!var1.isAuthenticated()) {
         var1.getCapabilities().put("__PRELOGIN__", "");
         String var4 = this.authorizationID;
         if (var4 == null) {
            var4 = this.proxyAuthUser;
            if (var4 == null) {
               var4 = var2;
            }
         }

         if (this.enableSASL) {
            var1.sasllogin(this.saslMechanisms, this.saslRealm, var4, var2, var3);
         }

         if (!var1.isAuthenticated()) {
            if (var1.hasCapability("AUTH=PLAIN") && !this.disableAuthPlain) {
               var1.authplain(var4, var2, var3);
            } else if ((var1.hasCapability("AUTH-LOGIN") || var1.hasCapability("AUTH=LOGIN")) && !this.disableAuthLogin) {
               var1.authlogin(var2, var3);
            } else {
               if (var1.hasCapability("LOGINDISABLED")) {
                  throw new ProtocolException("No login methods supported!");
               }

               var1.login(var2, var3);
            }
         }

         var2 = this.proxyAuthUser;
         if (var2 != null) {
            var1.proxyauth(var2);
         }

         if (var1.hasCapability("__PRELOGIN__")) {
            try {
               var1.capability();
            } catch (ConnectionException var5) {
               throw var5;
            } catch (ProtocolException var6) {
            }
         }

      }
   }

   private Folder[] namespaceToFolders(Namespaces.Namespace[] var1, String var2) {
      int var3 = var1.length;
      Folder[] var4 = new Folder[var3];

      for(int var5 = 0; var5 < var3; ++var5) {
         String var6 = var1[var5].prefix;
         String var8;
         if (var2 == null) {
            int var7 = var6.length();
            var8 = var6;
            if (var7 > 0) {
               --var7;
               var8 = var6;
               if (var6.charAt(var7) == var1[var5].delimiter) {
                  var8 = var6.substring(0, var7);
               }
            }
         } else {
            StringBuilder var11 = new StringBuilder(String.valueOf(var6));
            var11.append(var2);
            var8 = var11.toString();
         }

         char var9 = var1[var5].delimiter;
         boolean var10;
         if (var2 == null) {
            var10 = true;
         } else {
            var10 = false;
         }

         var4[var5] = new IMAPFolder(var8, var9, this, var10);
      }

      return var4;
   }

   private void timeoutConnections() {
      // $FF: Couldn't be decompiled
   }

   private void waitIfIdle() throws ProtocolException {
      while(this.pool.idleState != 0) {
         if (this.pool.idleState == 1) {
            this.pool.idleProtocol.idleAbort();
            this.pool.idleState = 2;
         }

         try {
            this.pool.wait();
         } catch (InterruptedException var2) {
         }
      }

   }

   boolean allowReadOnlySelect() {
      Session var1 = this.session;
      StringBuilder var2 = new StringBuilder("mail.");
      var2.append(this.name);
      var2.append(".allowreadonlyselect");
      String var3 = var1.getProperty(var2.toString());
      return var3 != null && var3.equalsIgnoreCase("true");
   }

   public void close() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.close();
   }

   int getAppendBufferSize() {
      return this.appendBufferSize;
   }

   boolean getConnectionPoolDebug() {
      return this.pool.debug;
   }

   public Folder getDefaultFolder() throws MessagingException {
      synchronized(this){}

      DefaultFolder var1;
      try {
         this.checkConnected();
         var1 = new DefaultFolder(this);
      } finally {
         ;
      }

      return var1;
   }

   int getFetchBlockSize() {
      return this.blksize;
   }

   public Folder getFolder(String var1) throws MessagingException {
      synchronized(this){}

      IMAPFolder var4;
      try {
         this.checkConnected();
         var4 = new IMAPFolder(var1, '\uffff', this);
      } finally {
         ;
      }

      return var4;
   }

   public Folder getFolder(URLName var1) throws MessagingException {
      synchronized(this){}

      IMAPFolder var4;
      try {
         this.checkConnected();
         var4 = new IMAPFolder(var1.getFile(), '\uffff', this);
      } finally {
         ;
      }

      return var4;
   }

   int getMinIdleTime() {
      return this.minIdleTime;
   }

   public Folder[] getPersonalNamespaces() throws MessagingException {
      Namespaces var1 = this.getNamespaces();
      return var1 != null && var1.personal != null ? this.namespaceToFolders(var1.personal, (String)null) : super.getPersonalNamespaces();
   }

   IMAPProtocol getProtocol(IMAPFolder param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public Quota[] getQuota(String param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   Session getSession() {
      return this.session;
   }

   public Folder[] getSharedNamespaces() throws MessagingException {
      Namespaces var1 = this.getNamespaces();
      return var1 != null && var1.shared != null ? this.namespaceToFolders(var1.shared, (String)null) : super.getSharedNamespaces();
   }

   int getStatusCacheTimeout() {
      return this.statusCacheTimeout;
   }

   IMAPProtocol getStoreProtocol() throws ProtocolException {
      // $FF: Couldn't be decompiled
   }

   public Folder[] getUserNamespaces(String var1) throws MessagingException {
      Namespaces var2 = this.getNamespaces();
      return var2 != null && var2.otherUsers != null ? this.namespaceToFolders(var2.otherUsers, var1) : super.getUserNamespaces(var1);
   }

   public void handleResponse(Response var1) {
      if (var1.isOK() || var1.isNO() || var1.isBAD() || var1.isBYE()) {
         this.handleResponseCode(var1);
      }

      if (var1.isBYE()) {
         if (this.debug) {
            this.out.println("DEBUG: IMAPStore connection dead");
         }

         if (this.connected) {
            this.cleanup(var1.isSynthetic());
         }
      }

   }

   void handleResponseCode(Response var1) {
      String var2 = var1.getRest();
      boolean var3 = var2.startsWith("[");
      boolean var4 = false;
      boolean var5 = false;
      String var6 = var2;
      if (var3) {
         int var7 = var2.indexOf(93);
         var4 = var5;
         if (var7 > 0) {
            var4 = var5;
            if (var2.substring(0, var7 + 1).equalsIgnoreCase("[ALERT]")) {
               var4 = true;
            }
         }

         var6 = var2.substring(var7 + 1).trim();
      }

      if (var4) {
         this.notifyStoreListeners(1, var6);
      } else if (var1.isUnTagged() && var6.length() > 0) {
         this.notifyStoreListeners(2, var6);
      }

   }

   public boolean hasCapability(String param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   boolean hasSeparateStoreConnection() {
      return this.pool.separateStoreConnection;
   }

   public void idle() throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public boolean isConnected() {
      // $FF: Couldn't be decompiled
   }

   boolean isConnectionPoolFull() {
      IMAPStore.ConnectionPool var1 = this.pool;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label211: {
         try {
            if (this.pool.debug) {
               PrintStream var2 = this.out;
               StringBuilder var3 = new StringBuilder("DEBUG: current size: ");
               var3.append(this.pool.authenticatedConnections.size());
               var3.append("   pool size: ");
               var3.append(this.pool.poolSize);
               var2.println(var3.toString());
            }
         } catch (Throwable var24) {
            var10000 = var24;
            var10001 = false;
            break label211;
         }

         boolean var4;
         label203: {
            label202: {
               try {
                  if (this.pool.authenticatedConnections.size() >= this.pool.poolSize) {
                     break label202;
                  }
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break label211;
               }

               var4 = false;
               break label203;
            }

            var4 = true;
         }

         label196:
         try {
            return var4;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label196;
         }
      }

      while(true) {
         Throwable var25 = var10000;

         try {
            throw var25;
         } catch (Throwable var21) {
            var10000 = var21;
            var10001 = false;
            continue;
         }
      }
   }

   protected boolean protocolConnect(String param1, int param2, String param3, String param4) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   void releaseProtocol(IMAPFolder param1, IMAPProtocol param2) {
      // $FF: Couldn't be decompiled
   }

   void releaseStoreProtocol(IMAPProtocol var1) {
      if (var1 != null) {
         IMAPStore.ConnectionPool var15 = this.pool;
         synchronized(var15){}

         Throwable var10000;
         boolean var10001;
         label136: {
            try {
               this.pool.storeConnectionInUse = false;
               this.pool.notifyAll();
               if (this.pool.debug) {
                  this.out.println("DEBUG: releaseStoreProtocol()");
               }
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label136;
            }

            label133:
            try {
               this.timeoutConnections();
               return;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label133;
            }
         }

         while(true) {
            Throwable var2 = var10000;

            try {
               throw var2;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               continue;
            }
         }
      }
   }

   public void setPassword(String var1) {
      synchronized(this){}

      try {
         this.password = var1;
      } finally {
         ;
      }

   }

   public void setQuota(Quota param1) throws MessagingException {
      // $FF: Couldn't be decompiled
   }

   public void setUsername(String var1) {
      synchronized(this){}

      try {
         this.user = var1;
      } finally {
         ;
      }

   }

   static class ConnectionPool {
      private static final int ABORTING = 2;
      private static final int IDLE = 1;
      private static final int RUNNING = 0;
      private Vector authenticatedConnections = new Vector();
      private long clientTimeoutInterval = 45000L;
      private boolean debug = false;
      private Vector folders;
      private IMAPProtocol idleProtocol;
      private int idleState = 0;
      private long lastTimePruned;
      private int poolSize = 1;
      private long pruningInterval = 60000L;
      private boolean separateStoreConnection = false;
      private long serverTimeoutInterval = 1800000L;
      private boolean storeConnectionInUse = false;

      // $FF: synthetic method
      static boolean access$12(IMAPStore.ConnectionPool var0) {
         return var0.storeConnectionInUse;
      }

      // $FF: synthetic method
      static Vector access$13(IMAPStore.ConnectionPool var0) {
         return var0.folders;
      }

      // $FF: synthetic method
      static void access$14(IMAPStore.ConnectionPool var0, Vector var1) {
         var0.folders = var1;
      }

      // $FF: synthetic method
      static long access$16(IMAPStore.ConnectionPool var0) {
         return var0.lastTimePruned;
      }

      // $FF: synthetic method
      static long access$17(IMAPStore.ConnectionPool var0) {
         return var0.pruningInterval;
      }

      // $FF: synthetic method
      static void access$18(IMAPStore.ConnectionPool var0, IMAPProtocol var1) {
         var0.idleProtocol = var1;
      }
   }
}
