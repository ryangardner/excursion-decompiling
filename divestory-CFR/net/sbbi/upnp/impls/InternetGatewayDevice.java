/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.impls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sbbi.upnp.Discovery;
import net.sbbi.upnp.devices.UPNPDevice;
import net.sbbi.upnp.devices.UPNPRootDevice;
import net.sbbi.upnp.messages.ActionMessage;
import net.sbbi.upnp.messages.ActionResponse;
import net.sbbi.upnp.messages.StateVariableMessage;
import net.sbbi.upnp.messages.StateVariableResponse;
import net.sbbi.upnp.messages.UPNPMessageFactory;
import net.sbbi.upnp.messages.UPNPResponseException;
import net.sbbi.upnp.services.UPNPService;

public class InternetGatewayDevice {
    private static final Logger log = Logger.getLogger(InternetGatewayDevice.class.getName());
    private UPNPRootDevice igd;
    private UPNPMessageFactory msgFactory;

    public InternetGatewayDevice(UPNPRootDevice uPNPRootDevice) throws UnsupportedOperationException {
        this(uPNPRootDevice, true, true);
    }

    private InternetGatewayDevice(UPNPRootDevice object, boolean bl, boolean bl2) throws UnsupportedOperationException {
        this.igd = object;
        Object object2 = ((UPNPDevice)object).getChildDevice("urn:schemas-upnp-org:device:WANConnectionDevice:1");
        if (object2 == null) {
            object2 = new StringBuilder("device urn:schemas-upnp-org:device:WANConnectionDevice:1 not supported by IGD device ");
            ((StringBuilder)object2).append(((UPNPDevice)object).getModelName());
            throw new UnsupportedOperationException(((StringBuilder)object2).toString());
        }
        object = ((UPNPDevice)object2).getService("urn:schemas-upnp-org:service:WANIPConnection:1");
        object2 = ((UPNPDevice)object2).getService("urn:schemas-upnp-org:service:WANPPPConnection:1");
        if (bl && bl2 && object == null) {
            if (object2 == null) throw new UnsupportedOperationException("Unable to find any urn:schemas-upnp-org:service:WANIPConnection:1 or urn:schemas-upnp-org:service:WANPPPConnection:1 service");
        }
        if (bl && !bl2) {
            if (object == null) throw new UnsupportedOperationException("Unable to find any urn:schemas-upnp-org:service:WANIPConnection:1 service");
        }
        if (!bl && bl2) {
            if (object2 == null) throw new UnsupportedOperationException("Unable to find any urn:schemas-upnp-org:service:WANPPPConnection:1 service");
        }
        if (object != null && object2 == null) {
            this.msgFactory = UPNPMessageFactory.getNewInstance((UPNPService)object);
            return;
        }
        if (object2 != null && object == null) {
            this.msgFactory = UPNPMessageFactory.getNewInstance((UPNPService)object2);
            return;
        }
        if (this.testWANInterface((UPNPService)object)) {
            this.msgFactory = UPNPMessageFactory.getNewInstance((UPNPService)object);
        } else if (this.testWANInterface((UPNPService)object2)) {
            this.msgFactory = UPNPMessageFactory.getNewInstance((UPNPService)object2);
        }
        if (this.msgFactory != null) return;
        log.warning("Unable to detect active WANIPConnection, dfaulting to urn:schemas-upnp-org:service:WANIPConnection:1");
        this.msgFactory = UPNPMessageFactory.getNewInstance((UPNPService)object);
    }

    private void checkPortMappingProtocol(String string2) throws IllegalArgumentException {
        if (string2 == null) throw new IllegalArgumentException("PortMappingProtocol must be either TCP or UDP");
        if (string2.equals("TCP")) return;
        if (!string2.equals("UDP")) throw new IllegalArgumentException("PortMappingProtocol must be either TCP or UDP");
    }

    private void checkPortRange(int n) throws IllegalArgumentException {
        if (n < 1) throw new IllegalArgumentException("Port range must be between 1 and 65535");
        if (n > 65535) throw new IllegalArgumentException("Port range must be between 1 and 65535");
    }

    public static InternetGatewayDevice[] getDevices(int n) throws IOException {
        return InternetGatewayDevice.lookupDeviceDevices(n, 4, 3, true, true, null);
    }

    public static InternetGatewayDevice[] getDevices(int n, int n2, int n3, NetworkInterface networkInterface) throws IOException {
        return InternetGatewayDevice.lookupDeviceDevices(n, n2, n3, true, true, networkInterface);
    }

    public static InternetGatewayDevice[] getIPDevices(int n) throws IOException {
        return InternetGatewayDevice.lookupDeviceDevices(n, 4, 3, true, false, null);
    }

    public static InternetGatewayDevice[] getPPPDevices(int n) throws IOException {
        return InternetGatewayDevice.lookupDeviceDevices(n, 4, 3, false, true, null);
    }

    private static InternetGatewayDevice[] lookupDeviceDevices(int n, int n2, int n3, boolean bl, boolean bl2, NetworkInterface object) throws IOException {
        object = n == -1 ? Discovery.discover(1500, n2, n3, "urn:schemas-upnp-org:device:InternetGatewayDevice:1", (NetworkInterface)object) : Discovery.discover(n, n2, n3, "urn:schemas-upnp-org:device:InternetGatewayDevice:1", (NetworkInterface)object);
        InternetGatewayDevice[] arrinternetGatewayDevice = null;
        if (object == null) return arrinternetGatewayDevice;
        HashSet<InternetGatewayDevice[]> hashSet = new HashSet<InternetGatewayDevice[]>();
        n2 = 0;
        n = 0;
        do {
            if (n >= ((UPNPRootDevice[])object).length) {
                if (hashSet.size() == 0) {
                    return null;
                }
                break;
            }
            try {
                arrinternetGatewayDevice = new InternetGatewayDevice(object[n], bl, bl2);
                hashSet.add(arrinternetGatewayDevice);
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                Logger logger = log;
                StringBuilder stringBuilder = new StringBuilder("UnsupportedOperationException during discovery ");
                stringBuilder.append(unsupportedOperationException.getMessage());
                logger.fine(stringBuilder.toString());
            }
            ++n;
        } while (true);
        arrinternetGatewayDevice = new InternetGatewayDevice[hashSet.size()];
        object = hashSet.iterator();
        n = n2;
        while (object.hasNext()) {
            arrinternetGatewayDevice[n] = (InternetGatewayDevice)object.next();
            ++n;
        }
        return arrinternetGatewayDevice;
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    private boolean testWANInterface(UPNPService object) {
        object = UPNPMessageFactory.getNewInstance((UPNPService)object).getMessage("GetExternalIPAddress");
        try {
            object = ((ActionMessage)object).service().getOutActionArgumentValue("NewExternalIPAddress");
        }
        catch (IOException iOException) {
            block5 : {
                log.log(Level.WARNING, "IOException occured during device detection", iOException);
                break block5;
                catch (UPNPResponseException uPNPResponseException) {}
            }
            object = null;
        }
        if (object == null) return false;
        if (((String)object).length() <= 0) return false;
        if (((String)object).equals("0.0.0.0")) return false;
        try {
            object = InetAddress.getByName((String)object);
            if (object == null) return false;
            return true;
        }
        catch (UnknownHostException unknownHostException) {
            return false;
        }
    }

    public boolean addPortMapping(String object, String string2, int n, int n2, String string3, int n3, String string4) throws IOException, UPNPResponseException {
        String string5 = string2;
        if (string2 == null) {
            string5 = "";
        }
        this.checkPortMappingProtocol(string4);
        if (n2 != 0) {
            this.checkPortRange(n2);
        }
        this.checkPortRange(n);
        string2 = object;
        if (object == null) {
            string2 = "";
        }
        if (n3 < 0) {
            object = new StringBuilder("Invalid leaseDuration (");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(") value");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = this.msgFactory.getMessage("AddPortMapping");
        ((ActionMessage)object).setInputParameter("NewRemoteHost", string5).setInputParameter("NewExternalPort", n2).setInputParameter("NewProtocol", string4).setInputParameter("NewInternalPort", n).setInputParameter("NewInternalClient", string3).setInputParameter("NewEnabled", true).setInputParameter("NewPortMappingDescription", string2).setInputParameter("NewLeaseDuration", n3);
        try {
            ((ActionMessage)object).service();
            return true;
        }
        catch (UPNPResponseException uPNPResponseException) {
            if (uPNPResponseException.getDetailErrorCode() != 718) throw uPNPResponseException;
            return false;
        }
    }

    public boolean deletePortMapping(String object, int n, String string2) throws IOException, UPNPResponseException {
        String string3 = object;
        if (object == null) {
            string3 = "";
        }
        this.checkPortMappingProtocol(string2);
        this.checkPortRange(n);
        object = this.msgFactory.getMessage("DeletePortMapping");
        ((ActionMessage)object).setInputParameter("NewRemoteHost", string3).setInputParameter("NewExternalPort", n).setInputParameter("NewProtocol", string2);
        try {
            ((ActionMessage)object).service();
            return true;
        }
        catch (UPNPResponseException uPNPResponseException) {
            if (uPNPResponseException.getDetailErrorCode() != 714) throw uPNPResponseException;
            return false;
        }
    }

    public String getExternalIPAddress() throws UPNPResponseException, IOException {
        return this.msgFactory.getMessage("GetExternalIPAddress").service().getOutActionArgumentValue("NewExternalIPAddress");
    }

    public ActionResponse getGenericPortMappingEntry(int n) throws IOException, UPNPResponseException {
        ActionMessage actionMessage = this.msgFactory.getMessage("GetGenericPortMappingEntry");
        actionMessage.setInputParameter("NewPortMappingIndex", n);
        try {
            return actionMessage.service();
        }
        catch (UPNPResponseException uPNPResponseException) {
            if (uPNPResponseException.getDetailErrorCode() != 714) throw uPNPResponseException;
            return null;
        }
    }

    public UPNPRootDevice getIGDRootDevice() {
        return this.igd;
    }

    public Integer getNatMappingsCount() throws IOException, UPNPResponseException {
        StateVariableMessage stateVariableMessage = this.msgFactory.getStateVariableMessage("PortMappingNumberOfEntries");
        try {
            StateVariableResponse stateVariableResponse = stateVariableMessage.service();
            return new Integer(stateVariableResponse.getStateVariableValue());
        }
        catch (UPNPResponseException uPNPResponseException) {
            if (uPNPResponseException.getDetailErrorCode() != 404) throw uPNPResponseException;
            return null;
        }
    }

    public Integer getNatTableSize() throws IOException, UPNPResponseException {
        int n;
        int n2 = 0;
        int n3 = 0;
        do {
            if (n3 >= 50) {
                return null;
            }
            try {
                this.getGenericPortMappingEntry(n3);
            }
            catch (UPNPResponseException uPNPResponseException) {
                if (uPNPResponseException.getDetailErrorCode() != 713) {
                    if (uPNPResponseException.getDetailErrorCode() != 402) throw uPNPResponseException;
                }
                ++n3;
                continue;
            }
            n = n3;
            if (n3 != -1) break;
            return null;
            break;
        } while (true);
        try {
            do {
                this.getGenericPortMappingEntry(n);
                ++n2;
                ++n;
            } while (true);
        }
        catch (UPNPResponseException uPNPResponseException) {
            if (uPNPResponseException.getDetailErrorCode() == 713) return new Integer(n2);
            if (uPNPResponseException.getDetailErrorCode() != 402) throw uPNPResponseException;
            return new Integer(n2);
        }
    }

    public ActionResponse getSpecificPortMappingEntry(String object, int n, String string2) throws IOException, UPNPResponseException {
        String string3 = object;
        if (object == null) {
            string3 = "";
        }
        this.checkPortMappingProtocol(string2);
        this.checkPortRange(n);
        object = this.msgFactory.getMessage("GetSpecificPortMappingEntry");
        ((ActionMessage)object).setInputParameter("NewRemoteHost", string3).setInputParameter("NewExternalPort", n).setInputParameter("NewProtocol", string2);
        try {
            return ((ActionMessage)object).service();
        }
        catch (UPNPResponseException uPNPResponseException) {
            if (uPNPResponseException.getDetailErrorCode() != 714) throw uPNPResponseException;
            return null;
        }
    }
}

