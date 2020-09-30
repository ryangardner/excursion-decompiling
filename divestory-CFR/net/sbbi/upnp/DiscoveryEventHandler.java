/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp;

import java.net.URL;

public interface DiscoveryEventHandler {
    public void eventSSDPAlive(String var1, String var2, String var3, String var4, URL var5);

    public void eventSSDPByeBye(String var1, String var2, String var3);
}

