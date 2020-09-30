/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.json;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.json.GenericJson;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

public class GoogleJsonErrorContainer
extends GenericJson {
    @Key
    private GoogleJsonError error;

    @Override
    public GoogleJsonErrorContainer clone() {
        return (GoogleJsonErrorContainer)super.clone();
    }

    public final GoogleJsonError getError() {
        return this.error;
    }

    @Override
    public GoogleJsonErrorContainer set(String string2, Object object) {
        return (GoogleJsonErrorContainer)super.set(string2, object);
    }

    public final void setError(GoogleJsonError googleJsonError) {
        this.error = googleJsonError;
    }
}

