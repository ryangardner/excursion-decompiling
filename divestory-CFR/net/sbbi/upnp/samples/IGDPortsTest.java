/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.samples;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import net.sbbi.upnp.devices.UPNPRootDevice;
import net.sbbi.upnp.impls.InternetGatewayDevice;
import net.sbbi.upnp.messages.ActionResponse;
import net.sbbi.upnp.messages.UPNPResponseException;

public class IGDPortsTest {
    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    public static final void main(String[] object) {
        Object object2;
        Object object3;
        try {
            System.out.println("Looking for Internet Gateway Device...");
            object = InternetGatewayDevice.getDevices(5000);
            if (object == null) {
                System.out.println("Unable to find IGD on your network");
                return;
            }
            int n = 0;
            block5 : do {
                if (n >= ((String[])object).length) {
                    System.out.println("\nDone.");
                    return;
                }
                object2 = object[n];
                object3 = System.out;
                Object object4 = new StringBuilder("\tFound device ");
                ((StringBuilder)object4).append(((InternetGatewayDevice)object2).getIGDRootDevice().getModelName());
                ((PrintStream)object3).println(((StringBuilder)object4).toString());
                object3 = System.out;
                object4 = new StringBuilder("External IP address: ");
                ((StringBuilder)object4).append(((InternetGatewayDevice)object2).getExternalIPAddress());
                ((PrintStream)object3).println(((StringBuilder)object4).toString());
                object3 = ((InternetGatewayDevice)object2).getNatTableSize();
                object4 = System.out;
                StringBuilder stringBuilder = new StringBuilder("NAT table size is ");
                stringBuilder.append(object3);
                ((PrintStream)object4).println(stringBuilder.toString());
                int n2 = 0;
                do {
                    if (n2 >= (Integer)object3) {
                        object4 = System.out;
                        object3 = new StringBuilder("\nTrying to map dummy port ");
                        ((StringBuilder)object3).append(9090);
                        ((StringBuilder)object3).append("...");
                        ((PrintStream)object4).println(((StringBuilder)object3).toString());
                        object3 = InetAddress.getLocalHost().getHostAddress();
                        if (((InternetGatewayDevice)object2).addPortMapping("Some mapping description", null, 9090, 9090, (String)object3, 0, "TCP")) {
                            object4 = System.out;
                            stringBuilder = new StringBuilder("Port ");
                            stringBuilder.append(9090);
                            stringBuilder.append(" mapped to ");
                            stringBuilder.append((String)object3);
                            ((PrintStream)object4).println(stringBuilder.toString());
                            object4 = System.out;
                            object3 = new StringBuilder("Current mappings count is ");
                            ((StringBuilder)object3).append(((InternetGatewayDevice)object2).getNatMappingsCount());
                            ((PrintStream)object4).println(((StringBuilder)object3).toString());
                            if (((InternetGatewayDevice)object2).getSpecificPortMappingEntry(null, 9090, "TCP") != null) {
                                object3 = System.out;
                                object4 = new StringBuilder("Port ");
                                ((StringBuilder)object4).append(9090);
                                ((StringBuilder)object4).append(" mapping confirmation received from device");
                                ((PrintStream)object3).println(((StringBuilder)object4).toString());
                            }
                            System.out.println("Delete dummy port mapping...");
                            if (((InternetGatewayDevice)object2).deletePortMapping(null, 9090, "TCP")) {
                                object3 = System.out;
                                object2 = new StringBuilder("Port ");
                                ((StringBuilder)object2).append(9090);
                                ((StringBuilder)object2).append(" unmapped");
                                ((PrintStream)object3).println(((StringBuilder)object2).toString());
                            }
                        }
                        ++n;
                        continue block5;
                    }
                    object4 = ((InternetGatewayDevice)object2).getGenericPortMappingEntry(n2);
                    PrintStream printStream = System.out;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(++n2);
                    stringBuilder.append(".\t");
                    stringBuilder.append(object4);
                    printStream.println(stringBuilder.toString());
                } while (true);
                break;
            } while (true);
        }
        catch (UPNPResponseException uPNPResponseException) {
            object2 = System.err;
            object = new StringBuilder("UPNP device unhappy ");
            ((StringBuilder)object).append(uPNPResponseException.getDetailErrorCode());
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(uPNPResponseException.getDetailErrorDescription());
            ((PrintStream)object2).println(((StringBuilder)object).toString());
            return;
        }
        catch (IOException iOException) {
            object = System.err;
            object3 = new StringBuilder("IOException occured during discovery or ports mapping ");
            ((StringBuilder)object3).append(iOException.getMessage());
            ((PrintStream)object).println(((StringBuilder)object3).toString());
        }
    }
}

