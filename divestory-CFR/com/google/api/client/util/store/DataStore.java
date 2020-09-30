/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util.store;

import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public interface DataStore<V extends Serializable> {
    public DataStore<V> clear() throws IOException;

    public boolean containsKey(String var1) throws IOException;

    public boolean containsValue(V var1) throws IOException;

    public DataStore<V> delete(String var1) throws IOException;

    public V get(String var1) throws IOException;

    public DataStoreFactory getDataStoreFactory();

    public String getId();

    public boolean isEmpty() throws IOException;

    public Set<String> keySet() throws IOException;

    public DataStore<V> set(String var1, V var2) throws IOException;

    public int size() throws IOException;

    public Collection<V> values() throws IOException;
}

