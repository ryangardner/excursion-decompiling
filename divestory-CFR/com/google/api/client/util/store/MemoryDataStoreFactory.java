/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util.store;

import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.AbstractMemoryDataStore;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.io.Serializable;

public class MemoryDataStoreFactory
extends AbstractDataStoreFactory {
    public static MemoryDataStoreFactory getDefaultInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected <V extends Serializable> DataStore<V> createDataStore(String string2) throws IOException {
        return new MemoryDataStore(this, string2);
    }

    static class InstanceHolder {
        static final MemoryDataStoreFactory INSTANCE = new MemoryDataStoreFactory();

        InstanceHolder() {
        }
    }

    static class MemoryDataStore<V extends Serializable>
    extends AbstractMemoryDataStore<V> {
        MemoryDataStore(MemoryDataStoreFactory memoryDataStoreFactory, String string2) {
            super(memoryDataStoreFactory, string2);
        }

        @Override
        public MemoryDataStoreFactory getDataStoreFactory() {
            return (MemoryDataStoreFactory)super.getDataStoreFactory();
        }
    }

}

