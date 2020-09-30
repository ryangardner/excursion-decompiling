/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.devices;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import net.sbbi.upnp.devices.UPNPRootDevice;
import net.sbbi.upnp.services.UPNPService;

public class UPNPDevice {
    private static final Logger log = Logger.getLogger(UPNPDevice.class.getName());
    protected String UDN;
    protected long UPC;
    protected String USN;
    protected List childDevices;
    protected List deviceIcons;
    protected String deviceType;
    protected String friendlyName;
    protected String manufacturer;
    protected URL manufacturerURL;
    protected String modelDescription;
    protected String modelName;
    protected String modelNumber;
    protected String modelURL;
    protected UPNPDevice parent;
    protected URL presentationURL;
    protected String serialNumber;
    protected List services;

    public UPNPDevice getChildDevice(String string2) {
        Object object = log;
        Object object2 = new StringBuilder("searching for device URI:");
        ((StringBuilder)object2).append(string2);
        ((Logger)object).fine(((StringBuilder)object2).toString());
        if (this.getDeviceType().equals(string2)) {
            return this;
        }
        object = this.childDevices;
        if (object == null) {
            return null;
        }
        object = object.iterator();
        do {
            if (object.hasNext()) continue;
            return null;
        } while ((object2 = ((UPNPDevice)object.next()).getChildDevice(string2)) == null);
        return object2;
    }

    public List getChildDevices() {
        if (this.childDevices == null) {
            return null;
        }
        ArrayList<UPNPDevice> arrayList = new ArrayList<UPNPDevice>();
        Iterator iterator2 = this.childDevices.iterator();
        while (iterator2.hasNext()) {
            Object object = (UPNPDevice)iterator2.next();
            arrayList.add((UPNPDevice)object);
            if ((object = ((UPNPDevice)object).getChildDevices()) == null) continue;
            arrayList.addAll((Collection<UPNPDevice>)object);
        }
        return arrayList;
    }

    public List getDeviceIcons() {
        return this.deviceIcons;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public UPNPDevice getDirectParent() {
        return this.parent;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public URL getManufacturerURL() {
        return this.manufacturerURL;
    }

    public String getModelDescription() {
        return this.modelDescription;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getModelNumber() {
        return this.modelNumber;
    }

    public String getModelURL() {
        return this.modelURL;
    }

    public URL getPresentationURL() {
        return this.presentationURL;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public UPNPService getService(String string2) {
        if (this.services == null) {
            return null;
        }
        Object object = log;
        Object object2 = new StringBuilder("searching for service URI:");
        ((StringBuilder)object2).append(string2);
        ((Logger)object).fine(((StringBuilder)object2).toString());
        object2 = this.services.iterator();
        do {
            if (object2.hasNext()) continue;
            return null;
        } while (!((UPNPService)(object = (UPNPService)object2.next())).getServiceType().equals(string2));
        return object;
    }

    public UPNPService getServiceByID(String string2) {
        if (this.services == null) {
            return null;
        }
        Object object = log;
        Object object2 = new StringBuilder("searching for service ID:");
        ((StringBuilder)object2).append(string2);
        ((Logger)object).fine(((StringBuilder)object2).toString());
        object2 = this.services.iterator();
        do {
            if (object2.hasNext()) continue;
            return null;
        } while (!((UPNPService)(object = (UPNPService)object2.next())).getServiceId().equals(string2));
        return object;
    }

    public List getServices() {
        if (this.services == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.services);
        return arrayList;
    }

    public List getServices(String string2) {
        if (this.services == null) {
            return null;
        }
        ArrayList<Object> arrayList = new ArrayList<Object>();
        Object object = log;
        Object object2 = new StringBuilder("searching for services URI:");
        ((StringBuilder)object2).append(string2);
        ((Logger)object).fine(((StringBuilder)object2).toString());
        object = this.services.iterator();
        do {
            if (!object.hasNext()) {
                if (arrayList.size() != 0) return arrayList;
                return null;
            }
            object2 = (UPNPService)object.next();
            if (!((UPNPService)object2).getServiceType().equals(string2)) continue;
            arrayList.add(object2);
        } while (true);
    }

    public List getTopLevelChildDevices() {
        if (this.childDevices == null) {
            return null;
        }
        ArrayList<UPNPDevice> arrayList = new ArrayList<UPNPDevice>();
        Iterator iterator2 = this.childDevices.iterator();
        while (iterator2.hasNext()) {
            arrayList.add((UPNPDevice)iterator2.next());
        }
        return arrayList;
    }

    public String getUDN() {
        return this.UDN;
    }

    public long getUPC() {
        return this.UPC;
    }

    public String getUSN() {
        return this.USN;
    }

    public boolean isRootDevice() {
        return this instanceof UPNPRootDevice;
    }

    public String toString() {
        return this.getDeviceType();
    }
}

