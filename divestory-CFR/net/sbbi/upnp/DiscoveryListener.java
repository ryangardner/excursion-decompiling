/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
import net.sbbi.upnp.DiscoveryResultsHandler;
import net.sbbi.upnp.HttpResponse;

public class DiscoveryListener
implements Runnable {
    private static final int DEFAULT_TIMEOUT = 250;
    private static boolean MATCH_IP;
    private static final Logger log;
    private static final DiscoveryListener singleton;
    private final Object REGISTRATION_PROCESS = new Object();
    private boolean daemon = true;
    private boolean inService = false;
    private DatagramPacket input;
    private Map registeredHandlers = new HashMap();
    private MulticastSocket skt;

    static {
        log = Logger.getLogger(DiscoveryListener.class.getName());
        MATCH_IP = true;
        String string2 = System.getProperty("net.sbbi.upnp.ddos.matchip");
        if (string2 != null && string2.equals("false")) {
            MATCH_IP = false;
        }
        singleton = new DiscoveryListener();
    }

    private DiscoveryListener() {
    }

    public static final DiscoveryListener getInstance() {
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
            object2 = log;
            StringBuilder stringBuilder = new StringBuilder("Skipping uncompliant HTTP message ");
            stringBuilder.append((String)object3);
            ((Logger)object2).fine(stringBuilder.toString());
            return;
        }
        String string2 = ((HttpResponse)object).getHeader();
        if (string2 != null && string2.startsWith("HTTP/1.1 200 OK") && ((HttpResponse)object).getHTTPHeaderField("st") != null) {
            string2 = ((HttpResponse)object).getHTTPHeaderField("location");
            if (string2 != null && string2.trim().length() != 0) {
                Object object4;
                object3 = new URL(string2);
                if (MATCH_IP && !((InetAddress)object2).equals(object4 = InetAddress.getByName(((URL)object3).getHost()))) {
                    object = log;
                    object3 = new StringBuilder("Discovery message sender IP ");
                    ((StringBuilder)object3).append(object2);
                    ((StringBuilder)object3).append(" does not match device description IP ");
                    ((StringBuilder)object3).append(object4);
                    ((StringBuilder)object3).append(" skipping device, set the net.sbbi.upnp.ddos.matchip system property");
                    ((StringBuilder)object3).append(" to false to avoid this check");
                    ((Logger)object).warning(((StringBuilder)object3).toString());
                    return;
                }
                object4 = log;
                object2 = new StringBuilder("Processing ");
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append(" device description location");
                ((Logger)object4).fine(((StringBuilder)object2).toString());
                string2 = ((HttpResponse)object).getHTTPHeaderField("st");
                if (string2 != null && string2.trim().length() != 0) {
                    object2 = ((HttpResponse)object).getHTTPHeaderField("usn");
                    if (object2 != null && ((String)object2).trim().length() != 0) {
                        object4 = ((HttpResponse)object).getHTTPFieldElement("Cache-Control", "max-age");
                        if (object4 != null && ((String)object4).trim().length() != 0) {
                            String string3 = ((HttpResponse)object).getHTTPHeaderField("server");
                            if (string3 != null && string3.trim().length() != 0) {
                                int n = ((String)object2).indexOf("::");
                                object = n != -1 ? ((String)object2).substring(0, n) : object2;
                                Object object5 = this.REGISTRATION_PROCESS;
                                synchronized (object5) {
                                    Object object6 = (Set)this.registeredHandlers.get(string2);
                                    if (object6 == null) return;
                                    object6 = object6.iterator();
                                    do {
                                        if (!object6.hasNext()) {
                                            return;
                                        }
                                        ((DiscoveryResultsHandler)object6.next()).discoveredDevice((String)object2, (String)object, string2, (String)object4, (URL)object3, string3);
                                    } while (true);
                                }
                            }
                            log.fine("Skipping SSDP message, missing HTTP header 'server' field");
                            return;
                        }
                        log.fine("Skipping SSDP message, missing HTTP header 'max-age' field");
                        return;
                    }
                    log.fine("Skipping SSDP message, missing HTTP header 'usn' field");
                    return;
                }
                log.fine("Skipping SSDP message, missing HTTP header 'st' field");
                return;
            }
            log.fine("Skipping SSDP message, missing HTTP header 'location' field");
            return;
        }
        object2 = log;
        object = new StringBuilder("Skipping uncompliant HTTP message ");
        ((StringBuilder)object).append((String)object3);
        ((Logger)object2).fine(((StringBuilder)object).toString());
        return;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void startDevicesListenerThread() throws IOException {
        var1_1 = DiscoveryListener.singleton;
        synchronized (var1_1) {
            if (this.inService != false) return;
            this.startMultiCastSocket();
            var2_2 = new Thread((Runnable)this, "DiscoveryListener daemon");
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
        Object object = System.getProperty("net.sbbi.upnp.Discovery.bindPort");
        int n = object != null ? Integer.parseInt((String)object) : 1901;
        this.skt = object = new MulticastSocket(null);
        ((DatagramSocket)object).bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), n));
        this.skt.setTimeToLive(4);
        this.skt.setSoTimeout(250);
        this.skt.joinGroup(InetAddress.getByName("239.255.255.250"));
        this.input = new DatagramPacket(new byte[2048], 2048);
    }

    private void stopDevicesListenerThread() {
        DiscoveryListener discoveryListener = singleton;
        synchronized (discoveryListener) {
            this.inService = false;
            return;
        }
    }

    public void registerResultsHandler(DiscoveryResultsHandler discoveryResultsHandler, String string2) throws IOException {
        Object object = this.REGISTRATION_PROCESS;
        synchronized (object) {
            HashSet<DiscoveryResultsHandler> hashSet;
            if (!this.inService) {
                this.startDevicesListenerThread();
            }
            HashSet<DiscoveryResultsHandler> hashSet2 = hashSet = (HashSet<DiscoveryResultsHandler>)this.registeredHandlers.get(string2);
            if (hashSet == null) {
                hashSet2 = new HashSet<DiscoveryResultsHandler>();
                this.registeredHandlers.put(string2, hashSet2);
            }
            hashSet2.add(discoveryResultsHandler);
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    public void run() {
        if (!Thread.currentThread().getName().equals("DiscoveryListener daemon")) throw new RuntimeException("No right to call this method");
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
                log.log(Level.SEVERE, "Fatal Error during UPNP DiscoveryListener messages listening thread, thread will exit", exception);
                this.inService = false;
            }
            catch (IOException iOException) {
                log.log(Level.SEVERE, "IO Exception during UPNP DiscoveryListener messages listening thread", iOException);
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

    public void unRegisterResultsHandler(DiscoveryResultsHandler discoveryResultsHandler, String string2) {
        Object object = this.REGISTRATION_PROCESS;
        synchronized (object) {
            Set set = (Set)this.registeredHandlers.get(string2);
            if (set != null) {
                set.remove(discoveryResultsHandler);
                if (set.size() == 0) {
                    this.registeredHandlers.remove(string2);
                }
            }
            if (this.registeredHandlers.size() != 0) return;
            this.stopDevicesListenerThread();
            return;
        }
    }
}

