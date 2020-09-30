/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.devices;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import net.sbbi.upnp.devices.DeviceIcon;
import net.sbbi.upnp.devices.UPNPDevice;
import net.sbbi.upnp.services.UPNPService;
import net.sbbi.upnp.xpath.JXPathContext;
import net.sbbi.upnp.xpath.JXPathFilterSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class UPNPRootDevice
extends UPNPDevice {
    private static final Logger log = Logger.getLogger(UPNPRootDevice.class.getName());
    private URL URLBase;
    private long creationTime;
    private URL deviceDefLoc;
    private String deviceDefLocData;
    private String discoveryUDN;
    private String discoveryUSN;
    private int specVersionMajor;
    private int specVersionMinor;
    private long validityTime;
    private String vendorFirmware;

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public UPNPRootDevice(URL var1_1, String var2_6) throws IllegalStateException {
        super();
        try {
            block18 : {
                block17 : {
                    try {
                        this.deviceDefLoc = var1_1;
                        var3_10 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                        var4_11 = new JXPathFilterSource(var1_1.openStream());
                        var4_11 = var3_10.parse((InputSource)var4_11);
                        this.validityTime = Integer.parseInt((String)var2_6) * 1000;
                        this.creationTime = System.currentTimeMillis();
                        var2_6 = new JXPathContext((Node)var4_11);
                        var3_10 = var2_6.getRelativeContext(var2_6.getPointer("root"));
                        this.specVersionMajor = Integer.parseInt(var3_10.getString("specVersion/major"));
                        this.specVersionMinor = var5_12 = Integer.parseInt(var3_10.getString("specVersion/minor"));
                        var6_13 = this.specVersionMajor;
                        var7_14 = 1;
                        if (var6_13 != 1 || var5_12 != 0) ** GOTO lbl79
                    }
                    catch (XPathException var1_2) {
                        UPNPRootDevice.log.log(Level.SEVERE, "Exception while navigating Device Descripttion with XPath!", var1_2);
                        return;
                    }
                    var4_11 = var3_10.getString("URLBase");
                    var6_13 = var7_14;
                    if (var4_11 == null) ** GOTO lbl54
                    var6_13 = var7_14;
                    try {
                        if (var4_11.trim().length() > 0) {
                            this.URLBase = var2_6 = new URL((String)var4_11);
                            var2_6 = UPNPRootDevice.log;
                            var8_15 = new StringBuilder("device URLBase ");
                            var8_15.append(this.URLBase);
                            var2_6.fine(var8_15.toString());
                            var6_13 = 0;
                        }
                        ** GOTO lbl54
                    }
                    catch (MalformedURLException var2_7) {
                        break block17;
                    }
                    catch (MalformedURLException var2_8) {
                        var4_11 = null;
                    }
                }
                var9_17 = UPNPRootDevice.log;
                var10_18 = Level.WARNING;
                var8_16 = new StringBuilder("Error occured during device baseURL ");
                var8_16.append((String)var4_11);
                var8_16.append(" parsing, building it from device default location");
                var9_17.log(var10_18, var8_16.toString(), (Throwable)var2_6);
                var6_13 = var7_14;
                break block18;
                catch (XPathException var2_9) {
                    var6_13 = var7_14;
                }
            }
            if (var6_13 != 0) {
                var2_6 = new StringBuilder(String.valueOf(var1_1.getProtocol()));
                var2_6.append("://");
                var2_6.append(var1_1.getHost());
                var2_6.append(":");
                var2_6.append(var1_1.getPort());
                var2_6 = var2_6.toString();
                var4_11 = var1_1.getPath();
                var1_1 = var2_6;
                if (var4_11 != null) {
                    var6_13 = var4_11.lastIndexOf(47);
                    var1_1 = var2_6;
                    if (var6_13 != -1) {
                        var1_1 = new StringBuilder(String.valueOf(var2_6));
                        var1_1.append(var4_11.substring(0, var6_13));
                        var1_1 = var1_1.toString();
                    }
                }
                this.URLBase = var2_6 = new URL((String)var1_1);
            }
            this.fillUPNPDevice(this, null, var3_10.getRelativeContext(var3_10.getPointer("device")), this.URLBase);
            return;
lbl79: // 1 sources:
            var2_6 = new StringBuilder("Unsupported device version (");
            var2_6.append(this.specVersionMajor);
            var2_6.append(".");
            var2_6.append(this.specVersionMinor);
            var2_6.append(")");
            var1_1 = new IllegalStateException(var2_6.toString());
            throw var1_1;
        }
        catch (SAXException var1_3) {
            UPNPRootDevice.log.log(Level.SEVERE, "Exception while parsing Device Descripttion xml!", var1_3);
            return;
        }
        catch (IOException var1_4) {
            UPNPRootDevice.log.log(Level.SEVERE, "Exception while accessing Device Descripttion!", var1_4);
            return;
        }
        catch (ParserConfigurationException var1_5) {
            UPNPRootDevice.log.log(Level.SEVERE, "Exception while initializing XML parser!", var1_5);
        }
    }

    public UPNPRootDevice(URL uRL, String string2, String string3) throws MalformedURLException, IllegalStateException {
        this(uRL, string2);
        this.vendorFirmware = string3;
    }

    public UPNPRootDevice(URL uRL, String string2, String string3, String string4, String string5) throws MalformedURLException, IllegalStateException {
        this(uRL, string2);
        this.vendorFirmware = string3;
        this.discoveryUSN = string4;
        this.discoveryUDN = string5;
    }

    private void fillUPNPDevice(UPNPDevice uPNPDevice, UPNPDevice object, JXPathContext jXPathContext, URL uRL) throws MalformedURLException, XPathException {
        uPNPDevice.deviceType = this.getMandatoryData(jXPathContext, "deviceType");
        Object object2 = log;
        Object object3 = new StringBuilder("parsing device ");
        ((StringBuilder)object3).append(uPNPDevice.deviceType);
        ((Logger)object2).fine(((StringBuilder)object3).toString());
        uPNPDevice.friendlyName = this.getMandatoryData(jXPathContext, "friendlyName");
        uPNPDevice.manufacturer = this.getNonMandatoryData(jXPathContext, "manufacturer");
        object3 = this.getNonMandatoryData(jXPathContext, "manufacturerURL");
        if (object3 != null) {
            try {
                uPNPDevice.manufacturerURL = object2 = new URL((String)object3);
            }
            catch (MalformedURLException malformedURLException) {}
        }
        try {
            uPNPDevice.presentationURL = UPNPRootDevice.getURL(this.getNonMandatoryData(jXPathContext, "presentationURL"), this.URLBase);
        }
        catch (MalformedURLException malformedURLException) {
            // empty catch block
        }
        uPNPDevice.modelDescription = this.getNonMandatoryData(jXPathContext, "modelDescription");
        uPNPDevice.modelName = this.getMandatoryData(jXPathContext, "modelName");
        uPNPDevice.modelNumber = this.getNonMandatoryData(jXPathContext, "modelNumber");
        uPNPDevice.modelURL = this.getNonMandatoryData(jXPathContext, "modelURL");
        uPNPDevice.serialNumber = this.getNonMandatoryData(jXPathContext, "serialNumber");
        uPNPDevice.UDN = this.getMandatoryData(jXPathContext, "UDN");
        uPNPDevice.USN = this.UDN.concat("::").concat(this.deviceType);
        object3 = this.getNonMandatoryData(jXPathContext, "UPC");
        if (object3 != null) {
            try {
                uPNPDevice.UPC = Long.parseLong((String)object3);
            }
            catch (Exception exception) {}
        }
        uPNPDevice.parent = object;
        this.fillUPNPServicesList(uPNPDevice, jXPathContext);
        this.fillUPNPDeviceIconsList(uPNPDevice, jXPathContext, this.URLBase);
        try {
            object = jXPathContext.getPointer("deviceList");
            jXPathContext = jXPathContext.getRelativeContext((Node)object);
        }
        catch (XPathException xPathException) {
            return;
        }
        object = jXPathContext.getNumber("count( device )");
        uPNPDevice.childDevices = new ArrayList();
        object3 = log;
        object2 = new StringBuilder("child devices count is ");
        ((StringBuilder)object2).append(object);
        ((Logger)object3).fine(((StringBuilder)object2).toString());
        int n = 1;
        while (n <= ((Double)object).intValue()) {
            object3 = new StringBuilder("device[");
            ((StringBuilder)object3).append(n);
            ((StringBuilder)object3).append("]");
            object2 = jXPathContext.getRelativeContext(jXPathContext.getPointer(((StringBuilder)object3).toString()));
            object3 = new UPNPDevice();
            this.fillUPNPDevice((UPNPDevice)object3, uPNPDevice, (JXPathContext)object2, uRL);
            Logger logger = log;
            object2 = new StringBuilder("adding child device ");
            ((StringBuilder)object2).append(((UPNPDevice)object3).getDeviceType());
            logger.fine(((StringBuilder)object2).toString());
            uPNPDevice.childDevices.add(object3);
            ++n;
        }
        return;
    }

    private void fillUPNPDeviceIconsList(UPNPDevice uPNPDevice, JXPathContext object, URL uRL) throws MalformedURLException, XPathException {
        Object object2;
        try {
            object2 = ((JXPathContext)object).getPointer("iconList");
            object2 = ((JXPathContext)object).getRelativeContext((Node)object2);
        }
        catch (XPathException xPathException) {
            return;
        }
        object = ((JXPathContext)object2).getNumber("count( icon )");
        Object object3 = log;
        Object object4 = new StringBuilder("device icons count is ");
        ((StringBuilder)object4).append(object);
        ((Logger)object3).fine(((StringBuilder)object4).toString());
        uPNPDevice.deviceIcons = new ArrayList();
        int n = 1;
        while (n <= ((Double)object).intValue()) {
            object4 = new DeviceIcon();
            object3 = new StringBuilder("icon[");
            ((StringBuilder)object3).append(n);
            ((StringBuilder)object3).append("]/mimetype");
            ((DeviceIcon)object4).mimeType = ((JXPathContext)object2).getString(((StringBuilder)object3).toString());
            object3 = new StringBuilder("icon[");
            ((StringBuilder)object3).append(n);
            ((StringBuilder)object3).append("]/width");
            ((DeviceIcon)object4).width = Integer.parseInt(((JXPathContext)object2).getString(((StringBuilder)object3).toString()));
            object3 = new StringBuilder("icon[");
            ((StringBuilder)object3).append(n);
            ((StringBuilder)object3).append("]/height");
            ((DeviceIcon)object4).height = Integer.parseInt(((JXPathContext)object2).getString(((StringBuilder)object3).toString()));
            object3 = new StringBuilder("icon[");
            ((StringBuilder)object3).append(n);
            ((StringBuilder)object3).append("]/depth");
            ((DeviceIcon)object4).depth = Integer.parseInt(((JXPathContext)object2).getString(((StringBuilder)object3).toString()));
            object3 = new StringBuilder("icon[");
            ((StringBuilder)object3).append(n);
            ((StringBuilder)object3).append("]/url");
            ((DeviceIcon)object4).url = UPNPRootDevice.getURL(((JXPathContext)object2).getString(((StringBuilder)object3).toString()), uRL);
            object3 = log;
            StringBuilder stringBuilder = new StringBuilder("icon URL is ");
            stringBuilder.append(((DeviceIcon)object4).url);
            ((Logger)object3).fine(stringBuilder.toString());
            uPNPDevice.deviceIcons.add(object4);
            ++n;
        }
        return;
    }

    private void fillUPNPServicesList(UPNPDevice uPNPDevice, JXPathContext object) throws MalformedURLException, XPathException {
        JXPathContext jXPathContext = ((JXPathContext)object).getRelativeContext(((JXPathContext)object).getPointer("serviceList"));
        Double d = jXPathContext.getNumber("count( service )");
        object = log;
        Object object2 = new StringBuilder("device services count is ");
        ((StringBuilder)object2).append(d);
        ((Logger)object).fine(((StringBuilder)object2).toString());
        uPNPDevice.services = new ArrayList();
        int n = 1;
        while (n <= d.intValue()) {
            object = new StringBuilder("service[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]");
            object2 = jXPathContext.getRelativeContext(jXPathContext.getPointer(((StringBuilder)object).toString()));
            object = this.URLBase;
            if (object == null) {
                object = this.deviceDefLoc;
            }
            object = new UPNPService((JXPathContext)object2, (URL)object, this);
            uPNPDevice.services.add(object);
            ++n;
        }
        return;
    }

    private String getMandatoryData(JXPathContext object, String string2) throws XPathException {
        if ((object = ((JXPathContext)object).getString(string2)) == null) return object;
        if (((String)object).length() != 0) {
            return object;
        }
        object = new StringBuilder("Mandatory field ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" not provided, uncompliant UPNP device !!");
        throw new XPathException(((StringBuilder)object).toString());
    }

    private String getNonMandatoryData(JXPathContext object, String string2) {
        Object var3_4 = null;
        try {
            object = ((JXPathContext)object).getString(string2);
            if (object == null) return object;
            int n = ((String)object).length();
            if (n != 0) return object;
            return var3_4;
        }
        catch (XPathException xPathException) {
            return var3_4;
        }
    }

    public static final URL getURL(String object, URL object2) throws MalformedURLException {
        if (object == null) return null;
        if (((String)object).trim().length() == 0) {
            return null;
        }
        try {
            URL uRL = new URL((String)object);
            return uRL;
        }
        catch (MalformedURLException malformedURLException) {
            if (object2 == null) throw malformedURLException;
            String string2 = ((String)object).replace('\\', '/');
            if (string2.charAt(0) != '/') {
                object = object2 = ((URL)object2).toExternalForm();
                if (!((String)object2).endsWith("/")) {
                    object = new StringBuilder(String.valueOf(object2));
                    ((StringBuilder)object).append("/");
                    object = ((StringBuilder)object).toString();
                }
                object = new StringBuilder(String.valueOf(object));
                ((StringBuilder)object).append(string2);
                return new URL(((StringBuilder)object).toString());
            }
            object = new StringBuilder(String.valueOf(((URL)object2).getProtocol()));
            ((StringBuilder)object).append("://");
            ((StringBuilder)object).append(((URL)object2).getHost());
            ((StringBuilder)object).append(":");
            ((StringBuilder)object).append(((URL)object2).getPort());
            object = new StringBuilder(String.valueOf(((StringBuilder)object).toString()));
            ((StringBuilder)object).append(string2);
            return new URL(((StringBuilder)object).toString());
        }
    }

    public URL getDeviceDefLoc() {
        return this.deviceDefLoc;
    }

    public String getDeviceDefLocData() {
        if (this.deviceDefLocData != null) return this.deviceDefLocData;
        try {
            InputStream inputStream2 = this.deviceDefLoc.openConnection().getInputStream();
            byte[] arrby = new byte[512];
            StringBuffer stringBuffer = new StringBuffer();
            do {
                int n;
                if ((n = inputStream2.read(arrby)) == -1) {
                    inputStream2.close();
                    this.deviceDefLocData = stringBuffer.toString();
                    return this.deviceDefLocData;
                }
                String string2 = new String(arrby, 0, n);
                stringBuffer.append(string2);
            } while (true);
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public String getDiscoveryUDN() {
        return this.discoveryUDN;
    }

    public String getDiscoveryUSN() {
        return this.discoveryUSN;
    }

    public int getSpecVersionMajor() {
        return this.specVersionMajor;
    }

    public int getSpecVersionMinor() {
        return this.specVersionMinor;
    }

    public URL getURLBase() {
        return this.URLBase;
    }

    public long getValidityTime() {
        long l = System.currentTimeMillis();
        long l2 = this.creationTime;
        return this.validityTime - (l - l2);
    }

    public String getVendorFirmware() {
        return this.vendorFirmware;
    }

    public void resetValidityTime(String string2) {
        this.validityTime = Integer.parseInt(string2) * 1000;
        this.creationTime = System.currentTimeMillis();
    }
}

