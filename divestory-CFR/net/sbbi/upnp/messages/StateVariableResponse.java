/*
 * Decompiled with CFR <Could not determine version>.
 */
package net.sbbi.upnp.messages;

import net.sbbi.upnp.services.ServiceStateVariable;

public class StateVariableResponse {
    protected ServiceStateVariable stateVar;
    protected String stateVariableValue;

    protected StateVariableResponse() {
    }

    public ServiceStateVariable getStateVar() {
        return this.stateVar;
    }

    public String getStateVariableValue() {
        return this.stateVariableValue;
    }
}

