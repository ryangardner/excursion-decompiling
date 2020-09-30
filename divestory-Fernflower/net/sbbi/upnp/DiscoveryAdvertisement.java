package net.sbbi.upnp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscoveryAdvertisement implements Runnable {
   private static final int DEFAULT_TIMEOUT = 250;
   public static final int EVENT_SSDP_ALIVE = 0;
   public static final int EVENT_SSDP_BYE_BYE = 1;
   private static boolean MATCH_IP = true;
   private static final String NTS_SSDP_ALIVE = "ssdp:alive";
   private static final String NTS_SSDP_BYE_BYE = "ssdp:byebye";
   private static final String NT_ALL_EVENTS = "DiscoveryAdvertisement:nt:allevents";
   private static final Logger log = Logger.getLogger(DiscoveryAdvertisement.class.getName());
   private static final DiscoveryAdvertisement singleton;
   private final Object REGISTRATION_PROCESS = new Object();
   private Map USNPerIP = new HashMap();
   private Map aliveRegistered = new HashMap();
   private Map byeByeRegistered = new HashMap();
   private boolean daemon = true;
   private boolean inService = false;
   private DatagramPacket input;
   private MulticastSocket skt;

   static {
      String var0 = System.getProperty("net.sbbi.upnp.ddos.matchip");
      if (var0 != null && var0.equals("false")) {
         MATCH_IP = false;
      }

      singleton = new DiscoveryAdvertisement();
   }

   private DiscoveryAdvertisement() {
   }

   public static final DiscoveryAdvertisement getInstance() {
      return singleton;
   }

   private void listenBroadCast() throws IOException {
      this.skt.receive(this.input);
      InetAddress var1 = this.input.getAddress();
      String var2 = new String(this.input.getData(), this.input.getOffset(), this.input.getLength());

      StringBuilder var450;
      HttpResponse var3;
      Logger var4;
      try {
         var3 = new HttpResponse(var2);
      } catch (IllegalArgumentException var429) {
         var4 = log;
         var450 = new StringBuilder("Skipping uncompliant HTTP message ");
         var450.append(var2);
         var4.fine(var450.toString());
         return;
      }

      Throwable var454;
      Throwable var10000;
      boolean var10001;
      label5391: {
         label5407: {
            String var455 = var3.getHeader();
            if (var455 != null && var455.startsWith("NOTIFY")) {
               log.fine(var2);
               var455 = var3.getHTTPHeaderField("nts");
               if (var455 != null && var455.trim().length() != 0) {
                  String var451;
                  int var7;
                  if (var455.equals("ssdp:alive")) {
                     var2 = var3.getHTTPHeaderField("location");
                     if (var2 == null || var2.trim().length() == 0) {
                        log.fine("Skipping SSDP message, missing HTTP header 'location' field");
                        return;
                     }

                     URL var456 = new URL(var2);
                     if (MATCH_IP) {
                        InetAddress var452 = InetAddress.getByName(var456.getHost());
                        if (!var1.equals(var452)) {
                           var4 = log;
                           StringBuilder var460 = new StringBuilder("Discovery message sender IP ");
                           var460.append(var1);
                           var460.append(" does not match device description IP ");
                           var460.append(var452);
                           var460.append(" skipping message, set the net.sbbi.upnp.ddos.matchip system property");
                           var460.append(" to false to avoid this check");
                           var4.warning(var460.toString());
                           return;
                        }
                     }

                     String var462 = var3.getHTTPHeaderField("nt");
                     if (var462 == null || var462.trim().length() == 0) {
                        log.fine("Skipping SSDP message, missing HTTP header 'nt' field");
                        return;
                     }

                     String var459 = var3.getHTTPFieldElement("Cache-Control", "max-age");
                     if (var459 == null || var459.trim().length() == 0) {
                        log.fine("Skipping SSDP message, missing HTTP header 'max-age' field");
                        return;
                     }

                     var2 = var3.getHTTPHeaderField("usn");
                     if (var2 == null || var2.trim().length() == 0) {
                        log.fine("Skipping SSDP message, missing HTTP header 'usn' field");
                        return;
                     }

                     this.USNPerIP.put(var2, var1);
                     var7 = var2.indexOf("::");
                     if (var7 != -1) {
                        var451 = var2.substring(0, var7);
                     } else {
                        var451 = var2;
                     }

                     Object var453 = this.REGISTRATION_PROCESS;
                     synchronized(var453){}

                     Set var8;
                     try {
                        var8 = (Set)this.aliveRegistered.get("DiscoveryAdvertisement:nt:allevents");
                     } catch (Throwable var438) {
                        var10000 = var438;
                        var10001 = false;
                        break label5391;
                     }

                     Iterator var463;
                     if (var8 != null) {
                        try {
                           var463 = var8.iterator();
                        } catch (Throwable var437) {
                           var10000 = var437;
                           var10001 = false;
                           break label5391;
                        }

                        while(true) {
                           try {
                              if (!var463.hasNext()) {
                                 break;
                              }
                           } catch (Throwable var447) {
                              var10000 = var447;
                              var10001 = false;
                              break label5391;
                           }

                           try {
                              ((DiscoveryEventHandler)var463.next()).eventSSDPAlive(var2, var451, var462, var459, var456);
                           } catch (Throwable var436) {
                              var10000 = var436;
                              var10001 = false;
                              break label5391;
                           }
                        }
                     }

                     try {
                        var8 = (Set)this.aliveRegistered.get(var462);
                     } catch (Throwable var435) {
                        var10000 = var435;
                        var10001 = false;
                        break label5391;
                     }

                     if (var8 != null) {
                        try {
                           var463 = var8.iterator();
                        } catch (Throwable var434) {
                           var10000 = var434;
                           var10001 = false;
                           break label5391;
                        }

                        while(true) {
                           try {
                              if (!var463.hasNext()) {
                                 break;
                              }
                           } catch (Throwable var446) {
                              var10000 = var446;
                              var10001 = false;
                              break label5391;
                           }

                           try {
                              ((DiscoveryEventHandler)var463.next()).eventSSDPAlive(var2, var451, var462, var459, var456);
                           } catch (Throwable var433) {
                              var10000 = var433;
                              var10001 = false;
                              break label5391;
                           }
                        }
                     }

                     try {
                        ;
                     } catch (Throwable var432) {
                        var10000 = var432;
                        var10001 = false;
                        break label5391;
                     }
                  } else if (var455.equals("ssdp:byebye")) {
                     var2 = var3.getHTTPHeaderField("usn");
                     if (var2 == null || var2.trim().length() == 0) {
                        log.fine("Skipping SSDP message, missing HTTP header 'usn' field");
                        return;
                     }

                     var455 = var3.getHTTPHeaderField("nt");
                     if (var455 == null || var455.trim().length() == 0) {
                        log.fine("Skipping SSDP message, missing HTTP header 'nt' field");
                        return;
                     }

                     InetAddress var457 = (InetAddress)this.USNPerIP.get(var2);
                     if (var457 != null && !var457.equals(var1)) {
                        return;
                     }

                     var7 = var2.indexOf("::");
                     if (var7 != -1) {
                        var451 = var2.substring(0, var7);
                     } else {
                        var451 = var2;
                     }

                     Object var458 = this.REGISTRATION_PROCESS;
                     synchronized(var458){}

                     Set var6;
                     try {
                        var6 = (Set)this.byeByeRegistered.get("DiscoveryAdvertisement:nt:allevents");
                     } catch (Throwable var445) {
                        var10000 = var445;
                        var10001 = false;
                        break label5407;
                     }

                     Iterator var461;
                     if (var6 != null) {
                        try {
                           var461 = var6.iterator();
                        } catch (Throwable var444) {
                           var10000 = var444;
                           var10001 = false;
                           break label5407;
                        }

                        while(true) {
                           try {
                              if (!var461.hasNext()) {
                                 break;
                              }
                           } catch (Throwable var449) {
                              var10000 = var449;
                              var10001 = false;
                              break label5407;
                           }

                           try {
                              ((DiscoveryEventHandler)var461.next()).eventSSDPByeBye(var2, var451, var455);
                           } catch (Throwable var443) {
                              var10000 = var443;
                              var10001 = false;
                              break label5407;
                           }
                        }
                     }

                     try {
                        var6 = (Set)this.byeByeRegistered.get(var455);
                     } catch (Throwable var442) {
                        var10000 = var442;
                        var10001 = false;
                        break label5407;
                     }

                     if (var6 != null) {
                        try {
                           var461 = var6.iterator();
                        } catch (Throwable var441) {
                           var10000 = var441;
                           var10001 = false;
                           break label5407;
                        }

                        while(true) {
                           try {
                              if (!var461.hasNext()) {
                                 break;
                              }
                           } catch (Throwable var448) {
                              var10000 = var448;
                              var10001 = false;
                              break label5407;
                           }

                           try {
                              ((DiscoveryEventHandler)var461.next()).eventSSDPByeBye(var2, var451, var455);
                           } catch (Throwable var440) {
                              var10000 = var440;
                              var10001 = false;
                              break label5407;
                           }
                        }
                     }

                     try {
                        ;
                     } catch (Throwable var439) {
                        var10000 = var439;
                        var10001 = false;
                        break label5407;
                     }
                  } else {
                     Logger var5 = log;
                     var450 = new StringBuilder("Unvalid NTS field value (");
                     var450.append(var455);
                     var450.append(") received in NOTIFY message :");
                     var450.append(var2);
                     var5.warning(var450.toString());
                  }
               } else {
                  log.fine("Skipping SSDP message, missing HTTP header 'ntsField' field");
               }
            }

            return;
         }

         while(true) {
            var454 = var10000;

            try {
               throw var454;
            } catch (Throwable var431) {
               var10000 = var431;
               var10001 = false;
               continue;
            }
         }
      }

      while(true) {
         var454 = var10000;

         try {
            throw var454;
         } catch (Throwable var430) {
            var10000 = var430;
            var10001 = false;
            continue;
         }
      }
   }

   private void startDevicesListenerThread() throws IOException {
      // $FF: Couldn't be decompiled
   }

   private void startMultiCastSocket() throws IOException {
      MulticastSocket var1 = new MulticastSocket((SocketAddress)null);
      this.skt = var1;
      var1.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 1900));
      this.skt.setTimeToLive(4);
      this.skt.setSoTimeout(250);
      this.skt.joinGroup(InetAddress.getByName("239.255.255.250"));
      this.input = new DatagramPacket(new byte[2048], 2048);
   }

   private void stopDevicesListenerThread() {
      // $FF: Couldn't be decompiled
   }

   public void registerEvent(int var1, String var2, DiscoveryEventHandler var3) throws IOException {
      Object var4 = this.REGISTRATION_PROCESS;
      synchronized(var4){}

      Throwable var10000;
      boolean var10001;
      label846: {
         try {
            if (!this.inService) {
               this.startDevicesListenerThread();
            }
         } catch (Throwable var116) {
            var10000 = var116;
            var10001 = false;
            break label846;
         }

         String var5 = var2;
         if (var2 == null) {
            var5 = "DiscoveryAdvertisement:nt:allevents";
         }

         Set var6;
         Object var117;
         if (var1 == 0) {
            try {
               var6 = (Set)this.aliveRegistered.get(var5);
            } catch (Throwable var115) {
               var10000 = var115;
               var10001 = false;
               break label846;
            }

            var117 = var6;
            if (var6 == null) {
               try {
                  var117 = new HashSet();
                  this.aliveRegistered.put(var5, var117);
               } catch (Throwable var114) {
                  var10000 = var114;
                  var10001 = false;
                  break label846;
               }
            }

            try {
               ((Set)var117).add(var3);
            } catch (Throwable var113) {
               var10000 = var113;
               var10001 = false;
               break label846;
            }
         } else {
            if (var1 != 1) {
               try {
                  IllegalArgumentException var119 = new IllegalArgumentException("Unknown notificationEvent type");
                  throw var119;
               } catch (Throwable var109) {
                  var10000 = var109;
                  var10001 = false;
                  break label846;
               }
            }

            try {
               var6 = (Set)this.byeByeRegistered.get(var5);
            } catch (Throwable var112) {
               var10000 = var112;
               var10001 = false;
               break label846;
            }

            var117 = var6;
            if (var6 == null) {
               try {
                  var117 = new HashSet();
                  this.byeByeRegistered.put(var5, var117);
               } catch (Throwable var111) {
                  var10000 = var111;
                  var10001 = false;
                  break label846;
               }
            }

            try {
               ((Set)var117).add(var3);
            } catch (Throwable var110) {
               var10000 = var110;
               var10001 = false;
               break label846;
            }
         }

         label818:
         try {
            return;
         } catch (Throwable var108) {
            var10000 = var108;
            var10001 = false;
            break label818;
         }
      }

      while(true) {
         Throwable var118 = var10000;

         try {
            throw var118;
         } catch (Throwable var107) {
            var10000 = var107;
            var10001 = false;
            continue;
         }
      }
   }

   public void run() {
      if (!Thread.currentThread().getName().equals("DiscoveryAdvertisement daemon")) {
         throw new RuntimeException("No right to call this method");
      } else {
         this.inService = true;

         while(this.inService) {
            try {
               this.listenBroadCast();
            } catch (SocketTimeoutException var3) {
            } catch (IOException var4) {
               log.log(Level.SEVERE, "IO Exception during UPNP DiscoveryAdvertisement messages listening thread", var4);
            } catch (Exception var5) {
               log.log(Level.SEVERE, "Fatal Error during UPNP DiscoveryAdvertisement messages listening thread, thread will exit", var5);
               this.inService = false;
               this.aliveRegistered.clear();
               this.byeByeRegistered.clear();
               this.USNPerIP.clear();
            }
         }

         try {
            this.skt.leaveGroup(InetAddress.getByName("239.255.255.250"));
            this.skt.close();
         } catch (Exception var2) {
         }

      }
   }

   public void setDaemon(boolean var1) {
      this.daemon = var1;
   }

   public void unRegisterEvent(int var1, String var2, DiscoveryEventHandler var3) {
      Object var4 = this.REGISTRATION_PROCESS;
      synchronized(var4){}
      String var5 = var2;
      if (var2 == null) {
         var5 = "DiscoveryAdvertisement:nt:allevents";
      }

      Throwable var10000;
      boolean var10001;
      label666: {
         Set var78;
         if (var1 == 0) {
            try {
               var78 = (Set)this.aliveRegistered.get(var5);
            } catch (Throwable var76) {
               var10000 = var76;
               var10001 = false;
               break label666;
            }

            if (var78 != null) {
               try {
                  var78.remove(var3);
                  if (var78.size() == 0) {
                     this.aliveRegistered.remove(var5);
                  }
               } catch (Throwable var77) {
                  var10000 = var77;
                  var10001 = false;
                  break label666;
               }
            }
         } else {
            if (var1 != 1) {
               try {
                  IllegalArgumentException var80 = new IllegalArgumentException("Unknown notificationEvent type");
                  throw var80;
               } catch (Throwable var73) {
                  var10000 = var73;
                  var10001 = false;
                  break label666;
               }
            }

            try {
               var78 = (Set)this.byeByeRegistered.get(var5);
            } catch (Throwable var75) {
               var10000 = var75;
               var10001 = false;
               break label666;
            }

            if (var78 != null) {
               try {
                  var78.remove(var3);
                  if (var78.size() == 0) {
                     this.byeByeRegistered.remove(var5);
                  }
               } catch (Throwable var74) {
                  var10000 = var74;
                  var10001 = false;
                  break label666;
               }
            }
         }

         try {
            if (this.aliveRegistered.size() == 0 && this.byeByeRegistered.size() == 0) {
               this.stopDevicesListenerThread();
            }
         } catch (Throwable var72) {
            var10000 = var72;
            var10001 = false;
            break label666;
         }

         label640:
         try {
            return;
         } catch (Throwable var71) {
            var10000 = var71;
            var10001 = false;
            break label640;
         }
      }

      while(true) {
         Throwable var79 = var10000;

         try {
            throw var79;
         } catch (Throwable var70) {
            var10000 = var70;
            var10001 = false;
            continue;
         }
      }
   }
}
