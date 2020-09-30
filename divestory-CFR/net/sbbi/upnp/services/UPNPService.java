/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathException;
import net.sbbi.upnp.devices.UPNPDevice;
import net.sbbi.upnp.devices.UPNPRootDevice;
import net.sbbi.upnp.services.ServiceAction;
import net.sbbi.upnp.services.ServiceActionArgument;
import net.sbbi.upnp.services.ServiceStateVariable;
import net.sbbi.upnp.xpath.JXPathContext;
import net.sbbi.upnp.xpath.JXPathFilterSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class UPNPService {
    protected URL SCPDURL;
    protected String SCPDURLData;
    protected Map UPNPServiceActions;
    protected Map UPNPServiceStateVariables;
    private String USN;
    protected URL controlURL;
    protected URL eventSubURL;
    private boolean parsedSCPD = false;
    protected String serviceId;
    protected UPNPDevice serviceOwnerDevice;
    protected String serviceType;
    private int specVersionMajor;
    private int specVersionMinor;

    public UPNPService(JXPathContext jXPathContext, URL uRL, UPNPDevice uPNPDevice) throws MalformedURLException, XPathException {
        this.serviceOwnerDevice = uPNPDevice;
        this.serviceType = jXPathContext.getString("serviceType");
        this.serviceId = jXPathContext.getString("serviceId");
        this.SCPDURL = UPNPRootDevice.getURL(jXPathContext.getString("SCPDURL"), uRL);
        this.controlURL = UPNPRootDevice.getURL(jXPathContext.getString("controlURL"), uRL);
        this.eventSubURL = UPNPRootDevice.getURL(jXPathContext.getString("eventSubURL"), uRL);
        this.USN = uPNPDevice.getUDN().concat("::").concat(this.serviceType);
    }

    private void lazyInitiate() {
        if (this.parsedSCPD) return;
        synchronized (this) {
            if (this.parsedSCPD) return;
            this.parseSCPD();
            return;
        }
    }

    private void parseSCPD() {
        Object object;
        try {
            object = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Object object2 = new JXPathFilterSource(this.SCPDURL.openStream());
            object = ((DocumentBuilder)object).parse((InputSource)object2);
            object2 = new JXPathContext((Node)object);
            object = ((JXPathContext)object2).getRelativeContext(((JXPathContext)object2).getPointer("scpd"));
            this.specVersionMajor = Integer.parseInt(((JXPathContext)object).getString("specVersion/major"));
            this.specVersionMinor = Integer.parseInt(((JXPathContext)object).getString("specVersion/minor"));
            this.parseServiceStateVariables((JXPathContext)object);
            Object object3 = ((JXPathContext)object2).getRelativeContext(((JXPathContext)object).getPointer("actionList"));
            Serializable serializable = ((JXPathContext)object3).getNumber("count( action )");
            object2 = new HashMap();
            this.UPNPServiceActions = object2;
            int n = 1;
            do {
                ServiceAction serviceAction;
                block11 : {
                    if (n > serializable.intValue()) {
                        this.parsedSCPD = true;
                        return;
                    }
                    serviceAction = new ServiceAction();
                    object2 = new StringBuilder("action[");
                    ((StringBuilder)object2).append(n);
                    ((StringBuilder)object2).append("]/name");
                    serviceAction.name = ((JXPathContext)object3).getString(((StringBuilder)object2).toString());
                    serviceAction.parent = this;
                    object2 = null;
                    try {
                        object = new StringBuilder("action[");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append("]/argumentList");
                        object2 = object = ((JXPathContext)object3).getPointer(((StringBuilder)object).toString());
                    }
                    catch (XPathException xPathException) {
                        // empty catch block
                    }
                    if (object2 != null) {
                        JXPathContext jXPathContext = ((JXPathContext)object3).getRelativeContext((Node)object2);
                        Double d = jXPathContext.getNumber("count( argument )");
                        ArrayList<Object> arrayList = new ArrayList<Object>();
                        int n2 = 1;
                        do {
                            if (n2 > d.intValue()) {
                                if (d.intValue() > 0) {
                                    serviceAction.setActionArguments(arrayList);
                                }
                                break block11;
                            }
                            object = new ServiceActionArgument();
                            object2 = new StringBuilder("argument[");
                            ((StringBuilder)object2).append(n2);
                            ((StringBuilder)object2).append("]/name");
                            ((ServiceActionArgument)object).name = jXPathContext.getString(((StringBuilder)object2).toString());
                            object2 = new StringBuilder("argument[");
                            ((StringBuilder)object2).append(n2);
                            ((StringBuilder)object2).append("]/direction");
                            object2 = jXPathContext.getString(((StringBuilder)object2).toString()).equals("in") ? "in" : "out";
                            ((ServiceActionArgument)object).direction = object2;
                            object2 = new StringBuilder("argument[");
                            ((StringBuilder)object2).append(n2);
                            ((StringBuilder)object2).append("]/relatedStateVariable");
                            object2 = jXPathContext.getString(((StringBuilder)object2).toString());
                            ServiceStateVariable serviceStateVariable = (ServiceStateVariable)this.UPNPServiceStateVariables.get(object2);
                            if (serviceStateVariable == null) break;
                            ((ServiceActionArgument)object).relatedStateVariable = serviceStateVariable;
                            arrayList.add(object);
                            ++n2;
                        } while (true);
                        object3 = new StringBuilder("Unable to find any state variable named ");
                        ((StringBuilder)object3).append((String)object2);
                        ((StringBuilder)object3).append(" for service ");
                        ((StringBuilder)object3).append(this.getServiceId());
                        ((StringBuilder)object3).append(" action ");
                        ((StringBuilder)object3).append(serviceAction.name);
                        ((StringBuilder)object3).append(" argument ");
                        ((StringBuilder)object3).append(((ServiceActionArgument)object).name);
                        serializable = new IllegalArgumentException(((StringBuilder)object3).toString());
                        throw serializable;
                    }
                }
                this.UPNPServiceActions.put(serviceAction.getName(), serviceAction);
                ++n;
            } while (true);
        }
        catch (Throwable throwable) {
            object = new StringBuilder("Error during lazy SCDP file parsing at ");
            ((StringBuilder)object).append(this.SCPDURL);
            throw new RuntimeException(((StringBuilder)object).toString(), throwable);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void parseServiceStateVariables(JXPathContext var1_1) throws XPathException {
        var2_7 = var1_1.getRelativeContext(var1_1.getPointer("serviceStateTable"));
        var3_8 = var2_7.getNumber("count( stateVariable )");
        this.UPNPServiceStateVariables = new HashMap<K, V>();
        var4_9 = 1;
        do {
            block15 : {
                if (var4_9 > var3_8.intValue()) {
                    return;
                }
                var5_10 = new ServiceStateVariable();
                try {
                    var1_1 = new StringBuilder("stateVariable[");
                    var1_1.append(var4_9);
                    var1_1.append("]/@sendEvents");
                    var1_1 = var2_7.getString(var1_1.toString());
                }
                catch (XPathException var1_2) {
                    var1_1 = "yes";
                }
                var5_10.parent = this;
                var5_10.sendEvents = var1_1.equalsIgnoreCase("no") ^ true;
                var1_1 = new StringBuilder("stateVariable[");
                var1_1.append(var4_9);
                var1_1.append("]/name");
                var5_10.name = var2_7.getString(var1_1.toString());
                var1_1 = new StringBuilder("stateVariable[");
                var1_1.append(var4_9);
                var1_1.append("]/dataType");
                var5_10.dataType = var2_7.getString(var1_1.toString());
                try {
                    var1_1 = new StringBuilder("stateVariable[");
                    var1_1.append(var4_9);
                    var1_1.append("]/defaultValue");
                    var5_10.defaultValue = var2_7.getString(var1_1.toString());
lbl40: // 2 sources:
                    do {
                        var6_11 = null;
                        break;
                    } while (true);
                }
                catch (XPathException var1_5) {
                    ** continue;
                }
                {
                    try {
                        var1_1 = new StringBuilder("stateVariable[");
                        var1_1.append(var4_9);
                        var1_1.append("]/allowedValueList");
                        var1_1 = var2_7.getPointer(var1_1.toString());
                    }
                    catch (XPathException var1_3) {
                        var1_1 = null;
                    }
                    if (var1_1 == null) break block15;
                    var1_1 = var2_7.getRelativeContext((Node)var1_1);
                    var7_12 = var1_1.getNumber("count( allowedValue )");
                    var5_10.allowedvalues = new HashSet<E>();
                }
                for (var8_13 = 1; var8_13 <= var7_12.intValue(); ++var8_13) {
                    var9_15 = new StringBuilder("allowedValue[");
                    var9_15.append(var8_13);
                    var9_15.append("]");
                    var9_14 = var1_1.getString(var9_15.toString());
                    var5_10.allowedvalues.add(var9_14);
                }
            }
            try {
                var1_1 = new StringBuilder("stateVariable[");
                var1_1.append(var4_9);
                var1_1.append("]/allowedValueRange");
                var1_1 = var2_7.getPointer(var1_1.toString());
            }
            catch (XPathException var1_4) {
                var1_1 = var6_11;
            }
            if (var1_1 != null) {
                var1_1 = new StringBuilder("stateVariable[");
                var1_1.append(var4_9);
                var1_1.append("]/allowedValueRange/minimum");
                var5_10.minimumRangeValue = var2_7.getString(var1_1.toString());
                var1_1 = new StringBuilder("stateVariable[");
                var1_1.append(var4_9);
                var1_1.append("]/allowedValueRange/maximum");
                var5_10.maximumRangeValue = var2_7.getString(var1_1.toString());
                try {
                    var1_1 = new StringBuilder("stateVariable[");
                    var1_1.append(var4_9);
                    var1_1.append("]/allowedValueRange/step");
                    var5_10.stepRangeValue = var2_7.getString(var1_1.toString());
                }
                catch (XPathException var1_6) {}
            }
            this.UPNPServiceStateVariables.put(var5_10.getName(), var5_10);
            ++var4_9;
        } while (true);
    }

    public Iterator getAvailableActionsName() {
        this.lazyInitiate();
        return this.UPNPServiceActions.keySet().iterator();
    }

    public int getAvailableActionsSize() {
        this.lazyInitiate();
        return this.UPNPServiceActions.keySet().size();
    }

    public Iterator getAvailableStateVariableName() {
        this.lazyInitiate();
        return this.UPNPServiceStateVariables.keySet().iterator();
    }

    public int getAvailableStateVariableSize() {
        this.lazyInitiate();
        return this.UPNPServiceStateVariables.keySet().size();
    }

    public URL getControlURL() {
        return this.controlURL;
    }

    public URL getEventSubURL() {
        return this.eventSubURL;
    }

    public String getSCDPData() {
        if (this.SCPDURLData != null) return this.SCPDURLData;
        try {
            InputStream inputStream2 = this.SCPDURL.openConnection().getInputStream();
            byte[] arrby = new byte[512];
            StringBuffer stringBuffer = new StringBuffer();
            do {
                int n;
                if ((n = inputStream2.read(arrby)) == -1) {
                    inputStream2.close();
                    this.SCPDURLData = stringBuffer.toString();
                    return this.SCPDURLData;
                }
                String string2 = new String(arrby, 0, n);
                stringBuffer.append(string2);
            } while (true);
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public URL getSCPDURL() {
        return this.SCPDURL;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public UPNPDevice getServiceOwnerDevice() {
        return this.serviceOwnerDevice;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public int getSpecVersionMajor() {
        this.lazyInitiate();
        return this.specVersionMajor;
    }

    public int getSpecVersionMinor() {
        this.lazyInitiate();
        return this.specVersionMinor;
    }

    public ServiceAction getUPNPServiceAction(String string2) {
        this.lazyInitiate();
        return (ServiceAction)this.UPNPServiceActions.get(string2);
    }

    public ServiceStateVariable getUPNPServiceStateVariable(String string2) {
        this.lazyInitiate();
        return (ServiceStateVariable)this.UPNPServiceStateVariables.get(string2);
    }

    public String getUSN() {
        return this.USN;
    }
}

