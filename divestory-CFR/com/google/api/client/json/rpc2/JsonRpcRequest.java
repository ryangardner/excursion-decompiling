/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json.rpc2;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

public class JsonRpcRequest
extends GenericData {
    @Key
    private Object id;
    @Key
    private final String jsonrpc;
    @Key
    private String method;
    @Key
    private Object params;

    public JsonRpcRequest() {
        this.jsonrpc = "2.0";
    }

    @Override
    public JsonRpcRequest clone() {
        return (JsonRpcRequest)super.clone();
    }

    public Object getId() {
        return this.id;
    }

    public String getMethod() {
        return this.method;
    }

    public Object getParameters() {
        return this.params;
    }

    public String getVersion() {
        return "2.0";
    }

    @Override
    public JsonRpcRequest set(String string2, Object object) {
        return (JsonRpcRequest)super.set(string2, object);
    }

    public void setId(Object object) {
        this.id = object;
    }

    public void setMethod(String string2) {
        this.method = string2;
    }

    public void setParameters(Object object) {
        this.params = object;
    }
}

