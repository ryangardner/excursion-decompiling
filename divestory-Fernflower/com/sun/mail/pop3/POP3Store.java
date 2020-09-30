package com.sun.mail.pop3;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

public class POP3Store extends Store {
   private int defaultPort;
   boolean disableTop;
   boolean forgetTopHeaders;
   private String host;
   private boolean isSSL;
   Constructor messageConstructor;
   private String name;
   private String passwd;
   private Protocol port;
   private int portNum;
   private POP3Folder portOwner;
   boolean rsetBeforeQuit;
   private String user;

   public POP3Store(Session var1, URLName var2) {
      this(var1, var2, "pop3", 110, false);
   }

   public POP3Store(Session param1, URLName param2, String param3, int param4, boolean param5) {
      // $FF: Couldn't be decompiled
   }

   private void checkConnected() throws MessagingException {
      if (!super.isConnected()) {
         throw new MessagingException("Not connected");
      }
   }

   public void close() throws MessagingException {
      synchronized(this){}
      boolean var32 = false;

      Throwable var10000;
      label271: {
         boolean var10001;
         label270: {
            label269: {
               try {
                  var32 = true;
                  if (this.port != null) {
                     this.port.quit();
                     var32 = false;
                  } else {
                     var32 = false;
                  }
                  break label269;
               } catch (IOException var37) {
                  var32 = false;
               } finally {
                  if (var32) {
                     try {
                        this.port = null;
                        super.close();
                     } catch (Throwable var33) {
                        var10000 = var33;
                        var10001 = false;
                        break label271;
                     }
                  }
               }

               try {
                  this.port = null;
                  break label270;
               } catch (Throwable var35) {
                  var10000 = var35;
                  var10001 = false;
                  break label271;
               }
            }

            try {
               this.port = null;
            } catch (Throwable var36) {
               var10000 = var36;
               var10001 = false;
               break label271;
            }
         }

         label260:
         try {
            super.close();
            return;
         } catch (Throwable var34) {
            var10000 = var34;
            var10001 = false;
            break label260;
         }
      }

      Throwable var1 = var10000;
      throw var1;
   }

   void closePort(POP3Folder var1) {
      synchronized(this){}

      try {
         if (this.portOwner == var1) {
            this.port = null;
            this.portOwner = null;
         }
      } finally {
         ;
      }

   }

   protected void finalize() throws Throwable {
      super.finalize();
      if (this.port != null) {
         this.close();
      }

   }

   public Folder getDefaultFolder() throws MessagingException {
      this.checkConnected();
      return new DefaultFolder(this);
   }

   public Folder getFolder(String var1) throws MessagingException {
      this.checkConnected();
      return new POP3Folder(this, var1);
   }

   public Folder getFolder(URLName var1) throws MessagingException {
      this.checkConnected();
      return new POP3Folder(this, var1.getFile());
   }

   Protocol getPort(POP3Folder var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label519: {
         boolean var10001;
         try {
            if (this.port != null && this.portOwner == null) {
               this.portOwner = var1;
               Protocol var67 = this.port;
               return var67;
            }
         } catch (Throwable var64) {
            var10000 = var64;
            var10001 = false;
            break label519;
         }

         Protocol var2;
         String var68;
         try {
            String var3 = this.host;
            int var4 = this.portNum;
            boolean var5 = this.session.getDebug();
            PrintStream var6 = this.session.getDebugOut();
            Properties var7 = this.session.getProperties();
            StringBuilder var8 = new StringBuilder("mail.");
            var8.append(this.name);
            var2 = new Protocol(var3, var4, var5, var6, var7, var8.toString(), this.isSSL);
            var68 = var2.login(this.user, this.passwd);
         } catch (Throwable var63) {
            var10000 = var63;
            var10001 = false;
            break label519;
         }

         if (var68 == null) {
            label520: {
               label498: {
                  try {
                     if (this.port != null) {
                        break label498;
                     }
                  } catch (Throwable var61) {
                     var10000 = var61;
                     var10001 = false;
                     break label520;
                  }

                  if (var1 != null) {
                     try {
                        this.port = var2;
                        this.portOwner = var1;
                     } catch (Throwable var60) {
                        var10000 = var60;
                        var10001 = false;
                        break label520;
                     }
                  }
               }

               try {
                  if (this.portOwner == null) {
                     this.portOwner = var1;
                  }
               } catch (Throwable var59) {
                  var10000 = var59;
                  var10001 = false;
                  break label520;
               }

               return var2;
            }
         } else {
            label487:
            try {
               var2.quit();
            } finally {
               break label487;
            }

            label501:
            try {
               EOFException var66 = new EOFException(var68);
               throw var66;
            } catch (Throwable var62) {
               var10000 = var62;
               var10001 = false;
               break label501;
            }
         }
      }

      Throwable var65 = var10000;
      throw var65;
   }

   public boolean isConnected() {
      // $FF: Couldn't be decompiled
   }

   protected boolean protocolConnect(String param1, int param2, String param3, String param4) throws MessagingException {
      // $FF: Couldn't be decompiled
   }
}
