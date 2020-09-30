/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.samples;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import net.sbbi.upnp.DiscoveryAdvertisement;
import net.sbbi.upnp.DiscoveryEventHandler;

public class DiscoveryAdvertisementSample {
    public static final void main(String[] object) throws IOException {
        object = new AdvHandler();
        DiscoveryAdvertisement.getInstance().setDaemon(false);
        System.out.println("Registering EVENT_SSDP_ALIVE event");
        DiscoveryAdvertisement.getInstance().registerEvent(0, "upnp:rootdevice", (DiscoveryEventHandler)object);
        System.out.println("Registering EVENT_SSDP_BYE_BYE event");
        DiscoveryAdvertisement.getInstance().registerEvent(1, "upnp:rootdevice", (DiscoveryEventHandler)object);
        System.out.println("Waiting for incoming events");
    }

    private static class AdvHandler
    implements DiscoveryEventHandler {
        private AdvHandler() {
        }

        @Override
        public void eventSSDPAlive(String object, String charSequence, String string2, String string3, URL uRL) {
            object = System.out;
            charSequence = new StringBuilder("Root device at ");
            ((StringBuilder)charSequence).append(uRL);
            ((StringBuilder)charSequence).append(" plugged in network, advertisement will expire in ");
            ((StringBuilder)charSequence).append(string3);
            ((StringBuilder)charSequence).append(" ms");
            ((PrintStream)object).println(((StringBuilder)charSequence).toString());
        }

        @Override
        public void eventSSDPByeBye(String string2, String string3, String string4) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder("Bye Bye usn:");
            stringBuilder.append(string2);
            stringBuilder.append(" udn:");
            stringBuilder.append(string3);
            stringBuilder.append(" nt:");
            stringBuilder.append(string4);
            printStream.println(stringBuilder.toString());
        }
    }

}

