/*
 * Decompiled with CFR <Could not determine version>.
 */
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
import net.sbbi.upnp.DiscoveryEventHandler;
import net.sbbi.upnp.HttpResponse;

public class DiscoveryAdvertisement
implements Runnable {
    private static final int DEFAULT_TIMEOUT = 250;
    public static final int EVENT_SSDP_ALIVE = 0;
    public static final int EVENT_SSDP_BYE_BYE = 1;
    private static boolean MATCH_IP = false;
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
        MATCH_IP = true;
        String string2 = System.getProperty("net.sbbi.upnp.ddos.matchip");
        if (string2 != null && string2.equals("false")) {
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
        Object object;
        this.skt.receive(this.input);
        Object object2 = this.input.getAddress();
        Object object3 = new String(this.input.getData(), this.input.getOffset(), this.input.getLength());
        try {
            object = new HttpResponse((String)object3);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Logger logger = log;
            StringBuilder stringBuilder = new StringBuilder("Skipping uncompliant HTTP message ");
            stringBuilder.append((String)object3);
            logger.fine(stringBuilder.toString());
            return;
        }
        Object object4 = ((HttpResponse)object).getHeader();
        if (object4 == null) return;
        if (!((String)object4).startsWith("NOTIFY")) return;
        log.fine((String)object3);
        object4 = ((HttpResponse)object).getHTTPHeaderField("nts");
        if (object4 != null && ((String)object4).trim().length() != 0) {
            if (((String)object4).equals(NTS_SSDP_ALIVE)) {
                object3 = ((HttpResponse)object).getHTTPHeaderField("location");
                if (object3 != null && ((String)object3).trim().length() != 0) {
                    object4 = new URL((String)object3);
                    if (MATCH_IP && !((InetAddress)object2).equals(object3 = InetAddress.getByName(((URL)object4).getHost()))) {
                        object4 = log;
                        StringBuilder stringBuilder = new StringBuilder("Discovery message sender IP ");
                        stringBuilder.append(object2);
                        stringBuilder.append(" does not match device description IP ");
                        stringBuilder.append(object3);
                        stringBuilder.append(" skipping message, set the net.sbbi.upnp.ddos.matchip system property");
                        stringBuilder.append(" to false to avoid this check");
                        ((Logger)object4).warning(stringBuilder.toString());
                        return;
                    }
                    String string2 = ((HttpResponse)object).getHTTPHeaderField("nt");
                    if (string2 != null && string2.trim().length() != 0) {
                        String string3 = ((HttpResponse)object).getHTTPFieldElement("Cache-Control", "max-age");
                        if (string3 != null && string3.trim().length() != 0) {
                            object3 = ((HttpResponse)object).getHTTPHeaderField("usn");
                            if (object3 != null && ((String)object3).trim().length() != 0) {
                                this.USNPerIP.put(object3, object2);
                                int n = ((String)object3).indexOf("::");
                                object2 = n != -1 ? ((String)object3).substring(0, n) : object3;
                                object = this.REGISTRATION_PROCESS;
                                synchronized (object) {
                                    Iterator iterator2 = (Set)this.aliveRegistered.get(NT_ALL_EVENTS);
                                    if (iterator2 != null) {
                                        iterator2 = iterator2.iterator();
                                        while (iterator2.hasNext()) {
                                            ((DiscoveryEventHandler)iterator2.next()).eventSSDPAlive((String)object3, (String)object2, string2, string3, (URL)object4);
                                        }
                                    }
                                    if ((iterator2 = (Set)this.aliveRegistered.get(string2)) == null) return;
                                    iterator2 = iterator2.iterator();
                                    do {
                                        if (!iterator2.hasNext()) {
                                            return;
                                        }
                                        ((DiscoveryEventHandler)iterator2.next()).eventSSDPAlive((String)object3, (String)object2, string2, string3, (URL)object4);
                                    } while (true);
                                }
                            }
                            log.fine("Skipping SSDP message, missing HTTP header 'usn' field");
                            return;
                        }
                        log.fine("Skipping SSDP message, missing HTTP header 'max-age' field");
                        return;
                    }
                    log.fine("Skipping SSDP message, missing HTTP header 'nt' field");
                    return;
                }
                log.fine("Skipping SSDP message, missing HTTP header 'location' field");
                return;
            }
            if (!((String)object4).equals(NTS_SSDP_BYE_BYE)) {
                Logger logger = log;
                object2 = new StringBuilder("Unvalid NTS field value (");
                ((StringBuilder)object2).append((String)object4);
                ((StringBuilder)object2).append(") received in NOTIFY message :");
                ((StringBuilder)object2).append((String)object3);
                logger.warning(((StringBuilder)object2).toString());
                return;
            }
            object3 = ((HttpResponse)object).getHTTPHeaderField("usn");
            if (object3 != null && ((String)object3).trim().length() != 0) {
                object4 = ((HttpResponse)object).getHTTPHeaderField("nt");
                if (object4 != null && ((String)object4).trim().length() != 0) {
                    Object object5 = (InetAddress)this.USNPerIP.get(object3);
                    if (object5 != null && !((InetAddress)object5).equals(object2)) {
                        return;
                    }
                    int n = ((String)object3).indexOf("::");
                    object2 = n != -1 ? ((String)object3).substring(0, n) : object3;
                    object5 = this.REGISTRATION_PROCESS;
                    synchronized (object5) {
                        Iterator iterator3 = (Set)this.byeByeRegistered.get(NT_ALL_EVENTS);
                        if (iterator3 != null) {
                            iterator3 = iterator3.iterator();
                            while (iterator3.hasNext()) {
                                ((DiscoveryEventHandler)iterator3.next()).eventSSDPByeBye((String)object3, (String)object2, (String)object4);
                            }
                        }
                        if ((iterator3 = (Set)this.byeByeRegistered.get(object4)) == null) return;
                        iterator3 = iterator3.iterator();
                        do {
                            if (!iterator3.hasNext()) {
                                return;
                            }
                            ((DiscoveryEventHandler)iterator3.next()).eventSSDPByeBye((String)object3, (String)object2, (String)object4);
                        } while (true);
                    }
                }
                log.fine("Skipping SSDP message, missing HTTP header 'nt' field");
                return;
            }
            log.fine("Skipping SSDP message, missing HTTP header 'usn' field");
            return;
        }
        log.fine("Skipping SSDP message, missing HTTP header 'ntsField' field");
        return;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void startDevicesListenerThread() throws IOException {
        var1_1 = DiscoveryAdvertisement.singleton;
        synchronized (var1_1) {
            if (this.inService != false) return;
            this.startMultiCastSocket();
            var2_2 = new Thread((Runnable)this, "DiscoveryAdvertisement daemon");
            var2_2.setDaemon(this.daemon);
            var2_2.start();
            do {
                if (var3_4 = this.inService) ** GOTO lbl13
                try {
                    Thread.sleep(2L);
                    continue;
lbl13: // 1 sources:
                    return;
                }
                catch (InterruptedException var2_3) {
                    continue;
                }
                break;
            } while (true);
        }
    }

    private void startMultiCastSocket() throws IOException {
        MulticastSocket multicastSocket;
        this.skt = multicastSocket = new MulticastSocket(null);
        multicastSocket.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 1900));
        this.skt.setTimeToLive(4);
        this.skt.setSoTimeout(250);
        this.skt.joinGroup(InetAddress.getByName("239.255.255.250"));
        this.input = new DatagramPacket(new byte[2048], 2048);
    }

    private void stopDevicesListenerThread() {
        DiscoveryAdvertisement discoveryAdvertisement = singleton;
        synchronized (discoveryAdvertisement) {
            this.inService = false;
            return;
        }
    }

    public void registerEvent(int n, String hashSet, DiscoveryEventHandler discoveryEventHandler) throws IOException {
        Object object = this.REGISTRATION_PROCESS;
        synchronized (object) {
            if (!this.inService) {
                this.startDevicesListenerThread();
            }
            String string2 = hashSet;
            if (hashSet == null) {
                string2 = NT_ALL_EVENTS;
            }
            if (n == 0) {
                Set set = (Set)this.aliveRegistered.get(string2);
                hashSet = set;
                if (set == null) {
                    hashSet = new HashSet<DiscoveryEventHandler>();
                    this.aliveRegistered.put(string2, hashSet);
                }
                hashSet.add(discoveryEventHandler);
            } else {
                if (n != 1) {
                    hashSet = new HashSet<DiscoveryEventHandler>("Unknown notificationEvent type");
                    throw hashSet;
                }
                Set set = (Set)this.byeByeRegistered.get(string2);
                hashSet = set;
                if (set == null) {
                    hashSet = new HashSet<DiscoveryEventHandler>();
                    this.byeByeRegistered.put(string2, hashSet);
                }
                hashSet.add(discoveryEventHandler);
            }
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    public void run() {
        if (!Thread.currentThread().getName().equals("DiscoveryAdvertisement daemon")) throw new RuntimeException("No right to call this method");
        this.inService = true;
        do {
            if (!this.inService) {
                this.skt.leaveGroup(InetAddress.getByName("239.255.255.250"));
                this.skt.close();
                return;
            }
            try {
                this.listenBroadCast();
            }
            catch (Exception exception) {
                log.log(Level.SEVERE, "Fatal Error during UPNP DiscoveryAdvertisement messages listening thread, thread will exit", exception);
                this.inService = false;
                this.aliveRegistered.clear();
                this.byeByeRegistered.clear();
                this.USNPerIP.clear();
            }
            catch (IOException iOException) {
                log.log(Level.SEVERE, "IO Exception during UPNP DiscoveryAdvertisement messages listening thread", iOException);
            }
            catch (SocketTimeoutException socketTimeoutException) {}
        } while (true);
        catch (Exception exception) {
            return;
        }
    }

    public void setDaemon(boolean bl) {
        this.daemon = bl;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    public void unRegisterEvent(int var1_1, String var2_2, DiscoveryEventHandler var3_4) {
        block10 : {
            var4_5 = this.REGISTRATION_PROCESS;
            // MONITORENTER : var4_5
            var5_6 = var2_2;
            if (var2_2 == null) {
                var5_6 = "DiscoveryAdvertisement:nt:allevents";
            }
            if (var1_1 != 0) ** GOTO lbl16
            var2_2 = (Set)this.aliveRegistered.get(var5_6);
            if (var2_2 != null) {
                var2_2.remove(var3_4);
                if (var2_2.size() == 0) {
                    this.aliveRegistered.remove(var5_6);
                }
            }
            break block10;
lbl16: // 1 sources:
            if (var1_1 != 1) {
                var2_2 = new IllegalArgumentException("Unknown notificationEvent type");
                throw var2_2;
            }
            var2_2 = (Set)this.byeByeRegistered.get(var5_6);
            if (var2_2 != null) {
                var2_2.remove(var3_4);
                if (var2_2.size() == 0) {
                    this.byeByeRegistered.remove(var5_6);
                }
            }
        }
        if (this.aliveRegistered.size() == 0 && this.byeByeRegistered.size() == 0) {
            this.stopDevicesListenerThread();
        }
        // MONITOREXIT : var4_5
        return;
    }
}

