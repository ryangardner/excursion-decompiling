/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.samples;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import net.sbbi.upnp.Discovery;
import net.sbbi.upnp.ServiceEventHandler;
import net.sbbi.upnp.ServicesEventing;
import net.sbbi.upnp.devices.UPNPDevice;
import net.sbbi.upnp.devices.UPNPRootDevice;
import net.sbbi.upnp.services.UPNPService;

public class MyStateVariableEventsHandler
implements ServiceEventHandler {
    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static void main(String[] var0) {
        var1_7 = ServicesEventing.getInstance();
        var2_8 = new MyStateVariableEventsHandler();
        var1_7.setDaemon(false);
        try {
            var0_1 = Discovery.discover();
        }
        catch (IOException var0_2) {
            var0_2.printStackTrace(System.err);
            var0_3 = null;
        }
        if (var0_4 == null) {
            System.out.println("Unable to find devices");
            return;
        }
        var0_5 = (UPNPService)((UPNPDevice)var0_4[0].getChildDevices().iterator().next()).getServices().iterator().next();
        try {
            var3_9 = var1_7.register(var0_5, var2_8, -1);
            if (var3_9 != -1 && var3_9 != 0) {
                var4_10 = System.out;
                var5_12 = new StringBuilder("State variable events registered for ");
                var5_12.append(var3_9);
                var5_12.append(" ms");
                var4_10.println(var5_12.toString());
            } else if (var3_9 == 0) {
                System.out.println("State variable events registered for infinite ms");
            }
            try {
                Thread.sleep(5000L);
lbl29: // 2 sources:
                do {
                    var1_7.unRegister(var0_5, var2_8);
                    break;
                } while (true);
            }
            catch (InterruptedException var4_11) {
                ** continue;
            }
            {
                return;
            }
        }
        catch (IOException var0_6) {
            var0_6.printStackTrace(System.err);
            return;
        }
    }

    @Override
    public void handleStateVariableEvent(String string2, String string3) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder("State variable ");
        stringBuilder.append(string2);
        stringBuilder.append(" changed to ");
        stringBuilder.append(string3);
        printStream.println(stringBuilder.toString());
    }
}

