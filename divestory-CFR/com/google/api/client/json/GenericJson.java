/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Throwables;
import java.io.IOException;

public class GenericJson
extends GenericData
implements Cloneable {
    private JsonFactory jsonFactory;

    @Override
    public GenericJson clone() {
        return (GenericJson)super.clone();
    }

    public final JsonFactory getFactory() {
        return this.jsonFactory;
    }

    @Override
    public GenericJson set(String string2, Object object) {
        return (GenericJson)super.set(string2, object);
    }

    public final void setFactory(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    public String toPrettyString() throws IOException {
        JsonFactory jsonFactory = this.jsonFactory;
        if (jsonFactory == null) return super.toString();
        return jsonFactory.toPrettyString(this);
    }

    @Override
    public String toString() {
        JsonFactory jsonFactory = this.jsonFactory;
        if (jsonFactory == null) return super.toString();
        try {
            return jsonFactory.toString(this);
        }
        catch (IOException iOException) {
            throw Throwables.propagate(iOException);
        }
    }
}

