/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util.store;

import com.google.api.client.util.Maps;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDataStoreFactory
implements DataStoreFactory {
    private static final Pattern ID_PATTERN = Pattern.compile("\\w{1,30}");
    private final Map<String, DataStore<? extends Serializable>> dataStoreMap = Maps.newHashMap();
    private final Lock lock = new ReentrantLock();

    protected abstract <V extends Serializable> DataStore<V> createDataStore(String var1) throws IOException;

    @Override
    public final <V extends Serializable> DataStore<V> getDataStore(String string2) throws IOException {
        Preconditions.checkArgument(ID_PATTERN.matcher(string2).matches(), "%s does not match pattern %s", string2, ID_PATTERN);
        this.lock.lock();
        try {
            DataStore<? extends Serializable> dataStore;
            DataStore<Serializable> dataStore2 = dataStore = this.dataStoreMap.get(string2);
            if (dataStore != null) return dataStore2;
            dataStore2 = this.createDataStore(string2);
            this.dataStoreMap.put(string2, dataStore2);
            return dataStore2;
        }
        finally {
            this.lock.unlock();
        }
    }
}

