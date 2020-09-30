/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util.store;

import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.DataStoreUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AbstractMemoryDataStore<V extends Serializable>
extends AbstractDataStore<V> {
    protected HashMap<String, byte[]> keyValueMap = Maps.newHashMap();
    private final Lock lock = new ReentrantLock();

    protected AbstractMemoryDataStore(DataStoreFactory dataStoreFactory, String string2) {
        super(dataStoreFactory, string2);
    }

    @Override
    public final DataStore<V> clear() throws IOException {
        this.lock.lock();
        try {
            this.keyValueMap.clear();
            this.save();
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean containsKey(String string2) throws IOException {
        if (string2 == null) {
            return false;
        }
        this.lock.lock();
        try {
            boolean bl = this.keyValueMap.containsKey(string2);
            return bl;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean containsValue(V object) throws IOException {
        if (object == null) {
            return false;
        }
        this.lock.lock();
        try {
            boolean bl;
            byte[] arrby = IOUtils.serialize(object);
            object = this.keyValueMap.values().iterator();
            do {
                if (object.hasNext()) continue;
                this.lock.unlock();
                return false;
            } while (!(bl = Arrays.equals(arrby, (byte[])object.next())));
            this.lock.unlock();
            return true;
        }
        catch (Throwable throwable) {
            this.lock.unlock();
            throw throwable;
        }
    }

    @Override
    public DataStore<V> delete(String string2) throws IOException {
        if (string2 == null) {
            return this;
        }
        this.lock.lock();
        try {
            this.keyValueMap.remove(string2);
            this.save();
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public final V get(String string2) throws IOException {
        if (string2 == null) {
            return null;
        }
        this.lock.lock();
        try {
            string2 = IOUtils.deserialize(this.keyValueMap.get(string2));
            return (V)string2;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() throws IOException {
        this.lock.lock();
        try {
            boolean bl = this.keyValueMap.isEmpty();
            return bl;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public final Set<String> keySet() throws IOException {
        this.lock.lock();
        try {
            Set<String> set = Collections.unmodifiableSet(this.keyValueMap.keySet());
            return set;
        }
        finally {
            this.lock.unlock();
        }
    }

    public void save() throws IOException {
    }

    @Override
    public final DataStore<V> set(String string2, V v) throws IOException {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(v);
        this.lock.lock();
        try {
            this.keyValueMap.put(string2, IOUtils.serialize(v));
            this.save();
            return this;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public int size() throws IOException {
        this.lock.lock();
        try {
            int n = this.keyValueMap.size();
            return n;
        }
        finally {
            this.lock.unlock();
        }
    }

    public String toString() {
        return DataStoreUtils.toString(this);
    }

    @Override
    public final Collection<V> values() throws IOException {
        this.lock.lock();
        try {
            ArrayList arrayList = Lists.newArrayList();
            Object object = this.keyValueMap.values().iterator();
            while (object.hasNext()) {
                arrayList.add(IOUtils.deserialize(object.next()));
            }
            object = Collections.unmodifiableList(arrayList);
            return object;
        }
        finally {
            this.lock.unlock();
        }
    }
}

