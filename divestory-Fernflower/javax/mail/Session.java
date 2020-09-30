package javax.mail;

import com.sun.mail.util.LineInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public final class Session {
   private static Session defaultSession;
   private final Properties addressMap = new Properties();
   private final Hashtable authTable = new Hashtable();
   private final Authenticator authenticator;
   private boolean debug = false;
   private PrintStream out;
   private final Properties props;
   private final Vector providers = new Vector();
   private final Hashtable providersByClassName = new Hashtable();
   private final Hashtable providersByProtocol = new Hashtable();

   private Session(Properties var1, Authenticator var2) {
      this.props = var1;
      this.authenticator = var2;
      if (Boolean.valueOf(var1.getProperty("mail.debug"))) {
         this.debug = true;
      }

      if (this.debug) {
         this.pr("DEBUG: JavaMail version 1.4.1");
      }

      Class var3;
      if (var2 != null) {
         var3 = var2.getClass();
      } else {
         var3 = this.getClass();
      }

      this.loadProviders(var3);
      this.loadAddressMap(var3);
   }

   private static ClassLoader getContextClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            ClassLoader var1;
            try {
               var1 = Thread.currentThread().getContextClassLoader();
            } catch (SecurityException var2) {
               var1 = null;
            }

            return var1;
         }
      });
   }

   public static Session getDefaultInstance(Properties var0) {
      return getDefaultInstance(var0, (Authenticator)null);
   }

   public static Session getDefaultInstance(Properties var0, Authenticator var1) {
      synchronized(Session.class){}

      Throwable var10000;
      label409: {
         boolean var10001;
         label410: {
            try {
               if (defaultSession == null) {
                  Session var2 = new Session(var0, var1);
                  defaultSession = var2;
                  break label410;
               }
            } catch (Throwable var44) {
               var10000 = var44;
               var10001 = false;
               break label409;
            }

            label393:
            try {
               if (defaultSession.authenticator != var1) {
                  break label393;
               }
               break label410;
            } catch (Throwable var43) {
               var10000 = var43;
               var10001 = false;
               break label409;
            }

            label388: {
               try {
                  if (defaultSession.authenticator == null) {
                     break label388;
                  }
               } catch (Throwable var42) {
                  var10000 = var42;
                  var10001 = false;
                  break label409;
               }

               if (var1 != null) {
                  try {
                     if (defaultSession.authenticator.getClass().getClassLoader() == var1.getClass().getClassLoader()) {
                        break label410;
                     }
                  } catch (Throwable var41) {
                     var10000 = var41;
                     var10001 = false;
                     break label409;
                  }
               }
            }

            try {
               SecurityException var45 = new SecurityException("Access to default session denied");
               throw var45;
            } catch (Throwable var40) {
               var10000 = var40;
               var10001 = false;
               break label409;
            }
         }

         Session var47;
         try {
            var47 = defaultSession;
         } catch (Throwable var39) {
            var10000 = var39;
            var10001 = false;
            break label409;
         }

         return var47;
      }

      Throwable var46 = var10000;
      throw var46;
   }

   public static Session getInstance(Properties var0) {
      return new Session(var0, (Authenticator)null);
   }

   public static Session getInstance(Properties var0, Authenticator var1) {
      return new Session(var0, var1);
   }

   private static InputStream getResourceAsStream(final Class var0, final String var1) throws IOException {
      try {
         PrivilegedExceptionAction var2 = new PrivilegedExceptionAction() {
            public Object run() throws IOException {
               return var0.getResourceAsStream(var1);
            }
         };
         InputStream var4 = (InputStream)AccessController.doPrivileged(var2);
         return var4;
      } catch (PrivilegedActionException var3) {
         throw (IOException)var3.getException();
      }
   }

   private static URL[] getResources(final ClassLoader var0, final String var1) {
      return (URL[])AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            URL[] var1x = (URL[])null;
            URL[] var2 = var1x;

            URL[] var15;
            label90: {
               boolean var10001;
               Vector var3;
               try {
                  var3 = new Vector;
               } catch (SecurityException | IOException var14) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1x;

               try {
                  var3.<init>();
               } catch (SecurityException | IOException var13) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1x;

               Enumeration var4;
               try {
                  var4 = var0.getResources(var1);
               } catch (SecurityException | IOException var11) {
                  var10001 = false;
                  break label90;
               }

               while(var4 != null) {
                  var2 = var1x;

                  try {
                     if (!var4.hasMoreElements()) {
                        break;
                     }
                  } catch (SecurityException | IOException var12) {
                     var10001 = false;
                     break label90;
                  }

                  var2 = var1x;

                  URL var5;
                  try {
                     var5 = (URL)var4.nextElement();
                  } catch (SecurityException | IOException var10) {
                     var10001 = false;
                     break label90;
                  }

                  if (var5 != null) {
                     var2 = var1x;

                     try {
                        var3.addElement(var5);
                     } catch (SecurityException | IOException var9) {
                        var10001 = false;
                        break label90;
                     }
                  }
               }

               var15 = var1x;
               var2 = var1x;

               try {
                  if (var3.size() <= 0) {
                     return var15;
                  }
               } catch (SecurityException | IOException var8) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1x;

               try {
                  var15 = new URL[var3.size()];
               } catch (SecurityException | IOException var7) {
                  var10001 = false;
                  break label90;
               }

               var2 = var15;

               try {
                  var3.copyInto(var15);
                  return var15;
               } catch (SecurityException | IOException var6) {
                  var10001 = false;
               }
            }

            var15 = var2;
            return var15;
         }
      });
   }

   private Object getService(Provider param1, URLName param2) throws NoSuchProviderException {
      // $FF: Couldn't be decompiled
   }

   private Store getStore(Provider var1, URLName var2) throws NoSuchProviderException {
      if (var1 != null && var1.getType() == Provider.Type.STORE) {
         try {
            Store var4 = (Store)this.getService(var1, var2);
            return var4;
         } catch (ClassCastException var3) {
            throw new NoSuchProviderException("incorrect class");
         }
      } else {
         throw new NoSuchProviderException("invalid provider");
      }
   }

   private static URL[] getSystemResources(final String var0) {
      return (URL[])AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            URL[] var1 = (URL[])null;
            URL[] var2 = var1;

            URL[] var15;
            label90: {
               boolean var10001;
               Vector var3;
               try {
                  var3 = new Vector;
               } catch (SecurityException | IOException var14) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1;

               try {
                  var3.<init>();
               } catch (SecurityException | IOException var13) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1;

               Enumeration var4;
               try {
                  var4 = ClassLoader.getSystemResources(var0);
               } catch (SecurityException | IOException var11) {
                  var10001 = false;
                  break label90;
               }

               while(var4 != null) {
                  var2 = var1;

                  try {
                     if (!var4.hasMoreElements()) {
                        break;
                     }
                  } catch (SecurityException | IOException var12) {
                     var10001 = false;
                     break label90;
                  }

                  var2 = var1;

                  URL var5;
                  try {
                     var5 = (URL)var4.nextElement();
                  } catch (SecurityException | IOException var10) {
                     var10001 = false;
                     break label90;
                  }

                  if (var5 != null) {
                     var2 = var1;

                     try {
                        var3.addElement(var5);
                     } catch (SecurityException | IOException var9) {
                        var10001 = false;
                        break label90;
                     }
                  }
               }

               var15 = var1;
               var2 = var1;

               try {
                  if (var3.size() <= 0) {
                     return var15;
                  }
               } catch (SecurityException | IOException var8) {
                  var10001 = false;
                  break label90;
               }

               var2 = var1;

               try {
                  var15 = new URL[var3.size()];
               } catch (SecurityException | IOException var7) {
                  var10001 = false;
                  break label90;
               }

               var2 = var15;

               try {
                  var3.copyInto(var15);
                  return var15;
               } catch (SecurityException | IOException var6) {
                  var10001 = false;
               }
            }

            var15 = var2;
            return var15;
         }
      });
   }

   private Transport getTransport(Provider var1, URLName var2) throws NoSuchProviderException {
      if (var1 != null && var1.getType() == Provider.Type.TRANSPORT) {
         try {
            Transport var4 = (Transport)this.getService(var1, var2);
            return var4;
         } catch (ClassCastException var3) {
            throw new NoSuchProviderException("incorrect class");
         }
      } else {
         throw new NoSuchProviderException("invalid provider");
      }
   }

   private void loadAddressMap(Class var1) {
      StreamLoader var2 = new StreamLoader() {
         public void load(InputStream var1) throws IOException {
            Session.this.addressMap.load(var1);
         }
      };
      this.loadResource("/META-INF/javamail.default.address.map", var1, var2);
      this.loadAllResources("META-INF/javamail.address.map", var1, var2);

      try {
         StringBuilder var4 = new StringBuilder(String.valueOf(System.getProperty("java.home")));
         var4.append(File.separator);
         var4.append("lib");
         var4.append(File.separator);
         var4.append("javamail.address.map");
         this.loadFile(var4.toString(), var2);
      } catch (SecurityException var3) {
         if (this.debug) {
            StringBuilder var5 = new StringBuilder("DEBUG: can't get java.home: ");
            var5.append(var3);
            this.pr(var5.toString());
         }
      }

      if (this.addressMap.isEmpty()) {
         if (this.debug) {
            this.pr("DEBUG: failed to load address map, using defaults");
         }

         this.addressMap.put("rfc822", "smtp");
      }

   }

   private void loadAllResources(String param1, Class param2, StreamLoader param3) {
      // $FF: Couldn't be decompiled
   }

   private void loadFile(String param1, StreamLoader param2) {
      // $FF: Couldn't be decompiled
   }

   private void loadProviders(Class var1) {
      StreamLoader var2 = new StreamLoader() {
         public void load(InputStream var1) throws IOException {
            Session.this.loadProvidersFromStream(var1);
         }
      };

      try {
         StringBuilder var3 = new StringBuilder(String.valueOf(System.getProperty("java.home")));
         var3.append(File.separator);
         var3.append("lib");
         var3.append(File.separator);
         var3.append("javamail.providers");
         this.loadFile(var3.toString(), var2);
      } catch (SecurityException var5) {
         if (this.debug) {
            StringBuilder var4 = new StringBuilder("DEBUG: can't get java.home: ");
            var4.append(var5);
            this.pr(var4.toString());
         }
      }

      this.loadAllResources("META-INF/javamail.providers", var1, var2);
      this.loadResource("/META-INF/javamail.default.providers", var1, var2);
      if (this.providers.size() == 0) {
         if (this.debug) {
            this.pr("DEBUG: failed to load any providers, using defaults");
         }

         this.addProvider(new Provider(Provider.Type.STORE, "imap", "com.sun.mail.imap.IMAPStore", "Sun Microsystems, Inc.", "1.4.1"));
         this.addProvider(new Provider(Provider.Type.STORE, "imaps", "com.sun.mail.imap.IMAPSSLStore", "Sun Microsystems, Inc.", "1.4.1"));
         this.addProvider(new Provider(Provider.Type.STORE, "pop3", "com.sun.mail.pop3.POP3Store", "Sun Microsystems, Inc.", "1.4.1"));
         this.addProvider(new Provider(Provider.Type.STORE, "pop3s", "com.sun.mail.pop3.POP3SSLStore", "Sun Microsystems, Inc.", "1.4.1"));
         this.addProvider(new Provider(Provider.Type.TRANSPORT, "smtp", "com.sun.mail.smtp.SMTPTransport", "Sun Microsystems, Inc.", "1.4.1"));
         this.addProvider(new Provider(Provider.Type.TRANSPORT, "smtps", "com.sun.mail.smtp.SMTPSSLTransport", "Sun Microsystems, Inc.", "1.4.1"));
      }

      if (this.debug) {
         this.pr("DEBUG: Tables of loaded providers");
         StringBuilder var6 = new StringBuilder("DEBUG: Providers Listed By Class Name: ");
         var6.append(this.providersByClassName.toString());
         this.pr(var6.toString());
         var6 = new StringBuilder("DEBUG: Providers Listed By Protocol: ");
         var6.append(this.providersByProtocol.toString());
         this.pr(var6.toString());
      }

   }

   private void loadProvidersFromStream(InputStream var1) throws IOException {
      if (var1 != null) {
         LineInputStream var2 = new LineInputStream(var1);

         while(true) {
            while(true) {
               String var3;
               do {
                  var3 = var2.readLine();
                  if (var3 == null) {
                     return;
                  }
               } while(var3.startsWith("#"));

               StringTokenizer var4 = new StringTokenizer(var3, ";");
               String var5 = null;
               String var6 = var5;
               String var7 = var5;
               String var8 = var5;
               String var9 = var5;
               Provider.Type var11 = var5;

               while(var4.hasMoreTokens()) {
                  var5 = var4.nextToken().trim();
                  int var10 = var5.indexOf("=");
                  if (var5.startsWith("protocol=")) {
                     var9 = var5.substring(var10 + 1);
                  } else if (var5.startsWith("type=")) {
                     var5 = var5.substring(var10 + 1);
                     if (var5.equalsIgnoreCase("store")) {
                        var11 = Provider.Type.STORE;
                     } else if (var5.equalsIgnoreCase("transport")) {
                        var11 = Provider.Type.TRANSPORT;
                     }
                  } else if (var5.startsWith("class=")) {
                     var6 = var5.substring(var10 + 1);
                  } else if (var5.startsWith("vendor=")) {
                     var7 = var5.substring(var10 + 1);
                  } else if (var5.startsWith("version=")) {
                     var8 = var5.substring(var10 + 1);
                  }
               }

               if (var11 != null && var9 != null && var6 != null && var9.length() > 0 && var6.length() > 0) {
                  this.addProvider(new Provider(var11, var9, var6, var7, var8));
               } else if (this.debug) {
                  StringBuilder var12 = new StringBuilder("DEBUG: Bad provider entry: ");
                  var12.append(var3);
                  this.pr(var12.toString());
               }
            }
         }
      }
   }

   private void loadResource(String param1, Class param2, StreamLoader param3) {
      // $FF: Couldn't be decompiled
   }

   private static InputStream openStream(final URL var0) throws IOException {
      try {
         PrivilegedExceptionAction var1 = new PrivilegedExceptionAction() {
            public Object run() throws IOException {
               return var0.openStream();
            }
         };
         InputStream var3 = (InputStream)AccessController.doPrivileged(var1);
         return var3;
      } catch (PrivilegedActionException var2) {
         throw (IOException)var2.getException();
      }
   }

   private void pr(String var1) {
      this.getDebugOut().println(var1);
   }

   public void addProvider(Provider var1) {
      synchronized(this){}

      try {
         this.providers.addElement(var1);
         this.providersByClassName.put(var1.getClassName(), var1);
         if (!this.providersByProtocol.containsKey(var1.getProtocol())) {
            this.providersByProtocol.put(var1.getProtocol(), var1);
         }
      } finally {
         ;
      }

   }

   public boolean getDebug() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.debug;
      } finally {
         ;
      }

      return var1;
   }

   public PrintStream getDebugOut() {
      synchronized(this){}

      PrintStream var1;
      try {
         if (this.out != null) {
            var1 = this.out;
            return var1;
         }

         var1 = System.out;
      } finally {
         ;
      }

      return var1;
   }

   public Folder getFolder(URLName var1) throws MessagingException {
      Store var2 = this.getStore(var1);
      var2.connect();
      return var2.getFolder(var1);
   }

   public PasswordAuthentication getPasswordAuthentication(URLName var1) {
      return (PasswordAuthentication)this.authTable.get(var1);
   }

   public Properties getProperties() {
      return this.props;
   }

   public String getProperty(String var1) {
      return this.props.getProperty(var1);
   }

   public Provider getProvider(String var1) throws NoSuchProviderException {
      Throwable var10000;
      label612: {
         boolean var10001;
         label609: {
            synchronized(this){}
            if (var1 != null) {
               try {
                  if (var1.length() > 0) {
                     break label609;
                  }
               } catch (Throwable var76) {
                  var10000 = var76;
                  var10001 = false;
                  break label612;
               }
            }

            try {
               NoSuchProviderException var77 = new NoSuchProviderException("Invalid protocol: null");
               throw var77;
            } catch (Throwable var75) {
               var10000 = var75;
               var10001 = false;
               break label612;
            }
         }

         Provider var2 = null;

         String var81;
         try {
            Properties var3 = this.props;
            StringBuilder var4 = new StringBuilder("mail.");
            var4.append(var1);
            var4.append(".class");
            var81 = var3.getProperty(var4.toString());
         } catch (Throwable var74) {
            var10000 = var74;
            var10001 = false;
            break label612;
         }

         StringBuilder var80;
         if (var81 != null) {
            try {
               if (this.debug) {
                  var80 = new StringBuilder("DEBUG: mail.");
                  var80.append(var1);
                  var80.append(".class property exists and points to ");
                  var80.append(var81);
                  this.pr(var80.toString());
               }
            } catch (Throwable var73) {
               var10000 = var73;
               var10001 = false;
               break label612;
            }

            try {
               var2 = (Provider)this.providersByClassName.get(var81);
            } catch (Throwable var72) {
               var10000 = var72;
               var10001 = false;
               break label612;
            }
         }

         if (var2 != null) {
            return var2;
         }

         try {
            var2 = (Provider)this.providersByProtocol.get(var1);
         } catch (Throwable var71) {
            var10000 = var71;
            var10001 = false;
            break label612;
         }

         if (var2 != null) {
            label580: {
               try {
                  if (this.debug) {
                     StringBuilder var78 = new StringBuilder("DEBUG: getProvider() returning ");
                     var78.append(var2.toString());
                     this.pr(var78.toString());
                  }
               } catch (Throwable var69) {
                  var10000 = var69;
                  var10001 = false;
                  break label580;
               }

               return var2;
            }
         } else {
            label582:
            try {
               var80 = new StringBuilder("No provider for ");
               var80.append(var1);
               NoSuchProviderException var82 = new NoSuchProviderException(var80.toString());
               throw var82;
            } catch (Throwable var70) {
               var10000 = var70;
               var10001 = false;
               break label582;
            }
         }
      }

      Throwable var79 = var10000;
      throw var79;
   }

   public Provider[] getProviders() {
      synchronized(this){}

      Provider[] var1;
      try {
         var1 = new Provider[this.providers.size()];
         this.providers.copyInto(var1);
      } finally {
         ;
      }

      return var1;
   }

   public Store getStore() throws NoSuchProviderException {
      return this.getStore(this.getProperty("mail.store.protocol"));
   }

   public Store getStore(String var1) throws NoSuchProviderException {
      return this.getStore(new URLName(var1, (String)null, -1, (String)null, (String)null, (String)null));
   }

   public Store getStore(Provider var1) throws NoSuchProviderException {
      return this.getStore(var1, (URLName)null);
   }

   public Store getStore(URLName var1) throws NoSuchProviderException {
      return this.getStore(this.getProvider(var1.getProtocol()), var1);
   }

   public Transport getTransport() throws NoSuchProviderException {
      return this.getTransport(this.getProperty("mail.transport.protocol"));
   }

   public Transport getTransport(String var1) throws NoSuchProviderException {
      return this.getTransport(new URLName(var1, (String)null, -1, (String)null, (String)null, (String)null));
   }

   public Transport getTransport(Address var1) throws NoSuchProviderException {
      String var2 = (String)this.addressMap.get(var1.getType());
      if (var2 != null) {
         return this.getTransport(var2);
      } else {
         StringBuilder var3 = new StringBuilder("No provider for Address type: ");
         var3.append(var1.getType());
         throw new NoSuchProviderException(var3.toString());
      }
   }

   public Transport getTransport(Provider var1) throws NoSuchProviderException {
      return this.getTransport(var1, (URLName)null);
   }

   public Transport getTransport(URLName var1) throws NoSuchProviderException {
      return this.getTransport(this.getProvider(var1.getProtocol()), var1);
   }

   public PasswordAuthentication requestPasswordAuthentication(InetAddress var1, int var2, String var3, String var4, String var5) {
      Authenticator var6 = this.authenticator;
      return var6 != null ? var6.requestPasswordAuthentication(var1, var2, var3, var4, var5) : null;
   }

   public void setDebug(boolean var1) {
      synchronized(this){}

      Throwable var10000;
      label75: {
         boolean var10001;
         try {
            this.debug = var1;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label75;
         }

         if (!var1) {
            return;
         }

         label66:
         try {
            this.pr("DEBUG: setDebug: JavaMail version 1.4.1");
            return;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label66;
         }
      }

      Throwable var2 = var10000;
      throw var2;
   }

   public void setDebugOut(PrintStream var1) {
      synchronized(this){}

      try {
         this.out = var1;
      } finally {
         ;
      }

   }

   public void setPasswordAuthentication(URLName var1, PasswordAuthentication var2) {
      if (var2 == null) {
         this.authTable.remove(var1);
      } else {
         this.authTable.put(var1, var2);
      }

   }

   public void setProtocolForAddress(String var1, String var2) {
      synchronized(this){}
      Throwable var10000;
      boolean var10001;
      if (var2 == null) {
         label53:
         try {
            this.addressMap.remove(var1);
            return;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label53;
         }
      } else {
         label55:
         try {
            this.addressMap.put(var1, var2);
            return;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label55;
         }
      }

      Throwable var9 = var10000;
      throw var9;
   }

   public void setProvider(Provider var1) throws NoSuchProviderException {
      synchronized(this){}
      Throwable var10000;
      boolean var10001;
      if (var1 != null) {
         label51: {
            try {
               this.providersByProtocol.put(var1.getProtocol(), var1);
               Properties var2 = this.props;
               StringBuilder var3 = new StringBuilder("mail.");
               var3.append(var1.getProtocol());
               var3.append(".class");
               var2.put(var3.toString(), var1.getClassName());
            } catch (Throwable var8) {
               var10000 = var8;
               var10001 = false;
               break label51;
            }

            return;
         }
      } else {
         label53:
         try {
            NoSuchProviderException var11 = new NoSuchProviderException("Can't set null provider");
            throw var11;
         } catch (Throwable var9) {
            var10000 = var9;
            var10001 = false;
            break label53;
         }
      }

      Throwable var10 = var10000;
      throw var10;
   }
}
