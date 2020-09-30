/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util.store;

import com.google.api.client.util.store.DataStore;
import java.io.IOException;
import java.io.Serializable;

public interface DataStoreFactory {
    public <V extends Serializable> DataStore<V> getDataStore(String var1) throws IOException;
}

