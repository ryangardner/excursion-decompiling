package net.sbbi.upnp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import net.sbbi.upnp.services.UPNPService;
import org.xml.sax.InputSource;

public class ServicesEventing implements Runnable {
   private static final Logger log = Logger.getLogger(ServicesEventing.class.getName());
   private static final ServicesEventing singleton = new ServicesEventing();
   private boolean daemon = true;
   private int daemonPort = 9999;
   private boolean inService = false;
   private List registered = new ArrayList();
   private ServerSocket server = null;

   private ServicesEventing() {
   }

   public static final ServicesEventing getInstance() {
      return singleton;
   }

   private ServicesEventing.Subscription lookupSubscriber(String var1) {
      List var2 = this.registered;
      synchronized(var2){}

      Throwable var10000;
      boolean var10001;
      label189: {
         Iterator var3;
         try {
            var3 = this.registered.iterator();
         } catch (Throwable var24) {
            var10000 = var24;
            var10001 = false;
            break label189;
         }

         while(true) {
            try {
               if (!var3.hasNext()) {
                  return null;
               }
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break;
            }

            try {
               ServicesEventing.Subscription var4 = (ServicesEventing.Subscription)var3.next();
               if (var4.sub.getSID().equals(var1)) {
                  return var4;
               }
            } catch (Throwable var22) {
               var10000 = var22;
               var10001 = false;
               break;
            }
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

   private ServicesEventing.Subscription lookupSubscriber(String var1, InetAddress var2) {
      List var3 = this.registered;
      synchronized(var3){}

      Throwable var10000;
      boolean var10001;
      label189: {
         Iterator var4;
         try {
            var4 = this.registered.iterator();
         } catch (Throwable var25) {
            var10000 = var25;
            var10001 = false;
            break label189;
         }

         while(true) {
            try {
               if (!var4.hasNext()) {
                  return null;
               }
            } catch (Throwable var24) {
               var10000 = var24;
               var10001 = false;
               break;
            }

            try {
               ServicesEventing.Subscription var5 = (ServicesEventing.Subscription)var4.next();
               if (var5.sub.getSID().equals(var1) && var5.sub.getDeviceIp().equals(var2)) {
                  return var5;
               }
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break;
            }
         }
      }

      while(true) {
         Throwable var26 = var10000;

         try {
            throw var26;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            continue;
         }
      }
   }

   private ServicesEventing.Subscription lookupSubscriber(UPNPService var1, ServiceEventHandler var2) {
      List var3 = this.registered;
      synchronized(var3){}

      Throwable var10000;
      boolean var10001;
      label209: {
         Iterator var4;
         try {
            var4 = this.registered.iterator();
         } catch (Throwable var25) {
            var10000 = var25;
            var10001 = false;
            break label209;
         }

         while(true) {
            try {
               if (!var4.hasNext()) {
                  return null;
               }
            } catch (Throwable var24) {
               var10000 = var24;
               var10001 = false;
               break;
            }

            try {
               ServicesEventing.Subscription var5 = (ServicesEventing.Subscription)var4.next();
               if (var5.handler == var2 && var5.sub.getServiceId().hashCode() == var1.getServiceId().hashCode() && var5.sub.getServiceType().hashCode() == var1.getServiceType().hashCode() && var5.sub.getServiceURL().equals(var1.getEventSubURL())) {
                  return var5;
               }
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break;
            }
         }
      }

      while(true) {
         Throwable var26 = var10000;

         try {
            throw var26;
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            continue;
         }
      }
   }

   private void startServicesEventingThread() {
      ServicesEventing var1 = singleton;
      synchronized(var1){}

      Throwable var10000;
      boolean var10001;
      label122: {
         try {
            if (!this.inService) {
               Thread var2 = new Thread(singleton, "ServicesEventing daemon");
               var2.setDaemon(this.daemon);
               this.inService = true;
               var2.start();
            }
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            return;
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var15 = var10000;

         try {
            throw var15;
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            continue;
         }
      }
   }

   private void stopServicesEventingThread() {
      // $FF: Couldn't be decompiled
   }

   public int register(UPNPService var1, ServiceEventHandler var2, int var3) throws IOException {
      ServiceEventSubscription var4 = this.registerEvent(var1, var2, var3);
      return var4 != null ? var4.getDurationTime() : -1;
   }

   public ServiceEventSubscription registerEvent(UPNPService param1, ServiceEventHandler param2, int param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void run() {
      if (!Thread.currentThread().getName().equals("ServicesEventing daemon")) {
         System.out.println("ABORTING THREAD LAUNCH: NAME INCORRECT");
      } else {
         try {
            ServerSocket var7 = new ServerSocket(this.daemonPort);
            this.server = var7;
         } catch (IOException var5) {
            Logger var2 = log;
            Level var1 = Level.SEVERE;
            StringBuilder var4 = new StringBuilder("Error during daemon server socket on port ");
            var4.append(this.daemonPort);
            var4.append(" creation");
            var2.log(var1, var4.toString(), var5);
            return;
         }

         while(this.inService) {
            try {
               Socket var8 = this.server.accept();
               ServicesEventing.RequestProcessor var3 = new ServicesEventing.RequestProcessor(var8, (ServicesEventing.RequestProcessor)null);
               Thread var9 = new Thread(var3, "RequestProcessor");
               var9.start();
            } catch (Exception var6) {
               if (this.inService) {
                  log.log(Level.SEVERE, "IO Exception during UPNP messages listening thread", var6);
                  var6.printStackTrace();
               }
            }
         }

      }
   }

   public void setDaemon(boolean var1) {
      this.daemon = var1;
   }

   public void setDaemonPort(int var1) {
      this.daemonPort = var1;
   }

   public boolean unRegister(UPNPService param1, ServiceEventHandler param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private class RequestProcessor implements Runnable {
      private Socket client;

      private RequestProcessor(Socket var2) {
         this.client = var2;
      }

      // $FF: synthetic method
      RequestProcessor(Socket var2, ServicesEventing.RequestProcessor var3) {
         this(var2);
      }

      public void run() {
         IOException var53;
         label178: {
            Exception var10000;
            label163: {
               InputStream var1;
               OutputStream var2;
               StringBuffer var3;
               byte[] var4;
               boolean var10001;
               try {
                  this.client.setSoTimeout(30000);
                  var1 = this.client.getInputStream();
                  var2 = this.client.getOutputStream();
                  var3 = new StringBuffer();
                  var4 = new byte[256];
               } catch (IOException var37) {
                  var53 = var37;
                  var10001 = false;
                  break label178;
               } catch (Exception var38) {
                  var10000 = var38;
                  var10001 = false;
                  break label163;
               }

               boolean var5 = false;

               String var7;
               while(!var5) {
                  int var6;
                  try {
                     var6 = var1.read(var4);
                  } catch (IOException var33) {
                     var53 = var33;
                     var10001 = false;
                     break label178;
                  } catch (Exception var34) {
                     var10000 = var34;
                     var10001 = false;
                     break label163;
                  }

                  if (var6 == -1) {
                     break;
                  }

                  try {
                     var7 = new String(var4, 0, var6, "UTF-8");
                     var3.append(var7);
                     var7 = var3.substring(var3.length() - 16, var3.length());
                     if (var3.charAt(var3.length() - 1) != 0 && !"</e:propertyset>".equals(var7)) {
                        continue;
                     }
                  } catch (IOException var35) {
                     var53 = var35;
                     var10001 = false;
                     break label178;
                  } catch (Exception var36) {
                     var10000 = var36;
                     var10001 = false;
                     break label163;
                  }

                  var5 = true;
               }

               String var43;
               try {
                  var43 = var3.toString();
                  if (var43.trim().length() <= 0) {
                     return;
                  }
               } catch (IOException var31) {
                  var53 = var31;
                  var10001 = false;
                  break label178;
               } catch (Exception var32) {
                  var10000 = var32;
                  var10001 = false;
                  break label163;
               }

               String var45 = var43;

               try {
                  if (var43.indexOf(0) != -1) {
                     var45 = var43.replace('\u0000', ' ');
                  }
               } catch (IOException var29) {
                  var53 = var29;
                  var10001 = false;
                  break label178;
               } catch (Exception var30) {
                  var10000 = var30;
                  var10001 = false;
                  break label163;
               }

               String var8;
               InetAddress var9;
               String var10;
               HttpResponse var51;
               try {
                  var51 = new HttpResponse(var45);
                  if (!var51.getHeader().startsWith("NOTIFY")) {
                     return;
                  }

                  var8 = var51.getHTTPHeaderField("SID");
                  var9 = this.client.getInetAddress();
                  var10 = var51.getHTTPHeaderField("SID");
               } catch (IOException var27) {
                  var53 = var27;
                  var10001 = false;
                  break label178;
               } catch (Exception var28) {
                  var10000 = var28;
                  var10001 = false;
                  break label163;
               }

               var3 = null;
               ServicesEventing.Subscription var48 = var3;
               if (var8 != null) {
                  var48 = var3;
                  if (var10 != null) {
                     ServicesEventing.Subscription var44;
                     try {
                        var44 = ServicesEventing.this.lookupSubscriber(var8, var9);
                     } catch (IOException var25) {
                        var53 = var25;
                        var10001 = false;
                        break label178;
                     } catch (Exception var26) {
                        var10000 = var26;
                        var10001 = false;
                        break label163;
                     }

                     var48 = var44;
                     if (var44 == null) {
                        try {
                           var48 = ServicesEventing.this.lookupSubscriber(var8);
                        } catch (IOException var23) {
                           var53 = var23;
                           var10001 = false;
                           break label178;
                        } catch (Exception var24) {
                           var10000 = var24;
                           var10001 = false;
                           break label163;
                        }
                     }
                  }
               }

               if (var48 != null) {
                  try {
                     var2.write("HTTP/1.1 200 OK\r\n".getBytes());
                  } catch (IOException var21) {
                     var53 = var21;
                     var10001 = false;
                     break label178;
                  } catch (Exception var22) {
                     var10000 = var22;
                     var10001 = false;
                     break label163;
                  }
               } else {
                  try {
                     var2.write("HTTP/1.1 412 Precondition Failed\r\n".getBytes());
                  } catch (IOException var19) {
                     var53 = var19;
                     var10001 = false;
                     break label178;
                  } catch (Exception var20) {
                     var10000 = var20;
                     var10001 = false;
                     break label163;
                  }
               }

               try {
                  var2.flush();
                  var1.close();
                  var2.close();
                  this.client.close();
               } catch (IOException var17) {
                  var53 = var17;
                  var10001 = false;
                  break label178;
               } catch (Exception var18) {
                  var10000 = var18;
                  var10001 = false;
                  break label163;
               }

               if (var48 == null) {
                  return;
               }

               Iterator var40;
               Map var42;
               try {
                  SAXParserFactory var46 = SAXParserFactory.newInstance();
                  var46.setValidating(false);
                  var46.setNamespaceAware(true);
                  SAXParser var47 = var46.newSAXParser();
                  ServiceEventMessageParser var41 = new ServiceEventMessageParser();
                  StringReader var39 = new StringReader(var51.getBody());
                  InputSource var52 = new InputSource(var39);
                  var47.parse(var52, var41);
                  var42 = var41.getChangedStateVars();
                  var40 = var42.keySet().iterator();
               } catch (IOException var15) {
                  var53 = var15;
                  var10001 = false;
                  break label178;
               } catch (Exception var16) {
                  var10000 = var16;
                  var10001 = false;
                  break label163;
               }

               while(true) {
                  try {
                     if (!var40.hasNext()) {
                        return;
                     }
                  } catch (IOException var13) {
                     var53 = var13;
                     var10001 = false;
                     break label178;
                  } catch (Exception var14) {
                     var10000 = var14;
                     var10001 = false;
                     break;
                  }

                  try {
                     var7 = (String)var40.next();
                     var43 = (String)var42.get(var7);
                     var48.handler.handleStateVariableEvent(var7, var43);
                  } catch (IOException var11) {
                     var53 = var11;
                     var10001 = false;
                     break label178;
                  } catch (Exception var12) {
                     var10000 = var12;
                     var10001 = false;
                     break;
                  }
               }
            }

            Exception var49 = var10000;
            ServicesEventing.log.log(Level.SEVERE, "Unexpected error during client processing thread", var49);
            return;
         }

         IOException var50 = var53;
         ServicesEventing.log.log(Level.SEVERE, "IO Exception during client processing thread", var50);
      }
   }

   private class Subscription {
      private ServiceEventHandler handler;
      private ServiceEventSubscription sub;

      private Subscription() {
         this.sub = null;
         this.handler = null;
      }

      // $FF: synthetic method
      Subscription(ServicesEventing.Subscription var2) {
         this();
      }

      // $FF: synthetic method
      static void access$2(ServicesEventing.Subscription var0, ServiceEventHandler var1) {
         var0.handler = var1;
      }

      // $FF: synthetic method
      static void access$3(ServicesEventing.Subscription var0, ServiceEventSubscription var1) {
         var0.sub = var1;
      }
   }
}
