/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.samples;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import net.sbbi.upnp.DiscoveryAdvertisement;
import net.sbbi.upnp.DiscoveryEventHandler;
import net.sbbi.upnp.devices.UPNPRootDevice;

public class MyDiscoveryEventsHandler
implements DiscoveryEventHandler {
    private Map devices = new HashMap();

    public static void main(String[] object) throws IOException {
        object = DiscoveryAdvertisement.getInstance();
        MyDiscoveryEventsHandler myDiscoveryEventsHandler = new MyDiscoveryEventsHandler();
        ((DiscoveryAdvertisement)object).setDaemon(false);
        ((DiscoveryAdvertisement)object).registerEvent(0, "upnp:rootdevice", myDiscoveryEventsHandler);
    }

    @Override
    public void eventSSDPAlive(String string2, String object, String charSequence, String string3, URL uRL) {
        PrintStream printStream = System.out;
        object = new StringBuilder("Device ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" at ");
        ((StringBuilder)object).append(uRL);
        ((StringBuilder)object).append(" of type ");
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append(" alive");
        printStream.println(((StringBuilder)object).toString());
        if (this.devices.get(string2) != null) return;
        try {
            object = new UPNPRootDevice(uRL, string3);
            this.devices.put(string2, object);
            object = System.out;
            charSequence = new StringBuilder("Device ");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" added");
            ((PrintStream)object).println(((StringBuilder)charSequence).toString());
            return;
        }
        catch (IllegalStateException illegalStateException) {
            object = System.err;
            charSequence = new StringBuilder("! Error: ");
            ((StringBuilder)charSequence).append(illegalStateException);
            ((PrintStream)object).println(((StringBuilder)charSequence).toString());
        }
    }

    @Override
    public void eventSSDPByeBye(String string2, String charSequence, String object) {
        if (this.devices.get(string2) == null) return;
        this.devices.remove(string2);
        object = System.out;
        charSequence = new StringBuilder("Device ");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" leaves");
        ((PrintStream)object).println(((StringBuilder)charSequence).toString());
    }
}

