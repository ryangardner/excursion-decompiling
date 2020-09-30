/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.messages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.sbbi.upnp.services.ServiceActionArgument;

public class ActionResponse {
    private Map outArguments = new HashMap();
    private Map outArgumentsVals = new HashMap();

    protected ActionResponse() {
    }

    protected void addResult(ServiceActionArgument serviceActionArgument, String string2) {
        this.outArguments.put(serviceActionArgument.getName(), serviceActionArgument);
        this.outArgumentsVals.put(serviceActionArgument.getName(), string2);
    }

    public ServiceActionArgument getOutActionArgument(String string2) {
        return (ServiceActionArgument)this.outArguments.get(string2);
    }

    public Set getOutActionArgumentNames() {
        return this.outArguments.keySet();
    }

    public String getOutActionArgumentValue(String string2) {
        return (String)this.outArgumentsVals.get(string2);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator iterator2 = this.outArguments.keySet().iterator();
        while (iterator2.hasNext()) {
            String string2 = (String)iterator2.next();
            String string3 = (String)this.outArgumentsVals.get(string2);
            stringBuffer.append(string2);
            stringBuffer.append("=");
            stringBuffer.append(string3);
            if (!iterator2.hasNext()) continue;
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }
}

