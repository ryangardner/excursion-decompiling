/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util.store;

import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public abstract class AbstractDataStore<V extends Serializable>
implements DataStore<V> {
    private final DataStoreFactory dataStoreFactory;
    private final String id;

    protected AbstractDataStore(DataStoreFactory dataStoreFactory, String string2) {
        this.dataStoreFactory = Preconditions.checkNotNull(dataStoreFactory);
        this.id = Preconditions.checkNotNull(string2);
    }

    @Override
    public boolean containsKey(String string2) throws IOException {
        if (this.get(string2) == null) return false;
        return true;
    }

    @Override
    public boolean containsValue(V v) throws IOException {
        return this.values().contains(v);
    }

    @Override
    public DataStoreFactory getDataStoreFactory() {
        return this.dataStoreFactory;
    }

    @Override
    public final String getId() {
        return this.id;
    }

    @Override
    public boolean isEmpty() throws IOException {
        if (this.size() != 0) return false;
        return true;
    }

    @Override
    public int size() throws IOException {
        return this.keySet().size();
    }
}

