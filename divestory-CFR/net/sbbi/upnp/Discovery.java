/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sbbi.upnp.DiscoveryListener;
import net.sbbi.upnp.DiscoveryResultsHandler;
import net.sbbi.upnp.devices.UPNPRootDevice;

public class Discovery {
    public static final String ALL_DEVICES = "ssdp:all";
    public static final int DEFAULT_MX = 3;
    public static final String DEFAULT_SEARCH = "ssdp:all";
    public static final int DEFAULT_SSDP_SEARCH_PORT = 1901;
    public static final int DEFAULT_TIMEOUT = 1500;
    public static final int DEFAULT_TTL = 4;
    public static final String ROOT_DEVICES = "upnp:rootdevice";
    public static final String SSDP_IP = "239.255.255.250";
    public static final int SSDP_PORT = 1900;
    private static final Logger log = Logger.getLogger(Discovery.class.getName());

    public static UPNPRootDevice[] discover() throws IOException {
        return Discovery.discover(1500, 4, 3, "ssdp:all");
    }

    public static UPNPRootDevice[] discover(int n, int n2, int n3, String string2) throws IOException {
        return Discovery.discoverDevices(n, n2, n3, string2, null);
    }

    public static UPNPRootDevice[] discover(int n, int n2, int n3, String string2, NetworkInterface networkInterface) throws IOException {
        return Discovery.discoverDevices(n, n2, n3, string2, networkInterface);
    }

    public static UPNPRootDevice[] discover(int n, String string2) throws IOException {
        return Discovery.discover(n, 4, 3, string2);
    }

    public static UPNPRootDevice[] discover(String string2) throws IOException {
        return Discovery.discover(1500, 4, 3, string2);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private static UPNPRootDevice[] discoverDevices(int var0, int var1_1, int var2_2, String var3_3, NetworkInterface var4_4) throws IOException {
        block11 : {
            block10 : {
                if (var3_3 == null) throw new IllegalArgumentException("Illegal searchTarget");
                if (var3_3.trim().length() == 0) throw new IllegalArgumentException("Illegal searchTarget");
                var5_6 = new HashMap<K, V>();
                var6_7 = new DiscoveryResultsHandler(){

                    @Override
                    public void discoveredDevice(String object, String object2, String object3, String string2, URL uRL, String charSequence) {
                        object3 = Map.this;
                        synchronized (object3) {
                            boolean bl = Map.this.containsKey(object);
                            if (bl) return;
                            try {
                                UPNPRootDevice uPNPRootDevice = new UPNPRootDevice(uRL, string2, (String)charSequence, (String)object, (String)object2);
                                Map.this.put(object, uPNPRootDevice);
                            }
                            catch (Exception exception) {
                                object = log;
                                object2 = Level.SEVERE;
                                charSequence = new StringBuilder("Error occured during upnp root device object creation from location ");
                                ((StringBuilder)charSequence).append(uRL);
                                ((Logger)object).log((Level)object2, ((StringBuilder)charSequence).toString(), exception);
                            }
                            return;
                        }
                    }
                };
                DiscoveryListener.getInstance().registerResultsHandler(var6_7, (String)var3_3);
                if (var4_4 == null) {
                    var7_8 = NetworkInterface.getNetworkInterfaces();
                    while (var7_8.hasMoreElements()) {
                        var4_4 = var7_8.nextElement().getInetAddresses();
                        while (var4_4.hasMoreElements()) {
                            var8_9 = var4_4.nextElement();
                            if (!(var8_9 instanceof Inet4Address) || var8_9.isLoopbackAddress()) continue;
                            Discovery.sendSearchMessage(var8_9, var1_1, var2_2, (String)var3_3);
                        }
                    }
                } else {
                    var4_4 = var4_4.getInetAddresses();
lbl18: // 3 sources:
                    do {
                        if (var4_4.hasMoreElements()) break block10;
                        break;
                    } while (true);
                }
                var9_10 = var0;
                try {
                    Thread.sleep(var9_10);
                }
                catch (InterruptedException var4_5) {
                    // empty catch block
                }
                DiscoveryListener.getInstance().unRegisterResultsHandler(var6_7, (String)var3_3);
                if (var5_6.size() == 0) {
                    return null;
                }
                break block11;
            }
            var7_8 = var4_4.nextElement();
            if (!(var7_8 instanceof Inet4Address) || var7_8.isLoopbackAddress()) ** GOTO lbl18
            Discovery.sendSearchMessage((InetAddress)var7_8, var1_1, var2_2, (String)var3_3);
            ** while (true)
        }
        var0 = 0;
        var3_3 = new UPNPRootDevice[var5_6.size()];
        var4_4 = var5_6.values().iterator();
        do {
            if (!var4_4.hasNext()) {
                return var3_3;
            }
            var3_3[var0] = (UPNPRootDevice)var4_4.next();
            ++var0;
        } while (true);
    }

    public static void sendSearchMessage(InetAddress arrby, int n, int n2, String object) throws IOException {
        Object object2 = System.getProperty("net.sbbi.upnp.Discovery.bindPort");
        int n3 = object2 != null ? Integer.parseInt((String)object2) : 1901;
        object2 = new InetSocketAddress(InetAddress.getByName(SSDP_IP), 1900);
        MulticastSocket multicastSocket = new MulticastSocket(null);
        multicastSocket.bind(new InetSocketAddress((InetAddress)arrby, n3));
        multicastSocket.setTimeToLive(n);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("M-SEARCH * HTTP/1.1\r\n");
        stringBuffer.append("HOST: 239.255.255.250:1900\r\n");
        stringBuffer.append("MAN: \"ssdp:discover\"\r\n");
        stringBuffer.append("MX: ");
        stringBuffer.append(n2);
        stringBuffer.append("\r\n");
        stringBuffer.append("ST: ");
        stringBuffer.append((String)object);
        stringBuffer.append("\r\n");
        stringBuffer.append("\r\n");
        object = log;
        StringBuilder stringBuilder = new StringBuilder("Sending discovery message on 239.255.255.250:1900 multicast address form ip ");
        stringBuilder.append(arrby.getHostAddress());
        stringBuilder.append(":\n");
        stringBuilder.append(stringBuffer.toString());
        ((Logger)object).fine(stringBuilder.toString());
        arrby = stringBuffer.toString().getBytes();
        multicastSocket.send(new DatagramPacket(arrby, arrby.length, (SocketAddress)object2));
        multicastSocket.disconnect();
        multicastSocket.close();
    }

}

