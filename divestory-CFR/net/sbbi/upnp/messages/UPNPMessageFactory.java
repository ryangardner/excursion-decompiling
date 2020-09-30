/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.messages;

import net.sbbi.upnp.messages.ActionMessage;
import net.sbbi.upnp.messages.StateVariableMessage;
import net.sbbi.upnp.services.ServiceAction;
import net.sbbi.upnp.services.ServiceStateVariable;
import net.sbbi.upnp.services.UPNPService;

public class UPNPMessageFactory {
    private UPNPService service;

    private UPNPMessageFactory(UPNPService uPNPService) {
        this.service = uPNPService;
    }

    public static UPNPMessageFactory getNewInstance(UPNPService uPNPService) {
        return new UPNPMessageFactory(uPNPService);
    }

    public ActionMessage getMessage(String object) {
        if ((object = this.service.getUPNPServiceAction((String)object)) == null) return null;
        return new ActionMessage(this.service, (ServiceAction)object);
    }

    public StateVariableMessage getStateVariableMessage(String object) {
        if ((object = this.service.getUPNPServiceStateVariable((String)object)) == null) return null;
        return new StateVariableMessage(this.service, (ServiceStateVariable)object);
    }
}

