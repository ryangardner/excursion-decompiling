/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp;

import java.net.InetAddress;
import java.net.URL;

public class ServiceEventSubscription {
    private String SID = null;
    private InetAddress deviceIp = null;
    private int durationTime = 0;
    private String serviceId = null;
    private String serviceType = null;
    private URL serviceURL = null;

    public ServiceEventSubscription(String string2, String string3, URL uRL, String string4, InetAddress inetAddress, int n) {
        this.serviceType = string2;
        this.serviceId = string3;
        this.serviceURL = uRL;
        this.SID = string4;
        this.deviceIp = inetAddress;
        this.durationTime = n;
    }

    public InetAddress getDeviceIp() {
        return this.deviceIp;
    }

    public int getDurationTime() {
        return this.durationTime;
    }

    public String getSID() {
        return this.SID;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public URL getServiceURL() {
        return this.serviceURL;
    }
}

